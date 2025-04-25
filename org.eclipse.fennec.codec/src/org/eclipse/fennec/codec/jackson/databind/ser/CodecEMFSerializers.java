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
package org.eclipse.fennec.codec.jackson.databind.ser;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import com.fasterxml.jackson.annotation.JsonFormat;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.jsontype.TypeSerializer;
import tools.jackson.databind.ser.Serializers;
import tools.jackson.databind.ser.jdk.CollectionSerializer;
import tools.jackson.databind.ser.jdk.MapSerializer;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.databind.type.MapLikeType;

/**
 * 
 * @author ilenia
 * @since Apr 24, 2025
 */
public class CodecEMFSerializers extends Serializers.Base {
	
	private final ValueSerializer<EObject> referenceSerializer;
	private final ValueSerializer<Resource> resourceSerializer = new CodecResourceSerializer();
	private final ValueSerializer<Object> mapKeySerializer = new EMapKeySerializer();
	private final ValueSerializer<Object> mapValueSerializer = new EMapValueSerializer();
	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;

	
	public CodecEMFSerializers(CodecModule module) {
		this.referenceSerializer = module.getReferenceSerializer();
		this.codecModule = module;
		this.codecModelInfoService = module.getCodecModelInfoService();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ser.Serializers.Base#findSerializer(tools.jackson.databind.SerializationConfig, tools.jackson.databind.JavaType, tools.jackson.databind.BeanDescription, com.fasterxml.jackson.annotation.JsonFormat.Value)
	 */
	@Override
	public ValueSerializer<?> findSerializer(final SerializationConfig config, final JavaType type,
			final BeanDescription beanDesc, JsonFormat.Value formatOverrides) {
		if (type.isTypeOrSubTypeOf(Resource.class)) {
			return resourceSerializer;
		}
		if (type.isTypeOrSubTypeOf(EObject.class)) {
			return new CodecEObjectSerializer(codecModule, codecModelInfoService);
		}
		return super.findSerializer(config, type, beanDesc, formatOverrides);
	}
	

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ser.Serializers.Base#findMapLikeSerializer(tools.jackson.databind.SerializationConfig, tools.jackson.databind.type.MapLikeType, tools.jackson.databind.BeanDescription, com.fasterxml.jackson.annotation.JsonFormat.Value, tools.jackson.databind.ValueSerializer, tools.jackson.databind.jsontype.TypeSerializer, tools.jackson.databind.ValueSerializer)
	 */
	@Override
	public ValueSerializer<?> findMapLikeSerializer(final SerializationConfig config, final MapLikeType type,
			final BeanDescription beanDesc,  JsonFormat.Value formatOverrides, final ValueSerializer<Object> keySerializer,
			final TypeSerializer elementTypeSerializer,
			final ValueSerializer<Object> elementValueSerializer) {
		if (type.isTypeOrSubTypeOf(EMap.class)) {
			// make a MapSerializer for configurability
			ValueSerializer<Object> keySer = Optional.ofNullable(keySerializer).orElse(mapKeySerializer);
			ValueSerializer<Object> valueSer = Optional.ofNullable(elementValueSerializer).orElse(mapValueSerializer);			
			/**
			 * 
			 * Jacskon 2.x
			 * public static MapSerializer construct(Set<String> ignoredEntries, JavaType mapType,
            boolean staticValueType, TypeSerializer vts,
            JsonSerializer<Object> keySerializer, JsonSerializer<Object> valueSerializer,
            Object filterId)
			 * 
			 * 
			 * Jackson 3.0 
			 * public static MapSerializer construct(JavaType mapType,
            boolean staticValueType, TypeSerializer vts,
            ValueSerializer<Object> keySerializer, ValueSerializer<Object> valueSerializer,
            Object filterId,
            Set<String> ignoredEntries, Set<String> includedEntries)
			 * 
			 * 
			 * 
			 */
			MapSerializer mapSer = MapSerializer.construct(type, false, elementTypeSerializer, keySer, valueSer,
					null, Set.of(), Set.of());
			// and use a wrapping EMapSerializer for edge cases
			return new EMapSerializer(mapSer);
		}

		return super.findMapLikeSerializer(config, type, beanDesc, formatOverrides, keySerializer, elementTypeSerializer,
				elementValueSerializer);
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ser.Serializers.Base#findCollectionSerializer(tools.jackson.databind.SerializationConfig, tools.jackson.databind.type.CollectionType, tools.jackson.databind.BeanDescription, com.fasterxml.jackson.annotation.JsonFormat.Value, tools.jackson.databind.jsontype.TypeSerializer, tools.jackson.databind.ValueSerializer)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ValueSerializer<?> findCollectionSerializer(final SerializationConfig config, final CollectionType type,
			final BeanDescription beanDesc, JsonFormat.Value formatOverrides, final TypeSerializer elementTypeSerializer,
			final ValueSerializer<Object> elementValueSerializer) {
		if (type.getContentType().isReferenceType()) {
			return new CollectionSerializer(type.getContentType(), false, null, (ValueSerializer) referenceSerializer);
		}
		return super.findCollectionSerializer(config, type, beanDesc, formatOverrides, elementTypeSerializer, elementValueSerializer);
	}

}
