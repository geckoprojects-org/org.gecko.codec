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

import java.io.IOException;
import java.io.UncheckedIOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.eclipse.fennec.codec.v2.value.CodecValueRegistry;
import org.eclipse.fennec.codec.v2.value.CodecValueWriter;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;

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
    private final CodecValueWriter<Object, EAttribute> customWriter;

    /**
     * Creates a new AttributeSerializationEntry with the effective feature configuration.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param attribute the EAttribute to serialize
     */
    public AttributeSerializationEntry(EffectiveFeatureConfig config, EAttribute attribute) {
        this(config, attribute, null);
    }

    /**
     * Creates a new AttributeSerializationEntry with custom value writer support.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param attribute the EAttribute to serialize
     * @param valueRegistry the registry for custom value writers (may be null)
     */
    @SuppressWarnings("unchecked")
    public AttributeSerializationEntry(EffectiveFeatureConfig config, EAttribute attribute,
            CodecValueRegistry valueRegistry) {
        this.config = config;
        this.attribute = attribute;

        // Pre-resolve the custom writer at construction time
        String writerName = config.getValueWriterName();
        if (writerName != null && !writerName.isEmpty() && valueRegistry != null) {
            this.customWriter = (CodecValueWriter<Object, EAttribute>) valueRegistry.getWriter(writerName).orElse(null);
        } else {
            this.customWriter = null;
        }
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
                writeValue(gen, item, ctxt);
            }
            gen.writeEndArray();
        } else {
            writeValue(gen, value, ctxt);
        }
    }

    /**
     * Writes a single attribute value to the generator.
     * <p>
     * If a custom value writer is configured, it will be used instead of
     * the default serialization logic.
     * </p>
     *
     * @param gen the JSON generator
     * @param value the value to write
     * @param ctxt the serialization context (may be null)
     */
    private void writeValue(JsonGenerator gen, Object value, SerializationContext ctxt) {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // Use custom writer if configured
        if (customWriter != null) {
            try {
                customWriter.write(value, attribute, gen, ctxt);
            } catch (IOException e) {
                throw new UncheckedIOException("Custom value writer failed for attribute: " + attribute.getName(), e);
            }
            return;
        }

        // Default serialization
        if (value instanceof String s) {
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
        } else if (value instanceof Enumerator e) {
            // EMF enums implement Enumerator
            writeEnumValue(gen, e);
        } else if (value instanceof Enum<?> e) {
            // Java enums (non-EMF)
            writeJavaEnumValue(gen, e);
        } else {
            gen.writeString(value.toString());
        }
    }

    /**
     * Writes an EMF enum (Enumerator) value based on the configured strategy.
     *
     * @param gen the JSON generator
     * @param e the EMF enumerator value
     */
    private void writeEnumValue(JsonGenerator gen, Enumerator e) {
        EnumSerializationStrategy strategy = config.getEnumSerialization();
        switch (strategy) {
            case VALUE:
                gen.writeNumber(e.getValue());
                break;
            case NAME:
                gen.writeString(e.getName());
                break;
            case LITERAL:
            default:
                gen.writeString(e.getLiteral());
                break;
        }
    }

    /**
     * Writes a Java enum value based on the configured strategy.
     *
     * @param gen the JSON generator
     * @param e the Java enum value
     */
    private void writeJavaEnumValue(JsonGenerator gen, Enum<?> e) {
        EnumSerializationStrategy strategy = config.getEnumSerialization();
        switch (strategy) {
            case VALUE:
                gen.writeNumber(e.ordinal());
                break;
            case NAME:
            case LITERAL:
            default:
                // For Java enums, NAME and LITERAL are the same
                gen.writeString(e.name());
                break;
        }
    }
}
