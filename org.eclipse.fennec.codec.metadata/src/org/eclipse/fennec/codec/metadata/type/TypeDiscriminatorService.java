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
package org.eclipse.fennec.codec.metadata.type;

import static org.eclipse.fennec.codec.metadata.provider.CodecAnnotationConstants.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.model.metadata.ClassMetadata;
import org.eclipse.fennec.model.metadata.MetadataRegistry;
import org.eclipse.fennec.model.metadata.PackageMetadata;
import org.eclipse.fennec.model.metadata.api.MetadataService;

/**
 * Service for managing type discriminator registries across multiple mapIds.
 * <p>
 * This service acts as the parent container for all {@link TypeDiscriminatorRegistry}
 * instances. Each registry is scoped to a specific mapId (namespace), allowing
 * the same discriminator value to map to different EClasses in different contexts.
 * </p>
 *
 * <h3>MapId Concept</h3>
 * <p>
 * A mapId identifies a specific type discrimination context. Common patterns:
 * </p>
 * <ul>
 *   <li>{@code "iot-sensors"} - IoT sensor devices</li>
 *   <li>{@code "lorawan-devices"} - LoRaWAN devices</li>
 *   <li>Package nsURI - Using the EPackage namespace as mapId</li>
 * </ul>
 *
 * <h3>Population from MetadataService</h3>
 * <p>
 * The service can be populated automatically from a {@link MetadataService} by
 * scanning all registered packages for codec annotations with {@code typeMapId}
 * and discriminator values.
 * </p>
 *
 * <h3>Usage Example</h3>
 * <pre>{@code
 * TypeDiscriminatorService service = TypeDiscriminatorService.fromMetadataService(metadataService);
 *
 * // Resolve type in "iot-sensors" context
 * EClass sensorClass = service.getEClass("iot-sensors", "temp-sensor");
 *
 * // Get discriminator for serialization
 * String discriminator = service.getDiscriminatorValue("iot-sensors", sensorClass);
 * }</pre>
 *
 * @see TypeDiscriminatorRegistry
 * @see ClassCodecAspect#getDiscriminatorValue()
 * @author Mark Hoffmann
 * @since 2025-12-17
 */
public class TypeDiscriminatorService {

    private static final Logger LOGGER = Logger.getLogger(TypeDiscriminatorService.class.getName());

    /** Default mapId used when no specific mapId is provided */
    public static final String DEFAULT_MAP_ID = "default";

    private final Map<String, TypeDiscriminatorRegistry> registries = new ConcurrentHashMap<>();

    /**
     * Creates an empty TypeDiscriminatorService.
     */
    public TypeDiscriminatorService() {
        // Empty service
    }

    /**
     * Creates a TypeDiscriminatorService populated from a MetadataService.
     * <p>
     * This method iterates through all registered metadata and extracts discriminator
     * mappings from {@link ClassCodecAspect} instances.
     * </p>
     *
     * @param metadataService the metadata service to scan
     * @return a new service populated with discriminator mappings
     */
    public static TypeDiscriminatorService fromMetadataService(MetadataService metadataService) {
        Objects.requireNonNull(metadataService, "metadataService must not be null");

        TypeDiscriminatorService service = new TypeDiscriminatorService();

        MetadataRegistry registry = metadataService.getRegistry();
        if (registry != null) {
            for (PackageMetadata pkgMetadata : registry.getPackages()) {
                for (ClassMetadata classMetadata : pkgMetadata.getClasses()) {
                    service.registerFromClassMetadata(classMetadata);
                }
            }
        }

        return service;
    }

    /**
     * Registers discriminator mappings from a ClassMetadata.
     * <p>
     * Extracts the discriminator value and path from the {@link ClassCodecAspect} if present.
     * The discriminator path (e.g., "info.sensorType") is set at the registry level since
     * all classes in the same mapId share the same path.
     * </p>
     * <p>
     * If the concrete class doesn't have a discriminatorPath in its typeConfig, we look
     * at the supertype hierarchy to find it (typically defined on an abstract base class).
     * </p>
     *
     * @param classMetadata the class metadata to process
     */
    public void registerFromClassMetadata(ClassMetadata classMetadata) {
        Objects.requireNonNull(classMetadata, "classMetadata must not be null");

        EClass eClass = classMetadata.getEClass();
        if (eClass == null) {
            return;
        }

        // Extract discriminator from ClassCodecAspect
        classMetadata.getAspects().stream()
                .filter(ClassCodecAspect.class::isInstance)
                .map(ClassCodecAspect.class::cast)
                .findFirst()
                .ifPresent(aspect -> {
                    String discriminator = aspect.getDiscriminatorValue();
                    if (discriminator != null && !discriminator.isEmpty()) {
                        // Determine mapId from EClass annotations
                        String mapId = resolveMapId(aspect, eClass);
                        TypeDiscriminatorRegistry registry = getOrCreateRegistry(mapId);
                        registry.register(discriminator, eClass);

                        // Set discriminator path if present (from typeConfig or inherited)
                        String discriminatorPath = resolveDiscriminatorPath(eClass, aspect, mapId);
                        if (discriminatorPath != null && !discriminatorPath.isEmpty()
                                && registry.getDiscriminatorPath() == null) {
                            registry.setDiscriminatorPath(discriminatorPath);
                        }
                    }
                });
    }

