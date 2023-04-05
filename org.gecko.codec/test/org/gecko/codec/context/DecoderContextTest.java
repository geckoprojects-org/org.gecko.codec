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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.gecko.codec.CodecType;
import org.gecko.codec.codecs.BasicCodecRegistry;
import org.gecko.codec.codecs.BasicCodecTypes;
import org.gecko.codec.codecs.CodecRegistry;
import org.gecko.codec.decode.CodecReader;
import org.gecko.codec.diagnostic.Diagnostic;
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
class DecoderContextTest {
	
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
	void testDecoderContextImpl() {
		assertThrows(NullPointerException.class, ()-> new DecoderContextImpl(null));
		assertThrows(NullPointerException.class, ()-> DecoderContext.createFromParent(null));
	}

	/**
	 * Test method for {@link org.gecko.codec.context.DecoderContext#getParentContext()}.
	 */
	@Test
	void testGetParentContext() {
		DecoderContext ctx = DecoderContext.createFromParent(context);
		assertNull(ctx.getParentContext());
		DecoderContext ctxChild = DecoderContext.createFromParent(ctx);
		assertNotEquals(ctx, ctxChild);
		assertEquals(ctx, ctxChild.getParentContext());
	}

	/**
	 * Test method for {@link org.gecko.codec.context.DecoderContext#decodeFromChild(org.gecko.codec.decode.CodecReader)}.
	 */
	@Test
	void testDecodeFromChild() {
		when(context.getRegistry()).thenReturn(registry);
		when(context.getDiagnostics()).thenReturn(registry.getDiagnostics());
		DecoderContext ctx01 = DecoderContext.createFromParent(context);
		assertThrows(NullPointerException.class, ()->ctx01.decodeFromChild(null));
		CodecReader reader = mock(CodecReader.class);
		
		when(reader.getCurrentType()).thenReturn(BasicCodecTypes.STRING);
		when(reader.readString()).thenReturn("42");
		
		ctx01.decodeFromChild(reader);
		
		verify(reader, times(1)).getCurrentType();
		verify(reader, times(1)).readString();
		assertTrue(registry.getDiagnostics().isEmpty());
		
		when(reader.getCurrentType()).thenReturn(null);
		assertThrows(NullPointerException.class, ()->ctx01.decodeFromChild(reader));
		
		reset(context);
		DecoderContext ctx02 = DecoderContext.createFromParent(context);
		CodecRegistry mockRegistry = mock(CodecRegistry.class);
		when(reader.getCurrentType()).thenReturn(BasicCodecTypes.INTEGER);
		when(mockRegistry.getCodec(any(CodecType.class))).thenReturn(null);
		when(context.getRegistry()).thenReturn(mockRegistry);
		List<Diagnostic> diags = new LinkedList<>();
		when(context.getDiagnostics()).thenReturn(diags);
		
		ctx02.decodeFromChild(reader);
		
		assertFalse(diags.isEmpty());
		assertEquals(1, diags.size());
		
	}

	/**
	 * Test method for {@link org.gecko.codec.context.DecoderContext#createFromParent(org.gecko.codec.context.Context)}.
	 */
	@Test
	void testCreateFromParent() {
		assertThrows(NullPointerException.class, ()-> DecoderContext.createFromParent(null));
		DecoderContext ctx = DecoderContext.createFromParent(context);
		assertNotNull(ctx);
	}

}
