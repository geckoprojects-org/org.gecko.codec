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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#clear()}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
@DisplayName("CodecValueRegistry clear")
class CodecValueRegistryClearTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("clears all writers and readers")
    void clearsAllWritersAndReaders() {
        CodecValueWriter<String> writer = (value, gen) -> gen.writeString(value);
        CodecValueReader<String> reader = parser -> parser.getString();

        registry.registerWriter("writer", writer);
        registry.registerReader("reader", reader);
        registry.clear();

        assertTrue(registry.getWriters().isEmpty());
        assertTrue(registry.getReaders().isEmpty());
    }
}
