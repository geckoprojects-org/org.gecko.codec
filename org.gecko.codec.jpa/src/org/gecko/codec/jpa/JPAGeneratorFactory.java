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
package org.gecko.codec.jpa;

import org.gecko.codec.CodecGeneratorFactory;
import org.gecko.codec.CodecWriterProvider;
import org.osgi.service.component.annotations.Component;

import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author ilenia
 * @since Nov 21, 2024
 */
@Component(immediate=true, name = "JPAGeneratorFactory", service = CodecGeneratorFactory.class, property = {"type=jpa"})
public class JPAGeneratorFactory implements CodecGeneratorFactory<EntityManagerFactory, JPACodecGenerator> {

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGeneratorFactory#createGenerator(org.gecko.codec.CodecWriterProvider)
	 */
	@Override
	public JPACodecGenerator createGenerator(CodecWriterProvider<EntityManagerFactory> provider) {
		return new JPACodecGenerator(provider.getWriter(), provider.getObjectCodec());
	}

}
