/*
 */
package org.gecko.code.demo.model.person;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link org.gecko.code.demo.model.person.Person#getLastName <em>Last Name</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getAddress <em>Address</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getBirthDate <em>Birth Date</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getAge <em>Age</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#isMarried <em>Married</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getGender <em>Gender</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getNonContainedAdd <em>Non Contained Add</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.Person#getTitles <em>Titles</em>}</li>
 * </ul>
 *
 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson()
 * @model annotation="codec.type include='true' use='NAME'"
 *        annotation="codec.id strategy='COMBINED' separator='-'"
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
	 * @model annotation="codec.id id.field='true' id.order='0'"
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
	 * Returns the value of the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Name</em>' attribute.
	 * @see #setLastName(String)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_LastName()
	 * @model annotation="codec.id id.field='true' id.order='1'"
	 * @generated
	 */
	String getLastName();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#getLastName <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Name</em>' attribute.
	 * @see #getLastName()
	 * @generated
	 */
	void setLastName(String value);

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

	/**
	 * Returns the value of the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Birth Date</em>' attribute.
	 * @see #setBirthDate(Date)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_BirthDate()
	 * @model
	 * @generated
	 */
	Date getBirthDate();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#getBirthDate <em>Birth Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Birth Date</em>' attribute.
	 * @see #getBirthDate()
	 * @generated
	 */
	void setBirthDate(Date value);

	/**
	 * Returns the value of the '<em><b>Age</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Age</em>' attribute.
	 * @see #setAge(int)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_Age()
	 * @model
	 * @generated
	 */
	int getAge();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#getAge <em>Age</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Age</em>' attribute.
	 * @see #getAge()
	 * @generated
	 */
	void setAge(int value);

	/**
	 * Returns the value of the '<em><b>Married</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Married</em>' attribute.
	 * @see #setMarried(boolean)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_Married()
	 * @model
	 * @generated
	 */
	boolean isMarried();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#isMarried <em>Married</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Married</em>' attribute.
	 * @see #isMarried()
	 * @generated
	 */
	void setMarried(boolean value);

	/**
	 * Returns the value of the '<em><b>Gender</b></em>' attribute.
	 * The literals are from the enumeration {@link org.gecko.code.demo.model.person.GENDER_TYPE}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gender</em>' attribute.
	 * @see org.gecko.code.demo.model.person.GENDER_TYPE
	 * @see #setGender(GENDER_TYPE)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_Gender()
	 * @model
	 * @generated
	 */
	GENDER_TYPE getGender();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#getGender <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gender</em>' attribute.
	 * @see org.gecko.code.demo.model.person.GENDER_TYPE
	 * @see #getGender()
	 * @generated
	 */
	void setGender(GENDER_TYPE value);

	/**
	 * Returns the value of the '<em><b>Non Contained Add</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Contained Add</em>' reference.
	 * @see #setNonContainedAdd(Address)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_NonContainedAdd()
	 * @model
	 * @generated
	 */
	Address getNonContainedAdd();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#getNonContainedAdd <em>Non Contained Add</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Contained Add</em>' reference.
	 * @see #getNonContainedAdd()
	 * @generated
	 */
	void setNonContainedAdd(Address value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.Person#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Titles</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Titles</em>' attribute list.
	 * @see org.gecko.code.demo.model.person.PersonPackage#getPerson_Titles()
	 * @model
	 * @generated
	 */
	EList<String> getTitles();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.name + \" \" + this.lastName;'"
	 * @generated
	 */
	String getFullName();

} // Person
