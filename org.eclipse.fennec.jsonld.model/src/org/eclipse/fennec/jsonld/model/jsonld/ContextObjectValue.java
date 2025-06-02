/*
 */
package org.eclipse.fennec.jsonld.model.jsonld;

import org.eclipse.emf.common.util.EMap;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context Object Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.jsonld.model.jsonld.ContextObjectValue#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage#getContextObjectValue()
 * @model
 * @generated
 */
@ProviderType
public interface ContextObjectValue extends ContextValue {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' map.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage#getContextObjectValue_Properties()
	 * @model mapType="org.eclipse.fennec.jsonld.model.jsonld.ContextTerm&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;"
	 * @generated
	 */
	EMap<String, String> getProperties();

} // ContextObjectValue
