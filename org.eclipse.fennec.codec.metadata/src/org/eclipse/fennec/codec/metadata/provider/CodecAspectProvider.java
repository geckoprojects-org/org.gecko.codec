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
package org.eclipse.fennec.codec.metadata.provider;

import static org.eclipse.fennec.codec.metadata.provider.CodecAnnotationConstants.*;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.CodecFactory;
import org.eclipse.fennec.codec.metadata.model.codec.FallbackStrategy;
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.InlineTypeMapping;
import org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.IdStrategy;
import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.SuperTypeSelection;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.eclipse.fennec.model.metadata.api.AspectProvider;

/**
 * AspectProvider implementation for codec serialization metadata.
 * <p>
 * Parses EAnnotations from EMF model elements and creates codec-specific
 * aspects (ClassCodecAspect, FeatureCodecAspect, ReferenceCodecAspect)
 * with serialization configuration.
 * </p>
 * <p>
 * All annotations use the unified source {@code http://eclipse.org/fennec/codec}
 * with configuration specified through detail key-value pairs.
 * </p>
 *
 * @author Mark Hoffmann
 * @since 2025-12-09
 */
public class CodecAspectProvider implements AspectProvider {

    /** Aspect type identifier for codec aspects. */
    public static final String ASPECT_TYPE_ID = "codec";

    private final CodecFactory factory = CodecFactory.eINSTANCE;

    @Override
    public String getAspectTypeId() {
        return ASPECT_TYPE_ID;
    }

    @Override
    public ClassAspect buildClassAspect(EClass eClass) {
        ClassCodecAspect aspect = factory.createClassCodecAspect();
        aspect.setTypeId(ASPECT_TYPE_ID);

        EAnnotation codecAnnotation = eClass.getEAnnotation(CODEC_SOURCE);
        if (codecAnnotation != null) {
            parseClassAnnotation(aspect, codecAnnotation);
        }

        return aspect;
    }

    @Override
    public FeatureAspect buildFeatureAspect(EStructuralFeature feature) {
        if (feature instanceof EReference) {
            return buildReferenceAspect((EReference) feature);
        } else if (feature instanceof EAttribute) {
            return buildAttributeAspect((EAttribute) feature);
        }
        return null;
    }

    @Override
    public FeatureAspect buildAttributeAspect(EAttribute attribute) {
        FeatureCodecAspect aspect = factory.createFeatureCodecAspect();
        aspect.setTypeId(ASPECT_TYPE_ID);

        populateFeatureAspect(aspect, attribute);

        return aspect;
    }

    @Override
    public FeatureAspect buildReferenceAspect(EReference reference) {
        ReferenceCodecAspect aspect = factory.createReferenceCodecAspect();
        aspect.setTypeId(ASPECT_TYPE_ID);

        populateFeatureAspect(aspect, reference);

        // Reference-specific parsing
        EAnnotation codecAnnotation = reference.getEAnnotation(CODEC_SOURCE);
        if (codecAnnotation != null) {
            Map<String, String> details = codecAnnotation.getDetails().map();

            // Parse type config (for polymorphic references)
            if (hasTypeConfig(details)) {
                aspect.setTypeConfig(buildTypeConfig(details));
            }

            // Parse reference config
            if (hasReferenceConfig(details)) {
                aspect.setReferenceConfig(buildReferenceConfig(details));
            }

            // Parse expand flag
            String expandStr = details.get(KEY_EXPAND);
            if (expandStr != null) {
                aspect.setExpand(Boolean.parseBoolean(expandStr));
            }

            // Parse inline type mappings (inlineMapping.{value}={EClass URI})
            parseInlineTypeMappings(aspect, details);

            // Parse fallback strategy for inline mappings
            String fallbackStrategyStr = details.get(KEY_FALLBACK_STRATEGY);
            if (fallbackStrategyStr != null) {
                aspect.setFallbackStrategy(parseFallbackStrategy(fallbackStrategyStr));
            }

            // Parse fallback EClass URI
            String fallbackEClass = details.get(KEY_FALLBACK_ECLASS);
            if (fallbackEClass != null) {
                aspect.setFallbackEClass(fallbackEClass);
            }
        }

        return aspect;
    }

