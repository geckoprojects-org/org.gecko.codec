/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test A</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA#getChildOfTypeC <em>Child Of Type C</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA#getChildrenOfTypeD <em>Children Of Type D</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestA()
 * @model annotation="JsonType property='@type'"
 * @generated
 */
@ProviderType
public interface TestA extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestA_Value()
	 * @model unique="false"
	 *        annotation="JsonProperty value='my_value'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Child Of Type C</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Of Type C</em>' containment reference.
	 * @see #setChildOfTypeC(TestC)
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestA_ChildOfTypeC()
	 * @model containment="true"
	 * @generated
	 */
	TestC getChildOfTypeC();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA#getChildOfTypeC <em>Child Of Type C</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Child Of Type C</em>' containment reference.
	 * @see #getChildOfTypeC()
	 * @generated
	 */
	void setChildOfTypeC(TestC value);

	/**
	 * Returns the value of the '<em><b>Children Of Type D</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.annotations.TestD}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children Of Type D</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestA_ChildrenOfTypeD()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestD> getChildrenOfTypeD();

} // TestA
