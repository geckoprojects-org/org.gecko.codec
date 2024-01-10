/*
 */
package org.eclipse.emfcloud.jackson.junit.annotations;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Raw Json</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.RawJson#getRaw <em>Raw</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getRawJson()
 * @model
 * @generated
 */
@ProviderType
public interface RawJson extends EObject {
	/**
	 * Returns the value of the '<em><b>Raw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Raw</em>' attribute.
	 * @see #setRaw(String)
	 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getRawJson_Raw()
	 * @model unique="false"
	 *        annotation="JsonRawValue value='true'"
	 * @generated
	 */
	String getRaw();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.RawJson#getRaw <em>Raw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Raw</em>' attribute.
	 * @see #getRaw()
	 * @generated
	 */
	void setRaw(String value);

} // RawJson
