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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.diagnostic.DiagnosticCollector;
import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.TypeStrategy;

/**
 * Resolves effective configuration by merging all configuration sources.
 * <p>
 * This is the SINGLE POINT where configuration resolution is orchestrated.
 * It implements the two-dimensional configuration model from the spec:
 * <ul>
 *   <li><b>Source Hierarchy (Vertical)</b>: OPTIONS → RESOURCE → FACTORY → MODULE → ANNOTATION → DEFAULT</li>
 *   <li><b>Scope Chain (Horizontal)</b>: FEATURE → ECLASS → GLOBAL</li>
 * </ul>
 * <p>
 * Usage pattern:
 * <pre>
 * ConfigurationResolver resolver = ConfigurationResolver.builder()
 *     .annotationProperties(annotationMap)
 *     .moduleProperties(moduleMap)
 *     .factoryProperties(factoryMap)
 *     .resourceProperties(resourceMap)
 *     .optionsProperties(optionsMap)
 *     .build();
 *
 * // Get effective configs (cached)
 * TypeConfig typeConfig = resolver.resolveTypeConfig(eClass, diagnostics);
 * FeatureConfig featureConfig = resolver.resolveFeatureConfig(feature, diagnostics);
 * </pre>
 * <p>
 * The resolver caches effective configurations per EClass/EStructuralFeature for efficiency.
 *
 * @see ConfigSource
 * @see ConfigLevel
 * @see Mergeable
 */
public final class ConfigurationResolver {

    // Configuration sources (lower index = higher priority)
    private final Map<String, Object> optionsProperties;      // Level 1 - highest priority
    private final Map<String, Object> resourceProperties;     // Level 2
    private final Map<String, Object> factoryProperties;      // Level 3
    private final Map<String, Object> moduleProperties;       // Level 4
    private final Map<String, Object> annotationProperties;   // Level 5
    // Level 6 (defaults) is built into config classes

    // Caches for resolved configurations
    private final ConcurrentHashMap<EClass, TypeConfig> typeConfigCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<EClass, SuperTypeConfig> superTypeConfigCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<EClass, IdConfig> idConfigCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<EClass, DiscriminatorConfig> discriminatorConfigCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<EStructuralFeature, FeatureConfig> featureConfigCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<EStructuralFeature, ReferenceConfig> referenceConfigCache = new ConcurrentHashMap<>();

    // Global config cache (singleton per resolver)
    private volatile TypeConfig globalTypeConfig;
    private volatile SuperTypeConfig globalSuperTypeConfig;
    private volatile IdConfig globalIdConfig;
    private volatile DiscriminatorConfig globalDiscriminatorConfig;
    private volatile FeatureConfig globalFeatureConfig;
    private volatile ReferenceConfig globalReferenceConfig;

    private ConfigurationResolver(Builder builder) {
        this.optionsProperties = builder.optionsProperties;
        this.resourceProperties = builder.resourceProperties;
        this.factoryProperties = builder.factoryProperties;
        this.moduleProperties = builder.moduleProperties;
        this.annotationProperties = builder.annotationProperties;
    }

    // ========================================================================
    // Type Configuration Resolution
    // ========================================================================

    /**
     * Resolves effective TypeConfig for an EClass.
     * <p>
     * Resolution order (first non-null wins at each property):
     * <ol>
     *   <li>OPTIONS: EClass-scoped, then GLOBAL-scoped</li>
     *   <li>RESOURCE: EClass-scoped, then GLOBAL-scoped</li>
     *   <li>FACTORY: EClass-scoped, then GLOBAL-scoped</li>
     *   <li>MODULE: EClass-scoped, then GLOBAL-scoped</li>
     *   <li>ANNOTATION: from EClass annotation</li>
     *   <li>DEFAULT: built-in defaults</li>
     * </ol>
     *
     * @param eClass the EClass to resolve config for
     * @param diagnostics collector for validation diagnostics
     * @return the effective TypeConfig (cached)
     */
    public TypeConfig resolveTypeConfig(EClass eClass, DiagnosticCollector diagnostics) {
        Objects.requireNonNull(eClass, "eClass must not be null");
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        return typeConfigCache.computeIfAbsent(eClass, ec -> {
            // Start with defaults, merge in reverse priority order
            // Annotation layer: walk up the EClass hierarchy (parents first, then child overrides)
            TypeConfig resolved = TypeConfig.defaults()
                    .mergeWith(extractGlobalProperties(annotationProperties));
            for (EClass superType : ec.getEAllSuperTypes()) {
                resolved = resolved.mergeWith(extractClassProperties(annotationProperties, superType));
            }
            return resolved
                    .mergeWith(extractClassProperties(annotationProperties, ec))
                    .mergeWith(extractGlobalProperties(moduleProperties))
                    .mergeWith(extractClassProperties(moduleProperties, ec))
                    .mergeWith(extractGlobalProperties(factoryProperties))
                    .mergeWith(extractClassProperties(factoryProperties, ec))
                    .mergeWith(extractGlobalProperties(resourceProperties))
                    .mergeWith(extractClassProperties(resourceProperties, ec))
                    .mergeWith(extractGlobalProperties(optionsProperties))
                    .mergeWith(extractClassProperties(optionsProperties, ec))
                    .validate(diagnostics);
        });
    }

