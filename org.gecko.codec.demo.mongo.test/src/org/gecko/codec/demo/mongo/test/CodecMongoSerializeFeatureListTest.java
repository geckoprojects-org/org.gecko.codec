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
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.ObjectMapperOptions;
import org.gecko.codec.test.helper.CodecTestHelper;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.fasterxml.jackson.databind.SerializationFeature;
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
public class CodecMongoSerializeFeatureListTest extends MongoEMFSetting {

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
	private MongoCollection<Document> addCollection;
	
	@BeforeEach
	public void doBefore(@InjectBundleContext BundleContext ctx) throws Exception {
		super.doBefore(ctx);
		bpCollection = client.getDatabase("test").getCollection("Person");
		cleanDBCollection(bpCollection);
		addCollection = client.getDatabase("test").getCollection("Address");
		cleanDBCollection(addCollection);
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
	}

	@AfterEach
	public void doAfter() {
		cleanDBCollection(bpCollection);
		cleanDBCollection(addCollection);
		super.doAfter();		
	}
	
	
	@Test
	public void testSerializationIgnoreFeatureList() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/Person/"));

		Person person = CodecTestHelper.getTestPerson();
		person.setAge(42);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		personOptions.put(CodecAnnotations.CODEC_IGNORE_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_Age(), 
				PersonPackage.eINSTANCE.getPerson_BirthDate()));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		assertEquals(1, bpCollection.countDocuments());
		FindIterable<Document> docIterable = bpCollection.find();
		Document first = docIterable.first();
		assertFalse(first.containsKey("birthDate"));
		assertFalse(first.containsKey("age"));
	}
	
	@Test
	public void testSerializationIgnoreNOTFeatureList() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/Address/"));

		Address address = CodecTestHelper.getTestAddress();
		resource.getContents().add(address);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		//		zip is marked with the codec annotation codec.transient in the model but with this we should be able to serialize it anyway
		addOptions.put(CodecAnnotations.CODEC_IGNORE_NOT_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getAddress_Zip()));

		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		assertEquals(1, addCollection.countDocuments());
		FindIterable<Document> docIterable = addCollection.find();
		Document first = docIterable.first();
		assertTrue(first.containsKey("zip"));
	}
		
	
	@Test
	public void testSerializationIgnoreNOTFeatureList2() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI("mongodb://"+ mongoHost + ":27017/test/Person/"));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		//		transientAtt is marked as transient in the model property view but with this we should be able to serialize it anyway
		personOptions.put(CodecAnnotations.CODEC_IGNORE_NOT_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_TransientAtt()));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
	
		assertEquals(1, bpCollection.countDocuments());
		FindIterable<Document> docIterable = bpCollection.find();
		Document first = docIterable.first();
		assertTrue(first.containsKey("transientAtt"));
		assertEquals(7, first.get("transientAtt"));
	}
}
