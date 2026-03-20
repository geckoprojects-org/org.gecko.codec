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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.StempelCategoryType;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.Trend;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trend</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getTimeHorizon <em>Time Horizon</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getPrimaryStempelCategory <em>Primary Stempel Category</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getSecondaryStempelCategory <em>Secondary Stempel Category</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getPageReference <em>Page Reference</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getEvidence <em>Evidence</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getImplications <em>Implications</em>}</li>
 *   <li>{@link org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.impl.TrendImpl#getRelatedItemIds <em>Related Item Ids</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TrendImpl extends MinimalEObjectImpl.Container implements Trend {
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
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

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
	 * The default value of the '{@link #getPrimaryStempelCategory() <em>Primary Stempel Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryStempelCategory()
	 * @generated
	 * @ordered
	 */
	protected static final StempelCategoryType PRIMARY_STEMPEL_CATEGORY_EDEFAULT = StempelCategoryType.SOCIAL;

	/**
	 * The cached value of the '{@link #getPrimaryStempelCategory() <em>Primary Stempel Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryStempelCategory()
	 * @generated
	 * @ordered
	 */
	protected StempelCategoryType primaryStempelCategory = PRIMARY_STEMPEL_CATEGORY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSecondaryStempelCategory() <em>Secondary Stempel Category</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryStempelCategory()
	 * @generated
	 * @ordered
	 */
	protected EList<StempelCategoryType> secondaryStempelCategory;

	/**
	 * The cached value of the '{@link #getPageReference() <em>Page Reference</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPageReference()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> pageReference;

	/**
	 * The cached value of the '{@link #getEvidence() <em>Evidence</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvidence()
	 * @generated
	 * @ordered
	 */
	protected EList<String> evidence;

	/**
	 * The cached value of the '{@link #getImplications() <em>Implications</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplications()
	 * @generated
	 * @ordered
	 */
	protected EList<String> implications;

	/**
	 * The cached value of the '{@link #getRelatedItemIds() <em>Related Item Ids</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelatedItemIds()
	 * @generated
	 * @ordered
	 */
	protected EList<String> relatedItemIds;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TrendImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TrendAnalysisPackage.Literals.TREND;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND__TIME_HORIZON, oldTimeHorizon, timeHorizon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StempelCategoryType getPrimaryStempelCategory() {
		return primaryStempelCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPrimaryStempelCategory(StempelCategoryType newPrimaryStempelCategory) {
		StempelCategoryType oldPrimaryStempelCategory = primaryStempelCategory;
		primaryStempelCategory = newPrimaryStempelCategory == null ? PRIMARY_STEMPEL_CATEGORY_EDEFAULT : newPrimaryStempelCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TrendAnalysisPackage.TREND__PRIMARY_STEMPEL_CATEGORY, oldPrimaryStempelCategory, primaryStempelCategory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<StempelCategoryType> getSecondaryStempelCategory() {
		if (secondaryStempelCategory == null) {
			secondaryStempelCategory = new EDataTypeUniqueEList<StempelCategoryType>(StempelCategoryType.class, this, TrendAnalysisPackage.TREND__SECONDARY_STEMPEL_CATEGORY);
		}
		return secondaryStempelCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getPageReference() {
		if (pageReference == null) {
			pageReference = new EDataTypeUniqueEList<Integer>(Integer.class, this, TrendAnalysisPackage.TREND__PAGE_REFERENCE);
		}
		return pageReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getEvidence() {
		if (evidence == null) {
			evidence = new EDataTypeUniqueEList<String>(String.class, this, TrendAnalysisPackage.TREND__EVIDENCE);
		}
		return evidence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getImplications() {
		if (implications == null) {
			implications = new EDataTypeUniqueEList<String>(String.class, this, TrendAnalysisPackage.TREND__IMPLICATIONS);
		}
		return implications;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getRelatedItemIds() {
		if (relatedItemIds == null) {
			relatedItemIds = new EDataTypeUniqueEList<String>(String.class, this, TrendAnalysisPackage.TREND__RELATED_ITEM_IDS);
		}
		return relatedItemIds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TrendAnalysisPackage.TREND__ID:
				return getId();
			case TrendAnalysisPackage.TREND__TITLE:
				return getTitle();
			case TrendAnalysisPackage.TREND__DESCRIPTION:
				return getDescription();
			case TrendAnalysisPackage.TREND__TIME_HORIZON:
				return getTimeHorizon();
			case TrendAnalysisPackage.TREND__PRIMARY_STEMPEL_CATEGORY:
				return getPrimaryStempelCategory();
			case TrendAnalysisPackage.TREND__SECONDARY_STEMPEL_CATEGORY:
				return getSecondaryStempelCategory();
			case TrendAnalysisPackage.TREND__PAGE_REFERENCE:
				return getPageReference();
			case TrendAnalysisPackage.TREND__EVIDENCE:
				return getEvidence();
			case TrendAnalysisPackage.TREND__IMPLICATIONS:
				return getImplications();
			case TrendAnalysisPackage.TREND__RELATED_ITEM_IDS:
				return getRelatedItemIds();
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
			case TrendAnalysisPackage.TREND__ID:
				setId((String)newValue);
				return;
			case TrendAnalysisPackage.TREND__TITLE:
				setTitle((String)newValue);
				return;
			case TrendAnalysisPackage.TREND__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TrendAnalysisPackage.TREND__TIME_HORIZON:
				setTimeHorizon((String)newValue);
				return;
			case TrendAnalysisPackage.TREND__PRIMARY_STEMPEL_CATEGORY:
				setPrimaryStempelCategory((StempelCategoryType)newValue);
				return;
			case TrendAnalysisPackage.TREND__SECONDARY_STEMPEL_CATEGORY:
				getSecondaryStempelCategory().clear();
				getSecondaryStempelCategory().addAll((Collection<? extends StempelCategoryType>)newValue);
				return;
			case TrendAnalysisPackage.TREND__PAGE_REFERENCE:
				getPageReference().clear();
				getPageReference().addAll((Collection<? extends Integer>)newValue);
				return;
			case TrendAnalysisPackage.TREND__EVIDENCE:
				getEvidence().clear();
				getEvidence().addAll((Collection<? extends String>)newValue);
				return;
			case TrendAnalysisPackage.TREND__IMPLICATIONS:
				getImplications().clear();
				getImplications().addAll((Collection<? extends String>)newValue);
				return;
			case TrendAnalysisPackage.TREND__RELATED_ITEM_IDS:
				getRelatedItemIds().clear();
				getRelatedItemIds().addAll((Collection<? extends String>)newValue);
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
			case TrendAnalysisPackage.TREND__ID:
				setId(ID_EDEFAULT);
				return;
			case TrendAnalysisPackage.TREND__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case TrendAnalysisPackage.TREND__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TrendAnalysisPackage.TREND__TIME_HORIZON:
				setTimeHorizon(TIME_HORIZON_EDEFAULT);
				return;
			case TrendAnalysisPackage.TREND__PRIMARY_STEMPEL_CATEGORY:
				setPrimaryStempelCategory(PRIMARY_STEMPEL_CATEGORY_EDEFAULT);
				return;
			case TrendAnalysisPackage.TREND__SECONDARY_STEMPEL_CATEGORY:
				getSecondaryStempelCategory().clear();
				return;
			case TrendAnalysisPackage.TREND__PAGE_REFERENCE:
				getPageReference().clear();
				return;
			case TrendAnalysisPackage.TREND__EVIDENCE:
				getEvidence().clear();
				return;
			case TrendAnalysisPackage.TREND__IMPLICATIONS:
				getImplications().clear();
				return;
			case TrendAnalysisPackage.TREND__RELATED_ITEM_IDS:
				getRelatedItemIds().clear();
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
			case TrendAnalysisPackage.TREND__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case TrendAnalysisPackage.TREND__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case TrendAnalysisPackage.TREND__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TrendAnalysisPackage.TREND__TIME_HORIZON:
				return TIME_HORIZON_EDEFAULT == null ? timeHorizon != null : !TIME_HORIZON_EDEFAULT.equals(timeHorizon);
			case TrendAnalysisPackage.TREND__PRIMARY_STEMPEL_CATEGORY:
				return primaryStempelCategory != PRIMARY_STEMPEL_CATEGORY_EDEFAULT;
			case TrendAnalysisPackage.TREND__SECONDARY_STEMPEL_CATEGORY:
				return secondaryStempelCategory != null && !secondaryStempelCategory.isEmpty();
			case TrendAnalysisPackage.TREND__PAGE_REFERENCE:
				return pageReference != null && !pageReference.isEmpty();
			case TrendAnalysisPackage.TREND__EVIDENCE:
				return evidence != null && !evidence.isEmpty();
			case TrendAnalysisPackage.TREND__IMPLICATIONS:
				return implications != null && !implications.isEmpty();
			case TrendAnalysisPackage.TREND__RELATED_ITEM_IDS:
				return relatedItemIds != null && !relatedItemIds.isEmpty();
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
		result.append(", description: ");
		result.append(description);
		result.append(", timeHorizon: ");
		result.append(timeHorizon);
		result.append(", primaryStempelCategory: ");
		result.append(primaryStempelCategory);
		result.append(", secondaryStempelCategory: ");
		result.append(secondaryStempelCategory);
		result.append(", pageReference: ");
		result.append(pageReference);
		result.append(", evidence: ");
		result.append(evidence);
		result.append(", implications: ");
		result.append(implications);
		result.append(", relatedItemIds: ");
		result.append(relatedItemIds);
		result.append(')');
		return result.toString();
	}

} //TrendImpl
