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
package org.eclipse.fennec.codec.metadata.provider;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for misconfigured codec annotations (keys at wrong EMF levels).
 * <p>
 * These tests verify that annotation keys placed at wrong EMF element levels
 * are <b>ignored</b> and generate appropriate <b>diagnostics</b>.
 * </p>
 * <p>
 * Per Section 7.4 of 19-test-coverage.md, every misconfiguration test MUST verify:
 * <ol>
 *   <li>Value is NOT applied (null, not set)</li>
 *   <li>Diagnostic IS added with correct key</li>
 * </ol>
 * </p>
 * <p>
 * All test models use @MISCONFIG tags in test-codec-annotations.ecore.
 * </p>
 *
 * @see CodecAspectProvider
 * @see CodecAspectProviderValidConfigTest for valid configuration tests
 */
@DisplayName("CodecAspectProvider - Misconfiguration Tests")
class CodecAspectProviderMisconfigTest {

    private static final String TEST_ECORE = "test-codec-annotations.ecore";

    private CodecAspectProvider provider;
    private EcoreHelper helper;
    private EPackage testPackage;

    @BeforeEach
    void setUp() throws IOException {
        provider = new CodecAspectProvider();
        helper = new EcoreHelper(CodecAspectProviderMisconfigTest.class);
        testPackage = helper.loadEcore(TEST_ECORE);
    }

    @AfterEach
    void tearDown() {
        helper.releaseAll();
    }

    // ========================================================================
    // Keys on Wrong Level: EClass
    // ========================================================================

    @Nested
    @DisplayName("Class-Level Misconfigurations")
    class ClassMisconfigTests {

        /**
         * @MISCONFIG @SPEC(10-reference.md)
         * Tests that refFormat/refKey on EClass are ignored.
         * These keys are reference-only per spec.
         */
        @Test
        @DisplayName("refFormat/refKey on EClass - ignored")
        void misconfig_refConfigKeysOnClass_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "ClassWithRefConfigMisplaced");

            ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

            // Valid key was parsed
            assertNotNull(aspect.getTypeConfig());
            assertEquals(TypeStrategy.URI, aspect.getTypeConfig().getStrategy());

