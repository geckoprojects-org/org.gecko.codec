/*******************************************************************************
 * Copyright (c) 2019-2022 Guillaume Hillairet and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *******************************************************************************/
package org.eclipse.emfcloud.jackson.databind.deser;

import static org.eclipse.emfcloud.jackson.databind.EMFContext.getFeature;
import static org.eclipse.emfcloud.jackson.databind.EMFContext.getResource;
import static tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.databind.property.EObjectProperty;
import org.eclipse.emfcloud.jackson.databind.property.EObjectPropertyMap;
import org.eclipse.emfcloud.jackson.databind.property.EObjectTypeProperty;
import org.eclipse.emfcloud.jackson.errors.JSONException;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.util.TokenBuffer;

public class EObjectDeserializer extends ValueDeserializer<EObject> {

	private final EObjectPropertyMap.Builder builder;
	private final Class<?> currentType;

	public EObjectDeserializer(final EObjectPropertyMap.Builder builder, final Class<?> currentType) {
		this.builder = builder;
		this.currentType = currentType;
	}

	@Override
	public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt)  {
		EMFContext.prepare(ctxt);

		final Resource resource = getResource(ctxt);
		final EStructuralFeature feature = getFeature(ctxt);
		final EClass defaultType = getDefaultType(ctxt);

		EObject current = null;
		EObjectPropertyMap propertyMap;

		if (feature == null && defaultType != null) {
			propertyMap = builder.construct(ctxt, defaultType);
		} else if (feature instanceof EReference) {
			final EObject parent = EMFContext.getParent(ctxt);
			final EClass resolvedType;
			if (parent == null) {
				resolvedType = ((EReference) feature).getEReferenceType();
			} else {
				resolvedType = (EClass) EcoreUtil.getReifiedType(parent.eClass(), feature.getEGenericType()).getERawType();
			}

			propertyMap = builder.construct(ctxt, resolvedType);
		} else {
			propertyMap = builder.constructDefault(ctxt);
		}

		TokenBuffer buffer = null;
		JsonToken nextToken = jp.nextToken();
		while (nextToken != JsonToken.END_OBJECT && nextToken != null) {
			final String field = jp.currentName();
			final EObjectProperty property = propertyMap.findProperty(field);

			if (property instanceof EObjectTypeProperty) {
				current = property.deserialize(jp, ctxt);
				if (current != null) {
					propertyMap = builder.construct(ctxt, current.eClass());
				}
			} else if (property != null && current != null) {
				property.deserializeAndSet(jp, current, ctxt, resource);
			} else if (property == null && current != null) {

				handleUnknownProperty(jp, resource, ctxt, current.eClass());

			} else {
				if (buffer == null) {
					buffer = TokenBuffer.forBuffering(jp, ctxt);
				}
				buffer.copyCurrentStructure(jp);
			}

			nextToken = jp.nextToken();
		}

		// handle empty objects
		if (buffer == null && current == null && defaultType != null) {
			return EcoreUtil.create(defaultType);
		}

		return buffer == null ? current : postDeserialize(buffer, current, defaultType, ctxt);
	}

	@Override
	public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt, final EObject intoValue) {
		if (intoValue == null) {
			return null;
		}

		EMFContext.prepare(ctxt);
		EObjectPropertyMap propertyMap = builder.construct(ctxt, intoValue.eClass());

		final Resource resource = getResource(ctxt);

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			final String field = jp.currentName();
			final EObjectProperty property = propertyMap.findProperty(field);
			if (property != null) {
				property.deserializeAndSet(jp, intoValue, ctxt, resource);
			} else {
				handleUnknownProperty(jp, resource, ctxt, intoValue.eClass());
			}
		}

		return intoValue;
	}

	private EObject postDeserialize(final TokenBuffer buffer, EObject object, final EClass defaultType,
			final DeserializationContext ctxt) {
		if (object == null && defaultType == null) {
			return null;
		}

		Resource resource = getResource(ctxt);
		JsonParser jp = buffer.asParser();
		JsonNode tree = jp.readValueAsTree();
		jp.close();

		EObjectPropertyMap propertyMap = builder.find(ctxt, defaultType, tree.propertyNames());
		EObjectTypeProperty typeProperty = propertyMap.getTypeProperty();

		if (typeProperty != null) {
			JsonNode value = tree.get(typeProperty.getFieldName());

			if (value != null) {
				object = typeProperty.create(value.asString(), ctxt);
			}
		}

		if (object == null) {
			object = EcoreUtil.create(defaultType);
		}

		// TODO explain that
		propertyMap = builder.construct(ctxt, object.eClass());

		jp = buffer.asParser();
		JsonToken nextToken = jp.nextToken();
		while (nextToken != JsonToken.END_OBJECT && nextToken != null) {
			final String field = jp.currentName();
			final EObjectProperty property = propertyMap.findProperty(field);

			if (property != null) {
				property.deserializeAndSet(jp, object, ctxt, resource);
			} else {
				handleUnknownProperty(jp, resource, ctxt, object.eClass());
			}

			nextToken = jp.nextToken();
		}

		jp.close();
		buffer.close();
		return object;
	}

	private void handleUnknownProperty(final JsonParser jp, final Resource resource, final DeserializationContext ctxt, EClass currentEClass) {
		if (resource != null && ctxt.getConfig().hasDeserializationFeatures(FAIL_ON_UNKNOWN_PROPERTIES.getMask())) {
			
			resource.getErrors().add(new JSONException(String.format("Unknown feature '%s' for %s" , jp.currentName(), EcoreUtil.getURI(currentEClass)), jp.currentLocation()));
		}
		// we didn't find a feature so consume
		// the field and move on
		jp.nextToken();
		jp.skipChildren();
	}

	@Override
	public boolean isCachable() { return true; }

	@Override
	public Class<?> handledType() {
		return EObject.class;
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
