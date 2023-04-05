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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.gecko.codec.CodecConstants;
import org.gecko.emf.codec.cache.EMFDescriptorCache;
import org.gecko.emf.codec.test.model.codectest.CodecTestPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 
 * @author mark
 * @since 28.03.2023
 */
@ExtendWith(MockitoExtension.class)
public class EMFCodecDescriptorTest {
	
	private EMFCodecDescriptor<ENamedElement> descriptor;
	
	@BeforeEach
	public void beforeEach() {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBasics() {
		descriptor = createAbstractMock(EMFCodecDescriptor.class, EcorePackage.eINSTANCE);
		when(descriptor.getSource()).thenReturn(null);
		assertThrows(NullPointerException.class, ()->descriptor.getNamespace());

		when(descriptor.getSource()).thenReturn(EcorePackage.eINSTANCE);
		assertEquals(EcorePackage.eINSTANCE.getNsURI() + "#/", descriptor.getNamespace());
		assertEquals(EcorePackage.eINSTANCE.getName(), descriptor.getName());
		assertEquals(EcorePackage.eINSTANCE.getName(), descriptor.getProcessedName());
		assertEquals(EcorePackage.eINSTANCE, descriptor.getSource());
		assertNotNull(descriptor.getProperties());
		assertTrue(descriptor.getProperties().isEmpty());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBasicTestModel() {
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.eINSTANCE);
		
		assertEquals(CodecTestPackage.eINSTANCE.getNsURI() + "#/", descriptor.getNamespace());
		assertEquals(CodecTestPackage.eINSTANCE.getName(), descriptor.getName());
		assertEquals("CodecTest", descriptor.getProcessedName());
		assertEquals(CodecTestPackage.eINSTANCE, descriptor.getSource());
		assertNotNull(descriptor.getProperties());
		assertTrue(descriptor.getProperties().isEmpty());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessedNameCodecAnnotation() {
		// model with just Codec annotations
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.Literals.ADDRESS);
		assertEquals(CodecTestPackage.Literals.ADDRESS.getName(), descriptor.getName());
		assertEquals("CodecAddress", descriptor.getProcessedName());
		
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.Literals.CONTACT);
		assertEquals(CodecTestPackage.Literals.CONTACT.getName(), descriptor.getName());
		assertEquals(CodecTestPackage.Literals.CONTACT.getName(), descriptor.getProcessedName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessedNameEMDAndCodecAnnotations() {
		
		// model with EMD and Codec annotations
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.Literals.PERSON__LAST_NAME);
		assertEquals(CodecTestPackage.Literals.PERSON__LAST_NAME.getName(), descriptor.getName());
		assertEquals("CodecLastName", descriptor.getProcessedName());
		
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.Literals.PERSON__FIRST_NAME);
		assertEquals(CodecTestPackage.Literals.PERSON__FIRST_NAME.getName(), descriptor.getName());
		assertEquals("EMDFirstName", descriptor.getProcessedName());
		
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.Literals.CONTACT);
		assertEquals(CodecTestPackage.Literals.CONTACT.getName(), descriptor.getName());
		assertEquals(CodecTestPackage.Literals.CONTACT.getName(), descriptor.getProcessedName());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessedNameCustomAnnotation() {
		// just custom annotation but no properties
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.Literals.PERSON__PERSON_ID);
		assertEquals(CodecTestPackage.Literals.PERSON__PERSON_ID.getName(), descriptor.getName());
		assertEquals(CodecTestPackage.Literals.PERSON__PERSON_ID.getName(), descriptor.getProcessedName());
		// custom annotation, extended metadata and codec annotations
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.Literals.PERSON__BIRTH_DATE);
		descriptor.getProperties().put(CodecConstants.ANNOTATION_PROPERTY_SOURCE_KEY, "Foo");
		assertEquals(CodecTestPackage.Literals.PERSON__BIRTH_DATE.getName(), descriptor.getName());
		assertEquals("CodecBirthDate", descriptor.getProcessedName());
		descriptor.getProperties().put(CodecConstants.ANNOTATION_PROPERTY_SOURCE_KEY, "foo");
		assertEquals("CodecBirthDate", descriptor.getProcessedName());
		descriptor.getProperties().put(CodecConstants.ANNOTATION_PROPERTY_NAME_KEY, "bar");
		assertEquals("Geburtsdatum", descriptor.getProcessedName());
		
		descriptor = createAbstractMock(EMFCodecDescriptor.class, CodecTestPackage.Literals.CONTACT);
		assertEquals(CodecTestPackage.Literals.CONTACT.getName(), descriptor.getName());
		assertEquals("Contact", descriptor.getProcessedName());
		// test with default name key
		descriptor.getProperties().put(CodecConstants.ANNOTATION_PROPERTY_SOURCE_KEY, "foo");
		assertEquals("CustomContact", descriptor.getProcessedName());
	}
	
	private <T> T createAbstractMock(Class<T> mockClass, ENamedElement element) {
		return Mockito.mock(mockClass, 
				Mockito.withSettings().
				useConstructor(element, new HashMap<String, Object>(), new EMFDescriptorCache()).
				defaultAnswer(Mockito.CALLS_REAL_METHODS));
	}

}
