/**
 */
package org.eclipse.emfcloud.jackson.junit.generics.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emfcloud.jackson.junit.generics.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GenericsFactoryImpl extends EFactoryImpl implements GenericsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GenericsFactory init() {
		try {
			GenericsFactory theGenericsFactory = (GenericsFactory)EPackage.Registry.INSTANCE.getEFactory(GenericsPackage.eNS_URI);
			if (theGenericsFactory != null) {
				return theGenericsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GenericsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericsFactoryImpl() {
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
			case GenericsPackage.GENERIC_CONTAINER: return createGenericContainer();
			case GenericsPackage.SPECIAL_TYPE_ONE: return createSpecialTypeOne();
			case GenericsPackage.SPECIAL_TYPE_TWO: return createSpecialTypeTwo();
			case GenericsPackage.SOME: return createSome();
			case GenericsPackage.ANY: return createAny();
			case GenericsPackage.BASE_ONE: return createBaseOne();
			case GenericsPackage.OTHER_CONTAINER: return createOtherContainer();
			case GenericsPackage.CONTENT_A: return createContentA();
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
	public GenericContainer createGenericContainer() {
		GenericContainerImpl genericContainer = new GenericContainerImpl();
		return genericContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SpecialTypeOne createSpecialTypeOne() {
		SpecialTypeOneImpl specialTypeOne = new SpecialTypeOneImpl();
		return specialTypeOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SpecialTypeTwo createSpecialTypeTwo() {
		SpecialTypeTwoImpl specialTypeTwo = new SpecialTypeTwoImpl();
		return specialTypeTwo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Some createSome() {
		SomeImpl some = new SomeImpl();
		return some;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Any createAny() {
		AnyImpl any = new AnyImpl();
		return any;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BaseOne createBaseOne() {
		BaseOneImpl baseOne = new BaseOneImpl();
		return baseOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public <ContentT extends Content> OtherContainer<ContentT> createOtherContainer() {
		OtherContainerImpl<ContentT> otherContainer = new OtherContainerImpl<ContentT>();
		return otherContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContentA createContentA() {
		ContentAImpl contentA = new ContentAImpl();
		return contentA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenericsPackage getGenericsPackage() {
		return (GenericsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GenericsPackage getPackage() {
		return GenericsPackage.eINSTANCE;
	}

} //GenericsFactoryImpl
