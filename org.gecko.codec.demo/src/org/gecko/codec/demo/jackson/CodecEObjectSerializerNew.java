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

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
import org.gecko.codec.info.codecinfo.TypeInfo;
import org.gecko.codec.jackson.CodecGeneratorBase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;

/**
 * 
 * @author ilenia
 * @since Aug 6, 2024
 */
public class CodecEObjectSerializerNew extends JsonSerializer<EObject> {

	private final static Logger LOGGER = Logger.getLogger(CodecEObjectSerializerNew.class.getName());

	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfoService;
	private final JsonSerializer<String[]> serializer = StringArraySerializer.instance;


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
		if(codecModule.isUseId()) {
			if(codecModule.isIdOnTop()) {
				serializeIdInfo(value, eObjCodecInfo, gen, provider);
			}
		}
		if(codecModule.isSerializeType()) {
			serializeType(value, eObjCodecInfo, gen, provider);
		}
		if(codecModule.isSerializeSuperTypes()) {
			serializeSuperTypes(value, eObjCodecInfo, gen, provider);
		}
		if(codecModule.isUseId()) {
			if(!codecModule.isIdOnTop()) {
				serializeIdInfo(value, eObjCodecInfo, gen, provider);
			}
		}
		for(FeatureCodecInfo featureCodecInfo : eObjCodecInfo.getFeatureInfo()) {
			serializeFeature(value, eObjCodecInfo, featureCodecInfo, gen, provider);
		}
		
//		TODO: add Reference handling here!!
		
		gen.writeEndObject();
	}
	
	@SuppressWarnings("unchecked")
	private void serializeFeature(EObject rootObj, EClassCodecInfo eObjCodecInfo, FeatureCodecInfo featureCodecInfo, JsonGenerator gen,
			SerializerProvider provider) throws IOException {
		if(featureCodecInfo.isIgnore() || featureCodecInfo.getFeatures().isEmpty()) return;
		if(!codecModule.isSerializeIdField()) {
			if(eObjCodecInfo.getIdentityInfo().getFeatures().contains(featureCodecInfo.getFeatures().get(0)))
				return;
		}
		EStructuralFeature feature = featureCodecInfo.getFeatures().get(0);
		CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.FEATURE);
		CodecValueWriter<Object,?> writer = infoHolder.getWriterByName(featureCodecInfo.getValueWriterName());
		if (rootObj.eIsSet(feature)) {
			Object value = rootObj.eGet(feature);
			if(codecModule.isUseNamesFromExtendedMetaData()) {
				gen.writeFieldName(featureCodecInfo.getKey() != null ? featureCodecInfo.getKey() : feature.getName());
			} else {
				gen.writeFieldName(feature.getName());
			}			
			if(writer != null) gen.writeObject(writer.writeValue(value, provider));
			else gen.writeObject(value);
		} else if(codecModule.isSerializeDefaultValue()) {
			if(codecModule.isUseNamesFromExtendedMetaData()) {
				gen.writeFieldName(featureCodecInfo.getKey() != null ? featureCodecInfo.getKey() : feature.getName());
			} else {
				gen.writeFieldName(feature.getName());
			}
			Object value = feature.getDefaultValue();
			if(writer != null) gen.writeObject(writer.writeValue(value, provider));
			else gen.writeObject(value);
		}
	}

	@SuppressWarnings("unchecked")
	private void serializeType(EObject value, EClassCodecInfo eObjCodecInfo, JsonGenerator gen,
			SerializerProvider provider) throws IOException {

		TypeInfo typeInfo = eObjCodecInfo.getTypeInfo();
		if(!typeInfo.isIgnoreType()) {
			EClass objectType = value.eClass();
			EReference containment = value.eContainmentFeature();

			if (isRoot(value) || shouldSaveType(objectType, containment.getEReferenceType(), containment)) {
				CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE);
				CodecValueWriter<EClass, String> writer = holder.getWriterByName(typeInfo.getValueWriterName());
				String v = writer.writeValue(value.eClass(), provider);
				gen.writeFieldName(codecModule.getTypeKey());
				if (gen.canWriteTypeId()) {
					gen.writeTypeId(v);
				} else {
					gen.writeString(v);
				}
			}
		}
	}


	@SuppressWarnings("unchecked")
	private void serializeSuperTypes(EObject value, EClassCodecInfo eObjCodecInfo, JsonGenerator gen,
			SerializerProvider provider) throws IOException {

		EClass objectType = value.eClass();
		EReference containment = value.eContainmentFeature();
		// check for our implementation with additional callbacks 
		CodecGeneratorBase cgb = null;
		if (gen instanceof CodecGeneratorBase) {
			cgb = (CodecGeneratorBase) gen;
		}

		if (isRoot(value) || shouldSaveType(objectType, containment.getEReferenceType(), containment)) {
			CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.REFERENCE);
			CodecValueWriter<EClass, String[]> writer = holder.getWriterByName("URIS_WRITER");

			String[] values = writer.writeValue(value.eClass(), provider);

			if (nonNull(values) && values.length > 0) {
				gen.writeFieldName(codecModule.getSuperTypeKey());
				/*
				 * We use our custom callback, if possible.
				 */
				if (!codecModule.isSerializeSuperTypesAsArray()) {
					if (nonNull(cgb) && cgb.canWriteSuperTypes()) {
						cgb.writeSuperTypes(values);
					} else {
						if (gen.canWriteTypeId()) {
							gen.writeTypeId(values);
						} else {
//							Comma separated string...?
							StringBuilder sb = new StringBuilder();
							for(String s : values) {
								sb.append(s);
								sb.append(",");
							}
							sb.replace(sb.length()-1, sb.length(), "");
							gen.writeString(sb.toString());
						}
						
					}        		 
				} else if (codecModule.isSerializeArrayBatched() && 
						nonNull(cgb) && 
						cgb.canWriteOneShotArray()) {
					cgb.writeArray(values, 0, values.length, String.class);
				} else {
					serializer.serialize(values, gen, provider);
				}
			}
		}
	}

	private boolean shouldSaveType(final EClass objectType, final EClass featureType, final EStructuralFeature feature) {
		return objectType != featureType && objectType != EcorePackage.Literals.EOBJECT;
	}


	private void serializeIdInfo(EObject value, EClassCodecInfo eObjCodecInfo, JsonGenerator gen, SerializerProvider provider) throws IOException {
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

	private boolean isRoot(final EObject bean) {
		EObject container = bean.eContainer();
		Resource.Internal resource = ((InternalEObject) bean).eDirectResource();

		return container == null || resource != null && resource != ((InternalEObject) container).eDirectResource();
	}
}
