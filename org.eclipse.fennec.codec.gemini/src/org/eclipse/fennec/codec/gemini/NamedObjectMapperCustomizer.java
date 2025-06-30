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

//--- 8. NamedObjectMapperCustomizer.java (NEW Example of a NAMED ObjectMapperCustomizer) ---
//This shows how to contribute a customizer specifically for a named ObjectMapper.


import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.SerializationFeature; // Example: SerializationFeature
import org.osgi.service.component.annotations.Component;

/**
* An example custom ObjectMapperCustomizer that targets a named ObjectMapper.
*/
@Component(service = ObjectMapperCustomizer.class, immediate = true)
public class NamedObjectMapperCustomizer implements ObjectMapperCustomizer {

 public static final String CONFIG_NAME = "SpecialConfig"; // The name for this configuration

 @Override
 public void customize(JsonMapper.Builder builder) {
     System.out.println("Applying NamedObjectMapperCustomizer to ObjectMapper builder for config: " + CONFIG_NAME);
     // This customizer might enforce strict serialization for a "SpecialConfig"
     builder.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
     builder.configure(SerializationFeature.INDENT_OUTPUT, true); // Override default for named config
 }

 @Override
 public String getName() {
     return CONFIG_NAME; // Apply only to ObjectMappers requested with "SpecialConfig"
 }
}

