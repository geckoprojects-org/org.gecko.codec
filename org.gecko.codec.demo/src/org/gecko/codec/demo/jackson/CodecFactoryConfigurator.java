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

import org.gecko.codec.CodecFactory;
import org.gecko.codec.CodecGeneratorFactory;
import org.gecko.codec.CodecParserFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;

/**
 * 
 * @author ilenia
 * @param <W>
 * @param <G>
 * @since Aug 14, 2024
 */
@Component(name = "CodecFactoryConfigurator", service = CodecFactoryConfigurator.class, 
	configurationPolicy = ConfigurationPolicy.REQUIRE, property = {"type=json"})
public class CodecFactoryConfigurator {
	
	@Reference(target="(type=json)")
	CodecGeneratorFactory genFactory;
	
	@Reference(target="(type=json)")
	CodecParserFactory parserFactory;
	
	private final static Logger LOGGER = Logger.getLogger(CodecFactoryConfigurator.class.getName());
	private TSFBuilder<?,?> factoryBuilder;
	private JsonFactory codecFactory;
	
	private class CodecFactoryBuilder<F extends JsonFactory, B extends TSFBuilder<F, B>> extends TSFBuilder<F, B>{

		/* 
		 * (non-Javadoc)
		 * @see com.fasterxml.jackson.core.TSFBuilder#build()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public F build() {
			return (F) new CodecFactory(genFactory, parserFactory);
		}
		
	}

	@Activate
	public void activate(Map<String, Object> properties) {
		factoryBuilder = new CodecFactoryBuilder();
		buildAndConfigureCodecFactory(properties);
	}
	
	public TSFBuilder<?,?> getFactoryBuilder() {
		return factoryBuilder;
	}

	private void buildAndConfigureCodecFactory(Map<String, Object> properties) {
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
	}

	private void setFeature(String featureString, boolean state) {
		if(featureString.contains(".")) {
			String prefix = featureString.substring(0, featureString.lastIndexOf("."));
			featureString = featureString.substring(featureString.lastIndexOf(".")+1);
			switch(prefix) {
			case "JsonFactory.Feature":
				setJsonFactoryFeature(featureString, state);
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
			setJsonFactoryFeature(featureString, state);
			setStreamWriteFeature(featureString, state);
			setJsonWriteFeature(featureString, state);
			setStreamReadFeature(featureString, state);
			setJsonReadFeature(featureString, state);
		}
	}
	
	private void setJsonFactoryFeature(String featureString, boolean state) {
		try {
//			if(state) factoryBuilder.enable(JsonFactory.Feature.valueOf(featureString));
//			else factoryBuilder.disable(JsonFactory.Feature.valueOf(featureString));
			if(state) codecFactory.enable(JsonFactory.Feature.valueOf(featureString));
			else codecFactory.disable(JsonFactory.Feature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No JsonFactoryFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setStreamWriteFeature(String featureString, boolean state) {
		try {
			if(state) factoryBuilder.enable(StreamWriteFeature.valueOf(featureString));
			else factoryBuilder.disable(StreamWriteFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No StreamWriteFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setJsonWriteFeature(String featureString, boolean state) {
		try {
			if(state) factoryBuilder.enable(JsonWriteFeature.valueOf(featureString));
			else factoryBuilder.disable(JsonWriteFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No JsonWriteFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setStreamReadFeature(String featureString, boolean state) {
		try {
			if(state) factoryBuilder.enable(StreamReadFeature.valueOf(featureString));
			else factoryBuilder.disable(StreamReadFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No StreamReadFeature feature with name %s has been found", featureString));
		} 
	}
	
	private void setJsonReadFeature(String featureString, boolean state) {
		try {
			if(state) factoryBuilder.enable(JsonReadFeature.valueOf(featureString));
			else factoryBuilder.disable(JsonReadFeature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No JsonReadFeature feature with name %s has been found", featureString));
		} 
	}
}