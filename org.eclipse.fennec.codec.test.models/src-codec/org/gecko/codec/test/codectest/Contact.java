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

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.test.codectest.Contact#getValue <em>Value</em>}</li>
 *   <li>{@link org.gecko.codec.test.codectest.Contact#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.test.codectest.CodecTestPackage#getContact()
 * @model annotation="foo name='CustomContact'"
 * @generated
 */
@ProviderType
public interface Contact extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.gecko.codec.test.codectest.CodecTestPackage#getContact_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.gecko.codec.test.codectest.Contact#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"Phone"</code>.
	 * The literals are from the enumeration {@link org.gecko.codec.test.codectest.ContactType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.gecko.codec.test.codectest.ContactType
	 * @see #setType(ContactType)
	 * @see org.gecko.codec.test.codectest.CodecTestPackage#getContact_Type()
	 * @model default="Phone"
	 * @generated
	 */
	ContactType getType();

	/**
	 * Sets the value of the '{@link org.gecko.codec.test.codectest.Contact#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.gecko.codec.test.codectest.ContactType
	 * @see #getType()
	 * @generated
	 */
	void setType(ContactType value);

} // Contact
