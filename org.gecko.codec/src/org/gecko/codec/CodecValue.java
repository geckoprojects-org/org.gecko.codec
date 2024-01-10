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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Interface for a codec value
 * @author Mark Hoffmann
 * @since 03.04.2023
 */
@Deprecated
@ProviderType
public interface CodecValue<T> extends Comparable<T> {
	
	/**
	 * Returns the value
	 * @return the value
	 */
	T getValue();
	
	/**
	 * Returns the codec type for this value. Must not be <code>null</code>
	 * @return the codec type for this value
	 */
	CodecType getCodecType();

}
