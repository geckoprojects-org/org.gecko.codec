/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.common.util.EList;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Holder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getInfoType <em>Info Type</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getReaders <em>Readers</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getWriters <em>Writers</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecInfoHolder()
 * @model
 * @generated
 */
@ProviderType
public interface CodecInfoHolder {
	/**
	 * Returns the value of the '<em><b>Info Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.gecko.codec.info.codecinfo.InfoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info Type</em>' attribute.
	 * @see org.gecko.codec.info.codecinfo.InfoType
	 * @see #setInfoType(InfoType)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecInfoHolder_InfoType()
	 * @model required="true"
	 * @generated
	 */
	InfoType getInfoType();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.CodecInfoHolder#getInfoType <em>Info Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Info Type</em>' attribute.
	 * @see org.gecko.codec.info.codecinfo.InfoType
	 * @see #getInfoType()
	 * @generated
	 */
	void setInfoType(InfoType value);

	/**
	 * Returns the value of the '<em><b>Readers</b></em>' reference list.
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.CodecValueReader}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Readers</em>' reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecInfoHolder_Readers()
	 * @model resolveProxies="false" keys="name" transient="true"
	 * @generated
	 */
	EList<CodecValueReader> getReaders();

	/**
	 * Returns the value of the '<em><b>Writers</b></em>' reference list.
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.CodecValueWriter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Writers</em>' reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getCodecInfoHolder_Writers()
	 * @model resolveProxies="false" keys="name" transient="true"
	 * @generated
	 */
	EList<CodecValueWriter> getWriters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return getReaders().stream().filter(r -&gt; r.getName() == readerName).findFirst().orElse(null);'"
	 * @generated
	 */
	CodecValueReader getReaderByName(String readerName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return getWriters().stream().filter(w -&gt; w.getName() == writerName).findFirst().orElse(null);'"
	 * @generated
	 */
	CodecValueWriter getWriterByName(String writerName);

} // CodecInfoHolder
