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
 * Tests SuperType sub-options dependency (Spec 16.4.2).
 * <p>
 * serializeAllSuperTypes depends on serializeSuperTypes (which in turn depends on serializeType).
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#164-configuration-dependencies">Spec 16.4: Configuration Dependencies</a>
 */
@DisplayName("SuperType → AllSuperTypes Dependency - Spec 16.4.2")
class CodecConfigurationSuperTypeSubOptionsDependencyTest {

    @Test
    @DisplayName("serializeAllSuperTypes returns false when serializeSuperTypes is false")
    void serializeAllSuperTypesReturnsFalseWhenSerializeSuperTypesIsFalse() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(true)
                .serializeSuperTypes(false)
                .serializeAllSuperTypes(true)  // explicitly enabled, but should be ineffective
                .build();

        assertFalse(config.isSerializeSuperTypes());
        assertFalse(config.isSerializeAllSuperTypes(),
                "serializeAllSuperTypes should be false when serializeSuperTypes is false");
    }

    @Test
    @DisplayName("serializeAllSuperTypes returns false when serializeType is false (transitive)")
    void serializeAllSuperTypesReturnsFalseWhenSerializeTypeIsFalse() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(false)
                .serializeSuperTypes(true)
                .serializeAllSuperTypes(true)  // explicitly enabled, but should be ineffective
                .build();

        assertFalse(config.isSerializeType());
        assertFalse(config.isSerializeSuperTypes());
        assertFalse(config.isSerializeAllSuperTypes(),
                "serializeAllSuperTypes should be false when serializeType is false");
    }

    @Test
    @DisplayName("serializeAllSuperTypes returns true only when full chain is enabled")
    void serializeAllSuperTypesReturnsTrueWhenFullChainEnabled() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(true)
                .serializeSuperTypes(true)
                .serializeAllSuperTypes(true)
                .build();

        assertTrue(config.isSerializeType());
        assertTrue(config.isSerializeSuperTypes());
        assertTrue(config.isSerializeAllSuperTypes());
    }

    @Test
    @DisplayName("serializeAllSuperTypes returns false when explicitly disabled")
    void serializeAllSuperTypesReturnsFalseWhenExplicitlyDisabled() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(true)
                .serializeSuperTypes(true)
                .serializeAllSuperTypes(false)
                .build();

        assertTrue(config.isSerializeSuperTypes());
        assertFalse(config.isSerializeAllSuperTypes());
    }
}
