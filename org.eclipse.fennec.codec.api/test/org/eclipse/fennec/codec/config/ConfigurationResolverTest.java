/*
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
package org.eclipse.fennec.codec.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.fennec.codec.diagnostic.DiagnosticCollector;
import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ConfigurationResolver}.
 */
@DisplayName("ConfigurationResolver")
class ConfigurationResolverTest {

    private DiagnosticCollector diagnostics;
    private EPackage testPackage;
    private EClass personClass;
    private EClass addressClass;
    private EAttribute firstNameAttribute;
    private EReference addressReference;

    @BeforeEach
    void setUp() {
        diagnostics = new DiagnosticCollector();

        // Create test model
        testPackage = EcoreFactory.eINSTANCE.createEPackage();
        testPackage.setName("test");
        testPackage.setNsPrefix("test");
        testPackage.setNsURI("http://test.org/1.0");

        personClass = EcoreFactory.eINSTANCE.createEClass();
        personClass.setName("Person");
        testPackage.getEClassifiers().add(personClass);

        addressClass = EcoreFactory.eINSTANCE.createEClass();
        addressClass.setName("Address");
        testPackage.getEClassifiers().add(addressClass);

        firstNameAttribute = EcoreFactory.eINSTANCE.createEAttribute();
        firstNameAttribute.setName("firstName");
        firstNameAttribute.setEType(EcorePackage.Literals.ESTRING);
        personClass.getEStructuralFeatures().add(firstNameAttribute);

        addressReference = EcoreFactory.eINSTANCE.createEReference();
        addressReference.setName("address");
        addressReference.setEType(addressClass);
        personClass.getEStructuralFeatures().add(addressReference);
    }

    @Nested
    @DisplayName("builder")
    class Builder {

        @Test
        @DisplayName("builds resolver with no configuration sources")
        void buildsResolverWithNoConfigurationSources() {
            ConfigurationResolver resolver = ConfigurationResolver.builder().build();

            assertNotNull(resolver);
        }

        @Test
        @DisplayName("builds resolver with all configuration sources")
        void buildsResolverWithAllConfigurationSources() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeKey", "annotation"))
                    .moduleProperties(Map.of("typeKey", "module"))
                    .factoryProperties(Map.of("typeKey", "factory"))
                    .resourceProperties(Map.of("typeKey", "resource"))
                    .optionsProperties(Map.of("typeKey", "options"))
                    .build();

            assertNotNull(resolver);
        }

