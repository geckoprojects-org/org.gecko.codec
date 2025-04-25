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
package org.eclipse.emfcloud.jackson.databind.ser;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.annotations.EcoreReferenceInfo;
import org.eclipse.emfcloud.jackson.annotations.EcoreTypeInfo;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.handlers.URIHandler;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.DatabindContext;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

@Deprecated
public class EcoreReferenceSerializer extends ValueSerializer<EObject> {

   private final EcoreReferenceInfo info;
   private final EcoreTypeInfo typeInfo;
   private final URIHandler handler;

   public EcoreReferenceSerializer(final EcoreReferenceInfo info, final EcoreTypeInfo typeInfo) {
      this.info = info;
      this.typeInfo = typeInfo;
      this.handler = info.getHandler();
   }

   @Override
   public void serialize(final EObject value, final JsonGenerator jg, final SerializationContext serializers) {
      final EObject parent = EMFContext.getParent(serializers);
      final String href = getHRef(serializers, parent, value);

      jg.writeStartObject();
      jg.writeStringProperty(typeInfo.getProperty(), typeInfo.getValueWriter().writeValue(value.eClass(), serializers));
      if (href == null) {
         jg.writeNullProperty(info.getProperty());
      } else {
         jg.writeStringProperty(info.getProperty(), href);
      }
      jg.writeEndObject();
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

   private String getHRef(final SerializationContext ctxt, final EObject parent, final EObject value) {
      if (isExternal(ctxt, parent, value)) {

         URI targetURI = EMFContext.getURI(ctxt, value);
         URI sourceURI = EMFContext.getURI(ctxt, parent);
         URI deresolved = handler != null ? handler.deresolve(sourceURI, targetURI) : targetURI;

         return deresolved == null ? null : deresolved.toString();

      }
      Resource resource = EMFContext.getResource(ctxt, value);
      if (resource != null) {
         return resource.getURIFragment(value);
      }

      return null;
   }
}
