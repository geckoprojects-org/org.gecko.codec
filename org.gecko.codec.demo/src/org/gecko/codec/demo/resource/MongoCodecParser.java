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
package org.gecko.codec.demo.resource;

import java.io.IOException;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.gecko.codec.CodecReaderProvider;
import org.gecko.codec.jackson.databind.CodecParserBaseImpl;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;

public class MongoCodecParser extends CodecParserBaseImpl {

	private BsonReader reader;
	private Object currentValue;
	private ObjectCodec objectCodec;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param context
	 * @param reader
	 * @param objectCodec 
	 */
	public MongoCodecParser(IOContext context, CodecReaderProvider<BsonReader> reader) {
		super(context, -1);
		this.reader = reader.getReader();
		this.objectCodec = reader.getObjectCodec();
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param context
	 * @param reader
	 * @param objectCodec 
	 */
	public MongoCodecParser(IOContext context, BsonReader reader, ObjectCodec objectCodec) {
		super(context, -1);
		this.reader = reader;
		this.objectCodec = objectCodec;
	}

	@Override
	protected void _closeInput() throws IOException {
		System.out.println("close input");
//		reader.close();
	}

	@Override
	public JsonToken nextToken() throws IOException {
		BsonType currentType = reader.getCurrentBsonType();
		System.out.println("currentType " + currentType);

		if (currentType == BsonType.END_OF_DOCUMENT) {
			if (_parsingContext.inArray()) {
				System.out.println("end array");
				reader.readEndArray();
				_currToken = JsonToken.END_ARRAY;
			} else {
				System.out.println("end document");
				reader.readEndDocument();
				_currToken = JsonToken.END_OBJECT;
			}
			_parsingContext = _parsingContext.clearAndGetParent();
			if(!_parsingContext.inRoot()) {
				BsonType nextType = reader.readBsonType();
				_nextToken = map(nextType);
			}
		} else if (_parsingContext.inObject() && _currToken != JsonToken.FIELD_NAME) {
			String name = reader.readName();
			System.out.print(name + "=");
			_parsingContext.setCurrentName(name);
			_currToken = JsonToken.FIELD_NAME;
		} else if (currentType == BsonType.DOCUMENT) {
			System.out.println("start document");

			reader.readStartDocument();
			_parsingContext = _parsingContext.createChildObjectContext(1, 0);
			BsonType nextType = reader.readBsonType();
			_currToken = JsonToken.START_OBJECT;
			_nextToken = map(nextType);
		} else if (currentType == BsonType.ARRAY) {
			System.out.println("start array");
			reader.readStartArray();
			_parsingContext = _parsingContext.createChildArrayContext(1, 0);
			BsonType nextType = reader.readBsonType();
			_currToken = JsonToken.START_ARRAY;
			_nextToken = map(nextType);
		} else {
			currentValue = getCurrentValue(currentType);
			System.out.println(currentValue);
			BsonType nextType = reader.readBsonType();
			setCurrentValue(currentValue);
			_currToken = map(currentType);
			_nextToken = map(nextType);
		}
//		System.out.println(" currentToken: " + _currToken + " nextType: " + nextType + " nextToken: " + _nextToken
//				+ " col: " + _tokenInputCol);
		return _currToken;
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
		default:
			return null;
		}
	}

	private JsonToken map(BsonType nextBsonType) {
		switch (nextBsonType) {
		case DOCUMENT:
			return JsonToken.START_OBJECT;
		case END_OF_DOCUMENT:
			return _parsingContext.inArray() ? JsonToken.END_ARRAY : JsonToken.END_OBJECT;
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
		default:
			return JsonToken.FIELD_NAME;
		}
	}

	@Override
	public String getText() throws IOException {
		System.out.println("get text " + currentValue);
		return (String) currentValue;
	}

	@Override
	public char[] getTextCharacters() throws IOException {
		System.out.println("get text chars " + currentValue);
		return getText().toCharArray();
	}

	@Override
	public int getTextLength() throws IOException {
		System.out.println("get text length");
		return getText().length();
	}

	@Override
	public int getTextOffset() throws IOException {
		System.out.println("get text offset");
		return 0;
	}

	@Override
	public ObjectCodec getCodec() {
		System.out.println("get codec");
		return objectCodec;
	}

	@Override
	public void setCodec(ObjectCodec oc) {
		System.out.println("set codec" + oc);
		this.objectCodec = oc;
	}
}
