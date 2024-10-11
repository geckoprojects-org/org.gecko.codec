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
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.demo.jackson.CodecModuleOptions;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.ObjectMapperOptions;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;

/**
 * 
 * @author ilenia
 * @since Aug 12, 2024
 */
public class CodecResource extends ResourceImpl {

	private static final Logger LOGGER = Logger.getLogger(CodecResource.class.getName());


	protected CodecModelInfo modelInfoService;
	protected Builder objMapperBuilder;
	protected CodecModule.Builder moduleBuilder;
	protected ObjectMapper mapper;

	public CodecResource(URI uri, CodecModelInfo modelInfoService, CodecModule.Builder moduleBuilder, Builder objMapperBuilder) {
		super(uri);
		this.modelInfoService = modelInfoService;
		this.objMapperBuilder = objMapperBuilder;
		this.moduleBuilder = moduleBuilder.bindCodecModelInfoService(modelInfoService);
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {

		EObject eObject = this.getContents().isEmpty() ? null : this.getContents().get(0);
		if(eObject == null) {
			LOGGER.severe(String.format("No content for Resource %s", this.getURI()));
			return;
		}

		PackageCodecInfo modelCodecInfo = modelInfoService.getCodecInfoForPackage(eObject.eClass().getEPackage().getNsURI()).get();	

		if(modelCodecInfo == null) {
			LOGGER.severe(String.format("No PackageCodecInfo associated with EObject %s has been found", eObject.eClass().getName()));
			return;
		}

		if(options == null) options = Collections.emptyMap();

		//		Update the CodecModule based on the passed options
		updateCodecModuleFromOptions(options);

		//		Update ObjectMapper based on the passed options
		updateMapperFromOptions(options);

//		Update the CodecModelInfo based on the passed options
		updateCodecModelInfoFromOptions(modelCodecInfo, options);

//		Bind the CodecModelInfo to the CodecModule.
//		This is necessary otherwise asking the ModelInfoService we would get a new instance every time
//		instead we need the same one here since it's the one we updated based on the options
		moduleBuilder.bindCodecModelInfo(modelCodecInfo);

//		Register the module with the mapper
		mapper = objMapperBuilder.build();
		mapper.registerModule(moduleBuilder.build());		
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#doLoad(java.io.InputStream, java.util.Map)
	 */
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		
		if (options == null) {
			options = Collections.<String, Object> emptyMap();
		}
		
		EClass eClass = null;
		
//		We need to know which object has to be deserialized, otherwise we cannot access the right model info at this stage
		if(options.containsKey("ROOT_OBJECT")) {
			eClass = (EClass) options.get("ROOT_OBJECT");
		}
		
		PackageCodecInfo modelCodecInfo = null;
		if(eClass != null) {
			modelCodecInfo = modelInfoService.getCodecInfoForPackage(eClass.getEPackage().getNsURI()).get();	
			if(modelCodecInfo == null) {
				LOGGER.warning(String.format("No PackageCodecInfo associated with EClass %s has been found", eClass.getName()));
			}
		} else {
			LOGGER.warning(String.format("Not able to retrieve EClass from options ROOT_OBJECT"));
		}
		
		try {
			updateCodecModuleFromOptions(options);

			//		Update ObjectMapper based on the passed options
			updateMapperFromOptions(options);

			if(modelCodecInfo != null) {
//				Update the CodecModelInfo based on the passed options
				updateCodecModelInfoFromOptions(modelCodecInfo, options);
				
//				Bind the CodecModelInfo to the CodecModule.
//				This is necessary otherwise asking the ModelInfoService we would get a new instance every time
//				instead we need the same one here since it's the one we updated based on the options
				moduleBuilder.bindCodecModelInfo(modelCodecInfo);
			}
//			

//			Register the module with the mapper
			mapper = objMapperBuilder.build();
			mapper.registerModule(moduleBuilder.build());	
		} catch(Exception e) {
			throw e;
		}
		
//		Update the CodecModule based on the passed options
		
	}
	
	
	/**
	 * This is responsible to update the EClassCodecInfo based on the options used to save/load the resource
	 *
	 * @param options the options passed in the {@link Resource} save/load methods.
	 */
	@SuppressWarnings("unchecked")
	private void updateCodecModelInfoFromOptions(EClassCodecInfo codecInfo, Map<?, ?> options) {
		if(options.containsKey(CodecAnnotations.CODEC_ID_STRATEGY)) {
			codecInfo.getIdentityInfo().setIdStrategy((String) options.get(CodecAnnotations.CODEC_ID_STRATEGY));
			if("ID_FIELD".equals(codecInfo.getIdentityInfo().getIdStrategy())) {
				codecInfo.getIdentityInfo().getFeatures().clear();
				if(codecInfo.getClassifier() instanceof EClass ec) {
					codecInfo.getIdentityInfo().getFeatures().add(ec.getEIDAttribute());
				}
			}
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_SEPARATOR)) {
			codecInfo.getIdentityInfo().setIdSeparator((String) options.get(CodecAnnotations.CODEC_ID_SEPARATOR));
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_FEATURES_LIST)) {
			List<EStructuralFeature> idFeatures = (List<EStructuralFeature>) options.get(CodecAnnotations.CODEC_ID_FEATURES_LIST);
			codecInfo.getIdentityInfo().getFeatures().clear();
			codecInfo.getIdentityInfo().getFeatures().addAll(idFeatures);
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_VALUE_READER_NAME)) {
			codecInfo.getIdentityInfo().setValueReaderName((String) options.get(CodecAnnotations.CODEC_ID_VALUE_READER_NAME));
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_VALUE_WRITER_NAME)) {
			codecInfo.getIdentityInfo().setValueWriterName((String) options.get(CodecAnnotations.CODEC_ID_VALUE_WRITER_NAME));
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_VALUE_READER)) {
			CodecValueReader<?,?> reader = (CodecValueReader<?,?>) options.get(CodecAnnotations.CODEC_ID_VALUE_READER);
			codecInfo.getIdentityInfo().setValueReaderName(reader.getName());
			modelInfoService.addCodecValueReaderForType(InfoType.IDENTITY, reader);
		}
		if(options.containsKey(CodecAnnotations.CODEC_ID_VALUE_WRITER)) {
			CodecValueWriter<?,?> writer = (CodecValueWriter<?,?>) options.get(CodecAnnotations.CODEC_ID_VALUE_WRITER);
			codecInfo.getIdentityInfo().setValueWriterName(writer.getName());
			modelInfoService.addCodecValueWriterForType(InfoType.IDENTITY, writer);
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_USE)) {
			String typeUse = (String) options.get(CodecAnnotations.CODEC_TYPE_USE);
			switch(typeUse) {
			case "NAME":
				codecInfo.getTypeInfo().setValueWriterName("WRITE_BY_NAME");
				codecInfo.getTypeInfo().setValueReaderName("READ_BY_NAME");
				codecInfo.getTypeInfo().setTypeStrategy(typeUse);
				break;
			case "CLASS":
				codecInfo.getTypeInfo().setValueWriterName("WRITE_BY_CLASS_NAME");
				codecInfo.getTypeInfo().setValueReaderName("READ_BY_CLASS");
				codecInfo.getTypeInfo().setTypeStrategy(typeUse);
				break;
			case "URI": 
				codecInfo.getTypeInfo().setValueWriterName("URI_WRITER");
				codecInfo.getTypeInfo().setValueReaderName("DEFAULT_ECLASS_READER");
				codecInfo.getTypeInfo().setTypeStrategy(typeUse);
				break;	
			default:
				LOGGER.warning(String.format("No Reader/Writer available for type use %s. Keeping the default ones.", typeUse));
			}			
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_VALUE_READER_NAME)) {
			codecInfo.getTypeInfo().setValueReaderName((String) options.get(CodecAnnotations.CODEC_TYPE_VALUE_READER_NAME));
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_VALUE_WRITER_NAME)) {
			codecInfo.getTypeInfo().setValueWriterName((String) options.get(CodecAnnotations.CODEC_TYPE_VALUE_WRITER_NAME));
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_VALUE_READER)) {
			CodecValueReader<?,?> reader = (CodecValueReader<?,?>) options.get(CodecAnnotations.CODEC_TYPE_VALUE_READER);
			codecInfo.getTypeInfo().setValueReaderName(reader.getName());
			modelInfoService.addCodecValueReaderForType(InfoType.TYPE, reader);
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_VALUE_WRITER)) {
			CodecValueWriter<?,?> writer = (CodecValueWriter<?,?>) options.get(CodecAnnotations.CODEC_TYPE_VALUE_WRITER);
			codecInfo.getTypeInfo().setValueWriterName(writer.getName());
			modelInfoService.addCodecValueWriterForType(InfoType.TYPE, writer);
		}
		if(options.containsKey(CodecAnnotations.CODEC_TYPE_INCLUDE)) {
			codecInfo.getTypeInfo().setIgnoreType(!((Boolean) options.get(CodecAnnotations.CODEC_TYPE_INCLUDE)));
		}
		if(options.containsKey(CodecAnnotations.CODEC_IGNORE_FEATURES_LIST)) {
			List<EStructuralFeature> ignoreFeatures = (List<EStructuralFeature>) options.get(CodecAnnotations.CODEC_IGNORE_FEATURES_LIST);
			ignoreFeatures.forEach(ignoreFeature -> {
				FeatureCodecInfo fci = codecInfo.getFeatureInfo().stream().filter(featureInfo -> ignoreFeature.equals(featureInfo.getFeatures().get(0))).findFirst().get();
				if(fci != null) fci.setIgnore(true);
			});
		}
		if(options.containsKey(CodecAnnotations.CODEC_IGNORE_NOT_FEATURES_LIST)) {
			List<EStructuralFeature> ignoreFeatures = (List<EStructuralFeature>) options.get(CodecAnnotations.CODEC_IGNORE_NOT_FEATURES_LIST);
			ignoreFeatures.forEach(ignoreFeature -> {
				FeatureCodecInfo fci = codecInfo.getFeatureInfo().stream().filter(featureInfo -> ignoreFeature.equals(featureInfo.getFeatures().get(0))).findFirst().get();
				if(fci != null) fci.setIgnore(false);
			});
		}
		if(options.containsKey(CodecAnnotations.CODEC_VALUE_READERS_MAP)) {
			Map<ETypedElement, CodecValueReader<?,?>> readersMap = (Map<ETypedElement, CodecValueReader<?,?>>)options.get(CodecAnnotations.CODEC_VALUE_READERS_MAP);
			readersMap.forEach((element, reader) -> {
				if(element instanceof EAttribute) {
					FeatureCodecInfo fci = codecInfo.getAttributeCodecInfo().stream().filter(featureInfo -> featureInfo.getFeatures().get(0).equals(element)).findFirst().get();
					if(fci != null) {
						fci.setValueReaderName(reader.getName());
						modelInfoService.addCodecValueReaderForType(InfoType.ATTRIBUTE, reader);
					}
				}
				else if(element instanceof EReference) {
					FeatureCodecInfo fci = codecInfo.getReferenceCodecInfo().stream().filter(featureInfo -> featureInfo.getFeatures().get(0).equals(element)).findFirst().get();
					if(fci != null) {
						fci.setValueReaderName(reader.getName());
						modelInfoService.addCodecValueReaderForType(InfoType.REFERENCE, reader);
					}
				}
				else if(element instanceof EOperation) {
					FeatureCodecInfo fci = codecInfo.getOperationCodecInfo().stream().filter(featureInfo -> featureInfo.getFeatures().get(0).equals(element)).findFirst().get();
					if(fci != null) {
						fci.setValueReaderName(reader.getName());
						modelInfoService.addCodecValueReaderForType(InfoType.OPERATION, reader);
					}
				}
			});
		}
		if(options.containsKey(CodecAnnotations.CODEC_VALUE_WRITERS_MAP)) {
			Map<ETypedElement, CodecValueWriter<?,?>> writersMap = (Map<ETypedElement, CodecValueWriter<?,?>>)options.get(CodecAnnotations.CODEC_VALUE_WRITERS_MAP);
			writersMap.forEach((element, writer) -> {
				if(element instanceof EAttribute) {
					FeatureCodecInfo fci = codecInfo.getAttributeCodecInfo().stream().filter(featureInfo -> featureInfo.getFeatures().get(0).equals(element)).findFirst().get();
					if(fci != null) {
						fci.setValueWriterName(writer.getName());
						modelInfoService.addCodecValueWriterForType(InfoType.ATTRIBUTE, writer);
					}
				}
				else if(element instanceof EReference) {
					FeatureCodecInfo fci = codecInfo.getReferenceCodecInfo().stream().filter(featureInfo -> featureInfo.getFeatures().get(0).equals(element)).findFirst().get();
					if(fci != null) {
						fci.setValueWriterName(writer.getName());
						modelInfoService.addCodecValueWriterForType(InfoType.REFERENCE, writer);
					}
				}
				else if(element instanceof EOperation) {
					FeatureCodecInfo fci = codecInfo.getOperationCodecInfo().stream().filter(featureInfo -> featureInfo.getFeatures().get(0).equals(element)).findFirst().get();
					if(fci != null) {
						fci.setValueWriterName(writer.getName());
						modelInfoService.addCodecValueWriterForType(InfoType.OPERATION, writer);
					}
				}
			});
		}		
	}

	@SuppressWarnings("unchecked")
	private void updateCodecModelInfoFromOptions(PackageCodecInfo codecModelInfo, Map<?, ?> options) {

		if(options.containsKey("codec.options")) {
			Map<EClass, Map<String, Object>> codecOptions = (Map<EClass, Map<String, Object>>) options.get("codec.options");
			codecOptions.forEach((ec, opt) -> {
				EClassCodecInfo eClassCodecInfo = codecModelInfo.getEClassCodecInfo().stream().filter(eci -> eci.getClassifier().getInstanceClassName().equals(ec.getInstanceClassName())).findFirst().get();
				if(eClassCodecInfo == null) {
					LOGGER.severe(String.format("No EClassCodecInfo associated with EClass %s has been found", ec.eClass().getName()));
				}
				else {
					updateCodecModelInfoFromOptions(eClassCodecInfo, opt);
				}
			});
		}
	}

	/**
	 * This updates the {@link CodecModule} based on the options passed when saving/loading the resource
	 * @param options the options passed in the {@link Resource} save/load methods.
	 */
	private void updateCodecModuleFromOptions(Map<?, ?> options) {
		options.forEach((k,v) -> {
			switch((String)k) {
			case CodecModuleOptions.CODEC_MODULE_ID_FEATURE_AS_PRIMARY_KEY:
				moduleBuilder.withIdFeatureAsPrimaryKey((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_ID_KEY:
				moduleBuilder.withIdKey((String) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_ID_ON_TOP:
				moduleBuilder.withIdOnTop((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_PROXY_KEY:
				moduleBuilder.withProxyKey((String) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_REFERENCE_KEY:
				moduleBuilder.withRefKey((String) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_ARRAY_BATCHED:
				moduleBuilder.withSerializeArrayBatched((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE:
				moduleBuilder.withSerializeDefaultValue((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_EMPTY_VALUE:
				moduleBuilder.withSerializeEmptyValue((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_NULL_VALUE:
				moduleBuilder.withSerializeNullValue((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_ID_FIELD:
				moduleBuilder.withSerializeIdField((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES:
				moduleBuilder.withSerializeSuperTypes((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_ALL_SUPER_TYPES:
				moduleBuilder.withSerializeAllSuperTypes((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_SUPER_TYPES_AS_ARRAY:
				moduleBuilder.withSerailizeSuperTypesAsArray((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE:
				moduleBuilder.withSerializeType((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_TIMESTAMP_KEY:
				moduleBuilder.withTimestampKey((String) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_TYPE_KEY:
				moduleBuilder.withTypeKey((String) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_SUPERTYPE_KEY:
				moduleBuilder.withSuperTypeKey((String) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_USE_ID:
				moduleBuilder.withUseId((boolean) options.get(k));
				break;
			case CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA:
				moduleBuilder.withUseNamesFromExtendedMetaData((boolean) options.get(k));
				break;				
			}
		});
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
		if(dateFormat != null) objMapperBuilder = objMapperBuilder.defaultDateFormat(dateFormat);

		Locale locale = options.containsKey(ObjectMapperOptions.OBJ_MAPPER_LOCALE) ? (Locale) options.get(ObjectMapperOptions.OBJ_MAPPER_LOCALE) : null;
		if(locale != null) objMapperBuilder = objMapperBuilder.defaultLocale(locale);

		TimeZone timeZone = options.containsKey(ObjectMapperOptions.OBJ_MAPPER_TIME_ZONE) ? (TimeZone) options.get(ObjectMapperOptions.OBJ_MAPPER_TIME_ZONE) : null;
		if(timeZone != null) objMapperBuilder = objMapperBuilder.defaultTimeZone(timeZone);

		if(options.containsKey(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH)) {
			List<SerializationFeature> serFeatureWith = (List<SerializationFeature>) options.get(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH);
			serFeatureWith.forEach(sf -> objMapperBuilder.enable(sf));
		}
		if(options.containsKey(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITHOUT)) {
			List<SerializationFeature> serFeatureWithout = (List<SerializationFeature>) options.get(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITHOUT);
			serFeatureWithout.forEach(sf -> objMapperBuilder.disable(sf));
		}
		if(options.containsKey(ObjectMapperOptions.OBJ_MAPPER_DESERIALIZATION_FEATURES_WITH)) {
			List<DeserializationFeature> deserFeatureWith = (List<DeserializationFeature>) options.get(ObjectMapperOptions.OBJ_MAPPER_DESERIALIZATION_FEATURES_WITH);
			deserFeatureWith.forEach(sf -> objMapperBuilder.enable(sf));
		}
		if(options.containsKey(ObjectMapperOptions.OBJ_MAPPER_DESERIALIZATION_FEATURES_WITHOUT)) {
			List<DeserializationFeature> deserFeatureWithout = (List<DeserializationFeature>) options.get(ObjectMapperOptions.OBJ_MAPPER_DESERIALIZATION_FEATURES_WITHOUT);
			deserFeatureWithout.forEach(sf -> objMapperBuilder.disable(sf));
		}
		if(options.containsKey(ObjectMapperOptions.OBJ_MAPPER_FEATURES_WITH)) {
			List<MapperFeature> featureWith = (List<MapperFeature>) options.get(ObjectMapperOptions.OBJ_MAPPER_FEATURES_WITH);
			featureWith.forEach(sf -> objMapperBuilder.enable(sf));
		}
		if(options.containsKey(ObjectMapperOptions.OBJ_MAPPER_FEATURES_WITHOUT)) {
			List<MapperFeature> featureWithout = (List<MapperFeature>) options.get(ObjectMapperOptions.OBJ_MAPPER_FEATURES_WITHOUT);
			featureWithout.forEach(sf -> objMapperBuilder.disable(sf));
		}
	}
}
