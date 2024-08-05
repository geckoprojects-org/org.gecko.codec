/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClassifier;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EClass Codec Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getClassifier <em>Classifier</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getIdentityInfo <em>Identity Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getTypeInfo <em>Type Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getFeatureInfo <em>Feature Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getReferenceInfo <em>Reference Info</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo()
 * @model
 * @generated
 */
@ProviderType
public interface EClassCodecInfo {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Classifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classifier</em>' reference.
	 * @see #setClassifier(EClassifier)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_Classifier()
	 * @model
	 * @generated
	 */
	EClassifier getClassifier();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getClassifier <em>Classifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classifier</em>' reference.
	 * @see #getClassifier()
	 * @generated
	 */
	void setClassifier(EClassifier value);

	/**
	 * Returns the value of the '<em><b>Identity Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identity Info</em>' containment reference.
	 * @see #setIdentityInfo(IdentityInfo)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_IdentityInfo()
	 * @model containment="true"
	 * @generated
	 */
	IdentityInfo getIdentityInfo();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getIdentityInfo <em>Identity Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identity Info</em>' containment reference.
	 * @see #getIdentityInfo()
	 * @generated
	 */
	void setIdentityInfo(IdentityInfo value);

	/**
	 * Returns the value of the '<em><b>Type Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Info</em>' containment reference.
	 * @see #setTypeInfo(TypeInfo)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_TypeInfo()
	 * @model containment="true"
	 * @generated
	 */
	TypeInfo getTypeInfo();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getTypeInfo <em>Type Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Info</em>' containment reference.
	 * @see #getTypeInfo()
	 * @generated
	 */
	void setTypeInfo(TypeInfo value);

	/**
	 * Returns the value of the '<em><b>Feature Info</b></em>' containment reference list.
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.FeatureCodecInfo}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Info</em>' containment reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_FeatureInfo()
	 * @model containment="true"
	 * @generated
	 */
	EList<FeatureCodecInfo> getFeatureInfo();

	/**
	 * Returns the value of the '<em><b>Reference Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Info</em>' containment reference.
	 * @see #setReferenceInfo(ReferenceInfo)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_ReferenceInfo()
	 * @model containment="true"
	 * @generated
	 */
	ReferenceInfo getReferenceInfo();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getReferenceInfo <em>Reference Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference Info</em>' containment reference.
	 * @see #getReferenceInfo()
	 * @generated
	 */
	void setReferenceInfo(ReferenceInfo value);

} // EClassCodecInfo
