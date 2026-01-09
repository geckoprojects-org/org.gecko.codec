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
package org.eclipse.fennec.codec.v2.resource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.util.MetadataServiceFactory;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for ID serialization formats.
 * <p>
 * Tests verify:
 * <ul>
 *   <li>PLAIN format (combined string with separator)</li>
 *   <li>STRUCTURED format (nested object with individual fields)</li>
 *   <li>Multiple idFeatures (combined ID)</li>
 *   <li>IdKeyMode (ID_ONLY, BOTH, FEATURE_ONLY)</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/06-id.md">Spec: ID Serialization</a>
 */
@DisplayName("ID Serialization Tests")
class CodecResourceIdTest {

    private static final String TEST_ECORE = "test-id.ecore";

    private EcoreHelper ecoreHelper;
    private EPackage testPackage;
    private MetadataService metadataService;

    // EClasses
    private EClass personClass;
    private EClass multiIdClass;

    // EAttributes
    private EAttribute personIdAttribute;
    private EAttribute personNameAttribute;
    private EAttribute firstNameAttribute;
    private EAttribute lastNameAttribute;
    private EAttribute sequenceAttribute;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(CodecResourceIdTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);

        // Register package in global registry for type resolution
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        // Create metadata service with CodecAspectProvider registered
        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        // Load EClasses
        personClass = ecoreHelper.getEClass(testPackage, "Person");
        multiIdClass = ecoreHelper.getEClass(testPackage, "MultiId");

