/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
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
package org.gecko.codec.encode;

import org.gecko.codec.CodecType;
import org.gecko.codec.CodecValue;
import org.gecko.codec.context.EncoderContext;

/**
 * Encoder interface
 * <T> the encoding type 
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public interface Encoder<T> {
	
	/**
	 * Encodes an instance to  
	 * @param writer
	 * @param value
	 * @param context
	 */
	void encode(CodecWriter writer, CodecValue<T> value, EncoderContext context);
	
	/**
	 * Returns the codec type the instance encodes. This is an abstraction to Java classes,
	 * to support different possibilities of types, beside Java
	 * @return the codec type the instance encodes
	 */
	CodecType getCodecType();

}
