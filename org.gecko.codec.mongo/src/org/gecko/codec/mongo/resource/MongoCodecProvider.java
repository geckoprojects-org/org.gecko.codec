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
import org.eclipse.emf.ecore.EObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author grune
 * @since Feb 2, 2024
 */
public final class MongoCodecProvider implements CodecProvider {
	/** options */
	private final Map<?, ?> options;
	private ObjectMapper mapper;
	private MongoResource resource;

	/**
	 * Creates a new instance.
	 * 
	 * @param mapper
	 * @param resource
	 * @param options
	 */
	public MongoCodecProvider(ObjectMapper mapper, MongoResource resource, Map<?, ?> options) {
		this.mapper = mapper;
		this.resource = resource;
		this.options = options;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
		if (EObject.class.isAssignableFrom(clazz)) {
			return (Codec<T>) new MongoCodec(mapper, resource, options);
		} else {
			return null;
		}
	}
}