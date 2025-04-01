/*
 */
package org.eclipse.emfcloud.jackson.junit.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.emfcloud.jackson.junit.model.ModelPackage;
import org.eclipse.emfcloud.jackson.junit.model.PrimaryObject;
import org.eclipse.emfcloud.jackson.junit.model.TargetObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Target Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl#getSingleAttribute <em>Single Attribute</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl#getArrayAttribute <em>Array Attribute</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl#getSingleReference <em>Single Reference</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl#getManyReferences <em>Many References</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.TargetObjectImpl#getManyReferencesNotResolveProxies <em>Many References Not Resolve Proxies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TargetObjectImpl extends MinimalEObjectImpl implements TargetObject {
	/**
	 * The default value of the '{@link #getSingleAttribute() <em>Single Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final String SINGLE_ATTRIBUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSingleAttribute() <em>Single Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleAttribute()
	 * @generated
	 * @ordered
	 */
	protected String singleAttribute = SINGLE_ATTRIBUTE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArrayAttribute() <em>Array Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrayAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<String> arrayAttribute;

	/**
	 * The cached value of the '{@link #getSingleReference() <em>Single Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleReference()
	 * @generated
	 * @ordered
	 */
	protected PrimaryObject singleReference;

	/**
	 * The cached value of the '{@link #getManyReferences() <em>Many References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<PrimaryObject> manyReferences;

	/**
	 * The cached value of the '{@link #getManyReferencesNotResolveProxies() <em>Many References Not Resolve Proxies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyReferencesNotResolveProxies()
	 * @generated
	 * @ordered
	 */
	protected EList<PrimaryObject> manyReferencesNotResolveProxies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TargetObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TARGET_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSingleAttribute() {
		return singleAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSingleAttribute(String newSingleAttribute) {
		String oldSingleAttribute = singleAttribute;
		singleAttribute = newSingleAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TARGET_OBJECT__SINGLE_ATTRIBUTE, oldSingleAttribute, singleAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getArrayAttribute() {
		if (arrayAttribute == null) {
			arrayAttribute = new EDataTypeUniqueEList<String>(String.class, this, ModelPackage.TARGET_OBJECT__ARRAY_ATTRIBUTE);
		}
		return arrayAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PrimaryObject getSingleReference() {
		if (singleReference != null && singleReference.eIsProxy()) {
			InternalEObject oldSingleReference = (InternalEObject)singleReference;
			singleReference = (PrimaryObject)eResolveProxy(oldSingleReference);
			if (singleReference != oldSingleReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE, oldSingleReference, singleReference));
			}
		}
		return singleReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrimaryObject basicGetSingleReference() {
		return singleReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleReference(PrimaryObject newSingleReference, NotificationChain msgs) {
		PrimaryObject oldSingleReference = singleReference;
		singleReference = newSingleReference;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE, oldSingleReference, newSingleReference);
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
	public void setSingleReference(PrimaryObject newSingleReference) {
		if (newSingleReference != singleReference) {
			NotificationChain msgs = null;
			if (singleReference != null)
				msgs = ((InternalEObject)singleReference).eInverseRemove(this, ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES, PrimaryObject.class, msgs);
			if (newSingleReference != null)
				msgs = ((InternalEObject)newSingleReference).eInverseAdd(this, ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES, PrimaryObject.class, msgs);
			msgs = basicSetSingleReference(newSingleReference, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE, newSingleReference, newSingleReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PrimaryObject> getManyReferences() {
		if (manyReferences == null) {
			manyReferences = new EObjectResolvingEList<PrimaryObject>(PrimaryObject.class, this, ModelPackage.TARGET_OBJECT__MANY_REFERENCES);
		}
		return manyReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PrimaryObject getSingleReferenceNotResolveProxies() {
		if (eContainerFeatureID() != ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES) return null;
		return (PrimaryObject)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleReferenceNotResolveProxies(PrimaryObject newSingleReferenceNotResolveProxies, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSingleReferenceNotResolveProxies, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSingleReferenceNotResolveProxies(PrimaryObject newSingleReferenceNotResolveProxies) {
		if (newSingleReferenceNotResolveProxies != eInternalContainer() || (eContainerFeatureID() != ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES && newSingleReferenceNotResolveProxies != null)) {
			if (EcoreUtil.isAncestor(this, newSingleReferenceNotResolveProxies))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSingleReferenceNotResolveProxies != null)
				msgs = ((InternalEObject)newSingleReferenceNotResolveProxies).eInverseAdd(this, ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES, PrimaryObject.class, msgs);
			msgs = basicSetSingleReferenceNotResolveProxies(newSingleReferenceNotResolveProxies, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES, newSingleReferenceNotResolveProxies, newSingleReferenceNotResolveProxies));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PrimaryObject> getManyReferencesNotResolveProxies() {
		if (manyReferencesNotResolveProxies == null) {
			manyReferencesNotResolveProxies = new EObjectEList<PrimaryObject>(PrimaryObject.class, this, ModelPackage.TARGET_OBJECT__MANY_REFERENCES_NOT_RESOLVE_PROXIES);
		}
		return manyReferencesNotResolveProxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE:
				if (singleReference != null)
					msgs = ((InternalEObject)singleReference).eInverseRemove(this, ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES, PrimaryObject.class, msgs);
				return basicSetSingleReference((PrimaryObject)otherEnd, msgs);
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSingleReferenceNotResolveProxies((PrimaryObject)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE:
				return basicSetSingleReference(null, msgs);
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				return basicSetSingleReferenceNotResolveProxies(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				return eInternalContainer().eInverseRemove(this, ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES, PrimaryObject.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.TARGET_OBJECT__SINGLE_ATTRIBUTE:
				return getSingleAttribute();
			case ModelPackage.TARGET_OBJECT__ARRAY_ATTRIBUTE:
				return getArrayAttribute();
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE:
				if (resolve) return getSingleReference();
				return basicGetSingleReference();
			case ModelPackage.TARGET_OBJECT__MANY_REFERENCES:
				return getManyReferences();
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				return getSingleReferenceNotResolveProxies();
			case ModelPackage.TARGET_OBJECT__MANY_REFERENCES_NOT_RESOLVE_PROXIES:
				return getManyReferencesNotResolveProxies();
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
			case ModelPackage.TARGET_OBJECT__SINGLE_ATTRIBUTE:
				setSingleAttribute((String)newValue);
				return;
			case ModelPackage.TARGET_OBJECT__ARRAY_ATTRIBUTE:
				getArrayAttribute().clear();
				getArrayAttribute().addAll((Collection<? extends String>)newValue);
				return;
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE:
				setSingleReference((PrimaryObject)newValue);
				return;
			case ModelPackage.TARGET_OBJECT__MANY_REFERENCES:
				getManyReferences().clear();
				getManyReferences().addAll((Collection<? extends PrimaryObject>)newValue);
				return;
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				setSingleReferenceNotResolveProxies((PrimaryObject)newValue);
				return;
			case ModelPackage.TARGET_OBJECT__MANY_REFERENCES_NOT_RESOLVE_PROXIES:
				getManyReferencesNotResolveProxies().clear();
				getManyReferencesNotResolveProxies().addAll((Collection<? extends PrimaryObject>)newValue);
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
			case ModelPackage.TARGET_OBJECT__SINGLE_ATTRIBUTE:
				setSingleAttribute(SINGLE_ATTRIBUTE_EDEFAULT);
				return;
			case ModelPackage.TARGET_OBJECT__ARRAY_ATTRIBUTE:
				getArrayAttribute().clear();
				return;
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE:
				setSingleReference((PrimaryObject)null);
				return;
			case ModelPackage.TARGET_OBJECT__MANY_REFERENCES:
				getManyReferences().clear();
				return;
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				setSingleReferenceNotResolveProxies((PrimaryObject)null);
				return;
			case ModelPackage.TARGET_OBJECT__MANY_REFERENCES_NOT_RESOLVE_PROXIES:
				getManyReferencesNotResolveProxies().clear();
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
			case ModelPackage.TARGET_OBJECT__SINGLE_ATTRIBUTE:
				return SINGLE_ATTRIBUTE_EDEFAULT == null ? singleAttribute != null : !SINGLE_ATTRIBUTE_EDEFAULT.equals(singleAttribute);
			case ModelPackage.TARGET_OBJECT__ARRAY_ATTRIBUTE:
				return arrayAttribute != null && !arrayAttribute.isEmpty();
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE:
				return singleReference != null;
			case ModelPackage.TARGET_OBJECT__MANY_REFERENCES:
				return manyReferences != null && !manyReferences.isEmpty();
			case ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				return getSingleReferenceNotResolveProxies() != null;
			case ModelPackage.TARGET_OBJECT__MANY_REFERENCES_NOT_RESOLVE_PROXIES:
				return manyReferencesNotResolveProxies != null && !manyReferencesNotResolveProxies.isEmpty();
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
		result.append(" (singleAttribute: ");
		result.append(singleAttribute);
		result.append(", arrayAttribute: ");
		result.append(arrayAttribute);
		result.append(')');
		return result.toString();
	}

} //TargetObjectImpl
