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
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfo#isWriteSuperTypes <em>Write Super Types</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo()
 * @model
 * @generated
 */
@ProviderType
public interface TypeInfo extends FeatureInfo {
	/**
	 * Returns the value of the '<em><b>Write Super Types</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Write Super Types</em>' attribute.
	 * @see #setWriteSuperTypes(boolean)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfo_WriteSuperTypes()
	 * @model default="false"
	 * @generated
	 */
	boolean isWriteSuperTypes();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.TypeInfo#isWriteSuperTypes <em>Write Super Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Write Super Types</em>' attribute.
	 * @see #isWriteSuperTypes()
	 * @generated
	 */
	void setWriteSuperTypes(boolean value);

} // TypeInfo
