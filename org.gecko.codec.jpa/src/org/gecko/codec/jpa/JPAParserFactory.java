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

import org.gecko.codec.CodecParserFactory;
import org.gecko.codec.CodecReaderProvider;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.IOContext;

import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author ilenia
 * @since Nov 21, 2024
 */
@Component(immediate=true, name = "JPAParserFactory", service = CodecParserFactory.class, property = {"type=jpa"})
public class JPAParserFactory implements CodecParserFactory<EntityManagerFactory, JsonParser> {

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecParserFactory#createParser(com.fasterxml.jackson.core.io.IOContext, org.gecko.codec.CodecReaderProvider)
	 */
	@Override
	public JsonParser createParser(IOContext context, CodecReaderProvider<EntityManagerFactory> provider) {
		return null;
	}

}
