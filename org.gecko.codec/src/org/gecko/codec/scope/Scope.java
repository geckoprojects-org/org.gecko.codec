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

import java.util.List;

import org.gecko.codec.diagnostic.Diagnostic;

/**
 * The scope defines a basic configuration under which a,n encoding or decoding will happen.
 * A scope is layered. The base comes from the Codec - global configuration that can be overwritten 
 * per request.
 * @author Mark Hoffmann
 * @since 22.03.2023
 */
public interface Scope {
	
	/**
	 * Validates the scope and returns <code>true</code>, if validation , otherwise false
	 * @return
	 */
	boolean validate();
	
	/**
	 * Returns the diagnostics or an empty {@link List}
	 * @return the diagnostics or an empty {@link List}
	 */
	List<Diagnostic> getDiagnostics();
}
