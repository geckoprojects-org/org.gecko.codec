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
package org.eclipse.fennec.codec.v2.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fennec.model.metadata.SerializationFormat;

/**
 * Central configuration holder for codec.v2 serialization settings.
 * <p>
 * This class holds all codec-wide defaults that can be overridden at various levels:
 * <ol>
 *   <li>Load/Save options (highest priority)</li>
 *   <li>ResourceFactory defaults</li>
 *   <li>This CodecConfiguration (codec module config)</li>
 *   <li>EAnnotations on model (via MetadataService aspects)</li>
 *   <li>Built-in defaults (lowest priority)</li>
 * </ol>
 * </p>
 *
 * @author Mark Hoffmann
 * @since 2025-12-11
 */
public class CodecConfiguration {

    // ========================================================================
    // Format Settings
    // ========================================================================

    /** Codec-wide default serialization format */
    private final SerializationFormat defaultFormat;

    // ========================================================================
    // Type Serialization Settings
    // ========================================================================

    /** Whether to serialize type information */
    private final boolean serializeType;

    /** Whether to deserialize type information */
    private final boolean deserializeType;

    /** Default key for type property */
    private final String typeKey;

    // ========================================================================
    // ID Serialization Settings
    // ========================================================================

    /** Whether to use ID serialization */
    private final boolean useId;

    /** Whether ID should appear first in output */
    private final boolean idOnTop;

    /** Whether to serialize the ID field as a regular feature too */
    private final boolean serializeIdField;

    /** Whether ID feature acts as primary key */
    private final boolean idFeatureAsPrimaryKey;

    /** Default key for ID property */
    private final String idKey;

    // ========================================================================
    // Reference Serialization Settings
    // ========================================================================

    /** Key for reference URI */
    private final String refKey;

    /** Key for proxy objects */
    private final String proxyKey;

    // ========================================================================
    // SuperType Serialization Settings
    // ========================================================================

    /** Whether to serialize supertypes */
    private final boolean serializeSuperTypes;

    /** Whether to serialize ALL supertypes (including EMF types) */
    private final boolean serializeAllSuperTypes;

    /** Whether supertypes are serialized as array */
    private final boolean serializeSuperTypesAsArray;

    /** Key for supertypes property */
    private final String superTypeKey;

    // ========================================================================
    // Value Serialization Settings
    // ========================================================================

    /** Whether to serialize default values */
    private final boolean serializeDefaultValue;

    /** Whether to serialize empty collections */
    private final boolean serializeEmptyValue;

    /** Whether to serialize null values */
    private final boolean serializeNullValue;

    // ========================================================================
    // Smart Compression Settings
    // ========================================================================

    /**
     * Whether to use smart compression.
     * <p>
     * When enabled, type information (_type) is omitted when it can be inferred:
     * <ul>
     *   <li>For contained objects: omit _type when instance type == reference type</li>
     *   <li>For non-contained refs: omit _type when instance type == reference type</li>
     *   <li>For supertypes: use plain names instead of URIs for same-schema types</li>
     * </ul>
     * </p>
     *
     * @see <a href="docs/codec-v2-serialization-spec.md#121-smart-compression">Spec 12.1: Smart Compression</a>
     */
    private final boolean smartCompression;

    // ========================================================================
    // Misc Settings
    // ========================================================================

    /** Whether to use names from ExtendedMetaData annotation */
    private final boolean useNamesFromExtendedMetaData;

    /** Whether to write enum literals instead of names */
    private final boolean writeEnumLiterals;

    /** Whether to sort properties alphabetically */
    private final boolean sortPropertiesAlphabetically;

    /** Key for timestamp property */
    private final String timestampKey;

    /** List of feature names to globally ignore during serialization */
    private final List<String> globalIgnoreFeatureNames;

    // ========================================================================
    // Constructor
    // ========================================================================

