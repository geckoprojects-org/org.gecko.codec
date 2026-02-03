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

/**
 * Effective (fully merged) configuration classes for codec v2.
 * <p>
 * <b>DEPRECATED:</b> This package is deprecated. Use the new config classes in
 * {@link org.eclipse.fennec.codec.config} package instead.
 * </p>
 * <p>
 * This package contains immutable, fully-resolved configuration classes that represent
 * the effective merged configuration from all sources:
 * <ol>
 *   <li>Load/Save options (highest priority)</li>
 *   <li>ResourceFactory defaults</li>
 *   <li>CodecModule configuration</li>
 *   <li>Model aspects (from MetadataService, based on EAnnotations)</li>
 *   <li>Built-in defaults (lowest priority)</li>
 * </ol>
 * </p>
 * <p>
 * Key classes (all deprecated):
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig} - Use new config classes instead</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveClassConfig} - Use new config classes instead</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig} - Use {@link org.eclipse.fennec.codec.config.FeatureConfig}</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveIdConfig} - Use {@link org.eclipse.fennec.codec.config.IdConfig}</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveTypeConfig} - Use {@link org.eclipse.fennec.codec.config.TypeConfig}</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveSuperTypeConfig} - Use {@link org.eclipse.fennec.codec.config.SuperTypeConfig}</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger} - Use Mergeable pattern instead</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.fennec.codec.config
 * @see <a href="docs/codec-v2-serialization-spec.md#16-configuration-hierarchy">Spec 16: Configuration Hierarchy</a>
 * @deprecated Use the new config classes in {@link org.eclipse.fennec.codec.config} package.
 */
@org.osgi.annotation.bundle.Export
@org.osgi.annotation.versioning.Version("1.0.0")
package org.eclipse.fennec.codec.v2.config.effective;
