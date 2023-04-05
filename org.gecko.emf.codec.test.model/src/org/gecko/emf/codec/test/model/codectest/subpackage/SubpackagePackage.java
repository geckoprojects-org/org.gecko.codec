/*
 */
package org.gecko.emf.codec.test.model.codectest.subpackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackageFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
public interface SubpackagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "subpackage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://example.de/codectest/subpackage/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sb";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SubpackagePackage eINSTANCE = org.gecko.emf.codec.test.model.codectest.subpackage.impl.SubpackagePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl <em>Example</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.impl.SubpackagePackageImpl#getExample()
	 * @generated
	 */
	int EXAMPLE = 0;

	/**
	 * The feature id for the '<em><b>Example Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__EXAMPLE_ID = 0;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__MANDATORY = 1;

	/**
	 * The feature id for the '<em><b>Mandatory Multiple</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__MANDATORY_MULTIPLE = 2;

	/**
	 * The feature id for the '<em><b>Multiple01</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__MULTIPLE01 = 3;

	/**
	 * The feature id for the '<em><b>Multiple02</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__MULTIPLE02 = 4;

	/**
	 * The feature id for the '<em><b>Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__DERIVED = 5;

	/**
	 * The feature id for the '<em><b>Containment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__CONTAINMENT = 6;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__TRANSIENT = 7;

	/**
	 * The feature id for the '<em><b>Big Long</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__BIG_LONG = 8;

	/**
	 * The feature id for the '<em><b>Big Double</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__BIG_DOUBLE = 9;

	/**
	 * The feature id for the '<em><b>Big Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__BIG_INTEGER = 10;

	/**
	 * The feature id for the '<em><b>Big Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__BIG_FLOAT = 11;

	/**
	 * The feature id for the '<em><b>Big Char</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__BIG_CHAR = 12;

	/**
	 * The feature id for the '<em><b>Big Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__BIG_BOOL = 13;

	/**
	 * The feature id for the '<em><b>Big Short</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__BIG_SHORT = 14;

	/**
	 * The feature id for the '<em><b>Big Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__BIG_BYTE = 15;

	/**
	 * The feature id for the '<em><b>Small Long</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__SMALL_LONG = 16;

	/**
	 * The feature id for the '<em><b>Small Double</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__SMALL_DOUBLE = 17;

	/**
	 * The feature id for the '<em><b>Small Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__SMALL_INTEGER = 18;

	/**
	 * The feature id for the '<em><b>Small Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__SMALL_FLOAT = 19;

	/**
	 * The feature id for the '<em><b>Small Char</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__SMALL_CHAR = 20;

	/**
	 * The feature id for the '<em><b>Small Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__SMALL_BOOL = 21;

	/**
	 * The feature id for the '<em><b>Small Short</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__SMALL_SHORT = 22;

	/**
	 * The feature id for the '<em><b>Small Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE__SMALL_BYTE = 23;

	/**
	 * The number of structural features of the '<em>Example</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE_FEATURE_COUNT = 24;

	/**
	 * The number of operations of the '<em>Example</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAMPLE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example <em>Example</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Example</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example
	 * @generated
	 */
	EClass getExample();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getExampleId <em>Example Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Example Id</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getExampleId()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_ExampleId();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMandatory()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_Mandatory();

	/**
	 * Returns the meta object for the attribute list '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMandatoryMultiple <em>Mandatory Multiple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mandatory Multiple</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMandatoryMultiple()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_MandatoryMultiple();

	/**
	 * Returns the meta object for the attribute list '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMultiple01 <em>Multiple01</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Multiple01</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMultiple01()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_Multiple01();

	/**
	 * Returns the meta object for the attribute list '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMultiple02 <em>Multiple02</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Multiple02</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMultiple02()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_Multiple02();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getDerived <em>Derived</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Derived</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getDerived()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_Derived();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getContainment <em>Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Containment</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getContainment()
	 * @see #getExample()
	 * @generated
	 */
	EReference getExample_Containment();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getTransient <em>Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transient</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getTransient()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_Transient();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigLong <em>Big Long</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Big Long</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigLong()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_BigLong();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigDouble <em>Big Double</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Big Double</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigDouble()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_BigDouble();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigInteger <em>Big Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Big Integer</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigInteger()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_BigInteger();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigFloat <em>Big Float</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Big Float</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigFloat()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_BigFloat();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigChar <em>Big Char</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Big Char</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigChar()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_BigChar();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigBool <em>Big Bool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Big Bool</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigBool()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_BigBool();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigShort <em>Big Short</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Big Short</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigShort()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_BigShort();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigByte <em>Big Byte</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Big Byte</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigByte()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_BigByte();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallLong <em>Small Long</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Long</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallLong()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_SmallLong();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallDouble <em>Small Double</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Double</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallDouble()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_SmallDouble();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallInteger <em>Small Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Integer</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallInteger()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_SmallInteger();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallFloat <em>Small Float</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Float</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallFloat()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_SmallFloat();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallChar <em>Small Char</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Char</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallChar()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_SmallChar();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#isSmallBool <em>Small Bool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Bool</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#isSmallBool()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_SmallBool();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallShort <em>Small Short</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Short</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallShort()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_SmallShort();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallByte <em>Small Byte</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Byte</em>'.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallByte()
	 * @see #getExample()
	 * @generated
	 */
	EAttribute getExample_SmallByte();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SubpackageFactory getSubpackageFactory();

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
		 * The meta object literal for the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl <em>Example</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl
		 * @see org.gecko.emf.codec.test.model.codectest.subpackage.impl.SubpackagePackageImpl#getExample()
		 * @generated
		 */
		EClass EXAMPLE = eINSTANCE.getExample();

		/**
		 * The meta object literal for the '<em><b>Example Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__EXAMPLE_ID = eINSTANCE.getExample_ExampleId();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__MANDATORY = eINSTANCE.getExample_Mandatory();

		/**
		 * The meta object literal for the '<em><b>Mandatory Multiple</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__MANDATORY_MULTIPLE = eINSTANCE.getExample_MandatoryMultiple();

		/**
		 * The meta object literal for the '<em><b>Multiple01</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__MULTIPLE01 = eINSTANCE.getExample_Multiple01();

		/**
		 * The meta object literal for the '<em><b>Multiple02</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__MULTIPLE02 = eINSTANCE.getExample_Multiple02();

		/**
		 * The meta object literal for the '<em><b>Derived</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__DERIVED = eINSTANCE.getExample_Derived();

		/**
		 * The meta object literal for the '<em><b>Containment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXAMPLE__CONTAINMENT = eINSTANCE.getExample_Containment();

		/**
		 * The meta object literal for the '<em><b>Transient</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__TRANSIENT = eINSTANCE.getExample_Transient();

		/**
		 * The meta object literal for the '<em><b>Big Long</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__BIG_LONG = eINSTANCE.getExample_BigLong();

		/**
		 * The meta object literal for the '<em><b>Big Double</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__BIG_DOUBLE = eINSTANCE.getExample_BigDouble();

		/**
		 * The meta object literal for the '<em><b>Big Integer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__BIG_INTEGER = eINSTANCE.getExample_BigInteger();

		/**
		 * The meta object literal for the '<em><b>Big Float</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__BIG_FLOAT = eINSTANCE.getExample_BigFloat();

		/**
		 * The meta object literal for the '<em><b>Big Char</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__BIG_CHAR = eINSTANCE.getExample_BigChar();

		/**
		 * The meta object literal for the '<em><b>Big Bool</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__BIG_BOOL = eINSTANCE.getExample_BigBool();

		/**
		 * The meta object literal for the '<em><b>Big Short</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__BIG_SHORT = eINSTANCE.getExample_BigShort();

		/**
		 * The meta object literal for the '<em><b>Big Byte</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__BIG_BYTE = eINSTANCE.getExample_BigByte();

		/**
		 * The meta object literal for the '<em><b>Small Long</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__SMALL_LONG = eINSTANCE.getExample_SmallLong();

		/**
		 * The meta object literal for the '<em><b>Small Double</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__SMALL_DOUBLE = eINSTANCE.getExample_SmallDouble();

		/**
		 * The meta object literal for the '<em><b>Small Integer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__SMALL_INTEGER = eINSTANCE.getExample_SmallInteger();

		/**
		 * The meta object literal for the '<em><b>Small Float</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__SMALL_FLOAT = eINSTANCE.getExample_SmallFloat();

		/**
		 * The meta object literal for the '<em><b>Small Char</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__SMALL_CHAR = eINSTANCE.getExample_SmallChar();

		/**
		 * The meta object literal for the '<em><b>Small Bool</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__SMALL_BOOL = eINSTANCE.getExample_SmallBool();

		/**
		 * The meta object literal for the '<em><b>Small Short</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__SMALL_SHORT = eINSTANCE.getExample_SmallShort();

		/**
		 * The meta object literal for the '<em><b>Small Byte</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAMPLE__SMALL_BYTE = eINSTANCE.getExample_SmallByte();

	}

} //SubpackagePackage
