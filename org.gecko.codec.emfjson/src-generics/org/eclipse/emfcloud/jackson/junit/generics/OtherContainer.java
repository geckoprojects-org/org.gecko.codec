/*
 */
package org.eclipse.emfcloud.jackson.junit.generics;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Other Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.OtherContainer#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.OtherContainer#getContent <em>Content</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getOtherContainer()
 * @model
 * @generated
 */
@ProviderType
public interface OtherContainer<ContentT extends Content> extends EObject {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getOtherContainer_Key()
	 * @model unique="false"
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.generics.OtherContainer#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' containment reference.
	 * @see #setContent(Content)
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getOtherContainer_Content()
	 * @model containment="true"
	 * @generated
	 */
	ContentT getContent();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.generics.OtherContainer#getContent <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' containment reference.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(ContentT value);

} // OtherContainer
