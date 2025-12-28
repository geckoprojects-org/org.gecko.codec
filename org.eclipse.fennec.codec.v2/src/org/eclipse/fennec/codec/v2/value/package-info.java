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
package org.eclipse.fennec.codec.v2.value;
