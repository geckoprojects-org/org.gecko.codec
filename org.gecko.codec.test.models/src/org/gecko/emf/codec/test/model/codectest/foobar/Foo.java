/*
 */
package org.gecko.emf.codec.test.model.codectest.foobar;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Foo</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.foobar.Foo#getBar <em>Bar</em>}</li>
 * </ul>
 *
 * @see org.gecko.emf.codec.test.model.codectest.foobar.FoobarPackage#getFoo()
 * @model
 * @generated
 */
@ProviderType
public interface Foo extends EObject {
	/**
	 * Returns the value of the '<em><b>Bar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bar</em>' attribute.
	 * @see #setBar(int)
	 * @see org.gecko.emf.codec.test.model.codectest.foobar.FoobarPackage#getFoo_Bar()
	 * @model
	 * @generated
	 */
	int getBar();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.foobar.Foo#getBar <em>Bar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bar</em>' attribute.
	 * @see #getBar()
	 * @generated
	 */
	void setBar(int value);

} // Foo
