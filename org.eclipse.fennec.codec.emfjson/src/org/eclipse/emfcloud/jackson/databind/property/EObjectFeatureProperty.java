/*******************************************************************************
 * Copyright (c) 2019-2022 Guillaume Hillairet and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *******************************************************************************/

package org.eclipse.emfcloud.jackson.databind.property;

import static org.eclipse.emfcloud.jackson.annotations.JsonAnnotations.getElementName;
import static org.eclipse.emfcloud.jackson.annotations.JsonAnnotations.isRawValue;
import static org.eclipse.emfcloud.jackson.module.EMFModule.Feature.OPTION_SERIALIZE_DEFAULT_VALUE;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.databind.deser.RawDeserializer;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntries;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;
import org.eclipse.emfcloud.jackson.databind.type.FeatureKind;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.ser.impl.UnknownSerializer;
import tools.jackson.databind.ser.jackson.RawSerializer;

public class EObjectFeatureProperty extends EObjectProperty {

   private final EStructuralFeature feature;
   private final JavaType javaType;
   private final boolean defaultValues;

   private ValueSerializer<Object> serializer;
   private ValueDeserializer<Object> deserializer;

   public EObjectFeatureProperty(final EStructuralFeature feature, final JavaType type, final int features) {
      super(getElementName(feature, features));

      this.feature = feature;
      this.javaType = type;
      this.defaultValues = OPTION_SERIALIZE_DEFAULT_VALUE.enabledIn(features);

      if (isRawValue(feature)) {
         this.serializer = new RawSerializer<>(String.class);
         this.deserializer = new RawDeserializer();
      }
   }

   @Override
   public void deserializeAndSet(final JsonParser jp, final EObject current, final DeserializationContext ctxt,
      final Resource resource) {
      if (deserializer == null) {
         deserializer = ctxt.findContextualValueDeserializer(javaType, null);
      }
      JsonToken token = null;

      if (jp.currentToken() == JsonToken.PROPERTY_NAME) {
         token = jp.nextToken();
      }

      if (jp.currentToken() == JsonToken.VALUE_NULL) {
         return;
      }

      boolean isMap = false;
      switch (FeatureKind.get(feature)) {
         case MAP:
            isMap = true;
            //$FALL-THROUGH$
         case MANY_CONTAINMENT:
         case SINGLE_CONTAINMENT: {
            EMFContext.setFeature(ctxt, feature);
            EMFContext.setParent(ctxt, current);
         }
         //$FALL-THROUGH$
         case SINGLE_ATTRIBUTE:
         case MANY_ATTRIBUTE: {
            if (feature.getEType() instanceof EDataType) {
               EMFContext.setDataType(ctxt, feature.getEType());
            }

            if (feature.isMany()) {
               if (token != JsonToken.START_ARRAY && !isMap) {
                  throw new StreamReadException(jp, "Expected START_ARRAY token, got " + token);
               }

               deserializer.deserialize(jp, ctxt, current.eGet(feature));
            } else {
               Object value = deserializer.deserialize(jp, ctxt);

               if (value != null) {
                  current.eSet(feature, value);
               }
            }
         }
            break;
         case MANY_REFERENCE:
         case SINGLE_REFERENCE: {
            EMFContext.setFeature(ctxt, feature);
            EMFContext.setParent(ctxt, current);

            ReferenceEntries entries = EMFContext.getEntries(ctxt);
            if (feature.isMany()) {
               deserializer.deserialize(jp, ctxt, entries.entries());
            } else {
               Object value = deserializer.deserialize(jp, ctxt);
               if (entries != null && value instanceof ReferenceEntry) {
                  entries.entries().add((ReferenceEntry) value);
               }
            }
         }
            break;
         default:
            break;
      }
   }

   @Override
   public void serialize(final EObject bean, final JsonGenerator jg, final SerializationContext provider) {
      if (serializer == null) {
         serializer = provider.findValueSerializer(javaType);
      }

      EMFContext.setParent(provider, bean);
      EMFContext.setFeature(provider, feature);

      if (bean.eIsSet(feature)) {
         Object value = bean.eGet(feature, false);

         jg.writeName(getFieldName());

         if (serializer instanceof UnknownSerializer) {
            ValueSerializer<Object> other = provider.findValueSerializer(value.getClass());
            if (other != null) {
               other.serialize(value, jg, provider);
            }
         } else {
            serializer.serialize(value, jg, provider);
         }
      } else if (defaultValues) {
         Object value = feature.getDefaultValue();

         if (value != null) {
            jg.writeName(getFieldName());
            serializer.serialize(value, jg, provider);
         }
      }
   }

   @Override
   public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) {
      return null;
   }
}
