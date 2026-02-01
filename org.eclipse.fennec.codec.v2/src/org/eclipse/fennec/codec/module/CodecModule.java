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
package org.eclipse.fennec.codec.module;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.fennec.codec.api.value.CodecValueRegistry;
import org.eclipse.fennec.codec.config.ConfigurationResolver;
import org.eclipse.fennec.codec.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.deser.CodecDeserializers;
import org.eclipse.fennec.codec.diagnostic.DiagnosticCollector;
import org.eclipse.fennec.codec.metadata.type.TypeDiscriminatorService;
import org.eclipse.fennec.codec.ser.CodecSerializers;
import org.eclipse.fennec.model.metadata.api.MetadataService;

import tools.jackson.core.Version;
import tools.jackson.databind.module.SimpleModule;

/**
 * Jackson module for EMF codec serialization.
 * <p>
 * This module integrates Jackson with EMF serialization using the
 * {@link ConfigurationResolver} for on-demand configuration resolution.
 * </p>
 * <p>
 * Key features:
 * <ul>
 *   <li>Uses {@link ConfigurationResolver} for spec-compliant configuration resolution</li>
 *   <li>Integrates with {@link MetadataService} for aspect-driven serialization decisions</li>
 *   <li>Supports custom value readers/writers via {@link CodecValueRegistry}</li>
 *   <li>Supports global feature name ignoring</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/09-jackson-module.md">Spec 9: Jackson Module Integration</a>
 * @author Mark Hoffmann
 * @since 2026-02-01
 */
