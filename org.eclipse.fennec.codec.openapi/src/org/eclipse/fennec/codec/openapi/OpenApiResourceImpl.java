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
import org.eclipse.fennec.codec.api.value.CodecValueRegistry;
import org.eclipse.fennec.codec.jsonschema.v2.value.EPackageValueReader;
import org.eclipse.fennec.codec.jsonschema.v2.value.EPackageValueWriter;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.resource.CodecResource;
import org.eclipse.fennec.model.metadata.api.MetadataService;

/**
 * EMF Resource implementation for OpenAPI documents.
 * <p>
 * Pre-configured to handle {@code components/schemas} as JSON Schema,
 * converting to/from EPackage during load/save.
 * </p>
 *
 * @author Data In Motion
 * @since 2025
 */
public class OpenApiResourceImpl extends CodecResource {

	private static final String SCHEMAS_FEATURE = "schemas";

	/**
	 * Creates an OpenAPI resource with the given URI and metadata service.
	 *
	 * @param uri the resource URI
	 * @param metadataService the metadata service for codec configuration
	 */
	public OpenApiResourceImpl(URI uri, MetadataService metadataService) {
		super(uri, metadataService, createConfiguration(), createValueRegistry(), null);
	}

	private static CodecConfiguration createConfiguration() {
		return CodecConfiguration.builder()
				.serializeType(false)  // OpenAPI doesn't use _type for root
				.build();
	}

	private static CodecValueRegistry createValueRegistry() {
		CodecValueRegistry registry = new CodecValueRegistry();

		// Register handlers for Components.schemas (EPackage ↔ JSON Schema)
		// The name must match the feature name for the codec to find them
		// EPackageValueReader auto-detects schema structure, no need for schemaFeature param
		registry.registerReader(SCHEMAS_FEATURE, new EPackageValueReader());
		registry.registerWriter(SCHEMAS_FEATURE, new EPackageValueWriter(SCHEMAS_FEATURE, true));

		return registry;
	}
}
