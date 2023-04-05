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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import org.gecko.codec.CodecType;

/**
 * Basic codec type implementation
 * @author Mark Hoffmann
 * @since 03.04.2023
 */
public class BasicCodecType implements CodecType {
	
	private final Object type;
	private final Object primitiveType;
	private final List<String> identifier;

	BasicCodecType(Object type) {
		this(type, null);
	}
	
	BasicCodecType(Object type, Object unboxedType) {
		requireNonNull(type);
		this.type = type;
		this.primitiveType = unboxedType;
		identifier = buildIdentifier();
	}

	/**
	 * 
	 */
	private List<String> buildIdentifier() {
		List<String> typeList = new ArrayList<>();
		if (type instanceof Class) {
			typeList.add(((Class<?>)type).getName());
		} else {
			typeList.add(type.toString());
		}
		if (nonNull(primitiveType)) {
			if (primitiveType instanceof Class) {
				typeList.add(((Class<?>)primitiveType).getName());
			} else {
				typeList.add(primitiveType.toString());
			}
		}
		return typeList;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecType#isType(java.lang.Object)
	 */
	@Override
	public boolean isType(Object typeToTest) {
		if (isNull(typeToTest)) {
			return false;
		} else if (typeToTest.equals(type) || 
				nonNull(primitiveType) && primitiveType.equals(typeToTest)) {
			return true;
		} else {
			return typeToTest.toString().equals(type.toString());
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecType#getIdentifier()
	 */
	@Override
	public List<String> getIdentifier() {
		return identifier;
	}

}
