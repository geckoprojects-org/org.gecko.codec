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
package org.eclipse.fennec.codec.v2.context;

import org.eclipse.emf.ecore.EClass;

import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;

/**
 * Helper class for managing codec context attributes.
 * <p>
 * Provides type-safe access to context attributes used during
 * EObject serialization and deserialization.
 * </p>
 *
 * @author Mark Hoffmann
 * @since 2025-12-29
 */
public final class ContextHelper {

    /** Context attribute key for expected type hint (must be EClass) */
    public static final String EXPECTED_TYPE = "CODEC_EXPECTED_TYPE";

    /** Context attribute key for collecting unresolved references */
    public static final String UNRESOLVED_REFERENCES = "CODEC_UNRESOLVED_REFERENCES";

    /**
     * Context attribute key for suppressing type serialization.
     * <p>
     * When set to true, the next EObject serialization should skip writing the _type field.
     * This is used by smart compression when instance type == reference type.
     * </p>
     */
    public static final String SUPPRESS_TYPE = "CODEC_SUPPRESS_TYPE";

    /**
     * Context attribute key for the context schema URI (root namespace).
     * <p>
     * Used by same-schema smart compression to determine if a type belongs
     * to the same schema as the root object. When set, types from this
     * schema will be written as simple names instead of full URIs.
     * </p>
     *
     * @see <a href="docs/codec-v2-spec/04-global-options.md#1-smart-compression">Spec: Smart Compression</a>
     */
    public static final String CONTEXT_SCHEMA_URI = "CODEC_CONTEXT_SCHEMA_URI";

    /**
     * Context attribute key indicating if root object serialization is complete.
     * <p>
     * Used by smart compression to ensure the root object always uses a full URI
     * (to establish the context schema), while contained objects can use simple names.
     * </p>
     */
    public static final String ROOT_SERIALIZED = "CODEC_ROOT_SERIALIZED";

    private ContextHelper() {
        // Static helper class
    }

    /**
     * Gets the expected EClass type from the deserialization context.
     * <p>
     * The expected type is used as a fallback when no explicit _type field
     * is present in the JSON. It can be set from:
     * <ul>
     *   <li>CODEC_ROOT_OBJECT option (resolved before deserialization starts)</li>
     *   <li>EReference.eType (for nested containment objects)</li>
     * </ul>
     * </p>
     *
     * @param ctxt the deserialization context
     * @return the expected EClass, or null if not set
     * @throws IllegalStateException if the attribute is set but not an EClass
     */
    public static EClass getExpectedType(DeserializationContext ctxt) {
        Object value = ctxt.getAttribute(EXPECTED_TYPE);
        return validateExpectedType(value);
    }

    /**
     * Gets the expected EClass type from the serialization context.
     *
     * @param ctxt the serialization context
     * @return the expected EClass, or null if not set
     * @throws IllegalStateException if the attribute is set but not an EClass
     */
    public static EClass getExpectedType(SerializationContext ctxt) {
        Object value = ctxt.getAttribute(EXPECTED_TYPE);
        return validateExpectedType(value);
    }

    /**
     * Sets the expected EClass type in the deserialization context.
     *
     * @param ctxt the deserialization context
     * @param eClass the expected EClass (must not be null)
     * @throws IllegalArgumentException if eClass is null
     */
    public static void setExpectedType(DeserializationContext ctxt, EClass eClass) {
        if (eClass == null) {
            throw new IllegalArgumentException("eClass must not be null");
        }
        ctxt.setAttribute(EXPECTED_TYPE, eClass);
    }

    /**
     * Sets the expected EClass type in the serialization context.
     *
     * @param ctxt the serialization context
     * @param eClass the expected EClass (must not be null)
     * @throws IllegalArgumentException if eClass is null
     */
    public static void setExpectedType(SerializationContext ctxt, EClass eClass) {
        if (eClass == null) {
            throw new IllegalArgumentException("eClass must not be null");
        }
        ctxt.setAttribute(EXPECTED_TYPE, eClass);
    }

    /**
     * Clears the expected type from the deserialization context.
     *
     * @param ctxt the deserialization context
     */
    public static void clearExpectedType(DeserializationContext ctxt) {
        ctxt.setAttribute(EXPECTED_TYPE, null);
    }

    /**
     * Clears the expected type from the serialization context.
     *
     * @param ctxt the serialization context
     */
    public static void clearExpectedType(SerializationContext ctxt) {
        ctxt.setAttribute(EXPECTED_TYPE, null);
    }

    // ========================================================================
    // Suppress Type Methods (for smart compression)
    // ========================================================================

    /**
     * Checks if type serialization should be suppressed.
     * <p>
     * Returns true if smart compression determined that _type should be omitted
     * because instance type == reference type.
     * </p>
     *
     * @param ctxt the serialization context
     * @return true if type should be suppressed
     */
    public static boolean isSuppressType(SerializationContext ctxt) {
        Object value = ctxt.getAttribute(SUPPRESS_TYPE);
        return Boolean.TRUE.equals(value);
    }

