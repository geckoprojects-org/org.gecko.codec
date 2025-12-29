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

import tools.jackson.core.JsonParser;

/**
 * Interface for custom value readers that transform values during deserialization.
 * <p>
 * Value readers allow you to:
 * <ul>
 *   <li>Transform values after deserialization</li>
 *   <li>Parse special data types (dates, binary, custom formats)</li>
 *   <li>Implement domain-specific decoding</li>
 * </ul>
 * </p>
 * <p>
 * Example implementation:
 * </p>
 * <pre>
 * public class ISO8601DateReader implements CodecValueReader&lt;Date&gt; {
 *     &#64;Override
 *     public Date read(JsonParser parser) throws IOException {
 *         String text = parser.getString();
 *         return ISO8601_FORMAT.parse(text);
 *     }
 * }
 * </pre>
 *
 * @param <T> the type of value to read
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
@FunctionalInterface
public interface CodecValueReader<T> {

    /**
     * Reads a value from the JSON parser.
     *
     * @param parser the JSON parser positioned at the value
     * @return the parsed value (may be null if the JSON value is null)
     * @throws IOException if an I/O error occurs
     */
    T read(JsonParser parser) throws IOException;
}