    /**
     * Resolves global TypeConfig (no EClass context).
     *
     * @param diagnostics collector for validation diagnostics
     * @return the effective global TypeConfig
     */
    public TypeConfig resolveGlobalTypeConfig(DiagnosticCollector diagnostics) {
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        if (globalTypeConfig == null) {
            synchronized (this) {
                if (globalTypeConfig == null) {
                    globalTypeConfig = TypeConfig.defaults()
                            .mergeWith(extractGlobalProperties(annotationProperties))
                            .mergeWith(extractGlobalProperties(moduleProperties))
                            .mergeWith(extractGlobalProperties(factoryProperties))
                            .mergeWith(extractGlobalProperties(resourceProperties))
                            .mergeWith(extractGlobalProperties(optionsProperties))
                            .validate(diagnostics);
                }
            }
        }
        return globalTypeConfig;
    }

    // ========================================================================
    // SuperType Configuration Resolution
    // ========================================================================

    /**
     * Resolves effective SuperTypeConfig for an EClass.
     *
     * @param eClass the EClass to resolve config for
     * @param diagnostics collector for validation diagnostics
     * @return the effective SuperTypeConfig (cached)
     */
    public SuperTypeConfig resolveSuperTypeConfig(EClass eClass, DiagnosticCollector diagnostics) {
        Objects.requireNonNull(eClass, "eClass must not be null");
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        return superTypeConfigCache.computeIfAbsent(eClass, ec -> {
            return SuperTypeConfig.defaults()
                    .mergeWith(extractGlobalProperties(annotationProperties))
                    .mergeWith(extractClassProperties(annotationProperties, ec))
                    .mergeWith(extractGlobalProperties(moduleProperties))
                    .mergeWith(extractClassProperties(moduleProperties, ec))
                    .mergeWith(extractGlobalProperties(factoryProperties))
                    .mergeWith(extractClassProperties(factoryProperties, ec))
                    .mergeWith(extractGlobalProperties(resourceProperties))
                    .mergeWith(extractClassProperties(resourceProperties, ec))
                    .mergeWith(extractGlobalProperties(optionsProperties))
                    .mergeWith(extractClassProperties(optionsProperties, ec))
                    .validate(diagnostics);
        });
    }

    /**
     * Resolves global SuperTypeConfig (no EClass context).
     *
     * @param diagnostics collector for validation diagnostics
     * @return the effective global SuperTypeConfig
     */
    public SuperTypeConfig resolveGlobalSuperTypeConfig(DiagnosticCollector diagnostics) {
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        if (globalSuperTypeConfig == null) {
            synchronized (this) {
                if (globalSuperTypeConfig == null) {
                    globalSuperTypeConfig = SuperTypeConfig.defaults()
                            .mergeWith(extractGlobalProperties(annotationProperties))
                            .mergeWith(extractGlobalProperties(moduleProperties))
                            .mergeWith(extractGlobalProperties(factoryProperties))
                            .mergeWith(extractGlobalProperties(resourceProperties))
                            .mergeWith(extractGlobalProperties(optionsProperties))
                            .validate(diagnostics);
                }
            }
        }
        return globalSuperTypeConfig;
    }

    // ========================================================================
    // ID Configuration Resolution
    // ========================================================================

    /**
     * Resolves effective IdConfig for an EClass.
     *
     * @param eClass the EClass to resolve config for
     * @param diagnostics collector for validation diagnostics
     * @return the effective IdConfig (cached)
     */
    public IdConfig resolveIdConfig(EClass eClass, DiagnosticCollector diagnostics) {
        Objects.requireNonNull(eClass, "eClass must not be null");
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        return idConfigCache.computeIfAbsent(eClass, ec -> {
            return IdConfig.defaults()
                    .mergeWith(extractGlobalProperties(annotationProperties))
                    .mergeWith(extractClassProperties(annotationProperties, ec))
                    .mergeWith(extractGlobalProperties(moduleProperties))
                    .mergeWith(extractClassProperties(moduleProperties, ec))
                    .mergeWith(extractGlobalProperties(factoryProperties))
                    .mergeWith(extractClassProperties(factoryProperties, ec))
                    .mergeWith(extractGlobalProperties(resourceProperties))
                    .mergeWith(extractClassProperties(resourceProperties, ec))
                    .mergeWith(extractGlobalProperties(optionsProperties))
                    .mergeWith(extractClassProperties(optionsProperties, ec))
                    .validate(diagnostics);
        });
    }

