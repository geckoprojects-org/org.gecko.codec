/*
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.codec.test.codectest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.test.codectest.BusinessPerson#getPosition <em>Position</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.test.codectest.CodecTestPackage#getBusinessPerson()
 * @model extendedMetaData="name='EMDBusinessPerson'"
 *        annotation="http://geckoprojects.org/codec/1.0.0 name='CodecBusinessPerson'"
 * @generated
 */
@ProviderType
public interface BusinessPerson extends Person {
	/**
	 * Returns the value of the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' attribute.
	 * @see #setPosition(String)
	 * @see org.gecko.codec.test.codectest.CodecTestPackage#getBusinessPerson_Position()
	 * @model
	 * @generated
	 */
	String getPosition();

	/**
	 * Sets the value of the '{@link org.gecko.codec.test.codectest.BusinessPerson#getPosition <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' attribute.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(String value);

} // BusinessPerson
