/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.codec.demo.jackson;

import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.info.CodecModelInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
@Component(name="DemoModule", service = Module.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class DemoModule extends SimpleModule {
	
	@Reference
	private PersonPackage personPackage;
	@Reference
	private CodecModelInfo modelInfo;

}
