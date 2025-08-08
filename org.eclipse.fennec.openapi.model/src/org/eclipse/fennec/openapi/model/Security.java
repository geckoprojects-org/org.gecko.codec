/*
 */
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Security</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.Security#getBearerAuth <em>Bearer Auth</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getSecurity()
 * @model
 * @generated
 */
@ProviderType
public interface Security extends EObject {
	/**
	 * Returns the value of the '<em><b>Bearer Auth</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bearer Auth</em>' attribute list.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getSecurity_BearerAuth()
	 * @model
	 * @generated
	 */
	EList<String> getBearerAuth();

} // Security
