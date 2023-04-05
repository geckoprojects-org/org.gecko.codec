/*
 */
package org.gecko.emf.codec.test.model.codectest;

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
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Person#getPersonId <em>Person Id</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Person#getFirstName <em>First Name</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Person#getLastName <em>Last Name</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Person#getBirthDate <em>Birth Date</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Person#getNote <em>Note</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Person#getContacts <em>Contacts</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Person#getAddress <em>Address</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Person#getPassword <em>Password</em>}</li>
 * </ul>
 *
 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson()
 * @model annotation="http://geckoprojects.org/codec/1.0.0 name='CodecPerson'"
 * @generated
 */
@ProviderType
public interface Person extends EObject {
	/**
	 * Returns the value of the '<em><b>Person Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Person Id</em>' attribute.
	 * @see #setPersonId(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson_PersonId()
	 * @model id="true"
	 *        annotation="foo bar='PersonIdentifier'"
	 * @generated
	 */
	String getPersonId();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Person#getPersonId <em>Person Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Person Id</em>' attribute.
	 * @see #getPersonId()
	 * @generated
	 */
	void setPersonId(String value);

	/**
	 * Returns the value of the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Name</em>' attribute.
	 * @see #setFirstName(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson_FirstName()
	 * @model required="true"
	 *        extendedMetaData="name='EMDFirstName'"
	 * @generated
	 */
	String getFirstName();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Person#getFirstName <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Name</em>' attribute.
	 * @see #getFirstName()
	 * @generated
	 */
	void setFirstName(String value);

	/**
	 * Returns the value of the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Name</em>' attribute.
	 * @see #setLastName(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson_LastName()
	 * @model extendedMetaData="name='EMDLastName'"
	 *        annotation="http://geckoprojects.org/codec/1.0.0 name='CodecLastName'"
	 * @generated
	 */
	String getLastName();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Person#getLastName <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Name</em>' attribute.
	 * @see #getLastName()
	 * @generated
	 */
	void setLastName(String value);

	/**
	 * Returns the value of the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Birth Date</em>' attribute.
	 * @see #setBirthDate(Date)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson_BirthDate()
	 * @model extendedMetaData="name='EMDBirthDate'"
	 *        annotation="http://geckoprojects.org/codec/1.0.0 name='CodecBirthDate'"
	 *        annotation="foo bar='Geburtsdatum'"
	 * @generated
	 */
	Date getBirthDate();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Person#getBirthDate <em>Birth Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Birth Date</em>' attribute.
	 * @see #getBirthDate()
	 * @generated
	 */
	void setBirthDate(Date value);

	/**
	 * Returns the value of the '<em><b>Note</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Note</em>' attribute.
	 * @see #setNote(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson_Note()
	 * @model dataType="org.gecko.emf.codec.test.model.codectest.DemoDataType"
	 * @generated
	 */
	String getNote();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Person#getNote <em>Note</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Note</em>' attribute.
	 * @see #getNote()
	 * @generated
	 */
	void setNote(String value);

	/**
	 * Returns the value of the '<em><b>Contacts</b></em>' containment reference list.
	 * The list contents are of type {@link org.gecko.emf.codec.test.model.codectest.Contact}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contacts</em>' containment reference list.
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson_Contacts()
	 * @model containment="true" required="true"
	 *        annotation="http://geckoprojects.org/codec/1.0.0 name='ContactList'"
	 * @generated
	 */
	EList<Contact> getContacts();

	/**
	 * Returns the value of the '<em><b>Address</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' reference.
	 * @see #setAddress(Address)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson_Address()
	 * @model keys="street number city zip"
	 *        annotation="http://geckoprojects.org/codec/1.0.0 name='AddressList'"
	 * @generated
	 */
	Address getAddress();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Person#getAddress <em>Address</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address</em>' reference.
	 * @see #getAddress()
	 * @generated
	 */
	void setAddress(Address value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getPerson_Password()
	 * @model transient="true"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Person#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

} // Person
