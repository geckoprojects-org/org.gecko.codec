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
package org.gecko.codec.jackson;

import org.eclipse.emfcloud.jackson.module.EMFModule;

import com.fasterxml.jackson.core.Version;

/**
 * 
 * @author mark
 * @since 10.01.2024
 */
public class CodecModule extends EMFModule {

	/** serialVersionUID */
	private static final long serialVersionUID = 8098206569874480984L;
	private static final String MODULE_NAME = "gecko-codec-emfjson";
	
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
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.module.EMFModule#setupModule(com.fasterxml.jackson.databind.Module.SetupContext)
	 */
	@Override
	public void setupModule(SetupContext context) {
		// Use the old ones from EMFJson for now, we will setup our own 
		super.setupModule(context);
	}
	

}
