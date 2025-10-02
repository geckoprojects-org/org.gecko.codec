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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Node#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Node#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Node#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Node#getManyRef <em>Many Ref</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Node#getChild <em>Child</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Node#getUniqueChild <em>Unique Child</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getNode()
 * @model
 * @generated
 */
@ProviderType
public interface Node extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2012 - 2024 Data In Motion and others.\nAll rights reserved.\n\nThis program and the accompanying materials are made\navailable under the terms of the Eclipse Public License 2.0\nwhich is available at https://www.eclipse.org/legal/epl-2.0/\n\nSPDX-License-Identifier: EPL-2.0\n\nContributors:\n    Data In Motion - initial API and implementation\n";

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getNode_Label()
	 * @model unique="false"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Node)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getNode_Target()
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node#getSource
	 * @model opposite="source" resolveProxies="false"
	 * @generated
	 */
	Node getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Node value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Node)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getNode_Source()
	 * @see org.eclipse.emfcloud.jackson.junit.model.Node#getTarget
	 * @model opposite="target" resolveProxies="false"
	 * @generated
	 */
	Node getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Node value);

	/**
	 * Returns the value of the '<em><b>Many Ref</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.Node}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many Ref</em>' reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getNode_ManyRef()
	 * @model resolveProxies="false"
	 * @generated
	 */
	EList<Node> getManyRef();

	/**
	 * Returns the value of the '<em><b>Child</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.Node}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getNode_Child()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Node> getChild();

	/**
	 * Returns the value of the '<em><b>Unique Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Child</em>' containment reference.
	 * @see #setUniqueChild(Node)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getNode_UniqueChild()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	Node getUniqueChild();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Node#getUniqueChild <em>Unique Child</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Child</em>' containment reference.
	 * @see #getUniqueChild()
	 * @generated
	 */
	void setUniqueChild(Node value);

} // Node
