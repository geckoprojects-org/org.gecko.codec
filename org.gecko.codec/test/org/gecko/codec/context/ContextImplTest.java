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
package org.gecko.codec.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.gecko.codec.codecs.BasicCodecRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 05.04.2023
 */
class ContextImplTest {
	
	private BasicCodecRegistry registry;
	
	@BeforeEach
	public void beforeEach() {
		registry = new BasicCodecRegistry();
	}

	/**
	 * Test method for {@link org.gecko.codec.context.ContextImpl#ContextImpl(org.gecko.codec.context.Context)}.
	 */
	@Test
	void testContextImpl() {
		assertThrows(NullPointerException.class, ()-> new ContextImpl(null));
	}

	/**
	 * Test method for {@link org.gecko.codec.context.ContextImpl#getProperties()}.
	 */
	@Test
	void testGetProperties() {
		Context ctx = new ContextImpl(registry);
		assertNotNull(ctx.getProperties());
		assertTrue(ctx.getProperties().isEmpty());
		ctx.getProperties().put("foo", "bar");
		assertEquals("bar", ctx.getProperties().get("foo"));
	}

	/**
	 * Test method for {@link org.gecko.codec.context.ContextImpl#getDiagnostics()}.
	 */
	@Test
	void testGetDiagnostics() {
		Context ctx = new ContextImpl(registry);
		assertNotNull(ctx.getDiagnostics());
		assertEquals(registry.getDiagnostics(), ctx.getDiagnostics());
	}

	/**
	 * Test method for {@link org.gecko.codec.context.ContextImpl#getTypeProvider()}.
	 */
	@Test
	void testGetTypeProvider() {
		Context ctx = new ContextImpl(registry);
		assertNotNull(ctx.getTypeProvider());
		assertEquals(registry.getTypeProvider(), ctx.getTypeProvider());
	}

	/**
	 * Test method for {@link org.gecko.codec.context.ContextImpl#getJavaTypeProvider()}.
	 */
	@Test
	void testGetJavaTypeProvider() {
		Context ctx = new ContextImpl(registry);
		assertNotNull(ctx.getJavaTypeProvider());
		assertEquals(registry.getJavaTypeProvider(), ctx.getJavaTypeProvider());
	}

	/**
	 * Test method for {@link org.gecko.codec.context.ContextImpl#getRegistry()}.
	 */
	@Test
	void testGetRegistry() {
		Context ctx = new ContextImpl(registry);
		assertNotNull(ctx.getRegistry());
		assertEquals(registry, ctx.getRegistry());
	}

	/**
	 * Test method for {@link org.gecko.codec.context.ContextImpl#getContext()}.
	 */
	@Test
	void testGetContext() {
		ContextImpl ctx = new ContextImpl(registry);
		assertNotNull(ctx.getContext());
		assertEquals(registry, ctx.getContext());
	}

}
