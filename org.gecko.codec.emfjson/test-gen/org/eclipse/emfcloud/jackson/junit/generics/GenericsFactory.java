/*
 */
package org.eclipse.emfcloud.jackson.junit.generics;

import org.eclipse.emf.ecore.EFactory;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage
 * @generated
 */
@ProviderType
public interface GenericsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenericsFactory eINSTANCE = org.eclipse.emfcloud.jackson.junit.generics.impl.GenericsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Generic Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Container</em>'.
	 * @generated
	 */
	GenericContainer createGenericContainer();

	/**
	 * Returns a new object of class '<em>Special Type One</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Special Type One</em>'.
	 * @generated
	 */
	SpecialTypeOne createSpecialTypeOne();

	/**
	 * Returns a new object of class '<em>Special Type Two</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Special Type Two</em>'.
	 * @generated
	 */
	SpecialTypeTwo createSpecialTypeTwo();

	/**
	 * Returns a new object of class '<em>Some</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Some</em>'.
	 * @generated
	 */
	Some createSome();

	/**
	 * Returns a new object of class '<em>Any</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any</em>'.
	 * @generated
	 */
	Any createAny();

	/**
	 * Returns a new object of class '<em>Base One</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Base One</em>'.
	 * @generated
	 */
	BaseOne createBaseOne();

	/**
	 * Returns a new object of class '<em>Other Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Other Container</em>'.
	 * @generated
	 */
	<ContentT extends Content> OtherContainer<ContentT> createOtherContainer();

	/**
	 * Returns a new object of class '<em>Content A</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Content A</em>'.
	 * @generated
	 */
	ContentA createContentA();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GenericsPackage getGenericsPackage();

} //GenericsFactory
