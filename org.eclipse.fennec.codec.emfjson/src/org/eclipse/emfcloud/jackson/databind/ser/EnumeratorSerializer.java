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

import org.eclipse.emf.common.util.Enumerator;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

@Deprecated
public class EnumeratorSerializer extends ValueSerializer<Enumerator> {

   @Override
   public void serialize(final Enumerator value, final JsonGenerator jg, final SerializationContext provider) {
      jg.writeString(value.getLiteral());
   }

   @Override
   public Class<Enumerator> handledType() {
      return Enumerator.class;
   }

}
