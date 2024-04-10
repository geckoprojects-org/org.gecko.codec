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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.gecko.codec.jackson.databind.ser.CodecGeneratorBaseImpl;

import com.fasterxml.jackson.core.ErrorReportConfiguration;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.io.ContentReference;
import com.fasterxml.jackson.core.io.IOContext;

/**
 * 
 * @author mark
 * @since 09.01.2024
 */
public class CodecFactory extends JsonFactory {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createGenerator(java.io.DataOutput)
	 */
	@Override
	public CodecGeneratorBaseImpl createGenerator(DataOutput out) throws IOException {
		return new CodecGeneratorImpl(-1, null, null);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createGenerator(java.io.OutputStream)
	 */
	@Override
	public CodecGeneratorBaseImpl createGenerator(OutputStream out) throws IOException {
		return new CodecGeneratorImpl(-1, null, null);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#_createUTF8Generator(java.io.OutputStream, com.fasterxml.jackson.core.io.IOContext)
	 */
	@Override
	protected JsonGenerator _createUTF8Generator(OutputStream out, IOContext ctxt) throws IOException {
		return new CodecGeneratorImpl(-1, null, null);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createParser(java.io.DataInput)
	 */
	@Override
	public CodecParserBaseImpl createParser(DataInput in) throws IOException {
		return new CodecParserBaseImpl(null, -1);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createParser(java.net.URL)
	 */
	@Override
	public CodecParserBaseImpl createParser(URL url) throws IOException, JsonParseException {
		IOContext context = _createContext(
				ContentReference.construct(false, "Hallo Welt", ErrorReportConfiguration.defaults()), true);

		return new CodecParserBaseImpl(context, -1);
	}
	
	

}
