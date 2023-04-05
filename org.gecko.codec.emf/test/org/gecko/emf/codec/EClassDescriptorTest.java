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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Optional;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.codec.descriptors.ClassifierDescriptor;
import org.gecko.codec.descriptors.PackageDescriptor;
import org.gecko.emf.codec.cache.EMFDescriptorCache;
import org.gecko.emf.codec.test.model.codectest.CodecTestPackage;
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
public class EClassDescriptorTest {
	
	private EClassDescriptor descriptor;
	
	@BeforeEach
	public void beforeEach() {
	}
	
	@Test
	public void testBasics() {
		assertThrows(NullPointerException.class, ()->new EClassDescriptor(null, null, null));
		assertThrows(NullPointerException.class, ()->new EClassDescriptor(null, Collections.emptyMap(), null));
		assertThrows(NullPointerException.class, ()->new EClassDescriptor(CodecTestPackage.Literals.PERSON, Collections.emptyMap(), null));
		descriptor = new EClassDescriptor(EcorePackage.Literals.EPACKAGE, null, new EMFDescriptorCache());
		assertEquals(EcoreUtil.getURI(EcorePackage.Literals.EPACKAGE).toString(), descriptor.getNamespace());
		assertEquals(EcorePackage.Literals.EPACKAGE.getName(), descriptor.getName());
	}
	
	@Test
	public void testSimple() {
		descriptor = new EClassDescriptor(SubpackagePackage.Literals.EXAMPLE, null, new EMFDescriptorCache());
		assertFalse(descriptor.isSimpleType());
		descriptor = new EClassDescriptor(EcorePackage.Literals.EPACKAGE, null, new EMFDescriptorCache());
		assertFalse(descriptor.isSimpleType());
	}
	
	@Test
	public void testIdFeature() {
		descriptor = new EClassDescriptor(SubpackagePackage.Literals.EXAMPLE, null, new EMFDescriptorCache());
		assertTrue(descriptor.getIdFeature().isPresent());
		assertEquals("exampleId", descriptor.getIdFeature().get().getName());
		descriptor = new EClassDescriptor(CodecTestPackage.Literals.CONTACT, null, new EMFDescriptorCache());
		assertTrue(descriptor.getIdFeature().isEmpty());
	}
	
	@Test
	public void testCreateInstance() {
		descriptor = new EClassDescriptor(SubpackagePackage.Literals.EXAMPLE, null, new EMFDescriptorCache());
		assertNotNull(descriptor.createInstance());
		assertEquals("Example", descriptor.createInstance().eClass().getName());
		descriptor = new EClassDescriptor(CodecTestPackage.Literals.CONTACT, null, new EMFDescriptorCache());
		assertNotNull(descriptor.createInstance());
		assertEquals("Contact", descriptor.createInstance().eClass().getName());
	}
	
	@Test
	public void testSuperClass() {
		descriptor = new EClassDescriptor(CodecTestPackage.Literals.PERSON, null, new EMFDescriptorCache());
		assertFalse(descriptor.isSimpleType());
		assertTrue(descriptor.getSuperClassifiers().isEmpty());
		descriptor = new EClassDescriptor(CodecTestPackage.Literals.BUSINESS_PERSON, null, new EMFDescriptorCache());
		assertFalse(descriptor.isSimpleType());
		assertEquals(1, descriptor.getSuperClassifiers().size());
		assertEquals("Person", descriptor.getSuperClassifiers().get(0).getName());
		
		Optional<ClassifierDescriptor<EClassifier,EObject>> classifierDescriptor = descriptor.getCache().getClassifierDescriptor(CodecTestPackage.Literals.PERSON);
		assertTrue(classifierDescriptor.isPresent());
	}
	
	@Test
	public void testFeatuers() {
		descriptor = new EClassDescriptor(CodecTestPackage.Literals.CONTACT, null, new EMFDescriptorCache());
		assertEquals(2, descriptor.getFeatures().size());
		assertEquals("type", descriptor.getFeatures().get(1).getName());
		assertEquals("value", descriptor.getFeatures().get(0).getName());
		
		assertNotNull(descriptor.getCache().getFeatureDescriptor(CodecTestPackage.Literals.CONTACT__TYPE));
		assertEquals(descriptor.getCache().getFeatureDescriptor(CodecTestPackage.Literals.CONTACT__TYPE).get(), descriptor.getFeatures().get(1));
		assertNotNull(descriptor.getCache().getFeatureDescriptor(CodecTestPackage.Literals.CONTACT__VALUE));
		assertEquals(descriptor.getCache().getFeatureDescriptor(CodecTestPackage.Literals.CONTACT__VALUE).get(), descriptor.getFeatures().get(0));
	}
	
	@Test
	public void testPackage() {
		descriptor = new EClassDescriptor(SubpackagePackage.Literals.EXAMPLE, null, new EMFDescriptorCache());
		Optional<PackageDescriptor<EPackage,EObject>> packageDescriptor = descriptor.getCache().getPackageDescriptor(SubpackagePackage.eINSTANCE);
		assertTrue(packageDescriptor.isEmpty());
	}
	
	@Test
	public void testCacheItself() {
		descriptor = new EClassDescriptor(SubpackagePackage.Literals.EXAMPLE, null, new EMFDescriptorCache());
		Optional<ClassifierDescriptor<EClassifier, EObject>> featureDescriptor = descriptor.getCache().getClassifierDescriptor(SubpackagePackage.Literals.EXAMPLE);
		assertTrue(featureDescriptor.isPresent());
		assertEquals("Example", descriptor.getName());
	}
	
}
