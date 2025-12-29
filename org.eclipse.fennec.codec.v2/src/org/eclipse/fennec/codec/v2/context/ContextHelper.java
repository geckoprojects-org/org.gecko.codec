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
