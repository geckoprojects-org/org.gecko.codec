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

import org.eclipse.emf.ecore.EObject;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author ilenia
 * @since Aug 6, 2024
 */
public class CodecEObjectSerializerNew extends JsonSerializer<EObject> {
	
	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfo;
	private final EObject eObject;
	
	public CodecEObjectSerializerNew(final CodecModule codecModule, final CodecModelInfo codecModelInfo, final EObject eObject) {
		this.codecModule = codecModule;
		this.codecModelInfo = codecModelInfo;
		this.eObject = eObject;
	}
	
	/* 
	 * (non-Javadoc)
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
	public void serialize(EObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		gen.writeStartObject();
		
		if((boolean)codecModule.getCodecModuleProperties().get("idField")) {
//			TODO: should I loop here over the CodecModelInfo...?
			CodecInfoHolder holder = codecModelInfo.getCodecInfoHolderByType(InfoType.IDENTITY).get();
			EClassCodecInfo eObjCodecInfo = codecModelInfo.getCodecInfoForEClass(eObject.eClass()).get();
			if(eObjCodecInfo != null && holder != null) {
				CodecValueReader reader = holder.getReaderByName(eObjCodecInfo.getIdentityInfo().getValueReaderName());
				
			}
		}
		
		
		gen.writeEndObject();
	}

}
