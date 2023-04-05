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
package org.gecko.emf.codec;

import static org.gecko.codec.codecs.BasicCodecValue.createCodecValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.codec.CodecConstants;
import org.gecko.codec.CodecException;
import org.gecko.codec.descriptors.ClassifierDescriptor;
import org.gecko.codec.descriptors.FeatureDescriptor;
import org.gecko.codec.descriptors.PackageDescriptor;
import org.gecko.emf.codec.cache.EMFDescriptorCache;
import org.gecko.emf.codec.test.model.codectest.CodecTestPackage;
import org.gecko.emf.codec.test.model.codectest.subpackage.Example;
import org.gecko.emf.codec.test.model.codectest.subpackage.SubpackageFactory;
import org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 
 * @author mark
 * @since 28.03.2023
 */
@ExtendWith(MockitoExtension.class)
public class EFeatureDescriptorTest {
	
	private EFeatureDescriptor descriptor;
	
	@BeforeEach
	public void beforeEach() {
	}
	
	@Test
	public void testBasics() {
		assertThrows(NullPointerException.class, ()->new EFeatureDescriptor(null, null, null));
		assertThrows(NullPointerException.class, ()->new EFeatureDescriptor(null, Collections.emptyMap(), null));
		assertThrows(NullPointerException.class, ()->new EFeatureDescriptor(CodecTestPackage.Literals.PERSON__ADDRESS, Collections.emptyMap(), null));
		descriptor = new EFeatureDescriptor(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS, null, new EMFDescriptorCache());
		assertEquals(EcoreUtil.getURI(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS).toString(), descriptor.getNamespace());
		assertEquals(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS.getName(), descriptor.getName());
	}
	
