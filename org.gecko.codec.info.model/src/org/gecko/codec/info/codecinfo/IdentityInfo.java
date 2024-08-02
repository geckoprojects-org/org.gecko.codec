/*
 */
package org.gecko.codec.info.codecinfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Identity Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.IdentityInfo#isUseId <em>Use Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.IdentityInfo#isUseIdField <em>Use Id Field</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.IdentityInfo#isIdTop <em>Id Top</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.IdentityInfo#isSerializeIdField <em>Serialize Id Field</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.IdentityInfo#isIdFeatureAsPrimaryKey <em>Id Feature As Primary Key</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo()
 * @model
 * @generated
 */
@ProviderType
public interface IdentityInfo extends FeatureCodecInfo {
	/**
	 * Returns the value of the '<em><b>Use Id</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Option used to indicate the module to use the default ID serializer if none are provided. The ID serializer used by default is IdSerializer.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Use Id</em>' attribute.
	 * @see #setUseId(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo_UseId()
	 * @model default="true"
	 * @generated
	 */
	boolean isUseId();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.IdentityInfo#isUseId <em>Use Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Id</em>' attribute.
	 * @see #isUseId()
	 * @generated
	 */
	void setUseId(boolean value);

	/**
	 * Returns the value of the '<em><b>Use Id Field</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Option used to indicate the module to use the ID field of the EObject.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Use Id Field</em>' attribute.
	 * @see #setUseIdField(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo_UseIdField()
	 * @model default="true"
	 * @generated
	 */
	boolean isUseIdField();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.IdentityInfo#isUseIdField <em>Use Id Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Id Field</em>' attribute.
	 * @see #isUseIdField()
	 * @generated
	 */
	void setUseIdField(boolean value);

	/**
	 * Returns the value of the '<em><b>Id Top</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Option used to indicate the module to serialize the id field on top of the document.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id Top</em>' attribute.
	 * @see #setIdTop(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo_IdTop()
	 * @model default="true"
	 * @generated
	 */
	boolean isIdTop();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.IdentityInfo#isIdTop <em>Id Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Top</em>' attribute.
	 * @see #isIdTop()
	 * @generated
	 */
	void setIdTop(boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Id Field</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Option used to indicate the module to additionally serialize the id field of an EObject as it is.
	 *   This is usually not needed, because the id key always holds the ID at the first position.  
	 *  This id-field itself can be found at a later index. So finding it may cost a lot of effort.
	 *   It can be useful with useId TRUE and useIdField FALSE  and additionally store this 
	 *   id field, while using the URI fragment or Resource ID as primary key
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Id Field</em>' attribute.
	 * @see #setSerializeIdField(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo_SerializeIdField()
	 * @model default="false"
	 * @generated
	 */
	boolean isSerializeIdField();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.IdentityInfo#isSerializeIdField <em>Serialize Id Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Id Field</em>' attribute.
	 * @see #isSerializeIdField()
	 * @generated
	 */
	void setSerializeIdField(boolean value);

	/**
	 * Returns the value of the '<em><b>Id Feature As Primary Key</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If it is set to Boolean.TRUE and the ID was not specified in the URI, the value of the ID attribute will be used as the primary key if it exists.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id Feature As Primary Key</em>' attribute.
	 * @see #setIdFeatureAsPrimaryKey(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo_IdFeatureAsPrimaryKey()
	 * @model default="true"
	 * @generated
	 */
	boolean isIdFeatureAsPrimaryKey();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.IdentityInfo#isIdFeatureAsPrimaryKey <em>Id Feature As Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Feature As Primary Key</em>' attribute.
	 * @see #isIdFeatureAsPrimaryKey()
	 * @generated
	 */
	void setIdFeatureAsPrimaryKey(boolean value);

} // IdentityInfo
