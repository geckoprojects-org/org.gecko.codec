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
package org.gecko.codec.decode;

import java.util.Optional;

import org.gecko.codec.CodecValue;
import org.gecko.codec.context.DecoderContext;

/**
 * Decoder interface 
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public interface Decoder<T> {
	
	Optional<CodecValue<T>> decode(CodecReader reader, DecoderContext context);

}
