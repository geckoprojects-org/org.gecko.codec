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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.options.CodecModelInfoOptions;
import org.eclipse.fennec.codec.options.CodecModuleOptions;
import org.eclipse.fennec.codec.options.CodecResourceOptions;
import org.eclipse.fennec.codec.test.helper.CodecTestHelper;
import org.gecko.codec.demo.model.person.Child;
import org.gecko.codec.demo.model.person.Child2;
import org.gecko.codec.demo.model.person.Person;
import org.gecko.codec.demo.model.person.PersonPackage;
import org.gecko.codec.demo.model.person.TestObject;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
@WithFactoryConfiguration(factoryPid = "DefaultCodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
public class CodecJsonDeserializeTypeTest extends JsonTestSetting{

	@InjectBundleContext
	BundleContext ctx;

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
		resourceSet = rsAware.waitForService(40000l);
		assertNotNull(resourceSet);
	}

	@AfterEach() 
	@Override
	public void afterEach() throws IOException {
		super.afterEach();
	}

	@Test
	public void testDifferentTypeKey() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		Person person = CodecTestHelper.getTestPerson();
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, PersonPackage.Literals.PERSON);
		Map<String, Object> classOptions = new HashMap<>();
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "type");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");
		options.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(PersonPackage.Literals.PERSON, classOptions));
		resource.save(options);

		resource.getContents().clear();
		resource.unload();

		Resource findResource = resourceSet.createResource(URI.createURI(personFileName));
		findResource.load(options);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());

		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertNotNull(p);
	}


	@Test
	public void testDeserializationDifferentTypeKeysFeatureLevelDynamicModel() throws IOException {

		// load ecore
		Resource ecoreResource = resourceSet.createResource(URI.createURI(ctx.getBundle().getEntry("test-data/type-as-feature.ecore").toString()));
		ecoreResource.load(null);
		EPackage epackage = (EPackage) ecoreResource.getContents().get(0);
		EClass child1 = (EClass) epackage.getEClassifier("Child");
		EClass child2 = (EClass) epackage.getEClassifier("Child2");
		EClass testClass = (EClass) epackage.getEClassifier("TestObject");

		URI uri1 = EcoreUtil.getURI(child1);
		URI uri2 = EcoreUtil.getURI(child2);
		
		String t1 = "\"type\": \"http://example.de/type/1.0#//Child\",";
		String t2 = "\"kind\": \"http://example.de/type/1.0#//Child2\"";
		
		String newT1 = "\"type\": \"" + uri1.toString() + "\",";
		String newT2 = "\"kind\": \"" + uri2.toString() + "\"";
		
		substituteType(t1, newT1);
		substituteType(t2, newT2);
		

		// load dynamic eobjects from json with classifier from ecore
		Resource resource = resourceSet.createResource(URI.createURI("test-data/type-different-keys.json"));

		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, testClass);
		Map<String, Object> classOptions = new HashMap<>();

		Map<String, Object> ref1Options = new HashMap<>();
		ref1Options.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "type");
		ref1Options.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");

		Map<String, Object> ref2Options = new HashMap<>();
		ref2Options.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "kind");
		ref2Options.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");

		classOptions.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(testClass.getEStructuralFeature("ref1"), ref1Options,
				testClass.getEStructuralFeature("ref2"), ref2Options));

		options.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(testClass, classOptions));
		//		options.put(CodecModuleOptions.CODEC_MODULE_TYPE_KEY, "type");
		resource.load(options);

		assertThat(resource.getContents()).hasSize(1);

		EObject loadClass = resource.getContents().get(0);


		EStructuralFeature ref1Feature = loadClass.eClass().getEStructuralFeature("ref1");
		assertThat(ref1Feature).isNotNull();
		assertThat(loadClass.eGet(ref1Feature)).isInstanceOf(child1.eClass().getClass());

		EStructuralFeature ref2Feature = loadClass.eClass().getEStructuralFeature("ref2");
		assertThat(ref2Feature).isNotNull();
		assertThat(loadClass.eGet(ref2Feature)).isInstanceOf(child2.eClass().getClass());	
		
		substituteType(newT1, t1);
		substituteType(newT2, t2);
	}

	private void substituteType(String oldValue, String newValue) throws IOException {
		List<String> fileContent = new ArrayList<String>(Files.readAllLines(Path.of("test-data/type-different-keys.json"), StandardCharsets.UTF_8));

		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).contains(oldValue)) {
				fileContent.set(i, newValue);
				break;
			}
		}

		Files.write(Path.of("test-data/temp.json"), fileContent, StandardCharsets.UTF_8);
		Files.copy(Path.of("test-data/temp.json"), Path.of("test-data/type-different-keys.json"), StandardCopyOption.REPLACE_EXISTING);
	}

	@Test
	public void testDeserializationDifferentTypeKeysFeatureLevel() throws IOException {

		// load dynamic eobjects from json with classifier from ecore
		Resource resource = resourceSet.createResource(URI.createURI(ctx.getBundle().getEntry("test-data/person-model-different-keys.json").toString()));

		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, PersonPackage.Literals.TEST_OBJECT);
		Map<String, Object> classOptions = new HashMap<>();

		Map<String, Object> ref1Options = new HashMap<>();
		ref1Options.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "type");
		ref1Options.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");

		Map<String, Object> ref2Options = new HashMap<>();
		ref2Options.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "kind");
		ref2Options.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");

		classOptions.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(PersonPackage.Literals.TEST_OBJECT__REF1, ref1Options,
				PersonPackage.Literals.TEST_OBJECT__REF2, ref2Options));

		options.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(PersonPackage.Literals.TEST_OBJECT, classOptions));
		//		options.put(CodecModuleOptions.CODEC_MODULE_TYPE_KEY, "type");
		resource.load(options);

		assertThat(resource.getContents()).hasSize(1);

		EObject loadClass = resource.getContents().get(0);
		assertTrue(loadClass instanceof TestObject);
		TestObject testObj = (TestObject) resource.getContents().get(0);
		assertNotNull(testObj.getRef1());
		assertTrue(testObj.getRef1() instanceof Child);
		assertNotNull(testObj.getRef2());
		assertTrue(testObj.getRef2() instanceof Child2);
	}
	
	@Test
	public void testDeserializeTypeRootObjMetadataNO() throws IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		org.eclipse.fennec.codec.test.models.metadata.BusinessPerson person = org.eclipse.fennec.codec.test.models.metadata.MetadataFactory.eINSTANCE.createBusinessPerson();
		person.setKind("BusinessPerson");
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<String, Object> classOptions = new HashMap<>();
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "kind");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_MAP, Map.of("BusinessPerson", "http://www.eclipse.org/fennec/codec/testmetadata#//BusinessPerson"));
		options.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(org.eclipse.fennec.codec.test.models.metadata.MetadataPackage.Literals.BUSINESS_PERSON, classOptions, 
				org.eclipse.fennec.codec.test.models.metadata.MetadataPackage.Literals.PERSON, classOptions));
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, false);
		resource.save(options);
		
		resource.unload();
		resourceSet.getResources().clear();
		
		Resource loadRes = resourceSet.createResource(URI.createURI(personFileName));
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, org.eclipse.fennec.codec.test.models.metadata.MetadataPackage.Literals.PERSON);
		loadRes.load(options);
		
		assertFalse(loadRes.getContents().isEmpty());
		assertTrue(loadRes.getContents().get(0) instanceof org.eclipse.fennec.codec.test.models.metadata.BusinessPerson);
	}
	
	@Test
	@Disabled("We have to decide if we really want to support this!")
	public void testDeserializeTypeRootObjMetadataYES() throws IOException {

		Resource resource = resourceSet.createResource(URI.createURI(personFileName));

		org.eclipse.fennec.codec.test.models.metadata.BusinessPerson person = org.eclipse.fennec.codec.test.models.metadata.MetadataFactory.eINSTANCE.createBusinessPerson();
		person.setKind("BusinessPerson");
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<String, Object> classOptions = new HashMap<>();
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "kind");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_MAP, Map.of("BusinessPerson", "http://www.eclipse.org/fennec/codec/testmetadata#//BusinessPerson"));
		options.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(org.eclipse.fennec.codec.test.models.metadata.MetadataPackage.Literals.BUSINESS_PERSON, classOptions, 
				org.eclipse.fennec.codec.test.models.metadata.MetadataPackage.Literals.PERSON, classOptions));
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		resource.save(options);
		
		resource.unload();
		resourceSet.getResources().clear();
		
		Resource loadRes = resourceSet.createResource(URI.createURI(personFileName));
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, org.eclipse.fennec.codec.test.models.metadata.MetadataPackage.Literals.PERSON);
		loadRes.load(options);
		
		assertFalse(loadRes.getContents().isEmpty());
		assertTrue(loadRes.getContents().get(0) instanceof org.eclipse.fennec.codec.test.models.metadata.BusinessPerson);
	}
}
