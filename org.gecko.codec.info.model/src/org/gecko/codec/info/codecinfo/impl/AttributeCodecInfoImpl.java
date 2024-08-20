/*
 */
package org.gecko.codec.info.codecinfo.impl;

import java.util.Collection;

import java.util.stream.Collectors;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;

import org.gecko.codec.info.codecinfo.AttributeCodecInfo;
import org.gecko.codec.info.codecinfo.CodecInfoPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Codec Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.AttributeCodecInfoImpl#getEAttributes <em>EAttributes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeCodecInfoImpl extends FeatureCodecInfoImpl implements AttributeCodecInfo {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeCodecInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.ATTRIBUTE_CODEC_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EAttribute> getEAttributes() {
		return ECollections.asEList(getFeatures().stream().filter(f->f instanceof EAttribute).map(EAttribute.class::cast).collect(Collectors.toList()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.ATTRIBUTE_CODEC_INFO__EATTRIBUTES:
				return getEAttributes();
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
			case CodecInfoPackage.ATTRIBUTE_CODEC_INFO__EATTRIBUTES:
				getEAttributes().clear();
				getEAttributes().addAll((Collection<? extends EAttribute>)newValue);
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
			case CodecInfoPackage.ATTRIBUTE_CODEC_INFO__EATTRIBUTES:
				getEAttributes().clear();
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
			case CodecInfoPackage.ATTRIBUTE_CODEC_INFO__EATTRIBUTES:
				return !getEAttributes().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AttributeCodecInfoImpl
