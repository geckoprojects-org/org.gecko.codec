/*
 */
package org.eclipse.fennec.lorawan.uplink2.model.lorawan2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.lorawan.uplink2.model.lorawan2.LoraInfo;
import org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package;
import org.eclipse.fennec.lorawan.uplink2.model.lorawan2.TxInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tx Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.impl.TxInfoImpl#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.impl.TxInfoImpl#getModulation <em>Modulation</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.impl.TxInfoImpl#getLora <em>Lora</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TxInfoImpl extends MinimalEObjectImpl.Container implements TxInfo {
	/**
	 * The default value of the '{@link #getFrequency() <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequency()
	 * @generated
	 * @ordered
	 */
	protected static final long FREQUENCY_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getFrequency() <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequency()
	 * @generated
	 * @ordered
	 */
	protected long frequency = FREQUENCY_EDEFAULT;

	/**
	 * The default value of the '{@link #getModulation() <em>Modulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModulation()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModulation() <em>Modulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModulation()
	 * @generated
	 * @ordered
	 */
	protected String modulation = MODULATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLora() <em>Lora</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLora()
	 * @generated
	 * @ordered
	 */
	protected LoraInfo lora;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TxInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Lorawan2Package.Literals.TX_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getFrequency() {
		return frequency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFrequency(long newFrequency) {
		long oldFrequency = frequency;
		frequency = newFrequency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Lorawan2Package.TX_INFO__FREQUENCY, oldFrequency, frequency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModulation() {
		return modulation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModulation(String newModulation) {
		String oldModulation = modulation;
		modulation = newModulation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Lorawan2Package.TX_INFO__MODULATION, oldModulation, modulation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LoraInfo getLora() {
		return lora;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLora(LoraInfo newLora, NotificationChain msgs) {
		LoraInfo oldLora = lora;
		lora = newLora;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Lorawan2Package.TX_INFO__LORA, oldLora, newLora);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLora(LoraInfo newLora) {
		if (newLora != lora) {
			NotificationChain msgs = null;
			if (lora != null)
				msgs = ((InternalEObject)lora).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Lorawan2Package.TX_INFO__LORA, null, msgs);
			if (newLora != null)
				msgs = ((InternalEObject)newLora).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Lorawan2Package.TX_INFO__LORA, null, msgs);
			msgs = basicSetLora(newLora, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Lorawan2Package.TX_INFO__LORA, newLora, newLora));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case Lorawan2Package.TX_INFO__LORA:
				return basicSetLora(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Lorawan2Package.TX_INFO__FREQUENCY:
				return getFrequency();
			case Lorawan2Package.TX_INFO__MODULATION:
				return getModulation();
			case Lorawan2Package.TX_INFO__LORA:
				return getLora();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Lorawan2Package.TX_INFO__FREQUENCY:
				setFrequency((Long)newValue);
				return;
			case Lorawan2Package.TX_INFO__MODULATION:
				setModulation((String)newValue);
				return;
			case Lorawan2Package.TX_INFO__LORA:
				setLora((LoraInfo)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case Lorawan2Package.TX_INFO__FREQUENCY:
				setFrequency(FREQUENCY_EDEFAULT);
				return;
			case Lorawan2Package.TX_INFO__MODULATION:
				setModulation(MODULATION_EDEFAULT);
				return;
			case Lorawan2Package.TX_INFO__LORA:
				setLora((LoraInfo)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case Lorawan2Package.TX_INFO__FREQUENCY:
				return frequency != FREQUENCY_EDEFAULT;
			case Lorawan2Package.TX_INFO__MODULATION:
				return MODULATION_EDEFAULT == null ? modulation != null : !MODULATION_EDEFAULT.equals(modulation);
			case Lorawan2Package.TX_INFO__LORA:
				return lora != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (frequency: ");
		result.append(frequency);
		result.append(", modulation: ");
		result.append(modulation);
		result.append(')');
		return result.toString();
	}

} //TxInfoImpl
