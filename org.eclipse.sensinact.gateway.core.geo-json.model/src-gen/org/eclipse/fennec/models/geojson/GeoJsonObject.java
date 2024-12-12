/*
 */
package org.eclipse.fennec.models.geojson;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.GeoJsonObject#getBoundingBox <em>Bounding Box</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getGeoJsonObject()
 * @model features="bbox" 
 *        bboxDataType="org.eclipse.fennec.models.geojson.DoubleArray1D" bboxMany="true" bboxVolatile="true" bboxSuppressedGetVisibility="true" bboxSuppressedSetVisibility="true" bboxSuppressedUnsetVisibility="true"
 *        bboxExtendedMetaData="name='bbox'"
 *        bboxAnnotation="http://www.eclipse.org/emf/2002/GenModel get='return new org.eclipse.fennec.models.geojson.util.GenericConvertingList&lt;double[], Coordinates&gt;(getBoundingBox(), org.eclipse.fennec.models.geojson.util.GeoJsonHelper::toCoordinates, org.eclipse.fennec.models.geojson.util.GeoJsonHelper::convertCoordinates);'"
 * @generated
 */
@ProviderType
public interface GeoJsonObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Bounding Box</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.models.geojson.Coordinates}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bounding Box</em>' containment reference list.
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getGeoJsonObject_BoundingBox()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Coordinates> getBoundingBox();

} // GeoJsonObject
