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
package org.gecko.codec.mongo.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.model.person.Address;
import org.gecko.codec.demo.model.person.Person;
import org.gecko.codec.demo.model.person.PersonPackage;
import org.gecko.codec.info.CodecAnnotations;
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
public class CodecMongoDeserializeWithCustomReaderTest extends MongoEMFSetting {

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
	public void testDeserializationIdCustomReader() throws BundleException, InvalidSyntaxException, IOException, InterruptedException {

		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Address/");
		Resource resource = resourceSet.createResource(uri);

		Address address = CodecTestHelper.getTestAddress();
		resource.getContents().add(address);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addOptions = new HashMap<>();
		addOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, CodecTestHelper.TEST_VALUE_WRITER);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);

		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		Resource findResource = resourceSet.createResource(uri);
		options = new HashMap<>();
		classOptions = new HashMap<>();
		addOptions = new HashMap<>();

		addOptions.put(CodecAnnotations.CODEC_ID_VALUE_READER, CodecTestHelper.TEST_VALUE_READER);
		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getAddress());
		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Address add = (Address) findResource.getContents().get(0);
		assertNotNull(add);
		assertEquals(address.getId(), add.getId());
	}
	
	@Test
	public void testDeserializationTypeCustomReader() throws InterruptedException, IOException {
		
		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Address/");
		Resource resource = resourceSet.createResource(uri);
		Address address = CodecTestHelper.getTestAddress();
		resource.getContents().add(address);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addOptions = new HashMap<>();
		addOptions.put(CodecAnnotations.CODEC_TYPE_VALUE_WRITER, CodecTestHelper.TEST_TYPE_WRITER);

		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		Resource findResource = resourceSet.createResource(uri);
		options = new HashMap<>();
		classOptions = new HashMap<>();
		addOptions = new HashMap<>();

		addOptions.put(CodecAnnotations.CODEC_TYPE_VALUE_READER, CodecTestHelper.TEST_TYPE_READER);
		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getAddress());
		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Address add = (Address) findResource.getContents().get(0);
		assertNotNull(add);
	}
	
	@Test
	public void testDeserializationIdCombinedCustomReader() throws InterruptedException, IOException {

		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Person/");
		Resource resource = resourceSet.createResource(uri);
		
		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, CodecTestHelper.TEST_VALUE_WRITER);
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		Resource findResource = resourceSet.createResource(uri);
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_READER, CodecTestHelper.TEST_VALUE_READER);

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals("John", p.getName());
		assertEquals("Doe", p.getLastName());
		
	}
	
	@Test
	public void testDeserializationCustomReaderSingleAttribute() throws InterruptedException, IOException {

		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Person/");
		Resource resource = resourceSet.createResource(uri);

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personOptions.put(CodecAnnotations.CODEC_VALUE_WRITERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Name(), CodecTestHelper.TEST_VALUE_WRITER));
		personOptions.put(CodecAnnotations.CODEC_VALUE_READERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Name(), CodecTestHelper.TEST_VALUE_READER));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		Resource findResource = resourceSet.createResource(uri);
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals("John", p.getName());
	}

	@Test
	public void testDeserializationCustomReaderManyAttribute() throws InterruptedException, IOException {

		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Person/");
		Resource resource = resourceSet.createResource(uri);

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		personOptions.put(CodecAnnotations.CODEC_VALUE_WRITERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Titles(), CodecTestHelper.TEST_MULTI_VALUE_WRITER));
		personOptions.put(CodecAnnotations.CODEC_VALUE_READERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Titles(), CodecTestHelper.TEST_VALUE_READER));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		Resource findResource = resourceSet.createResource(uri);
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertThat(p.getTitles()).contains("Mrs", "Dr");
	}
}