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

/**
 * Effective (fully merged) configuration classes for codec v2.
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
 * Key classes:
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig} - Top-level config with caching</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveClassConfig} - Per-EClass configuration</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig} - Per-feature configuration</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveIdConfig} - ID serialization settings</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveTypeConfig} - Type serialization settings</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.EffectiveSuperTypeConfig} - SuperType serialization settings</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger} - Merges all config sources</li>
 * </ul>
 * </p>
 * <p>
 * The {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger} is the single point
 * where configuration resolution order is implemented. All serializers use the resulting effective
 * configurations without any fallback logic.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#16-configuration-hierarchy">Spec 16: Configuration Hierarchy</a>
 */
package org.eclipse.fennec.codec.v2.config.effective;
