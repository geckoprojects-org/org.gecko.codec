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
package org.gecko.codec.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emfcloud.jackson.databind.ser.NullKeySerializer;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.ObjectMapperOptions;
import org.gecko.mongo.osgi.MongoDatabaseProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
public class MongoResource extends ResourceImpl {
	
	private static final Logger LOGGER = Logger.getLogger(MongoResource.class.getName());
	
	private CodecModelInfo modelInfo;
	private CodecModule module;
	private ObjectMapper mapper;
	private MongoDatabaseProvider provider;
	
	
	/**
	 * Creates a new instance.
	 * @param modelInfo
	 * @param module
	 * @param mapper
	 * @param provider
	 */
	public MongoResource(CodecModelInfo modelInfo, CodecModule module, ObjectMapper mapper, MongoDatabaseProvider provider) {
		this.modelInfo = modelInfo;
		this.module = module;
		this.mapper = mapper;
		this.provider = provider;
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
		
//		Update the CodecModule based on the passed options
		updateCodecModuleFromOptions(options);
		
//		Set up some mapper options based on module config and saving options
		updateMapperFromModuleOptions(options);
		
		EClassCodecInfo codecInfo = modelInfo.getCodecInfoForEClass(eObject.eClass()).get();
		if(codecInfo == null) {
			LOGGER.severe(String.format("No EClassCodecInfo found for EClass %s", eObject.eClass().getName()));
			return;
		}
		
		updateCodecModelInfoFromOptions(codecInfo, options);
		
		module.setEClassCodecInfo(codecInfo);
		mapper.registerModule(module);
		
		
		
//		This is specific for mongo
//		MongoCollection<EObject> collection = getCollection(options);
//		CodecRegistry eobjectRegistry = CodecRegistries
//				.fromProviders(new MongoCodecProvider(mapper, this, options));
//		CodecRegistry defaultRegistry = MongoClient.getDefaultCodecRegistry();
//		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(eobjectRegistry, defaultRegistry);
//
//		collection.withCodecRegistry(codecRegistry).insertMany(getContents());
	}
	
	/**
	 * 	TODO: add missing properties that might be overwritten from the options
	 * This is responsible to update the EClassCodecInfo based on the options used to save/load the resource
	 *
	 * @param options the options passed in the {@link Resource} save/load methods.
	 */
	private void updateCodecModelInfoFromOptions(EClassCodecInfo codecInfo, Map<?, ?> options) {
		if(options.containsKey(CodecAnnotations.CODEC_ID_STRATEGY)) {
			codecInfo.getIdentityInfo().setIdStrategy((String) options.get(CodecAnnotations.CODEC_ID_STRATEGY));
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_SEPARATOR)) {
			codecInfo.getIdentityInfo().setIdSeparator((String) options.get(CodecAnnotations.CODEC_ID_SEPARATOR));
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_STRATEGY)) {
			codecInfo.getTypeInfo().setTypeStrategy((String) options.get(CodecAnnotations.CODEC_TYPE_STRATEGY));
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_VALUE)) {
			codecInfo.getTypeInfo().setTypeValue((String) options.get(CodecAnnotations.CODEC_TYPE_VALUE));
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
	 * TODO: if updating the CodecModule from the options makes sense then this can be simplified, if 
	 * called after the CodecModule has been updated!!
	 * 
	 * This updates the {@link ObjectMapper} based on the options passed when saving/loading the resource
	 * and the {@link CodecModule}. Options passed when saving/loading the resource overwrite the ones
	 * defined in the {@link CodecModule}.
	 * @param options the options passed in the {@link Resource} save/load methods.
	 */
	private void updateMapperFromModuleOptions(Map<?, ?> options) {
		
		String dateFormatStr =  options.containsKey(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT) ? 
				(String) options.get(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT) : 
					module.getCodecModuleConfig().dateFormat();		
		
		Locale locale = options.containsKey(ObjectMapperOptions.OBJ_MAPPER_LOCALE) ? 
				new Locale.Builder().setLanguageTag((String)options.get(ObjectMapperOptions.OBJ_MAPPER_LOCALE)).build() : 
					new Locale.Builder().setLanguageTag(module.getCodecModuleConfig().locale()).build();
		
		final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr, locale);
		
		String timeZone = options.containsKey(ObjectMapperOptions.OBJ_MAPPER_TIME_ZONE) ? 
				(String) options.get(ObjectMapperOptions.OBJ_MAPPER_TIME_ZONE) : 
					module.getCodecModuleConfig().timeZone();
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		
		mapper.setDateFormat(dateFormat);
		mapper.setTimeZone(TimeZone.getTimeZone(timeZone));
		
		boolean indentOutput = options.containsKey(ObjectMapperOptions.OBJ_MAPPER_INDENT_OUTPUT) ? 
				(boolean) options.get(ObjectMapperOptions.OBJ_MAPPER_INDENT_OUTPUT) :
					module.getCodecModuleConfig().indentOutput();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, indentOutput);
		
		// add default serializer for null EMap key
		mapper.getSerializerProvider().setNullKeySerializer(new NullKeySerializer());
	}

	private MongoCollection<EObject> getCollection(Map<?, ?> options) {
		MongoDatabase database = provider.getDatabase();
		String collectionName = uri.segment(1);
		return database.getCollection(collectionName, EObject.class);
	}
}
