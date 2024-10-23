///**
// * Copyright (c) 2012 - 2024 Data In Motion and others.
// * All rights reserved. 
// * 
// * This program and the accompanying materials are made
// * available under the terms of the Eclipse Public License 2.0
// * which is available at https://www.eclipse.org/legal/epl-2.0/
// *
// * SPDX-License-Identifier: EPL-2.0
// * 
// * Contributors:
// *     Data In Motion - initial API and implementation
// */
//package org.gecko.codec.demo.resource;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//
//import org.bson.types.ObjectId;
//import org.eclipse.emf.ecore.EObject;
//import org.gecko.codec.jackson.databind.ser.CodecGeneratorBaseImpl;
//
//import com.fasterxml.jackson.core.Base64Variant;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.ObjectCodec;
//import com.fasterxml.jackson.core.io.IOContext;
//
///**
// * 
// * @author ilenia
// * @since Oct 16, 2024
// */
//public class JsonCodecGenerator extends CodecGeneratorBaseImpl {
//	
//	private JsonGenerator jsonGenerator;
//
//
//	public JsonCodecGenerator(JsonGenerator jsonGenerator, ObjectCodec codec) {
//		super(-1, codec, null);
//		this.jsonGenerator = jsonGenerator;
//	}
//	
//	
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doStartWriteRootEObject(org.eclipse.emf.ecore.EObject)
//	 */
//	@Override
//	public void doStartWriteRootEObject(EObject eObject) throws IOException {
//		doStartWriteEObject(0, "", eObject);		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doEndWriteRootEObject(org.eclipse.emf.ecore.EObject)
//	 */
//	@Override
//	public void doEndWriteRootEObject(EObject object) throws IOException {
//		jsonGenerator.writeEndObject();
//		jsonGenerator.flush();
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteType(int, java.lang.String, java.lang.Object)
//	 */
//	@Override
//	public void doWriteType(int index, String fieldName, Object object) throws IOException {
////		TODO: ARE WE REALLY USING THIS???
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteSuperTypes(int, java.lang.String, java.lang.String[])
//	 */
//	@Override
//	public void doWriteSuperTypes(int index, String fieldName, String[] superTypes) throws IOException {
////		TODO: ARE WE REALLY USING THIS???
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteObjectId(int, java.lang.String, java.lang.Object)
//	 */
//	@Override
//	public void doWriteObjectId(int index, String fieldName, Object object) throws IOException {
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doStartWriteEObject(int, java.lang.String, org.eclipse.emf.ecore.EObject)
//	 */
//	@Override
//	public void doStartWriteEObject(int index, String fieldName, EObject object) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doEndWriteEObject(int, java.lang.String, org.eclipse.emf.ecore.EObject)
//	 */
//	@Override
//	public void doEndWriteEObject(int index, String fieldName, EObject object) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteArray(java.lang.Object[], int, int, java.lang.Class)
//	 */
//	@Override
//	public <T> void doWriteArray(T[] array, int offset, int length, Class<T> clazz) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doStartWriteArray(int, java.lang.String, java.lang.Object)
//	 */
//	@Override
//	public void doStartWriteArray(int index, String fieldName, Object object) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doEndWriteArray(int, java.lang.String, java.lang.Object)
//	 */
//	@Override
//	public void doEndWriteArray(int index, String fieldName, Object object) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteString(int, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public void doWriteString(int index, String fieldName, String value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteShort(int, java.lang.String, short)
//	 */
//	@Override
//	public void doWriteShort(int index, String fieldName, short value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteLong(int, java.lang.String, long)
//	 */
//	@Override
//	public void doWriteLong(int index, String fieldName, long value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteInt(int, java.lang.String, int)
//	 */
//	@Override
//	public void doWriteInt(int index, String fieldName, int value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteBigInt(int, java.lang.String, java.math.BigInteger)
//	 */
//	@Override
//	public void doWriteBigInt(int index, String fieldName, BigInteger value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteBigDecimal(int, java.lang.String, java.math.BigDecimal)
//	 */
//	@Override
//	public void doWriteBigDecimal(int index, String fieldName, BigDecimal value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteFloat(int, java.lang.String, float)
//	 */
//	@Override
//	public void doWriteFloat(int index, String fieldName, float value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteDouble(int, java.lang.String, double)
//	 */
//	@Override
//	public void doWriteDouble(int index, String fieldName, double value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteChar(int, java.lang.String, char)
//	 */
//	@Override
//	public void doWriteChar(int index, String fieldName, char value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteChars(int, java.lang.String, char[])
//	 */
//	@Override
//	public void doWriteChars(int index, String fieldName, char[] values) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteBoolean(int, java.lang.String, boolean)
//	 */
//	@Override
//	public void doWriteBoolean(int index, String fieldName, boolean value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteStringNumber(int, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public void doWriteStringNumber(int index, String fieldName, String value) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteBinary(int, java.lang.String, com.fasterxml.jackson.core.Base64Variant, byte[], int, int)
//	 */
//	@Override
//	public void doWriteBinary(int index, String fieldName, Base64Variant b64variant, byte[] values, int offset, int len)
//			throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteNull(int, java.lang.String)
//	 */
//	@Override
//	public void doWriteNull(int index, String fieldName) throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
