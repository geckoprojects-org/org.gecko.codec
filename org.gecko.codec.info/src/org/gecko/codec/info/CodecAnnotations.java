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
package org.gecko.codec.info;

/**
 * 
 * @author ilenia
 * @since Aug 5, 2024
 */
public interface CodecAnnotations {
	
	/** CODEC_IGNORE 
	 *  Annotation for specifying that the feature should not be serialize.
	 *  This is supposed to be used when annotating the model.
	 *  If this has to be overwritten by {@link Resource}{@link #save} and {@link #load}
	 *  options, use {@link CODEC_IGNORE_FEATURES_LIST} and {@link CODEC_IGNORE_NOT_FEATURES_LIST}
	 * */
	String CODEC_IGNORE = "codec.ignore";
	
	/** CODEC_IGNORE_FEATURES_LIST 
	 * Option to be used with {@link Resource}{@link #save} and {@link #load}
	 * to provide a list of {@link EStructuralFeature} that has to be ignored during 
	 * serialization
	 * */
	String CODEC_IGNORE_FEATURES_LIST = "codec.ignore.features.list";
	
	/** CODEC_IGNORE_NOT_FEATURES_LIST 
	 * Option to be used with {@link Resource}{@link #save} and {@link #load}
	 * to provide a list of {@link EStructuralFeature} that has NOT to be ignored during 
	 * serialization
	 * */
	String CODEC_IGNORE_NOT_FEATURES_LIST = "codec.ignore.not.feature.list";
	
	/** CODEC_NAME 
	 *  Annotation for specifying the name with which the feature should be serialized.
	 *  This might be overwritten if property {@link OPTION_USE_NAMES_FROM_EXTENDED_META_DATA}
	 *  is set to true.
	 * */
	String CODEC_NAME = "codec.name";
	
	/** CODEC_ID_STRATEGY 
	 *  Annotation for specifying a strategy to be followed when building the id 
	 *  of the {@link EObject} when serializing it
	 * */
	String CODEC_ID_STRATEGY = "codec.id.strategy";
	
	/** CODEC_ID_FEATURES_LIST 
	 * This option is to provide a list of {@link EStructuralFeature} that can be used as id fields when constructing the id
	 * */
	String CODEC_ID_FEATURES_LIST = "codec.id.features.list";
	
	/** CODEC_ID_FIELD 
	 * Annotation for specifying that a field should be treated as an id field.
	 * This is needed especially when the id should be a combination of more fields.
	 * This is intended to be used when annotating the model.
	 * For overwriting this property via {@link Resource}{@link #save} and {@link #load} options
	 * use {@link CODEC_ID_FEATURES_LIST} and pass a list of {@link EStructuralFeauture} which should
	 * form the id field.
	 * */
	String CODEC_ID_FIELD = "codec.id.field";
	
	/** CODEC_ID_ORDER 
	 * When the {@link CODEC_ID_STRATEGY} consists of combining multiple fields to build the object
	 * id, every field should have an order, in such away the serialization process knows how to 
	 * properly build the id
	 * */
	String CODEC_ID_ORDER = "codec.id.order";
	
	/** CODEC_ID_SEPARATOR 
	 * When the {@link CODEC_ID_STRATEGY} consists of combining multiple fields to build the object
	 * id, we would need to specify a separator between the different fields values that build the id
	 * */
	String CODEC_ID_SEPARATOR = "codec.id.separator";
	
	/** CODEC_VALUE_READER_NAME 
	 * Annotation for specifying a ValueReader name to be used when deserializing the object marked like this
	 * */
	String CODEC_VALUE_READER_NAME = "codec.value.reader.name";
	
	/** CODEC_VALUE_WRITER_NAME 
	 * Annotation for specifying a ValueWriter name to be used when serializing the object marked like this
	 * */
	String CODEC_VALUE_WRITER_NAME = "codec.value.writer.name";
	
	/** CODEC_VALUE_READER 
	 * Annotation used for specifying a ValueReader to be used when deserializing the object marked like this
	 * */
	String CODEC_VALUE_READER = "codec.value.reader";
	
	/** CODEC_VALUE_WRITER 
	 * Annotation for specifying a ValueWriter to be used when serializing the object marked like this
	 * */
	String CODEC_VALUE_WRITER = "codec.value.writer";
	
	/** CODEC_TYPE_USE 
	 * Annotation used for specifying a strategy to serialize the type of the object (class name, uri, etc)
	 * */
	String CODEC_TYPE_USE = "codec.type.use";
	
	/** CODEC_TYPE_INCLUDE
	 * Annotation used to specify weather the type information should be serialized or not.
	 * */
	String CODEC_TYPE_INCLUDE = "codec.type.include";

}
