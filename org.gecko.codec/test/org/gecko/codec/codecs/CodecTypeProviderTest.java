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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.gecko.codec.CodecType;
import org.gecko.codec.CodecTypeProvider;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 03.04.2023
 */
public class CodecTypeProviderTest {
	
	@Test
	public void testGetNull() {
		assertEquals(BasicCodecTypes.NULL, BasicCodecTypes.getFrom(null));
		assertNull(BasicCodecTypes.getFrom(getClass()));
	}
	
	@Test
	public void testGetFromCacheJavaClass() {
		CodecTypeProvider<Class<?>> types = new JavaCodecTypeProvider();
		assertTrue(types.getTypeFrom(null).isPresent());
		assertEquals(BasicCodecTypes.NULL, types.getTypeFrom(null).get());
		Optional<CodecType> type = types.getTypeFrom(getClass());
		assertTrue(type.isPresent());
		assertEquals(1, type.get().getIdentifier().size());
		assertEquals(getClass().getName(), type.get().getIdentifier().get(0));
		
		type = types.getTypeFrom(Long.TYPE);
		assertTrue(type.isPresent());
		assertEquals(2, type.get().getIdentifier().size());
		assertEquals(Long.class.getName(), type.get().getIdentifier().get(0));
		assertEquals(Long.TYPE.getName(), type.get().getIdentifier().get(1));
	}
	
	@Test
	public void testGetFromCacheBasic() {
		BasicCodecTypes types = new BasicCodecTypes();
		assertEquals(BasicCodecTypes.NULL, types.getFromCache(null));
		CodecType type = types.getFromCache(getClass());
		assertNotNull(type);
		assertEquals(1, type.getIdentifier().size());
		assertEquals(getClass().getName(), type.getIdentifier().get(0));
		
		type = types.getFromCache(Long.TYPE);
		assertNotNull(type);
		assertEquals(2, type.getIdentifier().size());
		assertEquals(Long.class.getName(), type.getIdentifier().get(0));
		assertEquals(Long.TYPE.getName(), type.getIdentifier().get(1));
		
		type = types.getFromCache("test");
		assertNotNull(type);
		assertEquals(1, type.getIdentifier().size());
		assertEquals("test", type.getIdentifier().get(0));
		
		type = types.getFromCache(Integer.valueOf(42));
		assertNotNull(type);
		assertEquals(1, type.getIdentifier().size());
		assertEquals("42", type.getIdentifier().get(0));
		
		CodecType type24 = types.getFromCache(Integer.valueOf(24));
		assertNotNull(type24);
		assertEquals(1, type24.getIdentifier().size());
		assertEquals("24", type24.getIdentifier().get(0));
		assertNotEquals(type24, type);
	}
	
	@Test
	public void testNoDefaultType() {
		BasicCodecTypes types = new BasicCodecTypes();
		assertEquals(BasicCodecTypes.NULL, types.getFromCache(null));
		
		CodecType type = BasicCodecTypes.getFrom(IllegalStateException.class);
		assertNotNull(type);
		assertEquals(BasicCodecTypes.ERROR, type);
		
	}

}
