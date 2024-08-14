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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;

/**
 * 
 * @author ilenia
 * @since Aug 14, 2024
 */
@Component(name = "CodecFactoryConfigurator", service = CodecFactoryConfigurator.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class CodecFactoryConfigurator {
	
	private final static Logger LOGGER = Logger.getLogger(CodecFactoryConfigurator.class.getName());
	private CodecFactory codecFactory;
	
	

	@Activate
	public void activate(Map<String, Object> properties) {
		buildAndConfigureCodecFactory(properties);
	}
	
	public CodecFactory getCodecFactory() {
		return codecFactory;
	}
	
	private void buildAndConfigureCodecFactory(Map<String, Object> properties) {
		if(properties == null || properties.isEmpty()) {
			codecFactory = (CodecFactory) JsonFactory.builder().build();
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
			if(state) codecFactory = (CodecFactory) JsonFactory.builder().enable(JsonFactory.Feature.valueOf(featureString)).build();
			else codecFactory = (CodecFactory) JsonFactory.builder().disable(JsonFactory.Feature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {

		} 
		try {
			if(state) codecFactory = (CodecFactory) JsonFactory.builder().enable(StreamWriteFeature.valueOf(featureString)).build();
			else codecFactory = (CodecFactory) JsonFactory.builder().disable(StreamWriteFeature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {

		} 
		try {
			if(state) codecFactory = (CodecFactory) JsonFactory.builder().enable(JsonWriteFeature.valueOf(featureString)).build();
			else codecFactory = (CodecFactory) JsonFactory.builder().disable(JsonWriteFeature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {

		} 
		try {
			if(state) codecFactory = (CodecFactory) JsonFactory.builder().enable(JsonWriteFeature.valueOf(featureString)).build();
			else codecFactory = (CodecFactory) JsonFactory.builder().disable(JsonWriteFeature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {

		} 
		try {
			if(state) codecFactory = (CodecFactory) JsonFactory.builder().enable(StreamReadFeature.valueOf(featureString)).build();
			else codecFactory = (CodecFactory) JsonFactory.builder().disable(StreamReadFeature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {

		} 
		try {
			if(state) codecFactory = (CodecFactory) JsonFactory.builder().enable(JsonReadFeature.valueOf(featureString)).build();
			else codecFactory = (CodecFactory) JsonFactory.builder().disable(JsonReadFeature.valueOf(featureString)).build();
			return;
		} catch(Exception e) {
			LOGGER.warning(String.format("No JsonFactory feature with name %s has been found", featureString));
		} 
	}
}
