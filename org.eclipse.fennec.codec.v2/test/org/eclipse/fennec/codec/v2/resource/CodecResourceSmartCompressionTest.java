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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
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
 * Tests for smart compression behavior.
 * <p>
 * Smart compression suppresses the `_type` field when the instance type equals
 * the reference type (redundant type information). This is controlled by the
 * global {@code smartCompression} setting in {@link CodecConfiguration}.
 * </p>
 * <p>
 * Test scenarios:
 * <ul>
 *   <li><b>Smart compression OFF</b>: `_type` is always written for contained objects</li>
 *   <li><b>Smart compression ON (same type)</b>: `_type` is suppressed when instance type == reference type</li>
 *   <li><b>Smart compression ON (polymorphic)</b>: `_type` is written when instance type != reference type</li>
 * </ul>
 * </p>
 *
 * @see CodecConfiguration#isSmartCompression()
 */
@DisplayName("CodecResource Smart Compression Tests")
class CodecResourceSmartCompressionTest {

    private static final String TEST_ECORE = "test-smart-compression.ecore";

    private EcoreHelper ecoreHelper;
    private EPackage testPackage;
    private MetadataService metadataService;

    // EClasses
    private EClass animalClass;
    private EClass dogClass;
    private EClass catClass;
    private EClass ownerClass;
    private EClass kennelClass;

    // EReferences
    private EReference ownerPetRef;
    private EReference ownerPetsRef;
    private EReference kennelDogRef;
    private EReference kennelDogsRef;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(CodecResourceSmartCompressionTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        // Create metadata service and register package
        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        // Load EClasses
        animalClass = ecoreHelper.getEClass(testPackage, "Animal");
        dogClass = ecoreHelper.getEClass(testPackage, "Dog");
        catClass = ecoreHelper.getEClass(testPackage, "Cat");
        ownerClass = ecoreHelper.getEClass(testPackage, "Owner");
        kennelClass = ecoreHelper.getEClass(testPackage, "Kennel");

        // Load EReferences
        ownerPetRef = (EReference) ownerClass.getEStructuralFeature("pet");
        ownerPetsRef = (EReference) ownerClass.getEStructuralFeature("pets");
        kennelDogRef = (EReference) kennelClass.getEStructuralFeature("dog");
        kennelDogsRef = (EReference) kennelClass.getEStructuralFeature("dogs");
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
        ecoreHelper.releaseAll();
    }

    // ========================================================================
    // Factory Methods
    // ========================================================================

    private EObject createDog(String name, String breed) {
        EObject dog = testPackage.getEFactoryInstance().create(dogClass);
        dog.eSet(dogClass.getEStructuralFeature("name"), name);
        dog.eSet(dogClass.getEStructuralFeature("breed"), breed);
        return dog;
    }

    private EObject createCat(String name, boolean indoor) {
        EObject cat = testPackage.getEFactoryInstance().create(catClass);
        cat.eSet(catClass.getEStructuralFeature("name"), name);
        cat.eSet(catClass.getEStructuralFeature("indoor"), indoor);
        return cat;
    }

    private EObject createOwner(String name) {
        EObject owner = testPackage.getEFactoryInstance().create(ownerClass);
        owner.eSet(ownerClass.getEStructuralFeature("name"), name);
        return owner;
    }

    private EObject createKennel(String name) {
        EObject kennel = testPackage.getEFactoryInstance().create(kennelClass);
        kennel.eSet(kennelClass.getEStructuralFeature("name"), name);
        return kennel;
    }

    private CodecResource createResource(boolean smartCompression) {
        CodecConfiguration config = CodecConfiguration.builder()
                .smartCompression(smartCompression)
                .build();
        return new CodecResource(
                URI.createURI("test://smartcompression.json"),
                metadataService,
                config,
                null);
    }

    private String serialize(EObject object, boolean smartCompression) throws IOException {
        CodecResource resource = createResource(smartCompression);
        resource.getContents().add(object);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resource.save(out, Map.of());

        return out.toString(StandardCharsets.UTF_8);
    }

    // ========================================================================
    // Smart Compression OFF Tests - _type always written
    // ========================================================================

    @Nested
    @DisplayName("Smart Compression OFF")
    class SmartCompressionOff {

        @Test
        @DisplayName("same-type reference writes _type")
        void sameType_smartCompressionOff_writesType() throws IOException {
            // Kennel.dog is typed as Dog, holding a Dog instance
            EObject kennel = createKennel("Happy Paws");
            EObject dog = createDog("Buddy", "Golden Retriever");
            kennel.eSet(kennelDogRef, dog);

            String json = serialize(kennel, false);
            System.out.println("Kennel with Dog (smart compression OFF):\n" + json);

            // With smart compression OFF, _type SHOULD be written even for same-type references
            assertTrue(json.contains("\"_type\""),
                    "JSON should contain _type field when smart compression is OFF");

            // Verify content is correct
            assertTrue(json.contains("\"name\":\"Happy Paws\""),
                    "JSON should contain kennel name");
            assertTrue(json.contains("\"breed\":\"Golden Retriever\""),
                    "JSON should contain dog breed");
        }

