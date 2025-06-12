/*
 */
package org.eclipse.fennec.jsonld.model.jsonld.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fennec.jsonld.model.jsonld.ContextObject;
import org.eclipse.fennec.jsonld.model.jsonld.ContextValue;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectImpl#getContext <em>Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextObjectImpl extends MinimalEObjectImpl.Container implements ContextObject {
	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, ContextValue> context;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContextObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JsonLDPackage.Literals.CONTEXT_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, ContextValue> getContext() {
		if (context == null) {
			context = new EcoreEMap<String,ContextValue>(JsonLDPackage.Literals.CONTEXT_TERM, ContextTermImpl.class, this, JsonLDPackage.CONTEXT_OBJECT__CONTEXT);
		}
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JsonLDPackage.CONTEXT_OBJECT__CONTEXT:
				return ((InternalEList<?>)getContext()).basicRemove(otherEnd, msgs);
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
			case JsonLDPackage.CONTEXT_OBJECT__CONTEXT:
				if (coreType) return getContext();
				else return getContext().map();
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
			case JsonLDPackage.CONTEXT_OBJECT__CONTEXT:
				((EStructuralFeature.Setting)getContext()).set(newValue);
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
			case JsonLDPackage.CONTEXT_OBJECT__CONTEXT:
				getContext().clear();
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
			case JsonLDPackage.CONTEXT_OBJECT__CONTEXT:
				return context != null && !context.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ContextObjectImpl
