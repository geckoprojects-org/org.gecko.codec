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
 * Tests ID sub-options dependency (Spec 16.4.3).
 * <p>
 * All ID-related sub-options (idOnTop, serializeIdField, idFeatureAsPrimaryKey) depend on useId.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#164-configuration-dependencies">Spec 16.4: Configuration Dependencies</a>
 */
@DisplayName("ID → Sub-Options Dependency - Spec 16.4.3")
class CodecConfigurationIdSubOptionsDependencyTest {

    @Test
    @DisplayName("idOnTop returns false when useId is false")
    void idOnTopReturnsFalseWhenUseIdIsFalse() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useId(false)
                .idOnTop(true)  // explicitly enabled, but should be ineffective
                .build();

        assertFalse(config.isUseId());
        assertFalse(config.isIdOnTop(),
                "idOnTop should be false when useId is false");
    }

    @Test
    @DisplayName("serializeIdField returns false when useId is false")
    void serializeIdFieldReturnsFalseWhenUseIdIsFalse() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useId(false)
                .serializeIdField(true)  // explicitly enabled, but should be ineffective
                .build();

        assertFalse(config.isUseId());
        assertFalse(config.isSerializeIdField(),
                "serializeIdField should be false when useId is false");
    }

    @Test
    @DisplayName("idFeatureAsPrimaryKey returns false when useId is false")
    void idFeatureAsPrimaryKeyReturnsFalseWhenUseIdIsFalse() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useId(false)
                .idFeatureAsPrimaryKey(true)  // explicitly enabled, but should be ineffective
                .build();

        assertFalse(config.isUseId());
        assertFalse(config.isIdFeatureAsPrimaryKey(),
                "idFeatureAsPrimaryKey should be false when useId is false");
    }

    @Test
    @DisplayName("all ID sub-options return false when useId is false")
    void allIdSubOptionsReturnFalseWhenUseIdIsFalse() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useId(false)
                .idOnTop(true)
                .serializeIdField(true)
                .idFeatureAsPrimaryKey(true)
                .build();

        assertFalse(config.isUseId());
        assertFalse(config.isIdOnTop());
        assertFalse(config.isSerializeIdField());
        assertFalse(config.isIdFeatureAsPrimaryKey());
    }

    @Test
    @DisplayName("ID sub-options work correctly when useId is true")
    void idSubOptionsWorkWhenUseIdIsTrue() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useId(true)
                .idOnTop(true)
                .serializeIdField(true)
                .idFeatureAsPrimaryKey(true)
                .build();

        assertTrue(config.isUseId());
        assertTrue(config.isIdOnTop());
        assertTrue(config.isSerializeIdField());
        assertTrue(config.isIdFeatureAsPrimaryKey());
    }

    @Test
    @DisplayName("ID sub-options can be individually disabled when useId is true")
    void idSubOptionsCanBeIndividuallyDisabled() {
        CodecConfiguration config = CodecConfiguration.builder()
                .useId(true)
                .idOnTop(false)
                .serializeIdField(false)
                .idFeatureAsPrimaryKey(false)
                .build();

        assertTrue(config.isUseId());
        assertFalse(config.isIdOnTop());
        assertFalse(config.isSerializeIdField());
        assertFalse(config.isIdFeatureAsPrimaryKey());
    }
}
