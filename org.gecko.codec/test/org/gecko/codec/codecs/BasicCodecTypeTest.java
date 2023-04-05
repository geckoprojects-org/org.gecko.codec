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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.gecko.codec.CodecType;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 03.04.2023
 */
public class BasicCodecTypeTest {
	
	@Test
	public void testCreate() {
		assertThrows(NullPointerException.class, ()-> new BasicCodecType(null));
		assertThrows(NullPointerException.class, ()-> new BasicCodecType(null, null));
		CodecType type = new BasicCodecType(getClass());
		assertEquals(1, type.getIdentifier().size());
		assertEquals(getClass().getName(), type.getIdentifier().get(0));
		
		assertTrue(type.isType(getClass()));
		assertFalse(type.isType(Integer.class));
		assertFalse(type.isType(null));
	}
	
	@Test
	public void testWithUnboxed() {
		CodecType type = new BasicCodecType(Integer.class, Integer.TYPE);
		assertEquals(2, type.getIdentifier().size());
		assertEquals(Integer.class.getName(), type.getIdentifier().get(0));
		assertEquals(Integer.TYPE.getName(), type.getIdentifier().get(1));
		
		assertFalse(type.isType(getClass()));
		assertTrue(type.isType(Integer.class));
		assertTrue(type.isType(Integer.TYPE));
		assertFalse(type.isType(null));
		assertFalse(type.isType(Long.TYPE));
	}

}
