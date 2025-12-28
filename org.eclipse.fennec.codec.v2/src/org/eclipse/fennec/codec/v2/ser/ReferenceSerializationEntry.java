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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;

import tools.jackson.core.JsonGenerator;
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

    /**
     * Creates a new ReferenceSerializationEntry with the effective feature configuration.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param reference the EReference to serialize
     * @param refKey the JSON key to use for non-containment reference URIs
     */
    public ReferenceSerializationEntry(EffectiveFeatureConfig config, EReference reference, String refKey) {
        this.config = config;
        this.reference = reference;
        this.refKey = refKey;
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
     * non-containment references are serialized as URI references.
     * </p>
     *
     * @param target the target EObject
     * @param gen the JSON generator
     * @param ctxt the serialization context
     */
    private void serializeReference(EObject target, JsonGenerator gen, SerializationContext ctxt) {
        if (reference.isContainment()) {
            // Containment: serialize inline
            ctxt.writeValue(gen, target);
        } else {
            // Non-containment: serialize as reference
            writeReferenceObject(target, gen);
        }
    }

    /**
     * Writes a reference object with the configured reference key.
     *
     * @param target the target EObject
     * @param gen the JSON generator
     */
    private void writeReferenceObject(EObject target, JsonGenerator gen) {
        gen.writeStartObject();
        String uri = getReferenceUri(target);
        gen.writeStringProperty(refKey, uri);
        gen.writeEndObject();
    }

    /**
     * Gets the URI for a reference target.
     *
     * @param target the target EObject
     * @return the reference URI
     */
    private String getReferenceUri(EObject target) {
        if (target.eResource() != null) {
            return target.eResource().getURIFragment(target);
        }
        // Fallback for objects not in a resource
        return target.eClass().getEPackage().getNsURI() + "#//" + target.eClass().getName();
    }
}
