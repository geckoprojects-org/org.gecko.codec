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

import java.util.List;
import java.util.Map;

import org.gecko.codec.CodecTypeProvider;
import org.gecko.codec.codecs.CodecRegistry;
import org.gecko.codec.codecs.JavaCodecTypeProvider;
import org.gecko.codec.diagnostic.Diagnostic;

/**
 * Context information interface to transport data over the encoding / decoding process
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public interface Context {
	
	/**
	 * Returns the properties map or an empty map
	 * @return the properties map
	 */
	Map<String, Object> getProperties();
	
	/**
	 * Returns the list of diagnostics or an empty {@link List}
	 * @return the list of diagnostics or an empty {@link List}
	 */
	List<Diagnostic> getDiagnostics();
	
	/**
	 * Returns the codec type provider
	 * @return the codec type provider
	 */
	CodecTypeProvider<?> getTypeProvider();
	
	/**
	 * Returns a Java class base type provider
	 * @return a Java class base type provider
	 */
	JavaCodecTypeProvider getJavaTypeProvider();
	
	/**
	 * Returns the codec registry
	 * @return the codec registry
	 */
	CodecRegistry getRegistry();
	
}
