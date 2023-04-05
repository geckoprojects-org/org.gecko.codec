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

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import org.gecko.codec.CodecTypeProvider;
import org.gecko.codec.codecs.CodecRegistry;
import org.gecko.codec.codecs.JavaCodecTypeProvider;
import org.gecko.codec.diagnostic.Diagnostic;

/**
 * Context implementation
 * @author Mark Hoffmann
 * @since 05.04.2023
 */
public class ContextImpl implements Context {
	
	private final Context context;

	/**
	 * Creates a new instance.
	 * @param context the parent context
	 */
	ContextImpl(Context context) {
		requireNonNull(context);
		this.context = context;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return context.getProperties();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getDiagnostics()
	 */
	@Override
	public List<Diagnostic> getDiagnostics() {
		return context.getDiagnostics();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getTypeProvider()
	 */
	@Override
	public CodecTypeProvider<?> getTypeProvider() {
		return context.getTypeProvider();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getJavaTypeProvider()
	 */
	@Override
	public JavaCodecTypeProvider getJavaTypeProvider() {
		return context.getJavaTypeProvider();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.context.Context#getRegistry()
	 */
	@Override
	public CodecRegistry getRegistry() {
		return context.getRegistry();
	}
	
	/**
	 * Returns the context.
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

}
