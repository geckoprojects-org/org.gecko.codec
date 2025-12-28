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

import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for {@link CodecValueRegistry} tests.
 * <p>
 * Provides common setup for creating an empty registry.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
abstract class CodecValueRegistryTestBase {

    protected CodecValueRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new CodecValueRegistry();
    }
}
