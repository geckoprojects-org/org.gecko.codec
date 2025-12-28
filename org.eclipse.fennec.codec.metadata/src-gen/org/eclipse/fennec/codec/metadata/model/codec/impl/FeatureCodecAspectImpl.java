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
package org.eclipse.fennec.codec.metadata.model.codec.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fennec.codec.metadata.model.codec.CodecPackage;
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;

import org.eclipse.fennec.model.metadata.impl.FeatureAspectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Codec Aspect</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl#getEffectiveKey <em>Effective Key</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl#isSerialize <em>Serialize</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl#isSerializeNull <em>Serialize Null</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl#isSerializeEmpty <em>Serialize Empty</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl#isSerializeDefaults <em>Serialize Defaults</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl#getValueWriterName <em>Value Writer Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl#getValueReaderName <em>Value Reader Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureCodecAspectImpl extends FeatureAspectImpl implements FeatureCodecAspect {
	/**
	 * The default value of the '{@link #getEffectiveKey() <em>Effective Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEffectiveKey()
	 * @generated
	 * @ordered
	 */
	protected static final String EFFECTIVE_KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEffectiveKey() <em>Effective Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEffectiveKey()
	 * @generated
	 * @ordered
	 */
	protected String effectiveKey = EFFECTIVE_KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #isSerialize() <em>Serialize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerialize()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isSerialize() <em>Serialize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerialize()
	 * @generated
	 * @ordered
	 */
	protected boolean serialize = SERIALIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #isSerializeNull() <em>Serialize Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeNull()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_NULL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSerializeNull() <em>Serialize Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeNull()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeNull = SERIALIZE_NULL_EDEFAULT;

	/**
	 * The default value of the '{@link #isSerializeEmpty() <em>Serialize Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeEmpty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_EMPTY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSerializeEmpty() <em>Serialize Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeEmpty()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeEmpty = SERIALIZE_EMPTY_EDEFAULT;

	/**
	 * The default value of the '{@link #isSerializeDefaults() <em>Serialize Defaults</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeDefaults()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_DEFAULTS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSerializeDefaults() <em>Serialize Defaults</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeDefaults()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeDefaults = SERIALIZE_DEFAULTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueWriterName() <em>Value Writer Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueWriterName()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_WRITER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueWriterName() <em>Value Writer Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueWriterName()
	 * @generated
	 * @ordered
	 */
	protected String valueWriterName = VALUE_WRITER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueReaderName() <em>Value Reader Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueReaderName()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_READER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueReaderName() <em>Value Reader Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueReaderName()
	 * @generated
	 * @ordered
	 */
	protected String valueReaderName = VALUE_READER_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureCodecAspectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecPackage.Literals.FEATURE_CODEC_ASPECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEffectiveKey() {
		return effectiveKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEffectiveKey(String newEffectiveKey) {
		String oldEffectiveKey = effectiveKey;
		effectiveKey = newEffectiveKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.FEATURE_CODEC_ASPECT__EFFECTIVE_KEY, oldEffectiveKey, effectiveKey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerialize() {
		return serialize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerialize(boolean newSerialize) {
		boolean oldSerialize = serialize;
		serialize = newSerialize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE, oldSerialize, serialize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeNull() {
		return serializeNull;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeNull(boolean newSerializeNull) {
		boolean oldSerializeNull = serializeNull;
		serializeNull = newSerializeNull;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_NULL, oldSerializeNull, serializeNull));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeEmpty() {
		return serializeEmpty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeEmpty(boolean newSerializeEmpty) {
		boolean oldSerializeEmpty = serializeEmpty;
		serializeEmpty = newSerializeEmpty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_EMPTY, oldSerializeEmpty, serializeEmpty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeDefaults() {
		return serializeDefaults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeDefaults(boolean newSerializeDefaults) {
		boolean oldSerializeDefaults = serializeDefaults;
		serializeDefaults = newSerializeDefaults;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_DEFAULTS, oldSerializeDefaults, serializeDefaults));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValueWriterName() {
		return valueWriterName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValueWriterName(String newValueWriterName) {
		String oldValueWriterName = valueWriterName;
		valueWriterName = newValueWriterName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.FEATURE_CODEC_ASPECT__VALUE_WRITER_NAME, oldValueWriterName, valueWriterName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValueReaderName() {
		return valueReaderName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValueReaderName(String newValueReaderName) {
		String oldValueReaderName = valueReaderName;
		valueReaderName = newValueReaderName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.FEATURE_CODEC_ASPECT__VALUE_READER_NAME, oldValueReaderName, valueReaderName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecPackage.FEATURE_CODEC_ASPECT__EFFECTIVE_KEY:
				return getEffectiveKey();
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE:
				return isSerialize();
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_NULL:
				return isSerializeNull();
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_EMPTY:
				return isSerializeEmpty();
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_DEFAULTS:
				return isSerializeDefaults();
			case CodecPackage.FEATURE_CODEC_ASPECT__VALUE_WRITER_NAME:
				return getValueWriterName();
			case CodecPackage.FEATURE_CODEC_ASPECT__VALUE_READER_NAME:
				return getValueReaderName();
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
			case CodecPackage.FEATURE_CODEC_ASPECT__EFFECTIVE_KEY:
				setEffectiveKey((String)newValue);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE:
				setSerialize((Boolean)newValue);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_NULL:
				setSerializeNull((Boolean)newValue);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_EMPTY:
				setSerializeEmpty((Boolean)newValue);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_DEFAULTS:
				setSerializeDefaults((Boolean)newValue);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__VALUE_WRITER_NAME:
				setValueWriterName((String)newValue);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__VALUE_READER_NAME:
				setValueReaderName((String)newValue);
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
			case CodecPackage.FEATURE_CODEC_ASPECT__EFFECTIVE_KEY:
				setEffectiveKey(EFFECTIVE_KEY_EDEFAULT);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE:
				setSerialize(SERIALIZE_EDEFAULT);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_NULL:
				setSerializeNull(SERIALIZE_NULL_EDEFAULT);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_EMPTY:
				setSerializeEmpty(SERIALIZE_EMPTY_EDEFAULT);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_DEFAULTS:
				setSerializeDefaults(SERIALIZE_DEFAULTS_EDEFAULT);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__VALUE_WRITER_NAME:
				setValueWriterName(VALUE_WRITER_NAME_EDEFAULT);
				return;
			case CodecPackage.FEATURE_CODEC_ASPECT__VALUE_READER_NAME:
				setValueReaderName(VALUE_READER_NAME_EDEFAULT);
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
			case CodecPackage.FEATURE_CODEC_ASPECT__EFFECTIVE_KEY:
				return EFFECTIVE_KEY_EDEFAULT == null ? effectiveKey != null : !EFFECTIVE_KEY_EDEFAULT.equals(effectiveKey);
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE:
				return serialize != SERIALIZE_EDEFAULT;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_NULL:
				return serializeNull != SERIALIZE_NULL_EDEFAULT;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_EMPTY:
				return serializeEmpty != SERIALIZE_EMPTY_EDEFAULT;
			case CodecPackage.FEATURE_CODEC_ASPECT__SERIALIZE_DEFAULTS:
				return serializeDefaults != SERIALIZE_DEFAULTS_EDEFAULT;
			case CodecPackage.FEATURE_CODEC_ASPECT__VALUE_WRITER_NAME:
				return VALUE_WRITER_NAME_EDEFAULT == null ? valueWriterName != null : !VALUE_WRITER_NAME_EDEFAULT.equals(valueWriterName);
			case CodecPackage.FEATURE_CODEC_ASPECT__VALUE_READER_NAME:
				return VALUE_READER_NAME_EDEFAULT == null ? valueReaderName != null : !VALUE_READER_NAME_EDEFAULT.equals(valueReaderName);
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
		result.append(" (effectiveKey: ");
		result.append(effectiveKey);
		result.append(", serialize: ");
		result.append(serialize);
		result.append(", serializeNull: ");
		result.append(serializeNull);
		result.append(", serializeEmpty: ");
		result.append(serializeEmpty);
		result.append(", serializeDefaults: ");
		result.append(serializeDefaults);
		result.append(", valueWriterName: ");
		result.append(valueWriterName);
		result.append(", valueReaderName: ");
		result.append(valueReaderName);
		result.append(')');
		return result.toString();
	}

} //FeatureCodecAspectImpl
