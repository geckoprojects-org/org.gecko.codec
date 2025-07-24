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
package org.eclipse.fennec.codec.info.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.info.codecinfo.CodecInfoHolder;
import org.eclipse.fennec.codec.info.codecinfo.EClassCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.FeatureCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.IdentityInfo;
import org.eclipse.fennec.codec.info.codecinfo.InfoType;
import org.eclipse.fennec.codec.info.codecinfo.PackageCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.TypeInfo;
import org.eclipse.fennec.codec.options.CodecValueReaderConstants;
import org.eclipse.fennec.codec.options.CodecValueWriterConstants;
import org.gecko.codec.demo.model.person.PersonPackage;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
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
public class CodecModelInfoTest {
	
	@InjectService
	ServiceAware<ResourceSet> rsAware;
	
	@BeforeEach
	public void before() throws InterruptedException {
		System.out.println("");
		ResourceSet resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
	}

	
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
	public void testEClassCodecInfoCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		demoModel.getEClassifiers().forEach(ec -> {
			if(ec instanceof EClass eClass) {
				assertFalse(codecModelInfo.getCodecInfoForEClass(eClass).isEmpty());
				EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(eClass).get();
				assertEquals(eClassCodecInfo.getId(), EcoreUtil.getURI(eClass).toString());
				assertEquals(eClassCodecInfo.getClassifier(), eClass);				
				
				assertNotNull(eClassCodecInfo.getIdentityInfo());				
				assertNotNull(eClassCodecInfo.getTypeInfo());
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
		assertThat(identityInfo.getIdFeatures()).hasSize(2);
		assertEquals(identityInfo.getIdFeatures().get(0), demoModel.getPerson_Name());
		assertEquals(identityInfo.getIdFeatures().get(1), demoModel.getPerson_LastName());
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
		assertEquals("NAME", typeInfo.getTypeStrategy());
		assertEquals(CodecValueReaderConstants.READER_BY_ECLASS_NAME, typeInfo.getTypeValueReaderName());
		assertEquals(CodecValueWriterConstants.WRITER_BY_ECLASS_NAME, typeInfo.getTypeValueWriterName());
	}
	
	@Test
	public void testTypeInfoEClassCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(demoModel.getTypeKeyEClass()).get();
		assertNotNull(eClassCodecInfo);
		
		TypeInfo typeInfo = eClassCodecInfo.getTypeInfo();
		assertNotNull(typeInfo);
		assertEquals("NAME", typeInfo.getTypeStrategy());
		assertEquals(CodecValueReaderConstants.READER_BY_ECLASS_NAME, typeInfo.getTypeValueReaderName());
		assertEquals(CodecValueWriterConstants.WRITER_BY_ECLASS_NAME, typeInfo.getTypeValueWriterName());
		assertEquals("name", typeInfo.getTypeKey());
		assertThat(typeInfo.getTypeMap()).hasSize(2);
		assertTrue(typeInfo.getTypeMap().containsKey("dragino"));
		assertTrue(typeInfo.getTypeMap().containsKey("em310"));
		assertEquals("DraginoUplink", typeInfo.getTypeMap().get("dragino"));
		assertEquals("EM310Uplink", typeInfo.getTypeMap().get("em310"));
	}
	
	@Test
	public void testTypeInfoERefCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(demoModel.getSensorBook()).get();
		assertNotNull(eClassCodecInfo);
		
		FeatureCodecInfo refCodecInfo = eClassCodecInfo.getReferenceCodecInfo().stream().filter(r -> "sensors".equals(r.getKey())).findFirst().orElse(null);
		assertThat(refCodecInfo).isNotNull();
		
		TypeInfo typeInfo = refCodecInfo.getTypeInfo();
		assertNotNull(typeInfo);
		assertEquals("NAME", typeInfo.getTypeStrategy());
		assertEquals(CodecValueReaderConstants.READER_BY_ECLASS_NAME, typeInfo.getTypeValueReaderName());
		assertEquals(CodecValueWriterConstants.WRITER_BY_ECLASS_NAME, typeInfo.getTypeValueWriterName());
		assertEquals("name", typeInfo.getTypeKey());
		assertThat(typeInfo.getTypeMap()).hasSize(2);
		assertTrue(typeInfo.getTypeMap().containsKey("dragino"));
		assertTrue(typeInfo.getTypeMap().containsKey("em310"));
		assertEquals("DraginoUplink", typeInfo.getTypeMap().get("dragino"));
		assertEquals("EM310Uplink", typeInfo.getTypeMap().get("em310"));
	}
	
