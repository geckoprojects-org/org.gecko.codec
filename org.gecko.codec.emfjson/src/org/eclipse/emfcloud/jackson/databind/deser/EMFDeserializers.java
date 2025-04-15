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

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.property.EObjectPropertyMap;
import org.eclipse.emfcloud.jackson.databind.type.EcoreType;
import org.eclipse.emfcloud.jackson.module.EMFModule;

import tools.jackson.databind.ValueDeserializer;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.KeyDeserializer;
import tools.jackson.databind.deser.Deserializers;
import tools.jackson.databind.jsontype.TypeDeserializer;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.databind.type.MapLikeType;
import tools.jackson.databind.type.ReferenceType;

public class EMFDeserializers extends Deserializers.Base {

   private final ResourceDeserializer resourceDeserializer;
   private final ValueDeserializer<EList<Map.Entry<?, ?>>> mapDeserializer;
   private final ValueDeserializer<Object> dataTypeDeserializer;
   private final ValueDeserializer<ReferenceEntry> referenceDeserializer;
   private final EObjectPropertyMap.Builder builder;

   public EMFDeserializers(final EMFModule module) {
      this.builder = new EObjectPropertyMap.Builder(
         module.getIdentityInfo(),
         module.getTypeInfo(),
         module.getReferenceInfo(),
         module.getFeatures());
      this.resourceDeserializer = new ResourceDeserializer(module.getUriHandler());
      this.referenceDeserializer = module.getReferenceDeserializer();
      this.mapDeserializer = new EMapDeserializer();
      this.dataTypeDeserializer = new EDataTypeDeserializer();
   }

   @Override
   public ValueDeserializer<?> findMapLikeDeserializer(final MapLikeType type,
      final DeserializationConfig config,
      final BeanDescription beanDesc,
      final KeyDeserializer keyDeserializer,
      final TypeDeserializer elementTypeDeserializer,
      final ValueDeserializer<?> elementDeserializer) {
      if (type.isTypeOrSubTypeOf(EMap.class)) {
         return mapDeserializer;
      }

      return super.findMapLikeDeserializer(type, config, beanDesc, keyDeserializer, elementTypeDeserializer,
         elementDeserializer);
   }

   @Override
   public ValueDeserializer<?> findEnumDeserializer(final Class<?> type, final DeserializationConfig config,
      final BeanDescription beanDesc) {
      if (Enumerator.class.isAssignableFrom(type)) {
         return dataTypeDeserializer;
      }

      return super.findEnumDeserializer(type, config, beanDesc);
   }

   @Override
   public ValueDeserializer<?> findCollectionDeserializer(final CollectionType type,
      final DeserializationConfig config,
      final BeanDescription beanDesc,
      final TypeDeserializer elementTypeDeserializer,
      final ValueDeserializer<?> elementDeserializer) {
      if (type.getContentType().isTypeOrSubTypeOf(EObject.class)) {
         return new CollectionDeserializer(type, new EObjectDeserializer(builder, type.getContentType().getRawClass()),
            referenceDeserializer);
      }
      return super.findCollectionDeserializer(type, config, beanDesc, elementTypeDeserializer, elementDeserializer);
   }

   @Override
   public ValueDeserializer<?> findReferenceDeserializer(final ReferenceType refType,
      final DeserializationConfig config,
      final BeanDescription beanDesc,
      final TypeDeserializer contentTypeDeserializer,
      final ValueDeserializer<?> contentDeserializer) {
      if (referenceDeserializer != null) {
         return referenceDeserializer;
      }
      return super.findReferenceDeserializer(refType, config, beanDesc, contentTypeDeserializer, contentDeserializer);
   }

   @Override
   public ValueDeserializer<?> findBeanDeserializer(final JavaType type,
      final DeserializationConfig config,
      final BeanDescription beanDesc) {
      if (type.isTypeOrSubTypeOf(Resource.class)) {
         return resourceDeserializer;
      }

      if (type.isReferenceType()) {
         return referenceDeserializer;
      }

      if (type.isTypeOrSubTypeOf(EcoreType.DataType.class)) {
         return dataTypeDeserializer;
      }

      if (type.isTypeOrSubTypeOf(EObject.class)) {
         return new EObjectDeserializer(builder, type.getRawClass());
      }

      return super.findBeanDeserializer(type, config, beanDesc);
   }

/* 
 * (non-Javadoc)
 * @see tools.jackson.databind.deser.Deserializers#hasDeserializerFor(tools.jackson.databind.DeserializationConfig, java.lang.Class)
 */
@Override
public boolean hasDeserializerFor(DeserializationConfig config, Class<?> valueType) {
	// TODO Auto-generated method stub
	return false;
}
}
