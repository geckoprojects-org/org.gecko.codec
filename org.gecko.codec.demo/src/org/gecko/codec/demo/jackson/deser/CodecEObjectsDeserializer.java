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
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @author ilenia
 * @since Oct 2, 2024
 */
public class CodecEObjectsDeserializer extends JsonDeserializer<Collection<EObject>> {

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public Collection<EObject> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JacksonException {
		System.out.println("-------------------CodecEObjectsDeserializer----------------------");
		return null;
	}

}
