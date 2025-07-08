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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
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
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.constants.CodecModuleOptions;
import org.eclipse.fennec.codec.constants.CodecResourceOptions;
import org.eclipse.fennec.codec.introspectors.DynamicTypeInfoIntrospector;
import org.eclipse.fennec.codec.introspectors.FlexibleEClassTypeIdResolver;
import org.gecko.codec.demo.model.person.Parent;
import org.gecko.codec.demo.model.person.Parent2;
import org.gecko.codec.demo.model.person.PersonPackage;
import org.gecko.codec.demo.model.person.TestObject;
import org.gecko.codec.demo.model.person.impl.TestObjectImpl;
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
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.NamedType;

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
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
	}
	
	@AfterEach() 
	@Override
	public void afterEach() throws IOException {
		super.afterEach();
	}
	

	@Test
	public void testDeserializationTypeAsFeature() throws IOException {

		// load ecore
		Resource ecoreResource = resourceSet.createResource(URI.createURI(ctx.getBundle().getEntry("test-data/type-as-feature.ecore").toString()));
		ecoreResource.load(null);
		EPackage epackage = (EPackage) ecoreResource.getContents().get(0);
		EClass childClass = (EClass) epackage.getEClassifier("Child");
		
		// load dynamic eobjects from json with classifier from ecore
		Resource resource = resourceSet.createResource(URI.createURI(ctx.getBundle().getEntry("test-data/type-as-feature.json").toString()));

		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, childClass);
		options.put(CodecModuleOptions.CODEC_MODULE_TYPE_KEY, "type");
		options.put(CodecModuleOptions.CODEC_MODULE_DESERIALIZE_TYPE, true);
		resource.load(options);

		assertThat(resource.getContents()).hasSize(1);

		EObject loadClass = resource.getContents().get(0);
		
		EStructuralFeature nameFeature = loadClass.eClass().getEStructuralFeature("name");
		assertThat(nameFeature).isNotNull();
		assertThat(loadClass.eGet(nameFeature)).isEqualTo("test");
		
		
		EStructuralFeature typeFeature = loadClass.eClass().getEStructuralFeature("type");
		assertThat(typeFeature).isNotNull();
		assertThat(loadClass.eGet(typeFeature)).isNotNull();		
	}

	@Test
	public void testDeserializationDifferentTypeKeys() throws IOException {

		
		Resource resource = resourceSet.createResource(URI.createURI(ctx.getBundle().getEntry("test-data/type-different-keys.json").toString()));

		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, PersonPackage.eINSTANCE.getTestObject());
		options.put(CodecModuleOptions.CODEC_MODULE_TYPE_KEY, "type");
		options.put(CodecModuleOptions.CODEC_MODULE_NAMED_TYPES, List.of(new NamedType(PersonPackage.eINSTANCE.getChild().getInstanceClass(), "Child"), 
				new NamedType(PersonPackage.eINSTANCE.getChild2().getInstanceClass(), "Child2")));
		options.put(CodecModuleOptions.CODEC_MODULE_TYPE_KEYS, Map.of(PersonPackage.eINSTANCE.getParent().getInstanceClass(), "type", PersonPackage.eINSTANCE.getParent2().getInstanceClass(), "kind"));
		resource.load(options);

		assertThat(resource.getContents()).hasSize(1);

		EObject loadClass = resource.getContents().get(0);
		
		EStructuralFeature ref1Feature = loadClass.eClass().getEStructuralFeature("ref1");
		assertThat(ref1Feature).isNotNull();
		assertThat(loadClass.eGet(ref1Feature)).isInstanceOf(PersonPackage.eINSTANCE.getChild().getInstanceClass());
		
		EStructuralFeature ref2Feature = loadClass.eClass().getEStructuralFeature("ref2");
		assertThat(ref2Feature).isNotNull();
		assertThat(loadClass.eGet(ref2Feature)).isInstanceOf(PersonPackage.eINSTANCE.getChild2().getInstanceClass());	
	}
	
	@Test
	public void test() throws IOException {
		
		ObjectMapper mapper = JsonMapper.builder()
			    .annotationIntrospector(new DynamicTypeInfoIntrospector(
			        Parent.class,                   // base type to apply to
			        FlexibleEClassTypeIdResolver.class,  // your TypeIdResolver
			        "type"                           // name of the property to store the type
			    )).annotationIntrospector(new DynamicTypeInfoIntrospector(
				        Parent2.class,                   // base type to apply to
				        FlexibleEClassTypeIdResolver.class,  // your TypeIdResolver
				        "kind"                           // name of the property to store the type
				    ))
			    .registerSubtypes(new NamedType(PersonPackage.eINSTANCE.getChild().getInstanceClass(), "Child")).			    
			    registerSubtypes(new NamedType(PersonPackage.eINSTANCE.getChild2().getInstanceClass(), "Child2")).
			    build();
		TestObject loadClass = mapper.readValue(new File("test-data/type-different-keys.json"), TestObjectImpl.class);
		
		EStructuralFeature ref1Feature = loadClass.eClass().getEStructuralFeature("ref1");
		assertThat(ref1Feature).isNotNull();
		assertThat(loadClass.eGet(ref1Feature)).isInstanceOf(PersonPackage.eINSTANCE.getChild().getInstanceClass());
		
		EStructuralFeature ref2Feature = loadClass.eClass().getEStructuralFeature("ref2");
		assertThat(ref2Feature).isNotNull();
		assertThat(loadClass.eGet(ref2Feature)).isInstanceOf(PersonPackage.eINSTANCE.getChild2().getInstanceClass());	
	
	}
}
