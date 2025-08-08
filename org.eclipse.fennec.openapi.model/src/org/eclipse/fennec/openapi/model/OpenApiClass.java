/*
 */
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApiClass#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApiClass#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApiClass()
 * @model
 * @generated
 */
@ProviderType
public interface OpenApiClass extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApiClass_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.OpenApiClass#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.fennec.openapi.model.Property},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' map.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApiClass_Properties()
	 * @model mapType="org.eclipse.fennec.openapi.model.StringToPropertyMap&lt;org.eclipse.emf.ecore.EString, org.eclipse.fennec.openapi.model.Property&gt;"
	 * @generated
	 */
	EMap<String, Property> getProperties();

} // OpenApiClass
