/*
 */
package org.gecko.codec.demo.model.person;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
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
 * @see org.gecko.codec.demo.model.person.PersonFactory
 * @model kind="package"
 *        annotation="Version value='1.0'"
 * @generated
 */
@ProviderType
@EPackage(uri = PersonPackage.eNS_URI, genModel = "/model/person.genmodel", genModelSourceLocations = {"model/person.genmodel","org.gecko.codec.demo.model/model/person.genmodel"}, ecore="/model/person.ecore", ecoreSourceLocations="/model/person.ecore")
public interface PersonPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "person";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://example.de/person/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "person";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PersonPackage eINSTANCE = org.gecko.codec.demo.model.person.impl.PersonPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.gecko.codec.demo.model.person.impl.PersonImpl <em>Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.demo.model.person.impl.PersonImpl
	 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getPerson()
	 * @generated
	 */
	int PERSON = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__NAME = 0;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__LAST_NAME = 1;

	/**
	 * The feature id for the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__ADDRESS = 2;

	/**
	 * The feature id for the '<em><b>Addresses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__ADDRESSES = 3;

	/**
	 * The feature id for the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__BIRTH_DATE = 4;

	/**
	 * The feature id for the '<em><b>Age</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__AGE = 5;

	/**
	 * The feature id for the '<em><b>Married</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__MARRIED = 6;

	/**
	 * The feature id for the '<em><b>Gender</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__GENDER = 7;

	/**
	 * The feature id for the '<em><b>Non Contained Add</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__NON_CONTAINED_ADD = 8;

	/**
	 * The feature id for the '<em><b>Non Contained Adds</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__NON_CONTAINED_ADDS = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__ID = 10;

	/**
	 * The feature id for the '<em><b>Titles</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__TITLES = 11;

	/**
	 * The feature id for the '<em><b>Transient Att</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__TRANSIENT_ATT = 12;

	/**
	 * The feature id for the '<em><b>Business Add</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__BUSINESS_ADD = 13;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__HEIGHT = 14;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__WEIGHT = 15;

	/**
	 * The number of structural features of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_FEATURE_COUNT = 16;

	/**
	 * The operation id for the '<em>Get Full Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON___GET_FULL_NAME = 0;

	/**
	 * The number of operations of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.gecko.codec.demo.model.person.impl.AddressImpl <em>Address</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.demo.model.person.impl.AddressImpl
	 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getAddress()
	 * @generated
	 */
	int ADDRESS = 1;

	/**
	 * The feature id for the '<em><b>Street</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__STREET = 0;

	/**
	 * The feature id for the '<em><b>Zip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__ZIP = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__ID = 2;

	/**
	 * The number of structural features of the '<em>Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.demo.model.person.impl.BusinessPersonImpl <em>Business Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.demo.model.person.impl.BusinessPersonImpl
	 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getBusinessPerson()
	 * @generated
	 */
	int BUSINESS_PERSON = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__NAME = PERSON__NAME;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__LAST_NAME = PERSON__LAST_NAME;

	/**
	 * The feature id for the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__ADDRESS = PERSON__ADDRESS;

	/**
	 * The feature id for the '<em><b>Addresses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__ADDRESSES = PERSON__ADDRESSES;

	/**
	 * The feature id for the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__BIRTH_DATE = PERSON__BIRTH_DATE;

	/**
	 * The feature id for the '<em><b>Age</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__AGE = PERSON__AGE;

	/**
	 * The feature id for the '<em><b>Married</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__MARRIED = PERSON__MARRIED;

	/**
	 * The feature id for the '<em><b>Gender</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__GENDER = PERSON__GENDER;

	/**
	 * The feature id for the '<em><b>Non Contained Add</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__NON_CONTAINED_ADD = PERSON__NON_CONTAINED_ADD;

	/**
	 * The feature id for the '<em><b>Non Contained Adds</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__NON_CONTAINED_ADDS = PERSON__NON_CONTAINED_ADDS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__ID = PERSON__ID;

	/**
	 * The feature id for the '<em><b>Titles</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__TITLES = PERSON__TITLES;

	/**
	 * The feature id for the '<em><b>Transient Att</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__TRANSIENT_ATT = PERSON__TRANSIENT_ATT;

	/**
	 * The feature id for the '<em><b>Business Add</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__BUSINESS_ADD = PERSON__BUSINESS_ADD;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__HEIGHT = PERSON__HEIGHT;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__WEIGHT = PERSON__WEIGHT;

	/**
	 * The feature id for the '<em><b>Company Id Card Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__COMPANY_ID_CARD_NUMBER = PERSON_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Business Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON_FEATURE_COUNT = PERSON_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Full Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON___GET_FULL_NAME = PERSON___GET_FULL_NAME;

	/**
	 * The number of operations of the '<em>Business Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON_OPERATION_COUNT = PERSON_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.demo.model.person.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.demo.model.person.impl.ContactImpl
	 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.demo.model.person.impl.BusinessAddressImpl <em>Business Address</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.demo.model.person.impl.BusinessAddressImpl
	 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getBusinessAddress()
	 * @generated
	 */
	int BUSINESS_ADDRESS = 4;

	/**
	 * The feature id for the '<em><b>Street</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_ADDRESS__STREET = ADDRESS__STREET;

	/**
	 * The feature id for the '<em><b>Zip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_ADDRESS__ZIP = ADDRESS__ZIP;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_ADDRESS__ID = ADDRESS__ID;

	/**
	 * The feature id for the '<em><b>Company Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_ADDRESS__COMPANY_NAME = ADDRESS_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Business Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_ADDRESS_FEATURE_COUNT = ADDRESS_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Business Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_ADDRESS_OPERATION_COUNT = ADDRESS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.demo.model.person.impl.SpecificBusinessPersonImpl <em>Specific Business Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.demo.model.person.impl.SpecificBusinessPersonImpl
	 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getSpecificBusinessPerson()
	 * @generated
	 */
	int SPECIFIC_BUSINESS_PERSON = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__NAME = BUSINESS_PERSON__NAME;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__LAST_NAME = BUSINESS_PERSON__LAST_NAME;

	/**
	 * The feature id for the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__ADDRESS = BUSINESS_PERSON__ADDRESS;

	/**
	 * The feature id for the '<em><b>Addresses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__ADDRESSES = BUSINESS_PERSON__ADDRESSES;

	/**
	 * The feature id for the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__BIRTH_DATE = BUSINESS_PERSON__BIRTH_DATE;

	/**
	 * The feature id for the '<em><b>Age</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__AGE = BUSINESS_PERSON__AGE;

	/**
	 * The feature id for the '<em><b>Married</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__MARRIED = BUSINESS_PERSON__MARRIED;

	/**
	 * The feature id for the '<em><b>Gender</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__GENDER = BUSINESS_PERSON__GENDER;

	/**
	 * The feature id for the '<em><b>Non Contained Add</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__NON_CONTAINED_ADD = BUSINESS_PERSON__NON_CONTAINED_ADD;

	/**
	 * The feature id for the '<em><b>Non Contained Adds</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__NON_CONTAINED_ADDS = BUSINESS_PERSON__NON_CONTAINED_ADDS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__ID = BUSINESS_PERSON__ID;

	/**
	 * The feature id for the '<em><b>Titles</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__TITLES = BUSINESS_PERSON__TITLES;

	/**
	 * The feature id for the '<em><b>Transient Att</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__TRANSIENT_ATT = BUSINESS_PERSON__TRANSIENT_ATT;

	/**
	 * The feature id for the '<em><b>Business Add</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__BUSINESS_ADD = BUSINESS_PERSON__BUSINESS_ADD;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__HEIGHT = BUSINESS_PERSON__HEIGHT;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__WEIGHT = BUSINESS_PERSON__WEIGHT;

	/**
	 * The feature id for the '<em><b>Company Id Card Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON__COMPANY_ID_CARD_NUMBER = BUSINESS_PERSON__COMPANY_ID_CARD_NUMBER;

	/**
	 * The number of structural features of the '<em>Specific Business Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON_FEATURE_COUNT = BUSINESS_PERSON_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Full Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON___GET_FULL_NAME = BUSINESS_PERSON___GET_FULL_NAME;

	/**
	 * The number of operations of the '<em>Specific Business Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIFIC_BUSINESS_PERSON_OPERATION_COUNT = BUSINESS_PERSON_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.demo.model.person.GENDER_TYPE <em>GENDER TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.demo.model.person.GENDER_TYPE
	 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getGENDER_TYPE()
	 * @generated
	 */
	int GENDER_TYPE = 6;


	/**
	 * Returns the meta object for class '{@link org.gecko.codec.demo.model.person.Person <em>Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Person</em>'.
	 * @see org.gecko.codec.demo.model.person.Person
	 * @generated
	 */
	EClass getPerson();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getLastName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_LastName();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.codec.demo.model.person.Person#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Address</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getAddress()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_Address();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gecko.codec.demo.model.person.Person#getAddresses <em>Addresses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Addresses</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getAddresses()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_Addresses();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getBirthDate <em>Birth Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Birth Date</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getBirthDate()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_BirthDate();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getAge <em>Age</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Age</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getAge()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Age();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#isMarried <em>Married</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Married</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#isMarried()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Married();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getGender <em>Gender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gender</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getGender()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Gender();

	/**
	 * Returns the meta object for the reference '{@link org.gecko.codec.demo.model.person.Person#getNonContainedAdd <em>Non Contained Add</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Non Contained Add</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getNonContainedAdd()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_NonContainedAdd();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.demo.model.person.Person#getNonContainedAdds <em>Non Contained Adds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Non Contained Adds</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getNonContainedAdds()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_NonContainedAdds();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getId()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Id();

	/**
	 * Returns the meta object for the attribute list '{@link org.gecko.codec.demo.model.person.Person#getTitles <em>Titles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Titles</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getTitles()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Titles();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getTransientAtt <em>Transient Att</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transient Att</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getTransientAtt()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_TransientAtt();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.codec.demo.model.person.Person#getBusinessAdd <em>Business Add</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Business Add</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getBusinessAdd()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_BusinessAdd();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getHeight()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Height();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Person#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see org.gecko.codec.demo.model.person.Person#getWeight()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Weight();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.demo.model.person.Person#getFullName() <em>Get Full Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Full Name</em>' operation.
	 * @see org.gecko.codec.demo.model.person.Person#getFullName()
	 * @generated
	 */
	EOperation getPerson__GetFullName();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.demo.model.person.Address <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Address</em>'.
	 * @see org.gecko.codec.demo.model.person.Address
	 * @generated
	 */
	EClass getAddress();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Address#getStreet <em>Street</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Street</em>'.
	 * @see org.gecko.codec.demo.model.person.Address#getStreet()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Street();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Address#getZip <em>Zip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zip</em>'.
	 * @see org.gecko.codec.demo.model.person.Address#getZip()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Zip();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Address#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.gecko.codec.demo.model.person.Address#getId()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Id();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.demo.model.person.BusinessPerson <em>Business Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Business Person</em>'.
	 * @see org.gecko.codec.demo.model.person.BusinessPerson
	 * @generated
	 */
	EClass getBusinessPerson();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.BusinessPerson#getCompanyIdCardNumber <em>Company Id Card Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Company Id Card Number</em>'.
	 * @see org.gecko.codec.demo.model.person.BusinessPerson#getCompanyIdCardNumber()
	 * @see #getBusinessPerson()
	 * @generated
	 */
	EAttribute getBusinessPerson_CompanyIdCardNumber();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.demo.model.person.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see org.gecko.codec.demo.model.person.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.Contact#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.gecko.codec.demo.model.person.Contact#getValue()
	 * @see #getContact()
	 * @generated
	 */
	EAttribute getContact_Value();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.demo.model.person.BusinessAddress <em>Business Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Business Address</em>'.
	 * @see org.gecko.codec.demo.model.person.BusinessAddress
	 * @generated
	 */
	EClass getBusinessAddress();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.demo.model.person.BusinessAddress#getCompanyName <em>Company Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Company Name</em>'.
	 * @see org.gecko.codec.demo.model.person.BusinessAddress#getCompanyName()
	 * @see #getBusinessAddress()
	 * @generated
	 */
	EAttribute getBusinessAddress_CompanyName();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.demo.model.person.SpecificBusinessPerson <em>Specific Business Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Specific Business Person</em>'.
	 * @see org.gecko.codec.demo.model.person.SpecificBusinessPerson
	 * @generated
	 */
	EClass getSpecificBusinessPerson();

	/**
	 * Returns the meta object for enum '{@link org.gecko.codec.demo.model.person.GENDER_TYPE <em>GENDER TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>GENDER TYPE</em>'.
	 * @see org.gecko.codec.demo.model.person.GENDER_TYPE
	 * @generated
	 */
	EEnum getGENDER_TYPE();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PersonFactory getPersonFactory();

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
		 * The meta object literal for the '{@link org.gecko.codec.demo.model.person.impl.PersonImpl <em>Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.demo.model.person.impl.PersonImpl
		 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getPerson()
		 * @generated
		 */
		EClass PERSON = eINSTANCE.getPerson();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__NAME = eINSTANCE.getPerson_Name();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__LAST_NAME = eINSTANCE.getPerson_LastName();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__ADDRESS = eINSTANCE.getPerson_Address();

		/**
		 * The meta object literal for the '<em><b>Addresses</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__ADDRESSES = eINSTANCE.getPerson_Addresses();

		/**
		 * The meta object literal for the '<em><b>Birth Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__BIRTH_DATE = eINSTANCE.getPerson_BirthDate();

		/**
		 * The meta object literal for the '<em><b>Age</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__AGE = eINSTANCE.getPerson_Age();

		/**
		 * The meta object literal for the '<em><b>Married</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__MARRIED = eINSTANCE.getPerson_Married();

		/**
		 * The meta object literal for the '<em><b>Gender</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__GENDER = eINSTANCE.getPerson_Gender();

		/**
		 * The meta object literal for the '<em><b>Non Contained Add</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__NON_CONTAINED_ADD = eINSTANCE.getPerson_NonContainedAdd();

		/**
		 * The meta object literal for the '<em><b>Non Contained Adds</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__NON_CONTAINED_ADDS = eINSTANCE.getPerson_NonContainedAdds();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__ID = eINSTANCE.getPerson_Id();

		/**
		 * The meta object literal for the '<em><b>Titles</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__TITLES = eINSTANCE.getPerson_Titles();

		/**
		 * The meta object literal for the '<em><b>Transient Att</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__TRANSIENT_ATT = eINSTANCE.getPerson_TransientAtt();

		/**
		 * The meta object literal for the '<em><b>Business Add</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__BUSINESS_ADD = eINSTANCE.getPerson_BusinessAdd();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__HEIGHT = eINSTANCE.getPerson_Height();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__WEIGHT = eINSTANCE.getPerson_Weight();

		/**
		 * The meta object literal for the '<em><b>Get Full Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PERSON___GET_FULL_NAME = eINSTANCE.getPerson__GetFullName();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.demo.model.person.impl.AddressImpl <em>Address</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.demo.model.person.impl.AddressImpl
		 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getAddress()
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
		 * The meta object literal for the '<em><b>Zip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__ZIP = eINSTANCE.getAddress_Zip();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__ID = eINSTANCE.getAddress_Id();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.demo.model.person.impl.BusinessPersonImpl <em>Business Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.demo.model.person.impl.BusinessPersonImpl
		 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getBusinessPerson()
		 * @generated
		 */
		EClass BUSINESS_PERSON = eINSTANCE.getBusinessPerson();

		/**
		 * The meta object literal for the '<em><b>Company Id Card Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_PERSON__COMPANY_ID_CARD_NUMBER = eINSTANCE.getBusinessPerson_CompanyIdCardNumber();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.demo.model.person.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.demo.model.person.impl.ContactImpl
		 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getContact()
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
		 * The meta object literal for the '{@link org.gecko.codec.demo.model.person.impl.BusinessAddressImpl <em>Business Address</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.demo.model.person.impl.BusinessAddressImpl
		 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getBusinessAddress()
		 * @generated
		 */
		EClass BUSINESS_ADDRESS = eINSTANCE.getBusinessAddress();

		/**
		 * The meta object literal for the '<em><b>Company Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_ADDRESS__COMPANY_NAME = eINSTANCE.getBusinessAddress_CompanyName();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.demo.model.person.impl.SpecificBusinessPersonImpl <em>Specific Business Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.demo.model.person.impl.SpecificBusinessPersonImpl
		 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getSpecificBusinessPerson()
		 * @generated
		 */
		EClass SPECIFIC_BUSINESS_PERSON = eINSTANCE.getSpecificBusinessPerson();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.demo.model.person.GENDER_TYPE <em>GENDER TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.demo.model.person.GENDER_TYPE
		 * @see org.gecko.codec.demo.model.person.impl.PersonPackageImpl#getGENDER_TYPE()
		 * @generated
		 */
		EEnum GENDER_TYPE = eINSTANCE.getGENDER_TYPE();

	}

} //PersonPackage