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

/**
 * 
 * @author ilenia
 * @since Aug 16, 2024
 */
public interface CodecModuleOptions {
	
	String CODEC_MODULE_TYPE = "codec.type";

	String CODEC_MODULE_NAME = "codec.module.name";

	String CODEC_MODULE_VERSION = "codec.module.version";
	
	String CODEC_MODULE_SERIALIZE_DEFAULT_VALUE = "codec.module.serialize.default.value";
	
	String CODEC_MODULE_SERIALIZE_ARRAY_BATCHED = "codec.module.serialize.array.batched";
	
	String CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA = "codec.module.use.names.from.extended.metadata";
	
	String CODEC_MODULE_USE_ID = "codec.module.use.id";
	
	String CODEC_MODULE_USE_ID_FIELD = "codec.module.use.id.field";
	
	String CODEC_MODULE_ID_ON_TOP = "codec.module.id.on.top";
	
	String CODEC_MODULE_SERIALIZE_ID_FIELD = "codec.module.serialize.id.field";
	
	String CODEC_MODULE_ID_FEATURE_AS_PRIMARY_KEY = "codec.module.id.feature.as.primary.key";
	
	String CODEC_MODULE_ID_KEY = "codec.module.id.key";
	
	String CODEC_MODULE_SERIALIZE_TYPE = "codec.module.serialize.type";
	
	String CODEC_MODULE_SERIALIZE_SUPER_TYPES = "codec.module.serialize.super.types";
	
	String CODEC_MODULE_SERIALIZE_SUPER_TYPES_AS_ARRAY = "codec.module.serialize.super.types.as.array";
	
	String CODEC_MODULE_TYPE_KEY = "codec.module.type.key";
	
	String CODEC_MODULE_REFERENCE_KEY = "codec.module.reference.key";
	
	String CODEC_MODULE_PROXY_KEY = "codec.module.proxy.key";
	
	String CODEC_MODULE_TIMESTAMP_KEY = "codec.module.timestamp.key";
	
	String CODEC_MODULE_SUPERTYPE_KEY = "codec.module.supertype.key";

}
