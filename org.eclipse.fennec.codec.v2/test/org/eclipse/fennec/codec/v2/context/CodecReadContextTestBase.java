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
package org.eclipse.fennec.codec.v2.context;

import static org.mockito.Mockito.mock;

import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for {@link CodecReadContext} tests.
 * <p>
 * Provides common setup for creating a root context with a mocked metadata service.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#1811-emf-codec-context">Spec 18.11: EMF Codec Context</a>
 */
abstract class CodecReadContextTestBase {

    protected MetadataService metadataService;
    protected CodecReadContext context;

    @BeforeEach
    void setUp() {
        metadataService = mock(MetadataService.class);
        context = CodecReadContext.createRootContext(metadataService);
    }
}
