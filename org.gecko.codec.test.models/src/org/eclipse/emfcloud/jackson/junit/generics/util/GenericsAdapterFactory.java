/**
 */
package org.eclipse.emfcloud.jackson.junit.generics.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emfcloud.jackson.junit.generics.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage
 * @generated
 */
public class GenericsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GenericsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = GenericsPackage.eINSTANCE;
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
	protected GenericsSwitch<Adapter> modelSwitch =
		new GenericsSwitch<Adapter>() {
			@Override
			public Adapter caseGenericContainer(GenericContainer object) {
				return createGenericContainerAdapter();
			}
			@Override
			public <T> Adapter caseGenericType(GenericType<T> object) {
				return createGenericTypeAdapter();
			}
			@Override
			public Adapter caseSpecialTypeOne(SpecialTypeOne object) {
				return createSpecialTypeOneAdapter();
			}
			@Override
			public Adapter caseSpecialTypeTwo(SpecialTypeTwo object) {
				return createSpecialTypeTwoAdapter();
			}
			@Override
			public <U, T> Adapter caseBase(Base<U, T> object) {
				return createBaseAdapter();
			}
			@Override
			public Adapter caseSome(Some object) {
				return createSomeAdapter();
			}
			@Override
			public Adapter caseAny(Any object) {
				return createAnyAdapter();
			}
			@Override
			public Adapter caseBaseOne(BaseOne object) {
				return createBaseOneAdapter();
			}
			@Override
			public <ContentT extends Content> Adapter caseOtherContainer(OtherContainer<ContentT> object) {
				return createOtherContainerAdapter();
			}
			@Override
			public Adapter caseContent(Content object) {
				return createContentAdapter();
			}
			@Override
			public Adapter caseContentA(ContentA object) {
				return createContentAAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.GenericContainer <em>Generic Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericContainer
	 * @generated
	 */
	public Adapter createGenericContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.GenericType <em>Generic Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericType
	 * @generated
	 */
	public Adapter createGenericTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeOne <em>Special Type One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeOne
	 * @generated
	 */
	public Adapter createSpecialTypeOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeTwo <em>Special Type Two</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeTwo
	 * @generated
	 */
	public Adapter createSpecialTypeTwoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.Base <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Base
	 * @generated
	 */
	public Adapter createBaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.Some <em>Some</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Some
	 * @generated
	 */
	public Adapter createSomeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.Any <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Any
	 * @generated
	 */
	public Adapter createAnyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.BaseOne <em>Base One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.BaseOne
	 * @generated
	 */
	public Adapter createBaseOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.OtherContainer <em>Other Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.OtherContainer
	 * @generated
	 */
	public Adapter createOtherContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.Content <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.Content
	 * @generated
	 */
	public Adapter createContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.generics.ContentA <em>Content A</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfcloud.jackson.junit.generics.ContentA
	 * @generated
	 */
	public Adapter createContentAAdapter() {
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

} //GenericsAdapterFactory
