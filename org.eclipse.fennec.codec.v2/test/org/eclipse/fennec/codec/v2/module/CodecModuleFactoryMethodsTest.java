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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecModule} factory methods.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-jackson-module-integration">Spec 9: Jackson Module Integration</a>
 */
@DisplayName("CodecModule factory methods")
class CodecModuleFactoryMethodsTest {

    @Test
    @DisplayName("withDefaults creates module with default configuration")
    void withDefaultsCreatesModuleWithDefaultConfiguration() {
        CodecModule module = CodecModule.withDefaults();

        assertNotNull(module);
        assertNotNull(module.getConfiguration());
        assertTrue(module.isSerializeType());
        assertTrue(module.isUseId());
    }

    @Test
    @DisplayName("withConfiguration creates module with given configuration")
    void withConfigurationCreatesModuleWithGivenConfiguration() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(false)
                .useId(false)
                .build();

        CodecModule module = CodecModule.withConfiguration(config);

        assertSame(config, module.getConfiguration());
        assertFalse(module.isSerializeType());
        assertFalse(module.isUseId());
    }

    @Test
    @DisplayName("builder returns new builder instance")
    void builderReturnsNewBuilderInstance() {
        CodecModule.Builder builder = CodecModule.builder();

        assertNotNull(builder);
    }
}
