/*
 */
package org.eclipse.fennec.jsonld.model.jsonld;

import org.eclipse.emf.ecore.EFactory;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage
 * @generated
 */
@ProviderType
public interface JsonLDFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	JsonLDFactory eINSTANCE = org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Context String Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Context String Value</em>'.
	 * @generated
	 */
	ContextStringValue createContextStringValue();

	/**
	 * Returns a new object of class '<em>Context Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Context Object</em>'.
	 * @generated
	 */
	ContextObject createContextObject();

	/**
	 * Returns a new object of class '<em>Json LD</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Json LD</em>'.
	 * @generated
	 */
	JsonLD createJsonLD();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	JsonLDPackage getJsonLDPackage();

} //JsonLDFactory
