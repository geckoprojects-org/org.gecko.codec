/*
 */
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.Method#getTags <em>Tags</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Method#getSummary <em>Summary</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Method#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Method#getOperationId <em>Operation Id</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Method#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Method#getResponses <em>Responses</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.Method#getSecurity <em>Security</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getMethod()
 * @model abstract="true"
 * @generated
 */
@ProviderType
public interface Method extends EObject {
	/**
	 * Returns the value of the '<em><b>Tags</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tags</em>' attribute list.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getMethod_Tags()
	 * @model
	 * @generated
	 */
	EList<String> getTags();

	/**
	 * Returns the value of the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Summary</em>' attribute.
	 * @see #setSummary(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getMethod_Summary()
	 * @model
	 * @generated
	 */
	String getSummary();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Method#getSummary <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Summary</em>' attribute.
	 * @see #getSummary()
	 * @generated
	 */
	void setSummary(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getMethod_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Method#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Operation Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Id</em>' attribute.
	 * @see #setOperationId(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getMethod_OperationId()
	 * @model
	 * @generated
	 */
	String getOperationId();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.Method#getOperationId <em>Operation Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation Id</em>' attribute.
	 * @see #getOperationId()
	 * @generated
	 */
	void setOperationId(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.openapi.model.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getMethod_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Responses</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.fennec.openapi.model.Response},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Responses</em>' map.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getMethod_Responses()
	 * @model mapType="org.eclipse.fennec.openapi.model.StringToResponseMap&lt;org.eclipse.emf.ecore.EString, org.eclipse.fennec.openapi.model.Response&gt;"
	 * @generated
	 */
	EMap<String, Response> getResponses();

	/**
	 * Returns the value of the '<em><b>Security</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.openapi.model.Security}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security</em>' containment reference list.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getMethod_Security()
	 * @model containment="true"
	 * @generated
	 */
	EList<Security> getSecurity();

} // Method
