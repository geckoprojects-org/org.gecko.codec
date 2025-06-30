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
package org.eclipse.fennec.codec.gemini;

//--- 7. CustomObjectMapperCustomizer.java (NEW Example Implementation of ObjectMapperCustomizer) ---
//This demonstrates how to configure ObjectMapper features.


import tools.jackson.databind.json.JsonMapper;
import tools.jackson.core.StreamWriteFeature; // Example: StreamWriteFeature
import tools.jackson.databind.MapperFeature; // Example: MapperFeature
import org.osgi.service.component.annotations.Component;

/**
 * An example custom ObjectMapperCustomizer.
 * This customizer can enable/disable various ObjectMapper features.
 */
@Component(service = ObjectMapperCustomizer.class, immediate = true)
public class CustomObjectMapperCustomizer implements ObjectMapperCustomizer {

	@Override
	public void customize(JsonMapper.Builder builder) {
		System.out.println("Applying CustomObjectMapperCustomizer to ObjectMapper builder (default).");
		// Example: Enable StreamWriteFeature.WRITE_BIGDECIMAL_AS_PLAIN
		builder.enable(StreamWriteFeature.WRITE_BIGDECIMAL_AS_PLAIN);

		// Example: Disable MapperFeature.AUTO_DETECT_FIELDS
		builder.disable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

		// You can also enable/disable other features:
		// builder.enable(SerializationFeature.WRAP_ROOT_VALUE);
		// builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// builder.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS);
	}

	@Override
	public String getName() {
		return null; // Apply to the default ObjectMapper
	}
}

