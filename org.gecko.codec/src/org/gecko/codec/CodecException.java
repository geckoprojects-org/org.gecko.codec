/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
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
package org.gecko.codec;

/**
 * Exception used for Code API
 * @author Mark Hoffmann
 * @since 22.03.2023
 */
public class CodecException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 8442312057484794212L;
	
	/**
	 * Creates a new instance.
	 */
	public CodecException(String message) {
		super(message);
	}
	
	/**
	 * Creates a new instance.
	 */
	public CodecException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
