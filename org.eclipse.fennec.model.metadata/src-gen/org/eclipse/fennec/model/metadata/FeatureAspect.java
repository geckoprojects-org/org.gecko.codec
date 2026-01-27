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
 * 
 */
package org.eclipse.fennec.model.metadata;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Aspect</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for aspects that attach to FeatureMetadata.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.FeatureAspect#getEFeature <em>EFeature</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getFeatureAspect()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface FeatureAspect extends Aspect {
	/**
	 * Returns the value of the '<em><b>EFeature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The EStructuralFeature this aspect was built from.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>EFeature</em>' reference.
	 * @see #setEFeature(EStructuralFeature)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getFeatureAspect_EFeature()
	 * @model
	 * @generated
	 */
	EStructuralFeature getEFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.FeatureAspect#getEFeature <em>EFeature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EFeature</em>' reference.
	 * @see #getEFeature()
	 * @generated
	 */
	void setEFeature(EStructuralFeature value);

} // FeatureAspect
