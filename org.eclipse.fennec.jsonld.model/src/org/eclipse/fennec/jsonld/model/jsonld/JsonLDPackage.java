/*
 */
package org.eclipse.fennec.jsonld.model.jsonld;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import org.gecko.emf.osgi.annotation.provide.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = JsonLDPackage.eNS_URI, genModel = "/model/jsonld.genmodel", genModelSourceLocations = {"model/jsonld.genmodel","org.eclipse.fennec.jsonld.model/model/jsonld.genmodel"}, ecore="/model/jsonld.ecore", ecoreSourceLocations="/model/jsonld.ecore")
public interface JsonLDPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "jsonld";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/jsonld";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "jsonld";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	JsonLDPackage eINSTANCE = org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextValue <em>Context Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextValue
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextValue()
	 * @generated
	 */
	int CONTEXT_VALUE = 0;

	/**
	 * The number of structural features of the '<em>Context Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VALUE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Context Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextStringValueImpl <em>Context String Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.ContextStringValueImpl
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextStringValue()
	 * @generated
	 */
	int CONTEXT_STRING_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_STRING_VALUE__VALUE = CONTEXT_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_STRING_VALUE_FEATURE_COUNT = CONTEXT_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_STRING_VALUE_OPERATION_COUNT = CONTEXT_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectValueImpl <em>Context Object Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectValueImpl
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextObjectValue()
	 * @generated
	 */
	int CONTEXT_OBJECT_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OBJECT_VALUE__PROPERTIES = CONTEXT_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Object Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OBJECT_VALUE_FEATURE_COUNT = CONTEXT_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Object Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OBJECT_VALUE_OPERATION_COUNT = CONTEXT_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextTermImpl <em>Context Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.ContextTermImpl
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextTerm()
	 * @generated
	 */
	int CONTEXT_TERM = 3;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TERM__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TERM__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Context Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TERM_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Context Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TERM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectImpl <em>Context Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectImpl
	 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextObject()
	 * @generated
	 */
	int CONTEXT_OBJECT = 4;

	/**
	 * The feature id for the '<em><b>Context</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OBJECT__CONTEXT = 0;

	/**
	 * The number of structural features of the '<em>Context Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Context Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OBJECT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextValue <em>Context Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Value</em>'.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextValue
	 * @generated
	 */
	EClass getContextValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextStringValue <em>Context String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context String Value</em>'.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextStringValue
	 * @generated
	 */
	EClass getContextStringValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextStringValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextStringValue#getValue()
	 * @see #getContextStringValue()
	 * @generated
	 */
	EAttribute getContextStringValue_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextObjectValue <em>Context Object Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Object Value</em>'.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextObjectValue
	 * @generated
	 */
	EClass getContextObjectValue();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextObjectValue#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Properties</em>'.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextObjectValue#getProperties()
	 * @see #getContextObjectValue()
	 * @generated
	 */
	EReference getContextObjectValue_Properties();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Context Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Term</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getContextTerm();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getContextTerm()
	 * @generated
	 */
	EAttribute getContextTerm_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getContextTerm()
	 * @generated
	 */
	EAttribute getContextTerm_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextObject <em>Context Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Object</em>'.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextObject
	 * @generated
	 */
	EClass getContextObject();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextObject#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Context</em>'.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextObject#getContext()
	 * @see #getContextObject()
	 * @generated
	 */
	EReference getContextObject_Context();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	JsonLDFactory getJsonLDFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.jsonld.model.jsonld.ContextValue <em>Context Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.jsonld.model.jsonld.ContextValue
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextValue()
		 * @generated
		 */
		EClass CONTEXT_VALUE = eINSTANCE.getContextValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextStringValueImpl <em>Context String Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.ContextStringValueImpl
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextStringValue()
		 * @generated
		 */
		EClass CONTEXT_STRING_VALUE = eINSTANCE.getContextStringValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTEXT_STRING_VALUE__VALUE = eINSTANCE.getContextStringValue_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectValueImpl <em>Context Object Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectValueImpl
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextObjectValue()
		 * @generated
		 */
		EClass CONTEXT_OBJECT_VALUE = eINSTANCE.getContextObjectValue();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_OBJECT_VALUE__PROPERTIES = eINSTANCE.getContextObjectValue_Properties();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextTermImpl <em>Context Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.ContextTermImpl
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextTerm()
		 * @generated
		 */
		EClass CONTEXT_TERM = eINSTANCE.getContextTerm();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTEXT_TERM__KEY = eINSTANCE.getContextTerm_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTEXT_TERM__VALUE = eINSTANCE.getContextTerm_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectImpl <em>Context Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.ContextObjectImpl
		 * @see org.eclipse.fennec.jsonld.model.jsonld.impl.JsonLDPackageImpl#getContextObject()
		 * @generated
		 */
		EClass CONTEXT_OBJECT = eINSTANCE.getContextObject();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_OBJECT__CONTEXT = eINSTANCE.getContextObject_Context();

	}

} //JsonLDPackage
