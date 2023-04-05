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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.gecko.codec.CodecType;
import org.gecko.codec.context.Context;
import org.gecko.codec.decode.CodecReader;
import org.gecko.codec.diagnostic.Diagnostic;
import org.gecko.codec.diagnostic.Diagnostics;
import org.gecko.codec.encode.CodecWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 
 * @author mark
 * @since 05.04.2023
 */
@ExtendWith(MockitoExtension.class)
class BasicCodecRegistryTest {
	
	private BasicCodecRegistry registry;
	
	@BeforeEach
	public void beforeEach() {
		registry = new BasicCodecRegistry();
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#registerCodec(org.gecko.codec.context.Context, java.lang.Class, java.util.function.BiConsumer, java.util.function.Function)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testRegisterCodec() {
		assertThrows(NullPointerException.class, ()->registry.registerCodec(null, null));
		assertThrows(NullPointerException.class, ()->registry.registerCodec(BasicCodecTypes.BOOL, null));
		Codec<Boolean> codec01 = mock(Codec.class);
		CodecType codecType = mock(CodecType.class);
		when(codecType.getIdentifier()).thenReturn(List.of("foo"));
		assertThrows(NullPointerException.class, ()->registry.registerCodec(null, codec01));
		
		assertNull(registry.getCodec(codecType));
		registry.registerCodec(codecType, codec01);
		assertNotNull(registry.getCodec(codecType));
		assertEquals(codec01, registry.getCodec(codecType));
		
		Codec<Boolean> codec02 = mock(Codec.class);
		registry.registerCodec(codecType, codec02);
		assertNotNull(registry.getCodec(codecType));
		assertEquals(codec02, registry.getCodec(codecType));
		
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#registerCodec(org.gecko.codec.CodecType, org.gecko.codec.codecs.Codec)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testStaticRegisterCodecFail() {
		Context context = mock(Context.class);
		assertThrows(NullPointerException.class, ()->BasicCodecRegistry.registerCodec(null, null, null, null));
		assertThrows(NullPointerException.class, ()->BasicCodecRegistry.registerCodec(context, null, null, null));
		assertThrows(NullPointerException.class, ()->BasicCodecRegistry.registerCodec(context, String.class, null, null));
		BiConsumer<CodecWriter, String> encodeCall = mock(BiConsumer.class);
		Function<CodecReader, String> decodeCall = mock(Function.class);
		assertThrows(NullPointerException.class, ()->BasicCodecRegistry.registerCodec(context, String.class, encodeCall, null));
		assertThrows(NullPointerException.class, ()->BasicCodecRegistry.registerCodec(context, String.class, null, decodeCall));
		
		JavaCodecTypeProvider jtp = mock(JavaCodecTypeProvider.class);
		CodecRegistry reg = mock(CodecRegistry.class);
		List<Diagnostic> diags = new ArrayList<>();
		lenient().when(context.getRegistry()).thenReturn(reg);
		when(context.getDiagnostics()).thenReturn(diags);
		when(context.getJavaTypeProvider()).thenReturn(jtp);
		when(jtp.getTypeFrom(any())).thenReturn(Optional.empty());
		assertTrue(diags.isEmpty());
		
		BasicCodecRegistry.registerCodec(context, String.class, encodeCall, decodeCall);
		
		verify(context, never()).getRegistry();
		verify(reg, never()).getCodec(any());
		verify(context, times(1)).getJavaTypeProvider();
		verify(context, times(1)).getDiagnostics();
		assertEquals(1, diags.size());
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void testStaticRegisterCodecSuccess() {
		Context context = mock(Context.class);
		BiConsumer<CodecWriter, String> encodeCall01 = mock(BiConsumer.class);
		Function<CodecReader, String> decodeCall01 = mock(Function.class);
		
		CodecRegistry reg = mock(CodecRegistry.class);
		List<Diagnostic> diags = new ArrayList<>();
		lenient().when(context.getRegistry()).thenReturn(reg);
		when(context.getDiagnostics()).thenReturn(diags);
		when(context.getJavaTypeProvider()).thenReturn(registry.getJavaTypeProvider());
		
		assertTrue(diags.isEmpty());
		BasicCodecRegistry.registerCodec(context, String.class, encodeCall01, decodeCall01);
		assertTrue(diags.isEmpty());
		
		verify(context, times(1)).getJavaTypeProvider();
		verify(context, times(1)).getDiagnostics();
		verify(context, times(1)).getRegistry();
		verify(reg, times(1)).registerCodec(any(), any());
		
		BiConsumer<CodecWriter, Function> encodeCall02 = mock(BiConsumer.class);
		Function<CodecReader, Function> decodeCall02 = mock(Function.class);
		BasicCodecRegistry.registerCodec(context, Function.class, encodeCall02, decodeCall02);

		verify(context, times(2)).getJavaTypeProvider();
		verify(context, times(2)).getDiagnostics();
		verify(context, times(2)).getRegistry();
		verify(reg, times(2)).registerCodec(any(), any());
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#BasicCodecRegistry()}.
	 */
	@Test
	void testBasicCodecRegistry() {
		registry = new BasicCodecRegistry();
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#getCodec(org.gecko.codec.CodecType)}.
	 */
	@Test
	void testGetCodec() {
		assertTrue(registry.getDiagnostics().isEmpty());
		assertNull(registry.getCodec(BasicCodecTypes.NULL));
		assertEquals(1, registry.getDiagnostics().size());
		
		assertNull(registry.getCodec(BasicCodecTypes.ERROR));
		assertEquals(2, registry.getDiagnostics().size());
		Optional<CodecType> typeOptional = registry.getJavaTypeProvider().getTypeFrom(Function.class);
		assertTrue(typeOptional.isPresent());
		assertNull(registry.getCodec(typeOptional.get()));
		assertEquals(3, registry.getDiagnostics().size());
		
		Codec<?> codec = registry.getCodec(BasicCodecTypes.BOOL);
		assertEquals(3, registry.getDiagnostics().size());
		assertNotNull(codec);
		assertEquals(codec.getCodecType(), BasicCodecTypes.BOOL);
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#getCodecOpt(org.gecko.codec.CodecType)}.
	 */
	@Test
	void testGetCodecOpt() {
		assertTrue(registry.getDiagnostics().isEmpty());
		Optional<Codec<?>> codecOpt = registry.getCodecOpt(BasicCodecTypes.NULL);
		assertNotNull(codecOpt);
		assertTrue(codecOpt.isEmpty());
		assertEquals(1, registry.getDiagnostics().size());
		
		codecOpt = registry.getCodecOpt(BasicCodecTypes.ERROR);
		assertNotNull(codecOpt);
		assertTrue(codecOpt.isEmpty());
		assertEquals(2, registry.getDiagnostics().size());
		Optional<CodecType> typeOptional = registry.getJavaTypeProvider().getTypeFrom(Function.class);
		assertTrue(typeOptional.isPresent());
		codecOpt = registry.getCodecOpt(typeOptional.get());
		assertNotNull(codecOpt);
		assertTrue(codecOpt.isEmpty());
		assertEquals(3, registry.getDiagnostics().size());
		
		codecOpt = registry.getCodecOpt(BasicCodecTypes.BOOL);
		assertEquals(3, registry.getDiagnostics().size());
		assertNotNull(codecOpt);
		assertTrue(codecOpt.isPresent());
		assertEquals(codecOpt.get().getCodecType(), BasicCodecTypes.BOOL);
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#getDiagnostics()}.
	 */
	@Test
	void testGetDiagnostics() {
		assertNotNull(registry.getDiagnostics());
		assertTrue(registry.getDiagnostics().isEmpty());
		registry.getDiagnostics().add(Diagnostics.createInfoDiagnostic("Foo"));
		assertFalse(registry.getDiagnostics().isEmpty());
		assertEquals(1, registry.getDiagnostics().size());
		assertEquals("Foo", registry.getDiagnostics().get(0).getMessage());
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#getProperties()}.
	 */
	@Test
	void testGetProperties() {
		assertNotNull(registry.getProperties());
		assertTrue(registry.getProperties().isEmpty());
		registry.getProperties().put("foo", "bar");
		assertFalse(registry.getProperties().isEmpty());
		assertEquals(1, registry.getProperties().size());
		assertEquals("bar", registry.getProperties().get("foo"));
		
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#getTypeProvider()}.
	 */
	@Test
	void testGetTypeProvider() {
		assertNotNull(registry.getTypeProvider());
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#getJavaTypeProvider()}.
	 */
	@Test
	void testGetJavaTypeProvider() {
		assertNotNull(registry.getJavaTypeProvider());
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodecRegistry#getRegistry()}.
	 */
	@Test
	void testGetRegistry() {
		assertNotNull(registry.getRegistry());
		assertEquals(registry, registry.getRegistry());
	}

}
