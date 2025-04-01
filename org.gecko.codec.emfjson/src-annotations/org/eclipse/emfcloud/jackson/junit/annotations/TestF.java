/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test F</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestF#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestF#getOtherValue <em>Other Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestF()
 * @model
 * @generated
 */
@ProviderType
public interface TestF extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestF_Value()
	 * @model unique="false"
	 *        annotation="JsonAlias value='value'"
	 *        annotation="JsonProperty value='v'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestF#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Other Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Other Value</em>' attribute.
	 * @see #setOtherValue(String)
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestF_OtherValue()
	 * @model unique="false"
	 *        annotation="JsonAlias value='foo, bar'"
	 * @generated
	 */
	String getOtherValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestF#getOtherValue <em>Other Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Other Value</em>' attribute.
	 * @see #getOtherValue()
	 * @generated
	 */
	void setOtherValue(String value);

} // TestF
