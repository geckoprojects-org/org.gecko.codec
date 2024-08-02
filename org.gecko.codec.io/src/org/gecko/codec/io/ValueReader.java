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
package org.gecko.codec.io;

import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * 
 * @author ilenia
 * @since Aug 1, 2024
 */
public interface ValueReader<V, T> {
	T readValue(V value, DeserializationContext context);
}