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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gecko.codec.CodecType;

/**
 * Some basic codec types and a codec type cache implementation
 * @author Mark Hoffmann
 * @since 03.04.2023
 */
public class BasicCodecTypes {
	
	public static final CodecType STRING = new BasicCodecType(String.class);
	public static final CodecType INTEGER = new BasicCodecType(Integer.class, Integer.TYPE);
	public static final CodecType LONG = new BasicCodecType(Long.class, Long.TYPE);
	public static final CodecType SHORT = new BasicCodecType(Short.class, Short.TYPE);
	public static final CodecType FLOAT = new BasicCodecType(Float.class, Float.TYPE);
	public static final CodecType DOUBLE = new BasicCodecType(Double.class, Double.TYPE);
	public static final CodecType BYTE = new BasicCodecType(Byte.class, Byte.TYPE);
	public static final CodecType BYTEARRAY = new BasicCodecType(Byte[].class, byte[].class);
	public static final CodecType BOOL = new BasicCodecType(Boolean.class, Boolean.TYPE);
	public static final CodecType CHAR = new BasicCodecType(Character.class, Character.TYPE);
	public static final CodecType CALENDAR = new BasicCodecType(Calendar.class);
	public static final CodecType DATETIME = new BasicCodecType(Date.class);
	
	/** A null codec type */
	public static final CodecType NULL = new CodecType() {
		
		@Override
		public boolean isType(Object typeToTest) {
			return isNull(typeToTest);
		}
		
		@Override
		public List<String> getIdentifier() {
			return List.of("<NULL>");
		}
		
	};
	/** An error codec type */
	public static final CodecType ERROR = new CodecType() {
		
		@Override
		public boolean isType(Object typeToTest) {
			return typeToTest instanceof Throwable;
		}
		
		@Override
		public List<String> getIdentifier() {
			return List.of("<ERROR>");
		}
		
	};
	private static final Map<Class<?>, CodecType> TYPEMAP = new HashMap<>();
	protected final Map<Object, CodecType> cachedTypeMap = new HashMap<>();
	
	static {
		TYPEMAP.put(Integer.TYPE, INTEGER);
		TYPEMAP.put(Integer.class, INTEGER);
		TYPEMAP.put(Long.TYPE, LONG);
		TYPEMAP.put(Long.class, LONG);
		TYPEMAP.put(Boolean.TYPE, BOOL);
		TYPEMAP.put(Boolean.class, BOOL);
		TYPEMAP.put(Short.TYPE, SHORT);
		TYPEMAP.put(Short.class, SHORT);
		TYPEMAP.put(Byte.TYPE, BYTE);
		TYPEMAP.put(Byte.class, BYTE);
		TYPEMAP.put(byte[].class, BYTEARRAY);
		TYPEMAP.put(Byte[].class, BYTEARRAY);
		TYPEMAP.put(Float.TYPE, FLOAT);
		TYPEMAP.put(Float.class, FLOAT);
		TYPEMAP.put(Double.TYPE, DOUBLE);
		TYPEMAP.put(Double.class, DOUBLE);
		TYPEMAP.put(Character.TYPE, CHAR);
		TYPEMAP.put(Character.class, CHAR);
		TYPEMAP.put(Calendar.class, CALENDAR);
		TYPEMAP.put(Date.class, DATETIME);
		TYPEMAP.put(String.class, STRING);
	}
	
	public static CodecType getFrom(Class<?> clazz) {
		if (isNull(clazz)) {
			return NULL;
		}
		if (Throwable.class.isAssignableFrom(clazz)) {
			return ERROR;
		}
		return TYPEMAP.get(clazz);
	}
	
	/**
	 * Return a CodecType from the local cache. It may create a new type wrapper for unknown objects
	 * @param type the object type
	 * @return the codec type wrapper or <code>null</code>
	 */
	protected final CodecType getFromCache(Object type) {
		CodecType t = null;
		if (isNull(type) || type instanceof Class) {
			t = getFrom((Class<?>)type);
		}
		if (isNull(t)) {
			t = cachedTypeMap.get(type);
			if (isNull(t)) {
				t = new BasicCodecType(type);
				cachedTypeMap.put(type, t);
			}
		}
		return t;
	}

}
