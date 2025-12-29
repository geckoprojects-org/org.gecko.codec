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

import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveIdConfig;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;

/**
 * Deserialization entry that handles ID property (_id).
 * <p>
 * Reads the ID value and sets it on the EObject's ID attribute.
 * The ID attribute is determined by:
 * <ol>
 *   <li>The EClass's {@code eIDAttribute}</li>
 *   <li>The first attribute with {@code isID=true}</li>
 * </ol>
 * </p>
 *
 * @see EffectiveIdConfig
 * @see <a href="docs/codec-v2-serialization-spec.md#4-id-serialization">Spec 4: ID Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class IdDeserializationEntry implements DeserializationEntry {

    private static final Logger LOGGER = Logger.getLogger(IdDeserializationEntry.class.getName());

    private final EffectiveIdConfig config;
    private final EAttribute idAttribute;

    /**
     * Creates a new IdDeserializationEntry.
     *
     * @param config the effective ID configuration
     * @param eClass the EClass to find the ID attribute from
     */
    public IdDeserializationEntry(EffectiveIdConfig config, EClass eClass) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.idAttribute = findIdAttribute(eClass);
    }

    /**
     * Creates a new IdDeserializationEntry with explicit ID attribute.
     *
     * @param config the effective ID configuration
     * @param idAttribute the ID attribute (may be null if ID is URI-based)
     */
    public IdDeserializationEntry(EffectiveIdConfig config, EAttribute idAttribute) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.idAttribute = idAttribute;
    }

    @Override
    public String getKey() {
        return config.getKey();
    }

    @Override
    public void deserialize(DeserializationState state, JsonParser parser, DeserializationContext ctxt) {
        EObject eObject = state.getEObject();
        if (eObject == null) {
            LOGGER.warning("Cannot set ID: EObject not yet created");
            return;
        }

        JsonToken token = parser.currentToken();
        if (token == JsonToken.VALUE_NULL) {
            // Null ID - nothing to set
            return;
        }

        if (idAttribute != null) {
            // Set ID on the ID attribute
            Object idValue = readIdValue(parser, idAttribute);
            if (idValue != null) {
                eObject.eSet(idAttribute, idValue);
            }
        } else {
            // No ID attribute - ID may be used for resource URI fragment
            String idString = parser.getString();
            LOGGER.fine("ID value without ID attribute: " + idString + " (may be used for URI fragment)");
        }
    }

    /**
     * Reads the ID value and converts it to the appropriate type.
     *
     * @param parser the JSON parser
     * @param attribute the ID attribute
     * @return the converted ID value, or null if conversion fails
     */
    private Object readIdValue(JsonParser parser, EAttribute attribute) {
        JsonToken token = parser.currentToken();
        Class<?> instanceClass = attribute.getEAttributeType().getInstanceClass();

        try {
            if (token == JsonToken.VALUE_STRING) {
                String stringValue = parser.getString();
                // Use EMF's conversion for the attribute type
                return EcoreUtil.createFromString(attribute.getEAttributeType(), stringValue);
            } else if (token == JsonToken.VALUE_NUMBER_INT) {
                if (instanceClass == Integer.class || instanceClass == int.class) {
                    return parser.getIntValue();
                } else if (instanceClass == Long.class || instanceClass == long.class) {
                    return parser.getLongValue();
                } else {
                    // Convert to string and use EMF conversion
                    return EcoreUtil.createFromString(attribute.getEAttributeType(),
                            String.valueOf(parser.getLongValue()));
                }
            } else if (token == JsonToken.VALUE_NUMBER_FLOAT) {
                return EcoreUtil.createFromString(attribute.getEAttributeType(),
                        String.valueOf(parser.getDoubleValue()));
            } else {
                LOGGER.warning("Unexpected token type for ID: " + token);
                return null;
            }
        } catch (Exception e) {
            LOGGER.warning("Error converting ID value: " + e.getMessage());
            return null;
        }
    }

    /**
     * Finds the ID attribute for an EClass.
     *
     * @param eClass the EClass
     * @return the ID attribute, or null if none found
     */
    private EAttribute findIdAttribute(EClass eClass) {
        if (eClass == null) {
            return null;
        }

        // First check for explicit eIDAttribute
        EAttribute eIdAttr = eClass.getEIDAttribute();
        if (eIdAttr != null) {
            return eIdAttr;
        }

        // Then look for any attribute with isID=true
        for (EAttribute attr : eClass.getEAllAttributes()) {
            if (attr.isID()) {
                return attr;
            }
        }

        return null;
    }

    /**
     * Returns the ID attribute being used.
     *
     * @return the ID attribute, or null if URI-based ID
     */
    public EAttribute getIdAttribute() {
        return idAttribute;
    }
}
