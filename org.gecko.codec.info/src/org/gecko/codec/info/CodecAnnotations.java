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
	
	/** CODEC_ID_STRATEGY 
	 *  Annotation for specifying a strategy to be followed when building the id 
	 *  of the {@link EObject} when serializing it
	 * */
	String CODEC_ID_STRATEGY = "codec.id.startegy";
	
	/** CODEC_ID_FIELD 
	 * Annotation for specifying that a field should be treated as an id field.
	 * This is needed especially when the id should be a combination of more fields.
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
	
	/** CODEC_TYPE_STRATEGY 
	 * Annotation used for specifying a strategy to serialize the type of the object
	 * */
	String CODEC_TYPE_STRATEGY = "codec.type.strategy";
	
	/** CODEC_TYPE_VALUE
	 * Annotation used to provide a specific type value to be used when serializing the object
	 * */
	String CODEC_TYPE_VALUE = "codec.type.value";

}
