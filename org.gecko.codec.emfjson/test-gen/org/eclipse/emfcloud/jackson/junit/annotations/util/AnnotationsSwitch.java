/**
 */
package org.eclipse.emfcloud.jackson.junit.annotations.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.emfcloud.jackson.junit.annotations.*;

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
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage
 * @generated
 */
public class AnnotationsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AnnotationsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotationsSwitch() {
		if (modelPackage == null) {
			modelPackage = AnnotationsPackage.eINSTANCE;
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
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case AnnotationsPackage.TEST_A: {
				TestA testA = (TestA)theEObject;
				T result = caseTestA(testA);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.TEST_B: {
				TestB testB = (TestB)theEObject;
				T result = caseTestB(testB);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.TEST_C: {
				TestC testC = (TestC)theEObject;
				T result = caseTestC(testC);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.TEST_D: {
				TestD testD = (TestD)theEObject;
				T result = caseTestD(testD);
				if (result == null) result = caseTestC(testD);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.TEST_E: {
				TestE testE = (TestE)theEObject;
				T result = caseTestE(testE);
				if (result == null) result = caseTestD(testE);
				if (result == null) result = caseTestC(testE);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.TEST_F: {
				TestF testF = (TestF)theEObject;
				T result = caseTestF(testF);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.TEST_G: {
				TestG testG = (TestG)theEObject;
				T result = caseTestG(testG);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.CONTAINER: {
				Container container = (Container)theEObject;
				T result = caseContainer(container);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.TEST_TYPE_NAME: {
				TestTypeName testTypeName = (TestTypeName)theEObject;
				T result = caseTestTypeName(testTypeName);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.FOO_TYPE_NAME: {
				FooTypeName fooTypeName = (FooTypeName)theEObject;
				T result = caseFooTypeName(fooTypeName);
				if (result == null) result = caseTestTypeName(fooTypeName);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.BAR_TYPE_NAME: {
				BarTypeName barTypeName = (BarTypeName)theEObject;
				T result = caseBarTypeName(barTypeName);
				if (result == null) result = caseTestTypeName(barTypeName);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.TEST_TYPE_CLASS: {
				TestTypeClass testTypeClass = (TestTypeClass)theEObject;
				T result = caseTestTypeClass(testTypeClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.FOO_TYPE_CLASS: {
				FooTypeClass fooTypeClass = (FooTypeClass)theEObject;
				T result = caseFooTypeClass(fooTypeClass);
				if (result == null) result = caseTestTypeClass(fooTypeClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.BAR_TYPE_CLASS: {
				BarTypeClass barTypeClass = (BarTypeClass)theEObject;
				T result = caseBarTypeClass(barTypeClass);
				if (result == null) result = caseTestTypeClass(barTypeClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AnnotationsPackage.RAW_JSON: {
				RawJson rawJson = (RawJson)theEObject;
				T result = caseRawJson(rawJson);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test A</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test A</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestA(TestA object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test B</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test B</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestB(TestB object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test C</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test C</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestC(TestC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test D</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test D</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestD(TestD object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test E</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test E</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestE(TestE object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test F</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test F</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestF(TestF object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test G</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test G</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestG(TestG object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContainer(Container object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Type Name</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Type Name</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestTypeName(TestTypeName object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Foo Type Name</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Foo Type Name</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFooTypeName(FooTypeName object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bar Type Name</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bar Type Name</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBarTypeName(BarTypeName object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Type Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Type Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestTypeClass(TestTypeClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Foo Type Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Foo Type Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFooTypeClass(FooTypeClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bar Type Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bar Type Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBarTypeClass(BarTypeClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Raw Json</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Raw Json</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRawJson(RawJson object) {
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
	public T defaultCase(EObject object) {
		return null;
	}

} //AnnotationsSwitch
