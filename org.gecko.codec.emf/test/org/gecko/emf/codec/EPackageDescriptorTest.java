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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.gecko.codec.descriptors.PackageDescriptor;
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
public class EPackageDescriptorTest {
	
	private EPackageDescriptor descriptor;
	
	@BeforeEach
	public void beforeEach() {
	}
	
	@Test
	public void testBasics() {
		assertThrows(NullPointerException.class, ()->new EPackageDescriptor(null, null));
		assertThrows(NullPointerException.class, ()->new EPackageDescriptor(null, Collections.emptyMap()));
		descriptor = new EPackageDescriptor(EcorePackage.eINSTANCE, null);
		assertEquals(EcorePackage.eINSTANCE.getNsURI(), descriptor.getNamespace());
		assertEquals(EcorePackage.eINSTANCE.getName(), descriptor.getName());
		assertNull(descriptor.getParent());
		assertTrue(descriptor.getSubPackages().isEmpty());
	}
	
	@Test
	public void testParent() {
		descriptor = new EPackageDescriptor(EcorePackage.eINSTANCE, null);
		assertNull(descriptor.getParent());
		descriptor = new EPackageDescriptor(SubpackagePackage.eINSTANCE, null);
		assertNotNull(descriptor.getParent());
		assertEquals(CodecTestPackage.eINSTANCE, descriptor.getParent().getSource());
	}
	
	@Test
	public void testSubPackage() {
		descriptor = new EPackageDescriptor(EcorePackage.eINSTANCE, null);
		assertTrue(descriptor.getSubPackages().isEmpty());
		descriptor = new EPackageDescriptor(SubpackagePackage.eINSTANCE, null);
		assertTrue(descriptor.getSubPackages().isEmpty());
		descriptor = new EPackageDescriptor(CodecTestPackage.eINSTANCE, null);
		assertEquals(2, descriptor.getSubPackages().size());
	}
	
	@Test
	public void testClassifiers() {
		descriptor = new EPackageDescriptor(SubpackagePackage.eINSTANCE, null);
		assertEquals(1, descriptor.getClassifiers().size());
		assertEquals("Example", descriptor.getClassifiers().get(0).getName());
		
		assertNotNull(descriptor.getCache().getClassifierDescriptor(SubpackagePackage.Literals.EXAMPLE));
		assertEquals(descriptor.getCache().getClassifierDescriptor(SubpackagePackage.Literals.EXAMPLE).get(), descriptor.getClassifiers().get(0));
	}
	
	@Test
	public void testCacheItself() {
		descriptor = new EPackageDescriptor(SubpackagePackage.eINSTANCE, null);
		Optional<PackageDescriptor<EPackage,EObject>> packageDescriptor = descriptor.getCache().getPackageDescriptor(SubpackagePackage.eINSTANCE);
		assertTrue(packageDescriptor.isPresent());
		assertEquals("subpackage", descriptor.getName());
	}
	

}
