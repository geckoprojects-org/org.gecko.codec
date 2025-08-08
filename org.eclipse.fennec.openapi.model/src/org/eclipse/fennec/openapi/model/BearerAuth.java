/*
 */
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bearer Auth</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.BearerAuth#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.BearerAuth#getScheme <em>Scheme</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.BearerAuth#getBearerFormat <em>Bearer Format</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getBearerAuth()
 * @model
 * @generated
 */
@ProviderType
public interface BearerAuth extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getBearerAuth_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.BearerAuth#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Scheme</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scheme</em>' attribute.
	 * @see #setScheme(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getBearerAuth_Scheme()
	 * @model
	 * @generated
	 */
	String getScheme();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.BearerAuth#getScheme <em>Scheme</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scheme</em>' attribute.
	 * @see #getScheme()
	 * @generated
	 */
	void setScheme(String value);

	/**
	 * Returns the value of the '<em><b>Bearer Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bearer Format</em>' attribute.
	 * @see #setBearerFormat(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getBearerAuth_BearerFormat()
	 * @model
	 * @generated
	 */
	String getBearerFormat();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.BearerAuth#getBearerFormat <em>Bearer Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bearer Format</em>' attribute.
	 * @see #getBearerFormat()
	 * @generated
	 */
	void setBearerFormat(String value);

} // BearerAuth
