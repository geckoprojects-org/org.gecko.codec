/**
 */
package org.eclipse.emfcloud.jackson.junit.annotations.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emfcloud.jackson.junit.annotations.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage
 * @generated
 */
public class AnnotationsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AnnotationsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotationsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AnnotationsPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotationsSwitch<Adapter> modelSwitch =
		new AnnotationsSwitch<Adapter>() {
			@Override
			public Adapter caseTestA(TestA object) {
				return createTestAAdapter();
			}
			@Override
			public Adapter caseTestB(TestB object) {
				return createTestBAdapter();
			}
			@Override
			public Adapter caseTestC(TestC object) {
				return createTestCAdapter();
			}
			@Override
			public Adapter caseTestD(TestD object) {
				return createTestDAdapter();
			}
			@Override
			public Adapter caseTestE(TestE object) {
				return createTestEAdapter();
			}
			@Override
			public Adapter caseTestF(TestF object) {
				return createTestFAdapter();
			}
			@Override
			public Adapter caseTestG(TestG object) {
				return createTestGAdapter();
			}
			@Override
			public Adapter caseContainer(Container object) {
				return createContainerAdapter();
			}
			@Override
			public Adapter caseTestTypeName(TestTypeName object) {
				return createTestTypeNameAdapter();
			}
			@Override
			public Adapter caseFooTypeName(FooTypeName object) {
				return createFooTypeNameAdapter();
			}
			@Override
			public Adapter caseBarTypeName(BarTypeName object) {
				return createBarTypeNameAdapter();
			}
			@Override
			public Adapter caseTestTypeClass(TestTypeClass object) {
				return createTestTypeClassAdapter();
			}
			@Override
			public Adapter caseFooTypeClass(FooTypeClass object) {
				return createFooTypeClassAdapter();
			}
			@Override
			public Adapter caseBarTypeClass(BarTypeClass object) {
				return createBarTypeClassAdapter();
			}
			@Override
			public Adapter caseRawJson(RawJson object) {
				return createRawJsonAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestA <em>Test A</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestA
	 * @generated
	 */
	public Adapter createTestAAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestB <em>Test B</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestB
	 * @generated
	 */
	public Adapter createTestBAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestC <em>Test C</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestC
	 * @generated
	 */
	public Adapter createTestCAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestD <em>Test D</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestD
	 * @generated
	 */
	public Adapter createTestDAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestE <em>Test E</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestE
	 * @generated
	 */
	public Adapter createTestEAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestF <em>Test F</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestF
	 * @generated
	 */
	public Adapter createTestFAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestG <em>Test G</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestG
	 * @generated
	 */
	public Adapter createTestGAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.Container
	 * @generated
	 */
	public Adapter createContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName <em>Test Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName
	 * @generated
	 */
	public Adapter createTestTypeNameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.FooTypeName <em>Foo Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.FooTypeName
	 * @generated
	 */
	public Adapter createFooTypeNameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.BarTypeName <em>Bar Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.BarTypeName
	 * @generated
	 */
	public Adapter createBarTypeNameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass <em>Test Type Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass
	 * @generated
	 */
	public Adapter createTestTypeClassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.FooTypeClass <em>Foo Type Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.FooTypeClass
	 * @generated
	 */
	public Adapter createFooTypeClassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.BarTypeClass <em>Bar Type Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.BarTypeClass
	 * @generated
	 */
	public Adapter createBarTypeClassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.annotations.RawJson <em>Raw Json</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.RawJson
	 * @generated
	 */
	public Adapter createRawJsonAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //AnnotationsAdapterFactory
