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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveSuperTypeConfig;
import org.eclipse.fennec.model.metadata.SuperTypeSelection;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EObject supertype information.
 * <p>
 * Handles the serialization of supertype information based on the effective
 * (pre-merged) supertype configuration. No fallback logic is needed as all
 * configuration resolution happens in the {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger}.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#7-supertype-serialization">Spec 7: SuperType Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class SuperTypeSerializationEntry implements SerializationEntry {

    private final EffectiveSuperTypeConfig config;
    private final EClass eClass;

    /**
     * Creates a new SuperTypeSerializationEntry with the effective supertype configuration.
     *
     * @param config the effective (pre-merged) supertype configuration
     * @param eClass the EClass being serialized
     */
    public SuperTypeSerializationEntry(EffectiveSuperTypeConfig config, EClass eClass) {
        this.config = config;
        this.eClass = eClass;
    }

    @Override
    public String getKey() {
        return config.getSuperTypeKey();
    }

    @Override
    public boolean shouldSerialize(SerializationState state) {
        if (!config.isEnabled()) {
            return false;
        }
        List<String> superTypes = resolveSuperTypes();
        return !superTypes.isEmpty();
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
        List<String> superTypes = resolveSuperTypes();
        if (superTypes.isEmpty()) {
            return;
        }

        SuperTypeSelection selection = config.getSelection();
        if (selection == SuperTypeSelection.SINGLE) {
            // Single value: use first supertype
            gen.writeStringProperty(config.getSuperTypeKey(), superTypes.get(0));
        } else {
            // Array of supertypes
            gen.writeArrayPropertyStart(config.getSuperTypeKey());
            for (String superType : superTypes) {
                gen.writeString(superType);
            }
            gen.writeEndArray();
        }
    }

    /**
     * Resolves the list of supertypes to serialize.
     *
     * @return list of supertype URIs
     */
    private List<String> resolveSuperTypes() {
        List<String> superTypes = new ArrayList<>();
        SuperTypeSelection selection = config.getSelection();

        if (selection == SuperTypeSelection.NONE) {
            return superTypes;
        }

        boolean includeEmf = (selection == SuperTypeSelection.ALL_EMF);

        for (EClass superType : eClass.getESuperTypes()) {
            // Skip EMF base types unless selection is ALL_EMF
            if (!includeEmf && isEmfBaseType(superType)) {
                continue;
            }
            superTypes.add(getEClassUri(superType));

            // For SINGLE, only include the first matching supertype
            if (selection == SuperTypeSelection.SINGLE && !superTypes.isEmpty()) {
                break;
            }
        }
        return superTypes;
    }

    /**
     * Gets the URI for an EClass.
     *
     * @param eClass the EClass
     * @return the EClass URI (nsURI#//className)
     */
    private String getEClassUri(EClass eClass) {
        return eClass.getEPackage().getNsURI() + "#//" + eClass.getName();
    }

    /**
     * Checks if the given EClass is an EMF base type.
     *
     * @param eClass the EClass to check
     * @return true if it's an EMF base type
     */
    private boolean isEmfBaseType(EClass eClass) {
        String nsURI = eClass.getEPackage().getNsURI();
        return nsURI != null && (
                nsURI.startsWith("http://www.eclipse.org/emf/") ||
                nsURI.equals("http://www.eclipse.org/emf/2002/Ecore"));
    }
}
