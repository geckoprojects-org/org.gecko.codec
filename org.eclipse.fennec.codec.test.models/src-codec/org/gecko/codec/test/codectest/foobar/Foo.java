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
package org.gecko.codec.test.codectest.foobar;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Foo</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.test.codectest.foobar.Foo#getBar <em>Bar</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.test.codectest.foobar.FoobarPackage#getFoo()
 * @model
 * @generated
 */
@ProviderType
public interface Foo extends EObject {
	/**
	 * Returns the value of the '<em><b>Bar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bar</em>' attribute.
	 * @see #setBar(int)
	 * @see org.gecko.codec.test.codectest.foobar.FoobarPackage#getFoo_Bar()
	 * @model
	 * @generated
	 */
	int getBar();

	/**
	 * Sets the value of the '{@link org.gecko.codec.test.codectest.foobar.Foo#getBar <em>Bar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bar</em>' attribute.
	 * @see #getBar()
	 * @generated
	 */
	void setBar(int value);

} // Foo
