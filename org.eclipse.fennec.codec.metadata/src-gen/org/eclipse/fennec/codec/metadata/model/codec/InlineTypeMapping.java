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

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inline Type Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Maps a discriminator value to a target EClass for inline type resolution. Used as an alternative to Named Registry when mappings are simple and static.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.InlineTypeMapping#getDiscriminatorValue <em>Discriminator Value</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.InlineTypeMapping#getTargetClass <em>Target Class</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getInlineTypeMapping()
 * @model
 * @generated
 */
@ProviderType
public interface InlineTypeMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The discriminator value from JSON (e.g., 'FooBar').
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Discriminator Value</em>' attribute.
	 * @see #setDiscriminatorValue(String)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getInlineTypeMapping_DiscriminatorValue()
	 * @model
	 * @generated
	 */
	String getDiscriminatorValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.InlineTypeMapping#getDiscriminatorValue <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Value</em>' attribute.
	 * @see #getDiscriminatorValue()
	 * @generated
	 */
	void setDiscriminatorValue(String value);

	/**
	 * Returns the value of the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The target EClass URI (e.g., 'http://example.org#//Friend').
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target Class</em>' attribute.
	 * @see #setTargetClass(String)
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecPackage#getInlineTypeMapping_TargetClass()
	 * @model
	 * @generated
	 */
	String getTargetClass();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.codec.metadata.model.codec.InlineTypeMapping#getTargetClass <em>Target Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Class</em>' attribute.
	 * @see #getTargetClass()
	 * @generated
	 */
	void setTargetClass(String value);

} // InlineTypeMapping
