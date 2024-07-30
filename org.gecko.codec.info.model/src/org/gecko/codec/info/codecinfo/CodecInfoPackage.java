/*
 */
package org.gecko.codec.info.codecinfo;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see org.gecko.codec.info.codecinfo.CodecInfoFactory
 * @model kind="package"
 *        annotation="Version value='1.0'"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel complianceLevel='17.0' basePackage='org.gecko.codec.info' fileExtensions='codecinfo' oSGiCompatible='true' resource='XMI'"
 * @generated
 */
@ProviderType
@EPackage(uri = CodecInfoPackage.eNS_URI, genModel = "/model/codec-info.genmodel", genModelSourceLocations = {"model/codec-info.genmodel","org.gecko.codec.info.model/model/codec-info.genmodel"}, ecore="/model/codec-info.ecore", ecoreSourceLocations="/model/codec-info.ecore")
public interface CodecInfoPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "codecinfo";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://geckoprojects.org/codec/info/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ci";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CodecInfoPackage eINSTANCE = org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.PackageInfoImpl <em>Package Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.PackageInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getPackageInfo()
	 * @generated
	 */
	int PACKAGE_INFO = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>EPackage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_INFO__EPACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Sub Package Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_INFO__SUB_PACKAGE_INFO = 2;

	/**
	 * The feature id for the '<em><b>EClass Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_INFO__ECLASS_INFO = 3;

	/**
	 * The number of structural features of the '<em>Package Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_INFO_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Package Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.EClassInfoImpl <em>EClass Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.EClassInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getEClassInfo()
	 * @generated
	 */
	int ECLASS_INFO = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>Classifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_INFO__CLASSIFIER = 1;

	/**
	 * The feature id for the '<em><b>Identity Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_INFO__IDENTITY_INFO = 2;

	/**
	 * The feature id for the '<em><b>Type Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_INFO__TYPE_INFO = 3;

	/**
	 * The feature id for the '<em><b>Feature Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_INFO__FEATURE_INFO = 4;

	/**
	 * The number of structural features of the '<em>EClass Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_INFO_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>EClass Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.FeatureInfoImpl <em>Feature Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.FeatureInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getFeatureInfo()
	 * @generated
	 */
	int FEATURE_INFO = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>Features</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO__FEATURES = 1;

	/**
	 * The feature id for the '<em><b>Default Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO__DEFAULT_KEY = 2;

	/**
	 * The feature id for the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO__VALUE_READER_NAME = 3;

	/**
	 * The feature id for the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO__VALUE_WRITER_NAME = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO__TYPE = 5;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO__KEY = 6;

	/**
	 * The number of structural features of the '<em>Feature Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Feature Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.ValueReader <em>Value Reader</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.ValueReader
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getValueReader()
	 * @generated
	 */
	int VALUE_READER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_READER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Value Reader</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_READER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Read Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_READER___READ_VALUE__OBJECT_DESERIALIZATIONCONTEXT = 0;

	/**
	 * The number of operations of the '<em>Value Reader</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_READER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.ValueWriter <em>Value Writer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.ValueWriter
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getValueWriter()
	 * @generated
	 */
	int VALUE_WRITER = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_WRITER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Value Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_WRITER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Write Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_WRITER___WRITE_VALUE__OBJECT_SERIALIZERPROVIDER = 0;

	/**
	 * The number of operations of the '<em>Value Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_WRITER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.TypeInfoHolderImpl <em>Type Info Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.TypeInfoHolderImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getTypeInfoHolder()
	 * @generated
	 */
	int TYPE_INFO_HOLDER = 5;

	/**
	 * The feature id for the '<em><b>Info Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_HOLDER__INFO_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Readers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_HOLDER__READERS = 1;

	/**
	 * The feature id for the '<em><b>Writers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_HOLDER__WRITERS = 2;

	/**
	 * The number of structural features of the '<em>Type Info Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_HOLDER_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Get Reader By Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_HOLDER___GET_READER_BY_NAME__STRING = 0;

	/**
	 * The operation id for the '<em>Get Writer By Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_HOLDER___GET_WRITER_BY_NAME__STRING = 1;

	/**
	 * The number of operations of the '<em>Type Info Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_HOLDER_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl <em>Type Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.TypeInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getTypeInfo()
	 * @generated
	 */
	int TYPE_INFO = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__ID = FEATURE_INFO__ID;

	/**
	 * The feature id for the '<em><b>Features</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__FEATURES = FEATURE_INFO__FEATURES;

	/**
	 * The feature id for the '<em><b>Default Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__DEFAULT_KEY = FEATURE_INFO__DEFAULT_KEY;

	/**
	 * The feature id for the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__VALUE_READER_NAME = FEATURE_INFO__VALUE_READER_NAME;

	/**
	 * The feature id for the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__VALUE_WRITER_NAME = FEATURE_INFO__VALUE_WRITER_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__TYPE = FEATURE_INFO__TYPE;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__KEY = FEATURE_INFO__KEY;

	/**
	 * The feature id for the '<em><b>Write Super Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__WRITE_SUPER_TYPES = FEATURE_INFO_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_FEATURE_COUNT = FEATURE_INFO_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_OPERATION_COUNT = FEATURE_INFO_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.InfoType <em>Info Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.InfoType
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getInfoType()
	 * @generated
	 */
	int INFO_TYPE = 7;

	/**
	 * The meta object id for the '<em>Deserialization Context</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.fasterxml.jackson.databind.DeserializationContext
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getDeserializationContext()
	 * @generated
	 */
	int DESERIALIZATION_CONTEXT = 8;

	/**
	 * The meta object id for the '<em>Serializer Provider</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.fasterxml.jackson.databind.SerializerProvider
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getSerializerProvider()
	 * @generated
	 */
	int SERIALIZER_PROVIDER = 9;


	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.PackageInfo <em>Package Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Package Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageInfo
	 * @generated
	 */
	EClass getPackageInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.PackageInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageInfo#getId()
	 * @see #getPackageInfo()
	 * @generated
	 */
	EAttribute getPackageInfo_Id();

	/**
	 * Returns the meta object for the reference '{@link org.gecko.codec.info.codecinfo.PackageInfo#getEPackage <em>EPackage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EPackage</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageInfo#getEPackage()
	 * @see #getPackageInfo()
	 * @generated
	 */
	EReference getPackageInfo_EPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gecko.codec.info.codecinfo.PackageInfo#getSubPackageInfo <em>Sub Package Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Package Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageInfo#getSubPackageInfo()
	 * @see #getPackageInfo()
	 * @generated
	 */
	EReference getPackageInfo_SubPackageInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gecko.codec.info.codecinfo.PackageInfo#getEClassInfo <em>EClass Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>EClass Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageInfo#getEClassInfo()
	 * @see #getPackageInfo()
	 * @generated
	 */
	EReference getPackageInfo_EClassInfo();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.EClassInfo <em>EClass Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EClass Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassInfo
	 * @generated
	 */
	EClass getEClassInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.EClassInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassInfo#getId()
	 * @see #getEClassInfo()
	 * @generated
	 */
	EAttribute getEClassInfo_Id();

	/**
	 * Returns the meta object for the reference '{@link org.gecko.codec.info.codecinfo.EClassInfo#getClassifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Classifier</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassInfo#getClassifier()
	 * @see #getEClassInfo()
	 * @generated
	 */
	EReference getEClassInfo_Classifier();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.codec.info.codecinfo.EClassInfo#getIdentityInfo <em>Identity Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Identity Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassInfo#getIdentityInfo()
	 * @see #getEClassInfo()
	 * @generated
	 */
	EReference getEClassInfo_IdentityInfo();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.codec.info.codecinfo.EClassInfo#getTypeInfo <em>Type Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassInfo#getTypeInfo()
	 * @see #getEClassInfo()
	 * @generated
	 */
	EReference getEClassInfo_TypeInfo();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.codec.info.codecinfo.EClassInfo#getFeatureInfo <em>Feature Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassInfo#getFeatureInfo()
	 * @see #getEClassInfo()
	 * @generated
	 */
	EReference getEClassInfo_FeatureInfo();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.FeatureInfo <em>Feature Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureInfo
	 * @generated
	 */
	EClass getFeatureInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureInfo#getId()
	 * @see #getFeatureInfo()
	 * @generated
	 */
	EAttribute getFeatureInfo_Id();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.FeatureInfo#getFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Features</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureInfo#getFeatures()
	 * @see #getFeatureInfo()
	 * @generated
	 */
	EReference getFeatureInfo_Features();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureInfo#getDefaultKey <em>Default Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Key</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureInfo#getDefaultKey()
	 * @see #getFeatureInfo()
	 * @generated
	 */
	EAttribute getFeatureInfo_DefaultKey();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureInfo#getValueReaderName <em>Value Reader Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Reader Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureInfo#getValueReaderName()
	 * @see #getFeatureInfo()
	 * @generated
	 */
	EAttribute getFeatureInfo_ValueReaderName();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureInfo#getValueWriterName <em>Value Writer Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Writer Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureInfo#getValueWriterName()
	 * @see #getFeatureInfo()
	 * @generated
	 */
	EAttribute getFeatureInfo_ValueWriterName();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureInfo#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureInfo#getType()
	 * @see #getFeatureInfo()
	 * @generated
	 */
	EAttribute getFeatureInfo_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureInfo#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureInfo#getKey()
	 * @see #getFeatureInfo()
	 * @generated
	 */
	EAttribute getFeatureInfo_Key();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.ValueReader <em>Value Reader</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Reader</em>'.
	 * @see org.gecko.codec.info.codecinfo.ValueReader
	 * @generated
	 */
	EClass getValueReader();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.ValueReader#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.ValueReader#getName()
	 * @see #getValueReader()
	 * @generated
	 */
	EAttribute getValueReader_Name();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.ValueReader#readValue(java.lang.Object, com.fasterxml.jackson.databind.DeserializationContext) <em>Read Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Read Value</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.ValueReader#readValue(java.lang.Object, com.fasterxml.jackson.databind.DeserializationContext)
	 * @generated
	 */
	EOperation getValueReader__ReadValue__Object_DeserializationContext();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.ValueWriter <em>Value Writer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Writer</em>'.
	 * @see org.gecko.codec.info.codecinfo.ValueWriter
	 * @generated
	 */
	EClass getValueWriter();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.ValueWriter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.ValueWriter#getName()
	 * @see #getValueWriter()
	 * @generated
	 */
	EAttribute getValueWriter_Name();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.ValueWriter#writeValue(java.lang.Object, com.fasterxml.jackson.databind.SerializerProvider) <em>Write Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Write Value</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.ValueWriter#writeValue(java.lang.Object, com.fasterxml.jackson.databind.SerializerProvider)
	 * @generated
	 */
	EOperation getValueWriter__WriteValue__Object_SerializerProvider();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.TypeInfoHolder <em>Type Info Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Info Holder</em>'.
	 * @see org.gecko.codec.info.codecinfo.TypeInfoHolder
	 * @generated
	 */
	EClass getTypeInfoHolder();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getInfoType <em>Info Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Info Type</em>'.
	 * @see org.gecko.codec.info.codecinfo.TypeInfoHolder#getInfoType()
	 * @see #getTypeInfoHolder()
	 * @generated
	 */
	EAttribute getTypeInfoHolder_InfoType();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getReaders <em>Readers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Readers</em>'.
	 * @see org.gecko.codec.info.codecinfo.TypeInfoHolder#getReaders()
	 * @see #getTypeInfoHolder()
	 * @generated
	 */
	EReference getTypeInfoHolder_Readers();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getWriters <em>Writers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Writers</em>'.
	 * @see org.gecko.codec.info.codecinfo.TypeInfoHolder#getWriters()
	 * @see #getTypeInfoHolder()
	 * @generated
	 */
	EReference getTypeInfoHolder_Writers();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getReaderByName(java.lang.String) <em>Get Reader By Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Reader By Name</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.TypeInfoHolder#getReaderByName(java.lang.String)
	 * @generated
	 */
	EOperation getTypeInfoHolder__GetReaderByName__String();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getWriterByName(java.lang.String) <em>Get Writer By Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Writer By Name</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.TypeInfoHolder#getWriterByName(java.lang.String)
	 * @generated
	 */
	EOperation getTypeInfoHolder__GetWriterByName__String();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.TypeInfo <em>Type Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.TypeInfo
	 * @generated
	 */
	EClass getTypeInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.TypeInfo#isWriteSuperTypes <em>Write Super Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Write Super Types</em>'.
	 * @see org.gecko.codec.info.codecinfo.TypeInfo#isWriteSuperTypes()
	 * @see #getTypeInfo()
	 * @generated
	 */
	EAttribute getTypeInfo_WriteSuperTypes();

	/**
	 * Returns the meta object for enum '{@link org.gecko.codec.info.codecinfo.InfoType <em>Info Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Info Type</em>'.
	 * @see org.gecko.codec.info.codecinfo.InfoType
	 * @generated
	 */
	EEnum getInfoType();

	/**
	 * Returns the meta object for data type '{@link com.fasterxml.jackson.databind.DeserializationContext <em>Deserialization Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Deserialization Context</em>'.
	 * @see com.fasterxml.jackson.databind.DeserializationContext
	 * @model instanceClass="com.fasterxml.jackson.databind.DeserializationContext"
	 * @generated
	 */
	EDataType getDeserializationContext();

	/**
	 * Returns the meta object for data type '{@link com.fasterxml.jackson.databind.SerializerProvider <em>Serializer Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Serializer Provider</em>'.
	 * @see com.fasterxml.jackson.databind.SerializerProvider
	 * @model instanceClass="com.fasterxml.jackson.databind.SerializerProvider"
	 * @generated
	 */
	EDataType getSerializerProvider();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CodecInfoFactory getCodecInfoFactory();

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
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.PackageInfoImpl <em>Package Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.PackageInfoImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getPackageInfo()
		 * @generated
		 */
		EClass PACKAGE_INFO = eINSTANCE.getPackageInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE_INFO__ID = eINSTANCE.getPackageInfo_Id();

		/**
		 * The meta object literal for the '<em><b>EPackage</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_INFO__EPACKAGE = eINSTANCE.getPackageInfo_EPackage();

		/**
		 * The meta object literal for the '<em><b>Sub Package Info</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_INFO__SUB_PACKAGE_INFO = eINSTANCE.getPackageInfo_SubPackageInfo();

		/**
		 * The meta object literal for the '<em><b>EClass Info</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_INFO__ECLASS_INFO = eINSTANCE.getPackageInfo_EClassInfo();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.EClassInfoImpl <em>EClass Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.EClassInfoImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getEClassInfo()
		 * @generated
		 */
		EClass ECLASS_INFO = eINSTANCE.getEClassInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLASS_INFO__ID = eINSTANCE.getEClassInfo_Id();

		/**
		 * The meta object literal for the '<em><b>Classifier</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_INFO__CLASSIFIER = eINSTANCE.getEClassInfo_Classifier();

		/**
		 * The meta object literal for the '<em><b>Identity Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_INFO__IDENTITY_INFO = eINSTANCE.getEClassInfo_IdentityInfo();

		/**
		 * The meta object literal for the '<em><b>Type Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_INFO__TYPE_INFO = eINSTANCE.getEClassInfo_TypeInfo();

		/**
		 * The meta object literal for the '<em><b>Feature Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_INFO__FEATURE_INFO = eINSTANCE.getEClassInfo_FeatureInfo();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.FeatureInfoImpl <em>Feature Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.FeatureInfoImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getFeatureInfo()
		 * @generated
		 */
		EClass FEATURE_INFO = eINSTANCE.getFeatureInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_INFO__ID = eINSTANCE.getFeatureInfo_Id();

		/**
		 * The meta object literal for the '<em><b>Features</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_INFO__FEATURES = eINSTANCE.getFeatureInfo_Features();

		/**
		 * The meta object literal for the '<em><b>Default Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_INFO__DEFAULT_KEY = eINSTANCE.getFeatureInfo_DefaultKey();

		/**
		 * The meta object literal for the '<em><b>Value Reader Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_INFO__VALUE_READER_NAME = eINSTANCE.getFeatureInfo_ValueReaderName();

		/**
		 * The meta object literal for the '<em><b>Value Writer Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_INFO__VALUE_WRITER_NAME = eINSTANCE.getFeatureInfo_ValueWriterName();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_INFO__TYPE = eINSTANCE.getFeatureInfo_Type();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_INFO__KEY = eINSTANCE.getFeatureInfo_Key();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.ValueReader <em>Value Reader</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.ValueReader
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getValueReader()
		 * @generated
		 */
		EClass VALUE_READER = eINSTANCE.getValueReader();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_READER__NAME = eINSTANCE.getValueReader_Name();

		/**
		 * The meta object literal for the '<em><b>Read Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VALUE_READER___READ_VALUE__OBJECT_DESERIALIZATIONCONTEXT = eINSTANCE.getValueReader__ReadValue__Object_DeserializationContext();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.ValueWriter <em>Value Writer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.ValueWriter
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getValueWriter()
		 * @generated
		 */
		EClass VALUE_WRITER = eINSTANCE.getValueWriter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_WRITER__NAME = eINSTANCE.getValueWriter_Name();

		/**
		 * The meta object literal for the '<em><b>Write Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VALUE_WRITER___WRITE_VALUE__OBJECT_SERIALIZERPROVIDER = eINSTANCE.getValueWriter__WriteValue__Object_SerializerProvider();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.TypeInfoHolderImpl <em>Type Info Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.TypeInfoHolderImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getTypeInfoHolder()
		 * @generated
		 */
		EClass TYPE_INFO_HOLDER = eINSTANCE.getTypeInfoHolder();

		/**
		 * The meta object literal for the '<em><b>Info Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_INFO_HOLDER__INFO_TYPE = eINSTANCE.getTypeInfoHolder_InfoType();

		/**
		 * The meta object literal for the '<em><b>Readers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_INFO_HOLDER__READERS = eINSTANCE.getTypeInfoHolder_Readers();

		/**
		 * The meta object literal for the '<em><b>Writers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_INFO_HOLDER__WRITERS = eINSTANCE.getTypeInfoHolder_Writers();

		/**
		 * The meta object literal for the '<em><b>Get Reader By Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_INFO_HOLDER___GET_READER_BY_NAME__STRING = eINSTANCE.getTypeInfoHolder__GetReaderByName__String();

		/**
		 * The meta object literal for the '<em><b>Get Writer By Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_INFO_HOLDER___GET_WRITER_BY_NAME__STRING = eINSTANCE.getTypeInfoHolder__GetWriterByName__String();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl <em>Type Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.TypeInfoImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getTypeInfo()
		 * @generated
		 */
		EClass TYPE_INFO = eINSTANCE.getTypeInfo();

		/**
		 * The meta object literal for the '<em><b>Write Super Types</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_INFO__WRITE_SUPER_TYPES = eINSTANCE.getTypeInfo_WriteSuperTypes();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.InfoType <em>Info Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.InfoType
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getInfoType()
		 * @generated
		 */
		EEnum INFO_TYPE = eINSTANCE.getInfoType();

		/**
		 * The meta object literal for the '<em>Deserialization Context</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.fasterxml.jackson.databind.DeserializationContext
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getDeserializationContext()
		 * @generated
		 */
		EDataType DESERIALIZATION_CONTEXT = eINSTANCE.getDeserializationContext();

		/**
		 * The meta object literal for the '<em>Serializer Provider</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.fasterxml.jackson.databind.SerializerProvider
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getSerializerProvider()
		 * @generated
		 */
		EDataType SERIALIZER_PROVIDER = eINSTANCE.getSerializerProvider();

	}

} //CodecInfoPackage
