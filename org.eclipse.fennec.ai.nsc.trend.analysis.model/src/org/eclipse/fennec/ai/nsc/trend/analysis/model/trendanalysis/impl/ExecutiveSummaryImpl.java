/*
 * Copyright (c) 2012 - 2025 Data In Motion and others.
 * All rights reserved.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *      Data In Motion - initial API and implementation
 */
package org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Executive Summary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.ExecutiveSummaryImpl#getContext <em>Context</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.ExecutiveSummaryImpl#getScope <em>Scope</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.ExecutiveSummaryImpl#getTimeHorizon <em>Time Horizon</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.ExecutiveSummaryImpl#getKeyFocus <em>Key Focus</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExecutiveSummaryImpl extends MinimalEObjectImpl.Container implements ExecutiveSummary {
	/**
	 * The default value of the '{@link #getContext() <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected String context = CONTEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected static final String SCOPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected String scope = SCOPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeHorizon() <em>Time Horizon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeHorizon()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_HORIZON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeHorizon() <em>Time Horizon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeHorizon()
	 * @generated
	 * @ordered
	 */
	protected String timeHorizon = TIME_HORIZON_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeyFocus() <em>Key Focus</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyFocus()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_FOCUS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKeyFocus() <em>Key Focus</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyFocus()
	 * @generated
	 * @ordered
	 */
	protected String keyFocus = KEY_FOCUS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecutiveSummaryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TrendAnalysisPackage.Literals.EXECUTIVE_SUMMARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContext(String newContext) {
		String oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.EXECUTIVE_SUMMARY__CONTEXT, oldContext, context));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getScope() {
		return scope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setScope(String newScope) {
		String oldScope = scope;
		scope = newScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.EXECUTIVE_SUMMARY__SCOPE, oldScope, scope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTimeHorizon() {
		return timeHorizon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTimeHorizon(String newTimeHorizon) {
		String oldTimeHorizon = timeHorizon;
		timeHorizon = newTimeHorizon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.EXECUTIVE_SUMMARY__TIME_HORIZON, oldTimeHorizon, timeHorizon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKeyFocus() {
		return keyFocus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKeyFocus(String newKeyFocus) {
		String oldKeyFocus = keyFocus;
		keyFocus = newKeyFocus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.EXECUTIVE_SUMMARY__KEY_FOCUS, oldKeyFocus, keyFocus));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__CONTEXT:
				return getContext();
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__SCOPE:
				return getScope();
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__TIME_HORIZON:
				return getTimeHorizon();
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__KEY_FOCUS:
				return getKeyFocus();
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
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__CONTEXT:
				setContext((String)newValue);
				return;
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__SCOPE:
				setScope((String)newValue);
				return;
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__TIME_HORIZON:
				setTimeHorizon((String)newValue);
				return;
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__KEY_FOCUS:
				setKeyFocus((String)newValue);
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
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__SCOPE:
				setScope(SCOPE_EDEFAULT);
				return;
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__TIME_HORIZON:
				setTimeHorizon(TIME_HORIZON_EDEFAULT);
				return;
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__KEY_FOCUS:
				setKeyFocus(KEY_FOCUS_EDEFAULT);
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
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__SCOPE:
				return SCOPE_EDEFAULT == null ? scope != null : !SCOPE_EDEFAULT.equals(scope);
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__TIME_HORIZON:
				return TIME_HORIZON_EDEFAULT == null ? timeHorizon != null : !TIME_HORIZON_EDEFAULT.equals(timeHorizon);
			case TrendAnalysisPackage.EXECUTIVE_SUMMARY__KEY_FOCUS:
				return KEY_FOCUS_EDEFAULT == null ? keyFocus != null : !KEY_FOCUS_EDEFAULT.equals(keyFocus);
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
		result.append(" (context: ");
		result.append(context);
		result.append(", scope: ");
		result.append(scope);
		result.append(", timeHorizon: ");
		result.append(timeHorizon);
		result.append(", keyFocus: ");
		result.append(keyFocus);
		result.append(')');
		return result.toString();
	}

} //ExecutiveSummaryImpl
