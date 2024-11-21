///**
// * Copyright (c) 2012 - 2024 Data In Motion and others.
// * All rights reserved. 
// * 
// * This program and the accompanying materials are made
// * available under the terms of the Eclipse Public License 2.0
// * which is available at https://www.eclipse.org/legal/epl-2.0/
// *
// * SPDX-License-Identifier: EPL-2.0
// * 
// * Contributors:
// *     Data In Motion - initial API and implementation
// */
//package org.gecko.codec.jpa;
//
//import java.io.IOException;
//import java.util.Map;
//
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emfcloud.jackson.databind.EMFContext;
//import org.gecko.codec.Codec;
//import org.gecko.codec.CodecDataOutput;
//import org.gecko.codec.jpa.resource.CodecJPAResource;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import jakarta.persistence.EntityManagerFactory;
//
///**
// * 
// * @author ilenia
// * @since Nov 21, 2024
// */
//public class JPACodec implements Codec<EntityManagerFactory, EObject> {
//	
//	private ObjectMapper mapper;
//	private CodecJPAResource resource;
//	private Map<?, ?> options;
//
//	JPACodec(ObjectMapper mapper, CodecJPAResource resource, Map<?, ?> options) {
//		this.mapper = mapper;
//		this.resource = resource;
//		this.options = options;		
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.CodecEncoder#encode(java.lang.Object, java.lang.Object)
//	 */
//	@Override
//	public void encode(EntityManagerFactory writer, EObject value) {
//		try {
//			mapper.writer()
//			.with(EMFContext.from(options))
//			.writeValue(new CodecDataOutput<>(writer, mapper), value);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.CodecEncoder#getEncoderClass()
//	 */
//	@Override
//	public Class<EObject> getEncoderClass() {
//		return EObject.class;
//	}
//
//}