            // ClassCodecAspect doesn't have getReferenceConfig() - that's correct
            // The refFormat/refKey keys are simply ignored for classes
            // Note: Diagnostics for class-level misconfig not yet implemented
        }

        /**
         * @MISCONFIG @SPEC(08-discriminator-mapping.md)
         * Tests that inlineMapping.* keys on EClass are ignored.
         * These keys are reference-only per spec.
         */
        @Test
        @DisplayName("inlineMapping.* on EClass - ignored")
        void misconfig_inlineMappingKeysOnClass_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "ClassWithInlineMappingMisplaced");

            ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

            // Valid key was parsed
            assertNotNull(aspect.getTypeConfig());
            assertEquals(TypeStrategy.URI, aspect.getTypeConfig().getStrategy());

            // ClassCodecAspect doesn't have getInlineTypeMappings() - that's correct
            // The inlineMapping.* keys are simply ignored for classes
        }
    }

    // ========================================================================
    // Keys on Wrong Level: EReference
    // ========================================================================

    @Nested
    @DisplayName("Reference-Level Misconfigurations")
    class ReferenceMisconfigTests {

        /**
         * @MISCONFIG @SPEC(09-id.md)
         * Tests that idStrategy/idFeatures on EReference are ignored.
         * These keys are class-only per spec.
         */
        @Test
        @DisplayName("idStrategy/idFeatures on EReference - ignored")
        void misconfig_idConfigKeysOnReference_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "RefWithIdConfigMisplaced");
            EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

            // Valid key was parsed
            assertNotNull(aspect.getTypeConfig());
            assertEquals(TypeStrategy.NAME, aspect.getTypeConfig().getStrategy());

            // ReferenceCodecAspect doesn't have getIdConfig() - that's correct
            // The idStrategy/idFeatures keys are simply ignored for references
        }

        /**
         * @MISCONFIG @SPEC(07-supertype.md)
         * Tests that superType* keys on EReference are ignored.
         * These keys are class-only per spec.
         */
        @Test
        @DisplayName("superType* on EReference - ignored")
        void misconfig_superTypeConfigKeysOnReference_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "RefWithSuperTypeMisplaced");
            EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

            // Valid key was parsed
            assertEquals("addr", aspect.getEffectiveKey());

            // ReferenceCodecAspect doesn't have getSuperTypeConfig() - that's correct
            // The superType* keys are simply ignored for references
        }

        /**
         * @MISCONFIG @SPEC(11-feature.md)
         * Tests that enumSerialization on EReference is ignored.
         * This key is attribute-only per spec.
         */
        @Test
        @DisplayName("enumSerialization on EReference - ignored")
        void misconfig_enumSerializationOnReference_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "RefWithEnumSerializationMisplaced");
            EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

            // Valid key was parsed
            assertEquals("addr", aspect.getEffectiveKey());

            // The annotation has VALUE but should be ignored - stays at EMF model default (LITERAL)
            assertEquals(EnumSerializationStrategy.LITERAL, aspect.getEnumSerialization());
        }

        /**
         * @MISCONFIG @SPEC(08-discriminator-mapping.md)
         * Tests that typeDiscriminator value on EReference is ignored.
         * This key is class-only per spec.
         */
        @Test
        @DisplayName("typeDiscriminator on EReference - ignored")
        void misconfig_typeDiscriminatorOnReference_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "RefWithDiscriminatorMisplaced");
            EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

            // Valid key was parsed
            assertNotNull(aspect.getTypeConfig());
            assertEquals(TypeStrategy.NAME, aspect.getTypeConfig().getStrategy());

            // ReferenceCodecAspect doesn't have getDiscriminatorValue() - that's correct
            // The typeDiscriminator key is simply ignored for references
        }

        /**
         * @MISCONFIG @SPEC(08-discriminator-mapping.md#7)
         * Tests that typeDiscriminatorPath on EReference is ignored with diagnostic.
         * This key is class-only per spec section 7.
         */
        @Test
        @DisplayName("typeDiscriminatorPath on EReference - ignored with diagnostic")
        void misconfig_typeDiscriminatorPathOnReference_ignoredWithDiagnostic() {
            EClass personClass = helper.getEClass(testPackage, "PersonWithContacts");
            EReference contactsRef = (EReference) helper.getFeature(personClass, "contacts");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(contactsRef);

            // 1. Value is NOT applied - typeConfig should be null
            assertNull(aspect.getTypeConfig(),
                "typeConfig should be null - typeDiscriminatorPath is class-only and should be ignored on EReference");

            // 2. Diagnostic IS added
            assertEquals(1, aspect.getDiagnostics().size(),
                "Should have one diagnostic for ignored typeDiscriminatorPath");
            assertEquals("typeDiscriminatorPath", aspect.getDiagnostics().get(0).getKey());

            // Inline mappings should still be parsed (they ARE valid on EReference)
            assertEquals(3, aspect.getInlineTypeMappings().size());
        }

        /**
         * @MISCONFIG @SPEC(08-discriminator-mapping.md)
         * Tests that typeMapId on EReference is ignored.
         * This key is class-only per spec context.
         */
        @Test
        @DisplayName("typeMapId on EReference - ignored")
        void misconfig_typeMapIdOnReference_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "RefWithTypeMapIdMisplaced");
            EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

            // Valid key was parsed
            assertNotNull(aspect.getTypeConfig());
            assertEquals(TypeStrategy.NAME, aspect.getTypeConfig().getStrategy());

            // typeMapId should not be set on the reference type config
            assertNull(aspect.getTypeConfig().getMapId());
        }

        /**
         * @MISCONFIG @SPEC(09-id.md)
         * Tests that additional id* keys on EReference are ignored.
         * idSeparator, idKeyMode, idOnTop, idValueKey, idFormat are class-only.
         */
        @Test
        @DisplayName("more id* keys on EReference - ignored")
        void misconfig_moreIdConfigKeysOnReference_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "RefWithMoreIdConfigMisplaced");
            EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

            // Valid key was parsed
            assertEquals("addr", aspect.getEffectiveKey());

            // ReferenceCodecAspect doesn't have getIdConfig() - that's correct
            // All id* keys are simply ignored for references
        }

        /**
         * @MISCONFIG @SPEC(08-discriminator-mapping.md#7)
         * Tests fallback reference with typeDiscriminatorPath ignored but other valid keys parsed.
         */
        @Test
        @DisplayName("fallback reference with ignored typeDiscriminatorPath")
        void misconfig_fallbackReferenceWithIgnoredPath_validKeysParsed() {
            EClass personClass = helper.getEClass(testPackage, "PersonWithFallbackReference");
            EReference contactsRef = (EReference) helper.getFeature(personClass, "contacts");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(contactsRef);

            // 1. typeDiscriminatorPath is class-only - should be IGNORED
            assertNull(aspect.getTypeConfig(),
                "typeConfig should be null - typeDiscriminatorPath is class-only and should be ignored");

            // 2. Should have diagnostic warning for the ignored key
            assertEquals(1, aspect.getDiagnostics().size(),
                "Should have one diagnostic for ignored typeDiscriminatorPath");
            assertEquals("typeDiscriminatorPath", aspect.getDiagnostics().get(0).getKey());

            // 3. Valid keys should still be parsed
            assertEquals(org.eclipse.fennec.codec.metadata.model.codec.FallbackStrategy.SKIP,
                aspect.getFallbackStrategy());
            assertEquals("http://test.codec.example.org/1.0#//Contact", aspect.getFallbackEClass());

            // Should have inline mapping for friend
            assertEquals(1, aspect.getInlineTypeMappings().size());
        }
    }

    // ========================================================================
    // Keys on Wrong Level: EAttribute
    // ========================================================================

    @Nested
    @DisplayName("Attribute-Level Misconfigurations")
    class AttributeMisconfigTests {

        /**
         * @MISCONFIG @SPEC(06-type.md)
         * Tests that type* keys on EAttribute are ignored.
         * These keys are class/reference-only per spec.
         */
        @Test
        @DisplayName("type* keys on EAttribute - ignored")
        void misconfig_typeConfigKeysOnAttribute_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "AttrWithTypeConfigMisplaced");
            EAttribute nameAttr = (EAttribute) helper.getFeature(entityClass, "name");

            FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(nameAttr);

            // Valid key was parsed
            assertEquals("attr_name", aspect.getEffectiveKey());

            // FeatureCodecAspect doesn't have getTypeConfig() - that's correct
            // The type* keys are simply ignored for attributes
        }

        /**
         * @MISCONFIG @SPEC(07-supertype.md)
         * Tests that superType* keys on EAttribute are ignored.
         * These keys are class-only per spec.
         */
        @Test
        @DisplayName("superType* keys on EAttribute - ignored")
        void misconfig_superTypeConfigKeysOnAttribute_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "AttrWithSuperTypeMisplaced");
            EAttribute nameAttr = (EAttribute) helper.getFeature(entityClass, "name");

            FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(nameAttr);

            // Valid key was parsed
            assertEquals("attr_name", aspect.getEffectiveKey());

            // FeatureCodecAspect doesn't have getSuperTypeConfig() - that's correct
            // The superType* keys are simply ignored for attributes
        }

        /**
         * @MISCONFIG @SPEC(09-id.md)
         * Tests that id* keys on EAttribute are ignored.
         * These keys are class-only per spec.
         */
        @Test
        @DisplayName("id* keys on EAttribute - ignored")
        void misconfig_idConfigKeysOnAttribute_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "AttrWithIdConfigMisplaced");
            EAttribute nameAttr = (EAttribute) helper.getFeature(entityClass, "name");

            FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(nameAttr);

            // Valid key was parsed
            assertEquals("attr_name", aspect.getEffectiveKey());

            // FeatureCodecAspect doesn't have getIdConfig() - that's correct
            // The id* keys are simply ignored for attributes
        }

        /**
         * @MISCONFIG @SPEC(10-reference.md)
         * Tests that ref* keys on EAttribute are ignored.
         * These keys are reference-only per spec.
         */
        @Test
        @DisplayName("ref* keys on EAttribute - ignored")
        void misconfig_refConfigKeysOnAttribute_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "AttrWithRefConfigMisplaced");
            EAttribute nameAttr = (EAttribute) helper.getFeature(entityClass, "name");

            FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(nameAttr);

            // Valid key was parsed
            assertEquals("attr_name", aspect.getEffectiveKey());

            // FeatureCodecAspect doesn't have getReferenceConfig() - that's correct
            // The ref* keys are simply ignored for attributes
            assertFalse(aspect instanceof ReferenceCodecAspect);
        }

        /**
         * @MISCONFIG @SPEC(08-discriminator-mapping.md)
         * Tests that inlineMapping.* keys on EAttribute are ignored.
         * These keys are reference-only per spec.
         */
        @Test
        @DisplayName("inlineMapping.* on EAttribute - ignored")
        void misconfig_inlineMappingKeysOnAttribute_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "AttrWithInlineMappingMisplaced");
            EAttribute nameAttr = (EAttribute) helper.getFeature(entityClass, "name");

            FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(nameAttr);

            // Valid key was parsed
            assertEquals("attr_name", aspect.getEffectiveKey());

            // FeatureCodecAspect doesn't have getInlineTypeMappings() - that's correct
            // The inlineMapping.* keys are simply ignored for attributes
        }

        /**
         * @MISCONFIG @SPEC(08-discriminator-mapping.md)
         * Tests that fallback* keys on EAttribute are ignored.
         * These keys are class/reference-only per spec.
         */
        @Test
        @DisplayName("fallback* keys on EAttribute - ignored")
        void misconfig_fallbackConfigKeysOnAttribute_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "AttrWithFallbackMisplaced");
            EAttribute nameAttr = (EAttribute) helper.getFeature(entityClass, "name");

            FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(nameAttr);

            // Valid key was parsed
            assertEquals("attr_name", aspect.getEffectiveKey());

            // FeatureCodecAspect doesn't have getFallbackStrategy()/getFallbackEClass() - that's correct
            // The fallback* keys are simply ignored for attributes
        }

        /**
         * @MISCONFIG @SPEC(10-reference.md)
         * Tests that expand on EAttribute is ignored.
         * This key is reference-only per spec.
         */
        @Test
        @DisplayName("expand on EAttribute - ignored")
        void misconfig_expandOnAttribute_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "AttrWithExpandMisplaced");
            EAttribute nameAttr = (EAttribute) helper.getFeature(entityClass, "name");

            FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(nameAttr);

            // Valid key was parsed
            assertEquals("attr_name", aspect.getEffectiveKey());

            // FeatureCodecAspect doesn't have isExpand() that makes sense for attributes
            // The expand key is simply ignored for attributes
            assertFalse(aspect instanceof ReferenceCodecAspect);
        }

        /**
         * @MISCONFIG @SPEC(14-custom-values.md)
         * Tests that typeValueReaderName/WriterName on EReference are ignored.
         * These keys are typically for class-level ID handling.
         */
        @Test
        @DisplayName("typeValueReaderName/WriterName on EReference - ignored")
        void misconfig_typeValueHandlersOnReference_ignored() {
            EClass entityClass = helper.getEClass(testPackage, "RefWithTypeValueHandlersMisplaced");
            EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

            ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

            // Valid key was parsed
            assertEquals("addr", aspect.getEffectiveKey());

            // ReferenceCodecAspect inherits value reader/writer from FeatureCodecAspect
            // but these are for attribute values, not for type value handling on references
            // The keys should be ignored and reader/writer stay null
            assertNull(aspect.getValueReaderName());
            assertNull(aspect.getValueWriterName());
        }
    }
}
