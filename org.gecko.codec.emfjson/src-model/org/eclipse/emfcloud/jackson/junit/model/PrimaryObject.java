/*
 */
package org.eclipse.emfcloud.jackson.junit.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Primary Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getIdAttribute <em>Id Attribute</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttribute <em>Unsettable Attribute</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttributeWithNonNullDefault <em>Unsettable Attribute With Non Null Default</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableReference <em>Unsettable Reference</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleReference <em>Single Reference</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getManyReferences <em>Many References</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getManyReferenceNotResolveProxies <em>Many Reference Not Resolve Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getContainmentReferenceSameCollection <em>Containment Reference Same Collection</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceNoProxies <em>Single Containment Reference No Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getMultipleContainmentReferenceNoProxies <em>Multiple Containment Reference No Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceProxies <em>Single Containment Reference Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getMultipleContainmentReferenceProxies <em>Multiple Containment Reference Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceCollection <em>Feature Map Reference Collection</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceType1 <em>Feature Map Reference Type1</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapReferenceType2 <em>Feature Map Reference Type2</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeCollection <em>Feature Map Attribute Collection</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeType1 <em>Feature Map Attribute Type1</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getFeatureMapAttributeType2 <em>Feature Map Attribute Type2</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject()
 * @model
 * @generated
 */
