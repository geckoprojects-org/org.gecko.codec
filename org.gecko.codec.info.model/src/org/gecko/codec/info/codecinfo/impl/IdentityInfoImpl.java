/*
 */
package org.gecko.codec.info.codecinfo.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.IdentityInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Identity Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl#getIdStrategy <em>Id Strategy</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl#getIdSeparator <em>Id Separator</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl#getIdOrder <em>Id Order</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IdentityInfoImpl extends FeatureCodecInfoImpl implements IdentityInfo {
	/**
	 * The default value of the '{@link #getIdStrategy() <em>Id Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_STRATEGY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdStrategy() <em>Id Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdStrategy()
	 * @generated
	 * @ordered
	 */
	protected String idStrategy = ID_STRATEGY_EDEFAULT;

	/**
	 * The default value of the '{@link #getIdSeparator() <em>Id Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdSeparator()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_SEPARATOR_EDEFAULT = ".";

	/**
	 * The cached value of the '{@link #getIdSeparator() <em>Id Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdSeparator()
	 * @generated
	 * @ordered
	 */
	protected String idSeparator = ID_SEPARATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getIdOrder() <em>Id Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdOrder()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_ORDER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIdOrder() <em>Id Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdOrder()
	 * @generated
	 * @ordered
	 */
	protected int idOrder = ID_ORDER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IdentityInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.IDENTITY_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIdStrategy() {
		return idStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdStrategy(String newIdStrategy) {
		String oldIdStrategy = idStrategy;
		idStrategy = newIdStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.IDENTITY_INFO__ID_STRATEGY, oldIdStrategy, idStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIdSeparator() {
		return idSeparator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdSeparator(String newIdSeparator) {
		String oldIdSeparator = idSeparator;
		idSeparator = newIdSeparator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.IDENTITY_INFO__ID_SEPARATOR, oldIdSeparator, idSeparator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getIdOrder() {
		return idOrder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdOrder(int newIdOrder) {
		int oldIdOrder = idOrder;
		idOrder = newIdOrder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.IDENTITY_INFO__ID_ORDER, oldIdOrder, idOrder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.IDENTITY_INFO__ID_STRATEGY:
				return getIdStrategy();
			case CodecInfoPackage.IDENTITY_INFO__ID_SEPARATOR:
				return getIdSeparator();
			case CodecInfoPackage.IDENTITY_INFO__ID_ORDER:
				return getIdOrder();
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
			case CodecInfoPackage.IDENTITY_INFO__ID_STRATEGY:
				setIdStrategy((String)newValue);
				return;
			case CodecInfoPackage.IDENTITY_INFO__ID_SEPARATOR:
				setIdSeparator((String)newValue);
				return;
			case CodecInfoPackage.IDENTITY_INFO__ID_ORDER:
				setIdOrder((Integer)newValue);
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
			case CodecInfoPackage.IDENTITY_INFO__ID_STRATEGY:
				setIdStrategy(ID_STRATEGY_EDEFAULT);
				return;
			case CodecInfoPackage.IDENTITY_INFO__ID_SEPARATOR:
				setIdSeparator(ID_SEPARATOR_EDEFAULT);
				return;
			case CodecInfoPackage.IDENTITY_INFO__ID_ORDER:
				setIdOrder(ID_ORDER_EDEFAULT);
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
			case CodecInfoPackage.IDENTITY_INFO__ID_STRATEGY:
				return ID_STRATEGY_EDEFAULT == null ? idStrategy != null : !ID_STRATEGY_EDEFAULT.equals(idStrategy);
			case CodecInfoPackage.IDENTITY_INFO__ID_SEPARATOR:
				return ID_SEPARATOR_EDEFAULT == null ? idSeparator != null : !ID_SEPARATOR_EDEFAULT.equals(idSeparator);
			case CodecInfoPackage.IDENTITY_INFO__ID_ORDER:
				return idOrder != ID_ORDER_EDEFAULT;
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
		result.append(" (idStrategy: ");
		result.append(idStrategy);
		result.append(", idSeparator: ");
		result.append(idSeparator);
		result.append(", idOrder: ");
		result.append(idOrder);
		result.append(')');
		return result.toString();
	}

} //IdentityInfoImpl
