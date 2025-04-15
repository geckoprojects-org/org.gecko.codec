/*
 */
package org.gecko.emf.codec.test.model.codectest;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Address</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Address#getStreet <em>Street</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Address#getCity <em>City</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Address#getZip <em>Zip</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.Address#getNumber <em>Number</em>}</li>
 * </ul>
 *
 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getAddress()
 * @model annotation="http://geckoprojects.org/codec/1.0.0 name='CodecAddress'"
 * @generated
 */
@ProviderType
public interface Address extends EObject {
	/**
	 * Returns the value of the '<em><b>Street</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Street</em>' attribute.
	 * @see #setStreet(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getAddress_Street()
	 * @model annotation="https://geckoprojects.org/emf/codec/1.0.0 name='StreetName'"
	 * @generated
	 */
	String getStreet();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Address#getStreet <em>Street</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Street</em>' attribute.
	 * @see #getStreet()
	 * @generated
	 */
	void setStreet(String value);

	/**
	 * Returns the value of the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>City</em>' attribute.
	 * @see #setCity(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getAddress_City()
	 * @model
	 * @generated
	 */
	String getCity();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Address#getCity <em>City</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>City</em>' attribute.
	 * @see #getCity()
	 * @generated
	 */
	void setCity(String value);

	/**
	 * Returns the value of the '<em><b>Zip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zip</em>' attribute.
	 * @see #setZip(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getAddress_Zip()
	 * @model
	 * @generated
	 */
	String getZip();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Address#getZip <em>Zip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zip</em>' attribute.
	 * @see #getZip()
	 * @generated
	 */
	void setZip(String value);

	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(String)
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#getAddress_Number()
	 * @model
	 * @generated
	 */
	String getNumber();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.Address#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(String value);

} // Address
