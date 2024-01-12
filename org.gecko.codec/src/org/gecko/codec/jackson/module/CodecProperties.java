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
package org.gecko.codec.jackson.module;

import static java.util.Objects.requireNonNull;

import java.util.Map;

/**
 * 
 * @author mark
 * @since 11.01.2024
 */
public enum CodecProperties {
	
	TYPE_KEY("_type"),
	SUPER_TYPE_KEY("_supertypes"),
	ID_KEY("_id"),
	PROXY_KEY("_proxy"),
	TIMESTAMP("_timestamp");
	
	private String key;

	/**
	 * Creates a new instance.
	 */
	private CodecProperties(String key) {
		this.key = key;
	}
	
	public String getKeyValue() {
		return key;
	}
	
	public CodecProperties append(Map<String, Object> properties) {
		requireNonNull(properties);
		properties.put(this.name(), this.getKeyValue());
		return this;
	}

}
