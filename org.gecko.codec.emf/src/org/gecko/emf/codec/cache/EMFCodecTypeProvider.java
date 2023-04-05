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
package org.gecko.emf.codec.cache;

import static java.util.Objects.nonNull;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.gecko.codec.CodecType;
import org.gecko.codec.CodecTypeProvider;
import org.gecko.codec.codecs.BasicCodecTypes;
import org.gecko.codec.codecs.JavaCodecTypeProvider;

/**
 * 
 * @author mark
 * @since 03.04.2023
 */
public class EMFCodecTypeProvider extends BasicCodecTypes implements CodecTypeProvider<EClassifier> {

	private CodecTypeProvider<Class<?>> javaProvider = new JavaCodecTypeProvider();
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecTypeProvider#getTypeFrom(java.lang.Object)
	 */
	@Override
	public Optional<CodecType> getTypeFrom(EClassifier type) {
		if (type instanceof EDataType) {
			return getFromDataTypeInstanceClass((EDataType) type);
		} else {
			return Optional.ofNullable(getFromCache(type));
		}
	}

	/**
	 * Returns the {@link CodecType} for a data type.
	 * @param dataType the data type must not be <code>null</code>
	 */
	private Optional<CodecType> getFromDataTypeInstanceClass(final EDataType dataType) {
		Objects.requireNonNull(dataType);
		if (nonNull(dataType.getInstanceClass())) {
			return javaProvider.getTypeFrom(dataType.getInstanceClass());
		}
		return Optional.empty();
	}
	
	public Optional<CodecType> getTypeFrom(Class<?> type) {
		return javaProvider.getTypeFrom(type);
	}
		
}
