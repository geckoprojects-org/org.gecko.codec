/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.codec.jackson.databind.deser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.deser.CollectionDeserializer;
import org.eclipse.emfcloud.jackson.databind.deser.EDataTypeDeserializer;
import org.eclipse.emfcloud.jackson.databind.deser.EMFDeserializers;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;
import org.eclipse.emfcloud.jackson.databind.deser.ResourceDeserializer;
import org.eclipse.emfcloud.jackson.databind.type.EcoreType;
import org.gecko.codec.jackson.databind.property.CodecEObjectPropertyMap;
import org.gecko.codec.jackson.module.CodecModule;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;

public class CodecDeserializers extends EMFDeserializers {

	private final CodecEObjectPropertyMap.Builder builder;
	private final ResourceDeserializer resourceDeserializer;
//	private final JsonDeserializer<EList<Map.Entry<?, ?>>> mapDeserializer;
	private final JsonDeserializer<Object> dataTypeDeserializer;
	private final JsonDeserializer<ReferenceEntry> referenceDeserializer;

	/**
	 * Creates a new instance.
	 * 
	 * @param module
	 */
	public CodecDeserializers(CodecModule module) {
		super(module);
		builder = CodecEObjectPropertyMap.Builder.from(module, module.getFeatures());
		this.resourceDeserializer = new ResourceDeserializer(module.getUriHandler());
		this.referenceDeserializer = module.getReferenceDeserializer();
//		this.mapDeserializer = new EMapDeserializer();
		this.dataTypeDeserializer = new EDataTypeDeserializer();

	}
	
	@Override
	public JsonDeserializer<?> findCollectionDeserializer(CollectionType type, DeserializationConfig config,
			BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer)
			throws JsonMappingException {
		if (type.getContentType().isTypeOrSubTypeOf(EObject.class)) {
		     return new CollectionDeserializer(type, new CodecEObjectDeserializer(builder.with(config), type.getContentType().getRawClass()),
		        referenceDeserializer);
		  }
		  return super.findCollectionDeserializer(type, config, beanDesc, elementTypeDeserializer, (JsonDeserializer<?>) elementDeserializer);
	}
	
	@Override
	public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config,
			BeanDescription beanDesc) throws JsonMappingException {
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
			return new CodecEObjectDeserializer(builder.with(config), type.getRawClass());
		}

		return super.findBeanDeserializer(type, config, beanDesc);
	}

}
