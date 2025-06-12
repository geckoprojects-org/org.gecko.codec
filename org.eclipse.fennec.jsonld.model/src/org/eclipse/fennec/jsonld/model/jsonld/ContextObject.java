/*
 */
package org.eclipse.fennec.jsonld.model.jsonld;

import org.eclipse.emf.common.util.EMap;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.jsonld.model.jsonld.ContextObject#getContext <em>Context</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage#getContextObject()
 * @model
 * @generated
 */
@ProviderType
public interface ContextObject extends ContextValue {
	/**
	 * Returns the value of the '<em><b>Context</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.fennec.jsonld.model.jsonld.ContextValue},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' map.
	 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage#getContextObject_Context()
	 * @model mapType="org.eclipse.fennec.jsonld.model.jsonld.ContextTerm&lt;org.eclipse.emf.ecore.EString, org.eclipse.fennec.jsonld.model.jsonld.ContextValue&gt;"
	 * @generated
	 */
	EMap<String, ContextValue> getContext();

} // ContextObject
