/*
 */
package org.eclipse.fennec.models.geojson.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fennec.models.geojson.GeoJsonPackage;
import org.eclipse.fennec.models.geojson.MultiLineString;
import org.eclipse.fennec.models.geojson.SimpleLineString;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Line String</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.MultiLineStringImpl#getData <em>Data</em>}</li>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.MultiLineStringImpl#getLinesStrings <em>Lines Strings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiLineStringImpl extends GeoJsonObjectImpl implements MultiLineString {
	/**
	 * The default value of the '{@link #getData() <em>Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getData()
	 * @generated
	 * @ordered
	 */
	protected static final double[][][] DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLinesStrings() <em>Lines Strings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinesStrings()
	 * @generated
	 * @ordered
	 */
	protected EList<SimpleLineString> linesStrings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultiLineStringImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeoJsonPackage.Literals.MULTI_LINE_STRING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double[][][] getData() {
		return org.eclipse.fennec.models.geojson.util.GeoJsonHelper.getMultiLineStringData(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setData(double[][][] data) {
		org.eclipse.fennec.models.geojson.util.GeoJsonHelper.setMultiLineStringData(this, data);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SimpleLineString> getLinesStrings() {
		if (linesStrings == null) {
			linesStrings = new EObjectContainmentEList<SimpleLineString>(SimpleLineString.class, this, GeoJsonPackage.MULTI_LINE_STRING__LINES_STRINGS);
		}
		return linesStrings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GeoJsonPackage.MULTI_LINE_STRING__LINES_STRINGS:
				return ((InternalEList<?>)getLinesStrings()).basicRemove(otherEnd, msgs);
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
			case GeoJsonPackage.MULTI_LINE_STRING__DATA:
				return getData();
			case GeoJsonPackage.MULTI_LINE_STRING__LINES_STRINGS:
				return getLinesStrings();
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
			case GeoJsonPackage.MULTI_LINE_STRING__DATA:
				setData((double[][][])newValue);
				return;
			case GeoJsonPackage.MULTI_LINE_STRING__LINES_STRINGS:
				getLinesStrings().clear();
				getLinesStrings().addAll((Collection<? extends SimpleLineString>)newValue);
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
			case GeoJsonPackage.MULTI_LINE_STRING__DATA:
				setData(DATA_EDEFAULT);
				return;
			case GeoJsonPackage.MULTI_LINE_STRING__LINES_STRINGS:
				getLinesStrings().clear();
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
			case GeoJsonPackage.MULTI_LINE_STRING__DATA:
				return DATA_EDEFAULT == null ? getData() != null : !DATA_EDEFAULT.equals(getData());
			case GeoJsonPackage.MULTI_LINE_STRING__LINES_STRINGS:
				return linesStrings != null && !linesStrings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MultiLineStringImpl
