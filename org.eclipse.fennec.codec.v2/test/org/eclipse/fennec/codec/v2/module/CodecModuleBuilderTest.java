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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.value.CodecValueRegistry;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecModule.Builder}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-jackson-module-integration">Spec 9: Jackson Module Integration</a>
 */
@DisplayName("CodecModule.Builder")
class CodecModuleBuilderTest {

    @Test
    @DisplayName("builder creates module with default configuration")
    void builderCreatesModuleWithDefaultConfiguration() {
        CodecModule module = CodecModule.builder().build();

        assertNotNull(module.getConfiguration());
        assertNotNull(module.getValueRegistry());
    }

    @Test
    @DisplayName("builder accepts custom module name")
    void builderAcceptsCustomModuleName() {
        CodecModule module = CodecModule.builder()
                .moduleName("custom-module")
                .build();

        assertEquals("custom-module", module.getModuleName());
    }

    @Test
    @DisplayName("builder rejects null module name")
    void builderRejectsNullModuleName() {
        assertThrows(NullPointerException.class, () ->
                CodecModule.builder().moduleName(null));
    }

    @Test
    @DisplayName("builder accepts custom configuration")
    void builderAcceptsCustomConfiguration() {
        CodecConfiguration config = CodecConfiguration.builder()
                .serializeType(false)
                .build();

        CodecModule module = CodecModule.builder()
                .configuration(config)
                .build();

        assertSame(config, module.getConfiguration());
    }

    @Test
    @DisplayName("builder accepts metadata service")
    void builderAcceptsMetadataService() {
        MetadataService service = mock(MetadataService.class);

        CodecModule module = CodecModule.builder()
                .metadataService(service)
                .build();

        assertSame(service, module.getMetadataService());
    }

    @Test
    @DisplayName("builder accepts custom value registry")
    void builderAcceptsCustomValueRegistry() {
        CodecValueRegistry registry = new CodecValueRegistry();

        CodecModule module = CodecModule.builder()
                .valueRegistry(registry)
                .build();

        assertSame(registry, module.getValueRegistry());
    }

    @Test
    @DisplayName("builder uses global ignore from configuration")
    void builderUsesGlobalIgnoreFromConfiguration() {
        CodecConfiguration config = CodecConfiguration.builder()
                .globalIgnore("feature1")
                .globalIgnore("feature2")
                .build();

        CodecModule module = CodecModule.builder()
                .configuration(config)
                .build();

        assertEquals(2, module.getGlobalIgnoreFeatureNames().size());
    }
}
