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

import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.IdStrategy;
import org.eclipse.fennec.model.metadata.SerializationFormat;

/**
 * Fully resolved, immutable ID serialization configuration.
 * <p>
 * All properties are final and represent the effective merged values from
 * all configuration sources (load/save options, module config, model aspects).
 * No resolution logic is needed at serialization time.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#4-id-serialization">Spec 4: ID Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public final class EffectiveIdConfig {

    private final boolean enabled;
    private final boolean onTop;
    private final String key;
    private final IdStrategy strategy;
    private final IdKeyMode keyMode;
    private final SerializationFormat format;
    private final String separator;
    private final boolean serializeSeparator;
    private final String separatorKey;
    private final List<String> idFeatures;
    private final String valueWriterName;
    private final String valueReaderName;

    private EffectiveIdConfig(Builder builder) {
        this.enabled = builder.enabled;
        this.onTop = builder.onTop;
        this.key = builder.key;
        this.strategy = builder.strategy;
        this.keyMode = builder.keyMode;
        this.format = builder.format;
        this.separator = builder.separator;
        this.serializeSeparator = builder.serializeSeparator;
        this.separatorKey = builder.separatorKey;
        this.idFeatures = List.copyOf(builder.idFeatures);
        this.valueWriterName = builder.valueWriterName;
        this.valueReaderName = builder.valueReaderName;
    }

    /**
     * Returns whether ID serialization is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Returns whether the ID should appear at the top of the JSON object.
     */
    public boolean isOnTop() {
        return onTop;
    }

    /**
     * Returns the JSON property key for the ID field.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the ID strategy (ID_FIELD, COMBINED, etc.).
     */
    public IdStrategy getStrategy() {
        return strategy;
    }

    /**
     * Returns the key mode (ID_ONLY, BOTH, FEATURE_ONLY).
     */
    public IdKeyMode getKeyMode() {
        return keyMode;
    }

    /**
     * Returns the serialization format (PLAIN, STRUCTURED).
     */
    public SerializationFormat getFormat() {
        return format;
    }

    /**
     * Returns the separator for combining multiple ID features.
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Returns whether the separator should be serialized in STRUCTURED format.
     * <p>
     * When true, the separator is included in the JSON output as a separate field,
     * allowing deserialization without pre-configured separator knowledge.
     * When false, the separator must be configured for deserialization.
     * </p>
     * <p>Default: true</p>
     */
    public boolean isSerializeSeparator() {
        return serializeSeparator;
    }

    /**
     * Returns the JSON key for the separator field.
     * <p>
     * Default depends on format:
     * <ul>
     *   <li>PLAIN format: "_separator" (underscore prefix for root-level key)</li>
     *   <li>STRUCTURED format: "separator" (no prefix for inner key)</li>
     * </ul>
     * </p>
     */
    public String getSeparatorKey() {
        return separatorKey;
    }

    /**
     * Returns the effective separator key based on the current format.
     * <p>
     * If separatorKey was explicitly set, returns that value.
     * Otherwise, returns the format-appropriate default:
     * <ul>
     *   <li>PLAIN: "_separator"</li>
     *   <li>STRUCTURED: "separator"</li>
     * </ul>
     * </p>
     */
    public String getEffectiveSeparatorKey() {
        if (separatorKey != null) {
            return separatorKey;
        }
        return format == SerializationFormat.STRUCTURED ? "separator" : "_separator";
    }

    /**
     * Returns the list of feature names for combined ID strategy.
     */
    public List<String> getIdFeatures() {
        return idFeatures;
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
     * Creates a new builder with default values.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for EffectiveIdConfig.
     */
    public static final class Builder {
        private boolean enabled = true;
        private boolean onTop = true;
        private String key = "_id";
        private IdStrategy strategy = IdStrategy.ID_FIELD;
        private IdKeyMode keyMode = IdKeyMode.ID_ONLY;
        private SerializationFormat format = SerializationFormat.PLAIN;
        private String separator = "-";
        private boolean serializeSeparator = true;
        private String separatorKey = null;  // null = use format-appropriate default
        private List<String> idFeatures = List.of();
        private String valueWriterName;
        private String valueReaderName;

        private Builder() {}

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder onTop(boolean onTop) {
            this.onTop = onTop;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder strategy(IdStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public Builder keyMode(IdKeyMode keyMode) {
            this.keyMode = keyMode;
            return this;
        }

        public Builder format(SerializationFormat format) {
            this.format = format;
            return this;
        }

        public Builder separator(String separator) {
            this.separator = separator;
            return this;
        }

        public Builder serializeSeparator(boolean serializeSeparator) {
            this.serializeSeparator = serializeSeparator;
            return this;
        }

        public Builder separatorKey(String separatorKey) {
            this.separatorKey = separatorKey;
            return this;
        }

        public Builder idFeatures(List<String> idFeatures) {
            this.idFeatures = idFeatures != null ? idFeatures : List.of();
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

        public EffectiveIdConfig build() {
            return new EffectiveIdConfig(this);
        }
    }
}
