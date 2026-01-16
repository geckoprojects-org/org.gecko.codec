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
package org.eclipse.fennec.codec.api.value;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Specialized value reader for EReference containment references.
 * <p>
 * This interface extends {@link CodecValueReader} for reading contained EObjects
 * from JSON. Use this for custom deserialization of containment references where
 * the JSON structure differs from the standard EMF object format.
 * </p>
 * <p>
 * Example use case: Reading embedded JSON Schema as an EPackage within OpenAPI:
 * </p>
 * <pre>
 * public class EPackageValueReader implements ReferenceValueReader&lt;EPackage&gt; {
 *     &#64;Override
 *     public boolean canHandle(EReference reference) {
 *         return EcorePackage.Literals.EPACKAGE.isSuperTypeOf(reference.getEReferenceType());
 *     }
 *
 *     &#64;Override
 *     public EPackage read(JsonParser parser, EReference reference, DeserializationContext ctxt) {
 *         // Convert JSON Schema to EPackage
 *         return converter.convert(parser.readValueAsTree());
 *     }
 * }
 * </pre>
 *
 * @param <T> the type of EObject to read (must extend EObject)
 * @see CodecValueReader
 * @see ReferenceValueWriter
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
public interface ReferenceValueReader<T extends EObject> extends CodecValueReader<T, EReference> {

    /**
     * Checks if this reader can handle the given reference.
     * <p>
     * Implementations should check if the reference type is compatible with
     * the type this reader produces. For example, an EPackage reader should
     * verify that the reference's type is EPackage or a supertype.
     * </p>
     *
     * @param reference the EReference to check
     * @return true if this reader can handle the reference, false otherwise
     */
    boolean canHandle(EReference reference);
}
