/**
 */
package org.gecko.codec.info.codecinfo.impl;

import com.fasterxml.jackson.databind.SerializerProvider;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.gecko.codec.info.codecinfo.*;

import org.gecko.codec.io.ValueReader;
import org.gecko.codec.io.ValueWriter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CodecInfoFactoryImpl extends EFactoryImpl implements CodecInfoFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CodecInfoFactory init() {
		try {
			CodecInfoFactory theCodecInfoFactory = (CodecInfoFactory)EPackage.Registry.INSTANCE.getEFactory(CodecInfoPackage.eNS_URI);
			if (theCodecInfoFactory != null) {
				return theCodecInfoFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CodecInfoFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodecInfoFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CodecInfoPackage.PACKAGE_CODEC_INFO: return createPackageCodecInfo();
			case CodecInfoPackage.ECLASS_CODEC_INFO: return createEClassCodecInfo();
			case CodecInfoPackage.FEATURE_CODEC_INFO: return createFeatureCodecInfo();
			case CodecInfoPackage.TYPE_INFO: return createTypeInfo();
			case CodecInfoPackage.IDENTITY_INFO: return createIdentityInfo();
			case CodecInfoPackage.REFERENCE_INFO: return createReferenceInfo();
			case CodecInfoPackage.CODEC_VALUE_READER: return createCodecValueReader();
			case CodecInfoPackage.CODEC_VALUE_WRITER: return createCodecValueWriter();
			case CodecInfoPackage.CODEC_INFO_HOLDER: return createCodecInfoHolder();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case CodecInfoPackage.INFO_TYPE:
				return createInfoTypeFromString(eDataType, initialValue);
			case CodecInfoPackage.SERIALIZER_PROVIDER:
				return createSerializerProviderFromString(eDataType, initialValue);
			case CodecInfoPackage.VALUE_READER:
				return createValueReaderFromString(eDataType, initialValue);
			case CodecInfoPackage.VALUE_WRITER:
				return createValueWriterFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case CodecInfoPackage.INFO_TYPE:
				return convertInfoTypeToString(eDataType, instanceValue);
			case CodecInfoPackage.SERIALIZER_PROVIDER:
				return convertSerializerProviderToString(eDataType, instanceValue);
			case CodecInfoPackage.VALUE_READER:
				return convertValueReaderToString(eDataType, instanceValue);
			case CodecInfoPackage.VALUE_WRITER:
				return convertValueWriterToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PackageCodecInfo createPackageCodecInfo() {
		PackageCodecInfoImpl packageCodecInfo = new PackageCodecInfoImpl();
		return packageCodecInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClassCodecInfo createEClassCodecInfo() {
		EClassCodecInfoImpl eClassCodecInfo = new EClassCodecInfoImpl();
		return eClassCodecInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureCodecInfo createFeatureCodecInfo() {
		FeatureCodecInfoImpl featureCodecInfo = new FeatureCodecInfoImpl();
		return featureCodecInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeInfo createTypeInfo() {
		TypeInfoImpl typeInfo = new TypeInfoImpl();
		return typeInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentityInfo createIdentityInfo() {
		IdentityInfoImpl identityInfo = new IdentityInfoImpl();
		return identityInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReferenceInfo createReferenceInfo() {
		ReferenceInfoImpl referenceInfo = new ReferenceInfoImpl();
		return referenceInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CodecValueReader createCodecValueReader() {
		CodecValueReaderImpl codecValueReader = new CodecValueReaderImpl();
		return codecValueReader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CodecValueWriter createCodecValueWriter() {
		CodecValueWriterImpl codecValueWriter = new CodecValueWriterImpl();
		return codecValueWriter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CodecInfoHolder createCodecInfoHolder() {
		CodecInfoHolderImpl codecInfoHolder = new CodecInfoHolderImpl();
		return codecInfoHolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfoType createInfoTypeFromString(EDataType eDataType, String initialValue) {
		InfoType result = InfoType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInfoTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SerializerProvider createSerializerProviderFromString(EDataType eDataType, String initialValue) {
		return (SerializerProvider)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSerializerProviderToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueReader<?, ?> createValueReaderFromString(EDataType eDataType, String initialValue) {
		return (ValueReader<?, ?>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertValueReaderToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueWriter<?, ?> createValueWriterFromString(EDataType eDataType, String initialValue) {
		return (ValueWriter<?, ?>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertValueWriterToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CodecInfoPackage getCodecInfoPackage() {
		return (CodecInfoPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CodecInfoPackage getPackage() {
		return CodecInfoPackage.eINSTANCE;
	}

} //CodecInfoFactoryImpl
