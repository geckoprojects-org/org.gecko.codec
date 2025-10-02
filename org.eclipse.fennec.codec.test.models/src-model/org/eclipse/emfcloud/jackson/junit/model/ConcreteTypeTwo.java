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
package org.eclipse.emfcloud.jackson.junit.model;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concrete Type Two</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo#getPropTypeTwo <em>Prop Type Two</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getConcreteTypeTwo()
 * @model
 * @generated
 */
@ProviderType
public interface ConcreteTypeTwo extends AbstractType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2012 - 2024 Data In Motion and others.\nAll rights reserved.\n\nThis program and the accompanying materials are made\navailable under the terms of the Eclipse Public License 2.0\nwhich is available at https://www.eclipse.org/legal/epl-2.0/\n\nSPDX-License-Identifier: EPL-2.0\n\nContributors:\n    Data In Motion - initial API and implementation\n";

	/**
	 * Returns the value of the '<em><b>Prop Type Two</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prop Type Two</em>' attribute.
	 * @see #setPropTypeTwo(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getConcreteTypeTwo_PropTypeTwo()
	 * @model unique="false"
	 * @generated
	 */
	String getPropTypeTwo();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo#getPropTypeTwo <em>Prop Type Two</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prop Type Two</em>' attribute.
	 * @see #getPropTypeTwo()
	 * @generated
	 */
	void setPropTypeTwo(String value);

} // ConcreteTypeTwo
