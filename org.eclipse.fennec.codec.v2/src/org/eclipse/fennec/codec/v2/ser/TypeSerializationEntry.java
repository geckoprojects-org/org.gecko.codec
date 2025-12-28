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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveTypeConfig;
import org.eclipse.fennec.model.metadata.TypeStrategy;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EObject type information.
 * <p>
 * Handles the serialization of the type property based on the effective
 * (pre-merged) type configuration. No fallback logic is needed as all
 * configuration resolution happens in the {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger}.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#3-type-serialization">Spec 3: Type Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class TypeSerializationEntry implements SerializationEntry {

    private final EffectiveTypeConfig config;
    private final String typeValue;

    /**
     * Creates a new TypeSerializationEntry with the effective type configuration.
     *
     * @param config the effective (pre-merged) type configuration
     * @param eClass the EClass being serialized (for computing type value if no discriminator)
     */
    public TypeSerializationEntry(EffectiveTypeConfig config, EClass eClass) {
        this.config = config;
        this.typeValue = resolveTypeValue(eClass);
    }

    @Override
    public String getKey() {
        return config.getTypeKey();
    }

    @Override
    public boolean shouldSerialize(SerializationState state) {
        return config.isEnabled();
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
        gen.writeStringProperty(config.getTypeKey(), typeValue);
    }

    /**
     * Resolves the type value to serialize based on the configured strategy.
     * <p>
     * Handles different type strategies:
     * <ul>
     *   <li>MAPPED - Uses discriminator value if available</li>
     *   <li>NAME - Simple EClass name</li>
     *   <li>CLASS - Java instance class name</li>
     *   <li>URI - Full EClass URI (default)</li>
     *   <li>NUMERIC - EClass classifier ID</li>
     *   <li>STRUCTURED/SCHEMA_AND_TYPE - Handled separately in serialize()</li>
     * </ul>
     * </p>
     *
     * @param eClass the EClass
     * @return the type value string
     */
    private String resolveTypeValue(EClass eClass) {
        // Use discriminator if configured (for MAPPED strategy)
        String discriminator = config.getDiscriminatorValue();
        if (discriminator != null && !discriminator.isEmpty()) {
            return discriminator;
        }

        // Resolve based on strategy
        TypeStrategy strategy = config.getStrategy();
        if (strategy == null) {
            strategy = TypeStrategy.URI;
        }

        switch (strategy) {
            case NAME:
                return eClass.getName();
            case CLASS:
                Class<?> instanceClass = eClass.getInstanceClass();
                return instanceClass != null ? instanceClass.getName() : eClass.getName();
            case NUMERIC:
                return String.valueOf(eClass.getClassifierID());
            case URI:
            default:
                // Full EMF URI: nsURI#//className
                return EcoreUtil.getURI(eClass).toString();
        }
    }
}
