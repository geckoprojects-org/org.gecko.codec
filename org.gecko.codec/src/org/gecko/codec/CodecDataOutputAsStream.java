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
package org.gecko.codec;

import tools.jackson.core.io.DataOutputAsStream;

/**
 * 
 * @author ilenia
 * @since Apr 16, 2025
 */
public class CodecDataOutputAsStream extends DataOutputAsStream {

	/**
	 * Creates a new instance.
	 * @param out
	 */
	public CodecDataOutputAsStream(CodecDataOutput_old out) {
		super(out);
	}

	@SuppressWarnings("unchecked")
	public CodecDataOutput_old getCodecDataOutput() {
		return (CodecDataOutput_old)_output;
	}
}
