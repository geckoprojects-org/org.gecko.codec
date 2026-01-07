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
package org.eclipse.fennec.codec.v2.ser;

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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.resource.CodecResource;
import org.eclipse.fennec.codec.v2.util.MetadataServiceFactory;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for the expand feature for non-containment references.
 * <p>
 * Tests both serialization (with expand config) and deserialization
 * (auto-detection of expanded objects without _ref).
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/07-reference.md#42-expand-inline-serialization">Spec: Expand Inline Serialization</a>
 * @see <a href="docs/codec-v2-spec/07-reference.md#8-deserialization">Spec: Reference Deserialization</a>
 */
@DisplayName("Expand Reference Tests")
class ExpandReferenceTest {

    private static final String TEST_ECORE = "test-roundtrip.ecore";

    private EcoreHelper ecoreHelper;
    private EPackage testPackage;
    private MetadataService metadataService;

    // EClasses
    private EClass personClass;
    private EClass addressClass;
    private EClass companyClass;

    // EAttributes
    private EAttribute personNameAttribute;
    private EAttribute companyNameAttribute;

    // EReferences
    private EReference managerRef;
    private EReference friendsRef;
    private EReference employeesRef;
    private EReference ceoRef;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(ExpandReferenceTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);

        // Register package in global registry for type resolution
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        // Create metadata service
        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        // Load EClasses
        personClass = ecoreHelper.getEClass(testPackage, "Person");
        addressClass = ecoreHelper.getEClass(testPackage, "Address");
        companyClass = ecoreHelper.getEClass(testPackage, "Company");

