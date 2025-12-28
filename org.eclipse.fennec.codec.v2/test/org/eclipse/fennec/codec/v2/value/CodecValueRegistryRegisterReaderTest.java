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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#registerReader(String, CodecValueReader)}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
@DisplayName("CodecValueRegistry registerReader")
class CodecValueRegistryRegisterReaderTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("registers reader with valid name")
    void registersReaderWithValidName() {
        CodecValueReader<String> reader = parser -> parser.getText();
        registry.registerReader("testReader", reader);

        assertTrue(registry.hasReader("testReader"));
        assertSame(reader, registry.getReader("testReader").orElse(null));
    }

    @Test
    @DisplayName("throws exception for null name")
    void throwsExceptionForNullName() {
        CodecValueReader<String> reader = parser -> parser.getText();
        assertThrows(IllegalArgumentException.class,
                () -> registry.registerReader(null, reader));
    }

    @Test
    @DisplayName("throws exception for empty name")
    void throwsExceptionForEmptyName() {
        CodecValueReader<String> reader = parser -> parser.getText();
        assertThrows(IllegalArgumentException.class,
                () -> registry.registerReader("", reader));
    }

    @Test
    @DisplayName("throws exception for null reader")
    void throwsExceptionForNullReader() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.registerReader("test", null));
    }

    @Test
    @DisplayName("returns registry for chaining")
    void returnsRegistryForChaining() {
        CodecValueReader<String> reader = parser -> parser.getText();
        assertSame(registry, registry.registerReader("test", reader));
    }
}
