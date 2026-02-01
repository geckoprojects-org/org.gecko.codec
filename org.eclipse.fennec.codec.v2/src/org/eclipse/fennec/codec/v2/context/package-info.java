/**
 * EMF Codec Context classes for tracking state during serialization and deserialization.
 * <p>
 * This package provides the context infrastructure needed by custom Jackson generators
 * and parsers for EMF serialization. The key classes are:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.v2.context.EMFCodecContext} - Base interface for codec contexts</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.context.EMFCodecWriteContext} - Interface for serialization contexts</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.context.EMFCodecReadContext} - Interface for deserialization contexts</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.context.CodecWriteContext} - Implementation for serialization</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.context.CodecReadContext} - Implementation for deserialization</li>
 *   <li>{@link org.eclipse.fennec.codec.v2.context.EMFContextHolder} - Holds EMF state (current EObject, feature, etc.)</li>
 * </ul>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#1811-emf-codec-context">Spec 18.11: EMF Codec Context</a>
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.context}
 */
package org.eclipse.fennec.codec.v2.context;
