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
package org.gecko.codec.demo.jackson;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.databind.ser.EMFSerializers;
import org.gecko.codec.info.CodecModelInfo;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;

/**
 * 
 * @author ilenia
 * @since Aug 8, 2024
 */
public class CodecSerializersNew extends EMFSerializers {

	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	
	/**
	 * Creates a new instance.
	 * @param module
	 */
	public CodecSerializersNew(final CodecModule module) {
		super(module);
		this.codecModule = module;
		this.codecModelInfoService = module.getCodecModelInfoService();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.databind.ser.EMFSerializers#findSerializer(com.fasterxml.jackson.databind.SerializationConfig, com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.databind.BeanDescription)
	 */
	@Override
	public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
		
		if (type.isTypeOrSubTypeOf(EObject.class)) {
			return new CodecEObjectSerializerNew(codecModule, codecModelInfoService);
		}
		return super.findSerializer(config, type, beanDesc);
	}
}
