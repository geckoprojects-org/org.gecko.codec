/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.ETypedElement;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Codec Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getFeatures <em>Features</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getValueReaderName <em>Value Reader Name</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getValueWriterName <em>Value Writer Name</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getType <em>Type</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getKey <em>Key</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#isIgnore <em>Ignore</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getFeatureCodecInfo()
 * @model
 * @generated
 */
@ProviderType
public interface FeatureCodecInfo {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getFeatureCodecInfo_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Features</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.ETypedElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features</em>' reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getFeatureCodecInfo_Features()
	 * @model
	 * @generated
	 */
	EList<ETypedElement> getFeatures();

	/**
	 * Returns the value of the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Reader Name</em>' attribute.
	 * @see #setValueReaderName(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getFeatureCodecInfo_ValueReaderName()
	 * @model
	 * @generated
	 */
	String getValueReaderName();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getValueReaderName <em>Value Reader Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Reader Name</em>' attribute.
	 * @see #getValueReaderName()
	 * @generated
	 */
	void setValueReaderName(String value);

	/**
	 * Returns the value of the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Writer Name</em>' attribute.
	 * @see #setValueWriterName(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getFeatureCodecInfo_ValueWriterName()
	 * @model
	 * @generated
	 */
	String getValueWriterName();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getValueWriterName <em>Value Writer Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Writer Name</em>' attribute.
	 * @see #getValueWriterName()
	 * @generated
	 */
	void setValueWriterName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.gecko.codec.info.codecinfo.InfoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.gecko.codec.info.codecinfo.InfoType
	 * @see #setType(InfoType)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getFeatureCodecInfo_Type()
	 * @model
	 * @generated
	 */
	InfoType getType();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.gecko.codec.info.codecinfo.InfoType
	 * @see #getType()
	 * @generated
	 */
	void setType(InfoType value);

	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This is the field name to be used for serialization. Can be set in the model through the http:///org/eclipse/emf/ecore/util/ExtendedMetaData name annotation. If EXTENDED_META_DATA module option is enabled, this will be used, otherwise the field name will be used.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getFeatureCodecInfo_Key()
	 * @model
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Ignore</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If set to true, the EStructuralFeature marked with that will not be serialized.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ignore</em>' attribute.
	 * @see #setIgnore(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getFeatureCodecInfo_Ignore()
	 * @model
	 * @generated
	 */
	boolean isIgnore();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo#isIgnore <em>Ignore</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ignore</em>' attribute.
	 * @see #isIgnore()
	 * @generated
	 */
	void setIgnore(boolean value);

} // FeatureCodecInfo
