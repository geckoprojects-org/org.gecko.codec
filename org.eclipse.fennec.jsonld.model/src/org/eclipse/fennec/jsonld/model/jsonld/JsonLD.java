/*
 */
package org.eclipse.fennec.jsonld.model.jsonld;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Json LD</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.jsonld.model.jsonld.JsonLD#getContext <em>Context</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage#getJsonLD()
 * @model
 * @generated
 */
@ProviderType
public interface JsonLD extends EObject {
	/**
	 * Returns the value of the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' containment reference.
	 * @see #setContext(ContextObject)
	 * @see org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage#getJsonLD_Context()
	 * @model containment="true"
	 *        annotation="JsonProperty value='@context'"
	 * @generated
	 */
	ContextObject getContext();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.jsonld.model.jsonld.JsonLD#getContext <em>Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' containment reference.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(ContextObject value);

} // JsonLD
