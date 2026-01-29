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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#registerWriter(String, CodecValueWriter)}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.value}. Tests kept for migration reference.
 */
@Deprecated
@Disabled("Migrated to org.eclipse.fennec.codec.value - kept for migration reference")
@SuppressWarnings("deprecation")
@DisplayName("CodecValueRegistry registerWriter")
class CodecValueRegistryRegisterWriterTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("registers writer with valid name")
    void registersWriterWithValidName() {
        var writer = createStringWriter();
        registry.registerWriter("testWriter", writer);

        assertTrue(registry.hasWriter("testWriter"));
        assertSame(writer, registry.getWriter("testWriter").orElse(null));
    }

    @Test
    @DisplayName("throws exception for null name")
    void throwsExceptionForNullName() {
        var writer = createStringWriter();
        assertThrows(IllegalArgumentException.class,
                () -> registry.registerWriter(null, writer));
    }

    @Test
    @DisplayName("throws exception for empty name")
    void throwsExceptionForEmptyName() {
        var writer = createStringWriter();
        assertThrows(IllegalArgumentException.class,
                () -> registry.registerWriter("", writer));
    }

    @Test
    @DisplayName("throws exception for null writer")
    void throwsExceptionForNullWriter() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.registerWriter("test", null));
    }

    @Test
    @DisplayName("returns registry for chaining")
    void returnsRegistryForChaining() {
        var writer = createStringWriter();
        assertSame(registry, registry.registerWriter("test", writer));
    }

    @Test
    @DisplayName("overwrites existing writer with same name")
    void overwritesExistingWriter() {
        var writer1 = createStringWriter();
        var writer2 = createStringWriter();

        registry.registerWriter("test", writer1);
        registry.registerWriter("test", writer2);

        assertSame(writer2, registry.getWriter("test").orElse(null));
    }
}
