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

import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;

import com.fasterxml.jackson.core.Version;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
public class CodecModule extends EMFModule {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String codecType;
	private String codecModuleName;
	private boolean serializeDefaultValue;
	private boolean serializeArrayBatched;
	private boolean useNamesFromExtendedMetaData;
	private boolean useId;
	private boolean useIdField;
	private boolean idOnTop;
	private boolean serializeIdField;
	private boolean idFeatureAsPrimaryKey;
	private String idKey;
	private boolean serializeType;
	private boolean serializeSuperTypes;
	private boolean serializeSuperTypesAsArray;
	private String typeKey;
	private String refKey;
	private String proxyKey;
	private String timestampKey;
	
	private PackageCodecInfo codecModelInfo;
	private CodecModelInfo codecModelInfoService;
	
	public String getCodecType() {
		return codecType;
	}

	public String getCodecModuleName() {
		return codecModuleName;
	}

	public boolean isSerializeDefaultValue() {
		return serializeDefaultValue;
	}

	public boolean isSerializeArrayBatched() {
		return serializeArrayBatched;
	}

	public boolean isUseNamesFromExtendedMetaData() {
		return useNamesFromExtendedMetaData;
	}

	public boolean isUseId() {
		return useId;
	}

	public boolean isUseIdField() {
		return useIdField;
	}

	public boolean isIdOnTop() {
		return idOnTop;
	}

	public boolean isSerializeIdField() {
		return serializeIdField;
	}

	public boolean isIdFeatureAsPrimaryKey() {
		return idFeatureAsPrimaryKey;
	}

	public String getIdKey() {
		return idKey;
	}

	public boolean isSerializeType() {
		return serializeType;
	}

	public boolean isSerializeSuperTypes() {
		return serializeSuperTypes;
	}

