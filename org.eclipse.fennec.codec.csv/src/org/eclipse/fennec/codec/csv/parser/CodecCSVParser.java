/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
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
package org.eclipse.fennec.codec.csv.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.fennec.codec.CodecReaderProvider;
import org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonToken;
import tools.jackson.core.Version;
import tools.jackson.core.exc.InputCoercionException;
import tools.jackson.core.io.IOContext;
import tools.jackson.core.util.VersionUtil;

/**
 * 
 * @author ilenia
 * @since Sep 22, 2025
 */
public class CodecCSVParser extends CodecParserBaseImpl {
	
	private enum State {
		BEGIN,
		NAME,
		VALUE,
		END;
	}
	
	private InputStream input;
	private CSVParser csvParser;
	private Iterator<CSVRecord> recordIterator;
	private Map<String, Object> currentRowMap;
	private String currentName = null;
	private State state = State.BEGIN;
	private String[] headers;

	/**
	 * Creates a new instance.
	 * @param readCtxt
	 * @param ctxt
	 * @param streamReadFeatures
	 * @param formatReadFeatures
	 */
	public CodecCSVParser(IOContext context, CodecReaderProvider<InputStream> reader) {
		super(null, context, -1, -1, reader.getObjectCodec());
		this.input = reader.getReader();
		initializeCSVParser();
	}
	
	public CodecCSVParser(IOContext context, InputStream is) {
		super(null, context, -1, -1);
		this.input = is;
		initializeCSVParser();
	}
	
	private void initializeCSVParser() {
		try {
			this.csvParser = CSVStringParser.createParser(input);
			this.recordIterator = csvParser.iterator();
			this.headers = csvParser.getHeaderNames().toArray(new String[0]);
			loadNextRow();
		} catch (Exception e) {
			this.currentRowMap = Collections.emptyMap();
			this.state = State.END;
		}
	}
	
	private void loadNextRow() {
		if (recordIterator.hasNext()) {
			CSVRecord record = recordIterator.next();
			this.currentRowMap = CSVStringParser.parseRow(headers, record);
		} else {
			this.currentRowMap = Collections.emptyMap();
			this.state = State.END;
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#closeInput()
	 */
	@Override
	public void closeInput() {
		if (currentRowMap != null) {
			currentRowMap.clear();
		}
		try {
			if (csvParser != null) {
				csvParser.close();
			}
		} catch (IOException e) {
			// Ignore close errors
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#isBeginDocument()
	 */
	@Override
	public boolean isBeginDocument() {
		return State.BEGIN.equals(state);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doBeginDocument()
	 */
	@Override
	public void doBeginDocument() {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#isEndDocument()
	 */
	@Override
	public boolean isEndDocument() {
		return State.END.equals(state);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doEndDocument()
	 */
	@Override
	public void doEndDocument() {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#isBeginArray()
	 */
	@Override
	public boolean isBeginArray() {
		return false;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doBeginArray()
	 */
	@Override
	public void doBeginArray() {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doEndArray()
	 */
	@Override
	public void doEndArray() {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doGetNextToken()
	 */
	@Override
	public JsonToken doGetNextToken() {
		switch (state) {
		case BEGIN:
			state = State.NAME;
			break;
		case NAME:
			state = State.VALUE;
			break;
		case VALUE:			
			state = currentRowMap.isEmpty() ? State.END : State.NAME;
			break;
		case END:
			state = State.END;
			break;
		}
		return doGetCurrentToken();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doGetCurrentToken()
	 */
	@Override
	public JsonToken doGetCurrentToken() {
		return switch (state) {
		case BEGIN -> {
			yield JsonToken.START_OBJECT;
		}
		case NAME -> {
			yield JsonToken.PROPERTY_NAME;
		}
		case VALUE -> {
			yield JsonToken.VALUE_STRING;
		}
		case END -> {
			yield JsonToken.END_OBJECT;
		}
	};
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doReadName()
	 */
	@Override
	public String doReadName() {
		if (!currentRowMap.isEmpty()) {
			currentName = currentRowMap.keySet().iterator().next();
			state = State.VALUE;
			return currentName;
		}
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doGetCurrentValue()
	 */
	@Override
	public Object doGetCurrentValue() {
		return getCurrentValue(currentName);
	}
	
	private Object getCurrentValue(String name) {
		return currentRowMap.remove(name);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#getStringValueObject()
	 */
	@Override
	public Object getStringValueObject() {
		return doGetCurrentValue();
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.base.ParserBase#_parseNumericValue(int)
	 */
	@Override
	protected void _parseNumericValue(int expType) throws JacksonException, InputCoercionException {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.base.ParserBase#_parseIntValue()
	 */
	@Override
	protected int _parseIntValue() throws JacksonException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonParser#version()
	 */
	@Override
	public Version version() {
		return VersionUtil.parseVersion(
				"1.0.0-SNAPSHOT", "org.eclipse.fennec.codec", "codec-csv");
	}

}
