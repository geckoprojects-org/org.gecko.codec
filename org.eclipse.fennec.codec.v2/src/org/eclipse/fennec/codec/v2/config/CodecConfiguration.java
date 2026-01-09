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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.model.metadata.IdKeyMode;
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

    /** ID serialization format (PLAIN or STRUCTURED) */
    private final SerializationFormat idFormat;

    /** Separator for combining multiple ID features (PLAIN format) */
    private final String idSeparator;

    /** List of feature names to use as combined ID */
    private final List<String> idFeatures;

    /** ID key mode (ID_ONLY, BOTH, FEATURE_ONLY) */
    private final IdKeyMode idKeyMode;

    /** Whether to serialize separator in STRUCTURED ID format */
    private final boolean idSerializeSeparator;

    /** Key for separator field in STRUCTURED ID format */
    private final String idSeparatorKey;

    // ========================================================================
    // Reference Serialization Settings
    // ========================================================================

    /** Key for reference URI */
    private final String refKey;

    /** Key for proxy objects */
    private final String proxyKey;

    // ========================================================================
    // Expand Settings (for non-containment references)
    // ========================================================================

    /**
     * Whether to expand ALL non-containment references inline.
     * <p>
     * When enabled, all non-containment references are serialized as full objects
     * instead of proxy references. This is a global flag that affects all references.
     * </p>
     *
     * @see <a href="docs/codec-v2-spec/07-reference.md#42-expand-inline-serialization">Spec: Expand Inline Serialization</a>
     */
    private final boolean expandGlobal;

    /**
     * Set of specific EReferences to expand inline (by EReference object).
     * <p>
     * Only these references will be expanded, regardless of expandGlobal setting.
     * </p>
     */
    private final Set<EReference> expandReferences;

    /**
     * Set of reference names to expand inline (by String name).
     * <p>
     * At runtime, these names are resolved against the current EClass to find
     * the matching EReference.
     * </p>
     */
    private final Set<String> expandReferenceNames;

    /**
     * Maximum depth for nested expansion.
     * <p>
     * When expanding references, this limits how deep the expansion goes.
     * Default is 1 (only immediate references).
     * </p>
     */
    private final int expandDepth;

    /**
     * Whether to skip bi-directional (opposite) references during expansion.
     * <p>
     * When true (default), opposite references are not expanded to prevent cycles.
     * </p>
     */
    private final boolean expandIgnoreBidirectional;

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

    /** Whether to validate supertype hierarchy on deserialization */
    private final boolean validateSuperTypeHierarchy;

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
        this.idFormat = builder.idFormat;
        this.idSeparator = builder.idSeparator;
        this.idFeatures = builder.idFeatures != null ? List.copyOf(builder.idFeatures) : List.of();
        this.idKeyMode = builder.idKeyMode;
        this.idSerializeSeparator = builder.idSerializeSeparator;
        this.idSeparatorKey = builder.idSeparatorKey;
        this.refKey = builder.refKey;
        this.proxyKey = builder.proxyKey;
        this.expandGlobal = builder.expandGlobal;
        this.expandReferences = builder.expandReferences != null
                ? Set.copyOf(builder.expandReferences)
                : Collections.emptySet();
        this.expandReferenceNames = builder.expandReferenceNames != null
                ? Set.copyOf(builder.expandReferenceNames)
                : Collections.emptySet();
        this.expandDepth = builder.expandDepth;
        this.expandIgnoreBidirectional = builder.expandIgnoreBidirectional;
        this.serializeSuperTypes = builder.serializeSuperTypes;
        this.serializeAllSuperTypes = builder.serializeAllSuperTypes;
        this.serializeSuperTypesAsArray = builder.serializeSuperTypesAsArray;
        this.superTypeKey = builder.superTypeKey;
        this.validateSuperTypeHierarchy = builder.validateSuperTypeHierarchy;
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

    public SerializationFormat getIdFormat() {
        return idFormat;
    }

    public String getIdSeparator() {
        return idSeparator;
    }

    public List<String> getIdFeatures() {
        return idFeatures;
    }

    public IdKeyMode getIdKeyMode() {
        return idKeyMode;
    }

    /**
     * Returns whether the separator should be serialized in STRUCTURED ID format.
     * <p>
     * When true (default), the separator is included in the JSON output,
     * allowing deserialization without pre-configured separator knowledge.
     * When false, the separator must be configured for deserialization.
     * </p>
     *
     * @return true if separator should be serialized
     */
    public boolean isIdSerializeSeparator() {
        return idSerializeSeparator;
    }

    /**
     * Returns the JSON key for the separator field in STRUCTURED ID format.
     *
     * @return the separator key (default "_separator")
     */
    public String getIdSeparatorKey() {
        return idSeparatorKey;
    }

    public String getRefKey() {
        return refKey;
    }

    public String getProxyKey() {
        return proxyKey;
    }

    // ========================================================================
    // Expand Getters
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
    // SuperType Getters
    // ========================================================================

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

    /**
     * Returns whether to validate supertype hierarchy during deserialization.
     * <p>
     * When true, the deserializer validates that declared supertypes in JSON
     * match the resolved EClass's actual supertypes. Deserialization fails
     * if there's a mismatch.
     * </p>
     * <p>
     * Default is false (no validation - supertypes are ignored during deserialization).
     * </p>
     *
     * @return true if supertype hierarchy should be validated
     */
    public boolean isValidateSuperTypeHierarchy() {
        return validateSuperTypeHierarchy;
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
        private SerializationFormat idFormat = SerializationFormat.PLAIN;
        private String idSeparator = "-";
        private List<String> idFeatures;
        private IdKeyMode idKeyMode = IdKeyMode.ID_ONLY;
        private boolean idSerializeSeparator = true;
        private String idSeparatorKey = "separator";
        private String refKey = "_ref";
        private String proxyKey = "_proxy";
        // Expand settings
        private boolean expandGlobal = false;
        private Set<EReference> expandReferences;
        private Set<String> expandReferenceNames;
        private int expandDepth = 1;
        private boolean expandIgnoreBidirectional = true;
        private boolean serializeSuperTypes = false;
        private boolean serializeAllSuperTypes = false;
        private boolean serializeSuperTypesAsArray = true;
        private String superTypeKey = "_supertype";
        private boolean validateSuperTypeHierarchy = false;
        private boolean serializeDefaultValue = false;
        private boolean serializeEmptyValue = false;
        private boolean serializeNullValue = false;
        private boolean smartCompression = false;
        private boolean useNamesFromExtendedMetaData = false;  // Changed to false in v2 (was true in v1)
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

        public Builder idFormat(SerializationFormat idFormat) {
            this.idFormat = idFormat;
            return this;
        }

        public Builder idSeparator(String idSeparator) {
            this.idSeparator = idSeparator;
            return this;
        }

        public Builder idFeatures(List<String> idFeatures) {
            this.idFeatures = idFeatures;
            return this;
        }

        public Builder idKeyMode(IdKeyMode idKeyMode) {
            this.idKeyMode = idKeyMode;
            return this;
        }

        /**
         * Sets whether to serialize the separator in STRUCTURED ID format.
         * <p>
         * When true (default), the separator is included in the JSON output
         * as a separate field (e.g., "_separator": "-"), allowing deserialization
         * without pre-configured separator knowledge.
         * When false, the separator is not serialized and must be configured
         * for deserialization.
         * </p>
         *
         * @param idSerializeSeparator true to serialize the separator
         * @return this builder
         */
        public Builder idSerializeSeparator(boolean idSerializeSeparator) {
            this.idSerializeSeparator = idSerializeSeparator;
            return this;
        }

        /**
         * Sets the JSON key for the separator field in STRUCTURED ID format.
         * <p>
         * Default is "_separator".
         * </p>
         *
         * @param idSeparatorKey the key for the separator field
         * @return this builder
         */
        public Builder idSeparatorKey(String idSeparatorKey) {
            this.idSeparatorKey = idSeparatorKey;
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

        // Expand

        /**
         * Enables or disables global expansion of all non-containment references.
         * <p>
         * When enabled, all non-containment references are serialized inline
         * as full objects instead of proxy references.
         * </p>
         *
         * @param expandGlobal true to expand all references globally
         * @return this builder
         * @see <a href="docs/codec-v2-spec/07-reference.md#42-expand-inline-serialization">Spec: Expand Inline Serialization</a>
         */
        public Builder expandGlobal(boolean expandGlobal) {
            this.expandGlobal = expandGlobal;
            return this;
        }

        /**
         * Sets the maximum depth for nested expansion.
         *
         * @param depth the maximum expansion depth (default 1)
         * @return this builder
         */
        public Builder expandDepth(int depth) {
            this.expandDepth = depth;
            return this;
        }

        /**
         * Sets whether to ignore bi-directional references during expansion.
         *
         * @param ignore true to skip opposite references (default true)
         * @return this builder
         */
        public Builder expandIgnoreBidirectional(boolean ignore) {
            this.expandIgnoreBidirectional = ignore;
            return this;
        }

        /**
         * Adds specific EReferences to expand inline.
         * <p>
         * These references will be expanded regardless of the expandGlobal setting.
         * </p>
         *
         * @param references the EReferences to expand
         * @return this builder
         */
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

        /**
         * Adds specific reference names to expand inline.
         * <p>
         * At runtime, these names are resolved against the current EClass.
         * </p>
         *
         * @param names the reference names to expand
         * @return this builder
         */
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

        /**
         * Adds a list of EReferences to expand inline.
         *
         * @param references the list of EReferences to expand
         * @return this builder
         */
        public Builder expand(List<EReference> references) {
            if (references != null && !references.isEmpty()) {
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

        /**
         * Adds a set of reference names to expand inline.
         *
         * @param names the set of reference names to expand
         * @return this builder
         */
        public Builder expandNames(Set<String> names) {
            if (names != null && !names.isEmpty()) {
                if (this.expandReferenceNames == null) {
                    this.expandReferenceNames = new HashSet<>();
                }
                this.expandReferenceNames.addAll(names);
            }
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

        /**
         * Sets whether to validate supertype hierarchy during deserialization.
         * <p>
         * When true, the deserializer validates that declared supertypes in JSON
         * match the resolved EClass's actual supertypes. Deserialization fails
         * if there's a mismatch.
         * </p>
         *
         * @param validateSuperTypeHierarchy true to enable validation
         * @return this builder
         */
        public Builder validateSuperTypeHierarchy(boolean validateSuperTypeHierarchy) {
            this.validateSuperTypeHierarchy = validateSuperTypeHierarchy;
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
         * Adds multiple feature names to the global ignore list.
         *
         * @param featureNames the feature names to ignore
         * @return this builder
         */
        public Builder globalIgnoreFeatures(String... featureNames) {
            if (featureNames != null && featureNames.length > 0) {
                if (this.globalIgnoreFeatureNames == null) {
                    this.globalIgnoreFeatureNames = new ArrayList<>();
                }
                for (String name : featureNames) {
                    if (name != null && !name.isEmpty()) {
                        this.globalIgnoreFeatureNames.add(name);
                    }
                }
            }
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
