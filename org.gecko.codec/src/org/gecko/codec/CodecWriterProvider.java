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
 * 
 * @author grune
 * @since Apr 10, 2024
 */
public class CodecWriterProvider<W> implements ObjectCodecProvider{
	
	private final W writer;
	private ObjectCodec objectCodec;

	/**
	 * Creates a new instance.
	 */
	public CodecWriterProvider(W writer, ObjectCodec objectCodec) {
		this.writer = writer;
		this.objectCodec = objectCodec;
	}

	public W getWriter() {
		return writer;
	}

	@Override
	public ObjectCodec getObjectCodec() {
		return objectCodec;
	}
	
}
