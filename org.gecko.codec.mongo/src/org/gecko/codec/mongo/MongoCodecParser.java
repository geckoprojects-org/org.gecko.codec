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
package org.gecko.codec.mongo;

import java.io.IOException;

import org.bson.BsonReader;
import org.gecko.codec.jackson.databind.CodecParserBaseImpl;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;

/**
 * 
 * @author mark
 * @since 09.01.2024
 */
public class MongoCodecParser extends CodecParserBaseImpl {

	/**
	 * Creates a new instance.
	 * @param reader
	 */
	public MongoCodecParser(BsonReader reader) {
		super(null, -1);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserBase#_closeInput()
	 */
	@Override
	protected void _closeInput() throws IOException {
		System.out.println("close input");
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserMinimalBase#nextToken()
	 */
	@Override
	public JsonToken nextToken() throws IOException {
		System.out.println("next token");
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserMinimalBase#getText()
	 */
	@Override
	public String getText() throws IOException {
		System.out.println("get text");
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserMinimalBase#getTextCharacters()
	 */
	@Override
	public char[] getTextCharacters() throws IOException {
		System.out.println("get text chars");
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserMinimalBase#getTextLength()
	 */
	@Override
	public int getTextLength() throws IOException {
		System.out.println("get text length");
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.base.ParserMinimalBase#getTextOffset()
	 */
	@Override
	public int getTextOffset() throws IOException {
		System.out.println("get text offset");
		return 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#getCodec()
	 */
	@Override
	public ObjectCodec getCodec() {
		System.out.println("get codec");
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonParser#setCodec(com.fasterxml.jackson.core.ObjectCodec)
	 */
	@Override
	public void setCodec(ObjectCodec oc) {
		// TODO Auto-generated method stub
		System.out.println("set codec");
	}

}
