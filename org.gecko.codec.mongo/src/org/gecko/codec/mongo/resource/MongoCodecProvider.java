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

import java.util.Map;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author grune
 * @since Feb 2, 2024
 */
final class MongoCodecProvider implements CodecProvider {
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
	MongoCodecProvider(ObjectMapper mapper, ResourceSet resourceSet, Map<?, ?> options) {
		this.mapper = mapper;
		this.resourceSet = resourceSet;
		this.options = options;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
		return (Codec<T>) new MongoCodec(mapper, resourceSet, options);
	}
}