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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.context.ContextHelper;
import org.eclipse.fennec.codec.v2.deser.DeserializationState.UnresolvedReference;
import org.eclipse.fennec.codec.v2.module.CodecModule;
import org.eclipse.fennec.codec.v2.util.CodecResourceHelper;
import org.eclipse.fennec.model.metadata.PackageMetadata;
import org.eclipse.fennec.model.metadata.api.MetadataService;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * EMF Resource implementation for codec.v2 serialization.
 * <p>
 * This resource uses {@link MetadataService} to access pre-computed metadata
 * and codec aspects for efficient serialization/deserialization of EMF objects.
 * </p>
 *
 * <h2>Configuration Hierarchy</h2>
 * Settings are resolved in this order (highest priority first):
 * <ol>
 *   <li>Load/Save options passed to save()/load()</li>
 *   <li>ResourceFactory default options</li>
 *   <li>CodecConfiguration (codec module config)</li>
 *   <li>EAnnotations via MetadataService aspects</li>
 *   <li>Built-in defaults</li>
 * </ol>
 *
 * @author Mark Hoffmann
 * @since 2025-12-11
 */
public class CodecResource extends ResourceImpl {

    private static final Logger LOGGER = Logger.getLogger(CodecResource.class.getName());

    /** Option key for specifying the root EClass during deserialization */
    public static final String CODEC_ROOT_OBJECT = "CODEC_ROOT_OBJECT";

    private final MetadataService metadataService;
    private final CodecConfiguration configuration;
    private final JsonMapper.Builder mapperBuilder;
    private final CodecResourceHelper helper;

    private ObjectMapper mapper;

    /**
     * Creates a new CodecResource.
     *
     * @param uri the resource URI (must not be null)
     * @param metadataService the metadata service (must not be null)
     * @param configuration the codec configuration (null for defaults)
     * @param mapperBuilder pre-configured mapper builder (null for default JsonMapper)
     */
    public CodecResource(URI uri, MetadataService metadataService, CodecConfiguration configuration,
            JsonMapper.Builder mapperBuilder) {
        super(uri);
        this.metadataService = requireNonNull(metadataService, "metadataService must not be null");
        this.configuration = isNull(configuration) ? CodecConfiguration.defaults() : configuration;
        this.mapperBuilder = mapperBuilder;
        this.helper = new CodecResourceHelper(metadataService);
    }

    /**
     * Gets the ObjectMapper configured for this resource.
     * <p>
     * The mapper is created during save/load operations and configured
     * based on the configuration hierarchy.
     * </p>
     *
     * @return the configured ObjectMapper, or null if not yet created
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Gets the metadata service.
     *
     * @return the MetadataService
     */
    public MetadataService getMetadataService() {
        return metadataService;
    }

    /**
     * Gets the codec configuration.
     *
     * @return the CodecConfiguration
     */
    public CodecConfiguration getConfiguration() {
        return configuration;
    }

    // ========================================================================
    // Save Implementation
    // ========================================================================

    @Override
    protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
        if (getContents().isEmpty()) {
            LOGGER.warning(() -> String.format("No content to save for resource %s", getURI()));
            return;
        }

        EObject rootObject = getContents().get(0);
        EClass eClass = rootObject.eClass();
        EPackage ePackage = eClass.getEPackage();

        // Ensure package is registered with MetadataService
        if (isNull(ensurePackageRegistered(ePackage))) {
            throw new IOException("Failed to register package metadata for " + ePackage.getNsURI());
        }

        // Merge options with configuration hierarchy
        Map<String, Object> effectiveOptions = (Map<String, Object>) mergeOptions(options);

        // Create configured ObjectMapper with CodecModule
        mapper = createObjectMapper(effectiveOptions);

        // Serialize the root object
        if (getContents().size() == 1) {
            // Single root object
            mapper.writeValue(outputStream, rootObject);
        } else {
            // Multiple root objects - serialize as array
            mapper.writeValue(outputStream, getContents().toArray(new EObject[0]));
        }

