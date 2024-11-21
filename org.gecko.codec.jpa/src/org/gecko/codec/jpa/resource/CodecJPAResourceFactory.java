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
package org.gecko.codec.jpa.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.gecko.codec.configurator.CodecModuleConfigurator;
import org.gecko.codec.configurator.ObjectMapperConfigurator;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author ilenia
 * @since Nov 21, 2024
 */
@Component(name= "JPARF", service = Resource.Factory.class, property = { 
		EMFNamespaces.EMF_CONFIGURATOR_NAME + "=myJPA",
		EMFNamespaces.EMF_MODEL_FILE_EXT + "=jpa" })
public class CodecJPAResourceFactory extends ResourceFactoryImpl {
	
	@Reference(target = "(" + EntityManagerFactoryBuilder.JPA_UNIT_NAME + "=Codec)")
	EntityManagerFactory emf;
	
	@Reference
	private CodecModelInfo modelInfo;
	
	@Reference(target="(type=jpa)")
	private ObjectMapperConfigurator objMapperConfigurator;
	
	@Reference(target="(type=jpa)")
	private CodecModuleConfigurator codecModuleConfigurator;
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Resource createResource(URI uri) {
		return new CodecJPAResource(uri, modelInfo, codecModuleConfigurator.getCodecModuleBuilder(), 
				objMapperConfigurator.getObjMapperBuilderFactory(), emf);

	}

}
