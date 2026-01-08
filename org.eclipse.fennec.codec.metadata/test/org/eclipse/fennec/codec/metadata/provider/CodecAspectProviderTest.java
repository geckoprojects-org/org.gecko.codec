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
package org.eclipse.fennec.codec.metadata.provider;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.IdStrategy;
import org.eclipse.fennec.model.metadata.SuperTypeSelection;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link CodecAspectProvider}.
 * <p>
 * Uses test ecore file with various codec annotations instead of
 * programmatic EPackage creation for better maintainability.
 * </p>
 */
class CodecAspectProviderTest {

    private static final String TEST_ECORE = "test-codec-annotations.ecore";

    private CodecAspectProvider provider;
    private EcoreHelper helper;
    private EPackage testPackage;

    @BeforeEach
    void setUp() throws IOException {
        provider = new CodecAspectProvider();
        helper = new EcoreHelper(CodecAspectProviderTest.class);
        testPackage = helper.loadEcore(TEST_ECORE);
    }

    @AfterEach
    void tearDown() {
        helper.releaseAll();
    }

    // ========================================================================
    // Basic Tests
    // ========================================================================

    @Test
    void testAspectTypeId() {
        assertEquals("codec", provider.getAspectTypeId());
    }

    @Test
    void testBuildClassAspectNoAnnotations() {
        EClass simpleClass = helper.getEClass(testPackage, "SimpleClass");

        ClassAspect aspect = provider.buildClassAspect(simpleClass);

        assertNotNull(aspect);
        assertTrue(aspect instanceof ClassCodecAspect);
        assertEquals("codec", aspect.getTypeId());

        ClassCodecAspect codecAspect = (ClassCodecAspect) aspect;
        assertNull(codecAspect.getIdConfig());
        assertNull(codecAspect.getTypeConfig());
        assertNull(codecAspect.getSuperTypeConfig());
        // Default value for inheritFromParent is true (defined in codec.ecore)
        assertTrue(codecAspect.isInheritFromParent());
    }

    @Test
    void testBuildAttributeAspectNoAnnotations() {
        EClass simpleClass = helper.getEClass(testPackage, "SimpleClass");
        EAttribute nameAttr = (EAttribute) helper.getFeature(simpleClass, "name");

        FeatureAspect aspect = provider.buildAttributeAspect(nameAttr);

        assertNotNull(aspect);
        assertTrue(aspect instanceof FeatureCodecAspect);
        assertEquals("codec", aspect.getTypeId());

        FeatureCodecAspect codecAspect = (FeatureCodecAspect) aspect;
        assertTrue(codecAspect.isSerialize());
        // effectiveKey is null when no explicit "key" annotation - ConfigurationMerger falls back
        // to ExtendedMetaData name or feature name
        assertNull(codecAspect.getEffectiveKey());
        assertNull(codecAspect.getValueWriterName());
        assertNull(codecAspect.getValueReaderName());
    }

    @Test
    void testBuildReferenceAspectNoAnnotations() {
        EClass personClass = helper.getEClass(testPackage, "PersonWithTypedReference");
        EReference addressRef = (EReference) helper.getFeature(personClass, "address");

        // Remove annotation for this test
        addressRef.getEAnnotations().clear();

        FeatureAspect aspect = provider.buildReferenceAspect(addressRef);

        assertNotNull(aspect);
        assertTrue(aspect instanceof ReferenceCodecAspect);
        assertEquals("codec", aspect.getTypeId());

        ReferenceCodecAspect codecAspect = (ReferenceCodecAspect) aspect;
        assertTrue(codecAspect.isSerialize());
        // effectiveKey is null when no explicit "key" annotation - ConfigurationMerger falls back
        // to ExtendedMetaData name or feature name
        assertNull(codecAspect.getEffectiveKey());
    }

    @Test
    void testBuildFeatureAspectDelegation() {
        EClass simpleClass = helper.getEClass(testPackage, "SimpleClass");
        EAttribute nameAttr = (EAttribute) helper.getFeature(simpleClass, "name");

        EClass personClass = helper.getEClass(testPackage, "PersonWithTypedReference");
        EReference addressRef = (EReference) helper.getFeature(personClass, "address");

        // buildFeatureAspect should delegate to appropriate method
        FeatureAspect attrAspect = provider.buildFeatureAspect(nameAttr);
        assertTrue(attrAspect instanceof FeatureCodecAspect);
        assertFalse(attrAspect instanceof ReferenceCodecAspect);

        FeatureAspect refAspect = provider.buildFeatureAspect(addressRef);
        assertTrue(refAspect instanceof ReferenceCodecAspect);
    }

