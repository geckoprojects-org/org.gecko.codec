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
package org.eclipse.emfcloud.jackson.databind.deser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfcloud.jackson.annotations.EcoreReferenceInfo;
import org.eclipse.emfcloud.jackson.annotations.EcoreTypeInfo;
import org.eclipse.emfcloud.jackson.databind.EMFContext;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class EcoreReferenceDeserializer extends ValueDeserializer<ReferenceEntry> {

   private final EcoreReferenceInfo info;
   private final EcoreTypeInfo typeInfo;

   public EcoreReferenceDeserializer(final EcoreReferenceInfo info, final EcoreTypeInfo typeInfo) {
      this.typeInfo = typeInfo;
      this.info = info;
   }

   @Override
   public ReferenceEntry deserialize(final JsonParser jp, final DeserializationContext ctxt) {
      EObject parent = EMFContext.getParent(ctxt);
      EReference reference = EMFContext.getReference(ctxt);

      String id = null;
      String type = null;

      while (jp.nextToken() != JsonToken.END_OBJECT) {
         final String field = jp.currentName();

         if (field.equalsIgnoreCase(info.getProperty())) {
            id = jp.nextStringValue();
         } else if (field.equalsIgnoreCase(typeInfo.getProperty())) {
            type = jp.nextStringValue();
         }
      }

      return id != null ? new ReferenceEntry.Base(parent, reference, id, type) : null;
   }

}
