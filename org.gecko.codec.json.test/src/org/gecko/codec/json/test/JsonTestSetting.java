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
package org.gecko.codec.json.test;

import java.io.File;
import java.util.UUID;

/**
 * 
 * @author ilenia
 * @since Oct 18, 2024
 */
public abstract class JsonTestSetting {
	
	protected String personFileName;
	protected String addFileName;
	
	public void beforeEach() throws Exception {
		personFileName = "per".concat(UUID.randomUUID().toString()).concat(".json");
		addFileName = "add".concat(UUID.randomUUID().toString()).concat(".json");
	}
	
	public void afterEach() {
		if(personFileName != null) {
			File f = new File(personFileName);
			if(f.exists()) {
				f.delete();
			}
		}
		if(addFileName != null) {
			File f = new File(addFileName);
			if(f.exists()) {
				f.delete();
			}
		}	
	}

}
