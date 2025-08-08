/*
 */
package org.eclipse.fennec.openapi.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fennec.openapi.model.Delete;
import org.eclipse.fennec.openapi.model.Get;
import org.eclipse.fennec.openapi.model.OpenApiPackage;
import org.eclipse.fennec.openapi.model.Path;
import org.eclipse.fennec.openapi.model.Post;
import org.eclipse.fennec.openapi.model.Put;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Path</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.PathImpl#getPut <em>Put</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.PathImpl#getGet <em>Get</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.PathImpl#getPost <em>Post</em>}</li>
 *   <li>{@link org.eclipse.fennec.openapi.model.impl.PathImpl#getDelete <em>Delete</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PathImpl extends MinimalEObjectImpl.Container implements Path {
	/**
	 * The cached value of the '{@link #getPut() <em>Put</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPut()
	 * @generated
	 * @ordered
	 */
	protected Put put;

	/**
	 * The cached value of the '{@link #getGet() <em>Get</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGet()
	 * @generated
	 * @ordered
	 */
	protected Get get;

	/**
	 * The cached value of the '{@link #getPost() <em>Post</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPost()
	 * @generated
	 * @ordered
	 */
	protected Post post;

	/**
	 * The cached value of the '{@link #getDelete() <em>Delete</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelete()
	 * @generated
	 * @ordered
	 */
	protected Delete delete;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PathImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpenApiPackage.Literals.PATH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Put getPut() {
		return put;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPut(Put newPut, NotificationChain msgs) {
		Put oldPut = put;
		put = newPut;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.PATH__PUT, oldPut, newPut);
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
	public void setPut(Put newPut) {
		if (newPut != put) {
			NotificationChain msgs = null;
			if (put != null)
				msgs = ((InternalEObject)put).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.PATH__PUT, null, msgs);
			if (newPut != null)
				msgs = ((InternalEObject)newPut).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.PATH__PUT, null, msgs);
			msgs = basicSetPut(newPut, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.PATH__PUT, newPut, newPut));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Get getGet() {
		return get;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGet(Get newGet, NotificationChain msgs) {
		Get oldGet = get;
		get = newGet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.PATH__GET, oldGet, newGet);
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
	public void setGet(Get newGet) {
		if (newGet != get) {
			NotificationChain msgs = null;
			if (get != null)
				msgs = ((InternalEObject)get).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.PATH__GET, null, msgs);
			if (newGet != null)
				msgs = ((InternalEObject)newGet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.PATH__GET, null, msgs);
			msgs = basicSetGet(newGet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.PATH__GET, newGet, newGet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Post getPost() {
		return post;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPost(Post newPost, NotificationChain msgs) {
		Post oldPost = post;
		post = newPost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.PATH__POST, oldPost, newPost);
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
	public void setPost(Post newPost) {
		if (newPost != post) {
			NotificationChain msgs = null;
			if (post != null)
				msgs = ((InternalEObject)post).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.PATH__POST, null, msgs);
			if (newPost != null)
				msgs = ((InternalEObject)newPost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.PATH__POST, null, msgs);
			msgs = basicSetPost(newPost, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.PATH__POST, newPost, newPost));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Delete getDelete() {
		return delete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDelete(Delete newDelete, NotificationChain msgs) {
		Delete oldDelete = delete;
		delete = newDelete;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OpenApiPackage.PATH__DELETE, oldDelete, newDelete);
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
	public void setDelete(Delete newDelete) {
		if (newDelete != delete) {
			NotificationChain msgs = null;
			if (delete != null)
				msgs = ((InternalEObject)delete).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.PATH__DELETE, null, msgs);
			if (newDelete != null)
				msgs = ((InternalEObject)newDelete).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OpenApiPackage.PATH__DELETE, null, msgs);
			msgs = basicSetDelete(newDelete, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpenApiPackage.PATH__DELETE, newDelete, newDelete));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OpenApiPackage.PATH__PUT:
				return basicSetPut(null, msgs);
			case OpenApiPackage.PATH__GET:
				return basicSetGet(null, msgs);
			case OpenApiPackage.PATH__POST:
				return basicSetPost(null, msgs);
			case OpenApiPackage.PATH__DELETE:
				return basicSetDelete(null, msgs);
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
			case OpenApiPackage.PATH__PUT:
				return getPut();
			case OpenApiPackage.PATH__GET:
				return getGet();
			case OpenApiPackage.PATH__POST:
				return getPost();
			case OpenApiPackage.PATH__DELETE:
				return getDelete();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OpenApiPackage.PATH__PUT:
				setPut((Put)newValue);
				return;
			case OpenApiPackage.PATH__GET:
				setGet((Get)newValue);
				return;
			case OpenApiPackage.PATH__POST:
				setPost((Post)newValue);
				return;
			case OpenApiPackage.PATH__DELETE:
				setDelete((Delete)newValue);
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
			case OpenApiPackage.PATH__PUT:
				setPut((Put)null);
				return;
			case OpenApiPackage.PATH__GET:
				setGet((Get)null);
				return;
			case OpenApiPackage.PATH__POST:
				setPost((Post)null);
				return;
			case OpenApiPackage.PATH__DELETE:
				setDelete((Delete)null);
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
			case OpenApiPackage.PATH__PUT:
				return put != null;
			case OpenApiPackage.PATH__GET:
				return get != null;
			case OpenApiPackage.PATH__POST:
				return post != null;
			case OpenApiPackage.PATH__DELETE:
				return delete != null;
		}
		return super.eIsSet(featureID);
	}

} //PathImpl
