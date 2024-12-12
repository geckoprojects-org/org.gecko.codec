/*
 */
package org.eclipse.fennec.models.geojson.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.fennec.models.geojson.GeoJsonPackage;
import org.eclipse.fennec.models.geojson.Hole;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hole</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class HoleImpl extends RingImpl implements Hole {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HoleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeoJsonPackage.Literals.HOLE;
	}

} //HoleImpl
