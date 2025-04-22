/*
 */
package org.eclipse.emfcloud.jackson.junit.array.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emfcloud.jackson.junit.array.ArrayHost;
import org.eclipse.emfcloud.jackson.junit.array.ArrayPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Host</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl#getD1 <em>D1</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl#getD2 <em>D2</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl#getD3 <em>D3</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl#getS2 <em>S2</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.impl.ArrayHostImpl#getB <em>B</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArrayHostImpl extends MinimalEObjectImpl implements ArrayHost {
	/**
	 * The default value of the '{@link #getD1() <em>D1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getD1()
	 * @generated
	 * @ordered
	 */
	protected static final Double[] D1_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getD1() <em>D1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getD1()
	 * @generated
	 * @ordered
	 */
	protected Double[] d1 = D1_EDEFAULT;

	/**
	 * The default value of the '{@link #getD2() <em>D2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getD2()
	 * @generated
	 * @ordered
	 */
	protected static final Double[][] D2_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getD2() <em>D2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getD2()
	 * @generated
	 * @ordered
	 */
	protected Double[][] d2 = D2_EDEFAULT;

	/**
	 * The default value of the '{@link #getD3() <em>D3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getD3()
	 * @generated
	 * @ordered
	 */
	protected static final Double[][][] D3_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getD3() <em>D3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getD3()
	 * @generated
	 * @ordered
	 */
	protected Double[][][] d3 = D3_EDEFAULT;

	/**
	 * The default value of the '{@link #getS2() <em>S2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getS2()
	 * @generated
	 * @ordered
	 */
	protected static final String[][] S2_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getS2() <em>S2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getS2()
	 * @generated
	 * @ordered
	 */
	protected String[][] s2 = S2_EDEFAULT;

	/**
	 * The default value of the '{@link #getB() <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB()
	 * @generated
	 * @ordered
	 */
	protected static final byte[] B_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getB() <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB()
	 * @generated
	 * @ordered
	 */
	protected byte[] b = B_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArrayHostImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArrayPackage.Literals.ARRAY_HOST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double[] getD1() {
		return d1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setD1(Double[] newD1) {
		Double[] oldD1 = d1;
		d1 = newD1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArrayPackage.ARRAY_HOST__D1, oldD1, d1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double[][] getD2() {
		return d2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setD2(Double[][] newD2) {
		Double[][] oldD2 = d2;
		d2 = newD2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArrayPackage.ARRAY_HOST__D2, oldD2, d2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double[][][] getD3() {
		return d3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setD3(Double[][][] newD3) {
		Double[][][] oldD3 = d3;
		d3 = newD3;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArrayPackage.ARRAY_HOST__D3, oldD3, d3));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String[][] getS2() {
		return s2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setS2(String[][] newS2) {
		String[][] oldS2 = s2;
		s2 = newS2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArrayPackage.ARRAY_HOST__S2, oldS2, s2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public byte[] getB() {
		return b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setB(byte[] newB) {
		byte[] oldB = b;
		b = newB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArrayPackage.ARRAY_HOST__B, oldB, b));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArrayPackage.ARRAY_HOST__D1:
				return getD1();
			case ArrayPackage.ARRAY_HOST__D2:
				return getD2();
			case ArrayPackage.ARRAY_HOST__D3:
				return getD3();
			case ArrayPackage.ARRAY_HOST__S2:
				return getS2();
			case ArrayPackage.ARRAY_HOST__B:
				return getB();
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
			case ArrayPackage.ARRAY_HOST__D1:
				setD1((Double[])newValue);
				return;
			case ArrayPackage.ARRAY_HOST__D2:
				setD2((Double[][])newValue);
				return;
			case ArrayPackage.ARRAY_HOST__D3:
				setD3((Double[][][])newValue);
				return;
			case ArrayPackage.ARRAY_HOST__S2:
				setS2((String[][])newValue);
				return;
			case ArrayPackage.ARRAY_HOST__B:
				setB((byte[])newValue);
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
			case ArrayPackage.ARRAY_HOST__D1:
				setD1(D1_EDEFAULT);
				return;
			case ArrayPackage.ARRAY_HOST__D2:
				setD2(D2_EDEFAULT);
				return;
			case ArrayPackage.ARRAY_HOST__D3:
				setD3(D3_EDEFAULT);
				return;
			case ArrayPackage.ARRAY_HOST__S2:
				setS2(S2_EDEFAULT);
				return;
			case ArrayPackage.ARRAY_HOST__B:
				setB(B_EDEFAULT);
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
			case ArrayPackage.ARRAY_HOST__D1:
				return D1_EDEFAULT == null ? d1 != null : !D1_EDEFAULT.equals(d1);
			case ArrayPackage.ARRAY_HOST__D2:
				return D2_EDEFAULT == null ? d2 != null : !D2_EDEFAULT.equals(d2);
			case ArrayPackage.ARRAY_HOST__D3:
				return D3_EDEFAULT == null ? d3 != null : !D3_EDEFAULT.equals(d3);
			case ArrayPackage.ARRAY_HOST__S2:
				return S2_EDEFAULT == null ? s2 != null : !S2_EDEFAULT.equals(s2);
			case ArrayPackage.ARRAY_HOST__B:
				return B_EDEFAULT == null ? b != null : !B_EDEFAULT.equals(b);
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
		result.append(" (d1: ");
		result.append(d1);
		result.append(", d2: ");
		result.append(d2);
		result.append(", d3: ");
		result.append(d3);
		result.append(", s2: ");
		result.append(s2);
		result.append(", b: ");
		result.append(b);
		result.append(')');
		return result.toString();
	}

} //ArrayHostImpl
