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
 * A representation of the model object '<em><b>Trend</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getTimeHorizon <em>Time Horizon</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getPrimaryStempelCategory <em>Primary Stempel Category</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getSecondaryStempelCategory <em>Secondary Stempel Category</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getPageReference <em>Page Reference</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getEvidence <em>Evidence</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getImplications <em>Implications</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getRelatedItemIds <em>Related Item Ids</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend()
 * @model
 * @generated
 */
@ProviderType
public interface Trend extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getId <em>Id</em>}' attribute.
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
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_Title()
	 * @model
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Time Horizon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Horizon</em>' attribute.
	 * @see #setTimeHorizon(String)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_TimeHorizon()
	 * @model extendedMetaData="name='time_horizon'"
	 * @generated
	 */
	String getTimeHorizon();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getTimeHorizon <em>Time Horizon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Horizon</em>' attribute.
	 * @see #getTimeHorizon()
	 * @generated
	 */
	void setTimeHorizon(String value);

	/**
	 * Returns the value of the '<em><b>Primary Stempel Category</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Stempel Category</em>' attribute.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType
	 * @see #setPrimaryStempelCategory(StempelCategoryType)
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_PrimaryStempelCategory()
	 * @model extendedMetaData="name='primary_stempel_category'"
	 * @generated
	 */
	StempelCategoryType getPrimaryStempelCategory();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend#getPrimaryStempelCategory <em>Primary Stempel Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Stempel Category</em>' attribute.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType
	 * @see #getPrimaryStempelCategory()
	 * @generated
	 */
	void setPrimaryStempelCategory(StempelCategoryType value);

	/**
	 * Returns the value of the '<em><b>Secondary Stempel Category</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType}.
	 * The literals are from the enumeration {@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Stempel Category</em>' attribute list.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_SecondaryStempelCategory()
	 * @model upper="3"
	 *        extendedMetaData="name='secondary_stempel_categories'"
	 * @generated
	 */
	EList<StempelCategoryType> getSecondaryStempelCategory();

	/**
	 * Returns the value of the '<em><b>Page Reference</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Page Reference</em>' attribute list.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_PageReference()
	 * @model extendedMetaData="name='page_reference'"
	 * @generated
	 */
	EList<Integer> getPageReference();

	/**
	 * Returns the value of the '<em><b>Evidence</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Evidence</em>' attribute list.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_Evidence()
	 * @model
	 * @generated
	 */
	EList<String> getEvidence();

	/**
	 * Returns the value of the '<em><b>Implications</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implications</em>' attribute list.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_Implications()
	 * @model
	 * @generated
	 */
	EList<String> getImplications();

	/**
	 * Returns the value of the '<em><b>Related Item Ids</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Item Ids</em>' attribute list.
	 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getTrend_RelatedItemIds()
	 * @model extendedMetaData="name='related_item_ids'"
	 * @generated
	 */
	EList<String> getRelatedItemIds();

} // Trend
