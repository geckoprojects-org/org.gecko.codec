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

import org.eclipse.fennec.models.geojson.Coordinates;
import org.eclipse.fennec.models.geojson.GeoJsonPackage;
import org.eclipse.fennec.models.geojson.MultiPoint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.MultiPointImpl#getData <em>Data</em>}</li>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.MultiPointImpl#getCoordinates <em>Coordinates</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiPointImpl extends GeoJsonObjectImpl implements MultiPoint {
	/**
	 * The cached value of the '{@link #getCoordinates() <em>Coordinates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoordinates()
	 * @generated
	 * @ordered
	 */
	protected EList<Coordinates> coordinates;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultiPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeoJsonPackage.Literals.MULTI_POINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<double[]> getData() {
		return new org.eclipse.fennec.models.geojson.util.GenericConvertingList<double[], Coordinates>(getCoordinates(), org.eclipse.fennec.models.geojson.util.GeoJsonHelper::toCoordinates, org.eclipse.fennec.models.geojson.util.GeoJsonHelper::convertCoordinates);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Coordinates> getCoordinates() {
		if (coordinates == null) {
			coordinates = new EObjectContainmentEList<Coordinates>(Coordinates.class, this, GeoJsonPackage.MULTI_POINT__COORDINATES);
		}
		return coordinates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GeoJsonPackage.MULTI_POINT__COORDINATES:
				return ((InternalEList<?>)getCoordinates()).basicRemove(otherEnd, msgs);
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
			case GeoJsonPackage.MULTI_POINT__DATA:
				return getData();
			case GeoJsonPackage.MULTI_POINT__COORDINATES:
				return getCoordinates();
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
			case GeoJsonPackage.MULTI_POINT__DATA:
				getData().clear();
				getData().addAll((Collection<? extends double[]>)newValue);
				return;
			case GeoJsonPackage.MULTI_POINT__COORDINATES:
				getCoordinates().clear();
				getCoordinates().addAll((Collection<? extends Coordinates>)newValue);
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
			case GeoJsonPackage.MULTI_POINT__DATA:
				getData().clear();
				return;
			case GeoJsonPackage.MULTI_POINT__COORDINATES:
				getCoordinates().clear();
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
			case GeoJsonPackage.MULTI_POINT__DATA:
				return !getData().isEmpty();
			case GeoJsonPackage.MULTI_POINT__COORDINATES:
				return coordinates != null && !coordinates.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MultiPointImpl
