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
package org.gecko.codec.jackson.databind.ser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.jackson.module.CodecModule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Codec Serializer for EOperation 
 * 
 * @author ilenia
 * @since Aug 22, 2024
 */
public class OperationCodecInfoSerializer implements CodecInfoSerializer {
	
	
	private CodecModule codecModule;
	private FeatureCodecInfo featureCodecInfo;
	
	public OperationCodecInfoSerializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final FeatureCodecInfo featureCodecInfo) {
		this.codecModule = codecMoule;
		this.featureCodecInfo = featureCodecInfo;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.CodecInfoSerializer#serialize(org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(EObject rootObj, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if(featureCodecInfo.isIgnore()) return;
		Object value;		
		EOperation operation = (EOperation) featureCodecInfo.getFeatures().get(0);
		try {
			value = rootObj.eInvoke(operation, null);
		} catch (InvocationTargetException | NullPointerException | UnsupportedOperationException e) {
			// handle error
			value = null;
		}

		if (value != null) {
			if(codecModule.isUseNamesFromExtendedMetaData()) {
				gen.writeFieldName(featureCodecInfo.getKey() != null ? featureCodecInfo.getKey() : operation.getName());
			} else {
				gen.writeFieldName(operation.getName());
			}		
			JsonSerializer<Object> serializer = provider.findValueSerializer(value.getClass());
			serializer.serialize(value, gen, provider);
		}
	}

}
