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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.codec.v2.module.CodecModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FeatureKeyResolver}.
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#6-eobject-serialization">Spec 6: EObject Serialization</a>
 */
@DisplayName("FeatureKeyResolver")
class FeatureKeyResolverTest {

    private CodecModule codecModule;
    private FeatureKeyResolver resolver;
    private EPackage testPackage;
    private EClass testEClass;
    private EAttribute testAttribute;

    @BeforeEach
    void setUp() {
        codecModule = CodecModule.withDefaults();
        resolver = new FeatureKeyResolver(codecModule);

        // Create a test EPackage
        testPackage = EcoreFactory.eINSTANCE.createEPackage();
        testPackage.setName("test");
        testPackage.setNsPrefix("test");
        testPackage.setNsURI("http://example.org/test");

        // Create a test EClass
        testEClass = EcoreFactory.eINSTANCE.createEClass();
        testEClass.setName("Person");
        testPackage.getEClassifiers().add(testEClass);

        // Create a test attribute
        testAttribute = EcoreFactory.eINSTANCE.createEAttribute();
        testAttribute.setName("firstName");
        testAttribute.setEType(EcorePackage.Literals.ESTRING);
        testEClass.getEStructuralFeatures().add(testAttribute);
    }

    @Nested
    @DisplayName("resolveFeatureKey")
    class ResolveFeatureKey {

        @Test
        @DisplayName("returns feature name when no aspect")
        void returnsFeatureNameWhenNoAspect() {
            String key = resolver.resolveFeatureKey(testAttribute, null);

            assertEquals("firstName", key);
        }

        @Test
        @DisplayName("returns effective key from aspect")
        void returnsEffectiveKeyFromAspect() {
            FeatureCodecAspect aspect = mock(FeatureCodecAspect.class);
            when(aspect.getEffectiveKey()).thenReturn("first_name");

            String key = resolver.resolveFeatureKey(testAttribute, aspect);

            assertEquals("first_name", key);
        }

        @Test
        @DisplayName("returns feature name when aspect key is empty")
        void returnsFeatureNameWhenAspectKeyEmpty() {
            FeatureCodecAspect aspect = mock(FeatureCodecAspect.class);
            when(aspect.getEffectiveKey()).thenReturn("");

            String key = resolver.resolveFeatureKey(testAttribute, aspect);

            assertEquals("firstName", key);
        }

        @Test
        @DisplayName("returns feature name when aspect key is null")
        void returnsFeatureNameWhenAspectKeyNull() {
            FeatureCodecAspect aspect = mock(FeatureCodecAspect.class);
            when(aspect.getEffectiveKey()).thenReturn(null);

            String key = resolver.resolveFeatureKey(testAttribute, aspect);

            assertEquals("firstName", key);
        }
    }

    @Nested
    @DisplayName("resolveIdKey")
    class ResolveIdKey {

        @Test
        @DisplayName("returns module default when no aspect")
        void returnsModuleDefaultWhenNoAspect() {
            String key = resolver.resolveIdKey(null);

            assertEquals("_id", key);
        }

        @Test
        @DisplayName("returns custom key from aspect")
        void returnsCustomKeyFromAspect() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            IdSerializationConfig config = mock(IdSerializationConfig.class);
            when(aspect.getIdConfig()).thenReturn(config);
            when(config.getIdKey()).thenReturn("id");

            String key = resolver.resolveIdKey(aspect);

            assertEquals("id", key);
        }

        @Test
        @DisplayName("returns module default when aspect config is null")
        void returnsModuleDefaultWhenConfigNull() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            when(aspect.getIdConfig()).thenReturn(null);

            String key = resolver.resolveIdKey(aspect);

