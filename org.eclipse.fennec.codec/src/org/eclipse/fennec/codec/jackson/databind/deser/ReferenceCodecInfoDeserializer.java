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

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.info.codecinfo.CodecInfoHolder;
import org.eclipse.fennec.codec.info.codecinfo.CodecValueReader;
import org.eclipse.fennec.codec.info.codecinfo.EClassCodecInfo;
import org.eclipse.fennec.codec.info.codecinfo.InfoType;
import org.eclipse.fennec.codec.info.codecinfo.TypeInfo;
import org.eclipse.fennec.codec.jackson.databind.EMFCodecReadContext;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.TokenStreamContext;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Codec Deserializer for References
 * @author ilenia
 * @since Sep 30, 2024
 */
public class ReferenceCodecInfoDeserializer extends ValueDeserializer<EObject> implements CodecInfoDeserializer {

	private final CodecModule codecModule;
	private final CodecModelInfo codecModelInfoService;
	private final TypeInfo typeCodecInfo;

	public ReferenceCodecInfoDeserializer(CodecModule codecModule, CodecModelInfo codecModelInfoService, TypeInfo typeCodecInfo) {
		this.codecModule = codecModule;
		this.codecModelInfoService = codecModelInfoService;
		this.typeCodecInfo = typeCodecInfo;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public EObject deserialize(JsonParser jp, DeserializationContext ctxt) {
//		EObject parent = EMFContext.getParent(ctxt);
//		EReference reference = EMFContext.getReference(ctxt);
		
		EMFCodecReadContext codecReadCtxt = null;
		if(jp.streamReadContext() instanceof EMFCodecReadContext && jp.streamReadContext() instanceof TokenStreamContext crc && crc.getParent() != null) {
			if(crc.getParent().inObject()) {
				codecReadCtxt = (EMFCodecReadContext) crc.getParent();
			} else if(crc.getParent().inArray()) {
				codecReadCtxt = (EMFCodecReadContext) crc.getParent().getParent();
			}
			
		}
		
		EObject parent = codecReadCtxt != null ? codecReadCtxt.getCurrentEObject() : null;
		EReference reference = codecReadCtxt != null ? (EReference) codecReadCtxt.getCurrentFeature() : null;
		Resource resource = codecReadCtxt != null ? codecReadCtxt.getResource() : null;
		
//		TODO: we could try to retrieve the typeCodecInfo from the context, because at this point we do not have it if we construct this deserializer from the module
		
		String id = null;
		String type = null;

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			final String field = jp.currentName();

			if (field.equalsIgnoreCase(codecModule.getRefKey())) {
				id = jp.nextStringValue();

			} else if (field.equalsIgnoreCase(codecModule.getTypeKey())) {
				type = jp.nextStringValue();
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
		EObject ref = null;
		if (id != null && eClass != null) {
			URI baseURI = resource.getURI().trimFragment();
            URI uri = codecModule.getUriHandler().resolve(baseURI, URI.createURI(id));
			ref = codecModule.getProxyFactory().createProxy(eClass, uri);
			if(reference.isMany()) {
				Collection<EObject> objs = (Collection<EObject>) parent.eGet(reference);
				objs.add(ref);
			} else {
				parent.eSet(reference, ref);
			}			
		}
		return ref;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.deser.CodecInfoDeserializer#deserializeAndSet(com.fasterxml.jackson.core.JsonParser, org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.databind.DeserializationContext, org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public void deserializeAndSet(JsonParser jp, EObject current, DeserializationContext ctxt, Resource resource) {
		deserialize(jp, ctxt);
	}
}
