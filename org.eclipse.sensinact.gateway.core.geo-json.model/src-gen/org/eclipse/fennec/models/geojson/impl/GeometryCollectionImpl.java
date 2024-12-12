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
import org.eclipse.fennec.models.geojson.Geometry;
import org.eclipse.fennec.models.geojson.GeometryCollection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Geometry Collection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.GeometryCollectionImpl#getGeometries <em>Geometries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GeometryCollectionImpl extends GeoJsonObjectImpl implements GeometryCollection {
	/**
	 * The cached value of the '{@link #getGeometries() <em>Geometries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeometries()
	 * @generated
	 * @ordered
	 */
	protected EList<Geometry> geometries;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeometryCollectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeoJsonPackage.Literals.GEOMETRY_COLLECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Geometry> getGeometries() {
		if (geometries == null) {
			geometries = new EObjectContainmentEList<Geometry>(Geometry.class, this, GeoJsonPackage.GEOMETRY_COLLECTION__GEOMETRIES);
		}
		return geometries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GeoJsonPackage.GEOMETRY_COLLECTION__GEOMETRIES:
				return ((InternalEList<?>)getGeometries()).basicRemove(otherEnd, msgs);
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
			case GeoJsonPackage.GEOMETRY_COLLECTION__GEOMETRIES:
				return getGeometries();
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
			case GeoJsonPackage.GEOMETRY_COLLECTION__GEOMETRIES:
				getGeometries().clear();
				getGeometries().addAll((Collection<? extends Geometry>)newValue);
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
			case GeoJsonPackage.GEOMETRY_COLLECTION__GEOMETRIES:
				getGeometries().clear();
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
			case GeoJsonPackage.GEOMETRY_COLLECTION__GEOMETRIES:
				return geometries != null && !geometries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GeometryCollectionImpl
