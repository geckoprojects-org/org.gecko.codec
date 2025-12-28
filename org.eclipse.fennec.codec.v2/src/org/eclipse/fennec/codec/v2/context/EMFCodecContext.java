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
import org.eclipse.fennec.model.metadata.ClassMetadata;
import org.eclipse.fennec.model.metadata.api.MetadataService;

/**
 * Base context interface for EMF codec operations.
 * <p>
 * This interface tracks EMF state during serialization and deserialization,
 * providing access to the current EObject, feature, resource, and metadata service.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#1811-emf-codec-context">Spec 18.11: EMF Codec Context</a>
 */
public interface EMFCodecContext {

    /**
     * Returns the EObject currently being processed.
     *
     * @return the current EObject, or null if none
     */
    EObject getCurrentEObject();

    /**
     * Sets the EObject currently being processed.
     *
     * @param eObject the current EObject
     */
    void setCurrentEObject(EObject eObject);

    /**
     * Returns the EStructuralFeature currently being processed.
     *
     * @return the current feature, or null if none
     */
    EStructuralFeature getCurrentFeature();

    /**
     * Sets the EStructuralFeature currently being processed.
     *
     * @param feature the current feature
     */
    void setCurrentFeature(EStructuralFeature feature);

    /**
     * Resets the current feature to null.
     */
    void resetFeature();

    /**
     * Returns the EMF Resource being processed.
     *
     * @return the resource, or null if none
     */
    Resource getResource();

    /**
     * Sets the EMF Resource being processed.
     *
     * @param resource the resource
     */
    void setResource(Resource resource);

    /**
     * Returns the MetadataService for aspect lookups.
     *
     * @return the metadata service
     */
    MetadataService getMetadataService();

    /**
     * Convenience method to get ClassMetadata for an EClass.
     *
     * @param eClass the EClass
     * @return the class metadata, or null if not found
     */
    default ClassMetadata getClassMetadata(EClass eClass) {
        MetadataService service = getMetadataService();
        return service != null ? service.getClassMetadata(eClass) : null;
    }

    /**
     * Returns the context holder that stores EMF state.
     *
     * @return the EMF context holder
     */
    EMFContextHolder getEMFContextHolder();

    /**
     * Sets the context holder that stores EMF state.
     *
     * @param holder the EMF context holder
     */
    void setEMFContextHolder(EMFContextHolder holder);
}
