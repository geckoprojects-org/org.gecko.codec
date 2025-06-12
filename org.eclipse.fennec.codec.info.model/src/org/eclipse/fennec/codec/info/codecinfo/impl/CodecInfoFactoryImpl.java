/**
 */
package org.eclipse.fennec.codec.info.codecinfo.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fennec.codec.info.codecinfo.*;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;

import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;

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
			case CodecInfoPackage.PACKAGE_CODEC_INFO: return (EObject)createPackageCodecInfo();
			case CodecInfoPackage.ECLASS_CODEC_INFO: return (EObject)createEClassCodecInfo();
			case CodecInfoPackage.FEATURE_CODEC_INFO: return (EObject)createFeatureCodecInfo();
			case CodecInfoPackage.TYPE_INFO: return (EObject)createTypeInfo();
			case CodecInfoPackage.SUPER_TYPE_INFO: return (EObject)createSuperTypeInfo();
			case CodecInfoPackage.IDENTITY_INFO: return (EObject)createIdentityInfo();
			case CodecInfoPackage.CODEC_INFO_HOLDER: return (EObject)createCodecInfoHolder();
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
			case CodecInfoPackage.SERIALIZATION_CONTEXT:
				return createSerializationContextFromString(eDataType, initialValue);
			case CodecInfoPackage.DESERIALIZATION_CONTEXT:
				return createDeserializationContextFromString(eDataType, initialValue);
			case CodecInfoPackage.JSON_GENERATOR:
				return createJsonGeneratorFromString(eDataType, initialValue);
			case CodecInfoPackage.JSON_PARSER:
				return createJsonParserFromString(eDataType, initialValue);
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
			case CodecInfoPackage.SERIALIZATION_CONTEXT:
				return convertSerializationContextToString(eDataType, instanceValue);
			case CodecInfoPackage.DESERIALIZATION_CONTEXT:
				return convertDeserializationContextToString(eDataType, instanceValue);
			case CodecInfoPackage.JSON_GENERATOR:
				return convertJsonGeneratorToString(eDataType, instanceValue);
			case CodecInfoPackage.JSON_PARSER:
				return convertJsonParserToString(eDataType, instanceValue);
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
	public SuperTypeInfo createSuperTypeInfo() {
		SuperTypeInfoImpl superTypeInfo = new SuperTypeInfoImpl();
		return superTypeInfo;
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
	public SerializationContext createSerializationContextFromString(EDataType eDataType, String initialValue) {
		return (SerializationContext)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSerializationContextToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeserializationContext createDeserializationContextFromString(EDataType eDataType, String initialValue) {
		return (DeserializationContext)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDeserializationContextToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JsonGenerator createJsonGeneratorFromString(EDataType eDataType, String initialValue) {
		return (JsonGenerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJsonGeneratorToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JsonParser createJsonParserFromString(EDataType eDataType, String initialValue) {
		return (JsonParser)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJsonParserToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
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
