/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
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
package org.eclipse.fennec.codec.jackson.databind.deser;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.codec.constants.URIHandler;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.KeyDeserializer;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.Deserializers;
import tools.jackson.databind.jsontype.TypeDeserializer;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.databind.type.MapLikeType;
import tools.jackson.databind.type.ReferenceType;

/**
 * 
 * @author ilenia
 * @since Apr 23, 2025
 */
public class CodecEMFDeserializers extends Deserializers.Base {

	private final ValueDeserializer<EObject> referenceDeserializer;
	private final ValueDeserializer<EList<Map.Entry<?, ?>>> mapDeserializer;
	private final ValueDeserializer<Enumerator> enumDeserializer;
	private final CodecResourceDeserializer resourceDeserializer;
	protected final URIHandler handler;
	private CodecModelInfo codecModelInfoService;
	private CodecModule module;


	public CodecEMFDeserializers(CodecModule module) {
		this.module = module;
		this.codecModelInfoService = module.getCodecModelInfoService();
		this.referenceDeserializer = module.getReferenceDeserializer();
		this.mapDeserializer = new EMapDeserializer();
		this.handler = module.getUriHandler();
		this.enumDeserializer = new EnumDeserializer(module);
		this.resourceDeserializer = new CodecResourceDeserializer(module.getUriHandler());

	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.deser.Deserializers.Base#findCollectionDeserializer(tools.jackson.databind.type.CollectionType, tools.jackson.databind.DeserializationConfig, tools.jackson.databind.BeanDescription, tools.jackson.databind.jsontype.TypeDeserializer, tools.jackson.databind.ValueDeserializer)
	 */
	@Override
	public ValueDeserializer<?> findCollectionDeserializer(CollectionType type, DeserializationConfig config,
			BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, ValueDeserializer<?> elementDeserializer) {
		if (type.getContentType().isTypeOrSubTypeOf(EObject.class)) {
			return new CodecCollectionDeserializer(type, new CodecEObjectDeserializer(type.getContentType().getRawClass(), module, codecModelInfoService),
					referenceDeserializer);
		}
		return super.findCollectionDeserializer(type, config, beanDesc, elementTypeDeserializer, (ValueDeserializer<?>) elementDeserializer);
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.deser.Deserializers.Base#findBeanDeserializer(tools.jackson.databind.JavaType, tools.jackson.databind.DeserializationConfig, tools.jackson.databind.BeanDescription)
	 */
	@Override
	public ValueDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config,
			BeanDescription beanDesc) throws DatabindException {
		if (type.isTypeOrSubTypeOf(Resource.class)) {
			return resourceDeserializer;
		}

		if (type.isReferenceType()) {
			return referenceDeserializer;
		}

		if (type.isTypeOrSubTypeOf(EObject.class)) {
			return new CodecEObjectDeserializer(type.getRawClass(), module, codecModelInfoService);
		}

		return super.findBeanDeserializer(type, config, beanDesc);
	}


	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.deser.Deserializers.Base#findMapLikeDeserializer(tools.jackson.databind.type.MapLikeType, tools.jackson.databind.DeserializationConfig, tools.jackson.databind.BeanDescription, tools.jackson.databind.KeyDeserializer, tools.jackson.databind.jsontype.TypeDeserializer, tools.jackson.databind.ValueDeserializer)
	 */
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

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.deser.Deserializers.Base#findEnumDeserializer(java.lang.Class, tools.jackson.databind.DeserializationConfig, tools.jackson.databind.BeanDescription)
	 */
	@Override
	public ValueDeserializer<?> findEnumDeserializer(final Class<?> type, final DeserializationConfig config,
			final BeanDescription beanDesc) {
		if (Enumerator.class.isAssignableFrom(type)) {
			return enumDeserializer;
		}
		return super.findEnumDeserializer(type, config, beanDesc);
	}
	

	   /* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.deser.Deserializers.Base#findReferenceDeserializer(tools.jackson.databind.type.ReferenceType, tools.jackson.databind.DeserializationConfig, tools.jackson.databind.BeanDescription, tools.jackson.databind.jsontype.TypeDeserializer, tools.jackson.databind.ValueDeserializer)
	 */
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
