/**
 */
package org.gecko.codec.info.codecinfo.impl;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.gecko.codec.info.codecinfo.CodecInfoFactory;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
import org.gecko.codec.info.codecinfo.ReferenceInfo;
import org.gecko.codec.info.codecinfo.SampleValueReader;
import org.gecko.codec.info.codecinfo.TypeInfo;

import org.gecko.codec.io.ValueReader;
import org.gecko.codec.io.ValueWriter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CodecInfoPackageImpl extends EPackageImpl implements CodecInfoPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageCodecInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eClassCodecInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureCodecInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identityInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass codecValueReaderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass codecValueWriterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass codecInfoHolderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sampleValueReaderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum infoTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType serializerProviderEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType valueReaderEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType valueWriterEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType deSerializationContextEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CodecInfoPackageImpl() {
		super(eNS_URI, CodecInfoFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link CodecInfoPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CodecInfoPackage init() {
		if (isInited) return (CodecInfoPackage)EPackage.Registry.INSTANCE.getEPackage(CodecInfoPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCodecInfoPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CodecInfoPackageImpl theCodecInfoPackage = registeredCodecInfoPackage instanceof CodecInfoPackageImpl ? (CodecInfoPackageImpl)registeredCodecInfoPackage : new CodecInfoPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCodecInfoPackage.createPackageContents();

		// Initialize created meta-data
		theCodecInfoPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCodecInfoPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CodecInfoPackage.eNS_URI, theCodecInfoPackage);
		return theCodecInfoPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPackageCodecInfo() {
		return packageCodecInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPackageCodecInfo_Id() {
		return (EAttribute)packageCodecInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageCodecInfo_EPackage() {
		return (EReference)packageCodecInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageCodecInfo_SubPackageCodecInfo() {
		return (EReference)packageCodecInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageCodecInfo_EClassCodecInfo() {
		return (EReference)packageCodecInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEClassCodecInfo() {
		return eClassCodecInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEClassCodecInfo_Id() {
		return (EAttribute)eClassCodecInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassCodecInfo_Classifier() {
		return (EReference)eClassCodecInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassCodecInfo_IdentityInfo() {
		return (EReference)eClassCodecInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassCodecInfo_TypeInfo() {
		return (EReference)eClassCodecInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassCodecInfo_FeatureInfo() {
		return (EReference)eClassCodecInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassCodecInfo_ReferenceInfo() {
		return (EReference)eClassCodecInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEClassCodecInfo_SerializeDefaultValue() {
		return (EAttribute)eClassCodecInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEClassCodecInfo_SerializeArrayBatched() {
		return (EAttribute)eClassCodecInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEClassCodecInfo_UseNamesFromExtendedMetaData() {
		return (EAttribute)eClassCodecInfoEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureCodecInfo() {
		return featureCodecInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureCodecInfo_Id() {
		return (EAttribute)featureCodecInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureCodecInfo_Features() {
		return (EReference)featureCodecInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureCodecInfo_DefaultKey() {
		return (EAttribute)featureCodecInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureCodecInfo_ValueReaderName() {
		return (EAttribute)featureCodecInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureCodecInfo_ValueWriterName() {
		return (EAttribute)featureCodecInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureCodecInfo_Type() {
		return (EAttribute)featureCodecInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureCodecInfo_Key() {
		return (EAttribute)featureCodecInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeInfo() {
		return typeInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeInfo_SerializeType() {
		return (EAttribute)typeInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeInfo_SerializeSuperTypes() {
		return (EAttribute)typeInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeInfo_SerializeSuperTypeAsArray() {
		return (EAttribute)typeInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIdentityInfo() {
		return identityInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentityInfo_UseId() {
		return (EAttribute)identityInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentityInfo_UseIdField() {
		return (EAttribute)identityInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentityInfo_IdTop() {
		return (EAttribute)identityInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentityInfo_SerializeIdField() {
		return (EAttribute)identityInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentityInfo_IdFeatureAsPrimaryKey() {
		return (EAttribute)identityInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReferenceInfo() {
		return referenceInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCodecValueReader() {
		return codecValueReaderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCodecValueReader_Name() {
		return (EAttribute)codecValueReaderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCodecValueReader_Reader() {
		return (EAttribute)codecValueReaderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCodecValueWriter() {
		return codecValueWriterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCodecValueWriter_Name() {
		return (EAttribute)codecValueWriterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCodecValueWriter_Writer() {
		return (EAttribute)codecValueWriterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCodecInfoHolder() {
		return codecInfoHolderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCodecInfoHolder_InfoType() {
		return (EAttribute)codecInfoHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCodecInfoHolder_Readers() {
		return (EReference)codecInfoHolderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCodecInfoHolder_Writers() {
		return (EReference)codecInfoHolderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getCodecInfoHolder__GetReaderByName__String() {
		return codecInfoHolderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getCodecInfoHolder__GetWriterByName__String() {
		return codecInfoHolderEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSampleValueReader() {
		return sampleValueReaderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSampleValueReader_Name() {
		return (EAttribute)sampleValueReaderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSampleValueReader__ReadValue__Object_DeserializationContext() {
		return sampleValueReaderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getInfoType() {
		return infoTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSerializerProvider() {
		return serializerProviderEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getValueReader() {
		return valueReaderEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getValueWriter() {
		return valueWriterEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDeSerializationContext() {
		return deSerializationContextEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CodecInfoFactory getCodecInfoFactory() {
		return (CodecInfoFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		packageCodecInfoEClass = createEClass(PACKAGE_CODEC_INFO);
		createEAttribute(packageCodecInfoEClass, PACKAGE_CODEC_INFO__ID);
		createEReference(packageCodecInfoEClass, PACKAGE_CODEC_INFO__EPACKAGE);
		createEReference(packageCodecInfoEClass, PACKAGE_CODEC_INFO__SUB_PACKAGE_CODEC_INFO);
		createEReference(packageCodecInfoEClass, PACKAGE_CODEC_INFO__ECLASS_CODEC_INFO);

		eClassCodecInfoEClass = createEClass(ECLASS_CODEC_INFO);
		createEAttribute(eClassCodecInfoEClass, ECLASS_CODEC_INFO__ID);
		createEReference(eClassCodecInfoEClass, ECLASS_CODEC_INFO__CLASSIFIER);
		createEReference(eClassCodecInfoEClass, ECLASS_CODEC_INFO__IDENTITY_INFO);
		createEReference(eClassCodecInfoEClass, ECLASS_CODEC_INFO__TYPE_INFO);
		createEReference(eClassCodecInfoEClass, ECLASS_CODEC_INFO__FEATURE_INFO);
		createEReference(eClassCodecInfoEClass, ECLASS_CODEC_INFO__REFERENCE_INFO);
		createEAttribute(eClassCodecInfoEClass, ECLASS_CODEC_INFO__SERIALIZE_DEFAULT_VALUE);
		createEAttribute(eClassCodecInfoEClass, ECLASS_CODEC_INFO__SERIALIZE_ARRAY_BATCHED);
		createEAttribute(eClassCodecInfoEClass, ECLASS_CODEC_INFO__USE_NAMES_FROM_EXTENDED_META_DATA);

		featureCodecInfoEClass = createEClass(FEATURE_CODEC_INFO);
		createEAttribute(featureCodecInfoEClass, FEATURE_CODEC_INFO__ID);
		createEReference(featureCodecInfoEClass, FEATURE_CODEC_INFO__FEATURES);
		createEAttribute(featureCodecInfoEClass, FEATURE_CODEC_INFO__DEFAULT_KEY);
		createEAttribute(featureCodecInfoEClass, FEATURE_CODEC_INFO__VALUE_READER_NAME);
		createEAttribute(featureCodecInfoEClass, FEATURE_CODEC_INFO__VALUE_WRITER_NAME);
		createEAttribute(featureCodecInfoEClass, FEATURE_CODEC_INFO__TYPE);
		createEAttribute(featureCodecInfoEClass, FEATURE_CODEC_INFO__KEY);

		typeInfoEClass = createEClass(TYPE_INFO);
		createEAttribute(typeInfoEClass, TYPE_INFO__SERIALIZE_TYPE);
		createEAttribute(typeInfoEClass, TYPE_INFO__SERIALIZE_SUPER_TYPES);
		createEAttribute(typeInfoEClass, TYPE_INFO__SERIALIZE_SUPER_TYPE_AS_ARRAY);

		identityInfoEClass = createEClass(IDENTITY_INFO);
		createEAttribute(identityInfoEClass, IDENTITY_INFO__USE_ID);
		createEAttribute(identityInfoEClass, IDENTITY_INFO__USE_ID_FIELD);
		createEAttribute(identityInfoEClass, IDENTITY_INFO__ID_TOP);
		createEAttribute(identityInfoEClass, IDENTITY_INFO__SERIALIZE_ID_FIELD);
		createEAttribute(identityInfoEClass, IDENTITY_INFO__ID_FEATURE_AS_PRIMARY_KEY);

		referenceInfoEClass = createEClass(REFERENCE_INFO);

		codecValueReaderEClass = createEClass(CODEC_VALUE_READER);
		createEAttribute(codecValueReaderEClass, CODEC_VALUE_READER__NAME);
		createEAttribute(codecValueReaderEClass, CODEC_VALUE_READER__READER);

		codecValueWriterEClass = createEClass(CODEC_VALUE_WRITER);
		createEAttribute(codecValueWriterEClass, CODEC_VALUE_WRITER__NAME);
		createEAttribute(codecValueWriterEClass, CODEC_VALUE_WRITER__WRITER);

		codecInfoHolderEClass = createEClass(CODEC_INFO_HOLDER);
		createEAttribute(codecInfoHolderEClass, CODEC_INFO_HOLDER__INFO_TYPE);
		createEReference(codecInfoHolderEClass, CODEC_INFO_HOLDER__READERS);
		createEReference(codecInfoHolderEClass, CODEC_INFO_HOLDER__WRITERS);
		createEOperation(codecInfoHolderEClass, CODEC_INFO_HOLDER___GET_READER_BY_NAME__STRING);
		createEOperation(codecInfoHolderEClass, CODEC_INFO_HOLDER___GET_WRITER_BY_NAME__STRING);

		sampleValueReaderEClass = createEClass(SAMPLE_VALUE_READER);
		createEAttribute(sampleValueReaderEClass, SAMPLE_VALUE_READER__NAME);
		createEOperation(sampleValueReaderEClass, SAMPLE_VALUE_READER___READ_VALUE__OBJECT_DESERIALIZATIONCONTEXT);

		// Create enums
		infoTypeEEnum = createEEnum(INFO_TYPE);

		// Create data types
		serializerProviderEDataType = createEDataType(SERIALIZER_PROVIDER);
		valueReaderEDataType = createEDataType(VALUE_READER);
		valueWriterEDataType = createEDataType(VALUE_WRITER);
		deSerializationContextEDataType = createEDataType(DE_SERIALIZATION_CONTEXT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters
		ETypeParameter sampleValueReaderEClass_T = addETypeParameter(sampleValueReaderEClass, "T");
		ETypeParameter sampleValueReaderEClass_V = addETypeParameter(sampleValueReaderEClass, "V");
		addETypeParameter(valueReaderEDataType, "V");
		addETypeParameter(valueReaderEDataType, "T");
		addETypeParameter(valueWriterEDataType, "T");
		addETypeParameter(valueWriterEDataType, "V");

		// Set bounds for type parameters

		// Add supertypes to classes
		typeInfoEClass.getESuperTypes().add(this.getFeatureCodecInfo());
		identityInfoEClass.getESuperTypes().add(this.getFeatureCodecInfo());
		referenceInfoEClass.getESuperTypes().add(this.getFeatureCodecInfo());

		// Initialize classes, features, and operations; add parameters
		initEClass(packageCodecInfoEClass, PackageCodecInfo.class, "PackageCodecInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPackageCodecInfo_Id(), theEcorePackage.getEString(), "id", null, 0, 1, PackageCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageCodecInfo_EPackage(), theEcorePackage.getEPackage(), null, "ePackage", null, 0, 1, PackageCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageCodecInfo_SubPackageCodecInfo(), this.getPackageCodecInfo(), null, "subPackageCodecInfo", null, 0, -1, PackageCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageCodecInfo_EClassCodecInfo(), this.getEClassCodecInfo(), null, "eClassCodecInfo", null, 0, -1, PackageCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eClassCodecInfoEClass, EClassCodecInfo.class, "EClassCodecInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEClassCodecInfo_Id(), theEcorePackage.getEString(), "id", null, 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassCodecInfo_Classifier(), theEcorePackage.getEClassifier(), null, "classifier", null, 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassCodecInfo_IdentityInfo(), this.getIdentityInfo(), null, "identityInfo", null, 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassCodecInfo_TypeInfo(), this.getTypeInfo(), null, "typeInfo", null, 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassCodecInfo_FeatureInfo(), this.getFeatureCodecInfo(), null, "featureInfo", null, 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassCodecInfo_ReferenceInfo(), this.getReferenceInfo(), null, "referenceInfo", null, 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEClassCodecInfo_SerializeDefaultValue(), theEcorePackage.getEBoolean(), "serializeDefaultValue", "false", 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEClassCodecInfo_SerializeArrayBatched(), theEcorePackage.getEBoolean(), "serializeArrayBatched", "false", 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEClassCodecInfo_UseNamesFromExtendedMetaData(), theEcorePackage.getEBoolean(), "useNamesFromExtendedMetaData", "true", 0, 1, EClassCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureCodecInfoEClass, FeatureCodecInfo.class, "FeatureCodecInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFeatureCodecInfo_Id(), ecorePackage.getEString(), "id", null, 0, 1, FeatureCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureCodecInfo_Features(), theEcorePackage.getEStructuralFeature(), null, "features", null, 0, -1, FeatureCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureCodecInfo_DefaultKey(), theEcorePackage.getEString(), "defaultKey", "_id", 0, 1, FeatureCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureCodecInfo_ValueReaderName(), theEcorePackage.getEString(), "valueReaderName", "DEFAULT", 0, 1, FeatureCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureCodecInfo_ValueWriterName(), theEcorePackage.getEString(), "valueWriterName", "DEFAULT", 0, 1, FeatureCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureCodecInfo_Type(), this.getInfoType(), "type", null, 0, 1, FeatureCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureCodecInfo_Key(), theEcorePackage.getEString(), "key", null, 1, 1, FeatureCodecInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeInfoEClass, TypeInfo.class, "TypeInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeInfo_SerializeType(), theEcorePackage.getEBoolean(), "serializeType", "true", 0, 1, TypeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTypeInfo_SerializeSuperTypes(), theEcorePackage.getEBoolean(), "serializeSuperTypes", "false", 0, 1, TypeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTypeInfo_SerializeSuperTypeAsArray(), theEcorePackage.getEBoolean(), "serializeSuperTypeAsArray", "true", 0, 1, TypeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(identityInfoEClass, IdentityInfo.class, "IdentityInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentityInfo_UseId(), theEcorePackage.getEBoolean(), "useId", "true", 0, 1, IdentityInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentityInfo_UseIdField(), theEcorePackage.getEBoolean(), "useIdField", "true", 0, 1, IdentityInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentityInfo_IdTop(), theEcorePackage.getEBoolean(), "idTop", "true", 0, 1, IdentityInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentityInfo_SerializeIdField(), theEcorePackage.getEBoolean(), "serializeIdField", "false", 0, 1, IdentityInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentityInfo_IdFeatureAsPrimaryKey(), theEcorePackage.getEBoolean(), "idFeatureAsPrimaryKey", "true", 0, 1, IdentityInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenceInfoEClass, ReferenceInfo.class, "ReferenceInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(codecValueReaderEClass, CodecValueReader.class, "CodecValueReader", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCodecValueReader_Name(), theEcorePackage.getEString(), "name", null, 1, 1, CodecValueReader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCodecValueReader_Reader(), this.getValueReader(), "reader", null, 0, 1, CodecValueReader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(codecValueWriterEClass, CodecValueWriter.class, "CodecValueWriter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCodecValueWriter_Name(), theEcorePackage.getEString(), "name", null, 1, 1, CodecValueWriter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCodecValueWriter_Writer(), this.getValueWriter(), "writer", null, 0, 1, CodecValueWriter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(codecInfoHolderEClass, CodecInfoHolder.class, "CodecInfoHolder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCodecInfoHolder_InfoType(), this.getInfoType(), "infoType", null, 1, 1, CodecInfoHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCodecInfoHolder_Readers(), this.getCodecValueReader(), null, "readers", null, 0, -1, CodecInfoHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getCodecInfoHolder_Readers().getEKeys().add(this.getCodecValueReader_Name());
		initEReference(getCodecInfoHolder_Writers(), this.getCodecValueWriter(), null, "writers", null, 0, -1, CodecInfoHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getCodecInfoHolder_Writers().getEKeys().add(this.getCodecValueWriter_Name());

		EOperation op = initEOperation(getCodecInfoHolder__GetReaderByName__String(), this.getCodecValueReader(), "getReaderByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "readerName", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getCodecInfoHolder__GetWriterByName__String(), this.getCodecValueWriter(), "getWriterByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "writerName", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(sampleValueReaderEClass, SampleValueReader.class, "SampleValueReader", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSampleValueReader_Name(), theEcorePackage.getEString(), "name", null, 1, 1, SampleValueReader.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getSampleValueReader__ReadValue__Object_DeserializationContext(), null, "readValue", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(sampleValueReaderEClass_V);
		addEParameter(op, g1, "value", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDeSerializationContext(), "context", 1, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(sampleValueReaderEClass_T);
		initEOperation(op, g1);

		// Initialize enums and add enum literals
		initEEnum(infoTypeEEnum, InfoType.class, "InfoType");
		addEEnumLiteral(infoTypeEEnum, InfoType.IDENTITY);
		addEEnumLiteral(infoTypeEEnum, InfoType.TYPE);
		addEEnumLiteral(infoTypeEEnum, InfoType.FEATURE);
		addEEnumLiteral(infoTypeEEnum, InfoType.REFERENCE);
		addEEnumLiteral(infoTypeEEnum, InfoType.OBJECT);
		addEEnumLiteral(infoTypeEEnum, InfoType.OTHER);

		// Initialize data types
		initEDataType(serializerProviderEDataType, SerializerProvider.class, "SerializerProvider", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(valueReaderEDataType, ValueReader.class, "ValueReader", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(valueWriterEDataType, ValueWriter.class, "ValueWriter", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(deSerializationContextEDataType, DeserializationContext.class, "DeSerializationContext", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// Version
		createVersionAnnotations();
		// http://www.eclipse.org/emf/2002/GenModel
		createGenModelAnnotations();
	}

	/**
	 * Initializes the annotations for <b>Version</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createVersionAnnotations() {
		String source = "Version";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "value", "1.0"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/GenModel</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createGenModelAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/GenModel";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "complianceLevel", "17.0",
			   "basePackage", "org.gecko.codec.info",
			   "fileExtensions", "codecinfo",
			   "oSGiCompatible", "true",
			   "resource", "XMI"
		   });
		addAnnotation
		  (packageCodecInfoEClass,
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getEClassCodecInfo_SerializeDefaultValue(),
		   source,
		   new String[] {
			   "documentation", "Option used to indicate the module to serialize default attributes values.  Default values are not serialized by default."
		   });
		addAnnotation
		  (getEClassCodecInfo_SerializeArrayBatched(),
		   source,
		   new String[] {
			   "documentation", "Setting this option to Boolean.TRUE  will send lists and arrays using the writeArray callbacks.\\n Per default the serialization happens with startArray, then calling writeValue for each element. "
		   });
		addAnnotation
		  (getEClassCodecInfo_UseNamesFromExtendedMetaData(),
		   source,
		   new String[] {
			   "documentation", "Option used to indicate whether feature names specified in {@link org.eclipse.emf.ecore.util.ExtendedMetaData} annotations should be respected."
		   });
		addAnnotation
		  (getTypeInfo_SerializeType(),
		   source,
		   new String[] {
			   "documentation", "Option used to indicate the module to use the default type serializer if none are provided. The type serializer used by default is ETypeSerializer."
		   });
		addAnnotation
		  (getTypeInfo_SerializeSuperTypes(),
		   source,
		   new String[] {
			   "documentation", "To avoid writing unnecessary URIs in the result format, we write eClassUris only for the root  class and for EReferences,\\n where the actual value does not equal but inherit from the stated reference type.\\n  By setting this option to Boolean.TRUE, all eClass URIs will be written regardless. "
		   });
		addAnnotation
		  (getTypeInfo_SerializeSuperTypeAsArray(),
		   source,
		   new String[] {
			   "documentation", "By setting this to Boolean.TRUE the supertypes are written as an array of URIs."
		   });
		addAnnotation
		  (getIdentityInfo_UseId(),
		   source,
		   new String[] {
			   "documentation", "Option used to indicate the module to use the default ID serializer if none are provided. The ID serializer used by default is IdSerializer."
		   });
		addAnnotation
		  (getIdentityInfo_UseIdField(),
		   source,
		   new String[] {
			   "documentation", "Option used to indicate the module to use the ID field of the EObject."
		   });
		addAnnotation
		  (getIdentityInfo_IdTop(),
		   source,
		   new String[] {
			   "documentation", "Option used to indicate the module to serialize the id field on top of the document."
		   });
		addAnnotation
		  (getIdentityInfo_SerializeIdField(),
		   source,
		   new String[] {
			   "documentation", "Option used to indicate the module to additionally serialize the id field of an EObject as it is.\n  This is usually not needed, because the id key always holds the ID at the first position.  \n This id-field itself can be found at a later index. So finding it may cost a lot of effort.\n  It can be useful with useId TRUE and useIdField FALSE  and additionally store this \n  id field, while using the URI fragment or Resource ID as primary key"
		   });
		addAnnotation
		  (getIdentityInfo_IdFeatureAsPrimaryKey(),
		   source,
		   new String[] {
			   "documentation", "If it is set to Boolean.TRUE and the ID was not specified in the URI, the value of the ID attribute will be used as the primary key if it exists."
		   });
		addAnnotation
		  (getCodecInfoHolder__GetReaderByName__String(),
		   source,
		   new String[] {
			   "body", "return getReaders().stream().filter(r -> r.getName() == readerName).findFirst().orElse(null);"
		   });
		addAnnotation
		  (getCodecInfoHolder__GetWriterByName__String(),
		   source,
		   new String[] {
			   "body", "return getWriters().stream().filter(w -> w.getName() == writerName).findFirst().orElse(null);"
		   });
	}

} //CodecInfoPackageImpl
