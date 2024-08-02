/*
 */
package org.gecko.codec.info.codecinfo.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.IdentityInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Identity Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl#isUseId <em>Use Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl#isUseIdField <em>Use Id Field</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl#isIdTop <em>Id Top</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl#isSerializeIdField <em>Serialize Id Field</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.IdentityInfoImpl#isIdFeatureAsPrimaryKey <em>Id Feature As Primary Key</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IdentityInfoImpl extends FeatureCodecInfoImpl implements IdentityInfo {
	/**
	 * The default value of the '{@link #isUseId() <em>Use Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseId()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_ID_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUseId() <em>Use Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseId()
	 * @generated
	 * @ordered
	 */
	protected boolean useId = USE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseIdField() <em>Use Id Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseIdField()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_ID_FIELD_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUseIdField() <em>Use Id Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseIdField()
	 * @generated
	 * @ordered
	 */
	protected boolean useIdField = USE_ID_FIELD_EDEFAULT;

	/**
	 * The default value of the '{@link #isIdTop() <em>Id Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIdTop()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ID_TOP_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIdTop() <em>Id Top</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIdTop()
	 * @generated
	 * @ordered
	 */
	protected boolean idTop = ID_TOP_EDEFAULT;

	/**
	 * The default value of the '{@link #isSerializeIdField() <em>Serialize Id Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeIdField()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_ID_FIELD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSerializeIdField() <em>Serialize Id Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeIdField()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeIdField = SERIALIZE_ID_FIELD_EDEFAULT;

	/**
	 * The default value of the '{@link #isIdFeatureAsPrimaryKey() <em>Id Feature As Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIdFeatureAsPrimaryKey()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ID_FEATURE_AS_PRIMARY_KEY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIdFeatureAsPrimaryKey() <em>Id Feature As Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIdFeatureAsPrimaryKey()
	 * @generated
	 * @ordered
	 */
	protected boolean idFeatureAsPrimaryKey = ID_FEATURE_AS_PRIMARY_KEY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IdentityInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.IDENTITY_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUseId() {
		return useId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUseId(boolean newUseId) {
		boolean oldUseId = useId;
		useId = newUseId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.IDENTITY_INFO__USE_ID, oldUseId, useId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUseIdField() {
		return useIdField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUseIdField(boolean newUseIdField) {
		boolean oldUseIdField = useIdField;
		useIdField = newUseIdField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.IDENTITY_INFO__USE_ID_FIELD, oldUseIdField, useIdField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIdTop() {
		return idTop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdTop(boolean newIdTop) {
		boolean oldIdTop = idTop;
		idTop = newIdTop;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.IDENTITY_INFO__ID_TOP, oldIdTop, idTop));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeIdField() {
		return serializeIdField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeIdField(boolean newSerializeIdField) {
		boolean oldSerializeIdField = serializeIdField;
		serializeIdField = newSerializeIdField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.IDENTITY_INFO__SERIALIZE_ID_FIELD, oldSerializeIdField, serializeIdField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIdFeatureAsPrimaryKey() {
		return idFeatureAsPrimaryKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdFeatureAsPrimaryKey(boolean newIdFeatureAsPrimaryKey) {
		boolean oldIdFeatureAsPrimaryKey = idFeatureAsPrimaryKey;
		idFeatureAsPrimaryKey = newIdFeatureAsPrimaryKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.IDENTITY_INFO__ID_FEATURE_AS_PRIMARY_KEY, oldIdFeatureAsPrimaryKey, idFeatureAsPrimaryKey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.IDENTITY_INFO__USE_ID:
				return isUseId();
			case CodecInfoPackage.IDENTITY_INFO__USE_ID_FIELD:
				return isUseIdField();
			case CodecInfoPackage.IDENTITY_INFO__ID_TOP:
				return isIdTop();
			case CodecInfoPackage.IDENTITY_INFO__SERIALIZE_ID_FIELD:
				return isSerializeIdField();
			case CodecInfoPackage.IDENTITY_INFO__ID_FEATURE_AS_PRIMARY_KEY:
				return isIdFeatureAsPrimaryKey();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CodecInfoPackage.IDENTITY_INFO__USE_ID:
				setUseId((Boolean)newValue);
				return;
			case CodecInfoPackage.IDENTITY_INFO__USE_ID_FIELD:
				setUseIdField((Boolean)newValue);
				return;
			case CodecInfoPackage.IDENTITY_INFO__ID_TOP:
				setIdTop((Boolean)newValue);
				return;
			case CodecInfoPackage.IDENTITY_INFO__SERIALIZE_ID_FIELD:
				setSerializeIdField((Boolean)newValue);
				return;
			case CodecInfoPackage.IDENTITY_INFO__ID_FEATURE_AS_PRIMARY_KEY:
				setIdFeatureAsPrimaryKey((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CodecInfoPackage.IDENTITY_INFO__USE_ID:
				setUseId(USE_ID_EDEFAULT);
				return;
			case CodecInfoPackage.IDENTITY_INFO__USE_ID_FIELD:
				setUseIdField(USE_ID_FIELD_EDEFAULT);
				return;
			case CodecInfoPackage.IDENTITY_INFO__ID_TOP:
				setIdTop(ID_TOP_EDEFAULT);
				return;
			case CodecInfoPackage.IDENTITY_INFO__SERIALIZE_ID_FIELD:
				setSerializeIdField(SERIALIZE_ID_FIELD_EDEFAULT);
				return;
			case CodecInfoPackage.IDENTITY_INFO__ID_FEATURE_AS_PRIMARY_KEY:
				setIdFeatureAsPrimaryKey(ID_FEATURE_AS_PRIMARY_KEY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CodecInfoPackage.IDENTITY_INFO__USE_ID:
				return useId != USE_ID_EDEFAULT;
			case CodecInfoPackage.IDENTITY_INFO__USE_ID_FIELD:
				return useIdField != USE_ID_FIELD_EDEFAULT;
			case CodecInfoPackage.IDENTITY_INFO__ID_TOP:
				return idTop != ID_TOP_EDEFAULT;
			case CodecInfoPackage.IDENTITY_INFO__SERIALIZE_ID_FIELD:
				return serializeIdField != SERIALIZE_ID_FIELD_EDEFAULT;
			case CodecInfoPackage.IDENTITY_INFO__ID_FEATURE_AS_PRIMARY_KEY:
				return idFeatureAsPrimaryKey != ID_FEATURE_AS_PRIMARY_KEY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (useId: ");
		result.append(useId);
		result.append(", useIdField: ");
		result.append(useIdField);
		result.append(", idTop: ");
		result.append(idTop);
		result.append(", serializeIdField: ");
		result.append(serializeIdField);
		result.append(", idFeatureAsPrimaryKey: ");
		result.append(idFeatureAsPrimaryKey);
		result.append(')');
		return result.toString();
	}

} //IdentityInfoImpl
