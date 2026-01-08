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
package org.eclipse.fennec.codec.v2.config.effective;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.ClassMetadata;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.FeatureMetadata;
import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.IdStrategy;
import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.SuperTypeSelection;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ConfigurationMerger}.
 */
@DisplayName("ConfigurationMerger")
class ConfigurationMergerTest {

    private EClass testEClass;
    private EAttribute nameAttribute;
    private CodecConfiguration moduleConfig;

    @BeforeEach
    void setUp() {
        // Create test EClass
        testEClass = EcoreFactory.eINSTANCE.createEClass();
        testEClass.setName("Person");

        nameAttribute = EcoreFactory.eINSTANCE.createEAttribute();
        nameAttribute.setName("name");
        nameAttribute.setEType(EcorePackage.Literals.ESTRING);
        testEClass.getEStructuralFeatures().add(nameAttribute);

        moduleConfig = CodecConfiguration.builder().build();
    }

    @Nested
    @DisplayName("merge")
    class Merge {

        @Test
        @DisplayName("creates EffectiveCodecConfig with defaults")
        void createsEffectiveCodecConfigWithDefaults() {
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);

            EffectiveCodecConfig config = merger.merge();

            assertNotNull(config);
            assertFalse(config.isSortPropertiesAlphabetically());
            assertEquals("$ref", config.getRefKey());
        }

        @Test
        @DisplayName("uses module config global ignore features")
        void usesModuleConfigGlobalIgnoreFeatures() {
            moduleConfig = CodecConfiguration.builder()
                    .globalIgnoreFeatureNames(List.of("internal", "temp"))
                    .build();
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);

            EffectiveCodecConfig config = merger.merge();

