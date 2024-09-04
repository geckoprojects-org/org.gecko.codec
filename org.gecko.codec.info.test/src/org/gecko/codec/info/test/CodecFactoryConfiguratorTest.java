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

import java.io.IOException;

import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteFeature;

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
public class CodecFactoryConfiguratorTest {

	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "disableFeatures", value={"INTERN_FIELD_NAMES", "JsonFactory.Feature.CANONICALIZE_FIELD_NAMES", 
					"FAIL_ON_SYMBOL_HASH_OVERFLOW", "JsonFactory.Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING", "CHARSET_DETECTION"}, type = Type.Array)
	})
	@Test
	public void testFactoryConfigDisableJsonFactoryFeature(@InjectService(timeout = 2000l) CodecFactoryConfigurator configurator
			) throws InterruptedException, IOException {
	
		assertNotNull(configurator);
		
		JsonFactory codecFactory = configurator.getFactoryBuilder().build();
		assertNotNull(codecFactory);
				
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.INTERN_FIELD_NAMES));
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES));
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW));
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING));
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.CHARSET_DETECTION));	
	}
	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "enableFeatures", value={"WRITE_BIGDECIMAL_AS_PLAIN", "StreamWriteFeature.IGNORE_UNKNOWN", 
					"StreamWriteFeature.STRICT_DUPLICATE_DETECTION", "StreamWriteFeature.USE_FAST_DOUBLE_WRITER"}, type = Type.Array)
	})
	@Test
	public void testFactoryConfigEnableStreamWrite(@InjectService(timeout = 2000l) CodecFactoryConfigurator configurator
			) throws InterruptedException, IOException {
	
		assertNotNull(configurator);
		
		JsonFactory codecFactory = configurator.getFactoryBuilder().build();
		assertNotNull(codecFactory);
				
		assertTrue(codecFactory.isEnabled(StreamWriteFeature.IGNORE_UNKNOWN));
		assertTrue(codecFactory.isEnabled(StreamWriteFeature.USE_FAST_DOUBLE_WRITER));
		assertTrue(codecFactory.isEnabled(StreamWriteFeature.STRICT_DUPLICATE_DETECTION));
		assertTrue(codecFactory.isEnabled(StreamWriteFeature.WRITE_BIGDECIMAL_AS_PLAIN));
	
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "disableFeatures", value={"AUTO_CLOSE_TARGET", "StreamWriteFeature.AUTO_CLOSE_CONTENT", 
					"StreamWriteFeature.FLUSH_PASSED_TO_STREAM"}, type = Type.Array)
	})
	@Test
	public void testFactoryConfigDisableStreamWrite(@InjectService(timeout = 2000l) CodecFactoryConfigurator configurator
			) throws InterruptedException, IOException {
	
		assertNotNull(configurator);
		
		JsonFactory codecFactory = configurator.getFactoryBuilder().build();
		assertNotNull(codecFactory);
				
		assertFalse(codecFactory.isEnabled(StreamWriteFeature.AUTO_CLOSE_TARGET));
		assertFalse(codecFactory.isEnabled(StreamWriteFeature.AUTO_CLOSE_CONTENT));
		assertFalse(codecFactory.isEnabled(StreamWriteFeature.FLUSH_PASSED_TO_STREAM));	
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "enableFeatures", value={"STRICT_DUPLICATE_DETECTION", "IGNORE_UNDEFINED", 
					"INCLUDE_SOURCE_IN_LOCATION", "USE_FAST_DOUBLE_PARSER", "USE_FAST_BIG_NUMBER_PARSER"}, type = Type.Array)
	})
	@Test
	public void testFactoryConfigEnableStreamRead(@InjectService(timeout = 2000l) CodecFactoryConfigurator configurator
			) throws InterruptedException, IOException {
	
		assertNotNull(configurator);
		
		JsonFactory codecFactory = configurator.getFactoryBuilder().build();
		assertNotNull(codecFactory);
				
		assertTrue(codecFactory.isEnabled(StreamReadFeature.STRICT_DUPLICATE_DETECTION));
		assertTrue(codecFactory.isEnabled(StreamReadFeature.IGNORE_UNDEFINED));
		assertTrue(codecFactory.isEnabled(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION));
		assertTrue(codecFactory.isEnabled(StreamReadFeature.USE_FAST_BIG_NUMBER_PARSER));
		assertTrue(codecFactory.isEnabled(StreamReadFeature.USE_FAST_DOUBLE_PARSER));	
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "disableFeatures", value={"AUTO_CLOSE_SOURCE"}, type = Type.Array)
	})
	@Test
	public void testFactoryConfigDisableStreamRead(@InjectService(timeout = 2000l) CodecFactoryConfigurator configurator
			) throws InterruptedException, IOException {
	
		assertNotNull(configurator);
		
		JsonFactory codecFactory = configurator.getFactoryBuilder().build();
		assertNotNull(codecFactory);
				
		assertFalse(codecFactory.isEnabled(StreamReadFeature.AUTO_CLOSE_SOURCE));
	}
}
