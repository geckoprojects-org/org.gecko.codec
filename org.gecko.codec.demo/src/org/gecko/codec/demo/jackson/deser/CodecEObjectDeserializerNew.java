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

import static org.eclipse.emfcloud.jackson.databind.EMFContext.getFeature;
import static org.eclipse.emfcloud.jackson.databind.EMFContext.getResource;

import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
import org.gecko.codec.info.codecinfo.SuperTypeInfo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;

/**
 * 
 * @author ilenia
 * @since Sep 26, 2024
 */
public class CodecEObjectDeserializerNew extends JsonDeserializer<EObject> {

	private static final Logger LOGGER = Logger.getLogger(CodecEObjectDeserializerNew.class.getName());

	private Class<?> currentType;
	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfoService;

	public CodecEObjectDeserializerNew(final Class<?> currentType, final CodecModule codecModule, 
			final CodecModelInfo codecModelInfoService) {
		this.currentType = currentType;
		this.codecModule = codecModule;
		this.codecModelInfoService = codecModelInfoService;
	}


	@Override
	public Class<?> handledType() {
		return EObject.class;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
		EMFContext.prepare(ctxt);

		final Resource resource = getResource(ctxt);
		final EStructuralFeature feature = getFeature(ctxt);
		final EClass defaultType = getDefaultType(ctxt);
		
//		In case of contained ref, the defaultType is set and we can immediately construct everything. 
//		This fixes the issue when we don't have a _type property for contained ref, to retrieve the actual type
		EObject current = defaultType == null ? null : EcoreUtil.create(defaultType);
		EClass type = defaultType == null ? null : defaultType;	
		PackageCodecInfo codecModelInfo = codecModule.getCodecModelInfo();
		EClassCodecInfo eObjCodecInfo = type == null ? null : codecModelInfo.getEClassCodecInfo().stream().
				filter(eci -> 
				eci.getClassifier().getInstanceClassName().equals(defaultType.getInstanceClassName()))
				.findFirst().get();
		
		TokenBuffer buffer = null;
		JsonToken nextToken = jp.nextToken();
		CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE);
		while (nextToken != JsonToken.END_OBJECT && nextToken != null) {
			final String field = jp.getCurrentName();
			if(field.equals(codecModule.getTypeKey())) {
				jp.nextToken();
				for(CodecValueReader reader : infoHolder.getReaders()) {
					try {
						type = (EClass) reader.readValue(StringDeserializer.instance.deserialize(jp, ctxt), ctxt);
					} catch(Exception e) {
						type = null;
					}
					if(type != null) break;
				}
				EClass rootObj = type;
				eObjCodecInfo = codecModelInfo.getEClassCodecInfo().stream().
						filter(eci -> 
						eci.getClassifier().getInstanceClassName().equals(rootObj.getInstanceClassName()))
						.findFirst().get();
				current = EcoreUtil.create(type);
			}
			else if(current != null) {
				FeatureCodecInfo featureCodecInfo = getFeatureCodecInfo(field, eObjCodecInfo);
				if(featureCodecInfo instanceof IdentityInfo idInfo) {
					new IdCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, idInfo).deserializeAndSet(jp, current, ctxt, resource);
				} 
				else if(featureCodecInfo instanceof SuperTypeInfo superTypeInfo) {
					new SuperTypeCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, superTypeInfo).deserialize(jp, ctxt);
				} 	
				else if(featureCodecInfo != null) {
					new FeatureCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, featureCodecInfo, eObjCodecInfo.getTypeInfo()).deserializeAndSet(jp, current, ctxt, resource);
					
				} else if(featureCodecInfo == null) {
					//				TODO: unknown property!
				} 
			} else {
//				since current is not set, I guess we are copying the structure and try with the next property (e.g. we want to look for the type first so we know
//				which object we have to build)...?
				if (buffer == null) {
					buffer = new TokenBuffer(jp);
				}
				buffer.copyCurrentStructure(jp);
			}
			nextToken = jp.nextToken();
		}
		
//		 handle empty objects
				if (buffer == null && current == null && defaultType != null) {
					return EcoreUtil.create(defaultType);
				}
		return current;
	}


	private FeatureCodecInfo getFeatureCodecInfo(String fieldName, EClassCodecInfo eObjCodecInfo) {
		if(fieldName == null) return null;
		if(fieldName.equals(codecModule.getIdKey())) return eObjCodecInfo.getIdentityInfo();
		if(fieldName.equals(codecModule.getTypeKey())) return eObjCodecInfo.getTypeInfo();
		if(fieldName.equals(codecModule.getSuperTypeKey())) return eObjCodecInfo.getSuperTypeInfo();
		for(FeatureCodecInfo featureCodecInfo : eObjCodecInfo.getFeatureInfo()) {
			if(fieldName.equals(featureCodecInfo.getKey())) {
				return featureCodecInfo;
			}
		}
		LOGGER.warning(String.format("No CodecInfo found for field %s", fieldName));
		return null;
	}

	private EClass getDefaultType(final DeserializationContext ctxt) {
		EClass type = null;

		EObject parent = EMFContext.getParent(ctxt);
		if (parent == null) {
			if (currentType != null && currentType != EObject.class) {
				type = EMFContext.findEClassByQualifiedName(ctxt, currentType.getCanonicalName());
			}
			if (type == null) {
				type = EMFContext.getRoot(ctxt);
			}
		} else {
			final EReference reference = (EReference) getFeature(ctxt);
			if (reference != null && !reference.getEReferenceType().isAbstract()) {
				final EGenericType reifiedType = EcoreUtil.getReifiedType(parent.eClass(), reference.getEGenericType());
				return (EClass) reifiedType.getERawType();
			}
		}
		return type;
	}

}
