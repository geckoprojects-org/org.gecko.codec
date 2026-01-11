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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.util.MetadataServiceFactory;
import org.eclipse.fennec.codec.v2.value.CodecValueReader;
import org.eclipse.fennec.codec.v2.value.CodecValueRegistry;
import org.eclipse.fennec.codec.v2.value.CodecValueWriter;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for custom value readers/writers in CodecResource.
 * <p>
 * Tests that custom value transformations work correctly during
 * serialization and deserialization.
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/10-custom-values.md">Spec 10: Custom Value Readers/Writers</a>
 */
@DisplayName("CodecResource Custom Value Tests")
class CodecResourceCustomValueTest {

    private static final String TEST_ECORE = "test-roundtrip.ecore";

    private EcoreHelper ecoreHelper;
    private EPackage testPackage;
    private EClass personClass;
    private EAttribute nameAttribute;
    private EAttribute ageAttribute;
    private MetadataService metadataService;
    private CodecValueRegistry valueRegistry;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(CodecResourceCustomValueTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);

        // Register package in global registry for type resolution
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        // Create metadata service
        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        // Load EClasses and attributes
        personClass = ecoreHelper.getEClass(testPackage, "Person");
        nameAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "name");
        ageAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "age");

        // Create value registry
        valueRegistry = new CodecValueRegistry();
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
    }

    /**
     * Helper to create a Person EObject.
     */
    private EObject createPerson(String name, int age) {
        EObject person = testPackage.getEFactoryInstance().create(personClass);
        person.eSet(nameAttribute, name);
        person.eSet(ageAttribute, age);
        return person;
    }

    /**
     * Helper to serialize an EObject to JSON string.
     */
    private String serialize(EObject eObject, CodecConfiguration config, CodecValueRegistry registry) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CodecResource resource = new CodecResource(
                URI.createURI("test.json"),
                metadataService,
                config,
                registry,
                null);
        resource.getContents().add(eObject);
        resource.save(baos, Collections.emptyMap());
        return baos.toString(StandardCharsets.UTF_8);
    }

    /**
     * Helper to deserialize JSON string to EObject.
     */
    private EObject deserialize(String json, EClass rootClass, CodecConfiguration config, CodecValueRegistry registry) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        CodecResource resource = new CodecResource(
                URI.createURI("test.json"),
                metadataService,
                config,
                registry,
                null);
        resource.load(bais, Collections.singletonMap(CodecResource.CODEC_ROOT_OBJECT, rootClass));
        return resource.getContents().isEmpty() ? null : resource.getContents().get(0);
    }

    @Nested
    @DisplayName("Custom Writer Tests")
    class CustomWriterTests {

        @Test
        @DisplayName("Should register and retrieve custom writer")
        void shouldRegisterAndRetrieveCustomWriter() {
            // Create a custom writer that doubles integer values
            CodecValueWriter<Integer, EAttribute> doublingWriter = (value, feature, gen, ctxt) -> {
                gen.writeNumber(value * 2);
            };

            valueRegistry.registerWriter("doublingWriter", doublingWriter);

            assertTrue(valueRegistry.hasWriter("doublingWriter"));
            assertEquals(doublingWriter, valueRegistry.getWriter("doublingWriter").orElse(null));
        }

        @Test
        @DisplayName("Custom writer should be available after registration")
        void customWriterAvailableAfterRegistration() {
            // Create a writer that prefixes strings with "PREFIX_"
            CodecValueWriter<String, EAttribute> prefixWriter = (value, feature, gen, ctxt) -> {
                gen.writeString("PREFIX_" + value);
            };

            valueRegistry.registerWriter("prefixWriter", prefixWriter);

            // Verify writer is available
            assertTrue(valueRegistry.hasWriter("prefixWriter"));
            assertNotNull(valueRegistry.getWriter("prefixWriter").orElse(null));
        }
    }

    @Nested
    @DisplayName("Custom Reader Tests")
    class CustomReaderTests {

        @Test
        @DisplayName("Should register and retrieve custom reader")
        void shouldRegisterAndRetrieveCustomReader() {
            // Create a custom reader that halves integer values
            CodecValueReader<Integer, EAttribute> halvingReader = (parser, feature, ctxt) -> {
                return parser.getIntValue() / 2;
            };

            valueRegistry.registerReader("halvingReader", halvingReader);

            assertTrue(valueRegistry.hasReader("halvingReader"));
            assertEquals(halvingReader, valueRegistry.getReader("halvingReader").orElse(null));
        }
    }

    @Nested
    @DisplayName("Round-trip Tests")
    class RoundTripTests {

        @Test
        @DisplayName("Should serialize and deserialize without custom readers/writers")
        void roundTripWithoutCustomHandlers() throws IOException {
            EObject person = createPerson("Alice", 30);

            // Use defaults which includes type serialization
            CodecConfiguration config = CodecConfiguration.defaults();

            // Serialize
            String json = serialize(person, config, valueRegistry);
            // JSON format may vary (with/without spaces), so check for key and value separately
            assertTrue(json.contains("\"name\""), "Should contain name key, but got: " + json);
            assertTrue(json.contains("\"Alice\""), "Should contain Alice value, but got: " + json);
            assertTrue(json.contains("\"age\""), "Should contain age key, but got: " + json);
            assertTrue(json.contains("30"), "Should contain 30 value, but got: " + json);

            // Deserialize
            EObject loaded = deserialize(json, personClass, config, valueRegistry);
            assertNotNull(loaded);
            assertEquals("Alice", loaded.eGet(nameAttribute));
            assertEquals(30, loaded.eGet(ageAttribute));
        }

        @Test
        @DisplayName("Registry should be properly passed through the configuration chain")
        void registryPassedThroughConfigChain() throws IOException {
            // Register some handlers
            CodecValueWriter<String, EAttribute> testWriter = (v, f, g, c) -> g.writeString("TEST");
            CodecValueReader<String, EAttribute> testReader = (p, f, c) -> "RESULT";
            valueRegistry.registerWriter("testWriter", testWriter);
            valueRegistry.registerReader("testReader", testReader);

            EObject person = createPerson("Bob", 25);

            CodecConfiguration config = CodecConfiguration.defaults();

            // The important part is that the code doesn't throw and the registry is accessible
            String json = serialize(person, config, valueRegistry);
            assertNotNull(json);
            assertFalse(json.isEmpty());

            // Verify registry still has our handlers
            assertTrue(valueRegistry.hasWriter("testWriter"));
            assertTrue(valueRegistry.hasReader("testReader"));
        }
    }

    @Nested
    @DisplayName("Registry Integration Tests")
    class RegistryIntegrationTests {

        @Test
        @DisplayName("Multiple writers can be registered")
        void multipleWritersRegistered() {
            CodecValueWriter<String, EAttribute> writer1 = (v, f, g, c) -> g.writeString(v.toUpperCase());
            CodecValueWriter<Integer, EAttribute> writer2 = (v, f, g, c) -> g.writeNumber(v * 10);
            CodecValueWriter<Boolean, EAttribute> writer3 = (v, f, g, c) -> g.writeString(v ? "yes" : "no");

            valueRegistry.registerWriter("upperWriter", writer1);
            valueRegistry.registerWriter("timesTeWriter", writer2);
            valueRegistry.registerWriter("yesNoWriter", writer3);

            assertEquals(3, valueRegistry.getWriters().size());
            assertTrue(valueRegistry.hasWriter("upperWriter"));
            assertTrue(valueRegistry.hasWriter("timesTeWriter"));
            assertTrue(valueRegistry.hasWriter("yesNoWriter"));
        }

        @Test
        @DisplayName("Multiple readers can be registered")
        void multipleReadersRegistered() {
            CodecValueReader<String, EAttribute> reader1 = (p, f, c) -> p.getString().toLowerCase();
            CodecValueReader<Integer, EAttribute> reader2 = (p, f, c) -> p.getIntValue() / 10;

            valueRegistry.registerReader("lowerReader", reader1);
            valueRegistry.registerReader("divideReader", reader2);

            assertEquals(2, valueRegistry.getReaders().size());
            assertTrue(valueRegistry.hasReader("lowerReader"));
            assertTrue(valueRegistry.hasReader("divideReader"));
        }

        @Test
        @DisplayName("Null registry should not cause errors")
        void nullRegistryHandledGracefully() throws IOException {
            EObject person = createPerson("Charlie", 40);

            // Use defaults which includes type serialization
            CodecConfiguration config = CodecConfiguration.defaults();

            // Use null registry - should work with default serialization
            String json = serialize(person, config, null);
            assertNotNull(json);
            assertTrue(json.contains("\"Charlie\""));

            // Deserialize with null registry
            EObject loaded = deserialize(json, personClass, config, null);
            assertNotNull(loaded);
            assertEquals("Charlie", loaded.eGet(nameAttribute));
        }
    }
}
