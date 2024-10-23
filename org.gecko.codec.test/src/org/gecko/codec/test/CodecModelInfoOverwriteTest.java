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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.model.person.Person;
import org.gecko.codec.demo.model.person.PersonPackage;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
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
public class CodecModelInfoOverwriteTest {
	
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
	public void testCodecModelInfoOverwriteIdStrategy() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);		
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		personOptions.put(CodecAnnotations.CODEC_ID_STRATEGY, "TEST");
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo personCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getPerson().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(personCodecInfo);
		assertNotNull(personCodecInfo.getIdentityInfo());
		assertEquals("TEST", personCodecInfo.getIdentityInfo().getIdStrategy());
	}
	
	@Test
	public void testCodecModelInfoOverwriteIdOrder() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		personOptions.put(CodecAnnotations.CODEC_ID_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getPerson_LastName(), PersonPackage.eINSTANCE.getPerson_Name()));
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo personCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getPerson().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(personCodecInfo);
		assertNotNull(personCodecInfo.getIdentityInfo());
		assertThat(personCodecInfo.getIdentityInfo().getFeatures()).hasSize(2);
		assertEquals(PersonPackage.eINSTANCE.getPerson_LastName(), personCodecInfo.getIdentityInfo().getFeatures().get(0));
		assertEquals(PersonPackage.eINSTANCE.getPerson_Name(), personCodecInfo.getIdentityInfo().getFeatures().get(1));
	}
	
	@Test
	public void testCodecModelInfoOverwriteIdSeparator() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		personOptions.put(CodecAnnotations.CODEC_ID_SEPARATOR, "/");
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo personCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getPerson().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(personCodecInfo);
		assertNotNull(personCodecInfo.getIdentityInfo());
		assertEquals("/", personCodecInfo.getIdentityInfo().getIdSeparator());
	}
	
	@Test
	public void testCodecModelInfoOverwriteIgnoreFeature() throws InterruptedException, IOException {

		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addressOptions = new HashMap<>();
		addressOptions.put(CodecAnnotations.CODEC_IGNORE_NOT_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getAddress_Zip()));
		addressOptions.put(CodecAnnotations.CODEC_IGNORE_FEATURES_LIST, List.of(PersonPackage.eINSTANCE.getAddress_Street()));
		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addressOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo addressCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getAddress().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(addressCodecInfo);
		assertNotNull(addressCodecInfo.getFeatureInfo());
		assertThat(addressCodecInfo.getFeatureInfo()).hasSize(3);
		for(FeatureCodecInfo fi : addressCodecInfo.getFeatureInfo()) {
			if(PersonPackage.eINSTANCE.getAddress_Zip().equals(fi.getFeatures().get(0))) {
				assertFalse(fi.isIgnore());
			}
			if(PersonPackage.eINSTANCE.getAddress_Street().equals(fi.getFeatures().get(0))) {
				assertTrue(fi.isIgnore());
			}
		}
	}
	
	@Test
	public void testCodecModelInfoOverwriteTypeInclude() throws InterruptedException, IOException {
		
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		personOptions.put(CodecAnnotations.CODEC_TYPE_INCLUDE, false);
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo personCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getPerson().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(personCodecInfo);
		assertNotNull(personCodecInfo.getTypeInfo());
		assertTrue(personCodecInfo.getTypeInfo().isIgnoreType());
	}
	
	@Test
	public void testCodecModelInfoOverwriteTypeUse() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		personOptions.put(CodecAnnotations.CODEC_TYPE_USE, "CLASS");
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo personCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getPerson().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(personCodecInfo);
		assertNotNull(personCodecInfo.getTypeInfo());
		assertEquals("CLASS", personCodecInfo.getTypeInfo().getTypeStrategy());
		assertEquals("WRITE_BY_CLASS_NAME", personCodecInfo.getTypeInfo().getValueWriterName());
		assertEquals("READ_BY_CLASS", personCodecInfo.getTypeInfo().getValueReaderName());
	}
	
	
	@Test
	public void testCodecModelInfoOverwriteWriter() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		personOptions.put(CodecAnnotations.CODEC_VALUE_WRITERS_MAP, 
				Map.of(PersonPackage.eINSTANCE.getPerson_LastName(), CodecTestHelper.TEST_VALUE_WRITER));
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo personCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getPerson().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(personCodecInfo);
		FeatureCodecInfo featureInfo = personCodecInfo.getFeatureInfo().stream().filter(fi -> fi.getFeatures().get(0).equals(PersonPackage.eINSTANCE.getPerson_LastName())).findFirst().get();
		assertNotNull(featureInfo);
		assertEquals("TEST_VALUE_WRITER", featureInfo.getValueWriterName());
	}
	
	@Test
	public void testCodecModelInfoOverwriteReader() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		
		personOptions.put(CodecAnnotations.CODEC_VALUE_READERS_MAP, 
				Map.of(PersonPackage.eINSTANCE.getPerson_BirthDate(), CodecTestHelper.TEST_VALUE_READER));
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo personCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getPerson().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(personCodecInfo);
		FeatureCodecInfo featureInfo = personCodecInfo.getFeatureInfo().stream().filter(fi -> fi.getFeatures().get(0).equals(PersonPackage.eINSTANCE.getPerson_BirthDate())).findFirst().get();
		assertNotNull(featureInfo);
		assertEquals("TEST_VALUE_READER", featureInfo.getValueReaderName());
	}
	
	@Test
	public void testCodecModelInfoOverwriteIdWriter() throws InterruptedException, IOException {
	
		Resource resource = resourceSet.createResource(uri);
		Person person = CodecTestHelper.getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, CodecTestHelper.TEST_VALUE_WRITER);
		personOptions.put(CodecAnnotations.CODEC_ID_STRATEGY, "ID_FIELD");
		classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
		options.put("codec.options", classOptions);
		resource.save(options);
		
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		PackageCodecInfo modelCodecInfo = module.getCodecModelInfo();
		assertNotNull(modelCodecInfo);
		EClassCodecInfo personCodecInfo = modelCodecInfo.getEClassCodecInfo().stream().filter(ci -> PersonPackage.eINSTANCE.getPerson().getName().equals(ci.getClassifier().getName())).findFirst().get();
		assertNotNull(personCodecInfo);
		FeatureCodecInfo featureInfo = personCodecInfo.getIdentityInfo();
		assertNotNull(featureInfo);
		assertEquals("TEST_VALUE_WRITER", featureInfo.getValueWriterName());
	}
}
