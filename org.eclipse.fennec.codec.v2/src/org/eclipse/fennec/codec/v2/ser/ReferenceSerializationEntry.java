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
package org.eclipse.fennec.codec.v2.ser;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.eclipse.fennec.codec.v2.context.CodecWriteContext;
import org.eclipse.fennec.codec.v2.context.ContextHelper;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.TokenStreamContext;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EReference values.
 * <p>
 * Handles the serialization of reference values based on the effective
 * (pre-merged) feature configuration. No fallback logic is needed as all
 * configuration resolution happens in the {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger}.
 * Distinguishes between containment references (serialized inline) and
 * non-containment references (serialized as refs).
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#65-reference-serialization">Spec 6.5: Reference Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class ReferenceSerializationEntry implements SerializationEntry {

    private final EffectiveFeatureConfig config;
    private final EReference reference;
    private final String refKey;
    private final boolean smartCompression;
    private final EffectiveCodecConfig codecConfig;

    /**
     * Creates a new ReferenceSerializationEntry with the effective feature configuration.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param reference the EReference to serialize
     * @param refKey the JSON key to use for non-containment reference URIs
     */
    public ReferenceSerializationEntry(EffectiveFeatureConfig config, EReference reference, String refKey) {
        this(config, reference, refKey, false, null);
    }

    /**
     * Creates a new ReferenceSerializationEntry with smart compression support.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param reference the EReference to serialize
     * @param refKey the JSON key to use for non-containment reference URIs
     * @param smartCompression whether smart compression is enabled
     */
    public ReferenceSerializationEntry(EffectiveFeatureConfig config, EReference reference,
            String refKey, boolean smartCompression) {
        this(config, reference, refKey, smartCompression, null);
    }

    /**
     * Creates a new ReferenceSerializationEntry with full configuration support.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param reference the EReference to serialize
     * @param refKey the JSON key to use for non-containment reference URIs
     * @param smartCompression whether smart compression is enabled
     * @param codecConfig the effective codec configuration (for expand settings)
     */
    public ReferenceSerializationEntry(EffectiveFeatureConfig config, EReference reference,
            String refKey, boolean smartCompression, EffectiveCodecConfig codecConfig) {
        this.config = config;
        this.reference = reference;
        this.refKey = refKey;
        this.smartCompression = smartCompression;
        this.codecConfig = codecConfig;
    }

    @Override
    public String getKey() {
        return config.getKey();
    }

    @Override
    public boolean shouldSerialize(SerializationState state) {
        // Config already includes: global ignore, derived, transient, aspect.serialize checks
        if (!config.isSerialize()) {
            return false;
        }

        // Check value conditions - use cached value
        Object value = state.getValue(reference);

        // Null check
        if (value == null) {
            return config.isSerializeNull();
        }

        // Empty collection check
        if (reference.isMany() && value instanceof EList<?> list && list.isEmpty()) {
            return config.isSerializeEmpty();
        }

        return true;
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
        // Use cached value from shouldSerialize call
        Object value = state.getValue(reference);

        if (value == null) {
            gen.writeNullProperty(config.getKey());
            return;
        }

        gen.writeName(config.getKey());

        if (reference.isMany() && value instanceof EList<?> list) {
            gen.writeStartArray();
            for (Object item : list) {
                if (item instanceof EObject target) {
                    serializeReference(target, gen, ctxt);
                }
            }
            gen.writeEndArray();
        } else if (value instanceof EObject target) {
            serializeReference(target, gen, ctxt);
        }
    }

    /**
     * Serializes a single reference target.
     * <p>
     * Containment references are serialized inline (as nested objects),
     * unless they are cross-document containments (target in different resource).
     * Non-containment references are serialized as URI references, unless expand
     * is enabled for this reference.
     * </p>
     * <p>
     * When expand is enabled for a non-containment reference, the referenced object
     * is serialized inline (like a containment) if it is resolved (not a proxy).
     * </p>
     * <p>
     * When smart compression is enabled, type information is suppressed if the
     * instance type matches the declared reference type.
     * </p>
     *
     * @param target the target EObject
     * @param gen the JSON generator
     * @param ctxt the serialization context
     * @see <a href="docs/codec-v2-spec/07-reference.md#6-cross-document-containment">Spec: Cross-Document Containment</a>
     * @see <a href="docs/codec-v2-spec/07-reference.md#42-expand-inline-serialization">Spec: Expand Inline Serialization</a>
     */
    private void serializeReference(EObject target, JsonGenerator gen, SerializationContext ctxt) {
        // Check for cross-document containment: containment reference where target is in different resource
        if (reference.isContainment() && isCrossDocument(gen, target)) {
            // Cross-document containment: serialize as reference (like non-containment)
            writeReferenceObject(target, gen, true);
        } else if (reference.isContainment()) {
            // Standard containment: serialize inline
            // Smart compression: suppress _type if instance type == reference type
            if (smartCompression && shouldSuppressType(target)) {
                ContextHelper.setSuppressType(ctxt, true);
            }
            ctxt.writeValue(gen, target);
        } else if (shouldExpandReference(target)) {
            // Non-containment with expand enabled: serialize inline (like containment)
            // Only if the target is resolved (not a proxy)
            serializeExpandedReference(target, gen, ctxt);
        } else {
            // Non-containment: serialize as reference
            writeReferenceObject(target, gen, false);
        }
    }

    /**
     * Checks if this non-containment reference should be expanded.
     * <p>
     * A reference is expanded if:
     * <ul>
     *   <li>The codecConfig is available AND</li>
     *   <li>The reference is configured for expansion (globally or specifically) AND</li>
     *   <li>The target is resolved (not a proxy)</li>
     *   <li>For bi-directional references: expandIgnoreBidirectional is false OR ref has no opposite</li>
     * </ul>
     * </p>
     *
     * @param target the target EObject
     * @return true if the reference should be expanded
     */
    private boolean shouldExpandReference(EObject target) {
        if (codecConfig == null) {
            return false;
        }

        // Don't expand proxies - they need to be serialized as references
        if (target.eIsProxy()) {
            return false;
        }

        // Check if this reference is configured for expansion
        if (!codecConfig.shouldExpand(reference)) {
            return false;
        }

        // Check bi-directional handling
        if (codecConfig.isExpandIgnoreBidirectional() && reference.getEOpposite() != null) {
            return false;
        }

        return true;
    }

    /**
     * Serializes an expanded non-containment reference as an inline object.
     * <p>
     * The object is serialized similarly to a containment, with smart compression
     * applied if enabled.
     * </p>
     *
     * @param target the target EObject (must not be a proxy)
     * @param gen the JSON generator
     * @param ctxt the serialization context
     */
    private void serializeExpandedReference(EObject target, JsonGenerator gen, SerializationContext ctxt) {
        // Smart compression: suppress _type if instance type == reference type
        if (smartCompression && shouldSuppressType(target)) {
            ContextHelper.setSuppressType(ctxt, true);
        }
        ctxt.writeValue(gen, target);
    }

    /**
     * Checks if the target object is in a different resource than the source (cross-document).
     * <p>
     * This detects cross-document containment where the contained object has been
     * moved to or stored in a different EMF Resource.
     * </p>
     *
     * @param gen the JSON generator (to access source resource from context)
     * @param target the target EObject
     * @return true if target is in a different resource (cross-document)
     */
    private boolean isCrossDocument(JsonGenerator gen, EObject target) {
        TokenStreamContext ctx = gen.streamWriteContext();
        if (!(ctx instanceof CodecWriteContext codecCtx)) {
            return false;
        }

        Resource sourceResource = codecCtx.getResource();

        // Handle proxy targets
        if (target.eIsProxy() && target instanceof InternalEObject internalEObject) {
            URI proxyUri = internalEObject.eProxyURI();
            return sourceResource != null && sourceResource.getURI() != null
                    && !sourceResource.getURI().equals(proxyUri.trimFragment());
        }

        // Compare resources
        Resource targetResource = target.eResource();
        return sourceResource == null || sourceResource != targetResource;
    }

    /**
     * Checks if type should be suppressed for smart compression.
     * <p>
     * Type is suppressed when the instance type exactly matches the declared
     * reference type, as the type can be inferred from the reference declaration.
     * </p>
     *
     * @param target the target EObject
     * @return true if type should be suppressed
     */
    private boolean shouldSuppressType(EObject target) {
        EClass instanceType = target.eClass();
        EClass referenceType = reference.getEReferenceType();
        return instanceType == referenceType;
    }

    /**
     * Writes a reference object with the configured reference key.
     * <p>
     * For cross-document references, generates an absolute or relative URI
     * depending on whether the target is in a different resource.
     * </p>
     *
     * @param target the target EObject
     * @param gen the JSON generator
     * @param crossDocument true if this is a cross-document reference
     */
    private void writeReferenceObject(EObject target, JsonGenerator gen, boolean crossDocument) {
        gen.writeStartObject();

        // Write type if smart compression is off, or if type differs from reference type
        if (!smartCompression || !shouldSuppressType(target)) {
            // @CLAUDE: type serialization will need to be integrated with TypeSerializationEntry
            // For now, write the full URI as type
            String typeUri = EcoreUtil.getURI(target.eClass()).toString();
            gen.writeStringProperty("_type", typeUri);
        }

        String uri = getReferenceUri(gen, target, crossDocument);
        gen.writeStringProperty(refKey, uri);
        gen.writeEndObject();
    }

    /**
     * Gets the URI for a reference target.
     * <p>
     * For same-document references, returns the fragment path (e.g., "//@employees.0").
     * For cross-document references, returns a relative or absolute URI
     * (e.g., "other.json#//@employees.0").
     * </p>
     *
     * @param gen the JSON generator (to access source resource from context)
     * @param target the target EObject
     * @param crossDocument true if this is a cross-document reference
     * @return the reference URI
     */
    private String getReferenceUri(JsonGenerator gen, EObject target, boolean crossDocument) {
        if (crossDocument) {
            // Cross-document: generate relative URI from source to target
            TokenStreamContext ctx = gen.streamWriteContext();
            if (ctx instanceof CodecWriteContext codecCtx) {
                Resource sourceResource = codecCtx.getResource();
                if (sourceResource != null && sourceResource.getURI() != null) {
                    URI targetUri = EcoreUtil.getURI(target);
                    URI sourceUri = sourceResource.getURI();
                    // Deresolve to get relative URI
                    URI relativeUri = targetUri.deresolve(sourceUri);
                    return relativeUri.toString();
                }
            }
            // Fallback: use absolute URI
            return EcoreUtil.getURI(target).toString();
        }

        // Same-document: use fragment only
        if (target.eResource() != null) {
            return target.eResource().getURIFragment(target);
        }
        // Fallback for objects not in a resource
        return target.eClass().getEPackage().getNsURI() + "#//" + target.eClass().getName();
    }
}
