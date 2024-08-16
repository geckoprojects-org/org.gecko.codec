///**
// * Copyright (c) 2012 - 2024 Data In Motion and others.
// * All rights reserved. 
// * 
// * This program and the accompanying materials are made available under the terms of the 
// * Eclipse Public License v2.0 which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v20.html
// * 
// * Contributors:
// *     Data In Motion - initial API and implementation
// */
//package org.gecko.codec.demo;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//
//import org.bson.BsonBinary;
//import org.bson.BsonWriter;
//import org.bson.types.Decimal128;
//import org.bson.types.ObjectId;
//import org.eclipse.emf.ecore.EObject;
//import org.gecko.codec.jackson.databind.ser.CodecGeneratorBaseImpl;
//
//import com.fasterxml.jackson.core.Base64Variant;
//import com.fasterxml.jackson.core.ObjectCodec;
//
///**
// * 
// * @author grune
// * @since Jan 29, 2024
// */
//public class MongoCodecGenerator extends CodecGeneratorBaseImpl {
//
//	private BsonWriter writer;
//
//	/**
//	 * Creates a new instance.
//	 * @param objectCodec 
//	 * 
//	 * @param writer2
//	 */
//	public MongoCodecGenerator(BsonWriter writer, ObjectCodec objectCodec) {
//		super(-1, objectCodec, null);
//		this.writer = writer;
//	}
//
//	@Override
//	public void doStartWriteRootEObject(EObject eObject) throws IOException {
//		doStartWriteEObject(0, "", eObject);
//	}
//
//	@Override
//	public void doEndWriteRootEObject(EObject object) throws IOException {
//		writer.writeEndDocument();
//		writer.flush();
//	}
//
//	@Override
//	public void doWriteType(int index, String fieldName, Object object) throws IOException {
//		writer.writeString(fieldName, object.toString());
//	}
//
//	@Override
//	public void doWriteSuperTypes(int index, String fieldName, String[] superTypes) throws IOException {
//		String types = String.join(",", superTypes);
//		writer.writeString(fieldName, types);
//	}
//
//	@Override
//	public void doWriteObjectId(int index, String fieldName, Object object) throws IOException {
//		if (object instanceof ObjectId) {
//			writer.writeObjectId(fieldName, (ObjectId) object);
//		} else if (object instanceof String) {
//			if (ObjectId.isValid((String) object)) {
//				ObjectId objectId = new ObjectId((String) object);
//				writer.writeObjectId(fieldName, objectId);
//			} else {
//				writer.writeString(fieldName, (String) object);
//			}
//		} else if (object == null) {
//			writer.writeObjectId(fieldName, new ObjectId());
//		} else {
//			System.out.println("???" + object);
//		}
//
//	}
//
//	@Override
//	public void doStartWriteEObject(int index, String fieldName, EObject object) throws IOException {
//		if (fieldName != null && !fieldName.isEmpty()) {
//			writer.writeName(fieldName);
//		}
//		writer.writeStartDocument();
//	}
//
//	@Override
//	public void doEndWriteEObject(int index, String fieldName, EObject object) throws IOException {
//		writer.writeEndDocument();
//	}
//
//	@Override
//	public <T> void doWriteArray(T[] array, int offset, int length, Class<T> clazz) throws IOException {
//		writer.writeEndArray();
//	}
//
//	@Override
//	public void doStartWriteArray(int index, String fieldName, Object object) throws IOException {
//		writer.writeStartArray(fieldName);
//	}
//
//	@Override
//	public void doEndWriteArray(int index, String fieldName, Object object) throws IOException {
//		writer.writeEndArray();
//	}
//
//	@Override
//	public void doWriteString(int index, String fieldName, String value) throws IOException {
//		writer.writeString(fieldName, value);
//	}
//
//	@Override
//	public void doWriteShort(int index, String fieldName, short value) throws IOException {
//		writer.writeInt32(fieldName, value);
//	}
//
//	@Override
//	public void doWriteLong(int index, String fieldName, long value) throws IOException {
//		writer.writeInt64(fieldName, value);
//	}
//
//	@Override
//	public void doWriteInt(int index, String fieldName, int value) throws IOException {
//		writer.writeInt32(fieldName, value);
//	}
//
//	@Override
//	public void doWriteBigInt(int index, String fieldName, BigInteger value) throws IOException {
//		writer.writeString(fieldName, value.toString());
//	}
//
//	@Override
//	public void doWriteBigDecimal(int index, String fieldName, BigDecimal value) throws IOException {
//		writer.writeDecimal128(fieldName, new Decimal128(value));
//	}
//
//	@Override
//	public void doWriteFloat(int index, String fieldName, float value) throws IOException {
//		// https://stackoverflow.com/questions/7682714/does-mongodb-support-floating-point-types
//		writer.writeDouble(fieldName, value);
//	}
//
//	@Override
//	public void doWriteDouble(int index, String fieldName, double value) throws IOException {
//		writer.writeDouble(fieldName, value);
//	}
//
//	@Override
//	public void doWriteChar(int index, String fieldName, char value) throws IOException {
//		// TODO
//		writer.writeString(fieldName, "" + value);
//	}
//
//	@Override
//	public void doWriteChars(int index, String fieldName, char[] values) throws IOException {
//		writer.writeString(fieldName, new String(values));
//	}
//
//	@Override
//	public void doWriteBoolean(int index, String fieldName, boolean value) throws IOException {
//		writer.writeBoolean(fieldName, value);
//
//	}
//
//	@Override
//	public void doWriteStringNumber(int index, String fieldName, String value) throws IOException {
//		writer.writeString(fieldName, value);
//	}
//
//	@Override
//	public void doWriteBinary(int index, String fieldName, Base64Variant b64variant, byte[] values, int offset, int len)
//			throws IOException {
//		writer.writeBinaryData(fieldName, new BsonBinary(values));
//	}
//
//	@Override
//	public void doWriteNull(int index, String fieldName) throws IOException {
//		writer.writeNull(fieldName);
//	}
//
//}