    /**
     * Sets whether type serialization should be suppressed.
     *
     * @param ctxt the serialization context
     * @param suppress true to suppress type serialization
     */
    public static void setSuppressType(SerializationContext ctxt, boolean suppress) {
        ctxt.setAttribute(SUPPRESS_TYPE, suppress);
    }

    /**
     * Clears the suppress type flag.
     *
     * @param ctxt the serialization context
     */
    public static void clearSuppressType(SerializationContext ctxt) {
        ctxt.setAttribute(SUPPRESS_TYPE, null);
    }

    // ========================================================================
    // Context Schema Methods (for smart compression)
    // ========================================================================

    /**
     * Gets the context schema URI from the serialization context.
     * <p>
     * The context schema is derived from the root object and used by smart
     * compression to determine if a type belongs to the same schema.
     * </p>
     *
     * @param ctxt the serialization context
     * @return the context schema URI, or null if not set
     */
    public static String getContextSchemaUri(SerializationContext ctxt) {
        Object value = ctxt.getAttribute(CONTEXT_SCHEMA_URI);
        return value instanceof String ? (String) value : null;
    }

    /**
     * Gets the context schema URI from the deserialization context.
     *
     * @param ctxt the deserialization context
     * @return the context schema URI, or null if not set
     */
    public static String getContextSchemaUri(DeserializationContext ctxt) {
        Object value = ctxt.getAttribute(CONTEXT_SCHEMA_URI);
        return value instanceof String ? (String) value : null;
    }

    /**
     * Sets the context schema URI in the serialization context.
     *
     * @param ctxt the serialization context
     * @param schemaUri the context schema URI
     */
    public static void setContextSchemaUri(SerializationContext ctxt, String schemaUri) {
        ctxt.setAttribute(CONTEXT_SCHEMA_URI, schemaUri);
    }

    /**
     * Sets the context schema URI in the deserialization context.
     *
     * @param ctxt the deserialization context
     * @param schemaUri the context schema URI
     */
    public static void setContextSchemaUri(DeserializationContext ctxt, String schemaUri) {
        ctxt.setAttribute(CONTEXT_SCHEMA_URI, schemaUri);
    }

    /**
     * Checks if root object serialization is complete.
     * <p>
     * Smart compression should only apply simple names after the root object
     * has been serialized (root must use full URI to establish context).
     * </p>
     *
     * @param ctxt the serialization context
     * @return true if root object serialization is complete
     */
    public static boolean isRootSerialized(SerializationContext ctxt) {
        Object value = ctxt.getAttribute(ROOT_SERIALIZED);
        return Boolean.TRUE.equals(value);
    }

    /**
     * Marks root object serialization as complete.
     * <p>
     * Called after the root object's type is serialized to enable
     * smart compression for contained objects.
     * </p>
     *
     * @param ctxt the serialization context
     */
    public static void setRootSerialized(SerializationContext ctxt) {
        ctxt.setAttribute(ROOT_SERIALIZED, Boolean.TRUE);
    }

    /**
     * Checks if a type URI belongs to the context schema.
     * <p>
     * Used by smart compression to determine if a type should be written
     * as a simple name instead of a full URI.
     * </p>
     *
     * @param ctxt the serialization context
     * @param typeUri the full type URI (e.g., "http://example.org/1.0#//Person")
     * @return true if the type belongs to the context schema
     */
    public static boolean isSameSchema(SerializationContext ctxt, String typeUri) {
        String contextSchema = getContextSchemaUri(ctxt);
        if (contextSchema == null || typeUri == null) {
            return false;
        }
        // Type URI format: "http://example.org/1.0#//Person"
        // Context schema: "http://example.org/1.0"
        return typeUri.startsWith(contextSchema + "#");
    }

    /**
     * Extracts the simple type name from a full URI.
     * <p>
     * Example: "http://example.org/1.0#//Person" → "Person"
     * </p>
     *
     * @param typeUri the full type URI
     * @return the simple type name, or the original URI if not in expected format
     */
    public static String extractSimpleName(String typeUri) {
        if (typeUri == null) {
            return null;
        }
        int fragmentIndex = typeUri.indexOf("#//");
        if (fragmentIndex >= 0) {
            return typeUri.substring(fragmentIndex + 3);
        }
        return typeUri;
    }

    /**
     * Extracts the schema URI from a full type URI.
     * <p>
     * Example: "http://example.org/1.0#//Person" → "http://example.org/1.0"
     * </p>
     *
     * @param typeUri the full type URI
     * @return the schema URI, or null if not in expected format
     */
    public static String extractSchemaUri(String typeUri) {
        if (typeUri == null) {
            return null;
        }
        int fragmentIndex = typeUri.indexOf("#//");
        if (fragmentIndex >= 0) {
            return typeUri.substring(0, fragmentIndex);
        }
        return null;
    }

    /**
     * Validates that the expected type value is an EClass or null.
     *
     * @param value the value to validate
     * @return the EClass, or null if value is null
     * @throws IllegalStateException if value is not null and not an EClass
     */
    private static EClass validateExpectedType(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof EClass) {
            return (EClass) value;
        }
        throw new IllegalStateException(
                EXPECTED_TYPE + " must be of type EClass, but was: " + value.getClass().getName());
    }
}
