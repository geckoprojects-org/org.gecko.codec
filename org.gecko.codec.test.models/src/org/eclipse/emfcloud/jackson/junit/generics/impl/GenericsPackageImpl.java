/**
 */
package org.eclipse.emfcloud.jackson.junit.generics.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emfcloud.jackson.junit.generics.Any;
import org.eclipse.emfcloud.jackson.junit.generics.Base;
import org.eclipse.emfcloud.jackson.junit.generics.BaseOne;
import org.eclipse.emfcloud.jackson.junit.generics.Content;
import org.eclipse.emfcloud.jackson.junit.generics.ContentA;
import org.eclipse.emfcloud.jackson.junit.generics.GenericContainer;
import org.eclipse.emfcloud.jackson.junit.generics.GenericType;
import org.eclipse.emfcloud.jackson.junit.generics.GenericsFactory;
import org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage;
import org.eclipse.emfcloud.jackson.junit.generics.OtherContainer;
import org.eclipse.emfcloud.jackson.junit.generics.Some;
import org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeOne;
import org.eclipse.emfcloud.jackson.junit.generics.SpecialTypeTwo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GenericsPackageImpl extends EPackageImpl implements GenericsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specialTypeOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specialTypeTwoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass baseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass someEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass baseOneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass otherContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contentAEClass = null;

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
	 * @see org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GenericsPackageImpl() {
		super(eNS_URI, GenericsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link GenericsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GenericsPackage init() {
		if (isInited) return (GenericsPackage)EPackage.Registry.INSTANCE.getEPackage(GenericsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredGenericsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		GenericsPackageImpl theGenericsPackage = registeredGenericsPackage instanceof GenericsPackageImpl ? (GenericsPackageImpl)registeredGenericsPackage : new GenericsPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theGenericsPackage.createPackageContents();

		// Initialize created meta-data
		theGenericsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGenericsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GenericsPackage.eNS_URI, theGenericsPackage);
		return theGenericsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGenericContainer() {
		return genericContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGenericContainer_Values() {
		return (EReference)genericContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGenericType() {
		return genericTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGenericType_Value() {
		return (EAttribute)genericTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGenericType_Values() {
		return (EAttribute)genericTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSpecialTypeOne() {
		return specialTypeOneEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSpecialTypeTwo() {
		return specialTypeTwoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBase() {
		return baseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBase_LinkTo() {
		return (EReference)baseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBase_LinkToMany() {
		return (EReference)baseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBase_ContainsOne() {
		return (EReference)baseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBase_ContainsMany() {
		return (EReference)baseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSome() {
		return someEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAny() {
		return anyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBaseOne() {
		return baseOneEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOtherContainer() {
		return otherContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOtherContainer_Key() {
		return (EAttribute)otherContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOtherContainer_Content() {
		return (EReference)otherContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContent() {
		return contentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContentA() {
		return contentAEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContentA_Payload() {
		return (EAttribute)contentAEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenericsFactory getGenericsFactory() {
		return (GenericsFactory)getEFactoryInstance();
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
		genericContainerEClass = createEClass(GENERIC_CONTAINER);
		createEReference(genericContainerEClass, GENERIC_CONTAINER__VALUES);

		genericTypeEClass = createEClass(GENERIC_TYPE);
		createEAttribute(genericTypeEClass, GENERIC_TYPE__VALUE);
		createEAttribute(genericTypeEClass, GENERIC_TYPE__VALUES);

		specialTypeOneEClass = createEClass(SPECIAL_TYPE_ONE);

		specialTypeTwoEClass = createEClass(SPECIAL_TYPE_TWO);

		baseEClass = createEClass(BASE);
		createEReference(baseEClass, BASE__LINK_TO);
		createEReference(baseEClass, BASE__LINK_TO_MANY);
		createEReference(baseEClass, BASE__CONTAINS_ONE);
		createEReference(baseEClass, BASE__CONTAINS_MANY);

		someEClass = createEClass(SOME);

		anyEClass = createEClass(ANY);

		baseOneEClass = createEClass(BASE_ONE);

		otherContainerEClass = createEClass(OTHER_CONTAINER);
		createEAttribute(otherContainerEClass, OTHER_CONTAINER__KEY);
		createEReference(otherContainerEClass, OTHER_CONTAINER__CONTENT);

		contentEClass = createEClass(CONTENT);

		contentAEClass = createEClass(CONTENT_A);
		createEAttribute(contentAEClass, CONTENT_A__PAYLOAD);
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
		ETypeParameter genericTypeEClass_T = addETypeParameter(genericTypeEClass, "T");
		ETypeParameter baseEClass_U = addETypeParameter(baseEClass, "U");
		ETypeParameter baseEClass_T = addETypeParameter(baseEClass, "T");
		ETypeParameter otherContainerEClass_ContentT = addETypeParameter(otherContainerEClass, "ContentT");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getContent());
		otherContainerEClass_ContentT.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(this.getGenericType());
		EGenericType g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		specialTypeOneEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getGenericType());
		g2 = createEGenericType(ecorePackage.getEBooleanObject());
		g1.getETypeArguments().add(g2);
		specialTypeTwoEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getBase());
		g2 = createEGenericType(this.getSome());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getAny());
		g1.getETypeArguments().add(g2);
		baseOneEClass.getEGenericSuperTypes().add(g1);
		contentAEClass.getESuperTypes().add(this.getContent());

		// Initialize classes, features, and operations; add parameters
		initEClass(genericContainerEClass, GenericContainer.class, "GenericContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getGenericType());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getGenericContainer_Values(), g1, null, "values", null, 0, -1, GenericContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericTypeEClass, GenericType.class, "GenericType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(genericTypeEClass_T);
		initEAttribute(getGenericType_Value(), g1, "value", null, 0, 1, GenericType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(genericTypeEClass_T);
		initEAttribute(getGenericType_Values(), g1, "values", null, 0, -1, GenericType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(specialTypeOneEClass, SpecialTypeOne.class, "SpecialTypeOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(specialTypeTwoEClass, SpecialTypeTwo.class, "SpecialTypeTwo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(baseEClass, Base.class, "Base", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(baseEClass_U);
		initEReference(getBase_LinkTo(), g1, null, "linkTo", null, 0, 1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(baseEClass_U);
		initEReference(getBase_LinkToMany(), g1, null, "linkToMany", null, 0, -1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(baseEClass_T);
		initEReference(getBase_ContainsOne(), g1, null, "containsOne", null, 0, 1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(baseEClass_T);
		initEReference(getBase_ContainsMany(), g1, null, "containsMany", null, 0, -1, Base.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(someEClass, Some.class, "Some", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(anyEClass, Any.class, "Any", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(baseOneEClass, BaseOne.class, "BaseOne", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(otherContainerEClass, OtherContainer.class, "OtherContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOtherContainer_Key(), ecorePackage.getEString(), "key", null, 0, 1, OtherContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(otherContainerEClass_ContentT);
		initEReference(getOtherContainer_Content(), g1, null, "content", null, 0, 1, OtherContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contentEClass, Content.class, "Content", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(contentAEClass, ContentA.class, "ContentA", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContentA_Payload(), ecorePackage.getEString(), "payload", null, 0, 1, ContentA.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //GenericsPackageImpl
