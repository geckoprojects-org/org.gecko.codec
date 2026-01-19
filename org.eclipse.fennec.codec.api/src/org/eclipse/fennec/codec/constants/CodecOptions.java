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
package org.eclipse.fennec.codec.constants;

/**
 * Constants for codec Load/Save options.
 * <p>
 * These options can be passed to {@code resource.load()} and {@code resource.save()}
 * to customize serialization behavior at runtime.
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/18-feature-type-hints.md">Spec: Feature Type Hints</a>
 * @since 1.0
 */
public final class CodecOptions {

    private CodecOptions() {
        // Utility class - no instantiation
    }

    /**
     * Load option key for feature-specific type hints.
     * <p>
     * Value: {@code Map<EStructuralFeature, EClass>}
     * </p>
     * <p>
     * Provides EClass type hints for specific features, particularly useful for
     * EObject-typed features where the concrete type cannot be determined from JSON alone.
     * </p>
     *
     * @see <a href="docs/codec-v2-spec/18-feature-type-hints.md">Spec: Feature Type Hints</a>
     */
    public static final String CODEC_FEATURE_TYPE_HINTS = "codec.feature.type.hints";

    /**
     * Load option key for feature-specific value readers.
     * <p>
     * Value: {@code Map<EStructuralFeature, String>} where String is the registered reader name
     * </p>
     * <p>
     * This is the Load-Option equivalent of the {@code valueReaderName} EAnnotation.
     * Takes priority over CODEC_FEATURE_TYPE_HINTS for the same feature.
     * </p>
     *
     * @see <a href="docs/codec-v2-spec/18-feature-type-hints.md">Spec: Feature Type Hints</a>
     */
    public static final String CODEC_FEATURE_VALUE_READERS = "codec.feature.value.readers";

    /**
     * Save option key for feature-specific value writers.
     * <p>
     * Value: {@code Map<EStructuralFeature, String>} where String is the registered writer name
     * </p>
     * <p>
     * This is the Save-Option equivalent of the {@code valueWriterName} EAnnotation.
     * </p>
     *
     * @see <a href="docs/codec-v2-spec/18-feature-type-hints.md">Spec: Feature Type Hints</a>
     */
    public static final String CODEC_FEATURE_VALUE_WRITERS = "codec.feature.value.writers";
}
