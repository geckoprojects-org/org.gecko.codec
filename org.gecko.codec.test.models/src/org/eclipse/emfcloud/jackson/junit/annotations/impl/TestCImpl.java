/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage;
import org.eclipse.emfcloud.jackson.junit.annotations.TestC;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test C</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestCImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestCImpl#getManyValues <em>Many Values</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestCImpl#getOneValue <em>One Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TestCImpl extends MinimalEObjectImpl implements TestC {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final int VALUE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected int value = VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getManyValues() <em>Many Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyValues()
	 * @generated
	 * @ordered
	 */
	protected EList<TestC> manyValues;

	/**
	 * The cached value of the '{@link #getOneValue() <em>One Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOneValue()
	 * @generated
	 * @ordered
	 */
	protected TestC oneValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestCImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnnotationsPackage.Literals.TEST_C;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(int newValue) {
		int oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnnotationsPackage.TEST_C__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TestC> getManyValues() {
		if (manyValues == null) {
			manyValues = new EObjectContainmentEList<TestC>(TestC.class, this, AnnotationsPackage.TEST_C__MANY_VALUES);
		}
		return manyValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestC getOneValue() {
		return oneValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOneValue(TestC newOneValue, NotificationChain msgs) {
		TestC oldOneValue = oneValue;
		oneValue = newOneValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AnnotationsPackage.TEST_C__ONE_VALUE, oldOneValue, newOneValue);
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
	public void setOneValue(TestC newOneValue) {
		if (newOneValue != oneValue) {
			NotificationChain msgs = null;
			if (oneValue != null)
				msgs = ((InternalEObject)oneValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AnnotationsPackage.TEST_C__ONE_VALUE, null, msgs);
			if (newOneValue != null)
				msgs = ((InternalEObject)newOneValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AnnotationsPackage.TEST_C__ONE_VALUE, null, msgs);
			msgs = basicSetOneValue(newOneValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnnotationsPackage.TEST_C__ONE_VALUE, newOneValue, newOneValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnnotationsPackage.TEST_C__MANY_VALUES:
				return ((InternalEList<?>)getManyValues()).basicRemove(otherEnd, msgs);
			case AnnotationsPackage.TEST_C__ONE_VALUE:
				return basicSetOneValue(null, msgs);
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
			case AnnotationsPackage.TEST_C__VALUE:
				return getValue();
			case AnnotationsPackage.TEST_C__MANY_VALUES:
				return getManyValues();
			case AnnotationsPackage.TEST_C__ONE_VALUE:
				return getOneValue();
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
			case AnnotationsPackage.TEST_C__VALUE:
				setValue((Integer)newValue);
				return;
			case AnnotationsPackage.TEST_C__MANY_VALUES:
				getManyValues().clear();
				getManyValues().addAll((Collection<? extends TestC>)newValue);
				return;
			case AnnotationsPackage.TEST_C__ONE_VALUE:
				setOneValue((TestC)newValue);
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
			case AnnotationsPackage.TEST_C__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case AnnotationsPackage.TEST_C__MANY_VALUES:
				getManyValues().clear();
				return;
			case AnnotationsPackage.TEST_C__ONE_VALUE:
				setOneValue((TestC)null);
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
			case AnnotationsPackage.TEST_C__VALUE:
				return value != VALUE_EDEFAULT;
			case AnnotationsPackage.TEST_C__MANY_VALUES:
				return manyValues != null && !manyValues.isEmpty();
			case AnnotationsPackage.TEST_C__ONE_VALUE:
				return oneValue != null;
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
		result.append(" (value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //TestCImpl
