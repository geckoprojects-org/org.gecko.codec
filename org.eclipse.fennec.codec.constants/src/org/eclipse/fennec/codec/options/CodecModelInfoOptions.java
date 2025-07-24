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
package org.eclipse.fennec.codec.options;

import java.util.List;
import java.util.Map;


/**
 * These are the options that should be used with Resource#load/save in the options arguments
 * to overwrite the properties set through model annotations and used to build the ModelInfo objects
 * 
 * @author ilenia
 * @since Nov 11, 2024
 */
public interface CodecModelInfoOptions {
	
	/** CODEC_IGNORE_FEATURES_LIST 
	 * to specify a {@link List} of {@link EStructuralFeature} that should be ignored 
	 * during serialization or deserialization. If an {@link EStructuralFeature} is marked as 
	 * transient in the model or has been annotated with the {@link org.gecko.codec.constants.CodecAnnotations.CODEC_TRANSIENT} 
	 * annotation, it will still be ignored even is it is not present in this list.
	 * */
	String CODEC_IGNORE_FEATURES_LIST = "codec.ignore.features.list";
	
	/** CODEC_IGNORE_NOT_FEATURES_LIST 
	 * to specify a {@link List} of {@link EStructuralFeature} that should NOT be ignored 
	 * during serialization or deserialization. If an {@link EStructuralFeature} is marked as 
	 * transient in the model or has been annotated with the {@link org.gecko.codec.constants.CodecAnnotations.CODEC_TRANSIENT} 
	 * annotation, it will then be taken into account if present in this list.
	 * */
	String CODEC_IGNORE_NOT_FEATURES_LIST = "codec.ignore.not.feature.list";
	
	/** CODEC_ID_KEY 
	 * to overwrite the {@link org.gecko.codec.constants.CodecAnnotations.CODEC_ID} key detail annotation;
	 * */
	String CODEC_ID_KEY = "key";
	
	/** CODEC_ID_STRATEGY 
	 *  to overwrite the {@link org.gecko.codec.constants.CodecAnnotations.CODEC_ID} strategy detail annotation;
	 * */
	String CODEC_ID_STRATEGY = "strategy";
	
	/** CODEC_ID_SEPARATOR 
	 * to overwrite the {@link org.gecko.codec.constants.CodecAnnotations.CODEC_ID} separator detail annotation;
	 * */
	String CODEC_ID_SEPARATOR = "separator";
	
	/** CODEC_ID_FEATURES_LIST 
	 * to specify an ordered {@link List} of {@link EStructuralFeature} to be used when constructing the id, 
	 * if the id strategy is set to COMBINED. Otherwise it will be ignored.
	 * */
	String CODEC_ID_FEATURES_LIST = "idFeatures";
	
	/** CODEC_ID_VALUE_READER
	 * to specify a {@link CodecValueReader} object to be used when deserializing the id information;
	 * */
	String CODEC_ID_VALUE_READER = "codec.id.value.reader";
	
	/** CODEC_ID_VALUE_WRITER 
	 * to specify a {@link CodecValueWriter} object to be used when serializing the id information;
	 * */
	String CODEC_ID_VALUE_WRITER = "codec.id.value.writer";
	
	
	/** CODEC_VALUE_READERS_MAP 
	  * a {@link Map} where the keys are of type {@link EStructuralFeature} and the values 
	 * are of type {@link CodecValueReader}, to specify the {@link CodecValueReader} to use 
	 * when serializing the corresponding {@link EStructuralFeature}
	 * */
	String CODEC_VALUE_READERS_MAP = "codec.value.readers.map";
	
	
	/** CODEC_VALUE_WRITERS_MAP 
	 * a {@link Map} where the keys are of type {@link EStructuralFeature} and the values 
	 * are of type {@link CodecValueWriter}, to specify the {@link CodecValueWriter} to use 
	 * when serializing the corresponding {@link EStructuralFeature}
	 *  */
	String CODEC_VALUE_WRITERS_MAP = "codec.value.writers.map";
	
	/** CODEC_TYPE_USE 
	 * to overwrite the "strategy" detail of the {@link org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE} annotation;
	 * */
	String CODEC_TYPE_STRATEGY = "strategy";
	
	/** CODEC_TYPE_INCLUDE
	 * to overwrite the  "include" detail of {@link org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE} annotation;
	 * */
	String CODEC_TYPE_INCLUDE = "include";
	
	/** CODEC_TYPE_KEY 
	 * to overwrite the "typeKey" detail of {@link org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE} annotation;
	 * */
	String CODEC_TYPE_KEY = "typeKey";	
	
	/** CODEC_TYPE_MAP 
	 * to overwrite the type mapping in the details of {@link org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE} annotation;
	 * */
	String CODEC_TYPE_MAP = "typeMap";
	
	/** CODEC_TYPE_INFO 
	 * to overwrite the entire typeInfo object that comes out of the {@link org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE} annotation;
	 * */
	String CODEC_TYPE_INFO = "typeInfo";
	
	/** CODEC_TYPE_VALUE_READER
	 * to specify a {@link CodecValueReader} object to be used when deserializing the type information;
	 * */
	String CODEC_TYPE_VALUE_READER = "codec.type.value.reader";
	
	/** CODEC_TYPE_VALUE_WRITER 
	 * to specify a {@link CodecValueWriter} object to be used when serializing the type information;
	 * */
	String CODEC_TYPE_VALUE_WRITER = "codec.type.value.writer";

}
