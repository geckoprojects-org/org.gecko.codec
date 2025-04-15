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

package org.eclipse.emfcloud.jackson.support;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.module.EMFModule;

import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public class Utils {

	public static String uriOf(final EObject object) {
		return EcoreUtil.getURI(object).toString();
	}

	public static ObjectMapper createDefaultMapper() {
		JsonMapper.Builder mapperBuilder = JsonMapper.builder(JsonFactory.builder().build());
		EMFModule module = new EMFModule();
		mapperBuilder.addModule(module);
		return mapperBuilder.build();
	}
	
	public static ObjectMapper createMapper(EMFModule module) {
		JsonMapper.Builder mapperBuilder = JsonMapper.builder(JsonFactory.builder().build());
		mapperBuilder.addModule(module);
		return mapperBuilder.build();
	}

}
