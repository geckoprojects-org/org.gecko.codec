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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#getWriters()} and {@link CodecValueRegistry#getReaders()}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.value}. Tests kept for migration reference.
 */
@Deprecated
@Disabled("Migrated to org.eclipse.fennec.codec.value - kept for migration reference")
@SuppressWarnings("deprecation")
@DisplayName("CodecValueRegistry getWriters and getReaders")
class CodecValueRegistryGetAllTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("getWriters returns unmodifiable view")
    void getWritersReturnsUnmodifiableView() {
        var writer = createStringWriter();
        registry.registerWriter("test", writer);

        Map<String, CodecValueWriter<?, ?>> writers = registry.getWriters();
        assertEquals(1, writers.size());
        assertThrows(UnsupportedOperationException.class,
                () -> writers.put("new", writer));
    }

    @Test
    @DisplayName("getReaders returns unmodifiable view")
    void getReadersReturnsUnmodifiableView() {
        var reader = createStringReader();
        registry.registerReader("test", reader);

        Map<String, CodecValueReader<?, ?>> readers = registry.getReaders();
        assertEquals(1, readers.size());
        assertThrows(UnsupportedOperationException.class,
                () -> readers.put("new", reader));
    }
}
