/**
 * Copyright (c) 2012 - 2017 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec.mongo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.emf.mongo.InputStreamFactory;
import org.gecko.emf.mongo.OutputStreamFactory;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.gecko.emf.osgi.example.model.basic.BasicFactory;
import org.gecko.emf.osgi.example.model.basic.Contact;
import org.gecko.emf.osgi.example.model.basic.ContactContextType;
import org.gecko.emf.osgi.example.model.basic.ContactType;
import org.gecko.emf.osgi.example.model.basic.GenderType;
import org.gecko.emf.osgi.example.model.basic.Person;
import org.gecko.mongo.osgi.MongoClientProvider;
import org.gecko.mongo.osgi.MongoDatabaseProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection; 

@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(name = "mongoClient", location = "?", factoryPid = "MongoClientProvider", properties = {
		@Property(key = "client_id", value = "test"), @Property(key = "uri", value = "mongodb://localhost:27017") })
@WithFactoryConfiguration(name = "mongoDatabase", location = "?", factoryPid = "MongoDatabaseProvider", properties = {
		@Property(key = "alias", value = "TestDB"), @Property(key = "database", value = "test") })
public class MongoTest {

	@InjectService(cardinality = 0, filter = "(&(" + EMFNamespaces.EMF_CONFIGURATOR_NAME + "=mongo)("
			+ EMFNamespaces.EMF_MODEL_NAME + "=collection))")
	ServiceAware<ResourceSet> rsAware;

	@InjectService 
	MongoClientProvider clientProvider;
	@InjectService 
	MongoDatabaseProvider databaseProvider;
	@InjectService
	InputStreamFactory inputStreamFactory;
	@InjectService
	OutputStreamFactory outputStreamFactory;

	private void initResourceSet(ResourceSet resourceSet) {
//		resourceSet.getPackageRegistry().put(ModelPackage.eNS_URI, ModelPackage.eINSTANCE);
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new MongoResourceFactory());
//		resourceSet.getURIConverter().getURIHandlers().add(0, new URIHandlerImpl() {
//			
//			@Override
//			public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
//				return outputStreamFactory.createOutputStream(uri, options, getCollection(uri), getResponse(options));
//			}
//			
//			private MongoCollection<Document> getCollection(URI uri) {
//				MongoDatabase database = databaseProvider.getDatabase();
//				String collectionName = uri.segment(1);
//				return database.getCollection(collectionName);
//			}
//
//			@Override
//			public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
//				return inputStreamFactory.createInputStream(uri, options,  getCollection(uri), getResponse(options));
//			}
//			
//			@Override
//			public boolean canHandle(URI uri) {
//				return uri.scheme().equals("mongodb");
//			}
//		});
	}

	@Test
	public void testCreateContainmentSingle()
			throws BundleException, InvalidSyntaxException, IOException, InterruptedException {
		ResourceSet resourceSet = (ResourceSet) rsAware.waitForService(2000l);
		initResourceSet(resourceSet);
		System.out.println("Dropping DB");
		MongoCollection<Document> personCollection = clientProvider.getMongoClient().getDatabase("test").getCollection("Person");
		personCollection.drop();

		// create contacts
		Contact c1 = BasicFactory.eINSTANCE.createContact();
		c1.setContext(ContactContextType.PRIVATE);
		c1.setType(ContactType.SKYPE);
		c1.setValue("charles-brown");
		Contact c2 = BasicFactory.eINSTANCE.createContact();
		c2.setContext(ContactContextType.WORK);
		c2.setType(ContactType.EMAIL);
		c2.setValue("mark.hoffmann@tests.de");

		assertEquals(0, personCollection.countDocuments());
		Resource resource = resourceSet.createResource(URI.createURI("mongodb://localhost:27017/test/Person/"));

		Person person = BasicFactory.eINSTANCE.createPerson();
		person.setFirstName("Mark");
		person.setLastName("Hoffmann");
		person.setGender(GenderType.MALE);
		person.getContact().add(EcoreUtil.copy(c1));
		person.getContact().add(EcoreUtil.copy(c2));
		resource.getContents().add(person);
		resource.save(null);

		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		// long start = System.currentTimeMillis();
		Resource findResource = resourceSet
				.createResource(URI.createURI("mongodb://localhost:27017/test/Person/" + person.getId()));
		findResource.load(null);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals("Mark", p.getFirstName());
		assertEquals("Hoffmann", p.getLastName());
		assertEquals(GenderType.MALE, p.getGender());
		assertEquals(2, p.getContact().size());
		assertEquals("charles-brown", p.getContact().get(0).getValue());
		assertEquals("mark.hoffmann@tests.de", p.getContact().get(1).getValue());

		personCollection.drop();
	}
	
	@Test
	void testPlain() throws Exception {
		MongoClient client = clientProvider.getMongoClient();
        Document pingCommand = new Document("ping", 1);
        List<Document> databases = client.listDatabases().into(new ArrayList<>());
        databases.forEach(db -> System.out.println(db.toJson()));
        Document response = client.getDatabase("admin").runCommand(pingCommand);
        System.out.println("=> Print result of the '{ping: 1}' command.");
        System.out.println(response.toJson(JsonWriterSettings.builder().indent(true).build()));
//        System.out.println); response.getDouble("ok").equals(1.0);
	}
}
