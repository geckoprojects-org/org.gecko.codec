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
package org.gecko.codec.context;

import org.gecko.codec.encode.CodecWriter;
import org.gecko.codec.encode.Encoder;

/**
 * Encoding context
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public interface EncoderContext extends Context{
	
	/**
	 * Returns the parent context or <code>null</code>, if the context is the root context
	 * @return the parent context or <code>null</code>
	 */
	EncoderContext getParentContext();
	
	/**
	 * Encodes the given value with a child codec.
	 * This can be used when creating {@link Encoder} for wrapper classes. The real value can extracted from
	 * them and then delegated to another child codec.
	 * @param <T> the value type
	 * @param writer the codec writer
	 * @param value the value to delegate
	 */
	<T> void encodeWithChild(CodecWriter writer, T value);
	
	/**
	 * Creates an {@link EncoderContext} from a given context (which can also be an {@link EncoderContext})
	 * @param context the parent context, must not be <code>null</code>
	 * @return the child context
	 */
	public static EncoderContext createFromParent(Context context) {
		return new EncoderContextImpl(context);
	}
	
}
