/*
 */
package org.gecko.codec.info.codecinfo.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.ReferenceInfo;
import org.gecko.codec.info.codecinfo.TypeInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EClass Codec Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getClassifier <em>Classifier</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getIdentityInfo <em>Identity Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getTypeInfo <em>Type Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getFeatureInfo <em>Feature Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getReferenceInfo <em>Reference Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#isSerializeDefaultValue <em>Serialize Default Value</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#isSerializeArrayBatched <em>Serialize Array Batched</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#isUseNamesFromExtendedMetaData <em>Use Names From Extended Meta Data</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EClassCodecInfoImpl extends MinimalEObjectImpl.Container implements EClassCodecInfo {
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
	 * The cached value of the '{@link #getClassifier() <em>Classifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassifier()
	 * @generated
	 * @ordered
	 */
	protected EClassifier classifier;

	/**
	 * The cached value of the '{@link #getIdentityInfo() <em>Identity Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentityInfo()
	 * @generated
	 * @ordered
	 */
	protected IdentityInfo identityInfo;

	/**
	 * The cached value of the '{@link #getTypeInfo() <em>Type Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeInfo()
	 * @generated
	 * @ordered
	 */
	protected TypeInfo typeInfo;

	/**
	 * The cached value of the '{@link #getFeatureInfo() <em>Feature Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureInfo()
	 * @generated
	 * @ordered
	 */
	protected FeatureCodecInfo featureInfo;

	/**
	 * The cached value of the '{@link #getReferenceInfo() <em>Reference Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenceInfo()
	 * @generated
	 * @ordered
	 */
	protected ReferenceInfo referenceInfo;

	/**
	 * The default value of the '{@link #isSerializeDefaultValue() <em>Serialize Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_DEFAULT_VALUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSerializeDefaultValue() <em>Serialize Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeDefaultValue = SERIALIZE_DEFAULT_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isSerializeArrayBatched() <em>Serialize Array Batched</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeArrayBatched()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_ARRAY_BATCHED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSerializeArrayBatched() <em>Serialize Array Batched</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeArrayBatched()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeArrayBatched = SERIALIZE_ARRAY_BATCHED_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseNamesFromExtendedMetaData() <em>Use Names From Extended Meta Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseNamesFromExtendedMetaData()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_NAMES_FROM_EXTENDED_META_DATA_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUseNamesFromExtendedMetaData() <em>Use Names From Extended Meta Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseNamesFromExtendedMetaData()
	 * @generated
	 * @ordered
	 */
	protected boolean useNamesFromExtendedMetaData = USE_NAMES_FROM_EXTENDED_META_DATA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClassCodecInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.ECLASS_CODEC_INFO;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClassifier getClassifier() {
		if (classifier != null && classifier.eIsProxy()) {
			InternalEObject oldClassifier = (InternalEObject)classifier;
			classifier = (EClassifier)eResolveProxy(oldClassifier);
			if (classifier != oldClassifier) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodecInfoPackage.ECLASS_CODEC_INFO__CLASSIFIER, oldClassifier, classifier));
			}
		}
		return classifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier basicGetClassifier() {
		return classifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setClassifier(EClassifier newClassifier) {
		EClassifier oldClassifier = classifier;
		classifier = newClassifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__CLASSIFIER, oldClassifier, classifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentityInfo getIdentityInfo() {
		return identityInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdentityInfo(IdentityInfo newIdentityInfo, NotificationChain msgs) {
		IdentityInfo oldIdentityInfo = identityInfo;
		identityInfo = newIdentityInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO, oldIdentityInfo, newIdentityInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdentityInfo(IdentityInfo newIdentityInfo) {
		if (newIdentityInfo != identityInfo) {
			NotificationChain msgs = null;
			if (identityInfo != null)
				msgs = ((InternalEObject)identityInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO, null, msgs);
			if (newIdentityInfo != null)
				msgs = ((InternalEObject)newIdentityInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO, null, msgs);
			msgs = basicSetIdentityInfo(newIdentityInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO, newIdentityInfo, newIdentityInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeInfo getTypeInfo() {
		return typeInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeInfo(TypeInfo newTypeInfo, NotificationChain msgs) {
		TypeInfo oldTypeInfo = typeInfo;
		typeInfo = newTypeInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO, oldTypeInfo, newTypeInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeInfo(TypeInfo newTypeInfo) {
		if (newTypeInfo != typeInfo) {
			NotificationChain msgs = null;
			if (typeInfo != null)
				msgs = ((InternalEObject)typeInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO, null, msgs);
			if (newTypeInfo != null)
				msgs = ((InternalEObject)newTypeInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO, null, msgs);
			msgs = basicSetTypeInfo(newTypeInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO, newTypeInfo, newTypeInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureCodecInfo getFeatureInfo() {
		return featureInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeatureInfo(FeatureCodecInfo newFeatureInfo, NotificationChain msgs) {
		FeatureCodecInfo oldFeatureInfo = featureInfo;
		featureInfo = newFeatureInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO, oldFeatureInfo, newFeatureInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFeatureInfo(FeatureCodecInfo newFeatureInfo) {
		if (newFeatureInfo != featureInfo) {
			NotificationChain msgs = null;
			if (featureInfo != null)
				msgs = ((InternalEObject)featureInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO, null, msgs);
			if (newFeatureInfo != null)
				msgs = ((InternalEObject)newFeatureInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO, null, msgs);
			msgs = basicSetFeatureInfo(newFeatureInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO, newFeatureInfo, newFeatureInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReferenceInfo getReferenceInfo() {
		return referenceInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReferenceInfo(ReferenceInfo newReferenceInfo, NotificationChain msgs) {
		ReferenceInfo oldReferenceInfo = referenceInfo;
		referenceInfo = newReferenceInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO, oldReferenceInfo, newReferenceInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReferenceInfo(ReferenceInfo newReferenceInfo) {
		if (newReferenceInfo != referenceInfo) {
			NotificationChain msgs = null;
			if (referenceInfo != null)
				msgs = ((InternalEObject)referenceInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO, null, msgs);
			if (newReferenceInfo != null)
				msgs = ((InternalEObject)newReferenceInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO, null, msgs);
			msgs = basicSetReferenceInfo(newReferenceInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO, newReferenceInfo, newReferenceInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeDefaultValue() {
		return serializeDefaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeDefaultValue(boolean newSerializeDefaultValue) {
		boolean oldSerializeDefaultValue = serializeDefaultValue;
		serializeDefaultValue = newSerializeDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_DEFAULT_VALUE, oldSerializeDefaultValue, serializeDefaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeArrayBatched() {
		return serializeArrayBatched;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeArrayBatched(boolean newSerializeArrayBatched) {
		boolean oldSerializeArrayBatched = serializeArrayBatched;
		serializeArrayBatched = newSerializeArrayBatched;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_ARRAY_BATCHED, oldSerializeArrayBatched, serializeArrayBatched));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUseNamesFromExtendedMetaData() {
		return useNamesFromExtendedMetaData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUseNamesFromExtendedMetaData(boolean newUseNamesFromExtendedMetaData) {
		boolean oldUseNamesFromExtendedMetaData = useNamesFromExtendedMetaData;
		useNamesFromExtendedMetaData = newUseNamesFromExtendedMetaData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_CODEC_INFO__USE_NAMES_FROM_EXTENDED_META_DATA, oldUseNamesFromExtendedMetaData, useNamesFromExtendedMetaData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO:
				return basicSetIdentityInfo(null, msgs);
			case CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO:
				return basicSetTypeInfo(null, msgs);
			case CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO:
				return basicSetFeatureInfo(null, msgs);
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO:
				return basicSetReferenceInfo(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.ECLASS_CODEC_INFO__ID:
				return getId();
			case CodecInfoPackage.ECLASS_CODEC_INFO__CLASSIFIER:
				if (resolve) return getClassifier();
				return basicGetClassifier();
			case CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO:
				return getIdentityInfo();
			case CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO:
				return getTypeInfo();
			case CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO:
				return getFeatureInfo();
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO:
				return getReferenceInfo();
			case CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_DEFAULT_VALUE:
				return isSerializeDefaultValue();
			case CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_ARRAY_BATCHED:
				return isSerializeArrayBatched();
			case CodecInfoPackage.ECLASS_CODEC_INFO__USE_NAMES_FROM_EXTENDED_META_DATA:
				return isUseNamesFromExtendedMetaData();
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
			case CodecInfoPackage.ECLASS_CODEC_INFO__ID:
				setId((String)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__CLASSIFIER:
				setClassifier((EClassifier)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO:
				setIdentityInfo((IdentityInfo)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO:
				setTypeInfo((TypeInfo)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO:
				setFeatureInfo((FeatureCodecInfo)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO:
				setReferenceInfo((ReferenceInfo)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_DEFAULT_VALUE:
				setSerializeDefaultValue((Boolean)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_ARRAY_BATCHED:
				setSerializeArrayBatched((Boolean)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__USE_NAMES_FROM_EXTENDED_META_DATA:
				setUseNamesFromExtendedMetaData((Boolean)newValue);
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
			case CodecInfoPackage.ECLASS_CODEC_INFO__ID:
				setId(ID_EDEFAULT);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__CLASSIFIER:
				setClassifier((EClassifier)null);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO:
				setIdentityInfo((IdentityInfo)null);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO:
				setTypeInfo((TypeInfo)null);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO:
				setFeatureInfo((FeatureCodecInfo)null);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO:
				setReferenceInfo((ReferenceInfo)null);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_DEFAULT_VALUE:
				setSerializeDefaultValue(SERIALIZE_DEFAULT_VALUE_EDEFAULT);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_ARRAY_BATCHED:
				setSerializeArrayBatched(SERIALIZE_ARRAY_BATCHED_EDEFAULT);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__USE_NAMES_FROM_EXTENDED_META_DATA:
				setUseNamesFromExtendedMetaData(USE_NAMES_FROM_EXTENDED_META_DATA_EDEFAULT);
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
			case CodecInfoPackage.ECLASS_CODEC_INFO__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case CodecInfoPackage.ECLASS_CODEC_INFO__CLASSIFIER:
				return classifier != null;
			case CodecInfoPackage.ECLASS_CODEC_INFO__IDENTITY_INFO:
				return identityInfo != null;
			case CodecInfoPackage.ECLASS_CODEC_INFO__TYPE_INFO:
				return typeInfo != null;
			case CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO:
				return featureInfo != null;
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_INFO:
				return referenceInfo != null;
			case CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_DEFAULT_VALUE:
				return serializeDefaultValue != SERIALIZE_DEFAULT_VALUE_EDEFAULT;
			case CodecInfoPackage.ECLASS_CODEC_INFO__SERIALIZE_ARRAY_BATCHED:
				return serializeArrayBatched != SERIALIZE_ARRAY_BATCHED_EDEFAULT;
			case CodecInfoPackage.ECLASS_CODEC_INFO__USE_NAMES_FROM_EXTENDED_META_DATA:
				return useNamesFromExtendedMetaData != USE_NAMES_FROM_EXTENDED_META_DATA_EDEFAULT;
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
		result.append(", serializeDefaultValue: ");
		result.append(serializeDefaultValue);
		result.append(", serializeArrayBatched: ");
		result.append(serializeArrayBatched);
		result.append(", useNamesFromExtendedMetaData: ");
		result.append(useNamesFromExtendedMetaData);
		result.append(')');
		return result.toString();
	}

} //EClassCodecInfoImpl
