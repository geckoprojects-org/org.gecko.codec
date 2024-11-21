/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.codec;

/**
 * 
 * @author ilenia
 * @since Nov 21, 2024
 */
public interface CodecEncoder<W, T> {
	
	/**
     * Encode an instance of the type parameter {@code T} into a BSON value.
     * @param writer the BSON writer to encode into
     * @param value the value to encode
     */
	void encode(W writer, T value);

    /**
     * Returns the Class instance that this encodes. This is necessary because Java does not reify generic types.
     *
     * @return the Class instance that this encodes.
     */
    Class<T> getEncoderClass();

}
