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

import java.util.List;
import java.util.Optional;

import org.gecko.codec.CodecType;
import org.gecko.codec.CodecValue;
import org.gecko.codec.context.DecoderContext;
import org.gecko.codec.context.EncoderContext;
import org.gecko.codec.decode.CodecReader;
import org.gecko.codec.diagnostic.Diagnostic;
import org.gecko.codec.diagnostic.Diagnostics;
import org.gecko.codec.encode.CodecWriter;

/**
 * 
 * @author mark
 * @since 05.04.2023
 */
public class OptionalCodec<T> extends BasicCodec<Optional<T>> {

	/**
	 * Creates a new instance.
	 * @param type
	 * @param diagnostics
	 */
	OptionalCodec(CodecType type, List<Diagnostic> diagnostics) {
		super(type, diagnostics);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.codecs.BasicCodec#encode(org.gecko.codec.encode.CodecWriter, org.gecko.codec.CodecValue, org.gecko.codec.encode.EncoderContext)
	 */
	@Override
	public void encode(CodecWriter writer, CodecValue<Optional<T>> value, EncoderContext context) {
		if (!getCodecType().equals(value.getCodecType())) {
			context.getDiagnostics().add(Diagnostics.createErrorDiagnostic("The codecs type does not fit to the values type"));
			return;
		}
		Optional<T> optional = value.getValue();
		if (optional.isPresent()) {
			T v = optional.get();
			context.encodeWithChild(writer, v);
			return;
		} else {
			writer.writeNull();
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.codecs.BasicCodec#decode(org.gecko.codec.decode.CodecReader, org.gecko.codec.decode.DecoderContext)
	 */
	@Override
	public Optional<CodecValue<Optional<T>>> decode(CodecReader reader, DecoderContext context) {
		T value = context.decodeFromChild(reader);
		Optional<T> optional = Optional.ofNullable(value);
		return Optional.of(BasicCodecValue.createCodecValue(optional));
	}

	
}
