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
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.handlers.BaseURIHandler;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;

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
		EObject value = (EObject) rootObj.eGet(feature);
		EMFContext.setParent(provider, rootObj);
		EMFContext.setFeature(provider, feature);

		if(codecModule.isUseNamesFromExtendedMetaData()) {
			jg.writeFieldName(featureCodecInfo.getKey() != null ? featureCodecInfo.getKey() : feature.getName());
		} else {
			jg.writeFieldName(feature.getName());
		}	

		if(feature.isContainment()) {
			new CodecEObjectSerializerNew(codecModule, codecModelInfoService).serialize(value, jg, provider);
			return;
		}
		final String href = getHRef(provider, rootObj, value);

		jg.writeStartObject();

		CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE);
		CodecValueWriter<EClass, String> writer = holder.getWriterByName(eObjCodecInfo.getTypeInfo().getValueWriterName());
		String v = writer.writeValue(value.eClass(), provider);
		jg.writeFieldName(codecModule.getTypeKey());
		if (jg.canWriteTypeId()) {
			jg.writeTypeId(v);
		} else {
			jg.writeString(v);
		}
		//       jg.writeStringField(codecModule.getTypeKey(), typeInfo.getValueWriter().writeValue(value.eClass(), provider));
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
