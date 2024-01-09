/*
 */
package org.eclipse.emfcloud.jackson.junit.model;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concrete Type One</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne#getPropTypeOne <em>Prop Type One</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getConcreteTypeOne()
 * @model
 * @generated
 */
@ProviderType
public interface ConcreteTypeOne extends AbstractType {
	/**
	 * Returns the value of the '<em><b>Prop Type One</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prop Type One</em>' attribute.
	 * @see #setPropTypeOne(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getConcreteTypeOne_PropTypeOne()
	 * @model unique="false"
	 * @generated
	 */
	String getPropTypeOne();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne#getPropTypeOne <em>Prop Type One</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prop Type One</em>' attribute.
	 * @see #getPropTypeOne()
	 * @generated
	 */
	void setPropTypeOne(String value);

} // ConcreteTypeOne