    // ========================================================================
    // Class Annotation Parsing
    // ========================================================================

    /**
     * Parses codec annotation and populates the class aspect.
     */
    private void parseClassAnnotation(ClassCodecAspect aspect, EAnnotation annotation) {
        Map<String, String> details = annotation.getDetails().map();

        // Parse ID configuration (id* keys)
        if (hasIdConfig(details)) {
            aspect.setIdConfig(buildIdConfig(details));
        }

        // Parse type configuration (type* keys)
        if (hasTypeConfig(details)) {
            aspect.setTypeConfig(buildTypeConfig(details));
        }

        // Parse supertype configuration (superType* keys)
        if (hasSuperTypeConfig(details)) {
            aspect.setSuperTypeConfig(buildSuperTypeConfig(details));
        }

        // Parse inherit flag
        String inherit = details.get(KEY_INHERIT);
        if (inherit != null) {
            aspect.setInheritFromParent(Boolean.parseBoolean(inherit));
        }

        // Parse type mapping discriminator (for concrete classes in MAPPED strategy)
        String discriminator = details.get(KEY_TYPE_DISCRIMINATOR);
        if (discriminator != null) {
            aspect.setDiscriminatorValue(discriminator);
        }
    }

    // ========================================================================
    // Feature Annotation Parsing
    // ========================================================================

    /**
     * Populates common feature aspect properties from annotation.
     * <p>
     * Note: effectiveKey is only set if there's an explicit "key" annotation.
     * If not set, the ConfigurationMerger will use ExtendedMetaData name (if enabled)
     * or fall back to the feature name.
     * </p>
     */
    private void populateFeatureAspect(FeatureCodecAspect aspect, EStructuralFeature feature) {
        // Default: serialize = true
        aspect.setSerialize(true);

        EAnnotation codecAnnotation = feature.getEAnnotation(CODEC_SOURCE);
        if (codecAnnotation != null) {
            Map<String, String> details = codecAnnotation.getDetails().map();

            // Parse explicit key override - only set effectiveKey if explicitly specified
            String explicitKey = details.get(KEY_KEY);
            if (explicitKey != null && !explicitKey.isEmpty()) {
                aspect.setEffectiveKey(explicitKey);
            }

            // Parse transient flag (inverse of serialize)
            String transientStr = details.get(KEY_TRANSIENT);
            if (transientStr != null && Boolean.parseBoolean(transientStr)) {
                aspect.setSerialize(false);
            }

            // Parse explicit serialize flag (overrides transient if both present)
            String serializeStr = details.get(KEY_SERIALIZE);
            if (serializeStr != null) {
                aspect.setSerialize(Boolean.parseBoolean(serializeStr));
            }

            // Parse serializeNull
            String serializeNullStr = details.get(KEY_SERIALIZE_NULL);
            if (serializeNullStr != null) {
                aspect.setSerializeNull(Boolean.parseBoolean(serializeNullStr));
            }

            // Parse serializeEmpty
            String serializeEmptyStr = details.get(KEY_SERIALIZE_EMPTY);
            if (serializeEmptyStr != null) {
                aspect.setSerializeEmpty(Boolean.parseBoolean(serializeEmptyStr));
            }

            // Parse serializeDefaults
            String serializeDefaultsStr = details.get(KEY_SERIALIZE_DEFAULTS);
            if (serializeDefaultsStr != null) {
                aspect.setSerializeDefaults(Boolean.parseBoolean(serializeDefaultsStr));
            }

            // Parse value writer/reader names
            String writerName = details.get(KEY_VALUE_WRITER_NAME);
            if (writerName != null) {
                aspect.setValueWriterName(writerName);
            }

            String readerName = details.get(KEY_VALUE_READER_NAME);
            if (readerName != null) {
                aspect.setValueReaderName(readerName);
            }

            // Parse enum serialization strategy - only valid for EAttributes per spec
            // See annotation-scope-reference.md: enumSerialization is ❌ on EReference, ✅ on EAttribute
            if (feature instanceof EAttribute) {
                String enumSerStr = details.get(KEY_ENUM_SERIALIZATION);
                if (enumSerStr != null) {
                    aspect.setEnumSerialization(parseEnumSerializationStrategy(enumSerStr));
                }
            }
        }
    }

