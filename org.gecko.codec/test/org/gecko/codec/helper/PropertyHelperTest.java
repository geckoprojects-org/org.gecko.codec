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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 22.03.2023
 */
public class PropertyHelperTest {
	
	@Test
	public void testGetBoolean() {
		Map<String, Object> properties = new HashMap<>();
		String key = "Foo";
		boolean booleanValue = PropertiesHelper.getBooleanValue(null, null);
		assertFalse(booleanValue);
		booleanValue = PropertiesHelper.getBooleanValue(null, key);
		assertFalse(booleanValue);
		booleanValue = PropertiesHelper.getBooleanValue(properties, null);
		assertFalse(booleanValue);
		booleanValue = PropertiesHelper.getBooleanValue(properties, key);
		assertFalse(booleanValue);
		booleanValue = PropertiesHelper.getBooleanValue(properties, key, true);
		assertTrue(booleanValue);
		
		properties.put("Bar", "Test");
		booleanValue = PropertiesHelper.getBooleanValue(properties, key);
		assertFalse(booleanValue);
		booleanValue = PropertiesHelper.getBooleanValue(properties, key, true);
		assertTrue(booleanValue);
		properties.put(key, Integer.valueOf(42));
		booleanValue = PropertiesHelper.getBooleanValue(properties, key);
		assertFalse(booleanValue);
		properties.put(key, Boolean.FALSE);
		booleanValue = PropertiesHelper.getBooleanValue(properties, key);
		assertFalse(booleanValue);
		booleanValue = PropertiesHelper.getBooleanValue(properties, key, true);
		assertFalse(booleanValue);
		properties.put(key, Boolean.TRUE);
		booleanValue = PropertiesHelper.getBooleanValue(properties, key);
		assertTrue(booleanValue);
		booleanValue = PropertiesHelper.getBooleanValue(properties, key, false);
		assertTrue(booleanValue);
		properties.put(key, "true");
		booleanValue = PropertiesHelper.getBooleanValue(properties, key);
		assertTrue(booleanValue);
		booleanValue = PropertiesHelper.getBooleanValue(properties, key, false);
		assertTrue(booleanValue);
	}
	
	@Test
	public void testGetString() {
		Map<String, Object> properties = new HashMap<>();
		String key = "Foo";
		String stringValue = PropertiesHelper.getStringValue(null, null);
		assertNull(stringValue);
		stringValue = PropertiesHelper.getStringValue(properties, null);
		assertNull(stringValue);
		stringValue = PropertiesHelper.getStringValue(null, key);
		assertNull(stringValue);
		stringValue = PropertiesHelper.getStringValue(properties, key);
		assertNull(stringValue);
		properties.put("Bar", "Test");
		stringValue = PropertiesHelper.getStringValue(properties, key);
		assertNull(stringValue);
		properties.put(key, Integer.valueOf(42));
		stringValue = PropertiesHelper.getStringValue(properties, key);
		assertEquals("42", stringValue);
		properties.put(key,"fizz");
		stringValue = PropertiesHelper.getStringValue(properties, key);
		assertEquals("fizz", stringValue);
	}
	
	@Test
	public void testGetStringPlus() {
		Map<String, Object> properties = new HashMap<>();
		String key = "Foo";
		List<String> stringValues = PropertiesHelper.getStringPlusValue(null, null);
		assertNull(stringValues);
		stringValues = PropertiesHelper.getStringPlusValue(properties, null);
		assertNull(stringValues);
		stringValues = PropertiesHelper.getStringPlusValue(null, key);
		assertNull(stringValues);
		stringValues = PropertiesHelper.getStringPlusValue(properties, key);
		assertNull(stringValues);
		properties.put("Bar", "Test");
		stringValues = PropertiesHelper.getStringPlusValue(properties, key);
		assertNull(stringValues);
		properties.put(key, Integer.valueOf(42));
		stringValues = PropertiesHelper.getStringPlusValue(properties, key);
		assertEquals(1, stringValues.size());
		assertEquals("42", stringValues.get(0));
		properties.put(key,"fizz");
		stringValues = PropertiesHelper.getStringPlusValue(properties, key);
		assertEquals(1, stringValues.size());
		assertEquals("fizz", stringValues.get(0));
		properties.put(key,List.of("fizz", Integer.valueOf(42)));
		stringValues = PropertiesHelper.getStringPlusValue(properties, key);
		assertEquals(2, stringValues.size());
		assertEquals("fizz", stringValues.get(0));
		assertEquals("42", stringValues.get(1));
		properties.put(key,new String[]{"fizz", "buzz"});
		stringValues = PropertiesHelper.getStringPlusValue(properties, key);
		assertEquals(2, stringValues.size());
		assertEquals("fizz", stringValues.get(0));
		assertEquals("buzz", stringValues.get(1));
		properties.put(key,new Object[]{Integer.valueOf(42), "buzz"});
		stringValues = PropertiesHelper.getStringPlusValue(properties, key);
		assertEquals(2, stringValues.size());
		assertEquals("42", stringValues.get(0));
		assertEquals("buzz", stringValues.get(1));
		
	}
	
