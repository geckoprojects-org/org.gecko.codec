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
import org.eclipse.fennec.codec.v2.config.effective.EffectiveTypeConfig;
import org.eclipse.fennec.codec.v2.context.ContextHelper;

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
    private static final String DEFAULT_REF_KEY = "$ref";

    /** Default type key */
    private static final String DEFAULT_TYPE_KEY = "_type";

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

        // Create deserialization state
        DeserializationState state = new DeserializationState(null); // TODO: get resource from context

        // Get or create shared unresolved references list from context
        @SuppressWarnings("unchecked")
        java.util.List<DeserializationState.UnresolvedReference> unresolvedRefs =
                (java.util.List<DeserializationState.UnresolvedReference>) ctxt.getAttribute(ContextHelper.UNRESOLVED_REFERENCES);
        if (unresolvedRefs != null) {
            // Use shared list for collecting unresolved references
            state.setSharedUnresolvedReferences(unresolvedRefs);
        }

        // Get the expected type hint (must be EClass if set)
        // This is set by:
        // - CodecResource (from CODEC_ROOT_OBJECT option, resolved before deserialization)
        // - ReferenceDeserializationEntry (from EReference.eType for nested objects)
        EClass hintEClass = ContextHelper.getExpectedType(ctxt);

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

        // Read properties
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String propertyName = parser.currentName();
            parser.nextToken(); // Move to value

            // Check if this is the type property - ALWAYS process it when present
            if (isTypeKey(propertyName)) {
                resolvedEClass = resolveType(parser, state, hintEClass);
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
     * Resolves the EClass from the current parser position.
     * <p>
     * Uses the configured type strategy to interpret the type value.
     * The hint EClass is used as context for MAPPED type resolution -
     * it provides the mapId for discriminator lookup.
     * </p>
     *
     * @param parser the JSON parser positioned at the type value
     * @param state the deserialization state
     * @param hintEClass optional hint EClass for MAPPED context (may be null)
     * @return the resolved EClass, or null if resolution fails
     */
    private EClass resolveType(JsonParser parser, DeserializationState state, EClass hintEClass) {
        // Build effective type config from module defaults
        // We use module config here since we don't know the EClass yet
        EffectiveTypeConfig typeConfig = EffectiveTypeConfig.builder()
                .enabled(true)
                .typeKey(DEFAULT_TYPE_KEY)
                .strategy(config.getGlobalTypeStrategy())  // Use global strategy for deserialization
                .build();

        TypeDeserializationEntry typeEntry = new TypeDeserializationEntry(
                typeConfig, config.getTypeDiscriminatorService());

        // Pass the hint to the type entry for MAPPED context
        typeEntry.deserializeWithHint(state, parser, null, hintEClass);

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
            case START_ARRAY:
                // Skip complex values - they'll need to be re-parsed
                parser.skipChildren();
                return null;
            default:
                return null;
        }
    }

    /**
     * Processes deferred properties after the type is resolved.
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
                // Set the value directly on the EObject
                setDeferredValue(state, propertyName, value);
            }
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

        // Add type entry
        if (classConfig.getTypeConfig() != null && classConfig.getTypeConfig().isEnabled()) {
            TypeDeserializationEntry typeEntry = new TypeDeserializationEntry(
                    classConfig.getTypeConfig(), config.getTypeDiscriminatorService());
            entries.put(typeEntry.getKey(), typeEntry);
        }

        // Add ID entry
        if (classConfig.getIdConfig() != null && classConfig.getIdConfig().isEnabled()) {
            IdDeserializationEntry idEntry = new IdDeserializationEntry(classConfig.getIdConfig(), eClass);
            entries.put(idEntry.getKey(), idEntry);
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
