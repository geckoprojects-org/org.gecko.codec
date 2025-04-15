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

import static org.eclipse.emfcloud.jackson.module.EMFModule.Feature.OPTION_SERIALIZE_TYPE;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.annotations.EcoreTypeInfo;
import org.eclipse.emfcloud.jackson.utils.ValueReader;
import org.eclipse.emfcloud.jackson.utils.ValueWriter;

import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.jdk.StringDeserializer;
import tools.jackson.databind.ser.jdk.StringSerializer;

public class EObjectTypeProperty extends EObjectProperty {

   private final ValueSerializer<Object> serializer = new StringSerializer();
   private final ValueDeserializer<String> deserializer = StringDeserializer.instance;

   private final ValueReader<String, EClass> valueReader;
   private final ValueWriter<EClass, String> valueWriter;
   private final int features;

   public EObjectTypeProperty(final EcoreTypeInfo info, final int features) {
      super(info.getProperty());

      this.valueReader = info.getValueReader();
      this.valueWriter = info.getValueWriter();
      this.features = features;
   }

   @Override
   public void serialize(final EObject bean, final JsonGenerator jg, final SerializationContext provider) {
      if (!OPTION_SERIALIZE_TYPE.enabledIn(features)) {
         return;
      }

      EClass objectType = bean.eClass();
      EReference containment = bean.eContainmentFeature();

      if (isRoot(bean) || shouldSaveType(objectType, containment.getEReferenceType(), containment)) {
         String value = valueWriter.writeValue(bean.eClass(), provider);

         jg.writeName(getFieldName());
         serializer.serialize(value, jg, provider);
      }
   }

   private boolean isRoot(final EObject bean) {
      EObject container = bean.eContainer();
      Resource.Internal resource = ((InternalEObject) bean).eDirectResource();

      return container == null || resource != null && resource != ((InternalEObject) container).eDirectResource();
   }

   @Override
   public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) {
      if (jp.currentToken() == JsonToken.PROPERTY_NAME) {
         jp.nextToken();
      }

      return create(deserializer.deserialize(jp, ctxt), ctxt);
   }

   public EObject create(final String value, final DeserializationContext ctxt) {
      EClass eClass = valueReader.readValue(value, ctxt);

      return eClass != null ? EcoreUtil.create(eClass) : null;
   }

   @Override
   public void deserializeAndSet(final JsonParser jp, final EObject current, final DeserializationContext ctxt,
      final Resource resource) {
      // do nothing
   }

   private boolean shouldSaveType(final EClass objectType, final EClass featureType, final EStructuralFeature feature) {
      return objectType != featureType && objectType != EcorePackage.Literals.EOBJECT;
   }
}
