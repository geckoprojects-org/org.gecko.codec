/**
 */
package org.eclipse.fennec.jsonld.model.jsonld.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fennec.jsonld.model.jsonld.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JsonLDFactoryImpl extends EFactoryImpl implements JsonLDFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JsonLDFactory init() {
		try {
			JsonLDFactory theJsonLDFactory = (JsonLDFactory)EPackage.Registry.INSTANCE.getEFactory(JsonLDPackage.eNS_URI);
			if (theJsonLDFactory != null) {
				return theJsonLDFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new JsonLDFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JsonLDFactoryImpl() {
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
			case JsonLDPackage.CONTEXT_STRING_VALUE: return createContextStringValue();
			case JsonLDPackage.CONTEXT_OBJECT_VALUE: return createContextObjectValue();
			case JsonLDPackage.CONTEXT_TERM: return (EObject)createContextTerm();
			case JsonLDPackage.CONTEXT_OBJECT: return createContextObject();
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
	public ContextStringValue createContextStringValue() {
		ContextStringValueImpl contextStringValue = new ContextStringValueImpl();
		return contextStringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContextObjectValue createContextObjectValue() {
		ContextObjectValueImpl contextObjectValue = new ContextObjectValueImpl();
		return contextObjectValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createContextTerm() {
		ContextTermImpl contextTerm = new ContextTermImpl();
		return contextTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContextObject createContextObject() {
		ContextObjectImpl contextObject = new ContextObjectImpl();
		return contextObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JsonLDPackage getJsonLDPackage() {
		return (JsonLDPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static JsonLDPackage getPackage() {
		return JsonLDPackage.eINSTANCE;
	}

} //JsonLDFactoryImpl
