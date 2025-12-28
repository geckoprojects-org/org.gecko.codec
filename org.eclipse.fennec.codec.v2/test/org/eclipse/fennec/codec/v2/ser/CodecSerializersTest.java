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
package org.eclipse.fennec.codec.v2.ser;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.v2.module.CodecModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecSerializers}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-jackson-module-integration">Spec 9: Jackson Module Integration</a>
 */
@DisplayName("CodecSerializers")
class CodecSerializersTest {

    private EffectiveCodecConfig effectiveConfig;
    private CodecSerializers serializers;

    @BeforeEach
    void setUp() {
        CodecModule codecModule = CodecModule.withDefaults();
        effectiveConfig = codecModule.createEffectiveConfig(Collections.emptyMap());
        serializers = new CodecSerializers(effectiveConfig);
    }

    @Test
    @DisplayName("constructor creates serializers")
    void constructorCreatesSerializers() {
        assertNotNull(serializers);
    }

    @Test
    @DisplayName("can be created with custom effective config")
    void canBeCreatedWithCustomEffectiveConfig() {
        CodecModule customModule = CodecModule.builder()
                .moduleName("custom")
                .build();
        EffectiveCodecConfig customConfig = customModule.createEffectiveConfig(Collections.emptyMap());
        CodecSerializers customSerializers = new CodecSerializers(customConfig);
        assertNotNull(customSerializers);
    }
}
