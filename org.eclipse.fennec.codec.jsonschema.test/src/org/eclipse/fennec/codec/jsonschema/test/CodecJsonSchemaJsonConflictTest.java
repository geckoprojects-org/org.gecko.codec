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
package org.eclipse.fennec.codec.jsonschema.test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.json.resource.CodecJsonResource;
import org.eclipse.fennec.codec.jsonschema.resource.CodecJsonSchemaResource;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
public class CodecJsonSchemaJsonConflictTest {

	@InjectService(filter="("+EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/schema+json)")
	ResourceSet resourceSet;

	@InjectService
	CodecModelInfo codecModelInfo;

	@InjectBundleContext
	BundleContext ctx;



	@BeforeEach()
	public void beforeEach() throws InterruptedException {
		assertNotNull(resourceSet);
	}

	@AfterEach()
	public void afterEach() throws IOException {
//		if(file2 != null) Files.deleteIfExists(Path.of(file2));
	}
	
	@WithFactoryConfiguration(factoryPid = "DefaultCodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@Test
	public void testConflict() throws IOException {
		
		Resource jsonSchemaRes = resourceSet.createResource(URI.createURI("temp.jsonschema"), "application/schema+json");
		assertInstanceOf(CodecJsonSchemaResource.class, jsonSchemaRes);
		
		Resource jsonRes = resourceSet.createResource(URI.createURI("temp.json"), "application/json");
		assertInstanceOf(CodecJsonResource.class, jsonRes);
	}
	
	
}