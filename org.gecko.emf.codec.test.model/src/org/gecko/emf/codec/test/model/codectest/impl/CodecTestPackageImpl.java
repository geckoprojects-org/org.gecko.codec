/**
 */
package org.gecko.emf.codec.test.model.codectest.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.gecko.emf.codec.test.model.codectest.Address;
import org.gecko.emf.codec.test.model.codectest.BusinessPerson;
import org.gecko.emf.codec.test.model.codectest.CodecTestFactory;
import org.gecko.emf.codec.test.model.codectest.CodecTestPackage;
import org.gecko.emf.codec.test.model.codectest.Contact;
import org.gecko.emf.codec.test.model.codectest.ContactType;
import org.gecko.emf.codec.test.model.codectest.Person;

import org.gecko.emf.codec.test.model.codectest.foobar.FoobarPackage;

import org.gecko.emf.codec.test.model.codectest.foobar.impl.FoobarPackageImpl;

import org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage;

import org.gecko.emf.codec.test.model.codectest.subpackage.impl.SubpackagePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CodecTestPackageImpl extends EPackageImpl implements CodecTestPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contactEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass personEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass businessPersonEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addressEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum contactTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType demoDataTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType demoDataTypeNoSerializeEDataType = null;

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
	 * @see org.gecko.emf.codec.test.model.codectest.CodecTestPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CodecTestPackageImpl() {
		super(eNS_URI, CodecTestFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CodecTestPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CodecTestPackage init() {
		if (isInited) return (CodecTestPackage)EPackage.Registry.INSTANCE.getEPackage(CodecTestPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCodecTestPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CodecTestPackageImpl theCodecTestPackage = registeredCodecTestPackage instanceof CodecTestPackageImpl ? (CodecTestPackageImpl)registeredCodecTestPackage : new CodecTestPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(SubpackagePackage.eNS_URI);
		SubpackagePackageImpl theSubpackagePackage = (SubpackagePackageImpl)(registeredPackage instanceof SubpackagePackageImpl ? registeredPackage : SubpackagePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(FoobarPackage.eNS_URI);
		FoobarPackageImpl theFoobarPackage = (FoobarPackageImpl)(registeredPackage instanceof FoobarPackageImpl ? registeredPackage : FoobarPackage.eINSTANCE);

		// Create package meta-data objects
		theCodecTestPackage.createPackageContents();
		theSubpackagePackage.createPackageContents();
		theFoobarPackage.createPackageContents();

		// Initialize created meta-data
		theCodecTestPackage.initializePackageContents();
		theSubpackagePackage.initializePackageContents();
		theFoobarPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCodecTestPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CodecTestPackage.eNS_URI, theCodecTestPackage);
		return theCodecTestPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContact() {
		return contactEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContact_Value() {
		return (EAttribute)contactEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContact_Type() {
		return (EAttribute)contactEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPerson() {
		return personEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPerson_PersonId() {
		return (EAttribute)personEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPerson_FirstName() {
		return (EAttribute)personEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPerson_LastName() {
		return (EAttribute)personEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPerson_BirthDate() {
		return (EAttribute)personEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPerson_Note() {
		return (EAttribute)personEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPerson_Contacts() {
		return (EReference)personEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPerson_Address() {
		return (EReference)personEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPerson_Password() {
		return (EAttribute)personEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBusinessPerson() {
		return businessPersonEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBusinessPerson_Position() {
		return (EAttribute)businessPersonEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAddress() {
		return addressEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddress_Street() {
		return (EAttribute)addressEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddress_City() {
		return (EAttribute)addressEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddress_Zip() {
		return (EAttribute)addressEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAddress_Number() {
		return (EAttribute)addressEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getContactType() {
		return contactTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDemoDataType() {
		return demoDataTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDemoDataTypeNoSerialize() {
		return demoDataTypeNoSerializeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CodecTestFactory getCodecTestFactory() {
		return (CodecTestFactory)getEFactoryInstance();
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
		contactEClass = createEClass(CONTACT);
		createEAttribute(contactEClass, CONTACT__VALUE);
		createEAttribute(contactEClass, CONTACT__TYPE);

		personEClass = createEClass(PERSON);
		createEAttribute(personEClass, PERSON__PERSON_ID);
		createEAttribute(personEClass, PERSON__FIRST_NAME);
		createEAttribute(personEClass, PERSON__LAST_NAME);
		createEAttribute(personEClass, PERSON__BIRTH_DATE);
		createEAttribute(personEClass, PERSON__NOTE);
		createEReference(personEClass, PERSON__CONTACTS);
		createEReference(personEClass, PERSON__ADDRESS);
		createEAttribute(personEClass, PERSON__PASSWORD);

		businessPersonEClass = createEClass(BUSINESS_PERSON);
		createEAttribute(businessPersonEClass, BUSINESS_PERSON__POSITION);

		addressEClass = createEClass(ADDRESS);
		createEAttribute(addressEClass, ADDRESS__STREET);
		createEAttribute(addressEClass, ADDRESS__CITY);
		createEAttribute(addressEClass, ADDRESS__ZIP);
		createEAttribute(addressEClass, ADDRESS__NUMBER);

		// Create enums
		contactTypeEEnum = createEEnum(CONTACT_TYPE);

		// Create data types
		demoDataTypeEDataType = createEDataType(DEMO_DATA_TYPE);
		demoDataTypeNoSerializeEDataType = createEDataType(DEMO_DATA_TYPE_NO_SERIALIZE);
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
		SubpackagePackage theSubpackagePackage = (SubpackagePackage)EPackage.Registry.INSTANCE.getEPackage(SubpackagePackage.eNS_URI);
		FoobarPackage theFoobarPackage = (FoobarPackage)EPackage.Registry.INSTANCE.getEPackage(FoobarPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theSubpackagePackage);
		getESubpackages().add(theFoobarPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		businessPersonEClass.getESuperTypes().add(this.getPerson());

		// Initialize classes, features, and operations; add parameters
		initEClass(contactEClass, Contact.class, "Contact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContact_Value(), ecorePackage.getEString(), "value", null, 0, 1, Contact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContact_Type(), this.getContactType(), "type", "Phone", 0, 1, Contact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(personEClass, Person.class, "Person", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPerson_PersonId(), ecorePackage.getEString(), "personId", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPerson_FirstName(), ecorePackage.getEString(), "firstName", null, 1, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPerson_LastName(), ecorePackage.getEString(), "lastName", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPerson_BirthDate(), ecorePackage.getEDate(), "birthDate", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPerson_Note(), this.getDemoDataType(), "note", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPerson_Contacts(), this.getContact(), null, "contacts", null, 1, -1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPerson_Address(), this.getAddress(), null, "address", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getPerson_Address().getEKeys().add(this.getAddress_Street());
		getPerson_Address().getEKeys().add(this.getAddress_Number());
		getPerson_Address().getEKeys().add(this.getAddress_City());
		getPerson_Address().getEKeys().add(this.getAddress_Zip());
		initEAttribute(getPerson_Password(), ecorePackage.getEString(), "password", null, 0, 1, Person.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(businessPersonEClass, BusinessPerson.class, "BusinessPerson", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBusinessPerson_Position(), ecorePackage.getEString(), "position", null, 0, 1, BusinessPerson.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(addressEClass, Address.class, "Address", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAddress_Street(), ecorePackage.getEString(), "street", null, 0, 1, Address.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddress_City(), ecorePackage.getEString(), "city", null, 0, 1, Address.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddress_Zip(), ecorePackage.getEString(), "zip", null, 0, 1, Address.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddress_Number(), ecorePackage.getEString(), "number", null, 0, 1, Address.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(contactTypeEEnum, ContactType.class, "ContactType");
		addEEnumLiteral(contactTypeEEnum, ContactType.EMAIL);
		addEEnumLiteral(contactTypeEEnum, ContactType.PHONE);

		// Initialize data types
		initEDataType(demoDataTypeEDataType, String.class, "DemoDataType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(demoDataTypeNoSerializeEDataType, String.class, "DemoDataTypeNoSerialize", !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// Version
		createVersionAnnotations();
		// http://geckoprojects.org/codec/1.0.0
		create_1Annotations();
		// foo
		createFooAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
		// https://geckoprojects.org/emf/codec/1.0.0
		create_1_1Annotations();
	}

	/**
	 * Initializes the annotations for <b>Version</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createVersionAnnotations() {
		String source = "Version";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "value", "1.0"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://geckoprojects.org/codec/1.0.0</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void create_1Annotations() {
		String source = "http://geckoprojects.org/codec/1.0.0";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "name", "CodecTest"
		   });
		addAnnotation
		  (personEClass,
		   source,
		   new String[] {
			   "name", "CodecPerson"
		   });
		addAnnotation
		  (getPerson_LastName(),
		   source,
		   new String[] {
			   "name", "CodecLastName"
		   });
		addAnnotation
		  (getPerson_BirthDate(),
		   source,
		   new String[] {
			   "name", "CodecBirthDate"
		   });
		addAnnotation
		  (getPerson_Contacts(),
		   source,
		   new String[] {
			   "name", "ContactList"
		   });
		addAnnotation
		  (getPerson_Address(),
		   source,
		   new String[] {
			   "name", "AddressList"
		   });
		addAnnotation
		  (businessPersonEClass,
		   source,
		   new String[] {
			   "name", "CodecBusinessPerson"
		   });
		addAnnotation
		  (addressEClass,
		   source,
		   new String[] {
			   "name", "CodecAddress"
		   });
	}

	/**
	 * Initializes the annotations for <b>foo</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createFooAnnotations() {
		String source = "foo";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "bar", "PersonIdentifier"
		   });
		addAnnotation
		  (contactEClass,
		   source,
		   new String[] {
			   "name", "CustomContact"
		   });
		addAnnotation
		  (getPerson_PersonId(),
		   source,
		   new String[] {
			   "bar", "PersonIdentifier"
		   });
		addAnnotation
		  (getPerson_BirthDate(),
		   source,
		   new String[] {
			   "bar", "Geburtsdatum"
		   });
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation
		  (getPerson_FirstName(),
		   source,
		   new String[] {
			   "name", "EMDFirstName"
		   });
		addAnnotation
		  (getPerson_LastName(),
		   source,
		   new String[] {
			   "name", "EMDLastName"
		   });
		addAnnotation
		  (getPerson_BirthDate(),
		   source,
		   new String[] {
			   "name", "EMDBirthDate"
		   });
		addAnnotation
		  (businessPersonEClass,
		   source,
		   new String[] {
			   "name", "EMDBusinessPerson"
		   });
	}

	/**
	 * Initializes the annotations for <b>https://geckoprojects.org/emf/codec/1.0.0</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void create_1_1Annotations() {
		String source = "https://geckoprojects.org/emf/codec/1.0.0";
		addAnnotation
		  (getAddress_Street(),
		   source,
		   new String[] {
			   "name", "StreetName"
		   });
	}

} //CodecTestPackageImpl
