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
package org.eclipse.fennec.model.metadata.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.model.metadata.AttributeMetadata;
import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.ClassMetadata;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.FeatureMetadata;
import org.eclipse.fennec.model.metadata.MetadataFactory;
import org.eclipse.fennec.model.metadata.MetadataRegistry;
import org.eclipse.fennec.model.metadata.PackageMetadata;
import org.eclipse.fennec.model.metadata.ReferenceMetadata;
import org.eclipse.fennec.model.metadata.api.AspectProvider;
import org.eclipse.fennec.model.metadata.api.MetadataService;

/**
 * Default implementation of the {@link MetadataService}.
 * <p>
 * This service maintains a registry of pre-computed metadata for registered EPackages.
 * When an EPackage is registered, metadata is built for all its classes and features,
 * and all registered AspectProviders are called to contribute their aspects.
 * </p>
 *
 * @author Mark Hoffmann
 * @since 2025-12-09
 */
public class MetadataServiceImpl implements MetadataService {

    private final MetadataRegistry registry;
    private final List<AspectProvider> aspectProviders = new CopyOnWriteArrayList<>();

    // Fast lookup maps
    private final Map<String, PackageMetadata> packagesByNsURI = new ConcurrentHashMap<>();
    private final Map<EClass, ClassMetadata> classesByEClass = new ConcurrentHashMap<>();
    private final Map<String, ClassMetadata> classesByURI = new ConcurrentHashMap<>();
    private final Map<EStructuralFeature, FeatureMetadata> featuresByEFeature = new ConcurrentHashMap<>();
    private final Map<String, FeatureMetadata> featuresByURI = new ConcurrentHashMap<>();

    /**
     * Creates a new MetadataServiceImpl with an empty registry.
     */
    public MetadataServiceImpl() {
        this.registry = MetadataFactory.eINSTANCE.createMetadataRegistry();
    }

    /**
     * Creates a new MetadataServiceImpl with the given registry.
     * Use this constructor to load a pre-computed registry.
     *
     * @param registry the pre-computed registry
     */
    public MetadataServiceImpl(MetadataRegistry registry) {
        this.registry = registry;
        // Rebuild lookup maps from registry
        rebuildLookupMaps();
    }

    @Override
    public PackageMetadata registerPackage(EPackage ePackage) {
        if (ePackage == null) {
            return null;
        }

        String nsURI = ePackage.getNsURI();

        // Check if already registered
        PackageMetadata existing = packagesByNsURI.get(nsURI);
        if (existing != null) {
            return existing;
        }

        // Create package metadata
        PackageMetadata pkgMetadata = MetadataFactory.eINSTANCE.createPackageMetadata();
        pkgMetadata.setEPackage(ePackage);
        pkgMetadata.setNsURI(nsURI);

        // Process all EClasses
        for (EClassifier classifier : ePackage.getEClassifiers()) {
            if (classifier instanceof EClass eClass) {
                ClassMetadata classMetadata = buildClassMetadata(eClass, pkgMetadata);
                pkgMetadata.getClasses().add(classMetadata);
            }
        }

        // Resolve cross-references (supertypes, target classes)
        resolveReferences(pkgMetadata);

        // Add to registry and lookup maps
        registry.getPackages().add(pkgMetadata);
        packagesByNsURI.put(nsURI, pkgMetadata);

        return pkgMetadata;
    }

    @Override
    public void unregisterPackage(EPackage ePackage) {
        if (ePackage == null) {
            return;
        }

        String nsURI = ePackage.getNsURI();
        PackageMetadata pkgMetadata = packagesByNsURI.remove(nsURI);

        if (pkgMetadata != null) {
            // Remove from lookup maps
            for (ClassMetadata classMetadata : pkgMetadata.getClasses()) {
                EClass eClass = classMetadata.getEClass();
                if (eClass != null) {
                    classesByEClass.remove(eClass);
                    classesByURI.remove(classMetadata.getTypeURI());
                }

                for (FeatureMetadata featureMetadata : classMetadata.getFeatures()) {
                    EStructuralFeature feature = featureMetadata.getEFeature();
                    if (feature != null) {
                        featuresByEFeature.remove(feature);
                        featuresByURI.remove(buildFeatureURI(feature));
                    }
                }
            }

            // Remove from registry
            registry.getPackages().remove(pkgMetadata);
        }
    }

