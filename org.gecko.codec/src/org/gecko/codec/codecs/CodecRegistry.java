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
package org.gecko.codec.codecs;

import java.util.Optional;

import org.gecko.codec.CodecType;

/**
 * Registry for codecs
 * @author Mark Hoffmann
 * @since 04.04.2023
 */
public interface CodecRegistry {

	/**
	 * Returns a {@link Codec} for a given codec type
	 * @param type the codec type, must not be <code>null</code>
	 * @return the codec or <code>null</code>
	 */
	Codec<?> getCodec(CodecType type);
	
	/**
	 * Provides the result as {@link Optional}
	 * @param type the codec type
	 * @return the optional of the codec
	 */
	Optional<Codec<?>> getCodecOpt(CodecType type);
	
	/**
	 * Registers a new codec
	 * @param type the codec type, must not be <code>null</code>
	 * @param codec the codec instance, must not be <code>null</code>
	 */
	void registerCodec(CodecType type, Codec<?> codec);
	
}
