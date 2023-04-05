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
package org.gecko.codec.scope;

import java.util.LinkedList;
import java.util.List;

import org.gecko.codec.diagnostic.Diagnostic;

/**
 * Simple result for codec operations
 * @author Mark Hoffmann
 * @since 22.03.2023
 */
public interface CodecResult {

	List<Diagnostic> getEncodingDiagnostic();
	
	public static CodecResult create() {
		return new CodecResult() {
			
			private List<Diagnostic> diagnostics = new LinkedList<>();
			
			@Override
			public List<Diagnostic> getEncodingDiagnostic() {
				return diagnostics;
			}
		};
	}

}
