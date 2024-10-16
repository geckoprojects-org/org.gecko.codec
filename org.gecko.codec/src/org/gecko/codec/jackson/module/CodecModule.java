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
package org.gecko.codec.jackson.module;

import static org.gecko.codec.jackson.module.CodecProperties.ID_KEY;
import static org.gecko.codec.jackson.module.CodecProperties.TYPE_KEY;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import org.eclipse.emfcloud.jackson.annotations.EcoreTypeInfo;
import org.eclipse.emfcloud.jackson.databind.ser.EcoreReferenceSerializer;
import org.eclipse.emfcloud.jackson.databind.ser.NullKeySerializer;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.jackson.databind.CodecFactory;
import org.gecko.codec.jackson.databind.annotations.CodecIdentityInfo;
import org.gecko.codec.jackson.databind.deser.CodecDeserializers;
import org.gecko.codec.jackson.databind.ser.CodecEcoreReferenceSerializer;
import org.gecko.codec.jackson.databind.ser.CodecSerializers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * @author mark
 * @since 10.01.2024
 */
public class CodecModule extends EMFModule {

	/** serialVersionUID */
	private static final long serialVersionUID = 8098206569874480984L;
	private static final String MODULE_NAME = "gecko-codec-emfjson";
	protected static final int DEFAULT_FEATURES = CodecFeature.collectDefaults();
	protected int moduleFeatures = DEFAULT_FEATURES;
	
	private Map<String, Object> properties;
	private EClassCodecInfo eClassCodecInfo;
	
	public CodecModule() {
		this((Map<String, Object>) null);
	}

	/**
	 * Creates a new instance.
	 * @param properties
	 */
	public CodecModule(Map<String, Object> properties) {
		this.properties = Objects.isNull(properties) ? new HashMap<>() : properties;
	}
	
	public CodecModule(EClassCodecInfo eClassCodecInfo) {
		this.eClassCodecInfo = eClassCodecInfo;
	}
	
	/**
	 * Returns the properties.
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.module.EMFModule#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.module.EMFModule#version()
	 */
	@Override
	public Version version() {
		return new Version(1, 0, 0, "SNAPSHOT", "org.geckoprojects.codec", "org.gecko.codec");
	}

	/**
	 * Returns a pre configured mapper with the EMF module.
	 *
	 * @return mapper
	 */
	public static ObjectMapper setupDefaultMapper() {
		return setupDefaultMapper(new CodecFactory());
	}
	
	/**
	 * Returns a pre configured mapper with the EMF module.
	 *
	 * @return mapper
	 */
	public static ObjectMapper setupDefaultMapper(Map<String, Object> properties) {
		return setupDefaultMapper(new CodecFactory(), properties);
	}

	/**
	 * Returns a pre configured mapper using the EMF module and the specified jackson factory.
	 * This method can be used to work with formats others than JSON (such as YAML).
	 *
	 * @param factory Jackson factory
	 * @return mapper
	 */
	public static ObjectMapper setupDefaultMapper(final JsonFactory factory, final Map<String, Object> properties) {
		final ObjectMapper mapper = new ObjectMapper(factory);
		// new Locale.Builder().setLanguageTag(key).build()
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getDefault());

		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.setDateFormat(dateFormat);
		mapper.setTimeZone(TimeZone.getDefault());
		CodecModule module = new CodecModule(properties);
		mapper.registerModule(module);
		
		// add default serializer for null EMap key
		mapper.getSerializerProvider().setNullKeySerializer(new NullKeySerializer());

		return mapper;
	}
	
	public static ObjectMapper setupDefaultMapper(final JsonFactory factory, EClassCodecInfo eClassCodecInfo) {
		final ObjectMapper mapper = new ObjectMapper(factory);
		CodecModule module = new CodecModule(eClassCodecInfo);
		mapper.registerModule(module);
		
		
		return mapper;
	}

	private CodecModule enable(final CodecFeature f) {
		moduleFeatures |= f.getMask();
		return this;
	}

	private CodecModule disable(final CodecFeature f) {
		moduleFeatures &= ~f.getMask();
		return this;
	}

	public int getFeatures() { return moduleFeatures; }

	/**
	 * Configures the module with one of possible Feature.
	 * @param feature feature
	 * @param state   of feature
	 * @return EMFModule
	 */
	public CodecModule configure(final CodecFeature feature, final boolean state) {
		if (state) {
			enable(feature);
		} else {
			disable(feature);
		}
		return this;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.module.EMFModule#setupModule(com.fasterxml.jackson.databind.Module.SetupContext)
	 */
	@Override
	public void setupModule(final SetupContext context) {
		super.setupModule(context);

//		This is called when registering the module with mapper.register(module)
		
//		We should create the EcoreTypeInfo and so on starting from our CodecTypeInfo and so on...?
//		IdentityInfo idInfo = eClassCodecInfo.getIdentityInfo();
//		setIdentityInfo()
		
		String typeKey = (String) properties.getOrDefault(TYPE_KEY.name(), TYPE_KEY.getKeyValue());
		setTypeInfo(new EcoreTypeInfo(typeKey));
		

		
		String idKey = (String) properties.getOrDefault(ID_KEY.name(), ID_KEY.getKeyValue());
		setIdentityInfo(new CodecIdentityInfo(idKey));
		if (getReferenceSerializer() == null || getReferenceSerializer() instanceof EcoreReferenceSerializer) {
			setReferenceSerializer(new CodecEcoreReferenceSerializer(getReferenceInfo(), getTypeInfo()));
		}
//		if (getReferenceDeserializer() == null || getReferenceDeserializer() instanceof EcoreReferenceDeserializer) {
//			setReferenceDeserializer(new CodecEcoreReferenceDeserializer(getReferenceInfo(), getTypeInfo()));
//		}
		CodecSerializers serializers = new CodecSerializers(this);
		context.addSerializers(serializers);
		CodecDeserializers deserializers = new CodecDeserializers(this);
		context.addDeserializers(deserializers);
	}

}
