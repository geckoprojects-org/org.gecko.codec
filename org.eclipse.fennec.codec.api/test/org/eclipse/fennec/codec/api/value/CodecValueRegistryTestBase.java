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

import org.eclipse.emf.ecore.EAttribute;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for {@link CodecValueRegistry} tests.
 * <p>
 * Provides common setup for creating an empty registry.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.value}. Tests kept for migration reference.
 */
@Deprecated
@SuppressWarnings("deprecation")
abstract class CodecValueRegistryTestBase {

    protected CodecValueRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new CodecValueRegistry();
    }

    /**
     * Creates a simple string writer for testing.
     */
    protected CodecValueWriter<String, EAttribute> createStringWriter() {
        return (value, feature, gen, ctxt) -> gen.writeString(value);
    }

    /**
     * Creates a simple string reader for testing.
     */
    protected CodecValueReader<String, EAttribute> createStringReader() {
        return (parser, feature, ctxt) -> parser.getString();
    }

    /**
     * Creates a simple integer writer for testing.
     */
    protected CodecValueWriter<Integer, EAttribute> createIntWriter() {
        return (value, feature, gen, ctxt) -> gen.writeNumber(value);
    }

    /**
     * Creates a simple integer reader for testing.
     */
    protected CodecValueReader<Integer, EAttribute> createIntReader() {
        return (parser, feature, ctxt) -> parser.getIntValue();
    }
}
