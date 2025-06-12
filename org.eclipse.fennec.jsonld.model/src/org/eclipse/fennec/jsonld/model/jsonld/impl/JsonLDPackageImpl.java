/**
 */
package org.eclipse.fennec.jsonld.model.jsonld.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fennec.jsonld.model.jsonld.ContextObject;
import org.eclipse.fennec.jsonld.model.jsonld.ContextStringValue;
import org.eclipse.fennec.jsonld.model.jsonld.ContextValue;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLD;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLDFactory;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JsonLDPackageImpl extends EPackageImpl implements JsonLDPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contextValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contextStringValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contextTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contextObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonLDEClass = null;

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
	 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private JsonLDPackageImpl() {
		super(eNS_URI, JsonLDFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link JsonLDPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static JsonLDPackage init() {
		if (isInited) return (JsonLDPackage)EPackage.Registry.INSTANCE.getEPackage(JsonLDPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredJsonLDPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		JsonLDPackageImpl theJsonLDPackage = registeredJsonLDPackage instanceof JsonLDPackageImpl ? (JsonLDPackageImpl)registeredJsonLDPackage : new JsonLDPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theJsonLDPackage.createPackageContents();

		// Initialize created meta-data
		theJsonLDPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theJsonLDPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(JsonLDPackage.eNS_URI, theJsonLDPackage);
		return theJsonLDPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContextValue() {
		return contextValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContextStringValue() {
		return contextStringValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContextStringValue_Value() {
		return (EAttribute)contextStringValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContextTerm() {
		return contextTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContextTerm_Key() {
		return (EAttribute)contextTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContextTerm_Value() {
		return (EReference)contextTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContextObject() {
		return contextObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContextObject_Context() {
		return (EReference)contextObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJsonLD() {
		return jsonLDEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJsonLD_Context() {
		return (EReference)jsonLDEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JsonLDFactory getJsonLDFactory() {
		return (JsonLDFactory)getEFactoryInstance();
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
		contextValueEClass = createEClass(CONTEXT_VALUE);

		contextStringValueEClass = createEClass(CONTEXT_STRING_VALUE);
		createEAttribute(contextStringValueEClass, CONTEXT_STRING_VALUE__VALUE);

		contextTermEClass = createEClass(CONTEXT_TERM);
		createEAttribute(contextTermEClass, CONTEXT_TERM__KEY);
		createEReference(contextTermEClass, CONTEXT_TERM__VALUE);

		contextObjectEClass = createEClass(CONTEXT_OBJECT);
		createEReference(contextObjectEClass, CONTEXT_OBJECT__CONTEXT);

		jsonLDEClass = createEClass(JSON_LD);
		createEReference(jsonLDEClass, JSON_LD__CONTEXT);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		contextStringValueEClass.getESuperTypes().add(this.getContextValue());
		contextObjectEClass.getESuperTypes().add(this.getContextValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(contextValueEClass, ContextValue.class, "ContextValue", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(contextStringValueEClass, ContextStringValue.class, "ContextStringValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContextStringValue_Value(), theEcorePackage.getEString(), "value", null, 0, 1, ContextStringValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextTermEClass, Map.Entry.class, "ContextTerm", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContextTerm_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContextTerm_Value(), this.getContextValue(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextObjectEClass, ContextObject.class, "ContextObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextObject_Context(), this.getContextTerm(), null, "context", null, 0, -1, ContextObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsonLDEClass, JsonLD.class, "JsonLD", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJsonLD_Context(), this.getContextObject(), null, "context", null, 0, 1, JsonLD.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// JsonProperty
		createJsonPropertyAnnotations();
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
		  (getJsonLD_Context(),
		   source,
		   new String[] {
			   "value", "@context"
		   });
	}

} //JsonLDPackageImpl