    // ========================================================================
    // Configuration Builders
    // ========================================================================

    /**
     * Builds IdSerializationConfig from annotation details.
     */
    private IdSerializationConfig buildIdConfig(Map<String, String> details) {
        IdSerializationConfig config = factory.createIdSerializationConfig();

        // Strategy
        String strategyStr = details.get(KEY_ID_STRATEGY);
        if (strategyStr != null) {
            config.setStrategy(parseIdStrategy(strategyStr));
        }

        // Key (property name)
        String key = details.get(KEY_ID_KEY);
        if (key != null) {
            config.setIdKey(key);
        }

        // Separator for combined IDs
        String separator = details.get(KEY_ID_SEPARATOR);
        if (separator != null) {
            config.setSeparator(separator);
        }

        // ID features for combined strategy
        String idFeatures = details.get(KEY_ID_FEATURES);
        if (idFeatures != null) {
            String[] features = idFeatures.split(",");
            config.getIdFeatures().addAll(Arrays.asList(features));
        }

        // Custom reader/writer
        String readerName = details.get(KEY_ID_VALUE_READER_NAME);
        if (readerName != null) {
            config.setIdValueReaderName(readerName);
        }

        String writerName = details.get(KEY_ID_VALUE_WRITER_NAME);
        if (writerName != null) {
            config.setIdValueWriterName(writerName);
        }

        // Format (PLAIN/STRUCTURED)
        String formatStr = details.get(KEY_ID_FORMAT);
        if (formatStr != null) {
            config.setFormat(parseSerializationFormat(formatStr));
        }

        // Key mode (ID_ONLY, BOTH, FEATURE_ONLY)
        String keyModeStr = details.get(KEY_ID_KEY_MODE);
        if (keyModeStr != null) {
            config.setKeyMode(parseIdKeyMode(keyModeStr));
        }

        // On top (ID before type)
        String onTopStr = details.get(KEY_ID_ON_TOP);
        if (onTopStr != null) {
            config.setOnTop(Boolean.parseBoolean(onTopStr));
        }

        // Serialize separator
        String serializeSeparatorStr = details.get(KEY_ID_SERIALIZE_SEPARATOR);
        if (serializeSeparatorStr != null) {
            config.setSerializeSeparator(Boolean.parseBoolean(serializeSeparatorStr));
        }

        // Separator key
        String separatorKey = details.get(KEY_ID_SEPARATOR_KEY);
        if (separatorKey != null) {
            config.setSeparatorKey(separatorKey);
        }

        // Value key (inner key in STRUCTURED format)
        String valueKey = details.get(KEY_ID_VALUE_KEY);
        if (valueKey != null) {
            config.setValueKey(valueKey);
        }

        return config;
    }

