/**
 */
package org.gecko.codec.info.codecinfo.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.gecko.codec.info.codecinfo.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage
 * @generated
 */
public class CodecInfoAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CodecInfoPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodecInfoAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CodecInfoPackage.eINSTANCE;
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
	protected CodecInfoSwitch<Adapter> modelSwitch =
		new CodecInfoSwitch<Adapter>() {
			@Override
			public Adapter casePackageCodecInfo(PackageCodecInfo object) {
				return createPackageCodecInfoAdapter();
			}
			@Override
			public Adapter caseEClassCodecInfo(EClassCodecInfo object) {
				return createEClassCodecInfoAdapter();
			}
			@Override
			public Adapter caseFeatureCodecInfo(FeatureCodecInfo object) {
				return createFeatureCodecInfoAdapter();
			}
			@Override
			public Adapter caseTypeInfo(TypeInfo object) {
				return createTypeInfoAdapter();
			}
			@Override
			public Adapter caseIdentityInfo(IdentityInfo object) {
				return createIdentityInfoAdapter();
			}
			@Override
			public Adapter caseReferenceInfo(ReferenceInfo object) {
				return createReferenceInfoAdapter();
			}
			@Override
			public Adapter caseCodecValueReader(CodecValueReader object) {
				return createCodecValueReaderAdapter();
			}
			@Override
			public Adapter caseCodecValueWriter(CodecValueWriter object) {
				return createCodecValueWriterAdapter();
			}
			@Override
			public Adapter caseCodecInfoHolder(CodecInfoHolder object) {
				return createCodecInfoHolderAdapter();
			}
			@Override
			public <T, V> Adapter caseSampleValueReader(SampleValueReader<T, V> object) {
				return createSampleValueReaderAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.PackageCodecInfo <em>Package Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.PackageCodecInfo
	 * @generated
	 */
	public Adapter createPackageCodecInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.EClassCodecInfo <em>EClass Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.EClassCodecInfo
	 * @generated
	 */
	public Adapter createEClassCodecInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.FeatureCodecInfo <em>Feature Codec Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.FeatureCodecInfo
	 * @generated
	 */
	public Adapter createFeatureCodecInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.TypeInfo <em>Type Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.TypeInfo
	 * @generated
	 */
	public Adapter createTypeInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.IdentityInfo <em>Identity Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.IdentityInfo
	 * @generated
	 */
	public Adapter createIdentityInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.ReferenceInfo <em>Reference Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.ReferenceInfo
	 * @generated
	 */
	public Adapter createReferenceInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.CodecValueReader <em>Codec Value Reader</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.CodecValueReader
	 * @generated
	 */
	public Adapter createCodecValueReaderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.CodecValueWriter <em>Codec Value Writer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.CodecValueWriter
	 * @generated
	 */
	public Adapter createCodecValueWriterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.CodecInfoHolder <em>Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoHolder
	 * @generated
	 */
	public Adapter createCodecInfoHolderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gecko.codec.info.codecinfo.SampleValueReader <em>Sample Value Reader</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gecko.codec.info.codecinfo.SampleValueReader
	 * @generated
	 */
	public Adapter createSampleValueReaderAdapter() {
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

} //CodecInfoAdapterFactory
