/*
 */
package org.eclipse.fennec.openapi.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.openapi.model.BasicAuth;
import org.eclipse.fennec.openapi.model.BearerAuth;
import org.eclipse.fennec.openapi.model.OpenApiPackage;
import org.eclipse.fennec.openapi.model.SecuritySchemes;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Security Schemes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.SecuritySchemesImpl#getBearerAuth <em>Bearer Auth</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.SecuritySchemesImpl#getBasicAuth <em>Basic Auth</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SecuritySchemesImpl extends MinimalEObjectImpl.Container implements SecuritySchemes {
	/**
	 * The cached value of the '{@link #getBearerAuth() <em>Bearer Auth</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBearerAuth()
	 * @generated
	 * @ordered
	 */
	protected BearerAuth bearerAuth;

	/**
	 * The cached value of the '{@link #getBasicAuth() <em>Basic Auth</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicAuth()
	 * @generated
	 * @ordered
	 */
	protected BasicAuth basicAuth;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecuritySchemesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpenApiPackage.Literals.SECURITY_SCHEMES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BearerAuth getBearerAuth() {
		return bearerAuth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBearerAuth(BearerAuth newBearerAuth, NotificationChain msgs) {
		BearerAuth oldBearerAuth = bearerAuth;
		bearerAuth = newBearerAuth;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH, oldBearerAuth, newBearerAuth);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBearerAuth(BearerAuth newBearerAuth) {
		if (newBearerAuth != bearerAuth) {
			NotificationChain msgs = null;
			if (bearerAuth != null)
				msgs = ((InternalEObject)bearerAuth).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH, null, msgs);
			if (newBearerAuth != null)
				msgs = ((InternalEObject)newBearerAuth).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH, null, msgs);
			msgs = basicSetBearerAuth(newBearerAuth, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH, newBearerAuth, newBearerAuth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasicAuth getBasicAuth() {
		return basicAuth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBasicAuth(BasicAuth newBasicAuth, NotificationChain msgs) {
		BasicAuth oldBasicAuth = basicAuth;
		basicAuth = newBasicAuth;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH, oldBasicAuth, newBasicAuth);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBasicAuth(BasicAuth newBasicAuth) {
		if (newBasicAuth != basicAuth) {
			NotificationChain msgs = null;
			if (basicAuth != null)
				msgs = ((InternalEObject)basicAuth).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH, null, msgs);
			if (newBasicAuth != null)
				msgs = ((InternalEObject)newBasicAuth).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH, null, msgs);
			msgs = basicSetBasicAuth(newBasicAuth, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH, newBasicAuth, newBasicAuth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH:
				return basicSetBearerAuth(null, msgs);
			case OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH:
				return basicSetBasicAuth(null, msgs);
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
			case OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH:
				return getBearerAuth();
			case OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH:
				return getBasicAuth();
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
			case OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH:
				setBearerAuth((BearerAuth)newValue);
				return;
			case OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH:
				setBasicAuth((BasicAuth)newValue);
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
			case OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH:
				setBearerAuth((BearerAuth)null);
				return;
			case OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH:
				setBasicAuth((BasicAuth)null);
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
			case OpenApiPackage.SECURITY_SCHEMES__BEARER_AUTH:
				return bearerAuth != null;
			case OpenApiPackage.SECURITY_SCHEMES__BASIC_AUTH:
				return basicAuth != null;
		}
		return super.eIsSet(featureID);
	}

} //SecuritySchemesImpl
