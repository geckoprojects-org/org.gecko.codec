/*
 */
package org.gecko.codec.info.codecinfo;

import com.fasterxml.jackson.databind.DeserializationContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Codec Value Reader</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.CodecValueReader#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueReader()
 * @model interface="true" abstract="true"
 * @generated
 */
@ProviderType
public interface CodecValueReader<V, T> {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueReader_Name()
	 * @model id="true" required="true" changeable="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model valueRequired="true" contextDataType="org.gecko.codec.info.codecinfo.DeserializationContext" contextRequired="true"
	 * @generated
	 */
	T readValue(V value, DeserializationContext context);

} // CodecValueReader
