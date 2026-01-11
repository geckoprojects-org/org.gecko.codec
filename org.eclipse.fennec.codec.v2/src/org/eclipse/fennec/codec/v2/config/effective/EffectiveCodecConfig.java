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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.metadata.type.TypeDiscriminatorService;
import org.eclipse.fennec.codec.v2.value.CodecValueReader;
import org.eclipse.fennec.codec.v2.value.CodecValueRegistry;
import org.eclipse.fennec.codec.v2.value.CodecValueWriter;
import org.eclipse.fennec.model.metadata.ClassMetadata;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.eclipse.fennec.model.metadata.api.MetadataService;

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
    private final boolean smartCompression;
    private final MetadataService metadataService;

    // SuperType global settings
    private final String globalSuperTypeKey;
    private final boolean validateSuperTypeHierarchy;

    // Custom value readers/writers registry
    private final CodecValueRegistry valueRegistry;

    // Expand settings
    private final boolean expandGlobal;
    private final Set<EReference> expandReferences;
    private final Set<String> expandReferenceNames;
    private final int expandDepth;
    private final boolean expandIgnoreBidirectional;

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
        this.smartCompression = builder.smartCompression;
        this.metadataService = builder.metadataService;
        this.globalSuperTypeKey = builder.globalSuperTypeKey;
        this.validateSuperTypeHierarchy = builder.validateSuperTypeHierarchy;
        this.valueRegistry = builder.valueRegistry;
        this.expandGlobal = builder.expandGlobal;
        this.expandReferences = builder.expandReferences != null
                ? Set.copyOf(builder.expandReferences)
                : Collections.emptySet();
        this.expandReferenceNames = builder.expandReferenceNames != null
                ? Set.copyOf(builder.expandReferenceNames)
                : Collections.emptySet();
        this.expandDepth = builder.expandDepth;
        this.expandIgnoreBidirectional = builder.expandIgnoreBidirectional;
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

    /**
     * Returns whether smart compression is enabled.
     * <p>
     * When enabled, type information (_type) is omitted when it can be inferred
     * from the reference declaration (instance type == reference type).
     * </p>
     *
     * @return true if smart compression is enabled
     * @see <a href="docs/codec-v2-serialization-spec.md#121-smart-compression">Spec 12.1: Smart Compression</a>
     */
    public boolean isSmartCompression() {
        return smartCompression;
    }

    /**
     * Returns the global supertype key used for deserialization.
     * <p>
     * This is the key used to identify supertype fields in JSON when
     * deserializing before the EClass is known.
     * </p>
     *
     * @return the global supertype key, defaults to "_supertype"
     */
    public String getGlobalSuperTypeKey() {
        return globalSuperTypeKey;
    }

    /**
     * Returns whether to validate supertype hierarchy during deserialization.
     * <p>
     * When true, the deserializer validates that declared supertypes in JSON
     * match the resolved EClass's actual supertypes. Deserialization fails
     * if there's a mismatch.
     * </p>
     *
     * @return true if supertype hierarchy should be validated
     */
    public boolean isValidateSuperTypeHierarchy() {
        return validateSuperTypeHierarchy;
    }

    /**
     * Returns the metadata service for EClass resolution and aspect lookups.
     * <p>
     * This service provides access to ClassMetadata and FeatureMetadata
     * parsed from EAnnotations. May be null if no metadata service was configured.
     * </p>
     *
     * @return the metadata service, or null if not configured
     */
    public MetadataService getMetadataService() {
        return metadataService;
    }

    /**
     * Convenience method to get ClassMetadata for an EClass.
     *
     * @param eClass the EClass
     * @return the class metadata, or null if not found or no metadata service
     */
    public ClassMetadata getClassMetadata(EClass eClass) {
        return metadataService != null ? metadataService.getClassMetadata(eClass) : null;
    }

    /**
     * Resolves an EClass from its URI string using the metadata service.
     *
     * @param uri the EClass URI (e.g., "http://example.org/model#//MyClass")
     * @return the resolved EClass, or null if not found or no metadata service
     */
    public EClass resolveEClassByURI(String uri) {
        if (uri == null || uri.isEmpty() || metadataService == null) {
            return null;
        }
        ClassMetadata metadata = metadataService.getClassMetadataByURI(uri);
        return metadata != null ? metadata.getEClass() : null;
    }

    // ========================================================================
    // Custom Value Readers/Writers
    // ========================================================================

    /**
     * Returns the registry for custom value readers and writers.
     * <p>
     * This registry contains named readers/writers that can be referenced
     * by name in feature configurations via {@code valueWriterName} and
     * {@code valueReaderName}.
     * </p>
     *
     * @return the value registry, or null if not configured
     * @see <a href="docs/codec-v2-spec/10-custom-values.md">Spec 10: Custom Value Readers/Writers</a>
     */
    public CodecValueRegistry getValueRegistry() {
        return valueRegistry;
    }

    /**
     * Gets a custom value writer by name from the registry.
     *
     * @param <T> the value type
     * @param <F> the feature type
     * @param name the writer name
     * @return the writer, or null if not found or no registry
     */
    @SuppressWarnings("unchecked")
    public <T, F extends org.eclipse.emf.ecore.EStructuralFeature> CodecValueWriter<T, F> getValueWriter(String name) {
        if (valueRegistry == null || name == null || name.isEmpty()) {
            return null;
        }
        return (CodecValueWriter<T, F>) valueRegistry.getWriter(name).orElse(null);
    }

    /**
     * Gets a custom value reader by name from the registry.
     *
     * @param <T> the value type
     * @param <F> the feature type
     * @param name the reader name
     * @return the reader, or null if not found or no registry
     */
    @SuppressWarnings("unchecked")
    public <T, F extends org.eclipse.emf.ecore.EStructuralFeature> CodecValueReader<T, F> getValueReader(String name) {
        if (valueRegistry == null || name == null || name.isEmpty()) {
            return null;
        }
        return (CodecValueReader<T, F>) valueRegistry.getReader(name).orElse(null);
    }

    // ========================================================================
    // Expand settings
    // ========================================================================

    /**
     * Returns whether all non-containment references should be expanded globally.
     *
     * @return true if all references should be expanded inline
     * @see <a href="docs/codec-v2-spec/07-reference.md#42-expand-inline-serialization">Spec: Expand Inline Serialization</a>
     */
    public boolean isExpandGlobal() {
        return expandGlobal;
    }

    /**
     * Returns the set of specific EReferences to expand.
     *
     * @return unmodifiable set of EReferences to expand
     */
    public Set<EReference> getExpandReferences() {
        return expandReferences;
    }

    /**
     * Returns the set of reference names to expand.
     *
     * @return unmodifiable set of reference names to expand
     */
    public Set<String> getExpandReferenceNames() {
        return expandReferenceNames;
    }

    /**
     * Checks if a specific EReference should be expanded.
     * <p>
     * A reference is expanded if:
     * <ul>
     *   <li>{@link #isExpandGlobal()} returns true, OR</li>
     *   <li>the reference is in {@link #getExpandReferences()}, OR</li>
     *   <li>the reference name is in {@link #getExpandReferenceNames()}</li>
     * </ul>
     * </p>
     *
     * @param reference the EReference to check
     * @return true if the reference should be expanded
     */
    public boolean shouldExpand(EReference reference) {
        if (reference == null) {
            return false;
        }
        if (expandGlobal) {
            return true;
        }
        if (expandReferences.contains(reference)) {
            return true;
        }
        return expandReferenceNames.contains(reference.getName());
    }

    /**
     * Returns the maximum depth for nested expansion.
     *
     * @return the expand depth (default 1)
     */
    public int getExpandDepth() {
        return expandDepth;
    }

    /**
     * Returns whether bi-directional references should be ignored during expansion.
     *
     * @return true if opposite references should be skipped
     */
    public boolean isExpandIgnoreBidirectional() {
        return expandIgnoreBidirectional;
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
        private String refKey = "_ref";
        private boolean useNamesFromExtendedMetaData = false;
        private String globalTypeKey = "_type";
        private TypeStrategy globalTypeStrategy = TypeStrategy.URI;
        private TypeDiscriminatorService typeDiscriminatorService;
        private boolean smartCompression = false;
        private MetadataService metadataService;
        // SuperType global settings
        private String globalSuperTypeKey = "_supertype";
        private boolean validateSuperTypeHierarchy = false;
        // Custom value readers/writers
        private CodecValueRegistry valueRegistry;
        // Expand settings
        private boolean expandGlobal = false;
        private Set<EReference> expandReferences;
        private Set<String> expandReferenceNames;
        private int expandDepth = 1;
        private boolean expandIgnoreBidirectional = true;
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

        public Builder smartCompression(boolean smartCompression) {
            this.smartCompression = smartCompression;
            return this;
        }

        public Builder metadataService(MetadataService metadataService) {
            this.metadataService = metadataService;
            return this;
        }

        public Builder globalSuperTypeKey(String globalSuperTypeKey) {
            this.globalSuperTypeKey = globalSuperTypeKey;
            return this;
        }

        public Builder validateSuperTypeHierarchy(boolean validateSuperTypeHierarchy) {
            this.validateSuperTypeHierarchy = validateSuperTypeHierarchy;
            return this;
        }

        public Builder valueRegistry(CodecValueRegistry valueRegistry) {
            this.valueRegistry = valueRegistry;
            return this;
        }

        public Builder expandGlobal(boolean expandGlobal) {
            this.expandGlobal = expandGlobal;
            return this;
        }

        public Builder expandReferences(Set<EReference> expandReferences) {
            this.expandReferences = expandReferences;
            return this;
        }

        public Builder expandReferenceNames(Set<String> expandReferenceNames) {
            this.expandReferenceNames = expandReferenceNames;
            return this;
        }

        public Builder expand(EReference... references) {
            if (references != null && references.length > 0) {
                if (this.expandReferences == null) {
                    this.expandReferences = new HashSet<>();
                }
                for (EReference ref : references) {
                    if (ref != null) {
                        this.expandReferences.add(ref);
                    }
                }
            }
            return this;
        }

        public Builder expand(String... names) {
            if (names != null && names.length > 0) {
                if (this.expandReferenceNames == null) {
                    this.expandReferenceNames = new HashSet<>();
                }
                for (String name : names) {
                    if (name != null && !name.isEmpty()) {
                        this.expandReferenceNames.add(name);
                    }
                }
            }
            return this;
        }

        public Builder expandDepth(int expandDepth) {
            this.expandDepth = expandDepth;
            return this;
        }

        public Builder expandIgnoreBidirectional(boolean expandIgnoreBidirectional) {
            this.expandIgnoreBidirectional = expandIgnoreBidirectional;
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
