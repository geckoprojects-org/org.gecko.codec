/*
 */
package org.gecko.code.demo.model.person;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.gecko.code.demo.model.person.PersonFactory
 * @model kind="package"
 *        annotation="Version value='1.0'"
 * @generated
 */
@ProviderType
@EPackage(uri = PersonPackage.eNS_URI, genModel = "/model/person.genmodel", genModelSourceLocations = {"model/person.genmodel","org.gecko.code.demo.model/model/person.genmodel"}, ecore="/model/person.ecore", ecoreSourceLocations="/model/person.ecore")
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
	PersonPackage eINSTANCE = org.gecko.code.demo.model.person.impl.PersonPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.gecko.code.demo.model.person.impl.PersonImpl <em>Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.code.demo.model.person.impl.PersonImpl
	 * @see org.gecko.code.demo.model.person.impl.PersonPackageImpl#getPerson()
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
	 * The feature id for the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__ADDRESS = 1;

	/**
	 * The number of structural features of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.code.demo.model.person.impl.AddressImpl <em>Address</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.code.demo.model.person.impl.AddressImpl
	 * @see org.gecko.code.demo.model.person.impl.PersonPackageImpl#getAddress()
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
	 * The number of structural features of the '<em>Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.gecko.code.demo.model.person.Person <em>Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Person</em>'.
	 * @see org.gecko.code.demo.model.person.Person
	 * @generated
	 */
	EClass getPerson();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.code.demo.model.person.Person#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.gecko.code.demo.model.person.Person#getName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.code.demo.model.person.Person#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Address</em>'.
	 * @see org.gecko.code.demo.model.person.Person#getAddress()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_Address();

	/**
	 * Returns the meta object for class '{@link org.gecko.code.demo.model.person.Address <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Address</em>'.
	 * @see org.gecko.code.demo.model.person.Address
	 * @generated
	 */
	EClass getAddress();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.code.demo.model.person.Address#getStreet <em>Street</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Street</em>'.
	 * @see org.gecko.code.demo.model.person.Address#getStreet()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Street();

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
		 * The meta object literal for the '{@link org.gecko.code.demo.model.person.impl.PersonImpl <em>Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.code.demo.model.person.impl.PersonImpl
		 * @see org.gecko.code.demo.model.person.impl.PersonPackageImpl#getPerson()
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
		 * The meta object literal for the '<em><b>Address</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__ADDRESS = eINSTANCE.getPerson_Address();

		/**
		 * The meta object literal for the '{@link org.gecko.code.demo.model.person.impl.AddressImpl <em>Address</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.code.demo.model.person.impl.AddressImpl
		 * @see org.gecko.code.demo.model.person.impl.PersonPackageImpl#getAddress()
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

	}

} //PersonPackage
