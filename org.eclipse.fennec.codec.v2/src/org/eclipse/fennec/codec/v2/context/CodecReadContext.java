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

import tools.jackson.core.TokenStreamContext;
import tools.jackson.core.exc.StreamReadException;

/**
 * Read context implementation for EMF codec deserialization operations.
 * <p>
 * This class extends {@link TokenStreamContext} and implements {@link EMFCodecReadContext}
 * to provide EMF-aware context during Jackson deserialization. It tracks the current
 * EObject, feature, resource, and provides type resolution capabilities.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#1811-emf-codec-context">Spec 18.11: EMF Codec Context</a>
 */
public class CodecReadContext extends TokenStreamContext implements EMFCodecReadContext {

    private EMFContextHolder holder;

    /** Cached child context for reuse */
    protected CodecReadContext child;

    /** Parent context */
    protected CodecReadContext parent;

    /** Current field name */
    protected String currentName;

    /** Current value being deserialized */
    protected Object currentValue;

    /**
     * Creates a root read context.
     *
     * @param metadataService the metadata service for aspect lookups and type resolution
     */
    public CodecReadContext(MetadataService metadataService) {
        super(TYPE_ROOT, -1);
        this.holder = new EMFContextHolder(metadataService);
    }

    /**
     * Creates a child read context.
     *
     * @param parent the parent context
     * @param nestingDepth the nesting depth
     * @param type the context type (TYPE_ARRAY, TYPE_OBJECT)
     */
    protected CodecReadContext(CodecReadContext parent, int nestingDepth, int type) {
        super(type, -1);
        this.parent = parent;
        // Share the metadata service from parent
        this.holder = new EMFContextHolder(parent.holder.getMetadataService());
    }

    /**
     * Creates a root read context.
     *
     * @param metadataService the metadata service for aspect lookups and type resolution
     * @return the root context
     */
    public static CodecReadContext createRootContext(MetadataService metadataService) {
        return new CodecReadContext(metadataService);
    }

    @Override
    public EStructuralFeature getCurrentFeature() {
        return holder.getCurrentFeature();
    }

    @Override
    public void setCurrentFeature(EStructuralFeature feature) {
        holder.setCurrentFeature(feature);
    }

    @Override
    public EObject getCurrentEObject() {
        return holder.getCurrentEObject();
    }

    @Override
    public void setCurrentEObject(EObject eObject) {
        holder.setCurrentEObject(eObject);
    }

    @Override
    public void resetFeature() {
        holder.resetFeature();
    }

    @Override
    public void setResource(Resource resource) {
        holder.setResource(resource);
    }

    @Override
    public Resource getResource() {
        return holder.getResource();
    }

    @Override
    public MetadataService getMetadataService() {
        return holder.getMetadataService();
    }

    @Override
    public ClassMetadata getClassMetadata(EClass eClass) {
        MetadataService service = getMetadataService();
        return service != null ? service.getClassMetadata(eClass) : null;
    }

    @Override
    public CodecReadContext createChildArrayContext(int lineNr, int colNr) {
        CodecReadContext ctxt = child;
        if (ctxt == null) {
            child = ctxt = new CodecReadContext(this, _nestingDepth + 1, TYPE_ARRAY);
        } else {
            ctxt.reset(TYPE_ARRAY, lineNr, colNr);
        }
        return ctxt;
    }

    @Override
    public CodecReadContext createChildObjectContext(int lineNr, int colNr) {
        CodecReadContext ctxt = child;
        if (ctxt == null) {
            child = ctxt = new CodecReadContext(this, _nestingDepth + 1, TYPE_OBJECT);
            return ctxt;
        }
        ctxt.reset(TYPE_OBJECT, lineNr, colNr);
        return ctxt;
    }

    @Override
    public CodecReadContext reset(int type, int lineNr, int colNr) {
        _type = type;
        _index = -1;
        currentName = null;
        currentValue = null;
        return this;
    }

    @Override
    public CodecReadContext clearAndGetParent() {
        currentValue = null;
        return getParent();
    }

    @Override
    public void setCurrentName(String name) throws StreamReadException {
        currentName = name;
    }

    @Override
    public boolean hasCurrentName() {
        return currentName != null;
    }

    @Override
    public CodecReadContext getParent() {
        return parent;
    }

    @Override
    public String currentName() {
        return currentName;
    }

    @Override
    public Object currentValue() {
        return currentValue;
    }

    @Override
    public void assignCurrentValue(Object v) {
        currentValue = v;
    }

    @Override
    public boolean hasParentContext() {
        return parent != null;
    }

    @Override
    public EMFContextHolder getEMFContextHolder() {
        return holder;
    }

    @Override
    public void setEMFContextHolder(EMFContextHolder holder) {
        this.holder = holder;
    }

    @Override
    public EClass getCurrentTypeHint() {
        return holder.getCurrentTypeHint();
    }

    @Override
    public void setCurrentTypeHint(EClass typeHint) {
        holder.setCurrentTypeHint(typeHint);
    }

    @Override
    public EClass resolveEClass(String typeValue) {
        if (typeValue == null || typeValue.isEmpty()) {
            return null;
        }

        MetadataService service = getMetadataService();
        if (service == null) {
            return null;
        }

        // Try as full URI first
        ClassMetadata metadata = service.getClassMetadataByURI(typeValue);
        if (metadata != null) {
            return metadata.getEClass();
        }

        // TODO: Support SIMPLE_NAME and MAPPED strategies
        // This will require additional configuration from ClassCodecAspect

        return null;
    }

    @Override
    public EClass resolveEClass(String typeValue, EClass hint) {
        // Priority 1: Try to resolve from typeValue
        if (typeValue != null && !typeValue.isEmpty()) {
            EClass resolved = resolveEClass(typeValue);
            if (resolved != null) {
                return resolved;
            }
        }

        // Priority 2: Return hint (caller must check instantiability)
        return hint;
    }
}
