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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveIdConfig;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EObject ID field.
 * <p>
 * Handles the serialization of the ID property based on the effective
 * (pre-merged) ID configuration. No fallback logic is needed as all
 * configuration resolution happens in the {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger}.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#4-id-serialization">Spec 4: ID Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class IdSerializationEntry implements SerializationEntry {

    private final EffectiveIdConfig config;

    /**
     * Creates a new IdSerializationEntry with the effective ID configuration.
     *
     * @param config the effective (pre-merged) ID configuration
     */
    public IdSerializationEntry(EffectiveIdConfig config) {
        this.config = config;
    }

    @Override
    public String getKey() {
        return config.getKey();
    }

    @Override
    public boolean shouldSerialize(SerializationState state) {
        if (!config.isEnabled()) {
            return false;
        }
        String idValue = resolveIdValue(state.getEObject());
        return idValue != null;
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
        String idValue = resolveIdValue(state.getEObject());
        if (idValue != null) {
            gen.writeStringProperty(config.getKey(), idValue);
        }
    }

    /**
     * Resolves the ID value for the given EObject.
     * <p>
     * Priority:
     * <ol>
     *   <li>EClass ID attribute value</li>
     *   <li>Resource URI fragment</li>
     * </ol>
     * </p>
     *
     * @param eObject the EObject to get the ID from
     * @return the ID value or null if not available
     */
    private String resolveIdValue(EObject eObject) {
        // Try to get ID from ID feature
        EStructuralFeature idFeature = eObject.eClass().getEIDAttribute();
        if (idFeature != null) {
            Object id = eObject.eGet(idFeature);
            return id != null ? id.toString() : null;
        }
        // Fallback: use resource URI fragment
        if (eObject.eResource() != null) {
            return eObject.eResource().getURIFragment(eObject);
        }
        return null;
    }
}
