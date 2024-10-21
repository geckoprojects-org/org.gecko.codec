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
package org.gecko.codec.json.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.fasterxml.jackson.databind.SerializationFeature;

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
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
public class CodecJsonSerializeWithCustomWriterTest extends JsonTestSetting{
	
	@InjectService(cardinality = 0, filter = "("+ EMFNamespaces.EMF_MODEL_NAME + "=person)")
	ServiceAware<ResourceSet> rsAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;
	
	private ResourceSet resourceSet;	
	
	@BeforeEach() 
	public void beforeEach() throws Exception{
		super.beforeEach();
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
	}
	
	@AfterEach() 
	public void afterEach() {
		super.afterEach();
	}
	

	@Test
	public void testSerializationCustomWriterSingleAttribute() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personOptions.put(CodecAnnotations.CODEC_VALUE_WRITERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Name(), CodecTestHelper.TEST_VALUE_WRITER));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"name\" : \"SuperJohn\",")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}

	
	@Test
	public void testSerializationCustomWriterManyAttribute() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, false); //needed because in the model there is "title" and not "titles" in metadata
		personOptions.put(CodecAnnotations.CODEC_VALUE_WRITERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Titles(), CodecTestHelper.TEST_MULTI_VALUE_WRITER));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"titles\" : [ \"SuperMrs\", \"SuperDr\" ],")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}

	
	@Test
	public void testSerializationCustomIDFieldWriter() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		personOptions.put(CodecAnnotations.CODEC_ID_STRATEGY, "ID_FIELD");
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, CodecTestHelper.TEST_VALUE_WRITER);

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"_id\" : \"Super" + person.getId() +"\",")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}

	
	@Test
	public void testSerializationCustomIDCombinedWriter() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		personOptions.put(CodecAnnotations.CODEC_ID_STRATEGY, "COMBINED");
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, CodecTestHelper.TEST_VALUE_WRITER);
		personOptions.put(CodecAnnotations.CODEC_ID_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_Name(), PersonPackage.eINSTANCE.getPerson_LastName()));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"_id\" : \"Super" + person.getName() + "-" + person.getLastName() + "\",")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}
	
	@Test
	public void testSerializationTypeCustomWriter() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI(addFileName));
		Address address = CodecTestHelper.getTestAddress();
		resource.getContents().add(address);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		addOptions.put(CodecAnnotations.CODEC_TYPE_VALUE_WRITER, CodecTestHelper.TEST_TYPE_WRITER);

		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(addFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"_type\" : \"test.Address\"")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}
}
