/*
 */
package org.eclipse.emfcloud.jackson.junit.generics.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage;
import org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeTwo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Special Type Two</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class SpecialTypeTwoImpl extends GenericTypeImpl<Boolean> implements SpecialTypeTwo {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SpecialTypeTwoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenericsPackage.Literals.SPECIAL_TYPE_TWO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setValue(Boolean newValue) {
		super.setValue(newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<Boolean> getValues() {
		if (values == null) {
			values = new EDataTypeEList<Boolean>(Boolean.class, this, GenericsPackage.SPECIAL_TYPE_TWO__VALUES);
		}
		return values;
	}

} //SpecialTypeTwoImpl
