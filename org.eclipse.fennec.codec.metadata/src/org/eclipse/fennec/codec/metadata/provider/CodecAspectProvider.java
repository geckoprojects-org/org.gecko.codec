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
import java.util.Objects;

import org.eclipse.fennec.codec.metadata.util.AnnotationParseHelper;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
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
import org.eclipse.fennec.model.metadata.DiagnosticSeverity;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.PackageAspect;
import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.IdStrategy;
import org.eclipse.fennec.model.metadata.MetadataDiagnostic;
import org.eclipse.fennec.model.metadata.MetadataFactory;
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
    public PackageAspect buildPackageAspect(EPackage ePackage) {
        // Codec does not currently define package-level configuration
        // Return null to indicate no package aspect is needed
        return null;
    }

    @Override
    public ClassAspect buildClassAspect(EClass eClass) {
        Objects.requireNonNull(eClass, "eClass must not be null");
        ClassCodecAspect aspect = factory.createClassCodecAspect();
        aspect.setTypeId(ASPECT_TYPE_ID);
        aspect.setEClass(eClass);

        EAnnotation codecAnnotation = eClass.getEAnnotation(CODEC_SOURCE);
        if (codecAnnotation != null) {
            parseClassAnnotation(aspect, codecAnnotation);
        }

        return aspect;
    }

    @Override
    public FeatureAspect buildFeatureAspect(EStructuralFeature feature) {
        Objects.requireNonNull(feature, "feature must not be null");
        if (feature instanceof EReference reference) {
            return buildReferenceAspect(reference);
        } else if (feature instanceof EAttribute attribute) {
            return buildAttributeAspect(attribute);
        }
        throw new IllegalArgumentException("Unsupported feature type: " + feature.getClass().getName());
    }

    @Override
    public FeatureAspect buildAttributeAspect(EAttribute attribute) {
        Objects.requireNonNull(attribute, "attribute must not be null");
        FeatureCodecAspect aspect = factory.createFeatureCodecAspect();
        aspect.setTypeId(ASPECT_TYPE_ID);
        aspect.setEFeature(attribute);

        populateFeatureAspect(aspect, attribute);

        return aspect;
    }

    @Override
    public FeatureAspect buildReferenceAspect(EReference reference) {
        Objects.requireNonNull(reference, "reference must not be null");
        ReferenceCodecAspect aspect = factory.createReferenceCodecAspect();
        aspect.setTypeId(ASPECT_TYPE_ID);
        aspect.setEFeature(reference);

        populateFeatureAspect(aspect, reference);

        // Reference-specific parsing
        EAnnotation codecAnnotation = reference.getEAnnotation(CODEC_SOURCE);
        if (codecAnnotation != null) {
            Map<String, String> details = codecAnnotation.getDetails().map();

            // Check for class-only keys and add diagnostics (keys are ignored but logged)
            checkForClassOnlyKeys(aspect, details, reference);

            // Parse type config (for polymorphic references)
            // Note: References use a subset of type config keys - class-only keys are ignored
            if (hasReferenceTypeConfig(details)) {
                aspect.setTypeConfig(buildReferenceTypeConfig(details));
            }

            // Parse reference config
            if (hasReferenceConfig(details)) {
                aspect.setReferenceConfig(buildReferenceConfig(details));
            }

            // Parse expand flag
            AnnotationParseHelper.ifBooleanPresent(details, KEY_EXPAND, aspect::setExpand);

            // Parse inline type mappings (inlineMapping.{value}={EClass URI})
            parseInlineTypeMappings(aspect, details);

            // Parse fallback strategy for inline mappings
            AnnotationParseHelper.ifEnumPresent(details, KEY_FALLBACK_STRATEGY, FallbackStrategy.class, aspect::setFallbackStrategy);

            // Parse fallback EClass URI
            AnnotationParseHelper.ifStringPresent(details, KEY_FALLBACK_ECLASS, aspect::setFallbackEClass);
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
        AnnotationParseHelper.ifBooleanPresent(details, KEY_INHERIT, aspect::setInheritFromParent);

        // Parse type mapping discriminator (for concrete classes in MAPPED strategy)
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_DISCRIMINATOR, aspect::setDiscriminatorValue);
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
            AnnotationParseHelper.ifStringPresent(details, KEY_KEY, aspect::setEffectiveKey);

            // Parse transient flag (inverse of serialize)
            if (AnnotationParseHelper.parseBoolean(details, KEY_TRANSIENT, false)) {
                aspect.setSerialize(false);
            }

            // Parse explicit serialize flag (overrides transient if both present)
            AnnotationParseHelper.ifBooleanPresent(details, KEY_SERIALIZE, aspect::setSerialize);

            // Parse serializeNull, serializeEmpty, serializeDefaults
            AnnotationParseHelper.ifBooleanPresent(details, KEY_SERIALIZE_NULL, aspect::setSerializeNull);
            AnnotationParseHelper.ifBooleanPresent(details, KEY_SERIALIZE_EMPTY, aspect::setSerializeEmpty);
            AnnotationParseHelper.ifBooleanPresent(details, KEY_SERIALIZE_DEFAULTS, aspect::setSerializeDefaults);

            // Parse value writer/reader names
            AnnotationParseHelper.ifStringPresent(details, KEY_VALUE_WRITER_NAME, aspect::setValueWriterName);
            AnnotationParseHelper.ifStringPresent(details, KEY_VALUE_READER_NAME, aspect::setValueReaderName);

            // Parse enum serialization strategy - only valid for EAttributes per spec
            // See annotation-scope-reference.md: enumSerialization is ❌ on EReference, ✅ on EAttribute
            if (feature instanceof EAttribute) {
                AnnotationParseHelper.ifEnumPresent(details, KEY_ENUM_SERIALIZATION,
                        EnumSerializationStrategy.class, aspect::setEnumSerialization);
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
        AnnotationParseHelper.ifEnumPresent(details, KEY_ID_STRATEGY, IdStrategy.class, config::setStrategy);

        // Key (property name)
        AnnotationParseHelper.ifStringPresent(details, KEY_ID_KEY, config::setIdKey);

        // Separator for combined IDs
        AnnotationParseHelper.ifStringPresent(details, KEY_ID_SEPARATOR, config::setSeparator);

        // ID features for combined strategy
        String idFeatures = details.get(KEY_ID_FEATURES);
        if (idFeatures != null) {
            config.getIdFeatures().addAll(Arrays.asList(idFeatures.split(",")));
        }

        // Custom reader/writer
        AnnotationParseHelper.ifStringPresent(details, KEY_ID_VALUE_READER_NAME, config::setIdValueReaderName);
        AnnotationParseHelper.ifStringPresent(details, KEY_ID_VALUE_WRITER_NAME, config::setIdValueWriterName);

        // Format (PLAIN/STRUCTURED)
        AnnotationParseHelper.ifEnumPresent(details, KEY_ID_FORMAT, SerializationFormat.class, config::setFormat);

        // Key mode (ID_ONLY, BOTH, FEATURE_ONLY)
        AnnotationParseHelper.ifEnumPresent(details, KEY_ID_KEY_MODE, IdKeyMode.class, config::setKeyMode);

        // On top (ID before type)
        AnnotationParseHelper.ifBooleanPresent(details, KEY_ID_ON_TOP, config::setOnTop);

        // Serialize separator
        AnnotationParseHelper.ifBooleanPresent(details, KEY_ID_SERIALIZE_SEPARATOR, config::setSerializeSeparator);

        // Separator key
        AnnotationParseHelper.ifStringPresent(details, KEY_ID_SEPARATOR_KEY, config::setSeparatorKey);

        // Value key (inner key in STRUCTURED format)
        AnnotationParseHelper.ifStringPresent(details, KEY_ID_VALUE_KEY, config::setValueKey);

        return config;
    }

    /**
     * Builds TypeSerializationConfig from annotation details for EClass.
     * <p>
     * Note: Discriminator-based type resolution is now orthogonal to type strategy.
     * The discriminatorPath specifies an additional resolution mechanism that works
     * alongside the primary type strategy, not as a replacement.
     * </p>
     */
    private TypeSerializationConfig buildTypeConfig(Map<String, String> details) {
        TypeSerializationConfig config = factory.createTypeSerializationConfig();

        // Strategy - explicit strategy only, discriminator is orthogonal
        AnnotationParseHelper.ifEnumPresent(details, KEY_TYPE_STRATEGY, TypeStrategy.class, config::setStrategy);

        // Type key
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_KEY, config::setTypeKey);

        // Discriminator path - orthogonal to strategy, provides additional resolution
        // Class-only: defines where to find discriminator value in JSON
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_DISCRIMINATOR_PATH, config::setDiscriminatorPath);

        // Map ID for discriminator registry lookup
        // Class-only: identifies the discriminator registry
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_MAP_ID, config::setMapId);

        // Format (PLAIN/STRUCTURED)
        AnnotationParseHelper.ifEnumPresent(details, KEY_TYPE_FORMAT, SerializationFormat.class, config::setFormat);

        // Schema key
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_SCHEMA_KEY, config::setSchemaKey);

        // Name key
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_NAME_KEY, config::setNameKey);

        // Fallback strategy for discriminator resolution
        AnnotationParseHelper.ifEnumPresent(details, KEY_FALLBACK_STRATEGY, FallbackStrategy.class, config::setFallbackStrategy);

        // Fallback EClass URI
        AnnotationParseHelper.ifStringPresent(details, KEY_FALLBACK_ECLASS, config::setFallbackEClass);

        return config;
    }

    /**
     * Builds TypeSerializationConfig from annotation details for EReference.
     * <p>
     * References support a subset of type config keys. Class-only keys like
     * typeMapId and typeDiscriminatorPath are NOT parsed here - they are ignored
     * per spec (see 08-discriminator-mapping.md section 7).
     * </p>
     */
    private TypeSerializationConfig buildReferenceTypeConfig(Map<String, String> details) {
        TypeSerializationConfig config = factory.createTypeSerializationConfig();

        // Strategy
        AnnotationParseHelper.ifEnumPresent(details, KEY_TYPE_STRATEGY, TypeStrategy.class, config::setStrategy);

        // Type key - valid on references for inline mappings
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_KEY, config::setTypeKey);

        // Format (PLAIN/STRUCTURED)
        AnnotationParseHelper.ifEnumPresent(details, KEY_TYPE_FORMAT, SerializationFormat.class, config::setFormat);

        // Schema key
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_SCHEMA_KEY, config::setSchemaKey);

        // Name key
        AnnotationParseHelper.ifStringPresent(details, KEY_TYPE_NAME_KEY, config::setNameKey);

        // NOTE: The following are intentionally NOT parsed for references:
        // - typeMapId: class-only (type mapping registry is class-level)
        // - typeDiscriminatorPath: class-only (discriminator path is defined on base class)
        // See 08-discriminator-mapping.md section 7 "Invalid Configurations"

        return config;
    }

    /**
     * Builds SuperTypeSerializationConfig from annotation details.
     */
    private SuperTypeSerializationConfig buildSuperTypeConfig(Map<String, String> details) {
        SuperTypeSerializationConfig config = factory.createSuperTypeSerializationConfig();

        // Enabled
        AnnotationParseHelper.ifBooleanPresent(details, KEY_SUPERTYPE_SERIALIZE, config::setEnabled);

        // Key
        AnnotationParseHelper.ifStringPresent(details, KEY_SUPERTYPE_KEY, config::setSuperTypeKey);

        // Strategy/Selection
        AnnotationParseHelper.ifEnumPresent(details, KEY_SUPERTYPE_STRATEGY, SuperTypeSelection.class, config::setSelection);

        // As array
        AnnotationParseHelper.ifBooleanPresent(details, KEY_SUPERTYPE_AS_ARRAY, config::setAsArray);

        // Separator
        AnnotationParseHelper.ifStringPresent(details, KEY_SUPERTYPE_SEPARATOR, config::setSeparator);

        // Format (PLAIN/STRUCTURED)
        AnnotationParseHelper.ifEnumPresent(details, KEY_SUPERTYPE_FORMAT, SerializationFormat.class, config::setFormat);

        // Note: schemaKey is inherited from TypeConfig, not configured separately for supertype
        // Note: nameKey removed - superTypeKey has format-dependent default

        return config;
    }

    /**
     * Builds ReferenceSerializationConfig from annotation details.
     */
    private ReferenceSerializationConfig buildReferenceConfig(Map<String, String> details) {
        ReferenceSerializationConfig config = factory.createReferenceSerializationConfig();

        // Format (PLAIN/STRUCTURED)
        AnnotationParseHelper.ifEnumPresent(details, KEY_REF_FORMAT, SerializationFormat.class, config::setFormat);

        // Ref key
        AnnotationParseHelper.ifStringPresent(details, KEY_REF_KEY, config::setRefKey);

        // Type key
        AnnotationParseHelper.ifStringPresent(details, KEY_REF_TYPE_KEY, config::setTypeKey);

        // Expand
        AnnotationParseHelper.ifBooleanPresent(details, KEY_EXPAND, config::setExpand);

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
     * Checks if the details map contains any type configuration keys (for EClass).
     */
    private boolean hasTypeConfig(Map<String, String> details) {
        return details.containsKey(KEY_TYPE_STRATEGY)
                || details.containsKey(KEY_TYPE_KEY)
                || details.containsKey(KEY_TYPE_MAP_ID)
                || details.containsKey(KEY_TYPE_DISCRIMINATOR_PATH)
                || details.containsKey(KEY_TYPE_FORMAT)
                || details.containsKey(KEY_TYPE_SCHEMA_KEY)
                || details.containsKey(KEY_TYPE_NAME_KEY)
                || details.containsKey(KEY_FALLBACK_STRATEGY)
                || details.containsKey(KEY_FALLBACK_ECLASS);
    }

    /**
     * Checks if the details map contains any type configuration keys valid for EReference.
     * <p>
     * Note: typeMapId and typeDiscriminatorPath are class-only and not checked here.
     * </p>
     */
    private boolean hasReferenceTypeConfig(Map<String, String> details) {
        return details.containsKey(KEY_TYPE_STRATEGY)
                || details.containsKey(KEY_TYPE_KEY)
                || details.containsKey(KEY_TYPE_FORMAT)
                || details.containsKey(KEY_TYPE_SCHEMA_KEY)
                || details.containsKey(KEY_TYPE_NAME_KEY);
        // NOTE: KEY_TYPE_MAP_ID and KEY_TYPE_DISCRIMINATOR_PATH are NOT checked
        // as they are class-only per spec (08-discriminator-mapping.md section 7)
    }

    /**
     * Checks if the details map contains any supertype configuration keys.
     */
    private boolean hasSuperTypeConfig(Map<String, String> details) {
        return details.containsKey(KEY_SUPERTYPE_SERIALIZE)
                || details.containsKey(KEY_SUPERTYPE_KEY)
                || details.containsKey(KEY_SUPERTYPE_STRATEGY)
                || details.containsKey(KEY_SUPERTYPE_AS_ARRAY)
                || details.containsKey(KEY_SUPERTYPE_FORMAT);
        // Note: KEY_SUPERTYPE_SCHEMA_KEY removed - supertype inherits schemaKey from Type config
        // Note: KEY_SUPERTYPE_NAME_KEY removed - superTypeKey has format-dependent default
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
    // Diagnostic Helpers
    // ========================================================================

    /**
     * Checks for class-only annotation keys on a reference and adds diagnostics.
     * <p>
     * Per spec (08-discriminator-mapping.md section 7), typeMapId and typeDiscriminatorPath
     * are class-only keys. When found on a reference, they are ignored and a warning
     * diagnostic is added to the aspect.
     * </p>
     */
    private void checkForClassOnlyKeys(ReferenceCodecAspect aspect, Map<String, String> details, EReference reference) {
        if (details.containsKey(KEY_TYPE_MAP_ID)) {
            addDiagnostic(aspect, DiagnosticSeverity.WARNING,
                    "Annotation key '" + KEY_TYPE_MAP_ID + "' is not valid on EReference '" +
                    reference.getName() + "', ignored (class-only key)",
                    KEY_TYPE_MAP_ID);
        }
        if (details.containsKey(KEY_TYPE_DISCRIMINATOR_PATH)) {
            addDiagnostic(aspect, DiagnosticSeverity.WARNING,
                    "Annotation key '" + KEY_TYPE_DISCRIMINATOR_PATH + "' is not valid on EReference '" +
                    reference.getName() + "', ignored (class-only key)",
                    KEY_TYPE_DISCRIMINATOR_PATH);
        }
    }

    /**
     * Adds a diagnostic to the aspect.
     */
    private void addDiagnostic(FeatureCodecAspect aspect, DiagnosticSeverity severity, String message, String key) {
        MetadataDiagnostic diagnostic = MetadataFactory.eINSTANCE.createMetadataDiagnostic();
        diagnostic.setSeverity(severity);
        diagnostic.setMessage(message);
        diagnostic.setKey(key);
        aspect.getDiagnostics().add(diagnostic);
    }

}
