/*
 */
package org.gecko.emf.codec.test.model.codectest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.BusinessPerson#getPosition <em>Position</em>}</li>
 * </ul>
 *
 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getBusinessPerson()
 * @model extendedMetaData="name='EMDBusinessPerson'"
 *        annotation="http://geckoprojects.org/codec/1.0.0 name='CodecBusinessPerson'"
 * @generated
 */
@ProviderType
public interface BusinessPerson extends Person {
	/**
	 * Returns the value of the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' attribute.
	 * @see #setPosition(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getBusinessPerson_Position()
	 * @model
	 * @generated
	 */
	String getPosition();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.BusinessPerson#getPosition <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' attribute.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(String value);

} // BusinessPerson
