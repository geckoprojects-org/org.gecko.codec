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
package org.eclipse.fennec.codec.v2.resource;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.model.metadata.api.MetadataService;

import tools.jackson.databind.json.JsonMapper;

/**
 * Factory for creating {@link CodecResource} instances.
 * <p>
 * This factory integrates with the {@link MetadataService} for model metadata
 * and supports configurable defaults via {@link CodecConfiguration}.
 * </p>
 *
 * <h2>Usage</h2>
 * <pre>{@code
 * // Create factory with MetadataService
 * CodecResourceFactory factory = new CodecResourceFactory(metadataService);
 *
 * // Or with custom configuration
 * CodecConfiguration config = CodecConfiguration.builder()
 *     .serializeType(true)
 *     .idKey("_id")
 *     .build();
 * CodecResourceFactory factory = new CodecResourceFactory(metadataService, config);
 *
 * // Register with ResourceSet
 * resourceSet.getResourceFactoryRegistry()
 *     .getExtensionToFactoryMap()
 *     .put("json", factory);
 * }</pre>
 *
 * @author Mark Hoffmann
 * @since 2025-12-11
 */
public class CodecResourceFactory extends ResourceFactoryImpl {

    private final MetadataService metadataService;
    private final CodecConfiguration configuration;
    private final JsonMapper.Builder mapperBuilder;

    private Map<Object, Object> defaultSaveOptions = Collections.emptyMap();
    private Map<Object, Object> defaultLoadOptions = Collections.emptyMap();

    /**
     * Creates a new CodecResourceFactory with default configuration.
     *
     * @param metadataService the metadata service for model metadata lookup
     */
    public CodecResourceFactory(MetadataService metadataService) {
        this(metadataService, CodecConfiguration.defaults());
    }

    /**
     * Creates a new CodecResourceFactory with custom configuration.
     *
     * @param metadataService the metadata service for model metadata lookup
     * @param configuration the codec configuration
     */
    public CodecResourceFactory(MetadataService metadataService, CodecConfiguration configuration) {
        this(metadataService, configuration, null);
    }

    /**
     * Creates a new CodecResourceFactory with custom configuration and mapper builder.
     *
     * @param metadataService the metadata service for model metadata lookup
     * @param configuration the codec configuration
     * @param mapperBuilder optional pre-configured JsonMapper builder (null for default)
     */
    public CodecResourceFactory(MetadataService metadataService, CodecConfiguration configuration,
            JsonMapper.Builder mapperBuilder) {
        this.metadataService = metadataService;
        this.configuration = configuration != null ? configuration : CodecConfiguration.defaults();
        this.mapperBuilder = mapperBuilder;
    }

    @Override
    public Resource createResource(URI uri) {
        return new CodecResource(uri, metadataService, configuration, mapperBuilder);
    }

    /**
     * Gets the metadata service used by this factory.
     *
     * @return the MetadataService
     */
    public MetadataService getMetadataService() {
        return metadataService;
    }

    /**
     * Gets the codec configuration used by this factory.
     *
     * @return the CodecConfiguration
     */
    public CodecConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Gets the default save options.
     *
     * @return default save options map
     */
    public Map<Object, Object> getDefaultSaveOptions() {
        return defaultSaveOptions;
    }

    /**
     * Sets the default save options applied to all resources created by this factory.
     *
     * @param defaultSaveOptions the default options
     */
    public void setDefaultSaveOptions(Map<Object, Object> defaultSaveOptions) {
        this.defaultSaveOptions = defaultSaveOptions != null ? defaultSaveOptions : Collections.emptyMap();
    }

    /**
     * Gets the default load options.
     *
     * @return default load options map
     */
    public Map<Object, Object> getDefaultLoadOptions() {
        return defaultLoadOptions;
    }

    /**
     * Sets the default load options applied to all resources created by this factory.
     *
     * @param defaultLoadOptions the default options
     */
    public void setDefaultLoadOptions(Map<Object, Object> defaultLoadOptions) {
        this.defaultLoadOptions = defaultLoadOptions != null ? defaultLoadOptions : Collections.emptyMap();
    }
}
