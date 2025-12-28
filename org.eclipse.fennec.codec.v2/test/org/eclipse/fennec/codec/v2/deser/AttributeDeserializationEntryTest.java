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

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tools.jackson.core.JsonParser;

/**
 * Tests for {@link AttributeDeserializationEntry}.
 */
@DisplayName("AttributeDeserializationEntry")
class AttributeDeserializationEntryTest extends DeserializationEntryTestBase {

    @Nested
    @DisplayName("Construction")
    class Construction {

        @Test
        @DisplayName("creates entry with config and attribute")
        void createsEntryWithConfigAndAttribute() {
            EffectiveFeatureConfig config = createDefaultConfig("name", nameAttribute);
            AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, nameAttribute);

            assertEquals("name", entry.getKey());
            assertSame(nameAttribute, entry.getAttribute());
        }

        @Test
        @DisplayName("throws when config is null")
        void throwsWhenConfigIsNull() {
            assertThrows(NullPointerException.class,
                () -> new AttributeDeserializationEntry(null, nameAttribute));
        }

        @Test
        @DisplayName("throws when attribute is null")
        void throwsWhenAttributeIsNull() {
            EffectiveFeatureConfig config = createDefaultConfig("name", nameAttribute);
            assertThrows(NullPointerException.class,
                () -> new AttributeDeserializationEntry(config, null));
        }
    }

    @Nested
    @DisplayName("String attribute deserialization")
    class StringAttributeDeserialization {

        @Test
        @DisplayName("deserializes string value")
        void deserializesStringValue() {
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("\"John Doe\"")) {
                EffectiveFeatureConfig config = createDefaultConfig("name", nameAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, nameAttribute);

                entry.deserialize(state, parser, null);

                assertEquals("John Doe", person.eGet(nameAttribute));
            }
        }

        @Test
        @DisplayName("deserializes null string value")
        void deserializesNullStringValue() {
            EObject person = createPerson();
            person.eSet(nameAttribute, "Initial");
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("null")) {
                EffectiveFeatureConfig config = createDefaultConfig("name", nameAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, nameAttribute);

                entry.deserialize(state, parser, null);

                assertNull(person.eGet(nameAttribute));
            }
        }
    }

    @Nested
    @DisplayName("Integer attribute deserialization")
    class IntegerAttributeDeserialization {

        @Test
        @DisplayName("deserializes int value")
        void deserializesIntValue() {
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("30")) {
                EffectiveFeatureConfig config = createDefaultConfig("age", ageAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, ageAttribute);

                entry.deserialize(state, parser, null);

                assertEquals(30, person.eGet(ageAttribute));
            }
        }
    }

    @Nested
    @DisplayName("Boolean attribute deserialization")
    class BooleanAttributeDeserialization {

        @Test
        @DisplayName("deserializes true value")
        void deserializesTrueValue() {
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("true")) {
                EffectiveFeatureConfig config = createDefaultConfig("active", activeAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, activeAttribute);

                entry.deserialize(state, parser, null);

                assertEquals(Boolean.TRUE, person.eGet(activeAttribute));
            }
        }

        @Test
        @DisplayName("deserializes false value")
        void deserializesFalseValue() {
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("false")) {
                EffectiveFeatureConfig config = createDefaultConfig("active", activeAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, activeAttribute);

                entry.deserialize(state, parser, null);

                assertEquals(Boolean.FALSE, person.eGet(activeAttribute));
            }
        }
    }

    @Nested
    @DisplayName("Double attribute deserialization")
    class DoubleAttributeDeserialization {

        @Test
        @DisplayName("deserializes double value")
        void deserializesDoubleValue() {
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("95.5")) {
                EffectiveFeatureConfig config = createDefaultConfig("score", scoreAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, scoreAttribute);

                entry.deserialize(state, parser, null);

                assertEquals(95.5, (Double) person.eGet(scoreAttribute), 0.001);
            }
        }
    }

    @Nested
    @DisplayName("Multi-valued attribute deserialization")
    class MultiValuedAttributeDeserialization {

        @Test
        @DisplayName("deserializes string array")
        void deserializesStringArray() {
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("[\"tag1\", \"tag2\", \"tag3\"]")) {
                EffectiveFeatureConfig config = createDefaultConfig("tags", tagsAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, tagsAttribute);

                entry.deserialize(state, parser, null);

                @SuppressWarnings("unchecked")
                List<String> tags = (List<String>) person.eGet(tagsAttribute);
                assertEquals(3, tags.size());
                assertEquals("tag1", tags.get(0));
                assertEquals("tag2", tags.get(1));
                assertEquals("tag3", tags.get(2));
            }
        }

        @Test
        @DisplayName("deserializes empty array")
        void deserializesEmptyArray() {
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("[]")) {
                EffectiveFeatureConfig config = createDefaultConfig("tags", tagsAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, tagsAttribute);

                entry.deserialize(state, parser, null);

                @SuppressWarnings("unchecked")
                List<String> tags = (List<String>) person.eGet(tagsAttribute);
                assertTrue(tags.isEmpty());
            }
        }

        @Test
        @DisplayName("deserializes single value as array element")
        void deserializesSingleValueAsArrayElement() {
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            // Single value (not an array) for multi-valued attribute
            try (JsonParser parser = createParser("\"singleTag\"")) {
                EffectiveFeatureConfig config = createDefaultConfig("tags", tagsAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, tagsAttribute);

                entry.deserialize(state, parser, null);

                @SuppressWarnings("unchecked")
                List<String> tags = (List<String>) person.eGet(tagsAttribute);
                assertEquals(1, tags.size());
                assertEquals("singleTag", tags.get(0));
            }
        }
    }

    @Nested
    @DisplayName("Error handling")
    class ErrorHandling {

        @Test
        @DisplayName("handles missing EObject gracefully")
        void handlesMissingEObjectGracefully() {
            DeserializationState state = createState(personClass);
            // Note: EObject not created yet

            try (JsonParser parser = createParser("\"John\"")) {
                EffectiveFeatureConfig config = createDefaultConfig("name", nameAttribute);
                AttributeDeserializationEntry entry = new AttributeDeserializationEntry(config, nameAttribute);

                // Should not throw - just log warning
                assertDoesNotThrow(() -> entry.deserialize(state, parser, null));
            }
        }
    }

    private EffectiveFeatureConfig createDefaultConfig(String key, EAttribute attribute) {
        return EffectiveFeatureConfig.builder()
                .feature(attribute)
                .key(key)
                .serialize(true)
                .serializeNull(false)
                .serializeEmpty(false)
                .serializeDefaults(false)
                .build();
    }
}
