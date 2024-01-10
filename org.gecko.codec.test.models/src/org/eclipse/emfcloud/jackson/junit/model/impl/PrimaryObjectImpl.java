/*
 */
package org.eclipse.emfcloud.jackson.junit.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.jackson.junit.model.ModelPackage;
import org.eclipse.emfcloud.jackson.junit.model.PrimaryObject;
import org.eclipse.emfcloud.jackson.junit.model.SomeKind;
import org.eclipse.emfcloud.jackson.junit.model.TargetObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primary Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getIdAttribute <em>Id Attribute</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getUnsettableAttribute <em>Unsettable Attribute</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getUnsettableAttributeWithNonNullDefault <em>Unsettable Attribute With Non Null Default</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getUnsettableReference <em>Unsettable Reference</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getSingleReference <em>Single Reference</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getManyReferences <em>Many References</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getSingleReferenceNotResolveProxies <em>Single Reference Not Resolve Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getManyReferenceNotResolveProxies <em>Many Reference Not Resolve Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getContainmentReferenceSameCollection <em>Containment Reference Same Collection</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getSingleContainmentReferenceNoProxies <em>Single Containment Reference No Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getMultipleContainmentReferenceNoProxies <em>Multiple Containment Reference No Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getSingleContainmentReferenceProxies <em>Single Containment Reference Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getMultipleContainmentReferenceProxies <em>Multiple Containment Reference Proxies</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getFeatureMapReferenceCollection <em>Feature Map Reference Collection</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getFeatureMapReferenceType1 <em>Feature Map Reference Type1</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getFeatureMapReferenceType2 <em>Feature Map Reference Type2</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getFeatureMapAttributeCollection <em>Feature Map Attribute Collection</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getFeatureMapAttributeType1 <em>Feature Map Attribute Type1</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.PrimaryObjectImpl#getFeatureMapAttributeType2 <em>Feature Map Attribute Type2</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PrimaryObjectImpl extends MinimalEObjectImpl implements PrimaryObject {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getIdAttribute() <em>Id Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_ATTRIBUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdAttribute() <em>Id Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdAttribute()
	 * @generated
	 * @ordered
	 */
	protected String idAttribute = ID_ATTRIBUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final SomeKind KIND_EDEFAULT = SomeKind.ONE;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected SomeKind kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getUnsettableAttribute() <em>Unsettable Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsettableAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final String UNSETTABLE_ATTRIBUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUnsettableAttribute() <em>Unsettable Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsettableAttribute()
	 * @generated
	 * @ordered
	 */
	protected String unsettableAttribute = UNSETTABLE_ATTRIBUTE_EDEFAULT;

	/**
	 * This is true if the Unsettable Attribute attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean unsettableAttributeESet;

	/**
	 * The default value of the '{@link #getUnsettableAttributeWithNonNullDefault() <em>Unsettable Attribute With Non Null Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsettableAttributeWithNonNullDefault()
	 * @generated
	 * @ordered
	 */
	protected static final String UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT_EDEFAULT = "junit";

	/**
	 * The cached value of the '{@link #getUnsettableAttributeWithNonNullDefault() <em>Unsettable Attribute With Non Null Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsettableAttributeWithNonNullDefault()
	 * @generated
	 * @ordered
	 */
	protected String unsettableAttributeWithNonNullDefault = UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT_EDEFAULT;

	/**
	 * This is true if the Unsettable Attribute With Non Null Default attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean unsettableAttributeWithNonNullDefaultESet;

	/**
	 * The cached value of the '{@link #getUnsettableReference() <em>Unsettable Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsettableReference()
	 * @generated
	 * @ordered
	 */
	protected TargetObject unsettableReference;

	/**
	 * This is true if the Unsettable Reference reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean unsettableReferenceESet;

	/**
	 * The cached value of the '{@link #getSingleReference() <em>Single Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleReference()
	 * @generated
	 * @ordered
	 */
	protected TargetObject singleReference;

	/**
	 * The cached value of the '{@link #getManyReferences() <em>Many References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<TargetObject> manyReferences;

	/**
	 * The cached value of the '{@link #getSingleReferenceNotResolveProxies() <em>Single Reference Not Resolve Proxies</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleReferenceNotResolveProxies()
	 * @generated
	 * @ordered
	 */
	protected TargetObject singleReferenceNotResolveProxies;

	/**
	 * The cached value of the '{@link #getManyReferenceNotResolveProxies() <em>Many Reference Not Resolve Proxies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyReferenceNotResolveProxies()
	 * @generated
	 * @ordered
	 */
	protected EList<TargetObject> manyReferenceNotResolveProxies;

	/**
	 * The cached value of the '{@link #getContainmentReferenceSameCollection() <em>Containment Reference Same Collection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainmentReferenceSameCollection()
	 * @generated
	 * @ordered
	 */
	protected PrimaryObject containmentReferenceSameCollection;

	/**
	 * The cached value of the '{@link #getSingleContainmentReferenceNoProxies() <em>Single Containment Reference No Proxies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleContainmentReferenceNoProxies()
	 * @generated
	 * @ordered
	 */
	protected TargetObject singleContainmentReferenceNoProxies;

	/**
	 * The cached value of the '{@link #getMultipleContainmentReferenceNoProxies() <em>Multiple Containment Reference No Proxies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultipleContainmentReferenceNoProxies()
	 * @generated
	 * @ordered
	 */
	protected EList<TargetObject> multipleContainmentReferenceNoProxies;

	/**
	 * The cached value of the '{@link #getSingleContainmentReferenceProxies() <em>Single Containment Reference Proxies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleContainmentReferenceProxies()
	 * @generated
	 * @ordered
	 */
	protected TargetObject singleContainmentReferenceProxies;

	/**
	 * The cached value of the '{@link #getMultipleContainmentReferenceProxies() <em>Multiple Containment Reference Proxies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultipleContainmentReferenceProxies()
	 * @generated
	 * @ordered
	 */
	protected EList<TargetObject> multipleContainmentReferenceProxies;

	/**
	 * The cached value of the '{@link #getFeatureMapReferenceCollection() <em>Feature Map Reference Collection</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureMapReferenceCollection()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap featureMapReferenceCollection;

	/**
	 * The cached value of the '{@link #getFeatureMapAttributeCollection() <em>Feature Map Attribute Collection</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureMapAttributeCollection()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap featureMapAttributeCollection;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrimaryObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PRIMARY_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIdAttribute() {
		return idAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdAttribute(String newIdAttribute) {
		String oldIdAttribute = idAttribute;
		idAttribute = newIdAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__ID_ATTRIBUTE, oldIdAttribute, idAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SomeKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKind(SomeKind newKind) {
		SomeKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUnsettableAttribute() {
		return unsettableAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUnsettableAttribute(String newUnsettableAttribute) {
		String oldUnsettableAttribute = unsettableAttribute;
		unsettableAttribute = newUnsettableAttribute;
		boolean oldUnsettableAttributeESet = unsettableAttributeESet;
		unsettableAttributeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE, oldUnsettableAttribute, unsettableAttribute, !oldUnsettableAttributeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUnsettableAttribute() {
		String oldUnsettableAttribute = unsettableAttribute;
		boolean oldUnsettableAttributeESet = unsettableAttributeESet;
		unsettableAttribute = UNSETTABLE_ATTRIBUTE_EDEFAULT;
		unsettableAttributeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE, oldUnsettableAttribute, UNSETTABLE_ATTRIBUTE_EDEFAULT, oldUnsettableAttributeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetUnsettableAttribute() {
		return unsettableAttributeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUnsettableAttributeWithNonNullDefault() {
		return unsettableAttributeWithNonNullDefault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUnsettableAttributeWithNonNullDefault(String newUnsettableAttributeWithNonNullDefault) {
		String oldUnsettableAttributeWithNonNullDefault = unsettableAttributeWithNonNullDefault;
		unsettableAttributeWithNonNullDefault = newUnsettableAttributeWithNonNullDefault;
		boolean oldUnsettableAttributeWithNonNullDefaultESet = unsettableAttributeWithNonNullDefaultESet;
		unsettableAttributeWithNonNullDefaultESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT, oldUnsettableAttributeWithNonNullDefault, unsettableAttributeWithNonNullDefault, !oldUnsettableAttributeWithNonNullDefaultESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUnsettableAttributeWithNonNullDefault() {
		String oldUnsettableAttributeWithNonNullDefault = unsettableAttributeWithNonNullDefault;
		boolean oldUnsettableAttributeWithNonNullDefaultESet = unsettableAttributeWithNonNullDefaultESet;
		unsettableAttributeWithNonNullDefault = UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT_EDEFAULT;
		unsettableAttributeWithNonNullDefaultESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT, oldUnsettableAttributeWithNonNullDefault, UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT_EDEFAULT, oldUnsettableAttributeWithNonNullDefaultESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetUnsettableAttributeWithNonNullDefault() {
		return unsettableAttributeWithNonNullDefaultESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TargetObject getUnsettableReference() {
		if (unsettableReference != null && unsettableReference.eIsProxy()) {
			InternalEObject oldUnsettableReference = (InternalEObject)unsettableReference;
			unsettableReference = (TargetObject)eResolveProxy(oldUnsettableReference);
			if (unsettableReference != oldUnsettableReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.PRIMARY_OBJECT__UNSETTABLE_REFERENCE, oldUnsettableReference, unsettableReference));
			}
		}
		return unsettableReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TargetObject basicGetUnsettableReference() {
		return unsettableReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUnsettableReference(TargetObject newUnsettableReference) {
		TargetObject oldUnsettableReference = unsettableReference;
		unsettableReference = newUnsettableReference;
		boolean oldUnsettableReferenceESet = unsettableReferenceESet;
		unsettableReferenceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__UNSETTABLE_REFERENCE, oldUnsettableReference, unsettableReference, !oldUnsettableReferenceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUnsettableReference() {
		TargetObject oldUnsettableReference = unsettableReference;
		boolean oldUnsettableReferenceESet = unsettableReferenceESet;
		unsettableReference = null;
		unsettableReferenceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PRIMARY_OBJECT__UNSETTABLE_REFERENCE, oldUnsettableReference, null, oldUnsettableReferenceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetUnsettableReference() {
		return unsettableReferenceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TargetObject getSingleReference() {
		if (singleReference != null && singleReference.eIsProxy()) {
			InternalEObject oldSingleReference = (InternalEObject)singleReference;
			singleReference = (TargetObject)eResolveProxy(oldSingleReference);
			if (singleReference != oldSingleReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE, oldSingleReference, singleReference));
			}
		}
		return singleReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TargetObject basicGetSingleReference() {
		return singleReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSingleReference(TargetObject newSingleReference) {
		TargetObject oldSingleReference = singleReference;
		singleReference = newSingleReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE, oldSingleReference, singleReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TargetObject> getManyReferences() {
		if (manyReferences == null) {
			manyReferences = new EObjectWithInverseResolvingEList<TargetObject>(TargetObject.class, this, ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE);
		}
		return manyReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TargetObject getSingleReferenceNotResolveProxies() {
		return singleReferenceNotResolveProxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSingleReferenceNotResolveProxies(TargetObject newSingleReferenceNotResolveProxies) {
		TargetObject oldSingleReferenceNotResolveProxies = singleReferenceNotResolveProxies;
		singleReferenceNotResolveProxies = newSingleReferenceNotResolveProxies;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES, oldSingleReferenceNotResolveProxies, singleReferenceNotResolveProxies));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TargetObject> getManyReferenceNotResolveProxies() {
		if (manyReferenceNotResolveProxies == null) {
			manyReferenceNotResolveProxies = new EObjectEList<TargetObject>(TargetObject.class, this, ModelPackage.PRIMARY_OBJECT__MANY_REFERENCE_NOT_RESOLVE_PROXIES);
		}
		return manyReferenceNotResolveProxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PrimaryObject getContainmentReferenceSameCollection() {
		if (containmentReferenceSameCollection != null && containmentReferenceSameCollection.eIsProxy()) {
			InternalEObject oldContainmentReferenceSameCollection = (InternalEObject)containmentReferenceSameCollection;
			containmentReferenceSameCollection = (PrimaryObject)eResolveProxy(oldContainmentReferenceSameCollection);
			if (containmentReferenceSameCollection != oldContainmentReferenceSameCollection) {
				InternalEObject newContainmentReferenceSameCollection = (InternalEObject)containmentReferenceSameCollection;
				NotificationChain msgs = oldContainmentReferenceSameCollection.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION, null, null);
				if (newContainmentReferenceSameCollection.eInternalContainer() == null) {
					msgs = newContainmentReferenceSameCollection.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION, oldContainmentReferenceSameCollection, containmentReferenceSameCollection));
			}
		}
		return containmentReferenceSameCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrimaryObject basicGetContainmentReferenceSameCollection() {
		return containmentReferenceSameCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainmentReferenceSameCollection(PrimaryObject newContainmentReferenceSameCollection, NotificationChain msgs) {
		PrimaryObject oldContainmentReferenceSameCollection = containmentReferenceSameCollection;
		containmentReferenceSameCollection = newContainmentReferenceSameCollection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION, oldContainmentReferenceSameCollection, newContainmentReferenceSameCollection);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContainmentReferenceSameCollection(PrimaryObject newContainmentReferenceSameCollection) {
		if (newContainmentReferenceSameCollection != containmentReferenceSameCollection) {
			NotificationChain msgs = null;
			if (containmentReferenceSameCollection != null)
				msgs = ((InternalEObject)containmentReferenceSameCollection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION, null, msgs);
			if (newContainmentReferenceSameCollection != null)
				msgs = ((InternalEObject)newContainmentReferenceSameCollection).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION, null, msgs);
			msgs = basicSetContainmentReferenceSameCollection(newContainmentReferenceSameCollection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION, newContainmentReferenceSameCollection, newContainmentReferenceSameCollection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TargetObject getSingleContainmentReferenceNoProxies() {
		return singleContainmentReferenceNoProxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleContainmentReferenceNoProxies(TargetObject newSingleContainmentReferenceNoProxies, NotificationChain msgs) {
		TargetObject oldSingleContainmentReferenceNoProxies = singleContainmentReferenceNoProxies;
		singleContainmentReferenceNoProxies = newSingleContainmentReferenceNoProxies;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES, oldSingleContainmentReferenceNoProxies, newSingleContainmentReferenceNoProxies);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSingleContainmentReferenceNoProxies(TargetObject newSingleContainmentReferenceNoProxies) {
		if (newSingleContainmentReferenceNoProxies != singleContainmentReferenceNoProxies) {
			NotificationChain msgs = null;
			if (singleContainmentReferenceNoProxies != null)
				msgs = ((InternalEObject)singleContainmentReferenceNoProxies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES, null, msgs);
			if (newSingleContainmentReferenceNoProxies != null)
				msgs = ((InternalEObject)newSingleContainmentReferenceNoProxies).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES, null, msgs);
			msgs = basicSetSingleContainmentReferenceNoProxies(newSingleContainmentReferenceNoProxies, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES, newSingleContainmentReferenceNoProxies, newSingleContainmentReferenceNoProxies));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TargetObject> getMultipleContainmentReferenceNoProxies() {
		if (multipleContainmentReferenceNoProxies == null) {
			multipleContainmentReferenceNoProxies = new EObjectContainmentEList<TargetObject>(TargetObject.class, this, ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_NO_PROXIES);
		}
		return multipleContainmentReferenceNoProxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TargetObject getSingleContainmentReferenceProxies() {
		if (singleContainmentReferenceProxies != null && singleContainmentReferenceProxies.eIsProxy()) {
			InternalEObject oldSingleContainmentReferenceProxies = (InternalEObject)singleContainmentReferenceProxies;
			singleContainmentReferenceProxies = (TargetObject)eResolveProxy(oldSingleContainmentReferenceProxies);
			if (singleContainmentReferenceProxies != oldSingleContainmentReferenceProxies) {
				InternalEObject newSingleContainmentReferenceProxies = (InternalEObject)singleContainmentReferenceProxies;
				NotificationChain msgs =  oldSingleContainmentReferenceProxies.eInverseRemove(this, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES, TargetObject.class, null);
				if (newSingleContainmentReferenceProxies.eInternalContainer() == null) {
					msgs =  newSingleContainmentReferenceProxies.eInverseAdd(this, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES, TargetObject.class, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES, oldSingleContainmentReferenceProxies, singleContainmentReferenceProxies));
			}
		}
		return singleContainmentReferenceProxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TargetObject basicGetSingleContainmentReferenceProxies() {
		return singleContainmentReferenceProxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleContainmentReferenceProxies(TargetObject newSingleContainmentReferenceProxies, NotificationChain msgs) {
		TargetObject oldSingleContainmentReferenceProxies = singleContainmentReferenceProxies;
		singleContainmentReferenceProxies = newSingleContainmentReferenceProxies;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES, oldSingleContainmentReferenceProxies, newSingleContainmentReferenceProxies);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSingleContainmentReferenceProxies(TargetObject newSingleContainmentReferenceProxies) {
		if (newSingleContainmentReferenceProxies != singleContainmentReferenceProxies) {
			NotificationChain msgs = null;
			if (singleContainmentReferenceProxies != null)
				msgs = ((InternalEObject)singleContainmentReferenceProxies).eInverseRemove(this, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES, TargetObject.class, msgs);
			if (newSingleContainmentReferenceProxies != null)
				msgs = ((InternalEObject)newSingleContainmentReferenceProxies).eInverseAdd(this, ModelPackage.TARGET_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES, TargetObject.class, msgs);
			msgs = basicSetSingleContainmentReferenceProxies(newSingleContainmentReferenceProxies, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES, newSingleContainmentReferenceProxies, newSingleContainmentReferenceProxies));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TargetObject> getMultipleContainmentReferenceProxies() {
		if (multipleContainmentReferenceProxies == null) {
			multipleContainmentReferenceProxies = new EObjectContainmentEList.Resolving<TargetObject>(TargetObject.class, this, ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_PROXIES);
		}
		return multipleContainmentReferenceProxies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getFeatureMapReferenceCollection() {
		if (featureMapReferenceCollection == null) {
			featureMapReferenceCollection = new BasicFeatureMap(this, ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_COLLECTION);
		}
		return featureMapReferenceCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TargetObject> getFeatureMapReferenceType1() {
		return getFeatureMapReferenceCollection().list(ModelPackage.Literals.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TargetObject> getFeatureMapReferenceType2() {
		return getFeatureMapReferenceCollection().list(ModelPackage.Literals.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getFeatureMapAttributeCollection() {
		if (featureMapAttributeCollection == null) {
			featureMapAttributeCollection = new BasicFeatureMap(this, ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_COLLECTION);
		}
		return featureMapAttributeCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getFeatureMapAttributeType1() {
		return getFeatureMapAttributeCollection().list(ModelPackage.Literals.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getFeatureMapAttributeType2() {
		return getFeatureMapAttributeCollection().list(ModelPackage.Literals.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getManyReferences()).basicAdd(otherEnd, msgs);
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES:
				if (singleContainmentReferenceProxies != null)
					msgs = ((InternalEObject)singleContainmentReferenceProxies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES, null, msgs);
				return basicSetSingleContainmentReferenceProxies((TargetObject)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES:
				return ((InternalEList<?>)getManyReferences()).basicRemove(otherEnd, msgs);
			case ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION:
				return basicSetContainmentReferenceSameCollection(null, msgs);
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				return basicSetSingleContainmentReferenceNoProxies(null, msgs);
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				return ((InternalEList<?>)getMultipleContainmentReferenceNoProxies()).basicRemove(otherEnd, msgs);
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES:
				return basicSetSingleContainmentReferenceProxies(null, msgs);
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_PROXIES:
				return ((InternalEList<?>)getMultipleContainmentReferenceProxies()).basicRemove(otherEnd, msgs);
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_COLLECTION:
				return ((InternalEList<?>)getFeatureMapReferenceCollection()).basicRemove(otherEnd, msgs);
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE2:
				return ((InternalEList<?>)getFeatureMapReferenceType2()).basicRemove(otherEnd, msgs);
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_COLLECTION:
				return ((InternalEList<?>)getFeatureMapAttributeCollection()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.PRIMARY_OBJECT__NAME:
				return getName();
			case ModelPackage.PRIMARY_OBJECT__ID_ATTRIBUTE:
				return getIdAttribute();
			case ModelPackage.PRIMARY_OBJECT__KIND:
				return getKind();
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE:
				return getUnsettableAttribute();
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT:
				return getUnsettableAttributeWithNonNullDefault();
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_REFERENCE:
				if (resolve) return getUnsettableReference();
				return basicGetUnsettableReference();
			case ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE:
				if (resolve) return getSingleReference();
				return basicGetSingleReference();
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES:
				return getManyReferences();
			case ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				return getSingleReferenceNotResolveProxies();
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCE_NOT_RESOLVE_PROXIES:
				return getManyReferenceNotResolveProxies();
			case ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION:
				if (resolve) return getContainmentReferenceSameCollection();
				return basicGetContainmentReferenceSameCollection();
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				return getSingleContainmentReferenceNoProxies();
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				return getMultipleContainmentReferenceNoProxies();
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES:
				if (resolve) return getSingleContainmentReferenceProxies();
				return basicGetSingleContainmentReferenceProxies();
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_PROXIES:
				return getMultipleContainmentReferenceProxies();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_COLLECTION:
				if (coreType) return getFeatureMapReferenceCollection();
				return ((FeatureMap.Internal)getFeatureMapReferenceCollection()).getWrapper();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE1:
				return getFeatureMapReferenceType1();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE2:
				return getFeatureMapReferenceType2();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_COLLECTION:
				if (coreType) return getFeatureMapAttributeCollection();
				return ((FeatureMap.Internal)getFeatureMapAttributeCollection()).getWrapper();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE1:
				return getFeatureMapAttributeType1();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE2:
				return getFeatureMapAttributeType2();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.PRIMARY_OBJECT__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__ID_ATTRIBUTE:
				setIdAttribute((String)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__KIND:
				setKind((SomeKind)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE:
				setUnsettableAttribute((String)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT:
				setUnsettableAttributeWithNonNullDefault((String)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_REFERENCE:
				setUnsettableReference((TargetObject)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE:
				setSingleReference((TargetObject)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES:
				getManyReferences().clear();
				getManyReferences().addAll((Collection<? extends TargetObject>)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				setSingleReferenceNotResolveProxies((TargetObject)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCE_NOT_RESOLVE_PROXIES:
				getManyReferenceNotResolveProxies().clear();
				getManyReferenceNotResolveProxies().addAll((Collection<? extends TargetObject>)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION:
				setContainmentReferenceSameCollection((PrimaryObject)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				setSingleContainmentReferenceNoProxies((TargetObject)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				getMultipleContainmentReferenceNoProxies().clear();
				getMultipleContainmentReferenceNoProxies().addAll((Collection<? extends TargetObject>)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES:
				setSingleContainmentReferenceProxies((TargetObject)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_PROXIES:
				getMultipleContainmentReferenceProxies().clear();
				getMultipleContainmentReferenceProxies().addAll((Collection<? extends TargetObject>)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_COLLECTION:
				((FeatureMap.Internal)getFeatureMapReferenceCollection()).set(newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE1:
				getFeatureMapReferenceType1().clear();
				getFeatureMapReferenceType1().addAll((Collection<? extends TargetObject>)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE2:
				getFeatureMapReferenceType2().clear();
				getFeatureMapReferenceType2().addAll((Collection<? extends TargetObject>)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_COLLECTION:
				((FeatureMap.Internal)getFeatureMapAttributeCollection()).set(newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE1:
				getFeatureMapAttributeType1().clear();
				getFeatureMapAttributeType1().addAll((Collection<? extends String>)newValue);
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE2:
				getFeatureMapAttributeType2().clear();
				getFeatureMapAttributeType2().addAll((Collection<? extends String>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.PRIMARY_OBJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.PRIMARY_OBJECT__ID_ATTRIBUTE:
				setIdAttribute(ID_ATTRIBUTE_EDEFAULT);
				return;
			case ModelPackage.PRIMARY_OBJECT__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE:
				unsetUnsettableAttribute();
				return;
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT:
				unsetUnsettableAttributeWithNonNullDefault();
				return;
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_REFERENCE:
				unsetUnsettableReference();
				return;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE:
				setSingleReference((TargetObject)null);
				return;
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES:
				getManyReferences().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				setSingleReferenceNotResolveProxies((TargetObject)null);
				return;
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCE_NOT_RESOLVE_PROXIES:
				getManyReferenceNotResolveProxies().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION:
				setContainmentReferenceSameCollection((PrimaryObject)null);
				return;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				setSingleContainmentReferenceNoProxies((TargetObject)null);
				return;
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				getMultipleContainmentReferenceNoProxies().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES:
				setSingleContainmentReferenceProxies((TargetObject)null);
				return;
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_PROXIES:
				getMultipleContainmentReferenceProxies().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_COLLECTION:
				getFeatureMapReferenceCollection().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE1:
				getFeatureMapReferenceType1().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE2:
				getFeatureMapReferenceType2().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_COLLECTION:
				getFeatureMapAttributeCollection().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE1:
				getFeatureMapAttributeType1().clear();
				return;
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE2:
				getFeatureMapAttributeType2().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.PRIMARY_OBJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.PRIMARY_OBJECT__ID_ATTRIBUTE:
				return ID_ATTRIBUTE_EDEFAULT == null ? idAttribute != null : !ID_ATTRIBUTE_EDEFAULT.equals(idAttribute);
			case ModelPackage.PRIMARY_OBJECT__KIND:
				return kind != KIND_EDEFAULT;
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE:
				return isSetUnsettableAttribute();
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_ATTRIBUTE_WITH_NON_NULL_DEFAULT:
				return isSetUnsettableAttributeWithNonNullDefault();
			case ModelPackage.PRIMARY_OBJECT__UNSETTABLE_REFERENCE:
				return isSetUnsettableReference();
			case ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE:
				return singleReference != null;
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCES:
				return manyReferences != null && !manyReferences.isEmpty();
			case ModelPackage.PRIMARY_OBJECT__SINGLE_REFERENCE_NOT_RESOLVE_PROXIES:
				return singleReferenceNotResolveProxies != null;
			case ModelPackage.PRIMARY_OBJECT__MANY_REFERENCE_NOT_RESOLVE_PROXIES:
				return manyReferenceNotResolveProxies != null && !manyReferenceNotResolveProxies.isEmpty();
			case ModelPackage.PRIMARY_OBJECT__CONTAINMENT_REFERENCE_SAME_COLLECTION:
				return containmentReferenceSameCollection != null;
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				return singleContainmentReferenceNoProxies != null;
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_NO_PROXIES:
				return multipleContainmentReferenceNoProxies != null && !multipleContainmentReferenceNoProxies.isEmpty();
			case ModelPackage.PRIMARY_OBJECT__SINGLE_CONTAINMENT_REFERENCE_PROXIES:
				return singleContainmentReferenceProxies != null;
			case ModelPackage.PRIMARY_OBJECT__MULTIPLE_CONTAINMENT_REFERENCE_PROXIES:
				return multipleContainmentReferenceProxies != null && !multipleContainmentReferenceProxies.isEmpty();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_COLLECTION:
				return featureMapReferenceCollection != null && !featureMapReferenceCollection.isEmpty();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE1:
				return !getFeatureMapReferenceType1().isEmpty();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_REFERENCE_TYPE2:
				return !getFeatureMapReferenceType2().isEmpty();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_COLLECTION:
				return featureMapAttributeCollection != null && !featureMapAttributeCollection.isEmpty();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE1:
				return !getFeatureMapAttributeType1().isEmpty();
			case ModelPackage.PRIMARY_OBJECT__FEATURE_MAP_ATTRIBUTE_TYPE2:
				return !getFeatureMapAttributeType2().isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", idAttribute: ");
		result.append(idAttribute);
		result.append(", kind: ");
		result.append(kind);
		result.append(", unsettableAttribute: ");
		if (unsettableAttributeESet) result.append(unsettableAttribute); else result.append("<unset>");
		result.append(", unsettableAttributeWithNonNullDefault: ");
		if (unsettableAttributeWithNonNullDefaultESet) result.append(unsettableAttributeWithNonNullDefault); else result.append("<unset>");
		result.append(", featureMapReferenceCollection: ");
		result.append(featureMapReferenceCollection);
		result.append(", featureMapAttributeCollection: ");
		result.append(featureMapAttributeCollection);
		result.append(')');
		return result.toString();
	}

} //PrimaryObjectImpl
