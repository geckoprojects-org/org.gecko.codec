/*
 */
package org.gecko.codec.info.codecinfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Super Type Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.SuperTypeInfo#getSuperTypeStrategy <em>Super Type Strategy</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.SuperTypeInfo#getSuperTypeSeparator <em>Super Type Separator</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.SuperTypeInfo#isIgnoreSuperType <em>Ignore Super Type</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getSuperTypeInfo()
 * @model
 * @generated
 */
@ProviderType
public interface SuperTypeInfo extends FeatureCodecInfo {
	/**
	 * Returns the value of the '<em><b>Super Type Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This supports the possibility of setting a strategy for super type serialization, e.g. if the URI of the class should be used as type, or the class name, or another value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Super Type Strategy</em>' attribute.
	 * @see #setSuperTypeStrategy(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getSuperTypeInfo_SuperTypeStrategy()
	 * @model
	 * @generated
	 */
	String getSuperTypeStrategy();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.SuperTypeInfo#getSuperTypeStrategy <em>Super Type Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type Strategy</em>' attribute.
	 * @see #getSuperTypeStrategy()
	 * @generated
	 */
	void setSuperTypeStrategy(String value);

	/**
	 * Returns the value of the '<em><b>Super Type Separator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the superTypeStrategy is set to COMBINED, the supertype info will be serailized as a concatnated String, and this attribute will set the character to use as separator.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Super Type Separator</em>' attribute.
	 * @see #setSuperTypeSeparator(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getSuperTypeInfo_SuperTypeSeparator()
	 * @model
	 * @generated
	 */
	String getSuperTypeSeparator();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.SuperTypeInfo#getSuperTypeSeparator <em>Super Type Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type Separator</em>' attribute.
	 * @see #getSuperTypeSeparator()
	 * @generated
	 */
	void setSuperTypeSeparator(String value);

	/**
	 * Returns the value of the '<em><b>Ignore Super Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If set to false, the supertype information of the EClassifier with such a SuperTypeInfo object will not be serialized, unless this property is then overwritten at a later point.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ignore Super Type</em>' attribute.
	 * @see #setIgnoreSuperType(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getSuperTypeInfo_IgnoreSuperType()
	 * @model
	 * @generated
	 */
	boolean isIgnoreSuperType();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.SuperTypeInfo#isIgnoreSuperType <em>Ignore Super Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ignore Super Type</em>' attribute.
	 * @see #isIgnoreSuperType()
	 * @generated
	 */
	void setIgnoreSuperType(boolean value);

} // SuperTypeInfo
