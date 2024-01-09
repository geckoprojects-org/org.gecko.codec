/*
 */
package org.eclipse.emfcloud.jackson.junit.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.jackson.junit.model.AbstractNode;
import org.eclipse.emfcloud.jackson.junit.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl#getChild <em>Child</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl#getChildResolving <em>Child Resolving</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.AbstractNodeImpl#getParentResolving <em>Parent Resolving</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractNodeImpl extends MinimalEObjectImpl implements AbstractNode {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChild() <em>Child</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChild()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractNode> child;

	/**
	 * The cached value of the '{@link #getChildResolving() <em>Child Resolving</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildResolving()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractNode> childResolving;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ABSTRACT_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ABSTRACT_NODE__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractNode> getChild() {
		if (child == null) {
			child = new EObjectContainmentWithInverseEList<AbstractNode>(AbstractNode.class, this, ModelPackage.ABSTRACT_NODE__CHILD, ModelPackage.ABSTRACT_NODE__PARENT);
		}
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractNode> getChildResolving() {
		if (childResolving == null) {
			childResolving = new EObjectContainmentWithInverseEList.Resolving<AbstractNode>(AbstractNode.class, this, ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING, ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING);
		}
		return childResolving;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractNode getParent() {
		if (eContainerFeatureID() != ModelPackage.ABSTRACT_NODE__PARENT) return null;
		return (AbstractNode)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractNode basicGetParent() {
		if (eContainerFeatureID() != ModelPackage.ABSTRACT_NODE__PARENT) return null;
		return (AbstractNode)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(AbstractNode newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, ModelPackage.ABSTRACT_NODE__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParent(AbstractNode newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != ModelPackage.ABSTRACT_NODE__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, ModelPackage.ABSTRACT_NODE__CHILD, AbstractNode.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ABSTRACT_NODE__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractNode getParentResolving() {
		if (eContainerFeatureID() != ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING) return null;
		return (AbstractNode)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractNode basicGetParentResolving() {
		if (eContainerFeatureID() != ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING) return null;
		return (AbstractNode)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentResolving(AbstractNode newParentResolving, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentResolving, ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParentResolving(AbstractNode newParentResolving) {
		if (newParentResolving != eInternalContainer() || (eContainerFeatureID() != ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING && newParentResolving != null)) {
			if (EcoreUtil.isAncestor(this, newParentResolving))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentResolving != null)
				msgs = ((InternalEObject)newParentResolving).eInverseAdd(this, ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING, AbstractNode.class, msgs);
			msgs = basicSetParentResolving(newParentResolving, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING, newParentResolving, newParentResolving));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ABSTRACT_NODE__CHILD:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChild()).basicAdd(otherEnd, msgs);
			case ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildResolving()).basicAdd(otherEnd, msgs);
			case ModelPackage.ABSTRACT_NODE__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((AbstractNode)otherEnd, msgs);
			case ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentResolving((AbstractNode)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ABSTRACT_NODE__CHILD:
				return ((InternalEList<?>)getChild()).basicRemove(otherEnd, msgs);
			case ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING:
				return ((InternalEList<?>)getChildResolving()).basicRemove(otherEnd, msgs);
			case ModelPackage.ABSTRACT_NODE__PARENT:
				return basicSetParent(null, msgs);
			case ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING:
				return basicSetParentResolving(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ModelPackage.ABSTRACT_NODE__PARENT:
				return eInternalContainer().eInverseRemove(this, ModelPackage.ABSTRACT_NODE__CHILD, AbstractNode.class, msgs);
			case ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING:
				return eInternalContainer().eInverseRemove(this, ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING, AbstractNode.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.ABSTRACT_NODE__LABEL:
				return getLabel();
			case ModelPackage.ABSTRACT_NODE__CHILD:
				return getChild();
			case ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING:
				return getChildResolving();
			case ModelPackage.ABSTRACT_NODE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING:
				if (resolve) return getParentResolving();
				return basicGetParentResolving();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.ABSTRACT_NODE__LABEL:
				setLabel((String)newValue);
				return;
			case ModelPackage.ABSTRACT_NODE__CHILD:
				getChild().clear();
				getChild().addAll((Collection<? extends AbstractNode>)newValue);
				return;
			case ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING:
				getChildResolving().clear();
				getChildResolving().addAll((Collection<? extends AbstractNode>)newValue);
				return;
			case ModelPackage.ABSTRACT_NODE__PARENT:
				setParent((AbstractNode)newValue);
				return;
			case ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING:
				setParentResolving((AbstractNode)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.ABSTRACT_NODE__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case ModelPackage.ABSTRACT_NODE__CHILD:
				getChild().clear();
				return;
			case ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING:
				getChildResolving().clear();
				return;
			case ModelPackage.ABSTRACT_NODE__PARENT:
				setParent((AbstractNode)null);
				return;
			case ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING:
				setParentResolving((AbstractNode)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.ABSTRACT_NODE__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ModelPackage.ABSTRACT_NODE__CHILD:
				return child != null && !child.isEmpty();
			case ModelPackage.ABSTRACT_NODE__CHILD_RESOLVING:
				return childResolving != null && !childResolving.isEmpty();
			case ModelPackage.ABSTRACT_NODE__PARENT:
				return basicGetParent() != null;
			case ModelPackage.ABSTRACT_NODE__PARENT_RESOLVING:
				return basicGetParentResolving() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} //AbstractNodeImpl
