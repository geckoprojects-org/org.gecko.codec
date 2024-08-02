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
	 * Returns a new object of class '<em>Package Codec Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Package Codec Info</em>'.
	 * @generated
	 */
	PackageCodecInfo createPackageCodecInfo();

	/**
	 * Returns a new object of class '<em>EClass Codec Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EClass Codec Info</em>'.
	 * @generated
	 */
	EClassCodecInfo createEClassCodecInfo();

	/**
	 * Returns a new object of class '<em>Feature Codec Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Codec Info</em>'.
	 * @generated
	 */
	FeatureCodecInfo createFeatureCodecInfo();

	/**
	 * Returns a new object of class '<em>Type Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Info</em>'.
	 * @generated
	 */
	TypeInfo createTypeInfo();

	/**
	 * Returns a new object of class '<em>Identity Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Identity Info</em>'.
	 * @generated
	 */
	IdentityInfo createIdentityInfo();

	/**
	 * Returns a new object of class '<em>Reference Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reference Info</em>'.
	 * @generated
	 */
	ReferenceInfo createReferenceInfo();

	/**
	 * Returns a new object of class '<em>Codec Value Reader</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Codec Value Reader</em>'.
	 * @generated
	 */
	CodecValueReader createCodecValueReader();

	/**
	 * Returns a new object of class '<em>Codec Value Writer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Codec Value Writer</em>'.
	 * @generated
	 */
	CodecValueWriter createCodecValueWriter();

	/**
	 * Returns a new object of class '<em>Holder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Holder</em>'.
	 * @generated
	 */
	CodecInfoHolder createCodecInfoHolder();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CodecInfoPackage getCodecInfoPackage();

} //CodecInfoFactory
