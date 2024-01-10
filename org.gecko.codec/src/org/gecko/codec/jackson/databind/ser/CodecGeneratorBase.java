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
package org.gecko.codec.jackson.databind.ser;

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
public abstract class CodecGeneratorBase extends GeneratorBase {
	
	
	/**
	 * Creates a new instance.
	 * @param features
	 * @param codec
	 * @param ctxt
	 */
	protected CodecGeneratorBase(int features, ObjectCodec codec, IOContext ctxt) {
		super(features, codec, ctxt);
	}
	
	/**
	 * Creates a new instance.
	 * @param features
	 * @param codec
	 * @param ctxt
	 */
	protected CodecGeneratorBase(int features, ObjectCodec codec, IOContext ctxt, JsonWriteContext writeContext) {
		super(features, codec, ctxt, writeContext);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.GeneratorBase#flush()
	 */
	@Override
	public void flush() throws IOException {
		System.out.println("Flush Objects");
		_writeContext.reset(JsonWriteContext.TYPE_ROOT);
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
		System.out.println("Verify message: " + typeMsg);
		// TODO add diagnostic here
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
		// Here we still have the root context. It will become the parent after this call
		_verifyValueWrite("Start a new EObject: " + _writeContext.getCurrentValue());
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeStartObject(java.lang.Object)
	 */
	@Override
	public void writeStartObject(Object forValue) throws IOException {
		// We keep the original EObject in the parent context and let the fields serialize in a child context
		setCurrentValue(forValue);
		writeStartObject();
		_writeContext = _writeContext.createChildObjectContext(forValue);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeEndObject()
	 */
	@Override
	public void writeEndObject() throws IOException {
		if (!_writeContext.inObject()) {
            _reportError("Current context not Object but "+_writeContext.typeDesc());
        }
		_writeContext = _writeContext.clearAndGetParent();
		System.out.println("End Object: " + _writeContext.getCurrentValue());
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeFieldName(java.lang.String)
	 */
	@Override
	public void writeFieldName(String name) throws IOException {
		if (_writeContext.writeFieldName(name) == JsonWriteContext.STATUS_EXPECT_VALUE) {
			_reportError("Expected to retrieve a value instead of setting a field name");
		}
		_writeContext.writeFieldName(name);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeString(java.lang.String)
	 */
	@Override
	public void writeString(String text) throws IOException {
		_writeContext.setCurrentValue(text);
		_writeContext.writeValue();
		doWriteString(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), text);
	}
	
	protected abstract void doWriteString(int index, String fieldName, String value) throws IOException;
	protected abstract void doWriteShort(int index, String fieldName, short value) throws IOException;
	protected abstract void doWriteLong(int index, String fieldName, long value) throws IOException;
	protected abstract void doWriteInt(int index, String fieldName, int value) throws IOException;
	protected abstract void doWriteBigInt(int index, String fieldName, BigInteger value) throws IOException;
	protected abstract void doWriteBigDecimal(int index, String fieldName, BigDecimal value) throws IOException;
	protected abstract void doWriteFloat(int index, String fieldName, float value) throws IOException;
	protected abstract void doWriteDouble(int index, String fieldName, double value) throws IOException;
	protected abstract void doWriteChar(int index, String fieldName, char value) throws IOException;
	protected abstract void doWriteChars(int index, String fieldName, char[] values) throws IOException;
	protected abstract void doWriteBoolean(int index, String fieldName, boolean value) throws IOException;
	protected abstract void doWriteStringNumber(int index, String fieldName, String value) throws IOException;
	protected abstract void doWriteBinary(int index, String fieldName, Base64Variant b64variant,
            byte[] values, int offset, int len) throws IOException;
	protected abstract void doWriteNull(int index, String fieldName) throws IOException;
//	protected abstract void doWriteByte(int index, String fieldName, byte value) throws IOException;
//	protected abstract void doWriteStringBytes(int index, String fieldName, byte[] values) throws IOException;

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeString(char[], int, int)
	 */
	@Override
	public void writeString(char[] buffer, int offset, int len) throws IOException {
		writeRaw(buffer, offset, len);
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
		writeString(text);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRaw(java.lang.String, int, int)
	 */
	@Override
	public void writeRaw(String text, int offset, int len) throws IOException {
		writeString(text);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRaw(char[], int, int)
	 */
	@Override
	public void writeRaw(char[] text, int offset, int len) throws IOException {
		_writeContext.setCurrentValue(text);
		_writeContext.writeValue();
		doWriteChars(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), text);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRaw(char)
	 */
	@Override
	public void writeRaw(char c) throws IOException {
		_writeContext.setCurrentValue(c);
		_writeContext.writeValue();
		doWriteChar(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), c);

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeBinary(com.fasterxml.jackson.core.Base64Variant, byte[], int, int)
	 */
	@Override
	public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) throws IOException {
		_writeContext.setCurrentValue(data);
		_writeContext.writeValue();
		doWriteBinary(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), bv, data, offset, len);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(int)
	 */
	@Override
	public void writeNumber(int v) throws IOException {
		_writeContext.setCurrentValue(v);
		_writeContext.writeValue();
		doWriteInt(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(long)
	 */
	@Override
	public void writeNumber(long v) throws IOException {
		_writeContext.setCurrentValue(v);
		_writeContext.writeValue();
		doWriteLong(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.math.BigInteger)
	 */
	@Override
	public void writeNumber(BigInteger v) throws IOException {
		_writeContext.setCurrentValue(v);
		_writeContext.writeValue();
		doWriteBigInt(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(double)
	 */
	@Override
	public void writeNumber(double v) throws IOException {
		_writeContext.setCurrentValue(v);
		_writeContext.writeValue();
		doWriteDouble(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(float)
	 */
	@Override
	public void writeNumber(float v) throws IOException {
		_writeContext.setCurrentValue(v);
		_writeContext.writeValue();
		doWriteFloat(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.math.BigDecimal)
	 */
	@Override
	public void writeNumber(BigDecimal v) throws IOException {
		_writeContext.setCurrentValue(v);
		_writeContext.writeValue();
		doWriteBigDecimal(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.lang.String)
	 */
	@Override
	public void writeNumber(String encodedValue) throws IOException {
		_writeContext.setCurrentValue(encodedValue);
		_writeContext.writeValue();
		doWriteStringNumber(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), encodedValue);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(boolean state) throws IOException {
		_writeContext.setCurrentValue(state);
		_writeContext.writeValue();
		doWriteBoolean(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), state);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNull()
	 */
	@Override
	public void writeNull() throws IOException {
		_writeContext.setCurrentValue(null);
		_writeContext.writeValue();
		doWriteNull(_writeContext.getCurrentIndex(), _writeContext.getCurrentName());
	}

}
