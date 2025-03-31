/*
 */
package org.eclipse.emfcloud.jackson.junit.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleAttribute <em>Single Attribute</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getArrayAttribute <em>Array Attribute</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReference <em>Single Reference</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getManyReferences <em>Many References</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getManyReferencesNotResolveProxies <em>Many References Not Resolve Proxies</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getTargetObject()
 * @model
 * @generated
 */
@ProviderType
public interface TargetObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Attribute</em>' attribute.
	 * @see #setSingleAttribute(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getTargetObject_SingleAttribute()
	 * @model unique="false"
	 * @generated
	 */
	String getSingleAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleAttribute <em>Single Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Attribute</em>' attribute.
	 * @see #getSingleAttribute()
	 * @generated
	 */
	void setSingleAttribute(String value);

	/**
	 * Returns the value of the '<em><b>Array Attribute</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Attribute</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getTargetObject_ArrayAttribute()
	 * @model
	 * @generated
	 */
	EList<String> getArrayAttribute();

	/**
	 * Returns the value of the '<em><b>Single Reference</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getManyReferences <em>Many References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Reference</em>' reference.
	 * @see #setSingleReference(PrimaryObject)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getTargetObject_SingleReference()
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getManyReferences
	 * @model opposite="manyReferences"
	 * @generated
	 */
	PrimaryObject getSingleReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReference <em>Single Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Reference</em>' reference.
	 * @see #getSingleReference()
	 * @generated
	 */
	void setSingleReference(PrimaryObject value);

	/**
	 * Returns the value of the '<em><b>Many References</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many References</em>' reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getTargetObject_ManyReferences()
	 * @model
	 * @generated
	 */
	EList<PrimaryObject> getManyReferences();

	/**
	 * Returns the value of the '<em><b>Single Reference Not Resolve Proxies</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceProxies <em>Single Containment Reference Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Reference Not Resolve Proxies</em>' container reference.
	 * @see #setSingleReferenceNotResolveProxies(PrimaryObject)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getTargetObject_SingleReferenceNotResolveProxies()
	 * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceProxies
	 * @model opposite="singleContainmentReferenceProxies" resolveProxies="false" transient="false"
	 * @generated
	 */
	PrimaryObject getSingleReferenceNotResolveProxies();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Reference Not Resolve Proxies</em>' container reference.
	 * @see #getSingleReferenceNotResolveProxies()
	 * @generated
	 */
	void setSingleReferenceNotResolveProxies(PrimaryObject value);

	/**
	 * Returns the value of the '<em><b>Many References Not Resolve Proxies</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many References Not Resolve Proxies</em>' reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getTargetObject_ManyReferencesNotResolveProxies()
	 * @model resolveProxies="false"
	 * @generated
	 */
	EList<PrimaryObject> getManyReferencesNotResolveProxies();

} // TargetObject
