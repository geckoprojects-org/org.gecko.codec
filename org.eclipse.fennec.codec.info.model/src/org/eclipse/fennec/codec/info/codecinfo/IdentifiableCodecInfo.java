/*
 */
package org.eclipse.fennec.codec.info.codecinfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Identifiable Codec Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.info.codecinfo.IdentifiableCodecInfo#getIdentityInfo <em>Identity Info</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.codec.info.codecinfo.CodecInfoPackage#getIdentifiableCodecInfo()
 * @model
 * @generated
 */
@ProviderType
public interface IdentifiableCodecInfo {
	/**
	 * Returns the value of the '<em><b>Identity Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identity Info</em>' containment reference.
	 * @see #setIdentityInfo(IdentityInfo)
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecInfoPackage#getIdentifiableCodecInfo_IdentityInfo()
	 * @model containment="true"
	 * @generated
	 */
	IdentityInfo getIdentityInfo();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.info.codecinfo.IdentifiableCodecInfo#getIdentityInfo <em>Identity Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identity Info</em>' containment reference.
	 * @see #getIdentityInfo()
	 * @generated
	 */
	void setIdentityInfo(IdentityInfo value);

} // IdentifiableCodecInfo
