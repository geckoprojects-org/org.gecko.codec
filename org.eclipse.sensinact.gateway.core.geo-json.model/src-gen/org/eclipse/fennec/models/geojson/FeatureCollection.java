/*
 */
package org.eclipse.fennec.models.geojson;

import org.eclipse.emf.common.util.EList;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.FeatureCollection#getFeatures <em>Features</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getFeatureCollection()
 * @model
 * @generated
 */
@ProviderType
public interface FeatureCollection extends GeoJsonObject {
	/**
	 * Returns the value of the '<em><b>Features</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.models.geojson.Feature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features</em>' containment reference list.
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getFeatureCollection_Features()
	 * @model containment="true"
	 * @generated
	 */
	EList<Feature> getFeatures();

} // FeatureCollection
