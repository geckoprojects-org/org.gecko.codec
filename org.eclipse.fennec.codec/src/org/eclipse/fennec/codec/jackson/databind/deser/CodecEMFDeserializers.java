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
import org.eclipse.emfcloud.jackson.databind.deser.EDataTypeDeserializer;
import org.eclipse.emfcloud.jackson.databind.deser.EMapDeserializer;
import org.eclipse.fennec.codec.constants.URIHandler;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.KeyDeserializer;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.Deserializers;
import tools.jackson.databind.jsontype.TypeDeserializer;
import tools.jackson.databind.type.MapLikeType;
import tools.jackson.databind.type.ReferenceType;

/**
 * 
 * @author ilenia
 * @since Apr 23, 2025
 */
public class CodecEMFDeserializers extends Deserializers.Base {

	private final ValueDeserializer<?> referenceDeserializer;
	private final ValueDeserializer<EList<Map.Entry<?, ?>>> mapDeserializer;
	private final ValueDeserializer<Object> dataTypeDeserializer;
	protected final URIHandler handler;


	public CodecEMFDeserializers(CodecModule module) {
		this.referenceDeserializer = module.getReferenceDeserializer();
		this.mapDeserializer = new EMapDeserializer();
		this.dataTypeDeserializer = new EDataTypeDeserializer();
		this.handler = module.getUriHandler();
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
			return dataTypeDeserializer;
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