    private CodecConfiguration(Builder builder) {
        this.defaultFormat = builder.defaultFormat;
        this.serializeType = builder.serializeType;
        this.deserializeType = builder.deserializeType;
        this.typeKey = builder.typeKey;
        this.useId = builder.useId;
        this.idOnTop = builder.idOnTop;
        this.serializeIdField = builder.serializeIdField;
        this.idFeatureAsPrimaryKey = builder.idFeatureAsPrimaryKey;
        this.idKey = builder.idKey;
        this.refKey = builder.refKey;
        this.proxyKey = builder.proxyKey;
        this.serializeSuperTypes = builder.serializeSuperTypes;
        this.serializeAllSuperTypes = builder.serializeAllSuperTypes;
        this.serializeSuperTypesAsArray = builder.serializeSuperTypesAsArray;
        this.superTypeKey = builder.superTypeKey;
        this.serializeDefaultValue = builder.serializeDefaultValue;
        this.serializeEmptyValue = builder.serializeEmptyValue;
        this.serializeNullValue = builder.serializeNullValue;
        this.smartCompression = builder.smartCompression;
        this.useNamesFromExtendedMetaData = builder.useNamesFromExtendedMetaData;
        this.writeEnumLiterals = builder.writeEnumLiterals;
        this.sortPropertiesAlphabetically = builder.sortPropertiesAlphabetically;
        this.timestampKey = builder.timestampKey;
        this.globalIgnoreFeatureNames = builder.globalIgnoreFeatureNames != null
                ? List.copyOf(builder.globalIgnoreFeatureNames)
                : Collections.emptyList();
    }

    // ========================================================================
    // Getters
    // ========================================================================

    public SerializationFormat getDefaultFormat() {
        return defaultFormat;
    }

    public boolean isSerializeType() {
        return serializeType;
    }

