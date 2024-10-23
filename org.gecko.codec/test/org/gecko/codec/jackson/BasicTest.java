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
//package org.gecko.codec.jackson;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Map;
//
//import org.eclipse.emf.common.util.URI;
//import org.eclipse.emf.ecore.resource.Resource;
//import org.eclipse.emf.ecore.resource.ResourceSet;
//import org.eclipse.emf.ecore.resource.URIHandler;
//import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
//import org.eclipse.emfcloud.jackson.junit.model.Address;
//import org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne;
//import org.eclipse.emfcloud.jackson.junit.model.ModelFactory;
//import org.eclipse.emfcloud.jackson.junit.model.ModelPackage;
//import org.eclipse.emfcloud.jackson.junit.model.Sex;
//import org.eclipse.emfcloud.jackson.junit.model.User;
//import org.gecko.codec.jackson.databind.deser.CodecOutputStream;
//import org.gecko.codec.jackson.resource.CodecResourceFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
///**
// * 
// * @author mark
// * @since 09.01.2024
// */
//public class BasicTest {
//
//	private ResourceSet resourceSet;
//
//	@BeforeEach
//	public void setUp() {
//		resourceSet = new ResourceSetImpl();
//		resourceSet.getPackageRegistry().put(ModelPackage.eNS_URI, ModelPackage.eINSTANCE);
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new CodecResourceFactory());
//		resourceSet.getURIConverter().getURIHandlers().add(0, new URIHandler() {
//			
//			@Override
//			public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public boolean exists(URI uri, Map<?, ?> options) {
//				return canHandle(uri);
//			}
//			
//			@Override
//			public void delete(URI uri, Map<?, ?> options) throws IOException {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
//				return new CodecOutputStream();
//			}
//			
//			@Override
//			public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
//				return null;
//			}
//			
//			@Override
//			public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
//				return null;
//			}
//			
//			@Override
//			public boolean canHandle(URI uri) {
//				return uri.scheme().equals("mongo");
//			}
//		});
//	}
//
//	@Test
//	public void testSerializeType() {
//
//		User u1 = ModelFactory.eINSTANCE.createUser();
//		u1.setUserId("u1");
//		u1.setName("Mark");
//		u1.setSex(Sex.MALE);
//		Address a1 = ModelFactory.eINSTANCE.createAddress();
//		a1.setAddId("a1");
//		a1.setCity("Jena");
//		a1.setStreet("Kahlaische Strasse");
//		u1.setAddress(a1);
//
//		Resource resource = resourceSet.createResource(URI.createURI("mongo:test"));
//		resource.getContents().add(u1);
//		try {
//			resource.save(null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//
//	}
//	
//	@Test
//	public void testSerializeSuperType() {
//		
//		ConcreteTypeOne cto1 = ModelFactory.eINSTANCE.createConcreteTypeOne();
//		cto1.setName("test");
//		cto1.setPropTypeOne("testCTO");
//		
//		Resource resource = resourceSet.createResource(URI.createURI("mongo:test"));
//		resource.getContents().add(cto1);
//		try {
//			resource.save(null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//	
//	@Test
//	public void testSerializeNonContainment() {
//		
//		User u1 = ModelFactory.eINSTANCE.createUser();
//		u1.setUserId("u1");
//		u1.setName("Mark");
//		User u2 = ModelFactory.eINSTANCE.createUser();
//		u2.setUserId("u2");
//		u2.setName("Guido");
//		u1.setUniqueFriend(u2);
//		
//		Address a1 = ModelFactory.eINSTANCE.createAddress();
//		a1.setAddId("a1");
//		a1.setCity("Jena");
//		a1.setStreet("Kahlaische Strasse");
//		u1.setAddress(a1);
//		
//		Resource resource = resourceSet.createResource(URI.createURI("mongo:test"));
//		resource.getContents().add(u2);
//		resource.getContents().add(u1);
//		try {
//			resource.save(null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//	
//	@Test
//	public void testSerializeList() {
//		
//		User u1 = ModelFactory.eINSTANCE.createUser();
//		u1.setUserId("u1");
//		u1.setName("Mark");
//		
//		User u3= ModelFactory.eINSTANCE.createUser();
//		u3.setUserId("u3");
//		u3.setName("Jürgen");
//		User u4= ModelFactory.eINSTANCE.createUser();
//		u4.setUserId("u3");
//		u4.setName("Ilenia");
//		u1.getFriends().add(u3);
//		u1.getFriends().add(u4);
//		
//		Resource resource = resourceSet.createResource(URI.createURI("mongo:test"));
//		resource.getContents().add(u3);
//		resource.getContents().add(u4);
//		resource.getContents().add(u1);
//		try {
//			resource.save(null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//
//}
