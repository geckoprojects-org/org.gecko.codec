/**
 * Copyright (c) 2012 - 2026 Data In Motion and others.
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
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fennec.codec.api.value.AttributeValueWriter;
import org.eclipse.fennec.codec.api.value.CodecValueRegistry;
import org.eclipse.fennec.codec.api.value.CodecValueWriter;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
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
/**
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.ser.AttributeSerializationEntry}.
 */
@Deprecated
public class AttributeSerializationEntry implements SerializationEntry {

    private static final Logger LOGGER = Logger.getLogger(AttributeSerializationEntry.class.getName());

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
     * <p>
     * If the configured writer implements {@link AttributeValueWriter} and its
     * {@code canHandle()} method returns false for this attribute, a warning
     * is logged and the writer is not used (falls back to default serialization).
     * </p>
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
            CodecValueWriter<?, ?> writer = valueRegistry.getWriter(writerName).orElse(null);

            // Check canHandle() for AttributeValueWriter implementations
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
                // Generic CodecValueWriter - no canHandle() check needed
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
        } else if (value.getClass().isArray()) {
            // Array data types (e.g., double[], double[][], double[][][])
            writeArrayValue(gen, value, ctxt);
        } else {
            gen.writeString(value.toString());
        }
    }

    /**
     * Writes an array value to the generator.
     * Supports primitive arrays (double[], int[], etc.) and object arrays,
     * including multi-dimensional arrays (double[][], double[][][], etc.).
     *
     * @param gen the JSON generator
     * @param array the array value to write
     * @param ctxt the serialization context (may be null)
     */
    private void writeArrayValue(JsonGenerator gen, Object array, SerializationContext ctxt) {
        Class<?> componentType = array.getClass().getComponentType();

        gen.writeStartArray();

        if (componentType == double.class) {
            double[] arr = (double[]) array;
            for (double v : arr) {
                gen.writeNumber(v);
            }
        } else if (componentType == int.class) {
            int[] arr = (int[]) array;
            for (int v : arr) {
                gen.writeNumber(v);
            }
        } else if (componentType == long.class) {
            long[] arr = (long[]) array;
            for (long v : arr) {
                gen.writeNumber(v);
            }
        } else if (componentType == float.class) {
            float[] arr = (float[]) array;
            for (float v : arr) {
                gen.writeNumber(v);
            }
        } else if (componentType == boolean.class) {
            boolean[] arr = (boolean[]) array;
            for (boolean v : arr) {
                gen.writeBoolean(v);
            }
        } else if (componentType == short.class) {
            short[] arr = (short[]) array;
            for (short v : arr) {
                gen.writeNumber(v);
            }
        } else if (componentType == byte.class) {
            byte[] arr = (byte[]) array;
            for (byte v : arr) {
                gen.writeNumber(v);
            }
        } else {
            // Object array (including nested arrays for multi-dimensional)
            Object[] arr = (Object[]) array;
            for (Object element : arr) {
                if (element == null) {
                    gen.writeNull();
                } else if (element.getClass().isArray()) {
                    // Recursive call for multi-dimensional arrays
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