    public boolean isDeserializeType() {
        return deserializeType;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public boolean isUseId() {
        return useId;
    }

    /**
     * Returns whether ID should appear first in output.
     * <p>
     * This returns the <b>effective</b> value, considering dependencies:
     * If {@link #isUseId()} is false, this always returns false.
     * </p>
     *
     * @return true if ID should appear first
     * @see <a href="docs/codec-v2-serialization-spec.md#1643-id-sub-options-dependency">Spec 16.4.3: ID Sub-Options Dependency</a>
     */
    public boolean isIdOnTop() {
        return useId && idOnTop;
    }

    /**
     * Returns whether to serialize the ID field as a regular feature too.
     * <p>
     * This returns the <b>effective</b> value, considering dependencies:
     * If {@link #isUseId()} is false, this always returns false.
     * </p>
     *
     * @return true if ID field should be serialized as feature
     * @see <a href="docs/codec-v2-serialization-spec.md#1643-id-sub-options-dependency">Spec 16.4.3: ID Sub-Options Dependency</a>
     */
    public boolean isSerializeIdField() {
        return useId && serializeIdField;
    }

    /**
     * Returns whether ID feature acts as primary key.
     * <p>
     * This returns the <b>effective</b> value, considering dependencies:
     * If {@link #isUseId()} is false, this always returns false.
     * </p>
     *
     * @return true if ID feature acts as primary key
     * @see <a href="docs/codec-v2-serialization-spec.md#1643-id-sub-options-dependency">Spec 16.4.3: ID Sub-Options Dependency</a>
     */
    public boolean isIdFeatureAsPrimaryKey() {
        return useId && idFeatureAsPrimaryKey;
    }

    public String getIdKey() {
        return idKey;
    }

    public String getRefKey() {
        return refKey;
    }

    public String getProxyKey() {
        return proxyKey;
    }

    /**
     * Returns whether supertypes should be serialized.
     * <p>
     * This returns the <b>effective</b> value, considering dependencies:
     * If {@link #isSerializeType()} is false, this always returns false
     * because supertypes are a form of type information.
     * </p>
     *
     * @return true if supertypes should be serialized
     * @see <a href="docs/codec-v2-serialization-spec.md#1641-type-and-supertype-dependency">Spec 16.4.1: Type and SuperType Dependency</a>
     */
    public boolean isSerializeSuperTypes() {
        return serializeType && serializeSuperTypes;
    }

    /**
     * Returns whether ALL supertypes (including EMF types) should be serialized.
     * <p>
     * This returns the <b>effective</b> value, considering dependencies:
     * If {@link #isSerializeSuperTypes()} is false, this always returns false.
     * </p>
     *
     * @return true if all supertypes should be serialized
     * @see <a href="docs/codec-v2-serialization-spec.md#1642-supertype-sub-options-dependency">Spec 16.4.2: SuperType Sub-Options Dependency</a>
     */
    public boolean isSerializeAllSuperTypes() {
        return isSerializeSuperTypes() && serializeAllSuperTypes;
    }

    /**
     * Returns whether supertypes should be serialized as array.
     * <p>
     * This returns the <b>effective</b> value, considering dependencies:
     * If {@link #isSerializeSuperTypes()} is false, this returns the configured
     * value but it will have no effect since supertypes won't be serialized.
     * </p>
     *
     * @return true if supertypes should be serialized as array
     */
    public boolean isSerializeSuperTypesAsArray() {
        return serializeSuperTypesAsArray;
    }

    public String getSuperTypeKey() {
        return superTypeKey;
    }

    public boolean isSerializeDefaultValue() {
        return serializeDefaultValue;
    }

    public boolean isSerializeEmptyValue() {
        return serializeEmptyValue;
    }

    public boolean isSerializeNullValue() {
        return serializeNullValue;
    }

    /**
     * Returns whether smart compression is enabled.
     * <p>
     * When enabled, type information (_type) is omitted when it can be inferred
     * from the reference declaration or context.
     * </p>
     *
     * @return true if smart compression is enabled
     * @see <a href="docs/codec-v2-serialization-spec.md#121-smart-compression">Spec 12.1: Smart Compression</a>
     */
    public boolean isSmartCompression() {
        return smartCompression;
    }

    public boolean isUseNamesFromExtendedMetaData() {
        return useNamesFromExtendedMetaData;
    }

    public boolean isWriteEnumLiterals() {
        return writeEnumLiterals;
    }

    public boolean isSortPropertiesAlphabetically() {
        return sortPropertiesAlphabetically;
    }

    public String getTimestampKey() {
        return timestampKey;
    }

    /**
     * Returns the list of globally ignored feature names.
     *
     * @return unmodifiable list of feature names to ignore
     * @see <a href="docs/codec-v2-serialization-spec.md#167-global-ignore-features">Spec 16.7: Global Ignore Features</a>
     */
    public List<String> getGlobalIgnoreFeatureNames() {
        return globalIgnoreFeatureNames;
    }

    /**
     * Checks if a feature name should be globally ignored.
     *
     * @param featureName the feature name to check
     * @return true if the feature should be ignored
     */
    public boolean isGloballyIgnored(String featureName) {
        return featureName != null && globalIgnoreFeatureNames.contains(featureName);
    }

    // ========================================================================
    // Factory Methods
    // ========================================================================

    /**
     * Creates a new builder with built-in defaults.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the default configuration with built-in defaults.
     *
     * @return default CodecConfiguration
     */
    public static CodecConfiguration defaults() {
        return new Builder().build();
    }

    // ========================================================================
    // Builder
    // ========================================================================

    /**
     * Builder for CodecConfiguration.
     */
    public static class Builder {

        // Defaults matching v1 CodecModule.Builder
        private SerializationFormat defaultFormat = SerializationFormat.PLAIN;
        private boolean serializeType = true;
        private boolean deserializeType = false;
        private String typeKey = "_type";
        private boolean useId = true;
        private boolean idOnTop = true;
        private boolean serializeIdField = false;
        private boolean idFeatureAsPrimaryKey = true;
        private String idKey = "_id";
        private String refKey = "$ref";
        private String proxyKey = "_proxy";
        private boolean serializeSuperTypes = false;
        private boolean serializeAllSuperTypes = false;
        private boolean serializeSuperTypesAsArray = true;
        private String superTypeKey = "_supertype";
        private boolean serializeDefaultValue = false;
        private boolean serializeEmptyValue = false;
        private boolean serializeNullValue = false;
        private boolean smartCompression = false;
        private boolean useNamesFromExtendedMetaData = true;
        private boolean writeEnumLiterals = false;
        private boolean sortPropertiesAlphabetically = false;
        private String timestampKey = "_timestamp";
        private List<String> globalIgnoreFeatureNames;

        public Builder() {
        }

        // Format
        public Builder defaultFormat(SerializationFormat format) {
            this.defaultFormat = format;
            return this;
        }

        // Type
        public Builder serializeType(boolean serializeType) {
            this.serializeType = serializeType;
            return this;
        }

        public Builder deserializeType(boolean deserializeType) {
            this.deserializeType = deserializeType;
            return this;
        }

        public Builder typeKey(String typeKey) {
            this.typeKey = typeKey;
            return this;
        }

        // ID
        public Builder useId(boolean useId) {
            this.useId = useId;
            return this;
        }

        public Builder idOnTop(boolean idOnTop) {
            this.idOnTop = idOnTop;
            return this;
        }

        public Builder serializeIdField(boolean serializeIdField) {
            this.serializeIdField = serializeIdField;
            return this;
        }

        public Builder idFeatureAsPrimaryKey(boolean idFeatureAsPrimaryKey) {
            this.idFeatureAsPrimaryKey = idFeatureAsPrimaryKey;
            return this;
        }

        public Builder idKey(String idKey) {
            this.idKey = idKey;
            return this;
        }

        // Reference
        public Builder refKey(String refKey) {
            this.refKey = refKey;
            return this;
        }

        public Builder proxyKey(String proxyKey) {
            this.proxyKey = proxyKey;
            return this;
        }

        // SuperType
        public Builder serializeSuperTypes(boolean serializeSuperTypes) {
            this.serializeSuperTypes = serializeSuperTypes;
            return this;
        }

        public Builder serializeAllSuperTypes(boolean serializeAllSuperTypes) {
            this.serializeAllSuperTypes = serializeAllSuperTypes;
            return this;
        }

        public Builder serializeSuperTypesAsArray(boolean serializeSuperTypesAsArray) {
            this.serializeSuperTypesAsArray = serializeSuperTypesAsArray;
            return this;
        }

        public Builder superTypeKey(String superTypeKey) {
            this.superTypeKey = superTypeKey;
            return this;
        }

        // Values
        public Builder serializeDefaultValue(boolean serializeDefaultValue) {
            this.serializeDefaultValue = serializeDefaultValue;
            return this;
        }

        public Builder serializeEmptyValue(boolean serializeEmptyValue) {
            this.serializeEmptyValue = serializeEmptyValue;
            return this;
        }

        public Builder serializeNullValue(boolean serializeNullValue) {
            this.serializeNullValue = serializeNullValue;
            return this;
        }

        // Smart Compression
        /**
         * Enables or disables smart compression.
         * <p>
         * When enabled, type information is omitted when it can be inferred.
         * Default is OFF (false).
         * </p>
         *
         * @param smartCompression true to enable smart compression
         * @return this builder
         * @see <a href="docs/codec-v2-serialization-spec.md#121-smart-compression">Spec 12.1: Smart Compression</a>
         */
        public Builder smartCompression(boolean smartCompression) {
            this.smartCompression = smartCompression;
            return this;
        }

        // Misc
        public Builder useNamesFromExtendedMetaData(boolean useNamesFromExtendedMetaData) {
            this.useNamesFromExtendedMetaData = useNamesFromExtendedMetaData;
            return this;
        }

        public Builder writeEnumLiterals(boolean writeEnumLiterals) {
            this.writeEnumLiterals = writeEnumLiterals;
            return this;
        }

        public Builder sortPropertiesAlphabetically(boolean sortPropertiesAlphabetically) {
            this.sortPropertiesAlphabetically = sortPropertiesAlphabetically;
            return this;
        }

        public Builder timestampKey(String timestampKey) {
            this.timestampKey = timestampKey;
            return this;
        }

        /**
         * Sets the list of globally ignored feature names.
         *
         * @param featureNames the feature names to ignore
         * @return this builder
         */
        public Builder globalIgnoreFeatureNames(List<String> featureNames) {
            this.globalIgnoreFeatureNames = featureNames;
            return this;
        }

        /**
         * Adds a single feature name to the global ignore list.
         *
         * @param featureName the feature name to ignore
         * @return this builder
         */
        public Builder globalIgnore(String featureName) {
            if (this.globalIgnoreFeatureNames == null) {
                this.globalIgnoreFeatureNames = new ArrayList<>();
            }
            this.globalIgnoreFeatureNames.add(featureName);
            return this;
        }

        /**
         * Builds the CodecConfiguration.
         *
         * @return configured CodecConfiguration instance
         */
        public CodecConfiguration build() {
            return new CodecConfiguration(this);
        }
    }
}
