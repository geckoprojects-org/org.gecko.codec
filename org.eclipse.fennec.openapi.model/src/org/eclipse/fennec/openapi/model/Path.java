/*
 */
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Path</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.Path#getPut <em>Put</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Path#getGet <em>Get</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Path#getPost <em>Post</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Path#getDelete <em>Delete</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getPath()
 * @model
 * @generated
 */
@ProviderType
public interface Path extends EObject {
	/**
	 * Returns the value of the '<em><b>Put</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Put</em>' containment reference.
	 * @see #setPut(Put)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getPath_Put()
	 * @model containment="true"
	 * @generated
	 */
	Put getPut();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Path#getPut <em>Put</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Put</em>' containment reference.
	 * @see #getPut()
	 * @generated
	 */
	void setPut(Put value);

	/**
	 * Returns the value of the '<em><b>Get</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Get</em>' containment reference.
	 * @see #setGet(Get)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getPath_Get()
	 * @model containment="true"
	 * @generated
	 */
	Get getGet();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Path#getGet <em>Get</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Get</em>' containment reference.
	 * @see #getGet()
	 * @generated
	 */
	void setGet(Get value);

	/**
	 * Returns the value of the '<em><b>Post</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post</em>' containment reference.
	 * @see #setPost(Post)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getPath_Post()
	 * @model containment="true"
	 * @generated
	 */
	Post getPost();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Path#getPost <em>Post</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post</em>' containment reference.
	 * @see #getPost()
	 * @generated
	 */
	void setPost(Post value);

	/**
	 * Returns the value of the '<em><b>Delete</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete</em>' containment reference.
	 * @see #setDelete(Delete)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getPath_Delete()
	 * @model containment="true"
	 * @generated
	 */
	Delete getDelete();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Path#getDelete <em>Delete</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete</em>' containment reference.
	 * @see #getDelete()
	 * @generated
	 */
	void setDelete(Delete value);

} // Path
