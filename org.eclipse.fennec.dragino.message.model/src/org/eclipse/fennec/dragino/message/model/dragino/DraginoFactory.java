/*
 */
package org.eclipse.fennec.dragino.message.model.dragino;

import org.eclipse.emf.ecore.EFactory;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.dragino.message.model.dragino.DraginoPackage
 * @generated
 */
@ProviderType
public interface DraginoFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DraginoFactory eINSTANCE = org.eclipse.fennec.dragino.message.model.dragino.impl.DraginoFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Decoded Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decoded Object</em>'.
	 * @generated
	 */
	DecodedObject createDecodedObject();

	/**
	 * Returns a new object of class '<em>LSE01 Uplink</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>LSE01 Uplink</em>'.
	 * @generated
	 */
	DraginoLSE01Uplink createDraginoLSE01Uplink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DraginoPackage getDraginoPackage();

} //DraginoFactory
