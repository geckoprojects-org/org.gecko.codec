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
 *     Data In Motion - initial API and implementation
 * 
 */
package org.eclipse.fennec.model.metadata;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Id Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base configuration for ID serialization.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getStrategy <em>Strategy</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getKeyMode <em>Key Mode</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getFormat <em>Format</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getIdKey <em>Id Key</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getSeparator <em>Separator</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseIdConfig()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface BaseIdConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' attribute.
	 * The default value is <code>"ID_FIELD"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.metadata.IdStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Strategy for determining ID features.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.IdStrategy
	 * @see #setStrategy(IdStrategy)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseIdConfig_Strategy()
	 * @model default="ID_FIELD"
	 * @generated
	 */
	IdStrategy getStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.IdStrategy
	 * @see #getStrategy()
	 * @generated
	 */
	void setStrategy(IdStrategy value);

	/**
	 * Returns the value of the '<em><b>Key Mode</b></em>' attribute.
	 * The default value is <code>"ID_ONLY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.metadata.IdKeyMode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * How ID is represented in output.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Key Mode</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.IdKeyMode
	 * @see #setKeyMode(IdKeyMode)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseIdConfig_KeyMode()
	 * @model default="ID_ONLY"
	 * @generated
	 */
	IdKeyMode getKeyMode();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getKeyMode <em>Key Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Mode</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.IdKeyMode
	 * @see #getKeyMode()
	 * @generated
	 */
	void setKeyMode(IdKeyMode value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * The default value is <code>"PLAIN"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.metadata.SerializationFormat}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Serialization format for ID.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.SerializationFormat
	 * @see #setFormat(SerializationFormat)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseIdConfig_Format()
	 * @model default="PLAIN"
	 * @generated
	 */
	SerializationFormat getFormat();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.SerializationFormat
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(SerializationFormat value);

	/**
	 * Returns the value of the '<em><b>Id Key</b></em>' attribute.
	 * The default value is <code>"_id"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * JSON property name for ID.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id Key</em>' attribute.
	 * @see #setIdKey(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseIdConfig_IdKey()
	 * @model default="_id"
	 * @generated
	 */
	String getIdKey();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getIdKey <em>Id Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Key</em>' attribute.
	 * @see #getIdKey()
	 * @generated
	 */
	void setIdKey(String value);

	/**
	 * Returns the value of the '<em><b>Separator</b></em>' attribute.
	 * The default value is <code>"-"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Separator for combining multiple ID features.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Separator</em>' attribute.
	 * @see #setSeparator(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseIdConfig_Separator()
	 * @model default="-"
	 * @generated
	 */
	String getSeparator();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseIdConfig#getSeparator <em>Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Separator</em>' attribute.
	 * @see #getSeparator()
	 * @generated
	 */
	void setSeparator(String value);

} // BaseIdConfig
