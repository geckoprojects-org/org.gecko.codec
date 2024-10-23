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
package org.gecko.codec.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.model.person.Person;
import org.gecko.codec.info.CodecModelInfo;
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
public class ObjMapperConfigOverwriteTest {

	@InjectService(cardinality = 0, filter = "("+ EMFNamespaces.EMF_MODEL_NAME + "=person)")
	ServiceAware<ResourceSet> rsAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;
	
	private ResourceSet resourceSet;	
	private ObjectMapperConfigurator objMapperConfigurator;
	private URI uri = URI.createURI("mytest.json");
	
	@BeforeEach() 
	public void beforeEach() throws Exception{
		codecFactoryAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
		objMapperConfigurator = mapperAware.waitForService(2000l);
		assertNotNull(objMapperConfigurator);
	}
	
	@AfterEach()
	public void afterEach() {
		File f = new File("mytest.json");
		if(f.exists()) {
			f.delete();
		}
	}
	
	@Test
	public void testDateFormatOption() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		options.put(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT, df);
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		
		DateFormat mapperDF = mapper.getDateFormat();
		assertEquals(df.format(Date.from(                     // Convert from modern java.time class to troublesome old legacy class.  DO NOT DO THIS unless you must, to inter operate with old code not yet updated for java.time.
			    LocalDate.of(1990, 6, 20)                        // `LocalDate` class represents a date-only, without time-of-day and without time zone nor offset-from-UTC. 
			    .atStartOfDay(                       // Let java.time determine the first moment of the day on that date in that zone. Never assume the day starts at 00:00:00.
			        ZoneId.of( "Europe/Berlin" )  // Specify time zone using proper name in `continent/region` format, never 3-4 letter pseudo-zones such as “PST”, “CST”, “IST”. 
			    )                                    // Produce a `ZonedDateTime` object. 
			    .toInstant()                         // Extract an `Instant` object, a moment always in UTC.
			)), mapperDF.format(Date.from(                     // Convert from modern java.time class to troublesome old legacy class.  DO NOT DO THIS unless you must, to inter operate with old code not yet updated for java.time.
				    LocalDate.of(1990, 6, 20)                        // `LocalDate` class represents a date-only, without time-of-day and without time zone nor offset-from-UTC. 
				    .atStartOfDay(                       // Let java.time determine the first moment of the day on that date in that zone. Never assume the day starts at 00:00:00.
				        ZoneId.of( "Europe/Berlin" )  // Specify time zone using proper name in `continent/region` format, never 3-4 letter pseudo-zones such as “PST”, “CST”, “IST”. 
				    )                                    // Produce a `ZonedDateTime` object. 
				    .toInstant()                         // Extract an `Instant` object, a moment always in UTC.
				)));
	}
	
	
	@Test
	public void testLocaleOption() throws InterruptedException, IOException {
		
		Resource resource = resourceSet.createResource(uri);		
		Person person = CodecTestHelper.getTestPerson();
		
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
	
	
	@Test
	public void testTimeZoneOption() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);		
		Person person = CodecTestHelper.getTestPerson();
		
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
	
	
	
	@Test
	public void testSerFeatureWithOption() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT, SerializationFeature.CLOSE_CLOSEABLE));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertTrue(mapper.isEnabled(SerializationFeature.INDENT_OUTPUT));
		assertTrue(mapper.isEnabled(SerializationFeature.CLOSE_CLOSEABLE));
	}
	
	
	@Test
	public void testSerFeatureWithoutOption() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITHOUT, List.of(SerializationFeature.FAIL_ON_EMPTY_BEANS, SerializationFeature.FAIL_ON_SELF_REFERENCES));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertFalse(mapper.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
		assertFalse(mapper.isEnabled(SerializationFeature.FAIL_ON_SELF_REFERENCES));
	}
	
	
	@Test
	public void testDeserFeatureWithOption() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_DESERIALIZATION_FEATURES_WITH, List.of(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertTrue(mapper.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT));
		assertTrue(mapper.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
	}
	
	
	@Test
	public void testDeserFeatureWithoutOption() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_DESERIALIZATION_FEATURES_WITHOUT, List.of(DeserializationFeature.ACCEPT_FLOAT_AS_INT, DeserializationFeature.EAGER_DESERIALIZER_FETCH));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertFalse(mapper.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT));
		assertFalse(mapper.isEnabled(DeserializationFeature.EAGER_DESERIALIZER_FETCH));
	}
	
	
	
	@Test
	public void testMapperFeatureWithOption() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_FEATURES_WITH, 
				List.of(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, 
						MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
						MapperFeature.SORT_PROPERTIES_ALPHABETICALLY));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertTrue(mapper.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS));
		assertTrue(mapper.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
		assertTrue(mapper.isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY));
	}
	
	
	@Test
	public void testMapperFeatureWithoutOption() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_FEATURES_WITHOUT, List.of(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS, MapperFeature.APPLY_DEFAULT_VALUES));
		resource.save(options);
		
		ObjectMapper mapper = objMapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		assertFalse(mapper.isEnabled(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS));
		assertFalse(mapper.isEnabled(MapperFeature.APPLY_DEFAULT_VALUES));
	}
}
