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
package org.eclipse.fennec.codec.ecowitt.parser;

import java.io.InputStream;

import org.eclipse.fennec.codec.CodecParserFactory;
import org.eclipse.fennec.codec.CodecReaderProvider;

import tools.jackson.core.io.IOContext;

/**
 * Parser factory for the EcoWitt data
 * @author Mark Hoffmann
 * @since Apr 10, 2024
 */
public class EcoWittParserFactory implements CodecParserFactory<InputStream, EcoWittParser>{

	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.CodecParserFactory#createParser(tools.jackson.core.io.IOContext, org.eclipse.fennec.codec.CodecReaderProvider)
	 */
	@Override
	public EcoWittParser createParser(IOContext context, CodecReaderProvider<InputStream> provider) {
		return new EcoWittParser(context, provider);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.CodecParserFactory#createParser(tools.jackson.core.io.IOContext, java.lang.Object)
	 */
	@Override
	public EcoWittParser createParser(IOContext context, InputStream input) {
		return new EcoWittParser(context, input);
	}

}
