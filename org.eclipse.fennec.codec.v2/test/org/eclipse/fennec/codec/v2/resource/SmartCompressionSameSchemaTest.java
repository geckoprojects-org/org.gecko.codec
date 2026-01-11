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
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.util.MetadataServiceFactory;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for same-schema smart compression.
 * <p>
 * Smart Compression reduces redundancy by using <b>simple type names</b> instead of
 * <b>full URIs</b> when the type belongs to the same schema as the root object.
 * </p>
 * <p>
 * Key Rules:
 * <ul>
 *   <li>Type same schema as root → Write simple name (e.g., "Person")</li>
 *   <li>Type different schema → Write full URI</li>
 *   <li>Smart compression only affects the <b>format</b> of type values, not whether to write them</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/04-global-options.md#1-smart-compression">Spec: Smart Compression</a>
 */
@DisplayName("Smart Compression Same-Schema Tests")
class SmartCompressionSameSchemaTest {

    private static final String TEST_ECORE = "test-roundtrip.ecore";

    private EcoreHelper ecoreHelper;
    private EPackage testPackage;
    private MetadataService metadataService;

    private EClass companyClass;
    private EClass personClass;
    private EClass addressClass;
    private EAttribute companyNameAttr;
    private EAttribute personNameAttr;
    private EAttribute streetAttr;
    private EAttribute cityAttr;
    private EReference employeesRef;
    private EReference personAddressRef;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(SmartCompressionSameSchemaTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        companyClass = ecoreHelper.getEClass(testPackage, "Company");
        personClass = ecoreHelper.getEClass(testPackage, "Person");
        addressClass = ecoreHelper.getEClass(testPackage, "Address");

        companyNameAttr = (EAttribute) ecoreHelper.getFeature(companyClass, "name");
        personNameAttr = (EAttribute) ecoreHelper.getFeature(personClass, "name");
        streetAttr = (EAttribute) ecoreHelper.getFeature(addressClass, "street");
        cityAttr = (EAttribute) ecoreHelper.getFeature(addressClass, "city");

        employeesRef = (EReference) ecoreHelper.getFeature(companyClass, "employees");
        personAddressRef = (EReference) ecoreHelper.getFeature(personClass, "address");
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
        ecoreHelper.releaseAll();
    }

    private String serialize(EObject object, boolean smartCompression) throws IOException {
        CodecConfiguration config = CodecConfiguration.builder()
                .smartCompression(smartCompression)
                .build();

        CodecResource resource = new CodecResource(
                URI.createURI("test://smart-compression-test.json"),
                metadataService,
                config,
                null);
        resource.getContents().add(object);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resource.save(out, Collections.emptyMap());
        return out.toString(StandardCharsets.UTF_8);
    }

    @Nested
    @DisplayName("Smart Compression OFF")
    class SmartCompressionOff {

        @Test
        @DisplayName("All types use full URIs")
        void allTypesUseFullUris() throws IOException {
            EObject company = testPackage.getEFactoryInstance().create(companyClass);
            company.eSet(companyNameAttr, "Acme");

            EObject person = testPackage.getEFactoryInstance().create(personClass);
            person.eSet(personNameAttr, "Alice");

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(person);

            String json = serialize(company, false);
            System.out.println("=== Smart Compression OFF ===");
            System.out.println(json);
            System.out.println();

            // Both root and contained should use full URIs
            String nsUri = testPackage.getNsURI();
            assertTrue(json.contains("\"_type\":\"" + nsUri + "#//Company\""),
                    "Root Company should use full URI");
            assertTrue(json.contains("\"_type\":\"" + nsUri + "#//Person\""),
                    "Contained Person should use full URI");
        }

        @Test
        @DisplayName("Nested containment uses full URIs")
        void nestedContainmentUsesFullUris() throws IOException {
            EObject company = testPackage.getEFactoryInstance().create(companyClass);
            company.eSet(companyNameAttr, "Acme");

            EObject person = testPackage.getEFactoryInstance().create(personClass);
            person.eSet(personNameAttr, "Alice");

            EObject address = testPackage.getEFactoryInstance().create(addressClass);
            address.eSet(streetAttr, "123 Main St");
            address.eSet(cityAttr, "Springfield");
            person.eSet(personAddressRef, address);

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(person);

            String json = serialize(company, false);
            System.out.println("=== Nested Containment - Smart Compression OFF ===");
            System.out.println(json);
            System.out.println();

            String nsUri = testPackage.getNsURI();
            assertTrue(json.contains("\"_type\":\"" + nsUri + "#//Company\""),
                    "Root Company should use full URI");
            assertTrue(json.contains("\"_type\":\"" + nsUri + "#//Person\""),
                    "Contained Person should use full URI");
            assertTrue(json.contains("\"_type\":\"" + nsUri + "#//Address\""),
                    "Nested Address should use full URI");
        }
    }

