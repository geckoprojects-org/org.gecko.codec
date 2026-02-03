/**
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.ser}.
 *
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
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.ser}.
 *
 * Serializers for EMF codec v2.
 * <p>
 * This package contains Jackson serializers for EMF objects, using the
 * aspect-based MetadataService architecture for serialization decisions.
 * </p>
 * <p>
 * The serialization is organized following the separation of concerns principle:
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.CodecEObjectSerializer} - Main EObject serializer (orchestrator)</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.CodecSerializers} - Serializers registry for Jackson</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.SerializationEntry} - Interface for serialization entries</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.SerializationState} - State holder with value caching</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.FeatureKeyResolver} - Helper for key/URI resolution</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.IdSerializationEntry} - ID field serialization</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.TypeSerializationEntry} - Type information serialization</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.SuperTypeSerializationEntry} - Supertype information serialization</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.AttributeSerializationEntry} - EAttribute value serialization</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.ser.ReferenceSerializationEntry} - EReference value serialization</li>
 * </ul>
 * </p>
 * <p>
 * The {@link org.eclipse.fennec.codec.v2.ser.SerializationState} provides value caching
 * to avoid duplicate {@code eGet()} calls between shouldSerialize() and serialize() methods.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#6-eobject-serialization">Spec 6: EObject Serialization</a>
 */
package org.eclipse.fennec.codec.v2.ser;