        @Test
        @DisplayName("same-type list writes _type for each element")
        void sameType_list_smartCompressionOff_writesType() throws IOException {
            // Kennel.dogs is typed as Dog[], holding Dog instances
            EObject kennel = createKennel("Happy Paws");
            EObject dog1 = createDog("Buddy", "Golden Retriever");
            EObject dog2 = createDog("Max", "German Shepherd");

            @SuppressWarnings("unchecked")
            EList<EObject> dogs = (EList<EObject>) kennel.eGet(kennelDogsRef);
            dogs.add(dog1);
            dogs.add(dog2);

            String json = serialize(kennel, false);
            System.out.println("Kennel with Dogs list (smart compression OFF):\n" + json);

            // Count _type occurrences - should be at least 2 (one for each Dog)
            int typeCount = countOccurrences(json, "\"_type\"");
            assertTrue(typeCount >= 2,
                    "JSON should contain _type for each Dog when smart compression is OFF, found: " + typeCount);
        }

        @Test
        @DisplayName("polymorphic reference writes _type")
        void polymorphic_smartCompressionOff_writesType() throws IOException {
            // Owner.pet is typed as Animal, holding a Dog instance
            EObject owner = createOwner("John");
            EObject dog = createDog("Buddy", "Golden Retriever");
            owner.eSet(ownerPetRef, dog);

            String json = serialize(owner, false);
            System.out.println("Owner with Dog pet (smart compression OFF):\n" + json);

            // _type SHOULD be written (polymorphic reference)
            assertTrue(json.contains("\"_type\""),
                    "JSON should contain _type field for polymorphic reference");
        }
    }

    // ========================================================================
    // Smart Compression ON with Same Type - _type suppressed
    // ========================================================================

    @Nested
    @DisplayName("Smart Compression ON - Same Type")
    class SmartCompressionOnSameType {

        @Test
        @DisplayName("same-type reference suppresses _type")
        void sameType_smartCompressionOn_suppressesType() throws IOException {
            // Kennel.dog is typed as Dog, holding a Dog instance
            EObject kennel = createKennel("Happy Paws");
            EObject dog = createDog("Buddy", "Golden Retriever");
            kennel.eSet(kennelDogRef, dog);

            String json = serialize(kennel, true);
            System.out.println("Kennel with Dog (smart compression ON):\n" + json);

            // With smart compression ON and same type, _type should be suppressed
            // Count _type occurrences in the nested dog object (not root)
            // Root may still have _type, so we check the dog section
            String dogSection = extractSection(json, "dog");
            assertFalse(dogSection.contains("\"_type\""),
                    "Dog section should NOT contain _type when smart compression is ON and types match.\n" +
                    "Dog section: " + dogSection);
        }

        @Test
        @DisplayName("same-type list suppresses _type for each element")
        void sameType_list_smartCompressionOn_suppressesType() throws IOException {
            // Kennel.dogs is typed as Dog[], holding Dog instances
            EObject kennel = createKennel("Happy Paws");
            EObject dog1 = createDog("Buddy", "Golden Retriever");
            EObject dog2 = createDog("Max", "German Shepherd");

            @SuppressWarnings("unchecked")
            EList<EObject> dogs = (EList<EObject>) kennel.eGet(kennelDogsRef);
            dogs.add(dog1);
            dogs.add(dog2);

            String json = serialize(kennel, true);
            System.out.println("Kennel with Dogs list (smart compression ON):\n" + json);

            // Extract dogs array section
            String dogsSection = extractSection(json, "dogs");
            assertFalse(dogsSection.contains("\"_type\""),
                    "Dogs section should NOT contain _type when smart compression is ON and types match.\n" +
                    "Dogs section: " + dogsSection);
        }
    }

    // ========================================================================
    // Smart Compression ON with Polymorphism - _type still written
    // ========================================================================

    @Nested
    @DisplayName("Smart Compression ON - Polymorphic")
    class SmartCompressionOnPolymorphic {

        @Test
        @DisplayName("polymorphic reference still writes _type")
        void polymorphic_smartCompressionOn_writesType() throws IOException {
            // Owner.pet is typed as Animal (abstract), holding a Dog instance
            EObject owner = createOwner("John");
            EObject dog = createDog("Buddy", "Golden Retriever");
            owner.eSet(ownerPetRef, dog);

            String json = serialize(owner, true);
            System.out.println("Owner with Dog pet (smart compression ON):\n" + json);

            // _type SHOULD be written because Dog != Animal
            String petSection = extractSection(json, "pet");
            assertTrue(petSection.contains("\"_type\""),
                    "Pet section SHOULD contain _type when instance type != reference type.\n" +
                    "Pet section: " + petSection);
        }

