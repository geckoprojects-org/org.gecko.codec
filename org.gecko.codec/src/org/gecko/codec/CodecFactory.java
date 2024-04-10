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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.fasterxml.jackson.core.ErrorReportConfiguration;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.ContentReference;
import com.fasterxml.jackson.core.io.IOContext;

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

	@Override
	public JsonGenerator createGenerator(DataOutput out) throws IOException {
		return internalCreateGenerator(out);
	}

	@Override
	public JsonGenerator createGenerator(OutputStream out) throws IOException {
		return internalCreateGenerator(out);
	}

	@Override
	protected JsonGenerator _createUTF8Generator(OutputStream out, IOContext ctxt) throws IOException {
		return internalCreateGenerator(out);
	}

	@Override
	public JsonParser createParser(DataInput in) throws IOException {
		return internalCreateParser(in);
	}

	@Override
	public JsonParser createParser(URL url) throws IOException, JsonParseException {
		return internalCreateParser(url);
	}

	@Override
	public JsonParser createParser(InputStream in) throws IOException, JsonParseException {
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
	private G internalCreateGenerator(Object in) {
		if (in instanceof CodecWriterProvider) {
			return genFactory.createGenerator((CodecWriterProvider<W>) in);
		} else {
			throw new UnsupportedOperationException("The createGenerator call is only supported with a CodecWriterProvider as parameter.");
		}
	}

}
