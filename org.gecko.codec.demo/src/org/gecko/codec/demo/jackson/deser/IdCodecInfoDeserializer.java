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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * 
 * @author ilenia
 * @since Sep 26, 2024
 */
public class IdCodecInfoDeserializer implements CodecInfoDeserializer {
	
	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private EClassCodecInfo eObjCodecInfo;
	private IdentityInfo idCodecInfo;
	
	public IdCodecInfoDeserializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final IdentityInfo idCodecInfo) {
		this.codecModule = codecMoule;
		this.codecModelInfoService = codecModelInfoService;
		this.eObjCodecInfo = eObjCodecInfo;
		this.idCodecInfo = idCodecInfo;
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
		if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
			jp.nextToken();
		}

		Object value;
		switch (jp.getCurrentToken()) {
		case VALUE_STRING:
			value = jp.getValueAsString();
			break;
		case VALUE_NUMBER_INT:
			value = jp.getValueAsInt();
			break;
		case VALUE_NUMBER_FLOAT:
			value = jp.getValueAsLong();
			break;
		default:
			value = null;
		}

		if (value != null) {
			String readerName = idCodecInfo.getValueReaderName();
			String id = null;
			if(readerName != null) {
				CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.IDENTITY);
				id = (String) infoHolder.getReaderByName(readerName).readValue(value, ctxt);
			} else {
				id = value.toString();
			}
//			If the serializedIdField is false then we need to retrieve the values from the _id
			if(!codecModule.isSerializeIdField()) {
				setIdFields(current, id);
			}
			// TODO watch the ID handling in other resources?
//			if (resource instanceof CodecJsonResource codecJsonRes && id != null) {
//				((JsonResource) resource).setID(current, id);
//			}
		}

	}

	private void setIdFields(EObject current, String deserializedIdValue) {
		switch(idCodecInfo.getIdStrategy()) {
		case "COMBINED":
			String[] idSegments = deserializedIdValue.split(idCodecInfo.getIdSeparator());
			for(int i = 0; i < idSegments.length; i++) {
				EStructuralFeature feature = (EStructuralFeature) idCodecInfo.getFeatures().get(i);
				current.eSet(feature, idSegments[i]);
			}
			break;
		case "ID_FIELD":
			EcoreUtil.setID(current, deserializedIdValue);
			break;
		default:
			
		}
	}
}
