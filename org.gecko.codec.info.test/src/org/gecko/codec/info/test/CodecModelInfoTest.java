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

import java.io.IOException;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonFactory;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
import org.gecko.codec.info.codecinfo.ReferenceInfo;
import org.gecko.codec.info.codecinfo.TypeInfo;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.Scalar;
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
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(name = "mongoClient", location = "?", factoryPid = "MongoClientProvider", properties = {
		@Property(key = "client_id", value = "test"), @Property(key = "uri", value = "mongodb://localhost:27017") })
@WithFactoryConfiguration(name = "mongoDatabase", location = "?", factoryPid = "MongoDatabaseProvider", properties = {
		@Property(key = "alias", value = "TestDB"), @Property(key = "database", value = "test") })
public class CodecModelInfoTest {
	
//	  private ServiceRegistration<EMFRepository> repoRegistration;
	  
//	  @BeforeEach
//	  public void before(@InjectBundleContext BundleContext ctx, @Mock
//	                     EMFRepository repoMock) {
//	                       repoRegistration = ctx.registerService(EMFRepository.class, new PrototypeServiceFactory<EMFRepository>()
//	                       {
//
//	                         @Override
//	                         public EMFRepository getService(Bundle bundle,
//	                                                         ServiceRegistration<EMFRepository> registration)
//	                         {
//	                           return repoMock;
//	                         }
//
//	                         @Override
//	                         public void ungetService(Bundle bundle,
//	                                                  ServiceRegistration<EMFRepository> registration,
//	                                                  EMFRepository service)
//	                         {
//	                         }
//	                       }, Dictionaries.dictionaryOf(Constants.SERVICE_RANKING, Integer.valueOf(1000)));
//	  }
//
//	  @AfterEach
//	  public void after() {
//	    repoRegistration.unregister();
//	  }
	
//	 @Test
//	  public void testLoadGroup(@InjectService EMFRepository repo, @InjectService GroupsService groupService, @InjectService ResourceSet rs) {
//	    assertNotNull(repo);
//	    assertNotNull(groupService);
//	    assertThrows(NullPointerException.class, ()->groupService.loadGroup(null));
//	    verify(repo, never()).getEObject(any(EClass.class), any(Object.class));
//
//	    Group g = groupService.loadGroup("test");
//	    assertNull(g);
//	    verify(repo, times(1)).getEObject(any(EClass.class), eq("test"));
//
//	    reset(repo);
//	    Group dbg = createGroup(rs, "test");
//	    dbg.setId("myGroup");
//	    when(repo.getEObject(RuntimePackage.Literals.GROUP, "test")).thenReturn(dbg);
//	    g = groupService.loadGroup("test");
//	    assertNotNull(g);
//	    assertEquals(dbg.getId(), g.getId());
//	    verify(repo, times(1)).getEObject(eq(RuntimePackage.Literals.GROUP), eq("test"));
//	  }
	
	@Test
	public void testPackageCodecInfoCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		Optional<PackageCodecInfo> packageCodecInfoOpt = codecModelInfo.getCodecInfoForPackage(PersonPackage.eNS_URI);
		assertFalse(packageCodecInfoOpt.isEmpty());
		
