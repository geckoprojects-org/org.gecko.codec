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
package org.gecko.codec.codecs;

import java.util.Optional;

import org.gecko.codec.CodecType;
import org.gecko.codec.CodecTypeProvider;

/**
 * Java class based provider for codec types
 * @author Mark Hoffmann
 * @since 03.04.2023
 */
public class JavaCodecTypeProvider extends BasicCodecTypes implements CodecTypeProvider<Class<?>> {

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecTypeProvider#getTypeFrom(java.lang.Object)
	 */
	@Override
	public Optional<CodecType> getTypeFrom(Class<?> type) {
		return Optional.ofNullable(getFromCache(type));
	}

}
