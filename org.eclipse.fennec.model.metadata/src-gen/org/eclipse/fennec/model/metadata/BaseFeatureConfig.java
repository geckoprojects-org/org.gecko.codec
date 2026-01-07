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
 * A representation of the model object '<em><b>Base Feature Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base configuration for feature serialization.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getSerialize <em>Serialize</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getSerializeNull <em>Serialize Null</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getSerializeEmpty <em>Serialize Empty</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getSerializeDefaults <em>Serialize Defaults</em>}</li>
 *   <li>{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getEnumSerialization <em>Enum Serialization</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseFeatureConfig()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface BaseFeatureConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Custom JSON property name (null = use feature name).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseFeatureConfig_Key()
	 * @model
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Serialize</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether to serialize this feature (null = true, false = transient).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize</em>' attribute.
	 * @see #setSerialize(Boolean)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseFeatureConfig_Serialize()
	 * @model
	 * @generated
	 */
	Boolean getSerialize();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getSerialize <em>Serialize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize</em>' attribute.
	 * @see #getSerialize()
	 * @generated
	 */
	void setSerialize(Boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Serialize null values (null = use codec default).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Null</em>' attribute.
	 * @see #setSerializeNull(Boolean)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseFeatureConfig_SerializeNull()
	 * @model
	 * @generated
	 */
	Boolean getSerializeNull();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getSerializeNull <em>Serialize Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Null</em>' attribute.
	 * @see #getSerializeNull()
	 * @generated
	 */
	void setSerializeNull(Boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Serialize empty collections (null = use codec default).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Empty</em>' attribute.
	 * @see #setSerializeEmpty(Boolean)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseFeatureConfig_SerializeEmpty()
	 * @model
	 * @generated
	 */
	Boolean getSerializeEmpty();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getSerializeEmpty <em>Serialize Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Empty</em>' attribute.
	 * @see #getSerializeEmpty()
	 * @generated
	 */
	void setSerializeEmpty(Boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Defaults</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Serialize default values (null = use codec default).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Defaults</em>' attribute.
	 * @see #setSerializeDefaults(Boolean)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseFeatureConfig_SerializeDefaults()
	 * @model
	 * @generated
	 */
	Boolean getSerializeDefaults();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getSerializeDefaults <em>Serialize Defaults</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Defaults</em>' attribute.
	 * @see #getSerializeDefaults()
	 * @generated
	 */
	void setSerializeDefaults(Boolean value);

	/**
	 * Returns the value of the '<em><b>Enum Serialization</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.fennec.model.metadata.EnumSerializationStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Strategy for serializing enum values (null = use LITERAL default).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enum Serialization</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.EnumSerializationStrategy
	 * @see #setEnumSerialization(EnumSerializationStrategy)
	 * @see org.eclipse.fennec.model.metadata.MetadataPackage#getBaseFeatureConfig_EnumSerialization()
	 * @model
	 * @generated
	 */
	EnumSerializationStrategy getEnumSerialization();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.model.metadata.BaseFeatureConfig#getEnumSerialization <em>Enum Serialization</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enum Serialization</em>' attribute.
	 * @see org.eclipse.fennec.model.metadata.EnumSerializationStrategy
	 * @see #getEnumSerialization()
	 * @generated
	 */
	void setEnumSerialization(EnumSerializationStrategy value);

} // BaseFeatureConfig
