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
package org.eclipse.fennec.codec.v2.deser;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.eclipse.fennec.codec.v2.value.CodecValueReader;
import org.eclipse.fennec.codec.v2.value.CodecValueRegistry;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;

/**
 * Deserialization entry that handles EAttribute values.
 * <p>
 * Supports:
 * <ul>
 *   <li>Primitive types (String, int, long, double, boolean, etc.)</li>
 *   <li>EMF data types with custom conversion</li>
 *   <li>Multi-valued attributes (arrays)</li>
 *   <li>Null values</li>
 * </ul>
 * </p>
 *
 * @see EffectiveFeatureConfig
 * @see <a href="docs/codec-v2-serialization-spec.md#9-feature-serialization">Spec 9: Feature Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class AttributeDeserializationEntry implements DeserializationEntry {

    private static final Logger LOGGER = Logger.getLogger(AttributeDeserializationEntry.class.getName());

    private final EffectiveFeatureConfig config;
    private final EAttribute attribute;
    private final CodecValueReader<Object, EAttribute> customReader;

    /**
     * Creates a new AttributeDeserializationEntry.
     *
     * @param config the effective feature configuration
     * @param attribute the EAttribute to deserialize
     */
    public AttributeDeserializationEntry(EffectiveFeatureConfig config, EAttribute attribute) {
        this(config, attribute, null);
    }

    /**
     * Creates a new AttributeDeserializationEntry with custom value reader support.
     *
     * @param config the effective feature configuration
     * @param attribute the EAttribute to deserialize
     * @param valueRegistry the registry for custom value readers (may be null)
     */
    @SuppressWarnings("unchecked")
    public AttributeDeserializationEntry(EffectiveFeatureConfig config, EAttribute attribute,
            CodecValueRegistry valueRegistry) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.attribute = Objects.requireNonNull(attribute, "attribute must not be null");

        // Pre-resolve the custom reader at construction time
        String readerName = config.getValueReaderName();
        if (readerName != null && !readerName.isEmpty() && valueRegistry != null) {
            this.customReader = (CodecValueReader<Object, EAttribute>) valueRegistry.getReader(readerName).orElse(null);
        } else {
            this.customReader = null;
        }
    }

    @Override
    public String getKey() {
        return config.getKey();
    }

    @Override
    public void deserialize(DeserializationState state, JsonParser parser, DeserializationContext ctxt) {
        EObject eObject = state.getEObject();
        if (eObject == null) {
            LOGGER.warning("Cannot set attribute: EObject not yet created");
            return;
        }

        JsonToken token = parser.currentToken();

        if (token == JsonToken.VALUE_NULL) {
            // Null value - only set if the attribute is changeable
            if (attribute.isChangeable() && !attribute.isMany()) {
                eObject.eSet(attribute, null);
            }
            return;
        }

        if (attribute.isMany()) {
            deserializeMultiValued(state, parser, ctxt, eObject);
        } else {
            deserializeSingleValued(parser, ctxt, eObject);
        }
    }

    /**
     * Deserializes a single-valued attribute.
     */
    private void deserializeSingleValued(JsonParser parser, DeserializationContext ctxt, EObject eObject) {
        Object value = readValue(parser, ctxt, attribute.getEAttributeType());
        if (value != null && attribute.isChangeable()) {
            eObject.eSet(attribute, value);
        }
    }

    /**
     * Deserializes a multi-valued attribute (array).
     */
    @SuppressWarnings("unchecked")
    private void deserializeMultiValued(DeserializationState state, JsonParser parser,
            DeserializationContext ctxt, EObject eObject) {
        JsonToken token = parser.currentToken();

        if (token != JsonToken.START_ARRAY) {
            // Single value provided for multi-valued - wrap it
            Object value = readValue(parser, ctxt, attribute.getEAttributeType());
            if (value != null) {
                ((List<Object>) eObject.eGet(attribute)).add(value);
            }
            return;
        }

        List<Object> values = (List<Object>) eObject.eGet(attribute);

        while (parser.nextToken() != JsonToken.END_ARRAY) {
            Object value = readValue(parser, ctxt, attribute.getEAttributeType());
            if (value != null) {
                values.add(value);
            }
        }
    }

    /**
     * Reads a value from the parser and converts it to the appropriate type.
     * <p>
     * If a custom value reader is configured, it will be used instead of
     * the default deserialization logic.
     * </p>
     *
     * @param parser the JSON parser
     * @param ctxt the deserialization context (may be null)
     * @param dataType the target EMF data type
     * @return the converted value, or null if conversion fails
     */
    private Object readValue(JsonParser parser, DeserializationContext ctxt, EDataType dataType) {
        JsonToken token = parser.currentToken();

        // Handle null first - custom readers don't handle null
        if (token == JsonToken.VALUE_NULL) {
            return null;
        }

        // Use custom reader if configured
        if (customReader != null) {
            try {
                return customReader.read(parser, attribute, ctxt);
            } catch (IOException e) {
                throw new UncheckedIOException("Custom value reader failed for attribute: " + attribute.getName(), e);
            }
        }

        // Default deserialization
        Class<?> instanceClass = dataType.getInstanceClass();

        try {
            if (token == JsonToken.VALUE_STRING) {
                String stringValue = parser.getString();
                return convertFromString(stringValue, dataType, instanceClass);
            }

            if (token == JsonToken.VALUE_NUMBER_INT) {
                // Check if this is an enum with VALUE strategy
                if (dataType instanceof EEnum eEnum) {
                    return convertEnumFromInteger(parser, eEnum);
                }
                return convertFromInteger(parser, instanceClass);
            }

            if (token == JsonToken.VALUE_NUMBER_FLOAT) {
                return convertFromFloat(parser, instanceClass);
            }

            if (token == JsonToken.VALUE_TRUE || token == JsonToken.VALUE_FALSE) {
                boolean boolValue = parser.getBooleanValue();
                if (instanceClass == Boolean.class || instanceClass == boolean.class) {
                    return boolValue;
                }
                return EcoreUtil.createFromString(dataType, String.valueOf(boolValue));
            }

            LOGGER.warning("Unexpected token type for attribute " + attribute.getName() + ": " + token);
            return null;

        } catch (Exception e) {
            LOGGER.warning("Error converting value for attribute " + attribute.getName() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Converts a string value to the target type.
     */
    private Object convertFromString(String stringValue, EDataType dataType, Class<?> instanceClass) {
        if (instanceClass == String.class) {
            return stringValue;
        }
        // For enums, try both name and literal lookup for better compatibility
        if (dataType instanceof EEnum eEnum) {
            return convertEnumFromString(stringValue, eEnum);
        }
        // Use EMF's conversion mechanism for other data types
        return EcoreUtil.createFromString(dataType, stringValue);
    }

    /**
     * Converts a string value to an enum literal.
     * Tries name lookup first (for NAME strategy), then literal lookup (for LITERAL strategy).
     *
     * @param stringValue the string value
     * @param eEnum the target EEnum type
     * @return the enum literal's instance, or null if not found
     */
    private Object convertEnumFromString(String stringValue, EEnum eEnum) {
        // Try name lookup first (for NAME strategy serialization)
        EEnumLiteral literal = eEnum.getEEnumLiteral(stringValue);
        if (literal != null) {
            return literal.getInstance();
        }
        // Try literal lookup (for LITERAL strategy serialization)
        literal = eEnum.getEEnumLiteralByLiteral(stringValue);
        if (literal != null) {
            return literal.getInstance();
        }
        LOGGER.warning("No enum literal found for '" + stringValue + "' in " + eEnum.getName());
        return null;
    }

    /**
     * Converts an integer value to the target type.
     */
    private Object convertFromInteger(JsonParser parser, Class<?> instanceClass) {
        if (instanceClass == Integer.class || instanceClass == int.class) {
            return parser.getIntValue();
        }
        if (instanceClass == Long.class || instanceClass == long.class) {
            return parser.getLongValue();
        }
        if (instanceClass == Short.class || instanceClass == short.class) {
            return (short) parser.getIntValue();
        }
        if (instanceClass == Byte.class || instanceClass == byte.class) {
            return (byte) parser.getIntValue();
        }
        if (instanceClass == BigInteger.class) {
            return parser.getBigIntegerValue();
        }
        if (instanceClass == BigDecimal.class) {
            return new BigDecimal(parser.getLongValue());
        }
        if (instanceClass == Double.class || instanceClass == double.class) {
            return (double) parser.getLongValue();
        }
        if (instanceClass == Float.class || instanceClass == float.class) {
            return (float) parser.getLongValue();
        }
        // Default: return as long
        return parser.getLongValue();
    }

    /**
     * Converts an integer value to an enum literal.
     * Used when enums are serialized with VALUE strategy.
     *
     * @param parser the JSON parser
     * @param eEnum the target EEnum type
     * @return the enum literal's instance, or null if not found
     */
    private Object convertEnumFromInteger(JsonParser parser, EEnum eEnum) {
        int value = parser.getIntValue();
        EEnumLiteral literal = eEnum.getEEnumLiteral(value);
        if (literal != null) {
            return literal.getInstance();
        }
        LOGGER.warning("No enum literal found for value " + value + " in " + eEnum.getName());
        return null;
    }

    /**
     * Converts a float value to the target type.
     */
    private Object convertFromFloat(JsonParser parser, Class<?> instanceClass) {
        if (instanceClass == Double.class || instanceClass == double.class) {
            return parser.getDoubleValue();
        }
        if (instanceClass == Float.class || instanceClass == float.class) {
            return parser.getFloatValue();
        }
        if (instanceClass == BigDecimal.class) {
            return parser.getDecimalValue();
        }
        // Default: return as double
        return parser.getDoubleValue();
    }

    /**
     * Returns the attribute being deserialized.
     *
     * @return the EAttribute
     */
    public EAttribute getAttribute() {
        return attribute;
    }
}
