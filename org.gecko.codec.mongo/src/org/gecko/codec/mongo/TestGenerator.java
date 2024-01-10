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
package org.gecko.codec.mongo;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonWriteContext;

/**
 * A basic class to write Lucene Documents
 * @author mark
 * @since 09.01.2024
 */
public class TestGenerator extends GeneratorBase {
	
//	private enum State {
//		FIELD_NAME,
//		VALUE,
//		ARRAY,
//		OBJECT,
//		NONE
//	}
//	
//	private Set<State> currentState = Set.of(State.NONE);
//	private volatile String currentFieldName;

	/**
	 * Creates a new instance.
	 * @param features
	 * @param codec
	 * @param ctxt
	 */
	protected TestGenerator(int features, ObjectCodec codec, IOContext ctxt) {
		super(features, codec, ctxt);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.GeneratorBase#flush()
	 */
	@Override
	public void flush() throws IOException {
		System.out.println("flush generators");
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.GeneratorBase#_releaseBuffers()
	 */
	@Override
	protected void _releaseBuffers() {
		System.out.println("release buffers");
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.GeneratorBase#_verifyValueWrite(java.lang.String)
	 */
	@Override
	protected void _verifyValueWrite(String typeMsg) throws IOException {
		System.out.println("verify value write " + typeMsg);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeStartArray()
	 */
	@Override
	public void writeStartArray() throws IOException {
		System.out.println("start write array");
		
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeEndArray()
	 */
	@Override
	public void writeEndArray() throws IOException {
		System.out.println("end write array");
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeStartObject()
	 */
	@Override
	public void writeStartObject() throws IOException {
		System.out.println("start object " + toString());
//		currentState = Set.of(State.OBJECT);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeEndObject()
	 */
	@Override
	public void writeEndObject() throws IOException {
		System.out.println("end object");
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeFieldName(java.lang.String)
	 */
	@Override
	public void writeFieldName(String name) throws IOException {
		
		System.out.println("field name " + name  + ": " + getCurrentValue() + " - " + getOutputContext().getCurrentValue());
	}

	/**
	 * @param name
	 */
	private void _writeFieldName(String name) {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeString(java.lang.String)
	 */
	@Override
	public void writeString(String text) throws IOException {
		System.out.println("string value " + text);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeString(char[], int, int)
	 */
	@Override
	public void writeString(char[] buffer, int offset, int len) throws IOException {
		System.out.println("write string char buffer " + String.valueOf(buffer));
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRawUTF8String(byte[], int, int)
	 */
	@Override
	public void writeRawUTF8String(byte[] buffer, int offset, int len) throws IOException {
		System.out.println("write raw utf8 byte buffer " + new String(buffer, StandardCharsets.UTF_8));
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeUTF8String(byte[], int, int)
	 */
	@Override
	public void writeUTF8String(byte[] buffer, int offset, int len) throws IOException {
		System.out.println("write raw utf8 string " + new String(buffer, StandardCharsets.UTF_8));
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRaw(java.lang.String)
	 */
	@Override
	public void writeRaw(String text) throws IOException {
		System.out.println("write raw string basic " + text);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRaw(java.lang.String, int, int)
	 */
	@Override
	public void writeRaw(String text, int offset, int len) throws IOException {
		System.out.println("write raw string " + text);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRaw(char[], int, int)
	 */
	@Override
	public void writeRaw(char[] text, int offset, int len) throws IOException {
		System.out.println("write raw char[] " + String.valueOf(text));
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRaw(char)
	 */
	@Override
	public void writeRaw(char c) throws IOException {
		System.out.println("write raw char " + c);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeBinary(com.fasterxml.jackson.core.Base64Variant, byte[], int, int)
	 */
	@Override
	public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) throws IOException {
		System.out.println("write binary " + data);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(int)
	 */
	@Override
	public void writeNumber(int v) throws IOException {
		System.out.println("write int " + v);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(long)
	 */
	@Override
	public void writeNumber(long v) throws IOException {
		System.out.println("write long " + v);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.math.BigInteger)
	 */
	@Override
	public void writeNumber(BigInteger v) throws IOException {
		System.out.println("write bigint " + v);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(double)
	 */
	@Override
	public void writeNumber(double v) throws IOException {
		System.out.println("write double " + v);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(float)
	 */
	@Override
	public void writeNumber(float v) throws IOException {
		System.out.println("write float " + v);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.math.BigDecimal)
	 */
	@Override
	public void writeNumber(BigDecimal v) throws IOException {
		System.out.println("write big decimal " + v);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.lang.String)
	 */
	@Override
	public void writeNumber(String encodedValue) throws IOException {
		System.out.println("write encoded number " + encodedValue);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(boolean state) throws IOException {
		System.out.println("write boolean " + state);
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNull()
	 */
	@Override
	public void writeNull() throws IOException {
		System.out.println("write null");
		// TODO Auto-generated method stub

	}

}
