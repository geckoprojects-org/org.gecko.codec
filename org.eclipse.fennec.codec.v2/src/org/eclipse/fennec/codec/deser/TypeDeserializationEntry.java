/**
 * Copyright (c) 2012 - 2026 Data In Motion and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.eclipse.fennec.codec.deser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.codec.metadata.type.TypeDiscriminatorService;
import org.eclipse.fennec.codec.config.SuperTypeConfig;
import org.eclipse.fennec.codec.config.TypeConfig;
import org.eclipse.fennec.codec.context.ContextHelper;
import org.eclipse.fennec.model.metadata.TypeStrategy;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;

/**
 * Deserialization entry that handles type information (_type property).
 * <p>
 * Supports multiple type formats:
 * <ul>
 *   <li>PLAIN string: {@code "_type": "http://example.org/1.0#//Person"}</li>
 *   <li>STRUCTURED object: {@code "_type": {"schema": "...", "name": "..."}}</li>
 * </ul>
 * </p>
 * <p>
 * The resolved EClass is stored in the {@link DeserializationState} for use
 * by the deserializer when creating the EObject.
 * </p>
 *
 * @see TypeConfig
 * @see <a href="docs/codec-v2-serialization-spec.md#15-deserialization-requirements">Spec 15: Deserialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class TypeDeserializationEntry implements DeserializationEntry {

    private static final Logger LOGGER = Logger.getLogger(TypeDeserializationEntry.class.getName());

    private final TypeConfig config;
    private final TypeDiscriminatorService typeDiscriminatorService;
    private final SuperTypeConfig superTypeConfig;

    /**
     * Creates a new TypeDeserializationEntry.
     *
     * @param config the effective type configuration
     */
    public TypeDeserializationEntry(TypeConfig config) {
        this(config, null, null);
    }

    /**
     * Creates a new TypeDeserializationEntry with a TypeDiscriminatorService.
     *
     * @param config the effective type configuration
     * @param typeDiscriminatorService the service for MAPPED strategy type resolution (may be null)
     */
    public TypeDeserializationEntry(TypeConfig config, TypeDiscriminatorService typeDiscriminatorService) {
        this(config, typeDiscriminatorService, null);
    }

    /**
     * Creates a new TypeDeserializationEntry with SuperType configuration for STRUCTURED format.
     *
     * @param config the effective type configuration
     * @param typeDiscriminatorService the service for MAPPED strategy type resolution (may be null)
     * @param superTypeConfig the supertype configuration for validation (may be null)
     */
    public TypeDeserializationEntry(TypeConfig config, TypeDiscriminatorService typeDiscriminatorService,
            SuperTypeConfig superTypeConfig) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.typeDiscriminatorService = typeDiscriminatorService;
        this.superTypeConfig = superTypeConfig;
    }

    @Override
    public String getKey() {
        return config.getTypeKey();
    }

    @Override
    public void deserialize(DeserializationState state, JsonParser parser, DeserializationContext ctxt) {
        deserializeWithHint(state, parser, ctxt, null);
    }

    /**
     * Deserializes type information with an optional hint EClass for context.
     * <p>
     * The hint EClass is used to provide context for MAPPED type resolution.
     * If a hint is provided and has a mapId, that mapId is used to narrow
     * the discriminator lookup.
     * </p>
     *
     * @param state the deserialization state
     * @param parser the JSON parser
     * @param ctxt the deserialization context
     * @param hintEClass optional hint EClass for MAPPED context (may be null)
     */
    public void deserializeWithHint(DeserializationState state, JsonParser parser,
            DeserializationContext ctxt, EClass hintEClass) {
        deserializeWithSchemaHint(state, parser, ctxt, hintEClass, null);
    }

    /**
     * Deserializes type information with optional hint EClass and schema value.
     * <p>
     * The hint EClass is used to provide context for MAPPED type resolution.
     * The schemaValue is used for PLAIN SCHEMA_AND_TYPE format where schema
     * is provided as a separate field.
     * </p>
     * <p>
     * Supports smart compression: when encountering a simple name (e.g., "Person"),
     * the method first tries to resolve it using the context schema (if set).
     * The context schema is established from the root object's full type URI.
     * </p>
     *
     * @param state the deserialization state
     * @param parser the JSON parser
     * @param ctxt the deserialization context
     * @param hintEClass optional hint EClass for MAPPED context (may be null)
     * @param schemaValue optional schema value for PLAIN SCHEMA_AND_TYPE (may be null)
     */
    public void deserializeWithSchemaHint(DeserializationState state, JsonParser parser,
            DeserializationContext ctxt, EClass hintEClass, String schemaValue) {
        JsonToken token = parser.currentToken();

        String typeValue = null;

        if (token == JsonToken.VALUE_STRING) {
            // PLAIN format: "_type": "http://example.org/1.0#//Person" or "_type": "Person"
            String rawTypeValue = parser.getString();

            // If we have a schema value (PLAIN SCHEMA_AND_TYPE), combine them
            if (schemaValue != null && !schemaValue.isEmpty() && !rawTypeValue.contains("#//")) {
                // Schema + simple name -> compose URI
                typeValue = schemaValue + "#//" + rawTypeValue;
            } else {
                typeValue = rawTypeValue;
            }
        } else if (token == JsonToken.START_OBJECT) {
            // STRUCTURED format: "_type": {"schema": "...", "type": "...", "supertype": [...]}
            StructuredTypeResult result = parseStructuredType(parser);
            typeValue = result.typeValue;

            // Store parsed supertypes for validation after EClass is resolved
            if (result.superTypes != null && !result.superTypes.isEmpty()) {
                // We'll validate after resolving the EClass
                if (typeValue != null) {
                    EClass resolvedClass = resolveEClass(typeValue, hintEClass, ctxt);
                    if (resolvedClass != null) {
                        state.setResolvedEClass(resolvedClass);
                        // Validate supertype hierarchy if enabled
                        validateSuperTypes(resolvedClass, result.superTypes);
                    } else {
                        String msg = "Could not resolve EClass from type value: " + typeValue;
                        LOGGER.warning(msg);
                        ContextHelper.addWarning(ctxt, msg, parser, "TypeDeserializationEntry");
                    }
                }
                return;
            }
        } else {
            String msg = "Unexpected token for _type: " + token;
            LOGGER.warning(msg);
            ContextHelper.addWarning(ctxt, msg, parser, "TypeDeserializationEntry");
            return;
        }

        if (typeValue != null) {
            EClass resolvedClass = resolveEClass(typeValue, hintEClass, ctxt);
            if (resolvedClass != null) {
                state.setResolvedEClass(resolvedClass);
            } else {
                String msg = "Could not resolve EClass from type value: " + typeValue;
                LOGGER.warning(msg);
                ContextHelper.addWarning(ctxt, msg, parser, "TypeDeserializationEntry");
            }
        }
    }

    /**
     * Validates supertype hierarchy if validation is enabled.
     *
     * @param resolvedEClass the resolved EClass
     * @param declaredSuperTypes the supertypes declared in JSON
     */
    private void validateSuperTypes(EClass resolvedEClass, List<String> declaredSuperTypes) {
        // TODO: Replace with DeserializationMode.STRICT check when mode system is implemented
        if (superTypeConfig == null) {
            LOGGER.fine("SuperType validation disabled for STRUCTURED format");
            return;
        }

        if (declaredSuperTypes.isEmpty()) {
            return;
        }

        // Delegate to SuperTypeDeserializationEntry's validation logic
        SuperTypeDeserializationEntry.validateSuperTypeHierarchyStatic(
                resolvedEClass, declaredSuperTypes, superTypeConfig);
    }

    /**
     * Result holder for parsed structured type information.
     */
    private static class StructuredTypeResult {
        String typeValue;
        List<String> superTypes = new ArrayList<>();
    }

    /**
     * Parses a structured type object.
     * <p>
     * Supports multiple formats based on strategy (all use unified "type" key except NUMERIC):
     * <ul>
     *   <li>URI: {@code {"type": "http://example.org/1.0#//Person"}}</li>
     *   <li>NAME: {@code {"type": "Person"}}</li>
     *   <li>CLASS: {@code {"type": "org.example.Person"}}</li>
     *   <li>MAPPED: {@code {"type": "customer"}}</li>
     *   <li>NUMERIC: {@code {"schema": "http://...", "classifier": 3}}</li>
     *   <li>SCHEMA_AND_TYPE: {@code {"schema": "http://...", "type": "Person"}}</li>
     * </ul>
     * Also extracts optional supertype field if present:
     * <ul>
     *   <li>Array: {@code "supertype": ["Entity", "http://audit.org/1.0#//Auditable"]}</li>
     *   <li>String: {@code "supertype": "Entity,Auditable"}</li>
     * </ul>
     * </p>
     *
     * @param parser the JSON parser positioned at START_OBJECT
     * @return the structured type result containing type value and optional supertypes
     */
    private StructuredTypeResult parseStructuredType(JsonParser parser) {
        StructuredTypeResult result = new StructuredTypeResult();
        String schema = null;
        String typeValue = null;
        Integer classifier = null;

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.currentName();
            parser.nextToken(); // Move to value

            if (config.getSchemaKey().equals(fieldName)) {
                schema = parser.getString();
            } else if (config.getNameKey().equals(fieldName)) {
                // "type" key - contains the type value (URI, name, class, discriminator)
                typeValue = parser.getString();
            } else if ("classifier".equals(fieldName)) {
                // NUMERIC strategy: classifier ID
                classifier = parser.getIntValue();
            } else if (getSuperTypeKey().equals(fieldName)) {
                // Parse supertype (ARRAY or STRING presentation)
                result.superTypes = parseSuperTypesFromStructured(parser);
            }
        }

        // NUMERIC strategy: schema + classifier
        if (classifier != null && schema != null) {
            result.typeValue = buildNumericTypeValue(schema, classifier);
            return result;
        }

        // SCHEMA_AND_TYPE: schema + type name -> compose URI
        if (schema != null && typeValue != null && !typeValue.contains("#//")) {
            result.typeValue = schema + "#//" + typeValue;
            return result;
        }

        // URI, NAME, CLASS, MAPPED: just return the type value
        if (typeValue != null) {
            result.typeValue = typeValue;
            return result;
        }

        LOGGER.warning("Could not parse structured type: schema=" + schema + ", type=" + typeValue + ", classifier=" + classifier);
        return result;
    }

    /**
     * Gets the supertype key for STRUCTURED format.
     * <p>
     * In STRUCTURED format, the key is "supertype" (without underscore prefix)
     * since it's nested inside the _type object.
     * </p>
     *
     * @return the supertype key for STRUCTURED format
     */
    private String getSuperTypeKey() {
        // In STRUCTURED format, use "supertype" (not "_supertype")
        // as it's inside the _type object
        return superTypeConfig != null ? superTypeConfig.getSuperTypeKey().replace("_", "") : "supertype";
    }

    /**
     * Parses supertype values from within a structured type object.
     * <p>
     * Handles both ARRAY and STRING presentation:
     * <ul>
     *   <li>ARRAY: {@code ["Entity", "http://audit.org/1.0#//Auditable"]}</li>
     *   <li>STRING: {@code "Entity,http://audit.org/1.0#//Auditable"}</li>
     * </ul>
     * </p>
     *
     * @param parser the JSON parser positioned at the supertype value
     * @return list of declared supertype values
     */
    private List<String> parseSuperTypesFromStructured(JsonParser parser) {
        List<String> superTypes = new ArrayList<>();
        JsonToken token = parser.currentToken();

        if (token == JsonToken.START_ARRAY) {
            // ARRAY presentation
            JsonToken arrayToken;
            while ((arrayToken = parser.nextToken()) != JsonToken.END_ARRAY) {
                if (arrayToken == JsonToken.VALUE_STRING) {
                    superTypes.add(parser.getString());
                }
            }
        } else if (token == JsonToken.VALUE_STRING) {
            // STRING presentation - split by separator
            String value = parser.getString();
            if (value != null && !value.isEmpty()) {
                String separator = superTypeConfig != null ? superTypeConfig.getSeparator() : ",";
                for (String part : value.split(java.util.regex.Pattern.quote(separator))) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        superTypes.add(trimmed);
                    }
                }
            }
        }

        return superTypes;
    }

    /**
     * Builds a type value for NUMERIC strategy (schema + classifier).
     * <p>
     * Looks up the EPackage and finds the EClass by classifier ID.
     * Returns the composed URI if found.
     * </p>
     *
     * @param schema the EPackage nsURI
     * @param classifier the classifier ID
     * @return the composed URI string, or the classifier as string if not found
     */
    private String buildNumericTypeValue(String schema, int classifier) {
        EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(schema);
        if (ePackage != null) {
            for (EClassifier eClassifier : ePackage.getEClassifiers()) {
                if (eClassifier instanceof EClass && eClassifier.getClassifierID() == classifier) {
                    return schema + "#//" + eClassifier.getName();
                }
            }
        }
        LOGGER.warning("Could not find EClass for schema=" + schema + ", classifier=" + classifier);
        return String.valueOf(classifier);
    }

    /**
     * Resolves an EClass from a type value based on the configured strategy.
     * <p>
     * Supports formats based on strategy:
     * <ul>
     *   <li>URI: {@code http://example.org/1.0#//Person} - Full URI</li>
     *   <li>NAME: {@code Person} - Simple class name</li>
     *   <li>CLASS: {@code com.example.Person} - Java class name</li>
     *   <li>NUMERIC: {@code 1} - Classifier ID</li>
     *   <li>MAPPED: Uses discriminator value mapping</li>
     * </ul>
     * </p>
     * <p>
     * Supports smart compression: when encountering a full URI, the context schema
     * is established (if not already set). When encountering a simple name, it is
     * first resolved using the context schema before falling back to searching all packages.
     * </p>
     *
     * @param typeValue the type value (format depends on strategy)
     * @param hintEClass optional hint EClass for MAPPED context (may be null)
     * @param ctxt the deserialization context (for smart compression context schema)
     * @return the resolved EClass, or null if not found
     */
    public EClass resolveEClass(String typeValue, EClass hintEClass, DeserializationContext ctxt) {
        if (typeValue == null || typeValue.isEmpty()) {
            return null;
        }

        // First: check if it's a full URI (always highest priority)
        if (typeValue.contains("#//")) {
            EClass resolved = resolveFromUri(typeValue);
            if (resolved != null) {
                // Establish context schema for smart compression (root object)
                initializeContextSchemaIfNeeded(typeValue, ctxt);
                return resolved;
            }
        }

        // Second: try smart compression - resolve simple name using context schema
        if (!typeValue.contains("#//") && ctxt != null) {
            String contextSchema = ContextHelper.getContextSchemaUri(ctxt);
            if (contextSchema != null) {
                // Try to resolve using context schema first
                String composedUri = contextSchema + "#//" + typeValue;
                EClass resolved = resolveFromUri(composedUri);
                if (resolved != null) {
                    LOGGER.fine("Resolved type via smart compression: " + typeValue + " -> " + resolved.getName());
                    return resolved;
                }
            }
        }

        // Third: try discriminator lookup via TypeDiscriminatorService.
        // Use the hint to provide context for MAPPED strategy.
        if (typeDiscriminatorService != null) {
            EClass resolved = typeDiscriminatorService.getEClassFromAny(typeValue);
            if (resolved != null) {
                LOGGER.fine("Resolved type via discriminator: " + typeValue + " -> " + resolved.getName());
                return resolved;
            }
        }

        // Fourth: handle based on configured strategy
        TypeStrategy strategy = config.getStrategy();
        if (strategy == null) {
            strategy = TypeStrategy.URI;
        }

        switch (strategy) {
            case NAME:
                return resolveFromSimpleName(typeValue);
            case CLASS:
                return resolveFromClassName(typeValue);
            case NUMERIC:
                return resolveFromNumeric(typeValue, hintEClass);
            // @claude MAPPED was removed from TypeStrategy enum per spec Section 11.8.
            // Discriminator mapping is now a separate configuration layer, not a type strategy.
            // The discriminator mapping logic should be invoked BEFORE this switch, with priority
            // over type strategy resolution. See type-strategy-scope-proposal.md Section 11.8.
            case SCHEMA_AND_TYPE:
                // TODO: Implement SCHEMA_AND_TYPE resolution
                return resolveFromSimpleName(typeValue);
            case URI:
            default:
                // Fallback to simple name resolution
                return resolveFromSimpleName(typeValue);
        }
    }

    /**
     * Initializes the context schema for smart compression.
     * <p>
     * The context schema is extracted from the first full URI encountered (root object).
     * It is used to resolve simple names in subsequent (contained) objects.
     * </p>
     *
     * @param fullUri the full type URI (e.g., "http://example.org/1.0#//Company")
     * @param ctxt the deserialization context
     */
    private void initializeContextSchemaIfNeeded(String fullUri, DeserializationContext ctxt) {
        if (ctxt == null) {
            return;
        }

        // Only set context schema once (for root object)
        if (ContextHelper.getContextSchemaUri(ctxt) != null) {
            return;
        }

        // Extract schema from full URI
        String schemaUri = ContextHelper.extractSchemaUri(fullUri);
        if (schemaUri != null) {
            ContextHelper.setContextSchemaUri(ctxt, schemaUri);
            LOGGER.fine("Established context schema for smart compression: " + schemaUri);
        }
    }

    /**
     * Resolves an EClass by its simple name.
     * <p>
     * Searches through all registered EPackages for a matching class name.
     * </p>
     *
     * @param className the simple class name
     * @return the resolved EClass, or null if not found
     */
    private EClass resolveFromSimpleName(String className) {
        // Search through all registered packages
        for (Object key : EPackage.Registry.INSTANCE.keySet()) {
            EPackage pkg = EPackage.Registry.INSTANCE.getEPackage((String) key);
            if (pkg != null) {
                EClassifier classifier = pkg.getEClassifier(className);
                if (classifier instanceof EClass) {
                    return (EClass) classifier;
                }
            }
        }
        LOGGER.warning("Could not resolve EClass from simple name: " + className);
        return null;
    }

    /**
     * Resolves an EClass by its Java instance class name.
     *
     * @param className the fully qualified Java class name
     * @return the resolved EClass, or null if not found
     */
    private EClass resolveFromClassName(String className) {
        // Search through all registered packages for matching instance class
        for (Object key : EPackage.Registry.INSTANCE.keySet()) {
            EPackage pkg = EPackage.Registry.INSTANCE.getEPackage((String) key);
            if (pkg != null) {
                for (EClassifier classifier : pkg.getEClassifiers()) {
                    if (classifier instanceof EClass eClass) {
                        Class<?> instanceClass = eClass.getInstanceClass();
                        if (instanceClass != null && className.equals(instanceClass.getName())) {
                            return eClass;
                        }
                    }
                }
            }
        }
        // Fallback to simple name (use last segment of className)
        String simpleName = className.contains(".") ?
            className.substring(className.lastIndexOf('.') + 1) : className;
        return resolveFromSimpleName(simpleName);
    }

    /**
     * Resolves an EClass by its classifier ID.
     * <p>
     * For PLAIN NUMERIC format, the classifier ID alone is ambiguous since
     * different packages can have the same classifier IDs. If a hint EClass
     * is provided, its package is used for lookup first. Otherwise, all
     * registered packages are searched.
     * </p>
     *
     * @param numericValue the classifier ID as string
     * @param hintEClass optional hint EClass for package context (may be null)
     * @return the resolved EClass, or null if not found
     */
    private EClass resolveFromNumeric(String numericValue, EClass hintEClass) {
        try {
            int classifierId = Integer.parseInt(numericValue);

            // If we have a hint, try its package first (most reliable)
            if (hintEClass != null && hintEClass.getEPackage() != null) {
                EPackage hintPackage = hintEClass.getEPackage();
                EClass resolved = findClassifierInPackage(hintPackage, classifierId);
                if (resolved != null) {
                    return resolved;
                }
            }

            // Fallback: search through all registered packages
            for (Object key : EPackage.Registry.INSTANCE.keySet()) {
                EPackage pkg = EPackage.Registry.INSTANCE.getEPackage((String) key);
                if (pkg != null) {
                    EClass resolved = findClassifierInPackage(pkg, classifierId);
                    if (resolved != null) {
                        return resolved;
                    }
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid numeric classifier ID: " + numericValue);
        }
        return null;
    }

    /**
     * Finds an EClass by classifier ID within a specific package.
     *
     * @param pkg the EPackage to search
     * @param classifierId the classifier ID
     * @return the EClass if found, null otherwise
     */
    private EClass findClassifierInPackage(EPackage pkg, int classifierId) {
        for (EClassifier classifier : pkg.getEClassifiers()) {
            if (classifier instanceof EClass && classifier.getClassifierID() == classifierId) {
                return (EClass) classifier;
            }
        }
        return null;
    }

    /**
     * Resolves an EClass from a full URI.
     *
     * @param uri the URI in format "nsURI#//ClassName"
     * @return the resolved EClass, or null if not found
     */
    private EClass resolveFromUri(String uri) {
        try {
            URI emfUri = URI.createURI(uri);
            String nsUri = emfUri.trimFragment().toString();
            String fragment = emfUri.fragment();

            if (fragment == null || !fragment.startsWith("//")) {
                LOGGER.warning("Invalid EClass URI fragment: " + uri);
                return null;
            }

            String className = fragment.substring(2); // Remove "//"

            // Look up package in global registry
            EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsUri);
            if (ePackage == null) {
                LOGGER.warning("EPackage not found for URI: " + nsUri);
                return null;
            }

            Object classifier = ePackage.getEClassifier(className);
            if (classifier instanceof EClass) {
                return (EClass) classifier;
            } else {
                LOGGER.warning("Classifier is not an EClass: " + className);
                return null;
            }
        } catch (Exception e) {
            LOGGER.warning("Error resolving EClass from URI: " + uri + " - " + e.getMessage());
            return null;
        }
    }
}
