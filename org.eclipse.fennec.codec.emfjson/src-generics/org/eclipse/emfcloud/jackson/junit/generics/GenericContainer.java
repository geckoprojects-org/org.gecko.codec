/*
 */
package org.eclipse.emfcloud.jackson.junit.generics;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.GenericContainer#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getGenericContainer()
 * @model
 * @generated
 */
@ProviderType
public interface GenericContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.generics.GenericType}<code>&lt;?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#getGenericContainer_Values()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<GenericType<?>> getValues();

} // GenericContainer
