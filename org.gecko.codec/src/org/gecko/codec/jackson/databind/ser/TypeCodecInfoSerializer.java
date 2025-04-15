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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.TypeInfo;
import org.gecko.codec.jackson.module.CodecModule;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Codec Serializer for TypeInfo
 * @author ilenia
 * @since Aug 22, 2024
 */
public class TypeCodecInfoSerializer implements CodecInfoSerializer {
		
	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private TypeInfo typeCodecInfo;
	
	public TypeCodecInfoSerializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final TypeInfo typeCodecInfo) {
		this.codecModule = codecMoule;
		this.codecModelInfoService = codecModelInfoService;
		this.typeCodecInfo = typeCodecInfo;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.CodecInfoSerializer#serialize(org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void serialize(EObject rootObj, JsonGenerator gen, SerializationContext provider) {
		EMFContext.setParent(provider, rootObj);
		if(!typeCodecInfo.isIgnoreType()) {
			if (codecModule.isSerializeType()) {
				CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE);
				CodecValueWriter<EClass, String> writer = holder.getWriterByName(typeCodecInfo.getValueWriterName());
				String v = writer.writeValue(rootObj.eClass(), provider);
				gen.writeName(codecModule.getTypeKey());
				if (gen.canWriteTypeId()) {
					gen.writeTypeId(v);
				} else {
					gen.writeString(v);
				}
			}
		}
	}
}
