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

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import org.gecko.codec.CodecType;
import org.gecko.codec.codecs.Codec;
import org.gecko.codec.decode.CodecReader;
import org.gecko.codec.diagnostic.Diagnostics;

/**
 * Decoder context implementation
 * @author Mark Hoffmann
 * @since 05.04.2023
 */
public class DecoderContextImpl extends ContextImpl implements DecoderContext {
	
	/**
	 * Creates a new instance.
	 * @param context the parent context
	 */
	protected DecoderContextImpl(Context context) {
		super(context);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.decode.DecoderContext#getParentContext()
	 */
	@Override
	public DecoderContext getParentContext() {
		return getContext() instanceof DecoderContext ? (DecoderContext) getContext() : null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.decode.DecoderContext#decodeFromChild(org.gecko.codec.decode.CodecReader)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T decodeFromChild(CodecReader reader) {
		requireNonNull(reader);
		CodecType type = reader.getCurrentType();
		requireNonNull(type, "Error getting a non-null codec type from the reader");
		Codec<?> codec = getRegistry().getCodec(type);
		if (nonNull(codec)) {
			return (T)codec.decode(reader, DecoderContext.createFromParent(this));
		} else {
			getDiagnostics().add(Diagnostics.createErrorDiagnostic("No codec found for type " + type.getIdentifier().get(0)));
			return null;
		}
	}

}