	public boolean isSerializeSuperTypesAsArray() {
		return serializeSuperTypesAsArray;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public String getRefKey() {
		return refKey;
	}

	public String getProxyKey() {
		return proxyKey;
	}

	public String getTimestampKey() {
		return timestampKey;
	}
	
	public PackageCodecInfo getCodecModelInfo() {
		return codecModelInfo;
	}
	
	public CodecModelInfo getCodecModelInfoService() {
		return codecModelInfoService;
	}

	public CodecModule(Builder builder) {
		this.codecType = builder.codecType;
		this.codecModuleName = builder.codecModuleName;
		this.serializeArrayBatched = builder.serializeArrayBatched;
		this.serializeSuperTypesAsArray = builder.serializeSuperTypesAsArray;
		this.serializeDefaultValue = builder.serializeDefaultValue;
		this.serializeSuperTypes = builder.serializeSuperTypes;
		this.serializeType = builder.serializeType;
		this.idFeatureAsPrimaryKey = builder.idFeatureAsPrimaryKey;
		this.idKey = builder.idKey;
		this.typeKey = builder.typeKey;
		this.timestampKey = builder.timestampKey;
		this.proxyKey = builder.proxyKey;
		this.refKey = builder.refKey;
		this.useId = builder.useId;
		this.useIdField = builder.useIdField;
		this.useNamesFromExtendedMetaData = builder.useNamesFromExtendedMetaData;
		this.idOnTop = builder.idOnTop;
		this.serializeIdField = builder.serializeIdField;
		this.codecModelInfo = builder.codecModelInfo;
		this.codecModelInfoService = builder.codecModelInfoService;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.module.SimpleModule#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return this.codecModuleName;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.module.EMFModule#version()
	 */
	@Override
	public Version version() {
		return new Version(1, 0, 0, "SNAPSHOT", "org.geckoprojects.codec", "org.gecko.codec");
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.module.SimpleModule#setupModule(com.fasterxml.jackson.databind.Module.SetupContext)
	 */
	@Override
	public void setupModule(final SetupContext context) {
		super.setupModule(context);
		CodecSerializersNew serializers = new CodecSerializersNew(this);
		context.addSerializers(serializers);	
	}
	
	public static class Builder {
		
		private PackageCodecInfo codecModelInfo;
		private CodecModelInfo codecModelInfoService;

		private String codecType = "";
		private String codecModuleName = "gecko-codec-module";
		private boolean serializeDefaultValue = false;
		private boolean serializeArrayBatched = false;
		private boolean useNamesFromExtendedMetaData = true;
		private boolean useId = true;
		private boolean useIdField = true;
		private boolean idOnTop = true;
		private boolean serializeIdField = false;
		private boolean idFeatureAsPrimaryKey = true;
		private String idKey = "_id";
		private boolean serializeType = true;
		private boolean serializeSuperTypes = false;
		private boolean serializeSuperTypesAsArray = true;
		private String typeKey = "_type";
		private String refKey = "$ref";
		private String proxyKey = "_proxy";
		private String timestampKey = "_timestamp";
		
		public Builder() {
			
		}
		
		public Builder withCodecType(String codecType) {
			this.codecType = codecType;
			return this;
		}
		
		public Builder withCodecModuleName(String codecModuleName) {
			this.codecModuleName = codecModuleName;
			return this;
		}
		
		public Builder withIdKey(String idKey) {
			this.idKey = idKey;
			return this;
		}
		
		public Builder withTypeKey(String typeKey) {
			this.typeKey = typeKey;
			return this;
		}
		
		public Builder withProxyKey(String proxyKey) {
			this.proxyKey = proxyKey;
			return this;
		}
		
		public Builder withRefKey(String refKey) {
			this.refKey = refKey;
			return this;
		}
		
		public Builder withTimestampKey(String timestampKey) {
			this.timestampKey = timestampKey;
			return this;
		}
		
		public Builder withSerializeDefaultValue(boolean serializeDefaultValue) {
			this.serializeDefaultValue = serializeDefaultValue;
			return this;
		}
		public Builder withSerializeArrayBatched(boolean serializeArrayBatched) {
			this.serializeArrayBatched = serializeArrayBatched;
			return this;
		}
		public Builder withUseNamesFromExtendedMetaData(boolean useNamesFromExtendedMetaData) {
			this.useNamesFromExtendedMetaData = useNamesFromExtendedMetaData;
			return this;
		}
		public Builder withUseId(boolean useId) {
			this.useId = useId;
			return this;
		}
		public Builder withUseIdField(boolean useIdField) {
			this.useIdField = useIdField;
			return this;
		}
		public Builder withIdOnTop(boolean idOnTop) {
			this.idOnTop = idOnTop;
			return this;
		}
		
		public Builder withSerializeIdField(boolean serializeIdField) {
			this.serializeIdField = serializeIdField;
			return this;
		}
		
		public Builder withIdFeatureAsPrimaryKey(boolean idFeatureAsPrimaryKey) {
			this.idFeatureAsPrimaryKey = idFeatureAsPrimaryKey;
			return this;
		}
		public Builder withSerializeType(boolean serializeType) {
			this.serializeType = serializeType;
			return this;
		}
		
		public Builder withSerializeSuperTypes(boolean serializeSuperTypes) {
			this.serializeSuperTypes = serializeSuperTypes;
			return this;
		}
		
		public Builder withSerailizeSuperTypesAsArray(boolean serializeSuperTypesAsArray) {
			this.serializeSuperTypesAsArray = serializeSuperTypesAsArray;
			return this;
		}
		
		public Builder bindCodecModelInfo(PackageCodecInfo codecModelInfo) {
			this.codecModelInfo = codecModelInfo;
			return this;
		}
		
		public Builder bindCodecModelInfoService(CodecModelInfo codecModelInfoService) {
			this.codecModelInfoService = codecModelInfoService;
			return this;
		}
		
		public CodecModule build() {
			return new CodecModule(this);
		}
	}

}
