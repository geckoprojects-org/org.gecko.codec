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
package org.gecko.codec.json.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.BusinessPerson;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.code.demo.model.person.SpecificBusinessPerson;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
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
public class CodecModuleJsonSerializationTest {
	
	@InjectService(cardinality = 0, filter = "("+ EMFNamespaces.EMF_MODEL_NAME + "=person)")
	ServiceAware<ResourceSet> rsAware;
	
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
	
	
	
	
	

	
	
	
	
	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@Test
	public void testSerializationNonContainedRef(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		ResourceSet resourceSet = rsAware.waitForService(2000l);  assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		Resource resource = resourceSet.createResource(URI.createURI(addFileName));
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		
		Person person = CodecTestHelper.getTestPerson();
		Address address = CodecTestHelper.getTestAddress();
		person.setNonContainedAdd(address);
		resource.getContents().add(address);
		resource.save(options);
		
		resource = resourceSet.createResource(URI.createURI(personFileName));
		resource.getContents().add(person);
		resource.save(options);
		
		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			 String line = reader.readLine();
			 boolean found = false;
			 while(line != null) {
				 if(line.contains("\"$ref\" : \""+addFileName+"#"+address.getId()+"\"")) {
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
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	
	

	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@Test
	public void testSerializationEPackage(@InjectService(timeout = 2000l) PersonPackage demoModel, 	
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		ResourceSet resourceSet = rsAware.waitForService(2000l);  assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		Resource resource = resourceSet.createResource(URI.createURI("demomodel_nodefault.json"));
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
	
	
	
	
	
	
}
