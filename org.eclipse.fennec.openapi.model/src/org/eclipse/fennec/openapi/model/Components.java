/*
 */
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Components</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.Components#getSecuritySchemes <em>Security Schemes</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Components#getSchemas <em>Schemas</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getComponents()
 * @model
 * @generated
 */
@ProviderType
public interface Components extends EObject {
	/**
	 * Returns the value of the '<em><b>Security Schemes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Schemes</em>' containment reference.
	 * @see #setSecuritySchemes(SecuritySchemes)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getComponents_SecuritySchemes()
	 * @model containment="true"
	 * @generated
	 */
	SecuritySchemes getSecuritySchemes();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Components#getSecuritySchemes <em>Security Schemes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Security Schemes</em>' containment reference.
	 * @see #getSecuritySchemes()
	 * @generated
	 */
	void setSecuritySchemes(SecuritySchemes value);

	/**
	 * Returns the value of the '<em><b>Schemas</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schemas</em>' containment reference.
	 * @see #setSchemas(EPackage)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getComponents_Schemas()
	 * @model containment="true"
	 * @generated
	 */
	EPackage getSchemas();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Components#getSchemas <em>Schemas</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schemas</em>' containment reference.
	 * @see #getSchemas()
	 * @generated
	 */
	void setSchemas(EPackage value);

} // Components
