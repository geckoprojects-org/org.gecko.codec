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
package org.gecko.codec.demo.jackson.ser;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author ilenia
 * @since Aug 22, 2024
 */
public class FeatureCodecInfoSerializer implements CodecInfoSerializer{
	
	private final static Logger LOGGER = Logger.getLogger(FeatureCodecInfoSerializer.class.getName());
	
	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private EClassCodecInfo eObjCodecInfo;
	private FeatureCodecInfo featureCodecInfo;
	
	public FeatureCodecInfoSerializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final FeatureCodecInfo featureCodecInfo) {
		this.codecModule = codecMoule;
		this.codecModelInfoService = codecModelInfoService;
		this.eObjCodecInfo = eObjCodecInfo;
		this.featureCodecInfo = featureCodecInfo;
	}
	
	@SuppressWarnings("unchecked")
	public void serialize(EObject rootObj, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if(featureCodecInfo.isIgnore()) return;
		if(featureCodecInfo.getFeatures().size() != 1) {
			LOGGER.warning(String.format("Currently no support for multiple EStructuralFeature in CodecInfoObject which is not a CodecIdInfo"));
			return;
		}
		EStructuralFeature feature = (EStructuralFeature) featureCodecInfo.getFeatures().get(0);

		EMFContext.setParent(provider, rootObj);
		EMFContext.setFeature(provider, feature);

		if(!codecModule.isSerializeIdField()) {
			if(eObjCodecInfo.getIdentityInfo().getFeatures().contains(feature)) {
				return;
			}
		}
		if (rootObj.eIsSet(feature)) {					
			if(feature.isMany()) {
				List<Object> values = (List<Object>) rootObj.eGet(feature);
				serializeManyAttribute(rootObj, values, feature, gen, provider);
			} else {
				Object value = rootObj.eGet(feature);
				if(value == null && !codecModule.isSerializeDefaultValue()) return;
				if(codecModule.isUseNamesFromExtendedMetaData()) {
					gen.writeFieldName(featureCodecInfo.getKey());
				} else {
					gen.writeFieldName(feature.getName());
				}	
				serializeSingleAttribute(rootObj, value, feature, gen, provider);
			}			
		} else if(codecModule.isSerializeDefaultValue()) {
			if(codecModule.isUseNamesFromExtendedMetaData()) {
				gen.writeFieldName(featureCodecInfo.getKey() != null ? featureCodecInfo.getKey() : feature.getName());
			} else {
				gen.writeFieldName(feature.getName());
			}
			Object value = feature.getDefaultValue();
			serializeSingleAttribute(rootObj, value, feature, gen, provider);
		}
	}

	
	@SuppressWarnings("unchecked")
	private void serializeSingleAttribute(EObject rootObj, Object value, EStructuralFeature feature, JsonGenerator gen,
			SerializerProvider provider) throws IOException {
		if(value == null && codecModule.isSerializeDefaultValue()) {
			gen.writeNull();
			return;
		}
		CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.ATTRIBUTE);
		CodecValueWriter<Object,?> writer = infoHolder.getWriterByName(featureCodecInfo.getValueWriterName());
		if(writer != null) gen.writeObject(writer.writeValue(value, provider));
		else gen.writeObject(value);
	}

	
	private void serializeManyAttribute(EObject rootObj, List<Object> values, EStructuralFeature feature,
			JsonGenerator gen, SerializerProvider provider) throws IOException {
		if(values.isEmpty() && !codecModule.isSerializeDefaultValue()) return;
		if(codecModule.isUseNamesFromExtendedMetaData()) {
			gen.writeFieldName(featureCodecInfo.getKey());
		} else {
			gen.writeFieldName(feature.getName());
		}	
		gen.writeStartArray();
		values.forEach(value -> {
			try {
				serializeSingleAttribute(rootObj, value, feature, gen, provider);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		gen.writeEndArray();
	}
}
