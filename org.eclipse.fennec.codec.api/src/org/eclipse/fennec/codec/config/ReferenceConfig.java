/*
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
package org.eclipse.fennec.codec.config;

import static org.eclipse.fennec.codec.config.ConfigMergeHelper.getBoolean;
import static org.eclipse.fennec.codec.config.ConfigMergeHelper.getEnum;
import static org.eclipse.fennec.codec.config.ConfigMergeHelper.getInteger;
import static org.eclipse.fennec.codec.config.ConfigMergeHelper.getString;

import java.util.Map;

import org.eclipse.fennec.codec.diagnostic.DiagnosticCollector;
import org.eclipse.fennec.model.metadata.SerializationFormat;

/**
 * Immutable reference serialization configuration.
 * <p>
 * This class supports the cascading merge pattern where each configuration layer
 * can override values from the previous layer.
 *
 * @see ConfigProperty
 * @see ConfigMergeHelper
 */
public final class ReferenceConfig implements Mergeable<ReferenceConfig> {

    private final SerializationFormat format;
    private final String refKey;
    private final String refTypeKey;
    private final String proxyKey;
    private final boolean expand;
    private final boolean expandGlobal;
    private final int expandDepth;
    private final boolean expandIgnoreBidirectional;
    private final boolean serializeInstanceType;
    private final String valueReaderName;
    private final String valueWriterName;

    private ReferenceConfig(Builder builder) {
        this.format = builder.format;
        this.refKey = builder.refKey;
        this.refTypeKey = builder.refTypeKey;
        this.proxyKey = builder.proxyKey;
        this.expand = builder.expand;
        this.expandGlobal = builder.expandGlobal;
        this.expandDepth = builder.expandDepth;
        this.expandIgnoreBidirectional = builder.expandIgnoreBidirectional;
        this.serializeInstanceType = builder.serializeInstanceType;
        this.valueReaderName = builder.valueReaderName;
        this.valueWriterName = builder.valueWriterName;
    }

    // ========================================================================
    // Accessors
    // ========================================================================

    /**
     * Returns the serialization format (PLAIN or STRUCTURED).
     * Default: STRUCTURED
     */
    public SerializationFormat getFormat() {
        return format;
    }

    /**
     * Returns the JSON property key for reference value.
     * Default: "$ref"
     */
    public String getRefKey() {
        return refKey;
    }

    /**
     * Returns the JSON property key for type in STRUCTURED format.
     * Default: "_type"
     */
    public String getRefTypeKey() {
        return refTypeKey;
    }

    /**
     * Returns the JSON property key for proxy marker.
     * Default: "$proxy"
     */
    public String getProxyKey() {
        return proxyKey;
    }

    /**
     * Returns whether this specific reference should be expanded inline.
     * Default: false
     */
    public boolean isExpand() {
        return expand;
    }

    /**
     * Returns whether all non-containment references should be expanded globally.
     * Default: false
     */
    public boolean isExpandGlobal() {
        return expandGlobal;
    }

    /**
     * Returns the maximum depth for nested expansion.
     * Default: 1 (only direct references)
     */
    public int getExpandDepth() {
        return expandDepth;
    }

    /**
     * Returns whether to skip bidirectional (opposite) references during expansion.
     * Default: true (skip to prevent cycles)
     */
    public boolean isExpandIgnoreBidirectional() {
        return expandIgnoreBidirectional;
    }

    /**
     * Returns whether to serialize the instance type in STRUCTURED format.
     * Default: true
     */
    public boolean isSerializeInstanceType() {
        return serializeInstanceType;
    }

    /**
     * Returns the custom value reader name, or null if none.
     */
    public String getValueReaderName() {
        return valueReaderName;
    }

    /**
     * Returns the custom value writer name, or null if none.
     */
    public String getValueWriterName() {
        return valueWriterName;
    }

    // ========================================================================
    // Computed properties
    // ========================================================================

    /**
     * Returns whether references should be expanded based on expand flags.
     * A reference is expanded if either expand=true or expandGlobal=true.
     */
    public boolean shouldExpand() {
        return expand || expandGlobal;
    }

    // ========================================================================
    // Merge support
    // ========================================================================

    /**
     * Creates a new config by merging this config with values from a property map.
     *
     * @param source the property map to merge (may be null or empty)
     * @return a new config with merged values, or this if source is null/empty
     */
    @Override
    public ReferenceConfig mergeWith(Map<String, Object> source) {
        if (source == null || source.isEmpty()) {
            return this;
        }

        return toBuilder()
                .format(getEnum(source, ConfigProperty.REF_FORMAT, SerializationFormat.class, this.format))
                .refKey(getString(source, ConfigProperty.REF_KEY, this.refKey))
                .refTypeKey(getString(source, ConfigProperty.REF_TYPE_KEY, this.refTypeKey))
                .proxyKey(getString(source, ConfigProperty.PROXY_KEY, this.proxyKey))
                .expand(getBoolean(source, ConfigProperty.EXPAND, this.expand))
                .expandGlobal(getBoolean(source, ConfigProperty.EXPAND_GLOBAL, this.expandGlobal))
                .expandDepth(getInteger(source, ConfigProperty.EXPAND_DEPTH, this.expandDepth))
                .expandIgnoreBidirectional(getBoolean(source, ConfigProperty.EXPAND_IGNORE_BIDIRECTIONAL, this.expandIgnoreBidirectional))
                .serializeInstanceType(getBoolean(source, ConfigProperty.SERIALIZE_INSTANCE_TYPE, this.serializeInstanceType))
                .valueReaderName(getString(source, ConfigProperty.VALUE_READER_NAME, this.valueReaderName))
                .valueWriterName(getString(source, ConfigProperty.VALUE_WRITER_NAME, this.valueWriterName))
                .build();
    }

