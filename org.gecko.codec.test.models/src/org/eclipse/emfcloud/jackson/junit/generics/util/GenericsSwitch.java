/**
 */
package org.eclipse.emfcloud.jackson.junit.generics.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.emfcloud.jackson.junit.generics.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage
 * @generated
 */
public class GenericsSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GenericsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericsSwitch() {
		if (modelPackage == null) {
			modelPackage = GenericsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case GenericsPackage.GENERIC_CONTAINER: {
				GenericContainer genericContainer = (GenericContainer)theEObject;
				T1 result = caseGenericContainer(genericContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.GENERIC_TYPE: {
				GenericType<?> genericType = (GenericType<?>)theEObject;
				T1 result = caseGenericType(genericType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.SPECIAL_TYPE_ONE: {
				SpecialTypeOne specialTypeOne = (SpecialTypeOne)theEObject;
				T1 result = caseSpecialTypeOne(specialTypeOne);
				if (result == null) result = caseGenericType(specialTypeOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.SPECIAL_TYPE_TWO: {
				SpecialTypeTwo specialTypeTwo = (SpecialTypeTwo)theEObject;
				T1 result = caseSpecialTypeTwo(specialTypeTwo);
				if (result == null) result = caseGenericType(specialTypeTwo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.BASE: {
				Base<?, ?> base = (Base<?, ?>)theEObject;
				T1 result = caseBase(base);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.SOME: {
				Some some = (Some)theEObject;
				T1 result = caseSome(some);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.ANY: {
				Any any = (Any)theEObject;
				T1 result = caseAny(any);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.BASE_ONE: {
				BaseOne baseOne = (BaseOne)theEObject;
				T1 result = caseBaseOne(baseOne);
				if (result == null) result = caseBase(baseOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.OTHER_CONTAINER: {
				OtherContainer<?> otherContainer = (OtherContainer<?>)theEObject;
				T1 result = caseOtherContainer(otherContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.CONTENT: {
				Content content = (Content)theEObject;
				T1 result = caseContent(content);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericsPackage.CONTENT_A: {
				ContentA contentA = (ContentA)theEObject;
				T1 result = caseContentA(contentA);
				if (result == null) result = caseContent(contentA);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseGenericContainer(GenericContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T> T1 caseGenericType(GenericType<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Special Type One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Special Type One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSpecialTypeOne(SpecialTypeOne object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Special Type Two</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Special Type Two</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSpecialTypeTwo(SpecialTypeTwo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <U, T> T1 caseBase(Base<U, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Some</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Some</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSome(Some object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAny(Any object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseBaseOne(BaseOne object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Other Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Other Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <ContentT extends Content> T1 caseOtherContainer(OtherContainer<ContentT> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Content</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseContent(Content object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Content A</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Content A</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseContentA(ContentA object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T1 defaultCase(EObject object) {
		return null;
	}

} //GenericsSwitch
