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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
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
public class CodecJsonDeserializeReferenceTest extends JsonTestSetting{

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
	public void testDeserializationReference() throws InterruptedException, IOException {

		Resource addRes = resourceSet.createResource(URI.createURI(addFileName));
		Resource personRes = resourceSet.createResource(URI.createURI(personFileName));

		Address address = CodecTestHelper.getTestAddress();
		Person person = CodecTestHelper.getTestPerson();
		person.setNonContainedAdd(address);
		addRes.getContents().add(address);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		addRes.save(options);
		personRes.save(options);

		addRes.getContents().clear();
		addRes.unload();
		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = resourceSet.createResource(URI.createURI(personFileName));
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
		Address add = p.getNonContainedAdd();

		assertNotNull(add);
		assertEquals(address.getStreet(), add.getStreet());
		assertEquals(address.getId(), add.getId());
		assertNull(add.getZip());
	}
	

	@Test
	public void testDeserializationContainedReference() throws InterruptedException, IOException {

		Resource personRes = resourceSet.createResource(URI.createURI(personFileName));

		Address address = CodecTestHelper.getTestAddress();
		Person person = CodecTestHelper.getTestPerson();
		person.setAddress(address);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personRes.save(options);

		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = resourceSet.createResource(URI.createURI(personFileName));
		options = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
		Address add = p.getAddress();		
		assertNotNull(add);
		assertEquals(address.getStreet(), add.getStreet());
		assertEquals(address.getId(), add.getId());
		assertNull(add.getZip());
	}

	
	@Test
	public void testDeserializationManyContainedReference() throws InterruptedException, IOException {

		Resource personRes = resourceSet.createResource(URI.createURI(personFileName));

		Address address1 = CodecTestHelper.getTestAddress();
		Address address2 = CodecTestHelper.getTestAddress();
		Person person = CodecTestHelper.getTestPerson();
		person.getAddresses().add(address1);
		person.getAddresses().add(address2);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		personRes.save(options);

		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = resourceSet.createResource(URI.createURI(personFileName));
		options = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertThat(p.getAddresses()).hasSize(2);
		Address add1 = null, add2 = null;
		for(Address add : p.getAddresses()) {
			if(add.getId().equals(address1.getId())) add1 = add;
			else if(add.getId().equals(address2.getId())) add2 = add;
		}
		assertNotNull(add1);
		assertNotNull(add2);
	}


	@Test
	public void testDeserializationMultipleReference( ) throws InterruptedException, IOException {

		Resource addRes1 = resourceSet.createResource(URI.createURI(addFileName));
		Resource personRes = resourceSet.createResource(URI.createURI(personFileName));

		Address address1 = CodecTestHelper.getTestAddress();
		Address address2 = CodecTestHelper.getTestAddress();
		Person person = CodecTestHelper.getTestPerson();
		person.getNonContainedAdds().add(address1);
		person.getNonContainedAdds().add(address2);
		addRes1.getContents().add(address1);
		addRes1.getContents().add(address2);
		personRes.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		addRes1.save(options);
		personRes.save(options);

		addRes1.getContents().clear();
		addRes1.unload();
		personRes.getContents().clear();
		personRes.unload();

		Resource findResource = resourceSet.createResource(URI.createURI(personFileName));
		options = new HashMap<>();
		options.put("ROOT_OBJECT", PersonPackage.eINSTANCE.getPerson());

		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals(person.getId(), p.getId());
		assertThat(p.getNonContainedAdds()).hasSize(2);
		Address add1 = null, add2 = null;
		for(Address add : p.getNonContainedAdds()) {
			if(add.getId().equals(address1.getId())) add1 = add;
			else if(add.getId().equals(address2.getId())) add2 = add;
		}
		assertNotNull(add1);
		assertNotNull(add2);	
		assertEquals(address1.getStreet(), add1.getStreet());
		assertNull(add1.getZip());
		assertEquals(address2.getStreet(), add2.getStreet());
		assertNull(add2.getZip());
	}

}
