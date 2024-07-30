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
import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.EClassInfo;
import org.gecko.codec.info.codecinfo.FeatureInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageInfo;
import org.gecko.codec.info.codecinfo.TypeInfo;
import org.gecko.codec.info.codecinfo.TypeInfoHolder;
import org.gecko.codec.info.codecinfo.ValueReader;
import org.gecko.codec.info.codecinfo.ValueWriter;

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
	private EClass packageInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eClassInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueReaderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueWriterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeInfoHolderEClass = null;

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
	private EEnum infoTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType deserializationContextEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType serializerProviderEDataType = null;

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
	public EClass getPackageInfo() {
		return packageInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPackageInfo_Id() {
		return (EAttribute)packageInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageInfo_EPackage() {
		return (EReference)packageInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageInfo_SubPackageInfo() {
		return (EReference)packageInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageInfo_EClassInfo() {
		return (EReference)packageInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEClassInfo() {
		return eClassInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEClassInfo_Id() {
		return (EAttribute)eClassInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassInfo_Classifier() {
		return (EReference)eClassInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassInfo_IdentityInfo() {
		return (EReference)eClassInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassInfo_TypeInfo() {
		return (EReference)eClassInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEClassInfo_FeatureInfo() {
		return (EReference)eClassInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureInfo() {
		return featureInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureInfo_Id() {
		return (EAttribute)featureInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureInfo_Features() {
		return (EReference)featureInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureInfo_DefaultKey() {
		return (EAttribute)featureInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureInfo_ValueReaderName() {
		return (EAttribute)featureInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureInfo_ValueWriterName() {
		return (EAttribute)featureInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureInfo_Type() {
		return (EAttribute)featureInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureInfo_Key() {
		return (EAttribute)featureInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValueReader() {
		return valueReaderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValueReader_Name() {
		return (EAttribute)valueReaderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getValueReader__ReadValue__Object_DeserializationContext() {
		return valueReaderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValueWriter() {
		return valueWriterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValueWriter_Name() {
		return (EAttribute)valueWriterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getValueWriter__WriteValue__Object_SerializerProvider() {
		return valueWriterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeInfoHolder() {
		return typeInfoHolderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeInfoHolder_InfoType() {
		return (EAttribute)typeInfoHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeInfoHolder_Readers() {
		return (EReference)typeInfoHolderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeInfoHolder_Writers() {
		return (EReference)typeInfoHolderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTypeInfoHolder__GetReaderByName__String() {
		return typeInfoHolderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTypeInfoHolder__GetWriterByName__String() {
		return typeInfoHolderEClass.getEOperations().get(1);
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
	public EAttribute getTypeInfo_WriteSuperTypes() {
		return (EAttribute)typeInfoEClass.getEStructuralFeatures().get(0);
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
	public EDataType getDeserializationContext() {
		return deserializationContextEDataType;
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
		packageInfoEClass = createEClass(PACKAGE_INFO);
		createEAttribute(packageInfoEClass, PACKAGE_INFO__ID);
		createEReference(packageInfoEClass, PACKAGE_INFO__EPACKAGE);
		createEReference(packageInfoEClass, PACKAGE_INFO__SUB_PACKAGE_INFO);
		createEReference(packageInfoEClass, PACKAGE_INFO__ECLASS_INFO);

		eClassInfoEClass = createEClass(ECLASS_INFO);
		createEAttribute(eClassInfoEClass, ECLASS_INFO__ID);
		createEReference(eClassInfoEClass, ECLASS_INFO__CLASSIFIER);
		createEReference(eClassInfoEClass, ECLASS_INFO__IDENTITY_INFO);
		createEReference(eClassInfoEClass, ECLASS_INFO__TYPE_INFO);
		createEReference(eClassInfoEClass, ECLASS_INFO__FEATURE_INFO);

		featureInfoEClass = createEClass(FEATURE_INFO);
		createEAttribute(featureInfoEClass, FEATURE_INFO__ID);
		createEReference(featureInfoEClass, FEATURE_INFO__FEATURES);
		createEAttribute(featureInfoEClass, FEATURE_INFO__DEFAULT_KEY);
		createEAttribute(featureInfoEClass, FEATURE_INFO__VALUE_READER_NAME);
		createEAttribute(featureInfoEClass, FEATURE_INFO__VALUE_WRITER_NAME);
		createEAttribute(featureInfoEClass, FEATURE_INFO__TYPE);
		createEAttribute(featureInfoEClass, FEATURE_INFO__KEY);

		valueReaderEClass = createEClass(VALUE_READER);
		createEAttribute(valueReaderEClass, VALUE_READER__NAME);
		createEOperation(valueReaderEClass, VALUE_READER___READ_VALUE__OBJECT_DESERIALIZATIONCONTEXT);

		valueWriterEClass = createEClass(VALUE_WRITER);
		createEAttribute(valueWriterEClass, VALUE_WRITER__NAME);
		createEOperation(valueWriterEClass, VALUE_WRITER___WRITE_VALUE__OBJECT_SERIALIZERPROVIDER);

		typeInfoHolderEClass = createEClass(TYPE_INFO_HOLDER);
		createEAttribute(typeInfoHolderEClass, TYPE_INFO_HOLDER__INFO_TYPE);
		createEReference(typeInfoHolderEClass, TYPE_INFO_HOLDER__READERS);
		createEReference(typeInfoHolderEClass, TYPE_INFO_HOLDER__WRITERS);
		createEOperation(typeInfoHolderEClass, TYPE_INFO_HOLDER___GET_READER_BY_NAME__STRING);
		createEOperation(typeInfoHolderEClass, TYPE_INFO_HOLDER___GET_WRITER_BY_NAME__STRING);

		typeInfoEClass = createEClass(TYPE_INFO);
		createEAttribute(typeInfoEClass, TYPE_INFO__WRITE_SUPER_TYPES);

		// Create enums
		infoTypeEEnum = createEEnum(INFO_TYPE);

		// Create data types
		deserializationContextEDataType = createEDataType(DESERIALIZATION_CONTEXT);
		serializerProviderEDataType = createEDataType(SERIALIZER_PROVIDER);
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
		ETypeParameter valueReaderEClass_V = addETypeParameter(valueReaderEClass, "V");
		ETypeParameter valueReaderEClass_T = addETypeParameter(valueReaderEClass, "T");
		ETypeParameter valueWriterEClass_T = addETypeParameter(valueWriterEClass, "T");
		ETypeParameter valueWriterEClass_V = addETypeParameter(valueWriterEClass, "V");

		// Set bounds for type parameters

		// Add supertypes to classes
		typeInfoEClass.getESuperTypes().add(this.getFeatureInfo());

		// Initialize classes, features, and operations; add parameters
		initEClass(packageInfoEClass, PackageInfo.class, "PackageInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPackageInfo_Id(), theEcorePackage.getEString(), "id", null, 0, 1, PackageInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageInfo_EPackage(), theEcorePackage.getEPackage(), null, "ePackage", null, 0, 1, PackageInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageInfo_SubPackageInfo(), this.getPackageInfo(), null, "subPackageInfo", null, 0, -1, PackageInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageInfo_EClassInfo(), this.getEClassInfo(), null, "eClassInfo", null, 0, -1, PackageInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eClassInfoEClass, EClassInfo.class, "EClassInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEClassInfo_Id(), theEcorePackage.getEString(), "id", null, 0, 1, EClassInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassInfo_Classifier(), theEcorePackage.getEClassifier(), null, "classifier", null, 0, 1, EClassInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassInfo_IdentityInfo(), this.getFeatureInfo(), null, "identityInfo", null, 0, 1, EClassInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassInfo_TypeInfo(), this.getFeatureInfo(), null, "typeInfo", null, 0, 1, EClassInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassInfo_FeatureInfo(), this.getFeatureInfo(), null, "featureInfo", null, 0, 1, EClassInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureInfoEClass, FeatureInfo.class, "FeatureInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFeatureInfo_Id(), ecorePackage.getEString(), "id", null, 0, 1, FeatureInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureInfo_Features(), theEcorePackage.getEStructuralFeature(), null, "features", null, 0, -1, FeatureInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureInfo_DefaultKey(), theEcorePackage.getEString(), "defaultKey", "_id", 0, 1, FeatureInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureInfo_ValueReaderName(), theEcorePackage.getEString(), "valueReaderName", "DEFAULT", 0, 1, FeatureInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureInfo_ValueWriterName(), theEcorePackage.getEString(), "valueWriterName", "DEFAULT", 0, 1, FeatureInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureInfo_Type(), this.getInfoType(), "type", null, 0, 1, FeatureInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureInfo_Key(), theEcorePackage.getEString(), "key", null, 1, 1, FeatureInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueReaderEClass, ValueReader.class, "ValueReader", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValueReader_Name(), theEcorePackage.getEString(), "name", null, 1, 1, ValueReader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getValueReader__ReadValue__Object_DeserializationContext(), null, "readValue", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(valueReaderEClass_V);
		addEParameter(op, g1, "value", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDeserializationContext(), "ctx", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(valueReaderEClass_T);
		initEOperation(op, g1);

		initEClass(valueWriterEClass, ValueWriter.class, "ValueWriter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValueWriter_Name(), theEcorePackage.getEString(), "name", null, 1, 1, ValueWriter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getValueWriter__WriteValue__Object_SerializerProvider(), null, "writeValue", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(valueWriterEClass_T);
		addEParameter(op, g1, "value", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSerializerProvider(), "provider", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(valueWriterEClass_V);
		initEOperation(op, g1);

		initEClass(typeInfoHolderEClass, TypeInfoHolder.class, "TypeInfoHolder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeInfoHolder_InfoType(), this.getInfoType(), "infoType", null, 1, 1, TypeInfoHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getValueReader());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getTypeInfoHolder_Readers(), g1, null, "readers", null, 0, -1, TypeInfoHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getTypeInfoHolder_Readers().getEKeys().add(this.getValueReader_Name());
		g1 = createEGenericType(this.getValueWriter());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getTypeInfoHolder_Writers(), g1, null, "writers", null, 0, -1, TypeInfoHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getTypeInfoHolder_Writers().getEKeys().add(this.getValueWriter_Name());

		op = initEOperation(getTypeInfoHolder__GetReaderByName__String(), null, "getReaderByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "readerName", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(this.getValueReader());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getTypeInfoHolder__GetWriterByName__String(), null, "getWriterByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "readerName", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(this.getValueWriter());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(typeInfoEClass, TypeInfo.class, "TypeInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeInfo_WriteSuperTypes(), theEcorePackage.getEBoolean(), "writeSuperTypes", "false", 0, 1, TypeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(infoTypeEEnum, InfoType.class, "InfoType");
		addEEnumLiteral(infoTypeEEnum, InfoType.IDENTITY);
		addEEnumLiteral(infoTypeEEnum, InfoType.TYPE);
		addEEnumLiteral(infoTypeEEnum, InfoType.FEATURE);
		addEEnumLiteral(infoTypeEEnum, InfoType.REFERENCE);
		addEEnumLiteral(infoTypeEEnum, InfoType.OBJECT);
		addEEnumLiteral(infoTypeEEnum, InfoType.OTHER);

		// Initialize data types
		initEDataType(deserializationContextEDataType, DeserializationContext.class, "DeserializationContext", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(serializerProviderEDataType, SerializerProvider.class, "SerializerProvider", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

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
	}

} //CodecInfoPackageImpl
