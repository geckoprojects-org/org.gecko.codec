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

//--- 5. CustomJacksonModuleProvider.java (Example Implementation of JacksonModuleProvider) ---
//This is an example of a bundle contributing a custom Jackson module.


import tools.jackson.core.Version;
import tools.jackson.databind.module.SimpleModule;
import org.osgi.service.component.annotations.Component;

/**
* An example custom Jackson Module Provider.
* This module could register custom serializers/deserializers.
*/
@Component(service = JacksonModuleProvider.class, immediate = true)
public class CustomJacksonModuleProvider implements JacksonModuleProvider {

 private static final String MODULE_NAME = "CustomModule";

 @Override
 public SimpleModule getModule() {
     SimpleModule module = new SimpleModule(MODULE_NAME, new Version(1, 0, 0, null, "com.example", "jackson-custom-module"));

     // Example: Register a custom serializer/deserializer here
     // For demonstration, let's say we have a custom type 'MyCustomType'
     // module.addSerializer(MyCustomType.class, new MyCustomTypeSerializer());
     // module.addDeserializer(MyCustomType.class, new MyCustomTypeDeserializer());

     System.out.println("CustomJacksonModuleProvider providing module: " + MODULE_NAME);
     return module;
 }

 @Override
 public String getName() {
     // Return null to apply to the default ObjectMapper, or a specific name
     // for a named ObjectMapper configuration.
     return null; // Applies to the default ObjectMapper
 }
}