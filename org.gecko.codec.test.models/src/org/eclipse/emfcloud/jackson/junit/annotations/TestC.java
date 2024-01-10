/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test C</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC#getManyValues <em>Many Values</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC#getOneValue <em>One Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestC()
 * @model annotation="JsonType include='false'"
 * @generated
 */
@ProviderType
public interface TestC extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(int)
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestC_Value()
	 * @model unique="false"
	 * @generated
	 */
	int getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(int value);

	/**
	 * Returns the value of the '<em><b>Many Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.annotations.TestC}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many Values</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestC_ManyValues()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestC> getManyValues();

	/**
	 * Returns the value of the '<em><b>One Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One Value</em>' containment reference.
	 * @see #setOneValue(TestC)
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestC_OneValue()
	 * @model containment="true"
	 * @generated
	 */
	TestC getOneValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC#getOneValue <em>One Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>One Value</em>' containment reference.
	 * @see #getOneValue()
	 * @generated
	 */
	void setOneValue(TestC value);

} // TestC
