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


//--- 6. AnotherCustomJacksonModuleProvider.java (Example of a NAMED JacksonModuleProvider) ---
//This shows how to contribute a module specifically for a named ObjectMapper.

import tools.jackson.core.Version;
import tools.jackson.databind.module.SimpleModule;
import org.osgi.service.component.annotations.Component;

/**
* An example custom Jackson Module Provider that targets a named ObjectMapper.
*/
@Component(service = JacksonModuleProvider.class, immediate = true)
public class AnotherCustomJacksonModuleProvider implements JacksonModuleProvider {

 private static final String MODULE_NAME = "AnotherCustomModule";
 public static final String CONFIG_NAME = "SpecialConfig"; // The name for this configuration

 @Override
 public SimpleModule getModule() {
     SimpleModule module = new SimpleModule(MODULE_NAME, new Version(1, 0, 0, null, "com.example", "jackson-named-module"));

     // Example: This module might add specific features for a "SpecialConfig"
     // module.addSerializer(AnotherCustomType.class, new AnotherCustomTypeSerializer());

     System.out.println("AnotherCustomJacksonModuleProvider providing module: " + MODULE_NAME + " for config: " + CONFIG_NAME);
     return module;
 }

 @Override
 public String getName() {
     return CONFIG_NAME; // Applies only to ObjectMappers requested with "SpecialConfig"
 }
}