    @Override
    public PackageMetadata getPackageMetadata(String nsURI) {
        return packagesByNsURI.get(nsURI);
    }

    @Override
    public ClassMetadata getClassMetadata(EClass eClass) {
        if (eClass == null) {
            return null;
        }
        return classesByEClass.get(eClass);
    }

    @Override
    public ClassMetadata getClassMetadataByURI(String uri) {
        return classesByURI.get(uri);
    }

    @Override
    public ClassMetadata getClassMetadataByName(String className, String nsURI) {
        PackageMetadata pkgMetadata = packagesByNsURI.get(nsURI);
        if (pkgMetadata != null) {
            for (ClassMetadata classMetadata : pkgMetadata.getClasses()) {
                if (className.equals(classMetadata.getName())) {
                    return classMetadata;
                }
            }
        }
        return null;
    }

    @Override
    public FeatureMetadata getFeatureMetadata(EStructuralFeature feature) {
        if (feature == null) {
            return null;
        }
        return featuresByEFeature.get(feature);
    }

    @Override
    public FeatureMetadata getFeatureMetadataByURI(String uri) {
        return featuresByURI.get(uri);
    }

    @Override
    public FeatureMetadata getFeatureMetadataByName(String featureName, String className, String nsURI) {
        ClassMetadata classMetadata = getClassMetadataByName(className, nsURI);
        if (classMetadata != null) {
            return getFeatureMetadataFromClass(featureName, classMetadata);
        }
        return null;
    }

    @Override
    public FeatureMetadata getFeatureMetadataFromClass(String featureName, ClassMetadata classMetadata) {
        if (classMetadata == null || featureName == null) {
            return null;
        }
        for (FeatureMetadata featureMetadata : classMetadata.getFeatures()) {
            if (featureName.equals(featureMetadata.getName())) {
                return featureMetadata;
            }
        }
        return null;
    }

    @Override
    public ClassAspect getClassAspect(EClass eClass, String aspectTypeId) {
        ClassMetadata classMetadata = getClassMetadata(eClass);
        if (classMetadata != null) {
            for (ClassAspect aspect : classMetadata.getAspects()) {
                if (aspectTypeId.equals(aspect.getTypeId())) {
                    return aspect;
                }
            }
        }
        return null;
    }

    @Override
    public FeatureAspect getFeatureAspect(EStructuralFeature feature, String aspectTypeId) {
        FeatureMetadata featureMetadata = getFeatureMetadata(feature);
        if (featureMetadata != null) {
            for (FeatureAspect aspect : featureMetadata.getAspects()) {
                if (aspectTypeId.equals(aspect.getTypeId())) {
                    return aspect;
                }
            }
        }
        return null;
    }

    @Override
    public MetadataRegistry getRegistry() {
        return registry;
    }

    @Override
    public void registerAspectProvider(AspectProvider provider) {
        if (provider != null && !aspectProviders.contains(provider)) {
            aspectProviders.add(provider);

            // Apply provider to all existing metadata
            for (PackageMetadata pkgMetadata : registry.getPackages()) {
                applyProviderToPackage(provider, pkgMetadata);
            }
        }
    }

    @Override
    public void unregisterAspectProvider(AspectProvider provider) {
        if (provider != null) {
            aspectProviders.remove(provider);

            // Remove aspects from this provider
            String typeId = provider.getAspectTypeId();
            for (PackageMetadata pkgMetadata : registry.getPackages()) {
                removeAspectsFromPackage(typeId, pkgMetadata);
            }
        }
    }

    @Override
    public EList<AspectProvider> getAspectProviders() {
        return new BasicEList<>(aspectProviders);
    }

