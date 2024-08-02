/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.ecore.EObject;

import org.gecko.codec.io.ValueWriter;

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
 *   <li>{@link org.gecko.codec.info.codecinfo.CodecValueWriter#getWriter <em>Writer</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueWriter()
 * @model
 * @generated
 */
@ProviderType
public interface CodecValueWriter extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueWriter_Name()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.CodecValueWriter#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Writer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Writer</em>' attribute.
	 * @see #setWriter(ValueWriter)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecValueWriter_Writer()
	 * @model dataType="org.gecko.codec.info.codecinfo.ValueWriter"
	 * @generated
	 */
	ValueWriter getWriter();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.CodecValueWriter#getWriter <em>Writer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Writer</em>' attribute.
	 * @see #getWriter()
	 * @generated
	 */
	void setWriter(ValueWriter value);

} // CodecValueWriter
