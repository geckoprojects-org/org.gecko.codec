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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.gecko.codec.Codec;
import org.gecko.codec.CodecWriterProvider;
import org.gecko.codec.jpa.resource.CodecJPAResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author ilenia
 * @since Nov 21, 2024
 */
public class JPACodecProvider extends CodecWriterProvider<EntityManagerFactory> {

	private CodecJPAResource resource;
	private Map<?, ?> options;

	/**
	 * Creates a new instance.
	 * @param writer
	 * @param objectCodec
	 */
	public JPACodecProvider(EntityManagerFactory writer, ObjectMapper mapper, CodecJPAResource resource, Map<?, ?> options) {
		super(writer, mapper);
		this.resource = resource;
		this.options = options;
	}

	
	
	

}
