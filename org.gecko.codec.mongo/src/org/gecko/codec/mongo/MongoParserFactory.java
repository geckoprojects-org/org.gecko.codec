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
package org.gecko.codec.mongo;

import org.bson.BsonReader;
import org.gecko.codec.CodecParserFactory;
import org.gecko.codec.CodecReaderProvider;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.core.io.IOContext;

/**
 * 
 * @author grune
 * @since Apr 10, 2024
 */
@Component(immediate=true, name = "MongoParserFactory", service = CodecParserFactory.class, property = {"type=mongo"})
public class MongoParserFactory implements CodecParserFactory<BsonReader, MongoCodecParser>{

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.mongo.CodecParserFactory#createParser(com.fasterxml.jackson.core.io.IOContext, org.gecko.codec.mongo.CodecReaderProvider)
	 */
	@Override
	public MongoCodecParser createParser(IOContext context, CodecReaderProvider<BsonReader> provider) {
		return new MongoCodecParser(context, provider);
	}

}
