/*
 */
package org.eclipse.fennec.models.geojson;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ring</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Represents a List of Coordinates that follow the right-hand rule and run counterclockwise
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.models.geojson.Ring#getCoordinates <em>Coordinates</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getRing()
 * @model
 * @generated
 */
@ProviderType
public interface Ring extends EObject {
	/**
	 * Returns the value of the '<em><b>Coordinates</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.models.geojson.Coordinates}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Coordinates</em>' containment reference list.
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#getRing_Coordinates()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Coordinates> getCoordinates();

} // Ring
