/*
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
 *      Mark Hoffmann - initial API and implementation
 */
package org.eclipse.fennec.codec.geojson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.resource.CodecResource;
import org.eclipse.fennec.codec.v2.value.CodecValueRegistry;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.geojson.GeoJsonPackage;

import tools.jackson.databind.json.JsonMapper;

/**
 * EMF Resource implementation for GeoJSON format.
 * <p>
 * This resource is pre-configured for GeoJSON serialization/deserialization:
 * <ul>
 *   <li>Type key: "type" (GeoJSON standard)</li>
 *   <li>Type strategy: NAME (simple class names like "Point", "Feature")</li>
 *   <li>Uses ExtendedMetaData names for "coordinates" mapping</li>
 *   <li>Force serializes volatile "data" and "bbox" attributes</li>
 *   <li>No ID serialization (GeoJSON uses "id" as a regular property)</li>
 * </ul>
 * </p>
 *
 * @author Mark Hoffmann
 * @since 2025
 * @see <a href="https://geojson.org/">GeoJSON Specification</a>
 */
public class GeoJsonResourceImpl extends CodecResource {

	/**
	 * Default GeoJSON codec configuration.
	 * <p>
	 * Settings aligned with GeoJSON specification:
	 * <ul>
	 *   <li>typeKey="type" - GeoJSON uses "type" for geometry/feature type</li>
	 *   <li>typeStrategy=NAME - Simple names like "Point", "LineString", "Feature"</li>
	 *   <li>useNamesFromExtendedMetaData=true - Maps "coordinates" correctly</li>
	 *   <li>forceSerialize("data", "bbox") - Volatile attributes that must be serialized</li>
	 *   <li>useId=false - GeoJSON Feature.id is a regular property, not EMF ID</li>
	 * </ul>
	 * </p>
	 */
	public static final CodecConfiguration GEOJSON_CONFIGURATION = CodecConfiguration.builder()
			.typeKey("type")
			.typeStrategy(TypeStrategy.NAME)
			.useNamesFromExtendedMetaData(true)
			.forceSerialize("data", "bbox")
			.useId(false)
			.serializeType(true)
			.build();

	/**
	 * Creates a GeoJSON resource with the given URI and metadata service.
	 *
	 * @param uri the resource URI
	 * @param metadataService the metadata service for EPackage metadata
	 */
	public GeoJsonResourceImpl(URI uri, MetadataService metadataService) {
		this(uri, metadataService, null, null);
	}

	/**
	 * Creates a GeoJSON resource with custom value registry.
	 *
	 * @param uri the resource URI
	 * @param metadataService the metadata service for EPackage metadata
	 * @param valueRegistry custom value readers/writers (may be null)
	 */
	public GeoJsonResourceImpl(URI uri, MetadataService metadataService, CodecValueRegistry valueRegistry) {
		this(uri, metadataService, valueRegistry, null);
	}

	/**
	 * Creates a GeoJSON resource with full customization.
	 *
	 * @param uri the resource URI
	 * @param metadataService the metadata service for EPackage metadata
	 * @param valueRegistry custom value readers/writers (may be null)
	 * @param mapperBuilder pre-configured Jackson mapper builder (may be null)
	 */
	public GeoJsonResourceImpl(URI uri, MetadataService metadataService,
			CodecValueRegistry valueRegistry, JsonMapper.Builder mapperBuilder) {
		super(uri, metadataService, GEOJSON_CONFIGURATION, valueRegistry, mapperBuilder);
	}

	/**
	 * Loads GeoJSON content, automatically setting the GeoJSON package as context schema.
	 */
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		Map<Object, Object> effectiveOptions = createEffectiveOptions(options);

		// Set GeoJSON package as the context schema for type resolution
		if (!effectiveOptions.containsKey(CODEC_ROOT_SCHEMA)) {
			effectiveOptions.put(CODEC_ROOT_SCHEMA, GeoJsonPackage.eNS_URI);
		}

		super.doLoad(inputStream, effectiveOptions);
	}

	/**
	 * Saves content as GeoJSON.
	 */
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		Map<Object, Object> effectiveOptions = createEffectiveOptions(options);
		super.doSave(outputStream, effectiveOptions);
	}

	/**
	 * Creates effective options by merging provided options with GeoJSON defaults.
	 *
	 * @param options user-provided options (may be null)
	 * @return merged options map
	 */
	private Map<Object, Object> createEffectiveOptions(Map<?, ?> options) {
		Map<Object, Object> effectiveOptions = new HashMap<>();

		// Add user options (they override defaults)
		if (options != null) {
			options.forEach(effectiveOptions::put);
		}

		return effectiveOptions;
	}
}
