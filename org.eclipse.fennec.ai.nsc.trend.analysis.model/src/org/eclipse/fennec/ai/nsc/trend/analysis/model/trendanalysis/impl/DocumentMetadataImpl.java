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

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.DocumentMetadata;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Metadata</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getLegislativePeriod <em>Legislative Period</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getDate <em>Date</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getTotalPages <em>Total Pages</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getIssuingAuthority <em>Issuing Authority</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.DocumentMetadataImpl#getAnalysisDate <em>Analysis Date</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocumentMetadataImpl extends MinimalEObjectImpl.Container implements DocumentMetadata {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected String source = SOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLegislativePeriod() <em>Legislative Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLegislativePeriod()
	 * @generated
	 * @ordered
	 */
	protected static final String LEGISLATIVE_PERIOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLegislativePeriod() <em>Legislative Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLegislativePeriod()
	 * @generated
	 * @ordered
	 */
	protected String legislativePeriod = LEGISLATIVE_PERIOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalPages() <em>Total Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalPages()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_PAGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTotalPages() <em>Total Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalPages()
	 * @generated
	 * @ordered
	 */
	protected int totalPages = TOTAL_PAGES_EDEFAULT;

	/**
	 * The default value of the '{@link #getIssuingAuthority() <em>Issuing Authority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIssuingAuthority()
	 * @generated
	 * @ordered
	 */
	protected static final String ISSUING_AUTHORITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIssuingAuthority() <em>Issuing Authority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIssuingAuthority()
	 * @generated
	 * @ordered
	 */
	protected String issuingAuthority = ISSUING_AUTHORITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getAnalysisDate() <em>Analysis Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnalysisDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date ANALYSIS_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAnalysisDate() <em>Analysis Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnalysisDate()
	 * @generated
	 * @ordered
	 */
	protected Date analysisDate = ANALYSIS_DATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentMetadataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TrendAnalysisPackage.Literals.DOCUMENT_METADATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSource(String newSource) {
		String oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLegislativePeriod() {
		return legislativePeriod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLegislativePeriod(String newLegislativePeriod) {
		String oldLegislativePeriod = legislativePeriod;
		legislativePeriod = newLegislativePeriod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__LEGISLATIVE_PERIOD, oldLegislativePeriod, legislativePeriod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDate(Date newDate) {
		Date oldDate = date;
		date = newDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__DATE, oldDate, date));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTotalPages(int newTotalPages) {
		int oldTotalPages = totalPages;
		totalPages = newTotalPages;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__TOTAL_PAGES, oldTotalPages, totalPages));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIssuingAuthority() {
		return issuingAuthority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIssuingAuthority(String newIssuingAuthority) {
		String oldIssuingAuthority = issuingAuthority;
		issuingAuthority = newIssuingAuthority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__ISSUING_AUTHORITY, oldIssuingAuthority, issuingAuthority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getAnalysisDate() {
		return analysisDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnalysisDate(Date newAnalysisDate) {
		Date oldAnalysisDate = analysisDate;
		analysisDate = newAnalysisDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.DOCUMENT_METADATA__ANALYSIS_DATE, oldAnalysisDate, analysisDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TrendAnalysisPackage.DOCUMENT_METADATA__ID:
				return getId();
			case TrendAnalysisPackage.DOCUMENT_METADATA__TITLE:
				return getTitle();
			case TrendAnalysisPackage.DOCUMENT_METADATA__NAME:
				return getName();
			case TrendAnalysisPackage.DOCUMENT_METADATA__SOURCE:
				return getSource();
			case TrendAnalysisPackage.DOCUMENT_METADATA__LEGISLATIVE_PERIOD:
				return getLegislativePeriod();
			case TrendAnalysisPackage.DOCUMENT_METADATA__DATE:
				return getDate();
			case TrendAnalysisPackage.DOCUMENT_METADATA__TOTAL_PAGES:
				return getTotalPages();
			case TrendAnalysisPackage.DOCUMENT_METADATA__ISSUING_AUTHORITY:
				return getIssuingAuthority();
			case TrendAnalysisPackage.DOCUMENT_METADATA__ANALYSIS_DATE:
				return getAnalysisDate();
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
			case TrendAnalysisPackage.DOCUMENT_METADATA__ID:
				setId((String)newValue);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__TITLE:
				setTitle((String)newValue);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__NAME:
				setName((String)newValue);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__SOURCE:
				setSource((String)newValue);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__LEGISLATIVE_PERIOD:
				setLegislativePeriod((String)newValue);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__DATE:
				setDate((Date)newValue);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__TOTAL_PAGES:
				setTotalPages((Integer)newValue);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__ISSUING_AUTHORITY:
				setIssuingAuthority((String)newValue);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__ANALYSIS_DATE:
				setAnalysisDate((Date)newValue);
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
			case TrendAnalysisPackage.DOCUMENT_METADATA__ID:
				setId(ID_EDEFAULT);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__SOURCE:
				setSource(SOURCE_EDEFAULT);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__LEGISLATIVE_PERIOD:
				setLegislativePeriod(LEGISLATIVE_PERIOD_EDEFAULT);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__DATE:
				setDate(DATE_EDEFAULT);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__TOTAL_PAGES:
				setTotalPages(TOTAL_PAGES_EDEFAULT);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__ISSUING_AUTHORITY:
				setIssuingAuthority(ISSUING_AUTHORITY_EDEFAULT);
				return;
			case TrendAnalysisPackage.DOCUMENT_METADATA__ANALYSIS_DATE:
				setAnalysisDate(ANALYSIS_DATE_EDEFAULT);
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
			case TrendAnalysisPackage.DOCUMENT_METADATA__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case TrendAnalysisPackage.DOCUMENT_METADATA__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case TrendAnalysisPackage.DOCUMENT_METADATA__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TrendAnalysisPackage.DOCUMENT_METADATA__SOURCE:
				return SOURCE_EDEFAULT == null ? source != null : !SOURCE_EDEFAULT.equals(source);
			case TrendAnalysisPackage.DOCUMENT_METADATA__LEGISLATIVE_PERIOD:
				return LEGISLATIVE_PERIOD_EDEFAULT == null ? legislativePeriod != null : !LEGISLATIVE_PERIOD_EDEFAULT.equals(legislativePeriod);
			case TrendAnalysisPackage.DOCUMENT_METADATA__DATE:
				return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
			case TrendAnalysisPackage.DOCUMENT_METADATA__TOTAL_PAGES:
				return totalPages != TOTAL_PAGES_EDEFAULT;
			case TrendAnalysisPackage.DOCUMENT_METADATA__ISSUING_AUTHORITY:
				return ISSUING_AUTHORITY_EDEFAULT == null ? issuingAuthority != null : !ISSUING_AUTHORITY_EDEFAULT.equals(issuingAuthority);
			case TrendAnalysisPackage.DOCUMENT_METADATA__ANALYSIS_DATE:
				return ANALYSIS_DATE_EDEFAULT == null ? analysisDate != null : !ANALYSIS_DATE_EDEFAULT.equals(analysisDate);
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
		result.append(" (id: ");
		result.append(id);
		result.append(", title: ");
		result.append(title);
		result.append(", name: ");
		result.append(name);
		result.append(", source: ");
		result.append(source);
		result.append(", legislativePeriod: ");
		result.append(legislativePeriod);
		result.append(", date: ");
		result.append(date);
		result.append(", totalPages: ");
		result.append(totalPages);
		result.append(", issuingAuthority: ");
		result.append(issuingAuthority);
		result.append(", analysisDate: ");
		result.append(analysisDate);
		result.append(')');
		return result.toString();
	}

} //DocumentMetadataImpl
