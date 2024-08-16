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
import java.util.Locale;
import java.util.TimeZone;

import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.Type;
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
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
public class ObjMapperConfiguratorTest {

	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "enableFeatures", value={"MapperFeature.ALLOW_VOID_VALUED_PROPERTIES", "INDENT_OUTPUT", "SerializationFeature.CLOSE_CLOSEABLE", "USE_BIG_DECIMAL_FOR_FLOATS"}, type = Type.Array)
	})
	@Test
	public void testObjMapperConfigEnable(@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator mapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(factoryConfigurator);
		assertNotNull(mapperConfigurator);
		
		ObjectMapper mapper = mapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		
		assertTrue(mapper.isEnabled(MapperFeature.ALLOW_VOID_VALUED_PROPERTIES));
		assertTrue(mapper.isEnabled(SerializationFeature.INDENT_OUTPUT));
		assertTrue(mapper.isEnabled(SerializationFeature.CLOSE_CLOSEABLE));
		assertTrue(mapper.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS));
	
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "disableFeatures", value={"USE_ANNOTATIONS", "MapperFeature.AUTO_DETECT_FIELDS", "DeserializationFeature.WRAP_EXCEPTIONS"}, type = Type.Array),
	})
	@Test
	public void testObjMapperConfigDisable(@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator mapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(factoryConfigurator);
		assertNotNull(mapperConfigurator);
		
		ObjectMapper mapper = mapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
				
		assertFalse(mapper.isEnabled(MapperFeature.USE_ANNOTATIONS));
		assertFalse(mapper.isEnabled(MapperFeature.AUTO_DETECT_FIELDS));
		assertFalse(mapper.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS));
		assertTrue(mapper.isEnabled(SerializationFeature.WRAP_EXCEPTIONS));
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "dateFormat", value="yyyy-MM-dd"),
			@Property(key = "locale", value="it-IT"),
			@Property(key = "timeZone", value="Europe/Amsterdam"),
	})
	@Test
	public void testObjMapperConfiguratorDateFormat(@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator mapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(factoryConfigurator);
		assertNotNull(mapperConfigurator);
		
		ObjectMapper mapper = mapperConfigurator.getObjMapperBuilder().build();
		assertNotNull(mapper);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat mapperDF = mapper.getDateFormat();
		assertEquals(df.format(Date.valueOf(LocalDate.of(1990, 6, 20))), mapperDF.format(Date.valueOf(LocalDate.of(1990, 6, 20))));
		
		Locale loc = new Locale.Builder().setLanguageTag("it-IT").build();
		Locale mapperLoc = mapper.getSerializationConfig().getLocale();
		assertEquals(loc.getCountry(), mapperLoc.getCountry());
		assertEquals(loc.getLanguage(), mapperLoc.getLanguage());
		
		TimeZone tz = TimeZone.getTimeZone("Europe/Amsterdam");
		TimeZone mapperTZ = mapper.getSerializationConfig().getTimeZone();
		assertEquals(tz.getID(), mapperTZ.getID());
	}	
}
