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
package org.gecko.codec.jackson.databind.deser;

import java.io.IOException;
import java.math.BigDecimal;

import tools.jackson.core.Base64Variant;
import tools.jackson.core.JsonToken;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.TreeCodec;
import tools.jackson.core.base.ParserBase;
import tools.jackson.core.io.IOContext;

/**
 * This is the default basic impl of the Parser. 
 * @author mark
 * @since 09.01.2024
 */
public abstract class CodecParserBaseImpl extends ParserBase {

	private TreeCodec oc;

	/**
	 * Creates a new instance.
	 * @param ctxt
	 * @param features
	 */
	protected CodecParserBaseImpl(ObjectReadContext readCtxt, IOContext ctxt, int features) {
		super(readCtxt, ctxt, features);
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
	 * @see tools.jackson.core.JsonParser#nextToken()
	 */
	@Override
	public JsonToken nextToken()  {
		if (isEndDocument()) {
			if (streamReadContext().inArray()) {
				doEndArray();
				_currToken = JsonToken.END_ARRAY;
			} else {
				doEndDocument();
				_currToken = JsonToken.END_OBJECT;
			}
//			_parsingContext = _parsingContext.clearAndGetParent();
			if(!streamReadContext().inRoot()) {
				_nextToken = doGetNextToken();
			}
		} else if (streamReadContext().inObject() && _currToken != JsonToken.PROPERTY_NAME) {
			String name = doReadName();
//			_parsingContext.setCurrentName(name);
			_currToken = JsonToken.PROPERTY_NAME;
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
			assignCurrentValue(doGetCurrentValue());
			
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
	public int getIntValue() {
		return (int) currentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getDoubleValue()
	 */
	@Override
	public double getDoubleValue() {
		return (double) currentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getFloatValue()
	 */
	@Override
	public float getFloatValue() {
		return (float) currentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#getDecimalValue()
	 */
	@Override
	public BigDecimal getDecimalValue()  {
		return (BigDecimal) currentValue();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserMinimalBase#getText()
	 */
	@Override
	public String getText() {
		return (String) currentValue();
	}
	
	@Override
	public char[] getTextCharacters() {
		return getText().toCharArray();
	}

	@Override
	public int getTextLength() {
		return getText().length();
	}

	@Override
	public int getTextOffset() {
		return 0;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#getBinaryValue()
	 */
	@Override
	public byte[] getBinaryValue() {
		return (byte[]) currentValue();
	}
	
	@Override
	public byte[] getBinaryValue(Base64Variant variant){
		return (byte[]) currentValue();
    }


	public TreeCodec getCodec(){
		return oc;
	}


	public void setCodec(TreeCodec oc) {
		this.oc = oc;
	}
	
//	/* 
//	 * (non-Javadoc)
//	 * @see com.fasterxml.jackson.core.base.ParserBase#getParsingContext()
//	 */
//	@Override
//	public JsonReadContext getParsingContext() {
//		return super.getParsingContext();
//	}

}
