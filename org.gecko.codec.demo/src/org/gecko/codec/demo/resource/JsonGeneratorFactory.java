///**
// * Copyright (c) 2012 - 2024 Data In Motion and others.
// * All rights reserved. 
// * 
// * This program and the accompanying materials are made available under the terms of the 
// * Eclipse Public License v2.0 which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v20.html
// * 
// * Contributors:
// *     Data In Motion - initial API and implementation
// */
//package org.gecko.codec.demo.resource;
//
//import org.gecko.codec.CodecGeneratorFactory;
//import org.gecko.codec.CodecWriterProvider;
//import org.osgi.service.component.annotations.Component;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
//
///**
// * 
// * @author grune
// * @since Apr 10, 2024
// */
//@Component(immediate=true, name = "JsonGeneratorFactory", 
//	service = CodecGeneratorFactory.class, property = {"type=json"})
//public class JsonGeneratorFactory implements CodecGeneratorFactory<JsonGenerator, JsonCodecGenerator>{
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.CodecGeneratorFactory#createGenerator(org.gecko.codec.CodecWriterProvider)
//	 */
//	@Override
//	public JsonCodecGenerator createGenerator(CodecWriterProvider<JsonGenerator> provider) {
//		return new JsonCodecGenerator(provider.getWriter(), provider.getObjectCodec());
//	}
//}
