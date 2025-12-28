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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecModule} configuration delegation methods.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-jackson-module-integration">Spec 9: Jackson Module Integration</a>
 */
@DisplayName("CodecModule configuration delegation")
class CodecModuleConfigurationDelegationTest {

    @Test
    @DisplayName("delegates type serialization settings")
    void delegatesTypeSerializationSettings() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(false)
                .deserializeType(true)
                .typeKey("customType")
                .build();

        CodecModule module = CodecModule.withConfiguration(config);

        assertFalse(module.isSerializeType());
        assertTrue(module.isDeserializeType());
        assertEquals("customType", module.getTypeKey());
    }

    @Test
    @DisplayName("delegates ID serialization settings")
    void delegatesIdSerializationSettings() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useId(true)
                .idOnTop(false)
                .serializeIdField(true)
                .idFeatureAsPrimaryKey(false)
                .idKey("customId")
                .build();

        CodecModule module = CodecModule.withConfiguration(config);

        assertTrue(module.isUseId());
        assertFalse(module.isIdOnTop());
        assertTrue(module.isSerializeIdField());
        assertFalse(module.isIdFeatureAsPrimaryKey());
        assertEquals("customId", module.getIdKey());
    }

    @Test
    @DisplayName("delegates reference settings")
    void delegatesReferenceSettings() {
        CodecConfiguration config = CodecConfiguration.builder()
                .refKey("customRef")
                .proxyKey("customProxy")
                .build();

        CodecModule module = CodecModule.withConfiguration(config);

        assertEquals("customRef", module.getRefKey());
        assertEquals("customProxy", module.getProxyKey());
    }

    @Test
    @DisplayName("delegates supertype settings")
    void delegatesSupertypeSettings() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(true)
                .serializeSuperTypes(true)
                .serializeAllSuperTypes(true)
                .serializeSuperTypesAsArray(false)
                .superTypeKey("customSuperType")
                .build();

        CodecModule module = CodecModule.withConfiguration(config);

        assertTrue(module.isSerializeSuperTypes());
        assertTrue(module.isSerializeAllSuperTypes());
        assertFalse(module.isSerializeSuperTypesAsArray());
        assertEquals("customSuperType", module.getSuperTypeKey());
    }

    @Test
    @DisplayName("delegates value serialization settings")
    void delegatesValueSerializationSettings() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeDefaultValue(true)
                .serializeEmptyValue(true)
                .serializeNullValue(true)
                .build();

        CodecModule module = CodecModule.withConfiguration(config);

        assertTrue(module.isSerializeDefaultValue());
        assertTrue(module.isSerializeEmptyValue());
        assertTrue(module.isSerializeNullValue());
    }

    @Test
    @DisplayName("delegates misc settings")
    void delegatesMiscSettings() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useNamesFromExtendedMetaData(false)
                .writeEnumLiterals(true)
                .sortPropertiesAlphabetically(true)
                .timestampKey("customTimestamp")
                .build();

        CodecModule module = CodecModule.withConfiguration(config);

        assertFalse(module.isUseNamesFromExtendedMetaData());
        assertTrue(module.isWriteEnumLiterals());
        assertTrue(module.isSortPropertiesAlphabetically());
        assertEquals("customTimestamp", module.getTimestampKey());
    }
}
