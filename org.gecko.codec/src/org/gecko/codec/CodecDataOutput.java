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

import java.io.DataOutput;
import java.io.IOException;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Dummy DataOutput for the {@link ObjectWriter#writeValue(DataOutput)} method
 * containing a writer (see {@link CodecWriterProvider})
 * 
 * @author grune
 * @since Apr 10, 2024
 */
public class CodecDataOutput<W> extends CodecWriterProvider<W> implements DataOutput {

	/**
	 * Creates a new instance.
	 * 
	 * @param writer
	 */
	public CodecDataOutput(W writer, ObjectCodec mapper) {
		super(writer, mapper);
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#write(int)
	 */
	@Override
	public void write(int arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#write(byte[])
	 */
	@Override
	public void write(byte[] arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(boolean arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeByte(int)
	 */
	@Override
	public void writeByte(int arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeBytes(java.lang.String)
	 */
	@Override
	public void writeBytes(String arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeChar(int)
	 */
	@Override
	public void writeChar(int arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeChars(java.lang.String)
	 */
	@Override
	public void writeChars(String arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeDouble(double)
	 */
	@Override
	public void writeDouble(double arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeFloat(float)
	 */
	@Override
	public void writeFloat(float arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeInt(int)
	 */
	@Override
	public void writeInt(int arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeLong(long)
	 */
	@Override
	public void writeLong(long arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeShort(int)
	 */
	@Override
	public void writeShort(int arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataOutput#writeUTF(java.lang.String)
	 */
	@Override
	public void writeUTF(String arg0) throws IOException {
	}

}
