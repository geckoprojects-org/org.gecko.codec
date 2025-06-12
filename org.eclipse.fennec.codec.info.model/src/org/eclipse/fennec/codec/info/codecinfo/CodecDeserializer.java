/*
 */
package org.eclipse.fennec.codec.info.codecinfo;

import org.osgi.annotation.versioning.ProviderType;

import tools.jackson.core.JsonParser;

import tools.jackson.databind.DeserializationContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Codec Deserializer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.info.codecinfo.CodecDeserializer#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.codec.info.codecinfo.CodecInfoPackage#getCodecDeserializer()
 * @model interface="true" abstract="true"
 * @generated
 */
@ProviderType
public interface CodecDeserializer<V> {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecInfoPackage#getCodecDeserializer_Name()
	 * @model id="true" required="true" changeable="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model parserDataType="org.eclipse.fennec.codec.info.codecinfo.JsonParser" ctxtDataType="org.eclipse.fennec.codec.info.codecinfo.DeserializationContext" ctxtRequired="true"
	 * @generated
	 */
	V deserialize(JsonParser parser, DeserializationContext ctxt);

} // CodecDeserializer