            assertTrue(config.isGloballyIgnored("internal"));
            assertTrue(config.isGloballyIgnored("temp"));
            assertFalse(config.isGloballyIgnored("name"));
        }
    }

    @Nested
    @DisplayName("class config")
    class ClassConfig {

        @Test
        @DisplayName("builds class config with defaults when no aspect")
        void buildsClassConfigWithDefaultsWhenNoAspect() {
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            assertNotNull(classConfig);
            assertEquals(testEClass, classConfig.getEClass());
            assertTrue(classConfig.isIdEnabled());
            assertTrue(classConfig.isTypeEnabled());
            assertFalse(classConfig.isSuperTypeEnabled());
        }

        @Test
        @DisplayName("uses module config id key")
        void usesModuleConfigIdKey() {
            moduleConfig = CodecConfiguration.builder()
                    .idKey("id")
                    .build();
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            assertEquals("id", classConfig.getIdKey());
        }

        @Test
        @DisplayName("uses aspect id key over module config")
        void usesAspectIdKeyOverModuleConfig() {
            moduleConfig = CodecConfiguration.builder()
                    .idKey("defaultId")
                    .build();

            // Create mock metadata service with aspect
            MetadataService metadataService = mock(MetadataService.class);
            ClassMetadata classMetadata = mock(ClassMetadata.class);
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            IdSerializationConfig idConfig = mock(IdSerializationConfig.class);

            EList<ClassAspect> aspects = new BasicEList<>();
            aspects.add(aspect);

            when(metadataService.getClassMetadata(testEClass)).thenReturn(classMetadata);
            when(classMetadata.getAspects()).thenReturn(aspects);
            when(aspect.getIdConfig()).thenReturn(idConfig);
            when(idConfig.getIdKey()).thenReturn("customId");

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, metadataService, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            assertEquals("customId", classConfig.getIdKey());
        }

        @Test
        @DisplayName("caches class config")
        void cachesClassConfig() {
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveClassConfig first = config.getClassConfig(testEClass);
            EffectiveClassConfig second = config.getClassConfig(testEClass);

            assertTrue(first == second, "Should return same cached instance");
            assertEquals(1, config.getClassCacheSize());
        }
    }

    @Nested
    @DisplayName("id config")
    class IdConfig {

        @Test
        @DisplayName("builds id config with defaults")
        void buildsIdConfigWithDefaults() {
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();
            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            EffectiveIdConfig idConfig = classConfig.getIdConfig();

            assertTrue(idConfig.isEnabled());
            assertTrue(idConfig.isOnTop());
            assertEquals("_id", idConfig.getKey());
            assertEquals(IdStrategy.ID_FIELD, idConfig.getStrategy());
            assertEquals(IdKeyMode.ID_ONLY, idConfig.getKeyMode());
            assertEquals(SerializationFormat.PLAIN, idConfig.getFormat());
            assertEquals("-", idConfig.getSeparator());
        }

        @Test
        @DisplayName("uses aspect id strategy")
        void usesAspectIdStrategy() {
            MetadataService metadataService = mock(MetadataService.class);
            ClassMetadata classMetadata = mock(ClassMetadata.class);
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            IdSerializationConfig idConfig = mock(IdSerializationConfig.class);

            EList<ClassAspect> aspects = new BasicEList<>();
            aspects.add(aspect);

            when(metadataService.getClassMetadata(testEClass)).thenReturn(classMetadata);
            when(classMetadata.getAspects()).thenReturn(aspects);
            when(aspect.getIdConfig()).thenReturn(idConfig);
            when(idConfig.getStrategy()).thenReturn(IdStrategy.COMBINED);
            when(idConfig.getKeyMode()).thenReturn(IdKeyMode.BOTH);

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, metadataService, null, null);
            EffectiveCodecConfig config = merger.merge();
            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            assertEquals(IdStrategy.COMBINED, classConfig.getIdConfig().getStrategy());
            assertEquals(IdKeyMode.BOTH, classConfig.getIdConfig().getKeyMode());
        }
    }

    @Nested
    @DisplayName("type config")
    class TypeConfig {

        @Test
        @DisplayName("builds type config with defaults")
        void buildsTypeConfigWithDefaults() {
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();
            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            EffectiveTypeConfig typeConfig = classConfig.getTypeConfig();

            assertTrue(typeConfig.isEnabled());
            assertEquals(TypeStrategy.URI, typeConfig.getStrategy());
            assertEquals("_type", typeConfig.getTypeKey());
            assertEquals("schema", typeConfig.getSchemaKey());
            assertEquals("name", typeConfig.getNameKey());
        }

        @Test
        @DisplayName("uses aspect type configuration")
        void usesAspectTypeConfiguration() {
            MetadataService metadataService = mock(MetadataService.class);
            ClassMetadata classMetadata = mock(ClassMetadata.class);
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            TypeSerializationConfig typeSerConfig = mock(TypeSerializationConfig.class);

            EList<ClassAspect> aspects = new BasicEList<>();
            aspects.add(aspect);

            when(metadataService.getClassMetadata(testEClass)).thenReturn(classMetadata);
            when(classMetadata.getAspects()).thenReturn(aspects);
            when(aspect.getTypeConfig()).thenReturn(typeSerConfig);
            when(typeSerConfig.isInclude()).thenReturn(true);
            when(typeSerConfig.getStrategy()).thenReturn(TypeStrategy.NAME);
            when(typeSerConfig.getTypeKey()).thenReturn("@class");

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, metadataService, null, null);
            EffectiveCodecConfig config = merger.merge();
            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            assertEquals(TypeStrategy.NAME, classConfig.getTypeConfig().getStrategy());
            assertEquals("@class", classConfig.getTypeConfig().getTypeKey());
        }

        @Test
        @DisplayName("uses discriminator value from class aspect")
        void usesDiscriminatorValueFromClassAspect() {
            MetadataService metadataService = mock(MetadataService.class);
            ClassMetadata classMetadata = mock(ClassMetadata.class);
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);

            EList<ClassAspect> aspects = new BasicEList<>();
            aspects.add(aspect);

            when(metadataService.getClassMetadata(testEClass)).thenReturn(classMetadata);
            when(classMetadata.getAspects()).thenReturn(aspects);
            when(aspect.getDiscriminatorValue()).thenReturn("person");

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, metadataService, null, null);
            EffectiveCodecConfig config = merger.merge();
            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            assertEquals("person", classConfig.getTypeConfig().getDiscriminatorValue());
        }
    }

    @Nested
    @DisplayName("supertype config")
    class SuperTypeConfig {

        @Test
        @DisplayName("builds supertype config with defaults")
        void buildsSuperTypeConfigWithDefaults() {
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();
            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            EffectiveSuperTypeConfig superTypeConfig = classConfig.getSuperTypeConfig();

            assertFalse(superTypeConfig.isEnabled());
            // Default serializeAllSuperTypes=false maps to SINGLE selection
            assertEquals(SuperTypeSelection.SINGLE, superTypeConfig.getSelection());
            assertEquals(SerializationFormat.PLAIN, superTypeConfig.getFormat());
            assertEquals("_supertype", superTypeConfig.getSuperTypeKey());
        }

        @Test
        @DisplayName("uses aspect supertype configuration")
        void usesAspectSuperTypeConfiguration() {
            MetadataService metadataService = mock(MetadataService.class);
            ClassMetadata classMetadata = mock(ClassMetadata.class);
            ClassCodecAspect aspect = mock(ClassCodecAspect.class);
            SuperTypeSerializationConfig superTypeSerConfig = mock(SuperTypeSerializationConfig.class);

            EList<ClassAspect> aspects = new BasicEList<>();
            aspects.add(aspect);

            when(metadataService.getClassMetadata(testEClass)).thenReturn(classMetadata);
            when(classMetadata.getAspects()).thenReturn(aspects);
            when(aspect.getSuperTypeConfig()).thenReturn(superTypeSerConfig);
            when(superTypeSerConfig.isEnabled()).thenReturn(true);
            when(superTypeSerConfig.getSelection()).thenReturn(SuperTypeSelection.SINGLE);
            when(superTypeSerConfig.getSuperTypeKey()).thenReturn("extends");

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, metadataService, null, null);
            EffectiveCodecConfig config = merger.merge();
            EffectiveClassConfig classConfig = config.getClassConfig(testEClass);

            assertTrue(classConfig.getSuperTypeConfig().isEnabled());
            assertEquals(SuperTypeSelection.SINGLE, classConfig.getSuperTypeConfig().getSelection());
            assertEquals("extends", classConfig.getSuperTypeConfig().getSuperTypeKey());
        }
    }

    @Nested
    @DisplayName("feature config")
    class FeatureConfig {

        @Test
        @DisplayName("builds feature config with defaults")
        void buildsFeatureConfigWithDefaults() {
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(nameAttribute);

            assertNotNull(featureConfig);
            assertEquals(nameAttribute, featureConfig.getFeature());
            assertEquals("name", featureConfig.getKey());
            assertTrue(featureConfig.isSerialize());
            assertFalse(featureConfig.isSerializeNull());
            assertFalse(featureConfig.isSerializeEmpty());
        }

        @Test
        @DisplayName("uses aspect feature key")
        void usesAspectFeatureKey() {
            MetadataService metadataService = mock(MetadataService.class);
            FeatureMetadata featureMetadata = mock(FeatureMetadata.class);
            FeatureCodecAspect aspect = mock(FeatureCodecAspect.class);

            EList<FeatureAspect> aspects = new BasicEList<>();
            aspects.add(aspect);

            when(metadataService.getFeatureMetadata(nameAttribute)).thenReturn(featureMetadata);
            when(featureMetadata.getAspects()).thenReturn(aspects);
            when(aspect.getEffectiveKey()).thenReturn("firstName");
            when(aspect.isSerialize()).thenReturn(true);

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, metadataService, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(nameAttribute);

            assertEquals("firstName", featureConfig.getKey());
        }

        @Test
        @DisplayName("marks globally ignored features as not serializable")
        void marksGloballyIgnoredFeaturesAsNotSerializable() {
            moduleConfig = CodecConfiguration.builder()
                    .globalIgnore("name")
                    .build();
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(nameAttribute);

            assertFalse(featureConfig.isSerialize());
        }

        @Test
        @DisplayName("marks derived features as not serializable")
        void marksDerivedFeaturesAsNotSerializable() {
            EAttribute derivedAttr = EcoreFactory.eINSTANCE.createEAttribute();
            derivedAttr.setName("derived");
            derivedAttr.setEType(EcorePackage.Literals.ESTRING);
            derivedAttr.setDerived(true);

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(derivedAttr);

            assertFalse(featureConfig.isSerialize());
        }

        @Test
        @DisplayName("marks transient features as not serializable")
        void marksTransientFeaturesAsNotSerializable() {
            EAttribute transientAttr = EcoreFactory.eINSTANCE.createEAttribute();
            transientAttr.setName("transient");
            transientAttr.setEType(EcorePackage.Literals.ESTRING);
            transientAttr.setTransient(true);

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(transientAttr);

            assertFalse(featureConfig.isSerialize());
        }

        @Test
        @DisplayName("uses aspect serialize null setting")
        void usesAspectSerializeNullSetting() {
            MetadataService metadataService = mock(MetadataService.class);
            FeatureMetadata featureMetadata = mock(FeatureMetadata.class);
            FeatureCodecAspect aspect = mock(FeatureCodecAspect.class);

            EList<FeatureAspect> aspects = new BasicEList<>();
            aspects.add(aspect);

            when(metadataService.getFeatureMetadata(nameAttribute)).thenReturn(featureMetadata);
            when(featureMetadata.getAspects()).thenReturn(aspects);
            when(aspect.isSerialize()).thenReturn(true);
            when(aspect.isSerializeNull()).thenReturn(true);
            when(aspect.isSerializeEmpty()).thenReturn(true);

            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, metadataService, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(nameAttribute);

            assertTrue(featureConfig.isSerializeNull());
            assertTrue(featureConfig.isSerializeEmpty());
        }

        @Test
        @DisplayName("caches feature config")
        void cachesFeatureConfig() {
            ConfigurationMerger merger = new ConfigurationMerger(moduleConfig, null, null, null);
            EffectiveCodecConfig config = merger.merge();

            EffectiveFeatureConfig first = config.getFeatureConfig(nameAttribute);
            EffectiveFeatureConfig second = config.getFeatureConfig(nameAttribute);

            assertTrue(first == second, "Should return same cached instance");
            assertEquals(1, config.getFeatureCacheSize());
        }
    }
}
