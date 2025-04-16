/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import tools.jackson.core.ErrorReportConfiguration;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.io.ContentReference;
import tools.jackson.core.io.IOContext;
import tools.jackson.core.json.JsonFactory;

/**
 * Codec specific JsonFactory to create CodecGenerators and CodecParsers.
 * 
 * @author grune
 * @since Apr 10, 2024
 */
public class CodecFactory<R, W, P extends JsonParser, G extends JsonGenerator> extends JsonFactory {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	private final CodecGeneratorFactory<W, G> genFactory;
	private final CodecParserFactory<R, P> parserFactory;

	/**
	 * Creates a new instance.
	 */
	public CodecFactory(CodecGeneratorFactory<W, G> genFactory, CodecParserFactory<R, P> parserFactory) {
		this.genFactory = genFactory;
		this.parserFactory = parserFactory;
	}

//	/* 
//	 * (non-Javadoc)
//	 * @see com.fasterxml.jackson.core.JsonFactory#createGenerator(java.io.DataOutput)
//	 */
//	@Override
//	public JsonGenerator _createGenerator(DataOutput out)  {
//		return internalCreateGenerator(out);
//	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.TokenStreamFactory#createGenerator(java.io.OutputStream)
	 */
	@Override
	public JsonGenerator createGenerator(OutputStream out)  {
		return internalCreateGenerator(out, null);
	}

//	/* 
//	 * (non-Javadoc)
//	 * @see com.fasterxml.jackson.core.JsonFactory#_createUTF8Generator(java.io.OutputStream, com.fasterxml.jackson.core.io.IOContext)
//	 */
//	@Override
//	protected JsonGenerator _createUTF8Generator(OutputStream out, IOContext ctxt)  {
//		return internalCreateGenerator(out);
//	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.json.JsonFactory#_createUTF8Generator(tools.jackson.core.ObjectWriteContext, tools.jackson.core.io.IOContext, java.io.OutputStream)
	 */
	@Override
    public JsonGenerator _createUTF8Generator(ObjectWriteContext writeCtxt,
            IOContext ioCtxt, OutputStream out) {
	
		return internalCreateGenerator(out, ioCtxt);
	}

//	/* 
//	 * (non-Javadoc)
//	 * @see com.fasterxml.jackson.core.JsonFactory#createParser(java.io.DataInput)
//	 */
//	@Override
//	public JsonParser createParser(DataInput in)  {
//		return internalCreateParser(in);
//	}
	
	@Override
	public JsonParser _createParser(ObjectReadContext readCtxt, IOContext ioCtxt,
            DataInput input) {
		return internalCreateParser(input);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createParser(java.net.URL)
	 */
	@Override
	public JsonParser createParser(URL url)  {
		return internalCreateParser(url);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonFactory#createParser(java.io.InputStream)
	 */
	@Override
	public JsonParser createParser(InputStream in) {
		return internalCreateParser(in);
	}

	@SuppressWarnings({ "unchecked" })
	private P internalCreateParser(Object in) {
		if (in instanceof CodecReaderProvider) {
			CodecReaderProvider<R> readerProvider = (CodecReaderProvider<R>) in;
			IOContext context = _createContext(
					ContentReference.construct(false, readerProvider.getReader(), ErrorReportConfiguration.defaults()),
					true);
			return parserFactory.createParser(context, readerProvider);
		} else {
			throw new UnsupportedOperationException("The createParser call is only supported with a CodecReaderProvider as parameter.");
		}
	}

	@SuppressWarnings("unchecked")
	private G internalCreateGenerator(Object in, IOContext ioCtxt) {
		if(in instanceof CodecDataOutputAsStream doas) {
			return (G) genFactory.createGenerator(doas.getCodecDataOutput(), ioCtxt);
		}
		
		if (in instanceof CodecWriterProvider_old) {
			return genFactory.createGenerator((CodecWriterProvider_old<W>) in, ioCtxt);
		} else {
			throw new UnsupportedOperationException("The createGenerator call is only supported with a CodecWriterProvider as parameter.");
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.core.TokenStreamFactory#_createDataOutputWrapper(java.io.DataOutput)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected OutputStream _createDataOutputWrapper(DataOutput out) {
		return new CodecDataOutputAsStream((CodecDataOutput_old<W>) out);
	}

}
