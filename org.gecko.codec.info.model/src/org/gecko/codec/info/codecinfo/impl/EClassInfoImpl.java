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
import org.gecko.codec.info.codecinfo.EClassInfo;
import org.gecko.codec.info.codecinfo.FeatureInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EClass Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassInfoImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassInfoImpl#getClassifier <em>Classifier</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassInfoImpl#getIdentityInfo <em>Identity Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassInfoImpl#getTypeInfo <em>Type Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.EClassInfoImpl#getFeatureInfo <em>Feature Info</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EClassInfoImpl extends MinimalEObjectImpl.Container implements EClassInfo {
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
	protected FeatureInfo identityInfo;

	/**
	 * The cached value of the '{@link #getTypeInfo() <em>Type Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeInfo()
	 * @generated
	 * @ordered
	 */
	protected FeatureInfo typeInfo;

	/**
	 * The cached value of the '{@link #getFeatureInfo() <em>Feature Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureInfo()
	 * @generated
	 * @ordered
	 */
	protected FeatureInfo featureInfo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClassInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.ECLASS_INFO;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_INFO__ID, oldId, id));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodecInfoPackage.ECLASS_INFO__CLASSIFIER, oldClassifier, classifier));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_INFO__CLASSIFIER, oldClassifier, classifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureInfo getIdentityInfo() {
		return identityInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdentityInfo(FeatureInfo newIdentityInfo, NotificationChain msgs) {
		FeatureInfo oldIdentityInfo = identityInfo;
		identityInfo = newIdentityInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO, oldIdentityInfo, newIdentityInfo);
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
	public void setIdentityInfo(FeatureInfo newIdentityInfo) {
		if (newIdentityInfo != identityInfo) {
			NotificationChain msgs = null;
			if (identityInfo != null)
				msgs = ((InternalEObject)identityInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO, null, msgs);
			if (newIdentityInfo != null)
				msgs = ((InternalEObject)newIdentityInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO, null, msgs);
			msgs = basicSetIdentityInfo(newIdentityInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO, newIdentityInfo, newIdentityInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureInfo getTypeInfo() {
		return typeInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeInfo(FeatureInfo newTypeInfo, NotificationChain msgs) {
		FeatureInfo oldTypeInfo = typeInfo;
		typeInfo = newTypeInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_INFO__TYPE_INFO, oldTypeInfo, newTypeInfo);
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
	public void setTypeInfo(FeatureInfo newTypeInfo) {
		if (newTypeInfo != typeInfo) {
			NotificationChain msgs = null;
			if (typeInfo != null)
				msgs = ((InternalEObject)typeInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_INFO__TYPE_INFO, null, msgs);
			if (newTypeInfo != null)
				msgs = ((InternalEObject)newTypeInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_INFO__TYPE_INFO, null, msgs);
			msgs = basicSetTypeInfo(newTypeInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_INFO__TYPE_INFO, newTypeInfo, newTypeInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureInfo getFeatureInfo() {
		return featureInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeatureInfo(FeatureInfo newFeatureInfo, NotificationChain msgs) {
		FeatureInfo oldFeatureInfo = featureInfo;
		featureInfo = newFeatureInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_INFO__FEATURE_INFO, oldFeatureInfo, newFeatureInfo);
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
	public void setFeatureInfo(FeatureInfo newFeatureInfo) {
		if (newFeatureInfo != featureInfo) {
			NotificationChain msgs = null;
			if (featureInfo != null)
				msgs = ((InternalEObject)featureInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_INFO__FEATURE_INFO, null, msgs);
			if (newFeatureInfo != null)
				msgs = ((InternalEObject)newFeatureInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecInfoPackage.ECLASS_INFO__FEATURE_INFO, null, msgs);
			msgs = basicSetFeatureInfo(newFeatureInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.ECLASS_INFO__FEATURE_INFO, newFeatureInfo, newFeatureInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO:
				return basicSetIdentityInfo(null, msgs);
			case CodecInfoPackage.ECLASS_INFO__TYPE_INFO:
				return basicSetTypeInfo(null, msgs);
			case CodecInfoPackage.ECLASS_INFO__FEATURE_INFO:
				return basicSetFeatureInfo(null, msgs);
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
			case CodecInfoPackage.ECLASS_INFO__ID:
				return getId();
			case CodecInfoPackage.ECLASS_INFO__CLASSIFIER:
				if (resolve) return getClassifier();
				return basicGetClassifier();
			case CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO:
				return getIdentityInfo();
			case CodecInfoPackage.ECLASS_INFO__TYPE_INFO:
				return getTypeInfo();
			case CodecInfoPackage.ECLASS_INFO__FEATURE_INFO:
				return getFeatureInfo();
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
			case CodecInfoPackage.ECLASS_INFO__ID:
				setId((String)newValue);
				return;
			case CodecInfoPackage.ECLASS_INFO__CLASSIFIER:
				setClassifier((EClassifier)newValue);
				return;
			case CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO:
				setIdentityInfo((FeatureInfo)newValue);
				return;
			case CodecInfoPackage.ECLASS_INFO__TYPE_INFO:
				setTypeInfo((FeatureInfo)newValue);
				return;
			case CodecInfoPackage.ECLASS_INFO__FEATURE_INFO:
				setFeatureInfo((FeatureInfo)newValue);
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
			case CodecInfoPackage.ECLASS_INFO__ID:
				setId(ID_EDEFAULT);
				return;
			case CodecInfoPackage.ECLASS_INFO__CLASSIFIER:
				setClassifier((EClassifier)null);
				return;
			case CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO:
				setIdentityInfo((FeatureInfo)null);
				return;
			case CodecInfoPackage.ECLASS_INFO__TYPE_INFO:
				setTypeInfo((FeatureInfo)null);
				return;
			case CodecInfoPackage.ECLASS_INFO__FEATURE_INFO:
				setFeatureInfo((FeatureInfo)null);
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
			case CodecInfoPackage.ECLASS_INFO__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case CodecInfoPackage.ECLASS_INFO__CLASSIFIER:
				return classifier != null;
			case CodecInfoPackage.ECLASS_INFO__IDENTITY_INFO:
				return identityInfo != null;
			case CodecInfoPackage.ECLASS_INFO__TYPE_INFO:
				return typeInfo != null;
			case CodecInfoPackage.ECLASS_INFO__FEATURE_INFO:
				return featureInfo != null;
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

} //EClassInfoImpl
