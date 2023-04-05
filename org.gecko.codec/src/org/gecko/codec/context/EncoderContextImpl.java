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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import org.gecko.codec.CodecType;
import org.gecko.codec.CodecValue;
import org.gecko.codec.codecs.BasicCodecTypes;
import org.gecko.codec.codecs.BasicCodecValue;
import org.gecko.codec.codecs.Codec;
import org.gecko.codec.diagnostic.Diagnostics;
import org.gecko.codec.encode.CodecWriter;

/**
 * Encoder context implementation
 * @author Mark Hoffmann
 * @since 05.04.2023
 */
public class EncoderContextImpl extends ContextImpl implements EncoderContext {
	
	/**
	 * Creates a new instance.
	 * @param context the parent context
	 */
	EncoderContextImpl(Context context) {
		super(context);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.encode.EncoderContext#getParentContext()
	 */
	@Override
	public EncoderContext getParentContext() {
		return getContext() instanceof EncoderContext ? (EncoderContext) getContext() : null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.encode.EncoderContext#encodeChild(org.gecko.codec.encode.CodecWriter, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void encodeWithChild(CodecWriter writer, Object value) {
		requireNonNull(writer);
		if (isNull(value)) {
			writer.writeNull();
		} else {
			Optional<CodecType> valueTypeOptional = getJavaTypeProvider().getTypeFrom(value.getClass());
			CodecType valueType = valueTypeOptional.orElse(BasicCodecTypes.ERROR);
			Codec<Object> codec = (Codec<Object>) getRegistry().getCodec(valueType);
			if (nonNull(codec)) {
				codec.encode(writer, (CodecValue<Object>)BasicCodecValue.createCodecValue((Object)value), this);
			} else {
				getDiagnostics().add(Diagnostics.createErrorDiagnostic(String.format("No codec found for type '%s'", valueType.getIdentifier().get(0))));
			}
		}
	}

}
