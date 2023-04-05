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
package org.gecko.emf.codec.helper;

import static org.gecko.codec.CodecConstants.ANNOTATION_NAMESPACE;
import static org.gecko.codec.CodecConstants.ANNOTATION_NAME_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EcorePackage;
import org.gecko.emf.codec.test.model.codectest.CodecTestPackage;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 29.03.2023
 */
public class EMFHelperTest {

	@Test
	public void testENamedElementAnnotationError() {
		assertThrows(NullPointerException.class, ()->EMFHelper.getElementCodecAnnotationNames(null));
		assertThrows(NullPointerException.class, ()->EMFHelper.getElementAnnotationValues(null, null, null));
		assertThrows(NullPointerException.class, ()->EMFHelper.getElementAnnotationValues(null, "foo", null));
		assertThrows(NullPointerException.class, ()->EMFHelper.getElementAnnotationValues(null, "foo", "bar"));
		assertThrows(NullPointerException.class, ()->EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, null, null));
		assertThrows(NullPointerException.class, ()->EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, "foo", null));
		assertThrows(NullPointerException.class, ()->EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, null, "bar"));
		assertThrows(NullPointerException.class, ()->EMFHelper.getElementCodecAnnotationName(null));

		List<String> names = EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.eINSTANCE);
		assertNotNull(names);
	}

	@Test
	public void testENamedTypeAnnotation() {
		assertNull(EMFHelper.getElementCodecAnnotationName(EcorePackage.eINSTANCE));
		assertEquals("CodecTest", EMFHelper.getElementCodecAnnotationName(CodecTestPackage.eINSTANCE));
		assertEquals("CodecPerson", EMFHelper.getElementCodecAnnotationName(CodecTestPackage.Literals.PERSON));
		assertEquals("ContactList", EMFHelper.getElementCodecAnnotationName(CodecTestPackage.Literals.PERSON__CONTACTS));
		// two annotations at Person and BusinessPerson -> use 
		assertThrows(IllegalStateException.class, ()->EMFHelper.getElementCodecAnnotationName(CodecTestPackage.Literals.BUSINESS_PERSON));
	}

	@Test
	public void testENamedTypeAnnotationNames() {
		assertTrue(EMFHelper.getElementCodecAnnotationNames(EcorePackage.eINSTANCE).isEmpty());

		assertEquals(1, EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.eINSTANCE).size());
		assertEquals("CodecTest", EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.eINSTANCE).get(0));
		assertEquals(1, EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.PERSON).size());
		assertEquals("CodecPerson", EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.PERSON).get(0));
		assertEquals(1, EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.PERSON__CONTACTS).size());
		assertEquals("ContactList", EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.PERSON__CONTACTS).get(0));
		// two annotations at Person and BusinessPerson -> use 
		assertEquals(2, EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.BUSINESS_PERSON).size());
		assertTrue(EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.BUSINESS_PERSON).contains("CodecBusinessPerson"));
		assertTrue(EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.BUSINESS_PERSON).contains("CodecPerson"));
	}

	@Test
	public void testENamedElementAnnotationValue() {
		assertTrue(EMFHelper.getElementAnnotationValues(EcorePackage.eINSTANCE, "Foo", "Bar").isEmpty());
		assertTrue(EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, "Foo", "Bar").isEmpty());
		assertFalse(EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, "foo", "bar").isEmpty());
		assertTrue(EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, ANNOTATION_NAMESPACE, "Bar").isEmpty());
		assertEquals(1, EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, ANNOTATION_NAMESPACE, ANNOTATION_NAME_KEY).size());
		assertEquals("CodecTest", EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, ANNOTATION_NAMESPACE, ANNOTATION_NAME_KEY).get(0));
		assertEquals(1, EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, "Version", "value").size());
		assertEquals("1.0", EMFHelper.getElementAnnotationValues(CodecTestPackage.eINSTANCE, "Version", "value").get(0));
	}

	@Test
	public void testEClassCodecAnnotationNames() {
		// two annotations at Person and BusinessPerson -> use 
		assertEquals(1, EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.PERSON).size());
		assertTrue(EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.PERSON).contains("CodecPerson"));
		assertEquals(2, EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.BUSINESS_PERSON).size());
		assertTrue(EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.BUSINESS_PERSON).contains("CodecBusinessPerson"));
		assertTrue(EMFHelper.getElementCodecAnnotationNames(CodecTestPackage.Literals.BUSINESS_PERSON).contains("CodecPerson"));
		assertEquals("CodecTest", EMFHelper.getElementCodecAnnotationName(CodecTestPackage.eINSTANCE));
		assertEquals("CodecPerson", EMFHelper.getElementCodecAnnotationName(CodecTestPackage.Literals.PERSON));
		assertEquals("ContactList", EMFHelper.getElementCodecAnnotationName(CodecTestPackage.Literals.PERSON__CONTACTS));
	}
	
	@Test
	public void testEClassEMDAnnotationNames() {
		// two annotations at Person and BusinessPerson -> use 
		assertTrue(EMFHelper.getElementEMDAnnotationNames(CodecTestPackage.Literals.PERSON).isEmpty());
		assertEquals(1, EMFHelper.getElementEMDAnnotationNames(CodecTestPackage.Literals.BUSINESS_PERSON).size());
		assertTrue(EMFHelper.getElementEMDAnnotationNames(CodecTestPackage.Literals.BUSINESS_PERSON).contains("EMDBusinessPerson"));
		assertEquals("EMDFirstName", EMFHelper.getElementEMDAnnotationName(CodecTestPackage.Literals.PERSON__FIRST_NAME));
		assertNull(EMFHelper.getElementEMDAnnotationName(CodecTestPackage.Literals.PERSON__PERSON_ID));
	}

}
