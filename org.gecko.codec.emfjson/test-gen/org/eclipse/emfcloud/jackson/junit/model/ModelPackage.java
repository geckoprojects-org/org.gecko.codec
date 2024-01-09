/*
 */
package org.eclipse.emfcloud.jackson.junit.model;


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
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = ModelPackage.eNS_URI, genModel = "/../model.genmodel", genModelSourceLocations = {"test-data/model/model.genmodel","org.gecko.codec.emfjson/test-data/model/model.genmodel"}, ecore="/../model.ecore", ecoreSourceLocations="/test-data/model/model.ecore")
public interface ModelPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.emfjson.org/jackson/model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.UserImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getUser()
	 * @generated
	 */
	int USER = 0;

	/**
	 * The feature id for the '<em><b>User Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__USER_ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__NAME = 1;

	/**
	 * The feature id for the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__BIRTH_DATE = 2;

	/**
	 * The feature id for the '<em><b>Sex</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__SEX = 3;

	/**
	 * The feature id for the '<em><b>Friends</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__FRIENDS = 4;

	/**
	 * The feature id for the '<em><b>Unique Friend</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__UNIQUE_FRIEND = 5;

	/**
	 * The feature id for the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__ADDRESS = 6;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.AddressImpl <em>Address</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.AddressImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getAddress()
	 * @generated
	 */
	int ADDRESS = 1;

	/**
	 * The feature id for the '<em><b>Add Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__ADD_ID = 0;

	/**
	 * The feature id for the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__CITY = 1;

	/**
	 * The feature id for the '<em><b>Street</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDRESS__STREET = 2;

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
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl <em>ETypes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getETypes()
	 * @generated
	 */
	int ETYPES = 2;

	/**
	 * The feature id for the '<em><b>EString</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__ESTRING = 0;

	/**
	 * The feature id for the '<em><b>EStrings</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__ESTRINGS = 1;

	/**
	 * The feature id for the '<em><b>EBoolean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EBOOLEAN = 2;

	/**
	 * The feature id for the '<em><b>EBooleans</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EBOOLEANS = 3;

	/**
	 * The feature id for the '<em><b>EInt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EINT = 4;

	/**
	 * The feature id for the '<em><b>EInts</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EINTS = 5;

	/**
	 * The feature id for the '<em><b>Double Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__DOUBLE_VALUE = 6;

	/**
	 * The feature id for the '<em><b>EDouble</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EDOUBLE = 7;

	/**
	 * The feature id for the '<em><b>EDoubles</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EDOUBLES = 8;

	/**
	 * The feature id for the '<em><b>EBig Decimal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EBIG_DECIMAL = 9;

	/**
	 * The feature id for the '<em><b>EBig Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EBIG_INTEGER = 10;

	/**
	 * The feature id for the '<em><b>EByte</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EBYTE = 11;

	/**
	 * The feature id for the '<em><b>EByte Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EBYTE_ARRAY = 12;

	/**
	 * The feature id for the '<em><b>EChar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__ECHAR = 13;

	/**
	 * The feature id for the '<em><b>EDate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EDATE = 14;

	/**
	 * The feature id for the '<em><b>EFloat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__EFLOAT = 15;

	/**
	 * The feature id for the '<em><b>ELong</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__ELONG = 16;

	/**
	 * The feature id for the '<em><b>EShort</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__ESHORT = 17;

	/**
	 * The feature id for the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__OBJECT_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Object Type Many</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__OBJECT_TYPE_MANY = 19;

	/**
	 * The feature id for the '<em><b>Object Array Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__OBJECT_ARRAY_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Object Array Type Many</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__OBJECT_ARRAY_TYPE_MANY = 21;

	/**
	 * The feature id for the '<em><b>Values</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__VALUES = 22;

	/**
	 * The feature id for the '<em><b>Values With Ref</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__VALUES_WITH_REF = 23;

	/**
	 * The feature id for the '<em><b>String Map Values</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__STRING_MAP_VALUES = 24;

	/**
	 * The feature id for the '<em><b>Data Type Map Values</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__DATA_TYPE_MAP_VALUES = 25;

	/**
	 * The feature id for the '<em><b>Uris</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES__URIS = 26;

	/**
	 * The number of structural features of the '<em>ETypes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES_FEATURE_COUNT = 27;

	/**
	 * The number of operations of the '<em>ETypes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.StringMapImpl <em>String Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.StringMapImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getStringMap()
	 * @generated
	 */
	int STRING_MAP = 3;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.TMapImpl <em>TMap</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.TMapImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getTMap()
	 * @generated
	 */
	int TMAP = 4;

	/**
	 * The feature id for the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>TMap</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TMap</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.TMapRefImpl <em>TMap Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.TMapRefImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getTMapRef()
	 * @generated
	 */
	int TMAP_REF = 5;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMAP_REF__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMAP_REF__VALUE = 1;

	/**
	 * The number of structural features of the '<em>TMap Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMAP_REF_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TMap Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TMAP_REF_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.TypeImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ValueImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.DataTypeMapImpl <em>Data Type Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.DataTypeMapImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getDataTypeMap()
	 * @generated
	 */
	int DATA_TYPE_MAP = 8;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Data Type Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Data Type Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl <em>Primary Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getPrimaryObject()
	 * @generated
	 */
	int PRIMARY_OBJECT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Id Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__ID_ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__KIND = 2;

	/**
	 * The feature id for the '<em><b>Unsettable Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Unsettable Attribute With Non Null Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT = 4;

	/**
	 * The feature id for the '<em><b>Unsettable Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__UNSETTABLE_REFERENCE = 5;

	/**
	 * The feature id for the '<em><b>Single Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__SINGLE_REFERENCE = 6;

	/**
	 * The feature id for the '<em><b>Many References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__MANY_REFERENCES = 7;

	/**
	 * The feature id for the '<em><b>Single Reference Not Resolve Proxies</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES = 8;

	/**
	 * The feature id for the '<em><b>Many Reference Not Resolve Proxies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__MANY_REFERENCE_NOT_RESOLVE_PROXIES = 9;

	/**
	 * The feature id for the '<em><b>Containment Reference Same Collection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION = 10;

	/**
	 * The feature id for the '<em><b>Single Containment Reference No Proxies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES = 11;

	/**
	 * The feature id for the '<em><b>Multiple Containment Reference No Proxies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_NO_PROXIES = 12;

	/**
	 * The feature id for the '<em><b>Single Containment Reference Proxies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES = 13;

	/**
	 * The feature id for the '<em><b>Multiple Containment Reference Proxies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_PROXIES = 14;

	/**
	 * The feature id for the '<em><b>Feature Map Reference Collection</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_COLLECTION = 15;

	/**
	 * The feature id for the '<em><b>Feature Map Reference Type1</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE1 = 16;

	/**
	 * The feature id for the '<em><b>Feature Map Reference Type2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE2 = 17;

	/**
	 * The feature id for the '<em><b>Feature Map Attribute Collection</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_COLLECTION = 18;

	/**
	 * The feature id for the '<em><b>Feature Map Attribute Type1</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE1 = 19;

	/**
	 * The feature id for the '<em><b>Feature Map Attribute Type2</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE2 = 20;

	/**
	 * The number of structural features of the '<em>Primary Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT_FEATURE_COUNT = 21;

	/**
	 * The number of operations of the '<em>Primary Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ContainerImpl <em>Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ContainerImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 10;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__ELEMENTS = 0;

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
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl <em>Target Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getTargetObject()
	 * @generated
	 */
	int TARGET_OBJECT = 11;

	/**
	 * The feature id for the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OBJECT__SINGLE_ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Array Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OBJECT__ARRAY_ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Single Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OBJECT__SINGLE_REFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Many References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OBJECT__MANY_REFERENCES = 3;

	/**
	 * The feature id for the '<em><b>Single Reference Not Resolve Proxies</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES = 4;

	/**
	 * The feature id for the '<em><b>Many References Not Resolve Proxies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OBJECT__MANY_REFERENCES_NOT_RESOLVE_PROXIES = 5;

	/**
	 * The number of structural features of the '<em>Target Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OBJECT_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Target Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractTypeImpl <em>Abstract Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.AbstractTypeImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getAbstractType()
	 * @generated
	 */
	int ABSTRACT_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Ref Property</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE__REF_PROPERTY = 1;

	/**
	 * The number of structural features of the '<em>Abstract Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Abstract Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeOneImpl <em>Concrete Type One</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeOneImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getConcreteTypeOne()
	 * @generated
	 */
	int CONCRETE_TYPE_ONE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_ONE__NAME = ABSTRACT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Ref Property</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_ONE__REF_PROPERTY = ABSTRACT_TYPE__REF_PROPERTY;

	/**
	 * The feature id for the '<em><b>Prop Type One</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_ONE__PROP_TYPE_ONE = ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Concrete Type One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_ONE_FEATURE_COUNT = ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Concrete Type One</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_ONE_OPERATION_COUNT = ABSTRACT_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeTwoImpl <em>Concrete Type Two</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeTwoImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getConcreteTypeTwo()
	 * @generated
	 */
	int CONCRETE_TYPE_TWO = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_TWO__NAME = ABSTRACT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Ref Property</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_TWO__REF_PROPERTY = ABSTRACT_TYPE__REF_PROPERTY;

	/**
	 * The feature id for the '<em><b>Prop Type Two</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_TWO__PROP_TYPE_TWO = ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Concrete Type Two</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_TWO_FEATURE_COUNT = ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Concrete Type Two</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_TYPE_TWO_OPERATION_COUNT = ABSTRACT_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.NodeImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 15;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Many Ref</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__MANY_REF = 3;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__CHILD = 4;

	/**
	 * The feature id for the '<em><b>Unique Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__UNIQUE_CHILD = 5;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl <em>Abstract Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getAbstractNode()
	 * @generated
	 */
	int ABSTRACT_NODE = 16;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NODE__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NODE__CHILD = 1;

	/**
	 * The feature id for the '<em><b>Child Resolving</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NODE__CHILD_RESOLVING = 2;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NODE__PARENT = 3;

	/**
	 * The feature id for the '<em><b>Parent Resolving</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NODE__PARENT_RESOLVING = 4;

	/**
	 * The number of structural features of the '<em>Abstract Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NODE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Abstract Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NODE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.PhysicalNodeImpl <em>Physical Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.PhysicalNodeImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getPhysicalNode()
	 * @generated
	 */
	int PHYSICAL_NODE = 17;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE__LABEL = ABSTRACT_NODE__LABEL;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE__CHILD = ABSTRACT_NODE__CHILD;

	/**
	 * The feature id for the '<em><b>Child Resolving</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE__CHILD_RESOLVING = ABSTRACT_NODE__CHILD_RESOLVING;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE__PARENT = ABSTRACT_NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Parent Resolving</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE__PARENT_RESOLVING = ABSTRACT_NODE__PARENT_RESOLVING;

	/**
	 * The number of structural features of the '<em>Physical Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE_FEATURE_COUNT = ABSTRACT_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Physical Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE_OPERATION_COUNT = ABSTRACT_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.VirtualNodeImpl <em>Virtual Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.VirtualNodeImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getVirtualNode()
	 * @generated
	 */
	int VIRTUAL_NODE = 18;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NODE__LABEL = ABSTRACT_NODE__LABEL;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NODE__CHILD = ABSTRACT_NODE__CHILD;

	/**
	 * The feature id for the '<em><b>Child Resolving</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NODE__CHILD_RESOLVING = ABSTRACT_NODE__CHILD_RESOLVING;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NODE__PARENT = ABSTRACT_NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Parent Resolving</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NODE__PARENT_RESOLVING = ABSTRACT_NODE__PARENT_RESOLVING;

	/**
	 * The number of structural features of the '<em>Virtual Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NODE_FEATURE_COUNT = ABSTRACT_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Virtual Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NODE_OPERATION_COUNT = ABSTRACT_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.BookImpl <em>Book</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.BookImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getBook()
	 * @generated
	 */
	int BOOK = 19;

	/**
	 * The feature id for the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__AUTHOR = 0;

	/**
	 * The feature id for the '<em><b>Author Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__AUTHOR_NAME = 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK__TITLE = 2;

	/**
	 * The number of structural features of the '<em>Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.AuthorImpl <em>Author</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.AuthorImpl
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getAuthor()
	 * @generated
	 */
	int AUTHOR = 20;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__FIRST_NAME = 0;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__LAST_NAME = 1;

	/**
	 * The number of structural features of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.Sex <em>Sex</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.Sex
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getSex()
	 * @generated
	 */
	int SEX = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.emfcloud.jackson.junit.model.SomeKind <em>Some Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.SomeKind
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getSomeKind()
	 * @generated
	 */
	int SOME_KIND = 22;

	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getURI()
	 * @generated
	 */
	int URI = 23;

	/**
	 * The meta object id for the '<em>User Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getUserType()
	 * @generated
	 */
	int USER_TYPE = 24;

	/**
	 * The meta object id for the '<em>Object Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getObjectType()
	 * @generated
	 */
	int OBJECT_TYPE = 25;

	/**
	 * The meta object id for the '<em>Object Array Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getObjectArrayType()
	 * @generated
	 */
	int OBJECT_ARRAY_TYPE = 26;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.User#getUserId <em>User Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Id</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.User#getUserId()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_UserId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.User#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.User#getName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.User#getBirthDate <em>Birth Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Birth Date</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.User#getBirthDate()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_BirthDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.User#getSex <em>Sex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sex</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.User#getSex()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Sex();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.model.User#getFriends <em>Friends</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Friends</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.User#getFriends()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_Friends();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emfcloud.jackson.junit.model.User#getUniqueFriend <em>Unique Friend</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Unique Friend</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.User#getUniqueFriend()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_UniqueFriend();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.model.User#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Address</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.User#getAddress()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_Address();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.Address <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Address</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Address
	 * @generated
	 */
	EClass getAddress();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Address#getAddId <em>Add Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Add Id</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Address#getAddId()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_AddId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Address#getCity <em>City</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>City</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Address#getCity()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_City();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Address#getStreet <em>Street</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Street</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Address#getStreet()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Street();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Address#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Address#getNumber()
	 * @see #getAddress()
	 * @generated
	 */
	EAttribute getAddress_Number();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes <em>ETypes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ETypes</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes
	 * @generated
	 */
	EClass getETypes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEString <em>EString</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EString</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEString()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EString();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEStrings <em>EStrings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>EStrings</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEStrings()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EStrings();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#isEBoolean <em>EBoolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EBoolean</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#isEBoolean()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EBoolean();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBooleans <em>EBooleans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>EBooleans</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBooleans()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EBooleans();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEInt <em>EInt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EInt</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEInt()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EInt();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEInts <em>EInts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>EInts</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEInts()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EInts();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getDoubleValue <em>Double Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getDoubleValue()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_DoubleValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDouble <em>EDouble</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EDouble</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDouble()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EDouble();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDoubles <em>EDoubles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>EDoubles</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDoubles()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EDoubles();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBigDecimal <em>EBig Decimal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EBig Decimal</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBigDecimal()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EBigDecimal();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBigInteger <em>EBig Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EBig Integer</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBigInteger()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EBigInteger();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEByte <em>EByte</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EByte</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEByte()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EByte();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEByteArray <em>EByte Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EByte Array</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEByteArray()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EByteArray();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEChar <em>EChar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EChar</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEChar()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EChar();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDate <em>EDate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EDate</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDate()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEFloat <em>EFloat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EFloat</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEFloat()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EFloat();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getELong <em>ELong</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ELong</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getELong()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_ELong();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEShort <em>EShort</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>EShort</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getEShort()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_EShort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Type</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectType()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_ObjectType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectTypeMany <em>Object Type Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Object Type Many</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectTypeMany()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_ObjectTypeMany();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectArrayType <em>Object Array Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Array Type</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectArrayType()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_ObjectArrayType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectArrayTypeMany <em>Object Array Type Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Object Array Type Many</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectArrayTypeMany()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_ObjectArrayTypeMany();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Values</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getValues()
	 * @see #getETypes()
	 * @generated
	 */
	EReference getETypes_Values();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getValuesWithRef <em>Values With Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Values With Ref</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getValuesWithRef()
	 * @see #getETypes()
	 * @generated
	 */
	EReference getETypes_ValuesWithRef();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getStringMapValues <em>String Map Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>String Map Values</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getStringMapValues()
	 * @see #getETypes()
	 * @generated
	 */
	EReference getETypes_StringMapValues();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getDataTypeMapValues <em>Data Type Map Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Data Type Map Values</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getDataTypeMapValues()
	 * @see #getETypes()
	 * @generated
	 */
	EReference getETypes_DataTypeMapValues();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getUris <em>Uris</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Uris</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ETypes#getUris()
	 * @see #getETypes()
	 * @generated
	 */
	EAttribute getETypes_Uris();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyUnique="false" keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.emfcloud.jackson.junit.model.Value" valueContainment="true"
	 * @generated
	 */
	EClass getStringMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringMap()
	 * @generated
	 */
	EAttribute getStringMap_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringMap()
	 * @generated
	 */
	EReference getStringMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>TMap</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TMap</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emfcloud.jackson.junit.model.Type" keyContainment="true"
	 *        valueType="org.eclipse.emfcloud.jackson.junit.model.Value" valueContainment="true"
	 * @generated
	 */
	EClass getTMap();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTMap()
	 * @generated
	 */
	EReference getTMap_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTMap()
	 * @generated
	 */
	EReference getTMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>TMap Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TMap Ref</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emfcloud.jackson.junit.model.PrimaryObject"
	 *        valueType="org.eclipse.emfcloud.jackson.junit.model.TargetObject"
	 * @generated
	 */
	EClass getTMapRef();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTMapRef()
	 * @generated
	 */
	EReference getTMapRef_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getTMapRef()
	 * @generated
	 */
	EReference getTMapRef_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Type#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Type#getName()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Value#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Value#getValue()
	 * @see #getValue()
	 * @generated
	 */
	EAttribute getValue_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Data Type Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyUnique="false" keyDataType="org.eclipse.emfcloud.jackson.junit.model.UserType"
	 *        valueUnique="false" valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getDataTypeMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getDataTypeMap()
	 * @generated
	 */
	EAttribute getDataTypeMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getDataTypeMap()
	 * @generated
	 */
	EAttribute getDataTypeMap_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject <em>Primary Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primary Object</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject
	 * @generated
	 */
	EClass getPrimaryObject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getName()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getIdAttribute <em>Id Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id Attribute</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getIdAttribute()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_IdAttribute();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getKind()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttribute <em>Unsettable Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unsettable Attribute</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttribute()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_UnsettableAttribute();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttributeWithNonNullDefault <em>Unsettable Attribute With Non Null Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unsettable Attribute With Non Null Default</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttributeWithNonNullDefault()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_UnsettableAttributeWithNonNullDefault();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableReference <em>Unsettable Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Unsettable Reference</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableReference()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_UnsettableReference();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleReference <em>Single Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Single Reference</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleReference()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_SingleReference();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getManyReferences <em>Many References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Many References</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getManyReferences()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_ManyReferences();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Single Reference Not Resolve Proxies</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleReferenceNotResolveProxies()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_SingleReferenceNotResolveProxies();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getManyReferenceNotResolveProxies <em>Many Reference Not Resolve Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Many Reference Not Resolve Proxies</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getManyReferenceNotResolveProxies()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_ManyReferenceNotResolveProxies();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getContainmentReferenceSameCollection <em>Containment Reference Same Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Containment Reference Same Collection</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getContainmentReferenceSameCollection()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_ContainmentReferenceSameCollection();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceNoProxies <em>Single Containment Reference No Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Single Containment Reference No Proxies</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceNoProxies()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_SingleContainmentReferenceNoProxies();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getMultipleContainmentReferenceNoProxies <em>Multiple Containment Reference No Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Multiple Containment Reference No Proxies</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getMultipleContainmentReferenceNoProxies()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_MultipleContainmentReferenceNoProxies();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceProxies <em>Single Containment Reference Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Single Containment Reference Proxies</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceProxies()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_SingleContainmentReferenceProxies();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getMultipleContainmentReferenceProxies <em>Multiple Containment Reference Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Multiple Containment Reference Proxies</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getMultipleContainmentReferenceProxies()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_MultipleContainmentReferenceProxies();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceCollection <em>Feature Map Reference Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Feature Map Reference Collection</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceCollection()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_FeatureMapReferenceCollection();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceType1 <em>Feature Map Reference Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Feature Map Reference Type1</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceType1()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_FeatureMapReferenceType1();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceType2 <em>Feature Map Reference Type2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Feature Map Reference Type2</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceType2()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EReference getPrimaryObject_FeatureMapReferenceType2();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeCollection <em>Feature Map Attribute Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Feature Map Attribute Collection</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeCollection()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_FeatureMapAttributeCollection();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeType1 <em>Feature Map Attribute Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Feature Map Attribute Type1</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeType1()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_FeatureMapAttributeType1();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeType2 <em>Feature Map Attribute Type2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Feature Map Attribute Type2</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeType2()
	 * @see #getPrimaryObject()
	 * @generated
	 */
	EAttribute getPrimaryObject_FeatureMapAttributeType2();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Container
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.model.Container#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Container#getElements()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_Elements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject <em>Target Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Object</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject
	 * @generated
	 */
	EClass getTargetObject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleAttribute <em>Single Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Single Attribute</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleAttribute()
	 * @see #getTargetObject()
	 * @generated
	 */
	EAttribute getTargetObject_SingleAttribute();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getArrayAttribute <em>Array Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Array Attribute</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject#getArrayAttribute()
	 * @see #getTargetObject()
	 * @generated
	 */
	EAttribute getTargetObject_ArrayAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReference <em>Single Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Single Reference</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReference()
	 * @see #getTargetObject()
	 * @generated
	 */
	EReference getTargetObject_SingleReference();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getManyReferences <em>Many References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Many References</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject#getManyReferences()
	 * @see #getTargetObject()
	 * @generated
	 */
	EReference getTargetObject_ManyReferences();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Single Reference Not Resolve Proxies</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReferenceNotResolveProxies()
	 * @see #getTargetObject()
	 * @generated
	 */
	EReference getTargetObject_SingleReferenceNotResolveProxies();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getManyReferencesNotResolveProxies <em>Many References Not Resolve Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Many References Not Resolve Proxies</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject#getManyReferencesNotResolveProxies()
	 * @see #getTargetObject()
	 * @generated
	 */
	EReference getTargetObject_ManyReferencesNotResolveProxies();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractType <em>Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Type</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractType
	 * @generated
	 */
	EClass getAbstractType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractType#getName()
	 * @see #getAbstractType()
	 * @generated
	 */
	EAttribute getAbstractType_Name();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractType#getRefProperty <em>Ref Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ref Property</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractType#getRefProperty()
	 * @see #getAbstractType()
	 * @generated
	 */
	EReference getAbstractType_RefProperty();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne <em>Concrete Type One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Concrete Type One</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne
	 * @generated
	 */
	EClass getConcreteTypeOne();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne#getPropTypeOne <em>Prop Type One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prop Type One</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne#getPropTypeOne()
	 * @see #getConcreteTypeOne()
	 * @generated
	 */
	EAttribute getConcreteTypeOne_PropTypeOne();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo <em>Concrete Type Two</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Concrete Type Two</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo
	 * @generated
	 */
	EClass getConcreteTypeTwo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo#getPropTypeTwo <em>Prop Type Two</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prop Type Two</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo#getPropTypeTwo()
	 * @see #getConcreteTypeTwo()
	 * @generated
	 */
	EAttribute getConcreteTypeTwo_PropTypeTwo();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node#getLabel()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Label();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node#getTarget()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Target();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node#getSource()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Source();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getManyRef <em>Many Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Many Ref</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node#getManyRef()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_ManyRef();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Child</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node#getChild()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Child();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getUniqueChild <em>Unique Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Unique Child</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node#getUniqueChild()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_UniqueChild();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode <em>Abstract Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Node</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode
	 * @generated
	 */
	EClass getAbstractNode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getLabel()
	 * @see #getAbstractNode()
	 * @generated
	 */
	EAttribute getAbstractNode_Label();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Child</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChild()
	 * @see #getAbstractNode()
	 * @generated
	 */
	EReference getAbstractNode_Child();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChildResolving <em>Child Resolving</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Child Resolving</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChildResolving()
	 * @see #getAbstractNode()
	 * @generated
	 */
	EReference getAbstractNode_ChildResolving();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParent()
	 * @see #getAbstractNode()
	 * @generated
	 */
	EReference getAbstractNode_Parent();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParentResolving <em>Parent Resolving</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Resolving</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParentResolving()
	 * @see #getAbstractNode()
	 * @generated
	 */
	EReference getAbstractNode_ParentResolving();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.PhysicalNode <em>Physical Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Physical Node</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.PhysicalNode
	 * @generated
	 */
	EClass getPhysicalNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.VirtualNode <em>Virtual Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Virtual Node</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.VirtualNode
	 * @generated
	 */
	EClass getVirtualNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.Book <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Book</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Book
	 * @generated
	 */
	EClass getBook();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.jackson.junit.model.Book#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Author</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Book#getAuthor()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Book#getAuthorName <em>Author Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Book#getAuthorName()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_AuthorName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Book#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Book#getTitle()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Title();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfcloud.jackson.junit.model.Author <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Author</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Author
	 * @generated
	 */
	EClass getAuthor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Author#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Author#getFirstName()
	 * @see #getAuthor()
	 * @generated
	 */
	EAttribute getAuthor_FirstName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.jackson.junit.model.Author#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Author#getLastName()
	 * @see #getAuthor()
	 * @generated
	 */
	EAttribute getAuthor_LastName();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emfcloud.jackson.junit.model.Sex <em>Sex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Sex</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.Sex
	 * @generated
	 */
	EEnum getSex();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emfcloud.jackson.junit.model.SomeKind <em>Some Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Some Kind</em>'.
	 * @see org.eclipse.emfcloud.jackson.junit.model.SomeKind
	 * @generated
	 */
	EEnum getSomeKind();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.URI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>URI</em>'.
	 * @see org.eclipse.emf.common.util.URI
	 * @model instanceClass="org.eclipse.emf.common.util.URI"
	 * @generated
	 */
	EDataType getURI();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>User Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>User Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getUserType();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Object Type</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 * @generated
	 */
	EDataType getObjectType();

	/**
	 * Returns the meta object for data type '<em>Object Array Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Object Array Type</em>'.
	 * @model instanceClass="java.lang.Object[]"
	 * @generated
	 */
	EDataType getObjectArrayType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.UserImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>User Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__USER_ID = eINSTANCE.getUser_UserId();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__NAME = eINSTANCE.getUser_Name();

		/**
		 * The meta object literal for the '<em><b>Birth Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__BIRTH_DATE = eINSTANCE.getUser_BirthDate();

		/**
		 * The meta object literal for the '<em><b>Sex</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__SEX = eINSTANCE.getUser_Sex();

		/**
		 * The meta object literal for the '<em><b>Friends</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__FRIENDS = eINSTANCE.getUser_Friends();

		/**
		 * The meta object literal for the '<em><b>Unique Friend</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__UNIQUE_FRIEND = eINSTANCE.getUser_UniqueFriend();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__ADDRESS = eINSTANCE.getUser_Address();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.AddressImpl <em>Address</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.AddressImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getAddress()
		 * @generated
		 */
		EClass ADDRESS = eINSTANCE.getAddress();

		/**
		 * The meta object literal for the '<em><b>Add Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__ADD_ID = eINSTANCE.getAddress_AddId();

		/**
		 * The meta object literal for the '<em><b>City</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__CITY = eINSTANCE.getAddress_City();

		/**
		 * The meta object literal for the '<em><b>Street</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__STREET = eINSTANCE.getAddress_Street();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__NUMBER = eINSTANCE.getAddress_Number();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl <em>ETypes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getETypes()
		 * @generated
		 */
		EClass ETYPES = eINSTANCE.getETypes();

		/**
		 * The meta object literal for the '<em><b>EString</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__ESTRING = eINSTANCE.getETypes_EString();

		/**
		 * The meta object literal for the '<em><b>EStrings</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__ESTRINGS = eINSTANCE.getETypes_EStrings();

		/**
		 * The meta object literal for the '<em><b>EBoolean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EBOOLEAN = eINSTANCE.getETypes_EBoolean();

		/**
		 * The meta object literal for the '<em><b>EBooleans</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EBOOLEANS = eINSTANCE.getETypes_EBooleans();

		/**
		 * The meta object literal for the '<em><b>EInt</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EINT = eINSTANCE.getETypes_EInt();

		/**
		 * The meta object literal for the '<em><b>EInts</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EINTS = eINSTANCE.getETypes_EInts();

		/**
		 * The meta object literal for the '<em><b>Double Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__DOUBLE_VALUE = eINSTANCE.getETypes_DoubleValue();

		/**
		 * The meta object literal for the '<em><b>EDouble</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EDOUBLE = eINSTANCE.getETypes_EDouble();

		/**
		 * The meta object literal for the '<em><b>EDoubles</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EDOUBLES = eINSTANCE.getETypes_EDoubles();

		/**
		 * The meta object literal for the '<em><b>EBig Decimal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EBIG_DECIMAL = eINSTANCE.getETypes_EBigDecimal();

		/**
		 * The meta object literal for the '<em><b>EBig Integer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EBIG_INTEGER = eINSTANCE.getETypes_EBigInteger();

		/**
		 * The meta object literal for the '<em><b>EByte</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EBYTE = eINSTANCE.getETypes_EByte();

		/**
		 * The meta object literal for the '<em><b>EByte Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EBYTE_ARRAY = eINSTANCE.getETypes_EByteArray();

		/**
		 * The meta object literal for the '<em><b>EChar</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__ECHAR = eINSTANCE.getETypes_EChar();

		/**
		 * The meta object literal for the '<em><b>EDate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EDATE = eINSTANCE.getETypes_EDate();

		/**
		 * The meta object literal for the '<em><b>EFloat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__EFLOAT = eINSTANCE.getETypes_EFloat();

		/**
		 * The meta object literal for the '<em><b>ELong</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__ELONG = eINSTANCE.getETypes_ELong();

		/**
		 * The meta object literal for the '<em><b>EShort</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__ESHORT = eINSTANCE.getETypes_EShort();

		/**
		 * The meta object literal for the '<em><b>Object Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__OBJECT_TYPE = eINSTANCE.getETypes_ObjectType();

		/**
		 * The meta object literal for the '<em><b>Object Type Many</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__OBJECT_TYPE_MANY = eINSTANCE.getETypes_ObjectTypeMany();

		/**
		 * The meta object literal for the '<em><b>Object Array Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__OBJECT_ARRAY_TYPE = eINSTANCE.getETypes_ObjectArrayType();

		/**
		 * The meta object literal for the '<em><b>Object Array Type Many</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__OBJECT_ARRAY_TYPE_MANY = eINSTANCE.getETypes_ObjectArrayTypeMany();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ETYPES__VALUES = eINSTANCE.getETypes_Values();

		/**
		 * The meta object literal for the '<em><b>Values With Ref</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ETYPES__VALUES_WITH_REF = eINSTANCE.getETypes_ValuesWithRef();

		/**
		 * The meta object literal for the '<em><b>String Map Values</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ETYPES__STRING_MAP_VALUES = eINSTANCE.getETypes_StringMapValues();

		/**
		 * The meta object literal for the '<em><b>Data Type Map Values</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ETYPES__DATA_TYPE_MAP_VALUES = eINSTANCE.getETypes_DataTypeMapValues();

		/**
		 * The meta object literal for the '<em><b>Uris</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ETYPES__URIS = eINSTANCE.getETypes_Uris();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.StringMapImpl <em>String Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.StringMapImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getStringMap()
		 * @generated
		 */
		EClass STRING_MAP = eINSTANCE.getStringMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_MAP__KEY = eINSTANCE.getStringMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_MAP__VALUE = eINSTANCE.getStringMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.TMapImpl <em>TMap</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.TMapImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getTMap()
		 * @generated
		 */
		EClass TMAP = eINSTANCE.getTMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TMAP__KEY = eINSTANCE.getTMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TMAP__VALUE = eINSTANCE.getTMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.TMapRefImpl <em>TMap Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.TMapRefImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getTMapRef()
		 * @generated
		 */
		EClass TMAP_REF = eINSTANCE.getTMapRef();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TMAP_REF__KEY = eINSTANCE.getTMapRef_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TMAP_REF__VALUE = eINSTANCE.getTMapRef_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.TypeImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__NAME = eINSTANCE.getType_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ValueImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE__VALUE = eINSTANCE.getValue_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.DataTypeMapImpl <em>Data Type Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.DataTypeMapImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getDataTypeMap()
		 * @generated
		 */
		EClass DATA_TYPE_MAP = eINSTANCE.getDataTypeMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE_MAP__KEY = eINSTANCE.getDataTypeMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE_MAP__VALUE = eINSTANCE.getDataTypeMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl <em>Primary Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getPrimaryObject()
		 * @generated
		 */
		EClass PRIMARY_OBJECT = eINSTANCE.getPrimaryObject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__NAME = eINSTANCE.getPrimaryObject_Name();

		/**
		 * The meta object literal for the '<em><b>Id Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__ID_ATTRIBUTE = eINSTANCE.getPrimaryObject_IdAttribute();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__KIND = eINSTANCE.getPrimaryObject_Kind();

		/**
		 * The meta object literal for the '<em><b>Unsettable Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE = eINSTANCE.getPrimaryObject_UnsettableAttribute();

		/**
		 * The meta object literal for the '<em><b>Unsettable Attribute With Non Null Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT = eINSTANCE.getPrimaryObject_UnsettableAttributeWithNonNullDefault();

		/**
		 * The meta object literal for the '<em><b>Unsettable Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__UNSETTABLE_REFERENCE = eINSTANCE.getPrimaryObject_UnsettableReference();

		/**
		 * The meta object literal for the '<em><b>Single Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__SINGLE_REFERENCE = eINSTANCE.getPrimaryObject_SingleReference();

		/**
		 * The meta object literal for the '<em><b>Many References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__MANY_REFERENCES = eINSTANCE.getPrimaryObject_ManyReferences();

		/**
		 * The meta object literal for the '<em><b>Single Reference Not Resolve Proxies</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES = eINSTANCE.getPrimaryObject_SingleReferenceNotResolveProxies();

		/**
		 * The meta object literal for the '<em><b>Many Reference Not Resolve Proxies</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__MANY_REFERENCE_NOT_RESOLVE_PROXIES = eINSTANCE.getPrimaryObject_ManyReferenceNotResolveProxies();

		/**
		 * The meta object literal for the '<em><b>Containment Reference Same Collection</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION = eINSTANCE.getPrimaryObject_ContainmentReferenceSameCollection();

		/**
		 * The meta object literal for the '<em><b>Single Containment Reference No Proxies</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES = eINSTANCE.getPrimaryObject_SingleContainmentReferenceNoProxies();

		/**
		 * The meta object literal for the '<em><b>Multiple Containment Reference No Proxies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_NO_PROXIES = eINSTANCE.getPrimaryObject_MultipleContainmentReferenceNoProxies();

		/**
		 * The meta object literal for the '<em><b>Single Containment Reference Proxies</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES = eINSTANCE.getPrimaryObject_SingleContainmentReferenceProxies();

		/**
		 * The meta object literal for the '<em><b>Multiple Containment Reference Proxies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_PROXIES = eINSTANCE.getPrimaryObject_MultipleContainmentReferenceProxies();

		/**
		 * The meta object literal for the '<em><b>Feature Map Reference Collection</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_COLLECTION = eINSTANCE.getPrimaryObject_FeatureMapReferenceCollection();

		/**
		 * The meta object literal for the '<em><b>Feature Map Reference Type1</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE1 = eINSTANCE.getPrimaryObject_FeatureMapReferenceType1();

		/**
		 * The meta object literal for the '<em><b>Feature Map Reference Type2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE2 = eINSTANCE.getPrimaryObject_FeatureMapReferenceType2();

		/**
		 * The meta object literal for the '<em><b>Feature Map Attribute Collection</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_COLLECTION = eINSTANCE.getPrimaryObject_FeatureMapAttributeCollection();

		/**
		 * The meta object literal for the '<em><b>Feature Map Attribute Type1</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE1 = eINSTANCE.getPrimaryObject_FeatureMapAttributeType1();

		/**
		 * The meta object literal for the '<em><b>Feature Map Attribute Type2</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE2 = eINSTANCE.getPrimaryObject_FeatureMapAttributeType2();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ContainerImpl <em>Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ContainerImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getContainer()
		 * @generated
		 */
		EClass CONTAINER = eINSTANCE.getContainer();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER__ELEMENTS = eINSTANCE.getContainer_Elements();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl <em>Target Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getTargetObject()
		 * @generated
		 */
		EClass TARGET_OBJECT = eINSTANCE.getTargetObject();

		/**
		 * The meta object literal for the '<em><b>Single Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET_OBJECT__SINGLE_ATTRIBUTE = eINSTANCE.getTargetObject_SingleAttribute();

		/**
		 * The meta object literal for the '<em><b>Array Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET_OBJECT__ARRAY_ATTRIBUTE = eINSTANCE.getTargetObject_ArrayAttribute();

		/**
		 * The meta object literal for the '<em><b>Single Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_OBJECT__SINGLE_REFERENCE = eINSTANCE.getTargetObject_SingleReference();

		/**
		 * The meta object literal for the '<em><b>Many References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_OBJECT__MANY_REFERENCES = eINSTANCE.getTargetObject_ManyReferences();

		/**
		 * The meta object literal for the '<em><b>Single Reference Not Resolve Proxies</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES = eINSTANCE.getTargetObject_SingleReferenceNotResolveProxies();

		/**
		 * The meta object literal for the '<em><b>Many References Not Resolve Proxies</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_OBJECT__MANY_REFERENCES_NOT_RESOLVE_PROXIES = eINSTANCE.getTargetObject_ManyReferencesNotResolveProxies();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractTypeImpl <em>Abstract Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.AbstractTypeImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getAbstractType()
		 * @generated
		 */
		EClass ABSTRACT_TYPE = eINSTANCE.getAbstractType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_TYPE__NAME = eINSTANCE.getAbstractType_Name();

		/**
		 * The meta object literal for the '<em><b>Ref Property</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_TYPE__REF_PROPERTY = eINSTANCE.getAbstractType_RefProperty();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeOneImpl <em>Concrete Type One</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeOneImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getConcreteTypeOne()
		 * @generated
		 */
		EClass CONCRETE_TYPE_ONE = eINSTANCE.getConcreteTypeOne();

		/**
		 * The meta object literal for the '<em><b>Prop Type One</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCRETE_TYPE_ONE__PROP_TYPE_ONE = eINSTANCE.getConcreteTypeOne_PropTypeOne();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeTwoImpl <em>Concrete Type Two</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeTwoImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getConcreteTypeTwo()
		 * @generated
		 */
		EClass CONCRETE_TYPE_TWO = eINSTANCE.getConcreteTypeTwo();

		/**
		 * The meta object literal for the '<em><b>Prop Type Two</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCRETE_TYPE_TWO__PROP_TYPE_TWO = eINSTANCE.getConcreteTypeTwo_PropTypeTwo();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.NodeImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE__LABEL = eINSTANCE.getNode_Label();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__TARGET = eINSTANCE.getNode_Target();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__SOURCE = eINSTANCE.getNode_Source();

		/**
		 * The meta object literal for the '<em><b>Many Ref</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__MANY_REF = eINSTANCE.getNode_ManyRef();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__CHILD = eINSTANCE.getNode_Child();

		/**
		 * The meta object literal for the '<em><b>Unique Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__UNIQUE_CHILD = eINSTANCE.getNode_UniqueChild();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl <em>Abstract Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getAbstractNode()
		 * @generated
		 */
		EClass ABSTRACT_NODE = eINSTANCE.getAbstractNode();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_NODE__LABEL = eINSTANCE.getAbstractNode_Label();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_NODE__CHILD = eINSTANCE.getAbstractNode_Child();

		/**
		 * The meta object literal for the '<em><b>Child Resolving</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_NODE__CHILD_RESOLVING = eINSTANCE.getAbstractNode_ChildResolving();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_NODE__PARENT = eINSTANCE.getAbstractNode_Parent();

		/**
		 * The meta object literal for the '<em><b>Parent Resolving</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_NODE__PARENT_RESOLVING = eINSTANCE.getAbstractNode_ParentResolving();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.PhysicalNodeImpl <em>Physical Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.PhysicalNodeImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getPhysicalNode()
		 * @generated
		 */
		EClass PHYSICAL_NODE = eINSTANCE.getPhysicalNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.VirtualNodeImpl <em>Virtual Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.VirtualNodeImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getVirtualNode()
		 * @generated
		 */
		EClass VIRTUAL_NODE = eINSTANCE.getVirtualNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.BookImpl <em>Book</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.BookImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getBook()
		 * @generated
		 */
		EClass BOOK = eINSTANCE.getBook();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOK__AUTHOR = eINSTANCE.getBook_Author();

		/**
		 * The meta object literal for the '<em><b>Author Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__AUTHOR_NAME = eINSTANCE.getBook_AuthorName();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOK__TITLE = eINSTANCE.getBook_Title();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.impl.AuthorImpl <em>Author</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.AuthorImpl
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getAuthor()
		 * @generated
		 */
		EClass AUTHOR = eINSTANCE.getAuthor();

		/**
		 * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHOR__FIRST_NAME = eINSTANCE.getAuthor_FirstName();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHOR__LAST_NAME = eINSTANCE.getAuthor_LastName();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.Sex <em>Sex</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.Sex
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getSex()
		 * @generated
		 */
		EEnum SEX = eINSTANCE.getSex();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfcloud.jackson.junit.model.SomeKind <em>Some Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.SomeKind
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getSomeKind()
		 * @generated
		 */
		EEnum SOME_KIND = eINSTANCE.getSomeKind();

		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();

		/**
		 * The meta object literal for the '<em>User Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getUserType()
		 * @generated
		 */
		EDataType USER_TYPE = eINSTANCE.getUserType();

		/**
		 * The meta object literal for the '<em>Object Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getObjectType()
		 * @generated
		 */
		EDataType OBJECT_TYPE = eINSTANCE.getObjectType();

		/**
		 * The meta object literal for the '<em>Object Array Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emfcloud.jackson.junit.model.impl.ModelPackageImpl#getObjectArrayType()
		 * @generated
		 */
		EDataType OBJECT_ARRAY_TYPE = eINSTANCE.getObjectArrayType();

	}

} //ModelPackage
