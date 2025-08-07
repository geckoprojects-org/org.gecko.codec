/*
 */
package org.eclipse.fennec.lorawan.uplink2.model.lorawan2;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tags</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Tags#getDev_type <em>Dev type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getTags()
 * @model
 * @generated
 */
@ProviderType
public interface Tags extends EObject {
	/**
	 * Returns the value of the '<em><b>Dev type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dev type</em>' attribute.
	 * @see #setDev_type(String)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getTags_Dev_type()
	 * @model
	 * @generated
	 */
	String getDev_type();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Tags#getDev_type <em>Dev type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dev type</em>' attribute.
	 * @see #getDev_type()
	 * @generated
	 */
	void setDev_type(String value);

} // Tags
