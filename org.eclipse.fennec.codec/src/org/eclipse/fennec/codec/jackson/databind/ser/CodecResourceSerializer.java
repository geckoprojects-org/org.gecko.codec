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
package org.eclipse.fennec.codec.jackson.databind.ser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * 
 * @author ilenia
 * @since Apr 24, 2025
 */
public class CodecResourceSerializer extends ValueSerializer<Resource> {

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ValueSerializer#serialize(java.lang.Object, tools.jackson.core.JsonGenerator, tools.jackson.databind.SerializationContext)
	 */
	@Override
	public void serialize(final Resource value, final JsonGenerator jg, final SerializationContext provider) {
		if (value.getContents().size() == 1) {
			serializeOne(value.getContents().get(0), jg, provider);
		} else {
			jg.writeStartArray();
			for (EObject o : value.getContents()) {
				serializeOne(o, jg, provider);
			}
			jg.writeEndArray();
		}
	}	   

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ValueSerializer#handledType()
	 */
	@Override
	public Class<Resource> handledType() {
		return Resource.class;
	}

	private void serializeOne(final EObject object, final JsonGenerator jg, final SerializationContext provider) {
		final JavaType type = provider.constructType(object.getClass());
		final ValueSerializer<Object> serializer = provider.findValueSerializer(type);

		if (serializer != null) {
			serializer.serialize(object, jg, provider);
		}
	}

}
