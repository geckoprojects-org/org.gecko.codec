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

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Super Type Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base configuration for supertype serialization.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getSelection <em>Selection</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getFormat <em>Format</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getSuperTypeKey <em>Super Type Key</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getSchemaKey <em>Schema Key</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getNameKey <em>Name Key</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseSuperTypeConfig()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface BaseSuperTypeConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Enable supertype serialization.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseSuperTypeConfig_Enabled()
	 * @model default="false"
	 * @generated
	 */
	boolean isEnabled();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
	void setEnabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Selection</b></em>' attribute.
	 * The default value is <code>"ALL"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.metadata.SuperTypeSelection}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Which supertypes to include.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Selection</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.SuperTypeSelection
	 * @see #setSelection(SuperTypeSelection)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseSuperTypeConfig_Selection()
	 * @model default="ALL"
	 * @generated
	 */
	SuperTypeSelection getSelection();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getSelection <em>Selection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selection</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.SuperTypeSelection
	 * @see #getSelection()
	 * @generated
	 */
	void setSelection(SuperTypeSelection value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * The default value is <code>"PLAIN"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.metadata.SerializationFormat}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Serialization format for supertype entries.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.SerializationFormat
	 * @see #setFormat(SerializationFormat)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseSuperTypeConfig_Format()
	 * @model default="PLAIN"
	 * @generated
	 */
	SerializationFormat getFormat();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.SerializationFormat
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(SerializationFormat value);

	/**
	 * Returns the value of the '<em><b>Super Type Key</b></em>' attribute.
	 * The default value is <code>"_supertype"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * JSON property name for supertype information.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Super Type Key</em>' attribute.
	 * @see #setSuperTypeKey(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseSuperTypeConfig_SuperTypeKey()
	 * @model default="_supertype"
	 * @generated
	 */
	String getSuperTypeKey();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getSuperTypeKey <em>Super Type Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type Key</em>' attribute.
	 * @see #getSuperTypeKey()
	 * @generated
	 */
	void setSuperTypeKey(String value);

	/**
	 * Returns the value of the '<em><b>Schema Key</b></em>' attribute.
	 * The default value is <code>"schema"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Key for schema in STRUCTURED format.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Schema Key</em>' attribute.
	 * @see #setSchemaKey(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseSuperTypeConfig_SchemaKey()
	 * @model default="schema"
	 * @generated
	 */
	String getSchemaKey();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getSchemaKey <em>Schema Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema Key</em>' attribute.
	 * @see #getSchemaKey()
	 * @generated
	 */
	void setSchemaKey(String value);

	/**
	 * Returns the value of the '<em><b>Name Key</b></em>' attribute.
	 * The default value is <code>"name"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Key for name in STRUCTURED format.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name Key</em>' attribute.
	 * @see #setNameKey(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseSuperTypeConfig_NameKey()
	 * @model default="name"
	 * @generated
	 */
	String getNameKey();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseSuperTypeConfig#getNameKey <em>Name Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Key</em>' attribute.
	 * @see #getNameKey()
	 * @generated
	 */
	void setNameKey(String value);

} // BaseSuperTypeConfig
