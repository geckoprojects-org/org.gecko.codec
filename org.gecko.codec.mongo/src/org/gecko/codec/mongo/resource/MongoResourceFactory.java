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
package org.gecko.codec.mongo.resource;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.gecko.codec.jackson.module.CodecModule;
import org.gecko.codec.mongo.TestFactory;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.gecko.mongo.osgi.MongoDatabaseProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Resource.Factory.class, property = { EMFNamespaces.EMF_CONFIGURATOR_NAME + "=myMongo",
		EMFNamespaces.EMF_MODEL_PROTOCOL + "=mongodb" })
public class MongoResourceFactory extends ResourceFactoryImpl {
	
	@Reference
	MongoDatabaseProvider provider;
	
	@Override
	public Resource createResource(URI uri) {
		ObjectMapper mapper = CodecModule.setupDefaultMapper(new TestFactory(), Collections.emptyMap());
		return new MongoResource(uri, provider, mapper);
	}
	
	
}
