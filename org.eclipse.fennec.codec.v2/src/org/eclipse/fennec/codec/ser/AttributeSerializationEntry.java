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
package org.eclipse.fennec.codec.ser;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fennec.codec.api.value.AttributeValueWriter;
import org.eclipse.fennec.codec.api.value.CodecValueRegistry;
import org.eclipse.fennec.codec.api.value.CodecValueWriter;
import org.eclipse.fennec.codec.config.FeatureConfig;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EAttribute values.
 * <p>
 * Handles the serialization of attribute values based on the
 * feature configuration.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-feature-serialization">Spec 9: Feature Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class AttributeSerializationEntry implements SerializationEntry {

    private static final Logger LOGGER = Logger.getLogger(AttributeSerializationEntry.class.getName());

    private final FeatureConfig config;
    private final EAttribute attribute;
    private final CodecValueWriter<Object, EAttribute> customWriter;

    /**
     * Creates a new AttributeSerializationEntry with the feature configuration.
     *
     * @param config the feature configuration
     * @param attribute the EAttribute to serialize
     */
    public AttributeSerializationEntry(FeatureConfig config, EAttribute attribute) {
        this(config, attribute, null);
    }

    /**
     * Creates a new AttributeSerializationEntry with custom value writer support.
     *
     * @param config the feature configuration
     * @param attribute the EAttribute to serialize
     * @param valueRegistry the registry for custom value writers (may be null)
     */
    @SuppressWarnings("unchecked")
    public AttributeSerializationEntry(FeatureConfig config, EAttribute attribute,
            CodecValueRegistry valueRegistry) {
        this.config = config;
        this.attribute = attribute;

        // Pre-resolve the custom writer at construction time
        String writerName = config.getValueWriterName();
        if (writerName != null && !writerName.isEmpty() && valueRegistry != null) {
            CodecValueWriter<?, ?> writer = valueRegistry.getWriter(writerName).orElse(null);

            if (writer instanceof AttributeValueWriter<?> attributeWriter) {
                if (attributeWriter.canHandle(attribute)) {
                    this.customWriter = (CodecValueWriter<Object, EAttribute>) writer;
                } else {
                    LOGGER.warning("AttributeValueWriter '" + writerName
                            + "' cannot handle attribute '" + attribute.getName()
                            + "' (canHandle returned false). Using default serialization.");
                    this.customWriter = null;
                }
            } else {
                this.customWriter = (CodecValueWriter<Object, EAttribute>) writer;
            }
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
        // GAP-001: Use shouldSerialize() from FeatureConfig which handles
        // ignore, ignoreWrite, forceWrite, derived, transient checks
        if (!config.shouldSerialize()) {
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
            return config.isSerializeDefault();
        }

        return true;
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
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
     */
    private void writeValue(JsonGenerator gen, Object value, SerializationContext ctxt) {
        if (value == null) {
            gen.writeNull();
            return;
        }

        if (customWriter != null) {
            try {
                customWriter.write(value, attribute, gen, ctxt);
            } catch (IOException e) {
                throw new UncheckedIOException("Custom value writer failed for attribute: " + attribute.getName(), e);
            }
            return;
        }

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
            writeEnumValue(gen, e);
        } else if (value instanceof Enum<?> e) {
            writeJavaEnumValue(gen, e);
        } else if (value.getClass().isArray()) {
            writeArrayValue(gen, value, ctxt);
        } else {
            gen.writeString(value.toString());
        }
    }

    /**
     * Writes an array value to the generator, supporting multi-dimensional arrays.
     */
    private void writeArrayValue(JsonGenerator gen, Object array, SerializationContext ctxt) {
        Class<?> componentType = array.getClass().getComponentType();

        gen.writeStartArray();

        if (componentType == double.class) {
            for (double v : (double[]) array) gen.writeNumber(v);
        } else if (componentType == int.class) {
            for (int v : (int[]) array) gen.writeNumber(v);
        } else if (componentType == long.class) {
            for (long v : (long[]) array) gen.writeNumber(v);
        } else if (componentType == float.class) {
            for (float v : (float[]) array) gen.writeNumber(v);
        } else if (componentType == boolean.class) {
            for (boolean v : (boolean[]) array) gen.writeBoolean(v);
        } else if (componentType == short.class) {
            for (short v : (short[]) array) gen.writeNumber(v);
        } else if (componentType == byte.class) {
            for (byte v : (byte[]) array) gen.writeNumber(v);
        } else {
            Object[] arr = (Object[]) array;
            for (Object element : arr) {
                if (element == null) {
                    gen.writeNull();
                } else if (element.getClass().isArray()) {
                    writeArrayValue(gen, element, ctxt);
                } else {
                    writeValue(gen, element, ctxt);
                }
            }
        }

        gen.writeEndArray();
    }

    /**
     * Writes an EMF enum (Enumerator) value based on the configured strategy.
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
                gen.writeString(e.name());
                break;
        }
    }
}
