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

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.gecko.codec.CodecType;
import org.gecko.codec.CodecValue;
import org.gecko.codec.context.DecoderContext;
import org.gecko.codec.context.EncoderContext;
import org.gecko.codec.decode.CodecReader;
import org.gecko.codec.diagnostic.Diagnostic;
import org.gecko.codec.diagnostic.Diagnostics;
import org.gecko.codec.encode.CodecWriter;

/**
 * Very basic codec implementation
 * @author Mark Hoffmann
 * @since 04.04.2023
 */
public class BasicCodec<T> implements Codec<T> {

	private final CodecType type;
	private List<Diagnostic> diagnostics;
	private BiConsumer<CodecWriter, T> encodeConsumer;
	private Function<CodecReader, T> decodeFunction;

	static class BasicCodecBuilder<T> {

		private List<Diagnostic> diagnostics;
		private BiConsumer<CodecWriter, T> encodeConsumer;
		private Function<CodecReader, T> decodeFunction;
		private final CodecType type;

		private BasicCodecBuilder(CodecType type) {
			requireNonNull(type);
			this.type = type;
		}

		public BasicCodecBuilder<T> diagnostics(List<Diagnostic> diagnostics) {
			requireNonNull(diagnostics);
			this.diagnostics = diagnostics;
			return this;
		}

		public BasicCodecBuilder<T> encodeConsumer(BiConsumer<CodecWriter, T> encodeCall) {
			requireNonNull(encodeCall);
			this.encodeConsumer = encodeCall;
			return this;
		}

		public BasicCodecBuilder<T> decodeFunction(Function<CodecReader, T> decodeFunction) {
			requireNonNull(decodeFunction);
			this.decodeFunction = decodeFunction;
			return this;
		}

		public BasicCodec<T> build() {
			requireNonNull(type);
			requireNonNull(diagnostics);
			requireNonNull(encodeConsumer);
			requireNonNull(decodeFunction);
			BasicCodec<T> codec = new BasicCodec<>(type, diagnostics);
			codec.setEncodeConsumer(encodeConsumer);
			codec.setDecoderFunction(decodeFunction);
			codec.setDiagnostics(diagnostics);
			return codec;
		}
	}

	/**
	 * Creates a new instance.
	 */
	BasicCodec(CodecType type, List<Diagnostic> diagnostics) {
		requireNonNull(type);
		this.type = type;
		if (nonNull(diagnostics)) {
			this.diagnostics = diagnostics;
		} else {
			this.diagnostics = new ArrayList<>();
		}
	}

	private void setDiagnostics(List<Diagnostic> diagnostics) {
		requireNonNull(diagnostics);
		this.diagnostics = diagnostics;
	}

	void setEncodeConsumer(BiConsumer<CodecWriter, T> encodeConsumer) {
		requireNonNull(encodeConsumer);
		this.encodeConsumer = encodeConsumer;
	}

	void setDecoderFunction(Function<CodecReader, T> decodeFunction) {
		requireNonNull(decodeFunction);
		this.decodeFunction = decodeFunction;
	}

	public static <T> BasicCodecBuilder<T> create(CodecType type) {
		return new BasicCodecBuilder<>(type);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.encode.Encoder#encode(org.gecko.codec.encode.CodecWriter, org.gecko.codec.CodecValue, org.gecko.codec.encode.EncoderContext)
	 */
	@Override
	public void encode(CodecWriter writer, CodecValue<T> value, EncoderContext context) {
		requireNonNull(writer);
		requireNonNull(value);
		if (nonNull(encodeConsumer)) {
			T v = null;
			try {
				v = value.getValue();
				encodeConsumer.accept(writer, v);
			} catch (Exception e) {
				Diagnostic diagnostic = Diagnostics.createErrorDiagnostic("Error encoding value " + v, e);
				diagnostics.add(diagnostic);
			}
		} else {
			Diagnostic diagnostic = Diagnostics.createErrorDiagnostic("No encode consumer is available");
			diagnostics.add(diagnostic);
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.encode.Encoder#getCodecType()
	 */
	@Override
	public CodecType getCodecType() {
		return type;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.decode.Decoder#decode(org.gecko.codec.decode.CodecReader, org.gecko.codec.decode.DecoderContext)
	 */
	@Override
	public Optional<CodecValue<T>> decode(CodecReader reader, DecoderContext context) {
		requireNonNull(reader);
		requireNonNull(context);
		if (nonNull(decodeFunction)) {
			T v = null;
			try {
				v = decodeFunction.apply(reader);
				return Optional.of(BasicCodecValue.<T>createCodecValue(v));
			} catch (Exception e) {
				Diagnostic diagnostic = Diagnostics.createErrorDiagnostic("Error creating a codec value for " + v, e, false);
				diagnostics.add(diagnostic);
				return Optional.empty();
			}
		} else {
			Diagnostic diagnostic = Diagnostics.createErrorDiagnostic("No decode function is available");
			diagnostics.add(diagnostic);
			return Optional.empty();
		}
	}

	/**
	 * Returns the diagnostics.
	 * @return the diagnostics
	 */
	public List<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

}
