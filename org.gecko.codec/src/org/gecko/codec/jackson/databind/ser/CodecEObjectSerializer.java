/*******************************************************************************
 * Copyright (c) 2019-2021 Guillaume Hillairet and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *******************************************************************************/
package org.gecko.codec.jackson.databind.ser;

import static org.eclipse.emfcloud.jackson.databind.EMFContext.getParent;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.databind.property.EObjectProperty;
import org.eclipse.emfcloud.jackson.databind.property.EObjectPropertyMap;
import org.eclipse.emfcloud.jackson.databind.ser.EObjectSerializer;
import org.eclipse.emfcloud.jackson.utils.EObjects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CodecEObjectSerializer extends EObjectSerializer {

	private final JsonSerializer<EObject> refSerializer;
	private final EObjectPropertyMap.Builder builder;

	public CodecEObjectSerializer(final EObjectPropertyMap.Builder builder, final JsonSerializer<EObject> serializer) {
		super(builder, serializer);
		this.builder = builder;
		this.refSerializer = serializer;
	}

	@Override
	public Class<EObject> handledType() {
		return EObject.class;
	}

	@Override
	public void serialize(final EObject object, final JsonGenerator jg, final SerializerProvider provider)
			throws IOException {
		EObjectPropertyMap properties = builder.construct(provider, object.eClass());

		final EObject parent = getParent(provider);
		
		if (parent != null && (object.eIsProxy() || EObjects.isContainmentProxy(provider, parent, object))) {
			// containment proxies are serialized as references
			refSerializer.serialize(object, jg, provider);
			return;
		}

		jg.writeStartObject(object);
		for (EObjectProperty property : properties.getProperties()) {
			property.serialize(object, jg, provider);
		}
		jg.writeEndObject();
	}

}