/*
 */
package org.eclipse.emfcloud.jackson.junit.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.AbstractType#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.AbstractType#getRefProperty <em>Ref Property</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractType()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface AbstractType extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractType_Name()
	 * @model unique="false" id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Ref Property</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.AbstractType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref Property</em>' reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractType_RefProperty()
	 * @model
	 * @generated
	 */
	EList<AbstractType> getRefProperty();

} // AbstractType
