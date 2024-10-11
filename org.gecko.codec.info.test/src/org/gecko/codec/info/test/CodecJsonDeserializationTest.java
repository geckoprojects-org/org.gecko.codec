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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonFactory;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.JsonResourceFactory;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.resource.CodecJsonResource;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
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

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;

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
public class CodecJsonDeserializationTest {

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
	
	public static final CodecValueReader<String, String> TEST_VALUE_READER = new CodecValueReader<>() {

		@Override
		public String getName() {
			return "TEST_VALUE_READER";
		}

		@Override
		public String readValue(String value, DeserializationContext context) {
			if(value == null) return null;
			if(value.startsWith("Super")) return value.substring(5, value.length());
			return value;
		}		
	};


	public static final CodecValueWriter<String, String> TEST_VALUE_WRITER = new CodecValueWriter<>() {

		@Override
		public String getName() {
			return "TEST_VALUE_WRITER";
		}

		@Override
		public String writeValue(String value, SerializerProvider provider) {
			if(value == null) return null;
			return "Super".concat(value);
		}
	};

	public static final CodecValueWriter<EClass, String> TEST_TYPE_WRITER = new CodecValueWriter<>() {

		@Override
		public String getName() {
			return "TEST_TYPE_WRITER";
		}

		@Override
		public String writeValue(EClass value, SerializerProvider provider) {
			return "test.".concat(value.getName());
		}
	};