    /**
     * Resolves global IdConfig (no EClass context).
     *
     * @param diagnostics collector for validation diagnostics
     * @return the effective global IdConfig
     */
    public IdConfig resolveGlobalIdConfig(DiagnosticCollector diagnostics) {
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        if (globalIdConfig == null) {
            synchronized (this) {
                if (globalIdConfig == null) {
                    globalIdConfig = IdConfig.defaults()
                            .mergeWith(extractGlobalProperties(annotationProperties))
                            .mergeWith(extractGlobalProperties(moduleProperties))
                            .mergeWith(extractGlobalProperties(factoryProperties))
                            .mergeWith(extractGlobalProperties(resourceProperties))
                            .mergeWith(extractGlobalProperties(optionsProperties))
                            .validate(diagnostics);
                }
            }
        }
        return globalIdConfig;
    }

    // ========================================================================
    // Discriminator Configuration Resolution
    // ========================================================================

    /**
     * Resolves effective DiscriminatorConfig for an EClass.
     *
     * @param eClass the EClass to resolve config for
     * @param diagnostics collector for validation diagnostics
     * @return the effective DiscriminatorConfig (cached)
     */
    public DiscriminatorConfig resolveDiscriminatorConfig(EClass eClass, DiagnosticCollector diagnostics) {
        Objects.requireNonNull(eClass, "eClass must not be null");
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        return discriminatorConfigCache.computeIfAbsent(eClass, ec -> {
            return DiscriminatorConfig.defaults()
                    .mergeWith(extractGlobalProperties(annotationProperties))
                    .mergeWith(extractClassProperties(annotationProperties, ec))
                    .mergeWith(extractGlobalProperties(moduleProperties))
                    .mergeWith(extractClassProperties(moduleProperties, ec))
                    .mergeWith(extractGlobalProperties(factoryProperties))
                    .mergeWith(extractClassProperties(factoryProperties, ec))
                    .mergeWith(extractGlobalProperties(resourceProperties))
                    .mergeWith(extractClassProperties(resourceProperties, ec))
                    .mergeWith(extractGlobalProperties(optionsProperties))
                    .mergeWith(extractClassProperties(optionsProperties, ec))
                    .validate(diagnostics);
        });
    }

    /**
     * Resolves global DiscriminatorConfig (no EClass context).
     *
     * @param diagnostics collector for validation diagnostics
     * @return the effective global DiscriminatorConfig
     */
    public DiscriminatorConfig resolveGlobalDiscriminatorConfig(DiagnosticCollector diagnostics) {
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        if (globalDiscriminatorConfig == null) {
            synchronized (this) {
                if (globalDiscriminatorConfig == null) {
                    globalDiscriminatorConfig = DiscriminatorConfig.defaults()
                            .mergeWith(extractGlobalProperties(annotationProperties))
                            .mergeWith(extractGlobalProperties(moduleProperties))
                            .mergeWith(extractGlobalProperties(factoryProperties))
                            .mergeWith(extractGlobalProperties(resourceProperties))
                            .mergeWith(extractGlobalProperties(optionsProperties))
                            .validate(diagnostics);
                }
            }
        }
        return globalDiscriminatorConfig;
    }

    // ========================================================================
    // Feature Configuration Resolution
    // ========================================================================

