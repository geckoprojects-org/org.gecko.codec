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
package org.eclipse.fennec.codec.jackson.databind.deser;

import static tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.constants.CodecResourceOptions;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.info.codecinfo.CodecInfoHolder;
import org.eclipse.fennec.codec.info.codecinfo.CodecValueReader;
import org.eclipse.fennec.codec.info.codecinfo.EClassCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.FeatureCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.IdentityInfo;
import org.eclipse.fennec.codec.info.codecinfo.InfoType;
import org.eclipse.fennec.codec.info.codecinfo.PackageCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.SuperTypeInfo;
import org.eclipse.fennec.codec.info.codecinfo.TypeInfo;
import org.eclipse.fennec.codec.jackson.databind.CodecTokenBuffer;
import org.eclipse.fennec.codec.jackson.databind.EMFCodecReadContext;
import org.eclipse.fennec.codec.jackson.module.CodecModule;
import org.eclipse.fennec.codec.jackson.utils.CodecParserException;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.TokenStreamContext;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.jdk.StringDeserializer;

/**
 * 
 * @author ilenia
 * @since Sep 26, 2024
 */
public class CodecEObjectDeserializer extends ValueDeserializer<EObject> {

	private static final Logger LOGGER = Logger.getLogger(CodecEObjectDeserializer.class.getName());

	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfoService;

	private CodecInfoHolder infoHolder;
	private EClass type = null;

