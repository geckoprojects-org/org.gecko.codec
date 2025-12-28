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
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveIdConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tools.jackson.core.JsonGenerator;

/**
 * Tests for {@link IdSerializationEntry}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#62-id-serialization">Spec 6.2: ID Serialization</a>
 */
@DisplayName("IdSerializationEntry")
class IdSerializationEntryTest {

    private EClass testEClass;
    private EAttribute idAttribute;
    private JsonGenerator generator;

    @BeforeEach
    void setUp() {
        generator = mock(JsonGenerator.class);

        // Create a test EClass with an ID attribute
        testEClass = EcoreFactory.eINSTANCE.createEClass();
        testEClass.setName("TestClass");

        idAttribute = EcoreFactory.eINSTANCE.createEAttribute();
        idAttribute.setName("id");
        idAttribute.setEType(EcorePackage.Literals.ESTRING);
        idAttribute.setID(true);
        testEClass.getEStructuralFeatures().add(idAttribute);
    }

    private SerializationState createState(EObject eObject) {
        return new SerializationState(eObject);
    }

    private EffectiveIdConfig createDefaultConfig() {
        return EffectiveIdConfig.builder()
                .enabled(true)
                .key("_id")
                .onTop(true)
                .build();
    }

    @Test
    @DisplayName("getKey returns default _id key")
    void getKeyReturnsDefaultIdKey() {
        EffectiveIdConfig config = createDefaultConfig();
        IdSerializationEntry entry = new IdSerializationEntry(config);
        assertEquals("_id", entry.getKey());
    }

    @Test
    @DisplayName("getKey returns custom key from config")
    void getKeyReturnsCustomKeyFromConfig() {
        EffectiveIdConfig config = EffectiveIdConfig.builder()
                .enabled(true)
                .key("customId")
                .build();

        IdSerializationEntry entry = new IdSerializationEntry(config);
        assertEquals("customId", entry.getKey());
    }

    @Test
    @DisplayName("shouldSerialize returns false when enabled is false")
    void shouldSerializeReturnsFalseWhenNotEnabled() {
        EffectiveIdConfig config = EffectiveIdConfig.builder()
                .enabled(false)
                .key("_id")
                .build();

        IdSerializationEntry entry = new IdSerializationEntry(config);
        EObject eObject = mock(EObject.class);

        assertFalse(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("shouldSerialize returns true when ID value exists")
    void shouldSerializeReturnsTrueWhenIdValueExists() {
        EObject eObject = mock(EObject.class);
        when(eObject.eClass()).thenReturn(testEClass);
        when(eObject.eGet(idAttribute)).thenReturn("test-id-123");

        EffectiveIdConfig config = createDefaultConfig();
        IdSerializationEntry entry = new IdSerializationEntry(config);

        assertTrue(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("shouldSerialize returns false when ID value is null")
    void shouldSerializeReturnsFalseWhenIdValueNull() {
        EObject eObject = mock(EObject.class);
        when(eObject.eClass()).thenReturn(testEClass);
        when(eObject.eGet(idAttribute)).thenReturn(null);
        when(eObject.eResource()).thenReturn(null);

        EffectiveIdConfig config = createDefaultConfig();
        IdSerializationEntry entry = new IdSerializationEntry(config);

        assertFalse(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("shouldSerialize returns true when using resource URI fragment")
    void shouldSerializeReturnsTrueWhenUsingResourceFragment() {
        // EClass without ID attribute
        EClass noIdClass = EcoreFactory.eINSTANCE.createEClass();
        noIdClass.setName("NoIdClass");

        EObject eObject = mock(EObject.class);
        Resource resource = mock(Resource.class);
        when(eObject.eClass()).thenReturn(noIdClass);
        when(eObject.eResource()).thenReturn(resource);
        when(resource.getURIFragment(eObject)).thenReturn("//@items.0");

        EffectiveIdConfig config = createDefaultConfig();
        IdSerializationEntry entry = new IdSerializationEntry(config);

        assertTrue(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("serialize writes ID from attribute")
    void serializeWritesIdFromAttribute() {
        EObject eObject = mock(EObject.class);
        when(eObject.eClass()).thenReturn(testEClass);
        when(eObject.eGet(idAttribute)).thenReturn("my-id-value");

        EffectiveIdConfig config = createDefaultConfig();
        IdSerializationEntry entry = new IdSerializationEntry(config);
        entry.serialize(createState(eObject), generator, null);

        verify(generator).writeStringProperty("_id", "my-id-value");
    }

    @Test
    @DisplayName("serialize writes ID from resource URI fragment")
    void serializeWritesIdFromResourceFragment() {
        EClass noIdClass = EcoreFactory.eINSTANCE.createEClass();
        noIdClass.setName("NoIdClass");

        EObject eObject = mock(EObject.class);
        Resource resource = mock(Resource.class);
        when(eObject.eClass()).thenReturn(noIdClass);
        when(eObject.eResource()).thenReturn(resource);
        when(resource.getURIFragment(eObject)).thenReturn("//@items.0");

        EffectiveIdConfig config = createDefaultConfig();
        IdSerializationEntry entry = new IdSerializationEntry(config);
        entry.serialize(createState(eObject), generator, null);

        verify(generator).writeStringProperty("_id", "//@items.0");
    }
}
