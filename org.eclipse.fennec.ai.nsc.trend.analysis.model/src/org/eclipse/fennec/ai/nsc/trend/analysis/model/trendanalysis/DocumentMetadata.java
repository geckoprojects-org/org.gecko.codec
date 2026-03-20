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

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Metadata</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getLegislativePeriod <em>Legislative Period</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getDate <em>Date</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getTotalPages <em>Total Pages</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getIssuingAuthority <em>Issuing Authority</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getAnalysisDate <em>Analysis Date</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata()
 * @model
 * @generated
 */
@ProviderType
public interface DocumentMetadata extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_Id()
	 * @model required="true"
	 *        extendedMetaData="name='document_id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_Title()
	 * @model
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_Source()
	 * @model
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

	/**
	 * Returns the value of the '<em><b>Legislative Period</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Legislative Period</em>' attribute.
	 * @see #setLegislativePeriod(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_LegislativePeriod()
	 * @model extendedMetaData="name='legislative_period'"
	 * @generated
	 */
	String getLegislativePeriod();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getLegislativePeriod <em>Legislative Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Legislative Period</em>' attribute.
	 * @see #getLegislativePeriod()
	 * @generated
	 */
	void setLegislativePeriod(String value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(Date)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_Date()
	 * @model
	 * @generated
	 */
	Date getDate();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(Date value);

	/**
	 * Returns the value of the '<em><b>Total Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Pages</em>' attribute.
	 * @see #setTotalPages(int)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_TotalPages()
	 * @model extendedMetaData="name='total_pages'"
	 * @generated
	 */
	int getTotalPages();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getTotalPages <em>Total Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Pages</em>' attribute.
	 * @see #getTotalPages()
	 * @generated
	 */
	void setTotalPages(int value);

	/**
	 * Returns the value of the '<em><b>Issuing Authority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Issuing Authority</em>' attribute.
	 * @see #setIssuingAuthority(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_IssuingAuthority()
	 * @model extendedMetaData="name='issuing_authority'"
	 * @generated
	 */
	String getIssuingAuthority();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getIssuingAuthority <em>Issuing Authority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Issuing Authority</em>' attribute.
	 * @see #getIssuingAuthority()
	 * @generated
	 */
	void setIssuingAuthority(String value);

	/**
	 * Returns the value of the '<em><b>Analysis Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Analysis Date</em>' attribute.
	 * @see #setAnalysisDate(Date)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getDocumentMetadata_AnalysisDate()
	 * @model extendedMetaData="name='analysis_date'"
	 * @generated
	 */
	Date getAnalysisDate();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata#getAnalysisDate <em>Analysis Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Analysis Date</em>' attribute.
	 * @see #getAnalysisDate()
	 * @generated
	 */
	void setAnalysisDate(Date value);

} // DocumentMetadata
