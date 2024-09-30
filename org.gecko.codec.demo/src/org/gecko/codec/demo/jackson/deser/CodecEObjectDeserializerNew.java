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
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
import org.gecko.codec.info.codecinfo.SuperTypeInfo;
import org.gecko.codec.info.codecinfo.TypeInfo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
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
	@Override
	public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
		EMFContext.prepare(ctxt);

		final Resource resource = getResource(ctxt);
		final EStructuralFeature feature = getFeature(ctxt);
		final EClass defaultType = getDefaultType(ctxt);
		EObject current = null;


		EClass rootObj = (EClass) ctxt.getAttribute("ROOT_OBJECT");
		PackageCodecInfo codecModelInfo = codecModule.getCodecModelInfo();

		EClassCodecInfo eObjCodecInfo = codecModelInfo.getEClassCodecInfo().stream().
				filter(eci -> 
				eci.getClassifier().getInstanceClassName().equals(rootObj.getInstanceClassName()))
				.findFirst().get();

		if(eObjCodecInfo == null) {
			LOGGER.severe(String.format("No EClassCodecInfo found in CodecModule for EObject of class %s", rootObj.getInstanceClassName()));
			return null;
		}
		//		CodecEObjectPropertyMap propertyMap;

		//		Maybe this is not needed in our case because we have the model info...?
		//		if (feature == null && defaultType != null) {
		//			propertyMap = builder.construct(ctxt, defaultType);
		//		} else if (feature instanceof EReference) {
		//			final EObject parent = EMFContext.getParent(ctxt);
		//			final EClass resolvedType;
		//			if (parent == null) {
		//				resolvedType = ((EReference) feature).getEReferenceType();
		//			} else {
		//				resolvedType = (EClass) EcoreUtil.getReifiedType(parent.eClass(), feature.getEGenericType())
		//						.getERawType();
		//			}
		//
		//			propertyMap = builder.construct(ctxt, resolvedType);
		//		} else {
		//			propertyMap = builder.constructDefault(ctxt);
		//		}

		TokenBuffer buffer = null;
		JsonToken nextToken = jp.nextToken();
		while (nextToken != JsonToken.END_OBJECT && nextToken != null) {
			final String field = jp.getCurrentName();

			//			We might need an helper that from the name of the field retrieves the right CodecModelInfo
			//			But we have to account for aliases (e.g. if a field has been serialized with another name, then we would get that at deserialization)
			FeatureCodecInfo featureCodecInfo = getFeatureCodecInfo(field, eObjCodecInfo);
			System.out.println(String.format("CodecInfo for field %s found %s", field, featureCodecInfo == null ? false : true));

			if(featureCodecInfo instanceof TypeInfo typeInfo) {
				current = new TypeCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, typeInfo).deserialize(jp, ctxt);
			}
			else if(featureCodecInfo instanceof IdentityInfo idInfo) {
				new IdCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, idInfo).deserializeAndSet(jp, current, ctxt, resource);
			} 
			else if(featureCodecInfo instanceof SuperTypeInfo superTypeInfo) {
				new SuperTypeCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, superTypeInfo).deserialize(jp, ctxt);
			} 	
			else if(featureCodecInfo != null && current != null) {
				new FeatureCodecInfoDeserializer(codecModule, codecModelInfoService, eObjCodecInfo, featureCodecInfo).deserializeAndSet(jp, current, ctxt, resource);
				
			} else if(featureCodecInfo == null && current != null) {
				//				TODO: unknown property!
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
		//
//		return buffer == null ? current : postDeserialize(buffer, current, defaultType, ctxt);
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
