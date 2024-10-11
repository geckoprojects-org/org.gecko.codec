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

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntries;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.TypeInfo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * 
 * @author ilenia
 * @since Sep 30, 2024
 */
public class ReferenceCodecInfoDeserializer implements CodecInfoDeserializer {

	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfoService;
	private final TypeInfo typeCodecInfo;

	public ReferenceCodecInfoDeserializer(CodecModule codecModule, CodecModelInfo codecModelInfoService, TypeInfo typeCodecInfo) {
		this.codecModule = codecModule;
		this.codecModelInfoService = codecModelInfoService;
		this.typeCodecInfo = typeCodecInfo;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.deser.CodecInfoDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public EObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.deser.CodecInfoDeserializer#deserializeAndSet(com.fasterxml.jackson.core.JsonParser, org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.databind.DeserializationContext, org.eclipse.emf.ecore.resource.Resource)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deserializeAndSet(JsonParser jp, EObject current, DeserializationContext ctxt, Resource resource)
			throws IOException {
			
		EObject parent = EMFContext.getParent(ctxt);
		EReference reference = EMFContext.getReference(ctxt);
	
		String id = null;
		String type = null;

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			final String field = jp.getCurrentName();

			if (field.equalsIgnoreCase(codecModule.getRefKey())) {
				id = jp.nextTextValue();

			} else if (field.equalsIgnoreCase(codecModule.getTypeKey())) {
				type = jp.nextTextValue();
			}
		}
		EClass eClass = null;
		EClassCodecInfo refClassCodecInfo = codecModule.getCodecModelInfo().getEClassCodecInfo().stream()
				.filter(ecci -> ecci.getClassifier().getName().equals(reference.getEType().getName()))
				.findFirst().orElse(null);
		
		CodecInfoHolder infoHolder = codecModelInfoService.getCodecInfoHolderByType(InfoType.TYPE);
		CodecValueReader<String, EClass> valueReader = infoHolder.getReaderByName(refClassCodecInfo != null ? refClassCodecInfo.getTypeInfo().getValueReaderName() : typeCodecInfo.getValueReaderName());
		if(type != null) {
			eClass = valueReader.readValue(type, ctxt);
		}
//		If there is no type info in the serialized document
		if(type == null && reference.getEType() instanceof EClass refEClass) {
			eClass = refEClass;
		}
		if (id != null && eClass != null) {
			EObject ref = createProxy(eClass, URI.createURI(id));
			current.eSet(reference, ref);
		}
		ReferenceEntries entries = EMFContext.getEntries(ctxt);
		Object value = id != null ? new ReferenceEntry.Base(parent, reference, id, eClass.getInstanceClassName()) : null;
		if (entries != null && value instanceof ReferenceEntry) {
			entries.entries().add((ReferenceEntry) value);
		}	
	}

	private EObject createProxy(EClass eClass, URI uri) {
		EObject object = EcoreUtil.create(eClass);
		if (object instanceof InternalEObject) {
			((InternalEObject) object).eSetProxyURI(uri);
		}
		return object;
	}

}
