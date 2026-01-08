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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CodecConfiguration} default values match the specification.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#174-configuration-hierarchy-tests">Spec 17.4 C1: CodecConfiguration defaults</a>
 */
@DisplayName("CodecConfiguration Default Values - Spec 17.4: C1")
class CodecConfigurationDefaultsTest {

    @Test
    @DisplayName("defaults() returns configuration with all default values")
    void defaultsReturnsDefaultConfiguration() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertNotNull(config);
    }

    @Test
    @DisplayName("default format is PLAIN")
    void defaultFormatIsPlain() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertEquals(SerializationFormat.PLAIN, config.getDefaultFormat());
    }

    // ========================================================================
    // Type defaults
    // ========================================================================

    @Test
    @DisplayName("default serializeType is true")
    void defaultSerializeTypeIsTrue() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertTrue(config.isSerializeType());
    }

    @Test
    @DisplayName("default deserializeType is false")
    void defaultDeserializeTypeIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isDeserializeType());
    }

    @Test
    @DisplayName("default typeKey is _type")
    void defaultTypeKeyIsUnderscoreType() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertEquals("_type", config.getTypeKey());
    }

    // ========================================================================
    // ID defaults
    // ========================================================================

    @Test
    @DisplayName("default useId is true")
    void defaultUseIdIsTrue() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertTrue(config.isUseId());
    }

    @Test
    @DisplayName("default idOnTop is true")
    void defaultIdOnTopIsTrue() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertTrue(config.isIdOnTop());
    }

    @Test
    @DisplayName("default serializeIdField is false")
    void defaultSerializeIdFieldIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isSerializeIdField());
    }

    @Test
    @DisplayName("default idFeatureAsPrimaryKey is true")
    void defaultIdFeatureAsPrimaryKeyIsTrue() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertTrue(config.isIdFeatureAsPrimaryKey());
    }

    @Test
    @DisplayName("default idKey is _id")
    void defaultIdKeyIsUnderscoreId() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertEquals("_id", config.getIdKey());
    }

    // ========================================================================
    // Reference defaults
    // ========================================================================

    @Test
    @DisplayName("default refKey is $ref")
    void defaultRefKeyIsDollarRef() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertEquals("$ref", config.getRefKey());
    }

    @Test
    @DisplayName("default proxyKey is _proxy")
    void defaultProxyKeyIsUnderscoreProxy() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertEquals("_proxy", config.getProxyKey());
    }

    // ========================================================================
    // SuperType defaults
    // ========================================================================

    @Test
    @DisplayName("default serializeSuperTypes is false")
    void defaultSerializeSuperTypesIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isSerializeSuperTypes());
    }

    @Test
    @DisplayName("default serializeAllSuperTypes is false")
    void defaultSerializeAllSuperTypesIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isSerializeAllSuperTypes());
    }

    @Test
    @DisplayName("default serializeSuperTypesAsArray is true")
    void defaultSerializeSuperTypesAsArrayIsTrue() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertTrue(config.isSerializeSuperTypesAsArray());
    }

    @Test
    @DisplayName("default superTypeKey is _supertype (singular)")
    void defaultSuperTypeKeyIsUnderscoreSupertype() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertEquals("_supertype", config.getSuperTypeKey());
    }

    // ========================================================================
    // Value serialization defaults
    // ========================================================================

    @Test
    @DisplayName("default serializeDefaultValue is false")
    void defaultSerializeDefaultValueIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isSerializeDefaultValue());
    }

    @Test
    @DisplayName("default serializeEmptyValue is false")
    void defaultSerializeEmptyValueIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isSerializeEmptyValue());
    }

    @Test
    @DisplayName("default serializeNullValue is false")
    void defaultSerializeNullValueIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isSerializeNullValue());
    }

    // ========================================================================
    // Misc defaults
    // ========================================================================

    @Test
    @DisplayName("default useNamesFromExtendedMetaData is false")
    void defaultUseNamesFromExtendedMetaDataIsFalse() {
        // Changed in v2: default is now false (was true in v1)
        // See spec docs/codec-v2-spec/08-feature.md#3-extended-metadata-names
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isUseNamesFromExtendedMetaData());
    }

    @Test
    @DisplayName("default writeEnumLiterals is false")
    void defaultWriteEnumLiteralsIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isWriteEnumLiterals());
    }

    @Test
    @DisplayName("default sortPropertiesAlphabetically is false")
    void defaultSortPropertiesAlphabeticallyIsFalse() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertFalse(config.isSortPropertiesAlphabetically());
    }

    @Test
    @DisplayName("default timestampKey is _timestamp")
    void defaultTimestampKeyIsUnderscoreTimestamp() {
        CodecConfiguration config = CodecConfiguration.defaults();
        assertEquals("_timestamp", config.getTimestampKey());
    }
}
