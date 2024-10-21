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
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.gecko.codec.demo.jackson.ObjectMapperConfigurator;
import org.gecko.codec.demo.resource.CodecJsonResource;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
@Component(name = "JsonRF", service = {Resource.Factory.class, JsonResourceFactory.class}, 
		property = {EMFNamespaces.EMF_CONFIGURATOR_NAME + "=myJson", EMFNamespaces.EMF_MODEL_FILE_EXT + "=json"})
public class JsonResourceFactory extends ResourceFactoryImpl {
	
	@Reference
	private CodecModelInfo modelInfo;
	
	@Reference(target="(type=json)")
	private ObjectMapperConfigurator objMapperConfigurator;
	
	@Reference(target="(type=json)")
	private CodecModuleConfigurator codecModuleConfigurator;
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Resource createResource(URI uri) {		
		return new CodecJsonResource(uri, modelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder());
	}

}
