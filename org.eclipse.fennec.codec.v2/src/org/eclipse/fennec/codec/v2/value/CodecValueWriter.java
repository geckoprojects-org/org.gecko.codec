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
package org.eclipse.fennec.codec.v2.value;

import java.io.IOException;

import org.eclipse.emf.ecore.EStructuralFeature;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Interface for custom value writers that transform values during serialization.
 * <p>
 * Value writers allow you to:
 * <ul>
 *   <li>Transform values before serialization</li>
 *   <li>Handle special data types (dates, binary, custom formats)</li>
 *   <li>Implement domain-specific encoding</li>
 *   <li>Handle both attribute values (EAttribute) and reference URIs (EReference)</li>
 * </ul>
 * </p>
 * <p>
 * Example implementation for attributes:
 * </p>
 * <pre>
 * public class ISO8601DateWriter implements CodecValueWriter&lt;Date, EAttribute&gt; {
 *     &#64;Override
 *     public void write(Date value, EAttribute feature, JsonGenerator gen, SerializationContext ctxt) throws IOException {
 *         gen.writeString(ISO8601_FORMAT.format(value));
 *     }
 * }
 * </pre>
 * <p>
 * Example implementation for references (custom URI format):
 * </p>
 * <pre>
 * public class CustomRefWriter implements CodecValueWriter&lt;EObject, EReference&gt; {
 *     &#64;Override
 *     public void write(EObject target, EReference feature, JsonGenerator gen, SerializationContext ctxt) throws IOException {
 *         // Write custom reference format
 *         String customId = extractCustomId(target);
 *         gen.writeString(customId);
 *     }
 * }
 * </pre>
 *
 * @param <T> the type of value to write (value type for attributes, EObject for references)
 * @param <F> the feature type (EAttribute or EReference)
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
@FunctionalInterface
public interface CodecValueWriter<T, F extends EStructuralFeature> {

    /**
     * Writes the value to the JSON generator.
     *
     * @param value the value to write (never null)
     * @param feature the EStructuralFeature being serialized (EAttribute or EReference)
     * @param gen the JSON generator
     * @param ctxt the serialization context (may be null in some test scenarios)
     * @throws IOException if an I/O error occurs
     */
    void write(T value, F feature, JsonGenerator gen, SerializationContext ctxt) throws IOException;
}
