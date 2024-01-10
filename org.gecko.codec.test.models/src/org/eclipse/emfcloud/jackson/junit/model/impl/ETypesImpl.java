/*
 */
package org.eclipse.emfcloud.jackson.junit.model.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.jackson.junit.model.ETypes;
import org.eclipse.emfcloud.jackson.junit.model.ModelPackage;
import org.eclipse.emfcloud.jackson.junit.model.PrimaryObject;
import org.eclipse.emfcloud.jackson.junit.model.TargetObject;
import org.eclipse.emfcloud.jackson.junit.model.Type;
import org.eclipse.emfcloud.jackson.junit.model.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ETypes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEString <em>EString</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEStrings <em>EStrings</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#isEBoolean <em>EBoolean</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEBooleans <em>EBooleans</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEInt <em>EInt</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEInts <em>EInts</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getDoubleValue <em>Double Value</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEDouble <em>EDouble</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEDoubles <em>EDoubles</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEBigDecimal <em>EBig Decimal</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEBigInteger <em>EBig Integer</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEByte <em>EByte</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEByteArray <em>EByte Array</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEChar <em>EChar</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEDate <em>EDate</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEFloat <em>EFloat</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getELong <em>ELong</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getEShort <em>EShort</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getObjectTypeMany <em>Object Type Many</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getObjectArrayType <em>Object Array Type</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getObjectArrayTypeMany <em>Object Array Type Many</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getValues <em>Values</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getValuesWithRef <em>Values With Ref</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getStringMapValues <em>String Map Values</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getDataTypeMapValues <em>Data Type Map Values</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ETypesImpl#getUris <em>Uris</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ETypesImpl extends MinimalEObjectImpl implements ETypes {
	/**
	 * The default value of the '{@link #getEString() <em>EString</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEString()
	 * @generated
	 * @ordered
	 */
	protected static final String ESTRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEString() <em>EString</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEString()
	 * @generated
	 * @ordered
	 */
	protected String eString = ESTRING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEStrings() <em>EStrings</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEStrings()
	 * @generated
	 * @ordered
	 */
	protected EList<String> eStrings;

	/**
	 * The default value of the '{@link #isEBoolean() <em>EBoolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEBoolean()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EBOOLEAN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEBoolean() <em>EBoolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEBoolean()
	 * @generated
	 * @ordered
	 */
	protected boolean eBoolean = EBOOLEAN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEBooleans() <em>EBooleans</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEBooleans()
	 * @generated
	 * @ordered
	 */
	protected EList<Boolean> eBooleans;

	/**
	 * The default value of the '{@link #getEInt() <em>EInt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEInt()
	 * @generated
	 * @ordered
	 */
	protected static final int EINT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEInt() <em>EInt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEInt()
	 * @generated
	 * @ordered
	 */
	protected int eInt = EINT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEInts() <em>EInts</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEInts()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> eInts;

	/**
	 * The default value of the '{@link #getDoubleValue() <em>Double Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleValue()
	 * @generated
	 * @ordered
	 */
	protected static final Double DOUBLE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDoubleValue() <em>Double Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleValue()
	 * @generated
	 * @ordered
	 */
	protected Double doubleValue = DOUBLE_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEDouble() <em>EDouble</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEDouble()
	 * @generated
	 * @ordered
	 */
	protected static final double EDOUBLE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getEDouble() <em>EDouble</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEDouble()
	 * @generated
	 * @ordered
	 */
	protected double eDouble = EDOUBLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEDoubles() <em>EDoubles</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEDoubles()
	 * @generated
	 * @ordered
	 */
	protected EList<Double> eDoubles;

	/**
	 * The default value of the '{@link #getEBigDecimal() <em>EBig Decimal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEBigDecimal()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal EBIG_DECIMAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEBigDecimal() <em>EBig Decimal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEBigDecimal()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal eBigDecimal = EBIG_DECIMAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getEBigInteger() <em>EBig Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEBigInteger()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger EBIG_INTEGER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEBigInteger() <em>EBig Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEBigInteger()
	 * @generated
	 * @ordered
	 */
	protected BigInteger eBigInteger = EBIG_INTEGER_EDEFAULT;

	/**
	 * The default value of the '{@link #getEByte() <em>EByte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEByte()
	 * @generated
	 * @ordered
	 */
	protected static final byte EBYTE_EDEFAULT = 0x00;

	/**
	 * The cached value of the '{@link #getEByte() <em>EByte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEByte()
	 * @generated
	 * @ordered
	 */
	protected byte eByte = EBYTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEByteArray() <em>EByte Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEByteArray()
	 * @generated
	 * @ordered
	 */
	protected static final byte[] EBYTE_ARRAY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEByteArray() <em>EByte Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEByteArray()
	 * @generated
	 * @ordered
	 */
	protected byte[] eByteArray = EBYTE_ARRAY_EDEFAULT;

	/**
	 * The default value of the '{@link #getEChar() <em>EChar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEChar()
	 * @generated
	 * @ordered
	 */
	protected static final char ECHAR_EDEFAULT = '\u0000';

	/**
	 * The cached value of the '{@link #getEChar() <em>EChar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEChar()
	 * @generated
	 * @ordered
	 */
	protected char eChar = ECHAR_EDEFAULT;

	/**
	 * The default value of the '{@link #getEDate() <em>EDate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date EDATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEDate() <em>EDate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEDate()
	 * @generated
	 * @ordered
	 */
	protected Date eDate = EDATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEFloat() <em>EFloat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEFloat()
	 * @generated
	 * @ordered
	 */
	protected static final float EFLOAT_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getEFloat() <em>EFloat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEFloat()
	 * @generated
	 * @ordered
	 */
	protected float eFloat = EFLOAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getELong() <em>ELong</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getELong()
	 * @generated
	 * @ordered
	 */
	protected static final long ELONG_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getELong() <em>ELong</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getELong()
	 * @generated
	 * @ordered
	 */
	protected long eLong = ELONG_EDEFAULT;

	/**
	 * The default value of the '{@link #getEShort() <em>EShort</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEShort()
	 * @generated
	 * @ordered
	 */
	protected static final short ESHORT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEShort() <em>EShort</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEShort()
	 * @generated
	 * @ordered
	 */
	protected short eShort = ESHORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected static final Object OBJECT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected Object objectType = OBJECT_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getObjectTypeMany() <em>Object Type Many</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectTypeMany()
	 * @generated
	 * @ordered
	 */
	protected EList<Object> objectTypeMany;

	/**
	 * The default value of the '{@link #getObjectArrayType() <em>Object Array Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectArrayType()
	 * @generated
	 * @ordered
	 */
	protected static final Object[] OBJECT_ARRAY_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectArrayType() <em>Object Array Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectArrayType()
	 * @generated
	 * @ordered
	 */
	protected Object[] objectArrayType = OBJECT_ARRAY_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getObjectArrayTypeMany() <em>Object Array Type Many</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectArrayTypeMany()
	 * @generated
	 * @ordered
	 */
	protected EList<Object[]> objectArrayTypeMany;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EMap<Type, Value> values;

	/**
	 * The cached value of the '{@link #getValuesWithRef() <em>Values With Ref</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuesWithRef()
	 * @generated
	 * @ordered
	 */
	protected EMap<PrimaryObject, TargetObject> valuesWithRef;

	/**
	 * The cached value of the '{@link #getStringMapValues() <em>String Map Values</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringMapValues()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Value> stringMapValues;

	/**
	 * The cached value of the '{@link #getDataTypeMapValues() <em>Data Type Map Values</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataTypeMapValues()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> dataTypeMapValues;

	/**
	 * The cached value of the '{@link #getUris() <em>Uris</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUris()
	 * @generated
	 * @ordered
	 */
	protected EList<URI> uris;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ETypesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ETYPES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEString() {
		return eString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEString(String newEString) {
		String oldEString = eString;
		eString = newEString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__ESTRING, oldEString, eString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getEStrings() {
		if (eStrings == null) {
			eStrings = new EDataTypeUniqueEList<String>(String.class, this, ModelPackage.ETYPES__ESTRINGS);
		}
		return eStrings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isEBoolean() {
		return eBoolean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEBoolean(boolean newEBoolean) {
		boolean oldEBoolean = eBoolean;
		eBoolean = newEBoolean;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EBOOLEAN, oldEBoolean, eBoolean));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Boolean> getEBooleans() {
		if (eBooleans == null) {
			eBooleans = new EDataTypeUniqueEList<Boolean>(Boolean.class, this, ModelPackage.ETYPES__EBOOLEANS);
		}
		return eBooleans;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getEInt() {
		return eInt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEInt(int newEInt) {
		int oldEInt = eInt;
		eInt = newEInt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EINT, oldEInt, eInt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getEInts() {
		if (eInts == null) {
			eInts = new EDataTypeUniqueEList<Integer>(Integer.class, this, ModelPackage.ETYPES__EINTS);
		}
		return eInts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getDoubleValue() {
		return doubleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDoubleValue(Double newDoubleValue) {
		Double oldDoubleValue = doubleValue;
		doubleValue = newDoubleValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__DOUBLE_VALUE, oldDoubleValue, doubleValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getEDouble() {
		return eDouble;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEDouble(double newEDouble) {
		double oldEDouble = eDouble;
		eDouble = newEDouble;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EDOUBLE, oldEDouble, eDouble));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Double> getEDoubles() {
		if (eDoubles == null) {
			eDoubles = new EDataTypeUniqueEList<Double>(Double.class, this, ModelPackage.ETYPES__EDOUBLES);
		}
		return eDoubles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getEBigDecimal() {
		return eBigDecimal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEBigDecimal(BigDecimal newEBigDecimal) {
		BigDecimal oldEBigDecimal = eBigDecimal;
		eBigDecimal = newEBigDecimal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EBIG_DECIMAL, oldEBigDecimal, eBigDecimal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getEBigInteger() {
		return eBigInteger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEBigInteger(BigInteger newEBigInteger) {
		BigInteger oldEBigInteger = eBigInteger;
		eBigInteger = newEBigInteger;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EBIG_INTEGER, oldEBigInteger, eBigInteger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public byte getEByte() {
		return eByte;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEByte(byte newEByte) {
		byte oldEByte = eByte;
		eByte = newEByte;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EBYTE, oldEByte, eByte));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public byte[] getEByteArray() {
		return eByteArray;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEByteArray(byte[] newEByteArray) {
		byte[] oldEByteArray = eByteArray;
		eByteArray = newEByteArray;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EBYTE_ARRAY, oldEByteArray, eByteArray));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public char getEChar() {
		return eChar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEChar(char newEChar) {
		char oldEChar = eChar;
		eChar = newEChar;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__ECHAR, oldEChar, eChar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getEDate() {
		return eDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEDate(Date newEDate) {
		Date oldEDate = eDate;
		eDate = newEDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EDATE, oldEDate, eDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public float getEFloat() {
		return eFloat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEFloat(float newEFloat) {
		float oldEFloat = eFloat;
		eFloat = newEFloat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__EFLOAT, oldEFloat, eFloat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getELong() {
		return eLong;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setELong(long newELong) {
		long oldELong = eLong;
		eLong = newELong;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__ELONG, oldELong, eLong));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public short getEShort() {
		return eShort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEShort(short newEShort) {
		short oldEShort = eShort;
		eShort = newEShort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__ESHORT, oldEShort, eShort));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getObjectType() {
		return objectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObjectType(Object newObjectType) {
		Object oldObjectType = objectType;
		objectType = newObjectType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__OBJECT_TYPE, oldObjectType, objectType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Object> getObjectTypeMany() {
		if (objectTypeMany == null) {
			objectTypeMany = new EDataTypeEList<Object>(Object.class, this, ModelPackage.ETYPES__OBJECT_TYPE_MANY);
		}
		return objectTypeMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object[] getObjectArrayType() {
		return objectArrayType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObjectArrayType(Object[] newObjectArrayType) {
		Object[] oldObjectArrayType = objectArrayType;
		objectArrayType = newObjectArrayType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ETYPES__OBJECT_ARRAY_TYPE, oldObjectArrayType, objectArrayType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Object[]> getObjectArrayTypeMany() {
		if (objectArrayTypeMany == null) {
			objectArrayTypeMany = new EDataTypeEList<Object[]>(Object[].class, this, ModelPackage.ETYPES__OBJECT_ARRAY_TYPE_MANY);
		}
		return objectArrayTypeMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<Type, Value> getValues() {
		if (values == null) {
			values = new EcoreEMap<Type,Value>(ModelPackage.Literals.TMAP, TMapImpl.class, this, ModelPackage.ETYPES__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<PrimaryObject, TargetObject> getValuesWithRef() {
		if (valuesWithRef == null) {
			valuesWithRef = new EcoreEMap<PrimaryObject,TargetObject>(ModelPackage.Literals.TMAP_REF, TMapRefImpl.class, this, ModelPackage.ETYPES__VALUES_WITH_REF);
		}
		return valuesWithRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, Value> getStringMapValues() {
		if (stringMapValues == null) {
			stringMapValues = new EcoreEMap<String,Value>(ModelPackage.Literals.STRING_MAP, StringMapImpl.class, this, ModelPackage.ETYPES__STRING_MAP_VALUES);
		}
		return stringMapValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getDataTypeMapValues() {
		if (dataTypeMapValues == null) {
			dataTypeMapValues = new EcoreEMap<String,String>(ModelPackage.Literals.DATA_TYPE_MAP, DataTypeMapImpl.class, this, ModelPackage.ETYPES__DATA_TYPE_MAP_VALUES);
		}
		return dataTypeMapValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<URI> getUris() {
		if (uris == null) {
			uris = new EDataTypeUniqueEList<URI>(URI.class, this, ModelPackage.ETYPES__URIS);
		}
		return uris;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ETYPES__VALUES:
				return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
			case ModelPackage.ETYPES__VALUES_WITH_REF:
				return ((InternalEList<?>)getValuesWithRef()).basicRemove(otherEnd, msgs);
			case ModelPackage.ETYPES__STRING_MAP_VALUES:
				return ((InternalEList<?>)getStringMapValues()).basicRemove(otherEnd, msgs);
			case ModelPackage.ETYPES__DATA_TYPE_MAP_VALUES:
				return ((InternalEList<?>)getDataTypeMapValues()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.ETYPES__ESTRING:
				return getEString();
			case ModelPackage.ETYPES__ESTRINGS:
				return getEStrings();
			case ModelPackage.ETYPES__EBOOLEAN:
				return isEBoolean();
			case ModelPackage.ETYPES__EBOOLEANS:
				return getEBooleans();
			case ModelPackage.ETYPES__EINT:
				return getEInt();
			case ModelPackage.ETYPES__EINTS:
				return getEInts();
			case ModelPackage.ETYPES__DOUBLE_VALUE:
				return getDoubleValue();
			case ModelPackage.ETYPES__EDOUBLE:
				return getEDouble();
			case ModelPackage.ETYPES__EDOUBLES:
				return getEDoubles();
			case ModelPackage.ETYPES__EBIG_DECIMAL:
				return getEBigDecimal();
			case ModelPackage.ETYPES__EBIG_INTEGER:
				return getEBigInteger();
			case ModelPackage.ETYPES__EBYTE:
				return getEByte();
			case ModelPackage.ETYPES__EBYTE_ARRAY:
				return getEByteArray();
			case ModelPackage.ETYPES__ECHAR:
				return getEChar();
			case ModelPackage.ETYPES__EDATE:
				return getEDate();
			case ModelPackage.ETYPES__EFLOAT:
				return getEFloat();
			case ModelPackage.ETYPES__ELONG:
				return getELong();
			case ModelPackage.ETYPES__ESHORT:
				return getEShort();
			case ModelPackage.ETYPES__OBJECT_TYPE:
				return getObjectType();
			case ModelPackage.ETYPES__OBJECT_TYPE_MANY:
				return getObjectTypeMany();
			case ModelPackage.ETYPES__OBJECT_ARRAY_TYPE:
				return getObjectArrayType();
			case ModelPackage.ETYPES__OBJECT_ARRAY_TYPE_MANY:
				return getObjectArrayTypeMany();
			case ModelPackage.ETYPES__VALUES:
				if (coreType) return getValues();
				else return getValues().map();
			case ModelPackage.ETYPES__VALUES_WITH_REF:
				if (coreType) return getValuesWithRef();
				else return getValuesWithRef().map();
			case ModelPackage.ETYPES__STRING_MAP_VALUES:
				if (coreType) return getStringMapValues();
				else return getStringMapValues().map();
			case ModelPackage.ETYPES__DATA_TYPE_MAP_VALUES:
				if (coreType) return getDataTypeMapValues();
				else return getDataTypeMapValues().map();
			case ModelPackage.ETYPES__URIS:
				return getUris();
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
			case ModelPackage.ETYPES__ESTRING:
				setEString((String)newValue);
				return;
			case ModelPackage.ETYPES__ESTRINGS:
				getEStrings().clear();
				getEStrings().addAll((Collection<? extends String>)newValue);
				return;
			case ModelPackage.ETYPES__EBOOLEAN:
				setEBoolean((Boolean)newValue);
				return;
			case ModelPackage.ETYPES__EBOOLEANS:
				getEBooleans().clear();
				getEBooleans().addAll((Collection<? extends Boolean>)newValue);
				return;
			case ModelPackage.ETYPES__EINT:
				setEInt((Integer)newValue);
				return;
			case ModelPackage.ETYPES__EINTS:
				getEInts().clear();
				getEInts().addAll((Collection<? extends Integer>)newValue);
				return;
			case ModelPackage.ETYPES__DOUBLE_VALUE:
				setDoubleValue((Double)newValue);
				return;
			case ModelPackage.ETYPES__EDOUBLE:
				setEDouble((Double)newValue);
				return;
			case ModelPackage.ETYPES__EDOUBLES:
				getEDoubles().clear();
				getEDoubles().addAll((Collection<? extends Double>)newValue);
				return;
			case ModelPackage.ETYPES__EBIG_DECIMAL:
				setEBigDecimal((BigDecimal)newValue);
				return;
			case ModelPackage.ETYPES__EBIG_INTEGER:
				setEBigInteger((BigInteger)newValue);
				return;
			case ModelPackage.ETYPES__EBYTE:
				setEByte((Byte)newValue);
				return;
			case ModelPackage.ETYPES__EBYTE_ARRAY:
				setEByteArray((byte[])newValue);
				return;
			case ModelPackage.ETYPES__ECHAR:
				setEChar((Character)newValue);
				return;
			case ModelPackage.ETYPES__EDATE:
				setEDate((Date)newValue);
				return;
			case ModelPackage.ETYPES__EFLOAT:
				setEFloat((Float)newValue);
				return;
			case ModelPackage.ETYPES__ELONG:
				setELong((Long)newValue);
				return;
			case ModelPackage.ETYPES__ESHORT:
				setEShort((Short)newValue);
				return;
			case ModelPackage.ETYPES__OBJECT_TYPE:
				setObjectType(newValue);
				return;
			case ModelPackage.ETYPES__OBJECT_TYPE_MANY:
				getObjectTypeMany().clear();
				getObjectTypeMany().addAll((Collection<? extends Object>)newValue);
				return;
			case ModelPackage.ETYPES__OBJECT_ARRAY_TYPE:
				setObjectArrayType((Object[])newValue);
				return;
			case ModelPackage.ETYPES__OBJECT_ARRAY_TYPE_MANY:
				getObjectArrayTypeMany().clear();
				getObjectArrayTypeMany().addAll((Collection<? extends Object[]>)newValue);
				return;
			case ModelPackage.ETYPES__VALUES:
				((EStructuralFeature.Setting)getValues()).set(newValue);
				return;
			case ModelPackage.ETYPES__VALUES_WITH_REF:
				((EStructuralFeature.Setting)getValuesWithRef()).set(newValue);
				return;
			case ModelPackage.ETYPES__STRING_MAP_VALUES:
				((EStructuralFeature.Setting)getStringMapValues()).set(newValue);
				return;
			case ModelPackage.ETYPES__DATA_TYPE_MAP_VALUES:
				((EStructuralFeature.Setting)getDataTypeMapValues()).set(newValue);
				return;
			case ModelPackage.ETYPES__URIS:
				getUris().clear();
				getUris().addAll((Collection<? extends URI>)newValue);
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
			case ModelPackage.ETYPES__ESTRING:
				setEString(ESTRING_EDEFAULT);
				return;
			case ModelPackage.ETYPES__ESTRINGS:
				getEStrings().clear();
				return;
			case ModelPackage.ETYPES__EBOOLEAN:
				setEBoolean(EBOOLEAN_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EBOOLEANS:
				getEBooleans().clear();
				return;
			case ModelPackage.ETYPES__EINT:
				setEInt(EINT_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EINTS:
				getEInts().clear();
				return;
			case ModelPackage.ETYPES__DOUBLE_VALUE:
				setDoubleValue(DOUBLE_VALUE_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EDOUBLE:
				setEDouble(EDOUBLE_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EDOUBLES:
				getEDoubles().clear();
				return;
			case ModelPackage.ETYPES__EBIG_DECIMAL:
				setEBigDecimal(EBIG_DECIMAL_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EBIG_INTEGER:
				setEBigInteger(EBIG_INTEGER_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EBYTE:
				setEByte(EBYTE_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EBYTE_ARRAY:
				setEByteArray(EBYTE_ARRAY_EDEFAULT);
				return;
			case ModelPackage.ETYPES__ECHAR:
				setEChar(ECHAR_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EDATE:
				setEDate(EDATE_EDEFAULT);
				return;
			case ModelPackage.ETYPES__EFLOAT:
				setEFloat(EFLOAT_EDEFAULT);
				return;
			case ModelPackage.ETYPES__ELONG:
				setELong(ELONG_EDEFAULT);
				return;
			case ModelPackage.ETYPES__ESHORT:
				setEShort(ESHORT_EDEFAULT);
				return;
			case ModelPackage.ETYPES__OBJECT_TYPE:
				setObjectType(OBJECT_TYPE_EDEFAULT);
				return;
			case ModelPackage.ETYPES__OBJECT_TYPE_MANY:
				getObjectTypeMany().clear();
				return;
			case ModelPackage.ETYPES__OBJECT_ARRAY_TYPE:
				setObjectArrayType(OBJECT_ARRAY_TYPE_EDEFAULT);
				return;
			case ModelPackage.ETYPES__OBJECT_ARRAY_TYPE_MANY:
				getObjectArrayTypeMany().clear();
				return;
			case ModelPackage.ETYPES__VALUES:
				getValues().clear();
				return;
			case ModelPackage.ETYPES__VALUES_WITH_REF:
				getValuesWithRef().clear();
				return;
			case ModelPackage.ETYPES__STRING_MAP_VALUES:
				getStringMapValues().clear();
				return;
			case ModelPackage.ETYPES__DATA_TYPE_MAP_VALUES:
				getDataTypeMapValues().clear();
				return;
			case ModelPackage.ETYPES__URIS:
				getUris().clear();
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
			case ModelPackage.ETYPES__ESTRING:
				return ESTRING_EDEFAULT == null ? eString != null : !ESTRING_EDEFAULT.equals(eString);
			case ModelPackage.ETYPES__ESTRINGS:
				return eStrings != null && !eStrings.isEmpty();
			case ModelPackage.ETYPES__EBOOLEAN:
				return eBoolean != EBOOLEAN_EDEFAULT;
			case ModelPackage.ETYPES__EBOOLEANS:
				return eBooleans != null && !eBooleans.isEmpty();
			case ModelPackage.ETYPES__EINT:
				return eInt != EINT_EDEFAULT;
			case ModelPackage.ETYPES__EINTS:
				return eInts != null && !eInts.isEmpty();
			case ModelPackage.ETYPES__DOUBLE_VALUE:
				return DOUBLE_VALUE_EDEFAULT == null ? doubleValue != null : !DOUBLE_VALUE_EDEFAULT.equals(doubleValue);
			case ModelPackage.ETYPES__EDOUBLE:
				return eDouble != EDOUBLE_EDEFAULT;
			case ModelPackage.ETYPES__EDOUBLES:
				return eDoubles != null && !eDoubles.isEmpty();
			case ModelPackage.ETYPES__EBIG_DECIMAL:
				return EBIG_DECIMAL_EDEFAULT == null ? eBigDecimal != null : !EBIG_DECIMAL_EDEFAULT.equals(eBigDecimal);
			case ModelPackage.ETYPES__EBIG_INTEGER:
				return EBIG_INTEGER_EDEFAULT == null ? eBigInteger != null : !EBIG_INTEGER_EDEFAULT.equals(eBigInteger);
			case ModelPackage.ETYPES__EBYTE:
				return eByte != EBYTE_EDEFAULT;
			case ModelPackage.ETYPES__EBYTE_ARRAY:
				return EBYTE_ARRAY_EDEFAULT == null ? eByteArray != null : !EBYTE_ARRAY_EDEFAULT.equals(eByteArray);
			case ModelPackage.ETYPES__ECHAR:
				return eChar != ECHAR_EDEFAULT;
			case ModelPackage.ETYPES__EDATE:
				return EDATE_EDEFAULT == null ? eDate != null : !EDATE_EDEFAULT.equals(eDate);
			case ModelPackage.ETYPES__EFLOAT:
				return eFloat != EFLOAT_EDEFAULT;
			case ModelPackage.ETYPES__ELONG:
				return eLong != ELONG_EDEFAULT;
			case ModelPackage.ETYPES__ESHORT:
				return eShort != ESHORT_EDEFAULT;
			case ModelPackage.ETYPES__OBJECT_TYPE:
				return OBJECT_TYPE_EDEFAULT == null ? objectType != null : !OBJECT_TYPE_EDEFAULT.equals(objectType);
			case ModelPackage.ETYPES__OBJECT_TYPE_MANY:
				return objectTypeMany != null && !objectTypeMany.isEmpty();
			case ModelPackage.ETYPES__OBJECT_ARRAY_TYPE:
				return OBJECT_ARRAY_TYPE_EDEFAULT == null ? objectArrayType != null : !OBJECT_ARRAY_TYPE_EDEFAULT.equals(objectArrayType);
			case ModelPackage.ETYPES__OBJECT_ARRAY_TYPE_MANY:
				return objectArrayTypeMany != null && !objectArrayTypeMany.isEmpty();
			case ModelPackage.ETYPES__VALUES:
				return values != null && !values.isEmpty();
			case ModelPackage.ETYPES__VALUES_WITH_REF:
				return valuesWithRef != null && !valuesWithRef.isEmpty();
			case ModelPackage.ETYPES__STRING_MAP_VALUES:
				return stringMapValues != null && !stringMapValues.isEmpty();
			case ModelPackage.ETYPES__DATA_TYPE_MAP_VALUES:
				return dataTypeMapValues != null && !dataTypeMapValues.isEmpty();
			case ModelPackage.ETYPES__URIS:
				return uris != null && !uris.isEmpty();
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
		result.append(" (eString: ");
		result.append(eString);
		result.append(", eStrings: ");
		result.append(eStrings);
		result.append(", eBoolean: ");
		result.append(eBoolean);
		result.append(", eBooleans: ");
		result.append(eBooleans);
		result.append(", eInt: ");
		result.append(eInt);
		result.append(", eInts: ");
		result.append(eInts);
		result.append(", doubleValue: ");
		result.append(doubleValue);
		result.append(", eDouble: ");
		result.append(eDouble);
		result.append(", eDoubles: ");
		result.append(eDoubles);
		result.append(", eBigDecimal: ");
		result.append(eBigDecimal);
		result.append(", eBigInteger: ");
		result.append(eBigInteger);
		result.append(", eByte: ");
		result.append(eByte);
		result.append(", eByteArray: ");
		result.append(eByteArray);
		result.append(", eChar: ");
		result.append(eChar);
		result.append(", eDate: ");
		result.append(eDate);
		result.append(", eFloat: ");
		result.append(eFloat);
		result.append(", eLong: ");
		result.append(eLong);
		result.append(", eShort: ");
		result.append(eShort);
		result.append(", objectType: ");
		result.append(objectType);
		result.append(", objectTypeMany: ");
		result.append(objectTypeMany);
		result.append(", objectArrayType: ");
		result.append(objectArrayType);
		result.append(", objectArrayTypeMany: ");
		result.append(objectArrayTypeMany);
		result.append(", uris: ");
		result.append(uris);
		result.append(')');
		return result.toString();
	}

} //ETypesImpl
