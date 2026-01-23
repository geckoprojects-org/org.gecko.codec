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
package org.eclipse.fennec.model.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fennec.model.metadata.impl.BaseIdConfigImpl;
import org.junit.jupiter.api.Test;

/**
 * Tests for model configuration defaults and enum values.
 * <p>
 * These tests verify that the Ecore model defaults match the spec requirements.
 * </p>
 */
class ModelConfigDefaultsTest {

    // ========================================================================
    // TypeStrategy Enum Tests
    // ========================================================================

    @Test
    void testTypeStrategyValues() {
        // Verify all expected values exist
        assertNotNull(TypeStrategy.NAME);
        assertNotNull(TypeStrategy.CLASS);
        assertNotNull(TypeStrategy.URI);
        assertNotNull(TypeStrategy.SCHEMA_AND_TYPE);
        assertNotNull(TypeStrategy.NUMERIC);

        // Verify exactly 5 values (MAPPED was removed)
        assertEquals(5, TypeStrategy.VALUES.size());
    }

    @Test
    void testTypeStrategyMappedRemoved() {
        // MAPPED should no longer exist - verify by name lookup
        assertNull(TypeStrategy.getByName("MAPPED"));
        assertNull(TypeStrategy.get("MAPPED"));
    }

    @Test
    void testTypeStrategyOrdinalValues() {
        // Verify ordinal values after MAPPED removal
        assertEquals(0, TypeStrategy.NAME_VALUE);
        assertEquals(1, TypeStrategy.CLASS_VALUE);
        assertEquals(2, TypeStrategy.URI_VALUE);
        assertEquals(3, TypeStrategy.SCHEMA_AND_TYPE_VALUE);
        assertEquals(4, TypeStrategy.NUMERIC_VALUE);
    }

    @Test
    void testTypeStrategyGetByValue() {
        assertEquals(TypeStrategy.NAME, TypeStrategy.get(0));
        assertEquals(TypeStrategy.CLASS, TypeStrategy.get(1));
        assertEquals(TypeStrategy.URI, TypeStrategy.get(2));
        assertEquals(TypeStrategy.SCHEMA_AND_TYPE, TypeStrategy.get(3));
        assertEquals(TypeStrategy.NUMERIC, TypeStrategy.get(4));

        // Value 5 should not exist (was MAPPED's old value before renumbering)
        assertNull(TypeStrategy.get(5));
    }

    // ========================================================================
    // BaseIdConfig Default Tests
    // ========================================================================

    @Test
    void testBaseIdConfigDefaults() {
        // Use a concrete implementation to test defaults
        BaseIdConfig config = new TestBaseIdConfig();

        // Existing defaults
        assertEquals(IdStrategy.ID_FIELD, config.getStrategy());
        assertEquals(IdKeyMode.ID_ONLY, config.getKeyMode());
        assertEquals(SerializationFormat.PLAIN, config.getFormat());
        assertEquals("_id", config.getIdKey());
        assertEquals("-", config.getSeparator());

        // New defaults added for spec alignment
        assertTrue(config.isOnTop(), "onTop should default to true");
        assertTrue(config.isSerializeSeparator(), "serializeSeparator should default to true");
        assertEquals("separator", config.getSeparatorKey());
    }

    @Test
    void testBaseIdConfigOnTopDefault() {
        BaseIdConfig config = new TestBaseIdConfig();

        // onTop=true means _id appears before _type (useful for MongoDB indexing)
        assertTrue(config.isOnTop());
    }

    @Test
    void testBaseIdConfigSerializeSeparatorDefault() {
        BaseIdConfig config = new TestBaseIdConfig();

        // serializeSeparator=true means separator is included in STRUCTURED format
        // allowing deserialization without pre-configuration
        assertTrue(config.isSerializeSeparator());
    }

    @Test
    void testBaseIdConfigSeparatorKeyDefault() {
        BaseIdConfig config = new TestBaseIdConfig();

        // separatorKey="separator" is the JSON key for separator in STRUCTURED format
        assertEquals("separator", config.getSeparatorKey());
    }

    // ========================================================================
    // Other Enum Tests
    // ========================================================================

    @Test
    void testIdStrategyValues() {
        assertEquals(3, IdStrategy.VALUES.size());
        assertNotNull(IdStrategy.ID_FIELD);
        assertNotNull(IdStrategy.COMBINED);
        assertNotNull(IdStrategy.NONE);
    }

    @Test
    void testIdKeyModeValues() {
        assertEquals(3, IdKeyMode.VALUES.size());
        assertNotNull(IdKeyMode.ID_ONLY);
        assertNotNull(IdKeyMode.BOTH);
        assertNotNull(IdKeyMode.FEATURE_ONLY);
    }

    @Test
    void testSerializationFormatValues() {
        assertEquals(2, SerializationFormat.VALUES.size());
        assertNotNull(SerializationFormat.PLAIN);
        assertNotNull(SerializationFormat.STRUCTURED);
    }

    // ========================================================================
    // Test Implementation
    // ========================================================================

    /**
     * Concrete implementation of BaseIdConfig for testing defaults.
     */
    private static class TestBaseIdConfig extends BaseIdConfigImpl {
        // Uses default implementation - tests that defaults are set correctly
    }
}
