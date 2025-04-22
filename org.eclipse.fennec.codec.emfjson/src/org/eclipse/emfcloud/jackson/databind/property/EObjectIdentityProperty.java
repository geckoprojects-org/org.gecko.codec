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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.annotations.EcoreIdentityInfo;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.eclipse.emfcloud.jackson.utils.ValueReader;
import org.eclipse.emfcloud.jackson.utils.ValueWriter;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;

public class EObjectIdentityProperty extends EObjectProperty {

   private final ValueReader<Object, String> valueReader;
   private final ValueWriter<EObject, Object> valueWriter;

   public EObjectIdentityProperty(final EcoreIdentityInfo info) {
      super(info.getProperty());

      this.valueReader = info.getValueReader();
      this.valueWriter = info.getValueWriter();
   }

   @Override
   public void serialize(final EObject bean, final JsonGenerator jg, final SerializationContext provider) {
      jg.writePOJOProperty(getFieldName(), valueWriter.writeValue(bean, provider));
   }

   @Override
   public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) {
      return null;
   }

   @Override
   public void deserializeAndSet(final JsonParser jp, final EObject current, final DeserializationContext ctxt,
      final Resource resource) {
      if (jp.currentToken() == JsonToken.PROPERTY_NAME) {
         jp.nextToken();
      }

      Object value;
      switch (jp.currentToken()) {
         case VALUE_STRING:
            value = jp.getValueAsString();
            break;
         case VALUE_NUMBER_INT:
            value = jp.getValueAsInt();
            break;
         case VALUE_NUMBER_FLOAT:
            value = jp.getValueAsLong();
            break;
         default:
            value = null;
      }

      if (value != null) {
         String id = valueReader.readValue(value, ctxt);
         if (resource instanceof JsonResource && id != null) {
            ((JsonResource) resource).setID(current, id);
         }
      }
   }

}
