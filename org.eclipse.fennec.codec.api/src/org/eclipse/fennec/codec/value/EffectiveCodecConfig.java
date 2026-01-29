/*
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
package org.eclipse.fennec.codec.value;

import org.eclipse.fennec.codec.config.DiscriminatorConfig;
import org.eclipse.fennec.codec.config.FeatureConfig;
import org.eclipse.fennec.codec.config.IdConfig;
import org.eclipse.fennec.codec.config.ReferenceConfig;
import org.eclipse.fennec.codec.config.SuperTypeConfig;
import org.eclipse.fennec.codec.config.TypeConfig;

/**
 * The effective codec configuration after merging all sources.
 * <p>
 * This interface provides access to the fully-resolved configuration that
 * results from merging:
 * <ol>
 *   <li>EAnnotations on the model</li>
 *   <li>Factory defaults</li>
 *   <li>Resource options</li>
 *   <li>Load/Save options (highest priority)</li>
 * </ol>
 * <p>
 * Custom value readers and writers receive this configuration via their
 * context objects ({@link CodecReaderContext}, {@link CodecWriterContext}).
 *
 * @see CodecReaderContext#getConfig()
 * @see CodecWriterContext#getConfig()
 */
public interface EffectiveCodecConfig {

    /**
     * Returns the effective type configuration.
     *
     * @return the type configuration, never null
     */
    TypeConfig getTypeConfig();

    /**
     * Returns the effective supertype configuration.
     *
     * @return the supertype configuration, never null
     */
    SuperTypeConfig getSuperTypeConfig();

    /**
     * Returns the effective ID configuration.
     *
     * @return the ID configuration, never null
     */
    IdConfig getIdConfig();

    /**
     * Returns the effective feature configuration for the current feature.
     * <p>
     * This returns the configuration specific to the feature being
     * serialized/deserialized. May return a default configuration if
     * no feature-specific configuration is set.
     *
     * @return the feature configuration, never null
     */
    FeatureConfig getFeatureConfig();

    /**
     * Returns the effective reference configuration for the current reference.
     * <p>
     * This returns the configuration specific to the reference being
     * serialized/deserialized. May return a default configuration if
     * no reference-specific configuration is set.
     *
     * @return the reference configuration, never null
     */
    ReferenceConfig getReferenceConfig();

    /**
     * Returns the effective discriminator configuration.
     *
     * @return the discriminator configuration, never null
     */
    DiscriminatorConfig getDiscriminatorConfig();

    /**
     * Returns whether smart compression is enabled.
     *
     * @return true if smart compression is enabled
     */
    boolean isSmartCompressionEnabled();
}
