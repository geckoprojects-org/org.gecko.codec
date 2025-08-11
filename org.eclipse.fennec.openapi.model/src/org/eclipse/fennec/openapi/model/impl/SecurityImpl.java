/*
 */
package org.eclipse.fennec.openapi.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.fennec.openapi.model.OpenApiPackage;
import org.eclipse.fennec.openapi.model.Security;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Security</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.SecurityImpl#getBearerAuth <em>Bearer Auth</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.SecurityImpl#getBasicAuth <em>Basic Auth</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.SecurityImpl#getApiKey <em>Api Key</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SecurityImpl extends MinimalEObjectImpl.Container implements Security {
	/**
	 * The cached value of the '{@link #getBearerAuth() <em>Bearer Auth</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBearerAuth()
	 * @generated
	 * @ordered
	 */
	protected EList<String> bearerAuth;

	/**
	 * The cached value of the '{@link #getBasicAuth() <em>Basic Auth</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicAuth()
	 * @generated
	 * @ordered
	 */
	protected EList<String> basicAuth;

	/**
	 * The cached value of the '{@link #getApiKey() <em>Api Key</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApiKey()
	 * @generated
	 * @ordered
	 */
	protected EList<String> apiKey;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecurityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpenApiPackage.Literals.SECURITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getBearerAuth() {
		if (bearerAuth == null) {
			bearerAuth = new EDataTypeUniqueEList<String>(String.class, this, OpenApiPackage.SECURITY__BEARER_AUTH);
		}
		return bearerAuth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getBasicAuth() {
		if (basicAuth == null) {
			basicAuth = new EDataTypeUniqueEList<String>(String.class, this, OpenApiPackage.SECURITY__BASIC_AUTH);
		}
		return basicAuth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getApiKey() {
		if (apiKey == null) {
			apiKey = new EDataTypeUniqueEList<String>(String.class, this, OpenApiPackage.SECURITY__API_KEY);
		}
		return apiKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OpenApiPackage.SECURITY__BEARER_AUTH:
				return getBearerAuth();
			case OpenApiPackage.SECURITY__BASIC_AUTH:
				return getBasicAuth();
			case OpenApiPackage.SECURITY__API_KEY:
				return getApiKey();
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
			case OpenApiPackage.SECURITY__BEARER_AUTH:
				getBearerAuth().clear();
				getBearerAuth().addAll((Collection<? extends String>)newValue);
				return;
			case OpenApiPackage.SECURITY__BASIC_AUTH:
				getBasicAuth().clear();
				getBasicAuth().addAll((Collection<? extends String>)newValue);
				return;
			case OpenApiPackage.SECURITY__API_KEY:
				getApiKey().clear();
				getApiKey().addAll((Collection<? extends String>)newValue);
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
			case OpenApiPackage.SECURITY__BEARER_AUTH:
				getBearerAuth().clear();
				return;
			case OpenApiPackage.SECURITY__BASIC_AUTH:
				getBasicAuth().clear();
				return;
			case OpenApiPackage.SECURITY__API_KEY:
				getApiKey().clear();
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
			case OpenApiPackage.SECURITY__BEARER_AUTH:
				return bearerAuth != null && !bearerAuth.isEmpty();
			case OpenApiPackage.SECURITY__BASIC_AUTH:
				return basicAuth != null && !basicAuth.isEmpty();
			case OpenApiPackage.SECURITY__API_KEY:
				return apiKey != null && !apiKey.isEmpty();
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
		result.append(" (bearerAuth: ");
		result.append(bearerAuth);
		result.append(", basicAuth: ");
		result.append(basicAuth);
		result.append(", apiKey: ");
		result.append(apiKey);
		result.append(')');
		return result.toString();
	}

} //SecurityImpl
