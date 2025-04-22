/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage;
import org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass;
import org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.ContainerImpl#getTypedByNames <em>Typed By Names</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.ContainerImpl#getTypedByClasses <em>Typed By Classes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContainerImpl extends MinimalEObjectImpl implements org.eclipse.emfcloud.jackson.junit.annotations.Container {
	/**
	 * The cached value of the '{@link #getTypedByNames() <em>Typed By Names</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedByNames()
	 * @generated
	 * @ordered
	 */
	protected EList<TestTypeName> typedByNames;

	/**
	 * The cached value of the '{@link #getTypedByClasses() <em>Typed By Classes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedByClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<TestTypeClass> typedByClasses;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnnotationsPackage.Literals.CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TestTypeName> getTypedByNames() {
		if (typedByNames == null) {
			typedByNames = new EObjectContainmentEList<TestTypeName>(TestTypeName.class, this, AnnotationsPackage.CONTAINER__TYPED_BY_NAMES);
		}
		return typedByNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TestTypeClass> getTypedByClasses() {
		if (typedByClasses == null) {
			typedByClasses = new EObjectContainmentEList<TestTypeClass>(TestTypeClass.class, this, AnnotationsPackage.CONTAINER__TYPED_BY_CLASSES);
		}
		return typedByClasses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnnotationsPackage.CONTAINER__TYPED_BY_NAMES:
				return ((InternalEList<?>)getTypedByNames()).basicRemove(otherEnd, msgs);
			case AnnotationsPackage.CONTAINER__TYPED_BY_CLASSES:
				return ((InternalEList<?>)getTypedByClasses()).basicRemove(otherEnd, msgs);
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
			case AnnotationsPackage.CONTAINER__TYPED_BY_NAMES:
				return getTypedByNames();
			case AnnotationsPackage.CONTAINER__TYPED_BY_CLASSES:
				return getTypedByClasses();
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
			case AnnotationsPackage.CONTAINER__TYPED_BY_NAMES:
				getTypedByNames().clear();
				getTypedByNames().addAll((Collection<? extends TestTypeName>)newValue);
				return;
			case AnnotationsPackage.CONTAINER__TYPED_BY_CLASSES:
				getTypedByClasses().clear();
				getTypedByClasses().addAll((Collection<? extends TestTypeClass>)newValue);
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
			case AnnotationsPackage.CONTAINER__TYPED_BY_NAMES:
				getTypedByNames().clear();
				return;
			case AnnotationsPackage.CONTAINER__TYPED_BY_CLASSES:
				getTypedByClasses().clear();
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
			case AnnotationsPackage.CONTAINER__TYPED_BY_NAMES:
				return typedByNames != null && !typedByNames.isEmpty();
			case AnnotationsPackage.CONTAINER__TYPED_BY_CLASSES:
				return typedByClasses != null && !typedByClasses.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ContainerImpl
