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
package org.eclipse.fennec.codec.json.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.options.CodecModelInfoOptions;
import org.eclipse.fennec.codec.options.CodecResourceOptions;
import org.eclipse.fennec.codec.options.ObjectMapperOptions;
import org.eclipse.fennec.codec.test.helper.CodecTestHelper;
import org.gecko.codec.demo.model.person.Person;
import org.gecko.codec.demo.model.person.PersonPackage;
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

import tools.jackson.databind.SerializationFeature;

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
@WithFactoryConfiguration(factoryPid = "DefaultCodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
public class CodecJsonSerializeIdStrategyTest extends JsonTestSetting{
	
	@InjectService(cardinality = 0, filter = "(" + EMFNamespaces.EMF_CONFIGURATOR_NAME + "=CodecJson)")
	ServiceAware<ResourceSet> rsAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;
	
	private ResourceSet resourceSet;	
	
	@BeforeEach() 
	@Override
	public void beforeEach() throws Exception{
		super.beforeEach();
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
	}
	
	@AfterEach() 
	@Override
	public void afterEach() throws IOException {
		super.afterEach();
	}

	@Test
	public void testSerializationIdFieldStrategy() throws IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		personOptions.put(CodecModelInfoOptions.CODEC_ID_STRATEGY, "ID_FIELD");

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"_id\" : \"" + person.getId() + "\",")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}

	
	@Test
	public void testSerializationIdCombinedDefaultStrategy() throws IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		personOptions.put(CodecModelInfoOptions.CODEC_ID_STRATEGY, "COMBINED");

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"_id\" : \"" + person.getName() + "-" + person.getLastName() + "\",")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}

	
	@Test
	public void testSerializationIdCombinedStrategyDiffFeatures() throws IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		person.setAge(42);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		personOptions.put(CodecModelInfoOptions.CODEC_ID_STRATEGY, "COMBINED");
		personOptions.put(CodecModelInfoOptions.CODEC_ID_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_Name(), 
				PersonPackage.eINSTANCE.getPerson_Age()));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"_id\" : \"" + person.getName() + "-" + person.getAge() + "\",")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}

	
	@Test
	public void testSerializationIdCombinedStrategyDiffFeaturesOrder() throws IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		person.setAge(42);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		personOptions.put(CodecModelInfoOptions.CODEC_ID_STRATEGY, "COMBINED");
		personOptions.put(CodecModelInfoOptions.CODEC_ID_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_Age(), 
				PersonPackage.eINSTANCE.getPerson_Name()));

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"_id\" : \"" + person.getAge() + "-" + person.getName() + "\",")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}


	
	@Test
	public void testSerializationIdCombinedStrategyDiffSeparator() throws IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		person.setAge(42);
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		personOptions.put(CodecModelInfoOptions.CODEC_ID_STRATEGY, "COMBINED");
		personOptions.put(CodecModelInfoOptions.CODEC_ID_SEPARATOR, "test");

		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.save(options);

		try (BufferedReader reader = new BufferedReader(new FileReader(personFileName))) {
			String line = reader.readLine();
			boolean found = false;
			while(line != null) {
				if(line.contains("\"_id\" : \"" + person.getName() + "test" + person.getLastName() + "\",")) {
					found = true;
				}
				line = reader.readLine();
			}
			assertTrue(found);
		}
	}
}