    /**
     * Resolves effective FeatureConfig for an EStructuralFeature.
     * <p>
     * Resolution order:
     * <ol>
     *   <li>OPTIONS: Feature-scoped, then EClass-scoped, then GLOBAL-scoped</li>
     *   <li>RESOURCE: Feature-scoped, then EClass-scoped, then GLOBAL-scoped</li>
     *   <li>... (same pattern for all sources)</li>
     *   <li>ANNOTATION: from EStructuralFeature annotation, then EClass annotation</li>
     *   <li>DEFAULT: built-in defaults</li>
     * </ol>
     *
     * @param feature the EStructuralFeature to resolve config for
     * @param diagnostics collector for validation diagnostics
     * @return the effective FeatureConfig (cached)
     */
    public FeatureConfig resolveFeatureConfig(EStructuralFeature feature, DiagnosticCollector diagnostics) {
        Objects.requireNonNull(feature, "feature must not be null");
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        return featureConfigCache.computeIfAbsent(feature, f -> {
            EClass eClass = f.getEContainingClass();
            FeatureConfig resolved = FeatureConfig.defaults()
                    .mergeWith(extractGlobalProperties(annotationProperties))
                    .mergeWith(extractClassProperties(annotationProperties, eClass))
                    .mergeWith(extractFeatureProperties(annotationProperties, f))
                    .mergeWith(extractGlobalProperties(moduleProperties))
                    .mergeWith(extractClassProperties(moduleProperties, eClass))
                    .mergeWith(extractFeatureProperties(moduleProperties, f))
                    .mergeWith(extractGlobalProperties(factoryProperties))
                    .mergeWith(extractClassProperties(factoryProperties, eClass))
                    .mergeWith(extractFeatureProperties(factoryProperties, f))
                    .mergeWith(extractGlobalProperties(resourceProperties))
                    .mergeWith(extractClassProperties(resourceProperties, eClass))
                    .mergeWith(extractFeatureProperties(resourceProperties, f))
                    .mergeWith(extractGlobalProperties(optionsProperties))
                    .mergeWith(extractClassProperties(optionsProperties, eClass))
                    .mergeWith(extractFeatureProperties(optionsProperties, f))
                    .validate(diagnostics);

            // Apply key when no explicit key was configured
            // (ConfigProperty.KEY default is null = "use feature name or ExtendedMetaData name")
            if (resolved.getKey() == null) {
                String key = resolveDefaultFeatureKey(f);
                resolved = resolved.toBuilder().key(key).build();
            }

            // Apply global ignoreFeatures list
            List<String> globalIgnore = getGlobalProperty(ConfigProperty.IGNORE_FEATURES);
            if (globalIgnore != null && globalIgnore.contains(f.getName())) {
                if (!resolved.isForceWrite()) {
                    resolved = resolved.toBuilder().ignore(true).build();
                }
            }

            // Skip transient/derived/volatile features unless explicitly forced
            // This matches the old ConfigurationMerger.resolveFeatureSerialize() behavior
            if (f.isDerived() || f.isTransient() || f.isVolatile()) {
                if (!resolved.isForceWrite()) {
                    resolved = resolved.toBuilder().ignore(true).build();
                }
            }

            return resolved;
        });
    }

    /**
     * Resolves the default feature key when no explicit key is configured.
     * <p>
     * If useNamesFromExtendedMetaData is enabled, checks for ExtendedMetaData "name"
     * annotation on the feature. Otherwise, uses the feature name.
     * </p>
     *
     * @param feature the feature to resolve key for
     * @return the resolved key (never null)
     */
    private String resolveDefaultFeatureKey(EStructuralFeature feature) {
        // Check if ExtendedMetaData names should be used
        Boolean useExtendedMetaData = getGlobalProperty(ConfigProperty.USE_NAMES_FROM_EXTENDED_METADATA);
        if (Boolean.TRUE.equals(useExtendedMetaData)) {
            String extendedMetaDataName = getExtendedMetaDataName(feature);
            if (extendedMetaDataName != null && !extendedMetaDataName.isEmpty()) {
                return extendedMetaDataName;
            }
        }
        // Fall back to feature name
        return feature.getName();
    }

    /**
     * Gets the ExtendedMetaData name from a feature's annotation.
     * <p>
     * Looks for the annotation source "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"
     * and retrieves the "name" detail.
     * </p>
     *
     * @param feature the feature to check
     * @return the ExtendedMetaData name, or null if not present
     */
    private static String getExtendedMetaDataName(EStructuralFeature feature) {
        EAnnotation annotation = feature.getEAnnotation(
                "http:///org/eclipse/emf/ecore/util/ExtendedMetaData");
        if (annotation != null) {
            return annotation.getDetails().get("name");
        }
        return null;
    }

    /**
     * Resolves global FeatureConfig (no Feature context).
     *
     * @param diagnostics collector for validation diagnostics
     * @return the effective global FeatureConfig
     */
    public FeatureConfig resolveGlobalFeatureConfig(DiagnosticCollector diagnostics) {
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        if (globalFeatureConfig == null) {
            synchronized (this) {
                if (globalFeatureConfig == null) {
                    globalFeatureConfig = FeatureConfig.defaults()
                            .mergeWith(extractGlobalProperties(annotationProperties))
                            .mergeWith(extractGlobalProperties(moduleProperties))
                            .mergeWith(extractGlobalProperties(factoryProperties))
                            .mergeWith(extractGlobalProperties(resourceProperties))
                            .mergeWith(extractGlobalProperties(optionsProperties))
                            .validate(diagnostics);
                }
            }
        }
        return globalFeatureConfig;
    }

