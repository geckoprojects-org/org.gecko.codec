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
import static java.util.Objects.requireNonNull;

import org.gecko.codec.CodecException;
import org.gecko.codec.CodecType;
import org.gecko.codec.CodecValue;

/**
 * 
 * @author mark
 * @since 03.04.2023
 */
public class BasicCodecValue<T> implements CodecValue<T>{
	
	public static final BasicCodecValue<?> NULL_VALUE = new BasicCodecValue<>(BasicCodecTypes.NULL, null);
	private static final BasicCodecTypes typesCache = new BasicCodecTypes();
	private final T value;
	private final CodecType type;
	
	/**
	 * Creates a new instance.
	 * @throws CodecException 
	 */
	BasicCodecValue(final CodecType type, final T value) {
		requireNonNull(type);
		this.type = type;
		if (isNull(value) ) {
			this.value = null;
		} else if(type.isType(getType(value))) {
			this.value = value;
		} else {
			throw new IllegalStateException(String.format("The value: '%s' is not compatible to the given codec type: '%s'", value, type.getIdentifier()));
		}
		
	}
	
	/**
	 * Returns the type of the value.
	 * @param value
	 * @return
	 */
	protected Object getType(T value) {
		requireNonNull(value);
		return value.getClass();
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(T o) {
		if (isNull(value)) {
			return -1;
		}
		if (isNull(o)) {
			return 1;
		}
		if (value instanceof Comparable) {
			return ((Comparable<Object>)value).compareTo(o);
		}
		return value.toString().compareTo(o.toString());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecValue#getValue()
	 */
	@Override
	public T getValue() {
		return value;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecValue#getCodecType()
	 */
	@Override
	public CodecType getCodecType() {
		return type;
	}
	
	public static CodecValue<Object> createNullCodecValue() {
		return new BasicCodecValue<Object>(BasicCodecTypes.NULL, null);
	}
	
	public static CodecValue<Throwable> createErrorCodecValue(Throwable value) {
		return new BasicCodecValue<Throwable>(BasicCodecTypes.ERROR, value);
	}
	
	public static <T> CodecValue<T> createCodecValue(T value) {
		CodecType type;
		if (isNull(value)) {
			type = BasicCodecTypes.NULL;
		} else {
			type = typesCache.getFromCache(value.getClass());
		}
		return new BasicCodecValue<T>(type, value);
	}
	
	public static <T> CodecValue<T> createCodecValue(T value, BasicCodecTypes typeCache) {
		CodecType type;
		if (isNull(value)) {
			type = BasicCodecTypes.NULL;
		} else {
			type = typeCache.getFromCache(value.getClass());
		}
		return new BasicCodecValue<T>(type, value);
	}
	
}
