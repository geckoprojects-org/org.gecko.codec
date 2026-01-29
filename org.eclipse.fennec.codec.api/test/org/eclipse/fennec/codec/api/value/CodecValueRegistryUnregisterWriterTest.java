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
package org.eclipse.fennec.codec.api.value;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#unregisterWriter(String)}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.value}. Tests kept for migration reference.
 */
@Deprecated
@Disabled("Migrated to org.eclipse.fennec.codec.value - kept for migration reference")
@SuppressWarnings("deprecation")
@DisplayName("CodecValueRegistry unregisterWriter")
class CodecValueRegistryUnregisterWriterTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("removes registered writer")
    void removesRegisteredWriter() {
        var writer = createStringWriter();
        registry.registerWriter("test", writer);
        registry.unregisterWriter("test");

        assertFalse(registry.hasWriter("test"));
    }

    @Test
    @DisplayName("handles null name gracefully")
    void handlesNullNameGracefully() {
        registry.unregisterWriter(null); // Should not throw
    }

    @Test
    @DisplayName("handles non-existent name gracefully")
    void handlesNonExistentNameGracefully() {
        registry.unregisterWriter("nonexistent"); // Should not throw
    }

    @Test
    @DisplayName("returns registry for chaining")
    void returnsRegistryForChaining() {
        assertSame(registry, registry.unregisterWriter("test"));
    }
}
