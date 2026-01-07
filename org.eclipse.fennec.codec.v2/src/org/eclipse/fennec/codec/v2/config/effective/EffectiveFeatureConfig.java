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

import java.util.Objects;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;

/**
 * Fully resolved, immutable feature-level codec configuration.
 * <p>
 * All properties are final and represent the effective merged values from
 * all configuration sources (load/save options, module config, model aspects).
 * No resolution logic is needed at serialization time.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-feature-serialization">Spec 9: Feature Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public final class EffectiveFeatureConfig {

    private final EStructuralFeature feature;
    private final String key;
    private final boolean serialize;
    private final boolean serializeNull;
    private final boolean serializeEmpty;
    private final boolean serializeDefaults;
    private final String valueWriterName;
    private final String valueReaderName;
    private final EnumSerializationStrategy enumSerialization;

    private EffectiveFeatureConfig(Builder builder) {
        this.feature = Objects.requireNonNull(builder.feature, "feature must not be null");
        this.key = Objects.requireNonNull(builder.key, "key must not be null");
        this.serialize = builder.serialize;
        this.serializeNull = builder.serializeNull;
        this.serializeEmpty = builder.serializeEmpty;
        this.serializeDefaults = builder.serializeDefaults;
        this.valueWriterName = builder.valueWriterName;
        this.valueReaderName = builder.valueReaderName;
        this.enumSerialization = builder.enumSerialization;
    }

    /**
     * Returns the EStructuralFeature this configuration applies to.
     */
    public EStructuralFeature getFeature() {
        return feature;
    }

    /**
     * Returns the JSON property key for this feature.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns whether this feature should be serialized.
     * When false, the feature is treated as transient.
     */
    public boolean isSerialize() {
        return serialize;
    }

    /**
     * Returns whether null values should be serialized.
     */
    public boolean isSerializeNull() {
        return serializeNull;
    }

    /**
     * Returns whether empty collections should be serialized.
     */
    public boolean isSerializeEmpty() {
        return serializeEmpty;
    }

    /**
     * Returns whether default values should be serialized.
     */
    public boolean isSerializeDefaults() {
        return serializeDefaults;
    }

    /**
     * Returns the custom value writer name, or null if none.
     */
    public String getValueWriterName() {
        return valueWriterName;
    }

    /**
     * Returns the custom value reader name, or null if none.
     */
    public String getValueReaderName() {
        return valueReaderName;
    }

    /**
     * Returns the enum serialization strategy.
     * Defaults to LITERAL if not explicitly set.
     */
    public EnumSerializationStrategy getEnumSerialization() {
        return enumSerialization != null ? enumSerialization : EnumSerializationStrategy.LITERAL;
    }

    /**
     * Creates a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for EffectiveFeatureConfig.
     */
    public static final class Builder {
        private EStructuralFeature feature;
        private String key;
        private boolean serialize = true;
        private boolean serializeNull = false;
        private boolean serializeEmpty = false;
        private boolean serializeDefaults = false;
        private String valueWriterName;
        private String valueReaderName;
        private EnumSerializationStrategy enumSerialization;

        private Builder() {}

        public Builder feature(EStructuralFeature feature) {
            this.feature = feature;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder serialize(boolean serialize) {
            this.serialize = serialize;
            return this;
        }

        public Builder serializeNull(boolean serializeNull) {
            this.serializeNull = serializeNull;
            return this;
        }

        public Builder serializeEmpty(boolean serializeEmpty) {
            this.serializeEmpty = serializeEmpty;
            return this;
        }

        public Builder serializeDefaults(boolean serializeDefaults) {
            this.serializeDefaults = serializeDefaults;
            return this;
        }

        public Builder valueWriterName(String valueWriterName) {
            this.valueWriterName = valueWriterName;
            return this;
        }

        public Builder valueReaderName(String valueReaderName) {
            this.valueReaderName = valueReaderName;
            return this;
        }

        public Builder enumSerialization(EnumSerializationStrategy enumSerialization) {
            this.enumSerialization = enumSerialization;
            return this;
        }

        public EffectiveFeatureConfig build() {
            // Default key to feature name if not set
            if (key == null && feature != null) {
                key = feature.getName();
            }
            return new EffectiveFeatureConfig(this);
        }
    }
}
