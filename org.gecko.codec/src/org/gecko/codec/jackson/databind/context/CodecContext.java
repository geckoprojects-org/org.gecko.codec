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
package org.gecko.codec.jackson.databind.context;

import org.eclipse.emf.ecore.EObject;

/**
 * Context interface to provide EMF typed information
 * @author Mark Hoffmann
 * @since 10.01.2024
 */
public interface CodecContext {
	
	/**
	 * Returns the current EObject
	 * @return the current EObject
	 */
	EObject getEObject();
	
	/**
	 * Sets the current EObject
	 * @param object the {@link EObject} to be set
	 */
	void setEObject(EObject object);
	
	/**
	 * Returns the parent / container of this EObject
	 * @return the parent / container of this EObject
	 */
	EObject getParentEObject();
	
	/**
	 * Sets the parent / container of the current {@link EObject}
	 * @param parent the container {@link EObject} to set
	 */
	void setParentEObject(EObject parent);
	
	/**
	 * Returns the whole parent context
	 * @returnthe parent context
	 */
	CodecContext getParentContext();

}
