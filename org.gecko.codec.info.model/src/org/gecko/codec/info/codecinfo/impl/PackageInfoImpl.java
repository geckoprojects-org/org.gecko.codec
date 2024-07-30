/*
 */
package org.gecko.codec.info.codecinfo.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.EClassInfo;
import org.gecko.codec.info.codecinfo.PackageInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.PackageInfoImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.PackageInfoImpl#getEPackage <em>EPackage</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.PackageInfoImpl#getSubPackageInfo <em>Sub Package Info</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.PackageInfoImpl#getEClassInfo <em>EClass Info</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PackageInfoImpl extends MinimalEObjectImpl.Container implements PackageInfo {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEPackage() <em>EPackage</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEPackage()
	 * @generated
	 * @ordered
	 */
	protected EPackage ePackage;

	/**
	 * The cached value of the '{@link #getSubPackageInfo() <em>Sub Package Info</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubPackageInfo()
	 * @generated
	 * @ordered
	 */
	protected EList<PackageInfo> subPackageInfo;

	/**
	 * The cached value of the '{@link #getEClassInfo() <em>EClass Info</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEClassInfo()
	 * @generated
	 * @ordered
	 */
	protected EList<EClassInfo> eClassInfo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PackageInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.PACKAGE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.PACKAGE_INFO__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EPackage getEPackage() {
		if (ePackage != null && ePackage.eIsProxy()) {
			InternalEObject oldEPackage = (InternalEObject)ePackage;
			ePackage = (EPackage)eResolveProxy(oldEPackage);
			if (ePackage != oldEPackage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodecInfoPackage.PACKAGE_INFO__EPACKAGE, oldEPackage, ePackage));
			}
		}
		return ePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetEPackage() {
		return ePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEPackage(EPackage newEPackage) {
		EPackage oldEPackage = ePackage;
		ePackage = newEPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.PACKAGE_INFO__EPACKAGE, oldEPackage, ePackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PackageInfo> getSubPackageInfo() {
		if (subPackageInfo == null) {
			subPackageInfo = new EObjectContainmentEList<PackageInfo>(PackageInfo.class, this, CodecInfoPackage.PACKAGE_INFO__SUB_PACKAGE_INFO);
		}
		return subPackageInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EClassInfo> getEClassInfo() {
		if (eClassInfo == null) {
			eClassInfo = new EObjectContainmentEList<EClassInfo>(EClassInfo.class, this, CodecInfoPackage.PACKAGE_INFO__ECLASS_INFO);
		}
		return eClassInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodecInfoPackage.PACKAGE_INFO__SUB_PACKAGE_INFO:
				return ((InternalEList<?>)getSubPackageInfo()).basicRemove(otherEnd, msgs);
			case CodecInfoPackage.PACKAGE_INFO__ECLASS_INFO:
				return ((InternalEList<?>)getEClassInfo()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.PACKAGE_INFO__ID:
				return getId();
			case CodecInfoPackage.PACKAGE_INFO__EPACKAGE:
				if (resolve) return getEPackage();
				return basicGetEPackage();
			case CodecInfoPackage.PACKAGE_INFO__SUB_PACKAGE_INFO:
				return getSubPackageInfo();
			case CodecInfoPackage.PACKAGE_INFO__ECLASS_INFO:
				return getEClassInfo();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CodecInfoPackage.PACKAGE_INFO__ID:
				setId((String)newValue);
				return;
			case CodecInfoPackage.PACKAGE_INFO__EPACKAGE:
				setEPackage((EPackage)newValue);
				return;
			case CodecInfoPackage.PACKAGE_INFO__SUB_PACKAGE_INFO:
				getSubPackageInfo().clear();
				getSubPackageInfo().addAll((Collection<? extends PackageInfo>)newValue);
				return;
			case CodecInfoPackage.PACKAGE_INFO__ECLASS_INFO:
				getEClassInfo().clear();
				getEClassInfo().addAll((Collection<? extends EClassInfo>)newValue);
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
			case CodecInfoPackage.PACKAGE_INFO__ID:
				setId(ID_EDEFAULT);
				return;
			case CodecInfoPackage.PACKAGE_INFO__EPACKAGE:
				setEPackage((EPackage)null);
				return;
			case CodecInfoPackage.PACKAGE_INFO__SUB_PACKAGE_INFO:
				getSubPackageInfo().clear();
				return;
			case CodecInfoPackage.PACKAGE_INFO__ECLASS_INFO:
				getEClassInfo().clear();
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
			case CodecInfoPackage.PACKAGE_INFO__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case CodecInfoPackage.PACKAGE_INFO__EPACKAGE:
				return ePackage != null;
			case CodecInfoPackage.PACKAGE_INFO__SUB_PACKAGE_INFO:
				return subPackageInfo != null && !subPackageInfo.isEmpty();
			case CodecInfoPackage.PACKAGE_INFO__ECLASS_INFO:
				return eClassInfo != null && !eClassInfo.isEmpty();
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
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //PackageInfoImpl
