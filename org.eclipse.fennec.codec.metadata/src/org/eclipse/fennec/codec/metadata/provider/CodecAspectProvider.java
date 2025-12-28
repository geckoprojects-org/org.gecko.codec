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
import org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.model.metadata.ClassAspect;
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

        // Parse codec.id annotation
        EAnnotation idAnnotation = eClass.getEAnnotation(CODEC_ID);
        if (idAnnotation != null) {
            aspect.setIdConfig(buildIdConfig(idAnnotation));
        }

        // Parse codec.type annotation (basic type config)
        EAnnotation typeAnnotation = eClass.getEAnnotation(CODEC_TYPE);
        if (typeAnnotation != null) {
            aspect.setTypeConfig(buildTypeConfig(typeAnnotation));
        }

        // Parse mapId-scoped type annotations (codec.type.{mapId})
        // The presence of codec.type.{mapId} implies MAPPED strategy
        for (EAnnotation ann : eClass.getEAnnotations()) {
            if (isTypeMapAnnotation(ann.getSource())) {
                // Build type config - mapId-scoped annotations imply MAPPED strategy
                if (aspect.getTypeConfig() == null) {
                    aspect.setTypeConfig(buildMappedTypeConfig(ann));
                }

                // Extract discriminator value
                String discriminator = getDetail(ann, KEY_TYPE_DISCRIMINATOR);
                if (discriminator != null && aspect.getDiscriminatorValue() == null) {
                    aspect.setDiscriminatorValue(discriminator);
                }
            }
        }

        // Parse codec.supertype annotation
        EAnnotation superTypeAnnotation = eClass.getEAnnotation(CODEC_SUPERTYPE);
        if (superTypeAnnotation != null) {
            aspect.setSuperTypeConfig(buildSuperTypeConfig(superTypeAnnotation));
        }

        // Parse codec.inherit annotation
        EAnnotation inheritAnnotation = eClass.getEAnnotation(CODEC_INHERIT);
        aspect.setInheritFromParent(inheritAnnotation != null);

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

        // Reference-specific: Parse codec.type annotation on reference
        EAnnotation typeAnnotation = reference.getEAnnotation(CODEC_TYPE);
        if (typeAnnotation != null) {
            aspect.setTypeConfig(buildTypeConfig(typeAnnotation));
        }

        // Parse expand setting from reference if present
        // (can be set via future annotation support)

        return aspect;
    }

    // ========================================================================
    // Private Helper Methods
    // ========================================================================

    /**
     * Populates common feature aspect properties from annotations.
     */
    private void populateFeatureAspect(FeatureCodecAspect aspect, EStructuralFeature feature) {
        // Default: serialize = true
        aspect.setSerialize(true);
        aspect.setEffectiveKey(feature.getName());

        // Parse codec.transient annotation
        EAnnotation transientAnnotation = feature.getEAnnotation(CODEC_TRANSIENT);
        if (transientAnnotation != null) {
            aspect.setSerialize(false);
        }

        // Parse codec.value.writer.name annotation
        EAnnotation writerAnnotation = feature.getEAnnotation(CODEC_VALUE_WRITER_NAME);
        if (writerAnnotation != null) {
            String writerName = getDetail(writerAnnotation, "name");
            if (writerName != null) {
                aspect.setValueWriterName(writerName);
            }
        }

        // Parse codec.value.reader.name annotation
        EAnnotation readerAnnotation = feature.getEAnnotation(CODEC_VALUE_READER_NAME);
        if (readerAnnotation != null) {
            String readerName = getDetail(readerAnnotation, "name");
            if (readerName != null) {
                aspect.setValueReaderName(readerName);
            }
        }
    }

    /**
     * Builds IdSerializationConfig from codec.id annotation.
     */
    private IdSerializationConfig buildIdConfig(EAnnotation annotation) {
        IdSerializationConfig config = factory.createIdSerializationConfig();
        Map<String, String> details = annotation.getDetails().map();

        // Strategy
        String strategyStr = details.get(KEY_STRATEGY);
        if (strategyStr != null) {
            config.setStrategy(parseIdStrategy(strategyStr));
        }

        // Key (property name)
        String key = details.get(KEY_KEY);
        if (key != null) {
            config.setIdKey(key);
        }

        // Separator for combined IDs
        String separator = details.get(KEY_SEPARATOR);
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
     * Builds TypeSerializationConfig from codec.type annotation.
     */
    private TypeSerializationConfig buildTypeConfig(EAnnotation annotation) {
        TypeSerializationConfig config = factory.createTypeSerializationConfig();
        Map<String, String> details = annotation.getDetails().map();

        // Strategy
        String strategyStr = details.get(KEY_STRATEGY);
        if (strategyStr != null) {
            config.setStrategy(parseTypeStrategy(strategyStr));
        }

        // Include
        String includeStr = details.get(KEY_INCLUDE);
        if (includeStr != null) {
            config.setInclude(Boolean.parseBoolean(includeStr));
        }

        // Type key
        String typeKey = details.get(KEY_TYPE_KEY);
        if (typeKey != null) {
            config.setTypeKey(typeKey);
        }

        // Discriminator path (for MAPPED strategy)
        String discriminatorPath = details.get(KEY_TYPE_KEY_FEATURE_PATH);
        if (discriminatorPath != null) {
            config.setDiscriminatorPath(discriminatorPath);
        }

        return config;
    }

    /**
     * Builds TypeSerializationConfig from mapId-scoped annotation (codec.type.{mapId}).
     * <p>
     * The presence of a mapId-scoped annotation implies MAPPED strategy.
     * </p>
     */
    private TypeSerializationConfig buildMappedTypeConfig(EAnnotation annotation) {
        TypeSerializationConfig config = factory.createTypeSerializationConfig();
        Map<String, String> details = annotation.getDetails().map();

        // MapId-scoped annotations imply MAPPED strategy
        config.setStrategy(TypeStrategy.MAPPED);

        // Include (default true for MAPPED)
        String includeStr = details.get(KEY_INCLUDE);
        if (includeStr != null) {
            config.setInclude(Boolean.parseBoolean(includeStr));
        } else {
            config.setInclude(true);
        }

        // Type key
        String typeKey = details.get(KEY_TYPE_KEY);
        if (typeKey != null) {
            config.setTypeKey(typeKey);
        }

        // Discriminator path (feature path for MAPPED strategy)
        String discriminatorPath = details.get(KEY_TYPE_KEY_FEATURE_PATH);
        if (discriminatorPath != null) {
            config.setDiscriminatorPath(discriminatorPath);
        }

        return config;
    }

    /**
     * Builds SuperTypeSerializationConfig from codec.supertype annotation.
     */
    private SuperTypeSerializationConfig buildSuperTypeConfig(EAnnotation annotation) {
        SuperTypeSerializationConfig config = factory.createSuperTypeSerializationConfig();
        Map<String, String> details = annotation.getDetails().map();

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

        return config;
    }

    /**
     * Gets a detail value from an annotation.
     */
    private String getDetail(EAnnotation annotation, String key) {
        return annotation.getDetails().get(key);
    }

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
