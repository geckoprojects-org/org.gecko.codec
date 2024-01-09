/*
 */
package org.eclipse.emfcloud.jackson.junit.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ETypes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEString <em>EString</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEStrings <em>EStrings</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#isEBoolean <em>EBoolean</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBooleans <em>EBooleans</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEInt <em>EInt</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEInts <em>EInts</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getDoubleValue <em>Double Value</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDouble <em>EDouble</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDoubles <em>EDoubles</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBigDecimal <em>EBig Decimal</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBigInteger <em>EBig Integer</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEByte <em>EByte</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEByteArray <em>EByte Array</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEChar <em>EChar</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDate <em>EDate</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEFloat <em>EFloat</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getELong <em>ELong</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEShort <em>EShort</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectTypeMany <em>Object Type Many</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectArrayType <em>Object Array Type</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectArrayTypeMany <em>Object Array Type Many</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getValues <em>Values</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getValuesWithRef <em>Values With Ref</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getStringMapValues <em>String Map Values</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getDataTypeMapValues <em>Data Type Map Values</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getUris <em>Uris</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes()
 * @model
 * @generated
 */
@ProviderType
public interface ETypes extends EObject {
	/**
	 * Returns the value of the '<em><b>EString</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EString</em>' attribute.
	 * @see #setEString(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EString()
	 * @model unique="false"
	 * @generated
	 */
	String getEString();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEString <em>EString</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EString</em>' attribute.
	 * @see #getEString()
	 * @generated
	 */
	void setEString(String value);

	/**
	 * Returns the value of the '<em><b>EStrings</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EStrings</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EStrings()
	 * @model
	 * @generated
	 */
	EList<String> getEStrings();

	/**
	 * Returns the value of the '<em><b>EBoolean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EBoolean</em>' attribute.
	 * @see #setEBoolean(boolean)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EBoolean()
	 * @model unique="false"
	 * @generated
	 */
	boolean isEBoolean();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#isEBoolean <em>EBoolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EBoolean</em>' attribute.
	 * @see #isEBoolean()
	 * @generated
	 */
	void setEBoolean(boolean value);

	/**
	 * Returns the value of the '<em><b>EBooleans</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Boolean}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EBooleans</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EBooleans()
	 * @model
	 * @generated
	 */
	EList<Boolean> getEBooleans();

	/**
	 * Returns the value of the '<em><b>EInt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EInt</em>' attribute.
	 * @see #setEInt(int)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EInt()
	 * @model unique="false"
	 * @generated
	 */
	int getEInt();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEInt <em>EInt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EInt</em>' attribute.
	 * @see #getEInt()
	 * @generated
	 */
	void setEInt(int value);

	/**
	 * Returns the value of the '<em><b>EInts</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EInts</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EInts()
	 * @model
	 * @generated
	 */
	EList<Integer> getEInts();

	/**
	 * Returns the value of the '<em><b>Double Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Double Value</em>' attribute.
	 * @see #setDoubleValue(Double)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_DoubleValue()
	 * @model unique="false"
	 * @generated
	 */
	Double getDoubleValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getDoubleValue <em>Double Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Double Value</em>' attribute.
	 * @see #getDoubleValue()
	 * @generated
	 */
	void setDoubleValue(Double value);

	/**
	 * Returns the value of the '<em><b>EDouble</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EDouble</em>' attribute.
	 * @see #setEDouble(double)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EDouble()
	 * @model unique="false"
	 * @generated
	 */
	double getEDouble();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDouble <em>EDouble</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EDouble</em>' attribute.
	 * @see #getEDouble()
	 * @generated
	 */
	void setEDouble(double value);

	/**
	 * Returns the value of the '<em><b>EDoubles</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Double}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EDoubles</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EDoubles()
	 * @model
	 * @generated
	 */
	EList<Double> getEDoubles();

	/**
	 * Returns the value of the '<em><b>EBig Decimal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EBig Decimal</em>' attribute.
	 * @see #setEBigDecimal(BigDecimal)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EBigDecimal()
	 * @model unique="false"
	 * @generated
	 */
	BigDecimal getEBigDecimal();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBigDecimal <em>EBig Decimal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EBig Decimal</em>' attribute.
	 * @see #getEBigDecimal()
	 * @generated
	 */
	void setEBigDecimal(BigDecimal value);

	/**
	 * Returns the value of the '<em><b>EBig Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EBig Integer</em>' attribute.
	 * @see #setEBigInteger(BigInteger)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EBigInteger()
	 * @model unique="false"
	 * @generated
	 */
	BigInteger getEBigInteger();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEBigInteger <em>EBig Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EBig Integer</em>' attribute.
	 * @see #getEBigInteger()
	 * @generated
	 */
	void setEBigInteger(BigInteger value);

	/**
	 * Returns the value of the '<em><b>EByte</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EByte</em>' attribute.
	 * @see #setEByte(byte)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EByte()
	 * @model unique="false"
	 * @generated
	 */
	byte getEByte();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEByte <em>EByte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EByte</em>' attribute.
	 * @see #getEByte()
	 * @generated
	 */
	void setEByte(byte value);

	/**
	 * Returns the value of the '<em><b>EByte Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EByte Array</em>' attribute.
	 * @see #setEByteArray(byte[])
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EByteArray()
	 * @model unique="false"
	 * @generated
	 */
	byte[] getEByteArray();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEByteArray <em>EByte Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EByte Array</em>' attribute.
	 * @see #getEByteArray()
	 * @generated
	 */
	void setEByteArray(byte[] value);

	/**
	 * Returns the value of the '<em><b>EChar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EChar</em>' attribute.
	 * @see #setEChar(char)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EChar()
	 * @model unique="false"
	 * @generated
	 */
	char getEChar();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEChar <em>EChar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EChar</em>' attribute.
	 * @see #getEChar()
	 * @generated
	 */
	void setEChar(char value);

