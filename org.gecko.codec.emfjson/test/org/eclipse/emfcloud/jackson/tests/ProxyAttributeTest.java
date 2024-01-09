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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emfcloud.jackson.junit.model.ModelPackage;
import org.eclipse.emfcloud.jackson.junit.model.Node;
import org.eclipse.emfcloud.jackson.junit.model.User;
import org.eclipse.emfcloud.jackson.support.StandardExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(StandardExtension.class)
public class ProxyAttributeTest {

	@org.eclipse.emfcloud.jackson.support.ResourceSet
	private ResourceSet resourceSet;

   @Test
   public void testOptionProxyAttributesWithID() throws IOException {
      Resource resource = resourceSet.getResource(URI.createURI("test-data/tests/test-proxy-1.json"), true);

      User u1 = (User) resource.getContents().get(0);
      assertNotNull(u1);
      assertFalse(u1.eIsProxy());

      assertEquals(1, u1.getFriends().size());

      User u2 = u1.getFriends().get(0);
      assertFalse(u2.eIsProxy());

      assertEquals("2", u2.getUserId());
      assertEquals("Pierre", u2.getName());

      assertNotNull(u1.getUniqueFriend());
      assertFalse(u1.getUniqueFriend().eIsProxy());

      assertEquals("3", u1.getUniqueFriend().getUserId());
      assertEquals("Paul", u1.getUniqueFriend().getName());
   }

   @Test
   @SuppressWarnings("unchecked")
   public void testOptionProxyAttributes() throws IOException {
      // options.put(EMFJs.OPTION_PROXY_ATTRIBUTES, true);

      Resource resource = resourceSet.getResource(URI.createURI("test-data/tests/test-proxy-3.json"), true);
      // resource.load(options);

      Node node = (Node) resource.getContents().get(0);
      assertNotNull(node);

      InternalEList<Node> proxies = (InternalEList<Node>) node.eGet(ModelPackage.Literals.NODE__MANY_REF, false);
      assertEquals(3, proxies.size());

      assertFalse(proxies.basicGet(0).eIsProxy());
      assertFalse(proxies.basicGet(1).eIsProxy());
      assertFalse(proxies.basicGet(2).eIsProxy());

      assertEquals("2", proxies.basicGet(0).getLabel());
      assertEquals("21", proxies.basicGet(1).getLabel());
      assertEquals("3", proxies.basicGet(2).getLabel());

      assertNotNull(node.getSource());

      assertFalse(node.getSource().eIsProxy());
      assertEquals("2121", node.getSource().getLabel());

      Node node2 = (Node) resource.getContents().get(1);
      assertNotNull(node2);

      InternalEList<Node> proxies2 = (InternalEList<Node>) node2.eGet(ModelPackage.Literals.NODE__MANY_REF, false);
      assertEquals(2, proxies2.size());

      assertFalse(proxies2.basicGet(0).eIsProxy());
      assertFalse(proxies2.basicGet(1).eIsProxy());

      assertEquals("311", proxies2.basicGet(0).getLabel());
      assertEquals("3112", proxies2.basicGet(1).getLabel());
   }

}
