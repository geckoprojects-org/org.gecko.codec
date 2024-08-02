/*
 */
package org.gecko.code.demo.model.person;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getName <em>Name</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getAddress <em>Address</em>}</li>
 * </ul>
 *
 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson()
 * @model
 * @generated
 */
@ProviderType
public interface Person extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' containment reference.
	 * @see #setAddress(Address)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_Address()
	 * @model containment="true"
	 * @generated
	 */
	Address getAddress();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#getAddress <em>Address</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address</em>' containment reference.
	 * @see #getAddress()
	 * @generated
	 */
	void setAddress(Address value);

} // Person
