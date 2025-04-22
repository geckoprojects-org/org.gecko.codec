/**
 */
package org.eclipse.emfcloud.jackson.junit.annotations.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emfcloud.jackson.junit.annotations.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AnnotationsFactoryImpl extends EFactoryImpl implements AnnotationsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AnnotationsFactory init() {
		try {
			AnnotationsFactory theAnnotationsFactory = (AnnotationsFactory)EPackage.Registry.INSTANCE.getEFactory(AnnotationsPackage.eNS_URI);
			if (theAnnotationsFactory != null) {
				return theAnnotationsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AnnotationsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotationsFactoryImpl() {
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
			case AnnotationsPackage.TEST_A: return createTestA();
			case AnnotationsPackage.TEST_B: return createTestB();
			case AnnotationsPackage.TEST_C: return createTestC();
			case AnnotationsPackage.TEST_D: return createTestD();
			case AnnotationsPackage.TEST_E: return createTestE();
			case AnnotationsPackage.TEST_F: return createTestF();
			case AnnotationsPackage.TEST_G: return createTestG();
			case AnnotationsPackage.CONTAINER: return createContainer();
			case AnnotationsPackage.TEST_TYPE_NAME: return createTestTypeName();
			case AnnotationsPackage.FOO_TYPE_NAME: return createFooTypeName();
			case AnnotationsPackage.BAR_TYPE_NAME: return createBarTypeName();
			case AnnotationsPackage.TEST_TYPE_CLASS: return createTestTypeClass();
			case AnnotationsPackage.FOO_TYPE_CLASS: return createFooTypeClass();
			case AnnotationsPackage.BAR_TYPE_CLASS: return createBarTypeClass();
			case AnnotationsPackage.RAW_JSON: return createRawJson();
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
	public TestA createTestA() {
		TestAImpl testA = new TestAImpl();
		return testA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestB createTestB() {
		TestBImpl testB = new TestBImpl();
		return testB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestC createTestC() {
		TestCImpl testC = new TestCImpl();
		return testC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestD createTestD() {
		TestDImpl testD = new TestDImpl();
		return testD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestE createTestE() {
		TestEImpl testE = new TestEImpl();
		return testE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestF createTestF() {
		TestFImpl testF = new TestFImpl();
		return testF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestG createTestG() {
		TestGImpl testG = new TestGImpl();
		return testG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.jackson.junit.annotations.Container createContainer() {
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
	public TestTypeClass createTestTypeClass() {
		TestTypeClassImpl testTypeClass = new TestTypeClassImpl();
		return testTypeClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FooTypeClass createFooTypeClass() {
		FooTypeClassImpl fooTypeClass = new FooTypeClassImpl();
		return fooTypeClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BarTypeClass createBarTypeClass() {
		BarTypeClassImpl barTypeClass = new BarTypeClassImpl();
		return barTypeClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RawJson createRawJson() {
		RawJsonImpl rawJson = new RawJsonImpl();
		return rawJson;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnnotationsPackage getAnnotationsPackage() {
		return (AnnotationsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AnnotationsPackage getPackage() {
		return AnnotationsPackage.eINSTANCE;
	}

} //AnnotationsFactoryImpl
