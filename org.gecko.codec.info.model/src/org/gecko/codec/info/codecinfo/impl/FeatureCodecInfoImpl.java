/*
 */
package org.gecko.codec.info.codecinfo.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Codec Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl#getFeatures <em>Features</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl#getDefaultKey <em>Default Key</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl#getValueReaderName <em>Value Reader Name</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl#getValueWriterName <em>Value Writer Name</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.FeatureCodecInfoImpl#getKey <em>Key</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureCodecInfoImpl extends MinimalEObjectImpl.Container implements FeatureCodecInfo {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFeatures() <em>Features</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<EStructuralFeature> features;

	/**
	 * The default value of the '{@link #getDefaultKey() <em>Default Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultKey()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_KEY_EDEFAULT = "_id";

	/**
	 * The cached value of the '{@link #getDefaultKey() <em>Default Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultKey()
	 * @generated
	 * @ordered
	 */
	protected String defaultKey = DEFAULT_KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueReaderName() <em>Value Reader Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueReaderName()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_READER_NAME_EDEFAULT = "DEFAULT";

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
	 * The default value of the '{@link #getValueWriterName() <em>Value Writer Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueWriterName()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_WRITER_NAME_EDEFAULT = "DEFAULT";

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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final InfoType TYPE_EDEFAULT = InfoType.IDENTITY;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected InfoType type = TYPE_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureCodecInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.FEATURE_CODEC_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.FEATURE_CODEC_INFO__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EStructuralFeature> getFeatures() {
		if (features == null) {
			features = new EObjectResolvingEList<EStructuralFeature>(EStructuralFeature.class, this, CodecInfoPackage.FEATURE_CODEC_INFO__FEATURES);
		}
		return features;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDefaultKey() {
		return defaultKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefaultKey(String newDefaultKey) {
		String oldDefaultKey = defaultKey;
		defaultKey = newDefaultKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.FEATURE_CODEC_INFO__DEFAULT_KEY, oldDefaultKey, defaultKey));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_READER_NAME, oldValueReaderName, valueReaderName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_WRITER_NAME, oldValueWriterName, valueWriterName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InfoType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(InfoType newType) {
		InfoType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.FEATURE_CODEC_INFO__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.FEATURE_CODEC_INFO__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.FEATURE_CODEC_INFO__ID:
				return getId();
			case CodecInfoPackage.FEATURE_CODEC_INFO__FEATURES:
				return getFeatures();
			case CodecInfoPackage.FEATURE_CODEC_INFO__DEFAULT_KEY:
				return getDefaultKey();
			case CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_READER_NAME:
				return getValueReaderName();
			case CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_WRITER_NAME:
				return getValueWriterName();
			case CodecInfoPackage.FEATURE_CODEC_INFO__TYPE:
				return getType();
			case CodecInfoPackage.FEATURE_CODEC_INFO__KEY:
				return getKey();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CodecInfoPackage.FEATURE_CODEC_INFO__ID:
				setId((String)newValue);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__FEATURES:
				getFeatures().clear();
				getFeatures().addAll((Collection<? extends EStructuralFeature>)newValue);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__DEFAULT_KEY:
				setDefaultKey((String)newValue);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_READER_NAME:
				setValueReaderName((String)newValue);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_WRITER_NAME:
				setValueWriterName((String)newValue);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__TYPE:
				setType((InfoType)newValue);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__KEY:
				setKey((String)newValue);
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
			case CodecInfoPackage.FEATURE_CODEC_INFO__ID:
				setId(ID_EDEFAULT);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__FEATURES:
				getFeatures().clear();
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__DEFAULT_KEY:
				setDefaultKey(DEFAULT_KEY_EDEFAULT);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_READER_NAME:
				setValueReaderName(VALUE_READER_NAME_EDEFAULT);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_WRITER_NAME:
				setValueWriterName(VALUE_WRITER_NAME_EDEFAULT);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case CodecInfoPackage.FEATURE_CODEC_INFO__KEY:
				setKey(KEY_EDEFAULT);
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
			case CodecInfoPackage.FEATURE_CODEC_INFO__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case CodecInfoPackage.FEATURE_CODEC_INFO__FEATURES:
				return features != null && !features.isEmpty();
			case CodecInfoPackage.FEATURE_CODEC_INFO__DEFAULT_KEY:
				return DEFAULT_KEY_EDEFAULT == null ? defaultKey != null : !DEFAULT_KEY_EDEFAULT.equals(defaultKey);
			case CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_READER_NAME:
				return VALUE_READER_NAME_EDEFAULT == null ? valueReaderName != null : !VALUE_READER_NAME_EDEFAULT.equals(valueReaderName);
			case CodecInfoPackage.FEATURE_CODEC_INFO__VALUE_WRITER_NAME:
				return VALUE_WRITER_NAME_EDEFAULT == null ? valueWriterName != null : !VALUE_WRITER_NAME_EDEFAULT.equals(valueWriterName);
			case CodecInfoPackage.FEATURE_CODEC_INFO__TYPE:
				return type != TYPE_EDEFAULT;
			case CodecInfoPackage.FEATURE_CODEC_INFO__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
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
		result.append(" (id: ");
		result.append(id);
		result.append(", defaultKey: ");
		result.append(defaultKey);
		result.append(", valueReaderName: ");
		result.append(valueReaderName);
		result.append(", valueWriterName: ");
		result.append(valueWriterName);
		result.append(", type: ");
		result.append(type);
		result.append(", key: ");
		result.append(key);
		result.append(')');
		return result.toString();
	}

} //FeatureCodecInfoImpl
