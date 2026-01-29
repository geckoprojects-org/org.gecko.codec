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
 * Specialized value writer for EReference containment references.
 * <p>
 * This interface extends {@link CodecValueWriter} for writing contained EObjects
 * to JSON. Use this for custom serialization of containment references where
 * the JSON structure should differ from the standard EMF object format.
 * </p>
 * <p>
 * Example use case: Writing an EPackage as embedded JSON Schema within OpenAPI:
 * </p>
 * <pre>
 * public class EPackageValueWriter implements ReferenceValueWriter&lt;EPackage&gt; {
 *     &#64;Override
 *     public boolean canHandle(EReference reference) {
 *         return EcorePackage.Literals.EPACKAGE.isSuperTypeOf(reference.getEReferenceType());
 *     }
 *
 *     &#64;Override
 *     public void write(EPackage value, EReference reference, JsonGenerator gen, SerializationContext ctxt) {
 *         // Convert EPackage to JSON Schema and write
 *         JsonNode schema = converter.convert(value);
 *         gen.writePOJO(schema);
 *     }
 * }
 * </pre>
 *
 * @param <T> the type of EObject to write (must extend EObject)
 * @see CodecValueWriter
 * @see ReferenceValueReader
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Use {@link org.eclipse.fennec.codec.value.ReferenceValueWriter} instead, which provides
 *             access to effective configuration via context object
 */
@Deprecated
public interface ReferenceValueWriter<T extends EObject> extends CodecValueWriter<T, EReference> {

    /**
     * Checks if this writer can handle the given reference.
     * <p>
     * Implementations should check if the reference type is compatible with
     * the type this writer accepts. For example, an EPackage writer should
     * verify that the reference's type is EPackage or a supertype.
     * </p>
     *
     * @param reference the EReference to check
     * @return true if this writer can handle the reference, false otherwise
     */
    boolean canHandle(EReference reference);
}
