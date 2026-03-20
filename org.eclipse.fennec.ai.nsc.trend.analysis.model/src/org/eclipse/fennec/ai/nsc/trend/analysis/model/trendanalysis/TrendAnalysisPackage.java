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


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EReference;

import org.gecko.emf.osgi.annotation.provide.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisFactory
 * @model kind="package"
 *        annotation="Version value='1.0'"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel complianceLevel='17.0' oSGiCompatible='true' basePackage='org.eclipse.fennec.ai.nsc.trend.analysis.model' resource='XMI' copyrightText='Copyright (c) 2012 - 2025 Data In Motion and others.\nAll rights reserved.\n\nThis program and the accompanying materials are made\navailable under the terms of the Eclipse Public License 2.0\nwhich is available at https://www.eclipse.org/legal/epl-2.0/\n\nSPDX-License-Identifier: EPL-2.0\n\nContributors:\n     Data In Motion - initial API and implementation'"
 * @generated
 */
@ProviderType
@EPackage(uri = TrendAnalysisPackage.eNS_URI, genModel = "/model/trend-analysis.genmodel", genModelSourceLocations = {"model/trend-analysis.genmodel","org.eclipse.fennec.ai.nsc.trend.analysis.model/model/trend-analysis.genmodel"}, ecore="/model/trend-analysis.ecore", ecoreSourceLocations="/model/trend-analysis.ecore")
public interface TrendAnalysisPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "trendanalysis";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/fennec/ai/trendanalysis/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "trendanalysis";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TrendAnalysisPackage eINSTANCE = org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisImpl <em>Trend Analysis</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisImpl
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getTrendAnalysis()
	 * @generated
	 */
	int TREND_ANALYSIS = 0;

	/**
	 * The feature id for the '<em><b>Document Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND_ANALYSIS__DOCUMENT_METADATA = 0;

	/**
	 * The feature id for the '<em><b>Executive Summary</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND_ANALYSIS__EXECUTIVE_SUMMARY = 1;

	/**
	 * The feature id for the '<em><b>Trends</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND_ANALYSIS__TRENDS = 2;

	/**
	 * The number of structural features of the '<em>Trend Analysis</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND_ANALYSIS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Trend Analysis</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND_ANALYSIS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl <em>Document Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getDocumentMetadata()
	 * @generated
	 */
	int DOCUMENT_METADATA = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__ID = 0;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__TITLE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__NAME = 2;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__SOURCE = 3;

	/**
	 * The feature id for the '<em><b>Legislative Period</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__LEGISLATIVE_PERIOD = 4;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__DATE = 5;

	/**
	 * The feature id for the '<em><b>Total Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__TOTAL_PAGES = 6;

	/**
	 * The feature id for the '<em><b>Issuing Authority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__ISSUING_AUTHORITY = 7;

	/**
	 * The feature id for the '<em><b>Analysis Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA__ANALYSIS_DATE = 8;

	/**
	 * The number of structural features of the '<em>Document Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Document Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_METADATA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.ExecutiveSummaryImpl <em>Executive Summary</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.ExecutiveSummaryImpl
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getExecutiveSummary()
	 * @generated
	 */
	int EXECUTIVE_SUMMARY = 2;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTIVE_SUMMARY__CONTEXT = 0;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTIVE_SUMMARY__SCOPE = 1;

	/**
	 * The feature id for the '<em><b>Time Horizon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTIVE_SUMMARY__TIME_HORIZON = 2;

	/**
	 * The feature id for the '<em><b>Key Focus</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTIVE_SUMMARY__KEY_FOCUS = 3;

	/**
	 * The number of structural features of the '<em>Executive Summary</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTIVE_SUMMARY_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Executive Summary</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTIVE_SUMMARY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl <em>Trend</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getTrend()
	 * @generated
	 */
	int TREND = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__ID = 0;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__TITLE = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Time Horizon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__TIME_HORIZON = 3;

	/**
	 * The feature id for the '<em><b>Primary Stempel Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__PRIMARY_STEMPEL_CATEGORY = 4;

	/**
	 * The feature id for the '<em><b>Secondary Stempel Category</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__SECONDARY_STEMPEL_CATEGORY = 5;

	/**
	 * The feature id for the '<em><b>Page Reference</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__PAGE_REFERENCE = 6;

	/**
	 * The feature id for the '<em><b>Evidence</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__EVIDENCE = 7;

	/**
	 * The feature id for the '<em><b>Implications</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__IMPLICATIONS = 8;

	/**
	 * The feature id for the '<em><b>Related Item Ids</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND__RELATED_ITEM_IDS = 9;

	/**
	 * The number of structural features of the '<em>Trend</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Trend</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREND_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType <em>Stempel Category Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getStempelCategoryType()
	 * @generated
	 */
	int STEMPEL_CATEGORY_TYPE = 4;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis <em>Trend Analysis</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trend Analysis</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis
	 * @generated
	 */
	EClass getTrendAnalysis();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getDocumentMetadata <em>Document Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Document Metadata</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getDocumentMetadata()
	 * @see #getTrendAnalysis()
	 * @generated
	 */
	EReference getTrendAnalysis_DocumentMetadata();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getExecutiveSummary <em>Executive Summary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Executive Summary</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getExecutiveSummary()
	 * @see #getTrendAnalysis()
	 * @generated
	 */
	EReference getTrendAnalysis_ExecutiveSummary();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getTrends <em>Trends</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Trends</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis#getTrends()
	 * @see #getTrendAnalysis()
	 * @generated
	 */
	EReference getTrendAnalysis_Trends();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata <em>Document Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Metadata</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata
	 * @generated
	 */
	EClass getDocumentMetadata();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getId()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getTitle()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getName()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getSource()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_Source();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getLegislativePeriod <em>Legislative Period</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Legislative Period</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getLegislativePeriod()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_LegislativePeriod();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getDate()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_Date();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getTotalPages <em>Total Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Pages</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getTotalPages()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_TotalPages();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getIssuingAuthority <em>Issuing Authority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Issuing Authority</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getIssuingAuthority()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_IssuingAuthority();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getAnalysisDate <em>Analysis Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Analysis Date</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getAnalysisDate()
	 * @see #getDocumentMetadata()
	 * @generated
	 */
	EAttribute getDocumentMetadata_AnalysisDate();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary <em>Executive Summary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Executive Summary</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary
	 * @generated
	 */
	EClass getExecutiveSummary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getContext()
	 * @see #getExecutiveSummary()
	 * @generated
	 */
	EAttribute getExecutiveSummary_Context();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scope</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getScope()
	 * @see #getExecutiveSummary()
	 * @generated
	 */
	EAttribute getExecutiveSummary_Scope();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getTimeHorizon <em>Time Horizon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Horizon</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getTimeHorizon()
	 * @see #getExecutiveSummary()
	 * @generated
	 */
	EAttribute getExecutiveSummary_TimeHorizon();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getKeyFocus <em>Key Focus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key Focus</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary#getKeyFocus()
	 * @see #getExecutiveSummary()
	 * @generated
	 */
	EAttribute getExecutiveSummary_KeyFocus();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend <em>Trend</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trend</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend
	 * @generated
	 */
	EClass getTrend();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getId()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getTitle()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getDescription()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getTimeHorizon <em>Time Horizon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Horizon</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getTimeHorizon()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_TimeHorizon();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getPrimaryStempelCategory <em>Primary Stempel Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Stempel Category</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getPrimaryStempelCategory()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_PrimaryStempelCategory();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getSecondaryStempelCategory <em>Secondary Stempel Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Secondary Stempel Category</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getSecondaryStempelCategory()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_SecondaryStempelCategory();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getPageReference <em>Page Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Page Reference</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getPageReference()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_PageReference();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getEvidence <em>Evidence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Evidence</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getEvidence()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_Evidence();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getImplications <em>Implications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Implications</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getImplications()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_Implications();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getRelatedItemIds <em>Related Item Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Related Item Ids</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getRelatedItemIds()
	 * @see #getTrend()
	 * @generated
	 */
	EAttribute getTrend_RelatedItemIds();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType <em>Stempel Category Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Stempel Category Type</em>'.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType
	 * @generated
	 */
	EEnum getStempelCategoryType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TrendAnalysisFactory getTrendAnalysisFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisImpl <em>Trend Analysis</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisImpl
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getTrendAnalysis()
		 * @generated
		 */
		EClass TREND_ANALYSIS = eINSTANCE.getTrendAnalysis();

		/**
		 * The meta object literal for the '<em><b>Document Metadata</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TREND_ANALYSIS__DOCUMENT_METADATA = eINSTANCE.getTrendAnalysis_DocumentMetadata();

		/**
		 * The meta object literal for the '<em><b>Executive Summary</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TREND_ANALYSIS__EXECUTIVE_SUMMARY = eINSTANCE.getTrendAnalysis_ExecutiveSummary();

		/**
		 * The meta object literal for the '<em><b>Trends</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TREND_ANALYSIS__TRENDS = eINSTANCE.getTrendAnalysis_Trends();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl <em>Document Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getDocumentMetadata()
		 * @generated
		 */
		EClass DOCUMENT_METADATA = eINSTANCE.getDocumentMetadata();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__ID = eINSTANCE.getDocumentMetadata_Id();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__TITLE = eINSTANCE.getDocumentMetadata_Title();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__NAME = eINSTANCE.getDocumentMetadata_Name();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__SOURCE = eINSTANCE.getDocumentMetadata_Source();

		/**
		 * The meta object literal for the '<em><b>Legislative Period</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__LEGISLATIVE_PERIOD = eINSTANCE.getDocumentMetadata_LegislativePeriod();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__DATE = eINSTANCE.getDocumentMetadata_Date();

		/**
		 * The meta object literal for the '<em><b>Total Pages</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__TOTAL_PAGES = eINSTANCE.getDocumentMetadata_TotalPages();

		/**
		 * The meta object literal for the '<em><b>Issuing Authority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__ISSUING_AUTHORITY = eINSTANCE.getDocumentMetadata_IssuingAuthority();

		/**
		 * The meta object literal for the '<em><b>Analysis Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_METADATA__ANALYSIS_DATE = eINSTANCE.getDocumentMetadata_AnalysisDate();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.ExecutiveSummaryImpl <em>Executive Summary</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.ExecutiveSummaryImpl
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getExecutiveSummary()
		 * @generated
		 */
		EClass EXECUTIVE_SUMMARY = eINSTANCE.getExecutiveSummary();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTIVE_SUMMARY__CONTEXT = eINSTANCE.getExecutiveSummary_Context();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTIVE_SUMMARY__SCOPE = eINSTANCE.getExecutiveSummary_Scope();

		/**
		 * The meta object literal for the '<em><b>Time Horizon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTIVE_SUMMARY__TIME_HORIZON = eINSTANCE.getExecutiveSummary_TimeHorizon();

		/**
		 * The meta object literal for the '<em><b>Key Focus</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTIVE_SUMMARY__KEY_FOCUS = eINSTANCE.getExecutiveSummary_KeyFocus();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl <em>Trend</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getTrend()
		 * @generated
		 */
		EClass TREND = eINSTANCE.getTrend();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__ID = eINSTANCE.getTrend_Id();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__TITLE = eINSTANCE.getTrend_Title();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__DESCRIPTION = eINSTANCE.getTrend_Description();

		/**
		 * The meta object literal for the '<em><b>Time Horizon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__TIME_HORIZON = eINSTANCE.getTrend_TimeHorizon();

		/**
		 * The meta object literal for the '<em><b>Primary Stempel Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__PRIMARY_STEMPEL_CATEGORY = eINSTANCE.getTrend_PrimaryStempelCategory();

		/**
		 * The meta object literal for the '<em><b>Secondary Stempel Category</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__SECONDARY_STEMPEL_CATEGORY = eINSTANCE.getTrend_SecondaryStempelCategory();

		/**
		 * The meta object literal for the '<em><b>Page Reference</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__PAGE_REFERENCE = eINSTANCE.getTrend_PageReference();

		/**
		 * The meta object literal for the '<em><b>Evidence</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__EVIDENCE = eINSTANCE.getTrend_Evidence();

		/**
		 * The meta object literal for the '<em><b>Implications</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__IMPLICATIONS = eINSTANCE.getTrend_Implications();

		/**
		 * The meta object literal for the '<em><b>Related Item Ids</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TREND__RELATED_ITEM_IDS = eINSTANCE.getTrend_RelatedItemIds();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType <em>Stempel Category Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType
		 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisPackageImpl#getStempelCategoryType()
		 * @generated
		 */
		EEnum STEMPEL_CATEGORY_TYPE = eINSTANCE.getStempelCategoryType();

	}

} //TrendAnalysisPackage