    // ========================================================================
    // Reference Configuration Resolution
    // ========================================================================

    /**
     * Resolves effective ReferenceConfig for an EStructuralFeature (must be EReference).
     *
     * @param feature the EReference to resolve config for
     * @param diagnostics collector for validation diagnostics
     * @return the effective ReferenceConfig (cached)
     */
    public ReferenceConfig resolveReferenceConfig(EStructuralFeature feature, DiagnosticCollector diagnostics) {
        Objects.requireNonNull(feature, "feature must not be null");
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        return referenceConfigCache.computeIfAbsent(feature, f -> {
            EClass eClass = f.getEContainingClass();
            return ReferenceConfig.defaults()
                    .mergeWith(extractGlobalProperties(annotationProperties))
                    .mergeWith(extractClassProperties(annotationProperties, eClass))
                    .mergeWith(extractFeatureProperties(annotationProperties, f))
                    .mergeWith(extractGlobalProperties(moduleProperties))
                    .mergeWith(extractClassProperties(moduleProperties, eClass))
                    .mergeWith(extractFeatureProperties(moduleProperties, f))
                    .mergeWith(extractGlobalProperties(factoryProperties))
                    .mergeWith(extractClassProperties(factoryProperties, eClass))
                    .mergeWith(extractFeatureProperties(factoryProperties, f))
                    .mergeWith(extractGlobalProperties(resourceProperties))
                    .mergeWith(extractClassProperties(resourceProperties, eClass))
                    .mergeWith(extractFeatureProperties(resourceProperties, f))
                    .mergeWith(extractGlobalProperties(optionsProperties))
                    .mergeWith(extractClassProperties(optionsProperties, eClass))
                    .mergeWith(extractFeatureProperties(optionsProperties, f))
                    .validate(diagnostics);
        });
    }

    /**
     * Resolves global ReferenceConfig (no Feature context).
     *
     * @param diagnostics collector for validation diagnostics
     * @return the effective global ReferenceConfig
     */
    public ReferenceConfig resolveGlobalReferenceConfig(DiagnosticCollector diagnostics) {
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        if (globalReferenceConfig == null) {
            synchronized (this) {
                if (globalReferenceConfig == null) {
                    globalReferenceConfig = ReferenceConfig.defaults()
                            .mergeWith(extractGlobalProperties(annotationProperties))
                            .mergeWith(extractGlobalProperties(moduleProperties))
                            .mergeWith(extractGlobalProperties(factoryProperties))
                            .mergeWith(extractGlobalProperties(resourceProperties))
                            .mergeWith(extractGlobalProperties(optionsProperties))
                            .validate(diagnostics);
                }
            }
        }
        return globalReferenceConfig;
    }

    // ========================================================================
    // Cross-Config Validation
    // ========================================================================

    /**
     * Validates cross-config constraints between TypeConfig and SuperTypeConfig.
     * <p>
     * Spec reference: 07-supertype.md section 6.0 "Configuration Constraints"
     * <p>
     * Constraints validated:
     * <ul>
     *   <li><b>STRUCTURED requires Type:</b> typeFormat=STRUCTURED + typeStrategy=NONE + superTypeSerialize=true → ERROR</li>
     *   <li><b>Custom reader conflict:</b> STRUCTURED + typeValueReaderName + superTypeValueReaderName → WARNING (superType reader ignored)</li>
     *   <li><b>Custom writer conflict:</b> STRUCTURED + typeValueWriterName + superTypeValueWriterName → WARNING (superType writer ignored)</li>
     * </ul>
     *
     * @param typeConfig the resolved TypeConfig
     * @param superTypeConfig the resolved SuperTypeConfig
     * @param diagnostics collector for validation diagnostics
     */
    public void validateCrossConfig(TypeConfig typeConfig, SuperTypeConfig superTypeConfig, DiagnosticCollector diagnostics) {
        Objects.requireNonNull(typeConfig, "typeConfig must not be null");
        Objects.requireNonNull(superTypeConfig, "superTypeConfig must not be null");
        Objects.requireNonNull(diagnostics, "diagnostics must not be null");

        // Determine effective format for supertype (inherits from type if not set)
        SerializationFormat effectiveFormat = superTypeConfig.getFormat() != null
                ? superTypeConfig.getFormat()
                : typeConfig.getFormat();

        // Constraint: STRUCTURED + NONE + superTypeSerialize=true → ERROR
        // Spec: "Cannot write supertype inside _type object when no _type is written"
        if (effectiveFormat == SerializationFormat.STRUCTURED
                && typeConfig.getStrategy() == TypeStrategy.NONE
                && superTypeConfig.isSerialize()) {
            diagnostics.addError(
                "Invalid configuration: typeFormat=STRUCTURED + typeStrategy=NONE + superTypeSerialize=true. " +
                "Cannot write supertype inside _type object when no _type is written.",
                "ConfigurationResolver.validateCrossConfig");
        }

        // Constraint: STRUCTURED + both custom readers → WARNING (superType reader ignored)
        // Spec: "In STRUCTURED format, when both are configured: superTypeValueReaderName is IGNORED"
        if (effectiveFormat == SerializationFormat.STRUCTURED
                && typeConfig.getValueReaderName() != null
                && superTypeConfig.getValueReaderName() != null) {
            diagnostics.addWarning(
                "superTypeValueReaderName is ignored when typeFormat=STRUCTURED and typeValueReaderName is set. " +
                "The type value reader handles the entire _type object including supertype.",
                "ConfigurationResolver.validateCrossConfig");
        }

        // Constraint: STRUCTURED + both custom writers → WARNING (superType writer ignored)
        // Spec: "In STRUCTURED format, when both are configured: superTypeValueWriterName is IGNORED"
        if (effectiveFormat == SerializationFormat.STRUCTURED
                && typeConfig.getValueWriterName() != null
                && superTypeConfig.getValueWriterName() != null) {
            diagnostics.addWarning(
                "superTypeValueWriterName is ignored when typeFormat=STRUCTURED and typeValueWriterName is set. " +
                "The type value writer handles the entire _type object including supertype.",
                "ConfigurationResolver.validateCrossConfig");
        }
    }

