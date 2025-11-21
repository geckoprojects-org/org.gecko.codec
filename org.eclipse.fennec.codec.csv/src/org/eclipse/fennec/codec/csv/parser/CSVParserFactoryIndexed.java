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

import java.io.InputStream;

import org.eclipse.fennec.codec.CodecParserFactory;
import org.eclipse.fennec.codec.CodecReaderProvider;

import tools.jackson.core.io.IOContext;

/**
 * Indexed CSV Parser Factory using FastCSV's IndexedCsvReader for lazy loading.
 * This parser factory enables lazy loading of large CSV files with random access.
 *
 * @author Claude Code
 * @since Nov 21, 2025
 */
public class CSVParserFactoryIndexed implements CodecParserFactory<InputStream, CodecCSVParserIndexed>{

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.CodecParserFactory#createParser(tools.jackson.core.io.IOContext, org.eclipse.fennec.codec.CodecReaderProvider)
	 */
	@Override
	public CodecCSVParserIndexed createParser(IOContext context, CodecReaderProvider<InputStream> provider) {
		return new CodecCSVParserIndexed(context, provider);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.CodecParserFactory#createParser(tools.jackson.core.io.IOContext, java.lang.Object)
	 */
	@Override
	public CodecCSVParserIndexed createParser(IOContext context, InputStream input) {
		return new CodecCSVParserIndexed(context, input);
	}

}
