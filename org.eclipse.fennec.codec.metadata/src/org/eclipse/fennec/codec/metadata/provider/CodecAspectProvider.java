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
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.IdStrategy;
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

        // Reference-specific: Parse type config (for polymorphic references)
        EAnnotation codecAnnotation = reference.getEAnnotation(CODEC_SOURCE);
        if (codecAnnotation != null) {
            Map<String, String> details = codecAnnotation.getDetails().map();
            if (hasTypeConfig(details)) {
                aspect.setTypeConfig(buildTypeConfig(details));
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

            // Parse transient flag
            String transientStr = details.get(KEY_TRANSIENT);
            if (transientStr != null && Boolean.parseBoolean(transientStr)) {
                aspect.setSerialize(false);
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

        return config;
    }

    /**
     * Builds TypeSerializationConfig from annotation details.
     */
    private TypeSerializationConfig buildTypeConfig(Map<String, String> details) {
        TypeSerializationConfig config = factory.createTypeSerializationConfig();

        // Strategy - if typeMapId is present without explicit strategy, imply MAPPED
        String strategyStr = details.get(KEY_TYPE_STRATEGY);
        String mapId = details.get(KEY_TYPE_MAP_ID);

        if (strategyStr != null) {
            config.setStrategy(parseTypeStrategy(strategyStr));
        } else if (mapId != null) {
            // Presence of typeMapId implies MAPPED strategy
            config.setStrategy(TypeStrategy.MAPPED);
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

        // Discriminator path (for MAPPED strategy)
        String discriminatorPath = details.get(KEY_TYPE_DISCRIMINATOR_PATH);
        if (discriminatorPath != null) {
            config.setDiscriminatorPath(discriminatorPath);
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

        return config;
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
                || details.containsKey(KEY_ID_VALUE_WRITER_NAME);
    }

    /**
     * Checks if the details map contains any type configuration keys.
     */
    private boolean hasTypeConfig(Map<String, String> details) {
        return details.containsKey(KEY_TYPE_STRATEGY)
                || details.containsKey(KEY_TYPE_KEY)
                || details.containsKey(KEY_TYPE_INCLUDE)
                || details.containsKey(KEY_TYPE_MAP_ID)
                || details.containsKey(KEY_TYPE_DISCRIMINATOR_PATH);
    }

    /**
     * Checks if the details map contains any supertype configuration keys.
     */
    private boolean hasSuperTypeConfig(Map<String, String> details) {
        return details.containsKey(KEY_SUPERTYPE_SERIALIZE)
                || details.containsKey(KEY_SUPERTYPE_KEY)
                || details.containsKey(KEY_SUPERTYPE_STRATEGY)
                || details.containsKey(KEY_SUPERTYPE_AS_ARRAY);
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
            case "MAPPED":
                return TypeStrategy.MAPPED;
            case "STRUCTURED":
                return TypeStrategy.STRUCTURED;
            case "SCHEMA_AND_TYPE":
                return TypeStrategy.SCHEMA_AND_TYPE;
            case "NUMERIC":
                return TypeStrategy.NUMERIC;
            default:
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
}