    // ========================================================================
    // Validation support
    // ========================================================================

    /**
     * Validates this config and returns a validated (possibly normalized) config.
     * <p>
     * Reference config constraints:
     * <ul>
     *   <li>refTypeKey is only meaningful for STRUCTURED format</li>
     *   <li>expandDepth greater than 1 is not yet supported</li>
     *   <li>expandIgnoreBidirectional only matters when expand is true</li>
     * </ul>
     */
    @Override
    public ReferenceConfig validate(DiagnosticCollector diagnostics) {
        // Constraint: refTypeKey only meaningful for STRUCTURED format
        if (format != SerializationFormat.STRUCTURED && refTypeKey != null
                && !refTypeKey.equals(ConfigProperty.REF_TYPE_KEY.getDefaultValue())) {
            diagnostics.addWarning(
                "refTypeKey is ignored when refFormat is not STRUCTURED",
                "ReferenceConfig.validate");
        }

        // Constraint: expandDepth > 1 not yet supported
        if (expandDepth > 1) {
            diagnostics.addWarning(
                "expandDepth values greater than 1 are not yet supported; using depth=1",
                "ReferenceConfig.validate");
        }

        // Constraint: expand settings only matter when expanding
        if (!shouldExpand() && expandIgnoreBidirectional != ConfigProperty.EXPAND_IGNORE_BIDIRECTIONAL.<Boolean>getDefaultValue()) {
            diagnostics.addInfo(
                "expandIgnoreBidirectional is ignored when expand is not enabled",
                "ReferenceConfig.validate");
        }

        return this;
    }

    // ========================================================================
    // Builder support
    // ========================================================================

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .format(this.format)
                .refKey(this.refKey)
                .refTypeKey(this.refTypeKey)
                .proxyKey(this.proxyKey)
                .expand(this.expand)
                .expandGlobal(this.expandGlobal)
                .expandDepth(this.expandDepth)
                .expandIgnoreBidirectional(this.expandIgnoreBidirectional)
                .serializeInstanceType(this.serializeInstanceType)
                .valueReaderName(this.valueReaderName)
                .valueWriterName(this.valueWriterName);
    }

    /**
     * Creates a config with all default values from {@link ConfigProperty}.
     */
    public static ReferenceConfig defaults() {
        return builder().build();
    }

    public static final class Builder {
        private SerializationFormat format = SerializationFormat.valueOf(ConfigProperty.REF_FORMAT.getDefaultValue());
        private String refKey = ConfigProperty.REF_KEY.getDefaultValue();
        private String refTypeKey = ConfigProperty.REF_TYPE_KEY.getDefaultValue();
        private String proxyKey = ConfigProperty.PROXY_KEY.getDefaultValue();
        private boolean expand = ConfigProperty.EXPAND.getDefaultValue();
        private boolean expandGlobal = ConfigProperty.EXPAND_GLOBAL.getDefaultValue();
        private int expandDepth = ConfigProperty.EXPAND_DEPTH.getDefaultValue();
        private boolean expandIgnoreBidirectional = ConfigProperty.EXPAND_IGNORE_BIDIRECTIONAL.getDefaultValue();
        private boolean serializeInstanceType = ConfigProperty.SERIALIZE_INSTANCE_TYPE.getDefaultValue();
        private String valueReaderName = ConfigProperty.VALUE_READER_NAME.getDefaultValue();
        private String valueWriterName = ConfigProperty.VALUE_WRITER_NAME.getDefaultValue();

        private Builder() {}

        public Builder format(SerializationFormat format) {
            this.format = format;
            return this;
        }

        public Builder refKey(String refKey) {
            this.refKey = refKey;
            return this;
        }

        public Builder refTypeKey(String refTypeKey) {
            this.refTypeKey = refTypeKey;
            return this;
        }

        public Builder proxyKey(String proxyKey) {
            this.proxyKey = proxyKey;
            return this;
        }

        public Builder expand(boolean expand) {
            this.expand = expand;
            return this;
        }

        public Builder expandGlobal(boolean expandGlobal) {
            this.expandGlobal = expandGlobal;
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

        public Builder serializeInstanceType(boolean serializeInstanceType) {
            this.serializeInstanceType = serializeInstanceType;
            return this;
        }

        public Builder valueReaderName(String valueReaderName) {
            this.valueReaderName = valueReaderName;
            return this;
        }

        public Builder valueWriterName(String valueWriterName) {
            this.valueWriterName = valueWriterName;
            return this;
        }

        public ReferenceConfig build() {
            return new ReferenceConfig(this);
        }
    }
}
