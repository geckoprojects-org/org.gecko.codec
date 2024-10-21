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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.code.demo.model.person.Person;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
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
public class CodecJsonSerializeNullValuesTest extends JsonTestSetting{
	
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
	public void testSerializationNullSingleValueYESDefYES() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));
		
		Person person = CodecTestHelper.getTestPerson();
		person.setLastName(null);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true); //lastName = null is the default, so we need also this option
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true); //lastName is part of the id in the model so we need this option as well to get it serialized
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"lastName\" : null,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	
	@Test
	public void testSerializationNullSingleValueNODefYES() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));
		
		Person person = CodecTestHelper.getTestPerson();
		person.setLastName(null);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true); //lastName = null is the default, so we need also this option
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true); //lastName is part of the id in the model so we need this option as well to get it serialized
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"lastName\" : null,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	
	@Test
	public void testSerializationNullSingleValueNODefNO() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));
		
		Person person = CodecTestHelper.getTestPerson();
		person.setLastName(null);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false); //lastName = null is the default, so we need also this option
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true); //lastName is part of the id in the model so we need this option as well to get it serialized
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"lastName\" : null,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	
	@Test
	public void testSerializationNullSingleValueYESDefNO() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));
		
		Person person = CodecTestHelper.getTestPerson();
		person.setLastName(null);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false); //lastName = null is the default, so we need also this option
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true); //lastName is part of the id in the model so we need this option as well to get it serialized
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"lastName\" : null,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	
	@Test
	public void testSerializationNullRefValueYESDefYES() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));
		
		Person person = CodecTestHelper.getTestPerson();
		person.setAddress(null);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true); //address = null is the default, so we need also this option
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"address\" : null,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	

	@Test
	public void testSerializationNullRefValueNODefYES() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));
		
		Person person = CodecTestHelper.getTestPerson();
		person.setAddress(null);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true); //address = null is the default, so we need also this option
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"address\" : null,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	

	@Test
	public void testSerializationNullRefValueNODefNO() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));
		
		Person person = CodecTestHelper.getTestPerson();
		person.setAddress(null);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false); //address = null is the default, so we need also this option
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"address\" : null,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	
	@Test
	public void testSerializationNullRefValueYESDefNO() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));
		
		Person person = CodecTestHelper.getTestPerson();
		person.setAddress(null);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false); //address = null is the default, so we need also this option
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"address\" : null,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
}
