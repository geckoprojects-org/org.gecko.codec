/*
 */
package org.eclipse.fennec.models.geojson;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A GeoJSON point object as defined in
 * <a href="https://tools.ietf.org/html/rfc7946#section-3.1">the GeoJSON
 * specification</a>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.Point#getCoordinates <em>Coordinates</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getPoint()
 * @model features="data" 
 *        dataDataType="org.eclipse.fennec.models.geojson.DoubleArray1D" dataVolatile="true" dataSuppressedGetVisibility="true" dataSuppressedSetVisibility="true" dataSuppressedIsSetVisibility="true" dataSuppressedUnsetVisibility="true"
 *        dataExtendedMetaData="name='coordinates'"
 *        dataAnnotation="http://www.eclipse.org/emf/2002/GenModel get='return org.eclipse.fennec.models.geojson.util.GeoJsonHelper.convertCoordinates(getCoordinates());'"
 * @generated
 */
@ProviderType
public interface Point extends Geometry {
	/**
	 * Returns the value of the '<em><b>Coordinates</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Coordinates</em>' containment reference.
	 * @see #setCoordinates(Coordinates)
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getPoint_Coordinates()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	Coordinates getCoordinates();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.models.geojson.Point#getCoordinates <em>Coordinates</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Coordinates</em>' containment reference.
	 * @see #getCoordinates()
	 * @generated
	 */
	void setCoordinates(Coordinates value);

} // Point
