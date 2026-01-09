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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveTypeConfig;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tools.jackson.core.JsonParser;

/**
 * Tests for {@link TypeDeserializationEntry}.
 * <p>
 * Tests the Type deserialization for both PLAIN and STRUCTURED formats:
 * <ul>
 *   <li>PLAIN format: URI, NAME, CLASS, MAPPED, NUMERIC strategies</li>
 *   <li>STRUCTURED format: All strategies with unified "type" key</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/05-type.md">Spec: Type Serialization</a>
 */
@DisplayName("TypeDeserializationEntry")
class TypeDeserializationEntryTest extends DeserializationEntryTestBase {

    @BeforeEach
    void registerPackage() {
        // Register the test package in the global registry for type resolution
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);
    }

    @AfterEach
    void unregisterPackage() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
    }

    // ========================================================================
    // PLAIN Format Tests
    // ========================================================================

    @Nested
    @DisplayName("PLAIN format")
    class PlainFormatTests {

        @Test
        @DisplayName("URI strategy: deserializes full EClass URI")
        void uriStrategy_deserializesFullUri() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.URI)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            assertEquals("_type", entry.getKey());

            DeserializationState state = createState(null);

            // Person class URI
            String uri = testPackage.getNsURI() + "#//Person";
            try (JsonParser parser = createParser("\"" + uri + "\"")) {
                entry.deserialize(state, parser, null);
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("NAME strategy: deserializes simple class name")
        void nameStrategy_deserializesSimpleName() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.NAME)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            try (JsonParser parser = createParser("\"Person\"")) {
                entry.deserialize(state, parser, null);
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("NUMERIC strategy: deserializes classifier ID as string")
        void numericStrategy_deserializesClassifierId() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.NUMERIC)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // PLAIN NUMERIC: "_type": "N" where N is the classifier ID
            int classifierId = personClass.getClassifierID();
            try (JsonParser parser = createParser("\"" + classifierId + "\"")) {
                entry.deserialize(state, parser, null);
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("NUMERIC strategy: uses hint package for disambiguation")
        void numericStrategy_usesHintPackageForDisambiguation() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.NUMERIC)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // Use Address class's classifier ID with Person as hint (same package)
            int addressClassifierId = addressClass.getClassifierID();
            try (JsonParser parser = createParser("\"" + addressClassifierId + "\"")) {
                // With personClass as hint, lookup should use its package
                entry.deserializeWithHint(state, parser, null, personClass);
                assertEquals(addressClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("NUMERIC strategy: handles invalid classifier ID gracefully")
        void numericStrategy_handlesInvalidClassifierId() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.NUMERIC)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // Invalid: non-numeric value
            try (JsonParser parser = createParser("\"not-a-number\"")) {
                entry.deserialize(state, parser, null);
                assertNull(state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("NUMERIC strategy: handles non-existent classifier ID gracefully")
        void numericStrategy_handlesNonExistentClassifierId() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.NUMERIC)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // Non-existent classifier ID (very high number)
            try (JsonParser parser = createParser("\"99999\"")) {
                entry.deserialize(state, parser, null);
                assertNull(state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("getKey returns configured type key")
        void getKeyReturnsConfiguredTypeKey() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .typeKey("@class")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            assertEquals("@class", entry.getKey());
        }

        @Test
        @DisplayName("handles unknown type gracefully")
        void handlesUnknownTypeGracefully() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.NAME)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            try (JsonParser parser = createParser("\"NonExistentClass\"")) {
                entry.deserialize(state, parser, null);
                assertNull(state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("SCHEMA_AND_TYPE strategy: deserializes with separate schema hint")
        void schemaAndTypeStrategy_deserializesWithSchemaHint() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // PLAIN SCHEMA_AND_TYPE: schema provided separately, type is simple name
            try (JsonParser parser = createParser("\"Person\"")) {
                entry.deserializeWithSchemaHint(state, parser, null, null, testPackage.getNsURI());
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("SCHEMA_AND_TYPE strategy: schema hint ignored for full URI")
        void schemaAndTypeStrategy_schemaHintIgnoredForFullUri() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("_type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // If type is already a full URI, schema hint should be ignored
            String fullUri = testPackage.getNsURI() + "#//Person";
            try (JsonParser parser = createParser("\"" + fullUri + "\"")) {
                entry.deserializeWithSchemaHint(state, parser, null, null, "http://other.schema/1.0");
                assertEquals(personClass, state.getResolvedEClass());
            }
        }
    }

    // ========================================================================
    // STRUCTURED Format Tests
    // ========================================================================

    @Nested
    @DisplayName("STRUCTURED format")
    class StructuredFormatTests {

        @Test
        @DisplayName("URI strategy: deserializes nested object with type key containing URI")
        void uriStrategy_deserializesStructuredUri() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.URI)
                    .typeKey("_type")
                    .schemaKey("schema")
                    .nameKey("type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // STRUCTURED URI: {"type": "http://...#//Person"}
            String uri = testPackage.getNsURI() + "#//Person";
            String json = "{\"type\": \"" + uri + "\"}";
            try (JsonParser parser = createParser(json)) {
                entry.deserialize(state, parser, null);
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("NAME strategy: deserializes nested object with type key containing simple name")
        void nameStrategy_deserializesStructuredName() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.NAME)
                    .typeKey("_type")
                    .schemaKey("schema")
                    .nameKey("type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // STRUCTURED NAME: {"type": "Person"}
            String json = "{\"type\": \"Person\"}";
            try (JsonParser parser = createParser(json)) {
                entry.deserialize(state, parser, null);
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("SCHEMA_AND_TYPE strategy: deserializes nested object with schema and type")
        void schemaAndTypeStrategy_deserializesStructured() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("_type")
                    .schemaKey("schema")
                    .nameKey("type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // STRUCTURED SCHEMA_AND_TYPE: {"schema": "...", "type": "Person"}
            String json = "{\"schema\": \"" + testPackage.getNsURI() + "\", \"type\": \"Person\"}";
            try (JsonParser parser = createParser(json)) {
                entry.deserialize(state, parser, null);
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("NUMERIC strategy: deserializes nested object with schema and classifier")
        void numericStrategy_deserializesStructuredClassifier() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.NUMERIC)
                    .typeKey("_type")
                    .schemaKey("schema")
                    .nameKey("type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // STRUCTURED NUMERIC: {"schema": "...", "classifier": N}
            int classifierId = personClass.getClassifierID();
            String json = "{\"schema\": \"" + testPackage.getNsURI() + "\", \"classifier\": " + classifierId + "}";
            try (JsonParser parser = createParser(json)) {
                entry.deserialize(state, parser, null);
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("uses custom keys for structured parsing")
        void usesCustomKeysForStructuredParsing() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("@context")
                    .schemaKey("@vocab")
                    .nameKey("@type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            assertEquals("@context", entry.getKey());

            DeserializationState state = createState(null);

            // Custom keys: {"@vocab": "...", "@type": "Person"}
            String json = "{\"@vocab\": \"" + testPackage.getNsURI() + "\", \"@type\": \"Person\"}";
            try (JsonParser parser = createParser(json)) {
                entry.deserialize(state, parser, null);
                assertEquals(personClass, state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("handles unknown structured type gracefully")
        void handlesUnknownStructuredTypeGracefully() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("_type")
                    .schemaKey("schema")
                    .nameKey("type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // Unknown class in structured format
            String json = "{\"schema\": \"" + testPackage.getNsURI() + "\", \"type\": \"NonExistentClass\"}";
            try (JsonParser parser = createParser(json)) {
                entry.deserialize(state, parser, null);
                assertNull(state.getResolvedEClass());
            }
        }

        @Test
        @DisplayName("handles empty structured object gracefully")
        void handlesEmptyStructuredObjectGracefully() {
            EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                    .enabled(true)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("_type")
                    .schemaKey("schema")
                    .nameKey("type")
                    .build();

            TypeDeserializationEntry entry = new TypeDeserializationEntry(config);
            DeserializationState state = createState(null);

            // Empty object
            String json = "{}";
            try (JsonParser parser = createParser(json)) {
                entry.deserialize(state, parser, null);
                assertNull(state.getResolvedEClass());
            }
        }
    }
}
