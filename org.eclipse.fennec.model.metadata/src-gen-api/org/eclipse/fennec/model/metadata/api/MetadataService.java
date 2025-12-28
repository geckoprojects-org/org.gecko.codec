/*
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
 * 
 */
package org.eclipse.fennec.model.metadata.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.ClassMetadata;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.FeatureMetadata;
import org.eclipse.fennec.model.metadata.MetadataRegistry;
import org.eclipse.fennec.model.metadata.PackageMetadata;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metadata Service</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Main service interface for accessing pre-computed model metadata.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.fennec.model.metadata.api.ApiPackage#getMetadataService()
 * @model interface="true" abstract="true"
 * @generated
 */
@ProviderType
public interface MetadataService {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Register an EPackage and build metadata for all its classes.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	PackageMetadata registerPackage(EPackage ePackage);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unregister an EPackage and remove all associated metadata.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	void unregisterPackage(EPackage ePackage);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get metadata for a package by namespace URI.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	PackageMetadata getPackageMetadata(String nsURI);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get metadata for an EClass.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	ClassMetadata getClassMetadata(EClass eClass);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get metadata for an EClass by its full URI.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	ClassMetadata getClassMetadataByURI(String uri);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get metadata for an EClass by name and package namespace URI.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	ClassMetadata getClassMetadataByName(String className, String nsURI);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get metadata for an EStructuralFeature.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FeatureMetadata getFeatureMetadata(EStructuralFeature feature);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get metadata for an EStructuralFeature by its full URI.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FeatureMetadata getFeatureMetadataByURI(String uri);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get metadata for an EStructuralFeature by feature name, class name, and package namespace URI.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FeatureMetadata getFeatureMetadataByName(String featureName, String className, String nsURI);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get metadata for an EStructuralFeature by feature name and ClassMetadata.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FeatureMetadata getFeatureMetadataFromClass(String featureName, ClassMetadata classMetadata);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get a specific aspect from an EClass by aspect type ID.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	ClassAspect getClassAspect(EClass eClass, String aspectTypeId);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get a specific aspect from an EStructuralFeature by aspect type ID.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FeatureAspect getFeatureAspect(EStructuralFeature feature, String aspectTypeId);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get the full metadata registry for serialization/caching.
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	MetadataRegistry getRegistry();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Register an AspectProvider to contribute aspects when packages are registered.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	void registerAspectProvider(AspectProvider provider);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unregister an AspectProvider.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	void unregisterAspectProvider(AspectProvider provider);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get all registered AspectProviders.
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<AspectProvider> getAspectProviders();

} // MetadataService
