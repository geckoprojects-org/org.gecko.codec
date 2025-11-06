/*
 * Copyright (c) 2012 - 2025 Data In Motion and others.
 * All rights reserved.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *      Mark Hoffmann - initial API and implementation
 */
package org.eclipse.fennec.codec.demo.model.test1;


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
 * @see org.eclipse.fennec.codec.demo.model.test1.TestTypeAnnotationInheritanceFactory
 * @model kind="package"
 *        annotation="Version value='1.0'"
 * @generated
 */
@ProviderType
@EPackage(uri = TestTypeAnnotationInheritancePackage.eNS_URI, genModel = "/model/test1.genmodel", genModelSourceLocations = {"model/test1.genmodel","org.eclipse.fennec.codec.demo.model/model/test1.genmodel"}, ecore="/model/test1.ecore", ecoreSourceLocations="/model/test1.ecore")
public interface TestTypeAnnotationInheritancePackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "test1";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/fennec/codec/test-type-annotation-inheritance/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "test1";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestTypeAnnotationInheritancePackage eINSTANCE = org.eclipse.fennec.codec.demo.model.test1.impl.TestTypeAnnotationInheritancePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.demo.model.test1.impl.PersonImpl <em>Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.demo.model.test1.impl.PersonImpl
	 * @see org.eclipse.fennec.codec.demo.model.test1.impl.TestTypeAnnotationInheritancePackageImpl#getPerson()
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
	 * The meta object id for the '{@link org.eclipse.fennec.codec.demo.model.test1.impl.BusinessPersonImpl <em>Business Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.demo.model.test1.impl.BusinessPersonImpl
	 * @see org.eclipse.fennec.codec.demo.model.test1.impl.TestTypeAnnotationInheritancePackageImpl#getBusinessPerson()
	 * @generated
	 */
	int BUSINESS_PERSON = 1;

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
	 * The feature id for the '<em><b>Company Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__COMPANY_ID = PERSON_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Company Id Card Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON__COMPANY_ID_CARD_NUMBER = PERSON_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Business Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON_FEATURE_COUNT = PERSON_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Business Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_PERSON_OPERATION_COUNT = PERSON_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.demo.model.test1.impl.MeetingImpl <em>Meeting</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.demo.model.test1.impl.MeetingImpl
	 * @see org.eclipse.fennec.codec.demo.model.test1.impl.TestTypeAnnotationInheritancePackageImpl#getMeeting()
	 * @generated
	 */
	int MEETING = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEETING__ID = 0;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEETING__DATE = 1;

	/**
	 * The feature id for the '<em><b>Responsible Person</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEETING__RESPONSIBLE_PERSON = 2;

	/**
	 * The number of structural features of the '<em>Meeting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEETING_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Meeting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEETING_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.demo.model.test1.Person <em>Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Person</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.Person
	 * @generated
	 */
	EClass getPerson();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.demo.model.test1.Person#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.Person#getName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.demo.model.test1.Person#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.Person#getLastName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_LastName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.demo.model.test1.BusinessPerson <em>Business Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Business Person</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.BusinessPerson
	 * @generated
	 */
	EClass getBusinessPerson();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.demo.model.test1.BusinessPerson#getCompanyId <em>Company Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Company Id</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.BusinessPerson#getCompanyId()
	 * @see #getBusinessPerson()
	 * @generated
	 */
	EAttribute getBusinessPerson_CompanyId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.demo.model.test1.BusinessPerson#getCompanyIdCardNumber <em>Company Id Card Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Company Id Card Number</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.BusinessPerson#getCompanyIdCardNumber()
	 * @see #getBusinessPerson()
	 * @generated
	 */
	EAttribute getBusinessPerson_CompanyIdCardNumber();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.demo.model.test1.Meeting <em>Meeting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meeting</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.Meeting
	 * @generated
	 */
	EClass getMeeting();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.demo.model.test1.Meeting#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.Meeting#getId()
	 * @see #getMeeting()
	 * @generated
	 */
	EAttribute getMeeting_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.demo.model.test1.Meeting#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.Meeting#getDate()
	 * @see #getMeeting()
	 * @generated
	 */
	EAttribute getMeeting_Date();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.demo.model.test1.Meeting#getResponsiblePerson <em>Responsible Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Responsible Person</em>'.
	 * @see org.eclipse.fennec.codec.demo.model.test1.Meeting#getResponsiblePerson()
	 * @see #getMeeting()
	 * @generated
	 */
	EReference getMeeting_ResponsiblePerson();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestTypeAnnotationInheritanceFactory getTestTypeAnnotationInheritanceFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.demo.model.test1.impl.PersonImpl <em>Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.demo.model.test1.impl.PersonImpl
		 * @see org.eclipse.fennec.codec.demo.model.test1.impl.TestTypeAnnotationInheritancePackageImpl#getPerson()
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
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.demo.model.test1.impl.BusinessPersonImpl <em>Business Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.demo.model.test1.impl.BusinessPersonImpl
		 * @see org.eclipse.fennec.codec.demo.model.test1.impl.TestTypeAnnotationInheritancePackageImpl#getBusinessPerson()
		 * @generated
		 */
		EClass BUSINESS_PERSON = eINSTANCE.getBusinessPerson();

		/**
		 * The meta object literal for the '<em><b>Company Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_PERSON__COMPANY_ID = eINSTANCE.getBusinessPerson_CompanyId();

		/**
		 * The meta object literal for the '<em><b>Company Id Card Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_PERSON__COMPANY_ID_CARD_NUMBER = eINSTANCE.getBusinessPerson_CompanyIdCardNumber();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.demo.model.test1.impl.MeetingImpl <em>Meeting</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.demo.model.test1.impl.MeetingImpl
		 * @see org.eclipse.fennec.codec.demo.model.test1.impl.TestTypeAnnotationInheritancePackageImpl#getMeeting()
		 * @generated
		 */
		EClass MEETING = eINSTANCE.getMeeting();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEETING__ID = eINSTANCE.getMeeting_Id();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEETING__DATE = eINSTANCE.getMeeting_Date();

		/**
		 * The meta object literal for the '<em><b>Responsible Person</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEETING__RESPONSIBLE_PERSON = eINSTANCE.getMeeting_ResponsiblePerson();

	}

} //TestTypeAnnotationInheritancePackage
