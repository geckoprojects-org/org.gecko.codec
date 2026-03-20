/**
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisFactory;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TrendAnalysisPackageImpl extends EPackageImpl implements TrendAnalysisPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass trendAnalysisEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentMetadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executiveSummaryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass trendEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stempelCategoryTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TrendAnalysisPackageImpl() {
		super(eNS_URI, TrendAnalysisFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link TrendAnalysisPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TrendAnalysisPackage init() {
		if (isInited) return (TrendAnalysisPackage)EPackage.Registry.INSTANCE.getEPackage(TrendAnalysisPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTrendAnalysisPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TrendAnalysisPackageImpl theTrendAnalysisPackage = registeredTrendAnalysisPackage instanceof TrendAnalysisPackageImpl ? (TrendAnalysisPackageImpl)registeredTrendAnalysisPackage : new TrendAnalysisPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theTrendAnalysisPackage.createPackageContents();

		// Initialize created meta-data
		theTrendAnalysisPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTrendAnalysisPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TrendAnalysisPackage.eNS_URI, theTrendAnalysisPackage);
		return theTrendAnalysisPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTrendAnalysis() {
		return trendAnalysisEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTrendAnalysis_DocumentMetadata() {
		return (EReference)trendAnalysisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTrendAnalysis_ExecutiveSummary() {
		return (EReference)trendAnalysisEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTrendAnalysis_Trends() {
		return (EReference)trendAnalysisEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDocumentMetadata() {
		return documentMetadataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_Id() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_Title() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_Name() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_Source() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_LegislativePeriod() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_Date() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_TotalPages() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_IssuingAuthority() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentMetadata_AnalysisDate() {
		return (EAttribute)documentMetadataEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExecutiveSummary() {
		return executiveSummaryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExecutiveSummary_Context() {
		return (EAttribute)executiveSummaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExecutiveSummary_Scope() {
		return (EAttribute)executiveSummaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExecutiveSummary_TimeHorizon() {
		return (EAttribute)executiveSummaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExecutiveSummary_KeyFocus() {
		return (EAttribute)executiveSummaryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTrend() {
		return trendEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_Id() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_Title() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_Description() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_TimeHorizon() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_PrimaryStempelCategory() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_SecondaryStempelCategory() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_PageReference() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_Evidence() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_Implications() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTrend_RelatedItemIds() {
		return (EAttribute)trendEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getStempelCategoryType() {
		return stempelCategoryTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TrendAnalysisFactory getTrendAnalysisFactory() {
		return (TrendAnalysisFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		trendAnalysisEClass = createEClass(TREND_ANALYSIS);
		createEReference(trendAnalysisEClass, TREND_ANALYSIS__DOCUMENT_METADATA);
		createEReference(trendAnalysisEClass, TREND_ANALYSIS__EXECUTIVE_SUMMARY);
		createEReference(trendAnalysisEClass, TREND_ANALYSIS__TRENDS);

		documentMetadataEClass = createEClass(DOCUMENT_METADATA);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__ID);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__TITLE);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__NAME);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__SOURCE);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__LEGISLATIVE_PERIOD);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__DATE);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__TOTAL_PAGES);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__ISSUING_AUTHORITY);
		createEAttribute(documentMetadataEClass, DOCUMENT_METADATA__ANALYSIS_DATE);

		executiveSummaryEClass = createEClass(EXECUTIVE_SUMMARY);
		createEAttribute(executiveSummaryEClass, EXECUTIVE_SUMMARY__CONTEXT);
		createEAttribute(executiveSummaryEClass, EXECUTIVE_SUMMARY__SCOPE);
		createEAttribute(executiveSummaryEClass, EXECUTIVE_SUMMARY__TIME_HORIZON);
		createEAttribute(executiveSummaryEClass, EXECUTIVE_SUMMARY__KEY_FOCUS);

		trendEClass = createEClass(TREND);
		createEAttribute(trendEClass, TREND__ID);
		createEAttribute(trendEClass, TREND__TITLE);
		createEAttribute(trendEClass, TREND__DESCRIPTION);
		createEAttribute(trendEClass, TREND__TIME_HORIZON);
		createEAttribute(trendEClass, TREND__PRIMARY_STEMPEL_CATEGORY);
		createEAttribute(trendEClass, TREND__SECONDARY_STEMPEL_CATEGORY);
		createEAttribute(trendEClass, TREND__PAGE_REFERENCE);
		createEAttribute(trendEClass, TREND__EVIDENCE);
		createEAttribute(trendEClass, TREND__IMPLICATIONS);
		createEAttribute(trendEClass, TREND__RELATED_ITEM_IDS);

		// Create enums
		stempelCategoryTypeEEnum = createEEnum(STEMPEL_CATEGORY_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(trendAnalysisEClass, TrendAnalysis.class, "TrendAnalysis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrendAnalysis_DocumentMetadata(), this.getDocumentMetadata(), null, "documentMetadata", null, 0, 1, TrendAnalysis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrendAnalysis_ExecutiveSummary(), this.getExecutiveSummary(), null, "executiveSummary", null, 0, 1, TrendAnalysis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrendAnalysis_Trends(), this.getTrend(), null, "trends", null, 0, -1, TrendAnalysis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentMetadataEClass, DocumentMetadata.class, "DocumentMetadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentMetadata_Id(), ecorePackage.getEString(), "id", null, 1, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentMetadata_Title(), ecorePackage.getEString(), "title", null, 0, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentMetadata_Name(), ecorePackage.getEString(), "name", null, 0, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentMetadata_Source(), ecorePackage.getEString(), "source", null, 0, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentMetadata_LegislativePeriod(), ecorePackage.getEString(), "legislativePeriod", null, 0, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentMetadata_Date(), ecorePackage.getEDate(), "date", null, 0, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentMetadata_TotalPages(), ecorePackage.getEInt(), "totalPages", null, 0, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentMetadata_IssuingAuthority(), ecorePackage.getEString(), "issuingAuthority", null, 0, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentMetadata_AnalysisDate(), ecorePackage.getEDate(), "analysisDate", null, 0, 1, DocumentMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(executiveSummaryEClass, ExecutiveSummary.class, "ExecutiveSummary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExecutiveSummary_Context(), ecorePackage.getEString(), "context", null, 0, 1, ExecutiveSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutiveSummary_Scope(), ecorePackage.getEString(), "scope", null, 0, 1, ExecutiveSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutiveSummary_TimeHorizon(), ecorePackage.getEString(), "timeHorizon", null, 0, 1, ExecutiveSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutiveSummary_KeyFocus(), ecorePackage.getEString(), "keyFocus", null, 0, 1, ExecutiveSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trendEClass, Trend.class, "Trend", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrend_Id(), ecorePackage.getEString(), "id", null, 1, 1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_Title(), ecorePackage.getEString(), "title", null, 0, 1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_Description(), ecorePackage.getEString(), "description", null, 0, 1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_TimeHorizon(), ecorePackage.getEString(), "timeHorizon", null, 0, 1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_PrimaryStempelCategory(), this.getStempelCategoryType(), "primaryStempelCategory", null, 0, 1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_SecondaryStempelCategory(), this.getStempelCategoryType(), "secondaryStempelCategory", null, 0, 3, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_PageReference(), ecorePackage.getEInt(), "pageReference", null, 0, -1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_Evidence(), ecorePackage.getEString(), "evidence", null, 0, -1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_Implications(), ecorePackage.getEString(), "implications", null, 0, -1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrend_RelatedItemIds(), ecorePackage.getEString(), "relatedItemIds", null, 0, -1, Trend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(stempelCategoryTypeEEnum, StempelCategoryType.class, "StempelCategoryType");
		addEEnumLiteral(stempelCategoryTypeEEnum, StempelCategoryType.SOCIAL);
		addEEnumLiteral(stempelCategoryTypeEEnum, StempelCategoryType.TECHNOLOGICAL);
		addEEnumLiteral(stempelCategoryTypeEEnum, StempelCategoryType.ECONOMIC_GEO_ECONOMIC);
		addEEnumLiteral(stempelCategoryTypeEEnum, StempelCategoryType.EXTERNAL_SECURITY);
		addEEnumLiteral(stempelCategoryTypeEEnum, StempelCategoryType.INTERNAL_SECURITY);
		addEEnumLiteral(stempelCategoryTypeEEnum, StempelCategoryType.POLITICAL_GEO_POLITICAL);
		addEEnumLiteral(stempelCategoryTypeEEnum, StempelCategoryType.ECOLOGICAL_PLANETARY);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// Version
		createVersionAnnotations();
		// http://www.eclipse.org/emf/2002/GenModel
		createGenModelAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>Version</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createVersionAnnotations() {
		String source = "Version";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "value", "1.0"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/GenModel</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createGenModelAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/GenModel";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "complianceLevel", "17.0",
			   "oSGiCompatible", "true",
			   "basePackage", "org.eclipse.fennec.ai.nsc.trend.analysis.model",
			   "resource", "XMI",
			   "copyrightText", "Copyright (c) 2012 - 2025 Data In Motion and others.\nAll rights reserved.\n\nThis program and the accompanying materials are made\navailable under the terms of the Eclipse Public License 2.0\nwhich is available at https://www.eclipse.org/legal/epl-2.0/\n\nSPDX-License-Identifier: EPL-2.0\n\nContributors:\n     Data In Motion - initial API and implementation"
		   });
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation
		  (getTrendAnalysis_DocumentMetadata(),
		   source,
		   new String[] {
			   "name", "document_metadata"
		   });
		addAnnotation
		  (getTrendAnalysis_ExecutiveSummary(),
		   source,
		   new String[] {
			   "name", "executive_summary"
		   });
		addAnnotation
		  (getDocumentMetadata_Id(),
		   source,
		   new String[] {
			   "name", "document_id"
		   });
		addAnnotation
		  (getDocumentMetadata_LegislativePeriod(),
		   source,
		   new String[] {
			   "name", "legislative_period"
		   });
		addAnnotation
		  (getDocumentMetadata_TotalPages(),
		   source,
		   new String[] {
			   "name", "total_pages"
		   });
		addAnnotation
		  (getDocumentMetadata_IssuingAuthority(),
		   source,
		   new String[] {
			   "name", "issuing_authority"
		   });
		addAnnotation
		  (getDocumentMetadata_AnalysisDate(),
		   source,
		   new String[] {
			   "name", "analysis_date"
		   });
		addAnnotation
		  (getExecutiveSummary_TimeHorizon(),
		   source,
		   new String[] {
			   "name", "time_horizon"
		   });
		addAnnotation
		  (getExecutiveSummary_KeyFocus(),
		   source,
		   new String[] {
			   "name", "key_focus"
		   });
		addAnnotation
		  (getTrend_TimeHorizon(),
		   source,
		   new String[] {
			   "name", "time_horizon"
		   });
		addAnnotation
		  (getTrend_PrimaryStempelCategory(),
		   source,
		   new String[] {
			   "name", "primary_stempel_category"
		   });
		addAnnotation
		  (getTrend_SecondaryStempelCategory(),
		   source,
		   new String[] {
			   "name", "secondary_stempel_categories"
		   });
		addAnnotation
		  (getTrend_PageReference(),
		   source,
		   new String[] {
			   "name", "page_reference"
		   });
		addAnnotation
		  (getTrend_RelatedItemIds(),
		   source,
		   new String[] {
			   "name", "related_item_ids"
		   });
	}

} //TrendAnalysisPackageImpl
