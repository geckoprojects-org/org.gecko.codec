/*
 */
package org.eclipse.fennec.codec.info.codecinfo;

import org.osgi.annotation.versioning.ProviderType;

import tools.jackson.core.JsonGenerator;

import tools.jackson.databind.SerializationContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Codec Serializer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.info.codecinfo.CodecSerializer#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.codec.info.codecinfo.CodecInfoPackage#getCodecSerializer()
 * @model interface="true" abstract="true"
 * @generated
 */
@ProviderType
public interface CodecSerializer<T> {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecInfoPackage#getCodecSerializer_Name()
	 * @model id="true" required="true" changeable="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model valueRequired="true" generatorDataType="org.eclipse.fennec.codec.info.codecinfo.JsonGenerator" ctxtDataType="org.eclipse.fennec.codec.info.codecinfo.SerializationContext" ctxtRequired="true"
	 * @generated
	 */
	void serialize(T value, JsonGenerator generator, SerializationContext ctxt);

} // CodecSerializer
