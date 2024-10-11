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

import org.eclipse.emf.ecore.resource.Resource;

/**
 * This is the CodecModule configuration, which should be used to configure 
 * general serialization/deserialization properties
 * 
 * @author ilenia
 * @since Aug 2, 2024
 */
public @interface CodecModuleConfig {
	
	String codecType() default "json";
	
	String codecModuleName() default "gecko-codec-module";
	
	
	/**
	 * Option used to indicate the module to serialize default attributes values.
     * Default values are not serialized by default.
	 * @return
	 */
	boolean serializeDefaultValue() default false;
	
	/**
	 * Option used to indicate the module to serialize null values.
	 * This is used for objects in general and for map values.
	 * Default is false
	 * @return
	 */
	boolean serializeNullValue() default false;
	
	/**
	 * Option to specify weather or not to serialize empty values.
	 * This is used for arrays, lists, or empty String.
	 * Default is false.
	 * @return
	 */
	boolean serializeEmptyValue() default false;
	
	/**
	 * Setting this option to <code>true</code>, will send lists and arrays using the writeArray callbacks.
     * Per default the serialization happens with startArray, then calling writeValue for each element. 
     * Default is Boolean.FALSE
	 * @return
	 */
	boolean serializeArrayBatched() default false;
	
	/**
	 * Option used to indicate whether feature names specified in
     * {@link org.eclipse.emf.ecore.util.ExtendedMetaData} annotations should
     * be respected.
     * Default is Boolean.TRUE
	 * @return
	 */
	boolean useNamesFromExtendedMetaData() default true;
	
	
	/**
	 * Option used to indicate the module to serialize a special id field, which 
	 * might coincide with the ID field of the EObject or might be constructed 
	 * differently, depending on the strategy specified in the model or in the 
	 * serialization options.
	 * 
     * Default is Boolean.TRUE
	 * @return
	 */
	boolean useId() default true;
	
	/**
	 * Option used to indicate the module to serialize the id field 
	 * on top of the document.
	 * Default is Boolean.TRUE
	 * @return
	 */
	boolean idOnTop() default true;
	
	/**
	 * Option used to indicate the module to additionally serialize the id field of an EObject as it is.
     * This might be superfluous when using as id strategy the one that uses the id field itself, but it 
     * might be useful when the id strategy is set to COMBINED.
     * It can be useful to OPTION_USE_ID(true) and OPTIONS_USE_ID_FIELD(false) and additionally store this 
     * id field, while using the URI fragment or {@link Resource} ID as primary key
     * 
	 * Default is Boolean.FALSE
	 * @return
	 */
	boolean serializeIdField() default false;
	
	/**
	 * If it is set to Boolean.TRUE and the ID was not specified in the URI, the value of the ID
	 * attribute will be used as the primary key if it exists.
	 * Default is Boolean.TRUE
	 * @return
	 */
	boolean idFeatureAsPrimaryKey() default true;
	
	
	/**
	 * Option to indicate the default key to be used for id 
	 * Default is "_id"
	 * @return
	 */
	String idKey() default "_id";
	
	
	/**
	 * Option used to indicate the module to serialize the type information.
	 * Default is Boolean.TRUE
	 * @return
	 */
	boolean serializeType() default true;
	
	/**
	 * Option used to indicate the module to serialize the supertype information.
	 * If this is set to TRUE but serializeType is set to FALSE, then this option
	 * is ignored.
	 * If this is set to TRUE, only the direct parent will be listed as supertype.
	 * For the whole inheritance chain, look at serializeAllSuperTypes option.
	 * Default is Boolean.FALSE
	 * @return
	 */
	boolean serializeSuperTypes() default false;
	
	/**
	 * Option used to indicate the module to serialize the whole chain of inheritance.
	 * Default is Boolean.FALSE
	 * @return
	 */
	boolean serializeAllSuperTypes() default false;
	
	/**
	 * By setting this to Boolean.TRUE the supertypes are written as an array of URIs.
	 * If this is set to FALSE, the supertypes are written as a comma separated String.
	 * Default is Boolean.TRUE
	 * @return
	 */
	boolean serializeSuperTypesAsArray() default true;
	
	/**
	 * Option to indicate the default key to be used for type
	 * Default is "_type"
	 * @return
	 */
	String typeKey() default "_type";
	
	/**
	 * @return
	 */
	String superTypeKey() default "_supertype";
	
	
	/**
	 * Option to indicate the default key to be used for references
	 * Default is "$ref"
	 * @return
	 */
	String refKey() default "$ref";
	
	/**
	 * Option to indicate the default key for proxies
	 * Default is "_proxy"
	 * @return
	 */
	String proxyKey() default "_proxy";
	
	/**
	 * Option to indicate the default key for timestamp
	 * Default is "_timestamp"
	 * @return
	 */
	String timestampKey() default "_timestamp";
	
	

}
