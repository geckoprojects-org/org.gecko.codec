/*
 */
package org.eclipse.fennec.models.geojson;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Polygon</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  A GeoJSON Polygone object as defined in
 * <a href="https://tools.ietf.org/html/rfc7946#section-3.1.6">the GeoJSON specification</a>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.SimplePolygon#getExteriorRing <em>Exterior Ring</em>}</li>
 *   <li>{@link org.eclipse.fennec.models.geojson.SimplePolygon#getInteriorHoles <em>Interior Holes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getSimplePolygon()
 * @model features="data" 
 *        dataDataType="org.eclipse.fennec.models.geojson.DoubleArray3D" dataVolatile="true" dataSuppressedGetVisibility="true" dataSuppressedSetVisibility="true" dataSuppressedUnsetVisibility="true"
 *        dataExtendedMetaData="name='coordinates'"
 *        dataAnnotation="http://www.eclipse.org/emf/2002/GenModel get='return org.eclipse.fennec.models.geojson.util.GeoJsonHelper.getSimplePolygonData(this);'"
 * @generated
 */
@ProviderType
public interface SimplePolygon extends EObject {
	/**
	 * Returns the value of the '<em><b>Exterior Ring</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exterior Ring</em>' containment reference.
	 * @see #setExteriorRing(Ring)
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getSimplePolygon_ExteriorRing()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	Ring getExteriorRing();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.models.geojson.SimplePolygon#getExteriorRing <em>Exterior Ring</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exterior Ring</em>' containment reference.
	 * @see #getExteriorRing()
	 * @generated
	 */
	void setExteriorRing(Ring value);

	/**
	 * Returns the value of the '<em><b>Interior Holes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.models.geojson.Hole}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interior Holes</em>' containment reference list.
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getSimplePolygon_InteriorHoles()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Hole> getInteriorHoles();

} // SimplePolygon