	public static final CodecValueReader<String, EClass> TEST_TYPE_READER = new CodecValueReader<>() {

		@Override
		public String getName() {
			return "TEST_TYPE_READER";
		}

		@Override
		public EClass readValue(String value, DeserializationContext ctxt) {
			if(value == null) return null;
			if(value.startsWith("test.")) value = value.substring(5);
			return EMFContext.findEClassByName(ctxt, value);
		}
	};
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationIdCustomReader(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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

		Address address = getTestAddress();
		resource.getContents().add(address);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addOptions = new HashMap<>();
		addOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, TEST_VALUE_WRITER);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);

		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(addFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		options = new HashMap<>();
		classOptions = new HashMap<>();
		addOptions = new HashMap<>();

		addOptions.put(CodecAnnotations.CODEC_ID_VALUE_READER, TEST_VALUE_READER);
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationIdCombinedCustomReader(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, TEST_VALUE_WRITER);
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_READER, TEST_VALUE_READER);

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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationIdCombinedNoIdFieldSerialized(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, false);
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, TEST_VALUE_WRITER);
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_READER, TEST_VALUE_READER);

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

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationIdNoIdFieldSerialized(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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

		Address address = getTestAddress();
		resource.getContents().add(address);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addOptions = new HashMap<>();
		addOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, TEST_VALUE_WRITER);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, false);

		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(addFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		options = new HashMap<>();
		classOptions = new HashMap<>();
		addOptions = new HashMap<>();

		addOptions.put(CodecAnnotations.CODEC_ID_VALUE_READER, TEST_VALUE_READER);
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationTypeCustomReader(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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

		Address address = getTestAddress();
		resource.getContents().add(address);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addOptions = new HashMap<>();
		addOptions.put(CodecAnnotations.CODEC_TYPE_VALUE_WRITER, TEST_TYPE_WRITER);

		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(addFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		options = new HashMap<>();
		classOptions = new HashMap<>();
		addOptions = new HashMap<>();

		addOptions.put(CodecAnnotations.CODEC_TYPE_VALUE_READER, TEST_TYPE_READER);
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
			//			,
			//			@Property(key="dateFormat", value="dd-MM-yyyy")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationCustomReaderSingleAttribute(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personOptions.put(CodecAnnotations.CODEC_VALUE_WRITERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Name(), TEST_VALUE_WRITER));
		personOptions.put(CodecAnnotations.CODEC_VALUE_READERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Name(), TEST_VALUE_READER));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
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

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationCustomReaderManyAttribute(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		personOptions.put(CodecAnnotations.CODEC_VALUE_WRITERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Titles(), TEST_VALUE_WRITER));
		personOptions.put(CodecAnnotations.CODEC_VALUE_READERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_Titles(), TEST_VALUE_READER));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
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


	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationIgnoreFeature(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());
		personOptions.put(CodecAnnotations.CODEC_IGNORE_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_Age()));
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(0, p.getAge());
	}

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationIgnoreNOTFeature(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());
		personOptions.put(CodecAnnotations.CODEC_IGNORE_NOT_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_Age()));
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getAge(), p.getAge());
	}

	
	

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testDeserializationDefaultYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		CodecJsonResource findResource = new CodecJsonResource(URI.createURI(personFileName), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		options = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
		assertEquals("John", p.getName());
		assertEquals("Doe", p.getLastName());
		assertFalse(p.isMarried());		
		assertEquals(person.getBirthDate(), p.getBirthDate());
		assertThat(p.getTitles()).contains("Mrs", "Dr");
		assertEquals(person.getGender(), p.getGender());
		assertEquals(person.getAge(), p.getAge());
		assertEquals(0, p.getTransientAtt());
	}

	

	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@WithFactoryConfiguration(factoryPid = "CodecJsonRF", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDeserializationReference(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator,
			@InjectService(timeout = 2000l) JsonResourceFactory jsonResFactory,
			@InjectService(timeout = 2000l) ResourceSet rs
			) throws InterruptedException, IOException {

		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
		assertNotNull(jsonResFactory);
		assertNotNull(rs);

		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json", jsonResFactory);

		Resource addRes = rs.createResource(URI.createURI(addFileName));
		Resource personRes = rs.createResource(URI.createURI(personFileName));

		Address address = getTestAddress();
		Person person = getTestPerson();
		person.setNonContainedAdd(address);
		addRes.getContents().add(address);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		addRes.save(options);
		personRes.save(options);

		addRes.getContents().clear();
		addRes.unload();
		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = rs.createResource(URI.createURI(personFileName));
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
		Address add = p.getNonContainedAdd();

		assertNotNull(add);
		assertEquals(address.getStreet(), add.getStreet());
		assertEquals(address.getId(), add.getId());
		assertNull(add.getZip());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@WithFactoryConfiguration(factoryPid = "CodecJsonRF", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDeserializationRootObjWOType(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator,
			@InjectService(timeout = 2000l) JsonResourceFactory jsonResFactory,
			@InjectService(timeout = 2000l) ResourceSet rs
			) throws InterruptedException, IOException {

		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
		assertNotNull(jsonResFactory);
		assertNotNull(rs);

		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json", jsonResFactory);

		Resource personRes = rs.createResource(URI.createURI(personFileName));

		Person person = getTestPerson();
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		personRes.save(options);

		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = rs.createResource(URI.createURI(personFileName));
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
	}
	
//	TODO: this is failing at the moment because handling non cotanined ref w/o type info has never been supported before!!
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@WithFactoryConfiguration(factoryPid = "CodecJsonRF", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDeserializationReferenceWOType(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator,
			@InjectService(timeout = 2000l) JsonResourceFactory jsonResFactory,
			@InjectService(timeout = 2000l) ResourceSet rs
			) throws InterruptedException, IOException {

		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
		assertNotNull(jsonResFactory);
		assertNotNull(rs);

		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json", jsonResFactory);

		Resource addRes = rs.createResource(URI.createURI(addFileName));
		Resource personRes = rs.createResource(URI.createURI(personFileName));

		Address address = getTestAddress();
		Person person = getTestPerson();
		person.setNonContainedAdd(address);
		addRes.getContents().add(address);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		addRes.save(options);
		personRes.save(options);

		addRes.getContents().clear();
		addRes.unload();
		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = rs.createResource(URI.createURI(personFileName));
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
		Address add = p.getNonContainedAdd();

		assertNotNull(add);
		assertEquals(address.getStreet(), add.getStreet());
		assertEquals(address.getId(), add.getId());
		assertNull(add.getZip()); //zip is marked as transient in the model so it was not serialized
	}

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@WithFactoryConfiguration(factoryPid = "CodecJsonRF", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDeserializationContainedReference(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator,
			@InjectService(timeout = 2000l) JsonResourceFactory jsonResFactory,
			@InjectService(timeout = 2000l) ResourceSet rs
			) throws InterruptedException, IOException {

		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
		assertNotNull(jsonResFactory);
		assertNotNull(rs);

		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json", jsonResFactory);

		Resource personRes = rs.createResource(URI.createURI(personFileName));

		Address address = getTestAddress();
		Person person = getTestPerson();
		person.setAddress(address);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personRes.save(options);

		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = rs.createResource(URI.createURI(personFileName));
		options = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
		Address add = p.getAddress();		
		assertNotNull(add);
		assertEquals(address.getStreet(), add.getStreet());
		assertEquals(address.getId(), add.getId());
		assertNull(add.getZip());
	}

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@WithFactoryConfiguration(factoryPid = "CodecJsonRF", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDeserializationManyContainedReference(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator,
			@InjectService(timeout = 2000l) JsonResourceFactory jsonResFactory,
			@InjectService(timeout = 2000l) ResourceSet rs
			) throws InterruptedException, IOException {

		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
		assertNotNull(jsonResFactory);
		assertNotNull(rs);

		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json", jsonResFactory);

		Resource personRes = rs.createResource(URI.createURI(personFileName));

		Address address1 = getTestAddress();
		Address address2 = getTestAddress();
		Person person = getTestPerson();
		person.getAddresses().add(address1);
		person.getAddresses().add(address2);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personRes.save(options);

		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = rs.createResource(URI.createURI(personFileName));
		options = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertThat(p.getAddresses()).hasSize(2);
		Address add1 = null, add2 = null;
		for(Address add : p.getAddresses()) {
			if(add.getId().equals(address1.getId())) add1 = add;
			else if(add.getId().equals(address2.getId())) add2 = add;
		}
		assertNotNull(add1);
		assertNotNull(add2);
	}


	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@WithFactoryConfiguration(factoryPid = "CodecJsonRF", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDeserializationMultipleReference(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator,
			@InjectService(timeout = 2000l) JsonResourceFactory jsonResFactory,
			@InjectService(timeout = 2000l) ResourceSet rs
			) throws InterruptedException, IOException {

		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
		assertNotNull(jsonResFactory);
		assertNotNull(rs);

		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json", jsonResFactory);

		Resource addRes1 = rs.createResource(URI.createURI(addFileName));
		Resource personRes = rs.createResource(URI.createURI(personFileName));

		Address address1 = getTestAddress();
		Address address2 = getTestAddress();
		Person person = getTestPerson();
		person.getNonContainedAdds().add(address1);
		person.getNonContainedAdds().add(address2);
		addRes1.getContents().add(address1);
		addRes1.getContents().add(address2);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		addRes1.save(options);
		personRes.save(options);

		addRes1.getContents().clear();
		addRes1.unload();
		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = rs.createResource(URI.createURI(personFileName));
		options = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
		assertThat(p.getNonContainedAdds()).hasSize(2);
		Address add1 = null, add2 = null;
		for(Address add : p.getNonContainedAdds()) {
			if(add.getId().equals(address1.getId())) add1 = add;
			else if(add.getId().equals(address2.getId())) add2 = add;
		}
		assertNotNull(add1);
		assertNotNull(add2);	
		assertEquals(address1.getStreet(), add1.getStreet());
		assertNull(add1.getZip());
		assertEquals(address2.getStreet(), add2.getStreet());
		assertNull(add2.getZip());
	}

	



	private Person getTestPerson() {
		Person person = PersonFactory.eINSTANCE.createPerson();
		person.setId(UUID.randomUUID().toString());
		person.setName("John");
		person.setLastName("Doe");
		person.setAge(42);
		person.setMarried(false);
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
}
