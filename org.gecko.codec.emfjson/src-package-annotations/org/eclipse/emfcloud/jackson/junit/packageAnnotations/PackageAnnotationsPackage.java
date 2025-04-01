/*
 */
package org.eclipse.emfcloud.jackson.junit.packageAnnotations;


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
 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsFactory
 * @model kind="package"
 *        annotation="JsonType property='@type' use='NAME'"
 *        annotation="http://www.eclipse.org/emf/2011/Xcore JsonType='JsonType'"
 * @generated
 */
@ProviderType
@EPackage(uri = PackageAnnotationsPackage.eNS_URI, genModel = "/../packageAnnotations.genmodel", genModelSourceLocations = {"test-data/model/packageAnnotations.genmodel","org.gecko.codec.emfjson/test-data/model/packageAnnotations.genmodel"}, ecore="/../packageAnnotations.ecore", ecoreSourceLocations="/test-data/model/packageAnnotations.ecore")
public interface PackageAnnotationsPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "packageAnnotations";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.emfjson.org/jackson/annotations";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "packageAnnotations";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PackageAnnotationsPackage eINSTANCE = org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.ContainerImpl <em>Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.ContainerImpl
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__VALUES = 0;

	/**
	 * The number of structural features of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.TestTypeNameImpl <em>Test Type Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.TestTypeNameImpl
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl#getTestTypeName()
	 * @generated
	 */
	int TEST_TYPE_NAME = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_TYPE_NAME__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Test Type Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_TYPE_NAME_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Test Type Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_TYPE_NAME_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.FooTypeNameImpl <em>Foo Type Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.FooTypeNameImpl
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl#getFooTypeName()
	 * @generated
	 */
	int FOO_TYPE_NAME = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO_TYPE_NAME__VALUE = TEST_TYPE_NAME__VALUE;

	/**
	 * The number of structural features of the '<em>Foo Type Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO_TYPE_NAME_FEATURE_COUNT = TEST_TYPE_NAME_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Foo Type Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO_TYPE_NAME_OPERATION_COUNT = TEST_TYPE_NAME_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.BarTypeNameImpl <em>Bar Type Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.BarTypeNameImpl
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl#getBarTypeName()
	 * @generated
	 */
	int BAR_TYPE_NAME = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_TYPE_NAME__VALUE = TEST_TYPE_NAME__VALUE;

	/**
	 * The number of structural features of the '<em>Bar Type Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_TYPE_NAME_FEATURE_COUNT = TEST_TYPE_NAME_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Bar Type Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_TYPE_NAME_OPERATION_COUNT = TEST_TYPE_NAME_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.Container
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.Container#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.Container#getValues()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.TestTypeName <em>Test Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Type Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.TestTypeName
	 * @generated
	 */
	EClass getTestTypeName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.TestTypeName#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.TestTypeName#getValue()
	 * @see #getTestTypeName()
	 * @generated
	 */
	EAttribute getTestTypeName_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.FooTypeName <em>Foo Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Foo Type Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.FooTypeName
	 * @generated
	 */
	EClass getFooTypeName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.BarTypeName <em>Bar Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bar Type Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.BarTypeName
	 * @generated
	 */
	EClass getBarTypeName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PackageAnnotationsFactory getPackageAnnotationsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.ContainerImpl <em>Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.ContainerImpl
		 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl#getContainer()
		 * @generated
		 */
		EClass CONTAINER = eINSTANCE.getContainer();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER__VALUES = eINSTANCE.getContainer_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.TestTypeNameImpl <em>Test Type Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.TestTypeNameImpl
		 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl#getTestTypeName()
		 * @generated
		 */
		EClass TEST_TYPE_NAME = eINSTANCE.getTestTypeName();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_TYPE_NAME__VALUE = eINSTANCE.getTestTypeName_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.FooTypeNameImpl <em>Foo Type Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.FooTypeNameImpl
		 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl#getFooTypeName()
		 * @generated
		 */
		EClass FOO_TYPE_NAME = eINSTANCE.getFooTypeName();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.BarTypeNameImpl <em>Bar Type Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.BarTypeNameImpl
		 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl#getBarTypeName()
		 * @generated
		 */
		EClass BAR_TYPE_NAME = eINSTANCE.getBarTypeName();

	}

} //PackageAnnotationsPackage
