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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ReferenceSerializationEntry}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#65-reference-serialization">Spec 6.5: Reference Serialization</a>
 */
@DisplayName("ReferenceSerializationEntry")
@Deprecated
@org.junit.jupiter.api.Disabled("Migrated to org.eclipse.fennec.codec.ser.ReferenceSerializationEntryTest")
class ReferenceSerializationEntryTest extends SerializationEntryTestBase {

    private EffectiveFeatureConfig createDefaultConfig(String key, EReference reference) {
        return EffectiveFeatureConfig.builder()
                .feature(reference)
                .key(key)
                .serialize(true)
                .serializeNull(false)
                .serializeEmpty(false)
                .build();
    }

    @Nested
    @DisplayName("getKey")
    class GetKeyTests {

        @Test
        @DisplayName("returns key from config")
        void returnsKeyFromConfig() {
            EffectiveFeatureConfig config = createDefaultConfig("address", addressRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");
            assertEquals("address", entry.getKey());
        }

        @Test
        @DisplayName("returns custom key from config")
        void returnsCustomKeyFromConfig() {
            EffectiveFeatureConfig config = createDefaultConfig("homeAddress", addressRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");
            assertEquals("homeAddress", entry.getKey());
        }
    }

    @Nested
    @DisplayName("shouldSerialize")
    class ShouldSerializeTests {

        @Test
        @DisplayName("returns false when serialize is false")
        void returnsFalseWhenSerializeIsFalse() {
            EffectiveFeatureConfig config = EffectiveFeatureConfig.builder()
                    .feature(addressRef)
                    .key("address")
                    .serialize(false)
                    .build();

            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");
            EObject person = createPerson();

            assertFalse(entry.shouldSerialize(createState(person)));
        }

        @Test
        @DisplayName("returns false for null value by default")
        void returnsFalseForNullValueByDefault() {
            EffectiveFeatureConfig config = createDefaultConfig("address", addressRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");

            EObject person = createPerson();
            // address is null by default

            assertFalse(entry.shouldSerialize(createState(person)));
        }

        @Test
        @DisplayName("returns true for null value when serializeNull is true")
        void returnsTrueForNullValueWhenConfigured() {
            EffectiveFeatureConfig config = EffectiveFeatureConfig.builder()
                    .feature(addressRef)
                    .key("address")
                    .serialize(true)
                    .serializeNull(true)
                    .build();

            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");

            EObject person = createPerson();
            // address is null by default

            assertTrue(entry.shouldSerialize(createState(person)));
        }

        @Test
        @DisplayName("returns false for empty list by default")
        void returnsFalseForEmptyListByDefault() {
            EffectiveFeatureConfig config = createDefaultConfig("friends", friendsRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, friendsRef, "_ref");

            EObject person = createPerson();
            // friends is empty by default

            assertFalse(entry.shouldSerialize(createState(person)));
        }

        @Test
        @DisplayName("returns true for empty list when serializeEmpty is true")
        void returnsTrueForEmptyListWhenConfigured() {
            EffectiveFeatureConfig config = EffectiveFeatureConfig.builder()
                    .feature(friendsRef)
                    .key("friends")
                    .serialize(true)
                    .serializeEmpty(true)
                    .build();

            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, friendsRef, "_ref");

            EObject person = createPerson();
            // friends is empty by default

            assertTrue(entry.shouldSerialize(createState(person)));
        }

        @Test
        @DisplayName("returns true for non-null reference")
        void returnsTrueForNonNullReference() {
            EffectiveFeatureConfig config = createDefaultConfig("address", addressRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");

            EObject person = createPerson();
            EObject address = createAddress("123 Main St", "Springfield");
            person.eSet(addressRef, address);

            assertTrue(entry.shouldSerialize(createState(person)));
        }

        @Test
        @DisplayName("returns true for non-empty list")
        void returnsTrueForNonEmptyList() {
            EffectiveFeatureConfig config = createDefaultConfig("friends", friendsRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, friendsRef, "_ref");

            EObject person = createPerson("John");
            EObject friend = createPerson("Jane");
            @SuppressWarnings("unchecked")
            EList<EObject> friends = (EList<EObject>) person.eGet(friendsRef);
            friends.add(friend);

            assertTrue(entry.shouldSerialize(createState(person)));
        }
    }

    @Nested
    @DisplayName("serialize")
    class SerializeTests {

        @Test
        @DisplayName("writes null property when value is null")
        void writesNullPropertyWhenValueNull() {
            EffectiveFeatureConfig config = createDefaultConfig("address", addressRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");

            EObject person = createPerson();
            // address is null by default

            entry.serialize(createState(person), generator, serializationContext);

            verify(generator).writeNullProperty("address");
        }

        @Test
        @DisplayName("serializes containment reference inline")
        void serializesContainmentReferenceInline() {
            EffectiveFeatureConfig config = createDefaultConfig("address", addressRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");

            EObject person = createPerson();
            EObject address = createAddress("123 Main St", "Springfield");
            person.eSet(addressRef, address);

            entry.serialize(createState(person), generator, serializationContext);

            verify(generator).writeName("address");
            verify(serializationContext).writeValue(generator, address);
        }

        @Test
        @DisplayName("serializes non-containment reference as _ref object")
        void serializesNonContainmentAsRef() {
            EffectiveFeatureConfig config = createDefaultConfig("manager", managerRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, managerRef, "_ref");

            EObject person = createPerson("John");
            EObject manager = createPerson("Boss");
            manager.eSet(idAttribute, "boss-id");
            person.eSet(managerRef, manager);

            entry.serialize(createState(person), generator, serializationContext);

            verify(generator).writeName("manager");
            verify(generator).writeStartObject();
            // The ref will be the EClass URI since there's no resource
            verify(generator).writeStringProperty("_ref", "http://test.example.org/serialization/1.0#//Person");
            verify(generator).writeEndObject();
        }

        @Test
        @DisplayName("serializes multi-valued containment as array")
        void serializesMultiValuedContainmentAsArray() {
            EffectiveFeatureConfig config = createDefaultConfig("friends", friendsRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, friendsRef, "_ref");

            EObject person = createPerson("John");
            EObject friend1 = createPerson("Jane");
            EObject friend2 = createPerson("Bob");

            @SuppressWarnings("unchecked")
            EList<EObject> friends = (EList<EObject>) person.eGet(friendsRef);
            friends.add(friend1);
            friends.add(friend2);

            entry.serialize(createState(person), generator, serializationContext);

            verify(generator).writeName("friends");
            verify(generator).writeStartArray();
            verify(serializationContext).writeValue(generator, friend1);
            verify(serializationContext).writeValue(generator, friend2);
            verify(generator).writeEndArray();
        }

        @Test
        @DisplayName("serializes multi-valued non-containment as array of refs")
        void serializesMultiValuedNonContainmentAsArrayOfRefs() {
            EffectiveFeatureConfig config = createDefaultConfig("colleagues", colleaguesRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, colleaguesRef, "_ref");

            EObject person = createPerson("John");
            EObject colleague1 = createPerson("Alice");
            EObject colleague2 = createPerson("Charlie");

            @SuppressWarnings("unchecked")
            EList<EObject> colleagues = (EList<EObject>) person.eGet(colleaguesRef);
            colleagues.add(colleague1);
            colleagues.add(colleague2);

            entry.serialize(createState(person), generator, serializationContext);

            verify(generator).writeName("colleagues");
            verify(generator).writeStartArray();
            // Each non-containment ref is serialized as a ref object
            verify(generator).writeEndArray();
        }

        @Test
        @DisplayName("uses custom ref key")
        void usesCustomRefKey() {
            EffectiveFeatureConfig config = createDefaultConfig("manager", managerRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, managerRef, "@ref");

            EObject person = createPerson("John");
            EObject manager = createPerson("Boss");
            person.eSet(managerRef, manager);

            entry.serialize(createState(person), generator, serializationContext);

            verify(generator).writeStringProperty("@ref", "http://test.example.org/serialization/1.0#//Person");
        }

        @Test
        @DisplayName("writes custom key from config")
        void writesCustomKeyFromConfig() {
            EffectiveFeatureConfig config = createDefaultConfig("homeAddress", addressRef);
            ReferenceSerializationEntry entry = new ReferenceSerializationEntry(config, addressRef, "_ref");

            EObject person = createPerson();
            EObject address = createAddress("456 Oak Ave", "Shelbyville");
            person.eSet(addressRef, address);

            entry.serialize(createState(person), generator, serializationContext);

            verify(generator).writeName("homeAddress");
        }
    }
}
