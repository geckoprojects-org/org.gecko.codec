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

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.eclipse.fennec.codec.v2.deser.DeserializationState.UnresolvedReference;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;

/**
 * Deserialization entry that handles EReference values.
 * <p>
 * Supports:
 * <ul>
 *   <li>Containment references (inline objects)</li>
 *   <li>Non-containment references ($ref objects)</li>
 *   <li>Multi-valued references (arrays)</li>
 *   <li>Null values</li>
 * </ul>
 * </p>
 * <p>
 * Non-containment references are stored as {@link UnresolvedReference} objects
 * for later resolution after all objects are deserialized.
 * </p>
 *
 * @see EffectiveFeatureConfig
 * @see <a href="docs/codec-v2-serialization-spec.md#5-reference-serialization">Spec 5: Reference Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class ReferenceDeserializationEntry implements DeserializationEntry {

    private static final Logger LOGGER = Logger.getLogger(ReferenceDeserializationEntry.class.getName());

    private final EffectiveFeatureConfig config;
    private final EReference reference;
    private final String refKey;

    /**
     * Creates a new ReferenceDeserializationEntry.
     *
     * @param config the effective feature configuration
     * @param reference the EReference to deserialize
     * @param refKey the key used for non-containment references (e.g., "$ref")
     */
    public ReferenceDeserializationEntry(EffectiveFeatureConfig config, EReference reference, String refKey) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.reference = Objects.requireNonNull(reference, "reference must not be null");
        this.refKey = Objects.requireNonNull(refKey, "refKey must not be null");
    }

    @Override
    public String getKey() {
        return config.getKey();
    }

    @Override
    public void deserialize(DeserializationState state, JsonParser parser, DeserializationContext ctxt) {
        EObject eObject = state.getEObject();
        if (eObject == null) {
            LOGGER.warning("Cannot set reference: EObject not yet created");
            return;
        }

        JsonToken token = parser.currentToken();

        if (token == JsonToken.VALUE_NULL) {
            // Null reference - only set if changeable and single-valued
            if (reference.isChangeable() && !reference.isMany()) {
                eObject.eSet(reference, null);
            }
            return;
        }

        if (reference.isMany()) {
            deserializeMultiValued(state, parser, ctxt, eObject);
        } else {
            deserializeSingleValued(state, parser, ctxt, eObject);
        }
    }

    /**
     * Deserializes a single-valued reference.
     */
    private void deserializeSingleValued(DeserializationState state, JsonParser parser,
            DeserializationContext ctxt, EObject eObject) {
        JsonToken token = parser.currentToken();

        if (token == JsonToken.START_OBJECT) {
            if (reference.isContainment()) {
                // Containment: deserialize inline object
                EObject child = deserializeContainedObject(state, parser, ctxt);
                if (child != null && reference.isChangeable()) {
                    eObject.eSet(reference, child);
                }
            } else {
                // Non-containment: read $ref
                String refUri = readRefUri(parser);
                if (refUri != null) {
                    state.addUnresolvedReference(new UnresolvedReference(eObject, reference, refUri, -1));
                }
            }
        } else {
            LOGGER.warning("Expected START_OBJECT for reference " + reference.getName() + ", got: " + token);
        }
    }

    /**
     * Deserializes a multi-valued reference (array).
     */
    @SuppressWarnings("unchecked")
    private void deserializeMultiValued(DeserializationState state, JsonParser parser,
            DeserializationContext ctxt, EObject eObject) {
        JsonToken token = parser.currentToken();

        if (token != JsonToken.START_ARRAY) {
            // Single object provided for multi-valued - treat as single element
            deserializeSingleElement(state, parser, ctxt, eObject, 0);
            return;
        }

        List<EObject> values = (List<EObject>) eObject.eGet(reference);
        int index = 0;

        while (parser.nextToken() != JsonToken.END_ARRAY) {
            if (reference.isContainment()) {
                EObject child = deserializeContainedObject(state, parser, ctxt);
                if (child != null) {
                    values.add(child);
                }
            } else {
                // Non-containment: read $ref
                String refUri = readRefObjectUri(parser);
                if (refUri != null) {
                    state.addUnresolvedReference(new UnresolvedReference(eObject, reference, refUri, index));
                }
            }
            index++;
        }
    }

    /**
     * Deserializes a single element for a multi-valued reference.
     */
    @SuppressWarnings("unchecked")
    private void deserializeSingleElement(DeserializationState state, JsonParser parser,
            DeserializationContext ctxt, EObject eObject, int index) {
        if (reference.isContainment()) {
            EObject child = deserializeContainedObject(state, parser, ctxt);
            if (child != null) {
                ((List<EObject>) eObject.eGet(reference)).add(child);
            }
        } else {
            String refUri = readRefObjectUri(parser);
            if (refUri != null) {
                state.addUnresolvedReference(new UnresolvedReference(eObject, reference, refUri, index));
            }
        }
    }

    /**
     * Deserializes a contained object.
     * <p>
     * Creates a nested deserialization state and recursively deserializes
     * the contained object.
     * </p>
     *
     * @param parentState the parent state
     * @param parser the JSON parser at START_OBJECT
     * @param ctxt the deserialization context
     * @return the deserialized EObject, or null on error
     */
    private EObject deserializeContainedObject(DeserializationState parentState, JsonParser parser,
            DeserializationContext ctxt) {
        // The actual deserialization is handled by CodecEObjectDeserializer
        // We delegate to the deserializer directly
        try {
            tools.jackson.databind.ValueDeserializer<Object> deser = ctxt.findRootValueDeserializer(
                    ctxt.constructType(EObject.class));
            if (deser != null) {
                return (EObject) deser.deserialize(parser, ctxt);
            }
            LOGGER.warning("No deserializer found for EObject");
            return null;
        } catch (Exception e) {
            LOGGER.warning("Error deserializing contained object for " + reference.getName() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Reads a reference URI from a $ref object.
     * <p>
     * Expected format: {@code {"$ref": "uri"}}
     * </p>
     *
     * @param parser the JSON parser at START_OBJECT
     * @return the reference URI, or null if not found
     */
    private String readRefUri(JsonParser parser) {
        String uri = null;

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.currentName();
            parser.nextToken(); // Move to value

            if (refKey.equals(fieldName)) {
                uri = parser.getText();
            }
            // Skip other fields
        }

        return uri;
    }

    /**
     * Reads a reference URI, handling both object format and potentially direct URI.
     *
     * @param parser the JSON parser
     * @return the reference URI, or null if not found
     */
    private String readRefObjectUri(JsonParser parser) {
        JsonToken token = parser.currentToken();

        if (token == JsonToken.START_OBJECT) {
            return readRefUri(parser);
        } else if (token == JsonToken.VALUE_STRING) {
            // Direct URI string (alternative format)
            return parser.getText();
        }

        LOGGER.warning("Expected START_OBJECT or VALUE_STRING for non-containment ref, got: " + token);
        return null;
    }

    /**
     * Returns the reference being deserialized.
     *
     * @return the EReference
     */
    public EReference getReference() {
        return reference;
    }

    /**
     * Returns the key used for non-containment references.
     *
     * @return the ref key (e.g., "$ref")
     */
    public String getRefKey() {
        return refKey;
    }
}
