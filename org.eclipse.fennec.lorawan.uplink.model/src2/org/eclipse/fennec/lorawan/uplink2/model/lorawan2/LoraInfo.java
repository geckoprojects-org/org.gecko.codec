/*
 */
package org.eclipse.fennec.lorawan.uplink2.model.lorawan2;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Lora Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.LoraInfo#getBandwidth <em>Bandwidth</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.LoraInfo#getSpreadingFactor <em>Spreading Factor</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.LoraInfo#getCodeRate <em>Code Rate</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getLoraInfo()
 * @model
 * @generated
 */
@ProviderType
public interface LoraInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Bandwidth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bandwidth</em>' attribute.
	 * @see #setBandwidth(int)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getLoraInfo_Bandwidth()
	 * @model
	 * @generated
	 */
	int getBandwidth();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.LoraInfo#getBandwidth <em>Bandwidth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bandwidth</em>' attribute.
	 * @see #getBandwidth()
	 * @generated
	 */
	void setBandwidth(int value);

	/**
	 * Returns the value of the '<em><b>Spreading Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Spreading Factor</em>' attribute.
	 * @see #setSpreadingFactor(int)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getLoraInfo_SpreadingFactor()
	 * @model
	 * @generated
	 */
	int getSpreadingFactor();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.LoraInfo#getSpreadingFactor <em>Spreading Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Spreading Factor</em>' attribute.
	 * @see #getSpreadingFactor()
	 * @generated
	 */
	void setSpreadingFactor(int value);

	/**
	 * Returns the value of the '<em><b>Code Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code Rate</em>' attribute.
	 * @see #setCodeRate(String)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getLoraInfo_CodeRate()
	 * @model
	 * @generated
	 */
	String getCodeRate();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.LoraInfo#getCodeRate <em>Code Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code Rate</em>' attribute.
	 * @see #getCodeRate()
	 * @generated
	 */
	void setCodeRate(String value);

} // LoraInfo
