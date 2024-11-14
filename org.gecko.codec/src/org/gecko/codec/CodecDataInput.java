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

import java.io.DataInput;
import java.io.IOException;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectReader;

/**
 * Dummy DataInput for the {@link ObjectReader#readValue(DataInput)} method containing a reader and an {@link ObjectCodec}
 * (see {@link CodecReaderProvider})
 * 
 * @author grune
 * @since Apr 10, 2024
 */
public class CodecDataInput<R> extends CodecReaderProvider<R> implements DataInput {

	/**
	 * 
	 * Creates a new instance.
	 * 
	 * @param reader
	 * @param mapper
	 */
	public CodecDataInput(R reader, ObjectCodec mapper) {
		super(reader, mapper);
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readBoolean()
	 */
	@Override
	public boolean readBoolean() throws IOException {
		return false;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readByte()
	 */
	@Override
	public byte readByte() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readChar()
	 */
	@Override
	public char readChar() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readDouble()
	 */
	@Override
	public double readDouble() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readFloat()
	 */
	@Override
	public float readFloat() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readFully(byte[])
	 */
	@Override
	public void readFully(byte[] arg0) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readFully(byte[], int, int)
	 */
	@Override
	public void readFully(byte[] arg0, int arg1, int arg2) throws IOException {
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readInt()
	 */
	@Override
	public int readInt() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readLine()
	 */
	@Override
	public String readLine() throws IOException {
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readLong()
	 */
	@Override
	public long readLong() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readShort()
	 */
	@Override
	public short readShort() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readUTF()
	 */
	@Override
	public String readUTF() throws IOException {
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readUnsignedByte()
	 */
	@Override
	public int readUnsignedByte() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#readUnsignedShort()
	 */
	@Override
	public int readUnsignedShort() throws IOException {
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.io.DataInput#skipBytes(int)
	 */
	@Override
	public int skipBytes(int arg0) throws IOException {
		return 0;
	}

}
