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
package org.gecko.codec.demo.jackson;

import java.util.Map;
import java.util.logging.Logger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * 
 * @author ilenia
 * @since Aug 14, 2024
 */
@Component(name = "ObjectMapperConfigurator", service = ObjectMapperConfigurator.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ObjectMapperConfigurator {
	
	@Reference
	CodecFactoryConfigurator codecFactoryConfigurator;

	private static final Logger LOGGER = Logger.getLogger(ObjectMapperConfigurator.class.getName());
	private ObjectMapper mapper;
	
	@Activate
	public void activate(Map<String, Object> properties) {
		buildAndConfigureObjectMapper(properties);
	}

	public ObjectMapper getObjectMapper() {
		return mapper;
	}
	
	private void buildAndConfigureObjectMapper(Map<String, Object> properties) {
		if(properties == null || properties.isEmpty()) {
			mapper = JsonMapper.builder(codecFactoryConfigurator.getCodecFactory()).build();
			return;
		}
		String[] enableFeatures = (String[]) properties.getOrDefault("enableFeatures", new String[0]);
		for(String f : enableFeatures) {
			setFeature(f, true);
		}
		String[] disableFeatures = (String[]) properties.getOrDefault("disableFeatures", new String[0]);
		for(String f : disableFeatures) {
			setFeature(f, false);
		}
	}
	
	private void setFeature(String featureString, boolean state) {
		if(featureString.contains(".")) featureString = featureString.substring(featureString.lastIndexOf(".")+1);
		try {
			if(state) mapper = JsonMapper.builder(codecFactoryConfigurator.getCodecFactory()).enable(MapperFeature.valueOf(featureString)).build();
			else mapper = JsonMapper.builder(codecFactoryConfigurator.getCodecFactory()).disable(MapperFeature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {

		} 
		try {
			if(state) mapper = JsonMapper.builder(codecFactoryConfigurator.getCodecFactory()).enable(SerializationFeature.valueOf(featureString)).build();
			else mapper = JsonMapper.builder(codecFactoryConfigurator.getCodecFactory()).disable(SerializationFeature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {

		} 
		try {
			if(state) mapper = JsonMapper.builder(codecFactoryConfigurator.getCodecFactory()).enable(DeserializationFeature.valueOf(featureString)).build();
			else mapper = JsonMapper.builder(codecFactoryConfigurator.getCodecFactory()).disable(DeserializationFeature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No ObjectMapper feature with name %s has been found", featureString));
		} 
	}
}