    /**
     * Builds TypeSerializationConfig from annotation details.
     * <p>
     * Note: Discriminator-based type resolution is now orthogonal to type strategy.
     * The discriminatorPath specifies an additional resolution mechanism that works
     * alongside the primary type strategy, not as a replacement.
     * </p>
     */
    private TypeSerializationConfig buildTypeConfig(Map<String, String> details) {
        TypeSerializationConfig config = factory.createTypeSerializationConfig();

        // Strategy - explicit strategy only, discriminator is orthogonal
        String strategyStr = details.get(KEY_TYPE_STRATEGY);
        if (strategyStr != null) {
            config.setStrategy(parseTypeStrategy(strategyStr));
        }

        // Include
        String includeStr = details.get(KEY_TYPE_INCLUDE);
        if (includeStr != null) {
            config.setInclude(Boolean.parseBoolean(includeStr));
        }

        // Type key
        String typeKey = details.get(KEY_TYPE_KEY);
        if (typeKey != null) {
            config.setTypeKey(typeKey);
        }

        // Discriminator path - orthogonal to strategy, provides additional resolution
        String discriminatorPath = details.get(KEY_TYPE_DISCRIMINATOR_PATH);
        if (discriminatorPath != null) {
            config.setDiscriminatorPath(discriminatorPath);
        }

        // Map ID for discriminator registry lookup
        String mapId = details.get(KEY_TYPE_MAP_ID);
        if (mapId != null) {
            config.setMapId(mapId);
        }

        // Format (PLAIN/STRUCTURED)
        String formatStr = details.get(KEY_TYPE_FORMAT);
        if (formatStr != null) {
            config.setFormat(parseSerializationFormat(formatStr));
        }

        // Schema key
        String schemaKey = details.get(KEY_TYPE_SCHEMA_KEY);
        if (schemaKey != null) {
            config.setSchemaKey(schemaKey);
        }

        // Name key
        String nameKey = details.get(KEY_TYPE_NAME_KEY);
        if (nameKey != null) {
            config.setNameKey(nameKey);
        }

        // Fallback strategy for discriminator resolution
        String fallbackStrategyStr = details.get(KEY_FALLBACK_STRATEGY);
        if (fallbackStrategyStr != null) {
            config.setFallbackStrategy(parseFallbackStrategy(fallbackStrategyStr));
        }

        // Fallback EClass URI
        String fallbackEClass = details.get(KEY_FALLBACK_ECLASS);
        if (fallbackEClass != null) {
            config.setFallbackEClass(fallbackEClass);
        }

        return config;
    }

    /**
     * Builds SuperTypeSerializationConfig from annotation details.
     */
    private SuperTypeSerializationConfig buildSuperTypeConfig(Map<String, String> details) {
        SuperTypeSerializationConfig config = factory.createSuperTypeSerializationConfig();

        // Enabled
        String serializeStr = details.get(KEY_SUPERTYPE_SERIALIZE);
        if (serializeStr != null) {
            config.setEnabled(Boolean.parseBoolean(serializeStr));
        }

        // Key
        String key = details.get(KEY_SUPERTYPE_KEY);
        if (key != null) {
            config.setSuperTypeKey(key);
        }

        // Strategy/Selection
        String strategyStr = details.get(KEY_SUPERTYPE_STRATEGY);
        if (strategyStr != null) {
            config.setSelection(parseSuperTypeSelection(strategyStr));
        }

        // As array
        String asArrayStr = details.get(KEY_SUPERTYPE_AS_ARRAY);
        if (asArrayStr != null) {
            config.setAsArray(Boolean.parseBoolean(asArrayStr));
        }

        // Separator
        String separator = details.get(KEY_SUPERTYPE_SEPARATOR);
        if (separator != null) {
            config.setSeparator(separator);
        }

        // Format (PLAIN/STRUCTURED)
        String formatStr = details.get(KEY_SUPERTYPE_FORMAT);
        if (formatStr != null) {
            config.setFormat(parseSerializationFormat(formatStr));
        }

        // Schema key
        String schemaKey = details.get(KEY_SUPERTYPE_SCHEMA_KEY);
        if (schemaKey != null) {
            config.setSchemaKey(schemaKey);
        }

        // Name key
        String nameKey = details.get(KEY_SUPERTYPE_NAME_KEY);
        if (nameKey != null) {
            config.setNameKey(nameKey);
        }

        return config;
    }

    /**
     * Builds ReferenceSerializationConfig from annotation details.
     */
    private ReferenceSerializationConfig buildReferenceConfig(Map<String, String> details) {
        ReferenceSerializationConfig config = factory.createReferenceSerializationConfig();

        // Format (PLAIN/STRUCTURED)
        String formatStr = details.get(KEY_REF_FORMAT);
        if (formatStr != null) {
            config.setFormat(parseSerializationFormat(formatStr));
        }

        // Ref key
        String refKey = details.get(KEY_REF_KEY);
        if (refKey != null) {
            config.setRefKey(refKey);
        }

        // Type key
        String typeKey = details.get(KEY_REF_TYPE_KEY);
        if (typeKey != null) {
            config.setTypeKey(typeKey);
        }

        // Expand
        String expandStr = details.get(KEY_EXPAND);
        if (expandStr != null) {
            config.setExpand(Boolean.parseBoolean(expandStr));
        }

        return config;
    }

