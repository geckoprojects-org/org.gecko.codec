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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EAttribute values.
 * <p>
 * Handles the serialization of attribute values based on the effective
 * (pre-merged) feature configuration. No fallback logic is needed as all
 * configuration resolution happens in the {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger}.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-feature-serialization">Spec 9: Feature Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class AttributeSerializationEntry implements SerializationEntry {

    private final EffectiveFeatureConfig config;
    private final EAttribute attribute;

    /**
     * Creates a new AttributeSerializationEntry with the effective feature configuration.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param attribute the EAttribute to serialize
     */
    public AttributeSerializationEntry(EffectiveFeatureConfig config, EAttribute attribute) {
        this.config = config;
        this.attribute = attribute;
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
        Object value = state.getValue(attribute);

        // Null check
        if (value == null) {
            return config.isSerializeNull();
        }

        // Empty collection check
        if (attribute.isMany() && value instanceof EList<?> list && list.isEmpty()) {
            return config.isSerializeEmpty();
        }

        // Default value check
        if (value.equals(attribute.getDefaultValue())) {
            return config.isSerializeDefaults();
        }

        return true;
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
        // Use cached value from shouldSerialize call
        Object value = state.getValue(attribute);

        if (value == null) {
            gen.writeNullProperty(config.getKey());
            return;
        }

        gen.writeName(config.getKey());

        if (attribute.isMany() && value instanceof EList<?> list) {
            gen.writeStartArray();
            for (Object item : list) {
                writeValue(gen, item);
            }
            gen.writeEndArray();
        } else {
            writeValue(gen, value);
        }
    }

    /**
     * Writes a single attribute value to the generator.
     *
     * @param gen the JSON generator
     * @param value the value to write
     */
    private void writeValue(JsonGenerator gen, Object value) {
        if (value == null) {
            gen.writeNull();
        } else if (value instanceof String s) {
            gen.writeString(s);
        } else if (value instanceof Integer i) {
            gen.writeNumber(i);
        } else if (value instanceof Long l) {
            gen.writeNumber(l);
        } else if (value instanceof Double d) {
            gen.writeNumber(d);
        } else if (value instanceof Float f) {
            gen.writeNumber(f);
        } else if (value instanceof Boolean b) {
            gen.writeBoolean(b);
        } else if (value instanceof Enum<?> e) {
            // TODO: Add enum literal setting to EffectiveFeatureConfig if needed
            gen.writeString(e.name());
        } else {
            gen.writeString(value.toString());
        }
    }
}
