/*
 */
package org.gecko.codec.info.codecinfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfo#getTypeStrategy <em>Type Strategy</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfo#getTypeValue <em>Type Value</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo()
 * @model
 * @generated
 */
@ProviderType
public interface TypeInfo extends FeatureCodecInfo {
	/**
	 * Returns the value of the '<em><b>Type Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This supports the possibility of setting a strategy for type serialization, e.g. if the URI of the class should be used as type, or the class name, or another value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Strategy</em>' attribute.
	 * @see #setTypeStrategy(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo_TypeStrategy()
	 * @model
	 * @generated
	 */
	String getTypeStrategy();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.TypeInfo#getTypeStrategy <em>Type Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Strategy</em>' attribute.
	 * @see #getTypeStrategy()
	 * @generated
	 */
	void setTypeStrategy(String value);

	/**
	 * Returns the value of the '<em><b>Type Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This supports the possibility of serializing the type with the provided String.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Value</em>' attribute.
	 * @see #setTypeValue(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo_TypeValue()
	 * @model
	 * @generated
	 */
	String getTypeValue();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.TypeInfo#getTypeValue <em>Type Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Value</em>' attribute.
	 * @see #getTypeValue()
	 * @generated
	 */
	void setTypeValue(String value);

} // TypeInfo
