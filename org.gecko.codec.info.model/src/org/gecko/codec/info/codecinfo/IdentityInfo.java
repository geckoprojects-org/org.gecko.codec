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
 *   <li>{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdStrategy <em>Id Strategy</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdSeparator <em>Id Separator</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdOrder <em>Id Order</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo()
 * @model
 * @generated
 */
@ProviderType
public interface IdentityInfo extends FeatureCodecInfo {
	/**
	 * Returns the value of the '<em><b>Id Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This supports the possibility of setting a strategy for id field determination, based on model annotations. For instance, an id field could be the combination of two model fields, and this should be marked in the model.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id Strategy</em>' attribute.
	 * @see #setIdStrategy(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo_IdStrategy()
	 * @model
	 * @generated
	 */
	String getIdStrategy();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdStrategy <em>Id Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Strategy</em>' attribute.
	 * @see #getIdStrategy()
	 * @generated
	 */
	void setIdStrategy(String value);

	/**
	 * Returns the value of the '<em><b>Id Separator</b></em>' attribute.
	 * The default value is <code>"."</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This supports the possibility of setting an id as a combination of multiple fields. The idSeparator property indicates the separator to be used when building the id field.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id Separator</em>' attribute.
	 * @see #setIdSeparator(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo_IdSeparator()
	 * @model default="."
	 * @generated
	 */
	String getIdSeparator();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdSeparator <em>Id Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Separator</em>' attribute.
	 * @see #getIdSeparator()
	 * @generated
	 */
	void setIdSeparator(String value);

	/**
	 * Returns the value of the '<em><b>Id Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This supports the possibility of setting an id as a combination of multiple fields. The idOrder defines the position of the fields to be used when building the id.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id Order</em>' attribute.
	 * @see #setIdOrder(int)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getIdentityInfo_IdOrder()
	 * @model
	 * @generated
	 */
	int getIdOrder();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.IdentityInfo#getIdOrder <em>Id Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Order</em>' attribute.
	 * @see #getIdOrder()
	 * @generated
	 */
	void setIdOrder(int value);

} // IdentityInfo
