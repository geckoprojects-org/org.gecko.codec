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
package org.eclipse.fennec.codec.jackson;

import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.fennec.codec.CodecFactory;
import org.eclipse.fennec.codec.CodecGeneratorFactory;
import org.eclipse.fennec.codec.CodecParserFactory;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import tools.jackson.core.StreamReadFeature;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.core.TokenStreamFactory;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.core.json.JsonFactoryBuilder;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.core.json.JsonWriteFeature;


/**
 * Default implementation of {@link CodecFactoryConfigurator} 
 * 
 * @author ilenia
 * @since Nov 14, 2024
 */
@Component(name = "DefaultCodecFactoryConfigurator", service = CodecFactoryConfigurator.class, 
	configurationPolicy = ConfigurationPolicy.REQUIRE, property = {"type=json"})
public class DefaultCodecFactoryConfigurator implements CodecFactoryConfigurator{
	
	@Reference(target="(type=json)", cardinality = ReferenceCardinality.OPTIONAL)
	CodecGeneratorFactory<?,?> genFactory;
	
	@Reference(target="(type=json)", cardinality = ReferenceCardinality.OPTIONAL)
	CodecParserFactory<?,?> parserFactory;
	
	private final static Logger LOGGER = Logger.getLogger(DefaultCodecFactoryConfigurator.class.getName());
	private JsonFactoryBuilder factoryBuilder;
	
	private class CodecFactoryBuilder<F extends JsonFactory, B extends JsonFactoryBuilder> extends JsonFactoryBuilder{

		
		/**
		 * Creates a new instance.
		 * @param base
		 */
		protected CodecFactoryBuilder(JsonFactory base) {
			super(base);
		}

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
		if(genFactory == null || parserFactory == null ) {
			factoryBuilder = JsonFactory.builder();
		} else {
			factoryBuilder = new CodecFactoryBuilder(JsonFactory.builder().build());
		}
		buildAndConfigureCodecFactory(properties);
	}
	
	public JsonFactoryBuilder getFactoryBuilder() {
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
			case "TokenStreamFactory.Feature":
				setTokenStreamFactoryFeature(featureString, state);
			default:
				LOGGER.warning(String.format("Feature prefix %s not supported", prefix));
			}
		} else {
			setJsonFactoryFeature(featureString, state);
			setStreamWriteFeature(featureString, state);
			setJsonWriteFeature(featureString, state);
			setStreamReadFeature(featureString, state);
			setJsonReadFeature(featureString, state);
			setTokenStreamFactoryFeature(featureString, state);
		}
	}
	


	private void setTokenStreamFactoryFeature(String featureString, boolean state) {
		try {
			if(state) factoryBuilder.enable(TokenStreamFactory.Feature.valueOf(featureString));
			else factoryBuilder.disable(TokenStreamFactory.Feature.valueOf(featureString));
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No TokenStreamFactory feature with name %s has been found", featureString));
		} 
		
	}

	private void setJsonFactoryFeature(String featureString, boolean state) {
		try {
			if(state) factoryBuilder.enable(JsonFactory.Feature.valueOf(featureString));
			else factoryBuilder.disable(JsonFactory.Feature.valueOf(featureString));
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