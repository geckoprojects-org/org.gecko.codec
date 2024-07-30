/**
 */
package org.gecko.codec.info.codecinfo.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.gecko.codec.info.codecinfo.*;

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
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage
 * @generated
 */
public class CodecInfoSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CodecInfoPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodecInfoSwitch() {
		if (modelPackage == null) {
			modelPackage = CodecInfoPackage.eINSTANCE;
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
			case CodecInfoPackage.PACKAGE_INFO: {
				PackageInfo packageInfo = (PackageInfo)theEObject;
				T1 result = casePackageInfo(packageInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.ECLASS_INFO: {
				EClassInfo eClassInfo = (EClassInfo)theEObject;
				T1 result = caseEClassInfo(eClassInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.FEATURE_INFO: {
				FeatureInfo featureInfo = (FeatureInfo)theEObject;
				T1 result = caseFeatureInfo(featureInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.VALUE_READER: {
				ValueReader<?, ?> valueReader = (ValueReader<?, ?>)theEObject;
				T1 result = caseValueReader(valueReader);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.VALUE_WRITER: {
				ValueWriter<?, ?> valueWriter = (ValueWriter<?, ?>)theEObject;
				T1 result = caseValueWriter(valueWriter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.TYPE_INFO_HOLDER: {
				TypeInfoHolder typeInfoHolder = (TypeInfoHolder)theEObject;
				T1 result = caseTypeInfoHolder(typeInfoHolder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.TYPE_INFO: {
				TypeInfo typeInfo = (TypeInfo)theEObject;
				T1 result = caseTypeInfo(typeInfo);
				if (result == null) result = caseFeatureInfo(typeInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Package Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Package Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 casePackageInfo(PackageInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EClass Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EClass Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEClassInfo(EClassInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFeatureInfo(FeatureInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Reader</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Reader</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <V, T> T1 caseValueReader(ValueReader<V, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Writer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Writer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T, V> T1 caseValueWriter(ValueWriter<T, V> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Info Holder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Info Holder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTypeInfoHolder(TypeInfoHolder object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTypeInfo(TypeInfo object) {
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

} //CodecInfoSwitch
