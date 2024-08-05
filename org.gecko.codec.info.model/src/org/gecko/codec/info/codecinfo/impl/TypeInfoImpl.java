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
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl#getTypeStrategy <em>Type Strategy</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl#getTypeValue <em>Type Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeInfoImpl extends FeatureCodecInfoImpl implements TypeInfo {
	/**
	 * The default value of the '{@link #getTypeStrategy() <em>Type Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_STRATEGY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeStrategy() <em>Type Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeStrategy()
	 * @generated
	 * @ordered
	 */
	protected String typeStrategy = TYPE_STRATEGY_EDEFAULT;

	/**
	 * The default value of the '{@link #getTypeValue() <em>Type Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeValue()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeValue() <em>Type Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeValue()
	 * @generated
	 * @ordered
	 */
	protected String typeValue = TYPE_VALUE_EDEFAULT;

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
	public String getTypeStrategy() {
		return typeStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeStrategy(String newTypeStrategy) {
		String oldTypeStrategy = typeStrategy;
		typeStrategy = newTypeStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.TYPE_INFO__TYPE_STRATEGY, oldTypeStrategy, typeStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeValue() {
		return typeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeValue(String newTypeValue) {
		String oldTypeValue = typeValue;
		typeValue = newTypeValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.TYPE_INFO__TYPE_VALUE, oldTypeValue, typeValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.TYPE_INFO__TYPE_STRATEGY:
				return getTypeStrategy();
			case CodecInfoPackage.TYPE_INFO__TYPE_VALUE:
				return getTypeValue();
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
			case CodecInfoPackage.TYPE_INFO__TYPE_STRATEGY:
				setTypeStrategy((String)newValue);
				return;
			case CodecInfoPackage.TYPE_INFO__TYPE_VALUE:
				setTypeValue((String)newValue);
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
			case CodecInfoPackage.TYPE_INFO__TYPE_STRATEGY:
				setTypeStrategy(TYPE_STRATEGY_EDEFAULT);
				return;
			case CodecInfoPackage.TYPE_INFO__TYPE_VALUE:
				setTypeValue(TYPE_VALUE_EDEFAULT);
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
			case CodecInfoPackage.TYPE_INFO__TYPE_STRATEGY:
				return TYPE_STRATEGY_EDEFAULT == null ? typeStrategy != null : !TYPE_STRATEGY_EDEFAULT.equals(typeStrategy);
			case CodecInfoPackage.TYPE_INFO__TYPE_VALUE:
				return TYPE_VALUE_EDEFAULT == null ? typeValue != null : !TYPE_VALUE_EDEFAULT.equals(typeValue);
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
		result.append(" (typeStrategy: ");
		result.append(typeStrategy);
		result.append(", typeValue: ");
		result.append(typeValue);
		result.append(')');
		return result.toString();
	}

} //TypeInfoImpl