	@Test
	public void testReferenceInfoCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(demoModel.getPerson()).get();
		assertNotNull(eClassCodecInfo);
		
		List<FeatureCodecInfo> refInfos = eClassCodecInfo.getReferenceCodecInfo();
		assertNotNull(refInfos);
		assertThat(refInfos).hasSize(5); //containedAdd, containedAdds, nonContainedAdd, nonContainedAdds, businessAdd
	}
	
	@Test
	public void testFeatureIgnore(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		EClassCodecInfo eClassCodecInfo = codecModelInfo.getCodecInfoForEClass(demoModel.getAddress()).get();
		assertNotNull(eClassCodecInfo);
		
		List<FeatureCodecInfo> featureInfos = eClassCodecInfo.getFeatureInfo();
		assertThat(featureInfos).hasSize(3);
		for(FeatureCodecInfo fci : featureInfos) {
			if("zip".equals(fci.getFeature().getName())) {
				assertTrue(fci.isIgnore());
			}
		}
	}
	
	@Test
	public void testCodecInfoHolderCreation(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo) {
		
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		CodecInfoHolder codecInfoHolder = codecModelInfo.getCodecInfoHolderByType(InfoType.IDENTITY);
		assertNotNull(codecInfoHolder);
		assertEquals(codecInfoHolder.getInfoType(), InfoType.IDENTITY);
		assertThat(codecInfoHolder.getReaders()).hasSize(1);
		assertThat(codecInfoHolder.getWriters()).hasSize(1);
		
		codecInfoHolder = codecModelInfo.getCodecInfoHolderByType(InfoType.TYPE);
		assertNotNull(codecInfoHolder);
		assertEquals(codecInfoHolder.getInfoType(), InfoType.TYPE);
		assertThat(codecInfoHolder.getReaders()).hasSize(3);
		assertThat(codecInfoHolder.getWriters()).hasSize(3);
		
		codecInfoHolder = codecModelInfo.getCodecInfoHolderByType(InfoType.SUPER_TYPE);
		assertNotNull(codecInfoHolder);
		assertEquals(codecInfoHolder.getInfoType(), InfoType.SUPER_TYPE);
		assertThat(codecInfoHolder.getReaders()).isEmpty();
		assertThat(codecInfoHolder.getWriters()).hasSize(2);
		
		codecInfoHolder = codecModelInfo.getCodecInfoHolderByType(InfoType.REFERENCE);
		assertNotNull(codecInfoHolder);
		assertEquals(codecInfoHolder.getInfoType(), InfoType.REFERENCE);
		assertThat(codecInfoHolder.getReaders()).isEmpty();
		assertThat(codecInfoHolder.getWriters()).isEmpty();
		
		codecInfoHolder = codecModelInfo.getCodecInfoHolderByType(InfoType.ATTRIBUTE);
		assertNotNull(codecInfoHolder);
		assertThat(codecInfoHolder.getReaders()).isEmpty();
		assertThat(codecInfoHolder.getWriters()).isEmpty();
		
		codecInfoHolder = codecModelInfo.getCodecInfoHolderByType(InfoType.OPERATION);
		assertNotNull(codecInfoHolder);
		assertThat(codecInfoHolder.getReaders()).isEmpty();
		assertThat(codecInfoHolder.getWriters()).isEmpty();
		
		codecInfoHolder = codecModelInfo.getCodecInfoHolderByType(InfoType.OBJECT);
		assertNotNull(codecInfoHolder);
		assertThat(codecInfoHolder.getReaders()).isEmpty();
		assertThat(codecInfoHolder.getWriters()).isEmpty();
		
		codecInfoHolder = codecModelInfo.getCodecInfoHolderByType(InfoType.OTHER);
		assertNotNull(codecInfoHolder);
		assertThat(codecInfoHolder.getReaders()).isEmpty();
		assertThat(codecInfoHolder.getWriters()).isEmpty();
	}
}
