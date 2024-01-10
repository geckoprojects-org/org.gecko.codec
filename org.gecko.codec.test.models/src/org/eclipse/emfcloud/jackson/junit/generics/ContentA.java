/*
 */
package org.eclipse.emfcloud.jackson.junit.generics;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Content A</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.ContentA#getPayload <em>Payload</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getContentA()
 * @model
 * @generated
 */
@ProviderType
public interface ContentA extends Content {
	/**
	 * Returns the value of the '<em><b>Payload</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Payload</em>' attribute.
	 * @see #setPayload(String)
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getContentA_Payload()
	 * @model unique="false"
	 * @generated
	 */
	String getPayload();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.generics.ContentA#getPayload <em>Payload</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Payload</em>' attribute.
	 * @see #getPayload()
	 * @generated
	 */
	void setPayload(String value);

} // ContentA
