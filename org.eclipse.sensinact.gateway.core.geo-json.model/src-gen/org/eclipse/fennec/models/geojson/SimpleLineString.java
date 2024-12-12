/*
 */
package org.eclipse.fennec.models.geojson;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Line String</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.SimpleLineString#getCoordinates <em>Coordinates</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getSimpleLineString()
 * @model features="data" 
 *        dataDataType="org.eclipse.fennec.models.geojson.DoubleArray1D" dataMany="true" dataVolatile="true" dataSuppressedGetVisibility="true" dataSuppressedSetVisibility="true" dataSuppressedUnsetVisibility="true"
 *        dataExtendedMetaData="name='coordinates'"
 *        dataAnnotation="http://www.eclipse.org/emf/2002/GenModel get='return new org.eclipse.fennec.models.geojson.util.GenericConvertingList&lt;double[], Coordinates&gt;(getCoordinates(), org.eclipse.fennec.models.geojson.util.GeoJsonHelper::toCoordinates, org.eclipse.fennec.models.geojson.util.GeoJsonHelper::convertCoordinates);'"
 * @generated
 */
@ProviderType
public interface SimpleLineString extends EObject {
	/**
	 * Returns the value of the '<em><b>Coordinates</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.models.geojson.Coordinates}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Coordinates</em>' containment reference list.
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getSimpleLineString_Coordinates()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Coordinates> getCoordinates();

} // SimpleLineString
