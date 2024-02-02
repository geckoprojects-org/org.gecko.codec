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
package org.gecko.codec.mongo.resource;

import static org.eclipse.emfcloud.jackson.databind.EMFContext.Attributes.RESOURCE;
import static org.eclipse.emfcloud.jackson.databind.EMFContext.Attributes.RESOURCE_SET;

import java.io.IOException;
import java.util.Map;

import org.bson.BsonReader;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.codec.mongo.MongoCodecGenerator;
import org.gecko.codec.mongo.MongoCodecParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;

final class MongoCodec implements Codec<EObject> {
	/** options */
	private final Map<?, ?> options;
	private ObjectMapper mapper;
	private ResourceSet resourceSet;
	
	/**
	 * Creates a new instance.
	 * @param mapper 
	 * @param resourceSet 
	 * @param options
	 */
	MongoCodec(ObjectMapper mapper, ResourceSet resourceSet, Map<?, ?> options) {
		this.mapper = mapper;
		this.resourceSet = resourceSet;
		this.options = options;
	}
	
	public void encode(org.bson.BsonWriter writer, EObject value, org.bson.codecs.EncoderContext encoderContext) {
		try {
			mapper.writer()
			.with(EMFContext.from(options))
			.writeValue(new MongoCodecGenerator(writer), value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Class<EObject> getEncoderClass() {
		return EObject.class;
	}
	
	@Override
	public EObject decode(BsonReader reader, DecoderContext decoderContext) {
		ContextAttributes attributes;
		attributes = EMFContext
				.from(options)
				.withPerCallAttribute(RESOURCE_SET, resourceSet)
				.withPerCallAttribute(RESOURCE, this);
		
		try {
			return mapper.reader()
					.with(attributes)
					.forType(Resource.class)
					.withValueToUpdate(this)
					.readValue(new MongoCodecParser(reader));
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
}