public class CodecModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MODULE_NAME = "fennec-codec-module";

    private final String moduleName;
    private final ConfigurationResolver resolver;
    private final MetadataService metadataService;
    private final TypeDiscriminatorService typeDiscriminatorService;
    private final CodecValueRegistry valueRegistry;
    private final List<String> globalIgnoreFeatures;
    private final boolean sortPropertiesAlphabetically;
    private final boolean smartCompression;
    private final boolean useNamesFromExtendedMetaData;

    private CodecModule(Builder builder) {
        this.moduleName = builder.moduleName;
        this.resolver = builder.resolver;
        this.metadataService = builder.metadataService;
        this.typeDiscriminatorService = builder.typeDiscriminatorService;
        this.valueRegistry = builder.valueRegistry;
        this.globalIgnoreFeatures = builder.globalIgnoreFeatures != null
                ? List.copyOf(builder.globalIgnoreFeatures) : List.of();
        this.sortPropertiesAlphabetically = builder.sortPropertiesAlphabetically;
        this.smartCompression = builder.smartCompression;
        this.useNamesFromExtendedMetaData = builder.useNamesFromExtendedMetaData;
    }

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public Version version() {
        return new Version(2, 0, 0, "SNAPSHOT", "org.eclipse.fennec", "org.eclipse.fennec.codec");
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);

        // Create effective configuration
        EffectiveCodecConfig effectiveConfig = createEffectiveConfig();

        // Register serializers with effective configuration
        CodecSerializers serializers = new CodecSerializers(effectiveConfig);
        context.addSerializers(serializers);

        // Register deserializers with effective configuration
        CodecDeserializers deserializers = new CodecDeserializers(effectiveConfig);
        context.addDeserializers(deserializers);
    }

    /**
     * Creates an effective configuration from the module's resolver and settings.
     * <p>
     * This method is used at module setup time. For Resource.save()/load() with
     * additional options, a separate {@link EffectiveCodecConfig} should be created
     * with an options-aware resolver.
     * </p>
     *
     * @return the effective codec configuration
     */
    public EffectiveCodecConfig createEffectiveConfig() {
        return EffectiveCodecConfig.builder()
                .resolver(resolver)
                .diagnostics(new DiagnosticCollector())
                .metadataService(metadataService)
                .typeDiscriminatorService(typeDiscriminatorService)
                .valueRegistry(valueRegistry)
                .globalIgnoreFeatures(globalIgnoreFeatures)
                .sortPropertiesAlphabetically(sortPropertiesAlphabetically)
                .smartCompression(smartCompression)
                .useNamesFromExtendedMetaData(useNamesFromExtendedMetaData)
                .build();
    }

    /**
     * Creates an effective configuration with a custom diagnostics collector.
     *
     * @param diagnostics the diagnostics collector to use
     * @return the effective codec configuration
     */
    public EffectiveCodecConfig createEffectiveConfig(DiagnosticCollector diagnostics) {
        return EffectiveCodecConfig.builder()
                .resolver(resolver)
                .diagnostics(diagnostics != null ? diagnostics : new DiagnosticCollector())
                .metadataService(metadataService)
                .typeDiscriminatorService(typeDiscriminatorService)
                .valueRegistry(valueRegistry)
                .globalIgnoreFeatures(globalIgnoreFeatures)
                .sortPropertiesAlphabetically(sortPropertiesAlphabetically)
                .smartCompression(smartCompression)
                .useNamesFromExtendedMetaData(useNamesFromExtendedMetaData)
                .build();
    }

    // ========================================================================
    // Getters
    // ========================================================================

    /**
     * Returns the configuration resolver.
     *
     * @return the resolver, never null
     */
    public ConfigurationResolver getResolver() {
        return resolver;
    }

    /**
     * Returns the metadata service for aspect-based serialization decisions.
     *
     * @return the metadata service, may be null if not configured
     */
    public MetadataService getMetadataService() {
        return metadataService;
    }

    /**
     * Returns the type discriminator service for MAPPED strategy.
     *
     * @return the discriminator service, may be null
     */
    public TypeDiscriminatorService getTypeDiscriminatorService() {
        return typeDiscriminatorService;
    }

    /**
     * Returns the custom value registry.
     *
     * @return the value registry, never null
     */
    public CodecValueRegistry getValueRegistry() {
        return valueRegistry;
    }

    /**
     * Returns the list of globally ignored feature names.
     *
     * @return unmodifiable list of feature names to ignore
     */
    public List<String> getGlobalIgnoreFeatureNames() {
        return globalIgnoreFeatures;
    }

    /**
     * Checks if a feature name should be globally ignored.
     *
     * @param featureName the feature name to check
     * @return true if the feature should be ignored
     */
    public boolean isGloballyIgnored(String featureName) {
        return featureName != null && globalIgnoreFeatures.contains(featureName);
    }

    /**
     * Returns whether smart compression is enabled.
     */
    public boolean isSmartCompression() {
        return smartCompression;
    }

    /**
     * Returns whether properties should be sorted alphabetically.
     */
    public boolean isSortPropertiesAlphabetically() {
        return sortPropertiesAlphabetically;
    }

    /**
     * Returns whether to use names from ExtendedMetaData.
     */
    public boolean isUseNamesFromExtendedMetaData() {
        return useNamesFromExtendedMetaData;
    }

    // ========================================================================
    // Factory Methods
    // ========================================================================

    /**
     * Creates a new builder for CodecModule.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a CodecModule with default configuration.
     *
     * @return a new CodecModule with defaults
     */
    public static CodecModule withDefaults() {
        return new Builder().build();
    }

    // ========================================================================
    // Builder
    // ========================================================================

    /**
     * Builder for CodecModule.
     */
    public static class Builder {

        private String moduleName = DEFAULT_MODULE_NAME;
        private ConfigurationResolver resolver;
        private MetadataService metadataService;
        private TypeDiscriminatorService typeDiscriminatorService;
        private CodecValueRegistry valueRegistry;
        private List<String> globalIgnoreFeatures;
        private boolean sortPropertiesAlphabetically = false;
        private boolean smartCompression = false;
        private boolean useNamesFromExtendedMetaData = false;

        public Builder() {
        }

        public Builder moduleName(String moduleName) {
            this.moduleName = Objects.requireNonNull(moduleName, "moduleName must not be null");
            return this;
        }

        public Builder resolver(ConfigurationResolver resolver) {
            this.resolver = resolver;
            return this;
        }

        public Builder metadataService(MetadataService metadataService) {
            this.metadataService = metadataService;
            return this;
        }

        public Builder typeDiscriminatorService(TypeDiscriminatorService typeDiscriminatorService) {
            this.typeDiscriminatorService = typeDiscriminatorService;
            return this;
        }

        public Builder valueRegistry(CodecValueRegistry valueRegistry) {
            this.valueRegistry = valueRegistry;
            return this;
        }

        public Builder globalIgnoreFeatures(List<String> globalIgnoreFeatures) {
            this.globalIgnoreFeatures = globalIgnoreFeatures;
            return this;
        }

        public Builder sortPropertiesAlphabetically(boolean sortPropertiesAlphabetically) {
            this.sortPropertiesAlphabetically = sortPropertiesAlphabetically;
            return this;
        }

        public Builder smartCompression(boolean smartCompression) {
            this.smartCompression = smartCompression;
            return this;
        }

        public Builder useNamesFromExtendedMetaData(boolean useNamesFromExtendedMetaData) {
            this.useNamesFromExtendedMetaData = useNamesFromExtendedMetaData;
            return this;
        }

        public CodecModule build() {
            if (resolver == null) {
                resolver = ConfigurationResolver.defaults();
            }
            if (valueRegistry == null) {
                valueRegistry = new CodecValueRegistry();
            }
            return new CodecModule(this);
        }
    }
}
