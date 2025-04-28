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
package org.eclipse.emfcloud.jackson.databind.ser;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EEnumLiteralImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;
import org.eclipse.emfcloud.jackson.databind.property.EObjectPropertyMap;
import org.eclipse.emfcloud.jackson.databind.type.EcoreType;
import org.eclipse.emfcloud.jackson.module.EMFModule;

import com.fasterxml.jackson.annotation.JsonFormat.Value;

import tools.jackson.databind.BeanDescription.Supplier;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.jsontype.TypeSerializer;
import tools.jackson.databind.ser.Serializers;
import tools.jackson.databind.ser.jdk.CollectionSerializer;
import tools.jackson.databind.ser.jdk.MapSerializer;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.databind.type.MapLikeType;

public class EMFSerializers extends Serializers.Base {

	private final EObjectPropertyMap.Builder propertiesBuilder;
	private final ValueSerializer<EObject> referenceSerializer;
	private final ValueSerializer<Resource> resourceSerializer = new ResourceSerializer();
	private final ValueSerializer<?> dataTypeSerializer = new EDataTypeSerializer();
	private final ValueSerializer<Object> mapKeySerializer = new EMapKeySerializer();
	private final ValueSerializer<Object> mapValueSerializer = new EMapValueSerializer();
	private final ValueSerializer<?> enumeratorSerializer = new EnumeratorSerializer();

	public EMFSerializers(final EMFModule module) {
		this.propertiesBuilder = EObjectPropertyMap.Builder.from(module, module.getFeatures());
		this.referenceSerializer = module.getReferenceSerializer();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ser.Serializers.Base#findMapLikeSerializer(tools.jackson.databind.SerializationConfig, tools.jackson.databind.type.MapLikeType, tools.jackson.databind.BeanDescription.Supplier, com.fasterxml.jackson.annotation.JsonFormat.Value, tools.jackson.databind.ValueSerializer, tools.jackson.databind.jsontype.TypeSerializer, tools.jackson.databind.ValueSerializer)
	 */
	@Override
	public ValueSerializer<?> findMapLikeSerializer(SerializationConfig config, MapLikeType type, Supplier beanDescRef,
			Value formatOverrides, ValueSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer,
			ValueSerializer<Object> elementValueSerializer) {
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
		return super.findMapLikeSerializer(config, type, beanDescRef, formatOverrides, keySerializer, elementTypeSerializer,
				elementValueSerializer);
	}

	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ser.Serializers.Base#findCollectionSerializer(tools.jackson.databind.SerializationConfig, tools.jackson.databind.type.CollectionType, tools.jackson.databind.BeanDescription.Supplier, com.fasterxml.jackson.annotation.JsonFormat.Value, tools.jackson.databind.jsontype.TypeSerializer, tools.jackson.databind.ValueSerializer)
	 */
	@Override
	public ValueSerializer<?> findCollectionSerializer(SerializationConfig config, CollectionType type,
			Supplier beanDescRef, Value formatOverrides, TypeSerializer elementTypeSerializer,
			ValueSerializer<Object> elementValueSerializer) {
		if (type.getContentType().isReferenceType()) {
			return new CollectionSerializer(type.getContentType(), false, null, (ValueSerializer) referenceSerializer);
		}
		return super.findCollectionSerializer(config, type, beanDescRef, formatOverrides, elementTypeSerializer,
				elementValueSerializer);
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ser.Serializers.Base#findSerializer(tools.jackson.databind.SerializationConfig, tools.jackson.databind.JavaType, tools.jackson.databind.BeanDescription.Supplier, com.fasterxml.jackson.annotation.JsonFormat.Value)
	 */
	@Override
	public ValueSerializer<?> findSerializer(SerializationConfig config, JavaType type, Supplier beanDescRef,
			Value formatOverrides) {
		if (type.isTypeOrSubTypeOf(Resource.class)) {
			return resourceSerializer;
		}

		if (type.isTypeOrSubTypeOf(Enumerator.class) && !type.isReferenceType()) {
			if (type.getRawClass() != EEnumLiteralImpl.class) {
				return enumeratorSerializer;
			}
		}

		if (type.isReferenceType() || type.isTypeOrSubTypeOf(ReferenceEntry.class)) {
			return referenceSerializer;
		}
		

		if (type.isTypeOrSubTypeOf(EcoreType.DataType.class)) {
			return dataTypeSerializer;
		}

		if (type.isTypeOrSubTypeOf(EObject.class)) {
			return new EObjectSerializer(propertiesBuilder, referenceSerializer);
		}

		return super.findSerializer(config, type, beanDescRef, formatOverrides);
	}
	

	/**
	 * Returns the propertiesBuilder.
	 * @return the propertiesBuilder
	 */
	protected EObjectPropertyMap.Builder getPropertyBuilder() {
		return propertiesBuilder;
	}

	/**
	 * Returns the referenceSerializer.
	 * @return the referenceSerializer
	 */
	public ValueSerializer<EObject> getReferenceSerializer() {
		return referenceSerializer;
	}

}