    // ========================================================================
    // ID Configuration Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithIdFieldStrategy() {
        EClass personClass = helper.getEClass(testPackage, "PersonWithIdField");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(personClass);

        assertNotNull(aspect.getIdConfig());
        IdSerializationConfig idConfig = aspect.getIdConfig();
        assertEquals(IdStrategy.ID_FIELD, idConfig.getStrategy());
        assertEquals("_id", idConfig.getIdKey());
    }

    @Test
    void testBuildClassAspectWithCombinedIdStrategy() {
        EClass personClass = helper.getEClass(testPackage, "PersonWithCombinedId");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(personClass);

        assertNotNull(aspect.getIdConfig());
        IdSerializationConfig idConfig = aspect.getIdConfig();
        assertEquals(IdStrategy.COMBINED, idConfig.getStrategy());
        assertEquals("-", idConfig.getSeparator());
        assertEquals(2, idConfig.getIdFeatures().size());
        assertTrue(idConfig.getIdFeatures().contains("firstName"));
        assertTrue(idConfig.getIdFeatures().contains("lastName"));
    }

    @Test
    void testBuildClassAspectWithIdNoneStrategy() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithNoId");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getIdConfig());
        assertEquals(IdStrategy.NONE, aspect.getIdConfig().getStrategy());
    }

    @Test
    void testBuildClassAspectWithCustomIdReaderWriter() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithCustomIdHandlers");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getIdConfig());
        assertEquals("CustomIdReader", aspect.getIdConfig().getIdValueReaderName());
        assertEquals("CustomIdWriter", aspect.getIdConfig().getIdValueWriterName());
    }

    // ========================================================================
    // Type Configuration Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithTypeUriStrategy() {
        EClass entityClass = helper.getEClass(testPackage, "TypedEntityUri");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getTypeConfig());
        TypeSerializationConfig typeConfig = aspect.getTypeConfig();
        assertEquals(TypeStrategy.URI, typeConfig.getStrategy());
        assertEquals("_type", typeConfig.getTypeKey());
        assertTrue(typeConfig.isInclude());
    }

    @Test
    void testBuildClassAspectWithNameTypeStrategy() {
        EClass entityClass = helper.getEClass(testPackage, "TypedEntityName");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getTypeConfig());
        assertEquals(TypeStrategy.NAME, aspect.getTypeConfig().getStrategy());
    }

    @Test
    void testBuildClassAspectWithMappedTypeStrategy() {
        EClass entityClass = helper.getEClass(testPackage, "TypedEntityMapped");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getTypeConfig());
        TypeSerializationConfig typeConfig = aspect.getTypeConfig();
        assertEquals(TypeStrategy.MAPPED, typeConfig.getStrategy());
        assertEquals("deviceInfo.profileName", typeConfig.getDiscriminatorPath());
    }

    @Test
    void testBuildClassAspectWithTypeIncludeFalse() {
        EClass entityClass = helper.getEClass(testPackage, "TypedEntityNoInclude");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getTypeConfig());
        assertFalse(aspect.getTypeConfig().isInclude());
    }

    // ========================================================================
    // SuperType Configuration Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithSuperTypeConfig() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSuperTypes");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getSuperTypeConfig());
        SuperTypeSerializationConfig superConfig = aspect.getSuperTypeConfig();
        assertTrue(superConfig.isEnabled());
        assertEquals("_superTypes", superConfig.getSuperTypeKey());
        assertEquals(SuperTypeSelection.ALL, superConfig.getSelection());
    }

    @Test
    void testBuildClassAspectWithSingleSuperTypeStrategy() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSingleSuperType");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getSuperTypeConfig());
        assertEquals(SuperTypeSelection.SINGLE, aspect.getSuperTypeConfig().getSelection());
    }

    @Test
    void testBuildClassAspectWithSuperTypeAsArrayFalse() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSuperTypesNotAsArray");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getSuperTypeConfig());
        SuperTypeSerializationConfig superConfig = aspect.getSuperTypeConfig();
        assertTrue(superConfig.isEnabled());
        assertFalse(superConfig.isAsArray());
    }

    @Test
    void testBuildClassAspectWithSuperTypeSeparator() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSuperTypesSeparator");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getSuperTypeConfig());
        SuperTypeSerializationConfig superConfig = aspect.getSuperTypeConfig();
        assertTrue(superConfig.isEnabled());
        assertFalse(superConfig.isAsArray());
        assertEquals("|", superConfig.getSeparator());
    }

    @Test
    void testBuildClassAspectWithSuperTypeDefaultAsArrayTrue() {
        // EntityWithSuperTypes has superTypeSerialize=true but no asArray specified
        // Default should be true (array format)
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSuperTypes");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getSuperTypeConfig());
        SuperTypeSerializationConfig superConfig = aspect.getSuperTypeConfig();
        assertTrue(superConfig.isAsArray()); // Default is true
    }

    @Test
    void testBuildClassAspectWithSuperTypeDefaultSeparator() {
        // EntityWithSuperTypesNotAsArray has asArray=false but no separator specified
        // Default separator should be ","
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSuperTypesNotAsArray");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getSuperTypeConfig());
        SuperTypeSerializationConfig superConfig = aspect.getSuperTypeConfig();
        assertEquals(",", superConfig.getSeparator()); // Default is ","
    }

    // ========================================================================
    // Inherit Annotation Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithInheritAnnotation() {
        EClass entityClass = helper.getEClass(testPackage, "InheritingEntity");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertTrue(aspect.isInheritFromParent());
    }

    // ========================================================================
    // Discriminator Value Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithDiscriminatorValue() {
        EClass deviceClass = helper.getEClass(testPackage, "DraginoDevice");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(deviceClass);

        assertEquals("Dragino_LSE01", aspect.getDiscriminatorValue());
    }

    // ========================================================================
    // Transient Feature Tests
    // ========================================================================

    @Test
    void testBuildAttributeAspectWithTransient() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithTransientField");
        EAttribute secretAttr = (EAttribute) helper.getFeature(entityClass, "secretData");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(secretAttr);

        assertFalse(aspect.isSerialize());
    }

    @Test
    void testBuildAttributeAspectWithoutTransient() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithTransientField");
        EAttribute publicAttr = (EAttribute) helper.getFeature(entityClass, "publicData");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(publicAttr);

        assertTrue(aspect.isSerialize());
    }

    @Test
    void testBuildReferenceAspectWithTransient() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithTransientReference");
        EReference cachedRef = (EReference) helper.getFeature(entityClass, "cachedAddress");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(cachedRef);

        assertFalse(aspect.isSerialize());
    }

    // ========================================================================
    // Value Reader/Writer Tests
    // ========================================================================

    @Test
    void testBuildAttributeAspectWithValueWriter() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithCustomValueHandlers");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "onlyWriter");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertEquals("CustomWriter", aspect.getValueWriterName());
        assertNull(aspect.getValueReaderName());
    }

    @Test
    void testBuildAttributeAspectWithValueReader() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithCustomValueHandlers");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "onlyReader");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertEquals("CustomReader", aspect.getValueReaderName());
        assertNull(aspect.getValueWriterName());
    }

    @Test
    void testBuildAttributeAspectWithBothReaderAndWriter() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithCustomValueHandlers");
        EAttribute birthDateAttr = (EAttribute) helper.getFeature(entityClass, "birthDate");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(birthDateAttr);

        assertEquals("ISO8601DateWriter", aspect.getValueWriterName());
        assertEquals("FlexibleDateReader", aspect.getValueReaderName());
    }

    // ========================================================================
    // Reference Type Config Tests
    // ========================================================================

    @Test
    void testBuildReferenceAspectWithTypeConfig() {
        EClass personClass = helper.getEClass(testPackage, "PersonWithTypedReference");
        EReference addressRef = (EReference) helper.getFeature(personClass, "address");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

        assertNotNull(aspect.getTypeConfig());
        assertEquals(TypeStrategy.NAME, aspect.getTypeConfig().getStrategy());
        assertEquals("_refType", aspect.getTypeConfig().getTypeKey());
    }

    // ========================================================================
    // Combined Annotations Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithAllConfigs() {
        EClass entityClass = helper.getEClass(testPackage, "FullyConfiguredEntity");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getIdConfig());
        assertNotNull(aspect.getTypeConfig());
        assertNotNull(aspect.getSuperTypeConfig());
        assertTrue(aspect.isInheritFromParent());

        assertEquals(IdStrategy.ID_FIELD, aspect.getIdConfig().getStrategy());
        assertEquals(TypeStrategy.URI, aspect.getTypeConfig().getStrategy());
        assertTrue(aspect.getSuperTypeConfig().isEnabled());
    }
}
