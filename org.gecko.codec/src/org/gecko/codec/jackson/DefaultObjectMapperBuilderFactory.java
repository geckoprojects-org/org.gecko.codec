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

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.gecko.codec.configurator.CodecFactoryConfigurator;
import org.gecko.codec.configurator.ObjectMapperBuilderFactory;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;

/**
 * Default implementation of {@link ObjectMapperBuilderFactory}
 * @author ilenia
 * @since Oct 30, 2024
 */
public class DefaultObjectMapperBuilderFactory implements ObjectMapperBuilderFactory {

	private final Map<String, Object> properties;
	private CodecFactoryConfigurator codecFactoryConfigurator;
	private static final Logger LOGGER = Logger.getLogger(DefaultObjectMapperBuilderFactory.class.getName());

	
	public DefaultObjectMapperBuilderFactory(Map<String, Object> properties, CodecFactoryConfigurator codecFactoryConfigurator) {
		this.properties = properties;
		this.codecFactoryConfigurator = codecFactoryConfigurator;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.ObjectMapperBuilderFactory#createObjectMapperBuilder()
	 */
	@Override
	public Builder createObjectMapperBuilder() {
		Builder objMapperBuilder = JsonMapper.builder(codecFactoryConfigurator.getFactoryBuilder().build());
		return buildAndConfigureObjectMapper(objMapperBuilder);
	}
	
	private Builder buildAndConfigureObjectMapper(Builder objMapperBuilder) {
		if(properties == null || properties.isEmpty()) {
			return objMapperBuilder;
		}
		String[] enableFeatures = (String[]) properties.getOrDefault("enableFeatures", new String[0]);
		for(String f : enableFeatures) {
			setFeature(f, true, objMapperBuilder);
		}
		String[] disableFeatures = (String[]) properties.getOrDefault("disableFeatures", new String[0]);
		for(String f : disableFeatures) {
			setFeature(f, false, objMapperBuilder);
		}
		String dfStr = (String) properties.getOrDefault("dateFormat", "yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dfStr);
		objMapperBuilder.defaultDateFormat(simpleDateFormat);
		
		String localeStr = (String) properties.getOrDefault("locale", "en-US");
		Locale loc = new Locale.Builder().setLanguageTag(localeStr).build();
		objMapperBuilder.defaultLocale(loc);
		
		String tzStr = (String) properties.getOrDefault("timeZone", "Europe/Berlin");
		TimeZone tz = TimeZone.getTimeZone(tzStr);
		objMapperBuilder.defaultTimeZone(tz);
		
//		This is needed otherwise subsequent calls to Resource#save() will use the same module even if we are changing options!
		objMapperBuilder.disable(MapperFeature.IGNORE_DUPLICATE_MODULE_REGISTRATIONS);
		return objMapperBuilder;
	}
	
	private void setFeature(String featureString, boolean state, Builder objMapperBuilder) {
		if(featureString.contains(".")) {
			String prefix = featureString.substring(0, featureString.lastIndexOf("."));
			featureString = featureString.substring(featureString.lastIndexOf(".")+1);
			switch(prefix) {
			case "MapperFeature":
				setMapperFeature(featureString, state, objMapperBuilder);
				break;
			case "SerializationFeature":
				setSerializationFeature(featureString, state, objMapperBuilder);
				break;
			case "DeserializationFeature":
				setDeserializationFeature(featureString, state, objMapperBuilder);
				break;
			case "StreamWriteFeature":
				setStreamWriteFeature(featureString, state, objMapperBuilder);
				break;
			case "JsonWriteFeature":
				setJsonWriteFeature(featureString, state, objMapperBuilder);
				break;
			case "StreamReadFeature":
				setStreamReadFeature(featureString, state, objMapperBuilder);
				break;
			case "JsonReadFeature":
				setJsonReadFeature(featureString, state, objMapperBuilder);
				break;
			default:
				LOGGER.warning(String.format("Feature prefix %s not supported", prefix));
			}
		} else {
			setMapperFeature(featureString, state, objMapperBuilder);
			setSerializationFeature(featureString, state, objMapperBuilder);
			setDeserializationFeature(featureString, state, objMapperBuilder);
			setStreamReadFeature(featureString, state, objMapperBuilder);
			setStreamWriteFeature(featureString, state, objMapperBuilder);
			setJsonReadFeature(featureString, state, objMapperBuilder);
			setJsonWriteFeature(featureString, state, objMapperBuilder);
		}
	}
	
	private void setMapperFeature(String featureString, boolean state, Builder objMapperBuilder) {
		try {
			if(state) objMapperBuilder.enable(MapperFeature.valueOf(featureString));
			else objMapperBuilder.disable(MapperFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No MapperFeature with name %s has been found", featureString));
		} 
	}
	
	private void setSerializationFeature(String featureString, boolean state, Builder objMapperBuilder) {
		try {
			if(state) objMapperBuilder.enable(SerializationFeature.valueOf(featureString));
			else objMapperBuilder.disable(SerializationFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No SerializationFeature with name %s has been found", featureString));
		} 
	}
	
	private void setDeserializationFeature(String featureString, boolean state, Builder objMapperBuilder) {
		try {
			if(state) objMapperBuilder.enable(DeserializationFeature.valueOf(featureString));
			else objMapperBuilder.disable(DeserializationFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No DeserializationFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setStreamWriteFeature(String featureString, boolean state, Builder objMapperBuilder) {
		try {
			if(state) objMapperBuilder.enable(StreamWriteFeature.valueOf(featureString));
			else objMapperBuilder.disable(StreamWriteFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No StreamWriteFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setJsonWriteFeature(String featureString, boolean state, Builder objMapperBuilder) {
		try {
			if(state) objMapperBuilder.enable(JsonWriteFeature.valueOf(featureString));
			else objMapperBuilder.disable(JsonWriteFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No JsonWriteFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setStreamReadFeature(String featureString, boolean state, Builder objMapperBuilder) {
		try {
			if(state) objMapperBuilder.enable(StreamReadFeature.valueOf(featureString));
			else objMapperBuilder.disable(StreamReadFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No StreamReadFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setJsonReadFeature(String featureString, boolean state, Builder objMapperBuilder) {
		try {
			if(state) objMapperBuilder.enable(JsonReadFeature.valueOf(featureString));
			else objMapperBuilder.disable(JsonReadFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No JsonReadFeature feature with name %s has been found", featureString));
		} 
	}

}
