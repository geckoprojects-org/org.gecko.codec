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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests Type and SuperType dependency (Spec 16.4.1).
 * <p>
 * When serializeType is false, isSerializeSuperTypes() should always
 * return false because supertypes are a form of type information.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#164-configuration-dependencies">Spec 16.4: Configuration Dependencies</a>
 */
@DisplayName("Type → SuperType Dependency - Spec 16.4.1")
class CodecConfigurationTypeSuperTypeDependencyTest {

    @Test
    @DisplayName("serializeSuperTypes returns false when serializeType is false")
    void serializeSuperTypesReturnsFalseWhenSerializeTypeIsFalse() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(false)
                .serializeSuperTypes(true)  // explicitly enabled, but should be ineffective
                .build();

        assertFalse(config.isSerializeType());
        assertFalse(config.isSerializeSuperTypes(),
                "serializeSuperTypes should be false when serializeType is false");
    }

    @Test
    @DisplayName("serializeSuperTypes returns true only when both serializeType and serializeSuperTypes are true")
    void serializeSuperTypesReturnsTrueWhenBothEnabled() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(true)
                .serializeSuperTypes(true)
                .build();

        assertTrue(config.isSerializeType());
        assertTrue(config.isSerializeSuperTypes());
    }

    @Test
    @DisplayName("serializeSuperTypes returns false when serializeSuperTypes is false even with serializeType true")
    void serializeSuperTypesReturnsFalseWhenExplicitlyDisabled() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(true)
                .serializeSuperTypes(false)
                .build();

        assertTrue(config.isSerializeType());
        assertFalse(config.isSerializeSuperTypes());
    }

    @Test
    @DisplayName("serializeSuperTypes returns false when both are false")
    void serializeSuperTypesReturnsFalseWhenBothDisabled() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(false)
                .serializeSuperTypes(false)
                .build();

        assertFalse(config.isSerializeType());
        assertFalse(config.isSerializeSuperTypes());
    }
}
