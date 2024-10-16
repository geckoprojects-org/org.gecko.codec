/*
 */
package org.gecko.code.demo.model.person;

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
 *   <li>{@link org.gecko.code.demo.model.person.BusinessPerson#getCompanyIdCardNumber <em>Company Id Card Number</em>}</li>
 * </ul>
 *
 * @see org.gecko.code.demo.model.person.PersonPackage#getBusinessPerson()
 * @model annotation="codec.id strategy='ID_FIELD'"
 * @generated
 */
@ProviderType
public interface BusinessPerson extends Person {
	/**
	 * Returns the value of the '<em><b>Company Id Card Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Company Id Card Number</em>' attribute.
	 * @see #setCompanyIdCardNumber(String)
	 * @see org.gecko.code.demo.model.person.PersonPackage#getBusinessPerson_CompanyIdCardNumber()
	 * @model extendedMetaData="name='compId'"
	 * @generated
	 */
	String getCompanyIdCardNumber();

	/**
	 * Sets the value of the '{@link org.gecko.code.demo.model.person.BusinessPerson#getCompanyIdCardNumber <em>Company Id Card Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Company Id Card Number</em>' attribute.
	 * @see #getCompanyIdCardNumber()
	 * @generated
	 */
	void setCompanyIdCardNumber(String value);

} // BusinessPerson
