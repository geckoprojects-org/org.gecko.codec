/*
 */
package org.eclipse.fennec.openapi.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Open Api</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApi#getOpenapi <em>Openapi</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApi#getInfo <em>Info</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApi#getServers <em>Servers</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApi#getPaths <em>Paths</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApi#getComponents <em>Components</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApi#getExternalDocs <em>External Docs</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.OpenApi#getTags <em>Tags</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApi()
 * @model
 * @generated
 */
@ProviderType
public interface OpenApi extends EObject {
	/**
	 * Returns the value of the '<em><b>Openapi</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Openapi</em>' attribute.
	 * @see #setOpenapi(String)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApi_Openapi()
	 * @model
	 * @generated
	 */
	String getOpenapi();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.OpenApi#getOpenapi <em>Openapi</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Openapi</em>' attribute.
	 * @see #getOpenapi()
	 * @generated
	 */
	void setOpenapi(String value);

	/**
	 * Returns the value of the '<em><b>Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info</em>' containment reference.
	 * @see #setInfo(Info)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApi_Info()
	 * @model containment="true"
	 * @generated
	 */
	Info getInfo();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.OpenApi#getInfo <em>Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Info</em>' containment reference.
	 * @see #getInfo()
	 * @generated
	 */
	void setInfo(Info value);

	/**
	 * Returns the value of the '<em><b>Servers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.openapi.model.Server}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Servers</em>' containment reference list.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApi_Servers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Server> getServers();

	/**
	 * Returns the value of the '<em><b>Paths</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.fennec.openapi.model.Path},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paths</em>' map.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApi_Paths()
	 * @model mapType="org.eclipse.fennec.openapi.model.StringToPathMap&lt;org.eclipse.emf.ecore.EString, org.eclipse.fennec.openapi.model.Path&gt;"
	 * @generated
	 */
	EMap<String, Path> getPaths();

	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' containment reference.
	 * @see #setComponents(Components)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApi_Components()
	 * @model containment="true"
	 * @generated
	 */
	Components getComponents();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.OpenApi#getComponents <em>Components</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Components</em>' containment reference.
	 * @see #getComponents()
	 * @generated
	 */
	void setComponents(Components value);

	/**
	 * Returns the value of the '<em><b>External Docs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Docs</em>' containment reference.
	 * @see #setExternalDocs(Docs)
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApi_ExternalDocs()
	 * @model containment="true"
	 * @generated
	 */
	Docs getExternalDocs();

	/**
	 * Sets the value of the '{@link org.eclipse.fennec.openapi.model.OpenApi#getExternalDocs <em>External Docs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Docs</em>' containment reference.
	 * @see #getExternalDocs()
	 * @generated
	 */
	void setExternalDocs(Docs value);

	/**
	 * Returns the value of the '<em><b>Tags</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fennec.openapi.model.Tag}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tags</em>' containment reference list.
	 * @see org.eclipse.fennec.openapi.model.OpenApiPackage#getOpenApi_Tags()
	 * @model containment="true"
	 * @generated
	 */
	EList<Tag> getTags();

} // OpenApi