    /**
     * Parses inline type mappings from annotation details.
     * <p>
     * Looks for keys matching "inlineMapping.{discriminatorValue}" and creates
     * InlineTypeMapping objects for each.
     * </p>
     */
    private void parseInlineTypeMappings(ReferenceCodecAspect aspect, Map<String, String> details) {
        for (Map.Entry<String, String> entry : details.entrySet()) {
            String discriminatorValue = extractInlineMappingValue(entry.getKey());
            if (discriminatorValue != null) {
                InlineTypeMapping mapping = factory.createInlineTypeMapping();
                mapping.setDiscriminatorValue(discriminatorValue);
                mapping.setTargetClass(entry.getValue());
                aspect.getInlineTypeMappings().add(mapping);
            }
        }
    }

    // ========================================================================
    // Config Detection Helpers
    // ========================================================================

    /**
     * Checks if the details map contains any ID configuration keys.
     */
    private boolean hasIdConfig(Map<String, String> details) {
        return details.containsKey(KEY_ID_STRATEGY)
                || details.containsKey(KEY_ID_KEY)
                || details.containsKey(KEY_ID_SEPARATOR)
                || details.containsKey(KEY_ID_FEATURES)
                || details.containsKey(KEY_ID_VALUE_READER_NAME)
                || details.containsKey(KEY_ID_VALUE_WRITER_NAME)
                || details.containsKey(KEY_ID_FORMAT)
                || details.containsKey(KEY_ID_KEY_MODE)
                || details.containsKey(KEY_ID_ON_TOP)
                || details.containsKey(KEY_ID_SERIALIZE_SEPARATOR)
                || details.containsKey(KEY_ID_SEPARATOR_KEY)
                || details.containsKey(KEY_ID_VALUE_KEY);
    }

    /**
     * Checks if the details map contains any type configuration keys.
     */
    private boolean hasTypeConfig(Map<String, String> details) {
        return details.containsKey(KEY_TYPE_STRATEGY)
                || details.containsKey(KEY_TYPE_KEY)
                || details.containsKey(KEY_TYPE_INCLUDE)
                || details.containsKey(KEY_TYPE_MAP_ID)
                || details.containsKey(KEY_TYPE_DISCRIMINATOR_PATH)
                || details.containsKey(KEY_TYPE_FORMAT)
                || details.containsKey(KEY_TYPE_SCHEMA_KEY)
                || details.containsKey(KEY_TYPE_NAME_KEY)
                || details.containsKey(KEY_FALLBACK_STRATEGY)
                || details.containsKey(KEY_FALLBACK_ECLASS);
    }

    /**
     * Checks if the details map contains any supertype configuration keys.
     */
    private boolean hasSuperTypeConfig(Map<String, String> details) {
        return details.containsKey(KEY_SUPERTYPE_SERIALIZE)
                || details.containsKey(KEY_SUPERTYPE_KEY)
                || details.containsKey(KEY_SUPERTYPE_STRATEGY)
                || details.containsKey(KEY_SUPERTYPE_AS_ARRAY)
                || details.containsKey(KEY_SUPERTYPE_FORMAT)
                || details.containsKey(KEY_SUPERTYPE_SCHEMA_KEY)
                || details.containsKey(KEY_SUPERTYPE_NAME_KEY);
    }

    /**
     * Checks if the details map contains any reference configuration keys.
     */
    private boolean hasReferenceConfig(Map<String, String> details) {
        return details.containsKey(KEY_REF_FORMAT)
                || details.containsKey(KEY_REF_KEY)
                || details.containsKey(KEY_REF_TYPE_KEY);
    }

    // ========================================================================
    // Strategy Parsers
    // ========================================================================

