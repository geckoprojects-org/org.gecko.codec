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

import org.eclipse.emf.common.util.EList;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference Codec Aspect</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Codec aspect for EReference serialization configuration. Extends FeatureCodecAspect with reference-specific settings.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getReferenceConfig <em>Reference Config</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getTypeConfig <em>Type Config</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#isInheritTypeFromTarget <em>Inherit Type From Target</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#isExpand <em>Expand</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getInlineTypeMappings <em>Inline Type Mappings</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getFallbackStrategy <em>Fallback Strategy</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getFallbackEClass <em>Fallback EClass</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getReferenceCodecAspect()
 * @model
 * @generated
 */
@ProviderType
public interface ReferenceCodecAspect extends FeatureCodecAspect {
	/**
	 * Returns the value of the '<em><b>Reference Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Reference serialization configuration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reference Config</em>' containment reference.
	 * @see #setReferenceConfig(ReferenceSerializationConfig)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getReferenceCodecAspect_ReferenceConfig()
	 * @model containment="true"
	 * @generated
	 */
	ReferenceSerializationConfig getReferenceConfig();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getReferenceConfig <em>Reference Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference Config</em>' containment reference.
	 * @see #getReferenceConfig()
	 * @generated
	 */
	void setReferenceConfig(ReferenceSerializationConfig value);

	/**
	 * Returns the value of the '<em><b>Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type configuration for objects accessed via this reference.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Config</em>' containment reference.
	 * @see #setTypeConfig(TypeSerializationConfig)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getReferenceCodecAspect_TypeConfig()
	 * @model containment="true"
	 * @generated
	 */
	TypeSerializationConfig getTypeConfig();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getTypeConfig <em>Type Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Config</em>' containment reference.
	 * @see #getTypeConfig()
	 * @generated
	 */
	void setTypeConfig(TypeSerializationConfig value);

	/**
	 * Returns the value of the '<em><b>Inherit Type From Target</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether to inherit type config from target EClass when not explicitly set.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Inherit Type From Target</em>' attribute.
	 * @see #setInheritTypeFromTarget(boolean)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getReferenceCodecAspect_InheritTypeFromTarget()
	 * @model default="true"
	 * @generated
	 */
	boolean isInheritTypeFromTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#isInheritTypeFromTarget <em>Inherit Type From Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inherit Type From Target</em>' attribute.
	 * @see #isInheritTypeFromTarget()
	 * @generated
	 */
	void setInheritTypeFromTarget(boolean value);

	/**
	 * Returns the value of the '<em><b>Expand</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether to expand (inline) referenced objects.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expand</em>' attribute.
	 * @see #setExpand(boolean)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getReferenceCodecAspect_Expand()
	 * @model default="false"
	 * @generated
	 */
	boolean isExpand();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#isExpand <em>Expand</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expand</em>' attribute.
	 * @see #isExpand()
	 * @generated
	 */
	void setExpand(boolean value);

	/**
	 * Returns the value of the '<em><b>Inline Type Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.codec.metadata.model.codec.InlineTypeMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Inline type mappings for discriminator-based type resolution. Alternative to Named Registry when mappings are simple and static. Configured via EAnnotation details: inlineMapping.{discriminatorValue}={EClass URI}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Inline Type Mappings</em>' containment reference list.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getReferenceCodecAspect_InlineTypeMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<InlineTypeMapping> getInlineTypeMappings();

	/**
	 * Returns the value of the '<em><b>Fallback Strategy</b></em>' attribute.
	 * The default value is <code>"SKIP"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fennec.codec.metadata.model.codec.FallbackStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * What to do when discriminator value from inline mapping is not found. Default is SKIP (log WARNING, continue to Type Strategy).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Fallback Strategy</em>' attribute.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FallbackStrategy
	 * @see #setFallbackStrategy(FallbackStrategy)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getReferenceCodecAspect_FallbackStrategy()
	 * @model default="SKIP"
	 * @generated
	 */
	FallbackStrategy getFallbackStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getFallbackStrategy <em>Fallback Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fallback Strategy</em>' attribute.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FallbackStrategy
	 * @see #getFallbackStrategy()
	 * @generated
	 */
	void setFallbackStrategy(FallbackStrategy value);

	/**
	 * Returns the value of the '<em><b>Fallback EClass</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Explicit fallback EClass URI when inline mapping discriminator value not found. Required when fallbackStrategy is FALLBACK.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Fallback EClass</em>' attribute.
	 * @see #setFallbackEClass(String)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getReferenceCodecAspect_FallbackEClass()
	 * @model
	 * @generated
	 */
	String getFallbackEClass();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getFallbackEClass <em>Fallback EClass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fallback EClass</em>' attribute.
	 * @see #getFallbackEClass()
	 * @generated
	 */
	void setFallbackEClass(String value);

} // ReferenceCodecAspect
