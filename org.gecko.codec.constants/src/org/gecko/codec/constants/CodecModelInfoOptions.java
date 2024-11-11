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
package org.gecko.codec.constants;

/**
 * These are the options that should be used with Resource#load/save in the options arguments
 * to overwrite the properties set through model annotations and used to build the ModelInfo objects
 * 
 * @author ilenia
 * @since Nov 11, 2024
 */
public interface CodecModelInfoOptions {
	
	/** CODEC_IGNORE_FEATURES_LIST 
	 * Option to be used with {@link Resource}{@link #save} and {@link #load}
	 * to provide a list of {@link EStructuralFeature} that has to be ignored during 
	 * serialization/deserialization
	 * */
	String CODEC_IGNORE_FEATURES_LIST = "codec.ignore.features.list";
	
	/** CODEC_IGNORE_NOT_FEATURES_LIST 
	 * Option to be used with {@link Resource}{@link #save} and {@link #load}
	 * to provide a list of {@link EStructuralFeature} that has NOT to be ignored during 
	 * serialization/deserialization
	 * */
	String CODEC_IGNORE_NOT_FEATURES_LIST = "codec.ignore.not.feature.list";
	
	/** CODEC_ID_STRATEGY 
	 *  Option for specifying a strategy to be followed when building the id 
	 *  of the {@link EObject} when serializing it
	 * */
	String CODEC_ID_STRATEGY = "codec.id.strategy";
	
	/** CODEC_ID_SEPARATOR 
	 * When the {@link CODEC_ID_STRATEGY} consists of combining multiple fields to build the object
	 * id, we would need to specify a separator between the different fields values that build the id
	 * */
	String CODEC_ID_SEPARATOR = "codec.id.separator";
	
	/** CODEC_ID_FEATURES_LIST 
	 * This option is to provide a list of {@link EStructuralFeature} that can be used as id fields 
	 * when constructing the id
	 * */
	String CODEC_ID_FEATURES_LIST = "codec.id.features.list";
	
	/** CODEC_ID_VALUE_READER
	 * Option for specifying a ValueReader to be used when deserializing the id fields
	 * */
	String CODEC_ID_VALUE_READER = "codec.id.value.reader";
	
	/** CODEC_ID_VALUE_WRITER 
	 * Option for specifying a ValueWriter to be used when serializing the id fields
	 * */
	String CODEC_ID_VALUE_WRITER = "codec.id.value.writer";
	
	/** CODEC_TYPE_VALUE_READER
	 * Option for specifying a ValueReader to be used when deserializing the object marked like this
	 * */
	String CODEC_TYPE_VALUE_READER = "codec.type.value.reader";
	
	/** CODEC_TYPE_VALUE_WRITER 
	 * Option for specifying a ValueWriter to be used when serializing the object marked like this
	 * */
	String CODEC_TYPE_VALUE_WRITER = "codec.type.value.writer";
	
	/** CODEC_ID_VALUE_READER_NAME 
	 * Annotation for specifying a ValueReader name to be used when deserializing the object 
	 * marked like this
	 * */
	String CODEC_ID_VALUE_READER_NAME = "codec.id.value.reader.name";
	
	/** CODEC_ID_VALUE_WRITER_NAME 
	 * Option for specifying a ValueWriter name to be used when serializing the object marked like this
	 * */
	String CODEC_ID_VALUE_WRITER_NAME = "codec.id.value.writer.name";
	
	/** CODEC_TYPE_VALUE_READER_NAME 
	 * Option for specifying a ValueReader name to be used when deserializing the type 
	 * */
	String CODEC_TYPE_VALUE_READER_NAME = "codec.type.value.reader.name";
	
	/** CODEC_TYPE_VALUE_WRITER_NAME 
	 * Option for specifying a ValueWriter name to be used when serializing the type
	 * */
	String CODEC_TYPE_VALUE_WRITER_NAME = "codec.type.value.writer.name";
	
	/** CODEC_VALUE_READERS_MAP 
	 * Options to be passed via {@link Resource}{@link #load} and {@link #save} options for specifying
	 * alternative {@link CodecValueReader} for a certain @link{EStructuralFeature} 
	 * */
	String CODEC_VALUE_READERS_MAP = "codec.value.readers.map";
	
	
	/** CODEC_VALUE_WRITERS_MAP 
	 * Options to be passed via {@link Resource}{@link #load} and {@link #save} options for specifying
	 * alternative {@link CodecValueWriter} for a certain @link{EStructuralFeature} 
	 * */
	String CODEC_VALUE_WRITERS_MAP = "codec.value.writers.map";
	
	/** CODEC_TYPE_USE 
	 * Annotation used for specifying a strategy to serialize the type of the object (class name, uri, etc)
	 * */
	String CODEC_TYPE_USE = "codec.type.use";
	
	/** CODEC_TYPE_INCLUDE
	 * Annotation used to specify weather the type information should be serialized or not.
	 * */
	String CODEC_TYPE_INCLUDE = "codec.type.include";

}
