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
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfo#isSerializeType <em>Serialize Type</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfo#isSerializeSuperTypes <em>Serialize Super Types</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfo#isSerializeSuperTypeAsArray <em>Serialize Super Type As Array</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo()
 * @model
 * @generated
 */
@ProviderType
public interface TypeInfo extends FeatureCodecInfo {
	/**
	 * Returns the value of the '<em><b>Serialize Type</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Option used to indicate the module to use the default type serializer if none are provided. The type serializer used by default is ETypeSerializer.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Type</em>' attribute.
	 * @see #setSerializeType(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo_SerializeType()
	 * @model default="true"
	 * @generated
	 */
	boolean isSerializeType();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.TypeInfo#isSerializeType <em>Serialize Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Type</em>' attribute.
	 * @see #isSerializeType()
	 * @generated
	 */
	void setSerializeType(boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Super Types</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * To avoid writing unnecessary URIs in the result format, we write eClassUris only for the root  class and for EReferences,\n where the actual value does not equal but inherit from the stated reference type.\n  By setting this option to Boolean.TRUE, all eClass URIs will be written regardless. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Super Types</em>' attribute.
	 * @see #setSerializeSuperTypes(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo_SerializeSuperTypes()
	 * @model default="false"
	 * @generated
	 */
	boolean isSerializeSuperTypes();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.TypeInfo#isSerializeSuperTypes <em>Serialize Super Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Super Types</em>' attribute.
	 * @see #isSerializeSuperTypes()
	 * @generated
	 */
	void setSerializeSuperTypes(boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Super Type As Array</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * By setting this to Boolean.TRUE the supertypes are written as an array of URIs.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Super Type As Array</em>' attribute.
	 * @see #setSerializeSuperTypeAsArray(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo_SerializeSuperTypeAsArray()
	 * @model default="true"
	 * @generated
	 */
	boolean isSerializeSuperTypeAsArray();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.TypeInfo#isSerializeSuperTypeAsArray <em>Serialize Super Type As Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Super Type As Array</em>' attribute.
	 * @see #isSerializeSuperTypeAsArray()
	 * @generated
	 */
	void setSerializeSuperTypeAsArray(boolean value);

} // TypeInfo
