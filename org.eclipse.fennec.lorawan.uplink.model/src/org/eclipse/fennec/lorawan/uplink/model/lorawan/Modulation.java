/*
 */
package org.eclipse.fennec.lorawan.uplink.model.lorawan;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Modulation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink.model.lorawan.Modulation#getLora <em>Lora</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.lorawan.uplink.model.lorawan.LorawanPackage#getModulation()
 * @model
 * @generated
 */
@ProviderType
public interface Modulation extends EObject {
	/**
	 * Returns the value of the '<em><b>Lora</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lora</em>' containment reference.
	 * @see #setLora(LoraInfo)
	 * @see org.eclipse.fennec.lorawan.uplink.model.lorawan.LorawanPackage#getModulation_Lora()
	 * @model containment="true"
	 * @generated
	 */
	LoraInfo getLora();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink.model.lorawan.Modulation#getLora <em>Lora</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lora</em>' containment reference.
	 * @see #getLora()
	 * @generated
	 */
	void setLora(LoraInfo value);

} // Modulation