	@Test
	public void testGetObjectPlus() {
		Map<String, Object> properties = new HashMap<>();
		String key = "Foo";
		List<Object> objectValues = PropertiesHelper.getObjectPlusValue(null, null);
		assertNull(objectValues);
		objectValues = PropertiesHelper.getObjectPlusValue(properties, null);
		assertNull(objectValues);
		objectValues = PropertiesHelper.getObjectPlusValue(null, key);
		assertNull(objectValues);
		objectValues = PropertiesHelper.getObjectPlusValue(properties, key);
		assertNull(objectValues);
		properties.put("Bar", "Test");
		objectValues = PropertiesHelper.getObjectPlusValue(properties, key);
		assertNull(objectValues);
		properties.put(key, Integer.valueOf(42));
		objectValues = PropertiesHelper.getObjectPlusValue(properties, key);
		assertEquals(1, objectValues.size());
		assertEquals(Integer.valueOf(42), objectValues.get(0));
		properties.put(key,"fizz");
		objectValues = PropertiesHelper.getObjectPlusValue(properties, key);
		assertEquals(1, objectValues.size());
		assertEquals("fizz", objectValues.get(0));
		properties.put(key,List.of("fizz", Integer.valueOf(42)));
		objectValues = PropertiesHelper.getObjectPlusValue(properties, key);
		assertEquals(2, objectValues.size());
		assertEquals("fizz", objectValues.get(0));
		assertEquals(Integer.valueOf(42), objectValues.get(1));
		properties.put(key,new String[]{"fizz", "buzz"});
		objectValues = PropertiesHelper.getObjectPlusValue(properties, key);
		assertEquals(2, objectValues.size());
		assertEquals("fizz", objectValues.get(0));
		assertEquals("buzz", objectValues.get(1));
		properties.put(key,new Object[]{Integer.valueOf(42), "buzz"});
		objectValues = PropertiesHelper.getObjectPlusValue(properties, key);
		assertEquals(2, objectValues.size());
		assertEquals(Integer.valueOf(42), objectValues.get(0));
		assertEquals("buzz", objectValues.get(1));
		
	}
	
	@Test
	public void testGetTyped() {
		Map<String, Object> properties = new HashMap<>();
		String key = "Foo";
		Object value = PropertiesHelper.getTypedValue(null, null, null);
		assertNull(value);
		value = PropertiesHelper.getTypedValue(properties, null, null);
		assertNull(value);
		value = PropertiesHelper.getTypedValue(properties, key, null);
		assertNull(value);
		value = PropertiesHelper.getTypedValue(properties, key, Date.class);
		assertNull(value);
		properties.put(key, "Foo");
		assertThrows(IllegalStateException.class, ()->PropertiesHelper.getTypedValue(properties, key, Date.class));
		value = PropertiesHelper.getTypedValue(properties, key, String.class);
		assertEquals("Foo", value);
		
		GregorianCalendar gregorianCalendar = GregorianCalendar.from(ZonedDateTime.now());
		properties.put(key, gregorianCalendar);
		value = PropertiesHelper.getTypedValue(properties, key, Calendar.class);
		assertEquals(gregorianCalendar, value);
		value = PropertiesHelper.getTypedValue(properties, key, GregorianCalendar.class);
		assertEquals(gregorianCalendar, value);
		
		properties.put(key, 123l);
		value = PropertiesHelper.getTypedValue(properties, key, Long.class);
		assertEquals(123l, value);
	}

}
