/*
 */
package org.eclipse.emfcloud.jackson.junit.generics.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.jackson.junit.generics.Base;
import org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.impl.BaseImpl#getLinkTo <em>Link To</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.impl.BaseImpl#getLinkToMany <em>Link To Many</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.impl.BaseImpl#getContainsOne <em>Contains One</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.impl.BaseImpl#getContainsMany <em>Contains Many</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class BaseImpl<U, T> extends MinimalEObjectImpl implements Base<U, T> {
	/**
	 * The cached value of the '{@link #getLinkTo() <em>Link To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkTo()
	 * @generated
	 * @ordered
	 */
	protected U linkTo;

	/**
	 * The cached value of the '{@link #getLinkToMany() <em>Link To Many</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkToMany()
	 * @generated
	 * @ordered
	 */
	protected EList<U> linkToMany;

	/**
	 * The cached value of the '{@link #getContainsOne() <em>Contains One</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainsOne()
	 * @generated
	 * @ordered
	 */
	protected T containsOne;

	/**
	 * The cached value of the '{@link #getContainsMany() <em>Contains Many</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainsMany()
	 * @generated
	 * @ordered
	 */
	protected EList<T> containsMany;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenericsPackage.Literals.BASE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public U getLinkTo() {
		if (linkTo != null && ((EObject)linkTo).eIsProxy()) {
			InternalEObject oldLinkTo = (InternalEObject)linkTo;
			linkTo = (U)eResolveProxy(oldLinkTo);
			if (linkTo != oldLinkTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GenericsPackage.BASE__LINK_TO, oldLinkTo, linkTo));
			}
		}
		return linkTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public U basicGetLinkTo() {
		return linkTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLinkTo(U newLinkTo) {
		U oldLinkTo = linkTo;
		linkTo = newLinkTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericsPackage.BASE__LINK_TO, oldLinkTo, linkTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<U> getLinkToMany() {
		if (linkToMany == null) {
			linkToMany = new EObjectResolvingEList<U>(EObject.class, this, GenericsPackage.BASE__LINK_TO_MANY);
		}
		return linkToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public T getContainsOne() {
		return containsOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainsOne(T newContainsOne, NotificationChain msgs) {
		T oldContainsOne = containsOne;
		containsOne = newContainsOne;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GenericsPackage.BASE__CONTAINS_ONE, oldContainsOne, newContainsOne);
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
	public void setContainsOne(T newContainsOne) {
		if (newContainsOne != containsOne) {
			NotificationChain msgs = null;
			if (containsOne != null)
				msgs = ((InternalEObject)containsOne).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GenericsPackage.BASE__CONTAINS_ONE, null, msgs);
			if (newContainsOne != null)
				msgs = ((InternalEObject)newContainsOne).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GenericsPackage.BASE__CONTAINS_ONE, null, msgs);
			msgs = basicSetContainsOne(newContainsOne, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericsPackage.BASE__CONTAINS_ONE, newContainsOne, newContainsOne));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<T> getContainsMany() {
		if (containsMany == null) {
			containsMany = new EObjectContainmentEList<T>(EObject.class, this, GenericsPackage.BASE__CONTAINS_MANY);
		}
		return containsMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GenericsPackage.BASE__CONTAINS_ONE:
				return basicSetContainsOne(null, msgs);
			case GenericsPackage.BASE__CONTAINS_MANY:
				return ((InternalEList<?>)getContainsMany()).basicRemove(otherEnd, msgs);
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
			case GenericsPackage.BASE__LINK_TO:
				if (resolve) return getLinkTo();
				return basicGetLinkTo();
			case GenericsPackage.BASE__LINK_TO_MANY:
				return getLinkToMany();
			case GenericsPackage.BASE__CONTAINS_ONE:
				return getContainsOne();
			case GenericsPackage.BASE__CONTAINS_MANY:
				return getContainsMany();
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
			case GenericsPackage.BASE__LINK_TO:
				setLinkTo((U)newValue);
				return;
			case GenericsPackage.BASE__LINK_TO_MANY:
				getLinkToMany().clear();
				getLinkToMany().addAll((Collection<? extends U>)newValue);
				return;
			case GenericsPackage.BASE__CONTAINS_ONE:
				setContainsOne((T)newValue);
				return;
			case GenericsPackage.BASE__CONTAINS_MANY:
				getContainsMany().clear();
				getContainsMany().addAll((Collection<? extends T>)newValue);
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
			case GenericsPackage.BASE__LINK_TO:
				setLinkTo((U)null);
				return;
			case GenericsPackage.BASE__LINK_TO_MANY:
				getLinkToMany().clear();
				return;
			case GenericsPackage.BASE__CONTAINS_ONE:
				setContainsOne((T)null);
				return;
			case GenericsPackage.BASE__CONTAINS_MANY:
				getContainsMany().clear();
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
			case GenericsPackage.BASE__LINK_TO:
				return linkTo != null;
			case GenericsPackage.BASE__LINK_TO_MANY:
				return linkToMany != null && !linkToMany.isEmpty();
			case GenericsPackage.BASE__CONTAINS_ONE:
				return containsOne != null;
			case GenericsPackage.BASE__CONTAINS_MANY:
				return containsMany != null && !containsMany.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BaseImpl
