/**
 * Copyright (c) 2012 - 2026 Data In Motion and others.
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
package org.eclipse.fennec.codec.metadata.type;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TypeDiscriminatorService}.
 */
@DisplayName("TypeDiscriminatorService")
class TypeDiscriminatorServiceTest {

    private TypeDiscriminatorService service;
    private EClass personClass;
    private EClass customerClass;
    private EClass deviceClass;

    @BeforeEach
    void setUp() {
        service = new TypeDiscriminatorService();

        personClass = EcoreFactory.eINSTANCE.createEClass();
        personClass.setName("Person");

        customerClass = EcoreFactory.eINSTANCE.createEClass();
        customerClass.setName("Customer");

        deviceClass = EcoreFactory.eINSTANCE.createEClass();
        deviceClass.setName("Device");
    }

    @Nested
    @DisplayName("getOrCreateRegistry")
    class GetOrCreateRegistry {

        @Test
        @DisplayName("creates new registry for unknown mapId")
        void createsNewRegistry() {
            TypeDiscriminatorRegistry registry = service.getOrCreateRegistry("test-map");

            assertNotNull(registry);
            assertEquals("test-map", registry.getMapId());
            assertTrue(service.hasRegistry("test-map"));
        }

        @Test
        @DisplayName("returns existing registry for known mapId")
        void returnsExistingRegistry() {
            TypeDiscriminatorRegistry first = service.getOrCreateRegistry("test-map");
            TypeDiscriminatorRegistry second = service.getOrCreateRegistry("test-map");

            assertSame(first, second);
        }

        @Test
        @DisplayName("throws on null mapId")
        void throwsOnNullMapId() {
            assertThrows(NullPointerException.class, () -> service.getOrCreateRegistry(null));
        }
    }

    @Nested
    @DisplayName("getRegistry")
    class GetRegistry {

        @Test
        @DisplayName("returns registry for known mapId")
        void returnsRegistryForKnown() {
            service.getOrCreateRegistry("test-map");
            assertNotNull(service.getRegistry("test-map"));
        }

        @Test
        @DisplayName("returns null for unknown mapId")
        void returnsNullForUnknown() {
            assertNull(service.getRegistry("unknown"));
        }

        @Test
        @DisplayName("returns null for null mapId")
        void returnsNullForNull() {
            assertNull(service.getRegistry(null));
        }
    }

    @Nested
    @DisplayName("getEClass")
    class GetEClass {

        @Test
        @DisplayName("returns EClass from specific registry")
        void returnsEClassFromSpecificRegistry() {
            service.getOrCreateRegistry("map1").register("person", personClass);
            service.getOrCreateRegistry("map2").register("customer", customerClass);

            assertEquals(personClass, service.getEClass("map1", "person"));
            assertEquals(customerClass, service.getEClass("map2", "customer"));
            assertNull(service.getEClass("map1", "customer"));
            assertNull(service.getEClass("map2", "person"));
        }

        @Test
        @DisplayName("returns null for unknown mapId")
        void returnsNullForUnknownMapId() {
            assertNull(service.getEClass("unknown", "person"));
        }
    }

    @Nested
    @DisplayName("getEClassFromAny")
    class GetEClassFromAny {

        @Test
        @DisplayName("finds EClass across all registries")
        void findsEClassAcrossAllRegistries() {
            service.getOrCreateRegistry("map1").register("person", personClass);
            service.getOrCreateRegistry("map2").register("customer", customerClass);

            assertEquals(personClass, service.getEClassFromAny("person"));
            assertEquals(customerClass, service.getEClassFromAny("customer"));
        }

        @Test
        @DisplayName("returns null for unknown discriminator")
        void returnsNullForUnknown() {
            service.getOrCreateRegistry("map1").register("person", personClass);

            assertNull(service.getEClassFromAny("unknown"));
        }

        @Test
        @DisplayName("returns null for null discriminator")
        void returnsNullForNull() {
            assertNull(service.getEClassFromAny(null));
        }
    }

    @Nested
    @DisplayName("getDiscriminatorValue")
    class GetDiscriminatorValue {

        @Test
        @DisplayName("returns discriminator from specific registry")
        void returnsDiscriminatorFromSpecificRegistry() {
            service.getOrCreateRegistry("map1").register("person", personClass);
            service.getOrCreateRegistry("map2").register("customer", customerClass);

            assertEquals("person", service.getDiscriminatorValue("map1", personClass));
            assertEquals("customer", service.getDiscriminatorValue("map2", customerClass));
            assertNull(service.getDiscriminatorValue("map1", customerClass));
        }

