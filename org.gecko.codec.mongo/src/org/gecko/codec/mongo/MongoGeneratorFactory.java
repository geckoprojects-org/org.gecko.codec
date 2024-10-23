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

import org.bson.BsonWriter;
import org.gecko.codec.CodecGeneratorFactory;
import org.gecko.codec.CodecWriterProvider;
import org.osgi.service.component.annotations.Component;

/**
 * 
 * @author grune
 * @since Apr 10, 2024
 */
@Component(immediate=true, name = "MongoGeneratorFactory", service = CodecGeneratorFactory.class, property = {"type=mongo"})
public class MongoGeneratorFactory implements CodecGeneratorFactory<BsonWriter, MongoCodecGenerator>{

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.mongo.CodecGeneratorFactory#createGenerator(org.gecko.codec.mongo.CodecWriterProvider)
	 */
	@Override
	public MongoCodecGenerator createGenerator(CodecWriterProvider<BsonWriter> provider) {
		return new MongoCodecGenerator(provider.getWriter(), provider.getObjectCodec());
	}

}
