/*
 */
package org.eclipse.fennec.openapi.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fennec.openapi.model.OpenApiPackage;
import org.eclipse.fennec.openapi.model.Post;
import org.eclipse.fennec.openapi.model.RequestBody;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Post</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.PostImpl#getRequestBody <em>Request Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PostImpl extends MethodImpl implements Post {
	/**
	 * The cached value of the '{@link #getRequestBody() <em>Request Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestBody()
	 * @generated
	 * @ordered
	 */
	protected RequestBody requestBody;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PostImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpenApiPackage.Literals.POST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RequestBody getRequestBody() {
		return requestBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequestBody(RequestBody newRequestBody, NotificationChain msgs) {
		RequestBody oldRequestBody = requestBody;
		requestBody = newRequestBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.POST__REQUEST_BODY, oldRequestBody, newRequestBody);
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
	public void setRequestBody(RequestBody newRequestBody) {
		if (newRequestBody != requestBody) {
			NotificationChain msgs = null;
			if (requestBody != null)
				msgs = ((InternalEObject)requestBody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.POST__REQUEST_BODY, null, msgs);
			if (newRequestBody != null)
				msgs = ((InternalEObject)newRequestBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.POST__REQUEST_BODY, null, msgs);
			msgs = basicSetRequestBody(newRequestBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.POST__REQUEST_BODY, newRequestBody, newRequestBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OpenApiPackage.POST__REQUEST_BODY:
				return basicSetRequestBody(null, msgs);
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
			case OpenApiPackage.POST__REQUEST_BODY:
				return getRequestBody();
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
			case OpenApiPackage.POST__REQUEST_BODY:
				setRequestBody((RequestBody)newValue);
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
			case OpenApiPackage.POST__REQUEST_BODY:
				setRequestBody((RequestBody)null);
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
			case OpenApiPackage.POST__REQUEST_BODY:
				return requestBody != null;
		}
		return super.eIsSet(featureID);
	}

} //PostImpl
