/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * Factory for codec specific {@link JsonGenerator}.
 * 
 * @author grune
 * @since Apr 10, 2024
 */
public interface CodecGeneratorFactory<W, G extends JsonGenerator> {
	G createGenerator(CodecWriterProvider<W> provider);

}
