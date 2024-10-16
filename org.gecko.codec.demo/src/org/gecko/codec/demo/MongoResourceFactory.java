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
import org.gecko.codec.demo.resource.MongoResource;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.gecko.mongo.osgi.MongoDatabaseProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(name= "MongoRF", service = Resource.Factory.class, property = { EMFNamespaces.EMF_CONFIGURATOR_NAME + "=myMongo",
		EMFNamespaces.EMF_MODEL_PROTOCOL + "=mongodb" })
public class MongoResourceFactory extends ResourceFactoryImpl {
	
	@Reference
	MongoDatabaseProvider provider;
	
	@Reference
	private CodecModelInfo modelInfo;
	
	@Reference(target="(type=mongo)")
	private ObjectMapperConfigurator objMapperConfigurator;
	
	@Reference(target="(type=mongo)")
	private CodecModuleConfigurator codecModuleConfigurator;
	
	
	@Override
	public Resource createResource(URI uri) {
		return new MongoResource(uri, modelInfo, codecModuleConfigurator.getCodecModuleBuilder(), objMapperConfigurator.getObjMapperBuilder(), provider);
	}

}