        // Load EAttributes
        personIdAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "personId");
        personNameAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "name");
        firstNameAttribute = (EAttribute) ecoreHelper.getFeature(multiIdClass, "firstName");
        lastNameAttribute = (EAttribute) ecoreHelper.getFeature(multiIdClass, "lastName");
        sequenceAttribute = (EAttribute) ecoreHelper.getFeature(multiIdClass, "sequence");
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
        ecoreHelper.releaseAll();
    }

    // ========================================================================
    // Helper Methods
    // ========================================================================

    private EObject createPerson(String id, String name) {
        EObject person = testPackage.getEFactoryInstance().create(personClass);
        person.eSet(personIdAttribute, id);
        person.eSet(personNameAttribute, name);
        return person;
    }

    private EObject createMultiId(String firstName, String lastName, int sequence) {
        EObject obj = testPackage.getEFactoryInstance().create(multiIdClass);
        obj.eSet(firstNameAttribute, firstName);
        obj.eSet(lastNameAttribute, lastName);
        obj.eSet(sequenceAttribute, sequence);
        return obj;
    }

    private CodecResource createResource(CodecConfiguration config) {
        return new CodecResource(
                URI.createURI("test://id-test.json"),
                metadataService,
                config,
                null);
    }

    private String serialize(EObject object, CodecConfiguration config) throws IOException {
        CodecResource resource = createResource(config);
        resource.getContents().add(object);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resource.save(out, Collections.emptyMap());

        return out.toString(StandardCharsets.UTF_8);
    }

    private EObject deserialize(String json, EClass eClass, CodecConfiguration config) throws IOException {
        CodecResource resource = createResource(config);

        Map<String, Object> options = new HashMap<>();
        options.put(CodecResource.CODEC_ROOT_OBJECT, eClass);

        ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        resource.load(in, options);

        return resource.getContents().isEmpty() ? null : resource.getContents().get(0);
    }

    // ========================================================================
    // PLAIN Format Tests
    // ========================================================================

    @Nested
    @DisplayName("PLAIN format (default)")
    class PlainFormatTests {

        @Test
        @DisplayName("single ID feature - PLAIN format")
        void singleIdPlainFormat() throws IOException {
            EObject person = createPerson("john-123", "John Doe");

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .build();

            String json = serialize(person, config);

            assertTrue(json.contains("\"_id\":\"john-123\""), "Should have _id as string");
            assertTrue(json.contains("\"name\":\"John Doe\""), "Should have name");
        }

        @Test
        @DisplayName("multiple ID features - PLAIN format with separator")
        void multipleIdPlainFormat() throws IOException {
            EObject obj = createMultiId("John", "Doe", 42);

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idSeparator("-")
                    .build();

            String json = serialize(obj, config);

            assertTrue(json.contains("\"_id\":\"John-Doe-42\""), "Should have combined _id");
        }

        @Test
        @DisplayName("custom separator")
        void customSeparator() throws IOException {
            EObject obj = createMultiId("John", "Doe", 1);

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName"))
                    .idSeparator("_")
                    .build();

            String json = serialize(obj, config);

            assertTrue(json.contains("\"_id\":\"John_Doe\""), "Should use custom separator");
        }
    }

    // ========================================================================
    // STRUCTURED Format Tests
    // ========================================================================

    @Nested
    @DisplayName("STRUCTURED format")
    class StructuredFormatTests {

        @Test
        @DisplayName("single ID feature - STRUCTURED format")
        void singleIdStructuredFormat() throws IOException {
            EObject person = createPerson("john-123", "John Doe");

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFormat(SerializationFormat.STRUCTURED)
                    .build();

            String json = serialize(person, config);

            // Should be: "_id": { "personId": "john-123" }
            assertTrue(json.contains("\"_id\":{"), "Should have _id as object");
            assertTrue(json.contains("\"personId\":\"john-123\""), "Should have personId inside _id");
            // Should NOT have separator for single ID (only for multiple)
            assertFalse(json.contains("\"separator\""), "Should not have separator for single ID");
        }

        @Test
        @DisplayName("multiple ID features - STRUCTURED format with separator serialized (default)")
        void multipleIdStructuredFormatWithSeparator() throws IOException {
            EObject obj = createMultiId("John", "Doe", 42);

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .idSeparator("-")
                    // serializeSeparator defaults to true
                    .build();

            String json = serialize(obj, config);

            // Should be: "_id": { "separator": "-", "firstName": "John", "lastName": "Doe", "sequence": 42 }
            assertTrue(json.contains("\"_id\":{"), "Should have _id as object");
            assertTrue(json.contains("\"separator\":\"-\""), "Separator SHOULD be serialized (default)");
            assertTrue(json.contains("\"firstName\":\"John\""), "Should have firstName");
            assertTrue(json.contains("\"lastName\":\"Doe\""), "Should have lastName");
            assertTrue(json.contains("\"sequence\":42"), "Should have sequence as number");
        }

        @Test
        @DisplayName("multiple ID features - STRUCTURED format WITHOUT separator serialized")
        void multipleIdStructuredFormatWithoutSeparator() throws IOException {
            EObject obj = createMultiId("John", "Doe", 42);

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .idSeparator("-")
                    .idSerializeSeparator(false)  // Disable separator serialization
                    .build();

            String json = serialize(obj, config);

            // Should be: "_id": { "firstName": "John", "lastName": "Doe", "sequence": 42 }
            assertTrue(json.contains("\"_id\":{"), "Should have _id as object");
            assertFalse(json.contains("\"separator\""), "Separator should NOT be serialized");
            assertTrue(json.contains("\"firstName\":\"John\""), "Should have firstName");
            assertTrue(json.contains("\"lastName\":\"Doe\""), "Should have lastName");
            assertTrue(json.contains("\"sequence\":42"), "Should have sequence as number");
        }

        @Test
        @DisplayName("custom separator key")
        void customSeparatorKey() throws IOException {
            EObject obj = createMultiId("John", "Doe", 42);

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .idSeparator("|")
                    .idSeparatorKey("sep")  // Custom separator key
                    .build();

            String json = serialize(obj, config);

            assertTrue(json.contains("\"sep\":\"|\""), "Should use custom separator key");
            assertFalse(json.contains("\"separator\""), "Should not use default key");
        }

        @Test
        @DisplayName("custom ID key - STRUCTURED format")
        void customIdKeyStructuredFormat() throws IOException {
            EObject person = createPerson("john-123", "John Doe");

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idKey("identifier")
                    .idFormat(SerializationFormat.STRUCTURED)
                    .build();

            String json = serialize(person, config);

            assertTrue(json.contains("\"identifier\":{"), "Should use custom key");
            assertFalse(json.contains("\"_id\""), "Should not have default _id key");
        }
    }

    // ========================================================================
    // IdKeyMode Tests
    // ========================================================================

    @Nested
    @DisplayName("IdKeyMode")
    class IdKeyModeTests {

        @Test
        @DisplayName("ID_ONLY mode - only _id field")
        void idOnlyMode() throws IOException {
            EObject person = createPerson("john-123", "John Doe");

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idKeyMode(IdKeyMode.ID_ONLY)
                    .build();

            String json = serialize(person, config);

            assertTrue(json.contains("\"_id\":\"john-123\""), "Should have _id");
            // personId should NOT appear as separate field (not in ID_ONLY mode by default)
        }

        @Test
        @DisplayName("FEATURE_ONLY mode - no _id field")
        void featureOnlyMode() throws IOException {
            EObject person = createPerson("john-123", "John Doe");

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idKeyMode(IdKeyMode.FEATURE_ONLY)
                    .build();

            String json = serialize(person, config);

            assertFalse(json.contains("\"_id\""), "Should NOT have _id field");
            assertTrue(json.contains("\"personId\":\"john-123\""), "Should have personId field");
        }

        @Test
        @DisplayName("BOTH mode - _id and feature fields")
        void bothMode() throws IOException {
            EObject person = createPerson("john-123", "John Doe");

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idKeyMode(IdKeyMode.BOTH)
                    .build();

            String json = serialize(person, config);

            assertTrue(json.contains("\"_id\":\"john-123\""), "Should have _id");
            assertTrue(json.contains("\"personId\":\"john-123\""), "Should also have personId");
        }
    }

    // ========================================================================
    // Edge Cases
    // ========================================================================

    @Nested
    @DisplayName("Edge cases")
    class EdgeCases {

        @Test
        @DisplayName("ID disabled")
        void idDisabled() throws IOException {
            EObject person = createPerson("john-123", "John Doe");

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(false)
                    .build();

            String json = serialize(person, config);

            assertFalse(json.contains("\"_id\""), "Should not have _id when disabled");
        }

        @Test
        @DisplayName("feature ordering preserved in PLAIN format")
        void featureOrderingPreservedPlain() throws IOException {
            EObject obj = createMultiId("John", "Doe", 1);

            // lastName first, then firstName
            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("lastName", "firstName"))
                    .idSeparator("-")
                    .build();

            String json = serialize(obj, config);

            assertTrue(json.contains("\"_id\":\"Doe-John\""), "Should preserve feature order");
        }

        @Test
        @DisplayName("feature ordering preserved in STRUCTURED format")
        void featureOrderingPreservedStructured() throws IOException {
            EObject obj = createMultiId("John", "Doe", 1);

            // lastName first, then firstName
            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("lastName", "firstName"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .idSeparator("-")
                    .build();

            String json = serialize(obj, config);

            // In STRUCTURED format, order should still be preserved in the JSON object
            int lastNamePos = json.indexOf("\"lastName\"");
            int firstNamePos = json.indexOf("\"firstName\"");
            assertTrue(lastNamePos < firstNamePos, "lastName should appear before firstName");
        }
    }

    // ========================================================================
    // Deserialization Tests
    // ========================================================================

    @Nested
    @DisplayName("PLAIN format deserialization")
    class PlainDeserializationTests {

        @Test
        @DisplayName("single ID feature - PLAIN format")
        void deserializeSingleIdPlainFormat() throws IOException {
            String json = "{\"_id\":\"john-123\",\"name\":\"John Doe\"}";

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .build();

            EObject result = deserialize(json, personClass, config);
            assertNotNull(result, "Result should not be null");

            assertEquals("john-123", result.eGet(personIdAttribute), "Should have personId set");
            assertEquals("John Doe", result.eGet(personNameAttribute), "Should have name set");
        }

        @Test
        @DisplayName("multiple ID features - PLAIN format with separator")
        void deserializeMultipleIdPlainFormat() throws IOException {
            String json = "{\"_id\":\"John-Doe-42\",\"sequence\":42}";

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idSeparator("-")
                    .build();

            EObject result = deserialize(json, multiIdClass, config);

            assertEquals("John", result.eGet(firstNameAttribute), "Should have firstName set");
            assertEquals("Doe", result.eGet(lastNameAttribute), "Should have lastName set");
            assertEquals(42, result.eGet(sequenceAttribute), "Should have sequence set");
        }

        @Test
        @DisplayName("custom separator")
        void deserializeCustomSeparator() throws IOException {
            String json = "{\"_id\":\"John_Doe\"}";

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName"))
                    .idSeparator("_")
                    .build();

            EObject result = deserialize(json, multiIdClass, config);

            assertEquals("John", result.eGet(firstNameAttribute), "Should have firstName set");
            assertEquals("Doe", result.eGet(lastNameAttribute), "Should have lastName set");
        }
    }

    @Nested
    @DisplayName("STRUCTURED format deserialization")
    class StructuredDeserializationTests {

        @Test
        @DisplayName("single ID feature - STRUCTURED format")
        void deserializeSingleIdStructuredFormat() throws IOException {
            String json = "{\"_id\":{\"personId\":\"john-123\"},\"name\":\"John Doe\"}";

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFormat(SerializationFormat.STRUCTURED)
                    .build();

            EObject result = deserialize(json, personClass, config);

            assertEquals("john-123", result.eGet(personIdAttribute), "Should have personId set");
            assertEquals("John Doe", result.eGet(personNameAttribute), "Should have name set");
        }

        @Test
        @DisplayName("multiple ID features - STRUCTURED format")
        void deserializeMultipleIdStructuredFormat() throws IOException {
            String json = "{\"_id\":{\"firstName\":\"John\",\"lastName\":\"Doe\",\"sequence\":42}}";

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .build();

            EObject result = deserialize(json, multiIdClass, config);

            assertEquals("John", result.eGet(firstNameAttribute), "Should have firstName set");
            assertEquals("Doe", result.eGet(lastNameAttribute), "Should have lastName set");
            assertEquals(42, result.eGet(sequenceAttribute), "Should have sequence set");
        }

        @Test
        @DisplayName("STRUCTURED format with separator in JSON")
        void deserializeStructuredWithSeparatorInJson() throws IOException {
            // JSON includes the separator - deserializer should read it and use it
            String json = "{\"_id\":{\"separator\":\"-\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"sequence\":42}}";

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .idSeparator("|")  // Config says | but JSON has -
                    .build();

            EObject result = deserialize(json, multiIdClass, config);

            assertEquals("John", result.eGet(firstNameAttribute), "Should have firstName set");
            assertEquals("Doe", result.eGet(lastNameAttribute), "Should have lastName set");
            assertEquals(42, result.eGet(sequenceAttribute), "Should have sequence set");
            // The separator from JSON ("-") should be used for combining the EIDAttribute value
        }

        @Test
        @DisplayName("STRUCTURED format with custom separator key in JSON")
        void deserializeStructuredWithCustomSeparatorKey() throws IOException {
            String json = "{\"_id\":{\"sep\":\"|\",\"firstName\":\"John\",\"lastName\":\"Doe\"}}";

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .idSeparatorKey("sep")  // Custom separator key
                    .build();

            EObject result = deserialize(json, multiIdClass, config);

            assertEquals("John", result.eGet(firstNameAttribute), "Should have firstName set");
            assertEquals("Doe", result.eGet(lastNameAttribute), "Should have lastName set");
        }

        @Test
        @DisplayName("custom ID key - STRUCTURED format")
        void deserializeCustomIdKeyStructuredFormat() throws IOException {
            String json = "{\"identifier\":{\"personId\":\"john-123\"},\"name\":\"John Doe\"}";

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idKey("identifier")
                    .idFormat(SerializationFormat.STRUCTURED)
                    .build();

            EObject result = deserialize(json, personClass, config);

            assertEquals("john-123", result.eGet(personIdAttribute), "Should have personId set");
        }
    }

    @Nested
    @DisplayName("Round-trip tests")
    class RoundTripTests {

        @Test
        @DisplayName("round-trip PLAIN format")
        void roundTripPlainFormat() throws IOException {
            EObject original = createMultiId("John", "Doe", 42);

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idSeparator("-")
                    .build();

            String json = serialize(original, config);

            EObject result = deserialize(json, multiIdClass, config);
            assertNotNull(result, "Result should not be null. JSON was: " + json);

            assertEquals(original.eGet(firstNameAttribute), result.eGet(firstNameAttribute));
            assertEquals(original.eGet(lastNameAttribute), result.eGet(lastNameAttribute));
            assertEquals(original.eGet(sequenceAttribute), result.eGet(sequenceAttribute));
        }

        @Test
        @DisplayName("round-trip STRUCTURED format")
        void roundTripStructuredFormat() throws IOException {
            EObject original = createMultiId("John", "Doe", 42);

            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .build();

            String json = serialize(original, config);

            EObject result = deserialize(json, multiIdClass, config);
            assertNotNull(result, "Result should not be null. JSON was: " + json);

            assertEquals(original.eGet(firstNameAttribute), result.eGet(firstNameAttribute));
            assertEquals(original.eGet(lastNameAttribute), result.eGet(lastNameAttribute));
            assertEquals(original.eGet(sequenceAttribute), result.eGet(sequenceAttribute));
        }

        @Test
        @DisplayName("round-trip STRUCTURED format with separator serialized")
        void roundTripStructuredFormatWithSeparator() throws IOException {
            EObject original = createMultiId("John", "Doe", 42);

            // serializeSeparator=true (default) - separator will be in JSON
            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .idSeparator("-")
                    .build();

            String json = serialize(original, config);

            // Verify separator is in the JSON
            assertTrue(json.contains("\"separator\":\"-\""), "Separator should be in JSON");

            EObject result = deserialize(json, multiIdClass, config);
            assertNotNull(result, "Result should not be null. JSON was: " + json);

            assertEquals(original.eGet(firstNameAttribute), result.eGet(firstNameAttribute));
            assertEquals(original.eGet(lastNameAttribute), result.eGet(lastNameAttribute));
            assertEquals(original.eGet(sequenceAttribute), result.eGet(sequenceAttribute));
        }

        @Test
        @DisplayName("round-trip STRUCTURED format without separator serialized")
        void roundTripStructuredFormatWithoutSeparator() throws IOException {
            EObject original = createMultiId("John", "Doe", 42);

            // serializeSeparator=false - separator NOT in JSON, must be configured
            CodecConfiguration config = CodecConfiguration.builder()
                    .useId(true)
                    .idFeatures(List.of("firstName", "lastName", "sequence"))
                    .idFormat(SerializationFormat.STRUCTURED)
                    .idSeparator("-")
                    .idSerializeSeparator(false)
                    .build();

            String json = serialize(original, config);

            // Verify separator is NOT in the JSON
            assertFalse(json.contains("\"separator\""), "Separator should NOT be in JSON");

            EObject result = deserialize(json, multiIdClass, config);
            assertNotNull(result, "Result should not be null. JSON was: " + json);

            assertEquals(original.eGet(firstNameAttribute), result.eGet(firstNameAttribute));
            assertEquals(original.eGet(lastNameAttribute), result.eGet(lastNameAttribute));
            assertEquals(original.eGet(sequenceAttribute), result.eGet(sequenceAttribute));
        }
    }
}
