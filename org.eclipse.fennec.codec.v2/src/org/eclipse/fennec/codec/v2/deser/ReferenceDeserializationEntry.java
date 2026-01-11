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
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.eclipse.fennec.codec.v2.context.ContextHelper;
import org.eclipse.fennec.codec.v2.context.EMFCodecReadContext;
import org.eclipse.fennec.codec.v2.deser.DeserializationState.UnresolvedReference;
import org.eclipse.fennec.codec.v2.jackson.CodecJsonReadContext;
import org.eclipse.fennec.codec.v2.value.CodecValueReader;
import org.eclipse.fennec.codec.v2.value.CodecValueRegistry;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.TokenStreamContext;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.util.TokenBuffer;

/**
 * Deserialization entry that handles EReference values.
 * <p>
 * Supports:
 * <ul>
 *   <li>Containment references (inline objects)</li>
 *   <li>Non-containment references ($ref objects) - creates proxies</li>
 *   <li>Expanded non-containment references (no $ref) - creates orphan objects</li>
 *   <li>Multi-valued references (arrays)</li>
 *   <li>Null values</li>
 * </ul>
 * </p>
 * <p>
 * Non-containment reference handling depends on the presence of the {@code _ref} field:
 * <ul>
 *   <li>With {@code _ref}: Creates a proxy that can be resolved later</li>
 *   <li>Without {@code _ref}: Deserializes as an orphan object (expanded reference)</li>
 * </ul>
 * Orphan objects are fully deserialized but not contained - they have no resource assigned.
 * </p>
 *
 * @see EffectiveFeatureConfig
 * @see <a href="docs/codec-v2-spec/07-reference.md">Spec: Reference Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class ReferenceDeserializationEntry implements DeserializationEntry {

    private static final Logger LOGGER = Logger.getLogger(ReferenceDeserializationEntry.class.getName());

    private final EffectiveFeatureConfig config;
    private final EReference reference;
    private final String refKey;
    private final CodecValueReader<String, EReference> customReader;

    /**
     * Creates a new ReferenceDeserializationEntry.
     *
     * @param config the effective feature configuration
     * @param reference the EReference to deserialize
     * @param refKey the key used for non-containment references (e.g., "$ref")
     */
    public ReferenceDeserializationEntry(EffectiveFeatureConfig config, EReference reference, String refKey) {
        this(config, reference, refKey, null);
    }

    /**
     * Creates a new ReferenceDeserializationEntry with custom reader support.
     *
     * @param config the effective feature configuration
     * @param reference the EReference to deserialize
     * @param refKey the key used for non-containment references (e.g., "$ref")
     * @param valueRegistry the registry for custom value readers (may be null)
     */
    @SuppressWarnings("unchecked")
    public ReferenceDeserializationEntry(EffectiveFeatureConfig config, EReference reference,
            String refKey, CodecValueRegistry valueRegistry) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.reference = Objects.requireNonNull(reference, "reference must not be null");
        this.refKey = Objects.requireNonNull(refKey, "refKey must not be null");

        // Pre-resolve the custom reader at construction time
        String readerName = config.getValueReaderName();
        if (readerName != null && !readerName.isEmpty() && valueRegistry != null) {
            this.customReader = (CodecValueReader<String, EReference>) valueRegistry.getReader(readerName).orElse(null);
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
                // Non-containment: check for $ref to determine proxy vs orphan
                deserializeNonContainmentObject(state, parser, ctxt, eObject, -1);
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
                // Non-containment: check for $ref to determine proxy vs orphan
                deserializeNonContainmentElement(state, parser, ctxt, eObject, values, index);
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
            List<EObject> values = (List<EObject>) eObject.eGet(reference);
            deserializeNonContainmentElement(state, parser, ctxt, eObject, values, index);
        }
    }

    /**
     * Deserializes a contained object.
     * <p>
     * Creates a child context for nested deserialization and recursively deserializes
     * the contained object. The EReference's eType is passed as a hint via the child
     * context to allow deserialization of nested objects without explicit _type.
     * </p>
     * <p>
     * This method uses proper context isolation:
     * <ul>
     *   <li>If using CodecJsonReadContext: creates child context with type hint</li>
     *   <li>Falls back to ContextHelper for backwards compatibility</li>
     * </ul>
     * </p>
     *
     * @param parentState the parent state
     * @param parser the JSON parser at START_OBJECT
     * @param ctxt the deserialization context
     * @return the deserialized EObject, or null on error
     */
    private EObject deserializeContainedObject(DeserializationState parentState, JsonParser parser,
            DeserializationContext ctxt) {
        try {
            // Check if we have an EMF-aware context from the parser
            TokenStreamContext streamContext = parser.streamReadContext();

            if (streamContext instanceof CodecJsonReadContext codecContext) {
                // Create a child context for this nested object
                // The child context inherits the metadata service but gets its own type hint
                CodecJsonReadContext childContext = codecContext.createChildObjectContext(
                        parser.currentLocation().getLineNr(),
                        parser.currentLocation().getColumnNr());

                // Set the reference type as hint for the nested deserialization
                childContext.setCurrentTypeHint(reference.getEReferenceType());

                // Set the current feature being deserialized
                childContext.setCurrentFeature(reference);

                // Note: The parser's _streamReadContext is managed internally by Jackson
                // We're setting up the context but the actual context switching happens
                // when the parser processes tokens. For now we also use ContextHelper
                // for the actual hint passing until we fully integrate context management.

                // Also set via ContextHelper for backwards compatibility
                EClass previousExpectedType = ContextHelper.getExpectedType(ctxt);
                ContextHelper.setExpectedType(ctxt, reference.getEReferenceType());

                try {
                    tools.jackson.databind.ValueDeserializer<Object> deser = ctxt.findRootValueDeserializer(
                            ctxt.constructType(EObject.class));
                    if (deser != null) {
                        return (EObject) deser.deserialize(parser, ctxt);
                    }
                    LOGGER.warning("No deserializer found for EObject");
                    return null;
                } finally {
                    // Restore the previous hint
                    if (previousExpectedType != null) {
                        ContextHelper.setExpectedType(ctxt, previousExpectedType);
                    } else {
                        ContextHelper.clearExpectedType(ctxt);
                    }
                }
            } else if (streamContext instanceof EMFCodecReadContext emfContext) {
                // Generic EMF context (non-JSON format) - set hint directly
                EClass previousHint = emfContext.getCurrentTypeHint();
                emfContext.setCurrentTypeHint(reference.getEReferenceType());

                try {
                    tools.jackson.databind.ValueDeserializer<Object> deser = ctxt.findRootValueDeserializer(
                            ctxt.constructType(EObject.class));
                    if (deser != null) {
                        return (EObject) deser.deserialize(parser, ctxt);
                    }
                    LOGGER.warning("No deserializer found for EObject");
                    return null;
                } finally {
                    emfContext.setCurrentTypeHint(previousHint);
                }
            } else {
                // Fall back to ContextHelper for non-EMF-aware parsers
                return deserializeWithContextHelper(parser, ctxt);
            }
        } catch (Exception e) {
            LOGGER.warning("Error deserializing contained object for " + reference.getName() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Deserializes a non-containment reference object (single-valued).
     * <p>
     * Determines whether this is a proxy reference (has {@code _ref}) or an orphan object
     * (expanded reference without {@code _ref}) by buffering and inspecting the content.
     * </p>
     *
     * @param state the deserialization state
     * @param parser the JSON parser at START_OBJECT
     * @param ctxt the deserialization context
     * @param eObject the parent EObject
     * @param index the index for multi-valued references (-1 for single-valued)
     */
    private void deserializeNonContainmentObject(DeserializationState state, JsonParser parser,
            DeserializationContext ctxt, EObject eObject, int index) {
        // If no DeserializationContext, use simple approach (for backwards compatibility with tests)
        if (ctxt == null) {
            String refUri = readRefUri(parser);
            if (refUri != null) {
                state.addUnresolvedReference(new UnresolvedReference(eObject, reference, refUri, index));
            }
            return;
        }

        try {
            // Buffer the object to inspect for _ref and check for additional fields
            TokenBuffer buffer = ctxt.bufferForInputBuffering(parser);
            buffer.copyCurrentStructure(parser);

            // Parse the buffered content to check for _ref and count other fields
            JsonParser bufferParser = buffer.asParser(ctxt, parser);
            bufferParser.nextToken(); // START_OBJECT

            String refUri = null;
            EClass typeFromContent = null;
            boolean hasOtherFields = false;

            while (bufferParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = bufferParser.currentName();
                bufferParser.nextToken(); // Move to value

                if (refKey.equals(fieldName)) {
                    refUri = readReferenceValue(bufferParser, ctxt);
                } else if ("_type".equals(fieldName)) {
                    // Try to resolve the type from _type field
                    String typeValue = bufferParser.getString();
                    typeFromContent = resolveTypeFromValue(typeValue, ctxt);
                } else if (!"_id".equals(fieldName)) {
                    // Has fields other than _ref, _type, _id -> potential projection
                    hasOtherFields = true;
                    bufferParser.skipChildren();
                } else {
                    bufferParser.skipChildren();
                }
            }
            bufferParser.close();

            if (refUri != null && hasOtherFields) {
                // Has _ref AND other fields: proxy with projection
                // Deserialize the full object and then set proxy URI
                JsonParser replayParser = buffer.asParser(ctxt, parser);
                replayParser.nextToken(); // Move to START_OBJECT
                EObject proxyWithProjection = deserializeFullObject(state, replayParser, ctxt);
                replayParser.close();

                if (proxyWithProjection != null) {
                    // Set the proxy URI on the deserialized object
                    org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(refUri);
                    ((org.eclipse.emf.ecore.InternalEObject) proxyWithProjection).eSetProxyURI(uri);

                    if (reference.isChangeable()) {
                        eObject.eSet(reference, proxyWithProjection);
                    }
                }
            } else if (refUri != null) {
                // Has _ref only: create simple proxy reference (resolve later)
                state.addUnresolvedReference(new UnresolvedReference(eObject, reference, refUri, index, typeFromContent));
            } else {
                // No _ref: deserialize as orphan object (expanded reference)
                JsonParser replayParser = buffer.asParser(ctxt, parser);
                replayParser.nextToken(); // Move to START_OBJECT
                EObject orphan = deserializeOrphanObject(state, replayParser, ctxt);
                replayParser.close();

                if (orphan != null && reference.isChangeable()) {
                    eObject.eSet(reference, orphan);
                }
            }

            buffer.close();
        } catch (Exception e) {
            LOGGER.warning("Error deserializing non-containment reference " + reference.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Deserializes a non-containment reference element for multi-valued references.
     * <p>
     * Similar to {@link #deserializeNonContainmentObject} but adds to a list instead of setting directly.
     * Supports:
     * <ul>
     *   <li>Simple proxy (_ref only)</li>
     *   <li>Proxy with projection (_ref + additional fields)</li>
     *   <li>Orphan object (no _ref, expanded reference)</li>
     * </ul>
     * </p>
     */
    private void deserializeNonContainmentElement(DeserializationState state, JsonParser parser,
            DeserializationContext ctxt, EObject eObject, List<EObject> values, int index) {
        JsonToken token = parser.currentToken();

        if (token == JsonToken.VALUE_STRING) {
            // Direct URI string (PLAIN format)
            String refUri = readReferenceValue(parser, ctxt);
            state.addUnresolvedReference(new UnresolvedReference(eObject, reference, refUri, index));
            return;
        }

        if (token != JsonToken.START_OBJECT) {
            LOGGER.warning("Expected START_OBJECT or VALUE_STRING for non-containment ref element, got: " + token);
            return;
        }

        // If no DeserializationContext, use simple approach (for backwards compatibility with tests)
        if (ctxt == null) {
            String refUri = readRefUri(parser);
            if (refUri != null) {
                state.addUnresolvedReference(new UnresolvedReference(eObject, reference, refUri, index));
            }
            return;
        }

        try {
            // Buffer the object to inspect for _ref and check for additional fields
            TokenBuffer buffer = ctxt.bufferForInputBuffering(parser);
            buffer.copyCurrentStructure(parser);

            // Parse the buffered content to check for _ref and count other fields
            JsonParser bufferParser = buffer.asParser(ctxt, parser);
            bufferParser.nextToken(); // START_OBJECT

            String refUri = null;
            EClass typeFromContent = null;
            boolean hasOtherFields = false;

            while (bufferParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = bufferParser.currentName();
                bufferParser.nextToken(); // Move to value

                if (refKey.equals(fieldName)) {
                    refUri = readReferenceValue(bufferParser, ctxt);
                } else if ("_type".equals(fieldName)) {
                    String typeValue = bufferParser.getString();
                    typeFromContent = resolveTypeFromValue(typeValue, ctxt);
                } else if (!"_id".equals(fieldName)) {
                    // Has fields other than _ref, _type, _id -> potential projection
                    hasOtherFields = true;
                    bufferParser.skipChildren();
                } else {
                    bufferParser.skipChildren();
                }
            }
            bufferParser.close();

            if (refUri != null && hasOtherFields) {
                // Has _ref AND other fields: proxy with projection
                JsonParser replayParser = buffer.asParser(ctxt, parser);
                replayParser.nextToken(); // Move to START_OBJECT
                EObject proxyWithProjection = deserializeFullObject(state, replayParser, ctxt);
                replayParser.close();

                if (proxyWithProjection != null) {
                    // Set the proxy URI on the deserialized object
                    org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(refUri);
                    ((org.eclipse.emf.ecore.InternalEObject) proxyWithProjection).eSetProxyURI(uri);
                    values.add(proxyWithProjection);
                }
            } else if (refUri != null) {
                // Has _ref only: create simple proxy reference (resolve later)
                state.addUnresolvedReference(new UnresolvedReference(eObject, reference, refUri, index, typeFromContent));
            } else {
                // No _ref: deserialize as orphan object (expanded reference)
                JsonParser replayParser = buffer.asParser(ctxt, parser);
                replayParser.nextToken(); // Move to START_OBJECT
                EObject orphan = deserializeOrphanObject(state, replayParser, ctxt);
                replayParser.close();

                if (orphan != null) {
                    values.add(orphan);
                }
            }

            buffer.close();
        } catch (Exception e) {
            LOGGER.warning("Error deserializing non-containment reference element " + reference.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Deserializes an orphan object (expanded non-containment reference).
     * <p>
     * Creates a fully populated EObject that is not contained by its parent.
     * The object has no resource assigned and exists only in memory.
     * </p>
     *
     * @param parentState the parent deserialization state
     * @param parser the JSON parser at START_OBJECT
     * @param ctxt the deserialization context
     * @return the deserialized orphan EObject, or null on error
     */
    private EObject deserializeOrphanObject(DeserializationState parentState, JsonParser parser,
            DeserializationContext ctxt) {
        // Orphan objects are deserialized similarly to contained objects,
        // but they are not added to the parent's containment.
        // They just have the non-containment reference set to them.
        return deserializeFullObject(parentState, parser, ctxt);
    }

    /**
     * Deserializes a full EObject using the standard deserializer.
     */
    private EObject deserializeFullObject(DeserializationState parentState, JsonParser parser,
            DeserializationContext ctxt) {
        try {
            // Set the reference type as hint for the nested deserialization
            EClass previousExpectedType = ContextHelper.getExpectedType(ctxt);
            ContextHelper.setExpectedType(ctxt, reference.getEReferenceType());

            try {
                tools.jackson.databind.ValueDeserializer<Object> deser = ctxt.findRootValueDeserializer(
                        ctxt.constructType(EObject.class));
                if (deser != null) {
                    return (EObject) deser.deserialize(parser, ctxt);
                }
                LOGGER.warning("No deserializer found for EObject");
                return null;
            } finally {
                // Restore the previous hint
                if (previousExpectedType != null) {
                    ContextHelper.setExpectedType(ctxt, previousExpectedType);
                } else {
                    ContextHelper.clearExpectedType(ctxt);
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error deserializing object for " + reference.getName() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Resolves an EClass from a type value string.
     *
     * @param typeValue the type value (URI or discriminator)
     * @param ctxt the deserialization context
     * @return the resolved EClass, or null if not found
     */
    private EClass resolveTypeFromValue(String typeValue, DeserializationContext ctxt) {
        // Try to resolve as URI first
        if (typeValue != null && typeValue.contains("#")) {
            org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(typeValue);
            org.eclipse.emf.ecore.EPackage.Registry registry = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE;
            String nsUri = uri.trimFragment().toString();
            org.eclipse.emf.ecore.EPackage ePackage = registry.getEPackage(nsUri);
            if (ePackage != null) {
                org.eclipse.emf.ecore.EClassifier classifier = ePackage.getEClassifier(uri.fragment().replace("//", ""));
                if (classifier instanceof EClass) {
                    return (EClass) classifier;
                }
            }
        }
        return null;
    }

    /**
     * Fallback deserialization using ContextHelper for non-EMF-aware parsers.
     */
    private EObject deserializeWithContextHelper(JsonParser parser, DeserializationContext ctxt) {
        EClass previousExpectedType = ContextHelper.getExpectedType(ctxt);
        ContextHelper.setExpectedType(ctxt, reference.getEReferenceType());

        try {
            tools.jackson.databind.ValueDeserializer<Object> deser = ctxt.findRootValueDeserializer(
                    ctxt.constructType(EObject.class));
            if (deser != null) {
                return (EObject) deser.deserialize(parser, ctxt);
            }
            LOGGER.warning("No deserializer found for EObject");
            return null;
        } finally {
            if (previousExpectedType != null) {
                ContextHelper.setExpectedType(ctxt, previousExpectedType);
            } else {
                ContextHelper.clearExpectedType(ctxt);
            }
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
        return readRefUri(parser, null);
    }

    /**
     * Reads a reference URI from a $ref object with optional custom reader support.
     * <p>
     * Expected format: {@code {"$ref": "uri"}}
     * </p>
     * <p>
     * If a custom value reader is configured, it reads the reference value
     * (the _ref field content) instead of the default parser.getString() logic.
     * </p>
     *
     * @param parser the JSON parser at START_OBJECT
     * @param ctxt the deserialization context (may be null)
     * @return the reference URI, or null if not found
     * @see <a href="docs/codec-v2-spec/10-custom-values.md#5-reference-value-readerswriters">Spec: Reference Value Readers</a>
     */
    private String readRefUri(JsonParser parser, DeserializationContext ctxt) {
        String uri = null;

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.currentName();
            parser.nextToken(); // Move to value

            if (refKey.equals(fieldName)) {
                uri = readReferenceValue(parser, ctxt);
            }
            // Skip other fields
        }

        return uri;
    }

    /**
     * Reads the reference value (the _ref field content).
     * <p>
     * If a custom value reader is configured, it is used to read the value.
     * Otherwise, the default parser.getString() is used.
     * </p>
     *
     * @param parser the JSON parser positioned at the value token
     * @param ctxt the deserialization context (may be null)
     * @return the reference value string
     */
    private String readReferenceValue(JsonParser parser, DeserializationContext ctxt) {
        // Use custom reader if configured
        if (customReader != null) {
            try {
                return customReader.read(parser, reference, ctxt);
            } catch (IOException e) {
                throw new UncheckedIOException(
                        "Custom value reader failed for reference: " + reference.getName(), e);
            }
        }

        // Default: read string directly
        return parser.getString();
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