        @Test
        @DisplayName("returns null for unknown mapId")
        void returnsNullForUnknownMapId() {
            assertNull(service.getDiscriminatorValue("unknown", personClass));
        }
    }

    @Nested
    @DisplayName("getDiscriminatorValueFromAny")
    class GetDiscriminatorValueFromAny {

        @Test
        @DisplayName("finds discriminator across all registries")
        void findsDiscriminatorAcrossAllRegistries() {
            service.getOrCreateRegistry("map1").register("person", personClass);
            service.getOrCreateRegistry("map2").register("customer", customerClass);

            assertEquals("person", service.getDiscriminatorValueFromAny(personClass));
            assertEquals("customer", service.getDiscriminatorValueFromAny(customerClass));
        }

        @Test
        @DisplayName("returns null for unknown EClass")
        void returnsNullForUnknown() {
            service.getOrCreateRegistry("map1").register("person", personClass);

            assertNull(service.getDiscriminatorValueFromAny(deviceClass));
        }

        @Test
        @DisplayName("returns null for null EClass")
        void returnsNullForNull() {
            assertNull(service.getDiscriminatorValueFromAny(null));
        }
    }

    @Nested
    @DisplayName("multiple mapIds")
    class MultipleMapIds {

        @Test
        @DisplayName("same discriminator can map to different EClasses in different mapIds")
        void sameDiscriminatorDifferentMappings() {
            // In "api-v1", "entity" maps to Person
            service.getOrCreateRegistry("api-v1").register("entity", personClass);
            // In "api-v2", "entity" maps to Customer
            service.getOrCreateRegistry("api-v2").register("entity", customerClass);

            assertEquals(personClass, service.getEClass("api-v1", "entity"));
            assertEquals(customerClass, service.getEClass("api-v2", "entity"));
        }

        @Test
        @DisplayName("same EClass can have different discriminators in different mapIds")
        void sameEClassDifferentDiscriminators() {
            // Person is "person" in map1
            service.getOrCreateRegistry("map1").register("person", personClass);
            // Person is "user" in map2
            service.getOrCreateRegistry("map2").register("user", personClass);

            assertEquals("person", service.getDiscriminatorValue("map1", personClass));
            assertEquals("user", service.getDiscriminatorValue("map2", personClass));
        }
    }

    @Nested
    @DisplayName("getMapIds / getRegistries")
    class CollectionMethods {

        @Test
        @DisplayName("getMapIds returns all registered mapIds")
        void getMapIdsReturnsAll() {
            service.getOrCreateRegistry("map1");
            service.getOrCreateRegistry("map2");
            service.getOrCreateRegistry("map3");

            var mapIds = service.getMapIds();

            assertEquals(3, mapIds.size());
            assertTrue(mapIds.contains("map1"));
            assertTrue(mapIds.contains("map2"));
            assertTrue(mapIds.contains("map3"));
        }

        @Test
        @DisplayName("getRegistries returns all registries")
        void getRegistriesReturnsAll() {
            service.getOrCreateRegistry("map1").register("a", personClass);
            service.getOrCreateRegistry("map2").register("b", customerClass);

            var registries = service.getRegistries();

            assertEquals(2, registries.size());
        }

        @Test
        @DisplayName("getTotalMappings returns sum of all mappings")
        void getTotalMappingsReturnsSum() {
            service.getOrCreateRegistry("map1").register("a", personClass);
            service.getOrCreateRegistry("map1").register("b", customerClass);
            service.getOrCreateRegistry("map2").register("c", deviceClass);

            assertEquals(3, service.getTotalMappings());
        }
    }

    @Nested
    @DisplayName("clear")
    class Clear {

        @Test
        @DisplayName("removes all registries")
        void removesAllRegistries() {
            service.getOrCreateRegistry("map1").register("person", personClass);
            service.getOrCreateRegistry("map2").register("customer", customerClass);

            service.clear();

            assertEquals(0, service.getMapIds().size());
            assertEquals(0, service.getTotalMappings());
            assertFalse(service.hasRegistry("map1"));
            assertFalse(service.hasRegistry("map2"));
        }
    }

    @Test
    @DisplayName("toString includes registry count and total mappings")
    void toStringIncludesCounts() {
        service.getOrCreateRegistry("map1").register("person", personClass);
        service.getOrCreateRegistry("map2").register("customer", customerClass);

        String str = service.toString();

        assertTrue(str.contains("2")); // registries
        assertTrue(str.contains("2")); // totalMappings
    }
}
