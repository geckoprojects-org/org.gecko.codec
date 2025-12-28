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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveTypeConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tools.jackson.core.JsonGenerator;

/**
 * Tests for {@link TypeSerializationEntry}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#61-type-serialization">Spec 6.1: Type Serialization</a>
 */
@DisplayName("TypeSerializationEntry")
class TypeSerializationEntryTest {

    private EClass testEClass;
    private EPackage testPackage;
    private JsonGenerator generator;

    private SerializationState createState(EObject eObject) {
        return new SerializationState(eObject);
    }

    @BeforeEach
    void setUp() {
        generator = mock(JsonGenerator.class);

        // Create a test EPackage and EClass
        testPackage = EcoreFactory.eINSTANCE.createEPackage();
        testPackage.setName("testpackage");
        testPackage.setNsURI("http://example.org/test");
        testPackage.setNsPrefix("test");

        testEClass = EcoreFactory.eINSTANCE.createEClass();
        testEClass.setName("Person");
        testPackage.getEClassifiers().add(testEClass);
    }

    private EffectiveTypeConfig createDefaultConfig() {
        return EffectiveTypeConfig.builder()
                .enabled(true)
                .typeKey("_type")
                .build();
    }

    @Test
    @DisplayName("getKey returns default _type key")
    void getKeyReturnsDefaultTypeKey() {
        EffectiveTypeConfig config = createDefaultConfig();
        TypeSerializationEntry entry = new TypeSerializationEntry(config, testEClass);
        assertEquals("_type", entry.getKey());
    }

    @Test
    @DisplayName("getKey returns custom key from config")
    void getKeyReturnsCustomKeyFromConfig() {
        EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                .enabled(true)
                .typeKey("@class")
                .build();

        TypeSerializationEntry entry = new TypeSerializationEntry(config, testEClass);
        assertEquals("@class", entry.getKey());
    }

    @Test
    @DisplayName("shouldSerialize returns true when enabled")
    void shouldSerializeReturnsTrueWhenEnabled() {
        EffectiveTypeConfig config = createDefaultConfig();
        TypeSerializationEntry entry = new TypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        assertTrue(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("shouldSerialize returns false when not enabled")
    void shouldSerializeReturnsFalseWhenNotEnabled() {
        EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                .enabled(false)
                .typeKey("_type")
                .build();

        TypeSerializationEntry entry = new TypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        assertFalse(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("serialize writes EClass URI")
    void serializeWritesEClassUri() {
        EffectiveTypeConfig config = createDefaultConfig();
        TypeSerializationEntry entry = new TypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        entry.serialize(createState(eObject), generator, null);

        verify(generator).writeStringProperty("_type", "http://example.org/test#//Person");
    }

    @Test
    @DisplayName("serialize writes discriminator value from config")
    void serializeWritesDiscriminatorValueFromConfig() {
        EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                .enabled(true)
                .typeKey("_type")
                .discriminatorValue("person")
                .build();

        TypeSerializationEntry entry = new TypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        entry.serialize(createState(eObject), generator, null);

        verify(generator).writeStringProperty("_type", "person");
    }

    @Test
    @DisplayName("serialize uses EClass URI when discriminator is empty")
    void serializeUsesEClassUriWhenDiscriminatorEmpty() {
        EffectiveTypeConfig config = EffectiveTypeConfig.builder()
                .enabled(true)
                .typeKey("_type")
                .discriminatorValue("")
                .build();

        TypeSerializationEntry entry = new TypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        entry.serialize(createState(eObject), generator, null);

        verify(generator).writeStringProperty("_type", "http://example.org/test#//Person");
    }
}
