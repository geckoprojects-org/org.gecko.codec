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

import com.fasterxml.jackson.core.TSFBuilder;

/**
 * 
 * @author ilenia
 * @param <W>
 * @param <G>
 * @since Aug 14, 2024
 */

public abstract class CodecFactoryConfigurator {
	
	
	public abstract TSFBuilder<?,?> getFactoryBuilder();
}