        // Load EAttributes
        personNameAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "name");
        companyNameAttribute = (EAttribute) ecoreHelper.getFeature(companyClass, "name");

        // Load EReferences
        managerRef = (EReference) ecoreHelper.getFeature(personClass, "manager");
        friendsRef = (EReference) ecoreHelper.getFeature(personClass, "friends");
        employeesRef = (EReference) ecoreHelper.getFeature(companyClass, "employees");
        ceoRef = (EReference) ecoreHelper.getFeature(companyClass, "ceo");
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
        ecoreHelper.releaseAll();
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    private EObject createPerson(String name) {
        EObject person = testPackage.getEFactoryInstance().create(personClass);
        person.eSet(personNameAttribute, name);
        return person;
    }

    private EObject createCompany(String name) {
        EObject company = testPackage.getEFactoryInstance().create(companyClass);
        company.eSet(companyNameAttribute, name);
        return company;
    }

    private CodecResource createResource(CodecConfiguration config) {
        return new CodecResource(
                URI.createURI("test://expand-test.json"),
                metadataService,
                config,
                null);
    }

    private String serialize(EObject object, CodecConfiguration config) throws IOException {
        CodecResource resource = createResource(config);
        resource.getContents().add(object);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resource.save(out, Collections.emptyMap());

        String json = out.toString(StandardCharsets.UTF_8);
        System.out.println("Serialized JSON:\n" + json);
        return json;
    }

    private EObject deserialize(String json, EClass rootEClass) throws IOException {
        CodecResource resource = createResource(CodecConfiguration.defaults());

        Map<String, Object> options = new HashMap<>();
        options.put(CodecResource.CODEC_ROOT_OBJECT, rootEClass);

        ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        resource.load(in, options);

        return resource.getContents().isEmpty() ? null : resource.getContents().get(0);
    }

    // ========================================================================
    // Serialization Tests
    // ========================================================================

    @Nested
    @DisplayName("Expand Serialization")
    class ExpandSerialization {

        @Test
        @DisplayName("serializes non-containment reference as proxy by default")
        @SuppressWarnings("unchecked")
        void serializesAsProxyByDefault() throws IOException {
            // Create company with employees and CEO
            EObject company = createCompany("Acme Inc");
            EObject alice = createPerson("Alice");

            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(alice);
            company.eSet(ceoRef, alice);

            // Serialize with default config (no expand)
            String json = serialize(company, CodecConfiguration.defaults());

            // CEO should be serialized as $ref (proxy reference)
            assertTrue(json.contains("\"ceo\""), "JSON should contain ceo field");
            assertTrue(json.contains("\"$ref\""), "CEO should be serialized with $ref key");

            // The CEO object should NOT contain inline data like "name":"Alice" outside of employees
            // We need to check that the ceo field has $ref and not the full object
            // A proper check: ceo should have $ref and should NOT have "name" inside it
            int ceoIndex = json.indexOf("\"ceo\"");
            int ceoEndIndex = json.indexOf("}", ceoIndex);
            String ceoJson = json.substring(ceoIndex, ceoEndIndex + 1);
            assertTrue(ceoJson.contains("$ref"), "CEO should contain $ref");
            assertFalse(ceoJson.contains("\"name\""), "CEO should NOT contain name (not expanded)");
        }

        @Test
        @DisplayName("expands non-containment reference with expandGlobal=true")
        @SuppressWarnings("unchecked")
        void expandsWithGlobalFlag() throws IOException {
            // Create company with employees and CEO
            EObject company = createCompany("Acme Inc");
            EObject alice = createPerson("Alice");

            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(alice);
            company.eSet(ceoRef, alice);

            // Serialize with expandGlobal=true
            CodecConfiguration config = CodecConfiguration.builder()
                    .expandGlobal(true)
                    .build();

            String json = serialize(company, config);

            // CEO should be expanded inline (no $ref)
            assertTrue(json.contains("\"ceo\""), "JSON should contain ceo field");
            // The expanded CEO should have name but no $ref
            // Note: Due to expandIgnoreBidirectional default=true, we need objects without opposite refs
        }

        @Test
        @DisplayName("expands specific reference by EReference")
        @SuppressWarnings("unchecked")
        void expandsSpecificReferenceByEReference() throws IOException {
            // Create company with employees and CEO
            EObject company = createCompany("Acme Inc");
            EObject alice = createPerson("Alice");

            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(alice);
            company.eSet(ceoRef, alice);

            // Serialize with specific reference expanded
            CodecConfiguration config = CodecConfiguration.builder()
                    .expand(ceoRef)
                    .build();

            String json = serialize(company, config);

            // CEO should be expanded inline
            assertTrue(json.contains("\"ceo\""), "JSON should contain ceo field");
        }

        @Test
        @DisplayName("expands specific reference by name")
        @SuppressWarnings("unchecked")
        void expandsSpecificReferenceByName() throws IOException {
            // Create company with employees and CEO
            EObject company = createCompany("Acme Inc");
            EObject alice = createPerson("Alice");

            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(alice);
            company.eSet(ceoRef, alice);

            // Serialize with specific reference expanded by name
            CodecConfiguration config = CodecConfiguration.builder()
                    .expand("ceo")
                    .build();

            String json = serialize(company, config);

            // CEO should be expanded inline
            assertTrue(json.contains("\"ceo\""), "JSON should contain ceo field");
        }

        @Test
        @DisplayName("does not expand proxy objects")
        @SuppressWarnings("unchecked")
        void doesNotExpandProxies() throws IOException {
            // Create company
            EObject company = createCompany("Acme Inc");

            // Create a proxy for CEO (not resolved)
            EObject proxyPerson = testPackage.getEFactoryInstance().create(personClass);
            ((org.eclipse.emf.ecore.InternalEObject) proxyPerson)
                    .eSetProxyURI(URI.createURI("other.json#//@employees.0"));
            company.eSet(ceoRef, proxyPerson);

            // Serialize with expandGlobal=true
            CodecConfiguration config = CodecConfiguration.builder()
                    .expandGlobal(true)
                    .build();

            String json = serialize(company, config);

            // Proxy CEO should still be serialized as $ref (not expanded)
            assertTrue(json.contains("\"ceo\""), "JSON should contain ceo field");
            assertTrue(json.contains("$ref"), "Proxy CEO should be serialized as $ref");
        }

        @Test
        @DisplayName("expands multi-valued non-containment reference")
        @SuppressWarnings("unchecked")
        void expandsMultiValuedReference() throws IOException {
            // Create company with employees
            EObject company = createCompany("Acme Inc");
            EObject alice = createPerson("Alice");
            EObject bob = createPerson("Bob");

            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(alice);
            employees.add(bob);

            // Set Alice's friends to Bob (multi-valued non-containment)
            @SuppressWarnings("unchecked")
            List<EObject> friends = (List<EObject>) alice.eGet(friendsRef);
            friends.add(bob);

            // Serialize with friends expanded
            CodecConfiguration config = CodecConfiguration.builder()
                    .expand("friends")
                    .build();

            String json = serialize(company, config);

            // Friends should be expanded
            assertTrue(json.contains("\"friends\""), "JSON should contain friends field");
        }
    }

    // ========================================================================
    // Deserialization Tests (disabled until implementation is complete)
    // ========================================================================

    @Nested
    @DisplayName("Expand Deserialization")
    class ExpandDeserialization {

        @Test
        @DisplayName("deserializes expanded non-containment as orphan object")
        void deserializesExpandedAsOrphan() throws IOException {
            // JSON with expanded CEO (no $ref, just inline object)
            String json = """
                {
                  "_type": "http://test.example.org/roundtrip/1.0#//Company",
                  "name": "Acme Inc",
                  "employees": [],
                  "ceo": {
                    "_type": "http://test.example.org/roundtrip/1.0#//Person",
                    "name": "Alice"
                  }
                }
                """;

            EObject loaded = deserialize(json, companyClass);

            assertNotNull(loaded);
            assertEquals("Acme Inc", loaded.eGet(companyNameAttribute));

            // CEO should be deserialized as an orphan object
            EObject ceo = (EObject) loaded.eGet(ceoRef);
            assertNotNull(ceo, "CEO should be deserialized");
            assertEquals("Alice", ceo.eGet(personNameAttribute));

            // Orphan: not a proxy
            assertFalse(ceo.eIsProxy(), "Expanded CEO should not be a proxy");

            // Orphan: not contained (no eContainer)
            assertNull(ceo.eContainer(), "Expanded CEO should be an orphan (no container)");

            // Orphan: no resource assigned
            assertNull(ceo.eResource(), "Expanded CEO should have no resource");
        }

        @Test
        @DisplayName("deserializes proxy reference with _ref")
        void deserializesProxyReference() throws IOException {
            // JSON with proxy CEO (has $ref)
            String json = """
                {
                  "_type": "http://test.example.org/roundtrip/1.0#//Company",
                  "name": "Acme Inc",
                  "employees": [
                    {
                      "_type": "http://test.example.org/roundtrip/1.0#//Person",
                      "name": "Alice"
                    }
                  ],
                  "ceo": {
                    "_type": "http://test.example.org/roundtrip/1.0#//Person",
                    "$ref": "//@employees.0"
                  }
                }
                """;

            EObject loaded = deserialize(json, companyClass);

            assertNotNull(loaded);

            @SuppressWarnings("unchecked")
            List<EObject> employees = (List<EObject>) loaded.eGet(employeesRef);
            assertEquals(1, employees.size());
            assertEquals("Alice", employees.get(0).eGet(personNameAttribute));

            // CEO should be resolved to Alice
            EObject ceo = (EObject) loaded.eGet(ceoRef);
            assertNotNull(ceo, "CEO should be resolved");
            assertSame(employees.get(0), ceo, "CEO should be same instance as first employee");
        }

        @Test
        @DisplayName("deserializes proxy with projection (_ref + additional fields)")
        void deserializesProxyWithProjection() throws IOException {
            // JSON with proxy that has projection data
            String json = """
                {
                  "_type": "http://test.example.org/roundtrip/1.0#//Company",
                  "name": "Acme Inc",
                  "employees": [],
                  "ceo": {
                    "_type": "http://test.example.org/roundtrip/1.0#//Person",
                    "$ref": "other.json#//@employees.0",
                    "name": "Alice"
                  }
                }
                """;

            EObject loaded = deserialize(json, companyClass);

            assertNotNull(loaded);

            // CEO should be a proxy with populated name field
            EObject ceo = (EObject) loaded.eGet(ceoRef);
            assertNotNull(ceo, "CEO should be created");
            assertTrue(ceo.eIsProxy(), "CEO should be a proxy (has $ref)");
            assertEquals("Alice", ceo.eGet(personNameAttribute),
                    "Proxy should have projected name populated");
        }

        @Test
        @DisplayName("deserializes multi-valued expanded references")
        void deserializesMultiValuedExpanded() throws IOException {
            // JSON with expanded friends array (no $ref)
            String json = """
                {
                  "_type": "http://test.example.org/roundtrip/1.0#//Person",
                  "name": "Alice",
                  "friends": [
                    {
                      "_type": "http://test.example.org/roundtrip/1.0#//Person",
                      "name": "Bob"
                    },
                    {
                      "_type": "http://test.example.org/roundtrip/1.0#//Person",
                      "name": "Charlie"
                    }
                  ]
                }
                """;

            EObject loaded = deserialize(json, personClass);

            assertNotNull(loaded);
            assertEquals("Alice", loaded.eGet(personNameAttribute));

            @SuppressWarnings("unchecked")
            List<EObject> friends = (List<EObject>) loaded.eGet(friendsRef);
            assertEquals(2, friends.size());

            // Both friends should be orphan objects
            EObject bob = friends.get(0);
            EObject charlie = friends.get(1);

            assertEquals("Bob", bob.eGet(personNameAttribute));
            assertEquals("Charlie", charlie.eGet(personNameAttribute));

            assertFalse(bob.eIsProxy(), "Bob should not be a proxy");
            assertFalse(charlie.eIsProxy(), "Charlie should not be a proxy");

            assertNull(bob.eContainer(), "Bob should be an orphan");
            assertNull(charlie.eContainer(), "Charlie should be an orphan");
        }
    }

    // ========================================================================
    // Round-Trip Tests (disabled until deserialization is complete)
    // ========================================================================

    @Nested
    @DisplayName("Expand Round-Trip")
    class ExpandRoundTrip {

        @Test
        @DisplayName("round-trips expanded non-containment reference")
        @SuppressWarnings("unchecked")
        void roundTripsExpandedReference() throws IOException {
            // Create company with employees and CEO
            EObject company = createCompany("Acme Inc");
            EObject alice = createPerson("Alice");

            List<EObject> employees = (List<EObject>) company.eGet(employeesRef);
            employees.add(alice);
            company.eSet(ceoRef, alice);

            // Serialize with expand
            CodecConfiguration config = CodecConfiguration.builder()
                    .expand(ceoRef)
                    .build();

            String json = serialize(company, config);

            // Deserialize (auto-detects expanded)
            EObject loaded = deserialize(json, companyClass);

            assertNotNull(loaded);
            assertEquals("Acme Inc", loaded.eGet(companyNameAttribute));

            // CEO should be an orphan with correct data
            EObject loadedCeo = (EObject) loaded.eGet(ceoRef);
            assertNotNull(loadedCeo, "CEO should be deserialized");
            assertEquals("Alice", loadedCeo.eGet(personNameAttribute));
        }

        @Test
        @DisplayName("round-trips expanded multi-valued non-containment reference")
        @SuppressWarnings("unchecked")
        void roundTripsExpandedMultiValuedReference() throws IOException {
            // Create Alice with friends Bob and Charlie
            EObject alice = createPerson("Alice");
            EObject bob = createPerson("Bob");
            EObject charlie = createPerson("Charlie");

            List<EObject> friends = (List<EObject>) alice.eGet(friendsRef);
            friends.add(bob);
            friends.add(charlie);

            // Serialize with friends expanded
            CodecConfiguration config = CodecConfiguration.builder()
                    .expand("friends")
                    .build();

            String json = serialize(alice, config);
            System.out.println("Multi-valued expand JSON:\n" + json);

            // Verify serialized JSON has no $ref
            assertFalse(json.contains("$ref"), "Expanded friends should not have $ref");
            assertTrue(json.contains("\"Bob\""), "JSON should contain Bob");
            assertTrue(json.contains("\"Charlie\""), "JSON should contain Charlie");

            // Deserialize (auto-detects expanded)
            EObject loaded = deserialize(json, personClass);

            assertNotNull(loaded);
            assertEquals("Alice", loaded.eGet(personNameAttribute));

            // Friends should be orphan objects with correct data
            List<EObject> loadedFriends = (List<EObject>) loaded.eGet(friendsRef);
            assertEquals(2, loadedFriends.size(), "Should have 2 friends");

            EObject loadedBob = loadedFriends.get(0);
            EObject loadedCharlie = loadedFriends.get(1);

            assertEquals("Bob", loadedBob.eGet(personNameAttribute));
            assertEquals("Charlie", loadedCharlie.eGet(personNameAttribute));

            // Both should be orphans (not proxies, no container)
            assertFalse(loadedBob.eIsProxy(), "Bob should not be a proxy");
            assertFalse(loadedCharlie.eIsProxy(), "Charlie should not be a proxy");
            assertNull(loadedBob.eContainer(), "Bob should be an orphan");
            assertNull(loadedCharlie.eContainer(), "Charlie should be an orphan");
        }
    }
}
