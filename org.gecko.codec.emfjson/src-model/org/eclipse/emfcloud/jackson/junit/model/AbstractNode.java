/*
 */
package org.eclipse.emfcloud.jackson.junit.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChild <em>Child</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChildResolving <em>Child Resolving</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParentResolving <em>Parent Resolving</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractNode()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface AbstractNode extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractNode_Label()
	 * @model unique="false"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Child</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractNode_Child()
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<AbstractNode> getChild();

	/**
	 * Returns the value of the '<em><b>Child Resolving</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParentResolving <em>Parent Resolving</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Resolving</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractNode_ChildResolving()
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParentResolving
	 * @model opposite="parentResolving" containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<AbstractNode> getChildResolving();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(AbstractNode)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractNode_Parent()
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChild
	 * @model opposite="child" transient="false"
	 * @generated
	 */
	AbstractNode getParent();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(AbstractNode value);

	/**
	 * Returns the value of the '<em><b>Parent Resolving</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChildResolving <em>Child Resolving</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Resolving</em>' container reference.
	 * @see #setParentResolving(AbstractNode)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getAbstractNode_ParentResolving()
	 * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getChildResolving
	 * @model opposite="childResolving" transient="false"
	 * @generated
	 */
	AbstractNode getParentResolving();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode#getParentResolving <em>Parent Resolving</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Resolving</em>' container reference.
	 * @see #getParentResolving()
	 * @generated
	 */
	void setParentResolving(AbstractNode value);

} // AbstractNode