    /**
     * Resolves and validates both TypeConfig and SuperTypeConfig for an EClass.
     * <p>
     * This is a convenience method that resolves both configs and performs
     * cross-config validation in one call.
     *
     * @param eClass the EClass to resolve configs for
     * @param diagnostics collector for validation diagnostics
     * @return the resolved TypeConfig (SuperTypeConfig is resolved as side effect)
     */
    public TypeConfig resolveTypeAndSuperTypeConfig(EClass eClass, DiagnosticCollector diagnostics) {
        TypeConfig typeConfig = resolveTypeConfig(eClass, diagnostics);
        SuperTypeConfig superTypeConfig = resolveSuperTypeConfig(eClass, diagnostics);
        validateCrossConfig(typeConfig, superTypeConfig, diagnostics);
        return typeConfig;
    }

    // ========================================================================
    // Cache Management
    // ========================================================================

    /**
     * Clears all cached configurations.
     * <p>
     * Call this when underlying configuration sources have changed.
     */
    public void clearCaches() {
        typeConfigCache.clear();
        superTypeConfigCache.clear();
        idConfigCache.clear();
        discriminatorConfigCache.clear();
        featureConfigCache.clear();
        referenceConfigCache.clear();
        globalTypeConfig = null;
        globalSuperTypeConfig = null;
        globalIdConfig = null;
        globalDiscriminatorConfig = null;
        globalFeatureConfig = null;
        globalReferenceConfig = null;
    }

    // ========================================================================
    // Global Property Access
    // ========================================================================

    /**
     * Retrieves a global property value by searching all configuration sources
     * in priority order (OPTIONS → RESOURCE → FACTORY → MODULE → ANNOTATION).
     * <p>
     * Returns the first non-null value found, or the property's default value
     * if no source has it set.
     * </p>
     *
     * @param <T> the property value type
     * @param property the config property to look up
     * @return the resolved value, or the property's default
     */
    @SuppressWarnings("unchecked")
    public <T> T getGlobalProperty(ConfigProperty property) {
        // Search in priority order (highest first)
        Map<String, Object>[] sources = new Map[] {
            optionsProperties, resourceProperties, factoryProperties, moduleProperties, annotationProperties
        };
        String key = property.getKey();
        for (Map<String, Object> source : sources) {
            if (source != null && source.containsKey(key)) {
                Object value = source.get(key);
                if (value != null) {
                    return (T) value;
                }
            }
        }
        return property.getDefaultValue();
    }

    // ========================================================================
    // Property Extraction Helpers
    // ========================================================================