        @Test
        @DisplayName("polymorphic list writes _type for each element")
        void polymorphic_list_smartCompressionOn_writesType() throws IOException {
            // Owner.pets is typed as Animal[], holding Dog and Cat instances
            EObject owner = createOwner("John");
            EObject dog = createDog("Buddy", "Golden Retriever");
            EObject cat = createCat("Whiskers", true);

            @SuppressWarnings("unchecked")
            EList<EObject> pets = (EList<EObject>) owner.eGet(ownerPetsRef);
            pets.add(dog);
            pets.add(cat);

            String json = serialize(owner, true);
            System.out.println("Owner with Dog and Cat pets (smart compression ON):\n" + json);

            // _type SHOULD be written for each element because Dog/Cat != Animal
            String petsSection = extractSection(json, "pets");
            int typeCount = countOccurrences(petsSection, "\"_type\"");
            assertEquals(2, typeCount,
                    "Pets section should contain _type for each pet when types differ.\n" +
                    "Pets section: " + petsSection);
        }
    }

    // ========================================================================
    // Edge Cases
    // ========================================================================

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        @DisplayName("root object still writes _type (no reference context)")
        void rootObject_smartCompressionOn_stillWritesType() throws IOException {
            // Root object has no reference context, so _type should still be written
            EObject dog = createDog("Buddy", "Golden Retriever");

            String json = serialize(dog, true);
            System.out.println("Root Dog object (smart compression ON):\n" + json);

            // Root object should still have _type (no reference to compare against)
            assertTrue(json.contains("\"_type\""),
                    "Root object should contain _type even with smart compression ON");
        }

        @Test
        @DisplayName("mixed list with same base type writes _type for all")
        void mixedList_smartCompressionOn_writesTypeForAll() throws IOException {
            // Owner.pets typed as Animal[], containing both Dog and Cat
            // All elements should have _type because none match Animal exactly
            EObject owner = createOwner("John");
            EObject dog1 = createDog("Buddy", "Golden Retriever");
            EObject cat1 = createCat("Whiskers", true);
            EObject dog2 = createDog("Max", "German Shepherd");

            @SuppressWarnings("unchecked")
            EList<EObject> pets = (EList<EObject>) owner.eGet(ownerPetsRef);
            pets.add(dog1);
            pets.add(cat1);
            pets.add(dog2);

            String json = serialize(owner, true);
            System.out.println("Owner with mixed pets (smart compression ON):\n" + json);

            // All 3 pets should have _type
            String petsSection = extractSection(json, "pets");
            int typeCount = countOccurrences(petsSection, "\"_type\"");
            assertEquals(3, typeCount,
                    "All pets in polymorphic list should have _type.\n" +
                    "Pets section: " + petsSection);
        }

        @Test
        @DisplayName("nested containment with smart compression")
        void nestedContainment_smartCompressionOn() throws IOException {
            // Test nested containment: Kennel → Dog (same type)
            EObject kennel = createKennel("Happy Paws");
            EObject dog = createDog("Buddy", "Golden Retriever");
            kennel.eSet(kennelDogRef, dog);

            String json = serialize(kennel, true);
            System.out.println("Nested Kennel→Dog (smart compression ON):\n" + json);

            // Root (Kennel) should have _type, nested Dog should NOT
            assertTrue(json.startsWith("{") && json.contains("\"_type\":"),
                    "Root kennel should have _type");

            String dogSection = extractSection(json, "dog");
            assertFalse(dogSection.contains("\"_type\""),
                    "Nested dog should NOT have _type when types match");
        }
    }

    // ========================================================================
    // Utility Methods
    // ========================================================================

    private int countOccurrences(String text, String pattern) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }

    /**
     * Extracts a JSON section for a given field name.
     * This is a simple extraction for testing purposes.
     */
    private String extractSection(String json, String fieldName) {
        String pattern = "\"" + fieldName + "\":";
        int start = json.indexOf(pattern);
        if (start < 0) {
            return "";
        }
        start += pattern.length();

        // Skip whitespace
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }

        if (start >= json.length()) {
            return "";
        }

        char firstChar = json.charAt(start);
        if (firstChar == '{') {
            // Object - find matching closing brace
            return extractBalanced(json, start, '{', '}');
        } else if (firstChar == '[') {
            // Array - find matching closing bracket
            return extractBalanced(json, start, '[', ']');
        } else {
            // Primitive - find end
            int end = start;
            while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') {
                end++;
            }
            return json.substring(start, end);
        }
    }

    private String extractBalanced(String json, int start, char open, char close) {
        int depth = 0;
        int end = start;
        while (end < json.length()) {
            char c = json.charAt(end);
            if (c == open) {
                depth++;
            } else if (c == close) {
                depth--;
                if (depth == 0) {
                    return json.substring(start, end + 1);
                }
            }
            end++;
        }
        return json.substring(start);
    }
}
