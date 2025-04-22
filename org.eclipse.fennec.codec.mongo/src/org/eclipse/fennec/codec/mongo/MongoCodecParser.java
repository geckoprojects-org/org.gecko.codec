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
package org.eclipse.fennec.codec.mongo;

import java.math.BigDecimal;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.eclipse.fennec.codec.CodecReaderProvider;
import org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl;

import tools.jackson.core.Base64Variant;
import tools.jackson.core.JsonToken;
import tools.jackson.core.TreeCodec;
import tools.jackson.core.io.IOContext;

public class MongoCodecParser extends CodecParserBaseImpl {

	private BsonReader reader;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param context
	 * @param reader
	 * @param objectCodec 
	 */
	public MongoCodecParser(IOContext context, CodecReaderProvider<BsonReader> reader) {
		super(null, context, -1, -1, reader.getObjectCodec());
		this.reader = reader.getReader();
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param context
	 * @param reader
	 * @param objectCodec 
	 */
	public MongoCodecParser(IOContext context, BsonReader reader, TreeCodec objectCodec) {
		super(null, context, -1, -1, objectCodec);
		this.reader = reader;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#closeInput()
	 */
	@Override
	public void closeInput() {
		// Do not close the mongo reader here!
		// reader.close();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#isEnddocument()
	 */
	@Override
	public boolean isEndDocument() {
		return reader.getCurrentBsonType() == BsonType.END_OF_DOCUMENT;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#isBeginDocument()
	 */
	@Override
	public boolean isBeginDocument() {
		return reader.getCurrentBsonType() == BsonType.DOCUMENT;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#isBeginArray()
	 */
	@Override
	public boolean isBeginArray() {
		return reader.getCurrentBsonType() == BsonType.ARRAY;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#doBeginArray()
	 */
	@Override
	public void doBeginArray() {
		reader.readStartArray();
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#doEndArray()
	 */
	@Override
	public void doEndArray() {
		reader.readEndArray();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#doEndDocument()
	 */
	@Override
	public void doEndDocument() {
		reader.readEndDocument();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#doReadName()
	 */
	@Override
	public String doReadName() {
		return reader.readName();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#doBeginDocument()
	 */
	@Override
	public void doBeginDocument() {
		reader.readStartDocument();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#doGetCurrentToken()
	 */
	@Override
	public JsonToken doGetCurrentToken() {
		BsonType currentType = reader.getCurrentBsonType();
		return map(currentType);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#doNextToken()
	 */
	@Override
	public JsonToken doGetNextToken() {
		BsonType nextType = reader.readBsonType();
		return map(nextType);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.CodecParserBaseImpl#doGetCurrentValue()
	 */
	@Override
	public Object doGetCurrentValue() {
		return getCurrentValue(reader.getCurrentBsonType());
	}
	
	

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#canReadObjectId()
	 */
	@Override
	public boolean canReadObjectId() {
		return true;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#getObjectId()
	 */
	@Override
	public Object getObjectId()  {
		return _streamReadContext.currentValue();
	}
	
	private Object getCurrentValue(BsonType bsonType) {
		switch (bsonType) { 
		case STRING:
			return reader.readString();
		case BOOLEAN:
			return reader.readBoolean();
		case INT32:
			return reader.readInt32();
		case INT64:
			return reader.readInt64();
		case DOUBLE:
			return reader.readDouble();
		case OBJECT_ID:
			return reader.readObjectId().toHexString();
		case BINARY:
			return reader.readBinaryData();
		case DECIMAL128:
			return reader.readDecimal128();
		case NULL:
			reader.readNull();
			//go through
		default:
			return null;
		}
	}

	private JsonToken map(BsonType nextBsonType) {
		switch (nextBsonType) {
		case DOCUMENT:
			return JsonToken.START_OBJECT;
		case END_OF_DOCUMENT:
			return _streamReadContext.inArray() ? JsonToken.END_ARRAY : JsonToken.END_OBJECT;
		case ARRAY:
			return JsonToken.START_ARRAY;
		case INT32:
		case INT64:
			return JsonToken.VALUE_NUMBER_INT;
		case DOUBLE:
			return JsonToken.VALUE_NUMBER_FLOAT;
		case STRING:
		case OBJECT_ID:
			return JsonToken.VALUE_STRING;
		case BOOLEAN:
			return JsonToken.VALUE_TRUE; //not sure here because in principle also JsonToken.VALUE_FALSE is a valid one...?
		case BINARY:
			return JsonToken.VALUE_STRING; // what to put here???
		case NULL:
			return JsonToken.VALUE_NULL;
		case DECIMAL128:
			return JsonToken.VALUE_NUMBER_FLOAT;
		default:
			return JsonToken.PROPERTY_NAME;
		}
	}

	
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getDecimalValue()
	 */
	@Override
	public BigDecimal getDecimalValue() {
		return ((org.bson.types.Decimal128) _streamReadContext.currentValue()).bigDecimalValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#getBinaryValue()
	 */
	@Override
	public byte[] getBinaryValue()  {
		return ((org.bson.BsonBinary) _streamReadContext.currentValue()).getData();
	}
	
	@Override
	public byte[] getBinaryValue(Base64Variant variant)  {
		
		return((org.bson.BsonBinary) _streamReadContext.currentValue()).getData();
    }
}
