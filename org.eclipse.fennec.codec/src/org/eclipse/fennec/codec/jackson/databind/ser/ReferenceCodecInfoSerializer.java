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
package org.eclipse.fennec.codec.jackson.databind.ser;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.info.codecinfo.CodecInfoHolder;
import org.eclipse.fennec.codec.info.codecinfo.CodecValueWriter;
import org.eclipse.fennec.codec.info.codecinfo.EClassCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.FeatureCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.InfoType;
import org.eclipse.fennec.codec.jackson.databind.CodecWriteContext;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.DatabindContext;
import tools.jackson.databind.SerializationContext;

/**
 * Codec Serializer for References
 * 
 * @author ilenia
 * @since Aug 22, 2024
 */
public class ReferenceCodecInfoSerializer implements CodecInfoSerializer {

	private static final Logger LOGGER = Logger.getLogger(ReferenceCodecInfoSerializer.class.getName());

	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private EClassCodecInfo eObjCodecInfo;
	private FeatureCodecInfo featureCodecInfo;

	public ReferenceCodecInfoSerializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService,
			final EClassCodecInfo eObjCodecInfo, final FeatureCodecInfo featureCodecInfo) {
		this.codecModule = codecMoule;
		this.codecModelInfoService = codecModelInfoService;
		this.eObjCodecInfo = eObjCodecInfo;
		this.featureCodecInfo = featureCodecInfo;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.ser.CodecInfoSerializer#serialize(org.eclipse.emf.ecore.EObject, tools.jackson.core.JsonGenerator, tools.jackson.databind.SerializationContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void serialize(EObject rootObj, JsonGenerator jg, SerializationContext provider) {
		if (featureCodecInfo.isIgnore())
			return;
		if (featureCodecInfo.getFeatures().size() != 1) {
			LOGGER.warning(
					"Currently no support for multiple EStructuralFeature in CodecInfoObject which is not a CodecIdInfo");
			return;
		}
		EReference feature = (EReference) featureCodecInfo.getFeatures().get(0);
		EMFContext.setParent(provider, rootObj);
		EMFContext.setFeature(provider, feature);

		if (jg.streamWriteContext() instanceof CodecWriteContext cwt) {
			cwt.setFeature(feature);
		}

		if (feature.isMany()) {
			List<EObject> values = (List<EObject>) rootObj.eGet(feature);
			serializeManyReferences(rootObj, values, feature, jg, provider);
		} else {
			EObject value = (EObject) rootObj.eGet(feature);
			serializeSingleReference(rootObj, value, feature, jg, provider);
		}
	}

	private void serializeManyReferences(EObject rootObj, List<EObject> values, EReference feature, JsonGenerator jg,
			SerializationContext provider) {

		if (values.isEmpty() && (!codecModule.isSerializeDefaultValue() || !codecModule.isSerializeEmptyValue()))
			return;
		if (codecModule.isUseNamesFromExtendedMetaData()) {
			jg.writeName(featureCodecInfo.getKey());
		} else {
			jg.writeName(feature.getName());
		}
		if (values instanceof EMap eMap) {
			serializeEMap(jg, provider, eMap);
		} else {
			serializeArray(rootObj, values, feature, jg, provider);
		}
	}

	@SuppressWarnings("unchecked")
	private void serializeEMap(JsonGenerator jg, SerializationContext provider, EMap<?, ?> eMap) {
		jg.writeStartObject();
		eMap.forEach(value -> {

			BasicEMap.Entry<String, Object> entry = (BasicEMap.Entry<String, Object>) value;
			jg.writeName(entry.getKey());
			Object v = entry.getValue();
			if (v == null) {
				jg.writeNull();
			} else if (v instanceof EObject eo) {
				if (((EObject) entry).eContainmentFeature().isContainment()) {
					new CodecEObjectSerializer(codecModule, codecModelInfoService).serialize(eo, jg, provider);
				} else {
					serializeNonContainment((EObject) entry, eo, jg, provider);
				}
			} else if (v instanceof EMap<?,?> innerMap) {
				serializeEMap(jg, provider, innerMap);
			}

		});
		jg.writeEndObject();
	}

	private void serializeArray(EObject rootObj, List<EObject> values, EReference feature, JsonGenerator jg,
			SerializationContext provider)  {
		jg.writeStartArray(values);
		values.forEach(value -> {
			serializeSingleReferenceValue(rootObj, value, feature, jg, provider);

		});
		jg.writeEndArray();
	}

	private void serializeSingleReference(EObject rootObj, EObject value, EReference feature, JsonGenerator jg,
			SerializationContext provider) {
		if (value == null && (!codecModule.isSerializeDefaultValue() || !codecModule.isSerializeNullValue()))
			return;

		if (codecModule.isUseNamesFromExtendedMetaData()) {
			jg.writeName(featureCodecInfo.getKey());
		} else {
			jg.writeName(feature.getName());
		}

		serializeSingleReferenceValue(rootObj, value, feature, jg, provider);
	}

	private void serializeSingleReferenceValue(EObject rootObj, EObject value, EReference feature, JsonGenerator jg,
			SerializationContext provider) {

		if (feature.isContainment()) {
			if (value == null)
				jg.writeNull();
			else
				new CodecEObjectSerializer(codecModule, codecModelInfoService).serialize(value, jg, provider);
		} else {
			serializeNonContainment(rootObj, value, jg, provider);
		}
	}

	@SuppressWarnings("unchecked")
	private void serializeNonContainment(EObject rootObj, EObject value, JsonGenerator jg, SerializationContext provider) {
		if (value == null) {
			jg.writeNull();
			return;
		}
		final String href = getHRef(provider, rootObj, value);

		jg.writeStartObject(value);

		if (codecModule.isSerializeType()) {
			EClassCodecInfo refClassCodecInfo = codecModule.getCodecModelInfo().getEClassCodecInfo().stream()
					.filter(ecci -> ecci.getClassifier().getName().equals(value.eClass().getName())).findFirst()
					.orElse(null);
			CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE);
			CodecValueWriter<EClass, String> writer = holder
					.getWriterByName(refClassCodecInfo != null ? refClassCodecInfo.getTypeInfo().getValueWriterName()
							: eObjCodecInfo.getTypeInfo().getValueWriterName());
			String v = writer.writeValue(value.eClass(), provider);
			jg.writeName(codecModule.getTypeKey());
			if (jg.canWriteTypeId()) {
				jg.writeTypeId(v);
			} else {
				jg.writeString(v);
			}
		}

		if (href == null) {
			jg.writeNullProperty(codecModule.getRefKey());
		} else {
			jg.writeStringProperty(codecModule.getRefKey(), href);
		}
		jg.writeEndObject();

	}

	private String getHRef(final SerializationContext ctxt, final EObject parent, final EObject value) {
		if (isExternal(ctxt, parent, value)) {

			URI targetURI = EMFContext.getURI(ctxt, value);
			URI sourceURI = EMFContext.getURI(ctxt, parent);
			URI deresolved = codecModule.getUriHandler().deresolve(sourceURI, targetURI);

			return deresolved == null ? null : deresolved.toString();

		}
		Resource resource = EMFContext.getResource(ctxt, value);
		if (resource != null) {
			return resource.getURIFragment(value);
		}

		return null;
	}

	private boolean isExternal(final DatabindContext ctxt, final EObject source, final EObject target) {
		Resource sourceResource = EMFContext.getResource(ctxt, source);

		if (target.eIsProxy() && target instanceof InternalEObject internalEObject) {
			URI uri = internalEObject.eProxyURI();

			return sourceResource != null && sourceResource.getURI() != null
					&& !sourceResource.getURI().equals(uri.trimFragment());
		}

		return sourceResource == null || sourceResource != EMFContext.getResource(ctxt, target);
	}
}
