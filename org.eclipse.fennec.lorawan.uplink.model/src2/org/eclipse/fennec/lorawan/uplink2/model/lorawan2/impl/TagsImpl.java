/*
 */
package org.eclipse.fennec.lorawan.uplink2.model.lorawan2.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package;
import org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Tags;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tags</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.impl.TagsImpl#getDev_type <em>Dev type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TagsImpl extends MinimalEObjectImpl.Container implements Tags {
	/**
	 * The default value of the '{@link #getDev_type() <em>Dev type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDev_type()
	 * @generated
	 * @ordered
	 */
	protected static final String DEV_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDev_type() <em>Dev type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDev_type()
	 * @generated
	 * @ordered
	 */
	protected String dev_type = DEV_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TagsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Lorawan2Package.Literals.TAGS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDev_type() {
		return dev_type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDev_type(String newDev_type) {
		String oldDev_type = dev_type;
		dev_type = newDev_type;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Lorawan2Package.TAGS__DEV_TYPE, oldDev_type, dev_type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Lorawan2Package.TAGS__DEV_TYPE:
				return getDev_type();
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
			case Lorawan2Package.TAGS__DEV_TYPE:
				setDev_type((String)newValue);
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
			case Lorawan2Package.TAGS__DEV_TYPE:
				setDev_type(DEV_TYPE_EDEFAULT);
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
			case Lorawan2Package.TAGS__DEV_TYPE:
				return DEV_TYPE_EDEFAULT == null ? dev_type != null : !DEV_TYPE_EDEFAULT.equals(dev_type);
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
		result.append(" (dev_type: ");
		result.append(dev_type);
		result.append(')');
		return result.toString();
	}

} //TagsImpl
