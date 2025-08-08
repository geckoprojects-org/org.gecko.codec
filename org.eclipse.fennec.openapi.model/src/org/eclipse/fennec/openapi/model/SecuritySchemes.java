/*
 */
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Security Schemes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.SecuritySchemes#getBearerAuth <em>Bearer Auth</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.SecuritySchemes#getBasicAuth <em>Basic Auth</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getSecuritySchemes()
 * @model
 * @generated
 */
@ProviderType
public interface SecuritySchemes extends EObject {
	/**
	 * Returns the value of the '<em><b>Bearer Auth</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bearer Auth</em>' containment reference.
	 * @see #setBearerAuth(BearerAuth)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getSecuritySchemes_BearerAuth()
	 * @model containment="true"
	 * @generated
	 */
	BearerAuth getBearerAuth();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.SecuritySchemes#getBearerAuth <em>Bearer Auth</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bearer Auth</em>' containment reference.
	 * @see #getBearerAuth()
	 * @generated
	 */
	void setBearerAuth(BearerAuth value);

	/**
	 * Returns the value of the '<em><b>Basic Auth</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic Auth</em>' containment reference.
	 * @see #setBasicAuth(BasicAuth)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getSecuritySchemes_BasicAuth()
	 * @model containment="true"
	 * @generated
	 */
	BasicAuth getBasicAuth();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.SecuritySchemes#getBasicAuth <em>Basic Auth</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Basic Auth</em>' containment reference.
	 * @see #getBasicAuth()
	 * @generated
	 */
	void setBasicAuth(BasicAuth value);

} // SecuritySchemes
