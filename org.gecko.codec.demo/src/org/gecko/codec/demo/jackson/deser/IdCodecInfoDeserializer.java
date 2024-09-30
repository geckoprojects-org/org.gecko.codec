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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.demo.resource.CodecJsonResource;
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
			CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.IDENTITY);
			infoHolder.getReaderByName(readerName);
//			TODO: how can I be sure that the reader returns a String in this case...??
			String id = (String) infoHolder.getReaderByName(readerName).readValue(value, ctxt);
			// TODO watch the ID handling in other resources?
//			if (resource instanceof CodecJsonResource codecJsonRes && id != null) {
//				((JsonResource) resource).setID(current, id);
//			}
		}

	}

}