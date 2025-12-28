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
package org.eclipse.fennec.codec.v2.deser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Maintains state during deserialization of a single EObject.
 * <p>
 * This class tracks:
 * <ul>
 *   <li>The target EObject being populated</li>
 *   <li>The resolved EClass (may be set before EObject creation)</li>
 *   <li>Parent context for nested objects</li>
 *   <li>The containing feature (for nested objects)</li>
 *   <li>Unresolved references for later resolution</li>
 *   <li>The target resource (for adding root objects)</li>
 * </ul>
 * </p>
 *
 * @see DeserializationEntry
 * @see <a href="docs/codec-v2-serialization-spec.md#15-deserialization-requirements">Spec 15: Deserialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class DeserializationState {

    private EObject eObject;
    private EClass resolvedEClass;
    private final DeserializationState parent;
    private final EReference containingFeature;
    private final Resource resource;
    private List<UnresolvedReference> unresolvedReferences;
    private boolean isRootObject;

    /**
     * Creates a root deserialization state.
     *
     * @param resource the target resource (may be null for standalone deserialization)
     */
    public DeserializationState(Resource resource) {
        this.resource = resource;
        this.parent = null;
        this.containingFeature = null;
        this.unresolvedReferences = new ArrayList<>();
        this.isRootObject = true;
    }

    /**
     * Creates a nested deserialization state for contained objects.
     *
     * @param parent the parent state
     * @param containingFeature the EReference that contains this object
     */
    public DeserializationState(DeserializationState parent, EReference containingFeature) {
        this.parent = Objects.requireNonNull(parent, "parent must not be null");
        this.containingFeature = Objects.requireNonNull(containingFeature, "containingFeature must not be null");
        this.resource = parent.resource;
        this.unresolvedReferences = parent.unresolvedReferences; // Share with root
        this.isRootObject = false;
    }

    /**
     * Returns the EObject being populated.
     *
     * @return the target EObject, or null if not yet created
     */
    public EObject getEObject() {
        return eObject;
    }

    /**
     * Sets the EObject being populated.
     *
     * @param eObject the target EObject
     */
    public void setEObject(EObject eObject) {
        this.eObject = eObject;
    }

    /**
     * Returns the resolved EClass for this object.
     * <p>
     * This may be set before the EObject is created, during type resolution.
     * </p>
     *
     * @return the resolved EClass, or null if not yet resolved
     */
    public EClass getResolvedEClass() {
        return resolvedEClass;
    }

    /**
     * Sets the resolved EClass for this object.
     *
     * @param resolvedEClass the EClass to use for creating the EObject
     */
    public void setResolvedEClass(EClass resolvedEClass) {
        this.resolvedEClass = resolvedEClass;
    }

    /**
     * Returns the parent deserialization state.
     *
     * @return the parent state, or null if this is a root object
     */
    public DeserializationState getParent() {
        return parent;
    }

    /**
     * Returns the containing feature for nested objects.
     *
     * @return the EReference containing this object, or null if root
     */
    public EReference getContainingFeature() {
        return containingFeature;
    }

    /**
     * Returns the target resource.
     *
     * @return the resource, or null for standalone deserialization
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Returns whether this is a root object (not contained in another).
     *
     * @return true if this is a root object
     */
    public boolean isRootObject() {
        return isRootObject;
    }

    /**
     * Adds an unresolved reference for later resolution.
     * <p>
     * Non-containment references that point to objects not yet deserialized
     * are tracked here and resolved after all objects are created.
     * </p>
     *
     * @param reference the unresolved reference
     */
    public void addUnresolvedReference(UnresolvedReference reference) {
        unresolvedReferences.add(reference);
    }

    /**
     * Returns all unresolved references.
     *
     * @return list of unresolved references (shared across the object tree)
     */
    public List<UnresolvedReference> getUnresolvedReferences() {
        return unresolvedReferences;
    }

    /**
     * Sets a shared unresolved references list.
     * <p>
     * This allows multiple DeserializationState instances to share the same
     * list for collecting unresolved references during nested deserialization.
     * </p>
     *
     * @param sharedList the shared list to use
     */
    public void setSharedUnresolvedReferences(List<UnresolvedReference> sharedList) {
        this.unresolvedReferences = sharedList;
    }

    /**
     * Creates the EObject using the resolved EClass.
     * <p>
     * Requires that {@link #setResolvedEClass(EClass)} was called first.
     * </p>
     *
     * @return the created EObject
     * @throws IllegalStateException if EClass is not resolved
     */
    public EObject createEObject() {
        if (resolvedEClass == null) {
            throw new IllegalStateException("Cannot create EObject: EClass not resolved");
        }
        if (resolvedEClass.isAbstract() || resolvedEClass.isInterface()) {
            throw new IllegalStateException("Cannot instantiate abstract EClass: " + resolvedEClass.getName());
        }
        this.eObject = resolvedEClass.getEPackage().getEFactoryInstance().create(resolvedEClass);
        return eObject;
    }

    /**
     * Sets a feature value on the target EObject.
     *
     * @param feature the feature to set
     * @param value the value to set
     * @throws IllegalStateException if EObject is not created
     */
    public void setFeatureValue(EStructuralFeature feature, Object value) {
        if (eObject == null) {
            throw new IllegalStateException("Cannot set feature: EObject not created");
        }
        eObject.eSet(feature, value);
    }

    /**
     * Adds a value to a multi-valued feature on the target EObject.
     *
     * @param feature the multi-valued feature
     * @param value the value to add
     * @throws IllegalStateException if EObject is not created
     */
    @SuppressWarnings("unchecked")
    public void addFeatureValue(EStructuralFeature feature, Object value) {
        if (eObject == null) {
            throw new IllegalStateException("Cannot add feature value: EObject not created");
        }
        ((List<Object>) eObject.eGet(feature)).add(value);
    }

    /**
     * Represents an unresolved non-containment reference.
     */
    public static class UnresolvedReference {
        private final EObject source;
        private final EReference reference;
        private final String targetUri;
        private final int index; // -1 for single-valued

        public UnresolvedReference(EObject source, EReference reference, String targetUri, int index) {
            this.source = source;
            this.reference = reference;
            this.targetUri = targetUri;
            this.index = index;
        }

        public EObject getSource() {
            return source;
        }

        public EReference getReference() {
            return reference;
        }

        public String getTargetUri() {
            return targetUri;
        }

        public int getIndex() {
            return index;
        }

        public boolean isMultiValued() {
            return index >= 0;
        }
    }
}
