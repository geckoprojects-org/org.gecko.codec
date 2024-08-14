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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.core.Version;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
@Component(name="CodecModule", service = CodecModule.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class CodecModule extends EMFModule {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Reference
	private CodecModelInfo codecModelInfo;

	private CodecModuleConfig codecConfig;
	private Map<String, Object> properties = new HashMap<>();
	private EClassCodecInfo eClassCodecInfo;

	@Activate
	public void activate(CodecModuleConfig codecConfig, Map<String, Object> properties) {
		this.codecConfig = codecConfig;
		this.properties = Map.copyOf(properties);
	}

	public Map<String, Object> getCodecModuleProperties() {
		return properties;
	}

	public CodecModuleConfig getCodecModuleConfig() {
		return codecConfig;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.module.SimpleModule#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return codecConfig.codecModuleName();
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
		CodecSerializersNew serializers = new CodecSerializersNew(this, codecModelInfo);
		context.addSerializers(serializers);	
	}

	public void bindEClassCodecInfo(EClassCodecInfo eClassCodecInfo) {
		this.eClassCodecInfo = eClassCodecInfo;
	}
	
	public EClassCodecInfo getEClassCodecInfo(EClass eClass) {
		if(eClassCodecInfo.getClassifier().equals(eClass))return eClassCodecInfo;
		return null;
	}
}
