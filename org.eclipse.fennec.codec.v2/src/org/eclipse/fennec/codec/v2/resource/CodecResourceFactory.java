/**
 * Copyright (c) 2012 - 2026 Data In Motion and others.
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
  * @deprecated Migrated to {@link org.eclipse.fennec.codec.resource.CodecResourceFactory}.
 */
@Deprecated
public class CodecResourceFactory extends ResourceFactoryImpl {

    private MetadataService metadataService;
    private CodecConfiguration configuration = CodecConfiguration.defaults();
    private JsonMapper.Builder mapperBuilder;

    private Map<Object, Object> defaultSaveOptions = Collections.emptyMap();
    private Map<Object, Object> defaultLoadOptions = Collections.emptyMap();

    /**
     * Creates a new CodecResourceFactory for dependency injection.
     * <p>
     * When using this constructor, you must call {@link #setMetadataService(MetadataService)}
     * before creating resources. The configuration defaults to {@link CodecConfiguration#defaults()}.
     * </p>
     * <p>
     * This constructor is intended for DI frameworks (Spring, CDI, OSGi DS) that require
     * a parameterless constructor and setter-based injection.
     * </p>
     *
     * @see #setMetadataService(MetadataService)
     * @see #setConfiguration(CodecConfiguration)
     */
    public CodecResourceFactory() {
        // DI-friendly constructor
    }

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
        if (metadataService == null) {
            throw new IllegalStateException(
                "MetadataService not set. Call setMetadataService() before creating resources, " +
                "or use a constructor that accepts MetadataService.");
        }
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
     * Sets the metadata service for model metadata lookup.
     * <p>
     * This setter is intended for dependency injection frameworks (Spring, CDI, OSGi DS).
     * In OSGi, use {@code @Reference} annotation on this method.
     * </p>
     *
     * @param metadataService the metadata service (must not be null)
     * @throws IllegalArgumentException if metadataService is null
     */
    public void setMetadataService(MetadataService metadataService) {
        if (metadataService == null) {
            throw new IllegalArgumentException("MetadataService must not be null");
        }
        this.metadataService = metadataService;
    }

    /**
     * Sets the codec configuration.
     * <p>
     * This setter is intended for dependency injection frameworks. If not called,
     * the factory uses {@link CodecConfiguration#defaults()}.
     * </p>
     *
     * @param configuration the codec configuration (null resets to defaults)
     */
    public void setConfiguration(CodecConfiguration configuration) {
        this.configuration = configuration != null ? configuration : CodecConfiguration.defaults();
    }

    /**
     * Sets the JsonMapper builder for customizing Jackson configuration.
     * <p>
     * This setter is intended for dependency injection frameworks.
     * </p>
     *
     * @param mapperBuilder the mapper builder (null for default)
     */
    public void setMapperBuilder(JsonMapper.Builder mapperBuilder) {
        this.mapperBuilder = mapperBuilder;
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
