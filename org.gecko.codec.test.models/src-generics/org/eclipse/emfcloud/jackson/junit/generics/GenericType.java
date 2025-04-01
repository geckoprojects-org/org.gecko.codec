/*
 */
package org.eclipse.emfcloud.jackson.junit.generics;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.GenericType#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.GenericType#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getGenericType()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface GenericType<T> extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getGenericType_Value()
	 * @model unique="false"
	 * @generated
	 */
	T getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.generics.GenericType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(T value);

	/**
	 * Returns the value of the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getGenericType_Values()
	 * @model unique="false"
	 * @generated
	 */
	EList<T> getValues();

} // GenericType
