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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.handlers.BaseURIHandler;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.jackson.databind.CodecWriteContext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author ilenia
 * @since Aug 22, 2024
 */
public class ReferenceCodecInfoSerializer implements CodecInfoSerializer {

	private final static Logger LOGGER = Logger.getLogger(ReferenceCodecInfoSerializer.class.getName());

	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private EClassCodecInfo eObjCodecInfo;
	private FeatureCodecInfo featureCodecInfo;
	private BaseURIHandler handler = new BaseURIHandler();

	public ReferenceCodecInfoSerializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final FeatureCodecInfo featureCodecInfo) {
		this.codecModule = codecMoule;
		this.codecModelInfoService = codecModelInfoService;
		this.eObjCodecInfo = eObjCodecInfo;
		this.featureCodecInfo = featureCodecInfo;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.CodecInfoSerializer#serialize(org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void serialize(EObject rootObj, JsonGenerator jg, SerializerProvider provider) throws IOException {
		if(featureCodecInfo.isIgnore()) return;
		if(featureCodecInfo.getFeatures().size() != 1) {
			LOGGER.warning(String.format("Currently no support for multiple EStructuralFeature in CodecInfoObject which is not a CodecIdInfo"));
			return;
		}
		EReference feature = (EReference) featureCodecInfo.getFeatures().get(0);
		EMFContext.setParent(provider, rootObj);
		EMFContext.setFeature(provider, feature);
		
		if(jg.getOutputContext() instanceof CodecWriteContext cwt) {
			cwt.setFeature(feature);
		}

		if(feature.isMany()) {
			List<EObject> values = (List<EObject>) rootObj.eGet(feature);
			serializeManyReferences(rootObj, values, feature, jg, provider);
		} else {
			EObject value = (EObject) rootObj.eGet(feature);
			serializeSingleReference(rootObj, value, feature, jg, provider);
		}
	}


	private void serializeManyReferences(EObject rootObj, List<EObject> values, EReference feature, JsonGenerator jg,
			SerializerProvider provider) throws IOException {

		if(values.isEmpty() && (!codecModule.isSerializeDefaultValue() || !codecModule.isSerializeEmptyValue())) return;
		if(codecModule.isUseNamesFromExtendedMetaData()) {
			jg.writeFieldName(featureCodecInfo.getKey());
		} else {
			jg.writeFieldName(feature.getName());
		}	
		jg.writeStartArray(values);
		values.forEach(value -> {
			try {
				serializeSingleReferenceValue(rootObj, value, feature, jg, provider);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		jg.writeEndArray();

	}

	private void serializeSingleReference(EObject rootObj, EObject value, EReference feature, JsonGenerator jg, SerializerProvider provider) throws IOException {
		if(value == null && (!codecModule.isSerializeDefaultValue() || !codecModule.isSerializeNullValue())) return;

		if(codecModule.isUseNamesFromExtendedMetaData()) {
			jg.writeFieldName(featureCodecInfo.getKey());
		} else {
			jg.writeFieldName(feature.getName());
		}	

		serializeSingleReferenceValue(rootObj, value, feature, jg, provider);
	}

	private void serializeSingleReferenceValue(EObject rootObj, EObject value, EReference feature, JsonGenerator jg, SerializerProvider provider) throws IOException {

		if(feature.isContainment()) {
			if(value == null) jg.writeNull();
			else new CodecEObjectSerializerNew(codecModule, codecModelInfoService).serialize(value, jg, provider);
		} else {
			serializeNonContainment(rootObj, value, feature, jg, provider);
		}
	}

	@SuppressWarnings("unchecked")
	private void serializeNonContainment(EObject rootObj, EObject value, EReference feature, JsonGenerator jg, SerializerProvider provider) throws IOException {	
		if(value == null) {
			jg.writeNull();
			return;
		}
		final String href = getHRef(provider, rootObj, value);

		jg.writeStartObject(value);

		if(codecModule.isSerializeType()) {
			EClassCodecInfo refClassCodecInfo = codecModule.getCodecModelInfo().getEClassCodecInfo().stream()
					.filter(ecci -> ecci.getClassifier().getName().equals(value.eClass().getName()))
					.findFirst().orElse(null);
			CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE);
			CodecValueWriter<EClass, String> writer = holder.getWriterByName(refClassCodecInfo != null ? 
					refClassCodecInfo.getTypeInfo().getValueWriterName() : eObjCodecInfo.getTypeInfo().getValueWriterName());
			String v = writer.writeValue(value.eClass(), provider);
			jg.writeFieldName(codecModule.getTypeKey());
			if (jg.canWriteTypeId()) {
				jg.writeTypeId(v);
			} else {
				jg.writeString(v);
			}
		}

		if (href == null) {
			jg.writeNullField(codecModule.getRefKey());
		} else {
			jg.writeStringField(codecModule.getRefKey(), href);
		}
		jg.writeEndObject();

	}

	private String getHRef(final SerializerProvider ctxt, final EObject parent, final EObject value) {
		if (isExternal(ctxt, parent, value)) {

			URI targetURI = EMFContext.getURI(ctxt, value);
			URI sourceURI = EMFContext.getURI(ctxt, parent);
			URI deresolved = handler.deresolve(sourceURI, targetURI);

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

		if (target.eIsProxy() && target instanceof InternalEObject) {
			URI uri = ((InternalEObject) target).eProxyURI();

			return sourceResource != null
					&& sourceResource.getURI() != null
					&& !sourceResource.getURI().equals(uri.trimFragment());
		}

		return sourceResource == null || sourceResource != EMFContext.getResource(ctxt, target);
	}
}
