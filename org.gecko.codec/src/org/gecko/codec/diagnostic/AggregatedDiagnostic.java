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
package org.gecko.codec.diagnostic;

import java.util.List;

/**
 * Diagnostic that aggregates contains diagnostic to one
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public interface AggregatedDiagnostic extends Diagnostic {
	
	public static final String NO_MESSAGE = "<no-message>";
	
	/**
	 * The contained diagnostics in the list or an empty {@link List}
	 * @return contained diagnostics in the list or an empty {@link List}
	 */
	List<Diagnostic> getDiagnostics();

}
