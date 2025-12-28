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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecConfiguration.Builder}.
 * <p>
 * Tests builder methods, immutability, and chaining behavior.
 * </p>
 */
@DisplayName("CodecConfiguration Builder Tests")
class CodecConfigurationBuilderTest {

    // ========================================================================
    // Basic Builder Tests
    // ========================================================================

    @Test
    @DisplayName("builder() returns new Builder instance")
    void builderReturnsNewInstance() {
        CodecConfiguration.Builder builder = CodecConfiguration.builder();
        assertNotNull(builder);
    }

    @Test
    @DisplayName("builder produces immutable configuration")
    void builderProducesImmutableConfiguration() {
        CodecConfiguration config1 = CodecConfiguration.builder().build();
        CodecConfiguration config2 = CodecConfiguration.builder().build();
        assertNotSame(config1, config2);
    }

    @Test
    @DisplayName("defaults() returns new instance each time")
    void defaultsReturnsNewInstanceEachTime() {
        CodecConfiguration config1 = CodecConfiguration.defaults();
        CodecConfiguration config2 = CodecConfiguration.defaults();
        assertNotSame(config1, config2);
    }

    @Test
    @DisplayName("configuration is immutable after build")
    void configurationIsImmutableAfterBuild() {
        CodecConfiguration.Builder builder = CodecConfiguration.builder()
                .typeKey("_type")
                .idKey("_id");

        CodecConfiguration config = builder.build();

        // Modify builder after build
        builder.typeKey("modified_type");
        builder.idKey("modified_id");

        // Original config should not be affected
        assertEquals("_type", config.getTypeKey());
        assertEquals("_id", config.getIdKey());
    }

    // ========================================================================
    // Type Settings
    // ========================================================================

    @Test
    @DisplayName("builder can set serializeType")
    void builderCanSetSerializeType() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(false)
                .build();
        assertFalse(config.isSerializeType());
    }

    @Test
    @DisplayName("builder can set deserializeType")
    void builderCanSetDeserializeType() {
        CodecConfiguration config = CodecConfiguration.builder()
                .deserializeType(true)
                .build();
        assertTrue(config.isDeserializeType());
    }

    @Test
    @DisplayName("builder can set typeKey")
    void builderCanSetTypeKey() {
        CodecConfiguration config = CodecConfiguration.builder()
                .typeKey("eclass")
                .build();
        assertEquals("eclass", config.getTypeKey());
    }

    // ========================================================================
    // ID Settings
    // ========================================================================

    @Test
    @DisplayName("builder can set useId")
    void builderCanSetUseId() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useId(false)
                .build();
        assertFalse(config.isUseId());
    }

    @Test
    @DisplayName("builder can set idKey")
    void builderCanSetIdKey() {
        CodecConfiguration config = CodecConfiguration.builder()
                .idKey("id")
                .build();
        assertEquals("id", config.getIdKey());
    }

    // ========================================================================
    // SuperType Settings
    // ========================================================================

    @Test
    @DisplayName("builder can set superTypeKey")
    void builderCanSetSuperTypeKey() {
        CodecConfiguration config = CodecConfiguration.builder()
                .superTypeKey("_supertypes")
                .build();
        assertEquals("_supertypes", config.getSuperTypeKey());
    }

    @Test
    @DisplayName("builder can set serializeSuperTypes")
    void builderCanSetSerializeSuperTypes() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeSuperTypes(true)
                .build();
        assertTrue(config.isSerializeSuperTypes());
    }

    // ========================================================================
    // Format Settings
    // ========================================================================

    @Test
    @DisplayName("builder can set defaultFormat to STRUCTURED")
    void builderCanSetDefaultFormatToStructured() {
        CodecConfiguration config = CodecConfiguration.builder()
                .defaultFormat(SerializationFormat.STRUCTURED)
                .build();
        assertEquals(SerializationFormat.STRUCTURED, config.getDefaultFormat());
    }

    // ========================================================================
    // Chaining
    // ========================================================================

    @Test
    @DisplayName("builder methods return builder for chaining")
    void builderMethodsReturnBuilderForChaining() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(true)
                .typeKey("_type")
                .useId(true)
                .idKey("_id")
                .serializeSuperTypes(false)
                .superTypeKey("_supertype")
                .serializeNullValue(true)
                .serializeEmptyValue(true)
                .serializeDefaultValue(true)
                .build();

        assertTrue(config.isSerializeType());
        assertEquals("_type", config.getTypeKey());
        assertTrue(config.isUseId());
        assertEquals("_id", config.getIdKey());
        assertFalse(config.isSerializeSuperTypes());
        assertEquals("_supertype", config.getSuperTypeKey());
        assertTrue(config.isSerializeNullValue());
        assertTrue(config.isSerializeEmptyValue());
        assertTrue(config.isSerializeDefaultValue());
    }

    @Test
    @DisplayName("can create fully customized configuration")
    void canCreateFullyCustomizedConfiguration() {
        CodecConfiguration config = CodecConfiguration.builder()
                .defaultFormat(SerializationFormat.STRUCTURED)
                .serializeType(true)
                .deserializeType(true)
                .typeKey("eclass")
                .useId(true)
                .idOnTop(false)
                .serializeIdField(true)
                .idFeatureAsPrimaryKey(false)
                .idKey("identifier")
                .refKey("reference")
                .proxyKey("lazyRef")
                .serializeSuperTypes(true)
                .serializeAllSuperTypes(true)
                .serializeSuperTypesAsArray(false)
                .superTypeKey("parents")
                .serializeDefaultValue(true)
                .serializeEmptyValue(true)
                .serializeNullValue(true)
                .useNamesFromExtendedMetaData(false)
                .writeEnumLiterals(true)
                .sortPropertiesAlphabetically(true)
                .timestampKey("ts")
                .build();

        assertEquals(SerializationFormat.STRUCTURED, config.getDefaultFormat());
        assertTrue(config.isSerializeType());
        assertTrue(config.isDeserializeType());
        assertEquals("eclass", config.getTypeKey());
        assertTrue(config.isUseId());
        assertFalse(config.isIdOnTop());
        assertTrue(config.isSerializeIdField());
        assertFalse(config.isIdFeatureAsPrimaryKey());
        assertEquals("identifier", config.getIdKey());
        assertEquals("reference", config.getRefKey());
        assertEquals("lazyRef", config.getProxyKey());
        assertTrue(config.isSerializeSuperTypes());
        assertTrue(config.isSerializeAllSuperTypes());
        assertFalse(config.isSerializeSuperTypesAsArray());
        assertEquals("parents", config.getSuperTypeKey());
        assertTrue(config.isSerializeDefaultValue());
        assertTrue(config.isSerializeEmptyValue());
        assertTrue(config.isSerializeNullValue());
        assertFalse(config.isUseNamesFromExtendedMetaData());
        assertTrue(config.isWriteEnumLiterals());
        assertTrue(config.isSortPropertiesAlphabetically());
        assertEquals("ts", config.getTimestampKey());
    }
}
