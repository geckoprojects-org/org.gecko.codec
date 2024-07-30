/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Info Holder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getInfoType <em>Info Type</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getReaders <em>Readers</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getWriters <em>Writers</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfoHolder()
 * @model
 * @generated
 */
@ProviderType
public interface TypeInfoHolder extends EObject {
	/**
	 * Returns the value of the '<em><b>Info Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.gecko.codec.info.codecinfo.InfoType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info Type</em>' attribute.
	 * @see org.gecko.codec.info.codecinfo.InfoType
	 * @see #setInfoType(InfoType)
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfoHolder_InfoType()
	 * @model required="true"
	 * @generated
	 */
	InfoType getInfoType();

	/**
	 * Sets the value of the '{@link org.gecko.codec.info.codecinfo.TypeInfoHolder#getInfoType <em>Info Type</em>}' attribute.
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
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.ValueReader}<code>&lt;?, ?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Readers</em>' reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfoHolder_Readers()
	 * @model keys="name"
	 * @generated
	 */
	EList<ValueReader<?, ?>> getReaders();

	/**
	 * Returns the value of the '<em><b>Writers</b></em>' reference list.
	 * The list contents are of type {@link org.gecko.codec.info.codecinfo.ValueWriter}<code>&lt;?, ?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Writers</em>' reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getTypeInfoHolder_Writers()
	 * @model keys="name"
	 * @generated
	 */
	EList<ValueWriter<?, ?>> getWriters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ValueReader<?, ?> getReaderByName(String readerName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ValueWriter<?, ?> getWriterByName(String readerName);

} // TypeInfoHolder
