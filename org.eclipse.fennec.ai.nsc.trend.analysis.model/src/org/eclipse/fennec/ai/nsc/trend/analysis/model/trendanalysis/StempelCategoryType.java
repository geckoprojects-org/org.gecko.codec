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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Stempel Category Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage#getStempelCategoryType()
 * @model
 * @generated
 */
@ProviderType
public enum StempelCategoryType implements Enumerator {
	/**
	 * The '<em><b>SOCIAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOCIAL_VALUE
	 * @generated
	 * @ordered
	 */
	SOCIAL(0, "SOCIAL", "SOCIAL"),

	/**
	 * The '<em><b>TECHNOLOGICAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TECHNOLOGICAL_VALUE
	 * @generated
	 * @ordered
	 */
	TECHNOLOGICAL(1, "TECHNOLOGICAL", "TECHNOLOGICAL"),

	/**
	 * The '<em><b>ECONOMIC GEO ECONOMIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ECONOMIC_GEO_ECONOMIC_VALUE
	 * @generated
	 * @ordered
	 */
	ECONOMIC_GEO_ECONOMIC(2, "ECONOMIC_GEO_ECONOMIC", "ECONOMIC_GEO_ECONOMIC"),

	/**
	 * The '<em><b>EXTERNAL SECURITY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXTERNAL_SECURITY_VALUE
	 * @generated
	 * @ordered
	 */
	EXTERNAL_SECURITY(3, "EXTERNAL_SECURITY", "EXTERNAL_SECURITY"),

	/**
	 * The '<em><b>INTERNAL SECURITY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTERNAL_SECURITY_VALUE
	 * @generated
	 * @ordered
	 */
	INTERNAL_SECURITY(4, "INTERNAL_SECURITY", "INTERNAL_SECURITY"),

	/**
	 * The '<em><b>POLITICAL GEO POLITICAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POLITICAL_GEO_POLITICAL_VALUE
	 * @generated
	 * @ordered
	 */
	POLITICAL_GEO_POLITICAL(5, "POLITICAL_GEO_POLITICAL", "POLITICAL_GEO_POLITICAL"),

	/**
	 * The '<em><b>ECOLOGICAL PLANETARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ECOLOGICAL_PLANETARY_VALUE
	 * @generated
	 * @ordered
	 */
	ECOLOGICAL_PLANETARY(6, "ECOLOGICAL_PLANETARY", "ECOLOGICAL_PLANETARY");

	/**
	 * The '<em><b>SOCIAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOCIAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SOCIAL_VALUE = 0;

	/**
	 * The '<em><b>TECHNOLOGICAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TECHNOLOGICAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TECHNOLOGICAL_VALUE = 1;

	/**
	 * The '<em><b>ECONOMIC GEO ECONOMIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ECONOMIC_GEO_ECONOMIC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ECONOMIC_GEO_ECONOMIC_VALUE = 2;

	/**
	 * The '<em><b>EXTERNAL SECURITY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXTERNAL_SECURITY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EXTERNAL_SECURITY_VALUE = 3;

	/**
	 * The '<em><b>INTERNAL SECURITY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTERNAL_SECURITY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INTERNAL_SECURITY_VALUE = 4;

	/**
	 * The '<em><b>POLITICAL GEO POLITICAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POLITICAL_GEO_POLITICAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int POLITICAL_GEO_POLITICAL_VALUE = 5;

	/**
	 * The '<em><b>ECOLOGICAL PLANETARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ECOLOGICAL_PLANETARY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ECOLOGICAL_PLANETARY_VALUE = 6;

	/**
	 * An array of all the '<em><b>Stempel Category Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final StempelCategoryType[] VALUES_ARRAY =
		new StempelCategoryType[] {
			SOCIAL,
			TECHNOLOGICAL,
			ECONOMIC_GEO_ECONOMIC,
			EXTERNAL_SECURITY,
			INTERNAL_SECURITY,
			POLITICAL_GEO_POLITICAL,
			ECOLOGICAL_PLANETARY,
		};

	/**
	 * A public read-only list of all the '<em><b>Stempel Category Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<StempelCategoryType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Stempel Category Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static StempelCategoryType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StempelCategoryType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Stempel Category Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static StempelCategoryType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StempelCategoryType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Stempel Category Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static StempelCategoryType get(int value) {
		switch (value) {
			case SOCIAL_VALUE: return SOCIAL;
			case TECHNOLOGICAL_VALUE: return TECHNOLOGICAL;
			case ECONOMIC_GEO_ECONOMIC_VALUE: return ECONOMIC_GEO_ECONOMIC;
			case EXTERNAL_SECURITY_VALUE: return EXTERNAL_SECURITY;
			case INTERNAL_SECURITY_VALUE: return INTERNAL_SECURITY;
			case POLITICAL_GEO_POLITICAL_VALUE: return POLITICAL_GEO_POLITICAL;
			case ECOLOGICAL_PLANETARY_VALUE: return ECOLOGICAL_PLANETARY;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private StempelCategoryType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //StempelCategoryType
