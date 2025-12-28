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

import tools.jackson.core.JsonGenerator;

/**
 * Interface for custom value writers that transform values during serialization.
 * <p>
 * Value writers allow you to:
 * <ul>
 *   <li>Transform values before serialization</li>
 *   <li>Handle special data types (dates, binary, custom formats)</li>
 *   <li>Implement domain-specific encoding</li>
 * </ul>
 * </p>
 * <p>
 * Example implementation:
 * </p>
 * <pre>
 * public class ISO8601DateWriter implements CodecValueWriter&lt;Date&gt; {
 *     &#64;Override
 *     public void write(Date value, JsonGenerator gen) throws IOException {
 *         gen.writeString(ISO8601_FORMAT.format(value));
 *     }
 * }
 * </pre>
 *
 * @param <T> the type of value to write
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
@FunctionalInterface
public interface CodecValueWriter<T> {

    /**
     * Writes the value to the JSON generator.
     *
     * @param value the value to write (never null)
     * @param gen the JSON generator
     * @throws IOException if an I/O error occurs
     */
    void write(T value, JsonGenerator gen) throws IOException;
}
