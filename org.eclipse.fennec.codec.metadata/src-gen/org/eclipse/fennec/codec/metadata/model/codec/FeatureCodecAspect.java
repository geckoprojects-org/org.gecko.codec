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
package org.eclipse.fennec.codec.metadata.model.codec;

import org.eclipse.fennec.model.metadata.FeatureAspect;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Codec Aspect</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Codec aspect for EStructuralFeature serialization configuration. Attached to FeatureMetadata.aspects.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getEffectiveKey <em>Effective Key</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerialize <em>Serialize</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeNull <em>Serialize Null</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeEmpty <em>Serialize Empty</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeDefaults <em>Serialize Defaults</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getValueWriterName <em>Value Writer Name</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getValueReaderName <em>Value Reader Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getFeatureCodecAspect()
 * @model
 * @generated
 */
@ProviderType
public interface FeatureCodecAspect extends FeatureAspect {
	/**
	 * Returns the value of the '<em><b>Effective Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Resolved JSON property key for this feature.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Effective Key</em>' attribute.
	 * @see #setEffectiveKey(String)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getFeatureCodecAspect_EffectiveKey()
	 * @model
	 * @generated
	 */
	String getEffectiveKey();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getEffectiveKey <em>Effective Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Effective Key</em>' attribute.
	 * @see #getEffectiveKey()
	 * @generated
	 */
	void setEffectiveKey(String value);

	/**
	 * Returns the value of the '<em><b>Serialize</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether to serialize this feature (false = transient).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize</em>' attribute.
	 * @see #setSerialize(boolean)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getFeatureCodecAspect_Serialize()
	 * @model default="true"
	 * @generated
	 */
	boolean isSerialize();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerialize <em>Serialize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize</em>' attribute.
	 * @see #isSerialize()
	 * @generated
	 */
	void setSerialize(boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Null</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether to serialize null values.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Null</em>' attribute.
	 * @see #setSerializeNull(boolean)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getFeatureCodecAspect_SerializeNull()
	 * @model default="false"
	 * @generated
	 */
	boolean isSerializeNull();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeNull <em>Serialize Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Null</em>' attribute.
	 * @see #isSerializeNull()
	 * @generated
	 */
	void setSerializeNull(boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Empty</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether to serialize empty collections.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Empty</em>' attribute.
	 * @see #setSerializeEmpty(boolean)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getFeatureCodecAspect_SerializeEmpty()
	 * @model default="false"
	 * @generated
	 */
	boolean isSerializeEmpty();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeEmpty <em>Serialize Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Empty</em>' attribute.
	 * @see #isSerializeEmpty()
	 * @generated
	 */
	void setSerializeEmpty(boolean value);

	/**
	 * Returns the value of the '<em><b>Serialize Defaults</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether to serialize default values.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Serialize Defaults</em>' attribute.
	 * @see #setSerializeDefaults(boolean)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getFeatureCodecAspect_SerializeDefaults()
	 * @model default="false"
	 * @generated
	 */
	boolean isSerializeDefaults();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeDefaults <em>Serialize Defaults</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialize Defaults</em>' attribute.
	 * @see #isSerializeDefaults()
	 * @generated
	 */
	void setSerializeDefaults(boolean value);

	/**
	 * Returns the value of the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Custom value writer name for serialization.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value Writer Name</em>' attribute.
	 * @see #setValueWriterName(String)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getFeatureCodecAspect_ValueWriterName()
	 * @model
	 * @generated
	 */
	String getValueWriterName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getValueWriterName <em>Value Writer Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Writer Name</em>' attribute.
	 * @see #getValueWriterName()
	 * @generated
	 */
	void setValueWriterName(String value);

	/**
	 * Returns the value of the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Custom value reader name for deserialization.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value Reader Name</em>' attribute.
	 * @see #setValueReaderName(String)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getFeatureCodecAspect_ValueReaderName()
	 * @model
	 * @generated
	 */
	String getValueReaderName();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getValueReaderName <em>Value Reader Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Reader Name</em>' attribute.
	 * @see #getValueReaderName()
	 * @generated
	 */
	void setValueReaderName(String value);

} // FeatureCodecAspect
