/**
 */
package org.gecko.codec.info.codecinfo.impl;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.gecko.codec.info.codecinfo.*;

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
			case CodecInfoPackage.PACKAGE_INFO: return createPackageInfo();
			case CodecInfoPackage.ECLASS_INFO: return createEClassInfo();
			case CodecInfoPackage.FEATURE_INFO: return createFeatureInfo();
			case CodecInfoPackage.TYPE_INFO_HOLDER: return createTypeInfoHolder();
			case CodecInfoPackage.TYPE_INFO: return createTypeInfo();
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
			case CodecInfoPackage.DESERIALIZATION_CONTEXT:
				return createDeserializationContextFromString(eDataType, initialValue);
			case CodecInfoPackage.SERIALIZER_PROVIDER:
				return createSerializerProviderFromString(eDataType, initialValue);
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
			case CodecInfoPackage.DESERIALIZATION_CONTEXT:
				return convertDeserializationContextToString(eDataType, instanceValue);
			case CodecInfoPackage.SERIALIZER_PROVIDER:
				return convertSerializerProviderToString(eDataType, instanceValue);
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
	public PackageInfo createPackageInfo() {
		PackageInfoImpl packageInfo = new PackageInfoImpl();
		return packageInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClassInfo createEClassInfo() {
		EClassInfoImpl eClassInfo = new EClassInfoImpl();
		return eClassInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureInfo createFeatureInfo() {
		FeatureInfoImpl featureInfo = new FeatureInfoImpl();
		return featureInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeInfoHolder createTypeInfoHolder() {
		TypeInfoHolderImpl typeInfoHolder = new TypeInfoHolderImpl();
		return typeInfoHolder;
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