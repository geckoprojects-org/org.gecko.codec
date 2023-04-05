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
package org.gecko.codec.descriptors;

import java.util.Map;

/**
 * Basic descriptor for elements to describe
 * @author Mark Hoffmann
 * @since 20.03.2023
 */
public interface CodecDescriptor<T> {
	
	/**
	 * Returns the namespace of the descriptor
	 * @return the namespace of the descriptor
	 */
	String getNamespace();
	
	/**
	 * Returns the unprocessed name of the descriptor
	 * @return the unprocessed name of the descriptor
	 */
	String getName();
	
	/**
	 * Return the processed name of the descriptor. This can be equal to {@link CodecDescriptor#getName()}.
	 * @return the processed name of the descriptor
	 */
	String getProcessedName();

	/**
	 * Returns descriptor properties or an empty {@link Map}
	 * @return descriptor properties
	 */
	Map<String, Object> getProperties();
	
	/**
	 * Returns the element, that is described. Must not be <code>null</code>
	 * @return the element, that is described
	 */
	T getSource();
}
