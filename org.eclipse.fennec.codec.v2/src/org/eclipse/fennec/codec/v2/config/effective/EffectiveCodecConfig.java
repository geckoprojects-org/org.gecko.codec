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
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.metadata.type.TypeDiscriminatorService;
import org.eclipse.fennec.model.metadata.TypeStrategy;

/**
 * Fully resolved codec configuration for a single serialization/deserialization operation.
 * <p>
 * This class holds the merged global configuration and provides lazy-cached access to
 * class-level and feature-level effective configurations. It is created once at
 * Resource.save()/load() time and passed to serializers/deserializers.
 * </p>
 * <p>
 * Configuration sources (in resolution order, highest priority first):
 * <ol>
 *   <li>Load/Save options</li>
 *   <li>ResourceFactory defaults</li>
 *   <li>CodecModule configuration</li>
 *   <li>Model aspects (EAnnotations via MetadataService)</li>
 *   <li>Built-in defaults</li>
 * </ol>
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#16-configuration-hierarchy">Spec 16: Configuration Hierarchy</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public final class EffectiveCodecConfig {

    // ========================================================================
    // Global settings (already merged)
    // ========================================================================

    private final List<String> globalIgnoreFeatures;
    private final boolean sortPropertiesAlphabetically;
    private final String refKey;
    private final boolean useNamesFromExtendedMetaData;
    private final String globalTypeKey;
    private final TypeStrategy globalTypeStrategy;
    private final TypeDiscriminatorService typeDiscriminatorService;

    // ========================================================================
    // Class and feature config factories (for lazy building)
    // ========================================================================

    private final Function<EClass, EffectiveClassConfig> classConfigFactory;
    private final Function<EStructuralFeature, EffectiveFeatureConfig> featureConfigFactory;

    // ========================================================================
    // Caches (thread-safe, lazy populated)
    // ========================================================================

    private final Map<EClass, EffectiveClassConfig> classConfigCache = new ConcurrentHashMap<>();
    private final Map<EStructuralFeature, EffectiveFeatureConfig> featureConfigCache = new ConcurrentHashMap<>();

    private EffectiveCodecConfig(Builder builder) {
        this.globalIgnoreFeatures = List.copyOf(builder.globalIgnoreFeatures);
        this.sortPropertiesAlphabetically = builder.sortPropertiesAlphabetically;
        this.refKey = builder.refKey;
        this.useNamesFromExtendedMetaData = builder.useNamesFromExtendedMetaData;
        this.globalTypeKey = builder.globalTypeKey;
        this.globalTypeStrategy = builder.globalTypeStrategy;
        this.typeDiscriminatorService = builder.typeDiscriminatorService;
        this.classConfigFactory = Objects.requireNonNull(builder.classConfigFactory,
                "classConfigFactory must not be null");
        this.featureConfigFactory = Objects.requireNonNull(builder.featureConfigFactory,
                "featureConfigFactory must not be null");
    }

    // ========================================================================
    // Global settings
    // ========================================================================

    /**
     * Returns the list of globally ignored feature names.
     */
    public List<String> getGlobalIgnoreFeatures() {
        return globalIgnoreFeatures;
    }

    /**
     * Checks if a feature name is globally ignored.
     */
    public boolean isGloballyIgnored(String featureName) {
        return featureName != null && globalIgnoreFeatures.contains(featureName);
    }

    /**
     * Returns whether JSON properties should be sorted alphabetically.
     */
    public boolean isSortPropertiesAlphabetically() {
        return sortPropertiesAlphabetically;
    }

    /**
     * Returns the JSON property key for non-containment references.
     */
    public String getRefKey() {
        return refKey;
    }

    /**
     * Returns whether to use names from ExtendedMetaData annotations.
     */
    public boolean isUseNamesFromExtendedMetaData() {
        return useNamesFromExtendedMetaData;
    }

    /**
     * Returns the global type key used for type discrimination.
     * <p>
     * This is a module-level default that may be overridden per-class.
     * </p>
     */
    public String getGlobalTypeKey() {
        return globalTypeKey;
    }

    /**
     * Returns the global type strategy used for type discrimination.
     * <p>
     * This is a module-level default that may be overridden per-class.
     * </p>
     */
    public TypeStrategy getGlobalTypeStrategy() {
        return globalTypeStrategy;
    }

    /**
     * Returns the type discriminator service for MAPPED strategy resolution.
     * <p>
     * This service provides lookup of EClasses from discriminator values
     * and vice versa. May be null if no MAPPED types are configured.
     * </p>
     */
    public TypeDiscriminatorService getTypeDiscriminatorService() {
        return typeDiscriminatorService;
    }

    // ========================================================================
    // Class config (cached)
    // ========================================================================

    /**
     * Returns the effective configuration for an EClass.
     * <p>
     * Results are cached - subsequent calls for the same EClass return the cached instance.
     * </p>
     *
     * @param eClass the EClass
     * @return the effective class configuration
     */
    public EffectiveClassConfig getClassConfig(EClass eClass) {
        Objects.requireNonNull(eClass, "eClass must not be null");
        return classConfigCache.computeIfAbsent(eClass, classConfigFactory);
    }

    // ========================================================================
    // Feature config (cached)
    // ========================================================================

    /**
     * Returns the effective configuration for a structural feature.
     * <p>
     * Results are cached - subsequent calls for the same feature return the cached instance.
     * </p>
     *
     * @param feature the structural feature
     * @return the effective feature configuration
     */
    public EffectiveFeatureConfig getFeatureConfig(EStructuralFeature feature) {
        Objects.requireNonNull(feature, "feature must not be null");
        return featureConfigCache.computeIfAbsent(feature, featureConfigFactory);
    }

    // ========================================================================
    // Cache management
    // ========================================================================

    /**
     * Clears all cached configurations.
     * <p>
     * This is useful if the underlying metadata changes during the lifetime
     * of this config instance (unusual in practice).
     * </p>
     */
    public void clearCaches() {
        classConfigCache.clear();
        featureConfigCache.clear();
    }

    /**
     * Returns the number of cached class configurations.
     */
    public int getClassCacheSize() {
        return classConfigCache.size();
    }

    /**
     * Returns the number of cached feature configurations.
     */
    public int getFeatureCacheSize() {
        return featureConfigCache.size();
    }

    /**
     * Creates a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for EffectiveCodecConfig.
     */
    public static final class Builder {
        private List<String> globalIgnoreFeatures = List.of();
        private boolean sortPropertiesAlphabetically = false;
        private String refKey = "$ref";
        private boolean useNamesFromExtendedMetaData = false;
        private String globalTypeKey = "_type";
        private TypeStrategy globalTypeStrategy = TypeStrategy.URI;
        private TypeDiscriminatorService typeDiscriminatorService;
        private Function<EClass, EffectiveClassConfig> classConfigFactory;
        private Function<EStructuralFeature, EffectiveFeatureConfig> featureConfigFactory;

        private Builder() {}

        public Builder globalIgnoreFeatures(List<String> globalIgnoreFeatures) {
            this.globalIgnoreFeatures = globalIgnoreFeatures != null ? globalIgnoreFeatures : List.of();
            return this;
        }

        public Builder sortPropertiesAlphabetically(boolean sortPropertiesAlphabetically) {
            this.sortPropertiesAlphabetically = sortPropertiesAlphabetically;
            return this;
        }

        public Builder refKey(String refKey) {
            this.refKey = refKey;
            return this;
        }

        public Builder useNamesFromExtendedMetaData(boolean useNamesFromExtendedMetaData) {
            this.useNamesFromExtendedMetaData = useNamesFromExtendedMetaData;
            return this;
        }

        public Builder globalTypeKey(String globalTypeKey) {
            this.globalTypeKey = globalTypeKey;
            return this;
        }

        public Builder globalTypeStrategy(TypeStrategy globalTypeStrategy) {
            this.globalTypeStrategy = globalTypeStrategy != null ? globalTypeStrategy : TypeStrategy.URI;
            return this;
        }

        public Builder typeDiscriminatorService(TypeDiscriminatorService typeDiscriminatorService) {
            this.typeDiscriminatorService = typeDiscriminatorService;
            return this;
        }

        public Builder classConfigFactory(Function<EClass, EffectiveClassConfig> classConfigFactory) {
            this.classConfigFactory = classConfigFactory;
            return this;
        }

        public Builder featureConfigFactory(Function<EStructuralFeature, EffectiveFeatureConfig> featureConfigFactory) {
            this.featureConfigFactory = featureConfigFactory;
            return this;
        }

        public EffectiveCodecConfig build() {
            return new EffectiveCodecConfig(this);
        }
    }
}
