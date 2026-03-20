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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trend Analysis</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getDocumentMetadata <em>Document Metadata</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getExecutiveSummary <em>Executive Summary</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getTrends <em>Trends</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrendAnalysis()
 * @model
 * @generated
 */
@ProviderType
public interface TrendAnalysis extends EObject {
	/**
	 * Returns the value of the '<em><b>Document Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Document Metadata</em>' containment reference.
	 * @see #setDocumentMetadata(DocumentMetadata)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrendAnalysis_DocumentMetadata()
	 * @model containment="true"
	 *        extendedMetaData="name='document_metadata'"
	 * @generated
	 */
	DocumentMetadata getDocumentMetadata();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getDocumentMetadata <em>Document Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Document Metadata</em>' containment reference.
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	void setDocumentMetadata(DocumentMetadata value);

	/**
	 * Returns the value of the '<em><b>Executive Summary</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Executive Summary</em>' containment reference.
	 * @see #setExecutiveSummary(ExecutiveSummary)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrendAnalysis_ExecutiveSummary()
	 * @model containment="true"
	 *        extendedMetaData="name='executive_summary'"
	 * @generated
	 */
	ExecutiveSummary getExecutiveSummary();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getExecutiveSummary <em>Executive Summary</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Executive Summary</em>' containment reference.
	 * @see #getExecutiveSummary()
	 * @generated
	 */
	void setExecutiveSummary(ExecutiveSummary value);

	/**
	 * Returns the value of the '<em><b>Trends</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trends</em>' containment reference list.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrendAnalysis_Trends()
	 * @model containment="true"
	 * @generated
	 */
	EList<Trend> getTrends();

} // TrendAnalysis
