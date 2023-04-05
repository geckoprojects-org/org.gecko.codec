/**
 */
package org.gecko.emf.codec.test.model.codectest.foobar.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.gecko.emf.codec.test.model.codectest.CodecTestPackage;

import org.gecko.emf.codec.test.model.codectest.foobar.Foo;
import org.gecko.emf.codec.test.model.codectest.foobar.FoobarFactory;
import org.gecko.emf.codec.test.model.codectest.foobar.FoobarPackage;

import org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl;

import org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage;

import org.gecko.emf.codec.test.model.codectest.subpackage.impl.SubpackagePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FoobarPackageImpl extends EPackageImpl implements FoobarPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fooEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.gecko.emf.codec.test.model.codectest.foobar.FoobarPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private FoobarPackageImpl() {
		super(eNS_URI, FoobarFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link FoobarPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static FoobarPackage init() {
		if (isInited) return (FoobarPackage)EPackage.Registry.INSTANCE.getEPackage(FoobarPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredFoobarPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		FoobarPackageImpl theFoobarPackage = registeredFoobarPackage instanceof FoobarPackageImpl ? (FoobarPackageImpl)registeredFoobarPackage : new FoobarPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CodecTestPackage.eNS_URI);
		CodecTestPackageImpl theCodecTestPackage = (CodecTestPackageImpl)(registeredPackage instanceof CodecTestPackageImpl ? registeredPackage : CodecTestPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SubpackagePackage.eNS_URI);
		SubpackagePackageImpl theSubpackagePackage = (SubpackagePackageImpl)(registeredPackage instanceof SubpackagePackageImpl ? registeredPackage : SubpackagePackage.eINSTANCE);

		// Create package meta-data objects
		theFoobarPackage.createPackageContents();
		theCodecTestPackage.createPackageContents();
		theSubpackagePackage.createPackageContents();

		// Initialize created meta-data
		theFoobarPackage.initializePackageContents();
		theCodecTestPackage.initializePackageContents();
		theSubpackagePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFoobarPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FoobarPackage.eNS_URI, theFoobarPackage);
		return theFoobarPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFoo() {
		return fooEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFoo_Bar() {
		return (EAttribute)fooEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FoobarFactory getFoobarFactory() {
		return (FoobarFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		fooEClass = createEClass(FOO);
		createEAttribute(fooEClass, FOO__BAR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(fooEClass, Foo.class, "Foo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFoo_Bar(), ecorePackage.getEInt(), "bar", null, 0, 1, Foo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //FoobarPackageImpl
