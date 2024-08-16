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
import com.fasterxml.jackson.core.JsonGenerator;
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

	
	
	@SuppressWarnings("deprecation")
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "enableFeatures", value={"WRITE_NUMBERS_AS_STRINGS", "JsonWriteFeature.ESCAPE_NON_ASCII", 
					"StreamWriteFeature.STRICT_DUPLICATE_DETECTION", "StreamReadFeature.STRICT_DUPLICATE_DETECTION"}, type = Type.Array)
	})
	@Test
	public void testFactoryConfigEnable(@InjectService(timeout = 2000l) CodecFactoryConfigurator configurator
			) throws InterruptedException, IOException {
	
		assertNotNull(configurator);
		
		JsonFactory codecFactory = configurator.getFactoryBuilder().build();
		assertNotNull(codecFactory);
				
		assertTrue(codecFactory.isEnabled(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS));
		assertTrue(codecFactory.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII));
		assertTrue(codecFactory.isEnabled(StreamWriteFeature.STRICT_DUPLICATE_DETECTION));
		assertTrue(codecFactory.isEnabled(StreamReadFeature.STRICT_DUPLICATE_DETECTION));
	
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json"),
			@Property(key = "disableFeatures", value={"CANONICALIZE_FIELD_NAMES", "JsonFactory.Feature.INTERN_FIELD_NAMES", "AUTO_CLOSE_SOURCE", "AUTO_CLOSE_TARGET"}, type = Type.Array),
	})
	@Test
	public void testFactoryConfigDisable(@InjectService(timeout = 2000l) CodecFactoryConfigurator configurator
			) throws InterruptedException, IOException {
	
		assertNotNull(configurator);
		
		JsonFactory codecFactory = configurator.getFactoryBuilder().build();
		assertNotNull(codecFactory);
				
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES));
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.INTERN_FIELD_NAMES));
		assertFalse(codecFactory.isEnabled(StreamReadFeature.AUTO_CLOSE_SOURCE));
		assertFalse(codecFactory.isEnabled(StreamWriteFeature.AUTO_CLOSE_TARGET));
	}
}
