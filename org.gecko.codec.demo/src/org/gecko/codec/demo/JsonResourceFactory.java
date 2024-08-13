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
package org.gecko.codec.demo;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.gecko.codec.demo.jackson.CodecFactory;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.demo.resource.CodecJsonResource;
import org.gecko.codec.info.CodecModelInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
@Component(name = "JsonRF", service = Resource.Factory.class, property = "codecType=json", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class JsonResourceFactory extends ResourceFactoryImpl {
	
	@Reference
	private CodecModelInfo modelInfo;
	
	@Reference(name="jsonFactory")
	private CodecFactory jsonFactory;
	
	@Reference(name  ="module")
	private CodecModule module;
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Resource createResource(URI uri) {		
		
		ObjectMapper mapper = new ObjectMapper(jsonFactory);		
//		TODO: set the mapper with the properties set in the jsonFactory
		mapper.registerModule(module);
		
		return new CodecJsonResource(uri, modelInfo, module, mapper);
	}

}
