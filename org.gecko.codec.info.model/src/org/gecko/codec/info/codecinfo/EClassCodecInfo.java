/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

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
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#isSerializeDefaultValue <em>Serialize Default Value</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#isSerializeArrayBatched <em>Serialize Array Batched</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#isUseNamesFromExtendedMetaData <em>Use Names From Extended Meta Data</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo()
 * @model
 * @generated
 */
@ProviderType
public interface EClassCodecInfo extends EObject {
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
	 * Returns the value of the '<em><b>Feature Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Info</em>' containment reference.
	 * @see #setFeatureInfo(FeatureCodecInfo)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_FeatureInfo()
	 * @model containment="true"
	 * @generated
	 */
	FeatureCodecInfo getFeatureInfo();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#getFeatureInfo <em>Feature Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Info</em>' containment reference.
	 * @see #getFeatureInfo()
	 * @generated
	 */
	void setFeatureInfo(FeatureCodecInfo value);

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

	/**
	 * Returns the value of the '<em><b>Serialize Default Value</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Option used to indicate the module to serialize default attributes values.  Default values are not serialized by default.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Default Value</em>' attribute.
	 * @see #setSerializeDefaultValue(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_SerializeDefaultValue()
	 * @model default="false"
	 * @generated
	 */
	boolean isSerializeDefaultValue();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#isSerializeDefaultValue <em>Serialize Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Default Value</em>' attribute.
	 * @see #isSerializeDefaultValue()
	 * @generated
	 */
	void setSerializeDefaultValue(boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Array Batched</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Setting this option to Boolean.TRUE  will send lists and arrays using the writeArray callbacks.\n Per default the serialization happens with startArray, then calling writeValue for each element. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Array Batched</em>' attribute.
	 * @see #setSerializeArrayBatched(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_SerializeArrayBatched()
	 * @model default="false"
	 * @generated
	 */
	boolean isSerializeArrayBatched();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#isSerializeArrayBatched <em>Serialize Array Batched</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Array Batched</em>' attribute.
	 * @see #isSerializeArrayBatched()
	 * @generated
	 */
	void setSerializeArrayBatched(boolean value);

	/**
	 * Returns the value of the '<em><b>Use Names From Extended Meta Data</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Option used to indicate whether feature names specified in {@link org.eclipse.emf.ecore.util.ExtendedMetaData} annotations should be respected.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Use Names From Extended Meta Data</em>' attribute.
	 * @see #setUseNamesFromExtendedMetaData(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getEClassCodecInfo_UseNamesFromExtendedMetaData()
	 * @model default="true"
	 * @generated
	 */
	boolean isUseNamesFromExtendedMetaData();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo#isUseNamesFromExtendedMetaData <em>Use Names From Extended Meta Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Names From Extended Meta Data</em>' attribute.
	 * @see #isUseNamesFromExtendedMetaData()
	 * @generated
	 */
	void setUseNamesFromExtendedMetaData(boolean value);

} // EClassCodecInfo
