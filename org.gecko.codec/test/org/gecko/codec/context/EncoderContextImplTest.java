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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.gecko.codec.codecs.BasicCodecRegistry;
import org.gecko.codec.encode.CodecWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 
 * @author mark
 * @since 05.04.2023
 */
@ExtendWith(MockitoExtension.class)
class EncoderContextImplTest {
	
	@Mock
	private Context context;
	private BasicCodecRegistry registry;
	
	@BeforeEach
	public void beforeEach() {
		registry = new BasicCodecRegistry();
	}

	/**
	 * Test method for {@link org.gecko.codec.context.EncoderContextImpl#EncoderContextImpl(org.gecko.codec.context.Context)}.
	 */
	@Test
	void testEncoderContextImpl() {
		assertThrows(NullPointerException.class, ()-> new EncoderContextImpl(null));
		assertThrows(NullPointerException.class, ()-> EncoderContext.createFromParent(null));
	}

	/**
	 * Test method for {@link org.gecko.codec.context.EncoderContextImpl#getParentContext()}.
	 */
	@Test
	void testGetParentContext() {
		EncoderContext ctx = EncoderContext.createFromParent(context);
		assertNull(ctx.getParentContext());
		EncoderContext ctxChild = EncoderContext.createFromParent(ctx);
		assertNotEquals(ctx, ctxChild);
		assertEquals(ctx, ctxChild.getParentContext());
	}

	/**
	 * Test method for {@link org.gecko.codec.context.EncoderContextImpl#encodeChild(org.gecko.codec.encode.CodecWriter, java.lang.Object)}.
	 */
	@Test
	void testEncodeChild() {
		EncoderContext ctx = EncoderContext.createFromParent(context);
		assertThrows(NullPointerException.class, ()->ctx.encodeWithChild(null, null));
		assertThrows(NullPointerException.class, ()->ctx.encodeWithChild(null, "Foo"));
		CodecWriter codecWriter = mock(CodecWriter.class);
		ctx.encodeWithChild(codecWriter, null);
		verify(codecWriter, times(1)).writeNull();
		
		when(context.getJavaTypeProvider()).thenReturn(registry.getJavaTypeProvider());
		when(context.getRegistry()).thenReturn(registry.getRegistry());
		ctx.encodeWithChild(codecWriter, Integer.valueOf(42));
		
		verify(context, times(1)).getJavaTypeProvider();
		verify(context, times(1)).getRegistry();
		verify(codecWriter, times(1)).writeInt(anyInt());
		verify(codecWriter, never()).writeString(any(String.class));

		ctx.encodeWithChild(codecWriter, "test");
		
		verify(context, times(2)).getJavaTypeProvider();
		verify(context, times(2)).getRegistry();
		verify(codecWriter, times(1)).writeInt(anyInt());
		verify(codecWriter, times(1)).writeString(any(String.class));
	}
	
	/**
	 * Test method for {@link org.gecko.codec.context.EncoderContext#createFromParent(org.gecko.codec.context.Context)}.
	 */
	@Test
	void testCreateFromParent() {
		assertThrows(NullPointerException.class, ()-> EncoderContext.createFromParent(null));
		EncoderContext ctx = EncoderContext.createFromParent(context);
		assertNotNull(ctx);
	}

}
