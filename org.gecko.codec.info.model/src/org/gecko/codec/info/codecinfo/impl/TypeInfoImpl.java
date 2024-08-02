/*
 */
package org.gecko.codec.info.codecinfo.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.TypeInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl#isSerializeType <em>Serialize Type</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl#isSerializeSuperTypes <em>Serialize Super Types</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoImpl#isSerializeSuperTypeAsArray <em>Serialize Super Type As Array</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeInfoImpl extends FeatureCodecInfoImpl implements TypeInfo {
	/**
	 * The default value of the '{@link #isSerializeType() <em>Serialize Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_TYPE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isSerializeType() <em>Serialize Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeType()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeType = SERIALIZE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isSerializeSuperTypes() <em>Serialize Super Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeSuperTypes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_SUPER_TYPES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSerializeSuperTypes() <em>Serialize Super Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeSuperTypes()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeSuperTypes = SERIALIZE_SUPER_TYPES_EDEFAULT;

	/**
	 * The default value of the '{@link #isSerializeSuperTypeAsArray() <em>Serialize Super Type As Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeSuperTypeAsArray()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERIALIZE_SUPER_TYPE_AS_ARRAY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isSerializeSuperTypeAsArray() <em>Serialize Super Type As Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSerializeSuperTypeAsArray()
	 * @generated
	 * @ordered
	 */
	protected boolean serializeSuperTypeAsArray = SERIALIZE_SUPER_TYPE_AS_ARRAY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.TYPE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeType() {
		return serializeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeType(boolean newSerializeType) {
		boolean oldSerializeType = serializeType;
		serializeType = newSerializeType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.TYPE_INFO__SERIALIZE_TYPE, oldSerializeType, serializeType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeSuperTypes() {
		return serializeSuperTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeSuperTypes(boolean newSerializeSuperTypes) {
		boolean oldSerializeSuperTypes = serializeSuperTypes;
		serializeSuperTypes = newSerializeSuperTypes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPES, oldSerializeSuperTypes, serializeSuperTypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSerializeSuperTypeAsArray() {
		return serializeSuperTypeAsArray;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSerializeSuperTypeAsArray(boolean newSerializeSuperTypeAsArray) {
		boolean oldSerializeSuperTypeAsArray = serializeSuperTypeAsArray;
		serializeSuperTypeAsArray = newSerializeSuperTypeAsArray;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPE_AS_ARRAY, oldSerializeSuperTypeAsArray, serializeSuperTypeAsArray));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_TYPE:
				return isSerializeType();
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPES:
				return isSerializeSuperTypes();
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPE_AS_ARRAY:
				return isSerializeSuperTypeAsArray();
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
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_TYPE:
				setSerializeType((Boolean)newValue);
				return;
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPES:
				setSerializeSuperTypes((Boolean)newValue);
				return;
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPE_AS_ARRAY:
				setSerializeSuperTypeAsArray((Boolean)newValue);
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
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_TYPE:
				setSerializeType(SERIALIZE_TYPE_EDEFAULT);
				return;
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPES:
				setSerializeSuperTypes(SERIALIZE_SUPER_TYPES_EDEFAULT);
				return;
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPE_AS_ARRAY:
				setSerializeSuperTypeAsArray(SERIALIZE_SUPER_TYPE_AS_ARRAY_EDEFAULT);
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
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_TYPE:
				return serializeType != SERIALIZE_TYPE_EDEFAULT;
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPES:
				return serializeSuperTypes != SERIALIZE_SUPER_TYPES_EDEFAULT;
			case CodecInfoPackage.TYPE_INFO__SERIALIZE_SUPER_TYPE_AS_ARRAY:
				return serializeSuperTypeAsArray != SERIALIZE_SUPER_TYPE_AS_ARRAY_EDEFAULT;
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
		result.append(" (serializeType: ");
		result.append(serializeType);
		result.append(", serializeSuperTypes: ");
		result.append(serializeSuperTypes);
		result.append(", serializeSuperTypeAsArray: ");
		result.append(serializeSuperTypeAsArray);
		result.append(')');
		return result.toString();
	}

} //TypeInfoImpl
