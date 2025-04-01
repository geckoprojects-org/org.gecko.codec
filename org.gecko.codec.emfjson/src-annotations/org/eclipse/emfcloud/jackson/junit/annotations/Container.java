/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.Container#getTypedByNames <em>Typed By Names</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.Container#getTypedByClasses <em>Typed By Classes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getContainer()
 * @model
 * @generated
 */
@ProviderType
public interface Container extends EObject {
	/**
	 * Returns the value of the '<em><b>Typed By Names</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typed By Names</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getContainer_TypedByNames()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestTypeName> getTypedByNames();

	/**
	 * Returns the value of the '<em><b>Typed By Classes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typed By Classes</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getContainer_TypedByClasses()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestTypeClass> getTypedByClasses();

} // Container
