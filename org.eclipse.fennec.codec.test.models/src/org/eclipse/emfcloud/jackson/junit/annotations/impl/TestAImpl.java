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
import org.eclipse.emfcloud.jackson.junit.annotations.TestA;
import org.eclipse.emfcloud.jackson.junit.annotations.TestC;
import org.eclipse.emfcloud.jackson.junit.annotations.TestD;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test A</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestAImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestAImpl#getChildOfTypeC <em>Child Of Type C</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.impl.TestAImpl#getChildrenOfTypeD <em>Children Of Type D</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TestAImpl extends MinimalEObjectImpl implements TestA {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildOfTypeC() <em>Child Of Type C</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildOfTypeC()
	 * @generated
	 * @ordered
	 */
	protected TestC childOfTypeC;

	/**
	 * The cached value of the '{@link #getChildrenOfTypeD() <em>Children Of Type D</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenOfTypeD()
	 * @generated
	 * @ordered
	 */
	protected EList<TestD> childrenOfTypeD;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestAImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnnotationsPackage.Literals.TEST_A;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnnotationsPackage.TEST_A__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestC getChildOfTypeC() {
		return childOfTypeC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildOfTypeC(TestC newChildOfTypeC, NotificationChain msgs) {
		TestC oldChildOfTypeC = childOfTypeC;
		childOfTypeC = newChildOfTypeC;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C, oldChildOfTypeC, newChildOfTypeC);
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
	public void setChildOfTypeC(TestC newChildOfTypeC) {
		if (newChildOfTypeC != childOfTypeC) {
			NotificationChain msgs = null;
			if (childOfTypeC != null)
				msgs = ((InternalEObject)childOfTypeC).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C, null, msgs);
			if (newChildOfTypeC != null)
				msgs = ((InternalEObject)newChildOfTypeC).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C, null, msgs);
			msgs = basicSetChildOfTypeC(newChildOfTypeC, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C, newChildOfTypeC, newChildOfTypeC));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TestD> getChildrenOfTypeD() {
		if (childrenOfTypeD == null) {
			childrenOfTypeD = new EObjectContainmentEList<TestD>(TestD.class, this, AnnotationsPackage.TEST_A__CHILDREN_OF_TYPE_D);
		}
		return childrenOfTypeD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C:
				return basicSetChildOfTypeC(null, msgs);
			case AnnotationsPackage.TEST_A__CHILDREN_OF_TYPE_D:
				return ((InternalEList<?>)getChildrenOfTypeD()).basicRemove(otherEnd, msgs);
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
			case AnnotationsPackage.TEST_A__VALUE:
				return getValue();
			case AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C:
				return getChildOfTypeC();
			case AnnotationsPackage.TEST_A__CHILDREN_OF_TYPE_D:
				return getChildrenOfTypeD();
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
			case AnnotationsPackage.TEST_A__VALUE:
				setValue((String)newValue);
				return;
			case AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C:
				setChildOfTypeC((TestC)newValue);
				return;
			case AnnotationsPackage.TEST_A__CHILDREN_OF_TYPE_D:
				getChildrenOfTypeD().clear();
				getChildrenOfTypeD().addAll((Collection<? extends TestD>)newValue);
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
			case AnnotationsPackage.TEST_A__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C:
				setChildOfTypeC((TestC)null);
				return;
			case AnnotationsPackage.TEST_A__CHILDREN_OF_TYPE_D:
				getChildrenOfTypeD().clear();
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
			case AnnotationsPackage.TEST_A__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case AnnotationsPackage.TEST_A__CHILD_OF_TYPE_C:
				return childOfTypeC != null;
			case AnnotationsPackage.TEST_A__CHILDREN_OF_TYPE_D:
				return childrenOfTypeD != null && !childrenOfTypeD.isEmpty();
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

} //TestAImpl
