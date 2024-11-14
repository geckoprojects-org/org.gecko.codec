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
package org.gecko.codec.jackson.databind.deser;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.SuperTypeInfo;
import org.gecko.codec.jackson.module.CodecModule;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

/**
 * 
 * @author ilenia
 * @since Sep 30, 2024
 */
public class SuperTypeCodecInfoDeserializer implements CodecInfoDeserializer {
	
	private final JsonDeserializer<String> deserializer = StringDeserializer.instance;
	
	private CodecModelInfo codecModelInfoService;
	private SuperTypeInfo typeCodecInfo;
	
	public SuperTypeCodecInfoDeserializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final SuperTypeInfo typeCodecInfo) {
		this.codecModelInfoService = codecModelInfoService;
		this.typeCodecInfo = typeCodecInfo;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.deser.CodecInfoDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public EObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
			jp.nextToken();
		}
		if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
			jp.nextToken();
		}
		if (jp.getCurrentToken() == JsonToken.END_ARRAY) {
			return null;
		}
		return create(deserializer.deserialize(jp, ctxt), ctxt);
	}
	
	@SuppressWarnings("unchecked")
	public EObject create(final String value, final DeserializationContext ctxt) {
		CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.SUPER_TYPE);
		CodecValueReader<String, EClass> valueReader = holder.getReaderByName(typeCodecInfo.getValueReaderName());
		EClass eClass = valueReader.readValue(value, ctxt);

		return eClass != null ? EcoreUtil.create(eClass) : null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.deser.CodecInfoDeserializer#deserializeAndSet(com.fasterxml.jackson.core.JsonParser, org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.databind.DeserializationContext, org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public void deserializeAndSet(JsonParser jp, EObject current, DeserializationContext ctxt, Resource resource)
			throws IOException {
		// TODO Auto-generated method stub

	}

}
