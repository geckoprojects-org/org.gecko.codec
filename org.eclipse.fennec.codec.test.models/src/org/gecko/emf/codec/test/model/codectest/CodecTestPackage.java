/*
 */
package org.gecko.emf.codec.test.model.codectest;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EReference;

import org.gecko.emf.osgi.annotation.provide.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.gecko.emf.codec.test.model.codectest.CodecTestFactory
 * @model kind="package"
 *        annotation="Version value='1.0'"
 *        annotation="http://geckoprojects.org/codec/1.0.0 name='CodecTest'"
 *        annotation="foo bar='PersonIdentifier'"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelDirectory='org.gecko.codec.test.models/src' updateClasspath='false' bundleManifest='false' complianceLevel='null' basePackage='org.gecko.codec.test' oSGiCompatible='true'"
 * @generated
 */
@ProviderType
@EPackage(uri = CodecTestPackage.eNS_URI, genModel = "/model/codec-test.genmodel", genModelSourceLocations = {"model/codec-test.genmodel","org.gecko.codec.test.models/model/codec-test.genmodel"}, ecore="/model/codec-test.ecore", ecoreSourceLocations="/model/codec-test.ecore")
public interface CodecTestPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "codectest";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://example.de/codectest/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ct";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CodecTestPackage eINSTANCE = org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.gecko.emf.codec.test.model.codectest.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.emf.codec.test.model.codectest.impl.ContactImpl
	 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.emf.codec.test.model.codectest.impl.PersonImpl <em>Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.emf.codec.test.model.codectest.impl.PersonImpl
	 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getPerson()
	 * @generated
	 */
	int PERSON = 1;

	/**
	 * The feature id for the '<em><b>Person Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__PERSON_ID = 0;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__FIRST_NAME = 1;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__LAST_NAME = 2;

	/**
	 * The feature id for the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__BIRTH_DATE = 3;

	/**
	 * The feature id for the '<em><b>Note</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__NOTE = 4;

	/**
	 * The feature id for the '<em><b>Contacts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__CONTACTS = 5;

	/**
	 * The feature id for the '<em><b>Address</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__ADDRESS = 6;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__PASSWORD = 7;

	/**
	 * The number of structural features of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.emf.codec.test.model.codectest.impl.BusinessPersonImpl <em>Business Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.emf.codec.test.model.codectest.impl.BusinessPersonImpl
	 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getBusinessPerson()
	 * @generated
	 */
	int BUSINESS_PERSON = 2;

	/**
	 * The feature id for the '<em><b>Person Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__PERSON_ID = PERSON__PERSON_ID;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__FIRST_NAME = PERSON__FIRST_NAME;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__LAST_NAME = PERSON__LAST_NAME;

	/**
	 * The feature id for the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__BIRTH_DATE = PERSON__BIRTH_DATE;

	/**
	 * The feature id for the '<em><b>Note</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__NOTE = PERSON__NOTE;

	/**
	 * The feature id for the '<em><b>Contacts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__CONTACTS = PERSON__CONTACTS;

	/**
	 * The feature id for the '<em><b>Address</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__ADDRESS = PERSON__ADDRESS;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__PASSWORD = PERSON__PASSWORD;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__POSITION = PERSON_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Business Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON_FEATURE_COUNT = PERSON_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Business Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON_OPERATION_COUNT = PERSON_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.gecko.emf.codec.test.model.codectest.impl.AddressImpl <em>Address</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.emf.codec.test.model.codectest.impl.AddressImpl
	 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getAddress()
	 * @generated
	 */
	int ADDRESS = 3;

	/**
	 * The feature id for the '<em><b>Street</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__STREET = 0;

	/**
	 * The feature id for the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__CITY = 1;

	/**
	 * The feature id for the '<em><b>Zip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__ZIP = 2;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__NUMBER = 3;

	/**
	 * The number of structural features of the '<em>Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.emf.codec.test.model.codectest.ContactType <em>Contact Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.emf.codec.test.model.codectest.ContactType
	 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getContactType()
	 * @generated
	 */
	int CONTACT_TYPE = 4;

	/**
	 * The meta object id for the '<em>Demo Data Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getDemoDataType()
	 * @generated
	 */
	int DEMO_DATA_TYPE = 5;

	/**
	 * The meta object id for the '<em>Demo Data Type No Serialize</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getDemoDataTypeNoSerialize()
	 * @generated
	 */
	int DEMO_DATA_TYPE_NO_SERIALIZE = 6;


	/**
	 * Returns the meta object for class '{@link org.gecko.emf.codec.test.model.codectest.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Contact#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Contact#getValue()
	 * @see #getContact()
	 * @generated
	 */
	EAttribute getContact_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Contact#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Contact#getType()
	 * @see #getContact()
	 * @generated
	 */
	EAttribute getContact_Type();

	/**
	 * Returns the meta object for class '{@link org.gecko.emf.codec.test.model.codectest.Person <em>Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Person</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person
	 * @generated
	 */
	EClass getPerson();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Person#getPersonId <em>Person Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Person Id</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person#getPersonId()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_PersonId();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Person#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person#getFirstName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_FirstName();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Person#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person#getLastName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_LastName();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Person#getBirthDate <em>Birth Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Birth Date</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person#getBirthDate()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_BirthDate();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Person#getNote <em>Note</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Note</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person#getNote()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Note();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gecko.emf.codec.test.model.codectest.Person#getContacts <em>Contacts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contacts</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person#getContacts()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_Contacts();

	/**
	 * Returns the meta object for the reference '{@link org.gecko.emf.codec.test.model.codectest.Person#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Address</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person#getAddress()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_Address();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Person#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Person#getPassword()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Password();

	/**
	 * Returns the meta object for class '{@link org.gecko.emf.codec.test.model.codectest.BusinessPerson <em>Business Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Business Person</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.BusinessPerson
	 * @generated
	 */
	EClass getBusinessPerson();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.BusinessPerson#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.BusinessPerson#getPosition()
	 * @see #getBusinessPerson()
	 * @generated
	 */
	EAttribute getBusinessPerson_Position();

	/**
	 * Returns the meta object for class '{@link org.gecko.emf.codec.test.model.codectest.Address <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Address</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Address
	 * @generated
	 */
	EClass getAddress();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Address#getStreet <em>Street</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Street</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Address#getStreet()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Street();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Address#getCity <em>City</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>City</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Address#getCity()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_City();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Address#getZip <em>Zip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zip</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Address#getZip()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Zip();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.Address#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.Address#getNumber()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Number();

	/**
	 * Returns the meta object for enum '{@link org.gecko.emf.codec.test.model.codectest.ContactType <em>Contact Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Contact Type</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.ContactType
	 * @generated
	 */
	EEnum getContactType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Demo Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Demo Data Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getDemoDataType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Demo Data Type No Serialize</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Demo Data Type No Serialize</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String" serializeable="false"
	 * @generated
	 */
	EDataType getDemoDataTypeNoSerialize();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CodecTestFactory getCodecTestFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.gecko.emf.codec.test.model.codectest.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.emf.codec.test.model.codectest.impl.ContactImpl
		 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getContact()
		 * @generated
		 */
		EClass CONTACT = eINSTANCE.getContact();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT__VALUE = eINSTANCE.getContact_Value();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT__TYPE = eINSTANCE.getContact_Type();

		/**
		 * The meta object literal for the '{@link org.gecko.emf.codec.test.model.codectest.impl.PersonImpl <em>Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.emf.codec.test.model.codectest.impl.PersonImpl
		 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getPerson()
		 * @generated
		 */
		EClass PERSON = eINSTANCE.getPerson();

		/**
		 * The meta object literal for the '<em><b>Person Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__PERSON_ID = eINSTANCE.getPerson_PersonId();

		/**
		 * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__FIRST_NAME = eINSTANCE.getPerson_FirstName();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__LAST_NAME = eINSTANCE.getPerson_LastName();

		/**
		 * The meta object literal for the '<em><b>Birth Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__BIRTH_DATE = eINSTANCE.getPerson_BirthDate();

		/**
		 * The meta object literal for the '<em><b>Note</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__NOTE = eINSTANCE.getPerson_Note();

		/**
		 * The meta object literal for the '<em><b>Contacts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__CONTACTS = eINSTANCE.getPerson_Contacts();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__ADDRESS = eINSTANCE.getPerson_Address();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__PASSWORD = eINSTANCE.getPerson_Password();

		/**
		 * The meta object literal for the '{@link org.gecko.emf.codec.test.model.codectest.impl.BusinessPersonImpl <em>Business Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.emf.codec.test.model.codectest.impl.BusinessPersonImpl
		 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getBusinessPerson()
		 * @generated
		 */
		EClass BUSINESS_PERSON = eINSTANCE.getBusinessPerson();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_PERSON__POSITION = eINSTANCE.getBusinessPerson_Position();

		/**
		 * The meta object literal for the '{@link org.gecko.emf.codec.test.model.codectest.impl.AddressImpl <em>Address</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.emf.codec.test.model.codectest.impl.AddressImpl
		 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getAddress()
		 * @generated
		 */
		EClass ADDRESS = eINSTANCE.getAddress();

		/**
		 * The meta object literal for the '<em><b>Street</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__STREET = eINSTANCE.getAddress_Street();

		/**
		 * The meta object literal for the '<em><b>City</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__CITY = eINSTANCE.getAddress_City();

		/**
		 * The meta object literal for the '<em><b>Zip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__ZIP = eINSTANCE.getAddress_Zip();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__NUMBER = eINSTANCE.getAddress_Number();

		/**
		 * The meta object literal for the '{@link org.gecko.emf.codec.test.model.codectest.ContactType <em>Contact Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.emf.codec.test.model.codectest.ContactType
		 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getContactType()
		 * @generated
		 */
		EEnum CONTACT_TYPE = eINSTANCE.getContactType();

		/**
		 * The meta object literal for the '<em>Demo Data Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getDemoDataType()
		 * @generated
		 */
		EDataType DEMO_DATA_TYPE = eINSTANCE.getDemoDataType();

		/**
		 * The meta object literal for the '<em>Demo Data Type No Serialize</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl#getDemoDataTypeNoSerialize()
		 * @generated
		 */
		EDataType DEMO_DATA_TYPE_NO_SERIALIZE = eINSTANCE.getDemoDataTypeNoSerialize();

	}

} //CodecTestPackage
