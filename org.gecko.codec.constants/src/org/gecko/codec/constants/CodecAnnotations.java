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
 * These are the annotations which can be used directly on an EMF model to specify the general
 * codec properties that the model should follow. Accroding to these annotations, the ModelInfo
 * objects are created.
 * 
 * @author ilenia
 * @since Aug 5, 2024
 */
public interface CodecAnnotations {
	
	/** CODEC_INHERIT 
	 * Annotation for specifying that codec annotations on the direct parent should be inherited, even if the parent 
	 * comes from another {@link EPackage}. By default only if the parent belongs to the same {@link EPackage} then the 
	 * annotations are inherited. 
	 * */
	String CODEC_INHERIT = "codec.inherit";
	
	/** CODEC_TRANSIENT 
	 *  Annotation for specifying that the feature should not be serialized.
	 *  This is supposed to be used when annotating the model.
	 *  If this has to be overwritten by {@link Resource}{@link #save} and {@link #load}
	 *  options, use {@link CodecModelInfoOptions.CODEC_IGNORE_FEATURES_LIST} and 
	 *  {@link CodecModelInfoOptions.CODEC_IGNORE_NOT_FEATURES_LIST}
	 * */
	String CODEC_TRANSIENT = "codec.transient";
	
	/** CODEC_ID_STRATEGY 
	 *  Annotation for specifying a strategy to be followed when building the id 
	 *  of the {@link EObject} when serializing it
	 * */
	String CODEC_ID_STRATEGY = "codec.id.strategy";
	
	
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
	 * to specify the order of the annotated field when constructing the id. 
	 * This is ignored if the id strategy is not set to COMBINED or the same feature is not 
	 * marked with the {@link CODEC_ID_FIELD} annotation
	 * */
	String CODEC_ID_ORDER = "codec.id.order";
	
	/** CODEC_ID_SEPARATOR 
	 * annotation at the {@link EClassifier} level, to specify the separator to be used when constructing the id 
	 * with the COMBINED strategy. The default separator value is "-". This option is ignored if the id strategy 
	 * is different from COMBINED.
	 * */
	String CODEC_ID_SEPARATOR = "codec.id.separator";
	
	/** CODEC_ID_VALUE_WRITER_NAME 
	 * annotation at the {@link EClassifier} level, to specify a {@link CodecValueWriter} name to be used 
	 * when serializing the id field. The actual {@link CodecValueWriter} object should then be one
	 * of the automatically registered ones or should be passed through the options when saving a {@link Resource}. 
	 * */
	String CODEC_ID_VALUE_WRITER_NAME = "codec.id.value.writer.name";
	
	/** CODEC_ID_VALUE_READER_NAME 
	 * annotation at the {@link EClassifier} level, to specify a {@link CodecValueReader} name to be used 
	 * when deserializing the id field. The actual {@link CodecValueReader} object should then be one
	 * of the automatically registered ones or should be passed through the options when loading a {@link Resource}. 
	 * */
	String CODEC_ID_VALUE_READER_NAME = "codec.id.value.reader.name";
	
	/** CODEC_TYPE_VALUE_WRITER_NAME 
	* annotation at the {@link EClassifier} level, to specify a {@link CodecValueWriter} name to be used 
	 * when serializing the type information. The actual {@link CodecValueWriter} object should then be one
	 * of the automatically registered ones or should be passed through the options when saving a {@link Resource}. 
	 * */
	String CODEC_TYPE_VALUE_WRITER_NAME = "codec.type.value.writer.name";
	
	/** CODEC_TYPE_VALUE_READER_NAME 
	* annotation at the {@link EClassifier} level, to specify a {@link CodecValueReader} name to be used 
	 * when deserializing the type information. The actual {@link CodecValueReader} object should then be one
	 * of the automatically registered ones or should be passed through the options when loading a {@link Resource}. 
	 * */
	String CODEC_TYPE_VALUE_READER_NAME = "codec.type.value.reader.name";
	
	/** CODEC_VALUE_WRITER_NAME 
	 * annotation at the {@link EStructuralFeature} level, to specify a {@link CodecValueWriter} name to be used 
	 * when serializing the annotated {@link EStructuralFeature}. The actual {@link CodecValueWriter} object should 
	 * then be one of the automatically registered ones or should be passed through the options when saving a {@link Resource}. 
	 * */
	String CODEC_VALUE_WRITER_NAME = "codec.value.writer.name";
	
	/** CODEC_VALUE_READER_NAME 
	 * annotation at the {@link EStructuralFeature} level, to specify a {@link CodecValueReader} name to be used 
	 * when deserializing the annotated {@link EStructuralFeature}. The actual {@link CodecValueReader} object should 
	 * then be one of the automatically registered ones or should be passed through the options when loading a {@link Resource}. 
	 * */
	String CODEC_VALUE_READER_NAME = "codec.value.reader.name";
	
	/** CODEC_TYPE_USE 
	 * Annotation used for specifying a strategy to serialize the type of the object
	 * Currently supported values are:
  	 * CLASS: the class name will be used (e.g. org.gecko.codec.demo.model.person.Person) 
  	 * NAME: the class name will be used (e.g. Person)
  	 * URI: the URI will be used (e.g. http://example.de/person/1.0#//Person) 
	 * */
	String CODEC_TYPE_USE = "codec.type.use";
	
	/** CODEC_TYPE_INCLUDE
	 * Annotation used to specify weather the type information should be serialized or not.
	 * */
	String CODEC_TYPE_INCLUDE = "codec.type.include";

}
