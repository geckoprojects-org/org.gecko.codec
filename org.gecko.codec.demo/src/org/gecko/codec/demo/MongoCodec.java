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
//package org.gecko.codec.demo;
//
//import static org.eclipse.emfcloud.jackson.databind.EMFContext.Attributes.RESOURCE;
//import static org.eclipse.emfcloud.jackson.databind.EMFContext.Attributes.RESOURCE_SET;
//
//import java.io.IOException;
//import java.util.Map;
//
//import org.bson.BsonReader;
//import org.bson.BsonWriter;
//import org.bson.codecs.Codec;
//import org.bson.codecs.DecoderContext;
//import org.bson.codecs.EncoderContext;
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.ecore.resource.Resource;
//import org.eclipse.emfcloud.jackson.databind.EMFContext;
//import org.gecko.codec.CodecDataInput;
//import org.gecko.codec.CodecDataOutput;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.cfg.ContextAttributes;
//
//
//final class MongoCodec implements Codec<EObject> {
//	
//	private final Map<?, ?> options;
//	private final ObjectMapper mapper;
//	private final MongoResource resource;
//	
//	/**
//	 * Creates a new instance.
//	 * @param mapper 
//	 * @param resource 
//	 * @param options
//	 */
//	MongoCodec(ObjectMapper mapper, MongoResource resource, Map<?, ?> options) {
//		this.mapper = mapper;
//		this.resource = resource;
//		this.options = options;
//	}
//	
//	@Override
//	public void encode(BsonWriter writer, EObject value, EncoderContext encoderContext) {
//		try {
//			mapper.writer()
//			.with(EMFContext.from(options))
//			.writeValue(new CodecDataOutput<>(writer, mapper), value);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Override
//	public Class<EObject> getEncoderClass() {
//		return EObject.class;
//	}
//	
//	@Override
//	public EObject decode(BsonReader reader, DecoderContext decoderContext) {
//		ContextAttributes attributes;
//		attributes = EMFContext
//				.from(options)
//				.withPerCallAttribute(RESOURCE_SET, resource.getResourceSet())
//				.withPerCallAttribute(RESOURCE, resource);
//		try {
//			Resource r = mapper.reader()
//					.with(attributes)
//					.forType(Resource.class)
//					.withValueToUpdate(resource)
//					.readValue(new CodecDataInput<>(reader, mapper));
//			return r.getContents().isEmpty() ? null : r.getContents().get(0);
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new IllegalStateException(e);
//		}
//	}
//}