/*
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 * 
 */
package org.eclipse.fennec.openapi.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.openapi.model.Components;
import org.eclipse.fennec.openapi.model.OpenApiPackage;
import org.eclipse.fennec.openapi.model.SecuritySchemes;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Components</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.ComponentsImpl#getSecuritySchemes <em>Security Schemes</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.ComponentsImpl#getSchemas <em>Schemas</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentsImpl extends MinimalEObjectImpl.Container implements Components {
	/**
	 * The cached value of the '{@link #getSecuritySchemes() <em>Security Schemes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecuritySchemes()
	 * @generated
	 * @ordered
	 */
	protected SecuritySchemes securitySchemes;

	/**
	 * The cached value of the '{@link #getSchemas() <em>Schemas</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchemas()
	 * @generated
	 * @ordered
	 */
	protected EPackage schemas;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpenApiPackage.Literals.COMPONENTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SecuritySchemes getSecuritySchemes() {
		return securitySchemes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecuritySchemes(SecuritySchemes newSecuritySchemes, NotificationChain msgs) {
		SecuritySchemes oldSecuritySchemes = securitySchemes;
		securitySchemes = newSecuritySchemes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.COMPONENTS__SECURITY_SCHEMES, oldSecuritySchemes, newSecuritySchemes);
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
	public void setSecuritySchemes(SecuritySchemes newSecuritySchemes) {
		if (newSecuritySchemes != securitySchemes) {
			NotificationChain msgs = null;
			if (securitySchemes != null)
				msgs = ((InternalEObject)securitySchemes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.COMPONENTS__SECURITY_SCHEMES, null, msgs);
			if (newSecuritySchemes != null)
				msgs = ((InternalEObject)newSecuritySchemes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.COMPONENTS__SECURITY_SCHEMES, null, msgs);
			msgs = basicSetSecuritySchemes(newSecuritySchemes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.COMPONENTS__SECURITY_SCHEMES, newSecuritySchemes, newSecuritySchemes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EPackage getSchemas() {
		return schemas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSchemas(EPackage newSchemas, NotificationChain msgs) {
		EPackage oldSchemas = schemas;
		schemas = newSchemas;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.COMPONENTS__SCHEMAS, oldSchemas, newSchemas);
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
	public void setSchemas(EPackage newSchemas) {
		if (newSchemas != schemas) {
			NotificationChain msgs = null;
			if (schemas != null)
				msgs = ((InternalEObject)schemas).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.COMPONENTS__SCHEMAS, null, msgs);
			if (newSchemas != null)
				msgs = ((InternalEObject)newSchemas).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.COMPONENTS__SCHEMAS, null, msgs);
			msgs = basicSetSchemas(newSchemas, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.COMPONENTS__SCHEMAS, newSchemas, newSchemas));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OpenApiPackage.COMPONENTS__SECURITY_SCHEMES:
				return basicSetSecuritySchemes(null, msgs);
			case OpenApiPackage.COMPONENTS__SCHEMAS:
				return basicSetSchemas(null, msgs);
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
			case OpenApiPackage.COMPONENTS__SECURITY_SCHEMES:
				return getSecuritySchemes();
			case OpenApiPackage.COMPONENTS__SCHEMAS:
				return getSchemas();
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
			case OpenApiPackage.COMPONENTS__SECURITY_SCHEMES:
				setSecuritySchemes((SecuritySchemes)newValue);
				return;
			case OpenApiPackage.COMPONENTS__SCHEMAS:
				setSchemas((EPackage)newValue);
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
			case OpenApiPackage.COMPONENTS__SECURITY_SCHEMES:
				setSecuritySchemes((SecuritySchemes)null);
				return;
			case OpenApiPackage.COMPONENTS__SCHEMAS:
				setSchemas((EPackage)null);
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
			case OpenApiPackage.COMPONENTS__SECURITY_SCHEMES:
				return securitySchemes != null;
			case OpenApiPackage.COMPONENTS__SCHEMAS:
				return schemas != null;
		}
		return super.eIsSet(featureID);
	}

} //ComponentsImpl
