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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.ExecutiveSummary;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysis;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trend Analysis</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisImpl#getDocumentMetadata <em>Document Metadata</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisImpl#getExecutiveSummary <em>Executive Summary</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendAnalysisImpl#getTrends <em>Trends</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrendAnalysisImpl extends MinimalEObjectImpl.Container implements TrendAnalysis {
	/**
	 * The cached value of the '{@link #getDocumentMetadata() <em>Document Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentMetadata()
	 * @generated
	 * @ordered
	 */
	protected DocumentMetadata documentMetadata;

	/**
	 * The cached value of the '{@link #getExecutiveSummary() <em>Executive Summary</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutiveSummary()
	 * @generated
	 * @ordered
	 */
	protected ExecutiveSummary executiveSummary;

	/**
	 * The cached value of the '{@link #getTrends() <em>Trends</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrends()
	 * @generated
	 * @ordered
	 */
	protected EList<Trend> trends;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TrendAnalysisImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TrendAnalysisPackage.Literals.TREND_ANALYSIS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DocumentMetadata getDocumentMetadata() {
		return documentMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentMetadata(DocumentMetadata newDocumentMetadata, NotificationChain msgs) {
		DocumentMetadata oldDocumentMetadata = documentMetadata;
		documentMetadata = newDocumentMetadata;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA, oldDocumentMetadata, newDocumentMetadata);
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
	public void setDocumentMetadata(DocumentMetadata newDocumentMetadata) {
		if (newDocumentMetadata != documentMetadata) {
			NotificationChain msgs = null;
			if (documentMetadata != null)
				msgs = ((InternalEObject)documentMetadata).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA, null, msgs);
			if (newDocumentMetadata != null)
				msgs = ((InternalEObject)newDocumentMetadata).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA, null, msgs);
			msgs = basicSetDocumentMetadata(newDocumentMetadata, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA, newDocumentMetadata, newDocumentMetadata));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExecutiveSummary getExecutiveSummary() {
		return executiveSummary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExecutiveSummary(ExecutiveSummary newExecutiveSummary, NotificationChain msgs) {
		ExecutiveSummary oldExecutiveSummary = executiveSummary;
		executiveSummary = newExecutiveSummary;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY, oldExecutiveSummary, newExecutiveSummary);
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
	public void setExecutiveSummary(ExecutiveSummary newExecutiveSummary) {
		if (newExecutiveSummary != executiveSummary) {
			NotificationChain msgs = null;
			if (executiveSummary != null)
				msgs = ((InternalEObject)executiveSummary).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY, null, msgs);
			if (newExecutiveSummary != null)
				msgs = ((InternalEObject)newExecutiveSummary).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY, null, msgs);
			msgs = basicSetExecutiveSummary(newExecutiveSummary, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY, newExecutiveSummary, newExecutiveSummary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Trend> getTrends() {
		if (trends == null) {
			trends = new EObjectContainmentEList<Trend>(Trend.class, this, TrendAnalysisPackage.TREND_ANALYSIS__TRENDS);
		}
		return trends;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA:
				return basicSetDocumentMetadata(null, msgs);
			case TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY:
				return basicSetExecutiveSummary(null, msgs);
			case TrendAnalysisPackage.TREND_ANALYSIS__TRENDS:
				return ((InternalEList<?>)getTrends()).basicRemove(otherEnd, msgs);
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
			case TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA:
				return getDocumentMetadata();
			case TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY:
				return getExecutiveSummary();
			case TrendAnalysisPackage.TREND_ANALYSIS__TRENDS:
				return getTrends();
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
			case TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA:
				setDocumentMetadata((DocumentMetadata)newValue);
				return;
			case TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY:
				setExecutiveSummary((ExecutiveSummary)newValue);
				return;
			case TrendAnalysisPackage.TREND_ANALYSIS__TRENDS:
				getTrends().clear();
				getTrends().addAll((Collection<? extends Trend>)newValue);
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
			case TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA:
				setDocumentMetadata((DocumentMetadata)null);
				return;
			case TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY:
				setExecutiveSummary((ExecutiveSummary)null);
				return;
			case TrendAnalysisPackage.TREND_ANALYSIS__TRENDS:
				getTrends().clear();
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
			case TrendAnalysisPackage.TREND_ANALYSIS__DOCUMENT_METADATA:
				return documentMetadata != null;
			case TrendAnalysisPackage.TREND_ANALYSIS__EXECUTIVE_SUMMARY:
				return executiveSummary != null;
			case TrendAnalysisPackage.TREND_ANALYSIS__TRENDS:
				return trends != null && !trends.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TrendAnalysisImpl
