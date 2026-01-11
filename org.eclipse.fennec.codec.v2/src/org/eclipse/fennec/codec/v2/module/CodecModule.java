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
package org.eclipse.fennec.codec.v2.module;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.v2.deser.CodecDeserializers;
import org.eclipse.fennec.codec.v2.ser.CodecSerializers;
import org.eclipse.fennec.codec.v2.value.CodecValueRegistry;
import org.eclipse.fennec.model.metadata.api.MetadataService;

import tools.jackson.core.Version;
import tools.jackson.databind.module.SimpleModule;

/**
 * Jackson module for EMF codec v2 serialization.
 * <p>
 * This module integrates Jackson with EMF serialization using the aspect-based
 * MetadataService architecture. It replaces the v1 CodecModule with a cleaner,
 * more modular design.
 * </p>
 * <p>
 * Key features:
 * <ul>
 *   <li>Uses {@link CodecConfiguration} for all serialization settings</li>
 *   <li>Integrates with {@link MetadataService} for aspect-driven serialization decisions</li>
 *   <li>Supports custom value readers/writers via {@link CodecValueRegistry}</li>
 *   <li>Supports global feature name ignoring</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-jackson-module-integration">Spec 9: Jackson Module Integration</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class CodecModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MODULE_NAME = "fennec-codec-v2-module";

    // ========================================================================
    // Configuration
    // ========================================================================

    private final String moduleName;
    private final CodecConfiguration configuration;
    private final MetadataService metadataService;
    private final CodecValueRegistry valueRegistry;

    // ========================================================================
    // Constructor
    // ========================================================================

    private CodecModule(Builder builder) {
        this.moduleName = builder.moduleName;
        this.configuration = builder.configuration;
        this.metadataService = builder.metadataService;
        this.valueRegistry = builder.valueRegistry;
    }

    // ========================================================================
    // Jackson Module Overrides
    // ========================================================================

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public Version version() {
        return new Version(2, 0, 0, "SNAPSHOT", "org.eclipse.fennec", "org.eclipse.fennec.codec.v2");
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);

        // Create effective configuration by merging module config with defaults
        // Note: At module setup time, load/save options are not available.
        // Those will be provided at Resource.save()/load() time via different mechanism.
        EffectiveCodecConfig effectiveConfig = createEffectiveConfig(Collections.emptyMap());

        // Register serializers with effective configuration
        CodecSerializers serializers = new CodecSerializers(effectiveConfig);
        context.addSerializers(serializers);

        // Register deserializers with effective configuration
        CodecDeserializers deserializers = new CodecDeserializers(effectiveConfig);
        context.addDeserializers(deserializers);
    }

    /**
     * Creates an effective configuration by merging all configuration sources.
     * <p>
     * This method is used at module setup time and can be called with additional
     * load/save options at Resource.save()/load() time.
     * </p>
     *
     * @param options load/save options (may be empty)
     * @return the merged effective configuration
     */
    public EffectiveCodecConfig createEffectiveConfig(java.util.Map<String, Object> options) {
        ConfigurationMerger merger = new ConfigurationMerger(
                configuration,
                metadataService,
                valueRegistry,
                Collections.emptyMap(),  // factory defaults (not yet implemented)
                options
        );
        return merger.merge();
    }

    // ========================================================================
    // Getters
    // ========================================================================

    /**
     * Returns the codec configuration.
     *
     * @return the configuration, never null
     */
    public CodecConfiguration getConfiguration() {
        return configuration;
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
     * @see <a href="docs/codec-v2-serialization-spec.md#167-global-ignore-features">Spec 16.7: Global Ignore Features</a>
     */
    public List<String> getGlobalIgnoreFeatureNames() {
        return configuration.getGlobalIgnoreFeatureNames();
    }

    /**
     * Checks if a feature name should be globally ignored.
     *
     * @param featureName the feature name to check
     * @return true if the feature should be ignored
     */
    public boolean isGloballyIgnored(String featureName) {
        return configuration.isGloballyIgnored(featureName);
    }

    // ========================================================================
    // Convenience Accessors (Delegates to Configuration)
    // ========================================================================

    public boolean isSerializeType() {
        return configuration.isSerializeType();
    }

    public boolean isDeserializeType() {
        return configuration.isDeserializeType();
    }

    public String getTypeKey() {
        return configuration.getTypeKey();
    }

    public boolean isUseId() {
        return configuration.isUseId();
    }

    public boolean isIdOnTop() {
        return configuration.isIdOnTop();
    }

    public boolean isSerializeIdField() {
        return configuration.isSerializeIdField();
    }

    public boolean isIdFeatureAsPrimaryKey() {
        return configuration.isIdFeatureAsPrimaryKey();
    }

    public String getIdKey() {
        return configuration.getIdKey();
    }

    public String getRefKey() {
        return configuration.getRefKey();
    }

    public String getProxyKey() {
        return configuration.getProxyKey();
    }

    public boolean isSerializeSuperTypes() {
        return configuration.isSerializeSuperTypes();
    }

    public boolean isSerializeAllSuperTypes() {
        return configuration.isSerializeAllSuperTypes();
    }

    public boolean isSerializeSuperTypesAsArray() {
        return configuration.isSerializeSuperTypesAsArray();
    }

    public String getSuperTypeKey() {
        return configuration.getSuperTypeKey();
    }

    public boolean isSerializeDefaultValue() {
        return configuration.isSerializeDefaultValue();
    }

    public boolean isSerializeEmptyValue() {
        return configuration.isSerializeEmptyValue();
    }

    public boolean isSerializeNullValue() {
        return configuration.isSerializeNullValue();
    }

    public boolean isUseNamesFromExtendedMetaData() {
        return configuration.isUseNamesFromExtendedMetaData();
    }

    public boolean isWriteEnumLiterals() {
        return configuration.isWriteEnumLiterals();
    }

    public boolean isSortPropertiesAlphabetically() {
        return configuration.isSortPropertiesAlphabetically();
    }

    public String getTimestampKey() {
        return configuration.getTimestampKey();
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

    /**
     * Creates a CodecModule with the given configuration.
     *
     * @param configuration the configuration to use
     * @return a new CodecModule
     */
    public static CodecModule withConfiguration(CodecConfiguration configuration) {
        return new Builder().configuration(configuration).build();
    }

    // ========================================================================
    // Builder
    // ========================================================================

    /**
     * Builder for CodecModule.
     */
    public static class Builder {

        private String moduleName = DEFAULT_MODULE_NAME;
        private CodecConfiguration configuration;
        private MetadataService metadataService;
        private CodecValueRegistry valueRegistry;

        public Builder() {
        }

        /**
         * Sets the module name.
         *
         * @param moduleName the module name
         * @return this builder
         */
        public Builder moduleName(String moduleName) {
            this.moduleName = Objects.requireNonNull(moduleName, "moduleName must not be null");
            return this;
        }

        /**
         * Sets the codec configuration.
         *
         * @param configuration the configuration
         * @return this builder
         */
        public Builder configuration(CodecConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }

        /**
         * Sets the metadata service for aspect-based serialization.
         *
         * @param metadataService the metadata service
         * @return this builder
         */
        public Builder metadataService(MetadataService metadataService) {
            this.metadataService = metadataService;
            return this;
        }

        /**
         * Sets the custom value registry.
         *
         * @param valueRegistry the value registry
         * @return this builder
         */
        public Builder valueRegistry(CodecValueRegistry valueRegistry) {
            this.valueRegistry = valueRegistry;
            return this;
        }

        /**
         * Builds the CodecModule.
         *
         * @return the configured CodecModule
         */
        public CodecModule build() {
            if (configuration == null) {
                configuration = CodecConfiguration.defaults();
            }
            if (valueRegistry == null) {
                valueRegistry = new CodecValueRegistry();
            }
            return new CodecModule(this);
        }
    }
}
