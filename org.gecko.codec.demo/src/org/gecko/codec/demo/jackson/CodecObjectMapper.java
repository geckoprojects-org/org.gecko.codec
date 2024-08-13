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
//package org.gecko.codec.demo.jackson;
//
//import java.util.Map;
//
//import org.osgi.service.component.annotations.Activate;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.annotations.ConfigurationPolicy;
//import org.osgi.service.component.annotations.Reference;
//
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
///**
// * This is our version of the {@link ObjectMapper}, so that we can make it as a configurable
// * component and be able to overwrite {@link MapperFeature}, {@link SerializationFeature}, 
// * and {@link DeserializationFeature}
// * @author ilenia
// * @since Aug 12, 2024
// */
//@Component(name = "CodecObjectMapper", service = CodecObjectMapper.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
//public class CodecObjectMapper extends ObjectMapper {
//
//	@Reference(name="jsonFactory")
//	private JsonFactory jsonFactory;
//	
//	/** serialVersionUID */
//	private static final long serialVersionUID = -4289356195668406468L;
//	
//	private Map<String, Object> properties;
//	private ObjectMapper mapper;
//	
//	@Activate
//	public void activate(Map<String, Object> properties) {
//		this.properties = properties;
//		mapper = new ObjectMapper(jsonFactory);
//	}
//	
//	public Map<String, Object> getProperties() {
//		return Map.copyOf(properties);
//	}
//	
//	public 
//}
