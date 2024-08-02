/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Codec Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getEPackage <em>EPackage</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getSubPackageCodecInfo <em>Sub Package Codec Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getEClassCodecInfo <em>EClass Codec Info</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageCodecInfo()
 * @model annotation="http://www.eclipse.org/emf/2002/GenModel"
 * @generated
 */
@ProviderType
public interface PackageCodecInfo {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageCodecInfo_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>EPackage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EPackage</em>' reference.
	 * @see #setEPackage(EPackage)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageCodecInfo_EPackage()
	 * @model keys="nsURI"
	 * @generated
	 */
	EPackage getEPackage();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.PackageCodecInfo#getEPackage <em>EPackage</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EPackage</em>' reference.
	 * @see #getEPackage()
	 * @generated
	 */
	void setEPackage(EPackage value);

	/**
	 * Returns the value of the '<em><b>Sub Package Codec Info</b></em>' containment reference list.
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.PackageCodecInfo}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Package Codec Info</em>' containment reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageCodecInfo_SubPackageCodecInfo()
	 * @model containment="true"
	 * @generated
	 */
	EList<PackageCodecInfo> getSubPackageCodecInfo();

	/**
	 * Returns the value of the '<em><b>EClass Codec Info</b></em>' containment reference list.
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.EClassCodecInfo}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EClass Codec Info</em>' containment reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageCodecInfo_EClassCodecInfo()
	 * @model containment="true"
	 * @generated
	 */
	EList<EClassCodecInfo> getEClassCodecInfo();

} // PackageCodecInfo
