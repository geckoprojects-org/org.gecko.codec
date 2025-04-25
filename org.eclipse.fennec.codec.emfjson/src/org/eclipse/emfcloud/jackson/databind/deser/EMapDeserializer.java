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
package org.eclipse.emfcloud.jackson.databind.deser;

import java.util.Map;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.databind.type.FeatureKind;
import org.eclipse.emfcloud.jackson.utils.EObjects;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

@Deprecated
public class EMapDeserializer extends ValueDeserializer<EList<Map.Entry<?, ?>>> {

   @Override
   public EList<Map.Entry<?, ?>> deserialize(final JsonParser jp, final DeserializationContext ctxt) {
      return null;
   }

   @SuppressWarnings({ "rawtypes", "unchecked" })
@Override
   public EList<Map.Entry<?, ?>> deserialize(final JsonParser jp, final DeserializationContext ctxt,
      final EList<Map.Entry<?, ?>> intoValue)  {
      EReference reference = EMFContext.getReference(ctxt);
      EStructuralFeature valueFeature = null;
      if (reference != null) {
         EClass referenceType = reference.getEReferenceType();
         valueFeature = referenceType.getEStructuralFeature("value");

      }


      if (jp.currentToken() == JsonToken.START_OBJECT) {
    	  final EObject parent = EMFContext.getParent(ctxt);
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				if (parent != null) {
					EMFContext.setParent(ctxt, parent);
				}
				if (valueFeature != null) {
					EMFContext.setFeature(ctxt, valueFeature);
				}
				String key = jp.currentName();
				jp.nextToken();

				final Object value;
				if (jp.currentToken() == JsonToken.START_OBJECT) {
					if (valueFeature != null && FeatureKind.get(valueFeature) == FeatureKind.MAP) {
						EMap eMap = new BasicEMap<>();
						deserialize(jp, ctxt, eMap);
						value = eMap;
					} else {
						value = ctxt.readValue(jp, EObject.class);
					}
				} else {
					value = ctxt.readValue(jp, Object.class);
				}

				// Dynamic objects do not use the EMap interface
				// but store entries in a DynamicEList instead.
//            if (intoValue instanceof EMap ) {
//               ((EMap) intoValue).put(key, value);
//            } else if (reference != null) {
				intoValue.add((Map.Entry<?, ?>) EObjects.createEntry(key, value, reference.getEReferenceType()));
//            }
			}
      }

      return intoValue;
   }

}
