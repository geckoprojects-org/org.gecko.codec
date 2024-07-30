/*
 */
package org.gecko.codec.info.codecinfo.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.TypeInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl#isWriteSuperTypes <em>Write Super Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeInfoImpl extends FeatureInfoImpl implements TypeInfo {
	/**
	 * The default value of the '{@link #isWriteSuperTypes() <em>Write Super Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWriteSuperTypes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WRITE_SUPER_TYPES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWriteSuperTypes() <em>Write Super Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWriteSuperTypes()
	 * @generated
	 * @ordered
	 */
	protected boolean writeSuperTypes = WRITE_SUPER_TYPES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.TYPE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWriteSuperTypes() {
		return writeSuperTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWriteSuperTypes(boolean newWriteSuperTypes) {
		boolean oldWriteSuperTypes = writeSuperTypes;
		writeSuperTypes = newWriteSuperTypes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.TYPE_INFO__WRITE_SUPER_TYPES, oldWriteSuperTypes, writeSuperTypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.TYPE_INFO__WRITE_SUPER_TYPES:
				return isWriteSuperTypes();
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
			case CodecInfoPackage.TYPE_INFO__WRITE_SUPER_TYPES:
				setWriteSuperTypes((Boolean)newValue);
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
			case CodecInfoPackage.TYPE_INFO__WRITE_SUPER_TYPES:
				setWriteSuperTypes(WRITE_SUPER_TYPES_EDEFAULT);
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
			case CodecInfoPackage.TYPE_INFO__WRITE_SUPER_TYPES:
				return writeSuperTypes != WRITE_SUPER_TYPES_EDEFAULT;
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
		result.append(" (writeSuperTypes: ");
		result.append(writeSuperTypes);
		result.append(')');
		return result.toString();
	}

} //TypeInfoImpl
