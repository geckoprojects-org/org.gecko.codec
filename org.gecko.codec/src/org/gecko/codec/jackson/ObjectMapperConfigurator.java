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
package org.gecko.codec.jackson;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 * @author ilenia
 * @since Aug 14, 2024
 */
@Component(name = "ObjectMapperConfigurator", service = ObjectMapperConfigurator.class, 
configurationPolicy = ConfigurationPolicy.REQUIRE, property = {"type=json"})
public class ObjectMapperConfigurator {
	
	@Reference(target="(type=json)")
	CodecFactoryConfigurator codecFactoryConfigurator;


	private Map<String, Object> properties;
	
	@Activate
	public void activate(Map<String, Object> properties) {
		this.properties = properties;
	}

	public ObjectMapperBuilderFactory getObjMapperBuilderFactory() {
		DefaultObjectMapperBuilderFactory factory = new DefaultObjectMapperBuilderFactory(properties, codecFactoryConfigurator);
		return factory;
	}
	
	
}
