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
package org.eclipse.fennec.codec.v2.ser;

import static org.mockito.Mockito.mock;

import java.util.Collections;

import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.v2.module.CodecModule;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.junit.jupiter.api.BeforeEach;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * Base class for {@link CodecEObjectSerializer} tests.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#6-eobject-serialization">Spec 6: EObject Serialization</a>
 */
abstract class CodecEObjectSerializerTestBase {

    protected MetadataService metadataService;
    protected CodecModule codecModule;
    protected EffectiveCodecConfig effectiveConfig;
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        metadataService = mock(MetadataService.class);
        codecModule = CodecModule.builder()
                .metadataService(metadataService)
                .build();
        effectiveConfig = codecModule.createEffectiveConfig(Collections.emptyMap());
        objectMapper = JsonMapper.builder()
                .addModule(codecModule)
                .build();
    }
}
