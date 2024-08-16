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
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
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
	
	private final static Logger LOGGER = Logger.getLogger(CodecEObjectSerializerNew.class.getName());

	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfoService;

	public CodecEObjectSerializerNew(final CodecModule codecModule, final CodecModelInfo codecModelInfoService) {
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
	@SuppressWarnings("unchecked")
	@Override
	public void serialize(EObject value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		gen.writeStartObject();
//		Otherwise we get a new instance which does not contain the changes we made from the options of the resource
		PackageCodecInfo codecModelInfo = codecModule.getCodecModelInfo();
		
		EClassCodecInfo eObjCodecInfo = codecModelInfo.getEClassCodecInfo().stream().filter(eci -> eci.getClassifier().getInstanceClassName().equals(value.eClass().getInstanceClassName())).findFirst().get();
		if(eObjCodecInfo == null) {
			LOGGER.severe(String.format("No EClassCodecInfo found in CodecModule for EObject of class %s", value.eClass().getInstanceClassName()));
			return;
		}
		//		Serialize id field
		if(codecModule.isUseId()) {
			//			1. Check if ID has to be serialized on top
//			TODO: what to do if idOnTop is false??
			if(codecModule.isIdOnTop()) {
				IdentityInfo idInfo = eObjCodecInfo.getIdentityInfo();

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
					gen.writeFieldName(codecModule.getIdKey());
					gen.writeString(id);
					break;
				case "ID_FIELD":
					if(idFeatures.size() != 1) {
						LOGGER.severe(String.format("ID strategy is ID_FIELD but id features are %d. There should be exactly 1!", idFeatures.size()));
						break;
					}
					gen.writeFieldName(codecModule.getIdKey());
					if(gen.canWriteObjectId()) {
						gen.writeObjectId(value.eGet(idFeatures.get(0)));
					} else {
						gen.writeString(value.eGet(idFeatures.get(0)).toString());
					}
					break;					
				default:
					break;
				}	
			}
		}
		if(codecModule.isSerializeType()) {
			TypeInfo typeInfo = eObjCodecInfo.getTypeInfo();
			if(!typeInfo.isIgnoreType()) {
				CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE).get();
				CodecValueWriter<EClass, ?> writer = holder.getWriterByName(typeInfo.getValueWriterName());
				gen.writeFieldName(codecModule.getTypeKey());
				gen.writeObject(writer.writeValue(value.eClass(), provider));
			}
		}
		for(FeatureCodecInfo featureCodecInfo : eObjCodecInfo.getFeatureInfo()) {
			if(featureCodecInfo.isIgnore()) continue;
			if(codecModule.isUseNamesFromExtendedMetaData()) {
				gen.writeFieldName(featureCodecInfo.getKey() != null ? featureCodecInfo.getKey() : featureCodecInfo.getFeatures().get(0).getName());
			} else {
				gen.writeFieldName(featureCodecInfo.getFeatures().get(0).getName());
			}
			gen.writeObject(value.eGet(featureCodecInfo.getFeatures().get(0)));
		}
		gen.writeEndObject();
	}

}
