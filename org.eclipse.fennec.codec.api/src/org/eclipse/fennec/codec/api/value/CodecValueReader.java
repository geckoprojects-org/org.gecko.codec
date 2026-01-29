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

import java.io.IOException;

import org.eclipse.emf.ecore.EStructuralFeature;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;

/**
 * Interface for custom value readers that transform values during deserialization.
 * <p>
 * Value readers allow you to:
 * <ul>
 *   <li>Transform values after deserialization</li>
 *   <li>Parse special data types (dates, binary, custom formats)</li>
 *   <li>Implement domain-specific decoding</li>
 *   <li>Handle both attribute values (EAttribute) and reference URIs (EReference)</li>
 * </ul>
 * </p>
 * <p>
 * Example implementation for attributes:
 * </p>
 * <pre>
 * public class ISO8601DateReader implements CodecValueReader&lt;Date, EAttribute&gt; {
 *     &#64;Override
 *     public Date read(JsonParser parser, EAttribute feature, DeserializationContext ctxt) throws IOException {
 *         String text = parser.getString();
 *         return ISO8601_FORMAT.parse(text);
 *     }
 * }
 * </pre>
 * <p>
 * Example implementation for references (custom URI format):
 * </p>
 * <pre>
 * public class CustomRefReader implements CodecValueReader&lt;String, EReference&gt; {
 *     &#64;Override
 *     public String read(JsonParser parser, EReference feature, DeserializationContext ctxt) throws IOException {
 *         // Parse custom reference format and return URI string
 *         String customId = parser.getString();
 *         return "http://example.org/objects/" + customId;
 *     }
 * }
 * </pre>
 *
 * @param <T> the type of value to read (value type for attributes, String URI for references)
 * @param <F> the feature type (EAttribute or EReference)
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 * @deprecated Use {@link org.eclipse.fennec.codec.value.CodecValueReader} instead, which provides
 *             access to effective configuration via context object
 */
@Deprecated
@FunctionalInterface
public interface CodecValueReader<T, F extends EStructuralFeature> {

    /**
     * Reads a value from the JSON parser.
     *
     * @param parser the JSON parser positioned at the value
     * @param feature the EStructuralFeature being deserialized (EAttribute or EReference)
     * @param ctxt the deserialization context (may be null in some test scenarios)
     * @return the parsed value (may be null if the JSON value is null)
     * @throws IOException if an I/O error occurs
     */
    T read(JsonParser parser, F feature, DeserializationContext ctxt) throws IOException;
}
