/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * 
 * @author ilenia
 * @since Apr 24, 2025
 */
public class EcoreReferenceDeserializer extends ValueDeserializer<ReferenceEntry>{
	
	private CodecModule codecModule;

	public EcoreReferenceDeserializer(CodecModule codecModule) {
		this.codecModule = codecModule;
		
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ValueDeserializer#deserialize(tools.jackson.core.JsonParser, tools.jackson.databind.DeserializationContext)
	 */
	@Override
	public ReferenceEntry deserialize(JsonParser jp, DeserializationContext ctxt) throws JacksonException {
		EObject parent = EMFContext.getParent(ctxt);
	      EReference reference = EMFContext.getReference(ctxt);

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

	      return id != null ? new ReferenceEntry.Base(parent, reference, id, type) : null;
	}

}
