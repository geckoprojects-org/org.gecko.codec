/*
 */
package org.gecko.codec.info.codecinfo.impl;

import java.util.Collection;

import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
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
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getReferenceCodecInfo <em>Reference Codec Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getAttributeCodecInfo <em>Attribute Codec Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassCodecInfoImpl#getOperationCodecInfo <em>Operation Codec Info</em>}</li>
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
	 * The cached value of the '{@link #getFeatureInfo() <em>Feature Info</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureInfo()
	 * @generated
	 * @ordered
	 */
	protected EList<FeatureCodecInfo> featureInfo;

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
	public EList<FeatureCodecInfo> getFeatureInfo() {
		if (featureInfo == null) {
			featureInfo = new EObjectContainmentEList<FeatureCodecInfo>(FeatureCodecInfo.class, this, CodecInfoPackage.ECLASS_CODEC_INFO__FEATURE_INFO);
		}
		return featureInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FeatureCodecInfo> getReferenceCodecInfo() {
		return ECollections.asEList(getFeatureInfo().stream().filter(f->InfoType.REFERENCE.equals(f.getType())).collect(Collectors.toList()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FeatureCodecInfo> getAttributeCodecInfo() {
		return ECollections.asEList(getFeatureInfo().stream().filter(f->InfoType.ATTRIBUTE.equals(f.getType())).collect(Collectors.toList()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FeatureCodecInfo> getOperationCodecInfo() {
		return ECollections.asEList(getFeatureInfo().stream().filter(f->InfoType.OPERATION.equals(f.getType())).collect(Collectors.toList()));
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
				return ((InternalEList<?>)getFeatureInfo()).basicRemove(otherEnd, msgs);
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
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_CODEC_INFO:
				return getReferenceCodecInfo();
			case CodecInfoPackage.ECLASS_CODEC_INFO__ATTRIBUTE_CODEC_INFO:
				return getAttributeCodecInfo();
			case CodecInfoPackage.ECLASS_CODEC_INFO__OPERATION_CODEC_INFO:
				return getOperationCodecInfo();
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
				getFeatureInfo().clear();
				getFeatureInfo().addAll((Collection<? extends FeatureCodecInfo>)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_CODEC_INFO:
				getReferenceCodecInfo().clear();
				getReferenceCodecInfo().addAll((Collection<? extends FeatureCodecInfo>)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__ATTRIBUTE_CODEC_INFO:
				getAttributeCodecInfo().clear();
				getAttributeCodecInfo().addAll((Collection<? extends FeatureCodecInfo>)newValue);
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__OPERATION_CODEC_INFO:
				getOperationCodecInfo().clear();
				getOperationCodecInfo().addAll((Collection<? extends FeatureCodecInfo>)newValue);
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
				getFeatureInfo().clear();
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_CODEC_INFO:
				getReferenceCodecInfo().clear();
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__ATTRIBUTE_CODEC_INFO:
				getAttributeCodecInfo().clear();
				return;
			case CodecInfoPackage.ECLASS_CODEC_INFO__OPERATION_CODEC_INFO:
				getOperationCodecInfo().clear();
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
				return featureInfo != null && !featureInfo.isEmpty();
			case CodecInfoPackage.ECLASS_CODEC_INFO__REFERENCE_CODEC_INFO:
				return !getReferenceCodecInfo().isEmpty();
			case CodecInfoPackage.ECLASS_CODEC_INFO__ATTRIBUTE_CODEC_INFO:
				return !getAttributeCodecInfo().isEmpty();
			case CodecInfoPackage.ECLASS_CODEC_INFO__OPERATION_CODEC_INFO:
				return !getOperationCodecInfo().isEmpty();
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
		result.append(')');
		return result.toString();
	}

} //EClassCodecInfoImpl
