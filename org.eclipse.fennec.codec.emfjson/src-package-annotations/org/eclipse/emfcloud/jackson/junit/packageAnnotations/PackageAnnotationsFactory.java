/*
 */
package org.eclipse.emfcloud.jackson.junit.packageAnnotations;

import org.eclipse.emf.ecore.EFactory;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsPackage
 * @generated
 */
@ProviderType
public interface PackageAnnotationsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PackageAnnotationsFactory eINSTANCE = org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Container</em>'.
	 * @generated
	 */
	Container createContainer();

	/**
	 * Returns a new object of class '<em>Test Type Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Type Name</em>'.
	 * @generated
	 */
	TestTypeName createTestTypeName();

	/**
	 * Returns a new object of class '<em>Foo Type Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Foo Type Name</em>'.
	 * @generated
	 */
	FooTypeName createFooTypeName();

	/**
	 * Returns a new object of class '<em>Bar Type Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bar Type Name</em>'.
	 * @generated
	 */
	BarTypeName createBarTypeName();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PackageAnnotationsPackage getPackageAnnotationsPackage();

} //PackageAnnotationsFactory
