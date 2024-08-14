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

import java.io.IOException;

import org.gecko.codec.demo.jackson.CodecFactory;
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
			@Property(key = "enableFeatures", value={"CANONICALIZE_FIELD_NAMES", "JsonFactory.Feature.INTERN_FIELD_NAMES"}, type = Type.Array),
			@Property(key = "disableFeatures", value={"WRITE_NUMBERS_AS_STRINGS", "JsonGenerator.Feature.ESCAPE_NON_ASCII"}, type = Type.Array)
	})
	@Test
	public void testCodecJson(@InjectService(timeout = 2000l) CodecFactoryConfigurator configurator
			) throws InterruptedException, IOException {
	
		assertNotNull(configurator);
		
		CodecFactory codecFactory = configurator.getCodecFactory();
		assertNotNull(codecFactory);
		
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES));
		assertFalse(codecFactory.isEnabled(JsonFactory.Feature.INTERN_FIELD_NAMES));
		
	}
	

	
}