    /**
     * Extracts global-scoped properties from a source map.
     * <p>
     * Global properties use keys without prefix (e.g., "typeStrategy")
     * or with "codec." prefix (e.g., "codec.typeStrategy").
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> extractGlobalProperties(Map<String, Object> source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        // Look for nested "global" map first
        Object globalMap = source.get("global");
        if (globalMap instanceof Map) {
            return (Map<String, Object>) globalMap;
        }
        // Return source as-is (properties without class prefix are global)
        return source;
    }

    /**
     * Extracts EClass-scoped properties from a source map.
     * <p>
     * Class properties use keys like "Person.typeStrategy" or
     * nested map under EClass name key.
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> extractClassProperties(Map<String, Object> source, EClass eClass) {
        if (source == null || source.isEmpty() || eClass == null) {
            return null;
        }
        String className = eClass.getName();
        // Look for nested map under class name
        Object classMap = source.get(className);
        if (classMap instanceof Map) {
            return (Map<String, Object>) classMap;
        }
        return null;
    }

    /**
     * Extracts feature-scoped properties from a source map.
     * <p>
     * Feature properties use keys like "Person.firstName.key" or
     * nested map under "ClassName.featureName" key.
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> extractFeatureProperties(Map<String, Object> source, EStructuralFeature feature) {
        if (source == null || source.isEmpty() || feature == null) {
            return null;
        }
        EClass eClass = feature.getEContainingClass();
        if (eClass == null) {
            return null;
        }

        // First try: look for nested map under class name, then feature name
        Object classMap = source.get(eClass.getName());
        if (classMap instanceof Map) {
            Object featureMap = ((Map<String, Object>) classMap).get(feature.getName());
            if (featureMap instanceof Map) {
                return (Map<String, Object>) featureMap;
            }
        }

        // Second try: look for "ClassName.featureName" key
        String featureKey = eClass.getName() + "." + feature.getName();
        Object featureMap = source.get(featureKey);
        if (featureMap instanceof Map) {
            return (Map<String, Object>) featureMap;
        }

        return null;
    }

    // ========================================================================
    // Builder
    // ========================================================================

    /**
     * Creates a new builder pre-populated with this resolver's configuration.
     * <p>
     * Useful for creating a modified resolver that inherits existing settings.
     */
    public Builder toBuilder() {
        return new Builder()
                .optionsProperties(this.optionsProperties)
                .resourceProperties(this.resourceProperties)
                .factoryProperties(this.factoryProperties)
                .moduleProperties(this.moduleProperties)
                .annotationProperties(this.annotationProperties);
    }

    /**
     * Creates a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a resolver with no configuration (all defaults).
     */
    public static ConfigurationResolver defaults() {
        return builder().build();
    }

    public static final class Builder {
        private Map<String, Object> optionsProperties;
        private Map<String, Object> resourceProperties;
        private Map<String, Object> factoryProperties;
        private Map<String, Object> moduleProperties;
        private Map<String, Object> annotationProperties;

        // Convenience tracking for expand references (collected until build)
        private List<Object> expandReferences;

        private Builder() {}

        /**
         * Sets load/save options properties (highest priority).
         */
        public Builder optionsProperties(Map<String, Object> optionsProperties) {
            this.optionsProperties = optionsProperties;
            return this;
        }

        /**
         * Sets resource-level properties.
         */
        public Builder resourceProperties(Map<String, Object> resourceProperties) {
            this.resourceProperties = resourceProperties;
            return this;
        }

        /**
         * Sets factory-level properties.
         */
        public Builder factoryProperties(Map<String, Object> factoryProperties) {
            this.factoryProperties = factoryProperties;
            return this;
        }

        /**
         * Sets module-level properties.
         */
        public Builder moduleProperties(Map<String, Object> moduleProperties) {
            this.moduleProperties = moduleProperties;
            return this;
        }

        /**
         * Sets annotation-derived properties (from EAnnotations).
         */
        public Builder annotationProperties(Map<String, Object> annotationProperties) {
            this.annotationProperties = annotationProperties;
            return this;
        }

        // ====================================================================
        // Convenience Methods for Reference Expansion
        // ====================================================================

        /**
         * Enables global expansion of all non-containment references.
         * <p>
         * When enabled, all non-containment references are serialized inline
         * instead of as proxy references.
         *
         * @param expandGlobal true to expand all references, false otherwise
         * @return this builder
         * @see ConfigProperty#EXPAND_GLOBAL
         */
        public Builder expandGlobal(boolean expandGlobal) {
            ensureResourceProperties();
            resourceProperties.put(ConfigProperty.EXPAND_GLOBAL.getKey(), expandGlobal);
            return this;
        }

        /**
         * Adds specific EReferences to expand inline.
         * <p>
         * Only the specified references will be expanded; others remain as proxies.
         * Can be called multiple times to accumulate references.
         *
         * @param references the EReferences to expand
         * @return this builder
         * @see ConfigProperty#EXPAND
         */
        public Builder expand(EReference... references) {
            if (references != null) {
                ensureExpandReferences();
                for (EReference ref : references) {
                    if (ref != null) {
                        expandReferences.add(ref);
                    }
                }
            }
            return this;
        }

