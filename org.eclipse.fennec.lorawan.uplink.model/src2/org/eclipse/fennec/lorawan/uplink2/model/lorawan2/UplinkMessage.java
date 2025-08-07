/*
 */
package org.eclipse.fennec.lorawan.uplink2.model.lorawan2;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Uplink Message</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getDeduplicationId <em>Deduplication Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getTime <em>Time</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#isAdr <em>Adr</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getDr <em>Dr</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getFCnt <em>FCnt</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getFPort <em>FPort</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#isConfirmed <em>Confirmed</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getData <em>Data</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getDeviceInfo <em>Device Info</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getRxInfo <em>Rx Info</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getTxInfo <em>Tx Info</em>}</li>
 *   <li>{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getObject <em>Object</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface UplinkMessage extends EObject {
	/**
	 * Returns the value of the '<em><b>Deduplication Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deduplication Id</em>' attribute.
	 * @see #setDeduplicationId(String)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_DeduplicationId()
	 * @model
	 * @generated
	 */
	String getDeduplicationId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getDeduplicationId <em>Deduplication Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deduplication Id</em>' attribute.
	 * @see #getDeduplicationId()
	 * @generated
	 */
	void setDeduplicationId(String value);

	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(String)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_Time()
	 * @model
	 * @generated
	 */
	String getTime();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(String value);

	/**
	 * Returns the value of the '<em><b>Adr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adr</em>' attribute.
	 * @see #setAdr(boolean)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_Adr()
	 * @model
	 * @generated
	 */
	boolean isAdr();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#isAdr <em>Adr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adr</em>' attribute.
	 * @see #isAdr()
	 * @generated
	 */
	void setAdr(boolean value);

	/**
	 * Returns the value of the '<em><b>Dr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dr</em>' attribute.
	 * @see #setDr(int)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_Dr()
	 * @model
	 * @generated
	 */
	int getDr();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getDr <em>Dr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dr</em>' attribute.
	 * @see #getDr()
	 * @generated
	 */
	void setDr(int value);

	/**
	 * Returns the value of the '<em><b>FCnt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>FCnt</em>' attribute.
	 * @see #setFCnt(int)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_FCnt()
	 * @model
	 * @generated
	 */
	int getFCnt();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getFCnt <em>FCnt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>FCnt</em>' attribute.
	 * @see #getFCnt()
	 * @generated
	 */
	void setFCnt(int value);

	/**
	 * Returns the value of the '<em><b>FPort</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>FPort</em>' attribute.
	 * @see #setFPort(int)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_FPort()
	 * @model
	 * @generated
	 */
	int getFPort();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getFPort <em>FPort</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>FPort</em>' attribute.
	 * @see #getFPort()
	 * @generated
	 */
	void setFPort(int value);

	/**
	 * Returns the value of the '<em><b>Confirmed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Confirmed</em>' attribute.
	 * @see #setConfirmed(boolean)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_Confirmed()
	 * @model
	 * @generated
	 */
	boolean isConfirmed();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#isConfirmed <em>Confirmed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Confirmed</em>' attribute.
	 * @see #isConfirmed()
	 * @generated
	 */
	void setConfirmed(boolean value);

	/**
	 * Returns the value of the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' attribute.
	 * @see #setData(String)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_Data()
	 * @model
	 * @generated
	 */
	String getData();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getData <em>Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data</em>' attribute.
	 * @see #getData()
	 * @generated
	 */
	void setData(String value);

	/**
	 * Returns the value of the '<em><b>Device Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Device Info</em>' containment reference.
	 * @see #setDeviceInfo(DeviceInfo)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_DeviceInfo()
	 * @model containment="true" required="true"
	 * @generated
	 */
	DeviceInfo getDeviceInfo();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getDeviceInfo <em>Device Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Device Info</em>' containment reference.
	 * @see #getDeviceInfo()
	 * @generated
	 */
	void setDeviceInfo(DeviceInfo value);

	/**
	 * Returns the value of the '<em><b>Rx Info</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.RxInfo}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rx Info</em>' containment reference list.
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_RxInfo()
	 * @model containment="true"
	 * @generated
	 */
	EList<RxInfo> getRxInfo();

	/**
	 * Returns the value of the '<em><b>Tx Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tx Info</em>' containment reference.
	 * @see #setTxInfo(TxInfo)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_TxInfo()
	 * @model containment="true"
	 * @generated
	 */
	TxInfo getTxInfo();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getTxInfo <em>Tx Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tx Info</em>' containment reference.
	 * @see #getTxInfo()
	 * @generated
	 */
	void setTxInfo(TxInfo value);

	/**
	 * Returns the value of the '<em><b>Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object</em>' containment reference.
	 * @see #setObject(DecodedObject)
	 * @see org.eclipse.fennec.lorawan.uplink2.model.lorawan2.Lorawan2Package#getUplinkMessage_Object()
	 * @model containment="true"
	 *        annotation="codec.type typeKey='deviceInfo.deviceProfileName'"
	 * @generated
	 */
	DecodedObject getObject();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.lorawan.uplink2.model.lorawan2.UplinkMessage#getObject <em>Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object</em>' containment reference.
	 * @see #getObject()
	 * @generated
	 */
	void setObject(DecodedObject value);

} // UplinkMessage