	/**
	 * Returns the value of the '<em><b>EDate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EDate</em>' attribute.
	 * @see #setEDate(Date)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EDate()
	 * @model unique="false"
	 * @generated
	 */
	Date getEDate();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEDate <em>EDate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EDate</em>' attribute.
	 * @see #getEDate()
	 * @generated
	 */
	void setEDate(Date value);

	/**
	 * Returns the value of the '<em><b>EFloat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EFloat</em>' attribute.
	 * @see #setEFloat(float)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EFloat()
	 * @model unique="false"
	 * @generated
	 */
	float getEFloat();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEFloat <em>EFloat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EFloat</em>' attribute.
	 * @see #getEFloat()
	 * @generated
	 */
	void setEFloat(float value);

	/**
	 * Returns the value of the '<em><b>ELong</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ELong</em>' attribute.
	 * @see #setELong(long)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_ELong()
	 * @model unique="false"
	 * @generated
	 */
	long getELong();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getELong <em>ELong</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ELong</em>' attribute.
	 * @see #getELong()
	 * @generated
	 */
	void setELong(long value);

	/**
	 * Returns the value of the '<em><b>EShort</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EShort</em>' attribute.
	 * @see #setEShort(short)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_EShort()
	 * @model unique="false"
	 * @generated
	 */
	short getEShort();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getEShort <em>EShort</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EShort</em>' attribute.
	 * @see #getEShort()
	 * @generated
	 */
	void setEShort(short value);

	/**
	 * Returns the value of the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type</em>' attribute.
	 * @see #setObjectType(Object)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_ObjectType()
	 * @model unique="false" dataType="org.eclipse.emfcloud.jackson.junit.model.ObjectType"
	 * @generated
	 */
	Object getObjectType();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectType <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Type</em>' attribute.
	 * @see #getObjectType()
	 * @generated
	 */
	void setObjectType(Object value);

	/**
	 * Returns the value of the '<em><b>Object Type Many</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Object}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type Many</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_ObjectTypeMany()
	 * @model unique="false" dataType="org.eclipse.emfcloud.jackson.junit.model.ObjectType"
	 * @generated
	 */
	EList<Object> getObjectTypeMany();

	/**
	 * Returns the value of the '<em><b>Object Array Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Array Type</em>' attribute.
	 * @see #setObjectArrayType(Object[])
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_ObjectArrayType()
	 * @model unique="false" dataType="org.eclipse.emfcloud.jackson.junit.model.ObjectArrayType"
	 * @generated
	 */
	Object[] getObjectArrayType();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes#getObjectArrayType <em>Object Array Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Array Type</em>' attribute.
	 * @see #getObjectArrayType()
	 * @generated
	 */
	void setObjectArrayType(Object[] value);

	/**
	 * Returns the value of the '<em><b>Object Array Type Many</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Object}<code>[]</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Array Type Many</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_ObjectArrayTypeMany()
	 * @model unique="false" dataType="org.eclipse.emfcloud.jackson.junit.model.ObjectArrayType"
	 * @generated
	 */
	EList<Object[]> getObjectArrayTypeMany();

	/**
	 * Returns the value of the '<em><b>Values</b></em>' map.
	 * The key is of type {@link org.eclipse.emfcloud.jackson.junit.model.Type},
	 * and the value is of type {@link org.eclipse.emfcloud.jackson.junit.model.Value},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' map.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_Values()
	 * @model mapType="org.eclipse.emfcloud.jackson.junit.model.TMap&lt;org.eclipse.emfcloud.jackson.junit.model.Type, org.eclipse.emfcloud.jackson.junit.model.Value&gt;"
	 * @generated
	 */
	EMap<Type, Value> getValues();

	/**
	 * Returns the value of the '<em><b>Values With Ref</b></em>' map.
	 * The key is of type {@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject},
	 * and the value is of type {@link org.eclipse.emfcloud.jackson.junit.model.TargetObject},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values With Ref</em>' map.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_ValuesWithRef()
	 * @model mapType="org.eclipse.emfcloud.jackson.junit.model.TMapRef&lt;org.eclipse.emfcloud.jackson.junit.model.PrimaryObject, org.eclipse.emfcloud.jackson.junit.model.TargetObject&gt;"
	 * @generated
	 */
	EMap<PrimaryObject, TargetObject> getValuesWithRef();

	/**
	 * Returns the value of the '<em><b>String Map Values</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.emfcloud.jackson.junit.model.Value},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Map Values</em>' map.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_StringMapValues()
	 * @model mapType="org.eclipse.emfcloud.jackson.junit.model.StringMap&lt;org.eclipse.emf.ecore.EString, org.eclipse.emfcloud.jackson.junit.model.Value&gt;"
	 * @generated
	 */
	EMap<String, Value> getStringMapValues();

	/**
	 * Returns the value of the '<em><b>Data Type Map Values</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type Map Values</em>' map.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_DataTypeMapValues()
	 * @model mapType="org.eclipse.emfcloud.jackson.junit.model.DataTypeMap&lt;org.eclipse.emfcloud.jackson.junit.model.UserType, org.eclipse.emf.ecore.EString&gt;"
	 * @generated
	 */
	EMap<String, String> getDataTypeMapValues();

	/**
	 * Returns the value of the '<em><b>Uris</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.common.util.URI}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uris</em>' attribute list.
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getETypes_Uris()
	 * @model dataType="org.eclipse.emfcloud.jackson.junit.model.URI"
	 * @generated
	 */
	EList<URI> getUris();

} // ETypes