        LOGGER.fine(() -> String.format("Saved %s to %s", eClass.getName(), getURI()));
    }

    // ========================================================================
    // Load Implementation
    // ========================================================================

    @Override
    protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
        Map<?, ?> effectiveOptions = isNull(options) ? Collections.emptyMap() : options;

        // Get root EClass hint from options (required when no type info in content)
        EClass rootEClassHint = helper.resolveRootEClass(effectiveOptions);

        // If hint is provided, ensure its package is registered
        if (nonNull(rootEClassHint)) {
            EPackage ePackage = rootEClassHint.getEPackage();
            if (isNull(ensurePackageRegistered(ePackage))) {
                throw new IOException("Failed to register package metadata for " + ePackage.getNsURI());
            }
        }

        // Merge options with configuration hierarchy
        Map<String, Object> mergedOptions = (Map<String, Object>) mergeOptions(effectiveOptions);

        // Create configured ObjectMapper with CodecModule
        mapper = createObjectMapper(mergedOptions);

        // Create shared list to collect unresolved references during deserialization
        List<UnresolvedReference> unresolvedReferences = new ArrayList<>();

        // Deserialize - the _type field in JSON provides type information
        // Disable FAIL_ON_TRAILING_TOKENS since our deserializer leaves parser at END_OBJECT
        var reader = mapper.readerFor(EObject.class)
                .withAttribute(ContextHelper.UNRESOLVED_REFERENCES, unresolvedReferences)
                .without(tools.jackson.databind.DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

        // Set expected type hint for deserializer if provided
        // The hint is resolved from CODEC_ROOT_OBJECT option (EClass or URI string)
        if (nonNull(rootEClassHint)) {
            reader = reader.withAttribute(ContextHelper.EXPECTED_TYPE, rootEClassHint);
        }

        // Create parser to peek at first token and determine if array or object
        try (tools.jackson.core.JsonParser parser = mapper.createParser(inputStream)) {
            tools.jackson.core.JsonToken firstToken = parser.nextToken();

            if (firstToken == tools.jackson.core.JsonToken.START_ARRAY) {
                // Multiple root objects - read each element from the array
                while (parser.nextToken() != tools.jackson.core.JsonToken.END_ARRAY) {
                    EObject result = reader.readValue(parser);
                    if (nonNull(result)) {
                        getContents().add(result);
                    }
                }
            } else if (firstToken == tools.jackson.core.JsonToken.START_OBJECT) {
                // Single root object
                EObject result = reader.readValue(parser);
                if (nonNull(result)) {
                    getContents().add(result);
                }
            } else if (firstToken != null) {
                LOGGER.warning(() -> String.format("Unexpected token at root: %s", firstToken));
            }
        }

        // Resolve unresolved references after deserialization
        if (!unresolvedReferences.isEmpty()) {
            resolveReferences(unresolvedReferences);
        }

        LOGGER.fine(() -> String.format("Loaded %d objects from %s",
            getContents().size(), getURI()));
    }

    // ========================================================================
    // Helper Methods
    // ========================================================================

    /**
     * Ensures the package is registered with the MetadataService.
     *
     * @param ePackage the package to register
     * @return the PackageMetadata, or null if registration failed
     */
    private PackageMetadata ensurePackageRegistered(EPackage ePackage) {
        PackageMetadata metadata = metadataService.getPackageMetadata(ePackage.getNsURI());
        if (isNull(metadata)) {
            metadata = metadataService.registerPackage(ePackage);
        }
        return metadata;
    }

    /**
     * Merges runtime options with configuration defaults.
     * <p>
     * Options override configuration settings.
     * </p>
     *
     * @param options the runtime options
     * @return merged options map
     */
    private Map<String, Object> mergeOptions(Map<?, ?> options) {
        Map<String, Object> merged = new HashMap<>();

        // Add configuration defaults (lowest priority)
        // Configuration is already applied via CodecModule

        // Add runtime options (highest priority)
        if (nonNull(options)) {
            options.forEach((key, value) -> {
                if (key instanceof String) {
                    merged.put((String) key, value);
                }
            });
        }

        return merged;
    }

    /**
     * Creates an ObjectMapper configured with the CodecModule.
     *
     * @param options the effective options for this operation
     * @return configured ObjectMapper
     */
    private ObjectMapper createObjectMapper(Map<String, Object> options) {
        // Build the CodecModule with configuration and metadata service
        CodecModule codecModule = CodecModule.builder()
                .configuration(configuration)
                .metadataService(metadataService)
                .build();

        // Create the mapper with the module
        JsonMapper.Builder builder = isNull(mapperBuilder) ? JsonMapper.builder() : mapperBuilder;
        return builder
                .addModule(codecModule)
                .build();
    }

    /**
     * Resolves unresolved non-containment references after deserialization.
     * <p>
     * References can be resolved within this resource (using URI fragments)
     * or to external resources (using full URIs).
     * </p>
     *
     * @param unresolvedReferences the list of unresolved references to resolve
     */
    @SuppressWarnings("unchecked")
    private void resolveReferences(List<UnresolvedReference> unresolvedReferences) {
        for (UnresolvedReference unresolved : unresolvedReferences) {
            EObject target = resolveReference(unresolved.getTargetUri());
            if (nonNull(target)) {
                EObject source = unresolved.getSource();
                org.eclipse.emf.ecore.EReference reference = unresolved.getReference();

                if (unresolved.isMultiValued()) {
                    // Multi-valued reference - add at specific index
                    List<EObject> list = (List<EObject>) source.eGet(reference);
                    int index = unresolved.getIndex();
                    if (index < list.size()) {
                        list.set(index, target);
                    } else {
                        list.add(target);
                    }
                } else {
                    // Single-valued reference
                    source.eSet(reference, target);
                }
            } else {
                LOGGER.warning(() -> String.format(
                        "Could not resolve reference %s -> %s",
                        unresolved.getReference().getName(),
                        unresolved.getTargetUri()));
            }
        }
    }

    /**
     * Resolves a reference URI to an EObject.
     * <p>
     * Supports:
     * <ul>
     *   <li>Fragment paths (e.g., "//@employees.0") - resolved within this resource</li>
     *   <li>Fragment-only URIs (e.g., "#//@employees.0") - resolved within this resource</li>
     *   <li>Full URIs - resolved via resource set</li>
     * </ul>
     * </p>
     *
     * @param uri the reference URI
     * @return the resolved EObject, or null if not found
     */
    private EObject resolveReference(String uri) {
        if (isNull(uri) || uri.isEmpty()) {
            return null;
        }

        try {
            if (uri.startsWith("#")) {
                // Fragment-only URI - resolve within this resource
                String fragment = uri.substring(1);
                return getEObject(fragment);
            } else if (uri.startsWith("//")) {
                // Fragment path (EMF-style) - resolve within this resource
                return getEObject(uri);
            } else if (uri.contains("#")) {
                // Full URI with fragment - resolve via resource set or locally
                URI emfUri = URI.createURI(uri);
                if (nonNull(getResourceSet())) {
                    return getResourceSet().getEObject(emfUri, true);
                } else {
                    // No resource set - try to resolve locally
                    String fragment = emfUri.fragment();
                    if (nonNull(fragment)) {
                        return getEObject(fragment);
                    }
                }
            } else {
                // Treat as fragment path
                return getEObject(uri);
            }
        } catch (Exception e) {
            LOGGER.warning(() -> String.format(
                    "Error resolving reference URI '%s': %s", uri, e.getMessage()));
        }

        return null;
    }
}
