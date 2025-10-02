/**
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
package org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emfcloud.jackson.junit.packageAnnotations.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PackageAnnotationsFactoryImpl extends EFactoryImpl implements PackageAnnotationsFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2012 - 2024 Data In Motion and others.\nAll rights reserved.\n\nThis program and the accompanying materials are made\navailable under the terms of the Eclipse Public License 2.0\nwhich is available at https://www.eclipse.org/legal/epl-2.0/\n\nSPDX-License-Identifier: EPL-2.0\n\nContributors:\n    Data In Motion - initial API and implementation\n";

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PackageAnnotationsFactory init() {
		try {
			PackageAnnotationsFactory thePackageAnnotationsFactory = (PackageAnnotationsFactory)EPackage.Registry.INSTANCE.getEFactory(PackageAnnotationsPackage.eNS_URI);
			if (thePackageAnnotationsFactory != null) {
				return thePackageAnnotationsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PackageAnnotationsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageAnnotationsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PackageAnnotationsPackage.CONTAINER: return createContainer();
			case PackageAnnotationsPackage.TEST_TYPE_NAME: return createTestTypeName();
			case PackageAnnotationsPackage.FOO_TYPE_NAME: return createFooTypeName();
			case PackageAnnotationsPackage.BAR_TYPE_NAME: return createBarTypeName();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.jackson.junit.packageAnnotations.Container createContainer() {
		ContainerImpl container = new ContainerImpl();
		return container;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestTypeName createTestTypeName() {
		TestTypeNameImpl testTypeName = new TestTypeNameImpl();
		return testTypeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FooTypeName createFooTypeName() {
		FooTypeNameImpl fooTypeName = new FooTypeNameImpl();
		return fooTypeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BarTypeName createBarTypeName() {
		BarTypeNameImpl barTypeName = new BarTypeNameImpl();
		return barTypeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PackageAnnotationsPackage getPackageAnnotationsPackage() {
		return (PackageAnnotationsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PackageAnnotationsPackage getPackage() {
		return PackageAnnotationsPackage.eINSTANCE;
	}

} //PackageAnnotationsFactoryImpl
