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
package org.gecko.codec.helper;

import static java.util.Objects.isNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Helper class for property handling
 * @author Mark Hoffmann
 * @since 22.03.2023
 */
public class PropertiesHelper {
	
	/**
	 * Returns <code>true</code>, if the value for the key is {@link Boolean#TRUE} or "true". Otherwise <code>false</code>
	 * will returned. It delegates to {@link PropertiesHelper#getBooleanValue(Map, String, boolean)} with default value paramter set to <code>false</code>
	 * @param options the property map
	 * @param key the key to get
	 * @return <code>true</code>, if the value for the key is {@link Boolean#TRUE} or "true". Otherwise <code>false</code>
	 * will returned
	 */
	public static boolean getBooleanValue(Map<String, Object> options, String key) {
		return getBooleanValue(options, key, false);
	}
	
	/**
	 * Returns <code>true</code>, if the value for the key is {@link Boolean#TRUE} or "true". Otherwise <code>false</code>
	 * will returned
	 * @param options the property map
	 * @param key the key to get
	 * @param defaultValue the default value
	 * @return <code>true</code>, if the value for the key is {@link Boolean#TRUE} or "true". Otherwise <code>false</code>
	 * will returned
	 */
	public static boolean getBooleanValue(Map<String, Object> options, String key, boolean defaultValue) {
		if (options == null || key == null) {
			return false;
		}
		return Boolean.TRUE.equals(Boolean.parseBoolean(options.getOrDefault(key, Boolean.toString(defaultValue)).toString()));
	}
	
	/**
	 * Returns a String value for the key from the options map or <code>null</code>, if there is not value for the key
	 * @param options the property map
	 * @param key the key to get
	 * @return a String value for the key from the options map or <code>null</code>, if there is not value for the key
	 */
	public static String getStringValue(Map<String, Object> options, String key) {
		if (options == null || key == null) {
			return null;
		}
		Object value = options.get(key);
		if (value instanceof String) {
			return (String) value;
		} else {
			return value == null ? null : value.toString();
		}
	}
	
	/**
	 * returns a String+ value and recognizes Object, Object[] and Collection<Object>
	 * @param options the property map
	 * @param key the key to get
	 * @return a {@link List} of {@link String} or <code>null</code>, if the key is not available
	 */
	public static List<String> getStringPlusValue(Map<String, Object> options, String key) {
		if (options == null || key == null) {
			return null;
		}
		Object value = options.get(key);
		if (isNull(value)) {
			return null;
		}
		Collection<?> values;
		if (value instanceof String) {
			values = Collections.singletonList((String) value);
		} else if(value.getClass().isArray()){
			values = Arrays.asList((Object[])value);
		} else if (value instanceof Collection<?>) {
			values = (Collection<?>)value;
		} else {
			values =  Collections.singletonList(value.toString());
		}
		return values.
				stream().
				filter(Objects::nonNull).
				map(Object::toString).
				collect(Collectors.toList());
	}
	
	/**
	 * Returns a typed value for the key from the options map or <code>null</code>, if there is not value for the key
	 * @param options the property map
	 * @param key the key to get
	 * @param type the value type
	 * @return a typed value for the key from the options map or <code>null</code>, if there is not value for the key
	 * @throws IllegalStateException, when the type of the value is not of the provided class
	 */
	public static <T> T getTypedValue(Map<String, Object> options, String key, Class<T> type) {
		if (options == null || key == null || type == null) {
			return null;
		}
		Object value = options.get(key);
		if (value == null) {
			return null;
		}
		if (type.isAssignableFrom(value.getClass())) {
			return type.cast(value);
		} else {
			throw new IllegalStateException(String.format("Value for key '%s' is not of type %s", key, type.getName()));
		}
	}

	/**
	 * Returns a Object+ value and recognizes Object, Object[] and Collection<Object>
	 * @param options the property map
	 * @param key the key to get
	 * @return a {@link List} of {@link Object} or <code>null</code>, if the key is not available
	 */
	public static List<Object> getObjectPlusValue(Map<String, Object> options, String key) {
		if (options == null || key == null) {
			return null;
		}
		Object value = options.get(key);
		if (isNull(value)) {
			return null;
		}
		Collection<?> values;
		if(value.getClass().isArray()){
			values = Arrays.asList((Object[])value);
		} else if (value instanceof Collection<?>) {
			values = (Collection<?>)value;
		} else {
			values =  Collections.singletonList(value);
		}
		return values.
				stream().
				filter(Objects::nonNull).
				collect(Collectors.toList());
	}

}
