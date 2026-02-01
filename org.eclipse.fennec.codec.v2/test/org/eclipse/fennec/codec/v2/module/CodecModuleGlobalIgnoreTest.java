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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CodecModule} global ignore feature names.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#167-global-ignore-features">Spec 16.7: Global Ignore Features</a>
 */
@DisplayName("CodecModule global ignore features")
@Disabled("Migrated to org.eclipse.fennec.codec.module.CodecModuleGlobalIgnoreTest")
@Deprecated
class CodecModuleGlobalIgnoreTest extends CodecModuleTestBase {

    @Test
    @DisplayName("getGlobalIgnoreFeatureNames returns empty list by default")
    void getGlobalIgnoreFeatureNamesReturnsEmptyListByDefault() {
        assertTrue(module.getGlobalIgnoreFeatureNames().isEmpty());
    }

    @Test
    @DisplayName("getGlobalIgnoreFeatureNames returns configured names")
    void getGlobalIgnoreFeatureNamesReturnsConfiguredNames() {
        CodecConfiguration config = CodecConfiguration.builder()
                .globalIgnoreFeatureNames(List.of("createdAt", "updatedAt"))
                .build();
        CodecModule moduleWithIgnores = CodecModule.withConfiguration(config);

        assertTrue(moduleWithIgnores.getGlobalIgnoreFeatureNames().contains("createdAt"));
        assertTrue(moduleWithIgnores.getGlobalIgnoreFeatureNames().contains("updatedAt"));
    }

    @Test
    @DisplayName("getGlobalIgnoreFeatureNames returns unmodifiable list")
    void getGlobalIgnoreFeatureNamesReturnsUnmodifiableList() {
        CodecConfiguration config = CodecConfiguration.builder()
                .globalIgnore("feature1")
                .build();
        CodecModule moduleWithIgnores = CodecModule.withConfiguration(config);

        List<String> names = moduleWithIgnores.getGlobalIgnoreFeatureNames();
        assertThrows(UnsupportedOperationException.class, () -> names.add("feature2"));
    }

    @Test
    @DisplayName("isGloballyIgnored returns true for ignored feature")
    void isGloballyIgnoredReturnsTrueForIgnoredFeature() {
        CodecConfiguration config = CodecConfiguration.builder()
                .globalIgnore("password")
                .globalIgnore("secret")
                .build();
        CodecModule moduleWithIgnores = CodecModule.withConfiguration(config);

        assertTrue(moduleWithIgnores.isGloballyIgnored("password"));
        assertTrue(moduleWithIgnores.isGloballyIgnored("secret"));
    }

    @Test
    @DisplayName("isGloballyIgnored returns false for non-ignored feature")
    void isGloballyIgnoredReturnsFalseForNonIgnoredFeature() {
        CodecConfiguration config = CodecConfiguration.builder()
                .globalIgnore("password")
                .build();
        CodecModule moduleWithIgnores = CodecModule.withConfiguration(config);

        assertFalse(moduleWithIgnores.isGloballyIgnored("username"));
    }

    @Test
    @DisplayName("isGloballyIgnored returns false for null")
    void isGloballyIgnoredReturnsFalseForNull() {
        CodecConfiguration config = CodecConfiguration.builder()
                .globalIgnore("password")
                .build();
        CodecModule moduleWithIgnores = CodecModule.withConfiguration(config);

        assertFalse(moduleWithIgnores.isGloballyIgnored(null));
    }

    @Test
    @DisplayName("isGloballyIgnored returns false when no ignores configured")
    void isGloballyIgnoredReturnsFalseWhenNoIgnoresConfigured() {
        assertFalse(module.isGloballyIgnored("anyFeature"));
    }
}
