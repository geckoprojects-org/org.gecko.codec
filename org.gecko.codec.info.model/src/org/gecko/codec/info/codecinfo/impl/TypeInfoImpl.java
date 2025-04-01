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
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl#isIgnoreType <em>Ignore Type</em>}</li>
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
	 * The default value of the '{@link #isIgnoreType() <em>Ignore Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_TYPE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIgnoreType() <em>Ignore Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreType()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreType = IGNORE_TYPE_EDEFAULT;

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
	public boolean isIgnoreType() {
		return ignoreType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIgnoreType(boolean newIgnoreType) {
		boolean oldIgnoreType = ignoreType;
		ignoreType = newIgnoreType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.TYPE_INFO__IGNORE_TYPE, oldIgnoreType, ignoreType));
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
			case CodecInfoPackage.TYPE_INFO__IGNORE_TYPE:
				return isIgnoreType();
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
			case CodecInfoPackage.TYPE_INFO__IGNORE_TYPE:
				setIgnoreType((Boolean)newValue);
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
			case CodecInfoPackage.TYPE_INFO__IGNORE_TYPE:
				setIgnoreType(IGNORE_TYPE_EDEFAULT);
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
			case CodecInfoPackage.TYPE_INFO__IGNORE_TYPE:
				return ignoreType != IGNORE_TYPE_EDEFAULT;
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
		result.append(", ignoreType: ");
		result.append(ignoreType);
		result.append(')');
		return result.toString();
	}

} //TypeInfoImpl
