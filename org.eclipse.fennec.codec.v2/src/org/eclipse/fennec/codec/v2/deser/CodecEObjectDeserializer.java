/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
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
package org.eclipse.fennec.codec.v2.deser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveClassConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveSuperTypeConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveTypeConfig;
import org.eclipse.fennec.codec.v2.context.ContextHelper;
import org.eclipse.fennec.codec.v2.context.EMFCodecReadContext;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Main Jackson deserializer for EObjects in codec.v2.
 * <p>
 * Orchestrates the deserialization process using the entry-based pattern:
 * <ol>
 *   <li>Read type information first (if present)</li>
 *   <li>Resolve EClass (from type info or CODEC_ROOT_OBJECT option)</li>
 *   <li>Create EObject instance</li>
 *   <li>Build deserialization entries for the EClass</li>
 *   <li>Read remaining properties and deserialize</li>
 *   <li>Handle unresolved references</li>
 * </ol>
 * </p>
 * <p>
 * Uses pre-resolved {@link EffectiveCodecConfig} for all configuration,
 * eliminating runtime resolution overhead.
 * </p>
 *
 * @see EffectiveCodecConfig
 * @see DeserializationEntry
 * @see <a href="docs/codec-v2-serialization-spec.md#15-deserialization-requirements">Spec 15: Deserialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class CodecEObjectDeserializer extends ValueDeserializer<EObject> {

    private static final Logger LOGGER = Logger.getLogger(CodecEObjectDeserializer.class.getName());

    /** Context attribute key for CODEC_ROOT_OBJECT option (user-provided root hint) */
    public static final String CODEC_ROOT_OBJECT = "CODEC_ROOT_OBJECT";

    /** Default reference key for non-containment references */
    private static final String DEFAULT_REF_KEY = "_ref";

    /** Default type key */
    private static final String DEFAULT_TYPE_KEY = "_type";

    /** Default schema key for PLAIN SCHEMA_AND_TYPE format */
    private static final String DEFAULT_SCHEMA_KEY = "_schema";

    private final EffectiveCodecConfig config;

    /**
     * Creates a new CodecEObjectDeserializer.
     *
     * @param config the effective codec configuration
     */
    public CodecEObjectDeserializer(EffectiveCodecConfig config) {
        this.config = Objects.requireNonNull(config, "config must not be null");
    }

    @Override
    public Class<?> handledType() {
        return EObject.class;
    }

    @Override
    public EObject deserialize(JsonParser parser, DeserializationContext ctxt) {
        JsonToken token = parser.currentToken();

        // Handle null
        if (token == JsonToken.VALUE_NULL) {
            return null;
        }

        // Must be at START_OBJECT
        if (token != JsonToken.START_OBJECT) {
            LOGGER.warning("Expected START_OBJECT, got: " + token);
            return null;
        }

        // Try to get EMF context from parser's stream context (preferred)
        // This provides access to resource, type hints, and metadata service
        EMFCodecReadContext emfContext = null;
        if (parser.streamReadContext() instanceof EMFCodecReadContext ctx) {
            emfContext = ctx;
        }

        // Create deserialization state with resource from context
        org.eclipse.emf.ecore.resource.Resource resource = emfContext != null ? emfContext.getResource() : null;
        DeserializationState state = new DeserializationState(resource);

        // Get or create shared unresolved references list from context
        @SuppressWarnings("unchecked")
        java.util.List<DeserializationState.UnresolvedReference> unresolvedRefs =
                (java.util.List<DeserializationState.UnresolvedReference>) ctxt.getAttribute(ContextHelper.UNRESOLVED_REFERENCES);
        if (unresolvedRefs != null) {
            // Use shared list for collecting unresolved references
            state.setSharedUnresolvedReferences(unresolvedRefs);
        }

        // Get the expected type hint (must be EClass if set)
        // Priority:
        // 1. Parser's stream context (CodecJsonReadContext) - for nested objects
        // 2. Jackson's DeserializationContext.getAttribute() - for backwards compatibility
        EClass hintEClass = null;
        if (emfContext != null) {
            hintEClass = emfContext.getCurrentTypeHint();
        }
        if (hintEClass == null) {
            // Fall back to ContextHelper for backwards compatibility
            hintEClass = ContextHelper.getExpectedType(ctxt);
        }

        // Check if we need to use featurePath-based type resolution
        // featurePath is used when:
        // 1. The hint class has a discriminatorPath configured (e.g., "info.profileName"), OR
        // 2. No hint is provided but a discriminatorPath exists in any registered registry
        //
        // If no featurePath is configured, we use standard _type field resolution
        // which may use MAPPED strategy (discriminator value lookup) or URI strategy
        String discriminatorPath = getDiscriminatorPath(hintEClass);

        // If no hint provided, try to find ANY discriminatorPath from registered types
        if (!FeaturePathTypeResolver.hasDiscriminatorPath(discriminatorPath)
                && hintEClass == null
                && config.getTypeDiscriminatorService() != null) {
            discriminatorPath = config.getTypeDiscriminatorService().getAnyDiscriminatorPath();
        }

        if (FeaturePathTypeResolver.hasDiscriminatorPath(discriminatorPath)
                && config.getTypeDiscriminatorService() != null) {
            return deserializeWithFeaturePath(parser, ctxt, state, hintEClass, discriminatorPath);
        }

        // Standard deserialization flow
        // Note: hintEClass is stored but NOT set as resolvedEClass yet
        // We need to wait for _type field to potentially resolve to a more specific class
        // The hint is used as context for MAPPED type resolution

        // Storage for deferred properties (those read before type is resolved)
        Map<String, Object> deferredProperties = new HashMap<>();
        EClass resolvedEClass = null;
        EObject eObject = null;
        boolean typeFieldProcessed = false;
        String schemaValue = null;  // For PLAIN SCHEMA_AND_TYPE format

        // Read properties
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String propertyName = parser.currentName();
            parser.nextToken(); // Move to value

            // Check if this is the schema property (for PLAIN SCHEMA_AND_TYPE)
            if (isSchemaKey(propertyName)) {
                schemaValue = parser.getString();
                continue;
            }

            // Check if this is the type property - ALWAYS process it when present
            if (isTypeKey(propertyName)) {
                resolvedEClass = resolveType(parser, state, hintEClass, schemaValue);
                state.setResolvedEClass(resolvedEClass);
                typeFieldProcessed = true;

                // Now we can create the object and process deferred properties
                if (resolvedEClass != null) {
                    eObject = state.createEObject();
                    processDeferredProperties(state, deferredProperties, ctxt);
                }
                continue;
            }

            // If we don't have the type yet, defer this property
            if (resolvedEClass == null) {
                deferredProperties.put(propertyName, readCurrentValue(parser));
                continue;
            }

            // Create object if not yet created
            if (eObject == null) {
                eObject = state.createEObject();
                processDeferredProperties(state, deferredProperties, ctxt);
            }

            // Deserialize the property
            deserializeProperty(state, propertyName, parser, ctxt);
        }

        // If no _type field was found, fall back to hint
        if (!typeFieldProcessed && hintEClass != null) {
            resolvedEClass = hintEClass;
            state.setResolvedEClass(resolvedEClass);
        }

        // Handle case where type was never found
        if (eObject == null && resolvedEClass != null) {
            eObject = state.createEObject();
            processDeferredProperties(state, deferredProperties, ctxt);
        }

        if (eObject == null) {
            LOGGER.severe("Cannot deserialize: no type information found and no CODEC_ROOT_OBJECT hint");
        }

        return eObject;
    }

    /**
     * Checks if the property name is a type key.
     * <p>
     * Supports common type keys as well as custom keys configured in the codec.
     * </p>
     */
    private boolean isTypeKey(String propertyName) {
        // Common type keys - check these for compatibility with various serialization formats
        if (DEFAULT_TYPE_KEY.equals(propertyName)
            || "_class".equals(propertyName)
            || "@type".equals(propertyName)
            || "type".equals(propertyName)) {
            return true;
        }
        // Check against module-configured type key
        return config.getGlobalTypeKey() != null && config.getGlobalTypeKey().equals(propertyName);
    }

    /**
     * Checks if the property name is a schema key (for PLAIN SCHEMA_AND_TYPE format).
     * <p>
     * Supports common schema keys as well as custom keys.
     * </p>
     */
    private boolean isSchemaKey(String propertyName) {
        // Common schema keys for PLAIN SCHEMA_AND_TYPE format
        return DEFAULT_SCHEMA_KEY.equals(propertyName)
            || "@vocab".equals(propertyName)
            || "schema".equals(propertyName);
    }

    /**
     * Resolves the EClass from the current parser position.
     * <p>
     * Uses the configured type strategy to interpret the type value.
     * The hint EClass is used as context for MAPPED type resolution -
     * it provides the mapId for discriminator lookup.
     * </p>
     * <p>
     * For PLAIN SCHEMA_AND_TYPE format, the schemaValue parameter provides
     * the schema (EPackage nsURI) which is combined with the type name
     * to resolve the EClass.
     * </p>
     *
     * @param parser the JSON parser positioned at the type value
     * @param state the deserialization state
     * @param hintEClass optional hint EClass for MAPPED context (may be null)
     * @param schemaValue optional schema value for PLAIN SCHEMA_AND_TYPE (may be null)
     * @return the resolved EClass, or null if resolution fails
     */
    private EClass resolveType(JsonParser parser, DeserializationState state, EClass hintEClass, String schemaValue) {
        // Build effective type config from module defaults
        // We use module config here since we don't know the EClass yet
        EffectiveTypeConfig typeConfig = EffectiveTypeConfig.builder()
                .enabled(true)
                .typeKey(DEFAULT_TYPE_KEY)
                .strategy(config.getGlobalTypeStrategy())  // Use global strategy for deserialization
                .build();

        // Build supertype config for STRUCTURED format validation
        // Use defaults when we don't have a specific EClass config yet
        EffectiveSuperTypeConfig superTypeConfig = EffectiveSuperTypeConfig.builder()
                .validateSuperTypeHierarchy(config.isValidateSuperTypeHierarchy())
                .superTypeKey(config.getGlobalSuperTypeKey())
                .build();

        TypeDeserializationEntry typeEntry = new TypeDeserializationEntry(
                typeConfig, config.getTypeDiscriminatorService(), superTypeConfig);

        // Pass the hint and schema to the type entry
        typeEntry.deserializeWithSchemaHint(state, parser, null, hintEClass, schemaValue);

        return state.getResolvedEClass();
    }

    /**
     * Reads the current value from the parser into a simple Java object.
     * <p>
     * This is used for deferring property values when the type is not yet known.
     * </p>
     */
    private Object readCurrentValue(JsonParser parser) {
        JsonToken token = parser.currentToken();

        switch (token) {
            case VALUE_STRING:
                return parser.getString();
            case VALUE_NUMBER_INT:
                return parser.getLongValue();
            case VALUE_NUMBER_FLOAT:
                return parser.getDoubleValue();
            case VALUE_TRUE:
                return Boolean.TRUE;
            case VALUE_FALSE:
                return Boolean.FALSE;
            case VALUE_NULL:
                return null;
            case START_OBJECT:
                return readObjectAsMap(parser);
            case START_ARRAY:
                return readArrayAsList(parser);
            default:
                return null;
        }
    }

    /**
     * Reads a JSON object into a Map.
     */
    private Map<String, Object> readObjectAsMap(JsonParser parser) {
        Map<String, Object> result = new java.util.LinkedHashMap<>();

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.currentName();
            parser.nextToken(); // Move to value
            Object value = readCurrentValue(parser);
            result.put(fieldName, value);
        }

        return result;
    }

    /**
     * Reads a JSON array into a List.
     */
    private java.util.List<Object> readArrayAsList(JsonParser parser) {
        java.util.List<Object> result = new java.util.ArrayList<>();

        while (parser.nextToken() != JsonToken.END_ARRAY) {
            Object value = readCurrentValue(parser);
            result.add(value);
        }

        return result;
    }

    /**
     * Processes deferred properties after the type is resolved.
     * <p>
     * Deferred properties are those that were encountered before the type was resolved.
     * This includes ID, type entries, and regular features.
     * </p>
     */
    private void processDeferredProperties(DeserializationState state,
            Map<String, Object> deferredProperties, DeserializationContext ctxt) {
        EClass eClass = state.getResolvedEClass();
        if (eClass == null) {
            return;
        }

        Map<String, DeserializationEntry> entries = buildDeserializationEntries(eClass);

        for (Map.Entry<String, Object> entry : deferredProperties.entrySet()) {
            String propertyName = entry.getKey();
            Object value = entry.getValue();

            DeserializationEntry deserEntry = entries.get(propertyName);
            if (deserEntry != null && value != null) {
                // For special entries (ID, Type), replay the value through a TokenBuffer
                if (deserEntry instanceof IdDeserializationEntry
                        || deserEntry instanceof TypeDeserializationEntry) {
                    replayDeferredValue(state, deserEntry, value, ctxt);
                } else {
                    // For regular features, set the value directly
                    setDeferredValue(state, propertyName, value);
                }
            }
        }
    }

    /**
     * Replays a deferred value through a TokenBuffer for special entries.
     */
    private void replayDeferredValue(DeserializationState state, DeserializationEntry entry,
            Object value, DeserializationContext ctxt) {
        try {
            // Create a TokenBuffer and write the value to it
            tools.jackson.databind.util.TokenBuffer buffer = ctxt.bufferForInputBuffering(ctxt.getParser());

            // Write the appropriate token based on value type
            if (value instanceof String s) {
                buffer.writeString(s);
            } else if (value instanceof Number n) {
                if (n instanceof Integer i) {
                    buffer.writeNumber(i);
                } else if (n instanceof Long l) {
                    buffer.writeNumber(l);
                } else if (n instanceof Double d) {
                    buffer.writeNumber(d);
                } else {
                    buffer.writeNumber(n.doubleValue());
                }
            } else if (value instanceof Boolean b) {
                buffer.writeBoolean(b);
            } else if (value instanceof java.util.Map<?, ?> map) {
                // For structured ID, write as object
                buffer.writeStartObject();
                for (var e : map.entrySet()) {
                    buffer.writeName(String.valueOf(e.getKey()));
                    writeValueToBuffer(buffer, e.getValue());
                }
                buffer.writeEndObject();
            } else if (value == null) {
                buffer.writeNull();
            } else {
                buffer.writeString(value.toString());
            }

            // Replay through the entry
            try (tools.jackson.core.JsonParser bufferParser = buffer.asParser(ctxt)) {
                bufferParser.nextToken(); // Move to first token
                entry.deserialize(state, bufferParser, ctxt);
            }
        } catch (Exception e) {
            LOGGER.fine("Could not replay deferred value for " + entry.getKey() + ": " + e.getMessage());
        }
    }

    /**
     * Writes a value to the TokenBuffer.
     */
    private void writeValueToBuffer(tools.jackson.databind.util.TokenBuffer buffer, Object value) {
        if (value instanceof String s) {
            buffer.writeString(s);
        } else if (value instanceof Number n) {
            if (n instanceof Integer i) {
                buffer.writeNumber(i);
            } else if (n instanceof Long l) {
                buffer.writeNumber(l);
            } else if (n instanceof Double d) {
                buffer.writeNumber(d);
            } else {
                buffer.writeNumber(n.doubleValue());
            }
        } else if (value instanceof Boolean b) {
            buffer.writeBoolean(b);
        } else if (value == null) {
            buffer.writeNull();
        } else {
            buffer.writeString(value.toString());
        }
    }

    /**
     * Sets a deferred value on the EObject.
     */
    private void setDeferredValue(DeserializationState state, String propertyName, Object value) {
        EObject eObject = state.getEObject();
        EClass eClass = state.getResolvedEClass();

        // Find the feature by name or key
        EStructuralFeature feature = findFeatureByKey(eClass, propertyName);
        if (feature != null && feature.isChangeable() && !feature.isDerived()) {
            try {
                eObject.eSet(feature, value);
            } catch (Exception e) {
                LOGGER.fine("Could not set deferred value for " + propertyName + ": " + e.getMessage());
            }
        }
    }

    /**
     * Finds a feature by its JSON key or name.
     */
    private EStructuralFeature findFeatureByKey(EClass eClass, String key) {
        // First try direct name match
        EStructuralFeature feature = eClass.getEStructuralFeature(key);
        if (feature != null) {
            return feature;
        }

        // Search through all features checking config keys
        for (EStructuralFeature f : eClass.getEAllStructuralFeatures()) {
            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(f);
            if (featureConfig.getKey().equals(key)) {
                return f;
            }
        }

        return null;
    }

    /**
     * Deserializes a single property.
     */
    private void deserializeProperty(DeserializationState state, String propertyName,
            JsonParser parser, DeserializationContext ctxt) {
        EClass eClass = state.getResolvedEClass();
        Map<String, DeserializationEntry> entries = buildDeserializationEntries(eClass);

        DeserializationEntry entry = entries.get(propertyName);
        if (entry != null) {
            entry.deserialize(state, parser, ctxt);
        } else {
            // Unknown property - skip it
            LOGGER.fine("Unknown property: " + propertyName);
            parser.skipChildren();
        }
    }

    /**
     * Builds deserialization entries for the given EClass.
     *
     * @param eClass the EClass to build entries for
     * @return map of property name to deserialization entry
     */
    private Map<String, DeserializationEntry> buildDeserializationEntries(EClass eClass) {
        Map<String, DeserializationEntry> entries = new HashMap<>();

        EffectiveClassConfig classConfig = config.getClassConfig(eClass);

        // Add type entry (with supertype config for STRUCTURED format validation)
        if (classConfig.getTypeConfig() != null && classConfig.getTypeConfig().isEnabled()) {
            TypeDeserializationEntry typeEntry = new TypeDeserializationEntry(
                    classConfig.getTypeConfig(), config.getTypeDiscriminatorService(),
                    classConfig.getSuperTypeConfig());
            entries.put(typeEntry.getKey(), typeEntry);
        }

        // Add ID entry
        if (classConfig.getIdConfig() != null && classConfig.getIdConfig().isEnabled()) {
            IdDeserializationEntry idEntry = new IdDeserializationEntry(classConfig.getIdConfig(), eClass);
            entries.put(idEntry.getKey(), idEntry);
        }

        // Add supertype entry (for validation if enabled, otherwise just parses and ignores)
        if (classConfig.getSuperTypeConfig() != null) {
            SuperTypeDeserializationEntry superTypeEntry = new SuperTypeDeserializationEntry(
                    classConfig.getSuperTypeConfig());
            entries.put(superTypeEntry.getKey(), superTypeEntry);
        }

        // Add feature entries
        for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
            // Skip derived, transient, and non-changeable features
            if (feature.isDerived() || feature.isTransient() || !feature.isChangeable()) {
                continue;
            }

            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(feature);

            // Skip features marked as not serializable
            if (!featureConfig.isSerialize()) {
                continue;
            }

            DeserializationEntry entry;
            if (feature instanceof EAttribute) {
                entry = new AttributeDeserializationEntry(featureConfig, (EAttribute) feature);
            } else if (feature instanceof EReference) {
                entry = new ReferenceDeserializationEntry(featureConfig, (EReference) feature, DEFAULT_REF_KEY);
            } else {
                continue;
            }

            entries.put(entry.getKey(), entry);
        }

        return entries;
    }

    // ========================================================================
    // FeaturePath-based deserialization
    // ========================================================================

    /**
     * Gets the discriminator path from the EClass's type configuration.
     *
     * @param eClass the EClass to check (may be null)
     * @return the discriminator path, or null if not configured
     */
    private String getDiscriminatorPath(EClass eClass) {
        if (eClass == null) {
            return null;
        }
        EffectiveClassConfig classConfig = config.getClassConfig(eClass);
        if (classConfig == null || classConfig.getTypeConfig() == null) {
            return null;
        }
        return classConfig.getTypeConfig().getDiscriminatorPath();
    }

    /**
     * Deserializes an EObject using featurePath-based type resolution.
     * <p>
     * This method is used when the type information is embedded in the content
     * at a specific path (e.g., "info.profileName") rather than in a dedicated
     * "_type" field.
     * </p>
     *
     * @param parser the JSON parser positioned at START_OBJECT
     * @param ctxt the deserialization context
     * @param state the deserialization state
     * @param hintEClass the hint EClass (typically an abstract base class)
     * @param discriminatorPath the path to the discriminator value
     * @return the deserialized EObject, or null if deserialization failed
     */
    private EObject deserializeWithFeaturePath(
            JsonParser parser,
            DeserializationContext ctxt,
            DeserializationState state,
            EClass hintEClass,
            String discriminatorPath) {

        LOGGER.fine("Using featurePath-based type resolution: " + discriminatorPath);

        // Create resolver and scan the content
        FeaturePathTypeResolver resolver = new FeaturePathTypeResolver(
                discriminatorPath, config.getTypeDiscriminatorService());
        resolver.scan(parser, ctxt);

        // Get the resolved EClass
        EClass resolvedEClass = resolver.getResolvedEClass();
        if (resolvedEClass == null) {
            // Fall back to hint class if available and not abstract
            if (hintEClass != null && !hintEClass.isAbstract()) {
                LOGGER.fine("FeaturePath resolution failed, using hint class: " + hintEClass.getName());
                resolvedEClass = hintEClass;
            } else {
                LOGGER.severe("Cannot deserialize: featurePath resolution failed for '" +
                        discriminatorPath + "' and no concrete fallback available");
                return null;
            }
        }

        state.setResolvedEClass(resolvedEClass);

        // Get the buffered parser for actual deserialization
        JsonParser bufferedParser = resolver.getBufferedParser(ctxt, parser);
        if (bufferedParser == null) {
            LOGGER.severe("No buffered content available for deserialization");
            return null;
        }

        // Deserialize using the buffered parser
        return deserializeFromBufferedParser(bufferedParser, ctxt, state);
    }

    /**
     * Deserializes an EObject from a buffered parser.
     * <p>
     * The parser should be positioned at START_OBJECT. This method processes
     * all properties and returns the fully deserialized EObject.
     * </p>
     *
     * @param parser the buffered JSON parser
     * @param ctxt the deserialization context
     * @param state the deserialization state (with EClass already resolved)
     * @return the deserialized EObject
     */
    private EObject deserializeFromBufferedParser(
            JsonParser parser,
            DeserializationContext ctxt,
            DeserializationState state) {

        EClass resolvedEClass = state.getResolvedEClass();
        if (resolvedEClass == null) {
            LOGGER.severe("No resolved EClass in deserialization state");
            return null;
        }

        // Create the EObject
        EObject eObject = state.createEObject();
        if (eObject == null) {
            LOGGER.severe("Failed to create EObject for: " + resolvedEClass.getName());
            return null;
        }

        // Parser should be at START_OBJECT, move into the object
        JsonToken token = parser.currentToken();
        if (token != JsonToken.START_OBJECT) {
            LOGGER.warning("Expected START_OBJECT in buffered parser, got: " + token);
            return eObject;
        }

        // Read all properties
        while ((token = parser.nextToken()) != JsonToken.END_OBJECT && token != null) {
            if (token == JsonToken.PROPERTY_NAME) {
                String propertyName = parser.currentName();
                parser.nextToken(); // Move to value
                deserializeProperty(state, propertyName, parser, ctxt);
            }
        }

        return eObject;
    }
}
