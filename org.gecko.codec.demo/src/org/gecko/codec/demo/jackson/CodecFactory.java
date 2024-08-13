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

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import com.fasterxml.jackson.core.JsonFactory;

/**
 * This is our {@link JsonFactory} as configurable service.
 * The idea is that in this way we can configure {@link JsonFactory.Feature} and
 * {@link Mapper.Feature} as well.
 * 
 * @author ilenia
 * @since Aug 12, 2024
 */
@Component(name = "CodecFactory", service = CodecFactory.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class CodecFactory extends JsonFactory {

	/** serialVersionUID */
	private static final long serialVersionUID = 1610275007161521907L;
	
	private Map<String, Object> properties;
	
	@Activate
	public void activate(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	public Map<String, Object> getProperties() {
		return Map.copyOf(properties);
	}
}
