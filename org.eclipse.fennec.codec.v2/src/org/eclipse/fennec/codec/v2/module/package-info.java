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
 * Jackson module integration for EMF codec v2.
 * <p>
 * This package contains the Jackson module that integrates EMF serialization
 * with the Jackson streaming API. The module registers serializers and
 * deserializers for EObject handling.
 * </p>
 * <p>
 * Key classes:
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.v2.module.CodecModule} - Main Jackson module</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-jackson-module-integration">Spec 9: Jackson Module Integration</a>
 */
@org.osgi.annotation.bundle.Export
@org.osgi.annotation.versioning.Version("1.0.0")
package org.eclipse.fennec.codec.v2.module;
