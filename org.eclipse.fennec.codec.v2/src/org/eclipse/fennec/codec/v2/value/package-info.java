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
 * Custom value readers and writers for EMF codec serialization.
 * <p>
 * This package provides the extension points for custom value transformation:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.v2.value.CodecValueWriter} - Transform values during serialization</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.value.CodecValueReader} - Transform values during deserialization</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.value.CodecValueRegistry} - Registry for named readers/writers</li>
 * </ul>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
@org.osgi.annotation.bundle.Export
@org.osgi.annotation.versioning.Version("1.0.0")
package org.eclipse.fennec.codec.v2.value;
