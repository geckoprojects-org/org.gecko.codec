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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author ilenia
 * @since Aug 6, 2024
 */
public class CodecEObjectSerializerNew extends JsonSerializer<EObject> implements CodecInfoSerializer {

	private final static Logger LOGGER = Logger.getLogger(CodecEObjectSerializerNew.class.getName());

	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfoService;


	public CodecEObjectSerializerNew(final CodecModule codecModule, 
			final CodecModelInfo codecModelInfoService) {
		this.codecModule = codecModule;
		this.codecModelInfoService = codecModelInfoService;
	}



	/* 
	 * (non-Javadoc)eObjCodecInfo.getIdentityInfo().getFeatures();
	 * @see com.fasterxml.jackson.databind.JsonSerializer#handledType()
	 */
	@Override
	public Class<EObject> handledType() {
		return EObject.class;
	}


	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(EObject value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		PackageCodecInfo codecModelInfo = codecModule.getCodecModelInfo();
		EClassCodecInfo eObjCodecInfo = codecModelInfo.getEClassCodecInfo().stream().filter(eci -> eci.getClassifier().getInstanceClassName().equals(value.eClass().getInstanceClassName())).findFirst().get();
		if(eObjCodecInfo == null) {
			LOGGER.severe(String.format("No EClassCodecInfo found in CodecModule for EObject of class %s", value.eClass().getInstanceClassName()));
			return;
		}
		CodecInfoSerializer idInfoSerializer = new IdCodecInfoSerializer(codecModule, codecModelInfoService, eObjCodecInfo, eObjCodecInfo.getIdentityInfo());
		CodecInfoSerializer typeInfoSerializer = new TypeCodecInfoSerializer(codecModule, codecModelInfoService, eObjCodecInfo, eObjCodecInfo.getTypeInfo());
		CodecInfoSerializer superTypeInfoSerializer = new SuperTypeCodecInfoSerializer(codecModule, codecModelInfoService, eObjCodecInfo, eObjCodecInfo.getTypeInfo());

		List<CodecInfoSerializer> codecInfoSerializers = new LinkedList<>();
		eObjCodecInfo.getAttributeCodecInfo().forEach(aci -> codecInfoSerializers.add(new FeatureCodecInfoSerializer(codecModule, codecModelInfoService, eObjCodecInfo, aci)));
		eObjCodecInfo.getReferenceCodecInfo().forEach(aci -> codecInfoSerializers.add(new ReferenceCodecInfoSerializer(codecModule, codecModelInfoService, eObjCodecInfo, aci)));
		eObjCodecInfo.getOperationCodecInfo().forEach(aci -> codecInfoSerializers.add(new OperationCodecInfoSerializer(codecModule, codecModelInfoService, eObjCodecInfo, aci)));

		gen.writeStartObject();
				
		if(codecModule.isUseId()) {
			if(codecModule.isIdOnTop()) {
				idInfoSerializer.serialize(value, gen, provider);
			}
		}
		if(codecModule.isSerializeType()) {
			typeInfoSerializer.serialize(value, gen, provider);
		}
		if(codecModule.isSerializeSuperTypes()) {
			superTypeInfoSerializer.serialize(value, gen, provider);
		}
		if(codecModule.isUseId()) {
			if(!codecModule.isIdOnTop()) {
				idInfoSerializer.serialize(value, gen, provider);
			}
		}
		for(CodecInfoSerializer codecInfoSerializer : codecInfoSerializers) {
			codecInfoSerializer.serialize(value, gen, provider);
		}
		gen.writeEndObject();
	}
}
