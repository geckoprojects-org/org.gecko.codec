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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.gecko.mongo.osgi.MongoDatabaseProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public final class MongoResource extends JsonResource {

	private MongoDatabaseProvider provider;
	private ObjectMapper mapper;
	
	public MongoResource(URI uri) {
		super(uri);
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param uri
	 * @param provider
	 * @param mapper
	 */
	public MongoResource(URI uri, MongoDatabaseProvider provider, ObjectMapper mapper) {
		super(uri, mapper);
		this.provider = provider;
//		TODO: the mapper should not be available here but should be created when we actually want to save the resource
		this.mapper = mapper;

	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		
/*		TODO: the mapper should be created here 
		TODO: 	1. Retrieve the CodecModelInfo for this resource -> this.getContents() should not be empty and should contain an EObject
				2. Modified the CodecModelInfo according to the options passed here
				3. Create the CodecModule based on the CodecModelInfo
				4. Create the ObjectMapper	
*/		
//		if(this.getContents().isEmpty()) return;
//		EObject eObj = this.getContents().get(0);
		
		
		MongoCollection<EObject> collection = getCollection(options);

		CodecRegistry eobjectRegistry = CodecRegistries
				.fromProviders(new MongoCodecProvider(mapper, this, options));
		CodecRegistry defaultRegistry = MongoClient.getDefaultCodecRegistry();
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(eobjectRegistry, defaultRegistry);

		collection.withCodecRegistry(codecRegistry).insertMany(getContents());
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		MongoCollection<EObject> collection = getCollection(options);

		CodecRegistry eobjectRegistry = CodecRegistries
				.fromProviders(new MongoCodecProvider(mapper, this, options));
		CodecRegistry defaultRegistry = MongoClient.getDefaultCodecRegistry();
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(eobjectRegistry, defaultRegistry);

		FindIterable<EObject> resultIterable = collection.withCodecRegistry(codecRegistry)
				.withDocumentClass(EObject.class).find();
		try (MongoCursor<EObject> mongoCursor = resultIterable.iterator()) {
			while (mongoCursor.hasNext()) {
				mongoCursor.next();
			}
		}
	}

	private MongoCollection<EObject> getCollection(Map<?, ?> options) {
		MongoDatabase database = provider.getDatabase();
		String collectionName = uri.segment(1);
		return database.getCollection(collectionName, EObject.class);
	}
}