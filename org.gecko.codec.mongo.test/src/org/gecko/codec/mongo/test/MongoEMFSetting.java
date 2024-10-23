/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec.mongo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.bson.Document;
import org.eclipse.emf.common.util.URI;
import org.gecko.codec.test.helper.CodecTestSetting;
import org.gecko.mongo.osgi.MongoClientProvider;
import org.gecko.mongo.osgi.MongoDatabaseProvider;
import org.gecko.mongo.osgi.configuration.ConfigurationProperties;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author ilenia
 * @since Oct 14, 2024
 */
public abstract class MongoEMFSetting extends CodecTestSetting{

	static protected String mongoHost = System.getProperty("mongo.host", "localhost");

	protected MongoClient client;
	protected MongoCollection<?> collection;
	
	public void doBefore(BundleContext ctx) throws Exception {
		MongoClientOptions options = MongoClientOptions.builder().build();
		client = new MongoClient(mongoHost, options);
	}

	public void doAfter() {
		if (collection != null) {
			collection.drop();
		}
		if (client != null) {
			client.close();
		}
	}
	
	protected void cleanDBCollection(MongoCollection<Document> collection ) {
		collection.drop();		
		assertEquals(0, collection.countDocuments());
	}

	
	protected void defaultSetup(ConfigurationAdmin ca) throws IOException, InvalidSyntaxException {
		// has to be a new configuration
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		// add service properties
		String clientId = "testClient";
		String clientUri = "mongodb://" + mongoHost + ":27017";
		props = new Hashtable<String, Object>();
		props.put(MongoClientProvider.PROP_CLIENT_ID, clientId);
		props.put(MongoClientProvider.PROP_URI, clientUri);
		Configuration clientConfig = ca.createFactoryConfiguration(ConfigurationProperties.CLIENT_PID, "?");
		clientConfig.update(props);
		
		// add service properties
		String dbAlias = "testDB";
		String db = "test";
		Dictionary<String, Object> dbp = new Hashtable<String, Object>();
		dbp.put(MongoDatabaseProvider.PROP_ALIAS, dbAlias);
		dbp.put(MongoDatabaseProvider.PROP_DATABASE, db);
		Configuration databaseConfig = ca.createFactoryConfiguration(ConfigurationProperties.DATABASE_PID, "?");
		databaseConfig.update(dbp);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.test.helper.CodecTestSetting#getPersonURI()
	 */
	@Override
	protected URI getPersonURI() {
		return URI.createURI("mongodb://"+ mongoHost + ":27017/test/Person/");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.test.helper.CodecTestSetting#getAddressURI()
	 */
	@Override
	protected URI getAddressURI() {
		return URI.createURI("mongodb://"+ mongoHost + ":27017/test/Address/");
	}

}
