/*
 */
package org.eclipse.emfcloud.jackson.junit.generics;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getLinkTo <em>Link To</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getLinkToMany <em>Link To Many</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getContainsOne <em>Contains One</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getContainsMany <em>Contains Many</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getBase()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface Base<U, T> extends EObject {
	/**
	 * Returns the value of the '<em><b>Link To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link To</em>' reference.
	 * @see #setLinkTo(Object)
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getBase_LinkTo()
	 * @model kind="reference"
	 * @generated
	 */
	U getLinkTo();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getLinkTo <em>Link To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link To</em>' reference.
	 * @see #getLinkTo()
	 * @generated
	 */
	void setLinkTo(U value);

	/**
	 * Returns the value of the '<em><b>Link To Many</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link To Many</em>' reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getBase_LinkToMany()
	 * @model kind="reference"
	 * @generated
	 */
	EList<U> getLinkToMany();

	/**
	 * Returns the value of the '<em><b>Contains One</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contains One</em>' containment reference.
	 * @see #setContainsOne(Object)
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getBase_ContainsOne()
	 * @model kind="reference" containment="true"
	 * @generated
	 */
	T getContainsOne();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getContainsOne <em>Contains One</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contains One</em>' containment reference.
	 * @see #getContainsOne()
	 * @generated
	 */
	void setContainsOne(T value);

	/**
	 * Returns the value of the '<em><b>Contains Many</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contains Many</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getBase_ContainsMany()
	 * @model kind="reference" containment="true"
	 * @generated
	 */
	EList<T> getContainsMany();

} // Base
