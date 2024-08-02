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
public class CodecInfoSwitch<T> extends Switch<T> {
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
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CodecInfoPackage.PACKAGE_CODEC_INFO: {
				PackageCodecInfo packageCodecInfo = (PackageCodecInfo)theEObject;
				T result = casePackageCodecInfo(packageCodecInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.ECLASS_CODEC_INFO: {
				EClassCodecInfo eClassCodecInfo = (EClassCodecInfo)theEObject;
				T result = caseEClassCodecInfo(eClassCodecInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.FEATURE_CODEC_INFO: {
				FeatureCodecInfo featureCodecInfo = (FeatureCodecInfo)theEObject;
				T result = caseFeatureCodecInfo(featureCodecInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.TYPE_INFO: {
				TypeInfo typeInfo = (TypeInfo)theEObject;
				T result = caseTypeInfo(typeInfo);
				if (result == null) result = caseFeatureCodecInfo(typeInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.IDENTITY_INFO: {
				IdentityInfo identityInfo = (IdentityInfo)theEObject;
				T result = caseIdentityInfo(identityInfo);
				if (result == null) result = caseFeatureCodecInfo(identityInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.REFERENCE_INFO: {
				ReferenceInfo referenceInfo = (ReferenceInfo)theEObject;
				T result = caseReferenceInfo(referenceInfo);
				if (result == null) result = caseFeatureCodecInfo(referenceInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.CODEC_VALUE_READER: {
				CodecValueReader codecValueReader = (CodecValueReader)theEObject;
				T result = caseCodecValueReader(codecValueReader);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.CODEC_VALUE_WRITER: {
				CodecValueWriter codecValueWriter = (CodecValueWriter)theEObject;
				T result = caseCodecValueWriter(codecValueWriter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodecInfoPackage.CODEC_INFO_HOLDER: {
				CodecInfoHolder codecInfoHolder = (CodecInfoHolder)theEObject;
				T result = caseCodecInfoHolder(codecInfoHolder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Package Codec Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Package Codec Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePackageCodecInfo(PackageCodecInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EClass Codec Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EClass Codec Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEClassCodecInfo(EClassCodecInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature Codec Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature Codec Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeatureCodecInfo(FeatureCodecInfo object) {
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
	public T caseTypeInfo(TypeInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identity Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identity Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentityInfo(IdentityInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reference Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reference Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferenceInfo(ReferenceInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Codec Value Reader</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Codec Value Reader</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodecValueReader(CodecValueReader object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Codec Value Writer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Codec Value Writer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodecValueWriter(CodecValueWriter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Holder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Holder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodecInfoHolder(CodecInfoHolder object) {
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

} //CodecInfoSwitch
