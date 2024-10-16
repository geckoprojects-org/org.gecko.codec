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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.gecko.codec.jackson.CodecGenerator;
import org.gecko.codec.jackson.CodecGeneratorBase;
import org.gecko.codec.jackson.databind.CodecWriteContext;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonWriteContext;

/**
 * A basic class to write Lucene Documents
 * @author mark
 * @since 09.01.2024
 */
public abstract class CodecGeneratorBaseImpl extends GeneratorBase implements CodecGenerator, CodecGeneratorBase {
	
	
	/**
	 * Creates a new instance.
	 * @param features
	 * @param codec
	 * @param ctxt
	 */
	protected CodecGeneratorBaseImpl(int features, ObjectCodec codec, IOContext ctxt) {
		super(features, codec, ctxt);
		DupDetector dups = Feature.STRICT_DUPLICATE_DETECTION.enabledIn(features)
                ? DupDetector.rootDetector(this) : null;
		_writeContext = CodecWriteContext.createRootCodecContext(dups);
	}
	
	/**
	 * Creates a new instance.
	 * @param features
	 * @param codec
	 * @param ctxt
	 */
	protected CodecGeneratorBaseImpl(int features, ObjectCodec codec, IOContext ctxt, CodecWriteContext writeContext) {
		super(features, codec, ctxt, writeContext);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.GeneratorBase#getOutputContext()
	 */
	@Override
	public CodecWriteContext getOutputContext() {
		return (CodecWriteContext)super.getOutputContext();
	}
	
	protected EObject getCurrentEObject() {
		if ( _writeContext.inRoot()) {
			return (EObject) _writeContext.getCurrentValue();
		}
		if ( _writeContext.inObject() || _writeContext.inArray()) {
			return (EObject) _writeContext.getParent().getCurrentValue();
		}
		return null;
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
//		System.out.println("Verify message: " + typeMsg);
		// TODO add diagnostic here
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeStartArray()
	 */
	@Override
	public void writeStartArray() throws IOException {
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeStartArray(java.lang.Object, int)
	 */
	@Override
	public void writeStartArray(Object forValue, int size) throws IOException {
		writeStartArray(forValue);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeStartArray(java.lang.Object)
	 */
	@Override
	public void writeStartArray(Object forValue) throws IOException {
		setCurrentValue(forValue);
		int index = _writeContext.getCurrentIndex();
		String name = _writeContext.getCurrentName();
		
		EStructuralFeature feature = getOutputContext().getFeature();
		CodecWriteContext ctx = getOutputContext().createChildArrayContext(forValue);
		ctx.setFeature(feature);
		_writeContext = ctx;
		
		writeStartArray();
		doStartWriteArray(index + 1, name, forValue);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeEndArray()
	 */
	@Override
	public void writeEndArray() throws IOException {
		if (_writeContext.inRoot()) {
			return;
		}
		if (!_writeContext.inArray()) {
            _reportError("Current context is not array but " + _writeContext.typeDesc());
        }
		_writeContext = _writeContext.clearAndGetParent();
		Object result = _writeContext.getCurrentValue();
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doEndWriteArray(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), result);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeStartObject()
	 */
	@Override
	public void writeStartObject() throws IOException {
		// Here we still have the root context. It will become the parent after this call
		_verifyValueWrite("Start a new EObject: " + getCurrentEObject());
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeStartObject(java.lang.Object)
	 */
	@Override
	public void writeStartObject(Object forValue) throws IOException {
		/* 
		 * We keep the original EObject in the parent context.
		 * The field of the object are serialized in a child context.
		 */
		setCurrentValue(forValue);
		/*
		 * If we come from a EReference, we want to signal the start of reading an
		 * EObject for a field. So, the name should already be set in the context vie setField.
		 * So we are not in the state STATUS_EXPECT_NAME. If we start a new EObject, no field name 
		 * has been set before  
		 */
		boolean inRoot = _writeContext.inRoot();
		int index = _writeContext.getCurrentIndex();
		String name = _writeContext.getCurrentName();
		/*
		 * We create the sub / child context for the fields of the EObject
		 */
		EStructuralFeature feature = getOutputContext().getFeature();
		CodecWriteContext ctx = getOutputContext().createChildObjectContext(forValue);
		ctx.setFeature(feature);
		_writeContext = ctx;
		writeStartObject();
		if (inRoot) {
			doStartWriteRootEObject(getCurrentEObject());
		} else {
			doStartWriteEObject(index + 1, name, getCurrentEObject());
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeEndObject()
	 */
	@Override
	public void writeEndObject() throws IOException {
		if (!_writeContext.inObject()) {
            _reportError("Current context is not Object but " + _writeContext.typeDesc());
        }
		EObject result = getCurrentEObject();
		_writeContext = _writeContext.clearAndGetParent();
		/*
		 * If we have a root object, the we have no field name
		 */
		if (_writeContext.inRoot()) {
			doEndWriteRootEObject(result);
		} else {
			if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
				_reportError("Expect a value to write, but a field name is expected");
			}
			doEndWriteEObject(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), result);
		}
		CodecWriteContext.resetFeature(_writeContext);
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
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
//		the _writeContext has no currentName set because it is on the parent context and not on the array child context
		doWriteString(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), text);
		CodecWriteContext.resetFeature(_writeContext);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeArray(java.lang.String[], int, int)
	 */
	@Override
	public void writeArray(String[] array, int offset, int length) throws IOException {
		// TODO Auto-generated method stub
		super.writeArray(array, offset, length);
		CodecWriteContext.resetFeature(_writeContext);
	}
	
	public void writeOneShotArray(Object[] array, int offset, int length) throws IOException {
		CodecWriteContext.resetFeature(_writeContext);
	}
	
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
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteChars(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), text);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeRaw(char)
	 */
	@Override
	public void writeRaw(char c) throws IOException {
		_writeContext.setCurrentValue(c);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteChar(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), c);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeBinary(com.fasterxml.jackson.core.Base64Variant, byte[], int, int)
	 */
	@Override
	public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) throws IOException {
		_writeContext.setCurrentValue(data);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteBinary(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), bv, data, offset, len);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(int)
	 */
	@Override
	public void writeNumber(int v) throws IOException {
		_writeContext.setCurrentValue(v);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteInt(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(long)
	 */
	@Override
	public void writeNumber(long v) throws IOException {
		_writeContext.setCurrentValue(v);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteLong(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.math.BigInteger)
	 */
	@Override
	public void writeNumber(BigInteger v) throws IOException {
		_writeContext.setCurrentValue(v);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteBigInt(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(double)
	 */
	@Override
	public void writeNumber(double v) throws IOException {
		_writeContext.setCurrentValue(v);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteDouble(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(float)
	 */
	@Override
	public void writeNumber(float v) throws IOException {
		_writeContext.setCurrentValue(v);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteFloat(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.math.BigDecimal)
	 */
	@Override
	public void writeNumber(BigDecimal v) throws IOException {
		_writeContext.setCurrentValue(v);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteBigDecimal(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), v);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNumber(java.lang.String)
	 */
	@Override
	public void writeNumber(String encodedValue) throws IOException {
		_writeContext.setCurrentValue(encodedValue);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteStringNumber(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), encodedValue);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(boolean state) throws IOException {
		_writeContext.setCurrentValue(state);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteBoolean(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), state);
		CodecWriteContext.resetFeature(_writeContext);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeNull()
	 */
	@Override
	public void writeNull() throws IOException {
		_writeContext.setCurrentValue(null);
		if (_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteNull(_writeContext.getCurrentIndex(), _writeContext.getCurrentName());
		CodecWriteContext.resetFeature(_writeContext);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#canWriteObjectId()
	 */
	@Override
	public boolean canWriteObjectId() {
		return true;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeObjectId(java.lang.Object)
	 */
	@Override
	public void writeObjectId(Object id) throws IOException {
		setCurrentValue(id);
		if(_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Error writing object id while expecting a value");
		}
		doWriteObjectId(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), id);
		CodecWriteContext.resetFeature(_writeContext);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#canWriteTypeId()
	 */
	@Override
	public boolean canWriteTypeId() {
		return true;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonGenerator#writeTypeId(java.lang.Object)
	 */
	@Override
	public void writeTypeId(Object id) throws IOException {
		setCurrentValue(id);
		if(_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Error writing type information while expecting a value");
		}
		doWriteType(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), id);
		CodecWriteContext.resetFeature(_writeContext);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.CodeGeneratorBase#canWriteOneShotArray()
	 */
	@Override
	public boolean canWriteOneShotArray() {
		return true;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.CodeGeneratorBase#writeArray(java.lang.Object[], int, int, java.lang.Class)
	 */
	@Override
	public <T> void writeArray(T[] array, int offset, int length, Class<T> clazz) throws IOException {
		setCurrentValue(array);
		if(_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Error writing array expecting a value, but need a name");
		}
		doWriteArray(array, offset, length, clazz);
		CodecWriteContext.resetFeature(_writeContext);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.CodeGeneratorBase#canWriteSuperTypes()
	 */
	@Override
	public boolean canWriteSuperTypes() {
		return true;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.CodeGeneratorBase#writeSuperTypes(java.lang.String[])
	 */
	@Override
	public void writeSuperTypes(String[] supertypes) throws IOException {
		setCurrentValue(supertypes);
		if(_writeContext.writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Error writing supertype information while expecting a value");
		}
		doWriteSuperTypes(_writeContext.getCurrentIndex(), _writeContext.getCurrentName(), supertypes);
	}

}
