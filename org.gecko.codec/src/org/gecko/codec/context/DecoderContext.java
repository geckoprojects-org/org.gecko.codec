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

import org.gecko.codec.decode.CodecReader;

/**
 * Decoder context 
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public interface DecoderContext extends Context {
	
	/**
	 * Returns the parent context or <code>null</code>, if the context is laready the root context
	 * @return the parent context or <code>null</code>
	 */
	DecoderContext getParentContext();

	/**
	 * Decodes from a child decoder
	 * @param reader the codec reader, must not be <code>null</code>
	 * @return the value or <code>null</code>
	 */
	<T> T decodeFromChild(CodecReader reader);
	
	/**
	 * Creates an {@link DecoderContextContext} from a given context (which can also be an {@link DecoderContextContext})
	 * @param context the parent context, must not be <code>null</code>
	 * @return the child context
	 */
	public static DecoderContext createFromParent(Context context) {
		return new DecoderContextImpl(context);
	}

}
