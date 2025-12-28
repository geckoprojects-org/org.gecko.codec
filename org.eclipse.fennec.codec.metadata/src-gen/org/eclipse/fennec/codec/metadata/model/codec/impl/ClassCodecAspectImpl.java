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
package org.eclipse.fennec.codec.metadata.model.codec.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.CodecPackage;
import org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;

import org.eclipse.fennec.model.metadata.impl.ClassAspectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Codec Aspect</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl#getTypeConfig <em>Type Config</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl#getIdConfig <em>Id Config</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl#getSuperTypeConfig <em>Super Type Config</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl#isInheritFromParent <em>Inherit From Parent</em>}</li>
 *   <li>{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl#getDiscriminatorValue <em>Discriminator Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassCodecAspectImpl extends ClassAspectImpl implements ClassCodecAspect {
	/**
	 * The cached value of the '{@link #getTypeConfig() <em>Type Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeConfig()
	 * @generated
	 * @ordered
	 */
	protected TypeSerializationConfig typeConfig;

	/**
	 * The cached value of the '{@link #getIdConfig() <em>Id Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdConfig()
	 * @generated
	 * @ordered
	 */
	protected IdSerializationConfig idConfig;

	/**
	 * The cached value of the '{@link #getSuperTypeConfig() <em>Super Type Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperTypeConfig()
	 * @generated
	 * @ordered
	 */
	protected SuperTypeSerializationConfig superTypeConfig;

	/**
	 * The default value of the '{@link #isInheritFromParent() <em>Inherit From Parent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInheritFromParent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INHERIT_FROM_PARENT_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isInheritFromParent() <em>Inherit From Parent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInheritFromParent()
	 * @generated
	 * @ordered
	 */
	protected boolean inheritFromParent = INHERIT_FROM_PARENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiscriminatorValue() <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiscriminatorValue() <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected String discriminatorValue = DISCRIMINATOR_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassCodecAspectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecPackage.Literals.CLASS_CODEC_ASPECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeSerializationConfig getTypeConfig() {
		return typeConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeConfig(TypeSerializationConfig newTypeConfig, NotificationChain msgs) {
		TypeSerializationConfig oldTypeConfig = typeConfig;
		typeConfig = newTypeConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG, oldTypeConfig, newTypeConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeConfig(TypeSerializationConfig newTypeConfig) {
		if (newTypeConfig != typeConfig) {
			NotificationChain msgs = null;
			if (typeConfig != null)
				msgs = ((InternalEObject)typeConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG, null, msgs);
			if (newTypeConfig != null)
				msgs = ((InternalEObject)newTypeConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG, null, msgs);
			msgs = basicSetTypeConfig(newTypeConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG, newTypeConfig, newTypeConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdSerializationConfig getIdConfig() {
		return idConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdConfig(IdSerializationConfig newIdConfig, NotificationChain msgs) {
		IdSerializationConfig oldIdConfig = idConfig;
		idConfig = newIdConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG, oldIdConfig, newIdConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdConfig(IdSerializationConfig newIdConfig) {
		if (newIdConfig != idConfig) {
			NotificationChain msgs = null;
			if (idConfig != null)
				msgs = ((InternalEObject)idConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG, null, msgs);
			if (newIdConfig != null)
				msgs = ((InternalEObject)newIdConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG, null, msgs);
			msgs = basicSetIdConfig(newIdConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG, newIdConfig, newIdConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SuperTypeSerializationConfig getSuperTypeConfig() {
		return superTypeConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuperTypeConfig(SuperTypeSerializationConfig newSuperTypeConfig, NotificationChain msgs) {
		SuperTypeSerializationConfig oldSuperTypeConfig = superTypeConfig;
		superTypeConfig = newSuperTypeConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG, oldSuperTypeConfig, newSuperTypeConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSuperTypeConfig(SuperTypeSerializationConfig newSuperTypeConfig) {
		if (newSuperTypeConfig != superTypeConfig) {
			NotificationChain msgs = null;
			if (superTypeConfig != null)
				msgs = ((InternalEObject)superTypeConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG, null, msgs);
			if (newSuperTypeConfig != null)
				msgs = ((InternalEObject)newSuperTypeConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG, null, msgs);
			msgs = basicSetSuperTypeConfig(newSuperTypeConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG, newSuperTypeConfig, newSuperTypeConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInheritFromParent() {
		return inheritFromParent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInheritFromParent(boolean newInheritFromParent) {
		boolean oldInheritFromParent = inheritFromParent;
		inheritFromParent = newInheritFromParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.CLASS_CODEC_ASPECT__INHERIT_FROM_PARENT, oldInheritFromParent, inheritFromParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDiscriminatorValue() {
		return discriminatorValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDiscriminatorValue(String newDiscriminatorValue) {
		String oldDiscriminatorValue = discriminatorValue;
		discriminatorValue = newDiscriminatorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecPackage.CLASS_CODEC_ASPECT__DISCRIMINATOR_VALUE, oldDiscriminatorValue, discriminatorValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG:
				return basicSetTypeConfig(null, msgs);
			case CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG:
				return basicSetIdConfig(null, msgs);
			case CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG:
				return basicSetSuperTypeConfig(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG:
				return getTypeConfig();
			case CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG:
				return getIdConfig();
			case CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG:
				return getSuperTypeConfig();
			case CodecPackage.CLASS_CODEC_ASPECT__INHERIT_FROM_PARENT:
				return isInheritFromParent();
			case CodecPackage.CLASS_CODEC_ASPECT__DISCRIMINATOR_VALUE:
				return getDiscriminatorValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG:
				setTypeConfig((TypeSerializationConfig)newValue);
				return;
			case CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG:
				setIdConfig((IdSerializationConfig)newValue);
				return;
			case CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG:
				setSuperTypeConfig((SuperTypeSerializationConfig)newValue);
				return;
			case CodecPackage.CLASS_CODEC_ASPECT__INHERIT_FROM_PARENT:
				setInheritFromParent((Boolean)newValue);
				return;
			case CodecPackage.CLASS_CODEC_ASPECT__DISCRIMINATOR_VALUE:
				setDiscriminatorValue((String)newValue);
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
			case CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG:
				setTypeConfig((TypeSerializationConfig)null);
				return;
			case CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG:
				setIdConfig((IdSerializationConfig)null);
				return;
			case CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG:
				setSuperTypeConfig((SuperTypeSerializationConfig)null);
				return;
			case CodecPackage.CLASS_CODEC_ASPECT__INHERIT_FROM_PARENT:
				setInheritFromParent(INHERIT_FROM_PARENT_EDEFAULT);
				return;
			case CodecPackage.CLASS_CODEC_ASPECT__DISCRIMINATOR_VALUE:
				setDiscriminatorValue(DISCRIMINATOR_VALUE_EDEFAULT);
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
			case CodecPackage.CLASS_CODEC_ASPECT__TYPE_CONFIG:
				return typeConfig != null;
			case CodecPackage.CLASS_CODEC_ASPECT__ID_CONFIG:
				return idConfig != null;
			case CodecPackage.CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG:
				return superTypeConfig != null;
			case CodecPackage.CLASS_CODEC_ASPECT__INHERIT_FROM_PARENT:
				return inheritFromParent != INHERIT_FROM_PARENT_EDEFAULT;
			case CodecPackage.CLASS_CODEC_ASPECT__DISCRIMINATOR_VALUE:
				return DISCRIMINATOR_VALUE_EDEFAULT == null ? discriminatorValue != null : !DISCRIMINATOR_VALUE_EDEFAULT.equals(discriminatorValue);
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
		result.append(" (inheritFromParent: ");
		result.append(inheritFromParent);
		result.append(", discriminatorValue: ");
		result.append(discriminatorValue);
		result.append(')');
		return result.toString();
	}

} //ClassCodecAspectImpl
