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

import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provider interface to provide {@link CodecType}'s from a given type object 
 * @author Mark Hoffmann
 * @since 03.04.2023
 */
@Deprecated
@ProviderType
public interface CodecTypeProvider<T> {
	
	/**
	 * Returns an {@link Optional} of a {@link CodecType}
	 * @param type the type to get the codec type for
	 * @return the {@link Optional}
	 */
	Optional<CodecType> getTypeFrom(T type);

}
