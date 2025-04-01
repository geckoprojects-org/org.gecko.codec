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

package org.eclipse.emfcloud.jackson.tests;

import static org.eclipse.emfcloud.jackson.databind.EMFContext.Attributes.RESOURCE_SET;
import static org.eclipse.emfcloud.jackson.module.EMFModule.Feature.OPTION_USE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfcloud.jackson.junit.model.ModelFactory;
import org.eclipse.emfcloud.jackson.junit.model.User;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.eclipse.emfcloud.jackson.support.StandardExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(StandardExtension.class)
public class IdTest {

	@org.eclipse.emfcloud.jackson.support.ObjectMapper
	private ObjectMapper mapper;
	@org.eclipse.emfcloud.jackson.support.ResourceSet
	private ResourceSet resourceSet;

   @Test
   public void testWriteId(@org.eclipse.emfcloud.jackson.support.ObjectMapper(feature = OPTION_USE_ID, enabled = true) ObjectMapper idMapper) {
      JsonNode expected = mapper.createObjectNode()
         .put("eClass", "http://www.emfjson.org/jackson/model#//User")
         .put("@id", "1")
         .put("name", "Joe");

      JsonResource resource = new JsonResource(URI.createURI("test"), mapper);
      User user = ModelFactory.eINSTANCE.createUser();
      user.setName("Joe");
      resource.setID(user, "1");
      resource.getContents().add(user);

      assertEquals(expected, idMapper.valueToTree(resource));
   }

   @Test
   public void testReadId(@org.eclipse.emfcloud.jackson.support.ObjectMapper(feature = OPTION_USE_ID, enabled = true) ObjectMapper idMapper) throws JsonProcessingException {
      JsonNode data = mapper.createObjectNode()
         .put("eClass", "http://www.emfjson.org/jackson/model#//User")
         .put("@id", "1")
         .put("name", "Joe");

      JsonResource resource = idMapper
         .reader()
         .withAttribute(RESOURCE_SET, resourceSet)
         .treeToValue(data, JsonResource.class);

      User user = (User) resource.getContents().get(0);

      assertEquals("1", resource.getID(user));
   }

   @Test
   public void testReadId_WhenIdBeforeTypeField(@org.eclipse.emfcloud.jackson.support.ObjectMapper(feature = OPTION_USE_ID, enabled = true) ObjectMapper idMapper) throws JsonProcessingException {
      JsonNode data = mapper.createObjectNode()
         .put("@id", "1")
         .put("eClass", "http://www.emfjson.org/jackson/model#//User")
         .put("name", "Joe");

      JsonResource resource = idMapper
         .reader()
         .withAttribute(RESOURCE_SET, resourceSet)
         .treeToValue(data, JsonResource.class);

      User user = (User) resource.getContents().get(0);

      assertEquals("1", resource.getID(user));
   }

}