    // ========================================================================
    // Private helper methods
    // ========================================================================

    private ClassMetadata buildClassMetadata(EClass eClass, PackageMetadata pkgMetadata) {
        ClassMetadata classMetadata = MetadataFactory.eINSTANCE.createClassMetadata();
        classMetadata.setEClass(eClass);
        classMetadata.setName(eClass.getName());
        classMetadata.setClassifierID(eClass.getClassifierID());
        classMetadata.setTypeURI(EcoreUtil.getURI(eClass).toString());

        // Determine if class has ID
        EAttribute idAttribute = eClass.getEIDAttribute();
        classMetadata.setHasId(idAttribute != null);

        // Process features
        for (EStructuralFeature feature : eClass.getEStructuralFeatures()) {
            FeatureMetadata featureMetadata = buildFeatureMetadata(feature, classMetadata);
            classMetadata.getFeatures().add(featureMetadata);

            // Track ID features
            if (feature instanceof EAttribute attr && attr.isID()) {
                classMetadata.getIdFeatures().add(featureMetadata);
            }
        }

        // Add to lookup maps
        classesByEClass.put(eClass, classMetadata);
        classesByURI.put(classMetadata.getTypeURI(), classMetadata);

        // Apply all aspect providers
        for (AspectProvider provider : aspectProviders) {
            ClassAspect aspect = provider.buildClassAspect(eClass);
            if (aspect != null) {
                aspect.setTypeId(provider.getAspectTypeId());
                classMetadata.getAspects().add(aspect);
            }
        }

        return classMetadata;
    }

    private FeatureMetadata buildFeatureMetadata(EStructuralFeature feature, ClassMetadata classMetadata) {
        FeatureMetadata featureMetadata;

        if (feature instanceof EAttribute attr) {
            AttributeMetadata attrMetadata = MetadataFactory.eINSTANCE.createAttributeMetadata();
            attrMetadata.setEAttribute(attr);
            attrMetadata.setIsId(attr.isID());
            attrMetadata.setDefaultValue(attr.getDefaultValue());
            featureMetadata = attrMetadata;
        } else if (feature instanceof EReference ref) {
            ReferenceMetadata refMetadata = MetadataFactory.eINSTANCE.createReferenceMetadata();
            refMetadata.setEReference(ref);
            refMetadata.setContainment(ref.isContainment());
            refMetadata.setHasBidirectional(ref.getEOpposite() != null);
            featureMetadata = refMetadata;
        } else {
            // Should not happen, but handle gracefully
            return null;
        }

        // Common properties
        featureMetadata.setEFeature(feature);
        featureMetadata.setName(feature.getName());
        featureMetadata.setFeatureID(feature.getFeatureID());

        // Add to lookup maps
        String featureURI = buildFeatureURI(feature);
        featuresByEFeature.put(feature, featureMetadata);
        featuresByURI.put(featureURI, featureMetadata);

        // Apply all aspect providers
        for (AspectProvider provider : aspectProviders) {
            FeatureAspect aspect;
            if (feature instanceof EAttribute attr) {
                aspect = provider.buildAttributeAspect(attr);
            } else if (feature instanceof EReference ref) {
                aspect = provider.buildReferenceAspect(ref);
            } else {
                aspect = provider.buildFeatureAspect(feature);
            }

            if (aspect != null) {
                aspect.setTypeId(provider.getAspectTypeId());
                featureMetadata.getAspects().add(aspect);
            }
        }

        return featureMetadata;
    }

