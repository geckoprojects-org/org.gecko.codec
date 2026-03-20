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
package org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Executive Summary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getContext <em>Context</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getScope <em>Scope</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getTimeHorizon <em>Time Horizon</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getKeyFocus <em>Key Focus</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getExecutiveSummary()
 * @model
 * @generated
 */
@ProviderType
public interface ExecutiveSummary extends EObject {
	/**
	 * Returns the value of the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' attribute.
	 * @see #setContext(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getExecutiveSummary_Context()
	 * @model
	 * @generated
	 */
	String getContext();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getContext <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' attribute.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(String value);

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope</em>' attribute.
	 * @see #setScope(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getExecutiveSummary_Scope()
	 * @model
	 * @generated
	 */
	String getScope();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' attribute.
	 * @see #getScope()
	 * @generated
	 */
	void setScope(String value);

	/**
	 * Returns the value of the '<em><b>Time Horizon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Horizon</em>' attribute.
	 * @see #setTimeHorizon(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getExecutiveSummary_TimeHorizon()
	 * @model extendedMetaData="name='time_horizon'"
	 * @generated
	 */
	String getTimeHorizon();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getTimeHorizon <em>Time Horizon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Horizon</em>' attribute.
	 * @see #getTimeHorizon()
	 * @generated
	 */
	void setTimeHorizon(String value);

	/**
	 * Returns the value of the '<em><b>Key Focus</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Focus</em>' attribute.
	 * @see #setKeyFocus(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getExecutiveSummary_KeyFocus()
	 * @model extendedMetaData="name='key_focus'"
	 * @generated
	 */
	String getKeyFocus();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getKeyFocus <em>Key Focus</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Focus</em>' attribute.
	 * @see #getKeyFocus()
	 * @generated
	 */
	void setKeyFocus(String value);

} // ExecutiveSummary
