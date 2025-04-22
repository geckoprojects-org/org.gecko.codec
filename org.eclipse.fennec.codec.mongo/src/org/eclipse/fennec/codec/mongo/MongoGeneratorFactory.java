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
package org.eclipse.fennec.codec.mongo;

import org.bson.BsonWriter;
import org.eclipse.fennec.codec.CodecDataOutput_old;
import org.eclipse.fennec.codec.CodecGeneratorFactory;
import org.eclipse.fennec.codec.CodecWriterProvider_old;
import org.osgi.service.component.annotations.Component;

import tools.jackson.core.io.IOContext;

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
	public MongoCodecGenerator createGenerator(CodecWriterProvider_old<BsonWriter> provider, IOContext ioCtxt) {
//		return new MongoCodecGenerator(provider.getWriter(), provider.getObjectCodec());		
		return new MongoCodecGenerator(provider.getWriter(), ioCtxt);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGeneratorFactory#createGenerator(org.gecko.codec.CodecDataOutput)
	 */
	@Override
	public MongoCodecGenerator createGenerator(CodecDataOutput_old<BsonWriter> dataOutput, IOContext ioCtxt) {
		return new MongoCodecGenerator(dataOutput.getWriter(), ioCtxt);
	}

}
