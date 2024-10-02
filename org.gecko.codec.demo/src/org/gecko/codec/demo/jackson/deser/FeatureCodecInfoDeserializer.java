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
package org.gecko.codec.demo.jackson.deser;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntries;
import org.eclipse.emfcloud.jackson.databind.type.EcoreTypeFactory;
import org.eclipse.emfcloud.jackson.databind.type.FeatureKind;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.TypeInfo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @author ilenia
 * @since Sep 27, 2024
 */
public class FeatureCodecInfoDeserializer implements CodecInfoDeserializer {

	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private EClassCodecInfo eObjCodecInfo;
	private FeatureCodecInfo featureCodecInfo;
	private TypeInfo typeCodecInfo;
	private JsonDeserializer<Object> deserializer;


	public FeatureCodecInfoDeserializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final FeatureCodecInfo featureCodecInfo, TypeInfo typeInfo) {
		this.codecModule = codecMoule;
		this.codecModelInfoService = codecModelInfoService;
		this.eObjCodecInfo = eObjCodecInfo;
		this.featureCodecInfo = featureCodecInfo;
		this.typeCodecInfo = typeInfo;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.deser.CodecInfoDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public EObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		return null;

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.deser.CodecInfoDeserializer#deserializeAndSet(com.fasterxml.jackson.core.JsonParser, org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.databind.DeserializationContext, org.eclipse.emf.ecore.resource.Resource)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deserializeAndSet(JsonParser jp, EObject current, DeserializationContext ctxt, Resource resource)
			throws IOException {

		if(featureCodecInfo.getFeatures().get(0) instanceof EOperation) return;
		EStructuralFeature feature = (EStructuralFeature) featureCodecInfo.getFeatures().get(0);

		JsonToken token = null;

		if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
			token = jp.nextToken();
		}

		if (jp.getCurrentToken() == JsonToken.VALUE_NULL) {
			return;
		}
		//	      Use TypeFactory to create JavaType from Eclass
		EcoreTypeFactory factory = EMFContext.getTypeFactory(ctxt);
		System.out.println(feature.eClass());
		JavaType javaType = factory.typeOf(ctxt, feature.eClass(), feature);
		deserializer = ctxt.findContextualValueDeserializer(javaType, null);

		boolean isMap = false;
		switch (FeatureKind.get(feature)) {
		case MAP:
			isMap = true;
			//$FALL-THROUGH$
		case MANY_CONTAINMENT:
		case SINGLE_CONTAINMENT: {
			EMFContext.setFeature(ctxt, feature);
			EMFContext.setParent(ctxt, current);
		}
		//$FALL-THROUGH$
		case SINGLE_ATTRIBUTE:
		case MANY_ATTRIBUTE: {
			if (feature.getEType() instanceof EDataType) {
				EMFContext.setDataType(ctxt, feature.getEType());
			}
			String readerName = featureCodecInfo.getValueReaderName();
			CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.ATTRIBUTE);
			CodecValueReader<Object, ?> reader =  infoHolder.getReaderByName(readerName);
			if (feature.isMany()) {
				if (token != JsonToken.START_ARRAY && !isMap) {
					throw new JsonParseException(jp, "Expected START_ARRAY token, got " + token);
				}
				
				Collection<Object> objs = (Collection<Object>) deserializer.deserialize(jp, ctxt, current.eGet(feature));
				//	               If a custom ValueReader is set we use it to convert the deserialized value
				if(objs != null && reader != null) {
					Collection<Object> newObjs = new LinkedList<>();
					objs.forEach(obj -> {
						Object v = reader.readValue(obj, ctxt);
						newObjs.add(v);
					});
					current.eSet(feature, newObjs);
				}
			}  else {
				Object value = deserializer.deserialize(jp, ctxt);
				//		                If a custom ValueReader is set we use it to convert the deserialized value
				if (value != null && reader != null) {    	
					Object v = reader.readValue(value, ctxt);
					current.eSet(feature, v);
				}
				else current.eSet(feature, value);
			}
		}
		break;
		case MANY_REFERENCE:
		case SINGLE_REFERENCE: {
			EMFContext.setFeature(ctxt, feature);
			EMFContext.setParent(ctxt, current);
			ReferenceEntries entries = EMFContext.getEntries(ctxt);
			if (feature.isMany()) {
				deserializer.deserialize(jp, ctxt, entries.entries());
			} else {
				new ReferenceCodecInfoDeserializer(codecModule, codecModelInfoService, typeCodecInfo)
				.deserializeAndSet(jp, current, ctxt, resource);	  
			}
		}
		break;
		default:
			break;
		}
	}

}
