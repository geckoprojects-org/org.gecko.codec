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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonFactory;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.resource.CodecJsonResource;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.ObjectMapperOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
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
/**
 * Tests made to check the correct behaviour when properties set by {@link CodecModelInfo} should be 
 * overwritten by the {@link Resource}{@link #save()} and {@link Resource}{@link #load()} options
 * @author ilenia
 * @since Aug 15, 2024
 */
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
public class ObjMapperConfigOverwriteTest {

	
	
//	/**
//	 * This is just a comparison test that uses the standard emfjson libraries
//	 * @param demoModel
//	 * @throws InterruptedException
//	 * @throws IOException
//	 */
//	@Test
//	public void testEMFJson(@InjectService(timeout = 2000l) PersonPackage demoModel) throws InterruptedException, IOException {
//	
//		assertNotNull(demoModel);
//		
//		EMFModule module = new EMFModule();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//		mapper.registerModule(module);
//		JsonResource resource = new JsonResource(URI.createURI("test.json"), mapper);
//		
//		Person person = PersonFactory.eINSTANCE.createPerson();
//		person.setName("Ilenia");
//		person.setLastName("Salvadori");
//		person.setBirthDate(Date.valueOf(LocalDate.of(1990, 6, 20)));
//		Address address = PersonFactory.eINSTANCE.createAddress();
//		address.setStreet("Camsdorfer Str. 41");
//		address.setZip("07749");
//		person.setAddress(address);
//		
//		resource.getContents().add(person);
//		resource.save(null);
//	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDateFormatOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		options.put(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT, df);
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		
		DateFormat mapperDF = mapper.getDateFormat();
		assertEquals(df.format(Date.valueOf(LocalDate.of(1990, 6, 20))), mapperDF.format(Date.valueOf(LocalDate.of(1990, 6, 20))));
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testLocaleOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Locale loc = new Locale.Builder().setLanguageTag("it-IT").build();
		options.put(ObjectMapperOptions.OBJ_MAPPER_LOCALE, loc);
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		
		Locale mapperLoc = mapper.getSerializationConfig().getLocale();
		assertEquals(loc.getCountry(), mapperLoc.getCountry());
		assertEquals(loc.getLanguage(), mapperLoc.getLanguage());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testTimeZoneOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		TimeZone tz = TimeZone.getTimeZone("Europe/Amsterdam");
		options.put(ObjectMapperOptions.OBJ_MAPPER_TIME_ZONE, tz);
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		
		TimeZone mapperTZ = mapper.getSerializationConfig().getTimeZone();
		assertEquals(tz.getID(), mapperTZ.getID());
	}
	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testSerFeatureWithOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT, SerializationFeature.CLOSE_CLOSEABLE));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertTrue(mapper.isEnabled(SerializationFeature.INDENT_OUTPUT));
		assertTrue(mapper.isEnabled(SerializationFeature.CLOSE_CLOSEABLE));
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testSerFeatureWithoutOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITHOUT, List.of(SerializationFeature.FAIL_ON_EMPTY_BEANS, SerializationFeature.FAIL_ON_SELF_REFERENCES));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertFalse(mapper.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
		assertFalse(mapper.isEnabled(SerializationFeature.FAIL_ON_SELF_REFERENCES));
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDeserFeatureWithOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_DESERIALIZATION_FEATURES_WITH, List.of(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertTrue(mapper.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT));
		assertTrue(mapper.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testDeserFeatureWithoutOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_DESERIALIZATION_FEATURES_WITHOUT, List.of(DeserializationFeature.ACCEPT_FLOAT_AS_INT, DeserializationFeature.EAGER_DESERIALIZER_FETCH));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertFalse(mapper.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT));
		assertFalse(mapper.isEnabled(DeserializationFeature.EAGER_DESERIALIZER_FETCH));
	}
	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testMapperFeatureWithOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_FEATURES_WITH, List.of(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertTrue(mapper.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS));
		assertTrue(mapper.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json")
	})
	@Test
	public void testMapperFeatureWithoutOption(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
		
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_FEATURES_WITHOUT, List.of(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS, MapperFeature.APPLY_DEFAULT_VALUES));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertFalse(mapper.isEnabled(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS));
		assertFalse(mapper.isEnabled(MapperFeature.APPLY_DEFAULT_VALUES));
	}
	
//	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
//			@Property(key = "type", value="json")
//	})
//	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
//			@Property(key = "type", value="json")
//	})
//	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
//			@Property(key = "codecType", value="json"),
//			@Property(key = "useId", value="true", scalar = Scalar.Boolean),
//			@Property(key = "idOnTop", value="true", scalar = Scalar.Boolean),
//			@Property(key = "idKey", value="custom_id_key")
//	})
//	@Test
//	public void testCodecJsonIdSeparator(@InjectService(timeout = 2000l) PersonPackage demoModel,  
//			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
//			@InjectService(timeout = 2000l) CodecModule codecModule,
//			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
//			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
//			) throws InterruptedException, IOException {
//	
//		assertNotNull(demoModel);
//		assertNotNull(codecModelInfo);
//		assertNotNull(codecModuleConfigurator);
//		assertNotNull(factoryConfigurator);
//		assertNotNull(objMapperConfigurator);
//		
//		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
//		
//		Person person = PersonFactory.eINSTANCE.createPerson();
//		person.setName("Ilenia");
//		person.setLastName("Salvadori");
//		person.setBirthDate(Date.valueOf(LocalDate.of(1990, 6, 20)));
//		Address address = PersonFactory.eINSTANCE.createAddress();
//		address.setStreet("Camsdorfer Str. 41");
//		address.setZip("07749");
//		person.setAddress(address);
//		
//		resource.getContents().add(person);
//		Map<String, Object> options = new HashMap<>();
//		options.put(CodecAnnotations.CODEC_ID_SEPARATOR, "/");
//		resource.save(options);
//	}
	
	private Person getTestPerson() {
		Person person = PersonFactory.eINSTANCE.createPerson();
		person.setName("John");
		person.setLastName("Doe");
		person.setBirthDate(Date.valueOf(LocalDate.of(1990, 6, 20)));
		Address address = PersonFactory.eINSTANCE.createAddress();
		address.setStreet("Camsdorfer Str.");
		address.setZip("07749");
		person.setAddress(address);
		return person;
	}
}
