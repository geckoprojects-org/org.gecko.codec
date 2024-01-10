/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage;
import org.eclipse.emfcloud.jackson.junit.annotations.RawJson;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Raw Json</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.RawJsonImpl#getRaw <em>Raw</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RawJsonImpl extends MinimalEObjectImpl implements RawJson {
	/**
	 * The default value of the '{@link #getRaw() <em>Raw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaw()
	 * @generated
	 * @ordered
	 */
	protected static final String RAW_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRaw() <em>Raw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaw()
	 * @generated
	 * @ordered
	 */
	protected String raw = RAW_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RawJsonImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnnotationsPackage.Literals.RAW_JSON;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRaw() {
		return raw;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRaw(String newRaw) {
		String oldRaw = raw;
		raw = newRaw;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnnotationsPackage.RAW_JSON__RAW, oldRaw, raw));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AnnotationsPackage.RAW_JSON__RAW:
				return getRaw();
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
			case AnnotationsPackage.RAW_JSON__RAW:
				setRaw((String)newValue);
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
			case AnnotationsPackage.RAW_JSON__RAW:
				setRaw(RAW_EDEFAULT);
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
			case AnnotationsPackage.RAW_JSON__RAW:
				return RAW_EDEFAULT == null ? raw != null : !RAW_EDEFAULT.equals(raw);
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
		result.append(" (raw: ");
		result.append(raw);
		result.append(')');
		return result.toString();
	}

} //RawJsonImpl