    /**
     * Resolves the discriminator path for an EClass.
     * <p>
     * First checks the class's own typeConfig. If not found, walks up the
     * supertype hierarchy looking for a discriminator path defined on an abstract base.
     * </p>
     *
     * @param eClass the EClass to check
     * @param aspect the current class's aspect (may be null)
     * @param mapId the mapId context to search within
     * @return the discriminator path, or null if not found
     */
    private String resolveDiscriminatorPath(EClass eClass, ClassCodecAspect aspect, String mapId) {
        // First check the current class's typeConfig
        if (aspect != null && aspect.getTypeConfig() != null) {
            String path = aspect.getTypeConfig().getDiscriminatorPath();
            if (path != null && !path.isEmpty()) {
                return path;
            }
        }

        // Walk up the supertype hierarchy looking for discriminator path annotation
        for (EClass superType : eClass.getEAllSuperTypes()) {
            EAnnotation ann = superType.getEAnnotation(CODEC_SOURCE);
            if (ann != null) {
                String annotationMapId = ann.getDetails().get(KEY_TYPE_MAP_ID);
                if (mapId.equals(annotationMapId)) {
                    String path = ann.getDetails().get(KEY_TYPE_DISCRIMINATOR_PATH);
                    if (path != null && !path.isEmpty()) {
                        return path;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Resolves the mapId for a ClassCodecAspect by looking at the EClass annotations.
     * <p>
     * Scans the EClass's codec annotation for the {@code typeMapId} detail key.
     * </p>
     *
     * @param aspect the codec aspect
     * @param eClass the EClass to scan for annotations
     * @return the mapId to use, or DEFAULT_MAP_ID if not found
     */
    private String resolveMapId(ClassCodecAspect aspect, EClass eClass) {
        EAnnotation ann = eClass.getEAnnotation(CODEC_SOURCE);
        if (ann != null) {
            String mapId = ann.getDetails().get(KEY_TYPE_MAP_ID);
            if (mapId != null && !mapId.isEmpty()) {
                return mapId;
            }
        }
        return DEFAULT_MAP_ID;
    }

    /**
     * Gets or creates a registry for the given mapId.
     *
     * @param mapId the namespace identifier
     * @return the registry for this mapId
     */
    public TypeDiscriminatorRegistry getOrCreateRegistry(String mapId) {
        Objects.requireNonNull(mapId, "mapId must not be null");
        return registries.computeIfAbsent(mapId, TypeDiscriminatorRegistry::new);
    }

    /**
     * Gets a registry for the given mapId, or null if not exists.
     *
     * @param mapId the namespace identifier
     * @return the registry, or null if not found
     */
    public TypeDiscriminatorRegistry getRegistry(String mapId) {
        if (mapId == null) {
            return null;
        }
        return registries.get(mapId);
    }

    /**
     * Checks if a registry exists for the given mapId.
     *
     * @param mapId the namespace identifier
     * @return true if a registry exists
     */
    public boolean hasRegistry(String mapId) {
        return mapId != null && registries.containsKey(mapId);
    }

    /**
     * Gets the EClass for a discriminator value in a specific mapId context.
     *
     * @param mapId the namespace identifier
     * @param discriminatorValue the discriminator value
     * @return the corresponding EClass, or null if not found
     */
    public EClass getEClass(String mapId, String discriminatorValue) {
        TypeDiscriminatorRegistry registry = getRegistry(mapId);
        return registry != null ? registry.getEClass(discriminatorValue) : null;
    }

    /**
     * Gets the EClass for a discriminator value, searching all registries.
     * <p>
     * This method searches through all registries and returns the first match.
     * Use {@link #getEClass(String, String)} when the mapId is known.
     * </p>
     *
     * @param discriminatorValue the discriminator value
     * @return the corresponding EClass, or null if not found in any registry
     */
    public EClass getEClassFromAny(String discriminatorValue) {
        if (discriminatorValue == null) {
            return null;
        }
        for (TypeDiscriminatorRegistry registry : registries.values()) {
            EClass eClass = registry.getEClass(discriminatorValue);
            if (eClass != null) {
                return eClass;
            }
        }
        return null;
    }

    /**
     * Gets the discriminator value for an EClass in a specific mapId context.
     *
     * @param mapId the namespace identifier
     * @param eClass the EClass
     * @return the corresponding discriminator value, or null if not found
     */
    public String getDiscriminatorValue(String mapId, EClass eClass) {
        TypeDiscriminatorRegistry registry = getRegistry(mapId);
        return registry != null ? registry.getDiscriminatorValue(eClass) : null;
    }

    /**
     * Gets the discriminator value for an EClass, searching all registries.
     *
     * @param eClass the EClass
     * @return the corresponding discriminator value, or null if not found
     */
    public String getDiscriminatorValueFromAny(EClass eClass) {
        if (eClass == null) {
            return null;
        }
        for (TypeDiscriminatorRegistry registry : registries.values()) {
            String discriminator = registry.getDiscriminatorValue(eClass);
            if (discriminator != null) {
                return discriminator;
            }
        }
        return null;
    }

    /**
     * Gets the discriminator path for a specific mapId.
     *
     * @param mapId the namespace identifier
     * @return the discriminator path, or null if not set
     */
    public String getDiscriminatorPath(String mapId) {
        TypeDiscriminatorRegistry registry = getRegistry(mapId);
        return registry != null ? registry.getDiscriminatorPath() : null;
    }

    /**
     * Gets the first available discriminator path from any registry.
     * <p>
     * This is useful when the mapId is unknown and you need to find
     * the discriminator path to scan the JSON content.
     * </p>
     *
     * @return the first non-null discriminator path, or null if none found
     */
    public String getAnyDiscriminatorPath() {
        for (TypeDiscriminatorRegistry registry : registries.values()) {
            String path = registry.getDiscriminatorPath();
            if (path != null) {
                return path;
            }
        }
        return null;
    }

    /**
     * Finds a registry that has a discriminator path set.
     * <p>
     * Used for hint-free deserialization where we need to find which registry
     * can be used for type resolution.
     * </p>
     *
     * @return the first registry with a discriminator path, or null if none
     */
    public TypeDiscriminatorRegistry findRegistryWithPath() {
        for (TypeDiscriminatorRegistry registry : registries.values()) {
            if (registry.getDiscriminatorPath() != null) {
                return registry;
            }
        }
        return null;
    }

    /**
     * Returns all registered mapIds.
     */
    public Set<String> getMapIds() {
        return Collections.unmodifiableSet(registries.keySet());
    }

    /**
     * Returns all registries.
     */
    public Collection<TypeDiscriminatorRegistry> getRegistries() {
        return Collections.unmodifiableCollection(registries.values());
    }

    /**
     * Returns the total number of mappings across all registries.
     */
    public int getTotalMappings() {
        return registries.values().stream()
                .mapToInt(TypeDiscriminatorRegistry::size)
                .sum();
    }

    /**
     * Unregisters all discriminator mappings for EClasses from a specific package.
     * <p>
     * This method removes all discriminators that were registered for EClasses
     * belonging to the specified EPackage. Used when an EPackage is dynamically
     * unregistered from the MetadataService.
     * </p>
     *
     * @param packageMetadata the package metadata to unregister
     */
    public void unregisterPackage(PackageMetadata packageMetadata) {
        if (packageMetadata == null) {
            return;
        }
        for (ClassMetadata classMetadata : packageMetadata.getClasses()) {
            unregisterClass(classMetadata);
        }
    }

    /**
     * Unregisters discriminator mapping for a specific EClass.
     *
     * @param classMetadata the class metadata to unregister
     */
    public void unregisterClass(ClassMetadata classMetadata) {
        if (classMetadata == null) {
            return;
        }
        EClass eClass = classMetadata.getEClass();
        if (eClass == null) {
            return;
        }

        // Find and remove from all registries that have this EClass
        for (TypeDiscriminatorRegistry registry : registries.values()) {
            String discriminator = registry.getDiscriminatorValue(eClass);
            if (discriminator != null) {
                registry.unregister(discriminator);
                LOGGER.fine("Unregistered discriminator '" + discriminator + "' for " + eClass.getName());
            }
        }
    }

    /**
     * Clears all registries.
     */
    public void clear() {
        registries.clear();
    }

    @Override
    public String toString() {
        return "TypeDiscriminatorService[registries=" + registries.size()
                + ", totalMappings=" + getTotalMappings() + "]";
    }
}