    /**
     * Parses IdStrategy from string value.
     */
    private IdStrategy parseIdStrategy(String value) {
        if (value == null) {
            return IdStrategy.ID_FIELD;
        }
        switch (value.toUpperCase()) {
            case "ID_FIELD":
                return IdStrategy.ID_FIELD;
            case "COMBINED":
                return IdStrategy.COMBINED;
            case "NONE":
                return IdStrategy.NONE;
            default:
                return IdStrategy.ID_FIELD;
        }
    }

    /**
     * Parses TypeStrategy from string value.
     * <p>
     * Note: MAPPED strategy has been removed. Discriminator-based type resolution
     * is now orthogonal to type strategy and configured via discriminatorPath.
     * </p>
     */
    private TypeStrategy parseTypeStrategy(String value) {
        if (value == null) {
            return TypeStrategy.URI;
        }
        switch (value.toUpperCase()) {
            case "NAME":
                return TypeStrategy.NAME;
            case "CLASS":
                return TypeStrategy.CLASS;
            case "URI":
                return TypeStrategy.URI;
            case "SCHEMA_AND_TYPE":
                return TypeStrategy.SCHEMA_AND_TYPE;
            case "NUMERIC":
                return TypeStrategy.NUMERIC;
            default:
                // Log warning for unknown strategy (including deprecated MAPPED)
                return TypeStrategy.URI;
        }
    }

    /**
     * Parses SuperTypeSelection from string value.
     */
    private SuperTypeSelection parseSuperTypeSelection(String value) {
        if (value == null) {
            return SuperTypeSelection.ALL;
        }
        switch (value.toUpperCase()) {
            case "ALL":
                return SuperTypeSelection.ALL;
            case "ALL_EMF":
                return SuperTypeSelection.ALL_EMF;
            case "SINGLE":
                return SuperTypeSelection.SINGLE;
            case "NONE":
                return SuperTypeSelection.NONE;
            default:
                return SuperTypeSelection.ALL;
        }
    }

    /**
     * Parses SerializationFormat from string value.
     */
    private SerializationFormat parseSerializationFormat(String value) {
        if (value == null) {
            return SerializationFormat.PLAIN;
        }
        switch (value.toUpperCase()) {
            case "PLAIN":
                return SerializationFormat.PLAIN;
            case "STRUCTURED":
                return SerializationFormat.STRUCTURED;
            default:
                return SerializationFormat.PLAIN;
        }
    }

    /**
     * Parses IdKeyMode from string value.
     */
    private IdKeyMode parseIdKeyMode(String value) {
        if (value == null) {
            return IdKeyMode.ID_ONLY;
        }
        switch (value.toUpperCase()) {
            case "ID_ONLY":
                return IdKeyMode.ID_ONLY;
            case "BOTH":
                return IdKeyMode.BOTH;
            case "FEATURE_ONLY":
                return IdKeyMode.FEATURE_ONLY;
            default:
                return IdKeyMode.ID_ONLY;
        }
    }

    /**
     * Parses EnumSerializationStrategy from string value.
     */
    private EnumSerializationStrategy parseEnumSerializationStrategy(String value) {
        if (value == null) {
            return EnumSerializationStrategy.LITERAL;
        }
        switch (value.toUpperCase()) {
            case "LITERAL":
                return EnumSerializationStrategy.LITERAL;
            case "VALUE":
                return EnumSerializationStrategy.VALUE;
            case "NAME":
                return EnumSerializationStrategy.NAME;
            default:
                return EnumSerializationStrategy.LITERAL;
        }
    }

    /**
     * Parses FallbackStrategy from string value.
     */
    private FallbackStrategy parseFallbackStrategy(String value) {
        if (value == null) {
            return FallbackStrategy.FALLBACK;
        }
        switch (value.toUpperCase()) {
            case "FALLBACK":
                return FallbackStrategy.FALLBACK;
            case "ERROR":
                return FallbackStrategy.ERROR;
            case "SKIP":
                return FallbackStrategy.SKIP;
            default:
                return FallbackStrategy.FALLBACK;
        }
    }
}
