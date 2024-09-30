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
package org.gecko.codec.info.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonFactory;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.jackson.CodecFactoryConfigurator;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.resource.CodecJsonResource;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;

//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;

/**
 * See documentation here: 
 * 	https://github.com/osgi/osgi-test
 * 	https://github.com/osgi/osgi-test/wiki
 * Examples: https://github.com/osgi/osgi-test/tree/main/examples
 */
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
public class CodecModelInfoOverwriteTest {

	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteIdStrategy(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteIdOrder(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteIdSeparator(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteIgnoreFeature(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteTypeInclude(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
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
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteTypeUse(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
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
	
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteWriter(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		CodecValueWriter<Date, Object> TEST_WRITER = new CodecValueWriter<>() {
			@Override
			public String getName() {
				return "TEST_WRITER";
			}

			@Override
			public Object writeValue(Date value, SerializerProvider provider) {
				return "test.".concat(value.toString());
			}
		};
		personOptions.put(CodecAnnotations.CODEC_VALUE_WRITERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_BirthDate(), TEST_WRITER));
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
		assertEquals("TEST_WRITER", featureInfo.getValueWriterName());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteReader(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		CodecValueReader<String, EObject> TEST_READER = new CodecValueReader<>() {
			@Override
			public String getName() {
				return "TEST_READER";
			}

			@Override
			public EObject readValue(String value, DeserializationContext ctx) {
				return null;
			}
		};
		personOptions.put(CodecAnnotations.CODEC_VALUE_READERS_MAP, Map.of(PersonPackage.eINSTANCE.getPerson_BirthDate(), TEST_READER));
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
		assertEquals("TEST_READER", featureInfo.getValueReaderName());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactoryConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "ObjectMapperConfigurator", location = "?", name = "test", properties = {
			@Property(key = "type", value="json")
	})
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModelInfoOverwriteIdWriter(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator,
			@InjectService(timeout = 2000l) CodecFactoryConfigurator factoryConfigurator,
			@InjectService(timeout = 2000l) ObjectMapperConfigurator objMapperConfigurator
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		assertNotNull(codecModuleConfigurator);
		assertNotNull(factoryConfigurator);
		assertNotNull(objMapperConfigurator);
	
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
		
		Person person = getTestPerson();		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> personOptions = new HashMap<>();
		CodecValueWriter<Object, ?> TEST_WRITER = new CodecValueWriter<>() {
			@Override
			public String getName() {
				return "TEST_ID_WRITER";
			}

			@Override
			public Object writeValue(Object value, SerializerProvider provider) {
				return "test.".concat(value.toString());
			}
		};
		personOptions.put(CodecAnnotations.CODEC_ID_VALUE_WRITER, TEST_WRITER);
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
		assertEquals("TEST_ID_WRITER", featureInfo.getValueWriterName());
	}
	
	
	private Person getTestPerson() {
		Person person = PersonFactory.eINSTANCE.createPerson();
		person.setId(UUID.randomUUID().toString());
		person.setName("John");
		person.setLastName("Doe");
		person.setBirthDate(java.util.Date.from(                     // Convert from modern java.time class to troublesome old legacy class.  DO NOT DO THIS unless you must, to inter operate with old code not yet updated for java.time.
			    LocalDate.of(1990, 6, 20)                        // `LocalDate` class represents a date-only, without time-of-day and without time zone nor offset-from-UTC. 
			    .atStartOfDay(                       // Let java.time determine the first moment of the day on that date in that zone. Never assume the day starts at 00:00:00.
			        ZoneId.of( "Europe/Berlin" )  // Specify time zone using proper name in `continent/region` format, never 3-4 letter pseudo-zones such as “PST”, “CST”, “IST”. 
			    )                                    // Produce a `ZonedDateTime` object. 
			    .toInstant()                         // Extract an `Instant` object, a moment always in UTC.
			));
		Address address = PersonFactory.eINSTANCE.createAddress();
		address.setId(UUID.randomUUID().toString());
		address.setStreet("Camsdorfer Str.");
		address.setZip("07749");
		person.setAddress(address);
		address = PersonFactory.eINSTANCE.createAddress();
		address.setId(UUID.randomUUID().toString());
		address.setStreet("Via Oregne");
		address.setZip("32037");
		person.setNonContainedAdd(address);
		return person;
	}
	
}
