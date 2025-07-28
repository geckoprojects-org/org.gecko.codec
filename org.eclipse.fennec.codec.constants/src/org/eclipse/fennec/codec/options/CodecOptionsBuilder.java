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
package org.eclipse.fennec.codec.options;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author ilenia
 * @since Jul 28, 2025
 */
public class CodecOptionsBuilder {
	
	Map<String, Object> optionsMap = new HashMap<>();
	
	public CodecOptionsBuilder with(String key, Object value) {
		optionsMap.put(key, value);
		return this;
	}
	
	public Map<String, Object> build() {
		return optionsMap;
	}

}
