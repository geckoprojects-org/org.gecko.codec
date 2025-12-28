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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.FeatureAspect;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Aspect Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Extension point for contributing aspects to model metadata.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.fennec.model.metadata.api.ApiPackage#getAspectProvider()
 * @model interface="true" abstract="true"
 * @generated
 */
@ProviderType
public interface AspectProvider {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the unique identifier for this aspect type (e.g., 'codec', 'orm').
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getAspectTypeId();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Build a ClassAspect for the given EClass. Returns null if not applicable.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	ClassAspect buildClassAspect(EClass eClass);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Build a FeatureAspect for the given EStructuralFeature. Returns null if not applicable.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FeatureAspect buildFeatureAspect(EStructuralFeature feature);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Build a FeatureAspect for an EAttribute. Override for attribute-specific logic.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FeatureAspect buildAttributeAspect(EAttribute attribute);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Build a FeatureAspect for an EReference. Override for reference-specific logic.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FeatureAspect buildReferenceAspect(EReference reference);

} // AspectProvider
