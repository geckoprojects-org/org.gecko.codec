/**
 */
package org.gecko.emf.codec.test.model.codectest.subpackage.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.gecko.emf.codec.test.model.codectest.CodecTestPackage;

import org.gecko.emf.codec.test.model.codectest.foobar.FoobarPackage;

import org.gecko.emf.codec.test.model.codectest.foobar.impl.FoobarPackageImpl;

import org.gecko.emf.codec.test.model.codectest.impl.CodecTestPackageImpl;

import org.gecko.emf.codec.test.model.codectest.subpackage.Example;
import org.gecko.emf.codec.test.model.codectest.subpackage.SubpackageFactory;
import org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SubpackagePackageImpl extends EPackageImpl implements SubpackagePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exampleEClass = null;

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
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SubpackagePackageImpl() {
		super(eNS_URI, SubpackageFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SubpackagePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SubpackagePackage init() {
		if (isInited) return (SubpackagePackage)EPackage.Registry.INSTANCE.getEPackage(SubpackagePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSubpackagePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		SubpackagePackageImpl theSubpackagePackage = registeredSubpackagePackage instanceof SubpackagePackageImpl ? (SubpackagePackageImpl)registeredSubpackagePackage : new SubpackagePackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CodecTestPackage.eNS_URI);
		CodecTestPackageImpl theCodecTestPackage = (CodecTestPackageImpl)(registeredPackage instanceof CodecTestPackageImpl ? registeredPackage : CodecTestPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(FoobarPackage.eNS_URI);
		FoobarPackageImpl theFoobarPackage = (FoobarPackageImpl)(registeredPackage instanceof FoobarPackageImpl ? registeredPackage : FoobarPackage.eINSTANCE);

		// Create package meta-data objects
		theSubpackagePackage.createPackageContents();
		theCodecTestPackage.createPackageContents();
		theFoobarPackage.createPackageContents();

		// Initialize created meta-data
		theSubpackagePackage.initializePackageContents();
		theCodecTestPackage.initializePackageContents();
		theFoobarPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSubpackagePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SubpackagePackage.eNS_URI, theSubpackagePackage);
		return theSubpackagePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExample() {
		return exampleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_ExampleId() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_Mandatory() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_MandatoryMultiple() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_Multiple01() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_Multiple02() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_Derived() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExample_Containment() {
		return (EReference)exampleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_Transient() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_BigLong() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_BigDouble() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_BigInteger() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_BigFloat() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_BigChar() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_BigBool() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_BigShort() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_BigByte() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_SmallLong() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_SmallDouble() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_SmallInteger() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_SmallFloat() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_SmallChar() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_SmallBool() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_SmallShort() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExample_SmallByte() {
		return (EAttribute)exampleEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubpackageFactory getSubpackageFactory() {
		return (SubpackageFactory)getEFactoryInstance();
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
		exampleEClass = createEClass(EXAMPLE);
		createEAttribute(exampleEClass, EXAMPLE__EXAMPLE_ID);
		createEAttribute(exampleEClass, EXAMPLE__MANDATORY);
		createEAttribute(exampleEClass, EXAMPLE__MANDATORY_MULTIPLE);
		createEAttribute(exampleEClass, EXAMPLE__MULTIPLE01);
		createEAttribute(exampleEClass, EXAMPLE__MULTIPLE02);
		createEAttribute(exampleEClass, EXAMPLE__DERIVED);
		createEReference(exampleEClass, EXAMPLE__CONTAINMENT);
		createEAttribute(exampleEClass, EXAMPLE__TRANSIENT);
		createEAttribute(exampleEClass, EXAMPLE__BIG_LONG);
		createEAttribute(exampleEClass, EXAMPLE__BIG_DOUBLE);
		createEAttribute(exampleEClass, EXAMPLE__BIG_INTEGER);
		createEAttribute(exampleEClass, EXAMPLE__BIG_FLOAT);
		createEAttribute(exampleEClass, EXAMPLE__BIG_CHAR);
		createEAttribute(exampleEClass, EXAMPLE__BIG_BOOL);
		createEAttribute(exampleEClass, EXAMPLE__BIG_SHORT);
		createEAttribute(exampleEClass, EXAMPLE__BIG_BYTE);
		createEAttribute(exampleEClass, EXAMPLE__SMALL_LONG);
		createEAttribute(exampleEClass, EXAMPLE__SMALL_DOUBLE);
		createEAttribute(exampleEClass, EXAMPLE__SMALL_INTEGER);
		createEAttribute(exampleEClass, EXAMPLE__SMALL_FLOAT);
		createEAttribute(exampleEClass, EXAMPLE__SMALL_CHAR);
		createEAttribute(exampleEClass, EXAMPLE__SMALL_BOOL);
		createEAttribute(exampleEClass, EXAMPLE__SMALL_SHORT);
		createEAttribute(exampleEClass, EXAMPLE__SMALL_BYTE);
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

		// Obtain other dependent packages
		CodecTestPackage theCodecTestPackage = (CodecTestPackage)EPackage.Registry.INSTANCE.getEPackage(CodecTestPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(exampleEClass, Example.class, "Example", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExample_ExampleId(), ecorePackage.getEString(), "exampleId", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_Mandatory(), ecorePackage.getEString(), "mandatory", null, 1, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_MandatoryMultiple(), ecorePackage.getEString(), "mandatoryMultiple", null, 1, -1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_Multiple01(), ecorePackage.getEString(), "multiple01", null, 0, -1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_Multiple02(), ecorePackage.getEString(), "multiple02", null, 0, 4, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_Derived(), ecorePackage.getEInt(), "derived", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getExample_Containment(), theCodecTestPackage.getAddress(), null, "containment", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_Transient(), ecorePackage.getELong(), "transient", null, 0, 1, Example.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_BigLong(), ecorePackage.getELongObject(), "BigLong", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_BigDouble(), ecorePackage.getEDoubleObject(), "BigDouble", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_BigInteger(), ecorePackage.getEIntegerObject(), "BigInteger", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_BigFloat(), ecorePackage.getEFloatObject(), "BigFloat", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_BigChar(), ecorePackage.getECharacterObject(), "BigChar", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_BigBool(), ecorePackage.getEBooleanObject(), "BigBool", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_BigShort(), ecorePackage.getEShortObject(), "BigShort", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_BigByte(), ecorePackage.getEByteObject(), "BigByte", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_SmallLong(), ecorePackage.getELong(), "SmallLong", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_SmallDouble(), ecorePackage.getEDouble(), "SmallDouble", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_SmallInteger(), ecorePackage.getEInt(), "SmallInteger", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_SmallFloat(), ecorePackage.getEFloat(), "SmallFloat", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_SmallChar(), ecorePackage.getEChar(), "SmallChar", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_SmallBool(), ecorePackage.getEBoolean(), "SmallBool", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_SmallShort(), ecorePackage.getEShort(), "SmallShort", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExample_SmallByte(), ecorePackage.getEByte(), "SmallByte", null, 0, 1, Example.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //SubpackagePackageImpl
