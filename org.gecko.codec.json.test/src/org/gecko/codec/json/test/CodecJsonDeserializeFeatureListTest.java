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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.info.CodecAnnotations;
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
@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
public class CodecJsonDeserializeFeatureListTest extends JsonTestSetting{

	@InjectService(cardinality = 0, filter = "("+ EMFNamespaces.EMF_MODEL_NAME + "=person)")
	ServiceAware<ResourceSet> rsAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;
	
	private ResourceSet resourceSet;	
	
	@BeforeEach() 
	public void beforeEach() throws Exception{
		super.beforeEach();
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
	}
	
	@AfterEach() 
	public void afterEach() {
		super.afterEach();
	}
	
	@Test
	public void testDeserializationIgnoreFeature() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		Resource findResource = resourceSet.createResource(URI.createURI(personFileName));
		options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());
		personOptions.put(CodecAnnotations.CODEC_IGNORE_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_Age()));
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(0, p.getAge());
	}

	@Test
	public void testDeserializationIgnoreNOTFeature() throws InterruptedException, IOException {


		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		Resource findResource = resourceSet.createResource(URI.createURI(personFileName));
		options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());
		personOptions.put(CodecAnnotations.CODEC_IGNORE_NOT_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_Age()));
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getAge(), p.getAge());
	}
}
