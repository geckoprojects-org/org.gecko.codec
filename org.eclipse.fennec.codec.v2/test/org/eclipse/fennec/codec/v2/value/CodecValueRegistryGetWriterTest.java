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
package org.eclipse.fennec.codec.v2.value;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#getWriter(String)}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
@DisplayName("CodecValueRegistry getWriter")
class CodecValueRegistryGetWriterTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("returns writer when found")
    void returnsWriterWhenFound() {
        CodecValueWriter<String> writer = (value, gen) -> gen.writeString(value);
        registry.registerWriter("test", writer);

        Optional<CodecValueWriter<?>> result = registry.getWriter("test");
        assertTrue(result.isPresent());
        assertSame(writer, result.get());
    }

    @Test
    @DisplayName("returns empty when not found")
    void returnsEmptyWhenNotFound() {
        Optional<CodecValueWriter<?>> result = registry.getWriter("nonexistent");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("returns empty for null name")
    void returnsEmptyForNullName() {
        Optional<CodecValueWriter<?>> result = registry.getWriter(null);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("returns empty for empty name")
    void returnsEmptyForEmptyName() {
        Optional<CodecValueWriter<?>> result = registry.getWriter("");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("getWriter with type class returns typed writer")
    void getWriterWithTypeClassReturnsTypedWriter() {
        CodecValueWriter<String> writer = (value, gen) -> gen.writeString(value);
        registry.registerWriter("test", writer);

        Optional<CodecValueWriter<String>> result = registry.getWriter("test", String.class);
        assertTrue(result.isPresent());
    }
}
