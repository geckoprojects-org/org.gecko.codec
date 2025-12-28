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
import org.eclipse.fennec.codec.v2.config.effective.EffectiveSuperTypeConfig;
import org.eclipse.fennec.model.metadata.SuperTypeSelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tools.jackson.core.JsonGenerator;

/**
 * Tests for {@link SuperTypeSerializationEntry}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#63-supertype-serialization">Spec 6.3: SuperType Serialization</a>
 */
@DisplayName("SuperTypeSerializationEntry")
class SuperTypeSerializationEntryTest {

    private EClass testEClass;
    private EClass superClass;
    private EPackage testPackage;
    private JsonGenerator generator;

    private SerializationState createState(EObject eObject) {
        return new SerializationState(eObject);
    }

    @BeforeEach
    void setUp() {
        generator = mock(JsonGenerator.class);

        // Create a test EPackage with supertype hierarchy
        testPackage = EcoreFactory.eINSTANCE.createEPackage();
        testPackage.setName("testpackage");
        testPackage.setNsURI("http://example.org/test");
        testPackage.setNsPrefix("test");

        superClass = EcoreFactory.eINSTANCE.createEClass();
        superClass.setName("NamedElement");
        testPackage.getEClassifiers().add(superClass);

        testEClass = EcoreFactory.eINSTANCE.createEClass();
        testEClass.setName("Person");
        testEClass.getESuperTypes().add(superClass);
        testPackage.getEClassifiers().add(testEClass);
    }

    private EffectiveSuperTypeConfig createDefaultConfig() {
        return EffectiveSuperTypeConfig.builder()
                .enabled(true)
                .superTypeKey("_supertype")
                .selection(SuperTypeSelection.ALL)
                .build();
    }

    @Test
    @DisplayName("getKey returns default _supertype key")
    void getKeyReturnsDefaultSupertypeKey() {
        EffectiveSuperTypeConfig config = createDefaultConfig();
        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, testEClass);
        assertEquals("_supertype", entry.getKey());
    }

    @Test
    @DisplayName("getKey returns custom key from config")
    void getKeyReturnsCustomKeyFromConfig() {
        EffectiveSuperTypeConfig config = EffectiveSuperTypeConfig.builder()
                .enabled(true)
                .superTypeKey("extends")
                .selection(SuperTypeSelection.ALL)
                .build();

        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, testEClass);
        assertEquals("extends", entry.getKey());
    }

    @Test
    @DisplayName("shouldSerialize returns false when not enabled")
    void shouldSerializeReturnsFalseWhenNotEnabled() {
        EffectiveSuperTypeConfig config = EffectiveSuperTypeConfig.builder()
                .enabled(false)
                .superTypeKey("_supertype")
                .selection(SuperTypeSelection.ALL)
                .build();

        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        assertFalse(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("shouldSerialize returns true when has supertypes")
    void shouldSerializeReturnsTrueWhenHasSupertypes() {
        EffectiveSuperTypeConfig config = createDefaultConfig();
        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        assertTrue(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("shouldSerialize returns false when no supertypes")
    void shouldSerializeReturnsFalseWhenNoSupertypes() {
        EClass noSuperClass = EcoreFactory.eINSTANCE.createEClass();
        noSuperClass.setName("Standalone");
        testPackage.getEClassifiers().add(noSuperClass);

        EffectiveSuperTypeConfig config = createDefaultConfig();
        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, noSuperClass);
        EObject eObject = mock(EObject.class);

        assertFalse(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("serialize writes supertypes as array")
    void serializeWritesSupertypesAsArray() {
        EffectiveSuperTypeConfig config = createDefaultConfig();
        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        entry.serialize(createState(eObject), generator, null);

        verify(generator).writeArrayPropertyStart("_supertype");
        verify(generator).writeString("http://example.org/test#//NamedElement");
        verify(generator).writeEndArray();
    }

    @Test
    @DisplayName("serialize writes single supertype when SINGLE selection")
    void serializeWritesSingleSupertypeWhenSingleSelection() {
        EffectiveSuperTypeConfig config = EffectiveSuperTypeConfig.builder()
                .enabled(true)
                .superTypeKey("_supertype")
                .selection(SuperTypeSelection.SINGLE)
                .build();

        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, testEClass);
        EObject eObject = mock(EObject.class);

        entry.serialize(createState(eObject), generator, null);

        verify(generator).writeStringProperty("_supertype", "http://example.org/test#//NamedElement");
    }

    @Test
    @DisplayName("serialize excludes EMF base types by default")
    void serializeExcludesEmfBaseTypesByDefault() {
        // Create class that extends EObject (EMF base type)
        EClass emfSuperClass = EcoreFactory.eINSTANCE.createEClass();
        emfSuperClass.setName("EModelElement");

        EPackage emfPackage = EcoreFactory.eINSTANCE.createEPackage();
        emfPackage.setNsURI("http://www.eclipse.org/emf/2002/Ecore");
        emfPackage.getEClassifiers().add(emfSuperClass);

        EClass classWithEmfSuper = EcoreFactory.eINSTANCE.createEClass();
        classWithEmfSuper.setName("MyClass");
        classWithEmfSuper.getESuperTypes().add(emfSuperClass);
        testPackage.getEClassifiers().add(classWithEmfSuper);

        EffectiveSuperTypeConfig config = createDefaultConfig();
        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, classWithEmfSuper);
        EObject eObject = mock(EObject.class);

        // Should not serialize because only EMF base types exist
        assertFalse(entry.shouldSerialize(createState(eObject)));
    }

    @Test
    @DisplayName("serialize includes EMF base types when ALL_EMF selection")
    void serializeIncludesEmfBaseTypesWhenAllEmfSelection() {
        EffectiveSuperTypeConfig config = EffectiveSuperTypeConfig.builder()
                .enabled(true)
                .superTypeKey("_supertype")
                .selection(SuperTypeSelection.ALL_EMF)
                .build();

        // Create class that extends EMF type
        EClass emfSuperClass = EcoreFactory.eINSTANCE.createEClass();
        emfSuperClass.setName("EModelElement");

        EPackage emfPackage = EcoreFactory.eINSTANCE.createEPackage();
        emfPackage.setNsURI("http://www.eclipse.org/emf/2002/Ecore");
        emfPackage.getEClassifiers().add(emfSuperClass);

        EClass classWithEmfSuper = EcoreFactory.eINSTANCE.createEClass();
        classWithEmfSuper.setName("MyClass");
        classWithEmfSuper.getESuperTypes().add(emfSuperClass);
        testPackage.getEClassifiers().add(classWithEmfSuper);

        SuperTypeSerializationEntry entry = new SuperTypeSerializationEntry(config, classWithEmfSuper);
        EObject eObject = mock(EObject.class);

        assertTrue(entry.shouldSerialize(createState(eObject)));
    }
}
