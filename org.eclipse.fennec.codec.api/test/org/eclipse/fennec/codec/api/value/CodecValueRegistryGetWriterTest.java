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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecValueRegistry#getWriter(String)}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.value}. Tests kept for migration reference.
 */
@Deprecated
@Disabled("Migrated to org.eclipse.fennec.codec.value - kept for migration reference")
@SuppressWarnings("deprecation")
@DisplayName("CodecValueRegistry getWriter")
class CodecValueRegistryGetWriterTest extends CodecValueRegistryTestBase {

    @Test
    @DisplayName("returns writer when found")
    void returnsWriterWhenFound() {
        var writer = createStringWriter();
        registry.registerWriter("test", writer);

        Optional<CodecValueWriter<?, ?>> result = registry.getWriter("test");
        assertTrue(result.isPresent());
        assertSame(writer, result.get());
    }

    @Test
    @DisplayName("returns empty when not found")
    void returnsEmptyWhenNotFound() {
        Optional<CodecValueWriter<?, ?>> result = registry.getWriter("nonexistent");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("returns empty for null name")
    void returnsEmptyForNullName() {
        Optional<CodecValueWriter<?, ?>> result = registry.getWriter(null);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("returns empty for empty name")
    void returnsEmptyForEmptyName() {
        Optional<CodecValueWriter<?, ?>> result = registry.getWriter("");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("getWriter with type and feature class returns typed writer")
    void getWriterWithTypeAndFeatureClassReturnsTypedWriter() {
        var writer = createStringWriter();
        registry.registerWriter("test", writer);

        Optional<CodecValueWriter<String, EAttribute>> result =
            registry.getWriter("test", String.class, EAttribute.class);
        assertTrue(result.isPresent());
    }
}
