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


//--- 9. ResourceService.java (Example of a Consumer Bundle Service Interface) ---
//This service would typically use the ObjectMapper to load/save resources.


import tools.jackson.databind.JsonNode;

/**
* Example service interface that consumes the JacksonExtenderService.
* This service would load/save resources using Jackson.
*/
public interface ResourceService {

 /**
  * Loads a resource and returns it as a JsonNode.
  * @param resourcePath The path to the resource.
  * @return The loaded resource as a JsonNode.
  * @throws Exception If loading fails.
  */
 JsonNode loadResource(String resourcePath) throws Exception;

 /**
  * Saves a resource from a JsonNode.
  * @param resourcePath The path to save the resource.
  * @param data The JsonNode data to save.
  * @throws Exception If saving fails.
  */
 void saveResource(String resourcePath, JsonNode data) throws Exception;
}

