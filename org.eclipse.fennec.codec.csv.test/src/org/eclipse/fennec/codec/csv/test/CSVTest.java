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
package org.eclipse.fennec.codec.csv.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.codec.csv.resource.CSVResource;
import org.eclipse.fennec.codec.csv.resource.CSVResourceFactory;
import org.eclipse.fennec.codec.options.CodecModuleOptions;
import org.eclipse.fennec.codec.options.CodecResourceOptions;
import org.eclipse.fennec.ecowitt.model.ecowitt.EcoWittPackage;
import org.eclipse.fennec.ecowitt.model.ecowitt.EcoWittWeather;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

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
@ExtendWith(ConfigurationExtension.class)
//@ExtendWith(MockitoExtension.class)
public class CSVTest {
	
//	@Mock
//	TestInterface test;
	
	@BeforeEach
	public void before(@InjectBundleContext BundleContext ctx) {
		
	}
	
	@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "csv", properties = {
			@Property(key = "type", value="csv"),
			@Property(key = "codecFactoryConfigurator.target", value="(type=csv)")
	})
	@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "csv")
	@Test
	public void testCSV01(
			@InjectService(cardinality = 0) ServiceAware<CSVResourceFactory> csvRFAware, @InjectService ResourceSet rs) {
		
		CSVResourceFactory csvRF = null;
		try {
			csvRF = csvRFAware.waitForService(5000l);
		} catch (InterruptedException e) {
			fail("Retrievial CSV ResourceFactory failed with timeout after 5 secs", e);
		}
		assertNotNull(csvRF);
		Resource resource = csvRF.createResource(URI.createURI(System.getProperty("data")+"test01.csv"));
		assertInstanceOf(CSVResource.class, resource);
		Map<String, Object> properties = new HashMap<>();
		properties.put(CodecResourceOptions.CODEC_ROOT_OBJECT, EcoWittPackage.eINSTANCE.getEcoWittWeather());
		properties.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, Boolean.TRUE);
		try {
			resource.load(properties);
		} catch (IOException e) {
			fail("Failed loading Ecowitt DATA", e);
		}
		assertFalse(resource.getContents().isEmpty());
		EObject content = resource.getContents().get(0);
		assertInstanceOf(EcoWittWeather.class, content);
	}

}
