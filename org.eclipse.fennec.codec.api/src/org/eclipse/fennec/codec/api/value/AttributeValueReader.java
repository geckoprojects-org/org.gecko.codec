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

import org.eclipse.emf.ecore.EAttribute;

/**
 * Specialized value reader for EAttribute values.
 * <p>
 * This interface extends {@link CodecValueReader} for reading attribute values
 * from JSON. Use this for custom deserialization of attribute values where
 * the JSON format differs from the standard representation.
 * </p>
 * <p>
 * Example use case: Reading ISO 8601 date strings as Date objects:
 * </p>
 * <pre>
 * public class ISO8601DateReader implements AttributeValueReader&lt;Date&gt; {
 *     &#64;Override
 *     public boolean canHandle(EAttribute attribute) {
 *         return attribute.getEAttributeType().getInstanceClass() == Date.class;
 *     }
 *
 *     &#64;Override
 *     public Date read(JsonParser parser, EAttribute attribute, DeserializationContext ctxt) {
 *         String text = parser.getString();
 *         return ISO8601_FORMAT.parse(text);
 *     }
 * }
 * </pre>
 *
 * @param <T> the type of value to read
 * @see CodecValueReader
 * @see AttributeValueWriter
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
public interface AttributeValueReader<T> extends CodecValueReader<T, EAttribute> {

    /**
     * Checks if this reader can handle the given attribute.
     * <p>
     * Implementations should check if the attribute type is compatible with
     * the type this reader produces. For example, a Date reader should
     * verify that the attribute's type is Date or compatible.
     * </p>
     *
     * @param attribute the EAttribute to check
     * @return true if this reader can handle the attribute, false otherwise
     */
    boolean canHandle(EAttribute attribute);
}
