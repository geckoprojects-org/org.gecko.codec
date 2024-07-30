/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.ecore.EFactory;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage
 * @generated
 */
@ProviderType
public interface CodecInfoFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CodecInfoFactory eINSTANCE = org.gecko.codec.info.codecinfo.impl.CodecInfoFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Package Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Package Info</em>'.
	 * @generated
	 */
	PackageInfo createPackageInfo();

	/**
	 * Returns a new object of class '<em>EClass Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EClass Info</em>'.
	 * @generated
	 */
	EClassInfo createEClassInfo();

	/**
	 * Returns a new object of class '<em>Feature Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Info</em>'.
	 * @generated
	 */
	FeatureInfo createFeatureInfo();

	/**
	 * Returns a new object of class '<em>Type Info Holder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Info Holder</em>'.
	 * @generated
	 */
	TypeInfoHolder createTypeInfoHolder();

	/**
	 * Returns a new object of class '<em>Type Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Info</em>'.
	 * @generated
	 */
	TypeInfo createTypeInfo();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CodecInfoPackage getCodecInfoPackage();

} //CodecInfoFactory
