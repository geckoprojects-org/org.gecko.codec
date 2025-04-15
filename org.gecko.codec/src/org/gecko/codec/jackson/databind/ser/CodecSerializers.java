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
package org.gecko.codec.jackson.databind.ser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.databind.ser.EMFSerializers;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.jackson.module.CodecModule;

import com.fasterxml.jackson.annotation.JsonFormat;

import tools.jackson.databind.ValueSerializer;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationConfig;

/**
 * Extension of EMFSerializers to overwrite serializer for EObject so that it takes ours
 * @author ilenia
 * @since Aug 8, 2024
 */
public class CodecSerializers extends EMFSerializers {

	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	
	/**
	 * Creates a new instance.
	 * @param module
	 */
	public CodecSerializers(final CodecModule module) {
		super(module);
		this.codecModule = module;
		this.codecModelInfoService = module.getCodecModelInfoService();
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.databind.ser.EMFSerializers#findSerializer(tools.jackson.databind.SerializationConfig, tools.jackson.databind.JavaType, tools.jackson.databind.BeanDescription, com.fasterxml.jackson.annotation.JsonFormat.Value)
	 */
	@Override
	public ValueSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc, JsonFormat.Value formatOverrides) {
		
		if (type.isTypeOrSubTypeOf(EObject.class)) {
			return new CodecEObjectSerializer(codecModule, codecModelInfoService);
		}
		return super.findSerializer(config, type, beanDesc, formatOverrides);
	}
}
