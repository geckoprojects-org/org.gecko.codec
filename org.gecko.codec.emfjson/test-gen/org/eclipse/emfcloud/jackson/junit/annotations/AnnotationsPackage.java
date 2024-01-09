/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2011/Xcore JsonIgnore='JsonIgnore' JsonProperty='JsonProperty' JsonIgnoreProperties='JsonIgnoreProperties' JsonType='JsonType' JsonIdentity='JsonIdentity' JsonAlias='JsonAlias' JsonRawValue='JsonRawValue'"
 * @generated
 */
@ProviderType
@EPackage(uri = AnnotationsPackage.eNS_URI, genModel = "/../annotations.genmodel", genModelSourceLocations = {"test-data/model/annotations.genmodel","org.gecko.codec.emfjson/test-data/model/annotations.genmodel"}, ecore="/../annotations.ecore", ecoreSourceLocations="/test-data/model/annotations.ecore")
public interface AnnotationsPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "annotations";

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
	String eNS_PREFIX = "annotations";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AnnotationsPackage eINSTANCE = org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestAImpl <em>Test A</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestAImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestA()
	 * @generated
	 */
	int TEST_A = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_A__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Child Of Type C</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_A__CHILD_OF_TYPE_C = 1;

	/**
	 * The feature id for the '<em><b>Children Of Type D</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_A__CHILDREN_OF_TYPE_D = 2;

	/**
	 * The number of structural features of the '<em>Test A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_A_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Test A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_A_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestBImpl <em>Test B</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestBImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestB()
	 * @generated
	 */
	int TEST_B = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_B__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Test B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_B_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Hello</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_B___HELLO = 0;

	/**
	 * The number of operations of the '<em>Test B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_B_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestCImpl <em>Test C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestCImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestC()
	 * @generated
	 */
	int TEST_C = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_C__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Many Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_C__MANY_VALUES = 1;

	/**
	 * The feature id for the '<em><b>One Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_C__ONE_VALUE = 2;

	/**
	 * The number of structural features of the '<em>Test C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_C_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Test C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_C_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestDImpl <em>Test D</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestDImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestD()
	 * @generated
	 */
	int TEST_D = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_D__VALUE = TEST_C__VALUE;

	/**
	 * The feature id for the '<em><b>Many Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_D__MANY_VALUES = TEST_C__MANY_VALUES;

	/**
	 * The feature id for the '<em><b>One Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_D__ONE_VALUE = TEST_C__ONE_VALUE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_D__NAME = TEST_C_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_D__VALUES = TEST_C_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Test D</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_D_FEATURE_COUNT = TEST_C_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Test D</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_D_OPERATION_COUNT = TEST_C_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestEImpl <em>Test E</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestEImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestE()
	 * @generated
	 */
	int TEST_E = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_E__VALUE = TEST_D__VALUE;

	/**
	 * The feature id for the '<em><b>Many Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_E__MANY_VALUES = TEST_D__MANY_VALUES;

	/**
	 * The feature id for the '<em><b>One Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_E__ONE_VALUE = TEST_D__ONE_VALUE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_E__NAME = TEST_D__NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_E__VALUES = TEST_D__VALUES;

	/**
	 * The number of structural features of the '<em>Test E</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_E_FEATURE_COUNT = TEST_D_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Test E</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_E_OPERATION_COUNT = TEST_D_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestFImpl <em>Test F</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestFImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestF()
	 * @generated
	 */
	int TEST_F = 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_F__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Other Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_F__OTHER_VALUE = 1;

	/**
	 * The number of structural features of the '<em>Test F</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_F_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Test F</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_F_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestGImpl <em>Test G</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestGImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestG()
	 * @generated
	 */
	int TEST_G = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_G__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Test G</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_G_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Test G</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_G_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.ContainerImpl <em>Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.ContainerImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 7;

	/**
	 * The feature id for the '<em><b>Typed By Names</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__TYPED_BY_NAMES = 0;

	/**
	 * The feature id for the '<em><b>Typed By Classes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__TYPED_BY_CLASSES = 1;

	/**
	 * The number of structural features of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestTypeNameImpl <em>Test Type Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestTypeNameImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestTypeName()
	 * @generated
	 */
	int TEST_TYPE_NAME = 8;

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
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.FooTypeNameImpl <em>Foo Type Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.FooTypeNameImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getFooTypeName()
	 * @generated
	 */
	int FOO_TYPE_NAME = 9;

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
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.BarTypeNameImpl <em>Bar Type Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.BarTypeNameImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getBarTypeName()
	 * @generated
	 */
	int BAR_TYPE_NAME = 10;

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
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestTypeClassImpl <em>Test Type Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestTypeClassImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestTypeClass()
	 * @generated
	 */
	int TEST_TYPE_CLASS = 11;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_TYPE_CLASS__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Test Type Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_TYPE_CLASS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Test Type Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_TYPE_CLASS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.FooTypeClassImpl <em>Foo Type Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.FooTypeClassImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getFooTypeClass()
	 * @generated
	 */
	int FOO_TYPE_CLASS = 12;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO_TYPE_CLASS__VALUE = TEST_TYPE_CLASS__VALUE;

	/**
	 * The number of structural features of the '<em>Foo Type Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO_TYPE_CLASS_FEATURE_COUNT = TEST_TYPE_CLASS_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Foo Type Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOO_TYPE_CLASS_OPERATION_COUNT = TEST_TYPE_CLASS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.BarTypeClassImpl <em>Bar Type Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.BarTypeClassImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getBarTypeClass()
	 * @generated
	 */
	int BAR_TYPE_CLASS = 13;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_TYPE_CLASS__VALUE = TEST_TYPE_CLASS__VALUE;

	/**
	 * The number of structural features of the '<em>Bar Type Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_TYPE_CLASS_FEATURE_COUNT = TEST_TYPE_CLASS_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Bar Type Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_TYPE_CLASS_OPERATION_COUNT = TEST_TYPE_CLASS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.RawJsonImpl <em>Raw Json</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.RawJsonImpl
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getRawJson()
	 * @generated
	 */
	int RAW_JSON = 14;

	/**
	 * The feature id for the '<em><b>Raw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RAW_JSON__RAW = 0;

	/**
	 * The number of structural features of the '<em>Raw Json</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RAW_JSON_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Raw Json</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RAW_JSON_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA <em>Test A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test A</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestA
	 * @generated
	 */
	EClass getTestA();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestA#getValue()
	 * @see #getTestA()
	 * @generated
	 */
	EAttribute getTestA_Value();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA#getChildOfTypeC <em>Child Of Type C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child Of Type C</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestA#getChildOfTypeC()
	 * @see #getTestA()
	 * @generated
	 */
	EReference getTestA_ChildOfTypeC();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA#getChildrenOfTypeD <em>Children Of Type D</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children Of Type D</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestA#getChildrenOfTypeD()
	 * @see #getTestA()
	 * @generated
	 */
	EReference getTestA_ChildrenOfTypeD();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestB <em>Test B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test B</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestB
	 * @generated
	 */
	EClass getTestB();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestB#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestB#getValue()
	 * @see #getTestB()
	 * @generated
	 */
	EAttribute getTestB_Value();

	/**
	 * Returns the meta object for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestB#hello() <em>Hello</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Hello</em>' operation.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestB#hello()
	 * @generated
	 */
	EOperation getTestB__Hello();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC <em>Test C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test C</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestC
	 * @generated
	 */
	EClass getTestC();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestC#getValue()
	 * @see #getTestC()
	 * @generated
	 */
	EAttribute getTestC_Value();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC#getManyValues <em>Many Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Many Values</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestC#getManyValues()
	 * @see #getTestC()
	 * @generated
	 */
	EReference getTestC_ManyValues();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC#getOneValue <em>One Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>One Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestC#getOneValue()
	 * @see #getTestC()
	 * @generated
	 */
	EReference getTestC_OneValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestD <em>Test D</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test D</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestD
	 * @generated
	 */
	EClass getTestD();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestD#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestD#getName()
	 * @see #getTestD()
	 * @generated
	 */
	EAttribute getTestD_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestD#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestD#getValues()
	 * @see #getTestD()
	 * @generated
	 */
	EReference getTestD_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestE <em>Test E</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test E</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestE
	 * @generated
	 */
	EClass getTestE();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestF <em>Test F</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test F</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestF
	 * @generated
	 */
	EClass getTestF();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestF#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestF#getValue()
	 * @see #getTestF()
	 * @generated
	 */
	EAttribute getTestF_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestF#getOtherValue <em>Other Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Other Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestF#getOtherValue()
	 * @see #getTestF()
	 * @generated
	 */
	EAttribute getTestF_OtherValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestG <em>Test G</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test G</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestG
	 * @generated
	 */
	EClass getTestG();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestG#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestG#getValue()
	 * @see #getTestG()
	 * @generated
	 */
	EAttribute getTestG_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.Container
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.annotations.Container#getTypedByNames <em>Typed By Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Typed By Names</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.Container#getTypedByNames()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_TypedByNames();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.annotations.Container#getTypedByClasses <em>Typed By Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Typed By Classes</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.Container#getTypedByClasses()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_TypedByClasses();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName <em>Test Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Type Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName
	 * @generated
	 */
	EClass getTestTypeName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName#getValue()
	 * @see #getTestTypeName()
	 * @generated
	 */
	EAttribute getTestTypeName_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.FooTypeName <em>Foo Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Foo Type Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.FooTypeName
	 * @generated
	 */
	EClass getFooTypeName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.BarTypeName <em>Bar Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bar Type Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.BarTypeName
	 * @generated
	 */
	EClass getBarTypeName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass <em>Test Type Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Type Class</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass
	 * @generated
	 */
	EClass getTestTypeClass();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass#getValue()
	 * @see #getTestTypeClass()
	 * @generated
	 */
	EAttribute getTestTypeClass_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.FooTypeClass <em>Foo Type Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Foo Type Class</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.FooTypeClass
	 * @generated
	 */
	EClass getFooTypeClass();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.BarTypeClass <em>Bar Type Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bar Type Class</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.BarTypeClass
	 * @generated
	 */
	EClass getBarTypeClass();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.annotations.RawJson <em>Raw Json</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Raw Json</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.RawJson
	 * @generated
	 */
	EClass getRawJson();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.annotations.RawJson#getRaw <em>Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Raw</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.RawJson#getRaw()
	 * @see #getRawJson()
	 * @generated
	 */
	EAttribute getRawJson_Raw();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AnnotationsFactory getAnnotationsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestAImpl <em>Test A</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestAImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestA()
		 * @generated
		 */
		EClass TEST_A = eINSTANCE.getTestA();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_A__VALUE = eINSTANCE.getTestA_Value();

		/**
		 * The meta object literal for the '<em><b>Child Of Type C</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_A__CHILD_OF_TYPE_C = eINSTANCE.getTestA_ChildOfTypeC();

		/**
		 * The meta object literal for the '<em><b>Children Of Type D</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_A__CHILDREN_OF_TYPE_D = eINSTANCE.getTestA_ChildrenOfTypeD();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestBImpl <em>Test B</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestBImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestB()
		 * @generated
		 */
		EClass TEST_B = eINSTANCE.getTestB();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_B__VALUE = eINSTANCE.getTestB_Value();

		/**
		 * The meta object literal for the '<em><b>Hello</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TEST_B___HELLO = eINSTANCE.getTestB__Hello();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestCImpl <em>Test C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestCImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestC()
		 * @generated
		 */
		EClass TEST_C = eINSTANCE.getTestC();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_C__VALUE = eINSTANCE.getTestC_Value();

		/**
		 * The meta object literal for the '<em><b>Many Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_C__MANY_VALUES = eINSTANCE.getTestC_ManyValues();

		/**
		 * The meta object literal for the '<em><b>One Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_C__ONE_VALUE = eINSTANCE.getTestC_OneValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestDImpl <em>Test D</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestDImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestD()
		 * @generated
		 */
		EClass TEST_D = eINSTANCE.getTestD();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_D__NAME = eINSTANCE.getTestD_Name();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_D__VALUES = eINSTANCE.getTestD_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestEImpl <em>Test E</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestEImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestE()
		 * @generated
		 */
		EClass TEST_E = eINSTANCE.getTestE();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestFImpl <em>Test F</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestFImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestF()
		 * @generated
		 */
		EClass TEST_F = eINSTANCE.getTestF();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_F__VALUE = eINSTANCE.getTestF_Value();

		/**
		 * The meta object literal for the '<em><b>Other Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_F__OTHER_VALUE = eINSTANCE.getTestF_OtherValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestGImpl <em>Test G</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestGImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestG()
		 * @generated
		 */
		EClass TEST_G = eINSTANCE.getTestG();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_G__VALUE = eINSTANCE.getTestG_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.ContainerImpl <em>Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.ContainerImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getContainer()
		 * @generated
		 */
		EClass CONTAINER = eINSTANCE.getContainer();

		/**
		 * The meta object literal for the '<em><b>Typed By Names</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER__TYPED_BY_NAMES = eINSTANCE.getContainer_TypedByNames();

		/**
		 * The meta object literal for the '<em><b>Typed By Classes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER__TYPED_BY_CLASSES = eINSTANCE.getContainer_TypedByClasses();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestTypeNameImpl <em>Test Type Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestTypeNameImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestTypeName()
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
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.FooTypeNameImpl <em>Foo Type Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.FooTypeNameImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getFooTypeName()
		 * @generated
		 */
		EClass FOO_TYPE_NAME = eINSTANCE.getFooTypeName();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.BarTypeNameImpl <em>Bar Type Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.BarTypeNameImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getBarTypeName()
		 * @generated
		 */
		EClass BAR_TYPE_NAME = eINSTANCE.getBarTypeName();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestTypeClassImpl <em>Test Type Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.TestTypeClassImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getTestTypeClass()
		 * @generated
		 */
		EClass TEST_TYPE_CLASS = eINSTANCE.getTestTypeClass();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_TYPE_CLASS__VALUE = eINSTANCE.getTestTypeClass_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.FooTypeClassImpl <em>Foo Type Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.FooTypeClassImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getFooTypeClass()
		 * @generated
		 */
		EClass FOO_TYPE_CLASS = eINSTANCE.getFooTypeClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.BarTypeClassImpl <em>Bar Type Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.BarTypeClassImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getBarTypeClass()
		 * @generated
		 */
		EClass BAR_TYPE_CLASS = eINSTANCE.getBarTypeClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.RawJsonImpl <em>Raw Json</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.RawJsonImpl
		 * @see org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsPackageImpl#getRawJson()
		 * @generated
		 */
		EClass RAW_JSON = eINSTANCE.getRawJson();

		/**
		 * The meta object literal for the '<em><b>Raw</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RAW_JSON__RAW = eINSTANCE.getRawJson_Raw();

	}

} //AnnotationsPackage
