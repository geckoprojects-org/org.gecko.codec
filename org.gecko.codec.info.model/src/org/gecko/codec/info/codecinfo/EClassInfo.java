/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EClass Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassInfo#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassInfo#getClassifier <em>Classifier</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassInfo#getIdentityInfo <em>Identity Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassInfo#getTypeInfo <em>Type Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassInfo#getFeatureInfo <em>Feature Info</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassInfo()
 * @model
 * @generated
 */
@ProviderType
public interface EClassInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassInfo_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassInfo#getId <em>Id</em>}' attribute.
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
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassInfo_Classifier()
	 * @model
	 * @generated
	 */
	EClassifier getClassifier();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassInfo#getClassifier <em>Classifier</em>}' reference.
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
	 * @see #setIdentityInfo(FeatureInfo)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassInfo_IdentityInfo()
	 * @model containment="true"
	 * @generated
	 */
	FeatureInfo getIdentityInfo();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassInfo#getIdentityInfo <em>Identity Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identity Info</em>' containment reference.
	 * @see #getIdentityInfo()
	 * @generated
	 */
	void setIdentityInfo(FeatureInfo value);

	/**
	 * Returns the value of the '<em><b>Type Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Info</em>' containment reference.
	 * @see #setTypeInfo(FeatureInfo)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassInfo_TypeInfo()
	 * @model containment="true"
	 * @generated
	 */
	FeatureInfo getTypeInfo();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassInfo#getTypeInfo <em>Type Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Info</em>' containment reference.
	 * @see #getTypeInfo()
	 * @generated
	 */
	void setTypeInfo(FeatureInfo value);

	/**
	 * Returns the value of the '<em><b>Feature Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Info</em>' containment reference.
	 * @see #setFeatureInfo(FeatureInfo)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassInfo_FeatureInfo()
	 * @model containment="true"
	 * @generated
	 */
	FeatureInfo getFeatureInfo();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassInfo#getFeatureInfo <em>Feature Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Info</em>' containment reference.
	 * @see #getFeatureInfo()
	 * @generated
	 */
	void setFeatureInfo(FeatureInfo value);

} // EClassInfo