@ProviderType
public interface PrimaryObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Id Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Attribute</em>' attribute.
	 * @see #setIdAttribute(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_IdAttribute()
	 * @model unique="false" id="true"
	 * @generated
	 */
	String getIdAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getIdAttribute <em>Id Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Attribute</em>' attribute.
	 * @see #getIdAttribute()
	 * @generated
	 */
	void setIdAttribute(String value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emfcloud.jackson.junit.model.SomeKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.emfcloud.jackson.junit.model.SomeKind
	 * @see #setKind(SomeKind)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_Kind()
	 * @model unique="false"
	 * @generated
	 */
	SomeKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.emfcloud.jackson.junit.model.SomeKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(SomeKind value);

	/**
	 * Returns the value of the '<em><b>Unsettable Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unsettable Attribute</em>' attribute.
	 * @see #isSetUnsettableAttribute()
	 * @see #unsetUnsettableAttribute()
	 * @see #setUnsettableAttribute(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_UnsettableAttribute()
	 * @model unique="false" unsettable="true"
	 * @generated
	 */
	String getUnsettableAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttribute <em>Unsettable Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unsettable Attribute</em>' attribute.
	 * @see #isSetUnsettableAttribute()
	 * @see #unsetUnsettableAttribute()
	 * @see #getUnsettableAttribute()
	 * @generated
	 */
	void setUnsettableAttribute(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttribute <em>Unsettable Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUnsettableAttribute()
	 * @see #getUnsettableAttribute()
	 * @see #setUnsettableAttribute(String)
	 * @generated
	 */
	void unsetUnsettableAttribute();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttribute <em>Unsettable Attribute</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unsettable Attribute</em>' attribute is set.
	 * @see #unsetUnsettableAttribute()
	 * @see #getUnsettableAttribute()
	 * @see #setUnsettableAttribute(String)
	 * @generated
	 */
	boolean isSetUnsettableAttribute();

	/**
	 * Returns the value of the '<em><b>Unsettable Attribute With Non Null Default</b></em>' attribute.
	 * The default value is <code>"junit"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unsettable Attribute With Non Null Default</em>' attribute.
	 * @see #isSetUnsettableAttributeWithNonNullDefault()
	 * @see #unsetUnsettableAttributeWithNonNullDefault()
	 * @see #setUnsettableAttributeWithNonNullDefault(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_UnsettableAttributeWithNonNullDefault()
	 * @model default="junit" unique="false" unsettable="true"
	 * @generated
	 */
	String getUnsettableAttributeWithNonNullDefault();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttributeWithNonNullDefault <em>Unsettable Attribute With Non Null Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unsettable Attribute With Non Null Default</em>' attribute.
	 * @see #isSetUnsettableAttributeWithNonNullDefault()
	 * @see #unsetUnsettableAttributeWithNonNullDefault()
	 * @see #getUnsettableAttributeWithNonNullDefault()
	 * @generated
	 */
	void setUnsettableAttributeWithNonNullDefault(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttributeWithNonNullDefault <em>Unsettable Attribute With Non Null Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUnsettableAttributeWithNonNullDefault()
	 * @see #getUnsettableAttributeWithNonNullDefault()
	 * @see #setUnsettableAttributeWithNonNullDefault(String)
	 * @generated
	 */
	void unsetUnsettableAttributeWithNonNullDefault();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableAttributeWithNonNullDefault <em>Unsettable Attribute With Non Null Default</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unsettable Attribute With Non Null Default</em>' attribute is set.
	 * @see #unsetUnsettableAttributeWithNonNullDefault()
	 * @see #getUnsettableAttributeWithNonNullDefault()
	 * @see #setUnsettableAttributeWithNonNullDefault(String)
	 * @generated
	 */
	boolean isSetUnsettableAttributeWithNonNullDefault();

	/**
	 * Returns the value of the '<em><b>Unsettable Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unsettable Reference</em>' reference.
	 * @see #isSetUnsettableReference()
	 * @see #unsetUnsettableReference()
	 * @see #setUnsettableReference(TargetObject)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_UnsettableReference()
	 * @model unsettable="true"
	 * @generated
	 */
	TargetObject getUnsettableReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableReference <em>Unsettable Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unsettable Reference</em>' reference.
	 * @see #isSetUnsettableReference()
	 * @see #unsetUnsettableReference()
	 * @see #getUnsettableReference()
	 * @generated
	 */
	void setUnsettableReference(TargetObject value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableReference <em>Unsettable Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUnsettableReference()
	 * @see #getUnsettableReference()
	 * @see #setUnsettableReference(TargetObject)
	 * @generated
	 */
	void unsetUnsettableReference();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getUnsettableReference <em>Unsettable Reference</em>}' reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unsettable Reference</em>' reference is set.
	 * @see #unsetUnsettableReference()
	 * @see #getUnsettableReference()
	 * @see #setUnsettableReference(TargetObject)
	 * @generated
	 */
	boolean isSetUnsettableReference();

	/**
	 * Returns the value of the '<em><b>Single Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Reference</em>' reference.
	 * @see #setSingleReference(TargetObject)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_SingleReference()
	 * @model
	 * @generated
	 */
	TargetObject getSingleReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleReference <em>Single Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Reference</em>' reference.
	 * @see #getSingleReference()
	 * @generated
	 */
	void setSingleReference(TargetObject value);

	/**
	 * Returns the value of the '<em><b>Many References</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.TargetObject}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReference <em>Single Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many References</em>' reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_ManyReferences()
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReference
	 * @model opposite="singleReference"
	 * @generated
	 */
	EList<TargetObject> getManyReferences();

	/**
	 * Returns the value of the '<em><b>Single Reference Not Resolve Proxies</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Reference Not Resolve Proxies</em>' reference.
	 * @see #setSingleReferenceNotResolveProxies(TargetObject)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_SingleReferenceNotResolveProxies()
	 * @model resolveProxies="false"
	 * @generated
	 */
	TargetObject getSingleReferenceNotResolveProxies();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Reference Not Resolve Proxies</em>' reference.
	 * @see #getSingleReferenceNotResolveProxies()
	 * @generated
	 */
	void setSingleReferenceNotResolveProxies(TargetObject value);

	/**
	 * Returns the value of the '<em><b>Many Reference Not Resolve Proxies</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.TargetObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many Reference Not Resolve Proxies</em>' reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_ManyReferenceNotResolveProxies()
	 * @model resolveProxies="false"
	 * @generated
	 */
	EList<TargetObject> getManyReferenceNotResolveProxies();

	/**
	 * Returns the value of the '<em><b>Containment Reference Same Collection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containment Reference Same Collection</em>' containment reference.
	 * @see #setContainmentReferenceSameCollection(PrimaryObject)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_ContainmentReferenceSameCollection()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	PrimaryObject getContainmentReferenceSameCollection();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getContainmentReferenceSameCollection <em>Containment Reference Same Collection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containment Reference Same Collection</em>' containment reference.
	 * @see #getContainmentReferenceSameCollection()
	 * @generated
	 */
	void setContainmentReferenceSameCollection(PrimaryObject value);

	/**
	 * Returns the value of the '<em><b>Single Containment Reference No Proxies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Containment Reference No Proxies</em>' containment reference.
	 * @see #setSingleContainmentReferenceNoProxies(TargetObject)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_SingleContainmentReferenceNoProxies()
	 * @model containment="true"
	 * @generated
	 */
	TargetObject getSingleContainmentReferenceNoProxies();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceNoProxies <em>Single Containment Reference No Proxies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Containment Reference No Proxies</em>' containment reference.
	 * @see #getSingleContainmentReferenceNoProxies()
	 * @generated
	 */
	void setSingleContainmentReferenceNoProxies(TargetObject value);

	/**
	 * Returns the value of the '<em><b>Multiple Containment Reference No Proxies</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.TargetObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple Containment Reference No Proxies</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_MultipleContainmentReferenceNoProxies()
	 * @model containment="true"
	 * @generated
	 */
	EList<TargetObject> getMultipleContainmentReferenceNoProxies();

	/**
	 * Returns the value of the '<em><b>Single Containment Reference Proxies</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Containment Reference Proxies</em>' containment reference.
	 * @see #setSingleContainmentReferenceProxies(TargetObject)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_SingleContainmentReferenceProxies()
	 * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject#getSingleReferenceNotResolveProxies
	 * @model opposite="singleReferenceNotResolveProxies" containment="true" resolveProxies="true"
	 * @generated
	 */
	TargetObject getSingleContainmentReferenceProxies();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject#getSingleContainmentReferenceProxies <em>Single Containment Reference Proxies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Containment Reference Proxies</em>' containment reference.
	 * @see #getSingleContainmentReferenceProxies()
	 * @generated
	 */
	void setSingleContainmentReferenceProxies(TargetObject value);

	/**
	 * Returns the value of the '<em><b>Multiple Containment Reference Proxies</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.TargetObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple Containment Reference Proxies</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_MultipleContainmentReferenceProxies()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<TargetObject> getMultipleContainmentReferenceProxies();

	/**
	 * Returns the value of the '<em><b>Feature Map Reference Collection</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Map Reference Collection</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_FeatureMapReferenceCollection()
	 * @model dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="false"
	 *        extendedMetaData="kind='group'"
	 * @generated
	 */
	FeatureMap getFeatureMapReferenceCollection();

	/**
	 * Returns the value of the '<em><b>Feature Map Reference Type1</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.TargetObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Map Reference Type1</em>' reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_FeatureMapReferenceType1()
	 * @model resolveProxies="false" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="group='#featureMapReferenceCollection'"
	 * @generated
	 */
	EList<TargetObject> getFeatureMapReferenceType1();

	/**
	 * Returns the value of the '<em><b>Feature Map Reference Type2</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.TargetObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Map Reference Type2</em>' containment reference list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_FeatureMapReferenceType2()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="group='#featureMapReferenceCollection'"
	 * @generated
	 */
	EList<TargetObject> getFeatureMapReferenceType2();

	/**
	 * Returns the value of the '<em><b>Feature Map Attribute Collection</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Map Attribute Collection</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_FeatureMapAttributeCollection()
	 * @model dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group'"
	 * @generated
	 */
	FeatureMap getFeatureMapAttributeCollection();

	/**
	 * Returns the value of the '<em><b>Feature Map Attribute Type1</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Map Attribute Type1</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_FeatureMapAttributeType1()
	 * @model transient="true" volatile="true" derived="true"
	 *        extendedMetaData="group='#featureMapAttributeCollection'"
	 * @generated
	 */
	EList<String> getFeatureMapAttributeType1();

	/**
	 * Returns the value of the '<em><b>Feature Map Attribute Type2</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Map Attribute Type2</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getPrimaryObject_FeatureMapAttributeType2()
	 * @model transient="true" volatile="true" derived="true"
	 *        extendedMetaData="group='#featureMapAttributeCollection'"
	 * @generated
	 */
	EList<String> getFeatureMapAttributeType2();

} // PrimaryObject