    private void resolveReferences(PackageMetadata pkgMetadata) {
        for (ClassMetadata classMetadata : pkgMetadata.getClasses()) {
            EClass eClass = classMetadata.getEClass();

            // Resolve supertypes
            for (EClass superType : eClass.getESuperTypes()) {
                ClassMetadata superMetadata = classesByEClass.get(superType);
                if (superMetadata != null) {
                    classMetadata.getSuperTypes().add(superMetadata);
                }
            }

            // Resolve all supertypes (transitive)
            for (EClass superType : eClass.getEAllSuperTypes()) {
                ClassMetadata superMetadata = classesByEClass.get(superType);
                if (superMetadata != null) {
                    classMetadata.getAllSuperTypes().add(superMetadata);
                }
            }

            // Resolve reference targets
            for (FeatureMetadata featureMetadata : classMetadata.getFeatures()) {
                if (featureMetadata instanceof ReferenceMetadata refMetadata) {
                    EReference ref = refMetadata.getEReference();

                    // Target class
                    EClass targetClass = ref.getEReferenceType();
                    ClassMetadata targetMetadata = classesByEClass.get(targetClass);
                    refMetadata.setTargetClassMetadata(targetMetadata);

                    // Opposite reference
                    EReference opposite = ref.getEOpposite();
                    if (opposite != null) {
                        FeatureMetadata oppositeMetadata = featuresByEFeature.get(opposite);
                        if (oppositeMetadata instanceof ReferenceMetadata oppRefMetadata) {
                            refMetadata.setOppositeMetadata(oppRefMetadata);
                        }
                    }
                }
            }
        }
    }

    private void applyProviderToPackage(AspectProvider provider, PackageMetadata pkgMetadata) {
        for (ClassMetadata classMetadata : pkgMetadata.getClasses()) {
            // Build class aspect
            ClassAspect classAspect = provider.buildClassAspect(classMetadata.getEClass());
            if (classAspect != null) {
                classAspect.setTypeId(provider.getAspectTypeId());
                classMetadata.getAspects().add(classAspect);
            }

            // Build feature aspects
            for (FeatureMetadata featureMetadata : classMetadata.getFeatures()) {
                FeatureAspect featureAspect;
                EStructuralFeature feature = featureMetadata.getEFeature();

                if (feature instanceof EAttribute attr) {
                    featureAspect = provider.buildAttributeAspect(attr);
                } else if (feature instanceof EReference ref) {
                    featureAspect = provider.buildReferenceAspect(ref);
                } else {
                    featureAspect = provider.buildFeatureAspect(feature);
                }

                if (featureAspect != null) {
                    featureAspect.setTypeId(provider.getAspectTypeId());
                    featureMetadata.getAspects().add(featureAspect);
                }
            }
        }
    }

    private void removeAspectsFromPackage(String typeId, PackageMetadata pkgMetadata) {
        for (ClassMetadata classMetadata : pkgMetadata.getClasses()) {
            classMetadata.getAspects().removeIf(a -> typeId.equals(a.getTypeId()));

            for (FeatureMetadata featureMetadata : classMetadata.getFeatures()) {
                featureMetadata.getAspects().removeIf(a -> typeId.equals(a.getTypeId()));
            }
        }
    }

    private void rebuildLookupMaps() {
        packagesByNsURI.clear();
        classesByEClass.clear();
        classesByURI.clear();
        featuresByEFeature.clear();
        featuresByURI.clear();

        for (PackageMetadata pkgMetadata : registry.getPackages()) {
            packagesByNsURI.put(pkgMetadata.getNsURI(), pkgMetadata);

            for (ClassMetadata classMetadata : pkgMetadata.getClasses()) {
                EClass eClass = classMetadata.getEClass();
                if (eClass != null) {
                    classesByEClass.put(eClass, classMetadata);
                }
                String typeURI = classMetadata.getTypeURI();
                if (typeURI != null) {
                    classesByURI.put(typeURI, classMetadata);
                }

                for (FeatureMetadata featureMetadata : classMetadata.getFeatures()) {
                    EStructuralFeature feature = featureMetadata.getEFeature();
                    if (feature != null) {
                        featuresByEFeature.put(feature, featureMetadata);
                        featuresByURI.put(buildFeatureURI(feature), featureMetadata);
                    }
                }
            }
        }
    }

    private String buildFeatureURI(EStructuralFeature feature) {
        return EcoreUtil.getURI(feature).toString();
    }
}
