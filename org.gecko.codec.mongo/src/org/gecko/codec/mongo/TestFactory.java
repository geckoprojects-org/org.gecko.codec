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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;

/**
 * 
 * @author mark
 * @since 09.01.2024
 */
public class TestFactory extends JsonFactory {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createGenerator(java.io.DataOutput)
	 */
	@Override
	public TestGenerator createGenerator(DataOutput out) throws IOException {
		return new TestGenerator(-1, null, null);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createGenerator(java.io.OutputStream)
	 */
	@Override
	public TestGenerator createGenerator(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		return new TestGenerator(-1, null, null);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#_createUTF8Generator(java.io.OutputStream, com.fasterxml.jackson.core.io.IOContext)
	 */
	@Override
	protected JsonGenerator _createUTF8Generator(OutputStream out, IOContext ctxt) throws IOException {
		return new TestGenerator(-1, null, null);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createParser(java.io.DataInput)
	 */
	@Override
	public TestParser createParser(DataInput in) throws IOException {
		return new TestParser(null, -1);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createParser(java.net.URL)
	 */
	@Override
	public TestParser createParser(URL url) throws IOException, JsonParseException {
		System.out.println("create lucene parser for " + url);
		// TODO Auto-generated method stub
		return new TestParser(null, -1);
	}

}
