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
package org.gecko.codec.demo.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.ObjectMapperOptions;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * 
 * @author ilenia
 * @since Aug 12, 2024
 */
public class CodecResource extends ResourceImpl {
	
	private static final Logger LOGGER = Logger.getLogger(CodecResource.class.getName());


	protected CodecModelInfo modelInfo;
	protected CodecModule module;
	protected ObjectMapper mapper;
	
	public CodecResource(URI uri, CodecModelInfo modelInfo, EMFModule module, ObjectMapper mapper) {
		super(uri);
		this.modelInfo = modelInfo;
		this.module = module instanceof CodecModule ? (CodecModule) module : null;
		this.mapper = mapper;
	}

	
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		
//		This part could be done in a more generic CodecResource...? And then MongoResource and other resources
//		can extend the CodecResource...?
		
//		TODO: here we should setup the Module based on the options and on the CodecModelInfo
		EObject eObject = this.getContents().isEmpty() ? null : this.getContents().get(0);
		if(eObject == null) {
			LOGGER.severe(String.format("No content for Resource %s", this.getURI()));
			return;
		}
		
		if(options == null) options = Collections.emptyMap();
//		Update the CodecModule based on the passed options
		updateCodecModuleFromOptions(options);
		
//		Set up some mapper options based on module config and saving options
		updateMapperFromOptions(options);
		
		EClassCodecInfo codecInfo = modelInfo.getCodecInfoForEClass(eObject.eClass()).get();
		if(codecInfo == null) {
			LOGGER.severe(String.format("No EClassCodecInfo found for EClass %s", eObject.eClass().getName()));
			return;
		}
		
		updateCodecModelInfoFromOptions(codecInfo, options);
		
//		TODO: DOUBLE CHECK IF THIS IS REALLY NECESSARY
		mapper.registerModule(module);
		
		
	}
	
	/**
	 * 	TODO: add missing properties that might be overwritten from the options
	 * This is responsible to update the EClassCodecInfo based on the options used to save/load the resource
	 *
	 * @param options the options passed in the {@link Resource} save/load methods.
	 */
	@SuppressWarnings("unchecked")
	private void updateCodecModelInfoFromOptions(EClassCodecInfo codecInfo, Map<?, ?> options) {
		if(options.containsKey(CodecAnnotations.CODEC_ID_STRATEGY)) {
			codecInfo.getIdentityInfo().setIdStrategy((String) options.get(CodecAnnotations.CODEC_ID_STRATEGY));
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_SEPARATOR)) {
			codecInfo.getIdentityInfo().setIdSeparator((String) options.get(CodecAnnotations.CODEC_ID_SEPARATOR));
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_FEATURES_LIST)) {
			List<EStructuralFeature> idFeatures = (List<EStructuralFeature>) options.get(CodecAnnotations.CODEC_ID_FEATURES_LIST);
			codecInfo.getIdentityInfo().getFeatures().clear();
			codecInfo.getIdentityInfo().getFeatures().addAll(idFeatures);
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_USE)) {
			codecInfo.getTypeInfo().setTypeStrategy((String) options.get(CodecAnnotations.CODEC_TYPE_USE));
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_INCLUDE)) {
			codecInfo.getTypeInfo().setIgnoreType((Boolean) options.get(CodecAnnotations.CODEC_TYPE_INCLUDE));
		}
	}

	/**
	 * This updates the {@link CodecModule} based on the options passed when saving/loading the resource
	 * @param options the options passed in the {@link Resource} save/load methods.
	 */
	private void updateCodecModuleFromOptions(Map<?, ?> options) {
		module.getCodecModuleProperties().keySet().forEach(key -> {
			checkAndUpdateModuleProperty(key, options);
		});
	}
	
	private void checkAndUpdateModuleProperty(String property, Map<?,?> options) {
		if(options.containsKey(property)) {
			module.getCodecModuleProperties().put(property, options.get(property));
		}
	}

	/**
	 * Here the {@link CodecObjectMapper} can be configured based on the options passed with the {@link Resource} save/load methods.
	 * Only Serialization/Deserialization features should be configurable at this point. 
	 * Also GeneratorFeatures and ParserFeature can be overwritten here. 
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void updateMapperFromOptions(Map<?, ?> options) {
		
		DateFormat dateFormat =  options.containsKey(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT) ? (DateFormat) options.get(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT) : null;
		if(dateFormat != null) mapper.setDateFormat(dateFormat);

		
		Locale locale = options.containsKey(ObjectMapperOptions.OBJ_MAPPER_LOCALE) ? (Locale) options.get(ObjectMapperOptions.OBJ_MAPPER_LOCALE) : null;
		if(locale != null) mapper.setLocale(locale);
		
		TimeZone timeZone = options.containsKey(ObjectMapperOptions.OBJ_MAPPER_TIME_ZONE) ? (TimeZone) options.get(ObjectMapperOptions.OBJ_MAPPER_TIME_ZONE) : null;
		if(timeZone != null) mapper.setTimeZone(timeZone);
		
		if(options.containsKey(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH)) {
			List<SerializationFeature> serFeatureWith = (List<SerializationFeature>) options.get(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH);
			serFeatureWith.forEach(sf -> mapper.configure(sf, true));
		}
		if(options.containsKey(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITHOUT)) {
			List<SerializationFeature> serFeatureWithout = (List<SerializationFeature>) options.get(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITHOUT);
			serFeatureWithout.forEach(sf -> mapper.configure(sf, false));
		}
		
//		doing mapper.getSerializationConfig().with() does not work because it creates a new config
//		on the other hand the method here will be deprecated in jackson 3.0
		
//		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//		mapper.configure(MapperFeature.IGNORE_DUPLICATE_MODULE_REGISTRATIONS, false);
		
		// add default serializer for null EMap key
//		mapper.getSerializerProvider().setNullKeySerializer(new NullKeySerializer());
	}
}
