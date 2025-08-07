/*
 */
package org.eclipse.fennec.lorawan.uplink2.model.lorawan2;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tx Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.TxInfo#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.TxInfo#getModulation <em>Modulation</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.TxInfo#getLora <em>Lora</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getTxInfo()
 * @model
 * @generated
 */
@ProviderType
public interface TxInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Frequency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frequency</em>' attribute.
	 * @see #setFrequency(long)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getTxInfo_Frequency()
	 * @model
	 * @generated
	 */
	long getFrequency();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.TxInfo#getFrequency <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency</em>' attribute.
	 * @see #getFrequency()
	 * @generated
	 */
	void setFrequency(long value);

	/**
	 * Returns the value of the '<em><b>Modulation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modulation</em>' attribute.
	 * @see #setModulation(String)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getTxInfo_Modulation()
	 * @model
	 * @generated
	 */
	String getModulation();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.TxInfo#getModulation <em>Modulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modulation</em>' attribute.
	 * @see #getModulation()
	 * @generated
	 */
	void setModulation(String value);

	/**
	 * Returns the value of the '<em><b>Lora</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lora</em>' containment reference.
	 * @see #setLora(LoraInfo)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getTxInfo_Lora()
	 * @model containment="true"
	 * @generated
	 */
	LoraInfo getLora();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.TxInfo#getLora <em>Lora</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lora</em>' containment reference.
	 * @see #getLora()
	 * @generated
	 */
	void setLora(LoraInfo value);

} // TxInfo
