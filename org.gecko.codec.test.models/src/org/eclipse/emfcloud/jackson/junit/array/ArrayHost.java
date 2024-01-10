/*
 */
package org.eclipse.emfcloud.jackson.junit.array;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Host</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD1 <em>D1</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD2 <em>D2</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD3 <em>D3</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getS2 <em>S2</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getB <em>B</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayPackage#getArrayHost()
 * @model
 * @generated
 */
@ProviderType
public interface ArrayHost extends EObject {
	/**
	 * Returns the value of the '<em><b>D1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>D1</em>' attribute.
	 * @see #setD1(Double[])
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayPackage#getArrayHost_D1()
	 * @model unique="false" dataType="org.eclipse.emfcloud.jackson.junit.array.double1D"
	 * @generated
	 */
	Double[] getD1();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD1 <em>D1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>D1</em>' attribute.
	 * @see #getD1()
	 * @generated
	 */
	void setD1(Double[] value);

	/**
	 * Returns the value of the '<em><b>D2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>D2</em>' attribute.
	 * @see #setD2(Double[][])
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayPackage#getArrayHost_D2()
	 * @model unique="false" dataType="org.eclipse.emfcloud.jackson.junit.array.double2D"
	 * @generated
	 */
	Double[][] getD2();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD2 <em>D2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>D2</em>' attribute.
	 * @see #getD2()
	 * @generated
	 */
	void setD2(Double[][] value);

	/**
	 * Returns the value of the '<em><b>D3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>D3</em>' attribute.
	 * @see #setD3(Double[][][])
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayPackage#getArrayHost_D3()
	 * @model unique="false" dataType="org.eclipse.emfcloud.jackson.junit.array.double3D"
	 * @generated
	 */
	Double[][][] getD3();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getD3 <em>D3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>D3</em>' attribute.
	 * @see #getD3()
	 * @generated
	 */
	void setD3(Double[][][] value);

	/**
	 * Returns the value of the '<em><b>S2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>S2</em>' attribute.
	 * @see #setS2(String[][])
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayPackage#getArrayHost_S2()
	 * @model unique="false" dataType="org.eclipse.emfcloud.jackson.junit.array.string2D"
	 * @generated
	 */
	String[][] getS2();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getS2 <em>S2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>S2</em>' attribute.
	 * @see #getS2()
	 * @generated
	 */
	void setS2(String[][] value);

	/**
	 * Returns the value of the '<em><b>B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>B</em>' attribute.
	 * @see #setB(byte[])
	 * @see org.eclipse.emfcloud.jackson.junit.array.ArrayPackage#getArrayHost_B()
	 * @model unique="false"
	 * @generated
	 */
	byte[] getB();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.array.ArrayHost#getB <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>B</em>' attribute.
	 * @see #getB()
	 * @generated
	 */
	void setB(byte[] value);

} // ArrayHost
