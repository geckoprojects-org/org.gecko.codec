/*
 */
package org.gecko.code.demo.model.person.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.GENDER_TYPE;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getLastName <em>Last Name</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getAddresses <em>Addresses</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getBirthDate <em>Birth Date</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getAge <em>Age</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#isMarried <em>Married</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getGender <em>Gender</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getNonContainedAdd <em>Non Contained Add</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getNonContainedAdds <em>Non Contained Adds</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getTitles <em>Titles</em>}</li>
 *   <li>{@link org.gecko.code.demo.model.person.impl.PersonImpl#getTransientAtt <em>Transient Att</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PersonImpl extends MinimalEObjectImpl.Container implements Person {
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
	 * The default value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected String lastName = LAST_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAddress() <em>Address</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected Address address;

	/**
	 * The cached value of the '{@link #getAddresses() <em>Addresses</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddresses()
	 * @generated
	 * @ordered
	 */
	protected EList<Address> addresses;

	/**
	 * The default value of the '{@link #getBirthDate() <em>Birth Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBirthDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date BIRTH_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBirthDate() <em>Birth Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBirthDate()
	 * @generated
	 * @ordered
	 */
	protected Date birthDate = BIRTH_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAge() <em>Age</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAge()
	 * @generated
	 * @ordered
	 */
	protected static final int AGE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAge() <em>Age</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAge()
	 * @generated
	 * @ordered
	 */
	protected int age = AGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isMarried() <em>Married</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMarried()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MARRIED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMarried() <em>Married</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMarried()
	 * @generated
	 * @ordered
	 */
	protected boolean married = MARRIED_EDEFAULT;

	/**
	 * The default value of the '{@link #getGender() <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGender()
	 * @generated
	 * @ordered
	 */
	protected static final GENDER_TYPE GENDER_EDEFAULT = GENDER_TYPE.MALE;

	/**
	 * The cached value of the '{@link #getGender() <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGender()
	 * @generated
	 * @ordered
	 */
	protected GENDER_TYPE gender = GENDER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNonContainedAdd() <em>Non Contained Add</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonContainedAdd()
	 * @generated
	 * @ordered
	 */
	protected Address nonContainedAdd;

	/**
	 * The cached value of the '{@link #getNonContainedAdds() <em>Non Contained Adds</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonContainedAdds()
	 * @generated
	 * @ordered
	 */
	protected EList<Address> nonContainedAdds;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTitles() <em>Titles</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitles()
	 * @generated
	 * @ordered
	 */
	protected EList<String> titles;

	/**
	 * The default value of the '{@link #getTransientAtt() <em>Transient Att</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransientAtt()
	 * @generated
	 * @ordered
	 */
	protected static final int TRANSIENT_ATT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTransientAtt() <em>Transient Att</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransientAtt()
	 * @generated
	 * @ordered
	 */
	protected int transientAtt = TRANSIENT_ATT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PersonImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PersonPackage.Literals.PERSON;
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
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLastName() {
		return lastName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLastName(String newLastName) {
		String oldLastName = lastName;
		lastName = newLastName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__LAST_NAME, oldLastName, lastName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Address getAddress() {
		return address;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAddress(Address newAddress, NotificationChain msgs) {
		Address oldAddress = address;
		address = newAddress;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__ADDRESS, oldAddress, newAddress);
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
	public void setAddress(Address newAddress) {
		if (newAddress != address) {
			NotificationChain msgs = null;
			if (address != null)
				msgs = ((InternalEObject)address).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PersonPackage.PERSON__ADDRESS, null, msgs);
			if (newAddress != null)
				msgs = ((InternalEObject)newAddress).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PersonPackage.PERSON__ADDRESS, null, msgs);
			msgs = basicSetAddress(newAddress, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__ADDRESS, newAddress, newAddress));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Address> getAddresses() {
		if (addresses == null) {
			addresses = new EObjectContainmentEList<Address>(Address.class, this, PersonPackage.PERSON__ADDRESSES);
		}
		return addresses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBirthDate(Date newBirthDate) {
		Date oldBirthDate = birthDate;
		birthDate = newBirthDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__BIRTH_DATE, oldBirthDate, birthDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getAge() {
		return age;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAge(int newAge) {
		int oldAge = age;
		age = newAge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__AGE, oldAge, age));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMarried() {
		return married;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMarried(boolean newMarried) {
		boolean oldMarried = married;
		married = newMarried;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__MARRIED, oldMarried, married));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GENDER_TYPE getGender() {
		return gender;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGender(GENDER_TYPE newGender) {
		GENDER_TYPE oldGender = gender;
		gender = newGender == null ? GENDER_EDEFAULT : newGender;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__GENDER, oldGender, gender));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Address getNonContainedAdd() {
		if (nonContainedAdd != null && nonContainedAdd.eIsProxy()) {
			InternalEObject oldNonContainedAdd = (InternalEObject)nonContainedAdd;
			nonContainedAdd = (Address)eResolveProxy(oldNonContainedAdd);
			if (nonContainedAdd != oldNonContainedAdd) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PersonPackage.PERSON__NON_CONTAINED_ADD, oldNonContainedAdd, nonContainedAdd));
			}
		}
		return nonContainedAdd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Address basicGetNonContainedAdd() {
		return nonContainedAdd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNonContainedAdd(Address newNonContainedAdd) {
		Address oldNonContainedAdd = nonContainedAdd;
		nonContainedAdd = newNonContainedAdd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__NON_CONTAINED_ADD, oldNonContainedAdd, nonContainedAdd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Address> getNonContainedAdds() {
		if (nonContainedAdds == null) {
			nonContainedAdds = new EObjectResolvingEList<Address>(Address.class, this, PersonPackage.PERSON__NON_CONTAINED_ADDS);
		}
		return nonContainedAdds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getTitles() {
		if (titles == null) {
			titles = new EDataTypeUniqueEList<String>(String.class, this, PersonPackage.PERSON__TITLES);
		}
		return titles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getTransientAtt() {
		return transientAtt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransientAtt(int newTransientAtt) {
		int oldTransientAtt = transientAtt;
		transientAtt = newTransientAtt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersonPackage.PERSON__TRANSIENT_ATT, oldTransientAtt, transientAtt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PersonPackage.PERSON__ADDRESS:
				return basicSetAddress(null, msgs);
			case PersonPackage.PERSON__ADDRESSES:
				return ((InternalEList<?>)getAddresses()).basicRemove(otherEnd, msgs);
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
			case PersonPackage.PERSON__NAME:
				return getName();
			case PersonPackage.PERSON__LAST_NAME:
				return getLastName();
			case PersonPackage.PERSON__ADDRESS:
				return getAddress();
			case PersonPackage.PERSON__ADDRESSES:
				return getAddresses();
			case PersonPackage.PERSON__BIRTH_DATE:
				return getBirthDate();
			case PersonPackage.PERSON__AGE:
				return getAge();
			case PersonPackage.PERSON__MARRIED:
				return isMarried();
			case PersonPackage.PERSON__GENDER:
				return getGender();
			case PersonPackage.PERSON__NON_CONTAINED_ADD:
				if (resolve) return getNonContainedAdd();
				return basicGetNonContainedAdd();
			case PersonPackage.PERSON__NON_CONTAINED_ADDS:
				return getNonContainedAdds();
			case PersonPackage.PERSON__ID:
				return getId();
			case PersonPackage.PERSON__TITLES:
				return getTitles();
			case PersonPackage.PERSON__TRANSIENT_ATT:
				return getTransientAtt();
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
			case PersonPackage.PERSON__NAME:
				setName((String)newValue);
				return;
			case PersonPackage.PERSON__LAST_NAME:
				setLastName((String)newValue);
				return;
			case PersonPackage.PERSON__ADDRESS:
				setAddress((Address)newValue);
				return;
			case PersonPackage.PERSON__ADDRESSES:
				getAddresses().clear();
				getAddresses().addAll((Collection<? extends Address>)newValue);
				return;
			case PersonPackage.PERSON__BIRTH_DATE:
				setBirthDate((Date)newValue);
				return;
			case PersonPackage.PERSON__AGE:
				setAge((Integer)newValue);
				return;
			case PersonPackage.PERSON__MARRIED:
				setMarried((Boolean)newValue);
				return;
			case PersonPackage.PERSON__GENDER:
				setGender((GENDER_TYPE)newValue);
				return;
			case PersonPackage.PERSON__NON_CONTAINED_ADD:
				setNonContainedAdd((Address)newValue);
				return;
			case PersonPackage.PERSON__NON_CONTAINED_ADDS:
				getNonContainedAdds().clear();
				getNonContainedAdds().addAll((Collection<? extends Address>)newValue);
				return;
			case PersonPackage.PERSON__ID:
				setId((String)newValue);
				return;
			case PersonPackage.PERSON__TITLES:
				getTitles().clear();
				getTitles().addAll((Collection<? extends String>)newValue);
				return;
			case PersonPackage.PERSON__TRANSIENT_ATT:
				setTransientAtt((Integer)newValue);
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
			case PersonPackage.PERSON__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PersonPackage.PERSON__LAST_NAME:
				setLastName(LAST_NAME_EDEFAULT);
				return;
			case PersonPackage.PERSON__ADDRESS:
				setAddress((Address)null);
				return;
			case PersonPackage.PERSON__ADDRESSES:
				getAddresses().clear();
				return;
			case PersonPackage.PERSON__BIRTH_DATE:
				setBirthDate(BIRTH_DATE_EDEFAULT);
				return;
			case PersonPackage.PERSON__AGE:
				setAge(AGE_EDEFAULT);
				return;
			case PersonPackage.PERSON__MARRIED:
				setMarried(MARRIED_EDEFAULT);
				return;
			case PersonPackage.PERSON__GENDER:
				setGender(GENDER_EDEFAULT);
				return;
			case PersonPackage.PERSON__NON_CONTAINED_ADD:
				setNonContainedAdd((Address)null);
				return;
			case PersonPackage.PERSON__NON_CONTAINED_ADDS:
				getNonContainedAdds().clear();
				return;
			case PersonPackage.PERSON__ID:
				setId(ID_EDEFAULT);
				return;
			case PersonPackage.PERSON__TITLES:
				getTitles().clear();
				return;
			case PersonPackage.PERSON__TRANSIENT_ATT:
				setTransientAtt(TRANSIENT_ATT_EDEFAULT);
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
			case PersonPackage.PERSON__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PersonPackage.PERSON__LAST_NAME:
				return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
			case PersonPackage.PERSON__ADDRESS:
				return address != null;
			case PersonPackage.PERSON__ADDRESSES:
				return addresses != null && !addresses.isEmpty();
			case PersonPackage.PERSON__BIRTH_DATE:
				return BIRTH_DATE_EDEFAULT == null ? birthDate != null : !BIRTH_DATE_EDEFAULT.equals(birthDate);
			case PersonPackage.PERSON__AGE:
				return age != AGE_EDEFAULT;
			case PersonPackage.PERSON__MARRIED:
				return married != MARRIED_EDEFAULT;
			case PersonPackage.PERSON__GENDER:
				return gender != GENDER_EDEFAULT;
			case PersonPackage.PERSON__NON_CONTAINED_ADD:
				return nonContainedAdd != null;
			case PersonPackage.PERSON__NON_CONTAINED_ADDS:
				return nonContainedAdds != null && !nonContainedAdds.isEmpty();
			case PersonPackage.PERSON__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case PersonPackage.PERSON__TITLES:
				return titles != null && !titles.isEmpty();
			case PersonPackage.PERSON__TRANSIENT_ATT:
				return transientAtt != TRANSIENT_ATT_EDEFAULT;
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
			case PersonPackage.PERSON___GET_FULL_NAME:
				return getFullName();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", lastName: ");
		result.append(lastName);
		result.append(", birthDate: ");
		result.append(birthDate);
		result.append(", age: ");
		result.append(age);
		result.append(", married: ");
		result.append(married);
		result.append(", gender: ");
		result.append(gender);
		result.append(", id: ");
		result.append(id);
		result.append(", titles: ");
		result.append(titles);
		result.append(", transientAtt: ");
		result.append(transientAtt);
		result.append(')');
		return result.toString();
	}

} //PersonImpl
