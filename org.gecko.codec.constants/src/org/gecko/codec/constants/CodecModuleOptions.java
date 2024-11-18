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
 * These are the options that can be used when saving/loading a Resource, in order to 
 * overwrite the CodecModule configuration
 * 
 * @author ilenia
 * @since Aug 16, 2024
 */
public interface CodecModuleOptions {
	
	/** CODEC_MODULE_SERIALIZE_DEFAULT_VALUE 
	 * to overwrite the {@link org.gecko.codec.configurator.serializeDefaultValue} 
	 * */
	String CODEC_MODULE_SERIALIZE_DEFAULT_VALUE = "codec.module.serialize.default.value";

	/** CODEC_MODULE_SERIALIZE_EMPTY_VALUE 
	 * to overwrite the {@link org.gecko.codec.configurator.serializeEmptyValue} 
	 * */
	String CODEC_MODULE_SERIALIZE_EMPTY_VALUE = "codec.module.serialize.empty.value";
	
	/** CODEC_MODULE_SERIALIZE_NULL_VALUE 
	 * to overwrite the {@link org.gecko.codec.configurator.serializeNullValue} 
	 * */
	String CODEC_MODULE_SERIALIZE_NULL_VALUE = "codec.module.serialize.null.value";
	
	/** CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA 
	 * to overwrite the {@link org.gecko.codec.configurator.useNamesFromExtededMetaData} 
	 * */
	String CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA = "codec.module.use.names.from.extended.metadata";
	
	/** CODEC_MODULE_USE_ID 
	 * to overwrite the {@link org.gecko.codec.configurator.useId} 
	 * */
	String CODEC_MODULE_USE_ID = "codec.module.use.id";
		
	/** CODEC_MODULE_ID_ON_TOP 
	 * to overwrite the {@link org.gecko.codec.configurator.idOnTop} 
	 * */
	String CODEC_MODULE_ID_ON_TOP = "codec.module.id.on.top";
	
	/** CODEC_MODULE_SERIALIZE_ID_FIELD 
	 * to overwrite the {@link org.gecko.codec.configurator.serializeIdField} 
	 * */
	String CODEC_MODULE_SERIALIZE_ID_FIELD = "codec.module.serialize.id.field";
	
	/** CODEC_MODULE_ID_FEATURE_AS_PRIMARY_KEY 
	 * to overwrite the {@link org.gecko.codec.configurator.idFeatureAsPrimaryKey} 
	 * */
	String CODEC_MODULE_ID_FEATURE_AS_PRIMARY_KEY = "codec.module.id.feature.as.primary.key";
	
	/** CODEC_MODULE_ID_KEY 
	 * to overwrite the {@link org.gecko.codec.configurator.idKey} 
	 * */
	String CODEC_MODULE_ID_KEY = "codec.module.id.key";
	
	/** CODEC_MODULE_SERIALIZE_TYPE 
	 * to overwrite the {@link org.gecko.codec.configurator.serializeType} 
	 * */
	String CODEC_MODULE_SERIALIZE_TYPE = "codec.module.serialize.type";
	
	/** CODEC_MODULE_SERIALIZE_SUPER_TYPES 
	 * to overwrite the {@link org.gecko.codec.configurator.serializeSuperTypes} 
	 * */
	String CODEC_MODULE_SERIALIZE_SUPER_TYPES = "codec.module.serialize.super.types";
	
	/** CODEC_MODULE_SERIALIZE_ALL_SUPER_TYPES 
	 * to overwrite the {@link org.gecko.codec.configurator.serializeAllSuperTypes} 
	 * */
	String CODEC_MODULE_SERIALIZE_ALL_SUPER_TYPES = "codec.module.serialize.all.super.types";
	
	/** CODEC_MODULE_SERIALIZE_SUPER_TYPES_AS_ARRAY 
	 * to overwrite the {@link org.gecko.codec.configurator.serializeTypesAsArray} 
	 * */
	String CODEC_MODULE_SERIALIZE_SUPER_TYPES_AS_ARRAY = "codec.module.serialize.super.types.as.array";
	
	/** CODEC_MODULE_TYPE_KEY 
	 * to overwrite the {@link org.gecko.codec.configurator.typeKey} 
	 * */
	String CODEC_MODULE_TYPE_KEY = "codec.module.type.key";
	
	/** CODEC_MODULE_REFERENCE_KEY 
	 * to overwrite the {@link org.gecko.codec.configurator.refKey} 
	 * */
	String CODEC_MODULE_REFERENCE_KEY = "codec.module.reference.key";
	
	/** CODEC_MODULE_PROXY_KEY 
	 * to overwrite the {@link org.gecko.codec.configurator.proxyKey} 
	 * */
	String CODEC_MODULE_PROXY_KEY = "codec.module.proxy.key";
	
	/** CODEC_MODULE_TIMESTAMP_KEY 
	 * to overwrite the {@link org.gecko.codec.configurator.timestampKey} 
	 * */
	String CODEC_MODULE_TIMESTAMP_KEY = "codec.module.timestamp.key";
	
	/** CODEC_MODULE_SUPERTYPE_KEY 
	 * to overwrite the {@link org.gecko.codec.configurator.superTypeKey} 
	 * */
	String CODEC_MODULE_SUPERTYPE_KEY = "codec.module.supertype.key";
	
	/** CODEC_MODULE_WIRTE_ENUM_LITERAL 
	 * to overwrite the {@link org.gecko.codec.configurator.writeEnumLiteral} 
	 * */
	String CODEC_MODULE_WIRTE_ENUM_LITERAL = "codec.module.write.enum.literal";

}
