/*
 */
package org.gecko.codec.demo.model.person;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sensor Book</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.demo.model.person.SensorBook#getSensors <em>Sensors</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.demo.model.person.PersonPackage#getSensorBook()
 * @model
 * @generated
 */
@ProviderType
public interface SensorBook extends EObject {
	/**
	 * Returns the value of the '<em><b>Sensors</b></em>' containment reference list.
	 * The list contents are of type {@link org.gecko.codec.demo.model.person.Sensor}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sensors</em>' containment reference list.
	 * @see org.gecko.codec.demo.model.person.PersonPackage#getSensorBook_Sensors()
	 * @model containment="true"
	 *        annotation="codec.type include='true' strategy='NAME' typeKey='name' dragino='DraginoUplink' em310='EM310Uplink'"
	 * @generated
	 */
	EList<Sensor> getSensors();

} // SensorBook
