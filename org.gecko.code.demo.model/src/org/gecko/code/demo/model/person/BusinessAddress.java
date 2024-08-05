/*
 */
package org.gecko.code.demo.model.person;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Address</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.code.demo.model.person.BusinessAddress#getCompanyName <em>Company Name</em>}</li>
 * </ul>
 *
 * @see org.gecko.code.demo.model.person.PersonPackage#getBusinessAddress()
 * @model
 * @generated
 */
@ProviderType
public interface BusinessAddress extends Address {
	/**
	 * Returns the value of the '<em><b>Company Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Company Name</em>' attribute.
	 * @see #setCompanyName(String)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getBusinessAddress_CompanyName()
	 * @model
	 * @generated
	 */
	String getCompanyName();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.BusinessAddress#getCompanyName <em>Company Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Company Name</em>' attribute.
	 * @see #getCompanyName()
	 * @generated
	 */
	void setCompanyName(String value);

} // BusinessAddress