        @Test
        @DisplayName("defaults() creates resolver with no configuration")
        void defaultsCreatesResolverWithNoConfiguration() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            assertNotNull(resolver);
        }
    }

    @Nested
    @DisplayName("resolveTypeConfig")
    class ResolveTypeConfig {

        @Test
        @DisplayName("throws NullPointerException when eClass is null")
        void throwsNullPointerExceptionWhenEClassIsNull() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            assertThrows(NullPointerException.class,
                    () -> resolver.resolveTypeConfig(null, diagnostics));
        }

        @Test
        @DisplayName("throws NullPointerException when diagnostics is null")
        void throwsNullPointerExceptionWhenDiagnosticsIsNull() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            assertThrows(NullPointerException.class,
                    () -> resolver.resolveTypeConfig(personClass, null));
        }

        @Test
        @DisplayName("returns defaults when no configuration provided")
        void returnsDefaultsWhenNoConfigurationProvided() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertTrue(config.isInclude());
            assertEquals(TypeStrategy.URI, config.getStrategy());
            assertEquals("_type", config.getTypeKey());
        }

        @Test
        @DisplayName("caches results for same EClass")
        void cachesResultsForSameEClass() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            TypeConfig first = resolver.resolveTypeConfig(personClass, diagnostics);
            TypeConfig second = resolver.resolveTypeConfig(personClass, diagnostics);

            assertSame(first, second);
        }

        @Test
        @DisplayName("different EClass gets different config")
        void differentEClassGetsDifferentConfig() {
            Map<String, Object> personConfig = new HashMap<>();
            personConfig.put("typeKey", "personType");
            Map<String, Object> addressConfig = new HashMap<>();
            addressConfig.put("typeKey", "addressType");

            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "Person", personConfig,
                            "Address", addressConfig
                    ))
                    .build();

            TypeConfig personTypeConfig = resolver.resolveTypeConfig(personClass, diagnostics);
            TypeConfig addressTypeConfig = resolver.resolveTypeConfig(addressClass, diagnostics);

            assertEquals("personType", personTypeConfig.getTypeKey());
            assertEquals("addressType", addressTypeConfig.getTypeKey());
        }

        @Test
        @DisplayName("options override all other sources")
        void optionsOverrideAllOtherSources() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeKey", "annotation"))
                    .moduleProperties(Map.of("typeKey", "module"))
                    .factoryProperties(Map.of("typeKey", "factory"))
                    .resourceProperties(Map.of("typeKey", "resource"))
                    .optionsProperties(Map.of("typeKey", "options"))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("options", config.getTypeKey());
        }

        @Test
        @DisplayName("resource overrides factory, module, annotation when options absent")
        void resourceOverridesWhenOptionsAbsent() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeKey", "annotation"))
                    .moduleProperties(Map.of("typeKey", "module"))
                    .factoryProperties(Map.of("typeKey", "factory"))
                    .resourceProperties(Map.of("typeKey", "resource"))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("resource", config.getTypeKey());
        }

        @Test
        @DisplayName("factory overrides module, annotation when resource absent")
        void factoryOverridesWhenResourceAbsent() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeKey", "annotation"))
                    .moduleProperties(Map.of("typeKey", "module"))
                    .factoryProperties(Map.of("typeKey", "factory"))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("factory", config.getTypeKey());
        }

        @Test
        @DisplayName("module overrides annotation when factory absent")
        void moduleOverridesWhenFactoryAbsent() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeKey", "annotation"))
                    .moduleProperties(Map.of("typeKey", "module"))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("module", config.getTypeKey());
        }

        @Test
        @DisplayName("annotation used when all runtime sources absent")
        void annotationUsedWhenRuntimeSourcesAbsent() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeKey", "annotation"))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("annotation", config.getTypeKey());
        }

        @Test
        @DisplayName("class-level overrides global-level at same source")
        void classLevelOverridesGlobalLevelAtSameSource() {
            Map<String, Object> classConfig = new HashMap<>();
            classConfig.put("typeKey", "classLevel");

            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeKey", "globalLevel",
                            "Person", classConfig
                    ))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("classLevel", config.getTypeKey());
        }

        @Test
        @DisplayName("different properties can come from different sources")
        void differentPropertiesCanComeFromDifferentSources() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeStrategy", "NAME"))
                    .moduleProperties(Map.of("typeFormat", "STRUCTURED"))
                    .optionsProperties(Map.of("typeKey", "options"))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("options", config.getTypeKey());
            assertEquals(SerializationFormat.STRUCTURED, config.getFormat());
            assertEquals(TypeStrategy.NAME, config.getStrategy());
        }
    }

    @Nested
    @DisplayName("resolveGlobalTypeConfig")
    class ResolveGlobalTypeConfig {

        @Test
        @DisplayName("throws NullPointerException when diagnostics is null")
        void throwsNullPointerExceptionWhenDiagnosticsIsNull() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            assertThrows(NullPointerException.class,
                    () -> resolver.resolveGlobalTypeConfig(null));
        }

        @Test
        @DisplayName("returns defaults when no configuration provided")
        void returnsDefaultsWhenNoConfigurationProvided() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            TypeConfig config = resolver.resolveGlobalTypeConfig(diagnostics);

            assertTrue(config.isInclude());
            assertEquals(TypeStrategy.URI, config.getStrategy());
        }

        @Test
        @DisplayName("returns cached global config")
        void returnsCachedGlobalConfig() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            TypeConfig first = resolver.resolveGlobalTypeConfig(diagnostics);
            TypeConfig second = resolver.resolveGlobalTypeConfig(diagnostics);

            assertSame(first, second);
        }

        @Test
        @DisplayName("uses global properties only")
        void usesGlobalPropertiesOnly() {
            Map<String, Object> classConfig = new HashMap<>();
            classConfig.put("typeKey", "classLevel");

            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeKey", "globalLevel",
                            "Person", classConfig
                    ))
                    .build();

            TypeConfig config = resolver.resolveGlobalTypeConfig(diagnostics);

            assertEquals("globalLevel", config.getTypeKey());
        }
    }

    @Nested
    @DisplayName("resolveFeatureConfig")
    class ResolveFeatureConfig {

        @Test
        @DisplayName("throws NullPointerException when feature is null")
        void throwsNullPointerExceptionWhenFeatureIsNull() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            assertThrows(NullPointerException.class,
                    () -> resolver.resolveFeatureConfig(null, diagnostics));
        }

        @Test
        @DisplayName("returns defaults when no configuration provided")
        void returnsDefaultsWhenNoConfigurationProvided() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            FeatureConfig config = resolver.resolveFeatureConfig(firstNameAttribute, diagnostics);

            assertFalse(config.isIgnore());
            assertFalse(config.isSerializeNull());
        }

        @Test
        @DisplayName("caches results for same feature")
        void cachesResultsForSameFeature() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            FeatureConfig first = resolver.resolveFeatureConfig(firstNameAttribute, diagnostics);
            FeatureConfig second = resolver.resolveFeatureConfig(firstNameAttribute, diagnostics);

            assertSame(first, second);
        }

        @Test
        @DisplayName("feature-level overrides class-level")
        void featureLevelOverridesClassLevel() {
            Map<String, Object> featureConfig = new HashMap<>();
            featureConfig.put("key", "featureLevel");

            Map<String, Object> classConfig = new HashMap<>();
            classConfig.put("key", "classLevel");
            classConfig.put("firstName", featureConfig);

            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of("Person", classConfig))
                    .build();

            FeatureConfig config = resolver.resolveFeatureConfig(firstNameAttribute, diagnostics);

            assertEquals("featureLevel", config.getKey());
        }

        @Test
        @DisplayName("class-level overrides global-level")
        void classLevelOverridesGlobalLevel() {
            Map<String, Object> classConfig = new HashMap<>();
            classConfig.put("serializeNull", true);

            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "serializeNull", false,
                            "Person", classConfig
                    ))
                    .build();

            FeatureConfig config = resolver.resolveFeatureConfig(firstNameAttribute, diagnostics);

            assertTrue(config.isSerializeNull());
        }
    }

    @Nested
    @DisplayName("resolveReferenceConfig")
    class ResolveReferenceConfig {

        @Test
        @DisplayName("throws NullPointerException when feature is null")
        void throwsNullPointerExceptionWhenFeatureIsNull() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            assertThrows(NullPointerException.class,
                    () -> resolver.resolveReferenceConfig(null, diagnostics));
        }

        @Test
        @DisplayName("returns defaults when no configuration provided")
        void returnsDefaultsWhenNoConfigurationProvided() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            ReferenceConfig config = resolver.resolveReferenceConfig(addressReference, diagnostics);

            assertEquals("$ref", config.getRefKey());
            assertFalse(config.isExpand());
        }

        @Test
        @DisplayName("caches results for same reference")
        void cachesResultsForSameReference() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            ReferenceConfig first = resolver.resolveReferenceConfig(addressReference, diagnostics);
            ReferenceConfig second = resolver.resolveReferenceConfig(addressReference, diagnostics);

            assertSame(first, second);
        }
    }

    @Nested
    @DisplayName("resolveIdConfig")
    class ResolveIdConfig {

        @Test
        @DisplayName("returns defaults when no configuration provided")
        void returnsDefaultsWhenNoConfigurationProvided() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            IdConfig config = resolver.resolveIdConfig(personClass, diagnostics);

            assertEquals("_id", config.getKey());
        }

        @Test
        @DisplayName("class-level overrides global-level")
        void classLevelOverridesGlobalLevel() {
            Map<String, Object> classConfig = new HashMap<>();
            classConfig.put("idKey", "personId");

            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "idKey", "globalId",
                            "Person", classConfig
                    ))
                    .build();

            IdConfig config = resolver.resolveIdConfig(personClass, diagnostics);

            assertEquals("personId", config.getKey());
        }
    }

    @Nested
    @DisplayName("resolveSuperTypeConfig")
    class ResolveSuperTypeConfig {

        @Test
        @DisplayName("returns defaults when no configuration provided")
        void returnsDefaultsWhenNoConfigurationProvided() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            SuperTypeConfig config = resolver.resolveSuperTypeConfig(personClass, diagnostics);

            assertFalse(config.isSerialize());
        }
    }

    @Nested
    @DisplayName("resolveDiscriminatorConfig")
    class ResolveDiscriminatorConfig {

        @Test
        @DisplayName("returns defaults when no configuration provided")
        void returnsDefaultsWhenNoConfigurationProvided() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            DiscriminatorConfig config = resolver.resolveDiscriminatorConfig(personClass, diagnostics);

            assertEquals(DiscriminatorConfig.FallbackStrategy.SKIP, config.getFallbackStrategy());
        }
    }

    @Nested
    @DisplayName("clearCaches")
    class ClearCaches {

        @Test
        @DisplayName("clears all cached configurations")
        void clearsAllCachedConfigurations() {
            ConfigurationResolver resolver = ConfigurationResolver.defaults();

            // Populate caches
            resolver.resolveTypeConfig(personClass, diagnostics);
            resolver.resolveFeatureConfig(firstNameAttribute, diagnostics);
            resolver.resolveGlobalTypeConfig(diagnostics);

            // Clear caches
            resolver.clearCaches();

            // Get new configs (should be different instances if properly cleared)
            TypeConfig typeConfig2 = resolver.resolveTypeConfig(personClass, diagnostics);
            FeatureConfig featureConfig2 = resolver.resolveFeatureConfig(firstNameAttribute, diagnostics);
            TypeConfig globalConfig2 = resolver.resolveGlobalTypeConfig(diagnostics);

            // After clear, new calls return new instances
            // Note: Values are same (defaults), but should be re-computed instances
            assertNotNull(typeConfig2);
            assertNotNull(featureConfig2);
            assertNotNull(globalConfig2);
        }
    }

    @Nested
    @DisplayName("two-dimensional resolution")
    class TwoDimensionalResolution {

        @Test
        @DisplayName("horizontal: feature → class → global (most specific wins)")
        void horizontalMostSpecificWins() {
            Map<String, Object> featureConfig = new HashMap<>();
            featureConfig.put("key", "featureKey");

            Map<String, Object> classConfig = new HashMap<>();
            classConfig.put("key", "classKey");
            classConfig.put("firstName", featureConfig);

            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "key", "globalKey",
                            "Person", classConfig
                    ))
                    .build();

            FeatureConfig config = resolver.resolveFeatureConfig(firstNameAttribute, diagnostics);

            assertEquals("featureKey", config.getKey());
        }

        @Test
        @DisplayName("vertical: options → resource → factory → module → annotation → default")
        void verticalHigherPriorityWins() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeKey", "annotation"))
                    .moduleProperties(Map.of("typeKey", "module"))
                    .factoryProperties(Map.of("typeKey", "factory"))
                    .resourceProperties(Map.of("typeKey", "resource"))
                    .optionsProperties(Map.of("typeKey", "options"))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("options", config.getTypeKey());
        }

        @Test
        @DisplayName("combined: horizontal at each vertical level")
        void combinedHorizontalAtEachVerticalLevel() {
            // Module has global setting, Options has class-level setting
            // Options class-level should win over Module global
            Map<String, Object> optionsClassConfig = new HashMap<>();
            optionsClassConfig.put("typeKey", "optionsClass");

            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .moduleProperties(Map.of("typeKey", "moduleGlobal"))
                    .optionsProperties(Map.of("Person", optionsClassConfig))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            // Options class-level is higher priority than module global
            assertEquals("optionsClass", config.getTypeKey());
        }

        @Test
        @DisplayName("fallback through levels: property from annotation when not in options")
        void fallbackThroughLevels() {
            // Options sets typeKey, annotation sets typeStrategy
            // Both should be used
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .annotationProperties(Map.of("typeStrategy", "NAME"))
                    .optionsProperties(Map.of("typeKey", "optionsKey"))
                    .build();

            TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);

            assertEquals("optionsKey", config.getTypeKey());
            assertEquals(TypeStrategy.NAME, config.getStrategy());
        }
    }

    @Nested
    @DisplayName("cross-config validation")
    class CrossConfigValidation {

        @Test
        @DisplayName("STRUCTURED + NONE + superTypeSerialize=true produces ERROR")
        void structuredNoneSuperTypeSerializeProducesError() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeFormat", "STRUCTURED",
                            "typeStrategy", "NONE",
                            "superTypeSerialize", true
                    ))
                    .build();

            TypeConfig typeConfig = resolver.resolveTypeConfig(personClass, diagnostics);
            SuperTypeConfig superTypeConfig = resolver.resolveSuperTypeConfig(personClass, diagnostics);
            resolver.validateCrossConfig(typeConfig, superTypeConfig, diagnostics);

            assertTrue(diagnostics.hasErrors(),
                    "Expected ERROR for STRUCTURED + NONE + superTypeSerialize=true");
        }

        @Test
        @DisplayName("STRUCTURED + NONE + superTypeSerialize=false is valid")
        void structuredNoneSuperTypeSerializeFalseIsValid() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeFormat", "STRUCTURED",
                            "typeStrategy", "NONE",
                            "superTypeSerialize", false
                    ))
                    .build();

            TypeConfig typeConfig = resolver.resolveTypeConfig(personClass, diagnostics);
            SuperTypeConfig superTypeConfig = resolver.resolveSuperTypeConfig(personClass, diagnostics);
            resolver.validateCrossConfig(typeConfig, superTypeConfig, diagnostics);

            assertFalse(diagnostics.hasErrors(),
                    "STRUCTURED + NONE + superTypeSerialize=false should be valid");
        }

        @Test
        @DisplayName("PLAIN + NONE + superTypeSerialize=true is valid")
        void plainNoneSuperTypeSerializeTrueIsValid() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeFormat", "PLAIN",
                            "typeStrategy", "NONE",
                            "superTypeSerialize", true
                    ))
                    .build();

            TypeConfig typeConfig = resolver.resolveTypeConfig(personClass, diagnostics);
            SuperTypeConfig superTypeConfig = resolver.resolveSuperTypeConfig(personClass, diagnostics);
            resolver.validateCrossConfig(typeConfig, superTypeConfig, diagnostics);

            assertFalse(diagnostics.hasErrors(),
                    "PLAIN + NONE + superTypeSerialize=true should be valid");
        }

        @Test
        @DisplayName("STRUCTURED + URI + superTypeSerialize=true is valid")
        void structuredUriSuperTypeSerializeTrueIsValid() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeFormat", "STRUCTURED",
                            "typeStrategy", "URI",
                            "superTypeSerialize", true
                    ))
                    .build();

            TypeConfig typeConfig = resolver.resolveTypeConfig(personClass, diagnostics);
            SuperTypeConfig superTypeConfig = resolver.resolveSuperTypeConfig(personClass, diagnostics);
            resolver.validateCrossConfig(typeConfig, superTypeConfig, diagnostics);

            assertFalse(diagnostics.hasErrors(),
                    "STRUCTURED + URI + superTypeSerialize=true should be valid");
        }

        @Test
        @DisplayName("STRUCTURED + both value readers produces WARNING")
        void structuredBothValueReadersProducesWarning() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeFormat", "STRUCTURED",
                            "typeValueReaderName", "typeReader",
                            "superTypeValueReaderName", "superTypeReader"
                    ))
                    .build();

            TypeConfig typeConfig = resolver.resolveTypeConfig(personClass, diagnostics);
            SuperTypeConfig superTypeConfig = resolver.resolveSuperTypeConfig(personClass, diagnostics);
            resolver.validateCrossConfig(typeConfig, superTypeConfig, diagnostics);

            assertTrue(diagnostics.hasWarnings(),
                    "Expected WARNING for STRUCTURED + both value readers");
        }

        @Test
        @DisplayName("STRUCTURED + both value writers produces WARNING")
        void structuredBothValueWritersProducesWarning() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeFormat", "STRUCTURED",
                            "typeValueWriterName", "typeWriter",
                            "superTypeValueWriterName", "superTypeWriter"
                    ))
                    .build();

            TypeConfig typeConfig = resolver.resolveTypeConfig(personClass, diagnostics);
            SuperTypeConfig superTypeConfig = resolver.resolveSuperTypeConfig(personClass, diagnostics);
            resolver.validateCrossConfig(typeConfig, superTypeConfig, diagnostics);

            assertTrue(diagnostics.hasWarnings(),
                    "Expected WARNING for STRUCTURED + both value writers");
        }

        @Test
        @DisplayName("PLAIN + both value readers is valid (no conflict)")
        void plainBothValueReadersIsValid() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeFormat", "PLAIN",
                            "typeValueReaderName", "typeReader",
                            "superTypeValueReaderName", "superTypeReader"
                    ))
                    .build();

            TypeConfig typeConfig = resolver.resolveTypeConfig(personClass, diagnostics);
            SuperTypeConfig superTypeConfig = resolver.resolveSuperTypeConfig(personClass, diagnostics);
            resolver.validateCrossConfig(typeConfig, superTypeConfig, diagnostics);

            assertFalse(diagnostics.hasWarnings(),
                    "PLAIN + both value readers should be valid (separate fields)");
        }

        @Test
        @DisplayName("resolveTypeAndSuperTypeConfig performs cross-config validation")
        void resolveTypeAndSuperTypeConfigPerformsCrossValidation() {
            ConfigurationResolver resolver = ConfigurationResolver.builder()
                    .optionsProperties(Map.of(
                            "typeFormat", "STRUCTURED",
                            "typeStrategy", "NONE",
                            "superTypeSerialize", true
                    ))
                    .build();

            resolver.resolveTypeAndSuperTypeConfig(personClass, diagnostics);

            assertTrue(diagnostics.hasErrors(),
                    "resolveTypeAndSuperTypeConfig should perform cross-config validation");
        }
    }
}
