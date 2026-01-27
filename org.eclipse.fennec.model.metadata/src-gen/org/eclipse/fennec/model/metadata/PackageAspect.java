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

import org.eclipse.emf.ecore.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Aspect</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for aspects that attach to PackageMetadata.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.PackageAspect#getEPackage <em>EPackage</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getPackageAspect()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface PackageAspect extends Aspect {
	/**
	 * Returns the value of the '<em><b>EPackage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The EPackage this aspect was built from.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>EPackage</em>' reference.
	 * @see #setEPackage(EPackage)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getPackageAspect_EPackage()
	 * @model
	 * @generated
	 */
	EPackage getEPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.PackageAspect#getEPackage <em>EPackage</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EPackage</em>' reference.
	 * @see #getEPackage()
	 * @generated
	 */
	void setEPackage(EPackage value);

} // PackageAspect
