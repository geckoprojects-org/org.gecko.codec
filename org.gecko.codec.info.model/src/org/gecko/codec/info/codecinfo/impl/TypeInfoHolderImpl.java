/*
 */
package org.gecko.codec.info.codecinfo.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.gecko.codec.info.codecinfo.CodecInfoPackage;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.TypeInfoHolder;
import org.gecko.codec.info.codecinfo.ValueReader;
import org.gecko.codec.info.codecinfo.ValueWriter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Info Holder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoHolderImpl#getInfoType <em>Info Type</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoHolderImpl#getReaders <em>Readers</em>}</li>
 *   <li>{@link org.gecko.codec.info.codecinfo.impl.TypeInfoHolderImpl#getWriters <em>Writers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeInfoHolderImpl extends MinimalEObjectImpl.Container implements TypeInfoHolder {
	/**
	 * The default value of the '{@link #getInfoType() <em>Info Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoType()
	 * @generated
	 * @ordered
	 */
	protected static final InfoType INFO_TYPE_EDEFAULT = InfoType.IDENTITY;

	/**
	 * The cached value of the '{@link #getInfoType() <em>Info Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoType()
	 * @generated
	 * @ordered
	 */
	protected InfoType infoType = INFO_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReaders() <em>Readers</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReaders()
	 * @generated
	 * @ordered
	 */
	protected EList<ValueReader<?, ?>> readers;

	/**
	 * The cached value of the '{@link #getWriters() <em>Writers</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWriters()
	 * @generated
	 * @ordered
	 */
	protected EList<ValueWriter<?, ?>> writers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeInfoHolderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodecInfoPackage.Literals.TYPE_INFO_HOLDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InfoType getInfoType() {
		return infoType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInfoType(InfoType newInfoType) {
		InfoType oldInfoType = infoType;
		infoType = newInfoType == null ? INFO_TYPE_EDEFAULT : newInfoType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodecInfoPackage.TYPE_INFO_HOLDER__INFO_TYPE, oldInfoType, infoType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ValueReader<?, ?>> getReaders() {
		if (readers == null) {
			readers = new EObjectResolvingEList<ValueReader<?, ?>>(ValueReader.class, this, CodecInfoPackage.TYPE_INFO_HOLDER__READERS);
		}
		return readers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ValueWriter<?, ?>> getWriters() {
		if (writers == null) {
			writers = new EObjectResolvingEList<ValueWriter<?, ?>>(ValueWriter.class, this, CodecInfoPackage.TYPE_INFO_HOLDER__WRITERS);
		}
		return writers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueReader<?, ?> getReaderByName(String readerName) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueWriter<?, ?> getWriterByName(String readerName) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodecInfoPackage.TYPE_INFO_HOLDER__INFO_TYPE:
				return getInfoType();
			case CodecInfoPackage.TYPE_INFO_HOLDER__READERS:
				return getReaders();
			case CodecInfoPackage.TYPE_INFO_HOLDER__WRITERS:
				return getWriters();
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
			case CodecInfoPackage.TYPE_INFO_HOLDER__INFO_TYPE:
				setInfoType((InfoType)newValue);
				return;
			case CodecInfoPackage.TYPE_INFO_HOLDER__READERS:
				getReaders().clear();
				getReaders().addAll((Collection<? extends ValueReader<?, ?>>)newValue);
				return;
			case CodecInfoPackage.TYPE_INFO_HOLDER__WRITERS:
				getWriters().clear();
				getWriters().addAll((Collection<? extends ValueWriter<?, ?>>)newValue);
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
			case CodecInfoPackage.TYPE_INFO_HOLDER__INFO_TYPE:
				setInfoType(INFO_TYPE_EDEFAULT);
				return;
			case CodecInfoPackage.TYPE_INFO_HOLDER__READERS:
				getReaders().clear();
				return;
			case CodecInfoPackage.TYPE_INFO_HOLDER__WRITERS:
				getWriters().clear();
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
			case CodecInfoPackage.TYPE_INFO_HOLDER__INFO_TYPE:
				return infoType != INFO_TYPE_EDEFAULT;
			case CodecInfoPackage.TYPE_INFO_HOLDER__READERS:
				return readers != null && !readers.isEmpty();
			case CodecInfoPackage.TYPE_INFO_HOLDER__WRITERS:
				return writers != null && !writers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CodecInfoPackage.TYPE_INFO_HOLDER___GET_READER_BY_NAME__STRING:
				return getReaderByName((String)arguments.get(0));
			case CodecInfoPackage.TYPE_INFO_HOLDER___GET_WRITER_BY_NAME__STRING:
				return getWriterByName((String)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (infoType: ");
		result.append(infoType);
		result.append(')');
		return result.toString();
	}

} //TypeInfoHolderImpl
