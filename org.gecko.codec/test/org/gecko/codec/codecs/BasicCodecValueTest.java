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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.gecko.codec.CodecValue;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 03.04.2023
 */
public class BasicCodecValueTest {
	
	@Test
	public void testCreation() {
		assertThrows(NullPointerException.class, ()-> new BasicCodecValue<Integer>(null, null));
		assertThrows(NullPointerException.class, ()-> new BasicCodecValue<Integer>(null, 12));
		CodecValue<Integer> intValue = new BasicCodecValue<Integer>(BasicCodecTypes.getFrom(Integer.class), null);
		assertNull(intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		
		intValue = new BasicCodecValue<Integer>(BasicCodecTypes.getFrom(Integer.TYPE), Integer.valueOf(24));
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		intValue = new BasicCodecValue<Integer>(BasicCodecTypes.getFrom(Integer.class), Integer.valueOf(24));
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		
		intValue = new BasicCodecValue<Integer>(BasicCodecTypes.getFrom(Integer.TYPE), 24);
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		intValue = new BasicCodecValue<Integer>(BasicCodecTypes.getFrom(Integer.class), 24);
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
	}
	
	@Test
	public void testStaticCreation() {
		CodecValue<?> intValue = BasicCodecValue.createCodecValue(null);
		assertNull(intValue.getValue());
		assertEquals(BasicCodecTypes.NULL, intValue.getCodecType());
		
		intValue = BasicCodecValue.createCodecValue(Integer.valueOf(24));
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		intValue = BasicCodecValue.createCodecValue(Integer.valueOf(24));
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		
		intValue = BasicCodecValue.createCodecValue(24);
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		intValue = BasicCodecValue.createCodecValue(24);
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		
		CodecValue<String> stringValue01 = BasicCodecValue.createCodecValue("Hello");
		assertEquals("Hello", stringValue01.getValue());
		assertEquals(BasicCodecTypes.STRING, stringValue01.getCodecType());
		
		CodecValue<String> stringValue02 = BasicCodecValue.createCodecValue("Hurray");
		assertEquals("Hurray", stringValue02.getValue());
		assertEquals(BasicCodecTypes.STRING, stringValue02.getCodecType());

		CodecValue<String> stringValue03 = BasicCodecValue.createCodecValue("Hurray");
		assertEquals("Hurray", stringValue03.getValue());
		assertEquals(BasicCodecTypes.STRING, stringValue03.getCodecType());
		
		assertTrue(stringValue01.compareTo(stringValue02.getValue()) < 0);
		assertNotEquals(stringValue02, stringValue03);
		assertTrue(stringValue02.compareTo(stringValue03.getValue()) == 0);
	}
	
	@Test
	public void testStaticCreationWithCache() {
		JavaCodecTypeProvider provider = new JavaCodecTypeProvider();
		CodecValue<?> intValue = BasicCodecValue.createCodecValue(null, provider);
		assertNull(intValue.getValue());
		assertEquals(BasicCodecTypes.NULL, intValue.getCodecType());
		
		intValue = BasicCodecValue.createCodecValue(Integer.valueOf(24), provider);
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
		
		intValue = BasicCodecValue.createCodecValue(24, provider);
		assertEquals(24, intValue.getValue());
		assertEquals(BasicCodecTypes.INTEGER, intValue.getCodecType());
	}
	
	@Test
	public void testNoDefaultCreation() {
		JavaCodecTypeProvider provider = new JavaCodecTypeProvider();
		assertEquals(Optional.empty(), BasicCodecValue.createCodecValue(Optional.empty(), provider).getValue());
		assertTrue(provider.getTypeFrom(Optional.class).isPresent());
		assertEquals(provider.getTypeFrom(Optional.class).get(), BasicCodecValue.createCodecValue(Optional.empty(), provider).getCodecType());
	}
	
	@Test
	public void testCreationError() {
		JavaCodecTypeProvider provider = new JavaCodecTypeProvider();
		assertEquals(Optional.empty(), BasicCodecValue.createCodecValue(Optional.empty(), provider).getValue());
		assertTrue(provider.getTypeFrom(Optional.class).isPresent());
		assertEquals(provider.getTypeFrom(Optional.class).get(), BasicCodecValue.createCodecValue(Optional.empty(), provider).getCodecType());
	}

}
