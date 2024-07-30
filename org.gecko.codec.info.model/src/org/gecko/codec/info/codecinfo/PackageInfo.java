/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.PackageInfo#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.PackageInfo#getEPackage <em>EPackage</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.PackageInfo#getSubPackageInfo <em>Sub Package Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.PackageInfo#getEClassInfo <em>EClass Info</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageInfo()
 * @model
 * @generated
 */
@ProviderType
public interface PackageInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageInfo_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.PackageInfo#getId <em>Id</em>}' attribute.
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
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageInfo_EPackage()
	 * @model keys="nsURI"
	 * @generated
	 */
	EPackage getEPackage();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.PackageInfo#getEPackage <em>EPackage</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EPackage</em>' reference.
	 * @see #getEPackage()
	 * @generated
	 */
	void setEPackage(EPackage value);

	/**
	 * Returns the value of the '<em><b>Sub Package Info</b></em>' containment reference list.
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.PackageInfo}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Package Info</em>' containment reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageInfo_SubPackageInfo()
	 * @model containment="true"
	 * @generated
	 */
	EList<PackageInfo> getSubPackageInfo();

	/**
	 * Returns the value of the '<em><b>EClass Info</b></em>' containment reference list.
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.EClassInfo}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EClass Info</em>' containment reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getPackageInfo_EClassInfo()
	 * @model containment="true"
	 * @generated
	 */
	EList<EClassInfo> getEClassInfo();

} // PackageInfo
