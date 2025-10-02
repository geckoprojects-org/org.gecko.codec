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
package org.eclipse.fennec.codec.test.models.metadata.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.fennec.codec.test.models.metadata.BusinessPerson;
import org.eclipse.fennec.codec.test.models.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Business Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BusinessPersonImpl extends PersonImpl implements BusinessPerson {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BusinessPersonImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.BUSINESS_PERSON;
	}

} //BusinessPersonImpl
