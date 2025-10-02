/*
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#getFormat <em>Format</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#getItems <em>Items</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#isUniqueItems <em>Unique Items</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#getEnum <em>Enum</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#getRef <em>Ref</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#getDefault <em>Default</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Property#getAdditionalProperties <em>Additional Properties</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty()
 * @model
 * @generated
 */
@ProviderType
public interface Property extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Property#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see #setFormat(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_Format()
	 * @model
	 * @generated
	 */
	String getFormat();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Property#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(String value);

	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference.
	 * @see #setItems(Property)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_Items()
	 * @model containment="true"
	 * @generated
	 */
	Property getItems();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Property#getItems <em>Items</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Items</em>' containment reference.
	 * @see #getItems()
	 * @generated
	 */
	void setItems(Property value);

	/**
	 * Returns the value of the '<em><b>Unique Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Items</em>' attribute.
	 * @see #setUniqueItems(boolean)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_UniqueItems()
	 * @model
	 * @generated
	 */
	boolean isUniqueItems();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Property#isUniqueItems <em>Unique Items</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Items</em>' attribute.
	 * @see #isUniqueItems()
	 * @generated
	 */
	void setUniqueItems(boolean value);

	/**
	 * Returns the value of the '<em><b>Enum</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum</em>' attribute list.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_Enum()
	 * @model
	 * @generated
	 */
	EList<String> getEnum();

	/**
	 * Returns the value of the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref</em>' attribute.
	 * @see #setRef(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_Ref()
	 * @model annotation="JsonProperty value='$ref'"
	 * @generated
	 */
	String getRef();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Property#getRef <em>Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ref</em>' attribute.
	 * @see #getRef()
	 * @generated
	 */
	void setRef(String value);

	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see #setDefault(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_Default()
	 * @model
	 * @generated
	 */
	String getDefault();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Property#getDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' attribute.
	 * @see #getDefault()
	 * @generated
	 */
	void setDefault(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.fennec.openapi.model.Property},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' map.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_Properties()
	 * @model mapType="org.eclipse.fennec.openapi.model.StringToPropertyMap&lt;org.eclipse.emf.ecore.EString, org.eclipse.fennec.openapi.model.Property&gt;"
	 * @generated
	 */
	EMap<String, Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Additional Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Properties</em>' containment reference.
	 * @see #setAdditionalProperties(AdditionalProperties)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getProperty_AdditionalProperties()
	 * @model containment="true"
	 * @generated
	 */
	AdditionalProperties getAdditionalProperties();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Property#getAdditionalProperties <em>Additional Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Properties</em>' containment reference.
	 * @see #getAdditionalProperties()
	 * @generated
	 */
	void setAdditionalProperties(AdditionalProperties value);

} // Property
