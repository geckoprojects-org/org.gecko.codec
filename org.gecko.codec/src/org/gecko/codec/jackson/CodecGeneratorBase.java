/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.codec.jackson;

import java.io.IOException;

/**
 * Codec generator extensions 
 * @author Mark Hoffmann
 * @since 12.01.2024
 */
public interface CodecGeneratorBase {

	boolean canWriteSuperTypes();

	void writeSuperTypes(String[] supertypes) throws IOException;
	
	boolean canWriteOneShotArray();
	
	<T> void writeArray(T[] array, int offset, int length, Class<T> clazz) throws IOException;

}