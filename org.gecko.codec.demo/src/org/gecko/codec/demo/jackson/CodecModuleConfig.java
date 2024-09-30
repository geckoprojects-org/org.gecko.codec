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
	 * This is used for arrays, or lists.
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
	 * Option used to indicate the module to use the default ID serializer if
     * none are provided. The ID serializer used by default is IdSerializer.
     * Default is Boolean.TRUE
	 * @return
	 */
	boolean useId() default true;
		
	/**
	 * Option used to indicate the module to use the ID field of the EObject.
	 * Default is Boolean.TRUE
	 * @return
	 */
	boolean useIdField() default true;
	
	/**
	 * Value	Option used to indicate the module to serialize the id field 
	 * on top of the document.
	 * Default is Boolean.TRUE
	 * @return
	 */
	boolean idOnTop() default true;
	
	/**
	 * Option used to indicate the module to additionally serialize the id field of an EObject as it is.
     * This is usually not needed, because the _id key always holds the ID at the first position. 
     * This id-field itself can be found at a later index. So finding it may cost a lot of effort.
     * It can be useful to OPTION_USE_ID(true) and OPTIONS_USE_ID_FIELD(false) and additionally store this 
     * id field, while using the URI fragment or {@link Resource} ID as primary key
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
	 * Option used to indicate the module to use the default type serializer if
     * none are provided. The type serializer used by default is ETypeSerializer.
	 * Default is Boolean.TRUE
	 * @return
	 */
	boolean serializeType() default true;
	
	/**
	 * To avoid writing unnecessary URIs in the result format, we write eClassUris only for the root 
	 * class and for EReferences, where the actual value does not equal but inherit from the 
	 * stated reference type. 
	 * By setting this option to Boolean.TRUE, all eClass URIs will be written regardless. 
	 * Default is Boolean.FALSE
	 * @return
	 */
	boolean serializeSuperTypes() default false;
	
	/**
	 * By setting this to Boolean.TRUE the supertypes are written as an array of URIs.
	 * Default is Boolean.TRUE
	 * @return
	 */
	boolean serailizeSuperTypesAsArray() default true;
	
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
