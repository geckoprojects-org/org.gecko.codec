/*
 */
package org.eclipse.emfcloud.jackson.junit.generics.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage;
import org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeOne;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Special Type One</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class SpecialTypeOneImpl extends GenericTypeImpl<String> implements SpecialTypeOne {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SpecialTypeOneImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenericsPackage.Literals.SPECIAL_TYPE_ONE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		super.setValue(newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<String> getValues() {
		if (values == null) {
			values = new EDataTypeEList<String>(String.class, this, GenericsPackage.SPECIAL_TYPE_ONE__VALUES);
		}
		return values;
	}

} //SpecialTypeOneImpl
