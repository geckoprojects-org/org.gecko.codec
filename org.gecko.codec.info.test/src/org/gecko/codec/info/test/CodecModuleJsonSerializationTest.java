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
package org.gecko.codec.info.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.BusinessAddress;
import org.gecko.code.demo.model.person.BusinessPerson;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonFactory;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.code.demo.model.person.SpecificBusinessPerson;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.resource.CodecJsonResource;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.ObjectMapperOptions;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CodecModuleJsonSerializationTest {
	
	private String personFileName;
	private String addFileName;
	
	@BeforeEach() 
	public void beforeEach(){
		personFileName = "per".concat(UUID.randomUUID().toString()).concat(".json");
		addFileName = "add".concat(UUID.randomUUID().toString()).concat(".json");
	}
	
	@AfterEach() 
	public void afterEach() {
		File f = new File(personFileName);
		if(f.exists()) {
			f.delete();
		}
		f = new File(addFileName);
		if(f.exists()) {
			f.delete();
		}
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationCustomIdKey(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_ID_KEY, "_myId");
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_myId\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationCustomTypeKey(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_TYPE_KEY, "_myType");
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_myType\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationCustomSuperTypeKey(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		BusinessPerson person = getTestBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SUPERTYPE_KEY, "_mySuperType");
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_mySuperType\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationCustomRefKey(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(addFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		Address add = getTestAddress();
		Person person = getTestPerson();
		person.setNonContainedAdd(add);
		
		resource.getContents().add(add);
		resource.save(null);
		
		resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_REFERENCE_KEY, "_myRef");
		options.put(CodecModuleOptions.CODEC_MODULE_TYPE_KEY, "_myType");
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_myRef\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationUseIdYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_USE_ID, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_id\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationUseIdNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_USE_ID, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_id\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationIdOnTopYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_USE_ID, true);
		options.put(CodecModuleOptions.CODEC_MODULE_ID_ON_TOP, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 int lineNum = 0;
			 while(line != null) {
				 lineNum++;
				 if(line.contains("\"_id\" : ")) {
					 break;
				 }
				 line = reader.readLine();
			 }
			 assertEquals(2, lineNum);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationIdOnTopNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_USE_ID, true);
		options.put(CodecModuleOptions.CODEC_MODULE_ID_ON_TOP, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 int lineNum = 0;
			 while(line != null) {
				 lineNum++;
				 if(line.contains("\"_id\" : ")) {
					 break;
				 }
				 line = reader.readLine();
			 }
			 assertThat(lineNum).isGreaterThan(2);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializeIdFieldYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"name\" : ")) { //name is part of the _id field because of the COMBINED strategy set in the model
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializeIdFieldNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"name\" : ")) { //name is part of the _id field because of the COMBINED strategy set in the model
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationDefaultSingleValueYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"married\" : false,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationDefaultSingleValueNOT(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"married\" : false,")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptyManyValuesNODefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.getTitles().clear();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"title\" : [ ]")) { //we need to check against "title" and not "titles" because use-name-from-extended-metadata is true by default
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptyManyRefNODefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.getAddresses().clear();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"addresses\" : [ ]")) { 
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptyManyRefNODefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.getAddresses().clear();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"addresses\" : [ ]")) { 
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptyManyRefYESDefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.getAddresses().clear();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"addresses\" : [ ]")) { 
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptyManyRefYESDefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.getAddresses().clear();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"addresses\" : [ ]")) { 
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptyManyValuesYESDefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.getTitles().clear();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"title\" : [ ]")) {//we need to check against "title" and not "titles" because use-name-from-extended-metadata is true by default
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptyManyValuesNODefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.getTitles().clear();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"title\" : [ ]")) {//we need to check against "title" and not "titles" because use-name-from-extended-metadata is true by default
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptyManyValuesYESDefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.getTitles().clear();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"title\" : [ ]")) {//we need to check against "title" and not "titles" because use-name-from-extended-metadata is true by default
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNullSingleValueYESDefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNullSingleValueNODefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNullSingleValueNODefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNullSingleValueYESDefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNullRefValueYESDefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNullRefValueNODefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNullRefValueNODefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNullRefValueYESDefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptySingleValueYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.setLastName("");
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true); //lastName is part of the id in the model so we need this option as well to get it serialized
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"lastName\" : \"\",")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEmptySingleValueNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.setLastName("");
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, false);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true); //lastName is part of the id in the model so we need this option as well to get it serialized
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"lastName\" : \"\",")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationExtendedMetadataYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"title\" :")) {//we need to check against "title" and not "titles" because use-name-from-extended-metadata is true by default
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationExtendedMetadataNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"title\" :")) {//we need to check against "title" and not "titles" because use-name-from-extended-metadata is true by default
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationDefaultDateFormat(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"birthDate\" : \"1990-06-20T00:00:00\",")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationCustomDateFormat(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		options.put(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT, new SimpleDateFormat("dd-MM-yyyy"));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"birthDate\" : \"20-06-1990\",")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializeTypeRootObjYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_type\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializeTypeRootObjNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_type\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializeTypeContainedRefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		Address address = getTestAddress();
		person.setAddress(address);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false, foundSecond = false;
			 while(line != null) {
				 if(line.contains("\"_type\" : ")) {
					 if(found) foundSecond = true;
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(foundSecond);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializeTypeContainedRefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		Address address = getTestAddress();
		person.setAddress(address);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false, foundSecond = false;
			 while(line != null) {
				 if(line.contains("\"_type\" : ")) {
					 if(found) foundSecond = true;
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(foundSecond);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationSuperTypesYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		SpecificBusinessPerson person = getTestSpecificBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_supertype\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
		
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationAllSuperTypesYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		SpecificBusinessPerson person = getTestSpecificBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ALL_SUPER_TYPES, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_supertype\" : [ \"http://example.de/person/1.0#//Person\", \"http://example.de/person/1.0#//BusinessPerson\" ]")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationSuperTypesAsArrayYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		SpecificBusinessPerson person = getTestSpecificBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ALL_SUPER_TYPES, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES_AS_ARRAY, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_supertype\" : [ \"http://example.de/person/1.0#//Person\", \"http://example.de/person/1.0#//BusinessPerson\" ]")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationSuperTypesAsArrayNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		SpecificBusinessPerson person = getTestSpecificBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ALL_SUPER_TYPES, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES_AS_ARRAY, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_supertype\" : \"http://example.de/person/1.0#//Person,http://example.de/person/1.0#//BusinessPerson\"")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationAllSuperTypesNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		SpecificBusinessPerson person = getTestSpecificBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ALL_SUPER_TYPES, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_supertype\" : [ \"http://example.de/person/1.0#//BusinessPerson\" ]")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationSuperTypesNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		BusinessPerson person = getTestBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_supertype\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
		
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationSuperTypesContainedRefYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.setBusinessAdd(getTestBusinessAddress());
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true); 
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_supertype\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationSuperTypesContainedRefNO(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		person.setBusinessAdd(getTestBusinessAddress());
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, false); 
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_supertype\" : ")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertFalse(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNonContainedRef(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(addFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		
		Person person = getTestPerson();
		Address address = getTestAddress();
		person.setNonContainedAdd(address);
		resource.getContents().add(address);
		resource.save(options);
		
		resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		resource.getContents().add(person);
		resource.save(options);
		
		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"$ref\" : \""+addFileName+"#"+address.getId()+"\"")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationNonContainedRefType(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI(addFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		
		Person person = getTestPerson();
		Address address = getTestAddress();
		person.setNonContainedAdd(address);
		resource.getContents().add(address);
		resource.save(options);
		
		resource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		resource.getContents().add(person);
		resource.save(options);
		
		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"_type\" : \"http://example.de/person/1.0#//Address\"")) {
					 found = true;
				 }
				 line = reader.readLine();
			 }
			 assertTrue(found);
		 }
	}
	

	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationEPackage(@InjectService(timeout = 2000l) PersonPackage demoModel, 	
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("demomodel_nodefault.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		
		resource.getContents().add(EcoreUtil.copy(demoModel));
		resource.save(options);
	}
	
	@Test
	public void testEMFJsonEPackage() throws IOException {
		ObjectMapper mapper = new ObjectMapper(JsonFactory.builder().build());
		EMFModule module = new EMFModule();
		JsonResource res = new JsonResource(URI.createURI("emfjson_epackage.json"), mapper);
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		res.getContents().add(EcoreUtil.copy(PersonPackage.eINSTANCE));
		res.save(null);
	}
	
	
	
	private Person getTestPerson() {
		Person person = PersonFactory.eINSTANCE.createPerson();
		person.setId(UUID.randomUUID().toString());
		person.setName("John");
		person.setLastName("Doe");
		person.setBirthDate(Date.from(                     // Convert from modern java.time class to troublesome old legacy class.  DO NOT DO THIS unless you must, to inter operate with old code not yet updated for java.time.
			    LocalDate.of(1990, 6, 20)                        // `LocalDate` class represents a date-only, without time-of-day and without time zone nor offset-from-UTC. 
			    .atStartOfDay(                       // Let java.time determine the first moment of the day on that date in that zone. Never assume the day starts at 00:00:00.
			        ZoneId.of( "Europe/Berlin" )  // Specify time zone using proper name in `continent/region` format, never 3-4 letter pseudo-zones such as “PST”, “CST”, “IST”. 
			    )                                    // Produce a `ZonedDateTime` object. 
			    .toInstant()                         // Extract an `Instant` object, a moment always in UTC.
			));
		person.getTitles().add("Mrs");
		person.getTitles().add("Dr");
		person.setTransientAtt(7);
		return person;
	}
	
	private BusinessPerson getTestBusinessPerson() {
		BusinessPerson person = PersonFactory.eINSTANCE.createBusinessPerson();
		person.setId(UUID.randomUUID().toString());
		person.setName("John");
		person.setLastName("Doe");
		person.setBirthDate(Date.from(                     // Convert from modern java.time class to troublesome old legacy class.  DO NOT DO THIS unless you must, to inter operate with old code not yet updated for java.time.
			    LocalDate.of(1990, 6, 20)                        // `LocalDate` class represents a date-only, without time-of-day and without time zone nor offset-from-UTC. 
			    .atStartOfDay(                       // Let java.time determine the first moment of the day on that date in that zone. Never assume the day starts at 00:00:00.
			        ZoneId.of( "Europe/Berlin" )  // Specify time zone using proper name in `continent/region` format, never 3-4 letter pseudo-zones such as “PST”, “CST”, “IST”. 
			    )                                    // Produce a `ZonedDateTime` object. 
			    .toInstant()                         // Extract an `Instant` object, a moment always in UTC.
			));
		person.getTitles().add("Mrs");
		person.getTitles().add("Dr");
		person.setTransientAtt(7);
		return person;
	}
	
	private SpecificBusinessPerson getTestSpecificBusinessPerson() {
		SpecificBusinessPerson person = PersonFactory.eINSTANCE.createSpecificBusinessPerson();
		person.setId(UUID.randomUUID().toString());
		person.setName("John");
		person.setLastName("Doe");
		person.setBirthDate(Date.from(                     // Convert from modern java.time class to troublesome old legacy class.  DO NOT DO THIS unless you must, to inter operate with old code not yet updated for java.time.
			    LocalDate.of(1990, 6, 20)                        // `LocalDate` class represents a date-only, without time-of-day and without time zone nor offset-from-UTC. 
			    .atStartOfDay(                       // Let java.time determine the first moment of the day on that date in that zone. Never assume the day starts at 00:00:00.
			        ZoneId.of( "Europe/Berlin" )  // Specify time zone using proper name in `continent/region` format, never 3-4 letter pseudo-zones such as “PST”, “CST”, “IST”. 
			    )                                    // Produce a `ZonedDateTime` object. 
			    .toInstant()                         // Extract an `Instant` object, a moment always in UTC.
			));
		person.getTitles().add("Mrs");
		person.getTitles().add("Dr");
		person.setTransientAtt(7);
		return person;
	}
	
	private Address getTestAddress() {
		Address address = PersonFactory.eINSTANCE.createAddress();
		address.setId(UUID.randomUUID().toString());
		address.setStreet(UUID.randomUUID().toString());
		address.setZip(UUID.randomUUID().toString());
		return address;
	}
	
	private BusinessAddress getTestBusinessAddress() {
		BusinessAddress address = PersonFactory.eINSTANCE.createBusinessAddress();
		address.setId(UUID.randomUUID().toString());
		address.setStreet(UUID.randomUUID().toString());
		address.setZip(UUID.randomUUID().toString());
		return address;
	}
}