    @Nested
    @DisplayName("Smart Compression ON")
    class SmartCompressionOn {

        @Test
        @DisplayName("Root uses full URI, contained uses simple name")
        void rootFullUriContainedSimpleName() throws IOException {
            EObject company = testPackage.getEFactoryInstance().create(companyClass);
            company.eSet(companyNameAttr, "Acme");

            EObject person = testPackage.getEFactoryInstance().create(personClass);
            person.eSet(personNameAttr, "Alice");

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(person);

            String json = serialize(company, true);
            System.out.println("=== Smart Compression ON ===");
            System.out.println(json);
            System.out.println();

            String nsUri = testPackage.getNsURI();
            // Root Company should still use full URI (it establishes the context)
            assertTrue(json.contains("\"_type\":\"" + nsUri + "#//Company\""),
                    "Root Company should use full URI to establish context");
            // Contained Person should use simple name
            assertTrue(json.contains("\"_type\":\"Person\""),
                    "Contained Person should use simple name 'Person'");
            // Should NOT contain full URI for Person
            assertFalse(json.contains("\"_type\":\"" + nsUri + "#//Person\""),
                    "Contained Person should NOT use full URI");
        }

        @Test
        @DisplayName("Nested containment uses simple names")
        void nestedContainmentUsesSimpleNames() throws IOException {
            EObject company = testPackage.getEFactoryInstance().create(companyClass);
            company.eSet(companyNameAttr, "Acme");

            EObject person = testPackage.getEFactoryInstance().create(personClass);
            person.eSet(personNameAttr, "Alice");

            EObject address = testPackage.getEFactoryInstance().create(addressClass);
            address.eSet(streetAttr, "123 Main St");
            address.eSet(cityAttr, "Springfield");
            person.eSet(personAddressRef, address);

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(person);

            String json = serialize(company, true);
            System.out.println("=== Nested Containment - Smart Compression ON ===");
            System.out.println(json);
            System.out.println();

            String nsUri = testPackage.getNsURI();
            // Root uses full URI
            assertTrue(json.contains("\"_type\":\"" + nsUri + "#//Company\""),
                    "Root Company should use full URI");
            // Contained objects use simple names
            assertTrue(json.contains("\"_type\":\"Person\""),
                    "Contained Person should use simple name");
            assertTrue(json.contains("\"_type\":\"Address\""),
                    "Nested Address should use simple name");
        }

        @Test
        @DisplayName("Multiple employees all use simple names")
        void multipleEmployeesUseSimpleNames() throws IOException {
            EObject company = testPackage.getEFactoryInstance().create(companyClass);
            company.eSet(companyNameAttr, "Acme");

            EObject person1 = testPackage.getEFactoryInstance().create(personClass);
            person1.eSet(personNameAttr, "Alice");

            EObject person2 = testPackage.getEFactoryInstance().create(personClass);
            person2.eSet(personNameAttr, "Bob");

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(person1);
            employees.add(person2);

            String json = serialize(company, true);
            System.out.println("=== Multiple Employees - Smart Compression ON ===");
            System.out.println(json);
            System.out.println();

            // Count occurrences of simple name
            int simpleNameCount = countOccurrences(json, "\"_type\":\"Person\"");
            assertEquals(2, simpleNameCount,
                    "Should have 2 occurrences of simple name 'Person'");

            // Should not have full URI for Person
            String nsUri = testPackage.getNsURI();
            assertFalse(json.contains("\"_type\":\"" + nsUri + "#//Person\""),
                    "Should NOT have full URI for Person");
        }
    }

    @Nested
    @DisplayName("Root Object")
    class RootObject {

        @Test
        @DisplayName("Root object always uses full URI even with smart compression")
        void rootAlwaysUsesFullUri() throws IOException {
            EObject person = testPackage.getEFactoryInstance().create(personClass);
            person.eSet(personNameAttr, "Alice");

            String json = serialize(person, true);
            System.out.println("=== Root Object with Smart Compression ON ===");
            System.out.println(json);
            System.out.println();

            String nsUri = testPackage.getNsURI();
            // Root should still use full URI (it establishes the context)
            assertTrue(json.contains("\"_type\":\"" + nsUri + "#//Person\""),
                    "Root Person should use full URI even with smart compression");
        }
    }

