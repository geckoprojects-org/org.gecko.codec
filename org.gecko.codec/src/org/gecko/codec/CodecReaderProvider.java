/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec;

import com.fasterxml.jackson.core.ObjectCodec;

/**
 * Simple provider for the reader and ObjectCodec used for parsing.
 * 
 * @author grune
 * @since Apr 10, 2024
 */
public class CodecReaderProvider<R> implements ObjectCodecProvider {

	private final R reader;
	private ObjectCodec codec;

	/**
	 * Creates a new instance.
	 */
	public CodecReaderProvider(R reader) {
		this.reader = reader;
	}

	/**
	 * Creates a new instance.
	 */
	public CodecReaderProvider(R reader, ObjectCodec codec) {
		this.reader = reader;
		setCodec(codec);
	}

	public R getReader() {
		return reader;
	}

	@Override
	public ObjectCodec getObjectCodec() {
		return codec;
	}

	/**
	 * Sets the codec.
	 * 
	 * @param codec the codec to set
	 */
	public void setCodec(ObjectCodec codec) {
		this.codec = codec;
	}

}
