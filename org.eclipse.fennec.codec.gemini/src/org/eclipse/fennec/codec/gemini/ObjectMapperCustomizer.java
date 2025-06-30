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

/**
 * 
 * @author ilenia
 * @since Jun 27, 2025
 */
//--- 3. ObjectMapperCustomizer.java (NEW OSGi Service Interface) ---
//This new interface allows other OSGi bundles to customize the ObjectMapper.Builder.


import tools.jackson.databind.json.JsonMapper;

/**
* OSGi service interface for customizing Jackson's JsonMapper.Builder.
* Bundles wishing to apply custom configurations (like enabling/disabling features)
* to the ObjectMapper should register implementations of this service.
*/
public interface ObjectMapperCustomizer {

 /**
  * Applies custom configurations to the provided JsonMapper.Builder.
  * This method will be called before the ObjectMapper is built.
  *
  * @param builder The JsonMapper.Builder instance to customize.
  */
 void customize(JsonMapper.Builder builder);

 /**
  * Returns an optional name for this customizer.
  * If a name is provided, the customizer will only be applied to ObjectMappers
  * requested with that specific name using getObjectMapper(name).
  * If null or empty, the customizer will be applied to the default ObjectMapper
  * and any unnamed ObjectMapper instances.
  * @return The name of the customizer, or null for default application.
  */
 String getName();
}
