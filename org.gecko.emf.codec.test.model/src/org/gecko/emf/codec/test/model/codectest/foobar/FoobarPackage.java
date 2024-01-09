/*
 */
package org.gecko.emf.codec.test.model.codectest.foobar;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;

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
 * @see org.gecko.emf.codec.test.model.codectest.foobar.FoobarFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = FoobarPackage.eNS_URI, genModel = "/model/codec-test.genmodel", genModelSourceLocations = {"model/codec-test.genmodel","org.gecko.emf.codec.test.model/model/codec-test.genmodel"}, ecore="/model/codec-test.ecore", ecoreSourceLocations="/model/codec-test.ecore")
public interface FoobarPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "foobar";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://example.de/codectest/foobar/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "fb";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FoobarPackage eINSTANCE = org.gecko.emf.codec.test.model.codectest.foobar.impl.FoobarPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.gecko.emf.codec.test.model.codectest.foobar.impl.FooImpl <em>Foo</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.emf.codec.test.model.codectest.foobar.impl.FooImpl
	 * @see org.gecko.emf.codec.test.model.codectest.foobar.impl.FoobarPackageImpl#getFoo()
	 * @generated
	 */
	int FOO = 0;

	/**
	 * The feature id for the '<em><b>Bar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO__BAR = 0;

	/**
	 * The number of structural features of the '<em>Foo</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Foo</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.gecko.emf.codec.test.model.codectest.foobar.Foo <em>Foo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Foo</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.foobar.Foo
	 * @generated
	 */
	EClass getFoo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.foobar.Foo#getBar <em>Bar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bar</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.foobar.Foo#getBar()
	 * @see #getFoo()
	 * @generated
	 */
	EAttribute getFoo_Bar();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FoobarFactory getFoobarFactory();

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
		 * The meta object literal for the '{@link org.gecko.emf.codec.test.model.codectest.foobar.impl.FooImpl <em>Foo</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.emf.codec.test.model.codectest.foobar.impl.FooImpl
		 * @see org.gecko.emf.codec.test.model.codectest.foobar.impl.FoobarPackageImpl#getFoo()
		 * @generated
		 */
		EClass FOO = eINSTANCE.getFoo();

		/**
		 * The meta object literal for the '<em><b>Bar</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOO__BAR = eINSTANCE.getFoo_Bar();

	}

} //FoobarPackage
