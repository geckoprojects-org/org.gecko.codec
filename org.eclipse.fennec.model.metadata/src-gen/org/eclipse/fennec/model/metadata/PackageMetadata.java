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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Metadata</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Pre-computed metadata for an EPackage. Contains resolved configuration and metadata for all classes in the package.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.PackageMetadata#getEPackage <em>EPackage</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.PackageMetadata#getNsURI <em>Ns URI</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.PackageMetadata#getClasses <em>Classes</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.PackageMetadata#getAspects <em>Aspects</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getPackageMetadata()
 * @model
 * @generated
 */
@ProviderType
public interface PackageMetadata extends DiagnosticContainer {
	/**
	 * Returns the value of the '<em><b>EPackage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The EPackage this metadata describes.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>EPackage</em>' reference.
	 * @see #setEPackage(EPackage)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getPackageMetadata_EPackage()
	 * @model
	 * @generated
	 */
	EPackage getEPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.PackageMetadata#getEPackage <em>EPackage</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EPackage</em>' reference.
	 * @see #getEPackage()
	 * @generated
	 */
	void setEPackage(EPackage value);

	/**
	 * Returns the value of the '<em><b>Ns URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Cached namespace URI for fast lookup.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ns URI</em>' attribute.
	 * @see #setNsURI(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getPackageMetadata_NsURI()
	 * @model
	 * @generated
	 */
	String getNsURI();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.PackageMetadata#getNsURI <em>Ns URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ns URI</em>' attribute.
	 * @see #getNsURI()
	 * @generated
	 */
	void setNsURI(String value);

	/**
	 * Returns the value of the '<em><b>Classes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.metadata.ClassMetadata}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.metadata.ClassMetadata#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Metadata for all EClasses in this package.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Classes</em>' containment reference list.
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getPackageMetadata_Classes()
	 * @see org.eclipse.fennec.model.metadata.ClassMetadata#getPackage
	 * @model opposite="package" containment="true"
	 * @generated
	 */
	EList<ClassMetadata> getClasses();

	/**
	 * Returns the value of the '<em><b>Aspects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.metadata.PackageAspect}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Aspects attached to this package (codec, ORM, etc.).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Aspects</em>' containment reference list.
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getPackageMetadata_Aspects()
	 * @model containment="true"
	 * @generated
	 */
	EList<PackageAspect> getAspects();

} // PackageMetadata
