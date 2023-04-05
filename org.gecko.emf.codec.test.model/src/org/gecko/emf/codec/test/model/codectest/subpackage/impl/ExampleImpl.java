/*
 */
package org.gecko.emf.codec.test.model.codectest.subpackage.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.gecko.emf.codec.test.model.codectest.Address;

import org.gecko.emf.codec.test.model.codectest.subpackage.Example;
import org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Example</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getExampleId <em>Example Id</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getMandatory <em>Mandatory</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getMandatoryMultiple <em>Mandatory Multiple</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getMultiple01 <em>Multiple01</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getMultiple02 <em>Multiple02</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getDerived <em>Derived</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getContainment <em>Containment</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getTransient <em>Transient</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getBigLong <em>Big Long</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getBigDouble <em>Big Double</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getBigInteger <em>Big Integer</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getBigFloat <em>Big Float</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getBigChar <em>Big Char</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getBigBool <em>Big Bool</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getBigShort <em>Big Short</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getBigByte <em>Big Byte</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getSmallLong <em>Small Long</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getSmallDouble <em>Small Double</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getSmallInteger <em>Small Integer</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getSmallFloat <em>Small Float</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getSmallChar <em>Small Char</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#isSmallBool <em>Small Bool</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getSmallShort <em>Small Short</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.impl.ExampleImpl#getSmallByte <em>Small Byte</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExampleImpl extends MinimalEObjectImpl.Container implements Example {
	/**
	 * The default value of the '{@link #getExampleId() <em>Example Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExampleId()
	 * @generated
	 * @ordered
	 */
	protected static final String EXAMPLE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExampleId() <em>Example Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExampleId()
	 * @generated
	 * @ordered
	 */
	protected String exampleId = EXAMPLE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMandatory()
	 * @generated
	 * @ordered
	 */
	protected static final String MANDATORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMandatory()
	 * @generated
	 * @ordered
	 */
	protected String mandatory = MANDATORY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMandatoryMultiple() <em>Mandatory Multiple</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMandatoryMultiple()
	 * @generated
	 * @ordered
	 */
	protected EList<String> mandatoryMultiple;

	/**
	 * The cached value of the '{@link #getMultiple01() <em>Multiple01</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiple01()
	 * @generated
	 * @ordered
	 */
	protected EList<String> multiple01;

	/**
	 * The cached value of the '{@link #getMultiple02() <em>Multiple02</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiple02()
	 * @generated
	 * @ordered
	 */
	protected EList<String> multiple02;

	/**
	 * The default value of the '{@link #getDerived() <em>Derived</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDerived()
	 * @generated
	 * @ordered
	 */
	protected static final int DERIVED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDerived() <em>Derived</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDerived()
	 * @generated
	 * @ordered
	 */
	protected int derived = DERIVED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContainment() <em>Containment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainment()
	 * @generated
	 * @ordered
	 */
	protected Address containment;

	/**
	 * The default value of the '{@link #getTransient() <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransient()
	 * @generated
	 * @ordered
	 */
	protected static final long TRANSIENT_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getTransient() <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransient()
	 * @generated
	 * @ordered
	 */
	protected long transient_ = TRANSIENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getBigLong() <em>Big Long</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigLong()
	 * @generated
	 * @ordered
	 */
	protected static final Long BIG_LONG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBigLong() <em>Big Long</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigLong()
	 * @generated
	 * @ordered
	 */
	protected Long bigLong = BIG_LONG_EDEFAULT;

	/**
	 * The default value of the '{@link #getBigDouble() <em>Big Double</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigDouble()
	 * @generated
	 * @ordered
	 */
	protected static final Double BIG_DOUBLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBigDouble() <em>Big Double</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigDouble()
	 * @generated
	 * @ordered
	 */
	protected Double bigDouble = BIG_DOUBLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBigInteger() <em>Big Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigInteger()
	 * @generated
	 * @ordered
	 */
	protected static final Integer BIG_INTEGER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBigInteger() <em>Big Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigInteger()
	 * @generated
	 * @ordered
	 */
	protected Integer bigInteger = BIG_INTEGER_EDEFAULT;

	/**
	 * The default value of the '{@link #getBigFloat() <em>Big Float</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigFloat()
	 * @generated
	 * @ordered
	 */
	protected static final Float BIG_FLOAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBigFloat() <em>Big Float</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigFloat()
	 * @generated
	 * @ordered
	 */
	protected Float bigFloat = BIG_FLOAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getBigChar() <em>Big Char</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigChar()
	 * @generated
	 * @ordered
	 */
	protected static final Character BIG_CHAR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBigChar() <em>Big Char</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigChar()
	 * @generated
	 * @ordered
	 */
	protected Character bigChar = BIG_CHAR_EDEFAULT;

	/**
	 * The default value of the '{@link #getBigBool() <em>Big Bool</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigBool()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean BIG_BOOL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBigBool() <em>Big Bool</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigBool()
	 * @generated
	 * @ordered
	 */
	protected Boolean bigBool = BIG_BOOL_EDEFAULT;

	/**
	 * The default value of the '{@link #getBigShort() <em>Big Short</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigShort()
	 * @generated
	 * @ordered
	 */
	protected static final Short BIG_SHORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBigShort() <em>Big Short</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigShort()
	 * @generated
	 * @ordered
	 */
	protected Short bigShort = BIG_SHORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getBigByte() <em>Big Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigByte()
	 * @generated
	 * @ordered
	 */
	protected static final Byte BIG_BYTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBigByte() <em>Big Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBigByte()
	 * @generated
	 * @ordered
	 */
	protected Byte bigByte = BIG_BYTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSmallLong() <em>Small Long</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallLong()
	 * @generated
	 * @ordered
	 */
	protected static final long SMALL_LONG_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getSmallLong() <em>Small Long</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallLong()
	 * @generated
	 * @ordered
	 */
	protected long smallLong = SMALL_LONG_EDEFAULT;

	/**
	 * The default value of the '{@link #getSmallDouble() <em>Small Double</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallDouble()
	 * @generated
	 * @ordered
	 */
	protected static final double SMALL_DOUBLE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSmallDouble() <em>Small Double</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallDouble()
	 * @generated
	 * @ordered
	 */
	protected double smallDouble = SMALL_DOUBLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSmallInteger() <em>Small Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallInteger()
	 * @generated
	 * @ordered
	 */
	protected static final int SMALL_INTEGER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSmallInteger() <em>Small Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallInteger()
	 * @generated
	 * @ordered
	 */
	protected int smallInteger = SMALL_INTEGER_EDEFAULT;

	/**
	 * The default value of the '{@link #getSmallFloat() <em>Small Float</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallFloat()
	 * @generated
	 * @ordered
	 */
	protected static final float SMALL_FLOAT_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getSmallFloat() <em>Small Float</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallFloat()
	 * @generated
	 * @ordered
	 */
	protected float smallFloat = SMALL_FLOAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSmallChar() <em>Small Char</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallChar()
	 * @generated
	 * @ordered
	 */
	protected static final char SMALL_CHAR_EDEFAULT = '\u0000';

	/**
	 * The cached value of the '{@link #getSmallChar() <em>Small Char</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallChar()
	 * @generated
	 * @ordered
	 */
	protected char smallChar = SMALL_CHAR_EDEFAULT;

	/**
	 * The default value of the '{@link #isSmallBool() <em>Small Bool</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSmallBool()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SMALL_BOOL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSmallBool() <em>Small Bool</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSmallBool()
	 * @generated
	 * @ordered
	 */
	protected boolean smallBool = SMALL_BOOL_EDEFAULT;

	/**
	 * The default value of the '{@link #getSmallShort() <em>Small Short</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallShort()
	 * @generated
	 * @ordered
	 */
	protected static final short SMALL_SHORT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSmallShort() <em>Small Short</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallShort()
	 * @generated
	 * @ordered
	 */
	protected short smallShort = SMALL_SHORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSmallByte() <em>Small Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallByte()
	 * @generated
	 * @ordered
	 */
	protected static final byte SMALL_BYTE_EDEFAULT = 0x00;

	/**
	 * The cached value of the '{@link #getSmallByte() <em>Small Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSmallByte()
	 * @generated
	 * @ordered
	 */
	protected byte smallByte = SMALL_BYTE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExampleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SubpackagePackage.Literals.EXAMPLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExampleId() {
		return exampleId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExampleId(String newExampleId) {
		String oldExampleId = exampleId;
		exampleId = newExampleId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__EXAMPLE_ID, oldExampleId, exampleId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMandatory() {
		return mandatory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMandatory(String newMandatory) {
		String oldMandatory = mandatory;
		mandatory = newMandatory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__MANDATORY, oldMandatory, mandatory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getMandatoryMultiple() {
		if (mandatoryMultiple == null) {
			mandatoryMultiple = new EDataTypeUniqueEList<String>(String.class, this, SubpackagePackage.EXAMPLE__MANDATORY_MULTIPLE);
		}
		return mandatoryMultiple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getMultiple01() {
		if (multiple01 == null) {
			multiple01 = new EDataTypeUniqueEList<String>(String.class, this, SubpackagePackage.EXAMPLE__MULTIPLE01);
		}
		return multiple01;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getMultiple02() {
		if (multiple02 == null) {
			multiple02 = new EDataTypeUniqueEList<String>(String.class, this, SubpackagePackage.EXAMPLE__MULTIPLE02);
		}
		return multiple02;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDerived() {
		return derived;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDerived(int newDerived) {
		int oldDerived = derived;
		derived = newDerived;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__DERIVED, oldDerived, derived));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Address getContainment() {
		return containment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainment(Address newContainment, NotificationChain msgs) {
		Address oldContainment = containment;
		containment = newContainment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__CONTAINMENT, oldContainment, newContainment);
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
	public void setContainment(Address newContainment) {
		if (newContainment != containment) {
			NotificationChain msgs = null;
			if (containment != null)
				msgs = ((InternalEObject)containment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SubpackagePackage.EXAMPLE__CONTAINMENT, null, msgs);
			if (newContainment != null)
				msgs = ((InternalEObject)newContainment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SubpackagePackage.EXAMPLE__CONTAINMENT, null, msgs);
			msgs = basicSetContainment(newContainment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__CONTAINMENT, newContainment, newContainment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getTransient() {
		return transient_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransient(long newTransient) {
		long oldTransient = transient_;
		transient_ = newTransient;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__TRANSIENT, oldTransient, transient_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Long getBigLong() {
		return bigLong;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBigLong(Long newBigLong) {
		Long oldBigLong = bigLong;
		bigLong = newBigLong;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__BIG_LONG, oldBigLong, bigLong));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getBigDouble() {
		return bigDouble;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBigDouble(Double newBigDouble) {
		Double oldBigDouble = bigDouble;
		bigDouble = newBigDouble;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__BIG_DOUBLE, oldBigDouble, bigDouble));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Integer getBigInteger() {
		return bigInteger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBigInteger(Integer newBigInteger) {
		Integer oldBigInteger = bigInteger;
		bigInteger = newBigInteger;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__BIG_INTEGER, oldBigInteger, bigInteger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Float getBigFloat() {
		return bigFloat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBigFloat(Float newBigFloat) {
		Float oldBigFloat = bigFloat;
		bigFloat = newBigFloat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__BIG_FLOAT, oldBigFloat, bigFloat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Character getBigChar() {
		return bigChar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBigChar(Character newBigChar) {
		Character oldBigChar = bigChar;
		bigChar = newBigChar;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__BIG_CHAR, oldBigChar, bigChar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getBigBool() {
		return bigBool;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBigBool(Boolean newBigBool) {
		Boolean oldBigBool = bigBool;
		bigBool = newBigBool;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__BIG_BOOL, oldBigBool, bigBool));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Short getBigShort() {
		return bigShort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBigShort(Short newBigShort) {
		Short oldBigShort = bigShort;
		bigShort = newBigShort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__BIG_SHORT, oldBigShort, bigShort));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Byte getBigByte() {
		return bigByte;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBigByte(Byte newBigByte) {
		Byte oldBigByte = bigByte;
		bigByte = newBigByte;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__BIG_BYTE, oldBigByte, bigByte));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getSmallLong() {
		return smallLong;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSmallLong(long newSmallLong) {
		long oldSmallLong = smallLong;
		smallLong = newSmallLong;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__SMALL_LONG, oldSmallLong, smallLong));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getSmallDouble() {
		return smallDouble;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSmallDouble(double newSmallDouble) {
		double oldSmallDouble = smallDouble;
		smallDouble = newSmallDouble;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__SMALL_DOUBLE, oldSmallDouble, smallDouble));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSmallInteger() {
		return smallInteger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSmallInteger(int newSmallInteger) {
		int oldSmallInteger = smallInteger;
		smallInteger = newSmallInteger;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__SMALL_INTEGER, oldSmallInteger, smallInteger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public float getSmallFloat() {
		return smallFloat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSmallFloat(float newSmallFloat) {
		float oldSmallFloat = smallFloat;
		smallFloat = newSmallFloat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__SMALL_FLOAT, oldSmallFloat, smallFloat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public char getSmallChar() {
		return smallChar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSmallChar(char newSmallChar) {
		char oldSmallChar = smallChar;
		smallChar = newSmallChar;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__SMALL_CHAR, oldSmallChar, smallChar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSmallBool() {
		return smallBool;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSmallBool(boolean newSmallBool) {
		boolean oldSmallBool = smallBool;
		smallBool = newSmallBool;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__SMALL_BOOL, oldSmallBool, smallBool));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public short getSmallShort() {
		return smallShort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSmallShort(short newSmallShort) {
		short oldSmallShort = smallShort;
		smallShort = newSmallShort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__SMALL_SHORT, oldSmallShort, smallShort));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public byte getSmallByte() {
		return smallByte;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSmallByte(byte newSmallByte) {
		byte oldSmallByte = smallByte;
		smallByte = newSmallByte;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SubpackagePackage.EXAMPLE__SMALL_BYTE, oldSmallByte, smallByte));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SubpackagePackage.EXAMPLE__CONTAINMENT:
				return basicSetContainment(null, msgs);
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
			case SubpackagePackage.EXAMPLE__EXAMPLE_ID:
				return getExampleId();
			case SubpackagePackage.EXAMPLE__MANDATORY:
				return getMandatory();
			case SubpackagePackage.EXAMPLE__MANDATORY_MULTIPLE:
				return getMandatoryMultiple();
			case SubpackagePackage.EXAMPLE__MULTIPLE01:
				return getMultiple01();
			case SubpackagePackage.EXAMPLE__MULTIPLE02:
				return getMultiple02();
			case SubpackagePackage.EXAMPLE__DERIVED:
				return getDerived();
			case SubpackagePackage.EXAMPLE__CONTAINMENT:
				return getContainment();
			case SubpackagePackage.EXAMPLE__TRANSIENT:
				return getTransient();
			case SubpackagePackage.EXAMPLE__BIG_LONG:
				return getBigLong();
			case SubpackagePackage.EXAMPLE__BIG_DOUBLE:
				return getBigDouble();
			case SubpackagePackage.EXAMPLE__BIG_INTEGER:
				return getBigInteger();
			case SubpackagePackage.EXAMPLE__BIG_FLOAT:
				return getBigFloat();
			case SubpackagePackage.EXAMPLE__BIG_CHAR:
				return getBigChar();
			case SubpackagePackage.EXAMPLE__BIG_BOOL:
				return getBigBool();
			case SubpackagePackage.EXAMPLE__BIG_SHORT:
				return getBigShort();
			case SubpackagePackage.EXAMPLE__BIG_BYTE:
				return getBigByte();
			case SubpackagePackage.EXAMPLE__SMALL_LONG:
				return getSmallLong();
			case SubpackagePackage.EXAMPLE__SMALL_DOUBLE:
				return getSmallDouble();
			case SubpackagePackage.EXAMPLE__SMALL_INTEGER:
				return getSmallInteger();
			case SubpackagePackage.EXAMPLE__SMALL_FLOAT:
				return getSmallFloat();
			case SubpackagePackage.EXAMPLE__SMALL_CHAR:
				return getSmallChar();
			case SubpackagePackage.EXAMPLE__SMALL_BOOL:
				return isSmallBool();
			case SubpackagePackage.EXAMPLE__SMALL_SHORT:
				return getSmallShort();
			case SubpackagePackage.EXAMPLE__SMALL_BYTE:
				return getSmallByte();
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
			case SubpackagePackage.EXAMPLE__EXAMPLE_ID:
				setExampleId((String)newValue);
				return;
			case SubpackagePackage.EXAMPLE__MANDATORY:
				setMandatory((String)newValue);
				return;
			case SubpackagePackage.EXAMPLE__MANDATORY_MULTIPLE:
				getMandatoryMultiple().clear();
				getMandatoryMultiple().addAll((Collection<? extends String>)newValue);
				return;
			case SubpackagePackage.EXAMPLE__MULTIPLE01:
				getMultiple01().clear();
				getMultiple01().addAll((Collection<? extends String>)newValue);
				return;
			case SubpackagePackage.EXAMPLE__MULTIPLE02:
				getMultiple02().clear();
				getMultiple02().addAll((Collection<? extends String>)newValue);
				return;
			case SubpackagePackage.EXAMPLE__DERIVED:
				setDerived((Integer)newValue);
				return;
			case SubpackagePackage.EXAMPLE__CONTAINMENT:
				setContainment((Address)newValue);
				return;
			case SubpackagePackage.EXAMPLE__TRANSIENT:
				setTransient((Long)newValue);
				return;
			case SubpackagePackage.EXAMPLE__BIG_LONG:
				setBigLong((Long)newValue);
				return;
			case SubpackagePackage.EXAMPLE__BIG_DOUBLE:
				setBigDouble((Double)newValue);
				return;
			case SubpackagePackage.EXAMPLE__BIG_INTEGER:
				setBigInteger((Integer)newValue);
				return;
			case SubpackagePackage.EXAMPLE__BIG_FLOAT:
				setBigFloat((Float)newValue);
				return;
			case SubpackagePackage.EXAMPLE__BIG_CHAR:
				setBigChar((Character)newValue);
				return;
			case SubpackagePackage.EXAMPLE__BIG_BOOL:
				setBigBool((Boolean)newValue);
				return;
			case SubpackagePackage.EXAMPLE__BIG_SHORT:
				setBigShort((Short)newValue);
				return;
			case SubpackagePackage.EXAMPLE__BIG_BYTE:
				setBigByte((Byte)newValue);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_LONG:
				setSmallLong((Long)newValue);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_DOUBLE:
				setSmallDouble((Double)newValue);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_INTEGER:
				setSmallInteger((Integer)newValue);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_FLOAT:
				setSmallFloat((Float)newValue);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_CHAR:
				setSmallChar((Character)newValue);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_BOOL:
				setSmallBool((Boolean)newValue);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_SHORT:
				setSmallShort((Short)newValue);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_BYTE:
				setSmallByte((Byte)newValue);
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
			case SubpackagePackage.EXAMPLE__EXAMPLE_ID:
				setExampleId(EXAMPLE_ID_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__MANDATORY:
				setMandatory(MANDATORY_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__MANDATORY_MULTIPLE:
				getMandatoryMultiple().clear();
				return;
			case SubpackagePackage.EXAMPLE__MULTIPLE01:
				getMultiple01().clear();
				return;
			case SubpackagePackage.EXAMPLE__MULTIPLE02:
				getMultiple02().clear();
				return;
			case SubpackagePackage.EXAMPLE__DERIVED:
				setDerived(DERIVED_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__CONTAINMENT:
				setContainment((Address)null);
				return;
			case SubpackagePackage.EXAMPLE__TRANSIENT:
				setTransient(TRANSIENT_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__BIG_LONG:
				setBigLong(BIG_LONG_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__BIG_DOUBLE:
				setBigDouble(BIG_DOUBLE_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__BIG_INTEGER:
				setBigInteger(BIG_INTEGER_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__BIG_FLOAT:
				setBigFloat(BIG_FLOAT_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__BIG_CHAR:
				setBigChar(BIG_CHAR_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__BIG_BOOL:
				setBigBool(BIG_BOOL_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__BIG_SHORT:
				setBigShort(BIG_SHORT_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__BIG_BYTE:
				setBigByte(BIG_BYTE_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_LONG:
				setSmallLong(SMALL_LONG_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_DOUBLE:
				setSmallDouble(SMALL_DOUBLE_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_INTEGER:
				setSmallInteger(SMALL_INTEGER_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_FLOAT:
				setSmallFloat(SMALL_FLOAT_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_CHAR:
				setSmallChar(SMALL_CHAR_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_BOOL:
				setSmallBool(SMALL_BOOL_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_SHORT:
				setSmallShort(SMALL_SHORT_EDEFAULT);
				return;
			case SubpackagePackage.EXAMPLE__SMALL_BYTE:
				setSmallByte(SMALL_BYTE_EDEFAULT);
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
			case SubpackagePackage.EXAMPLE__EXAMPLE_ID:
				return EXAMPLE_ID_EDEFAULT == null ? exampleId != null : !EXAMPLE_ID_EDEFAULT.equals(exampleId);
			case SubpackagePackage.EXAMPLE__MANDATORY:
				return MANDATORY_EDEFAULT == null ? mandatory != null : !MANDATORY_EDEFAULT.equals(mandatory);
			case SubpackagePackage.EXAMPLE__MANDATORY_MULTIPLE:
				return mandatoryMultiple != null && !mandatoryMultiple.isEmpty();
			case SubpackagePackage.EXAMPLE__MULTIPLE01:
				return multiple01 != null && !multiple01.isEmpty();
			case SubpackagePackage.EXAMPLE__MULTIPLE02:
				return multiple02 != null && !multiple02.isEmpty();
			case SubpackagePackage.EXAMPLE__DERIVED:
				return derived != DERIVED_EDEFAULT;
			case SubpackagePackage.EXAMPLE__CONTAINMENT:
				return containment != null;
			case SubpackagePackage.EXAMPLE__TRANSIENT:
				return transient_ != TRANSIENT_EDEFAULT;
			case SubpackagePackage.EXAMPLE__BIG_LONG:
				return BIG_LONG_EDEFAULT == null ? bigLong != null : !BIG_LONG_EDEFAULT.equals(bigLong);
			case SubpackagePackage.EXAMPLE__BIG_DOUBLE:
				return BIG_DOUBLE_EDEFAULT == null ? bigDouble != null : !BIG_DOUBLE_EDEFAULT.equals(bigDouble);
			case SubpackagePackage.EXAMPLE__BIG_INTEGER:
				return BIG_INTEGER_EDEFAULT == null ? bigInteger != null : !BIG_INTEGER_EDEFAULT.equals(bigInteger);
			case SubpackagePackage.EXAMPLE__BIG_FLOAT:
				return BIG_FLOAT_EDEFAULT == null ? bigFloat != null : !BIG_FLOAT_EDEFAULT.equals(bigFloat);
			case SubpackagePackage.EXAMPLE__BIG_CHAR:
				return BIG_CHAR_EDEFAULT == null ? bigChar != null : !BIG_CHAR_EDEFAULT.equals(bigChar);
			case SubpackagePackage.EXAMPLE__BIG_BOOL:
				return BIG_BOOL_EDEFAULT == null ? bigBool != null : !BIG_BOOL_EDEFAULT.equals(bigBool);
			case SubpackagePackage.EXAMPLE__BIG_SHORT:
				return BIG_SHORT_EDEFAULT == null ? bigShort != null : !BIG_SHORT_EDEFAULT.equals(bigShort);
			case SubpackagePackage.EXAMPLE__BIG_BYTE:
				return BIG_BYTE_EDEFAULT == null ? bigByte != null : !BIG_BYTE_EDEFAULT.equals(bigByte);
			case SubpackagePackage.EXAMPLE__SMALL_LONG:
				return smallLong != SMALL_LONG_EDEFAULT;
			case SubpackagePackage.EXAMPLE__SMALL_DOUBLE:
				return smallDouble != SMALL_DOUBLE_EDEFAULT;
			case SubpackagePackage.EXAMPLE__SMALL_INTEGER:
				return smallInteger != SMALL_INTEGER_EDEFAULT;
			case SubpackagePackage.EXAMPLE__SMALL_FLOAT:
				return smallFloat != SMALL_FLOAT_EDEFAULT;
			case SubpackagePackage.EXAMPLE__SMALL_CHAR:
				return smallChar != SMALL_CHAR_EDEFAULT;
			case SubpackagePackage.EXAMPLE__SMALL_BOOL:
				return smallBool != SMALL_BOOL_EDEFAULT;
			case SubpackagePackage.EXAMPLE__SMALL_SHORT:
				return smallShort != SMALL_SHORT_EDEFAULT;
			case SubpackagePackage.EXAMPLE__SMALL_BYTE:
				return smallByte != SMALL_BYTE_EDEFAULT;
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
		result.append(" (exampleId: ");
		result.append(exampleId);
		result.append(", mandatory: ");
		result.append(mandatory);
		result.append(", mandatoryMultiple: ");
		result.append(mandatoryMultiple);
		result.append(", multiple01: ");
		result.append(multiple01);
		result.append(", multiple02: ");
		result.append(multiple02);
		result.append(", derived: ");
		result.append(derived);
		result.append(", transient: ");
		result.append(transient_);
		result.append(", BigLong: ");
		result.append(bigLong);
		result.append(", BigDouble: ");
		result.append(bigDouble);
		result.append(", BigInteger: ");
		result.append(bigInteger);
		result.append(", BigFloat: ");
		result.append(bigFloat);
		result.append(", BigChar: ");
		result.append(bigChar);
		result.append(", BigBool: ");
		result.append(bigBool);
		result.append(", BigShort: ");
		result.append(bigShort);
		result.append(", BigByte: ");
		result.append(bigByte);
		result.append(", SmallLong: ");
		result.append(smallLong);
		result.append(", SmallDouble: ");
		result.append(smallDouble);
		result.append(", SmallInteger: ");
		result.append(smallInteger);
		result.append(", SmallFloat: ");
		result.append(smallFloat);
		result.append(", SmallChar: ");
		result.append(smallChar);
		result.append(", SmallBool: ");
		result.append(smallBool);
		result.append(", SmallShort: ");
		result.append(smallShort);
		result.append(", SmallByte: ");
		result.append(smallByte);
		result.append(')');
		return result.toString();
	}

} //ExampleImpl
