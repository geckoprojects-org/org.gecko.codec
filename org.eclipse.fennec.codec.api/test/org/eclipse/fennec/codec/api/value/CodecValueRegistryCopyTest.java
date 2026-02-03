/**
 * Copyright (c) 2012 - 2026 Data In Motion and others.
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

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#copy()}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.value}. Tests kept for migration reference.
 */
@Deprecated
@Disabled("Migrated to org.eclipse.fennec.codec.value - kept for migration reference")
@SuppressWarnings("deprecation")
@DisplayName("CodecValueRegistry copy")
class CodecValueRegistryCopyTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("creates independent copy")
    void createsIndependentCopy() {
        var writer = createStringWriter();
        var reader = createStringReader();

        registry.registerWriter("writer", writer);
        registry.registerReader("reader", reader);

        CodecValueRegistry copy = registry.copy();

        assertNotSame(registry, copy);
        assertTrue(copy.hasWriter("writer"));
        assertTrue(copy.hasReader("reader"));

        // Modifications to copy don't affect original
        copy.unregisterWriter("writer");
        assertTrue(registry.hasWriter("writer"));
    }
}
