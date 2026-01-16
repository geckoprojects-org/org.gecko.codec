/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
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
package org.eclipse.fennec.codec.openapi;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.fennec.codec.v2.util.MetadataServiceFactory;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.eclipse.fennec.openapi.model.OpenApiPackage;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * Resource factory for OpenAPI documents.
 * <p>
 * Registered as OSGi DS component for file extensions: json, yaml, openapi
 * </p>
 *
 * @author Data In Motion
 * @since 2025
 */
@Component(
		name = "OpenApiResourceFactory",
		service = Resource.Factory.class,
		scope = ServiceScope.SINGLETON,
		property = {
				"emf.resource.name=openapi",
				"emf.model.fileExtension=openapi"
		}
)
public class OpenApiResourceFactoryImpl extends ResourceFactoryImpl {

	private final MetadataService metadataService;

	/**
	 * OSGi DS constructor with injected MetadataService.
	 *
	 * @param metadataService the metadata service
	 */
	@Activate
	public OpenApiResourceFactoryImpl(@Reference MetadataService metadataService) {
		this.metadataService = metadataService;
		// Ensure OpenAPI package is registered
		metadataService.registerPackage(OpenApiPackage.eINSTANCE);
	}

	/**
	 * Non-OSGi constructor for standalone usage.
	 */
	public OpenApiResourceFactoryImpl() {
		this.metadataService = MetadataServiceFactory.create();
		metadataService.registerPackage(OpenApiPackage.eINSTANCE);
	}

	@Override
	public Resource createResource(URI uri) {
		return new OpenApiResourceImpl(uri, metadataService);
	}
}