	public CodecEObjectDeserializer(final Class<?> currentType, final CodecModule codecModule, 
			final CodecModelInfo codecModelInfoService) {
		this.codecModule = codecModule;
		this.codecModelInfoService = codecModelInfoService;
		infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE);
	}


	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ValueDeserializer#handledType()
	 */
	@Override
	public Class<?> handledType() {
		return EObject.class;
	}

	private EMFCodecReadContext extractCodecContext(JsonParser jp) {
		EMFCodecReadContext codecReadCtxt = null;
		if(jp.streamReadContext() instanceof EMFCodecReadContext crc) {
			codecReadCtxt = crc;
		}
//		If we do not have a EMFCodecReadContext yet it might be we are trying to read the buffer
		if(codecReadCtxt == null && jp instanceof CodecTokenBuffer.Parser ctbp) {
			if(ctbp.streamReadContext() instanceof EMFCodecReadContext crc)
			codecReadCtxt = crc;
		}
		return codecReadCtxt;
	}

	private boolean isRootObject(TokenStreamContext codecContext) {
		if(codecContext.getParent() == null) return true;
		if(codecContext.inObject() && codecContext.getParent().inRoot()) return true; //if we are in a Resource then the root object has as parent the Resource ctxt
		return false;
	}

	private EClassCodecInfo extractModelInfo(EClass type) {
		PackageCodecInfo codecModelInfo = codecModule.getCodecModelInfo();
		EClassCodecInfo eObjCodecInfo = null;
		if(type != null) {
			for(EClassCodecInfo eci : codecModelInfo.getEClassCodecInfo()) {
				if(eci.getClassifier().equals(type)) {
					eObjCodecInfo = eci;
					break;
				}
			}
		}
//		we look in other packages
		if(eObjCodecInfo == null) {
			eObjCodecInfo = codecModelInfoService.getCodecInfoForEClass(type).orElse(null);
		}
		return eObjCodecInfo;
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ValueDeserializer#deserialize(tools.jackson.core.JsonParser, tools.jackson.databind.DeserializationContext)
	 */
	@Override
	public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) {

		//		EMFContext.prepare(ctxt);
		EMFCodecReadContext codecReadCtxt = extractCodecContext(jp);
		if(codecReadCtxt == null) {
			throw new IllegalArgumentException(String.format("StreamReadContext is not of type EMFCodecReadContext! Something went wrong!"));
		}

		CodecTokenBuffer buffer = null;
		if(isRootObject((TokenStreamContext)codecReadCtxt)) { //also non contained ref end up here!!
			if(ctxt.getAttribute(CodecResourceOptions.CODEC_ROOT_OBJECT) != null) {
				type  = (EClass) ctxt.getAttribute(CodecResourceOptions.CODEC_ROOT_OBJECT);
				System.out.println("Root object with CODEC_ROOT_OBJECT option!");
			} else {
				//				We look in the type key (the type key has to be the same for all root objects otherwise we have no way to decide which key to use when)
				System.out.println("Root object but without CODEC_ROOT_OBJECT option!");
				buffer = determineType(jp, ctxt);
				if(type == null) {
					throw new IllegalArgumentException(String.format("It was not possible to determine the type of the EObject from the type key %s", codecModule.getTypeKey()));
				}
			}
		} else {
			EStructuralFeature currentFeature = getCurrentFeature((TokenStreamContext)codecReadCtxt);
			if(currentFeature == null) {
				throw new IllegalArgumentException(String.format("Current Feature is not set in context. Something went wrong!"));
			}
			//			Here in principle we could have different type keys based on the EStructuralFeature...? Where are we storing them though? In the Module? In the InfoService?
			System.out.println("Reference!");
			buffer = determineType(jp, ctxt);
			if(type == null) {
				LOGGER.warning(() -> String.format("It was not possible to determine the type of the EReference %s from the type key %s. The type of the EReference will be set to its default type", currentFeature.getName(), codecModule.getTypeKey()));
				type = (EClass) currentFeature.getEGenericType().getERawType();
			}
		}

		
		EObject current = EcoreUtil.create(type);
		codecReadCtxt.setCurrentEObject(current);
		
//		TODO: if we now have a custom deserializer for the current type we shall use that		
		if(buffer != null) {
			doDeserialize(buffer.asParser(), ctxt, current, codecReadCtxt);
		} else {
			doDeserialize(jp, ctxt, current, codecReadCtxt);
		}

		return current;
	}
	
	private void doDeserialize(JsonParser jp, DeserializationContext ctxt, EObject current, EMFCodecReadContext codecReadContext) {
		EClassCodecInfo eObjCodecInfo = extractModelInfo(type);
		Resource resource = codecReadContext.getResource();		
		JsonToken nextToken = jp.nextToken();
		while (nextToken != JsonToken.END_OBJECT && nextToken != null) {
			final String field = jp.currentName();
			FeatureCodecInfo featureCodecInfo = getFeatureCodecInfo(field, eObjCodecInfo);
			if(featureCodecInfo instanceof IdentityInfo idInfo) {
				new IdCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, idInfo)
				.deserializeAndSet(jp, current, ctxt, resource);
			} 
			else if(featureCodecInfo != null && !(featureCodecInfo instanceof TypeInfo) && !(featureCodecInfo instanceof SuperTypeInfo)) {
				new FeatureCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, featureCodecInfo, eObjCodecInfo.getTypeInfo())
				.deserializeAndSet(jp, current, ctxt, resource);
			} else if(featureCodecInfo == null && current != null) {
				handleUnknownProperty(jp, resource, ctxt, current.eClass());
			} 
			nextToken = jp.nextToken();
		}
	}

	@SuppressWarnings("unchecked")
	private CodecTokenBuffer determineType(JsonParser jp, DeserializationContext ctxt) {
		CodecTokenBuffer buffer = null;
		JsonToken nextToken = jp.nextToken();

		while (nextToken != JsonToken.END_OBJECT && nextToken != null) {
			final String field = jp.currentName();
			//			If it was not possible to determine the type from the conditions before then we look for the _type in the serialized document
			if(field.equals(codecModule.getTypeKey()) && type == null) {
				jp.nextToken();
				for(CodecValueReader<String, EClass> reader : infoHolder.getReaders()) {
					try {
						type = reader.readValue(StringDeserializer.instance.deserialize(jp, ctxt), ctxt);
					} catch(Exception e) {
						type = null;
					}
					if(type != null) break;
				}				
			}
			if (buffer == null) {
				buffer = CodecTokenBuffer.forBuffering(jp, ctxt);
			}
			buffer.copyCurrentStructure(jp);
			nextToken = jp.nextToken();
		}
		return buffer;
	}

	private void handleUnknownProperty(final JsonParser jp, final Resource resource, final DeserializationContext ctxt,	EClass currentEClass)  {
		if (resource != null && ctxt.getConfig().hasDeserializationFeatures(FAIL_ON_UNKNOWN_PROPERTIES.getMask())) {
			resource.getErrors().add(new CodecParserException(String.format("Unknown feature '%s' for %s", jp.currentName(), EcoreUtil.getURI(currentEClass)),jp.currentLocation()));
		}
		// we didn't find a feature so consume
		// the field and move on
		jp.nextToken();
		jp.skipChildren();
	}

	private FeatureCodecInfo getFeatureCodecInfo(String fieldName, EClassCodecInfo eObjCodecInfo) {
		if(fieldName == null) return null;
		if(fieldName.equals(codecModule.getIdKey())) return eObjCodecInfo.getIdentityInfo();
		if(fieldName.equals(codecModule.getTypeKey()) && !codecModule.isDeserializeType()) return eObjCodecInfo.getTypeInfo();
		if(fieldName.equals(codecModule.getSuperTypeKey())) return eObjCodecInfo.getSuperTypeInfo();
		for(FeatureCodecInfo featureCodecInfo : eObjCodecInfo.getFeatureInfo()) {
			String key = codecModule.isUseNamesFromExtendedMetaData() ? featureCodecInfo.getKey() : featureCodecInfo.getFeatures().get(0).getName();
			if(fieldName.equals(key)) {
				return featureCodecInfo;
			}
		}
		LOGGER.warning(() -> "No CodecInfo found for field " + fieldName + " in " + eObjCodecInfo.getId());
		return null;
	}

	
	private EStructuralFeature getCurrentFeature(final TokenStreamContext ctxt) {
		TokenStreamContext parentCodecReadCtxt = null;
		if(ctxt.getParent() != null) {
			if(ctxt.getParent().inObject()) {
				parentCodecReadCtxt = ctxt.getParent();
			} else if(ctxt.getParent().inArray()) {
				parentCodecReadCtxt = ctxt.getParent().getParent();
			}
		}
		if(parentCodecReadCtxt != null) {
			EReference reference = (EReference) (((EMFCodecReadContext) parentCodecReadCtxt).getCurrentFeature());
			return reference;

		}
		return null;
	}

}
