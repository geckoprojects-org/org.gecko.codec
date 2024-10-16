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

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

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
 * 
 * @author ilenia
 * @since Aug 14, 2024
 */
@Component(name = "ObjectMapperConfigurator", service = ObjectMapperConfigurator.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ObjectMapperConfigurator {
	
	@Reference(target="(type=json)")
	CodecFactoryConfigurator codecFactoryConfigurator;

	private static final Logger LOGGER = Logger.getLogger(ObjectMapperConfigurator.class.getName());
	private Builder objMapperBuilder;
	
	@Activate
	public void activate(Map<String, Object> properties) {
		objMapperBuilder = JsonMapper.builder(codecFactoryConfigurator.getFactoryBuilder().build());
		buildAndConfigureObjectMapper(properties);
	}

	public Builder getObjMapperBuilder() {
		return objMapperBuilder;
	}
	
	private void buildAndConfigureObjectMapper(Map<String, Object> properties) {
		if(properties == null || properties.isEmpty()) {
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
	}
	
	private void setFeature(String featureString, boolean state) {
		if(featureString.contains(".")) {
			String prefix = featureString.substring(0, featureString.lastIndexOf("."));
			featureString = featureString.substring(featureString.lastIndexOf(".")+1);
			switch(prefix) {
			case "MapperFeature":
				setMapperFeature(featureString, state);
				break;
			case "SerializationFeature":
				setSerializationFeature(featureString, state);
				break;
			case "DeserializationFeature":
				setDeserializationFeature(featureString, state);
				break;
			case "StreamWriteFeature":
				setStreamWriteFeature(featureString, state);
				break;
			case "JsonWriteFeature":
				setJsonWriteFeature(featureString, state);
				break;
			case "StreamReadFeature":
				setStreamReadFeature(featureString, state);
				break;
			case "JsonReadFeature":
				setJsonReadFeature(featureString, state);
				break;
			default:
				LOGGER.warning(String.format("Feature prefix %s not supported", prefix));
			}
		} else {
			setMapperFeature(featureString, state);
			setSerializationFeature(featureString, state);
			setDeserializationFeature(featureString, state);
			setStreamReadFeature(featureString, state);
			setStreamWriteFeature(featureString, state);
			setJsonReadFeature(featureString, state);
			setJsonWriteFeature(featureString, state);
		}
	}
	
	private void setMapperFeature(String featureString, boolean state) {
		try {
			if(state) objMapperBuilder.enable(MapperFeature.valueOf(featureString));
			else objMapperBuilder.disable(MapperFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No MapperFeature with name %s has been found", featureString));
		} 
	}
	
	private void setSerializationFeature(String featureString, boolean state) {
		try {
			if(state) objMapperBuilder.enable(SerializationFeature.valueOf(featureString));
			else objMapperBuilder.disable(SerializationFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No SerializationFeature with name %s has been found", featureString));
		} 
	}
	
	private void setDeserializationFeature(String featureString, boolean state) {
		try {
			if(state) objMapperBuilder.enable(DeserializationFeature.valueOf(featureString));
			else objMapperBuilder.disable(DeserializationFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No DeserializationFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setStreamWriteFeature(String featureString, boolean state) {
		try {
			if(state) objMapperBuilder.enable(StreamWriteFeature.valueOf(featureString));
			else objMapperBuilder.disable(StreamWriteFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No StreamWriteFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setJsonWriteFeature(String featureString, boolean state) {
		try {
			if(state) objMapperBuilder.enable(JsonWriteFeature.valueOf(featureString));
			else objMapperBuilder.disable(JsonWriteFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No JsonWriteFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setStreamReadFeature(String featureString, boolean state) {
		try {
			if(state) objMapperBuilder.enable(StreamReadFeature.valueOf(featureString));
			else objMapperBuilder.disable(StreamReadFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No StreamReadFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setJsonReadFeature(String featureString, boolean state) {
		try {
			if(state) objMapperBuilder.enable(JsonReadFeature.valueOf(featureString));
			else objMapperBuilder.disable(JsonReadFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No JsonReadFeature feature with name %s has been found", featureString));
		} 
	}
}
