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
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.PackageCodecInfoImpl <em>Package Codec Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.PackageCodecInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getPackageCodecInfo()
	 * @generated
	 */
	int PACKAGE_CODEC_INFO = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_CODEC_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>EPackage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_CODEC_INFO__EPACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Sub Package Codec Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_CODEC_INFO__SUB_PACKAGE_CODEC_INFO = 2;

	/**
	 * The feature id for the '<em><b>EClass Codec Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_CODEC_INFO__ECLASS_CODEC_INFO = 3;

	/**
	 * The number of structural features of the '<em>Package Codec Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_CODEC_INFO_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Package Codec Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_CODEC_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl <em>EClass Codec Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getEClassCodecInfo()
	 * @generated
	 */
	int ECLASS_CODEC_INFO = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>Classifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO__CLASSIFIER = 1;

	/**
	 * The feature id for the '<em><b>Identity Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO__IDENTITY_INFO = 2;

	/**
	 * The feature id for the '<em><b>Type Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO__TYPE_INFO = 3;

	/**
	 * The feature id for the '<em><b>Feature Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO__FEATURE_INFO = 4;

	/**
	 * The feature id for the '<em><b>Reference Codec Info</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO__REFERENCE_CODEC_INFO = 5;

	/**
	 * The feature id for the '<em><b>Attribute Codec Info</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO__ATTRIBUTE_CODEC_INFO = 6;

	/**
	 * The feature id for the '<em><b>Operation Codec Info</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO__OPERATION_CODEC_INFO = 7;

	/**
	 * The number of structural features of the '<em>EClass Codec Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>EClass Codec Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_CODEC_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl <em>Feature Codec Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getFeatureCodecInfo()
	 * @generated
	 */
	int FEATURE_CODEC_INFO = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>Features</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO__FEATURES = 1;

	/**
	 * The feature id for the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO__VALUE_READER_NAME = 2;

	/**
	 * The feature id for the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO__VALUE_WRITER_NAME = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO__TYPE = 4;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO__KEY = 5;

	/**
	 * The feature id for the '<em><b>Ignore</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO__IGNORE = 6;

	/**
	 * The number of structural features of the '<em>Feature Codec Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Feature Codec Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl <em>Type Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.TypeInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getTypeInfo()
	 * @generated
	 */
	int TYPE_INFO = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__ID = FEATURE_CODEC_INFO__ID;

	/**
	 * The feature id for the '<em><b>Features</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__FEATURES = FEATURE_CODEC_INFO__FEATURES;

	/**
	 * The feature id for the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__VALUE_READER_NAME = FEATURE_CODEC_INFO__VALUE_READER_NAME;

	/**
	 * The feature id for the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__VALUE_WRITER_NAME = FEATURE_CODEC_INFO__VALUE_WRITER_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__TYPE = FEATURE_CODEC_INFO__TYPE;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__KEY = FEATURE_CODEC_INFO__KEY;

	/**
	 * The feature id for the '<em><b>Ignore</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__IGNORE = FEATURE_CODEC_INFO__IGNORE;

	/**
	 * The feature id for the '<em><b>Type Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__TYPE_STRATEGY = FEATURE_CODEC_INFO_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ignore Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO__IGNORE_TYPE = FEATURE_CODEC_INFO_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_FEATURE_COUNT = FEATURE_CODEC_INFO_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Type Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INFO_OPERATION_COUNT = FEATURE_CODEC_INFO_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl <em>Identity Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getIdentityInfo()
	 * @generated
	 */
	int IDENTITY_INFO = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__ID = FEATURE_CODEC_INFO__ID;

	/**
	 * The feature id for the '<em><b>Features</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__FEATURES = FEATURE_CODEC_INFO__FEATURES;

	/**
	 * The feature id for the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__VALUE_READER_NAME = FEATURE_CODEC_INFO__VALUE_READER_NAME;

	/**
	 * The feature id for the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__VALUE_WRITER_NAME = FEATURE_CODEC_INFO__VALUE_WRITER_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__TYPE = FEATURE_CODEC_INFO__TYPE;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__KEY = FEATURE_CODEC_INFO__KEY;

	/**
	 * The feature id for the '<em><b>Ignore</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__IGNORE = FEATURE_CODEC_INFO__IGNORE;

	/**
	 * The feature id for the '<em><b>Id Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__ID_STRATEGY = FEATURE_CODEC_INFO_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id Separator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__ID_SEPARATOR = FEATURE_CODEC_INFO_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO__ID_ORDER = FEATURE_CODEC_INFO_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Identity Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO_FEATURE_COUNT = FEATURE_CODEC_INFO_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Identity Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_INFO_OPERATION_COUNT = FEATURE_CODEC_INFO_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.CodecValueReader <em>Codec Value Reader</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.CodecValueReader
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getCodecValueReader()
	 * @generated
	 */
	int CODEC_VALUE_READER = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_VALUE_READER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Codec Value Reader</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_VALUE_READER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Read Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_VALUE_READER___READ_VALUE__OBJECT_DESERIALIZATIONCONTEXT = 0;

	/**
	 * The number of operations of the '<em>Codec Value Reader</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_VALUE_READER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.CodecValueWriter <em>Codec Value Writer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.CodecValueWriter
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getCodecValueWriter()
	 * @generated
	 */
	int CODEC_VALUE_WRITER = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_VALUE_WRITER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Codec Value Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_VALUE_WRITER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Write Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_VALUE_WRITER___WRITE_VALUE__OBJECT_SERIALIZERPROVIDER = 0;

	/**
	 * The number of operations of the '<em>Codec Value Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_VALUE_WRITER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.impl.CodecInfoHolderImpl <em>Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoHolderImpl
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getCodecInfoHolder()
	 * @generated
	 */
	int CODEC_INFO_HOLDER = 7;

	/**
	 * The feature id for the '<em><b>Info Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_INFO_HOLDER__INFO_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Readers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_INFO_HOLDER__READERS = 1;

	/**
	 * The feature id for the '<em><b>Writers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_INFO_HOLDER__WRITERS = 2;

	/**
	 * The number of structural features of the '<em>Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_INFO_HOLDER_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Get Reader By Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_INFO_HOLDER___GET_READER_BY_NAME__STRING = 0;

	/**
	 * The operation id for the '<em>Get Writer By Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_INFO_HOLDER___GET_WRITER_BY_NAME__STRING = 1;

	/**
	 * The number of operations of the '<em>Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_INFO_HOLDER_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.SampleValueReader <em>Sample Value Reader</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.SampleValueReader
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getSampleValueReader()
	 * @generated
	 */
	int SAMPLE_VALUE_READER = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_VALUE_READER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Sample Value Reader</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_VALUE_READER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Read Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_VALUE_READER___READ_VALUE__OBJECT_DESERIALIZATIONCONTEXT = 0;

	/**
	 * The number of operations of the '<em>Sample Value Reader</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_VALUE_READER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.gecko.codec.info.codecinfo.InfoType <em>Info Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gecko.codec.info.codecinfo.InfoType
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getInfoType()
	 * @generated
	 */
	int INFO_TYPE = 9;

	/**
	 * The meta object id for the '<em>Serializer Provider</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.fasterxml.jackson.databind.SerializerProvider
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getSerializerProvider()
	 * @generated
	 */
	int SERIALIZER_PROVIDER = 10;

	/**
	 * The meta object id for the '<em>Deserialization Context</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.fasterxml.jackson.databind.DeserializationContext
	 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getDeserializationContext()
	 * @generated
	 */
	int DESERIALIZATION_CONTEXT = 11;


	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.PackageCodecInfo <em>Package Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Package Codec Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageCodecInfo
	 * @generated
	 */
	EClass getPackageCodecInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageCodecInfo#getId()
	 * @see #getPackageCodecInfo()
	 * @generated
	 */
	EAttribute getPackageCodecInfo_Id();

	/**
	 * Returns the meta object for the reference '{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getEPackage <em>EPackage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EPackage</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageCodecInfo#getEPackage()
	 * @see #getPackageCodecInfo()
	 * @generated
	 */
	EReference getPackageCodecInfo_EPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getSubPackageCodecInfo <em>Sub Package Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Package Codec Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageCodecInfo#getSubPackageCodecInfo()
	 * @see #getPackageCodecInfo()
	 * @generated
	 */
	EReference getPackageCodecInfo_SubPackageCodecInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getEClassCodecInfo <em>EClass Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>EClass Codec Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.PackageCodecInfo#getEClassCodecInfo()
	 * @see #getPackageCodecInfo()
	 * @generated
	 */
	EReference getPackageCodecInfo_EClassCodecInfo();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo <em>EClass Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EClass Codec Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo
	 * @generated
	 */
	EClass getEClassCodecInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo#getId()
	 * @see #getEClassCodecInfo()
	 * @generated
	 */
	EAttribute getEClassCodecInfo_Id();

	/**
	 * Returns the meta object for the reference '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getClassifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Classifier</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo#getClassifier()
	 * @see #getEClassCodecInfo()
	 * @generated
	 */
	EReference getEClassCodecInfo_Classifier();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getIdentityInfo <em>Identity Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Identity Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo#getIdentityInfo()
	 * @see #getEClassCodecInfo()
	 * @generated
	 */
	EReference getEClassCodecInfo_IdentityInfo();

	/**
	 * Returns the meta object for the containment reference '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getTypeInfo <em>Type Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo#getTypeInfo()
	 * @see #getEClassCodecInfo()
	 * @generated
	 */
	EReference getEClassCodecInfo_TypeInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getFeatureInfo <em>Feature Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Feature Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo#getFeatureInfo()
	 * @see #getEClassCodecInfo()
	 * @generated
	 */
	EReference getEClassCodecInfo_FeatureInfo();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getReferenceCodecInfo <em>Reference Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Reference Codec Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo#getReferenceCodecInfo()
	 * @see #getEClassCodecInfo()
	 * @generated
	 */
	EReference getEClassCodecInfo_ReferenceCodecInfo();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getAttributeCodecInfo <em>Attribute Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Attribute Codec Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo#getAttributeCodecInfo()
	 * @see #getEClassCodecInfo()
	 * @generated
	 */
	EReference getEClassCodecInfo_AttributeCodecInfo();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getOperationCodecInfo <em>Operation Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Operation Codec Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo#getOperationCodecInfo()
	 * @see #getEClassCodecInfo()
	 * @generated
	 */
	EReference getEClassCodecInfo_OperationCodecInfo();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo <em>Feature Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Codec Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo
	 * @generated
	 */
	EClass getFeatureCodecInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo#getId()
	 * @see #getFeatureCodecInfo()
	 * @generated
	 */
	EAttribute getFeatureCodecInfo_Id();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Features</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo#getFeatures()
	 * @see #getFeatureCodecInfo()
	 * @generated
	 */
	EReference getFeatureCodecInfo_Features();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getValueReaderName <em>Value Reader Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Reader Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo#getValueReaderName()
	 * @see #getFeatureCodecInfo()
	 * @generated
	 */
	EAttribute getFeatureCodecInfo_ValueReaderName();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getValueWriterName <em>Value Writer Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Writer Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo#getValueWriterName()
	 * @see #getFeatureCodecInfo()
	 * @generated
	 */
	EAttribute getFeatureCodecInfo_ValueWriterName();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo#getType()
	 * @see #getFeatureCodecInfo()
	 * @generated
	 */
	EAttribute getFeatureCodecInfo_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo#getKey()
	 * @see #getFeatureCodecInfo()
	 * @generated
	 */
	EAttribute getFeatureCodecInfo_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#isIgnore <em>Ignore</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ignore</em>'.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo#isIgnore()
	 * @see #getFeatureCodecInfo()
	 * @generated
	 */
	EAttribute getFeatureCodecInfo_Ignore();

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
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.TypeInfo#getTypeStrategy <em>Type Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Strategy</em>'.
	 * @see org.gecko.codec.info.codecinfo.TypeInfo#getTypeStrategy()
	 * @see #getTypeInfo()
	 * @generated
	 */
	EAttribute getTypeInfo_TypeStrategy();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.TypeInfo#isIgnoreType <em>Ignore Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ignore Type</em>'.
	 * @see org.gecko.codec.info.codecinfo.TypeInfo#isIgnoreType()
	 * @see #getTypeInfo()
	 * @generated
	 */
	EAttribute getTypeInfo_IgnoreType();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.IdentityInfo <em>Identity Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identity Info</em>'.
	 * @see org.gecko.codec.info.codecinfo.IdentityInfo
	 * @generated
	 */
	EClass getIdentityInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdStrategy <em>Id Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id Strategy</em>'.
	 * @see org.gecko.codec.info.codecinfo.IdentityInfo#getIdStrategy()
	 * @see #getIdentityInfo()
	 * @generated
	 */
	EAttribute getIdentityInfo_IdStrategy();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdSeparator <em>Id Separator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id Separator</em>'.
	 * @see org.gecko.codec.info.codecinfo.IdentityInfo#getIdSeparator()
	 * @see #getIdentityInfo()
	 * @generated
	 */
	EAttribute getIdentityInfo_IdSeparator();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdOrder <em>Id Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id Order</em>'.
	 * @see org.gecko.codec.info.codecinfo.IdentityInfo#getIdOrder()
	 * @see #getIdentityInfo()
	 * @generated
	 */
	EAttribute getIdentityInfo_IdOrder();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.CodecValueReader <em>Codec Value Reader</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Codec Value Reader</em>'.
	 * @see org.gecko.codec.info.codecinfo.CodecValueReader
	 * @generated
	 */
	EClass getCodecValueReader();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.CodecValueReader#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.CodecValueReader#getName()
	 * @see #getCodecValueReader()
	 * @generated
	 */
	EAttribute getCodecValueReader_Name();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.CodecValueReader#readValue(java.lang.Object, com.fasterxml.jackson.databind.DeserializationContext) <em>Read Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Read Value</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.CodecValueReader#readValue(java.lang.Object, com.fasterxml.jackson.databind.DeserializationContext)
	 * @generated
	 */
	EOperation getCodecValueReader__ReadValue__Object_DeserializationContext();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.CodecValueWriter <em>Codec Value Writer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Codec Value Writer</em>'.
	 * @see org.gecko.codec.info.codecinfo.CodecValueWriter
	 * @generated
	 */
	EClass getCodecValueWriter();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.CodecValueWriter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.CodecValueWriter#getName()
	 * @see #getCodecValueWriter()
	 * @generated
	 */
	EAttribute getCodecValueWriter_Name();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.CodecValueWriter#writeValue(java.lang.Object, com.fasterxml.jackson.databind.SerializerProvider) <em>Write Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Write Value</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.CodecValueWriter#writeValue(java.lang.Object, com.fasterxml.jackson.databind.SerializerProvider)
	 * @generated
	 */
	EOperation getCodecValueWriter__WriteValue__Object_SerializerProvider();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.CodecInfoHolder <em>Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Holder</em>'.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoHolder
	 * @generated
	 */
	EClass getCodecInfoHolder();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getInfoType <em>Info Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Info Type</em>'.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoHolder#getInfoType()
	 * @see #getCodecInfoHolder()
	 * @generated
	 */
	EAttribute getCodecInfoHolder_InfoType();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getReaders <em>Readers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Readers</em>'.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoHolder#getReaders()
	 * @see #getCodecInfoHolder()
	 * @generated
	 */
	EReference getCodecInfoHolder_Readers();

	/**
	 * Returns the meta object for the reference list '{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getWriters <em>Writers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Writers</em>'.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoHolder#getWriters()
	 * @see #getCodecInfoHolder()
	 * @generated
	 */
	EReference getCodecInfoHolder_Writers();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getReaderByName(java.lang.String) <em>Get Reader By Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Reader By Name</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoHolder#getReaderByName(java.lang.String)
	 * @generated
	 */
	EOperation getCodecInfoHolder__GetReaderByName__String();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getWriterByName(java.lang.String) <em>Get Writer By Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Writer By Name</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoHolder#getWriterByName(java.lang.String)
	 * @generated
	 */
	EOperation getCodecInfoHolder__GetWriterByName__String();

	/**
	 * Returns the meta object for class '{@link org.gecko.codec.info.codecinfo.SampleValueReader <em>Sample Value Reader</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sample Value Reader</em>'.
	 * @see org.gecko.codec.info.codecinfo.SampleValueReader
	 * @generated
	 */
	EClass getSampleValueReader();

	/**
	 * Returns the meta object for the attribute '{@link org.gecko.codec.info.codecinfo.SampleValueReader#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.gecko.codec.info.codecinfo.SampleValueReader#getName()
	 * @see #getSampleValueReader()
	 * @generated
	 */
	EAttribute getSampleValueReader_Name();

	/**
	 * Returns the meta object for the '{@link org.gecko.codec.info.codecinfo.SampleValueReader#readValue(java.lang.Object, com.fasterxml.jackson.databind.DeserializationContext) <em>Read Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Read Value</em>' operation.
	 * @see org.gecko.codec.info.codecinfo.SampleValueReader#readValue(java.lang.Object, com.fasterxml.jackson.databind.DeserializationContext)
	 * @generated
	 */
	EOperation getSampleValueReader__ReadValue__Object_DeserializationContext();

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
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.PackageCodecInfoImpl <em>Package Codec Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.PackageCodecInfoImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getPackageCodecInfo()
		 * @generated
		 */
		EClass PACKAGE_CODEC_INFO = eINSTANCE.getPackageCodecInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE_CODEC_INFO__ID = eINSTANCE.getPackageCodecInfo_Id();

		/**
		 * The meta object literal for the '<em><b>EPackage</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_CODEC_INFO__EPACKAGE = eINSTANCE.getPackageCodecInfo_EPackage();

		/**
		 * The meta object literal for the '<em><b>Sub Package Codec Info</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_CODEC_INFO__SUB_PACKAGE_CODEC_INFO = eINSTANCE.getPackageCodecInfo_SubPackageCodecInfo();

		/**
		 * The meta object literal for the '<em><b>EClass Codec Info</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_CODEC_INFO__ECLASS_CODEC_INFO = eINSTANCE.getPackageCodecInfo_EClassCodecInfo();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl <em>EClass Codec Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getEClassCodecInfo()
		 * @generated
		 */
		EClass ECLASS_CODEC_INFO = eINSTANCE.getEClassCodecInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ECLASS_CODEC_INFO__ID = eINSTANCE.getEClassCodecInfo_Id();

		/**
		 * The meta object literal for the '<em><b>Classifier</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_CODEC_INFO__CLASSIFIER = eINSTANCE.getEClassCodecInfo_Classifier();

		/**
		 * The meta object literal for the '<em><b>Identity Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_CODEC_INFO__IDENTITY_INFO = eINSTANCE.getEClassCodecInfo_IdentityInfo();

		/**
		 * The meta object literal for the '<em><b>Type Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_CODEC_INFO__TYPE_INFO = eINSTANCE.getEClassCodecInfo_TypeInfo();

		/**
		 * The meta object literal for the '<em><b>Feature Info</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_CODEC_INFO__FEATURE_INFO = eINSTANCE.getEClassCodecInfo_FeatureInfo();

		/**
		 * The meta object literal for the '<em><b>Reference Codec Info</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_CODEC_INFO__REFERENCE_CODEC_INFO = eINSTANCE.getEClassCodecInfo_ReferenceCodecInfo();

		/**
		 * The meta object literal for the '<em><b>Attribute Codec Info</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_CODEC_INFO__ATTRIBUTE_CODEC_INFO = eINSTANCE.getEClassCodecInfo_AttributeCodecInfo();

		/**
		 * The meta object literal for the '<em><b>Operation Codec Info</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_CODEC_INFO__OPERATION_CODEC_INFO = eINSTANCE.getEClassCodecInfo_OperationCodecInfo();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl <em>Feature Codec Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getFeatureCodecInfo()
		 * @generated
		 */
		EClass FEATURE_CODEC_INFO = eINSTANCE.getFeatureCodecInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_INFO__ID = eINSTANCE.getFeatureCodecInfo_Id();

		/**
		 * The meta object literal for the '<em><b>Features</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_CODEC_INFO__FEATURES = eINSTANCE.getFeatureCodecInfo_Features();

		/**
		 * The meta object literal for the '<em><b>Value Reader Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_INFO__VALUE_READER_NAME = eINSTANCE.getFeatureCodecInfo_ValueReaderName();

		/**
		 * The meta object literal for the '<em><b>Value Writer Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_INFO__VALUE_WRITER_NAME = eINSTANCE.getFeatureCodecInfo_ValueWriterName();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_INFO__TYPE = eINSTANCE.getFeatureCodecInfo_Type();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_INFO__KEY = eINSTANCE.getFeatureCodecInfo_Key();

		/**
		 * The meta object literal for the '<em><b>Ignore</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_INFO__IGNORE = eINSTANCE.getFeatureCodecInfo_Ignore();

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
		 * The meta object literal for the '<em><b>Type Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_INFO__TYPE_STRATEGY = eINSTANCE.getTypeInfo_TypeStrategy();

		/**
		 * The meta object literal for the '<em><b>Ignore Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_INFO__IGNORE_TYPE = eINSTANCE.getTypeInfo_IgnoreType();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl <em>Identity Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getIdentityInfo()
		 * @generated
		 */
		EClass IDENTITY_INFO = eINSTANCE.getIdentityInfo();

		/**
		 * The meta object literal for the '<em><b>Id Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY_INFO__ID_STRATEGY = eINSTANCE.getIdentityInfo_IdStrategy();

		/**
		 * The meta object literal for the '<em><b>Id Separator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY_INFO__ID_SEPARATOR = eINSTANCE.getIdentityInfo_IdSeparator();

		/**
		 * The meta object literal for the '<em><b>Id Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY_INFO__ID_ORDER = eINSTANCE.getIdentityInfo_IdOrder();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.CodecValueReader <em>Codec Value Reader</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.CodecValueReader
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getCodecValueReader()
		 * @generated
		 */
		EClass CODEC_VALUE_READER = eINSTANCE.getCodecValueReader();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_VALUE_READER__NAME = eINSTANCE.getCodecValueReader_Name();

		/**
		 * The meta object literal for the '<em><b>Read Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CODEC_VALUE_READER___READ_VALUE__OBJECT_DESERIALIZATIONCONTEXT = eINSTANCE.getCodecValueReader__ReadValue__Object_DeserializationContext();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.CodecValueWriter <em>Codec Value Writer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.CodecValueWriter
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getCodecValueWriter()
		 * @generated
		 */
		EClass CODEC_VALUE_WRITER = eINSTANCE.getCodecValueWriter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_VALUE_WRITER__NAME = eINSTANCE.getCodecValueWriter_Name();

		/**
		 * The meta object literal for the '<em><b>Write Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CODEC_VALUE_WRITER___WRITE_VALUE__OBJECT_SERIALIZERPROVIDER = eINSTANCE.getCodecValueWriter__WriteValue__Object_SerializerProvider();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.impl.CodecInfoHolderImpl <em>Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoHolderImpl
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getCodecInfoHolder()
		 * @generated
		 */
		EClass CODEC_INFO_HOLDER = eINSTANCE.getCodecInfoHolder();

		/**
		 * The meta object literal for the '<em><b>Info Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_INFO_HOLDER__INFO_TYPE = eINSTANCE.getCodecInfoHolder_InfoType();

		/**
		 * The meta object literal for the '<em><b>Readers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_INFO_HOLDER__READERS = eINSTANCE.getCodecInfoHolder_Readers();

		/**
		 * The meta object literal for the '<em><b>Writers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_INFO_HOLDER__WRITERS = eINSTANCE.getCodecInfoHolder_Writers();

		/**
		 * The meta object literal for the '<em><b>Get Reader By Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CODEC_INFO_HOLDER___GET_READER_BY_NAME__STRING = eINSTANCE.getCodecInfoHolder__GetReaderByName__String();

		/**
		 * The meta object literal for the '<em><b>Get Writer By Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CODEC_INFO_HOLDER___GET_WRITER_BY_NAME__STRING = eINSTANCE.getCodecInfoHolder__GetWriterByName__String();

		/**
		 * The meta object literal for the '{@link org.gecko.codec.info.codecinfo.SampleValueReader <em>Sample Value Reader</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gecko.codec.info.codecinfo.SampleValueReader
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getSampleValueReader()
		 * @generated
		 */
		EClass SAMPLE_VALUE_READER = eINSTANCE.getSampleValueReader();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SAMPLE_VALUE_READER__NAME = eINSTANCE.getSampleValueReader_Name();

		/**
		 * The meta object literal for the '<em><b>Read Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SAMPLE_VALUE_READER___READ_VALUE__OBJECT_DESERIALIZATIONCONTEXT = eINSTANCE.getSampleValueReader__ReadValue__Object_DeserializationContext();

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
		 * The meta object literal for the '<em>Serializer Provider</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.fasterxml.jackson.databind.SerializerProvider
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getSerializerProvider()
		 * @generated
		 */
		EDataType SERIALIZER_PROVIDER = eINSTANCE.getSerializerProvider();

		/**
		 * The meta object literal for the '<em>Deserialization Context</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.fasterxml.jackson.databind.DeserializationContext
		 * @see org.gecko.codec.info.codecinfo.impl.CodecInfoPackageImpl#getDeserializationContext()
		 * @generated
		 */
		EDataType DESERIALIZATION_CONTEXT = eINSTANCE.getDeserializationContext();

	}

} //CodecInfoPackage
