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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * 
 * @author ilenia
 * @since Aug 15, 2024
 */
@Component(name = "CodecModuleConfigurator", service = CodecModuleConfigurator.class, 
	configurationPolicy = ConfigurationPolicy.REQUIRE, property = {"type=json"})
public class CodecModuleConfigurator {
	
	private CodecModule.Builder moduleBuilder; 

	@Activate
	public void activate(CodecModuleConfig codecConfig) {
		moduleBuilder = new CodecModule.Builder();
		configureModuleBuilder(codecConfig);
	}
	
	private void configureModuleBuilder(CodecModuleConfig codecConfig) {
		moduleBuilder
			.withIdKey(codecConfig.idKey())
			.withIdOnTop(codecConfig.idOnTop())
			.withCodecModuleName(codecConfig.codecModuleName())
			.withCodecType(codecConfig.codecType())
			.withIdFeatureAsPrimaryKey(codecConfig.idFeatureAsPrimaryKey())
			.withProxyKey(codecConfig.proxyKey())
			.withRefKey(codecConfig.refKey())
			.withSerailizeSuperTypesAsArray(codecConfig.serializeSuperTypesAsArray())
			.withSerializeArrayBatched(codecConfig.serializeArrayBatched())
			.withSerializeDefaultValue(codecConfig.serializeDefaultValue())
			.withSerializeEmptyValue(codecConfig.serializeEmptyValue())
			.withSerializeNullValue(codecConfig.serializeNullValue())
			.withSerializeIdField(codecConfig.serializeIdField())
			.withSerializeSuperTypes(codecConfig.serializeSuperTypes())
			.withSerializeAllSuperTypes(codecConfig.serializeAllSuperTypes())
			.withSerializeType(codecConfig.serializeType())
			.withTimestampKey(codecConfig.timestampKey())
			.withTypeKey(codecConfig.typeKey())
			.withUseId(codecConfig.useId())
			.withUseNamesFromExtendedMetaData(codecConfig.useNamesFromExtendedMetaData())
			.withSuperTypeKey(codecConfig.superTypeKey());
	}
	
	public CodecModule.Builder getCodecModuleBuilder() {
		return moduleBuilder;
	}
	
}
