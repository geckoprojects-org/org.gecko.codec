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
 * Custom value readers and writers API for EMF codec serialization.
 * <p>
 * <strong>DEPRECATED:</strong> This package is deprecated. Use {@link org.eclipse.fennec.codec.value}
 * instead, which provides context-aware interfaces with access to effective configuration.
 * </p>
 * <p>
 * This package provides the API interfaces for custom value transformation:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.api.value.CodecValueWriter} - Transform values during serialization</li>
 *   <li>{@link org.eclipse.fennec.codec.api.value.CodecValueReader} - Transform values during deserialization</li>
 *   <li>{@link org.eclipse.fennec.codec.api.value.CodecValueRegistry} - Registry for named readers/writers</li>
 * </ul>
 *
 * @see org.eclipse.fennec.codec.value
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Use {@link org.eclipse.fennec.codec.value} package instead
 */
@org.osgi.annotation.bundle.Export
@org.osgi.annotation.versioning.Version("1.0.0")
package org.eclipse.fennec.codec.api.value;
