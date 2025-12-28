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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Metadata</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Pre-computed metadata for an EClass. Contains resolved serialization configuration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getPackage <em>Package</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getEClass <em>EClass</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getClassifierID <em>Classifier ID</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getTypeURI <em>Type URI</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getFeatures <em>Features</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getSuperTypes <em>Super Types</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getAllSuperTypes <em>All Super Types</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getIdFeatures <em>Id Features</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#isHasId <em>Has Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.ClassMetadata#getAspects <em>Aspects</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata()
 * @model
 * @generated
 */
@ProviderType
public interface ClassMetadata extends EObject {
	/**
	 * Returns the value of the '<em><b>Package</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.metadata.PackageMetadata#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The parent package metadata.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Package</em>' container reference.
	 * @see #setPackage(PackageMetadata)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_Package()
	 * @see org.eclipse.fennec.model.metadata.PackageMetadata#getClasses
	 * @model opposite="classes" transient="false"
	 * @generated
	 */
	PackageMetadata getPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.ClassMetadata#getPackage <em>Package</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' container reference.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(PackageMetadata value);

	/**
	 * Returns the value of the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The EClass this metadata describes.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>EClass</em>' reference.
	 * @see #setEClass(EClass)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_EClass()
	 * @model
	 * @generated
	 */
	EClass getEClass();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.ClassMetadata#getEClass <em>EClass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EClass</em>' reference.
	 * @see #getEClass()
	 * @generated
	 */
	void setEClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Cached class name for fast lookup.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.ClassMetadata#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Classifier ID</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Cached classifier ID for numeric serialization.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Classifier ID</em>' attribute.
	 * @see #setClassifierID(int)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_ClassifierID()
	 * @model default="-1"
	 * @generated
	 */
	int getClassifierID();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.ClassMetadata#getClassifierID <em>Classifier ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classifier ID</em>' attribute.
	 * @see #getClassifierID()
	 * @generated
	 */
	void setClassifierID(int value);

	/**
	 * Returns the value of the '<em><b>Type URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Pre-computed full type URI (nsURI#//className).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type URI</em>' attribute.
	 * @see #setTypeURI(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_TypeURI()
	 * @model
	 * @generated
	 */
	String getTypeURI();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.ClassMetadata#getTypeURI <em>Type URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type URI</em>' attribute.
	 * @see #getTypeURI()
	 * @generated
	 */
	void setTypeURI(String value);

	/**
	 * Returns the value of the '<em><b>Features</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.metadata.FeatureMetadata}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fennec.model.metadata.FeatureMetadata#getClassMetadata <em>Class Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Metadata for all EStructuralFeatures of this class.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Features</em>' containment reference list.
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_Features()
	 * @see org.eclipse.fennec.model.metadata.FeatureMetadata#getClassMetadata
	 * @model opposite="classMetadata" containment="true"
	 * @generated
	 */
	EList<FeatureMetadata> getFeatures();

	/**
	 * Returns the value of the '<em><b>Super Types</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.metadata.ClassMetadata}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Pre-resolved direct supertype metadata.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Super Types</em>' reference list.
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_SuperTypes()
	 * @model
	 * @generated
	 */
	EList<ClassMetadata> getSuperTypes();

	/**
	 * Returns the value of the '<em><b>All Super Types</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.metadata.ClassMetadata}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * All supertypes in hierarchy (transitive closure).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>All Super Types</em>' reference list.
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_AllSuperTypes()
	 * @model
	 * @generated
	 */
	EList<ClassMetadata> getAllSuperTypes();

	/**
	 * Returns the value of the '<em><b>Id Features</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.metadata.FeatureMetadata}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Pre-resolved features that form the ID (in order).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id Features</em>' reference list.
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_IdFeatures()
	 * @model
	 * @generated
	 */
	EList<FeatureMetadata> getIdFeatures();

	/**
	 * Returns the value of the '<em><b>Has Id</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this class has ID features.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Has Id</em>' attribute.
	 * @see #setHasId(boolean)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_HasId()
	 * @model default="false"
	 * @generated
	 */
	boolean isHasId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.ClassMetadata#isHasId <em>Has Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Id</em>' attribute.
	 * @see #isHasId()
	 * @generated
	 */
	void setHasId(boolean value);

	/**
	 * Returns the value of the '<em><b>Aspects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.model.metadata.ClassAspect}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Aspects attached to this class (codec, ORM, etc.).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Aspects</em>' containment reference list.
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getClassMetadata_Aspects()
	 * @model containment="true"
	 * @generated
	 */
	EList<ClassAspect> getAspects();

} // ClassMetadata
