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
package org.gecko.codec.jackson.resource;

import java.util.Collections;

import org.eclipse.emfcloud.jackson.resource.JsonResourceFactory;
import org.gecko.codec.jackson.databind.CodecFactory;
import org.gecko.codec.jackson.module.CodecModule;

/**
 * 
 * @author mark
 * @since 10.01.2024
 */
public class CodecResourceFactory extends JsonResourceFactory {
	
	/**
	 * Creates a new instance.
	 */
	public CodecResourceFactory() {
		super(CodecModule.setupDefaultMapper(new CodecFactory(),Collections.emptyMap()));
	}
	
}
