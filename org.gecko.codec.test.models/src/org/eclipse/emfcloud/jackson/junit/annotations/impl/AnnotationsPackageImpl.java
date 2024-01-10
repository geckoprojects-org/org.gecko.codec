/**
 */
package org.eclipse.emfcloud.jackson.junit.annotations.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsFactory;
import org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage;
import org.eclipse.emfcloud.jackson.junit.annotations.BarTypeClass;
import org.eclipse.emfcloud.jackson.junit.annotations.BarTypeName;
import org.eclipse.emfcloud.jackson.junit.annotations.FooTypeClass;
import org.eclipse.emfcloud.jackson.junit.annotations.FooTypeName;
import org.eclipse.emfcloud.jackson.junit.annotations.RawJson;
import org.eclipse.emfcloud.jackson.junit.annotations.TestA;
import org.eclipse.emfcloud.jackson.junit.annotations.TestB;
import org.eclipse.emfcloud.jackson.junit.annotations.TestC;
import org.eclipse.emfcloud.jackson.junit.annotations.TestD;
import org.eclipse.emfcloud.jackson.junit.annotations.TestE;
import org.eclipse.emfcloud.jackson.junit.annotations.TestF;
import org.eclipse.emfcloud.jackson.junit.annotations.TestG;
import org.eclipse.emfcloud.jackson.junit.annotations.TestTypeClass;
import org.eclipse.emfcloud.jackson.junit.annotations.TestTypeName;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AnnotationsPackageImpl extends EPackageImpl implements AnnotationsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testAEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testBEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testCEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testDEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testEEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testFEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testGEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass containerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testTypeNameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fooTypeNameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass barTypeNameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testTypeClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fooTypeClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass barTypeClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rawJsonEClass = null;

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
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AnnotationsPackageImpl() {
		super(eNS_URI, AnnotationsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link AnnotationsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AnnotationsPackage init() {
		if (isInited) return (AnnotationsPackage)EPackage.Registry.INSTANCE.getEPackage(AnnotationsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAnnotationsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AnnotationsPackageImpl theAnnotationsPackage = registeredAnnotationsPackage instanceof AnnotationsPackageImpl ? (AnnotationsPackageImpl)registeredAnnotationsPackage : new AnnotationsPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theAnnotationsPackage.createPackageContents();

		// Initialize created meta-data
		theAnnotationsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAnnotationsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AnnotationsPackage.eNS_URI, theAnnotationsPackage);
		return theAnnotationsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestA() {
		return testAEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestA_Value() {
		return (EAttribute)testAEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestA_ChildOfTypeC() {
		return (EReference)testAEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestA_ChildrenOfTypeD() {
		return (EReference)testAEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestB() {
		return testBEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestB_Value() {
		return (EAttribute)testBEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTestB__Hello() {
		return testBEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestC() {
		return testCEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestC_Value() {
		return (EAttribute)testCEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestC_ManyValues() {
		return (EReference)testCEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestC_OneValue() {
		return (EReference)testCEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestD() {
		return testDEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestD_Name() {
		return (EAttribute)testDEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestD_Values() {
		return (EReference)testDEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestE() {
		return testEEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestF() {
		return testFEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestF_Value() {
		return (EAttribute)testFEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestF_OtherValue() {
		return (EAttribute)testFEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestG() {
		return testGEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestG_Value() {
		return (EAttribute)testGEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContainer() {
		return containerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContainer_TypedByNames() {
		return (EReference)containerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContainer_TypedByClasses() {
		return (EReference)containerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestTypeName() {
		return testTypeNameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestTypeName_Value() {
		return (EAttribute)testTypeNameEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFooTypeName() {
		return fooTypeNameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBarTypeName() {
		return barTypeNameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestTypeClass() {
		return testTypeClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestTypeClass_Value() {
		return (EAttribute)testTypeClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFooTypeClass() {
		return fooTypeClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBarTypeClass() {
		return barTypeClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRawJson() {
		return rawJsonEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRawJson_Raw() {
		return (EAttribute)rawJsonEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnnotationsFactory getAnnotationsFactory() {
		return (AnnotationsFactory)getEFactoryInstance();
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
		testAEClass = createEClass(TEST_A);
		createEAttribute(testAEClass, TEST_A__VALUE);
		createEReference(testAEClass, TEST_A__CHILD_OF_TYPE_C);
		createEReference(testAEClass, TEST_A__CHILDREN_OF_TYPE_D);

		testBEClass = createEClass(TEST_B);
		createEAttribute(testBEClass, TEST_B__VALUE);
		createEOperation(testBEClass, TEST_B___HELLO);

		testCEClass = createEClass(TEST_C);
		createEAttribute(testCEClass, TEST_C__VALUE);
		createEReference(testCEClass, TEST_C__MANY_VALUES);
		createEReference(testCEClass, TEST_C__ONE_VALUE);

		testDEClass = createEClass(TEST_D);
		createEAttribute(testDEClass, TEST_D__NAME);
		createEReference(testDEClass, TEST_D__VALUES);

		testEEClass = createEClass(TEST_E);

		testFEClass = createEClass(TEST_F);
		createEAttribute(testFEClass, TEST_F__VALUE);
		createEAttribute(testFEClass, TEST_F__OTHER_VALUE);

		testGEClass = createEClass(TEST_G);
		createEAttribute(testGEClass, TEST_G__VALUE);

		containerEClass = createEClass(CONTAINER);
		createEReference(containerEClass, CONTAINER__TYPED_BY_NAMES);
		createEReference(containerEClass, CONTAINER__TYPED_BY_CLASSES);

		testTypeNameEClass = createEClass(TEST_TYPE_NAME);
		createEAttribute(testTypeNameEClass, TEST_TYPE_NAME__VALUE);

		fooTypeNameEClass = createEClass(FOO_TYPE_NAME);

		barTypeNameEClass = createEClass(BAR_TYPE_NAME);

		testTypeClassEClass = createEClass(TEST_TYPE_CLASS);
		createEAttribute(testTypeClassEClass, TEST_TYPE_CLASS__VALUE);

		fooTypeClassEClass = createEClass(FOO_TYPE_CLASS);

		barTypeClassEClass = createEClass(BAR_TYPE_CLASS);

		rawJsonEClass = createEClass(RAW_JSON);
		createEAttribute(rawJsonEClass, RAW_JSON__RAW);
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
		testDEClass.getESuperTypes().add(this.getTestC());
		testEEClass.getESuperTypes().add(this.getTestD());
		fooTypeNameEClass.getESuperTypes().add(this.getTestTypeName());
		barTypeNameEClass.getESuperTypes().add(this.getTestTypeName());
		fooTypeClassEClass.getESuperTypes().add(this.getTestTypeClass());
		barTypeClassEClass.getESuperTypes().add(this.getTestTypeClass());

		// Initialize classes, features, and operations; add parameters
		initEClass(testAEClass, TestA.class, "TestA", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestA_Value(), ecorePackage.getEString(), "value", null, 0, 1, TestA.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestA_ChildOfTypeC(), this.getTestC(), null, "childOfTypeC", null, 0, 1, TestA.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestA_ChildrenOfTypeD(), this.getTestD(), null, "childrenOfTypeD", null, 0, -1, TestA.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testBEClass, TestB.class, "TestB", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestB_Value(), ecorePackage.getEString(), "value", null, 0, 1, TestB.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTestB__Hello(), ecorePackage.getEString(), "hello", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(testCEClass, TestC.class, "TestC", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestC_Value(), ecorePackage.getEInt(), "value", null, 0, 1, TestC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestC_ManyValues(), this.getTestC(), null, "manyValues", null, 0, -1, TestC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestC_OneValue(), this.getTestC(), null, "oneValue", null, 0, 1, TestC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testDEClass, TestD.class, "TestD", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestD_Name(), ecorePackage.getEString(), "name", null, 0, 1, TestD.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestD_Values(), this.getTestD(), null, "values", null, 0, -1, TestD.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testEEClass, TestE.class, "TestE", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(testFEClass, TestF.class, "TestF", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestF_Value(), ecorePackage.getEString(), "value", null, 0, 1, TestF.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestF_OtherValue(), ecorePackage.getEString(), "otherValue", null, 0, 1, TestF.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testGEClass, TestG.class, "TestG", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestG_Value(), ecorePackage.getEString(), "value", null, 0, 1, TestG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(containerEClass, org.eclipse.emfcloud.jackson.junit.annotations.Container.class, "Container", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContainer_TypedByNames(), this.getTestTypeName(), null, "typedByNames", null, 0, -1, org.eclipse.emfcloud.jackson.junit.annotations.Container.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContainer_TypedByClasses(), this.getTestTypeClass(), null, "typedByClasses", null, 0, -1, org.eclipse.emfcloud.jackson.junit.annotations.Container.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testTypeNameEClass, TestTypeName.class, "TestTypeName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestTypeName_Value(), ecorePackage.getEString(), "value", null, 0, 1, TestTypeName.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fooTypeNameEClass, FooTypeName.class, "FooTypeName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(barTypeNameEClass, BarTypeName.class, "BarTypeName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(testTypeClassEClass, TestTypeClass.class, "TestTypeClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestTypeClass_Value(), ecorePackage.getEString(), "value", null, 0, 1, TestTypeClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fooTypeClassEClass, FooTypeClass.class, "FooTypeClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(barTypeClassEClass, BarTypeClass.class, "BarTypeClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(rawJsonEClass, RawJson.class, "RawJson", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRawJson_Raw(), ecorePackage.getEString(), "raw", null, 0, 1, RawJson.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2011/Xcore
		createXcoreAnnotations();
		// JsonType
		createJsonTypeAnnotations();
		// JsonProperty
		createJsonPropertyAnnotations();
		// JsonIdentity
		createJsonIdentityAnnotations();
		// JsonAlias
		createJsonAliasAnnotations();
		// JsonRawValue
		createJsonRawValueAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2011/Xcore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createXcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2011/Xcore";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "JsonIgnore", "JsonIgnore",
			   "JsonProperty", "JsonProperty",
			   "JsonIgnoreProperties", "JsonIgnoreProperties",
			   "JsonType", "JsonType",
			   "JsonIdentity", "JsonIdentity",
			   "JsonAlias", "JsonAlias",
			   "JsonRawValue", "JsonRawValue"
		   });
	}

	/**
	 * Initializes the annotations for <b>JsonType</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createJsonTypeAnnotations() {
		String source = "JsonType";
		addAnnotation
		  (testAEClass,
		   source,
		   new String[] {
			   "property", "@type"
		   });
		addAnnotation
		  (testCEClass,
		   source,
		   new String[] {
			   "include", "false"
		   });
		addAnnotation
		  (testDEClass,
		   source,
		   new String[] {
			   "property", "@bar"
		   });
		addAnnotation
		  (testEEClass,
		   source,
		   new String[] {
			   "property", "@foo"
		   });
		addAnnotation
		  (testGEClass,
		   source,
		   new String[] {
			   "property", "@type"
		   });
		addAnnotation
		  (testTypeNameEClass,
		   source,
		   new String[] {
			   "property", "@type",
			   "use", "NAME"
		   });
		addAnnotation
		  (testTypeClassEClass,
		   source,
		   new String[] {
			   "property", "_type",
			   "use", "CLASS"
		   });
	}

	/**
	 * Initializes the annotations for <b>JsonProperty</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createJsonPropertyAnnotations() {
		String source = "JsonProperty";
		addAnnotation
		  (getTestA_Value(),
		   source,
		   new String[] {
			   "value", "my_value"
		   });
		addAnnotation
		  (getTestB__Hello(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getTestB_Value(),
		   source,
		   new String[] {
			   "value", "my_value"
		   });
		addAnnotation
		  (getTestF_Value(),
		   source,
		   new String[] {
			   "value", "v"
		   });
	}

	/**
	 * Initializes the annotations for <b>JsonIdentity</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createJsonIdentityAnnotations() {
		String source = "JsonIdentity";
		addAnnotation
		  (testBEClass,
		   source,
		   new String[] {
			   "property", "id",
			   "include", "true"
		   });
	}

	/**
	 * Initializes the annotations for <b>JsonAlias</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createJsonAliasAnnotations() {
		String source = "JsonAlias";
		addAnnotation
		  (getTestF_Value(),
		   source,
		   new String[] {
			   "value", "value"
		   });
		addAnnotation
		  (getTestF_OtherValue(),
		   source,
		   new String[] {
			   "value", "foo, bar"
		   });
	}

	/**
	 * Initializes the annotations for <b>JsonRawValue</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createJsonRawValueAnnotations() {
		String source = "JsonRawValue";
		addAnnotation
		  (getRawJson_Raw(),
		   source,
		   new String[] {
			   "value", "true"
		   });
	}

} //AnnotationsPackageImpl
