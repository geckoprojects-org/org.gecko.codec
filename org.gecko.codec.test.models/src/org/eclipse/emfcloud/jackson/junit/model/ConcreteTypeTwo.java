/*
 */
package org.eclipse.emfcloud.jackson.junit.model;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concrete Type Two</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo#getPropTypeTwo <em>Prop Type Two</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getConcreteTypeTwo()
 * @model
 * @generated
 */
@ProviderType
public interface ConcreteTypeTwo extends AbstractType {
	/**
	 * Returns the value of the '<em><b>Prop Type Two</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prop Type Two</em>' attribute.
	 * @see #setPropTypeTwo(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getConcreteTypeTwo_PropTypeTwo()
	 * @model unique="false"
	 * @generated
	 */
	String getPropTypeTwo();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo#getPropTypeTwo <em>Prop Type Two</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prop Type Two</em>' attribute.
	 * @see #getPropTypeTwo()
	 * @generated
	 */
	void setPropTypeTwo(String value);

} // ConcreteTypeTwo
