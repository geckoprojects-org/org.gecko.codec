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

import org.eclipse.emf.ecore.EStructuralFeature;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 
 * @author ilenia
 * @since Aug 9, 2024
 */
public class FeatureCodecInfoSerializer extends StdSerializer<FeatureCodecInfo> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 6082452110958999595L;
	
	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfo;
	
	public FeatureCodecInfoSerializer(Class<FeatureCodecInfo> t, final CodecModule codecModule, final CodecModelInfo codecModelInfo) {
		super(t);
		this.codecModule = codecModule;
		this.codecModelInfo = codecModelInfo;
	}
	
	@Override
	public Class<FeatureCodecInfo> handledType() {
		return FeatureCodecInfo.class;
	}


	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(FeatureCodecInfo featureCodecInfo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(featureCodecInfo instanceof IdentityInfo idInfo) {
//			id serialzation
//			CodecInfoHolder holder = codecModelInfo.getCodecInfoHolderByType(InfoType.IDENTITY).get();
//			if(holder != null) {
////				1. Check if ID has to be serialized on top
//				if((boolean)codecModule.getCodecModuleProperties().get("idOnTop")) {
//					String idStrategy = idInfo.getIdStrategy();
//					List<EStructuralFeature> idFeatures = idInfo.getFeatures();
//					switch(idStrategy) {
//					case "COMBINED":
//						String idSeparator = idInfo.getIdSeparator();
//						String id = "";
//						for(EStructuralFeature f : idFeatures) {
//							id = id.concat((String) value.eGet(f)).concat(idSeparator);
//						}
//						int start = id.lastIndexOf(idSeparator);
//						StringBuilder builder = new StringBuilder();						
//						id = builder.append(id.substring(0, start)).toString();
//						gen.writeFieldName((String)codecModule.getCodecModuleProperties().get("defaultIdKey"));
//						gen.writeString(id);
//						break;
//					default:
//						if(!idFeatures.isEmpty()) {
//							gen.writeFieldName((String)codecModule.getCodecModuleProperties().get("defaultIdKey"));
////							gen.writeObjectId(writer.writeValue(idFeatures.get(0), provider));
//							gen.writeObjectId(idFeatures.get(0));
//						}
//						break;
//					}
//				}
//			}
			
		}
		
	}

}
