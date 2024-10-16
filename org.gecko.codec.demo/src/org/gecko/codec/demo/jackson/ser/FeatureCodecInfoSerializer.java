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
import org.eclipse.emfcloud.jackson.databind.type.EcoreTypeFactory;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.jackson.databind.CodecWriteContext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
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
	private JsonSerializer<Object> serializer;
	
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
		
		if(gen.getOutputContext() instanceof CodecWriteContext cwt) {
			cwt.setFeature(feature);
		}
		EcoreTypeFactory factory = EMFContext.getTypeFactory(provider);
		JavaType javaType = factory.typeOf(provider, feature.eClass(), feature);
		serializer = provider.findValueSerializer(javaType);
		
		if (rootObj.eIsSet(feature)) {					
			if(feature.isMany()) {
				List<Object> values = (List<Object>) rootObj.eGet(feature);
				serializeManyAttribute(rootObj, values, feature, gen, provider);
			} else {
				Object value = rootObj.eGet(feature);
				serializeSingleAttribute(rootObj, value, feature, gen, provider);
			}			
		} else if(codecModule.isSerializeDefaultValue()) {
			if(feature.isMany()) {
				List<Object> values = (List<Object>) rootObj.eGet(feature);
				serializeManyAttribute(rootObj, values, feature, gen, provider);
			} else {
				Object value = feature.getDefaultValue();
				serializeSingleAttribute(rootObj, value, feature, gen, provider);
			}
		}
	}

	
	private void serializeSingleAttribute(EObject rootObj, Object value, EStructuralFeature feature, JsonGenerator gen,
			SerializerProvider provider) throws IOException {
		
		if(value == null && !codecModule.isSerializeNullValue()) {
			return;
		}
		if(value instanceof String str && str != null && str.isEmpty() && !codecModule.isSerializeEmptyValue()) {
			return;
		}
		if(codecModule.isUseNamesFromExtendedMetaData()) {
			gen.writeFieldName(featureCodecInfo.getKey());
		} else {
			gen.writeFieldName(feature.getName());
		}
		
		serializeSingleAttributeValue(rootObj, value, feature, gen, provider);
	}
	
	@SuppressWarnings("unchecked")
	private void serializeSingleAttributeValue(EObject rootObj, Object value, EStructuralFeature feature, JsonGenerator gen,
			SerializerProvider provider) throws IOException {
		
		if(value == null) {
			gen.writeNull();
			return;
		} 
		else {
			CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.ATTRIBUTE);
			CodecValueWriter<Object,?> writer = infoHolder.getWriterByName(featureCodecInfo.getValueWriterName());
//			if(writer != null) gen.writeObject(writer.writeValue(value, provider));
//			else gen.writeObject(value);
			
			
			if(writer != null) serializer.serialize(writer.writeValue(value, provider), gen, provider);
			else serializer.serialize(value, gen, provider);
		}
	}

	
	@SuppressWarnings("unchecked")
	private void serializeManyAttribute(EObject rootObj, List<Object> values, EStructuralFeature feature,
			JsonGenerator gen, SerializerProvider provider) throws IOException {
		if(values.isEmpty() && (!codecModule.isSerializeDefaultValue() || !codecModule.isSerializeEmptyValue())) return;
		if(codecModule.isUseNamesFromExtendedMetaData()) {
			gen.writeFieldName(featureCodecInfo.getKey());
		} else {
			gen.writeFieldName(feature.getName());
		}	
		
//		TODO: check serailized array batched here...?
		
		CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.ATTRIBUTE);
		CodecValueWriter<Object,?> writer = infoHolder.getWriterByName(featureCodecInfo.getValueWriterName());
		
//		gen.writeStartArray();
		
		if(writer != null) serializer.serialize(writer.writeValue(values, provider), gen, provider);
		else serializer.serialize(values, gen, provider);
//		values.forEach(value -> {
//			try {
//				serializeSingleAttributeValue(rootObj, value, feature, gen, provider);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
//		gen.writeEndArray();
	}
}
