/*
 */
package org.eclipse.emfcloud.jackson.junit.model;

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
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Address#getAddId <em>Add Id</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Address#getCity <em>City</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Address#getStreet <em>Street</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Address#getNumber <em>Number</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAddress()
 * @model
 * @generated
 */
@ProviderType
public interface Address extends EObject {
	/**
	 * Returns the value of the '<em><b>Add Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Add Id</em>' attribute.
	 * @see #setAddId(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAddress_AddId()
	 * @model unique="false" id="true"
	 * @generated
	 */
	String getAddId();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Address#getAddId <em>Add Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Add Id</em>' attribute.
	 * @see #getAddId()
	 * @generated
	 */
	void setAddId(String value);

	/**
	 * Returns the value of the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>City</em>' attribute.
	 * @see #setCity(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAddress_City()
	 * @model unique="false"
	 * @generated
	 */
	String getCity();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Address#getCity <em>City</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>City</em>' attribute.
	 * @see #getCity()
	 * @generated
	 */
	void setCity(String value);

	/**
	 * Returns the value of the '<em><b>Street</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Street</em>' attribute.
	 * @see #setStreet(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAddress_Street()
	 * @model unique="false"
	 * @generated
	 */
	String getStreet();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Address#getStreet <em>Street</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Street</em>' attribute.
	 * @see #getStreet()
	 * @generated
	 */
	void setStreet(String value);

	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(Integer)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAddress_Number()
	 * @model unique="false"
	 * @generated
	 */
	Integer getNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Address#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(Integer value);

} // Address
