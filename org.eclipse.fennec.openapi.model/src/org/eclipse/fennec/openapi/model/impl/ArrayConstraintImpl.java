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
package org.eclipse.fennec.openapi.model.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.fennec.openapi.model.ArrayConstraint;
import org.eclipse.fennec.openapi.model.OpenApiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Array Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ArrayConstraintImpl extends ConstraintImpl implements ArrayConstraint {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArrayConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpenApiPackage.Literals.ARRAY_CONSTRAINT;
	}

} //ArrayConstraintImpl
