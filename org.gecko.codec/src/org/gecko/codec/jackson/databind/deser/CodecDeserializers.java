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
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.jackson.module.CodecModule;

import tools.jackson.databind.DatabindException;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.jsontype.TypeDeserializer;
import tools.jackson.databind.type.CollectionType;

/**
 * 
 * @author ilenia
 * @since Sep 26, 2024
 */
public class CodecDeserializers extends EMFDeserializers {

	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private final ResourceDeserializer resourceDeserializer;
	private final ValueDeserializer<Object> dataTypeDeserializer;
	private final ValueDeserializer<ReferenceEntry> referenceDeserializer;

	/**
	 * Creates a new instance.
	 * @param module
	 */
	public CodecDeserializers(final CodecModule  module) {
		super(module);
		this.codecModule = module;
		this.codecModelInfoService = module.getCodecModelInfoService();
		this.resourceDeserializer = new ResourceDeserializer(module.getUriHandler());
		this.referenceDeserializer = module.getReferenceDeserializer();
		this.dataTypeDeserializer = new EDataTypeDeserializer();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.databind.deser.EMFDeserializers#findCollectionDeserializer(com.fasterxml.jackson.databind.type.CollectionType, com.fasterxml.jackson.databind.DeserializationConfig, com.fasterxml.jackson.databind.BeanDescription, com.fasterxml.jackson.databind.jsontype.TypeDeserializer, com.fasterxml.jackson.databind.JsonDeserializer)
	 */
	@Override
	public ValueDeserializer<?> findCollectionDeserializer(CollectionType type, DeserializationConfig config,
			BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, ValueDeserializer<?> elementDeserializer) {
		if (type.getContentType().isTypeOrSubTypeOf(EObject.class)) {
			return new CollectionDeserializer(type, new CodecEObjectDeserializer(type.getContentType().getRawClass(), codecModule, codecModelInfoService),
					referenceDeserializer);
		}
		return super.findCollectionDeserializer(type, config, beanDesc, elementTypeDeserializer, (ValueDeserializer<?>) elementDeserializer);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.databind.deser.EMFDeserializers#findBeanDeserializer(com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.databind.DeserializationConfig, com.fasterxml.jackson.databind.BeanDescription)
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

		if (type.isTypeOrSubTypeOf(EcoreType.DataType.class)) {
			return dataTypeDeserializer;
		}

		if (type.isTypeOrSubTypeOf(EObject.class)) {
			return new CodecEObjectDeserializer(type.getRawClass(), codecModule, codecModelInfoService);
		}

		return super.findBeanDeserializer(type, config, beanDesc);
	}




}
