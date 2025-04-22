/**
 */
package org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emfcloud.jackson.junit.packageAnnotations.BarTypeName;
import org.eclipse.emfcloud.jackson.junit.packageAnnotations.FooTypeName;
import org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsFactory;
import org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsPackage;
import org.eclipse.emfcloud.jackson.junit.packageAnnotations.TestTypeName;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PackageAnnotationsPackageImpl extends EPackageImpl implements PackageAnnotationsPackage {
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
	 * @see org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PackageAnnotationsPackageImpl() {
		super(eNS_URI, PackageAnnotationsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PackageAnnotationsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PackageAnnotationsPackage init() {
		if (isInited) return (PackageAnnotationsPackage)EPackage.Registry.INSTANCE.getEPackage(PackageAnnotationsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredPackageAnnotationsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PackageAnnotationsPackageImpl thePackageAnnotationsPackage = registeredPackageAnnotationsPackage instanceof PackageAnnotationsPackageImpl ? (PackageAnnotationsPackageImpl)registeredPackageAnnotationsPackage : new PackageAnnotationsPackageImpl();

		isInited = true;

		// Create package meta-data objects
		thePackageAnnotationsPackage.createPackageContents();

		// Initialize created meta-data
		thePackageAnnotationsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePackageAnnotationsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PackageAnnotationsPackage.eNS_URI, thePackageAnnotationsPackage);
		return thePackageAnnotationsPackage;
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
	public EReference getContainer_Values() {
		return (EReference)containerEClass.getEStructuralFeatures().get(0);
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
	public PackageAnnotationsFactory getPackageAnnotationsFactory() {
		return (PackageAnnotationsFactory)getEFactoryInstance();
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
		containerEClass = createEClass(CONTAINER);
		createEReference(containerEClass, CONTAINER__VALUES);

		testTypeNameEClass = createEClass(TEST_TYPE_NAME);
		createEAttribute(testTypeNameEClass, TEST_TYPE_NAME__VALUE);

		fooTypeNameEClass = createEClass(FOO_TYPE_NAME);

		barTypeNameEClass = createEClass(BAR_TYPE_NAME);
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
		fooTypeNameEClass.getESuperTypes().add(this.getTestTypeName());
		barTypeNameEClass.getESuperTypes().add(this.getTestTypeName());

		// Initialize classes, features, and operations; add parameters
		initEClass(containerEClass, org.eclipse.emfcloud.jackson.junit.packageAnnotations.Container.class, "Container", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContainer_Values(), this.getTestTypeName(), null, "values", null, 0, -1, org.eclipse.emfcloud.jackson.junit.packageAnnotations.Container.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testTypeNameEClass, TestTypeName.class, "TestTypeName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestTypeName_Value(), ecorePackage.getEString(), "value", null, 0, 1, TestTypeName.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fooTypeNameEClass, FooTypeName.class, "FooTypeName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(barTypeNameEClass, BarTypeName.class, "BarTypeName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// JsonType
		createJsonTypeAnnotations();
		// http://www.eclipse.org/emf/2011/Xcore
		createXcoreAnnotations();
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
		  (this,
		   source,
		   new String[] {
			   "property", "@type",
			   "use", "NAME"
		   });
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
			   "JsonType", "JsonType"
		   });
	}

} //PackageAnnotationsPackageImpl
