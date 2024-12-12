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
import org.eclipse.fennec.models.geojson.LineString;
import org.eclipse.fennec.models.geojson.SimpleLineString;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Line String</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.LineStringImpl#getData <em>Data</em>}</li>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.LineStringImpl#getCoordinates <em>Coordinates</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LineStringImpl extends GeoJsonObjectImpl implements LineString {
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
	public LineStringImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeoJsonPackage.Literals.LINE_STRING;
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
			coordinates = new EObjectContainmentEList<Coordinates>(Coordinates.class, this, GeoJsonPackage.LINE_STRING__COORDINATES);
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
			case GeoJsonPackage.LINE_STRING__COORDINATES:
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
			case GeoJsonPackage.LINE_STRING__DATA:
				return getData();
			case GeoJsonPackage.LINE_STRING__COORDINATES:
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
			case GeoJsonPackage.LINE_STRING__DATA:
				getData().clear();
				getData().addAll((Collection<? extends double[]>)newValue);
				return;
			case GeoJsonPackage.LINE_STRING__COORDINATES:
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
			case GeoJsonPackage.LINE_STRING__DATA:
				getData().clear();
				return;
			case GeoJsonPackage.LINE_STRING__COORDINATES:
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
			case GeoJsonPackage.LINE_STRING__DATA:
				return !getData().isEmpty();
			case GeoJsonPackage.LINE_STRING__COORDINATES:
				return coordinates != null && !coordinates.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == SimpleLineString.class) {
			switch (derivedFeatureID) {
				case GeoJsonPackage.LINE_STRING__DATA: return GeoJsonPackage.SIMPLE_LINE_STRING__DATA;
				case GeoJsonPackage.LINE_STRING__COORDINATES: return GeoJsonPackage.SIMPLE_LINE_STRING__COORDINATES;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == SimpleLineString.class) {
			switch (baseFeatureID) {
				case GeoJsonPackage.SIMPLE_LINE_STRING__DATA: return GeoJsonPackage.LINE_STRING__DATA;
				case GeoJsonPackage.SIMPLE_LINE_STRING__COORDINATES: return GeoJsonPackage.LINE_STRING__COORDINATES;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //LineStringImpl