        /**
         * Adds specific reference names to expand inline.
         * <p>
         * The names are resolved against the EClass at runtime.
         * Can be called multiple times to accumulate references.
         *
         * @param referenceNames the reference names to expand
         * @return this builder
         * @see ConfigProperty#EXPAND
         */
        public Builder expand(String... referenceNames) {
            if (referenceNames != null) {
                ensureExpandReferences();
                for (String name : referenceNames) {
                    if (name != null && !name.isEmpty()) {
                        expandReferences.add(name);
                    }
                }
            }
            return this;
        }

        /**
         * Sets the maximum depth for nested expansion.
         * <p>
         * Currently only depth=1 is supported. Higher values produce a warning.
         *
         * @param depth the maximum expansion depth
         * @return this builder
         * @see ConfigProperty#EXPAND_DEPTH
         */
        public Builder expandDepth(int depth) {
            ensureResourceProperties();
            resourceProperties.put(ConfigProperty.EXPAND_DEPTH.getKey(), depth);
            return this;
        }

        /**
         * Controls whether bidirectional (opposite) references are skipped during expansion.
         * <p>
         * Default is true (skip) to prevent cycles.
         *
         * @param ignore true to skip bidirectional references, false to include
         * @return this builder
         * @see ConfigProperty#EXPAND_IGNORE_BIDIRECTIONAL
         */
        public Builder expandIgnoreBidirectional(boolean ignore) {
            ensureResourceProperties();
            resourceProperties.put(ConfigProperty.EXPAND_IGNORE_BIDIRECTIONAL.getKey(), ignore);
            return this;
        }

        // ====================================================================
        // Convenience Methods for Type Configuration
        // ====================================================================

        /**
         * Sets the type serialization strategy.
         *
         * @param strategy the type strategy (URI, NAME, NONE, etc.)
         * @return this builder
         * @see ConfigProperty#TYPE_STRATEGY
         */
        public Builder typeStrategy(TypeStrategy strategy) {
            ensureResourceProperties();
            resourceProperties.put(ConfigProperty.TYPE_STRATEGY.getKey(), strategy.name());
            return this;
        }

        /**
         * Sets the JSON key for type information.
         *
         * @param key the type key (default: "_type")
         * @return this builder
         * @see ConfigProperty#TYPE_KEY
         */
        public Builder typeKey(String key) {
            ensureResourceProperties();
            resourceProperties.put(ConfigProperty.TYPE_KEY.getKey(), key);
            return this;
        }

        /**
         * Sets whether to include type information.
         *
         * @param include true to include type, false to omit
         * @return this builder
         * @see ConfigProperty#TYPE_INCLUDE
         */
        public Builder typeInclude(boolean include) {
            ensureResourceProperties();
            resourceProperties.put(ConfigProperty.TYPE_INCLUDE.getKey(), include);
            return this;
        }

        // ====================================================================
        // Convenience Methods for ID Configuration
        // ====================================================================

        /**
         * Sets the JSON key for ID information.
         *
         * @param key the ID key (default: "_id")
         * @return this builder
         * @see ConfigProperty#ID_KEY
         */
        public Builder idKey(String key) {
            ensureResourceProperties();
            resourceProperties.put(ConfigProperty.ID_KEY.getKey(), key);
            return this;
        }

        // ====================================================================
        // Convenience Methods for Feature Configuration
        // ====================================================================

        /**
         * Enables use of ExtendedMetaData names for JSON keys.
         *
         * @param use true to use ExtendedMetaData names, false to use feature names
         * @return this builder
         * @see ConfigProperty#USE_NAMES_FROM_EXTENDED_METADATA
         */
        public Builder useNamesFromExtendedMetaData(boolean use) {
            ensureResourceProperties();
            resourceProperties.put(ConfigProperty.USE_NAMES_FROM_EXTENDED_METADATA.getKey(), use);
            return this;
        }

        // ====================================================================
        // Helper Methods
        // ====================================================================

        private void ensureResourceProperties() {
            if (resourceProperties == null) {
                resourceProperties = new HashMap<>();
            }
        }

        private void ensureExpandReferences() {
            if (expandReferences == null) {
                expandReferences = new ArrayList<>();
            }
        }

        /**
         * Builds the resolver.
         */
        public ConfigurationResolver build() {
            // Apply collected expand references to resource properties
            if (expandReferences != null && !expandReferences.isEmpty()) {
                ensureResourceProperties();
                resourceProperties.put(ConfigProperty.EXPAND.getKey(), new ArrayList<>(expandReferences));
            }
            return new ConfigurationResolver(this);
        }
    }
}
