/*
 */
package org.gecko.codec.info.codecinfo;

import org.gecko.codec.io.ValueReader;

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
 *   <li>{@link org.gecko.codec.info.codecinfo.CodecValueReader#getReader <em>Reader</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueReader()
 * @model
 * @generated
 */
@ProviderType
public interface CodecValueReader {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueReader_Name()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.CodecValueReader#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Reader</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reader</em>' attribute.
	 * @see #setReader(ValueReader)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueReader_Reader()
	 * @model dataType="org.gecko.codec.info.codecinfo.ValueReader"
	 * @generated
	 */
	ValueReader getReader();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.CodecValueReader#getReader <em>Reader</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reader</em>' attribute.
	 * @see #getReader()
	 * @generated
	 */
	void setReader(ValueReader value);

} // CodecValueReader
