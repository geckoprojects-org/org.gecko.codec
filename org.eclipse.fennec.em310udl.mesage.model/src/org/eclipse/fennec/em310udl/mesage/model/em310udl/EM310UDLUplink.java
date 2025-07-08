/*
 */
package org.eclipse.fennec.em310udl.mesage.model.em310udl;

import org.eclipse.fennec.lorawan.uplink.model.lorawan.UplinkMessage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Uplink</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.em310udl.mesage.model.em310udl.EM310UDLUplink#getObject <em>Object</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.em310udl.mesage.model.em310udl.EM310UDLPackage#getEM310UDLUplink()
 * @model
 * @generated
 */
@ProviderType
public interface EM310UDLUplink extends UplinkMessage {
	/**
	 * Returns the value of the '<em><b>Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object</em>' containment reference.
	 * @see #setObject(DecodedObject)
	 * @see org.eclipse.fennec.em310udl.mesage.model.em310udl.EM310UDLPackage#getEM310UDLUplink_Object()
	 * @model containment="true"
	 * @generated
	 */
	DecodedObject getObject();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.em310udl.mesage.model.em310udl.EM310UDLUplink#getObject <em>Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object</em>' containment reference.
	 * @see #getObject()
	 * @generated
	 */
	void setObject(DecodedObject value);

} // EM310UDLUplink