            assertEquals("_id", key);
        }

        @Test
        @DisplayName("returns module default when aspect key is empty")
        void returnsModuleDefaultWhenKeyEmpty() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            IdSerializationConfig config = mock(IdSerializationConfig.class);
            when(aspect.getIdConfig()).thenReturn(config);
            when(config.getIdKey()).thenReturn("");

            String key = resolver.resolveIdKey(aspect);

            assertEquals("_id", key);
        }
    }

    @Nested
    @DisplayName("resolveTypeKey")
    class ResolveTypeKey {

        @Test
        @DisplayName("returns module default when no aspect")
        void returnsModuleDefaultWhenNoAspect() {
            String key = resolver.resolveTypeKey(null);

            assertEquals("_type", key);
        }

        @Test
        @DisplayName("returns custom key from aspect")
        void returnsCustomKeyFromAspect() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            TypeSerializationConfig config = mock(TypeSerializationConfig.class);
            when(aspect.getTypeConfig()).thenReturn(config);
            when(config.getTypeKey()).thenReturn("@type");

            String key = resolver.resolveTypeKey(aspect);

            assertEquals("@type", key);
        }

        @Test
        @DisplayName("returns module default when aspect config is null")
        void returnsModuleDefaultWhenConfigNull() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            when(aspect.getTypeConfig()).thenReturn(null);

            String key = resolver.resolveTypeKey(aspect);

            assertEquals("_type", key);
        }

        @Test
        @DisplayName("returns module default when aspect key is empty")
        void returnsModuleDefaultWhenKeyEmpty() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            TypeSerializationConfig config = mock(TypeSerializationConfig.class);
            when(aspect.getTypeConfig()).thenReturn(config);
            when(config.getTypeKey()).thenReturn("");

            String key = resolver.resolveTypeKey(aspect);

            assertEquals("_type", key);
        }
    }

    @Nested
    @DisplayName("resolveTypeValue")
    class ResolveTypeValue {

        @Test
        @DisplayName("returns EClass URI when no aspect")
        void returnsEClassUriWhenNoAspect() {
            String value = resolver.resolveTypeValue(testEClass, null);

            assertEquals("http://example.org/test#//Person", value);
        }

        @Test
        @DisplayName("returns discriminator value from aspect")
        void returnsDiscriminatorValueFromAspect() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            when(aspect.getDiscriminatorValue()).thenReturn("person");

            String value = resolver.resolveTypeValue(testEClass, aspect);

            assertEquals("person", value);
        }

        @Test
        @DisplayName("returns EClass URI when discriminator is empty")
        void returnsEClassUriWhenDiscriminatorEmpty() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            when(aspect.getDiscriminatorValue()).thenReturn("");

            String value = resolver.resolveTypeValue(testEClass, aspect);

            assertEquals("http://example.org/test#//Person", value);
        }

        @Test
        @DisplayName("returns EClass URI when discriminator is null")
        void returnsEClassUriWhenDiscriminatorNull() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            when(aspect.getDiscriminatorValue()).thenReturn(null);

            String value = resolver.resolveTypeValue(testEClass, aspect);

            assertEquals("http://example.org/test#//Person", value);
        }
    }

    @Nested
    @DisplayName("resolveSuperTypeKey")
    class ResolveSuperTypeKey {

        @Test
        @DisplayName("returns module default when no aspect")
        void returnsModuleDefaultWhenNoAspect() {
            String key = resolver.resolveSuperTypeKey(null);

            assertEquals("_supertype", key);
        }

        @Test
        @DisplayName("returns custom key from aspect")
        void returnsCustomKeyFromAspect() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            SuperTypeSerializationConfig config = mock(SuperTypeSerializationConfig.class);
            when(aspect.getSuperTypeConfig()).thenReturn(config);
            when(config.getSuperTypeKey()).thenReturn("extends");

            String key = resolver.resolveSuperTypeKey(aspect);

            assertEquals("extends", key);
        }

        @Test
        @DisplayName("returns module default when aspect config is null")
        void returnsModuleDefaultWhenConfigNull() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            when(aspect.getSuperTypeConfig()).thenReturn(null);

            String key = resolver.resolveSuperTypeKey(aspect);

            assertEquals("_supertype", key);
        }

        @Test
        @DisplayName("returns module default when aspect key is empty")
        void returnsModuleDefaultWhenKeyEmpty() {
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            SuperTypeSerializationConfig config = mock(SuperTypeSerializationConfig.class);
            when(aspect.getSuperTypeConfig()).thenReturn(config);
            when(config.getSuperTypeKey()).thenReturn("");

            String key = resolver.resolveSuperTypeKey(aspect);

            assertEquals("_supertype", key);
        }
    }

    @Nested
    @DisplayName("getEClassUri")
    class GetEClassUri {

        @Test
        @DisplayName("returns correct URI format")
        void returnsCorrectUriFormat() {
            String uri = resolver.getEClassUri(testEClass);

            assertEquals("http://example.org/test#//Person", uri);
        }

        @Test
        @DisplayName("handles Ecore types")
        void handlesEcoreTypes() {
            String uri = resolver.getEClassUri(EcorePackage.Literals.ECLASS);

            assertEquals("http://www.eclipse.org/emf/2002/Ecore#//EClass", uri);
        }
    }

    @Nested
    @DisplayName("getReferenceUri")
    class GetReferenceUri {

        @Test
        @DisplayName("returns resource URI with fragment when resource exists")
        void returnsResourceUriWithFragment() {
            EObject target = mock(EObject.class);
            Resource resource = mock(Resource.class);
            when(target.eResource()).thenReturn(resource);
            when(target.eClass()).thenReturn(testEClass);
            when(resource.getURI()).thenReturn(URI.createURI("http://example.org/data.json"));
            when(resource.getURIFragment(target)).thenReturn("//@persons.0");

            String uri = resolver.getReferenceUri(target);

            assertEquals("http://example.org/data.json#//@persons.0", uri);
        }

        @Test
        @DisplayName("returns class name fragment when no resource")
        void returnsClassNameFragmentWhenNoResource() {
            EObject target = mock(EObject.class);
            when(target.eResource()).thenReturn(null);
            when(target.eClass()).thenReturn(testEClass);

            String uri = resolver.getReferenceUri(target);

            assertEquals("#Person", uri);
        }
    }
}
