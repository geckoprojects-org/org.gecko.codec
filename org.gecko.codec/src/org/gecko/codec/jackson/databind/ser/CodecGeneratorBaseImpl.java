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
import org.gecko.codec.CodecGenerator;
import org.gecko.codec.CodecGeneratorBase;
import org.gecko.codec.jackson.databind.CodecWriteContext;

import tools.jackson.core.Base64Variant;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.StreamWriteCapability;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.core.TokenStreamContext;
import tools.jackson.core.Version;
import tools.jackson.core.base.GeneratorBase;
import tools.jackson.core.io.IOContext;
import tools.jackson.core.json.DupDetector;
import tools.jackson.core.json.JsonWriteContext;
import tools.jackson.core.util.JacksonFeatureSet;

/**
 * A basic implementation of the Generator
 * 
 * @author mark
 * @since 09.01.2024
 */
public abstract class CodecGeneratorBaseImpl extends GeneratorBase implements CodecGenerator, CodecGeneratorBase {
	
	private CodecWriteContext _writeContext;

	protected CodecGeneratorBaseImpl(ObjectWriteContext writeCtxt, IOContext ioCtxt,
            int streamWriteFeatures) {
		super(writeCtxt, ioCtxt, streamWriteFeatures);	
		DupDetector dups = StreamWriteFeature.STRICT_DUPLICATE_DETECTION.enabledIn(streamWriteFeatures)
                ? DupDetector.rootDetector(this) : null;
		_writeContext = CodecWriteContext.createRootCodecContext(dups);
	}
	
	
	public CodecWriteContext getOutputContext() {
		return _writeContext;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#assignCurrentValue(java.lang.Object)
	 */
	@Override
	public void assignCurrentValue(Object v) {
		_writeContext.assignCurrentValue(v);		
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#currentValue()
	 */
	@Override
	public Object currentValue() {
		return _writeContext.currentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#version()
	 */
	@Override
	public Version version() {
		return new Version(1, 0, 0, "rc1", "org.eclipse.fennec", "gecko-codec");
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#streamWriteContext()
	 */
	@Override
	public TokenStreamContext streamWriteContext() {
		return _writeContext;
	}
	
	protected EObject getCurrentEObject() {
		
		if ( getOutputContext().inRoot()) {
			return ((EObject) (getOutputContext().currentValue()));
		}
		if ( getOutputContext().inObject() || getOutputContext().inArray()) {
			return (EObject) getOutputContext().getParent().currentValue();
		}
		return null;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#flush()
	 */
	@Override
	public void flush() {
		getOutputContext().reset(JsonWriteContext.TYPE_ROOT, null);
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.base.GeneratorBase#_releaseBuffers()
	 */
	@Override
	final protected void _releaseBuffers() {
//		This is called so if we let the unsupportedOperation we get the Exception
//		_reportUnsupportedOperation();
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.base.GeneratorBase#_verifyValueWrite(java.lang.String)
	 */
	@Override
	final protected void _verifyValueWrite(String typeMsg)  {
		// We have no use for this method. It does nothing
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeStartArray()
	 */
	@Override
	public JsonGenerator writeStartArray() {
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.base.GeneratorBase#writeStartArray(java.lang.Object, int)
	 */
	@Override
	public JsonGenerator writeStartArray(Object forValue, int size)  {
		writeStartArray(forValue);
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeStartArray(java.lang.Object)
	 */
	@Override
	public JsonGenerator writeStartArray(Object forValue)  {
		assignCurrentValue(forValue);
		int index = getOutputContext().getCurrentIndex();
		String name = getOutputContext().currentName();
		
		EStructuralFeature feature = getOutputContext().getFeature();
		CodecWriteContext ctx = getOutputContext().createChildArrayContext(forValue);
		ctx.setFeature(feature);
		_writeContext = ctx;
		writeStartArray();
		doStartWriteArray(index + 1, name, forValue);
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeEndArray()
	 */
	@Override
	public JsonGenerator writeEndArray() {
		if (getOutputContext().inRoot()) {
			return null;
		}
		if (!getOutputContext().inArray()) {
            _reportError("Current context is not array but " + getOutputContext().typeDesc());
        }
		_writeContext = (CodecWriteContext) _writeContext.clearAndGetParent();
		Object result = getOutputContext().currentValue();
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		int currIndex = getOutputContext().getCurrentIndex();
		String currName = getOutputContext().currentName();
		doEndWriteArray(currIndex, currName, result);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeStartObject()
	 */
	@Override
	public JsonGenerator writeStartObject()  {
		// Here we still have the root context. It will become the parent after this call
		_verifyValueWrite("Start a new EObject: " + getCurrentEObject());
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeStartObject(java.lang.Object)
	 */
	@Override
	public JsonGenerator writeStartObject(Object forValue)  {
		/* 
		 * We keep the original EObject in the parent context.
		 * The field of the object are serialized in a child context.
		 */
		assignCurrentValue(forValue);
		/*
		 * If we come from a EReference, we want to signal the start of reading an
		 * EObject for a field. So, the name should already be set in the context vie setField.
		 * So we are not in the state STATUS_EXPECT_NAME. If we start a new EObject, no field name 
		 * has been set before  
		 */
		boolean inRoot = getOutputContext().inRoot();
		int index = getOutputContext().getCurrentIndex();
		String name = getOutputContext().currentName();
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
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeEndObject()
	 */
	@Override
	public JsonGenerator writeEndObject() {
		if (!getOutputContext().inObject()) {
            _reportError("Current context is not Object but " + getOutputContext().typeDesc());
        }
		EObject result = getCurrentEObject();
		_writeContext = (CodecWriteContext) _writeContext.clearAndGetParent();
		/*
		 * If we have a root object, the we have no field name
		 */
		int currIndex = getOutputContext().getCurrentIndex();
		String currName = getOutputContext().currentName();
		
		if (getOutputContext().inRoot()) {
			doEndWriteRootEObject(result);
		} else {
			if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
				_reportError("Expect a value to write, but a field name is expected");
			}
			doEndWriteEObject(currIndex, currName, result);
		}		
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeName(java.lang.String)
	 */
	@Override
	public JsonGenerator writeName(String name)  {
		if (getOutputContext().writeName(name) == JsonWriteContext.STATUS_EXPECT_VALUE) {
			_reportError(String.format("Expected to retrieve a value instead of setting a field name for %s", name));
		}
		getOutputContext().writeName(name);
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeString(java.lang.String)
	 */
	@Override
	public JsonGenerator writeString(String text) {
		assignCurrentValue(text);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
//		the getOutputContext() has no currentName set because it is on the parent context and not on the array child context
		doWriteString(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), text);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeArray(java.lang.String[], int, int)
	 */
	@Override
	public JsonGenerator writeArray(String[] array, int offset, int length)  {
		super.writeArray(array, offset, length);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeString(char[], int, int)
	 */
	@Override
	public JsonGenerator writeString(char[] buffer, int offset, int len) {
		writeRaw(buffer, offset, len);
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeRawUTF8String(byte[], int, int)
	 */
	@Override
	public JsonGenerator writeRawUTF8String(byte[] buffer, int offset, int len) {
		writeRaw(new String(buffer, StandardCharsets.UTF_8));
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeUTF8String(byte[], int, int)
	 */
	@Override
	public JsonGenerator writeUTF8String(byte[] buffer, int offset, int len) {
		writeString(new String(buffer, StandardCharsets.UTF_8));
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeRaw(java.lang.String)
	 */
	@Override
	public JsonGenerator writeRaw(String text)  {
		writeString(text);
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeRaw(java.lang.String, int, int)
	 */
	@Override
	public JsonGenerator writeRaw(String text, int offset, int len)  {
		writeString(text);
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeRaw(char[], int, int)
	 */
	@Override
	public JsonGenerator writeRaw(char[] text, int offset, int len) {
		assignCurrentValue(text);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteChars(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), text);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}


	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeRaw(char)
	 */
	@Override
	public JsonGenerator writeRaw(char c) {
		assignCurrentValue(c);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}		
		doWriteChar(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), c);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeBinary(tools.jackson.core.Base64Variant, byte[], int, int)
	 */
	@Override
	public JsonGenerator writeBinary(Base64Variant bv, byte[] data, int offset, int len)  {
		assignCurrentValue(data);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}		
		doWriteBinary(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), bv, data, offset, len);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNumber(int)
	 */
	@Override
	public JsonGenerator writeNumber(int v)  {
		assignCurrentValue(v);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}		
		doWriteInt(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), v);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNumber(long)
	 */
	@Override
	public JsonGenerator writeNumber(long v)  {
		assignCurrentValue(v);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}		
		doWriteLong(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), v);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNumber(short)
	 */
	@Override
	public JsonGenerator writeNumber(short v) throws JacksonException {
		assignCurrentValue(v);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}		
		doWriteShort(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), v);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNumber(java.math.BigInteger)
	 */
	@Override
	public JsonGenerator writeNumber(BigInteger v) {
		assignCurrentValue(v);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}		
		doWriteBigInt(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), v);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNumber(double)
	 */
	@Override
	public JsonGenerator writeNumber(double v)  {
		assignCurrentValue(v);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteDouble(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), v);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}


	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNumber(float)
	 */
	@Override
	public JsonGenerator writeNumber(float v) {
		assignCurrentValue(v);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteFloat(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), v);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}


	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNumber(java.math.BigDecimal)
	 */
	@Override
	public JsonGenerator writeNumber(BigDecimal v)  {
		assignCurrentValue(v);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteBigDecimal(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), v);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNumber(java.lang.String)
	 */
	@Override
	public JsonGenerator writeNumber(String encodedValue) {
		assignCurrentValue(encodedValue);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteStringNumber(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), encodedValue);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeBoolean(boolean)
	 */
	@Override
	public JsonGenerator writeBoolean(boolean state) {
		assignCurrentValue(state);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteBoolean(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), state);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeNull()
	 */
	@Override
	public JsonGenerator writeNull()  {
		assignCurrentValue(null);
		if (getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Expect a value to write, but a field name is expected");
		}
		doWriteNull(getOutputContext().getCurrentIndex(), getOutputContext().currentName());
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#canWriteObjectId()
	 */
	@Override
	public boolean canWriteObjectId() {
		return true;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeObjectId(java.lang.Object)
	 */
	@Override
	public JsonGenerator writeObjectId(Object id)  {
		assignCurrentValue(id);
		if(getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Error writing object id while expecting a value");
		}
		doWriteObjectId(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), id);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writePropertyId(long)
	 */
	@Override
	public JsonGenerator writePropertyId(long id) throws JacksonException {
		assignCurrentValue(id);
		if(getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Error writing property id while expecting a value");
		}
		doWritePropertyId(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), id);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#canWriteTypeId()
	 */
	@Override
	public boolean canWriteTypeId() {
		return true;
	}
	

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#writeTypeId(java.lang.Object)
	 */
	@Override
	public JsonGenerator writeTypeId(Object id) {
		assignCurrentValue(id);
		if(getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Error writing type information while expecting a value");
		}
		doWriteType(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), id);
		CodecWriteContext.resetFeature(getOutputContext());
		return this;		
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
	 * @see org.gecko.codec.CodecGeneratorBase#writeSuperTypes(java.lang.String[])
	 */
	@Override
	public JsonGenerator writeSuperTypes(String[] supertypes)  {
		assignCurrentValue(supertypes);
		if(getOutputContext().writeValue() == JsonWriteContext.STATUS_EXPECT_NAME) {
			_reportError("Error writing supertype information while expecting a value");
		}
		doWriteSuperTypes(getOutputContext().getCurrentIndex(), getOutputContext().currentName(), supertypes);
		return this;
	}
	

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.base.GeneratorBase#_closeInput()
	 */
	@Override
	protected void _closeInput() throws IOException {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#streamWriteOutputTarget()
	 */
	@Override
	public Object streamWriteOutputTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#streamWriteOutputBuffered()
	 */
	@Override
	public int streamWriteOutputBuffered() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonGenerator#streamWriteCapabilities()
	 */
	@Override
	public JacksonFeatureSet<StreamWriteCapability> streamWriteCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
