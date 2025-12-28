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
package org.eclipse.fennec.codec.metadata.type;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;

/**
 * Registry for type discriminator mappings within a single mapId scope.
 * <p>
 * This registry maintains a bidirectional mapping between discriminator values
 * and EClasses for a specific mapId (namespace). The MAPPED TypeStrategy uses
 * discriminator values to determine the concrete type during deserialization.
 * </p>
 * <p>
 * Each registry is scoped to a single mapId. Use {@link TypeDiscriminatorService}
 * to manage multiple registries across different mapIds.
 * </p>
 *
 * @see TypeDiscriminatorService
 * @author Mark Hoffmann
 * @since 2025-12-17
 */
public class TypeDiscriminatorRegistry {

    private static final Logger LOGGER = Logger.getLogger(TypeDiscriminatorRegistry.class.getName());

    private final String mapId;
    private final Map<String, EClass> valueToClass = new ConcurrentHashMap<>();
    private final Map<EClass, String> classToValue = new ConcurrentHashMap<>();

    /**
     * Creates a new TypeDiscriminatorRegistry for the given mapId.
     *
     * @param mapId the namespace identifier for this registry
     */
    public TypeDiscriminatorRegistry(String mapId) {
        this.mapId = Objects.requireNonNull(mapId, "mapId must not be null");
    }

    /**
     * Returns the mapId (namespace) of this registry.
     */
    public String getMapId() {
        return mapId;
    }

    /**
     * Registers a discriminator value to EClass mapping.
     *
     * @param discriminatorValue the discriminator value (e.g., "Dragino_LSE01")
     * @param eClass the EClass that corresponds to this discriminator
     */
    public void register(String discriminatorValue, EClass eClass) {
        Objects.requireNonNull(discriminatorValue, "discriminatorValue must not be null");
        Objects.requireNonNull(eClass, "eClass must not be null");

        EClass existing = valueToClass.put(discriminatorValue, eClass);
        if (existing != null && existing != eClass) {
            LOGGER.warning("[" + mapId + "] Discriminator '" + discriminatorValue
                    + "' remapped from " + existing.getName() + " to " + eClass.getName());
        }

        classToValue.put(eClass, discriminatorValue);
        LOGGER.fine("[" + mapId + "] Registered '" + discriminatorValue + "' -> " + eClass.getName());
    }

    /**
     * Unregisters a discriminator value mapping.
     *
     * @param discriminatorValue the discriminator value to remove
     * @return the previously mapped EClass, or null if none
     */
    public EClass unregister(String discriminatorValue) {
        if (discriminatorValue == null) {
            return null;
        }
        EClass removed = valueToClass.remove(discriminatorValue);
        if (removed != null) {
            classToValue.remove(removed);
        }
        return removed;
    }

    /**
     * Gets the EClass for a discriminator value.
     *
     * @param discriminatorValue the discriminator value
     * @return the corresponding EClass, or null if not found
     */
    public EClass getEClass(String discriminatorValue) {
        if (discriminatorValue == null) {
            return null;
        }
        return valueToClass.get(discriminatorValue);
    }

    /**
     * Gets the discriminator value for an EClass.
     *
     * @param eClass the EClass
     * @return the corresponding discriminator value, or null if not registered
     */
    public String getDiscriminatorValue(EClass eClass) {
        if (eClass == null) {
            return null;
        }
        return classToValue.get(eClass);
    }

    /**
     * Checks if a discriminator value is registered.
     *
     * @param discriminatorValue the discriminator value
     * @return true if the value is registered
     */
    public boolean hasMapping(String discriminatorValue) {
        return discriminatorValue != null && valueToClass.containsKey(discriminatorValue);
    }

    /**
     * Checks if an EClass has a discriminator value registered.
     *
     * @param eClass the EClass
     * @return true if the class has a discriminator
     */
    public boolean hasDiscriminator(EClass eClass) {
        return eClass != null && classToValue.containsKey(eClass);
    }

    /**
     * Returns the number of registered mappings.
     */
    public int size() {
        return valueToClass.size();
    }

    /**
     * Clears all registered mappings.
     */
    public void clear() {
        valueToClass.clear();
        classToValue.clear();
    }

    /**
     * Returns an unmodifiable view of all discriminator values.
     */
    public Set<String> getDiscriminatorValues() {
        return Collections.unmodifiableSet(valueToClass.keySet());
    }

    @Override
    public String toString() {
        return "TypeDiscriminatorRegistry[mapId=" + mapId + ", mappings=" + valueToClass.size() + "]";
    }
}
