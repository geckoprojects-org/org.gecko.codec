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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.gecko.codec.CodecValue;
import org.gecko.codec.codecs.BasicCodec.BasicCodecBuilder;
import org.gecko.codec.context.DecoderContext;
import org.gecko.codec.context.EncoderContext;
import org.gecko.codec.decode.CodecReader;
import org.gecko.codec.diagnostic.Diagnostic;
import org.gecko.codec.encode.CodecWriter;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 05.04.2023
 */
class BasicCodecTest {

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodec#BasicCodec(org.gecko.codec.CodecType, java.util.List)}.
	 */
	@Test
	void testBasicCodec() {
		assertThrows(NullPointerException.class, ()->new BasicCodec<>(null, null));
		BasicCodec<?> codec = new BasicCodec<>(BasicCodecTypes.CHAR, null);
		assertTrue(codec.getDiagnostics().isEmpty());
		List<Diagnostic> diags = List.of(mock(Diagnostic.class));
		codec = new BasicCodec<>(BasicCodecTypes.CHAR, diags);
		assertEquals(1, codec.getDiagnostics().size());
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodec#setEncodeConsumer(java.util.function.BiConsumer)}.
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	void testSetEncodeConsumer() {
		List<Diagnostic> diags = new ArrayList<>();
		BasicCodec<String> codec = new BasicCodec<>(BasicCodecTypes.STRING, diags);
		assertTrue(codec.getDiagnostics().isEmpty());
		EncoderContext ctx = mock(EncoderContext.class);
		CodecValue<String> value = mock(CodecValue.class);
		CodecWriter writer = mock(CodecWriter.class);
		
		codec.encode(writer, value, ctx);
		
		verify(value, never()).getValue();
		assertEquals(1, diags.size());

		BiConsumer<CodecWriter, String> encodeCall = mock(BiConsumer.class);
		codec.setEncodeConsumer(encodeCall);
		codec.encode(writer, value, ctx);
		verify(value, times(1)).getValue();
		verify(encodeCall, times(1)).accept(any(), any());
		assertEquals(1, diags.size());
		
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodec#setDecoderFunction(java.util.function.Function)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testSetDecoderFunction() {
		List<Diagnostic> diags = new ArrayList<>();
		BasicCodec<String> codec = new BasicCodec<>(BasicCodecTypes.STRING, diags);
		assertTrue(codec.getDiagnostics().isEmpty());
		DecoderContext ctx = mock(DecoderContext.class);
		CodecReader reader = mock(CodecReader.class);
		Function<CodecReader, String> decodeCall = mock(Function.class);
		
		assertTrue(codec.getDiagnostics().isEmpty());
		Optional<CodecValue<String>> result = codec.decode(reader, ctx);
		assertNotNull(result);
		assertTrue(result.isEmpty());
		assertEquals(1, diags.size());
		verify(decodeCall, never()).apply(any());   

		when(decodeCall.apply(any())).thenReturn("foo");
		codec.setDecoderFunction(decodeCall);
		result = codec.decode(reader, ctx);
		assertNotNull(result);
		assertTrue(result.isPresent());
		assertEquals("foo", result.get().getValue());
		assertEquals(1, diags.size());
		
		verify(decodeCall, times(1)).apply(any());
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodec#create(org.gecko.codec.CodecType)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testCreate() {
		assertThrows(NullPointerException.class, ()->BasicCodec.create(null));
		BasicCodecBuilder<String> builder = BasicCodec.<String>create(BasicCodecTypes.STRING);
		assertNotNull(builder);
		BiConsumer<CodecWriter, String> encodeCall = mock(BiConsumer.class);
		Function<CodecReader, String> decodeCall = mock(Function.class);
		
		assertThrows(NullPointerException.class, ()->BasicCodec.<String>create(BasicCodecTypes.STRING).build());
		assertThrows(NullPointerException.class, ()->BasicCodec.<String>create(BasicCodecTypes.STRING).diagnostics(List.of(mock(Diagnostic.class))).build());
		assertThrows(NullPointerException.class, ()->BasicCodec.<String>create(BasicCodecTypes.STRING).diagnostics(List.of(mock(Diagnostic.class))).build());
		assertThrows(NullPointerException.class, ()->BasicCodec.<String>create(BasicCodecTypes.STRING).diagnostics(List.of(mock(Diagnostic.class))).decodeFunction(decodeCall).build());
		assertThrows(NullPointerException.class, ()->BasicCodec.<String>create(BasicCodecTypes.STRING).diagnostics(List.of(mock(Diagnostic.class))).encodeConsumer(encodeCall).build());
		assertThrows(NullPointerException.class, ()->BasicCodec.<String>create(BasicCodecTypes.STRING).encodeConsumer(encodeCall).build());
		assertThrows(NullPointerException.class, ()->BasicCodec.<String>create(BasicCodecTypes.STRING).decodeFunction(decodeCall).build());
		assertThrows(NullPointerException.class, ()->BasicCodec.<String>create(BasicCodecTypes.STRING).decodeFunction(decodeCall).encodeConsumer(encodeCall).build());
		
		BasicCodec<String> codec = BasicCodec.<String>
			create(BasicCodecTypes.STRING).
			decodeFunction(decodeCall).
			encodeConsumer(encodeCall).
			diagnostics(List.of(mock(Diagnostic.class))).
			build();
		assertNotNull(codec);
		assertEquals(1, codec.getDiagnostics().size());
		
		DecoderContext dctx = mock(DecoderContext.class);
		CodecReader reader = mock(CodecReader.class);
		Optional<CodecValue<String>> result = codec.decode(reader, dctx);
		assertNotNull(result);
		assertTrue(result.isPresent());
		assertEquals(1, codec.getDiagnostics().size());
		
		verify(decodeCall, times(1)).apply(any());
		
		EncoderContext ectx = mock(EncoderContext.class);
		CodecValue<String> value = mock(CodecValue.class);
		CodecWriter writer = mock(CodecWriter.class);
		
		codec.encode(writer, value, ectx);
		assertNotNull(result);
		assertTrue(result.isPresent());
		assertEquals(1, codec.getDiagnostics().size());
		
		verify(decodeCall, times(1)).apply(any());
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodec#encode(org.gecko.codec.encode.CodecWriter, org.gecko.codec.CodecValue, org.gecko.codec.context.EncoderContext)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testEncode() {
		List<Diagnostic> diags = new ArrayList<>();
		BasicCodec<String> codec = new BasicCodec<>(BasicCodecTypes.STRING, diags);
		assertTrue(codec.getDiagnostics().isEmpty());
		
		EncoderContext ctx = mock(EncoderContext.class);
		CodecValue<String> value = mock(CodecValue.class);
		CodecWriter writer = mock(CodecWriter.class);
		BiConsumer<CodecWriter, String> encodeCall = mock(BiConsumer.class);
		
		assertThrows(NullPointerException.class, ()->codec.encode(null, null, null));
		assertThrows(NullPointerException.class, ()->codec.encode(writer, null, null));
		assertThrows(NullPointerException.class, ()->codec.encode(null, value, null));

		codec.encode(writer, value, null);
		
		verify(value, never()).getValue();
		assertEquals(1, diags.size());
		
		reset(value);
		reset(encodeCall);

		codec.setEncodeConsumer(encodeCall);
		codec.encode(writer, value, null);
		
		verify(value, times(1)).getValue();
		verify(encodeCall, times(1)).accept(any(), any());
		assertEquals(1, diags.size());
		
		reset(value);
		reset(encodeCall);
		
		codec.setEncodeConsumer(encodeCall);
		codec.encode(writer, value, ctx);
		
		verify(value, times(1)).getValue();
		verify(encodeCall, times(1)).accept(any(), any());
		assertEquals(1, diags.size());
		
		reset(value);
		reset(encodeCall);
		
		when(value.getValue()).thenThrow(IllegalStateException.class);
		codec.encode(writer, value, ctx);
		verify(value, times(1)).getValue();
		verify(encodeCall, never()).accept(any(), any());
		assertEquals(2, diags.size());
		
		reset(value);
		reset(encodeCall);
		
		when(value.getValue()).thenReturn("foo");
		doThrow(IllegalStateException.class).when(encodeCall).accept(any(), any());
		codec.encode(writer, value, ctx);
		verify(value, times(1)).getValue();
		verify(encodeCall, times(1)).accept(any(), any());
		assertEquals(3, diags.size());
		
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodec#getCodecType()}.
	 */
	@Test
	void testGetCodecType() {
		BasicCodec<Character> codec = new BasicCodec<>(BasicCodecTypes.CHAR, null);
		assertEquals(BasicCodecTypes.CHAR, codec.getCodecType());
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodec#decode(org.gecko.codec.decode.CodecReader, org.gecko.codec.context.DecoderContext)}.
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	void testDecode() {
		List<Diagnostic> diags = new ArrayList<>();
		BasicCodec<String> codec = new BasicCodec<>(BasicCodecTypes.STRING, diags);
		assertTrue(codec.getDiagnostics().isEmpty());
		
		DecoderContext ctx = mock(DecoderContext.class);
		CodecReader reader = mock(CodecReader.class);
		
		assertThrows(NullPointerException.class, ()->codec.decode(null, null));
		assertThrows(NullPointerException.class, ()->codec.decode(null, ctx));
		assertThrows(NullPointerException.class, ()->codec.decode(reader, null));
		
		Function<CodecReader, String> decodeCall = mock(Function.class);
		
		Optional<CodecValue<String>> result = codec.decode(reader, ctx);
		assertNotNull(result);
		assertTrue(result.isEmpty());
		assertEquals(1, diags.size());
		verify(decodeCall, never()).apply(any());   
		
		
		reset(decodeCall);
		
		when(decodeCall.apply(any())).thenReturn("foo");
		codec.setDecoderFunction(decodeCall);
		result = codec.decode(reader, ctx);
		
		assertNotNull(result);
		assertTrue(result.isPresent());
		assertEquals("foo", result.get().getValue());
		assertEquals(1, diags.size());
		
		verify(decodeCall, times(1)).apply(any());
		
		reset(decodeCall);
		
		when(decodeCall.apply(any())).thenThrow(IllegalStateException.class);
		result = codec.decode(reader, ctx);
		assertNotNull(result);
		assertTrue(result.isEmpty());
		assertEquals(2, diags.size());
		
		verify(decodeCall, times(1)).apply(any());
		
		
	}

	/**
	 * Test method for {@link org.gecko.codec.codecs.BasicCodec#getDiagnostics()}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testGetDiagnostics() {
		BasicCodec<Character> codec = new BasicCodec<>(BasicCodecTypes.CHAR, null);
		assertTrue(codec.getDiagnostics().isEmpty());
		List<Diagnostic> diags = new ArrayList<>();
		diags.add(mock(Diagnostic.class));
		
		codec = new BasicCodec<>(BasicCodecTypes.CHAR, diags);
		assertEquals(1, codec.getDiagnostics().size());
		assertEquals(diags, codec.getDiagnostics());
		
		EncoderContext ctx = mock(EncoderContext.class);
		CodecValue<Character> value = mock(CodecValue.class);
		CodecWriter writer = mock(CodecWriter.class);
		
		codec.encode(writer, value, ctx);
		assertEquals(2, codec.getDiagnostics().size());
		
	}

}
