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
package org.gecko.codec.json;

import org.gecko.codec.jackson.databind.CodecParserBaseImpl2;

import com.fasterxml.jackson.core.io.IOContext;

/**
 * 
 * @author ilenia
 * @since Nov 11, 2024
 */
public class JsonCodecParser extends CodecParserBaseImpl2 {

	/**
	 * Creates a new instance.
	 * @param ctxt
	 * @param features
	 */
	protected JsonCodecParser(IOContext ctxt, int features) {
		super(ctxt, features);
	}

}
