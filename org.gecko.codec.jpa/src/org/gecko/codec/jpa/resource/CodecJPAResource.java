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
package org.gecko.codec.jpa.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.codec.CodecDataOutput;
import org.gecko.codec.configurator.ObjectMapperBuilderFactory;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.jackson.module.CodecModule.Builder;
import org.gecko.codec.jackson.resource.CodecResource;

import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author ilenia
 * @since Nov 21, 2024
 */
public class CodecJPAResource extends CodecResource {

	private EntityManagerFactory entityManagerFactory;

	/**
	 * Creates a new instance.
	 * @param uri
	 * @param modelInfoService
	 * @param moduleBuilder
	 * @param objMapperBuilderFactory
	 */
	public CodecJPAResource(URI uri, CodecModelInfo modelInfoService, Builder moduleBuilder,
			ObjectMapperBuilderFactory objMapperBuilderFactory) {
		super(uri, modelInfoService, moduleBuilder, objMapperBuilderFactory);
	}
	
	public CodecJPAResource(URI uri, CodecModelInfo modelInfoService, Builder moduleBuilder,
			ObjectMapperBuilderFactory objMapperBuilderFactory, EntityManagerFactory entityManagerFactory) {
		super(uri, modelInfoService, moduleBuilder, objMapperBuilderFactory);
		this.entityManagerFactory = entityManagerFactory;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.resource.CodecResource#doSave(java.io.OutputStream, java.util.Map)
	 */
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		super.doSave(outputStream, options);

		try {
			mapper.writer()
			.with(EMFContext.from(options))
			.writeValue(new CodecDataOutput<>(entityManagerFactory, mapper), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.resource.CodecResource#doLoad(java.io.InputStream, java.util.Map)
	 */
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		super.doLoad(inputStream, options);
	}

}
