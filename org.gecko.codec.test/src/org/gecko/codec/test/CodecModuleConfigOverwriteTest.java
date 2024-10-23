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
package org.gecko.codec.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.model.person.Person;
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
public class CodecModuleConfigOverwriteTest {
	
	@InjectService(cardinality = 0, filter = "("+ EMFNamespaces.EMF_MODEL_NAME + "=person)")
	ServiceAware<ResourceSet> rsAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;
	
	private ResourceSet resourceSet;	
	private CodecModuleConfigurator codecModuleConfigurator;
	private URI uri = URI.createURI("mytest.json");
	
	@BeforeEach() 
	public void beforeEach() throws Exception{
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
		codecModuleConfigurator = codecModuleAware.waitForService(2000l);
		assertNotNull(codecModuleConfigurator);
	}
	
	@AfterEach()
	public void afterEach() {
		File f = new File("mytest.json");
		if(f.exists()) {
			f.delete();
		}
	}

	
	@Test
	public void testCodecModuleOverwriteIdKey() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("_id", module.getIdKey());		
		
		Resource resource = resourceSet.createResource(uri);
		
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_ID_KEY, "test");
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getIdKey());		
	}
	
	@Test
	public void testCodecModuleOverwriteTypeKey() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("_type", module.getTypeKey());		
		
		Resource resource = resourceSet.createResource(uri);
		
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_TYPE_KEY, "test");
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getTypeKey());		
	}
	
	@Test
	public void testCodecModuleOverwriteProxyKey() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("_proxy", module.getProxyKey());		
		
		Resource resource = resourceSet.createResource(uri);
		
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_PROXY_KEY, "test");
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getProxyKey());		
	}
	
	@Test
	public void testCodecModuleOverwriteRefKey() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("$ref", module.getRefKey());		
		
		Resource resource = resourceSet.createResource(uri);
		
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_REFERENCE_KEY, "test");
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getRefKey());		
	}
	
	@Test
	public void testCodecModuleOverwriteTimestampKey() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("_timestamp", module.getTimestampKey());		
		
		Resource resource = resourceSet.createResource(uri);
		
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_TIMESTAMP_KEY, "test");
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getTimestampKey());		
	}
	
	@Test
	public void testCodecModuleOverwriteSupertypeKey() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("_supertype", module.getSuperTypeKey());		
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SUPERTYPE_KEY, "test");
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getSuperTypeKey());		
	}
	
	@Test
	public void testCodecModuleOverwriteUseId() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isUseId());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_USE_ID, false);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isUseId());	
	}

	
	@Test
	public void testCodecModuleOverwriteSerializeType() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeType());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeType());	
	}
	
	@Test
	public void testCodecModuleOverwriteSerializeSuperTypesAsArray() throws InterruptedException, IOException {
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeSuperTypesAsArray());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES_AS_ARRAY, false);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeSuperTypesAsArray());	
	}
	
	@Test
	public void testCodecModuleOverwriteUseNamesFromExtendedMetaData() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isUseNamesFromExtendedMetaData());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, false);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isUseNamesFromExtendedMetaData());	
	}
	
	@Test
	public void testCodecModuleOverwriteIdOnTop() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isIdOnTop());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_ID_ON_TOP, false);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isIdOnTop());	
	}
	
	@Test
	public void testCodecModuleOverwriteIdFeatureAsPrimaryKey() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isIdFeatureAsPrimaryKey());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_ID_FEATURE_AS_PRIMARY_KEY, false);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isIdFeatureAsPrimaryKey());	
	}
	
	@Test
	public void testCodecModuleOverwriteSerializeSuperTypes() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeSuperTypes());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES, true);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeSuperTypes());	
	}
	
	@Test
	public void testCodecModuleOverwriteSerializeDefaultValue() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeDefaultValue());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeDefaultValue());	
	}
	
	@Test
	public void testCodecModuleOverwriteSerializeEmptyValue() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeEmptyValue());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE, true);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeEmptyValue());	
	}
	
	@Test
	public void testCodecModuleOverwriteSerializeNullValue() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeNullValue());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE, true);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeNullValue());	
	}
	
	@Test
	public void testCodecModuleOverwriteSerializeArrayBatched() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeArrayBatched());	
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ARRAY_BATCHED, true);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeArrayBatched());	
	}
	
	@Test
	public void testCodecModuleOverwriteSerializeIdField() throws InterruptedException, IOException {
	
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeIdField());	
		
		Resource resource = resourceSet.createResource(uri);
		
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD, true);
		resource.save(options);
		
		module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeIdField());	
	}
}
