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
package org.eclipse.fennec.codec.v2.config.effective;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.type.TypeDiscriminatorService;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.model.metadata.ClassMetadata;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;
import org.eclipse.fennec.model.metadata.FeatureMetadata;
import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.IdStrategy;
import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.SuperTypeSelection;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.eclipse.fennec.codec.v2.util.AnnotationHelper;

/**
 * Merges configuration from all sources into effective configurations.
 * <p>
 * This is the SINGLE POINT where configuration resolution order is implemented:
 * <ol>
 *   <li>Load/Save options (highest priority)</li>
 *   <li>ResourceFactory defaults</li>
 *   <li>CodecConfiguration (module config)</li>
 *   <li>Model aspects (from MetadataService, based on EAnnotations)</li>
 *   <li>Built-in defaults (lowest priority)</li>
 * </ol>
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#16-configuration-hierarchy">Spec 16: Configuration Hierarchy</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class ConfigurationMerger {

    private final CodecConfiguration moduleConfig;
    private final MetadataService metadataService;

    /**
     * Creates a new ConfigurationMerger.
     *
     * @param moduleConfig the codec module configuration
     * @param metadataService the metadata service for model aspects (may be null)
     * @param factoryDefaults the resource factory default options (may be null)
     * @param options the load/save options (may be null)
     */
    public ConfigurationMerger(
            CodecConfiguration moduleConfig,
            MetadataService metadataService,
            Map<String, Object> factoryDefaults,
            Map<String, Object> options) {
        this.moduleConfig = moduleConfig != null ? moduleConfig : CodecConfiguration.builder().build();
        this.metadataService = metadataService;
    }

    /**
     * Creates the fully resolved EffectiveCodecConfig.
     *
     * @return the effective codec configuration
     */
    public EffectiveCodecConfig merge() {
        // Build TypeDiscriminatorService from MetadataService if available
        TypeDiscriminatorService typeDiscriminatorService = null;
        if (metadataService != null) {
            typeDiscriminatorService = TypeDiscriminatorService.fromMetadataService(metadataService);
        }

        return EffectiveCodecConfig.builder()
                .globalIgnoreFeatures(moduleConfig.getGlobalIgnoreFeatureNames())
                .sortPropertiesAlphabetically(moduleConfig.isSortPropertiesAlphabetically())
                .refKey(moduleConfig.getRefKey())
                .useNamesFromExtendedMetaData(moduleConfig.isUseNamesFromExtendedMetaData())
                .globalTypeKey(moduleConfig.getTypeKey())
                .globalTypeStrategy(TypeStrategy.URI)  // Module config default, can be enhanced later
                .typeDiscriminatorService(typeDiscriminatorService)
                .smartCompression(moduleConfig.isSmartCompression())
                .metadataService(metadataService)
                // Expand settings
                .expandGlobal(moduleConfig.isExpandGlobal())
                .expandReferences(moduleConfig.getExpandReferences())
                .expandReferenceNames(moduleConfig.getExpandReferenceNames())
                .expandDepth(moduleConfig.getExpandDepth())
                .expandIgnoreBidirectional(moduleConfig.isExpandIgnoreBidirectional())
                .classConfigFactory(this::buildClassConfig)
                .featureConfigFactory(this::buildFeatureConfig)
                .build();
    }

    // ========================================================================
    // Class config building
    // ========================================================================

    /**
     * Builds the effective class configuration for an EClass.
     */
    private EffectiveClassConfig buildClassConfig(EClass eClass) {
        ClassCodecAspect aspect = getClassCodecAspect(eClass);

        return EffectiveClassConfig.builder()
                .eClass(eClass)
                .idConfig(buildIdConfig(eClass, aspect))
                .typeConfig(buildTypeConfig(eClass, aspect))
                .superTypeConfig(buildSuperTypeConfig(eClass, aspect))
                .build();
    }

    /**
     * Builds the effective ID configuration.
     */
    private EffectiveIdConfig buildIdConfig(EClass eClass, ClassCodecAspect aspect) {
        IdSerializationConfig aspectConfig = aspect != null ? aspect.getIdConfig() : null;

        return EffectiveIdConfig.builder()
                .enabled(moduleConfig.isUseId())
                .onTop(moduleConfig.isIdOnTop())
                .key(resolveIdKey(aspectConfig))
                .strategy(resolveIdStrategy(aspectConfig))
                .keyMode(resolveIdKeyMode(aspectConfig))
                .format(resolveIdFormat(aspectConfig))
                .separator(resolveIdSeparator(aspectConfig))
                .serializeSeparator(moduleConfig.isIdSerializeSeparator())
                .separatorKey(moduleConfig.getIdSeparatorKey())
                .idFeatures(resolveIdFeatures(aspectConfig))
                .valueWriterName(aspectConfig != null ? aspectConfig.getIdValueWriterName() : null)
                .valueReaderName(aspectConfig != null ? aspectConfig.getIdValueReaderName() : null)
                .build();
    }

    /**
     * Builds the effective type configuration.
     */
    private EffectiveTypeConfig buildTypeConfig(EClass eClass, ClassCodecAspect aspect) {
        TypeSerializationConfig aspectConfig = aspect != null ? aspect.getTypeConfig() : null;

        return EffectiveTypeConfig.builder()
                .enabled(resolveTypeEnabled(aspectConfig))
                .strategy(resolveTypeStrategy(aspectConfig))
                .typeKey(resolveTypeKey(aspectConfig))
                .schemaKey(resolveSchemaKey(aspectConfig))
                .nameKey(resolveNameKey(aspectConfig))
                .discriminatorPath(resolveDiscriminatorPath(eClass, aspectConfig))
                .discriminatorValue(resolveDiscriminatorValue(aspect, aspectConfig))
                .build();
    }

    /**
     * Builds the effective supertype configuration.
     */
    private EffectiveSuperTypeConfig buildSuperTypeConfig(EClass eClass, ClassCodecAspect aspect) {
        SuperTypeSerializationConfig aspectConfig = aspect != null ? aspect.getSuperTypeConfig() : null;

        return EffectiveSuperTypeConfig.builder()
                .enabled(resolveSuperTypeEnabled(aspectConfig))
                .selection(resolveSuperTypeSelection(aspectConfig))
                .format(resolveSuperTypeFormat(aspectConfig))
                .superTypeKey(resolveSuperTypeKey(aspectConfig))
                .schemaKey(resolveSuperTypeSchemaKey(aspectConfig))
                .nameKey(resolveSuperTypeNameKey(aspectConfig))
                .useSmartCompression(aspectConfig != null && aspectConfig.isUseSmartCompression())
                .build();
    }

    // ========================================================================
    // Feature config building
    // ========================================================================

    /**
     * Builds the effective feature configuration.
     */
    private EffectiveFeatureConfig buildFeatureConfig(EStructuralFeature feature) {
        FeatureCodecAspect aspect = getFeatureCodecAspect(feature);

        return EffectiveFeatureConfig.builder()
                .feature(feature)
                .key(resolveFeatureKey(feature, aspect))
                .serialize(resolveFeatureSerialize(feature, aspect))
                .serializeNull(resolveSerializeNull(aspect))
                .serializeEmpty(resolveSerializeEmpty(aspect))
                .serializeDefaults(resolveSerializeDefaults(aspect))
                .valueWriterName(aspect != null ? aspect.getValueWriterName() : null)
                .valueReaderName(aspect != null ? aspect.getValueReaderName() : null)
                .enumSerialization(resolveEnumSerialization(aspect))
                .build();
    }

    // ========================================================================
    // Aspect extraction
    // ========================================================================

    private ClassCodecAspect getClassCodecAspect(EClass eClass) {
        if (metadataService == null) {
            return null;
        }
        ClassMetadata metadata = metadataService.getClassMetadata(eClass);
        if (metadata == null) {
            return null;
        }
        return metadata.getAspects().stream()
                .filter(ClassCodecAspect.class::isInstance)
                .map(ClassCodecAspect.class::cast)
                .findFirst()
                .orElse(null);
    }

    private FeatureCodecAspect getFeatureCodecAspect(EStructuralFeature feature) {
        if (metadataService == null) {
            return null;
        }
        FeatureMetadata metadata = metadataService.getFeatureMetadata(feature);
        if (metadata == null) {
            return null;
        }
        return metadata.getAspects().stream()
                .filter(FeatureCodecAspect.class::isInstance)
                .map(FeatureCodecAspect.class::cast)
                .findFirst()
                .orElse(null);
    }

    // ========================================================================
    // ID resolution helpers
    // ========================================================================

    private String resolveIdKey(IdSerializationConfig aspectConfig) {
        if (aspectConfig != null && isNonEmpty(aspectConfig.getIdKey())) {
            return aspectConfig.getIdKey();
        }
        return moduleConfig.getIdKey();
    }

    private IdStrategy resolveIdStrategy(IdSerializationConfig aspectConfig) {
        if (aspectConfig != null && aspectConfig.getStrategy() != null) {
            return aspectConfig.getStrategy();
        }
        return IdStrategy.ID_FIELD;
    }

    private IdKeyMode resolveIdKeyMode(IdSerializationConfig aspectConfig) {
        if (aspectConfig != null && aspectConfig.getKeyMode() != null) {
            return aspectConfig.getKeyMode();
        }
        return moduleConfig.getIdKeyMode();
    }

    private SerializationFormat resolveIdFormat(IdSerializationConfig aspectConfig) {
        if (aspectConfig != null && aspectConfig.getFormat() != null) {
            return aspectConfig.getFormat();
        }
        return moduleConfig.getIdFormat();
    }

    private String resolveIdSeparator(IdSerializationConfig aspectConfig) {
        if (aspectConfig != null && isNonEmpty(aspectConfig.getSeparator())) {
            return aspectConfig.getSeparator();
        }
        return moduleConfig.getIdSeparator();
    }

    private List<String> resolveIdFeatures(IdSerializationConfig aspectConfig) {
        if (aspectConfig != null && aspectConfig.getIdFeatures() != null && !aspectConfig.getIdFeatures().isEmpty()) {
            return List.copyOf(aspectConfig.getIdFeatures());
        }
        List<String> configFeatures = moduleConfig.getIdFeatures();
        if (configFeatures != null && !configFeatures.isEmpty()) {
            return configFeatures;
        }
        return List.of();
    }

    // ========================================================================
    // Type resolution helpers
    // ========================================================================

    private boolean resolveTypeEnabled(TypeSerializationConfig aspectConfig) {
        if (aspectConfig != null) {
            return aspectConfig.isInclude();
        }
        return moduleConfig.isSerializeType();
    }

    private TypeStrategy resolveTypeStrategy(TypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && aspectConfig.getStrategy() != null) {
            return aspectConfig.getStrategy();
        }
        return TypeStrategy.URI;
    }

    private String resolveTypeKey(TypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && isNonEmpty(aspectConfig.getTypeKey())) {
            return aspectConfig.getTypeKey();
        }
        return moduleConfig.getTypeKey();
    }

    private String resolveSchemaKey(TypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && isNonEmpty(aspectConfig.getSchemaKey())) {
            return aspectConfig.getSchemaKey();
        }
        return "schema";
    }

    private String resolveNameKey(TypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && isNonEmpty(aspectConfig.getNameKey())) {
            return aspectConfig.getNameKey();
        }
        return "name";
    }

    private String resolveDiscriminatorValue(ClassCodecAspect aspect, TypeSerializationConfig typeConfig) {
        // Check class-level discriminator first
        if (aspect != null && isNonEmpty(aspect.getDiscriminatorValue())) {
            return aspect.getDiscriminatorValue();
        }
        // Then check type config discriminator
        if (typeConfig != null && isNonEmpty(typeConfig.getDiscriminatorValue())) {
            return typeConfig.getDiscriminatorValue();
        }
        return null;
    }

    /**
     * Resolves the discriminator path for an EClass.
     * <p>
     * First checks the class's own typeConfig. If not found, walks up the
     * supertype hierarchy looking for a discriminator path defined on an abstract base.
     * </p>
     *
     * @param eClass the EClass to check
     * @param typeConfig the current class's type config (may be null)
     * @return the discriminator path, or null if not found
     */
    private String resolveDiscriminatorPath(EClass eClass, TypeSerializationConfig typeConfig) {
        // First check the current class's typeConfig
        if (typeConfig != null && isNonEmpty(typeConfig.getDiscriminatorPath())) {
            return typeConfig.getDiscriminatorPath();
        }

        // Walk up the supertype hierarchy looking for discriminator path
        if (metadataService != null && eClass != null) {
            for (EClass superType : eClass.getEAllSuperTypes()) {
                ClassCodecAspect superAspect = getClassCodecAspect(superType);
                if (superAspect != null && superAspect.getTypeConfig() != null) {
                    String path = superAspect.getTypeConfig().getDiscriminatorPath();
                    if (isNonEmpty(path)) {
                        return path;
                    }
                }
            }
        }

        return null;
    }

    // ========================================================================
    // SuperType resolution helpers
    // ========================================================================

    private boolean resolveSuperTypeEnabled(SuperTypeSerializationConfig aspectConfig) {
        if (aspectConfig != null) {
            return aspectConfig.isEnabled();
        }
        return moduleConfig.isSerializeSuperTypes();
    }

    private SuperTypeSelection resolveSuperTypeSelection(SuperTypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && aspectConfig.getSelection() != null) {
            return aspectConfig.getSelection();
        }
        // Map from CodecConfiguration's boolean API to SuperTypeSelection enum
        return moduleConfig.isSerializeAllSuperTypes() ? SuperTypeSelection.ALL : SuperTypeSelection.SINGLE;
    }

    private SerializationFormat resolveSuperTypeFormat(SuperTypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && aspectConfig.getFormat() != null) {
            return aspectConfig.getFormat();
        }
        return SerializationFormat.PLAIN;
    }

    private String resolveSuperTypeKey(SuperTypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && isNonEmpty(aspectConfig.getSuperTypeKey())) {
            return aspectConfig.getSuperTypeKey();
        }
        return moduleConfig.getSuperTypeKey();
    }

    private String resolveSuperTypeSchemaKey(SuperTypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && isNonEmpty(aspectConfig.getSchemaKey())) {
            return aspectConfig.getSchemaKey();
        }
        return "schema";
    }

    private String resolveSuperTypeNameKey(SuperTypeSerializationConfig aspectConfig) {
        if (aspectConfig != null && isNonEmpty(aspectConfig.getNameKey())) {
            return aspectConfig.getNameKey();
        }
        return "name";
    }

    // ========================================================================
    // Feature resolution helpers
    // ========================================================================

    private String resolveFeatureKey(EStructuralFeature feature, FeatureCodecAspect aspect) {
        // 1. Highest priority: explicit codec annotation key (via effectiveKey)
        if (aspect != null && isNonEmpty(aspect.getEffectiveKey())) {
            return aspect.getEffectiveKey();
        }

        // 2. Medium priority: ExtendedMetaData name (if enabled)
        if (moduleConfig.isUseNamesFromExtendedMetaData()) {
            String extendedMetaDataName = getExtendedMetaDataName(feature);
            if (extendedMetaDataName != null) {
                return extendedMetaDataName;
            }
        }

        // 3. Lowest priority: feature name
        return feature.getName();
    }

    /**
     * Gets the ExtendedMetaData name for a feature.
     * <p>
     * First checks the MetadataService (if available), then falls back to AnnotationHelper.
     * </p>
     */
    private String getExtendedMetaDataName(EStructuralFeature feature) {
        // Prefer metadata from MetadataService (already pre-computed)
        if (metadataService != null) {
            FeatureMetadata featureMeta = metadataService.getFeatureMetadata(feature);
            if (featureMeta != null) {
                return featureMeta.getExtendedMetaDataName();
            }
        }
        // Fall back to direct annotation lookup
        return AnnotationHelper.getExtendedMetaDataName(feature);
    }

    private boolean resolveFeatureSerialize(EStructuralFeature feature, FeatureCodecAspect aspect) {
        // Check if globally ignored
        if (moduleConfig.isGloballyIgnored(feature.getName())) {
            return false;
        }
        // Check derived/transient
        if (feature.isDerived() || feature.isTransient()) {
            return false;
        }
        // Check aspect
        if (aspect != null) {
            return aspect.isSerialize();
        }
        return true;
    }

    private boolean resolveSerializeNull(FeatureCodecAspect aspect) {
        if (aspect != null) {
            return aspect.isSerializeNull();
        }
        return moduleConfig.isSerializeNullValue();
    }

    private boolean resolveSerializeEmpty(FeatureCodecAspect aspect) {
        if (aspect != null) {
            return aspect.isSerializeEmpty();
        }
        return moduleConfig.isSerializeEmptyValue();
    }

    private boolean resolveSerializeDefaults(FeatureCodecAspect aspect) {
        if (aspect != null) {
            return aspect.isSerializeDefaults();
        }
        return moduleConfig.isSerializeDefaultValue();
    }

    private EnumSerializationStrategy resolveEnumSerialization(FeatureCodecAspect aspect) {
        if (aspect != null && aspect.getEnumSerialization() != null) {
            return aspect.getEnumSerialization();
        }
        // Default to LITERAL
        return EnumSerializationStrategy.LITERAL;
    }

    // ========================================================================
    // Utility methods
    // ========================================================================

    private boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
