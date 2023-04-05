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

import org.gecko.codec.decode.Decoder;
import org.gecko.codec.encode.Encoder;

/**
 * Codec interface
 * @author Mark Hoffmann
 * @since 04.04.2023
 */
public interface Codec<T> extends Encoder<T>, Decoder<T>{

}
