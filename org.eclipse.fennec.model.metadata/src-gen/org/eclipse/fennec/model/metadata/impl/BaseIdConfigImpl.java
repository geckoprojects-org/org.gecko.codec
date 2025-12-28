/*
 * Copyright (c) 2012 - 2025 Data In Motion and others.
 * All rights reserved.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 * 
 */
package org.eclipse.fennec.model.metadata.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.model.metadata.BaseIdConfig;
import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.IdStrategy;
import org.eclipse.fennec.model.metadata.MetadataPackage;
import org.eclipse.fennec.model.metadata.SerializationFormat;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Id Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseIdConfigImpl#getStrategy <em>Strategy</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseIdConfigImpl#getKeyMode <em>Key Mode</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseIdConfigImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseIdConfigImpl#getIdKey <em>Id Key</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseIdConfigImpl#getSeparator <em>Separator</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class BaseIdConfigImpl extends MinimalEObjectImpl.Container implements BaseIdConfig {
	/**
	 * The default value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final IdStrategy STRATEGY_EDEFAULT = IdStrategy.ID_FIELD;

	/**
	 * The cached value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected IdStrategy strategy = STRATEGY_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeyMode() <em>Key Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyMode()
	 * @generated
	 * @ordered
	 */
	protected static final IdKeyMode KEY_MODE_EDEFAULT = IdKeyMode.ID_ONLY;

	/**
	 * The cached value of the '{@link #getKeyMode() <em>Key Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyMode()
	 * @generated
	 * @ordered
	 */
	protected IdKeyMode keyMode = KEY_MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFormat() <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormat()
	 * @generated
	 * @ordered
	 */
	protected static final SerializationFormat FORMAT_EDEFAULT = SerializationFormat.PLAIN;

	/**
	 * The cached value of the '{@link #getFormat() <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormat()
	 * @generated
	 * @ordered
	 */
	protected SerializationFormat format = FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getIdKey() <em>Id Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdKey()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_KEY_EDEFAULT = "_id";

	/**
	 * The cached value of the '{@link #getIdKey() <em>Id Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdKey()
	 * @generated
	 * @ordered
	 */
	protected String idKey = ID_KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSeparator() <em>Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeparator()
	 * @generated
	 * @ordered
	 */
	protected static final String SEPARATOR_EDEFAULT = "-";

	/**
	 * The cached value of the '{@link #getSeparator() <em>Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeparator()
	 * @generated
	 * @ordered
	 */
	protected String separator = SEPARATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseIdConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.BASE_ID_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdStrategy getStrategy() {
		return strategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStrategy(IdStrategy newStrategy) {
		IdStrategy oldStrategy = strategy;
		strategy = newStrategy == null ? STRATEGY_EDEFAULT : newStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_ID_CONFIG__STRATEGY, oldStrategy, strategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdKeyMode getKeyMode() {
		return keyMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKeyMode(IdKeyMode newKeyMode) {
		IdKeyMode oldKeyMode = keyMode;
		keyMode = newKeyMode == null ? KEY_MODE_EDEFAULT : newKeyMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_ID_CONFIG__KEY_MODE, oldKeyMode, keyMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SerializationFormat getFormat() {
		return format;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFormat(SerializationFormat newFormat) {
		SerializationFormat oldFormat = format;
		format = newFormat == null ? FORMAT_EDEFAULT : newFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_ID_CONFIG__FORMAT, oldFormat, format));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIdKey() {
		return idKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdKey(String newIdKey) {
		String oldIdKey = idKey;
		idKey = newIdKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_ID_CONFIG__ID_KEY, oldIdKey, idKey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSeparator() {
		return separator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSeparator(String newSeparator) {
		String oldSeparator = separator;
		separator = newSeparator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_ID_CONFIG__SEPARATOR, oldSeparator, separator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetadataPackage.BASE_ID_CONFIG__STRATEGY:
				return getStrategy();
			case MetadataPackage.BASE_ID_CONFIG__KEY_MODE:
				return getKeyMode();
			case MetadataPackage.BASE_ID_CONFIG__FORMAT:
				return getFormat();
			case MetadataPackage.BASE_ID_CONFIG__ID_KEY:
				return getIdKey();
			case MetadataPackage.BASE_ID_CONFIG__SEPARATOR:
				return getSeparator();
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
			case MetadataPackage.BASE_ID_CONFIG__STRATEGY:
				setStrategy((IdStrategy)newValue);
				return;
			case MetadataPackage.BASE_ID_CONFIG__KEY_MODE:
				setKeyMode((IdKeyMode)newValue);
				return;
			case MetadataPackage.BASE_ID_CONFIG__FORMAT:
				setFormat((SerializationFormat)newValue);
				return;
			case MetadataPackage.BASE_ID_CONFIG__ID_KEY:
				setIdKey((String)newValue);
				return;
			case MetadataPackage.BASE_ID_CONFIG__SEPARATOR:
				setSeparator((String)newValue);
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
			case MetadataPackage.BASE_ID_CONFIG__STRATEGY:
				setStrategy(STRATEGY_EDEFAULT);
				return;
			case MetadataPackage.BASE_ID_CONFIG__KEY_MODE:
				setKeyMode(KEY_MODE_EDEFAULT);
				return;
			case MetadataPackage.BASE_ID_CONFIG__FORMAT:
				setFormat(FORMAT_EDEFAULT);
				return;
			case MetadataPackage.BASE_ID_CONFIG__ID_KEY:
				setIdKey(ID_KEY_EDEFAULT);
				return;
			case MetadataPackage.BASE_ID_CONFIG__SEPARATOR:
				setSeparator(SEPARATOR_EDEFAULT);
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
			case MetadataPackage.BASE_ID_CONFIG__STRATEGY:
				return strategy != STRATEGY_EDEFAULT;
			case MetadataPackage.BASE_ID_CONFIG__KEY_MODE:
				return keyMode != KEY_MODE_EDEFAULT;
			case MetadataPackage.BASE_ID_CONFIG__FORMAT:
				return format != FORMAT_EDEFAULT;
			case MetadataPackage.BASE_ID_CONFIG__ID_KEY:
				return ID_KEY_EDEFAULT == null ? idKey != null : !ID_KEY_EDEFAULT.equals(idKey);
			case MetadataPackage.BASE_ID_CONFIG__SEPARATOR:
				return SEPARATOR_EDEFAULT == null ? separator != null : !SEPARATOR_EDEFAULT.equals(separator);
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
		result.append(" (strategy: ");
		result.append(strategy);
		result.append(", keyMode: ");
		result.append(keyMode);
		result.append(", format: ");
		result.append(format);
		result.append(", idKey: ");
		result.append(idKey);
		result.append(", separator: ");
		result.append(separator);
		result.append(')');
		return result.toString();
	}

} //BaseIdConfigImpl
