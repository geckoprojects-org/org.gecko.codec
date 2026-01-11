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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#hasWriter(String)} and {@link CodecValueRegistry#hasReader(String)}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
@DisplayName("CodecValueRegistry hasWriter and hasReader")
class CodecValueRegistryHasWriterReaderTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("hasWriter returns true for registered writer")
    void hasWriterReturnsTrueForRegistered() {
        var writer = createStringWriter();
        registry.registerWriter("test", writer);
        assertTrue(registry.hasWriter("test"));
    }

    @Test
    @DisplayName("hasWriter returns false for unregistered")
    void hasWriterReturnsFalseForUnregistered() {
        assertFalse(registry.hasWriter("nonexistent"));
    }

    @Test
    @DisplayName("hasWriter returns false for null")
    void hasWriterReturnsFalseForNull() {
        assertFalse(registry.hasWriter(null));
    }

    @Test
    @DisplayName("hasReader returns true for registered reader")
    void hasReaderReturnsTrueForRegistered() {
        var reader = createStringReader();
        registry.registerReader("test", reader);
        assertTrue(registry.hasReader("test"));
    }

    @Test
    @DisplayName("hasReader returns false for unregistered")
    void hasReaderReturnsFalseForUnregistered() {
        assertFalse(registry.hasReader("nonexistent"));
    }

    @Test
    @DisplayName("hasReader returns false for null")
    void hasReaderReturnsFalseForNull() {
        assertFalse(registry.hasReader(null));
    }
}
