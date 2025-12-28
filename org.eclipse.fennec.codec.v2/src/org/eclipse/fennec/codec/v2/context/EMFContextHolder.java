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
package org.eclipse.fennec.codec.v2.context;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.model.metadata.api.MetadataService;

/**
 * Holds EMF state during serialization/deserialization operations.
 * <p>
 * This class stores the current EObject, feature, resource, and metadata service
 * that are needed by serializers and deserializers to properly process EMF objects.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#1811-emf-codec-context">Spec 18.11: EMF Codec Context</a>
 */
public class EMFContextHolder {

    private EStructuralFeature currentFeature;
    private EObject currentEObject;
    private Resource resource;
    private MetadataService metadataService;
    private EClass currentTypeHint;

    /**
     * Creates a new context holder.
     */
    public EMFContextHolder() {
    }

    /**
     * Creates a new context holder with the specified metadata service.
     *
     * @param metadataService the metadata service
     */
    public EMFContextHolder(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    /**
     * Returns the current EObject being processed.
     *
     * @return the current EObject, or null if none
     */
    public EObject getCurrentEObject() {
        return currentEObject;
    }

    /**
     * Sets the current EObject being processed.
     *
     * @param eObject the current EObject
     */
    public void setCurrentEObject(EObject eObject) {
        this.currentEObject = eObject;
    }

    /**
     * Returns the current EStructuralFeature being processed.
     *
     * @return the current feature, or null if none
     */
    public EStructuralFeature getCurrentFeature() {
        return currentFeature;
    }

    /**
     * Sets the current EStructuralFeature being processed.
     *
     * @param feature the current feature
     */
    public void setCurrentFeature(EStructuralFeature feature) {
        this.currentFeature = feature;
    }

    /**
     * Resets the current feature to null.
     */
    public void resetFeature() {
        this.currentFeature = null;
    }

    /**
     * Returns the EMF Resource being processed.
     *
     * @return the resource, or null if none
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Sets the EMF Resource being processed.
     *
     * @param resource the resource
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    /**
     * Returns the MetadataService for aspect lookups.
     *
     * @return the metadata service, or null if none
     */
    public MetadataService getMetadataService() {
        return metadataService;
    }

    /**
     * Sets the MetadataService for aspect lookups.
     *
     * @param metadataService the metadata service
     */
    public void setMetadataService(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    /**
     * Returns the current type hint for deserialization.
     * <p>
     * The type hint is typically set from CODEC_ROOT_OBJECT option or
     * from the containing reference's EType.
     * </p>
     *
     * @return the type hint EClass, or null if none
     */
    public EClass getCurrentTypeHint() {
        return currentTypeHint;
    }

    /**
     * Sets the current type hint for deserialization.
     *
     * @param typeHint the type hint EClass
     */
    public void setCurrentTypeHint(EClass typeHint) {
        this.currentTypeHint = typeHint;
    }
}
