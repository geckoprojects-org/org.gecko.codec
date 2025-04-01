/*
 */
package org.eclipse.emfcloud.jackson.junit.array;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;

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
 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = ArrayPackage.eNS_URI, genModel = "/model/array.genmodel", genModelSourceLocations = {"model/array.genmodel","org.gecko.codec.test.models/model/array.genmodel"}, ecore="/model/array.ecore", ecoreSourceLocations="/model/array.ecore")
public interface ArrayPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "array";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.emfjson.org/jackson/array";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "array";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArrayPackage eINSTANCE = org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl <em>Host</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl
	 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getArrayHost()
	 * @generated
	 */
	int ARRAY_HOST = 0;

	/**
	 * The feature id for the '<em><b>D1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_HOST__D1 = 0;

	/**
	 * The feature id for the '<em><b>D2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_HOST__D2 = 1;

	/**
	 * The feature id for the '<em><b>D3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_HOST__D3 = 2;

	/**
	 * The feature id for the '<em><b>S2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_HOST__S2 = 3;

	/**
	 * The feature id for the '<em><b>B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_HOST__B = 4;

	/**
	 * The number of structural features of the '<em>Host</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_HOST_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Host</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_HOST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>double1 D</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getdouble1D()
	 * @generated
	 */
	int DOUBLE1_D = 1;

	/**
	 * The meta object id for the '<em>double2 D</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getdouble2D()
	 * @generated
	 */
	int DOUBLE2_D = 2;

	/**
	 * The meta object id for the '<em>double3 D</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getdouble3D()
	 * @generated
	 */
	int DOUBLE3_D = 3;

	/**
	 * The meta object id for the '<em>string2 D</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getstring2D()
	 * @generated
	 */
	int STRING2_D = 4;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost <em>Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Host</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayHost
	 * @generated
	 */
	EClass getArrayHost();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD1 <em>D1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>D1</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD1()
	 * @see #getArrayHost()
	 * @generated
	 */
	EAttribute getArrayHost_D1();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD2 <em>D2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>D2</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD2()
	 * @see #getArrayHost()
	 * @generated
	 */
	EAttribute getArrayHost_D2();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD3 <em>D3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>D3</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD3()
	 * @see #getArrayHost()
	 * @generated
	 */
	EAttribute getArrayHost_D3();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getS2 <em>S2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>S2</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getS2()
	 * @see #getArrayHost()
	 * @generated
	 */
	EAttribute getArrayHost_S2();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getB <em>B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>B</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getB()
	 * @see #getArrayHost()
	 * @generated
	 */
	EAttribute getArrayHost_B();

	/**
	 * Returns the meta object for data type '<em>double1 D</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>double1 D</em>'.
	 * @model instanceClass="java.lang.Double[]"
	 * @generated
	 */
	EDataType getdouble1D();

	/**
	 * Returns the meta object for data type '<em>double2 D</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>double2 D</em>'.
	 * @model instanceClass="java.lang.Double[][]"
	 * @generated
	 */
	EDataType getdouble2D();

	/**
	 * Returns the meta object for data type '<em>double3 D</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>double3 D</em>'.
	 * @model instanceClass="java.lang.Double[][][]"
	 * @generated
	 */
	EDataType getdouble3D();

	/**
	 * Returns the meta object for data type '<em>string2 D</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>string2 D</em>'.
	 * @model instanceClass="java.lang.String[][]"
	 * @generated
	 */
	EDataType getstring2D();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ArrayFactory getArrayFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl <em>Host</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl
		 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getArrayHost()
		 * @generated
		 */
		EClass ARRAY_HOST = eINSTANCE.getArrayHost();

		/**
		 * The meta object literal for the '<em><b>D1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_HOST__D1 = eINSTANCE.getArrayHost_D1();

		/**
		 * The meta object literal for the '<em><b>D2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_HOST__D2 = eINSTANCE.getArrayHost_D2();

		/**
		 * The meta object literal for the '<em><b>D3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_HOST__D3 = eINSTANCE.getArrayHost_D3();

		/**
		 * The meta object literal for the '<em><b>S2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_HOST__S2 = eINSTANCE.getArrayHost_S2();

		/**
		 * The meta object literal for the '<em><b>B</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_HOST__B = eINSTANCE.getArrayHost_B();

		/**
		 * The meta object literal for the '<em>double1 D</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getdouble1D()
		 * @generated
		 */
		EDataType DOUBLE1_D = eINSTANCE.getdouble1D();

		/**
		 * The meta object literal for the '<em>double2 D</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getdouble2D()
		 * @generated
		 */
		EDataType DOUBLE2_D = eINSTANCE.getdouble2D();

		/**
		 * The meta object literal for the '<em>double3 D</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getdouble3D()
		 * @generated
		 */
		EDataType DOUBLE3_D = eINSTANCE.getdouble3D();

		/**
		 * The meta object literal for the '<em>string2 D</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.array.impl.ArrayPackageImpl#getstring2D()
		 * @generated
		 */
		EDataType STRING2_D = eINSTANCE.getstring2D();

	}

} //ArrayPackage
