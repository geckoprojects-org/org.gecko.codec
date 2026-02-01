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
package org.eclipse.fennec.codec.v2.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tools.jackson.core.Version;

/**
 * Tests for {@link CodecModule} version and name.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-jackson-module-integration">Spec 9: Jackson Module Integration</a>
 */
@DisplayName("CodecModule version and name")
@Disabled("Migrated to org.eclipse.fennec.codec.module.CodecModuleVersionTest")
@Deprecated
class CodecModuleVersionTest extends CodecModuleTestBase {

    @Test
    @DisplayName("getModuleName returns default name")
    void getModuleNameReturnsDefaultName() {
        assertEquals("fennec-codec-v2-module", module.getModuleName());
    }

    @Test
    @DisplayName("getModuleName returns custom name when set")
    void getModuleNameReturnsCustomName() {
        CodecModule customModule = CodecModule.builder()
                .moduleName("my-custom-module")
                .build();

        assertEquals("my-custom-module", customModule.getModuleName());
    }

    @Test
    @DisplayName("version returns v2 version")
    void versionReturnsV2Version() {
        Version version = module.version();

        assertNotNull(version);
        assertEquals(2, version.getMajorVersion());
        assertEquals(0, version.getMinorVersion());
        assertEquals(0, version.getPatchLevel());
    }

    @Test
    @DisplayName("version includes group and artifact id")
    void versionIncludesGroupAndArtifactId() {
        Version version = module.version();

        assertEquals("org.eclipse.fennec", version.getGroupId());
        assertEquals("org.eclipse.fennec.codec.v2", version.getArtifactId());
    }
}
