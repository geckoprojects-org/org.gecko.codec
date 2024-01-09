/*******************************************************************************
 * Copyright (c) 2019-2021 Guillaume Hillairet and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *******************************************************************************/

package org.eclipse.emfcloud.jackson.tests.dynamic;

import static org.eclipse.emfcloud.jackson.databind.EMFContext.Attributes.RESOURCE_SET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.support.DynamicExtension;
import org.eclipse.emfcloud.jackson.utils.EObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(DynamicExtension.class)
public class DynamicContainmentTest {

	@org.eclipse.emfcloud.jackson.support.ObjectMapper
	private ObjectMapper mapper;
	@org.eclipse.emfcloud.jackson.support.ResourceSet
	private ResourceSet resourceSet;

   @Test
   public void testSaveContainmentWithOpposite() {
      JsonNode expected = mapper.createObjectNode()
         .put("eClass", "http://emfjson/dynamic/model#//A")
         .set("containB", mapper.createObjectNode());

      EClass classA = (EClass) resourceSet.getEObject(URI.createURI("http://emfjson/dynamic/model#//A"), true);
      EClass classB = (EClass) resourceSet.getEObject(URI.createURI("http://emfjson/dynamic/model#//B"), true);

      EObject a1 = EcoreUtil.create(classA);
      EObject b1 = EcoreUtil.create(classB);
      EObjects.setOrAdd(b1, (EReference) classA.getEStructuralFeature("parent"), a1);

      assertEquals(expected, mapper.valueToTree(a1));
   }

   @Test
   public void testLoadContainmentWithOpposite() throws JsonProcessingException {
      JsonNode data = mapper.createObjectNode()
         .put("eClass", "http://emfjson/dynamic/model#//A")
         .put("someKind", "e1")
         .set("containB", mapper.createObjectNode()
            .put("eClass", "http://emfjson/dynamic/model#//B")
            .put("someKind", "e1"));

      EObject a1 = mapper
         .reader()
         .withAttribute(RESOURCE_SET, resourceSet)
         .treeToValue(data, EObject.class);

      EObject b1 = (EObject) a1.eGet(a1.eClass().getEStructuralFeature("containB"));

      assertNotNull(b1);
      assertSame(a1, b1.eGet(b1.eClass().getEStructuralFeature("parent")));
   }

}