	@Test
	public void testStandards() {
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, null, new EMFDescriptorCache());
		assertTrue(descriptor.isDerived());
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__TRANSIENT, null, new EMFDescriptorCache());
		assertFalse(descriptor.isDerived());
		assertTrue(descriptor.isTransient());
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__CONTAINMENT, null, new EMFDescriptorCache());
		assertFalse(descriptor.isTransient());
		assertTrue(descriptor.isContainmentReference());
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, null, new EMFDescriptorCache());
		assertFalse(descriptor.isContainmentReference());
		assertTrue(descriptor.isNullable());
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__MANDATORY, null, new EMFDescriptorCache());
		assertFalse(descriptor.isNullable());
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__MANDATORY_MULTIPLE, null, new EMFDescriptorCache());
		assertFalse(descriptor.isNullable());
		assertTrue(descriptor.isMultiValue());
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__MULTIPLE01, null, new EMFDescriptorCache());
		assertTrue(descriptor.isNullable());
		assertTrue(descriptor.isMultiValue());
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__MULTIPLE02, null, new EMFDescriptorCache());
		assertTrue(descriptor.isNullable());
		assertTrue(descriptor.isMultiValue());
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, null, new EMFDescriptorCache());
		assertTrue(descriptor.isNullable());
		assertTrue(descriptor.isIdFeature());
		assertFalse(descriptor.isMultiValue());
	}
	
	@Test
	public void testClass() {
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, null, new EMFDescriptorCache());
		assertTrue(descriptor.isDerived());
		Optional<ClassifierDescriptor<EClassifier,EObject>> classifierDescriptor = descriptor.getCache().getClassifierDescriptor(SubpackagePackage.Literals.EXAMPLE);
		assertTrue(classifierDescriptor.isEmpty());
	}
	
	@Test
	public void testPackage() {
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, null, new EMFDescriptorCache());
		assertTrue(descriptor.isDerived());
		Optional<PackageDescriptor<EPackage,EObject>> packageDescriptor = descriptor.getCache().getPackageDescriptor(SubpackagePackage.eINSTANCE);
		assertTrue(packageDescriptor.isEmpty());
	}
	
	@Test
	public void testCacheItself() {
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, null, new EMFDescriptorCache());
		assertTrue(descriptor.isDerived());
		Optional<FeatureDescriptor<EStructuralFeature, EObject>> featureDescriptor = descriptor.getCache().getFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED);
		assertTrue(featureDescriptor.isPresent());
		assertEquals("derived", descriptor.getName());
	}
	
	@Test
	public void testProjected() {
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, null, new EMFDescriptorCache());
		assertTrue(descriptor.isProjected());
		Map<String, Object> properties = new HashMap<>();
		properties.put(CodecConstants.PROJECTION_FEATURES_PROPERTY_KEY, new String[] {"test"});
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, properties, new EMFDescriptorCache());
		assertTrue(descriptor.isProjected());
		properties.put(CodecConstants.PROJECTION_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, properties, new EMFDescriptorCache());
		assertFalse(descriptor.isProjected());
		properties.put(CodecConstants.PROJECTION_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT, SubpackagePackage.Literals.EXAMPLE__DERIVED));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, properties, new EMFDescriptorCache());
		assertTrue(descriptor.isProjected());
		
		properties = new HashMap<>();
		properties.put(CodecConstants.PROJECTION_NOT_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, properties, new EMFDescriptorCache());
		assertTrue(descriptor.isProjected());
		properties = new HashMap<>();
		properties.put(CodecConstants.PROJECTION_NOT_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT));
		properties.put(CodecConstants.PROJECTION_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT, SubpackagePackage.Literals.EXAMPLE__DERIVED));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, properties, new EMFDescriptorCache());
		assertTrue(descriptor.isProjected());
		properties = new HashMap<>();
		properties.put(CodecConstants.PROJECTION_NOT_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT, SubpackagePackage.Literals.EXAMPLE__DERIVED));
		properties.put(CodecConstants.PROJECTION_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, properties, new EMFDescriptorCache());
		assertFalse(descriptor.isProjected());
		properties = new HashMap<>();
		properties.put(CodecConstants.PROJECTION_NOT_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT, SubpackagePackage.Literals.EXAMPLE__DERIVED));
		properties.put(CodecConstants.PROJECTION_FEATURES_PROPERTY_KEY, List.of(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, SubpackagePackage.Literals.EXAMPLE__TRANSIENT, SubpackagePackage.Literals.EXAMPLE__DERIVED));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__DERIVED, properties, new EMFDescriptorCache());
		assertFalse(descriptor.isProjected());
		
	}
	
	@Test
	public void testCompatibleBasic() throws CodecException {
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__EXAMPLE_ID, null, new EMFDescriptorCache());
		assertTrue(descriptor.isNullable());
		assertTrue(descriptor.isCompatible(null));
		assertTrue(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__TRANSIENT, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(42l)));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__TRANSIENT, null, new EMFDescriptorCache());
		assertFalse(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__MANDATORY_MULTIPLE, null, new EMFDescriptorCache());
		assertFalse(descriptor.isNullable());
		assertFalse(descriptor.isCompatible(null));
	}
	@Test
	public void testCompatible() throws CodecException {
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_INTEGER, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		assertFalse(descriptor.isCompatible(createCodecValue(Long.valueOf(42))));
		assertTrue(descriptor.isCompatible(createCodecValue(4)));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__BIG_INTEGER, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(4)));
		assertFalse(descriptor.isCompatible(createCodecValue(Long.valueOf(42))));
		assertFalse(descriptor.isCompatible(createCodecValue(42l)));

		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_BOOL, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Boolean.FALSE)));
		assertTrue(descriptor.isCompatible(createCodecValue(true)));
		assertFalse(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__BIG_BOOL, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Boolean.FALSE)));
		assertTrue(descriptor.isCompatible(createCodecValue(true)));
		assertFalse(descriptor.isCompatible(createCodecValue(42)));
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_CHAR, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Character.valueOf('c'))));
		assertFalse(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		assertTrue(descriptor.isCompatible(createCodecValue('c')));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__BIG_CHAR, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Character.valueOf('c'))));
		assertTrue(descriptor.isCompatible(createCodecValue('c')));
		assertFalse(descriptor.isCompatible(createCodecValue(42)));
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_LONG, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Long.valueOf(42l))));
		assertFalse(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		assertTrue(descriptor.isCompatible(createCodecValue(42l)));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__BIG_LONG, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Long.valueOf(42l))));
		assertTrue(descriptor.isCompatible(createCodecValue(42l)));
		assertFalse(descriptor.isCompatible(createCodecValue(42)));
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_BYTE, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Byte.valueOf((byte)0x42))));
		assertFalse(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		assertTrue(descriptor.isCompatible(createCodecValue((byte)0x42)));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__BIG_BYTE, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Byte.valueOf((byte)0x42))));
		assertTrue(descriptor.isCompatible(createCodecValue((byte)0x42)));
		assertFalse(descriptor.isCompatible(createCodecValue(42)));
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_SHORT, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Short.valueOf((short)42))));
		assertFalse(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		assertTrue(descriptor.isCompatible(createCodecValue((short)42)));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__BIG_SHORT, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Short.valueOf((short)42))));
		assertTrue(descriptor.isCompatible(createCodecValue((short)42)));
		assertFalse(descriptor.isCompatible(createCodecValue(42)));
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_DOUBLE, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Double.valueOf((double)42.5))));
		assertFalse(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		assertTrue(descriptor.isCompatible(createCodecValue((double)42.5)));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__BIG_DOUBLE, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Double.valueOf((double)42.5))));
		assertTrue(descriptor.isCompatible(createCodecValue((double)42.5)));
		assertFalse(descriptor.isCompatible(createCodecValue(42)));
		
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_FLOAT, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Float.valueOf((float)42.5))));
		assertTrue(descriptor.isCompatible(createCodecValue((float)42.5)));
		assertFalse(descriptor.isCompatible(createCodecValue(Integer.valueOf(42))));
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__BIG_FLOAT, null, new EMFDescriptorCache());
		assertTrue(descriptor.isCompatible(createCodecValue(Float.valueOf((float)42.5))));
		assertTrue(descriptor.isCompatible(createCodecValue((float)42.5)));
		assertFalse(descriptor.isCompatible(createCodecValue(42)));
		
	}
	
	@Test
	public void testGetValue() throws CodecException {
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_FLOAT, null, new EMFDescriptorCache());
		Example ex = SubpackageFactory.eINSTANCE.createExample();
		ex.setSmallFloat(24.5f);
		assertNotNull(descriptor.getValue(ex));
		assertEquals(24.5f, descriptor.getValue(ex).getValue());
		descriptor = new EFeatureDescriptor(CodecTestPackage.Literals.PERSON__BIRTH_DATE, null, new EMFDescriptorCache());
		assertThrows(AssertionError.class, ()-> descriptor.getValue(ex));
		assertNull(descriptor.getValue(null).getValue());
	}
	
	@Test
	public void testSetValue() throws CodecException {
		descriptor = new EFeatureDescriptor(SubpackagePackage.Literals.EXAMPLE__SMALL_FLOAT, null, new EMFDescriptorCache());
		Example ex = SubpackageFactory.eINSTANCE.createExample();
		assertEquals(0f, ex.getSmallFloat());
		descriptor.setValue(ex, createCodecValue(42.5f));
		assertEquals(42.5f, ex.getSmallFloat());
		
		descriptor = new EFeatureDescriptor(CodecTestPackage.Literals.PERSON__BIRTH_DATE, null, new EMFDescriptorCache());
		assertThrows(AssertionError.class, ()->descriptor.setValue(ex, createCodecValue(42f)));
	}
	

}
