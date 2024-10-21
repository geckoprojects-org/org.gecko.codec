/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
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
package org.gecko.codec.demo.mongo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.code.demo.model.person.BusinessPerson;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.test.helper.CodecTestHelper;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;


//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;

/**
 * See documentation here: 
 * 	https://github.com/osgi/osgi-test
 * 	https://github.com/osgi/osgi-test/wiki
 * Examples: https://github.com/osgi/osgi-test/tree/main/examples
 */
@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(name = "mongoClient", location = "?", factoryPid = "MongoClientProvider", properties = {
		@Property(key = "client_id", value = "test"), @Property(key = "uri", value = "mongodb://localhost:27017") })
@WithFactoryConfiguration(name = "mongoDatabase", location = "?", factoryPid = "MongoDatabaseProvider", properties = {
		@Property(key = "alias", value = "TestDB"), @Property(key = "database", value = "test") })
@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="mongo"),
		@Property(key = "genFactory.target", value="(type=mongo)"), 
		@Property(key = "parserFactory.target", value="(type=mongo)")
})
@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "codecFactoryConfigurator.target", value="(type=mongo)"),
		@Property(key = "type", value="mongo")
})
@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="mongo")
})
public class CodecMongoSerializeTypeTest extends MongoEMFSetting {

	@InjectService(cardinality = 0, filter = "(&(" + EMFNamespaces.EMF_CONFIGURATOR_NAME + "=mongo)("
			+ EMFNamespaces.EMF_MODEL_NAME + "=collection)("+ EMFNamespaces.EMF_MODEL_NAME + "=person))")
	ServiceAware<ResourceSet> rsAware;

	@InjectService(cardinality = 0, filter = "(type=mongo)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;
	
	@InjectService(cardinality = 0, filter = "(type=mongo)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;
	
	@InjectService(cardinality = 0, filter = "(type=mongo)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;
	
	private ResourceSet resourceSet;
	private MongoCollection<Document> bpCollection;
	private MongoCollection<Document> aCollection;
	
	@BeforeEach
	public void doBefore(@InjectBundleContext BundleContext ctx) throws Exception {
		super.doBefore(ctx);
		bpCollection = client.getDatabase("test").getCollection("BusinessPerson");
		cleanDBCollection(bpCollection);
		aCollection = client.getDatabase("test").getCollection("Address");
		cleanDBCollection(aCollection);
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
	}

	@AfterEach
	public void doAfter() {
		cleanDBCollection(bpCollection);
		cleanDBCollection(aCollection);
		super.doAfter();		
	}
	
	
	@Test
	public void testSaveTypeRootObjYES() throws BundleException, InvalidSyntaxException, IOException, InterruptedException {
		ResourceSet resourceSet = rsAware.waitForService(2000l);
		
		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/BusinessPerson/"));
		
		BusinessPerson person = CodecTestHelper.getTestBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		assertEquals(1, bpCollection.countDocuments());
		FindIterable<Document> docIterable = bpCollection.find();
		Document first = docIterable.first();
		assertTrue(first.containsKey("_type"));
		assertEquals("BusinessPerson", first.get("_type"));
		
	}
	
	
	@Test
	public void testSaveTypeContainedRefYES() throws BundleException, InvalidSyntaxException, IOException, InterruptedException {
		ResourceSet resourceSet = rsAware.waitForService(2000l);
		
		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/BusinessPerson/"));
		
		BusinessPerson person = CodecTestHelper.getTestBusinessPerson();
		person.setAddress(CodecTestHelper.getTestAddress());
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		assertEquals(1, bpCollection.countDocuments());
		FindIterable<Document> docIterable = bpCollection.find();
		Document first = docIterable.first();
		assertTrue(first.containsKey("address"));
		assertTrue(first.get("address", Document.class).containsKey("_type"));
		
	}
	
//	TODO: DOUBLE CHECK THIS
	@Test
	public void testSaveTypeNonContainedRefYES() throws BundleException, InvalidSyntaxException, IOException, InterruptedException {
		ResourceSet resourceSet = rsAware.waitForService(2000l);
		
		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/BusinessPerson/"));
		
		BusinessPerson person = CodecTestHelper.getTestBusinessPerson();
		person.setNonContainedAdd(CodecTestHelper.getTestAddress());
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		assertEquals(1, bpCollection.countDocuments());
		FindIterable<Document> docIterable = bpCollection.find();
		Document first = docIterable.first();
		assertTrue(first.containsKey("nonContainedAdd"));
		assertTrue(first.get("nonContainedAdd", Document.class).containsKey("_type"));
		
	}
	
	
	@Test
	public void testSaveTypeRootObjNO() throws BundleException, InvalidSyntaxException, IOException, InterruptedException {
		ResourceSet resourceSet = rsAware.waitForService(2000l);
	
		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/BusinessPerson/"));
		
		BusinessPerson person = CodecTestHelper.getTestBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		assertEquals(1, bpCollection.countDocuments());
		FindIterable<Document> docIterable = bpCollection.find();
		Document first = docIterable.first();
		assertFalse(first.containsKey("_type"));
		
	}
	
	
	@Test
	public void testSaveTypeContainedRefNO() throws BundleException, InvalidSyntaxException, IOException, InterruptedException {
		ResourceSet resourceSet = rsAware.waitForService(2000l);
		
		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/BusinessPerson/"));
		
		BusinessPerson person = CodecTestHelper.getTestBusinessPerson();
		person.setAddress(CodecTestHelper.getTestAddress());
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		assertEquals(1, bpCollection.countDocuments());
		FindIterable<Document> docIterable = bpCollection.find();
		Document first = docIterable.first();
		assertTrue(first.containsKey("address"));
		assertFalse(first.get("address", Document.class).containsKey("_type"));
		
	}
	
	
	@Test
	public void testSaveTypeNonContainedRefNO() throws BundleException, InvalidSyntaxException, IOException, InterruptedException {
		ResourceSet resourceSet = rsAware.waitForService(2000l);
		
		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/BusinessPerson/"));
		
		BusinessPerson person = CodecTestHelper.getTestBusinessPerson();
		person.setNonContainedAdd(CodecTestHelper.getTestAddress());
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		assertEquals(1, bpCollection.countDocuments());
		FindIterable<Document> docIterable = bpCollection.find();
		Document first = docIterable.first();
		assertTrue(first.containsKey("nonContainedAdd"));
		assertFalse(first.get("nonContainedAdd", Document.class).containsKey("_type"));
		
	}

	
	
}
