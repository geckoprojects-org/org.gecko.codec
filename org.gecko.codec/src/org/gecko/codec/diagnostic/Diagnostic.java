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

/**
 * Diagnostic wrapper to collect multiple errors
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public interface Diagnostic {
	
	/**
	 * Returns the grade of the severity, must not be <code>null</code>
	 * @return the grade of the severity
	 */
	Severity getSeverity();
	
	/**
	 * Returns the diagnostic message, should never be <code>null</code>
	 * @return the diagnostic message, should never be <code>null</code>
	 */
	String getMessage();
	
	/**
	 * Returns the error as {@link Throwable} or <code>null</code>
	 * @return the error as {@link Throwable} or <code>null</code>
	 */
	Throwable getError();
	
	/**
	 * Returns <code>true</code> if this {@link Diagnostic} is a show stopper
	 * @return <code>true</code> if this {@link Diagnostic} is a show stopper
	 */
	boolean isStopper();

}
