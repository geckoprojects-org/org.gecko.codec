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
package org.gecko.codec.jackson.databind;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonReadContext;

/**
 * 
 * @author mark
 * @since 09.01.2024
 */
public abstract class CodecParserBaseImpl extends ParserBase {

	private ObjectCodec oc;

	/**
	 * Creates a new instance.
	 * @param ctxt
	 * @param features
	 */
	protected CodecParserBaseImpl(IOContext ctxt, int features) {
		super(ctxt, features);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#_closeInput()
	 */
	@Override
	protected void _closeInput() throws IOException {
		closeInput();
	}
	
	abstract public void closeInput();
	
	abstract public boolean isBeginDocument(); 
	
	abstract public void doBeginDocument();
	
	abstract public boolean isEndDocument();

	abstract public void doEndDocument(); 
	
	abstract public boolean isBeginArray();
	
	abstract public void doBeginArray(); 
	
	abstract public void doEndArray(); 
	
	abstract public JsonToken doGetNextToken();
	
	public abstract JsonToken doGetCurrentToken();	
	
	abstract public String doReadName();
	
	public abstract Object doGetCurrentValue();

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserMinimalBase#nextToken()
	 */
	@Override
	public JsonToken nextToken() throws IOException {
		if (isEndDocument()) {
			if (_parsingContext.inArray()) {
				doEndArray();
				_currToken = JsonToken.END_ARRAY;
			} else {
				doEndDocument();
				_currToken = JsonToken.END_OBJECT;
			}
			_parsingContext = _parsingContext.clearAndGetParent();
			if(!_parsingContext.inRoot()) {
				_nextToken = doGetNextToken();
			}
		} else if (_parsingContext.inObject() && _currToken != JsonToken.FIELD_NAME) {
			String name = doReadName();
			_parsingContext.setCurrentName(name);
			_currToken = JsonToken.FIELD_NAME;
		} else if (isBeginDocument()) {
			doBeginDocument();
			_parsingContext = _parsingContext.createChildObjectContext(1, 0);
			_currToken = JsonToken.START_OBJECT;
			_nextToken = doGetNextToken();
		} else if (isBeginArray()) {
			doBeginArray();
			_parsingContext = _parsingContext.createChildArrayContext(1, 0);
			_currToken = JsonToken.START_ARRAY;
			_nextToken = doGetNextToken();
		} else {
			setCurrentValue(doGetCurrentValue());
			
			// 17-Sep-2019, tatu: [core#563] Need to call this to update index 
	        _parsingContext.expectComma();
						
			_currToken = doGetCurrentToken();
			_nextToken = doGetNextToken();
		}
		return _currToken;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getIntValue()
	 */
	@Override
	public int getIntValue() throws IOException {
		return (int)getCurrentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getDoubleValue()
	 */
	@Override
	public double getDoubleValue() throws IOException {
		return (double) getCurrentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getFloatValue()
	 */
	@Override
	public float getFloatValue() throws IOException {
		return (float) getCurrentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getDecimalValue()
	 */
	@Override
	public BigDecimal getDecimalValue() throws IOException {
		return (BigDecimal) getCurrentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserMinimalBase#getText()
	 */
	@Override
	public String getText() throws IOException {
		return (String)getCurrentValue();
	}
	
	@Override
	public char[] getTextCharacters() throws IOException {
		return getText().toCharArray();
	}

	@Override
	public int getTextLength() throws IOException {
		return getText().length();
	}

	@Override
	public int getTextOffset() throws IOException {
		return 0;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#getBinaryValue()
	 */
	@Override
	public byte[] getBinaryValue() throws IOException {
		return (byte[]) getCurrentValue();
	}
	
	@Override
	public byte[] getBinaryValue(Base64Variant variant) throws IOException {
		return (byte[]) getCurrentValue();
    }

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#getCodec()
	 */
	@Override
	public ObjectCodec getCodec() {
		return oc;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#setCodec(com.fasterxml.jackson.core.ObjectCodec)
	 */
	@Override
	public void setCodec(ObjectCodec oc) {
		this.oc = oc;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getParsingContext()
	 */
	@Override
	public JsonReadContext getParsingContext() {
		return super.getParsingContext();
	}

}
