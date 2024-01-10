/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations;

import org.eclipse.emf.ecore.EFactory;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage
 * @generated
 */
@ProviderType
public interface AnnotationsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AnnotationsFactory eINSTANCE = org.eclipse.emfcloud.jackson.junit.annotations.impl.AnnotationsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Test A</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test A</em>'.
	 * @generated
	 */
	TestA createTestA();

	/**
	 * Returns a new object of class '<em>Test B</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test B</em>'.
	 * @generated
	 */
	TestB createTestB();

	/**
	 * Returns a new object of class '<em>Test C</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test C</em>'.
	 * @generated
	 */
	TestC createTestC();

	/**
	 * Returns a new object of class '<em>Test D</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test D</em>'.
	 * @generated
	 */
	TestD createTestD();

	/**
	 * Returns a new object of class '<em>Test E</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test E</em>'.
	 * @generated
	 */
	TestE createTestE();

	/**
	 * Returns a new object of class '<em>Test F</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test F</em>'.
	 * @generated
	 */
	TestF createTestF();

	/**
	 * Returns a new object of class '<em>Test G</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test G</em>'.
	 * @generated
	 */
	TestG createTestG();

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
	 * Returns a new object of class '<em>Test Type Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Type Class</em>'.
	 * @generated
	 */
	TestTypeClass createTestTypeClass();

	/**
	 * Returns a new object of class '<em>Foo Type Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Foo Type Class</em>'.
	 * @generated
	 */
	FooTypeClass createFooTypeClass();

	/**
	 * Returns a new object of class '<em>Bar Type Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bar Type Class</em>'.
	 * @generated
	 */
	BarTypeClass createBarTypeClass();

	/**
	 * Returns a new object of class '<em>Raw Json</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Raw Json</em>'.
	 * @generated
	 */
	RawJson createRawJson();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AnnotationsPackage getAnnotationsPackage();

} //AnnotationsFactory
