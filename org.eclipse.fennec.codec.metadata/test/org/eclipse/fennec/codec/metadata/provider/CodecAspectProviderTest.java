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
import org.eclipse.fennec.codec.metadata.model.codec.InlineTypeMapping;
import org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.IdStrategy;
import org.eclipse.fennec.model.metadata.SerializationFormat;
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
    void testBuildClassAspectWithDiscriminatorPath() {
        // Discriminator is now orthogonal to strategy - can use any strategy with discriminator
        EClass entityClass = helper.getEClass(testPackage, "TypedEntityWithDiscriminator");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getTypeConfig());
        TypeSerializationConfig typeConfig = aspect.getTypeConfig();
        assertEquals(TypeStrategy.NAME, typeConfig.getStrategy());
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

    // ========================================================================
    // NEW: ID Format/KeyMode/OnTop Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithStructuredIdConfig() {
        EClass personClass = helper.getEClass(testPackage, "PersonWithStructuredId");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(personClass);

        assertNotNull(aspect.getIdConfig());
        IdSerializationConfig idConfig = aspect.getIdConfig();

        assertEquals(IdStrategy.COMBINED, idConfig.getStrategy());
        assertEquals(SerializationFormat.STRUCTURED, idConfig.getFormat());
        assertEquals(IdKeyMode.BOTH, idConfig.getKeyMode());
        assertFalse(idConfig.isOnTop());
        assertTrue(idConfig.isSerializeSeparator());
        assertEquals("sep", idConfig.getSeparatorKey());
        assertEquals(2, idConfig.getIdFeatures().size());
    }

    // ========================================================================
    // NEW: Type Format/SchemaKey/NameKey Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithStructuredTypeConfig() {
        EClass entityClass = helper.getEClass(testPackage, "TypedEntityStructured");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getTypeConfig());
        TypeSerializationConfig typeConfig = aspect.getTypeConfig();

        assertEquals(TypeStrategy.SCHEMA_AND_TYPE, typeConfig.getStrategy());
        assertEquals(SerializationFormat.STRUCTURED, typeConfig.getFormat());
        assertEquals("$schema", typeConfig.getSchemaKey());
        assertEquals("typeName", typeConfig.getNameKey());
        assertEquals("typeInfo", typeConfig.getTypeKey());
    }

    @Test
    void testBuildClassAspectWithTypeMapId() {
        EClass entityClass = helper.getEClass(testPackage, "TypedEntityWithMapId");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getTypeConfig());
        TypeSerializationConfig typeConfig = aspect.getTypeConfig();

        assertEquals(TypeStrategy.NAME, typeConfig.getStrategy());
        assertEquals("my-custom-registry", typeConfig.getMapId());
    }

    // ========================================================================
    // NEW: SuperType Format/SchemaKey/NameKey Tests
    // ========================================================================

    @Test
    void testBuildClassAspectWithStructuredSuperTypeConfig() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithStructuredSuperType");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        assertNotNull(aspect.getSuperTypeConfig());
        SuperTypeSerializationConfig superConfig = aspect.getSuperTypeConfig();

        assertTrue(superConfig.isEnabled());
        assertEquals(SerializationFormat.STRUCTURED, superConfig.getFormat());
        assertEquals("schemaUri", superConfig.getSchemaKey());
        assertEquals("typeName", superConfig.getNameKey());
    }

    // ========================================================================
    // NEW: Reference Configuration Tests
    // ========================================================================

    @Test
    void testBuildReferenceAspectWithRefConfig() {
        EClass personClass = helper.getEClass(testPackage, "PersonWithRefConfig");
        EReference employerRef = (EReference) helper.getFeature(personClass, "employer");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(employerRef);

        assertNotNull(aspect.getReferenceConfig());
        ReferenceSerializationConfig refConfig = aspect.getReferenceConfig();

        assertEquals(SerializationFormat.STRUCTURED, refConfig.getFormat());
        assertEquals("$ref", refConfig.getRefKey());
        assertEquals("$type", refConfig.getTypeKey());
        assertTrue(refConfig.isExpand());
        assertTrue(aspect.isExpand()); // Should also be set on aspect directly
    }

    // ========================================================================
    // NEW: Inline Type Mapping Tests
    // ========================================================================

    @Test
    void testBuildReferenceAspectWithInlineTypeMappings() {
        EClass personClass = helper.getEClass(testPackage, "PersonWithContacts");
        EReference contactsRef = (EReference) helper.getFeature(personClass, "contacts");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(contactsRef);

        // Should have type config with discriminator path
        assertNotNull(aspect.getTypeConfig());
        assertEquals("contactType", aspect.getTypeConfig().getDiscriminatorPath());

        // Should have 3 inline mappings
        assertEquals(3, aspect.getInlineTypeMappings().size());

        // Find specific mappings
        InlineTypeMapping friendMapping = aspect.getInlineTypeMappings().stream()
                .filter(m -> "friend".equals(m.getDiscriminatorValue()))
                .findFirst()
                .orElse(null);
        assertNotNull(friendMapping);
        assertEquals("http://test.codec.example.org/1.0#//Friend", friendMapping.getTargetClass());

        InlineTypeMapping enemyMapping = aspect.getInlineTypeMappings().stream()
                .filter(m -> "enemy".equals(m.getDiscriminatorValue()))
                .findFirst()
                .orElse(null);
        assertNotNull(enemyMapping);
        assertEquals("http://test.codec.example.org/1.0#//Enemy", enemyMapping.getTargetClass());

        InlineTypeMapping colleagueMapping = aspect.getInlineTypeMappings().stream()
                .filter(m -> "colleague".equals(m.getDiscriminatorValue()))
                .findFirst()
                .orElse(null);
        assertNotNull(colleagueMapping);
        assertEquals("http://test.codec.example.org/1.0#//Contact", colleagueMapping.getTargetClass());
    }

    // ========================================================================
    // NEW: Feature Serialize Options Tests
    // ========================================================================

    @Test
    void testBuildAttributeAspectWithExplicitSerialize() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSerializeOptions");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "explicitSerialize");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertTrue(aspect.isSerialize());
    }

    @Test
    void testBuildAttributeAspectWithSerializeFalse() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSerializeOptions");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "noSerialize");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertFalse(aspect.isSerialize());
    }

    @Test
    void testBuildAttributeAspectSerializeOverridesTransient() {
        // When both transient=true and serialize=true are present, serialize wins
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSerializeOptions");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "conflictSerializeWins");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertTrue(aspect.isSerialize()); // serialize=true wins over transient=true
    }

    @Test
    void testBuildAttributeAspectWithSerializeNull() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSerializeOptions");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "nullableField");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertTrue(aspect.isSerializeNull());
    }

    @Test
    void testBuildAttributeAspectWithSerializeEmpty() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSerializeOptions");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "emptyListField");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertTrue(aspect.isSerializeEmpty());
    }

    @Test
    void testBuildAttributeAspectWithSerializeDefaults() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithSerializeOptions");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "defaultValueField");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertTrue(aspect.isSerializeDefaults());
    }

    // ========================================================================
    // NEW: Enum Serialization Tests
    // ========================================================================

    @Test
    void testBuildAttributeAspectWithEnumLiteralStrategy() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithEnumFields");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "statusLiteral");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertEquals(EnumSerializationStrategy.LITERAL, aspect.getEnumSerialization());
    }

    @Test
    void testBuildAttributeAspectWithEnumValueStrategy() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithEnumFields");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "statusValue");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertEquals(EnumSerializationStrategy.VALUE, aspect.getEnumSerialization());
    }

    @Test
    void testBuildAttributeAspectWithEnumNameStrategy() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithEnumFields");
        EAttribute attr = (EAttribute) helper.getFeature(entityClass, "statusName");

        FeatureCodecAspect aspect = (FeatureCodecAspect) provider.buildAttributeAspect(attr);

        assertEquals(EnumSerializationStrategy.NAME, aspect.getEnumSerialization());
    }

    // ========================================================================
    // NEW: Custom Key Tests
    // ========================================================================

    @Test
    void testBuildAttributeAspectWithCustomKey() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithCustomKeys");
        EAttribute firstNameAttr = (EAttribute) helper.getFeature(entityClass, "firstName");
        EAttribute lastNameAttr = (EAttribute) helper.getFeature(entityClass, "lastName");

        FeatureCodecAspect firstNameAspect = (FeatureCodecAspect) provider.buildAttributeAspect(firstNameAttr);
        FeatureCodecAspect lastNameAspect = (FeatureCodecAspect) provider.buildAttributeAspect(lastNameAttr);

        assertEquals("first_name", firstNameAspect.getEffectiveKey());
        assertEquals("last_name", lastNameAspect.getEffectiveKey());
    }

    @Test
    void testBuildReferenceAspectWithCustomKey() {
        EClass entityClass = helper.getEClass(testPackage, "EntityWithCustomKeys");
        EReference homeAddressRef = (EReference) helper.getFeature(entityClass, "homeAddress");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(homeAddressRef);

        assertEquals("home_address", aspect.getEffectiveKey());
    }

    // ========================================================================
    // NEW: Configuration Hierarchy Tests (EClass → EReference override)
    // ========================================================================

    @Test
    void testReferenceWithNoOverrideHasNoTypeConfig() {
        // Reference without codec annotation should not have type config
        // (inheritance is handled at runtime by ConfigurationMerger, not by provider)
        EClass personClass = helper.getEClass(testPackage, "PersonWithHierarchyConfig");
        EReference primaryAddressRef = (EReference) helper.getFeature(personClass, "primaryAddress");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(primaryAddressRef);

        // No annotation = no type config parsed by provider
        assertNull(aspect.getTypeConfig());
    }

    @Test
    void testReferenceOverridesTypeConfig() {
        // Reference with type annotation should have its own type config
        EClass personClass = helper.getEClass(testPackage, "PersonWithHierarchyConfig");
        EReference businessAddressRef = (EReference) helper.getFeature(personClass, "businessAddress");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(businessAddressRef);

        assertNotNull(aspect.getTypeConfig());
        TypeSerializationConfig typeConfig = aspect.getTypeConfig();

        assertEquals(TypeStrategy.NAME, typeConfig.getStrategy());
        assertEquals(SerializationFormat.STRUCTURED, typeConfig.getFormat());
        assertEquals("addressType", typeConfig.getTypeKey());
    }

    @Test
    void testReferencePartiallyOverridesTypeConfig() {
        // Reference with partial type annotation - provider parses what's there
        EClass personClass = helper.getEClass(testPackage, "PersonWithHierarchyConfig");
        EReference shippingAddressRef = (EReference) helper.getFeature(personClass, "shippingAddress");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(shippingAddressRef);

        assertNotNull(aspect.getTypeConfig());
        TypeSerializationConfig typeConfig = aspect.getTypeConfig();

        // Only format is specified in the annotation
        assertEquals(SerializationFormat.STRUCTURED, typeConfig.getFormat());
        // Strategy not set - uses default (URI)
        assertEquals(TypeStrategy.URI, typeConfig.getStrategy()); // Default from parser
        // TypeKey not set - uses EMF model default "_type"
        assertEquals("_type", typeConfig.getTypeKey()); // EMF model default
    }

    @Test
    void testClassLevelTypeConfig() {
        // Verify class has its own type config
        EClass personClass = helper.getEClass(testPackage, "PersonWithHierarchyConfig");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(personClass);

        assertNotNull(aspect.getTypeConfig());
        TypeSerializationConfig typeConfig = aspect.getTypeConfig();

        assertEquals(TypeStrategy.URI, typeConfig.getStrategy());
        assertEquals(SerializationFormat.PLAIN, typeConfig.getFormat());
        assertEquals("_type", typeConfig.getTypeKey());
    }

    // ========================================================================
    // NEW: Misconfiguration Tests (Keys at Wrong Levels)
    // These tests document current behavior - misplaced keys are ignored.
    // Future versions may add validation warnings/errors.
    // ========================================================================

    @Test
    void testClassIgnoresRefConfigKeys() {
        // refFormat/refKey are reference-only - should be ignored on EClass
        EClass entityClass = helper.getEClass(testPackage, "ClassWithRefConfigMisplaced");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        // Class should not have reference config (it's class-level, not reference-level)
        // The provider doesn't parse refConfig for classes - it's ignored
        assertNotNull(aspect.getTypeConfig()); // Valid key was parsed
        assertEquals(TypeStrategy.URI, aspect.getTypeConfig().getStrategy());
        // Note: ClassCodecAspect doesn't have getReferenceConfig() - that's correct
        // The refFormat/refKey keys are simply ignored for classes
    }

    @Test
    void testReferenceIgnoresIdConfigKeys() {
        // idStrategy/idFeatures are class-only - should be ignored on EReference
        EClass entityClass = helper.getEClass(testPackage, "RefWithIdConfigMisplaced");
        EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

        // Reference should not have ID config - it's not parsed for references
        // The provider ignores idStrategy/idFeatures on references
        assertNotNull(aspect.getTypeConfig()); // Valid key was parsed
        assertEquals(TypeStrategy.NAME, aspect.getTypeConfig().getStrategy());
        // Note: ReferenceCodecAspect doesn't have getIdConfig() - that's correct
        // The idStrategy/idFeatures keys are simply ignored for references
    }

    @Test
    void testReferenceIgnoresSuperTypeConfigKeys() {
        // superType* keys are class-only - should be ignored on EReference
        EClass entityClass = helper.getEClass(testPackage, "RefWithSuperTypeMisplaced");
        EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

        // Reference should not have superType config - it's not parsed for references
        assertEquals("addr", aspect.getEffectiveKey()); // Valid key was parsed
        // Note: ReferenceCodecAspect doesn't have getSuperTypeConfig() - that's correct
        // The superType* keys are simply ignored for references
    }

    @Test
    void testReferenceIgnoresEnumSerializationKey() {
        // enumSerialization is attribute-only per spec - should be ignored on EReference
        // The annotation has enumSerialization=VALUE but it should NOT be parsed for references
        EClass entityClass = helper.getEClass(testPackage, "RefWithEnumSerializationMisplaced");
        EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

        assertEquals("addr", aspect.getEffectiveKey()); // Valid key was parsed
        // The annotation has VALUE but should be ignored - stays at EMF model default (LITERAL)
        assertEquals(EnumSerializationStrategy.LITERAL, aspect.getEnumSerialization());
        // Note: VALUE from annotation was NOT applied because enumSerialization is attribute-only
    }

    @Test
    void testClassIgnoresInlineMappingKeys() {
        // inlineMapping.* keys are reference-only - should be ignored on EClass
        EClass entityClass = helper.getEClass(testPackage, "ClassWithInlineMappingMisplaced");

        ClassCodecAspect aspect = (ClassCodecAspect) provider.buildClassAspect(entityClass);

        // Class should not have inline mappings - they're not parsed at class level
        assertNotNull(aspect.getTypeConfig()); // Valid key was parsed
        assertEquals(TypeStrategy.URI, aspect.getTypeConfig().getStrategy());
        // Note: ClassCodecAspect doesn't have getInlineTypeMappings() - that's correct
        // The inlineMapping.* keys are simply ignored for classes
    }

    @Test
    void testReferenceIgnoresTypeDiscriminatorKey() {
        // typeDiscriminator (the value, not path) is class-only - should be ignored on EReference
        EClass entityClass = helper.getEClass(testPackage, "RefWithDiscriminatorMisplaced");
        EReference addressRef = (EReference) helper.getFeature(entityClass, "address");

        ReferenceCodecAspect aspect = (ReferenceCodecAspect) provider.buildReferenceAspect(addressRef);

        // Reference should not have discriminator value set
        assertNotNull(aspect.getTypeConfig()); // Valid key was parsed
        assertEquals(TypeStrategy.NAME, aspect.getTypeConfig().getStrategy());
        // Note: ReferenceCodecAspect doesn't have getDiscriminatorValue() - that's correct
        // The typeDiscriminator key is simply ignored for references
    }
}