		PackageCodecInfo packageCodecInfo = packageCodecInfoOpt.get();
		assertEquals(packageCodecInfo.getId(), PersonPackage.eNS_URI);
		assertEquals(packageCodecInfo.getEPackage(), demoModel);
	}
	
	@Test
	public void testElassCodecInfoCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		demoModel.getEClassifiers().forEach(ec -> {
			if(ec instanceof EClass eClass) {
				assertFalse(codecModelInfo.getCodecInfoForEClass(eClass).isEmpty());
				EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(eClass).get();
				assertEquals(eClassCodecInfo.getId(), eClass.getInstanceClassName());
				assertEquals(eClassCodecInfo.getClassifier(), eClass);				
				
				assertNotNull(eClassCodecInfo.getIdentityInfo());
				assertEquals(eClassCodecInfo.getIdentityInfo().getType(), InfoType.IDENTITY);
				
				assertNotNull(eClassCodecInfo.getTypeInfo());
				assertEquals(eClassCodecInfo.getTypeInfo().getType(), InfoType.TYPE);
				
				assertNotNull(eClassCodecInfo.getReferenceInfo());
				assertEquals(eClassCodecInfo.getReferenceInfo().getType(), InfoType.REFERENCE);
			}
		});
	}
	
	@Test
	public void testIdentityInfoCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(demoModel.getPerson()).get();
		assertNotNull(eClassCodecInfo);
		
		IdentityInfo identityInfo = eClassCodecInfo.getIdentityInfo();
		assertThat(identityInfo.getFeatures()).hasSize(2);
		assertEquals(identityInfo.getFeatures().get(0), demoModel.getPerson_Name());
		assertEquals(identityInfo.getFeatures().get(1), demoModel.getPerson_LastName());
		assertEquals("DEFAULT_ID_READER", identityInfo.getValueReaderName());
		assertEquals( "DEFAULT_ID_WRITER", identityInfo.getValueWriterName());
		assertEquals("COMBINED", identityInfo.getIdStrategy());
		assertEquals( "-", identityInfo.getIdSeparator());
	}

	@Test
	public void testTypeInfoCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(demoModel.getPerson()).get();
		assertNotNull(eClassCodecInfo);
		
		TypeInfo typeInfo = eClassCodecInfo.getTypeInfo();
		assertNotNull(typeInfo);
		assertEquals(typeInfo.getValueReaderName(), "DEFAULT_ECLASS_READER");
		assertEquals(typeInfo.getValueWriterName(), "URI_WRITER");
	}
	
	@Test
	public void testReferenceInfoCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(demoModel.getPerson()).get();
		assertNotNull(eClassCodecInfo);
		
		ReferenceInfo refInfo = eClassCodecInfo.getReferenceInfo();
		assertNotNull(refInfo);
		assertEquals(refInfo.getValueReaderName(), "DEFAULT_ECLASS_READER");
		assertEquals(refInfo.getValueWriterName(), "URIS_WRITER");
		assertThat(refInfo.getFeatures()).isNotEmpty();
		assertThat(refInfo.getFeatures()).hasSize(1);
		assertThat(refInfo.getFeatures()).contains(demoModel.getPerson_Address());
	}
	
	@Test
	public void testCodecInfoHolderCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		Optional<CodecInfoHolder> codecInfoHolderOpt = codecModelInfo.getCodecInfoHolderByType(InfoType.IDENTITY);
		assertFalse(codecInfoHolderOpt.isEmpty());
		
		CodecInfoHolder codecInfoHolder = codecInfoHolderOpt.get();
		assertEquals(codecInfoHolder.getInfoType(), InfoType.IDENTITY);
		assertThat(codecInfoHolder.getReaders()).hasSize(1);
		assertThat(codecInfoHolder.getWriters()).hasSize(2);
		
		codecInfoHolderOpt = codecModelInfo.getCodecInfoHolderByType(InfoType.TYPE);
		assertFalse(codecInfoHolderOpt.isEmpty());
		
		codecInfoHolder = codecInfoHolderOpt.get();
		assertEquals(codecInfoHolder.getInfoType(), InfoType.TYPE);
		assertThat(codecInfoHolder.getReaders()).hasSize(3);
		assertThat(codecInfoHolder.getWriters()).hasSize(3);
		
		codecInfoHolderOpt = codecModelInfo.getCodecInfoHolderByType(InfoType.REFERENCE);
		assertFalse(codecInfoHolderOpt.isEmpty());
		
		codecInfoHolder = codecInfoHolderOpt.get();
		assertEquals(codecInfoHolder.getInfoType(), InfoType.REFERENCE);
		assertThat(codecInfoHolder.getReaders()).hasSize(1);
		assertThat(codecInfoHolder.getWriters()).hasSize(1);
	}
	
	@InjectService(cardinality = 0, filter = "("+ EMFNamespaces.EMF_MODEL_NAME + "=person)")
	ServiceAware<ResourceSet> rsAware;
	
	@WithFactoryConfiguration(factoryPid = "CodecModule", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="MONGO"), 
			@Property(key = "useId", value="true", scalar = Scalar.Boolean),
			@Property(key = "idOnTop", value="true", scalar = Scalar.Boolean)
	})
	@WithFactoryConfiguration(factoryPid = "MongoRF", location = "?", name = "testRF", properties = {
			@Property(key = "jsonFactory.target", value="(codecType=MONGO)"), 
			@Property(key = "module.target", value="(codecType=MONGO)"),
			@Property(key = EMFNamespaces.EMF_CONFIGURATOR_NAME, value="myMongo"),
			@Property(key = EMFNamespaces.EMF_MODEL_PROTOCOL, value="mongodb")
	})
	@Test
	public void testCodecDemo(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo
//			@InjectService(timeout = 2000l, filter = "(component.name=MONGORF)" ) Resource.Factory resFactory
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		ResourceSet resourceSet = (ResourceSet) rsAware.waitForService(2000l);
		assertNotNull(resourceSet.getPackageRegistry().getEPackage(PersonPackage.eNS_URI));
		
		Resource resource = resourceSet.createResource(URI.createURI("mongodb://localhost:27017/test/Person/"));
		
		Person person = PersonFactory.eINSTANCE.createPerson();
		person.setName("Ilenia");
		person.setLastName("Salvadori");
		
		resource.getContents().add(person);
		resource.save(null);
	}
	
	
}
