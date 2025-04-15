/*
 */
package org.eclipse.emfcloud.jackson.junit.generics.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emfcloud.jackson.junit.generics.Any;
import org.eclipse.emfcloud.jackson.junit.generics.BaseOne;
import org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage;
import org.eclipse.emfcloud.jackson.junit.generics.Some;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base One</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BaseOneImpl extends BaseImpl<Some, Any> implements BaseOne {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseOneImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenericsPackage.Literals.BASE_ONE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setLinkTo(Some newLinkTo) {
		super.setLinkTo(newLinkTo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<Some> getLinkToMany() {
		if (linkToMany == null) {
			linkToMany = new EObjectResolvingEList<Some>(Some.class, this, GenericsPackage.BASE_ONE__LINK_TO_MANY);
		}
		return linkToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetContainsOne(Any newContainsOne, NotificationChain msgs) {
		return super.basicSetContainsOne(newContainsOne, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<Any> getContainsMany() {
		if (containsMany == null) {
			containsMany = new EObjectContainmentEList<Any>(Any.class, this, GenericsPackage.BASE_ONE__CONTAINS_MANY);
		}
		return containsMany;
	}

} //BaseOneImpl
