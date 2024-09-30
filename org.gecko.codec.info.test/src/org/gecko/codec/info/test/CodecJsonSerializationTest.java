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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
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
import org.gecko.code.demo.model.person.BusinessPerson;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonFactory;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.resource.CodecJsonResource;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.ObjectMapperOptions;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
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
public class CodecJsonSerializationTest {

	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationDefaultValuesYES(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("serialized.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader("serialized.json"))) {
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
	public void testSerializationDefaultValuesNOT(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("serialized.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader("serialized.json"))) {
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
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("serialized.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader("serialized.json"))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
//				 "birthDate" : "1990-06-20"
				 if(line.contains("\"birthDate\" : \"1990-06-20\",")) {
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
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("serialized.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		options.put(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT, new SimpleDateFormat("dd-MM-yyyy"));
		resource.save(options);
		
		 try (BufferedReader reader = new BufferedReader(new FileReader("serialized.json"))) {
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
	public void testSerializationRef(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("myaddress.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		
		Person person = getTestPerson();
		resource.getContents().add(person.getNonContainedAdd());
		resource.save(options);
		
		resource = new CodecJsonResource(URI.createURI("myperson.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		resource.getContents().add(person);
		
		resource.save(options);
	
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testSerializationSuperTypes(@InjectService(timeout = 2000l) PersonPackage demoModel,  
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
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("bsnperson.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		BusinessPerson person = getTestBusinessPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		resource.save(options);
		
//		 try (BufferedReader reader = new BufferedReader(new FileReader("serialized.json"))) {
//			 String line = reader.readLine();
//			 boolean found = false;
//			 while(line != null) {
//				 if(line.contains("\"married\" : false,")) {
//					 found = true;
//				 }
//				 line = reader.readLine();
//			 }
//			 assertTrue(found);
//		 }
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
		Address address = PersonFactory.eINSTANCE.createAddress();
		address.setId(UUID.randomUUID().toString());
		address.setStreet("Camsdorfer Str.");
		address.setZip("07749");
		person.setAddress(address);
		address = PersonFactory.eINSTANCE.createAddress();
		address.setId(UUID.randomUUID().toString());
		address.setStreet("Via Oregne");
		address.setZip("32037");
		person.setNonContainedAdd(address);
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
}
