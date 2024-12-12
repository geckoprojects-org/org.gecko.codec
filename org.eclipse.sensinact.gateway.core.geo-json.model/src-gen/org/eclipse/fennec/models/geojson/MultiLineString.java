/*
 */
package org.eclipse.fennec.models.geojson;

import org.eclipse.emf.common.util.EList;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Line String</b></em>'.
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
 *   <li>{@link org.eclipse.fennec.models.geojson.MultiLineString#getLinesStrings <em>Lines Strings</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getMultiLineString()
 * @model features="data" 
 *        dataDataType="org.eclipse.fennec.models.geojson.DoubleArray3D" dataVolatile="true" dataSuppressedGetVisibility="true" dataSuppressedSetVisibility="true" dataSuppressedUnsetVisibility="true"
 *        dataExtendedMetaData="name='coordinates'"
 *        dataAnnotation="http://www.eclipse.org/emf/2002/GenModel get='return org.eclipse.fennec.models.geojson.util.GeoJsonHelper.getMultiLineStringData(this);'"
 * @generated
 */
@ProviderType
public interface MultiLineString extends Geometry {
	/**
	 * Returns the value of the '<em><b>Lines Strings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.models.geojson.SimpleLineString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lines Strings</em>' containment reference list.
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getMultiLineString_LinesStrings()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<SimpleLineString> getLinesStrings();

} // MultiLineString
