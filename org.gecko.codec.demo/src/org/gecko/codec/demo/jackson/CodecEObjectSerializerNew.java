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
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.TypeInfo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author ilenia
 * @since Aug 6, 2024
 */
public class CodecEObjectSerializerNew extends JsonSerializer<EObject> {

	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfo;

	public CodecEObjectSerializerNew(final CodecModule codecModule, final CodecModelInfo codecModelInfo) {
		this.codecModule = codecModule;
		this.codecModelInfo = codecModelInfo;
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

		gen.writeStartObject();
		EClassCodecInfo eObjCodecInfo = codecModelInfo.getCodecInfoForEClass(value.eClass()).get();
		if(eObjCodecInfo == null) return;
		//		JsonSerializer<Object> featureCodecSer = provider.findValueSerializer(FeatureCodecInfo.class);
		//		featureCodecSer.se

		//		Serialize id field
		if((boolean)codecModule.getCodecModuleProperties().get("useId")) {
			//			1. Check if ID has to be serialized on top
			if((boolean)codecModule.getCodecModuleProperties().get("idOnTop")) {
				IdentityInfo idInfo = eObjCodecInfo.getIdentityInfo();
//				CodecInfoHolder holder = codecModelInfo.getCodecInfoHolderByType(InfoType.IDENTITY).get();
//				CodecValueWriter writer = holder.getWriterByName(eObjCodecInfo.getIdentityInfo().getValueWriterName());
				String idStrategy = idInfo.getIdStrategy() != null ? idInfo.getIdStrategy() : "";
				List<EStructuralFeature> idFeatures = idInfo.getFeatures();
				switch(idStrategy) {
				case "COMBINED":
					String idSeparator = idInfo.getIdSeparator();
					String id = "";
					for(EStructuralFeature f : idFeatures) {
						id = id.concat((String) value.eGet(f)).concat(idSeparator);
					}
					int start = id.lastIndexOf(idSeparator);
					StringBuilder builder = new StringBuilder();						
					id = builder.append(id.substring(0, start)).toString();
					gen.writeFieldName((String)codecModule.getCodecModuleProperties().get("defaultIdKey"));
					gen.writeString(id);
					break;
				default:
					if(!idFeatures.isEmpty()) {
						gen.writeFieldName((String)codecModule.getCodecModuleProperties().get("defaultIdKey"));
						gen.writeObjectId(value.eGet(idFeatures.get(0)));
					}
					break;
				}	
			}
		}
		if((boolean)codecModule.getCodecModuleProperties().get("serializeType")) {
			TypeInfo typeInfo = eObjCodecInfo.getTypeInfo();
			if(!typeInfo.isIgnoreType()) {
				CodecInfoHolder holder = codecModelInfo.getCodecInfoHolderByType(InfoType.TYPE).get();
				CodecValueWriter writer = holder.getWriterByName(typeInfo.getValueWriterName());
				gen.writeFieldName((String)codecModule.getCodecModuleProperties().get("defaultTypeKey"));
				gen.writeObject(writer.writeValue(value.eClass(), provider));
			}
		}
		for(FeatureCodecInfo featureCodecInfo : eObjCodecInfo.getFeatureInfo()) {
			if(featureCodecInfo.isIgnore()) continue;
			if((boolean)codecModule.getCodecModuleProperties().get("useNamesFromExtendedMetaData")) {
				gen.writeFieldName(featureCodecInfo.getKey() != null ? featureCodecInfo.getKey() : featureCodecInfo.getFeatures().get(0).getName());
			} else {
				gen.writeFieldName(featureCodecInfo.getFeatures().get(0).getName());
			}
			gen.writeObject(value.eGet(featureCodecInfo.getFeatures().get(0)));
		}
		


		gen.writeEndObject();
	}

}
