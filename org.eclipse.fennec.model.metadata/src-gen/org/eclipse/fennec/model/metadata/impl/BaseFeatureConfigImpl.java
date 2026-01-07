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

import org.eclipse.fennec.model.metadata.BaseFeatureConfig;
import org.eclipse.fennec.model.metadata.EnumSerializationStrategy;
import org.eclipse.fennec.model.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Feature Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseFeatureConfigImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseFeatureConfigImpl#getSerialize <em>Serialize</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseFeatureConfigImpl#getSerializeNull <em>Serialize Null</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseFeatureConfigImpl#getSerializeEmpty <em>Serialize Empty</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseFeatureConfigImpl#getSerializeDefaults <em>Serialize Defaults</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.impl.BaseFeatureConfigImpl#getEnumSerialization <em>Enum Serialization</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class BaseFeatureConfigImpl extends MinimalEObjectImpl.Container implements BaseFeatureConfig {
	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSerialize() <em>Serialize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerialize()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean SERIALIZE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSerialize() <em>Serialize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerialize()
	 * @generated
	 * @ordered
	 */
	protected Boolean serialize = SERIALIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSerializeNull() <em>Serialize Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializeNull()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean SERIALIZE_NULL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSerializeNull() <em>Serialize Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializeNull()
	 * @generated
	 * @ordered
	 */
	protected Boolean serializeNull = SERIALIZE_NULL_EDEFAULT;

	/**
	 * The default value of the '{@link #getSerializeEmpty() <em>Serialize Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializeEmpty()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean SERIALIZE_EMPTY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSerializeEmpty() <em>Serialize Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializeEmpty()
	 * @generated
	 * @ordered
	 */
	protected Boolean serializeEmpty = SERIALIZE_EMPTY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSerializeDefaults() <em>Serialize Defaults</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializeDefaults()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean SERIALIZE_DEFAULTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSerializeDefaults() <em>Serialize Defaults</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializeDefaults()
	 * @generated
	 * @ordered
	 */
	protected Boolean serializeDefaults = SERIALIZE_DEFAULTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getEnumSerialization() <em>Enum Serialization</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumSerialization()
	 * @generated
	 * @ordered
	 */
	protected static final EnumSerializationStrategy ENUM_SERIALIZATION_EDEFAULT = EnumSerializationStrategy.LITERAL;

	/**
	 * The cached value of the '{@link #getEnumSerialization() <em>Enum Serialization</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumSerialization()
	 * @generated
	 * @ordered
	 */
	protected EnumSerializationStrategy enumSerialization = ENUM_SERIALIZATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseFeatureConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.BASE_FEATURE_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_FEATURE_CONFIG__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getSerialize() {
		return serialize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerialize(Boolean newSerialize) {
		Boolean oldSerialize = serialize;
		serialize = newSerialize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE, oldSerialize, serialize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getSerializeNull() {
		return serializeNull;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeNull(Boolean newSerializeNull) {
		Boolean oldSerializeNull = serializeNull;
		serializeNull = newSerializeNull;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_NULL, oldSerializeNull, serializeNull));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getSerializeEmpty() {
		return serializeEmpty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeEmpty(Boolean newSerializeEmpty) {
		Boolean oldSerializeEmpty = serializeEmpty;
		serializeEmpty = newSerializeEmpty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_EMPTY, oldSerializeEmpty, serializeEmpty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getSerializeDefaults() {
		return serializeDefaults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeDefaults(Boolean newSerializeDefaults) {
		Boolean oldSerializeDefaults = serializeDefaults;
		serializeDefaults = newSerializeDefaults;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_DEFAULTS, oldSerializeDefaults, serializeDefaults));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumSerializationStrategy getEnumSerialization() {
		return enumSerialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEnumSerialization(EnumSerializationStrategy newEnumSerialization) {
		EnumSerializationStrategy oldEnumSerialization = enumSerialization;
		enumSerialization = newEnumSerialization == null ? ENUM_SERIALIZATION_EDEFAULT : newEnumSerialization;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.BASE_FEATURE_CONFIG__ENUM_SERIALIZATION, oldEnumSerialization, enumSerialization));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetadataPackage.BASE_FEATURE_CONFIG__KEY:
				return getKey();
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE:
				return getSerialize();
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_NULL:
				return getSerializeNull();
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_EMPTY:
				return getSerializeEmpty();
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_DEFAULTS:
				return getSerializeDefaults();
			case MetadataPackage.BASE_FEATURE_CONFIG__ENUM_SERIALIZATION:
				return getEnumSerialization();
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
			case MetadataPackage.BASE_FEATURE_CONFIG__KEY:
				setKey((String)newValue);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE:
				setSerialize((Boolean)newValue);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_NULL:
				setSerializeNull((Boolean)newValue);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_EMPTY:
				setSerializeEmpty((Boolean)newValue);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_DEFAULTS:
				setSerializeDefaults((Boolean)newValue);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__ENUM_SERIALIZATION:
				setEnumSerialization((EnumSerializationStrategy)newValue);
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
			case MetadataPackage.BASE_FEATURE_CONFIG__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE:
				setSerialize(SERIALIZE_EDEFAULT);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_NULL:
				setSerializeNull(SERIALIZE_NULL_EDEFAULT);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_EMPTY:
				setSerializeEmpty(SERIALIZE_EMPTY_EDEFAULT);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_DEFAULTS:
				setSerializeDefaults(SERIALIZE_DEFAULTS_EDEFAULT);
				return;
			case MetadataPackage.BASE_FEATURE_CONFIG__ENUM_SERIALIZATION:
				setEnumSerialization(ENUM_SERIALIZATION_EDEFAULT);
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
			case MetadataPackage.BASE_FEATURE_CONFIG__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE:
				return SERIALIZE_EDEFAULT == null ? serialize != null : !SERIALIZE_EDEFAULT.equals(serialize);
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_NULL:
				return SERIALIZE_NULL_EDEFAULT == null ? serializeNull != null : !SERIALIZE_NULL_EDEFAULT.equals(serializeNull);
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_EMPTY:
				return SERIALIZE_EMPTY_EDEFAULT == null ? serializeEmpty != null : !SERIALIZE_EMPTY_EDEFAULT.equals(serializeEmpty);
			case MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_DEFAULTS:
				return SERIALIZE_DEFAULTS_EDEFAULT == null ? serializeDefaults != null : !SERIALIZE_DEFAULTS_EDEFAULT.equals(serializeDefaults);
			case MetadataPackage.BASE_FEATURE_CONFIG__ENUM_SERIALIZATION:
				return enumSerialization != ENUM_SERIALIZATION_EDEFAULT;
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
		result.append(" (key: ");
		result.append(key);
		result.append(", serialize: ");
		result.append(serialize);
		result.append(", serializeNull: ");
		result.append(serializeNull);
		result.append(", serializeEmpty: ");
		result.append(serializeEmpty);
		result.append(", serializeDefaults: ");
		result.append(serializeDefaults);
		result.append(", enumSerialization: ");
		result.append(enumSerialization);
		result.append(')');
		return result.toString();
	}

} //BaseFeatureConfigImpl
