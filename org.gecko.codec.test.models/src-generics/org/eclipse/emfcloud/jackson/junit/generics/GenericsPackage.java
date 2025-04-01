/*
 */
package org.eclipse.emfcloud.jackson.junit.generics;


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
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = GenericsPackage.eNS_URI, genModel = "/model/generics.genmodel", genModelSourceLocations = {"model/generics.genmodel","org.gecko.codec.test.models/model/generics.genmodel"}, ecore="/model/generics.ecore", ecoreSourceLocations="/model/generics.ecore")
public interface GenericsPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "generics";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.emfjson.org/jackson/generics";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "generics";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenericsPackage eINSTANCE = org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.GenericContainerImpl <em>Generic Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericContainerImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getGenericContainer()
	 * @generated
	 */
	int GENERIC_CONTAINER = 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_CONTAINER__VALUES = 0;

	/**
	 * The number of structural features of the '<em>Generic Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Generic Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.GenericTypeImpl <em>Generic Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericTypeImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getGenericType()
	 * @generated
	 */
	int GENERIC_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TYPE__VALUES = 1;

	/**
	 * The number of structural features of the '<em>Generic Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Generic Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.SpecialTypeOneImpl <em>Special Type One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.SpecialTypeOneImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getSpecialTypeOne()
	 * @generated
	 */
	int SPECIAL_TYPE_ONE = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIAL_TYPE_ONE__VALUE = GENERIC_TYPE__VALUE;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIAL_TYPE_ONE__VALUES = GENERIC_TYPE__VALUES;

	/**
	 * The number of structural features of the '<em>Special Type One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIAL_TYPE_ONE_FEATURE_COUNT = GENERIC_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Special Type One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIAL_TYPE_ONE_OPERATION_COUNT = GENERIC_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.SpecialTypeTwoImpl <em>Special Type Two</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.SpecialTypeTwoImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getSpecialTypeTwo()
	 * @generated
	 */
	int SPECIAL_TYPE_TWO = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIAL_TYPE_TWO__VALUE = GENERIC_TYPE__VALUE;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIAL_TYPE_TWO__VALUES = GENERIC_TYPE__VALUES;

	/**
	 * The number of structural features of the '<em>Special Type Two</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIAL_TYPE_TWO_FEATURE_COUNT = GENERIC_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Special Type Two</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIAL_TYPE_TWO_OPERATION_COUNT = GENERIC_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.BaseImpl <em>Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.BaseImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getBase()
	 * @generated
	 */
	int BASE = 4;

	/**
	 * The feature id for the '<em><b>Link To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE__LINK_TO = 0;

	/**
	 * The feature id for the '<em><b>Link To Many</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE__LINK_TO_MANY = 1;

	/**
	 * The feature id for the '<em><b>Contains One</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE__CONTAINS_ONE = 2;

	/**
	 * The feature id for the '<em><b>Contains Many</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE__CONTAINS_MANY = 3;

	/**
	 * The number of structural features of the '<em>Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.SomeImpl <em>Some</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.SomeImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getSome()
	 * @generated
	 */
	int SOME = 5;

	/**
	 * The number of structural features of the '<em>Some</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOME_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Some</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOME_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.AnyImpl <em>Any</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.AnyImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getAny()
	 * @generated
	 */
	int ANY = 6;

	/**
	 * The number of structural features of the '<em>Any</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Any</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.BaseOneImpl <em>Base One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.BaseOneImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getBaseOne()
	 * @generated
	 */
	int BASE_ONE = 7;

	/**
	 * The feature id for the '<em><b>Link To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ONE__LINK_TO = BASE__LINK_TO;

	/**
	 * The feature id for the '<em><b>Link To Many</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ONE__LINK_TO_MANY = BASE__LINK_TO_MANY;

	/**
	 * The feature id for the '<em><b>Contains One</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ONE__CONTAINS_ONE = BASE__CONTAINS_ONE;

	/**
	 * The feature id for the '<em><b>Contains Many</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ONE__CONTAINS_MANY = BASE__CONTAINS_MANY;

	/**
	 * The number of structural features of the '<em>Base One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ONE_FEATURE_COUNT = BASE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Base One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ONE_OPERATION_COUNT = BASE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.OtherContainerImpl <em>Other Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.OtherContainerImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getOtherContainer()
	 * @generated
	 */
	int OTHER_CONTAINER = 8;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_CONTAINER__KEY = 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_CONTAINER__CONTENT = 1;

	/**
	 * The number of structural features of the '<em>Other Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_CONTAINER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Other Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.Content <em>Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Content
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getContent()
	 * @generated
	 */
	int CONTENT = 9;

	/**
	 * The number of structural features of the '<em>Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.ContentAImpl <em>Content A</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.ContentAImpl
	 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getContentA()
	 * @generated
	 */
	int CONTENT_A = 10;

	/**
	 * The feature id for the '<em><b>Payload</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_A__PAYLOAD = CONTENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Content A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_A_FEATURE_COUNT = CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Content A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_A_OPERATION_COUNT = CONTENT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.GenericContainer <em>Generic Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Container</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericContainer
	 * @generated
	 */
	EClass getGenericContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.generics.GenericContainer#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericContainer#getValues()
	 * @see #getGenericContainer()
	 * @generated
	 */
	EReference getGenericContainer_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.GenericType <em>Generic Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Type</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericType
	 * @generated
	 */
	EClass getGenericType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.generics.GenericType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericType#getValue()
	 * @see #getGenericType()
	 * @generated
	 */
	EAttribute getGenericType_Value();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.generics.GenericType#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericType#getValues()
	 * @see #getGenericType()
	 * @generated
	 */
	EAttribute getGenericType_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeOne <em>Special Type One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Special Type One</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeOne
	 * @generated
	 */
	EClass getSpecialTypeOne();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeTwo <em>Special Type Two</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Special Type Two</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeTwo
	 * @generated
	 */
	EClass getSpecialTypeTwo();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.Base <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Base
	 * @generated
	 */
	EClass getBase();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getLinkTo <em>Link To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Link To</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Base#getLinkTo()
	 * @see #getBase()
	 * @generated
	 */
	EReference getBase_LinkTo();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getLinkToMany <em>Link To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Link To Many</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Base#getLinkToMany()
	 * @see #getBase()
	 * @generated
	 */
	EReference getBase_LinkToMany();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getContainsOne <em>Contains One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Contains One</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Base#getContainsOne()
	 * @see #getBase()
	 * @generated
	 */
	EReference getBase_ContainsOne();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.generics.Base#getContainsMany <em>Contains Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contains Many</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Base#getContainsMany()
	 * @see #getBase()
	 * @generated
	 */
	EReference getBase_ContainsMany();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.Some <em>Some</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Some</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Some
	 * @generated
	 */
	EClass getSome();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.Any <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Any
	 * @generated
	 */
	EClass getAny();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.BaseOne <em>Base One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base One</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.BaseOne
	 * @generated
	 */
	EClass getBaseOne();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.OtherContainer <em>Other Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Other Container</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.OtherContainer
	 * @generated
	 */
	EClass getOtherContainer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.generics.OtherContainer#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.OtherContainer#getKey()
	 * @see #getOtherContainer()
	 * @generated
	 */
	EAttribute getOtherContainer_Key();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.generics.OtherContainer#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Content</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.OtherContainer#getContent()
	 * @see #getOtherContainer()
	 * @generated
	 */
	EReference getOtherContainer_Content();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.Content <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Content</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Content
	 * @generated
	 */
	EClass getContent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.generics.ContentA <em>Content A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Content A</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.ContentA
	 * @generated
	 */
	EClass getContentA();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.generics.ContentA#getPayload <em>Payload</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Payload</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.ContentA#getPayload()
	 * @see #getContentA()
	 * @generated
	 */
	EAttribute getContentA_Payload();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GenericsFactory getGenericsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.GenericContainerImpl <em>Generic Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericContainerImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getGenericContainer()
		 * @generated
		 */
		EClass GENERIC_CONTAINER = eINSTANCE.getGenericContainer();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_CONTAINER__VALUES = eINSTANCE.getGenericContainer_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.GenericTypeImpl <em>Generic Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericTypeImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getGenericType()
		 * @generated
		 */
		EClass GENERIC_TYPE = eINSTANCE.getGenericType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_TYPE__VALUE = eINSTANCE.getGenericType_Value();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_TYPE__VALUES = eINSTANCE.getGenericType_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.SpecialTypeOneImpl <em>Special Type One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.SpecialTypeOneImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getSpecialTypeOne()
		 * @generated
		 */
		EClass SPECIAL_TYPE_ONE = eINSTANCE.getSpecialTypeOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.SpecialTypeTwoImpl <em>Special Type Two</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.SpecialTypeTwoImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getSpecialTypeTwo()
		 * @generated
		 */
		EClass SPECIAL_TYPE_TWO = eINSTANCE.getSpecialTypeTwo();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.BaseImpl <em>Base</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.BaseImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getBase()
		 * @generated
		 */
		EClass BASE = eINSTANCE.getBase();

		/**
		 * The meta object literal for the '<em><b>Link To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE__LINK_TO = eINSTANCE.getBase_LinkTo();

		/**
		 * The meta object literal for the '<em><b>Link To Many</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE__LINK_TO_MANY = eINSTANCE.getBase_LinkToMany();

		/**
		 * The meta object literal for the '<em><b>Contains One</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE__CONTAINS_ONE = eINSTANCE.getBase_ContainsOne();

		/**
		 * The meta object literal for the '<em><b>Contains Many</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE__CONTAINS_MANY = eINSTANCE.getBase_ContainsMany();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.SomeImpl <em>Some</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.SomeImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getSome()
		 * @generated
		 */
		EClass SOME = eINSTANCE.getSome();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.AnyImpl <em>Any</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.AnyImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getAny()
		 * @generated
		 */
		EClass ANY = eINSTANCE.getAny();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.BaseOneImpl <em>Base One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.BaseOneImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getBaseOne()
		 * @generated
		 */
		EClass BASE_ONE = eINSTANCE.getBaseOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.OtherContainerImpl <em>Other Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.OtherContainerImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getOtherContainer()
		 * @generated
		 */
		EClass OTHER_CONTAINER = eINSTANCE.getOtherContainer();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_CONTAINER__KEY = eINSTANCE.getOtherContainer_Key();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OTHER_CONTAINER__CONTENT = eINSTANCE.getOtherContainer_Content();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.Content <em>Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.Content
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getContent()
		 * @generated
		 */
		EClass CONTENT = eINSTANCE.getContent();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.generics.impl.ContentAImpl <em>Content A</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.ContentAImpl
		 * @see org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsPackageImpl#getContentA()
		 * @generated
		 */
		EClass CONTENT_A = eINSTANCE.getContentA();

		/**
		 * The meta object literal for the '<em><b>Payload</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTENT_A__PAYLOAD = eINSTANCE.getContentA_Payload();

	}

} //GenericsPackage
