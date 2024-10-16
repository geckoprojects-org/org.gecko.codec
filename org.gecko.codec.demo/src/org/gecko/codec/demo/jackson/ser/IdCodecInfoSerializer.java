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
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author ilenia
 * @since Aug 22, 2024
 */
public class IdCodecInfoSerializer implements CodecInfoSerializer{

	private final static Logger LOGGER = Logger.getLogger(IdCodecInfoSerializer.class.getName());

	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private EClassCodecInfo eObjCodecInfo;
	private IdentityInfo idCodecInfo;

	public IdCodecInfoSerializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final IdentityInfo idCodecInfo) {
		this.codecModule = codecMoule;
		this.codecModelInfoService = codecModelInfoService;
		this.eObjCodecInfo = eObjCodecInfo;
		this.idCodecInfo = idCodecInfo;
	}

	@SuppressWarnings("unchecked")
	public void serialize(EObject rootObj, JsonGenerator gen, SerializerProvider provider) throws IOException {
		EMFContext.setParent(provider, rootObj);
		String idStrategy = idCodecInfo.getIdStrategy() != null ? idCodecInfo.getIdStrategy() : "";
		List<EStructuralFeature> idFeatures = idCodecInfo.getFeatures().stream().filter(f -> f instanceof EStructuralFeature).map(EStructuralFeature.class::cast).collect(Collectors.toList());

		switch(idStrategy) {
		case "COMBINED":
			CodecValueWriter<Object, String> w = codecModelInfoService.getCodecInfoHolderByType(InfoType.IDENTITY).getWriterByName(idCodecInfo.getValueWriterName());
			String idSeparator = idCodecInfo.getIdSeparator();
			String id = "";
			for(EStructuralFeature f : idFeatures) {
				if(rootObj.eGet(f) != null) {
					id = id.concat(rootObj.eGet(f).toString()).concat(idSeparator);
				}
			}
			int start = id.lastIndexOf(idSeparator);
			StringBuilder builder = new StringBuilder();						
			id = builder.append(id.substring(0, start)).toString();
			if(w != null) {
				id = w.writeValue(id, provider);
			}
			gen.writeFieldName(codecModule.getIdKey());
			
			if(gen.canWriteObjectId() && codecModule.isIdFeatureAsPrimaryKey()) {
				gen.writeObjectId(id);
			} else {
				gen.writeString(id);
			}
			break;
		case "ID_FIELD": default:
			if(idFeatures.size() == 0) {
				LOGGER.warning(String.format("ID strategy is ID_FIELD but no id feature has been found. Not doing anything."));
				break;
			}
			if(idFeatures.size() != 1) {
				LOGGER.severe(String.format("ID strategy is ID_FIELD but id features are %d. There should be exactly 1!", idFeatures.size()));
				break;
			}
			gen.writeFieldName(codecModule.getIdKey());

			//TODO: We have to specify in the documentation that we are expecting a writer which takes the id field as input
			CodecValueWriter<Object, String> writer = codecModelInfoService.getCodecInfoHolderByType(InfoType.IDENTITY).getWriterByName(idCodecInfo.getValueWriterName());
			if(writer != null) {
				String value = writer.writeValue(rootObj.eGet(idFeatures.get(0)), provider);
				if(gen.canWriteObjectId() && codecModule.isIdFeatureAsPrimaryKey()) {
					gen.writeObjectId(value);
				} else {
					gen.writeString(value);
				}
			}
			else {
				if(gen.canWriteObjectId() && codecModule.isIdFeatureAsPrimaryKey()) {
					gen.writeObjectId(rootObj.eGet(idFeatures.get(0)));
				} else {
					gen.writeString(rootObj.eGet(idFeatures.get(0)).toString());
				}
			}
			break;					
		}	
	}
}
