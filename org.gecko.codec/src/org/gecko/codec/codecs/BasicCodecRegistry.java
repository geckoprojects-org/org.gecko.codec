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

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.gecko.codec.CodecType;
import org.gecko.codec.CodecTypeProvider;
import org.gecko.codec.context.Context;
import org.gecko.codec.decode.CodecReader;
import org.gecko.codec.diagnostic.Diagnostic;
import org.gecko.codec.diagnostic.Diagnostics;
import org.gecko.codec.encode.CodecWriter;

/**
 * A basic codec registry to regsiter an get codecs 
 * @author Mark Hoffmann
 * @since 05.04.2023
 */
public class BasicCodecRegistry implements CodecRegistry, Context {
	
	private final Map<String, Object> properties = new HashMap<>();
	private final List<Diagnostic> diags = new LinkedList<>();
	private final JavaCodecTypeProvider types = new JavaCodecTypeProvider();
	private final Map<CodecType, Codec<?>> registry = new ConcurrentHashMap<>();
	
	/**
	 * Registers a {@link BasicCodec} from a given Java type using corresponding encoding callback and decoding callback
	 * @param <T>
	 * @param context the context to register the codec for
	 * @param clazz the java class type, the codec is valid for
	 * @param encodeCall the encode callback
	 * @param decodeCall the decode callback
	 */
	public static <T> void registerCodec(Context context, Class<T> clazz, BiConsumer<CodecWriter, T> encodeCall, Function<CodecReader, T> decodeCall) {
		requireNonNull(context);
		requireNonNull(clazz);
		requireNonNull(encodeCall);
		requireNonNull(decodeCall);
		Optional<CodecType> typeOptional = context.getJavaTypeProvider().getTypeFrom(clazz);
		if (typeOptional.isPresent()) {
			CodecType type = typeOptional.get();
			Codec<T> codec = BasicCodec.<T>
				create(type).
				diagnostics(context.getDiagnostics()).
				encodeConsumer(encodeCall).
				decodeFunction(decodeCall).
				build();
			context.getRegistry().registerCodec(type, codec);
		} else {
			context.getDiagnostics().add(Diagnostics.createErrorDiagnostic(String.format("No codec found for class '%s'", clazz.getName())));
		}
	}
	/**
	 * Creates a new instance.
	 */
	public BasicCodecRegistry() {
		registerCodec(this, Boolean.class, CodecWriter::writeBoolean, CodecReader::readBoolean);
		registerCodec(this, Character.class, CodecWriter::writeChar, CodecReader::readChar);
		registerCodec(this, Double.class, CodecWriter::writeDouble, CodecReader::readDouble);
		registerCodec(this, Float.class, CodecWriter::writeFloat, CodecReader::readFloat);
		registerCodec(this, Byte.class, CodecWriter::writeByte, CodecReader::readByte);
		registerCodec(this, Integer.class, CodecWriter::writeInt, CodecReader::readInt);
		registerCodec(this, Long.class, CodecWriter::writeLong, CodecReader::readLong);
		registerCodec(this, Short.class, CodecWriter::writeShort, CodecReader::readShort);
		registerCodec(this, String.class, CodecWriter::writeString, CodecReader::readString);
		registerCodec(this, byte[].class, CodecWriter::writeByteArray, CodecReader::readByteArray);
		registerCodec(this, Date.class, CodecWriter::writeDateTime, CodecReader::readDateTime);
		registerCodec(this, Calendar.class, CodecWriter::writeCalendar, CodecReader::readCalendar);
		registerCodec(this, AtomicInteger.class, (w, v)->w.writeInt(v.get()), (r)->new AtomicInteger(r.readInt()));
		registerCodec(this, AtomicLong.class, (w, v)->w.writeLong(v.get()), (r)->new AtomicLong(r.readLong()));
		registerCodec(this, AtomicBoolean.class, (w, v)->w.writeBoolean(v.get()), (r)->new AtomicBoolean(r.readBoolean()));
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.codecs.CodecRegistry#getCodec(org.gecko.codec.CodecType)
	 */
	@Override
	public Codec<?> getCodec(CodecType type) {
		if (isNull(type)) {
			return null;
		}
		Codec<?> codec = registry.get(type);
		if (isNull(codec)) {
			getDiagnostics().add(Diagnostics.createErrorDiagnostic("No codec found for type: " + type.getIdentifier().get(0)));
		}
		return codec;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.codecs.CodecRegistry#getCodecOpt(org.gecko.codec.CodecType)
	 */
	@Override
	public Optional<Codec<?>> getCodecOpt(CodecType type) {
		return Optional.ofNullable(getCodec(type));
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.codecs.CodecRegistry#registerCodec(org.gecko.codec.CodecType, org.gecko.codec.codecs.Codec)
	 */
	@Override
	public void registerCodec(CodecType type, Codec<?> codec) {
		requireNonNull(type);
		requireNonNull(codec);
		registry.put(type, codec);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.codecs.CodecRegistry#getDiagnostics()
	 */
	@Override
	public List<Diagnostic> getDiagnostics() {
		return diags;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getTypeProvider()
	 */
	@Override
	public CodecTypeProvider<?> getTypeProvider() {
		return types;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getJavaTypeProvider()
	 */
	@Override
	public JavaCodecTypeProvider getJavaTypeProvider() {
		return types;
	}
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getRegistry()
	 */
	@Override
	public CodecRegistry getRegistry() {
		return this;
	}
	
}
