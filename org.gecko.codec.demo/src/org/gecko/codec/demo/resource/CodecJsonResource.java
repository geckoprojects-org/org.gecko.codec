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
package org.gecko.codec.demo.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.gecko.codec.info.CodecModelInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
public class CodecJsonResource extends CodecResource {
	
	/**
	 * Creates a new instance.
	 * @param uri
	 * @param modelInfo
	 * @param module
	 * @param mapper
	 */
	public CodecJsonResource(URI uri, CodecModelInfo modelInfo, EMFModule module, ObjectMapper mapper) {
		super(uri, modelInfo, module, mapper);
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		super.doSave(outputStream, options);

		if (options == null) {
			options = Collections.<String, Object> emptyMap();
		}

		if (outputStream instanceof URIConverter.Saveable) {

			((URIConverter.Saveable) outputStream).saveResource(this);

		} else {

			mapper.writer()
			.with(EMFContext.from(options))
			.writeValue(outputStream, this);

		}		
	}

}
