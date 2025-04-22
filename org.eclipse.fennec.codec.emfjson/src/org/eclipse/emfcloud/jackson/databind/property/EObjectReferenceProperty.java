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

package org.eclipse.emfcloud.jackson.databind.property;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.annotations.EcoreReferenceInfo;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;

public class EObjectReferenceProperty extends EObjectProperty {

   public EObjectReferenceProperty(final EcoreReferenceInfo referenceInfo) {
      super(referenceInfo.getProperty());
   }

   @Override
   public void serialize(final EObject bean, final JsonGenerator jg, final SerializationContext provider) {
      // do nothing
   }

   @Override
   public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) {
      return null;
   }

   @Override
   public void deserializeAndSet(final JsonParser jp, final EObject current, final DeserializationContext ctxt,
      final Resource resource) {
      String value = jp.nextStringValue();
      if (value != null) {
         ((InternalEObject) current).eSetProxyURI(URI.createURI(value));
      }
   }
}
