/*
 */
package org.eclipse.emfcloud.jackson.junit.packageAnnotations;

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
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.Container#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsPackage#getContainer()
 * @model
 * @generated
 */
@ProviderType
public interface Container extends EObject {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.packageAnnotations.TestTypeName}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsPackage#getContainer_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestTypeName> getValues();

} // Container
