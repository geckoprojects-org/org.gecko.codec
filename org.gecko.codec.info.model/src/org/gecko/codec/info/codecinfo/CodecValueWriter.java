/*
 */
package org.gecko.codec.info.codecinfo;

import com.fasterxml.jackson.databind.SerializerProvider;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Codec Value Writer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.CodecValueWriter#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueWriter()
 * @model interface="true" abstract="true"
 * @generated
 */
@ProviderType
public interface CodecValueWriter<T, V> {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueWriter_Name()
	 * @model id="true" required="true" changeable="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model valueRequired="true" providerDataType="org.gecko.codec.info.codecinfo.SerializerProvider" providerRequired="true"
	 * @generated
	 */
	V writeValue(T value, SerializerProvider provider);

} // CodecValueWriter