    private int countOccurrences(String text, String pattern) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }

    private EObject deserialize(String json) throws IOException {
        CodecConfiguration config = CodecConfiguration.builder()
                .smartCompression(true)
                .build();

        CodecResource resource = new CodecResource(
                URI.createURI("test://smart-compression-deser.json"),
                metadataService,
                config,
                null);

        ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        resource.load(in, Collections.emptyMap());

        assertFalse(resource.getContents().isEmpty(), "Resource should have contents after load");
        return resource.getContents().get(0);
    }

    @Nested
    @DisplayName("Deserialization Round-Trip")
    class DeserializationRoundTrip {

        @Test
        @DisplayName("Deserialize JSON with simple type names")
        void deserializeSimpleTypeNames() throws IOException {
            // Create and serialize with smart compression
            EObject company = testPackage.getEFactoryInstance().create(companyClass);
            company.eSet(companyNameAttr, "Acme");

            EObject person = testPackage.getEFactoryInstance().create(personClass);
            person.eSet(personNameAttr, "Alice");

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(person);

            // Serialize with smart compression (produces simple names)
            String json = serialize(company, true);
            System.out.println("=== Deserialization Round-Trip ===");
            System.out.println(json);

            // Verify the JSON uses simple names
            assertTrue(json.contains("\"_type\":\"Person\""),
                    "Serialized JSON should use simple name for Person");

            // Deserialize - should resolve simple names using context schema
            EObject loaded = deserialize(json);

            // Verify structure
            assertNotNull(loaded, "Loaded object should not be null");
            assertEquals(companyClass, loaded.eClass(), "Root should be Company");
            assertEquals("Acme", loaded.eGet(companyNameAttr), "Company name should match");

            // Verify contained Person
            @SuppressWarnings("unchecked")
            List<EObject> loadedEmployees = (List<EObject>) loaded.eGet(employeesRef);
            assertEquals(1, loadedEmployees.size(), "Should have 1 employee");

            EObject loadedPerson = loadedEmployees.get(0);
            assertEquals(personClass, loadedPerson.eClass(),
                    "Employee should be Person (resolved from simple name)");
            assertEquals("Alice", loadedPerson.eGet(personNameAttr), "Person name should match");
        }

        @Test
        @DisplayName("Deserialize nested containment with simple names")
        void deserializeNestedContainment() throws IOException {
            // Create nested structure
            EObject company = testPackage.getEFactoryInstance().create(companyClass);
            company.eSet(companyNameAttr, "Acme");

            EObject person = testPackage.getEFactoryInstance().create(personClass);
            person.eSet(personNameAttr, "Alice");

            EObject address = testPackage.getEFactoryInstance().create(addressClass);
            address.eSet(streetAttr, "123 Main St");
            address.eSet(cityAttr, "Springfield");
            person.eSet(personAddressRef, address);

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(person);

            // Serialize with smart compression
            String json = serialize(company, true);
            System.out.println("=== Nested Deserialization Round-Trip ===");
            System.out.println(json);

            // Verify the JSON uses simple names
            assertTrue(json.contains("\"_type\":\"Person\""),
                    "Should use simple name for Person");
            assertTrue(json.contains("\"_type\":\"Address\""),
                    "Should use simple name for Address");

            // Deserialize
            EObject loaded = deserialize(json);

            // Verify structure
            assertEquals(companyClass, loaded.eClass(), "Root should be Company");

            @SuppressWarnings("unchecked")
            List<EObject> loadedEmployees = (List<EObject>) loaded.eGet(employeesRef);
            assertEquals(1, loadedEmployees.size(), "Should have 1 employee");

            EObject loadedPerson = loadedEmployees.get(0);
            assertEquals(personClass, loadedPerson.eClass(), "Employee should be Person");
            assertEquals("Alice", loadedPerson.eGet(personNameAttr), "Person name should match");

            // Verify nested Address
            EObject loadedAddress = (EObject) loadedPerson.eGet(personAddressRef);
            assertNotNull(loadedAddress, "Person should have address");
            assertEquals(addressClass, loadedAddress.eClass(),
                    "Address should be resolved from simple name");
            assertEquals("123 Main St", loadedAddress.eGet(streetAttr), "Street should match");
            assertEquals("Springfield", loadedAddress.eGet(cityAttr), "City should match");
        }

        @Test
        @DisplayName("Deserialize multiple employees with simple names")
        void deserializeMultipleEmployees() throws IOException {
            EObject company = testPackage.getEFactoryInstance().create(companyClass);
            company.eSet(companyNameAttr, "Acme");

            EObject person1 = testPackage.getEFactoryInstance().create(personClass);
            person1.eSet(personNameAttr, "Alice");

            EObject person2 = testPackage.getEFactoryInstance().create(personClass);
            person2.eSet(personNameAttr, "Bob");

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(person1);
            employees.add(person2);

            // Serialize and deserialize
            String json = serialize(company, true);
            System.out.println("=== Multiple Employees Deserialization ===");
            System.out.println(json);

            EObject loaded = deserialize(json);

            // Verify structure
            assertEquals(companyClass, loaded.eClass(), "Root should be Company");

            @SuppressWarnings("unchecked")
            List<EObject> loadedEmployees = (List<EObject>) loaded.eGet(employeesRef);
            assertEquals(2, loadedEmployees.size(), "Should have 2 employees");

            assertEquals(personClass, loadedEmployees.get(0).eClass(), "First employee should be Person");
            assertEquals("Alice", loadedEmployees.get(0).eGet(personNameAttr), "First person name");

            assertEquals(personClass, loadedEmployees.get(1).eClass(), "Second employee should be Person");
            assertEquals("Bob", loadedEmployees.get(1).eGet(personNameAttr), "Second person name");
        }

        @Test
        @DisplayName("Deserialize raw JSON with simple type names")
        void deserializeRawJsonWithSimpleNames() throws IOException {
            // Construct JSON manually with simple type names
            // This tests that the deserializer can handle simple names when context is established
            String nsUri = testPackage.getNsURI();
            String json = """
                    {
                      "_type": "%s#//Company",
                      "name": "TestCorp",
                      "employees": [
                        { "_type": "Person", "name": "Charlie" },
                        { "_type": "Person", "name": "Diana" }
                      ]
                    }
                    """.formatted(nsUri);

            System.out.println("=== Raw JSON with Simple Names ===");
            System.out.println(json);

            EObject loaded = deserialize(json);

            // Verify structure
            assertEquals(companyClass, loaded.eClass(), "Root should be Company");
            assertEquals("TestCorp", loaded.eGet(companyNameAttr), "Company name should match");

            @SuppressWarnings("unchecked")
            List<EObject> loadedEmployees = (List<EObject>) loaded.eGet(employeesRef);
            assertEquals(2, loadedEmployees.size(), "Should have 2 employees");

            // Both should be resolved as Person from simple name
            assertEquals(personClass, loadedEmployees.get(0).eClass(),
                    "First employee should be Person (from simple name)");
            assertEquals("Charlie", loadedEmployees.get(0).eGet(personNameAttr));

            assertEquals(personClass, loadedEmployees.get(1).eClass(),
                    "Second employee should be Person (from simple name)");
            assertEquals("Diana", loadedEmployees.get(1).eGet(personNameAttr));
        }

        @Test
        @DisplayName("Deserialize deeply nested JSON with simple names")
        void deserializeDeeplyNestedSimpleNames() throws IOException {
            String nsUri = testPackage.getNsURI();
            String json = """
                    {
                      "_type": "%s#//Company",
                      "name": "DeepCorp",
                      "employees": [
                        {
                          "_type": "Person",
                          "name": "Eve",
                          "address": {
                            "_type": "Address",
                            "street": "456 Oak Ave",
                            "city": "Metropolis"
                          }
                        }
                      ]
                    }
                    """.formatted(nsUri);

            System.out.println("=== Deeply Nested JSON with Simple Names ===");
            System.out.println(json);

            EObject loaded = deserialize(json);

            // Verify structure
            assertEquals(companyClass, loaded.eClass(), "Root should be Company");

            @SuppressWarnings("unchecked")
            List<EObject> loadedEmployees = (List<EObject>) loaded.eGet(employeesRef);
            assertEquals(1, loadedEmployees.size());

            EObject loadedPerson = loadedEmployees.get(0);
            assertEquals(personClass, loadedPerson.eClass(), "Should be Person from simple name");
            assertEquals("Eve", loadedPerson.eGet(personNameAttr));

            EObject loadedAddress = (EObject) loadedPerson.eGet(personAddressRef);
            assertNotNull(loadedAddress, "Person should have address");
            assertEquals(addressClass, loadedAddress.eClass(), "Should be Address from simple name");
            assertEquals("456 Oak Ave", loadedAddress.eGet(streetAttr));
            assertEquals("Metropolis", loadedAddress.eGet(cityAttr));
        }
    }
}
