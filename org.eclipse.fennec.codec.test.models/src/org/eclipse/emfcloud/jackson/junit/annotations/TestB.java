/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test B</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestB#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestB()
 * @model annotation="JsonIdentity property='id' include='true'"
 * @generated
 */
@ProviderType
public interface TestB extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestB_Value()
	 * @model unique="false"
	 *        annotation="JsonProperty value='my_value'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestB#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="JsonProperty"
	 * @generated
	 */
	String hello();

} // TestB
