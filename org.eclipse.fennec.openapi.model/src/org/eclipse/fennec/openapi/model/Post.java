/*
 */
package org.eclipse.fennec.openapi.model;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Post</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.Post#getRequestBody <em>Request Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getPost()
 * @model
 * @generated
 */
@ProviderType
public interface Post extends Method {
	/**
	 * Returns the value of the '<em><b>Request Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Request Body</em>' containment reference.
	 * @see #setRequestBody(RequestBody)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getPost_RequestBody()
	 * @model containment="true"
	 * @generated
	 */
	RequestBody getRequestBody();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Post#getRequestBody <em>Request Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Request Body</em>' containment reference.
	 * @see #getRequestBody()
	 * @generated
	 */
	void setRequestBody(RequestBody value);

} // Post
