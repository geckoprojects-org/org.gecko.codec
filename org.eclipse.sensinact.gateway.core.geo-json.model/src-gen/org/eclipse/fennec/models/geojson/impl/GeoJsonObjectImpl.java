/*
 */
package org.eclipse.fennec.models.geojson.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fennec.models.geojson.Coordinates;
import org.eclipse.fennec.models.geojson.GeoJsonObject;
import org.eclipse.fennec.models.geojson.GeoJsonPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.GeoJsonObjectImpl#getBbox <em>Bbox</em>}</li>
 *   <li>{@link org.eclipse.fennec.models.geojson.impl.GeoJsonObjectImpl#getBoundingBox <em>Bounding Box</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GeoJsonObjectImpl extends MinimalEObjectImpl.Container implements GeoJsonObject {
	/**
	 * The cached value of the '{@link #getBoundingBox() <em>Bounding Box</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBoundingBox()
	 * @generated
	 * @ordered
	 */
	protected EList<Coordinates> boundingBox;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeoJsonObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeoJsonPackage.Literals.GEO_JSON_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<double[]> getBbox() {
		return new org.eclipse.fennec.models.geojson.util.GenericConvertingList<double[], Coordinates>(getBoundingBox(), org.eclipse.fennec.models.geojson.util.GeoJsonHelper::toCoordinates, org.eclipse.fennec.models.geojson.util.GeoJsonHelper::convertCoordinates);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Coordinates> getBoundingBox() {
		if (boundingBox == null) {
			boundingBox = new EObjectContainmentEList<Coordinates>(Coordinates.class, this, GeoJsonPackage.GEO_JSON_OBJECT__BOUNDING_BOX);
		}
		return boundingBox;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GeoJsonPackage.GEO_JSON_OBJECT__BOUNDING_BOX:
				return ((InternalEList<?>)getBoundingBox()).basicRemove(otherEnd, msgs);
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
			case GeoJsonPackage.GEO_JSON_OBJECT__BBOX:
				return getBbox();
			case GeoJsonPackage.GEO_JSON_OBJECT__BOUNDING_BOX:
				return getBoundingBox();
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
			case GeoJsonPackage.GEO_JSON_OBJECT__BBOX:
				getBbox().clear();
				getBbox().addAll((Collection<? extends double[]>)newValue);
				return;
			case GeoJsonPackage.GEO_JSON_OBJECT__BOUNDING_BOX:
				getBoundingBox().clear();
				getBoundingBox().addAll((Collection<? extends Coordinates>)newValue);
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
			case GeoJsonPackage.GEO_JSON_OBJECT__BBOX:
				getBbox().clear();
				return;
			case GeoJsonPackage.GEO_JSON_OBJECT__BOUNDING_BOX:
				getBoundingBox().clear();
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
			case GeoJsonPackage.GEO_JSON_OBJECT__BBOX:
				return !getBbox().isEmpty();
			case GeoJsonPackage.GEO_JSON_OBJECT__BOUNDING_BOX:
				return boundingBox != null && !boundingBox.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GeoJsonObjectImpl
