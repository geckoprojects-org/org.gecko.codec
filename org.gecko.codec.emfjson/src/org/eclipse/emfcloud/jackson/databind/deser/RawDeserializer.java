/*******************************************************************************
 * Copyright (c) 2022 Jan Hicken.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *******************************************************************************/
package org.eclipse.emfcloud.jackson.databind.deser;

import java.io.StringWriter;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

public class RawDeserializer extends ValueDeserializer<Object> {
   @Override
   public Object deserialize(final JsonParser p, final DeserializationContext ctxt) {
      final StringWriter writer = new StringWriter();
      final JsonGenerator generator = ctxt.tokenStreamFactory().createGenerator(ObjectWriteContext.empty(), writer);
//      getCodec().getFactory().createGenerator(writer);
      final JsonNode tree = p.readValueAsTree();
      generator.writeTree(tree);

      return writer.toString();
   }

   @Override
   public boolean isCachable() {
      return true;
   }
}
