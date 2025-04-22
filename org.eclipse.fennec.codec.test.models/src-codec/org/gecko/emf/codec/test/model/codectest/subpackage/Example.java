/*
 */
package org.gecko.emf.codec.test.model.codectest.subpackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.gecko.emf.codec.test.model.codectest.Address;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Example</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getExampleId <em>Example Id</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMandatory <em>Mandatory</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMandatoryMultiple <em>Mandatory Multiple</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMultiple01 <em>Multiple01</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMultiple02 <em>Multiple02</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getDerived <em>Derived</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getContainment <em>Containment</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getTransient <em>Transient</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigLong <em>Big Long</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigDouble <em>Big Double</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigInteger <em>Big Integer</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigFloat <em>Big Float</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigChar <em>Big Char</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigBool <em>Big Bool</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigShort <em>Big Short</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigByte <em>Big Byte</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallLong <em>Small Long</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallDouble <em>Small Double</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallInteger <em>Small Integer</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallFloat <em>Small Float</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallChar <em>Small Char</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#isSmallBool <em>Small Bool</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallShort <em>Small Short</em>}</li>
 *   <li>{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallByte <em>Small Byte</em>}</li>
 * </ul>
 *
 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample()
 * @model
 * @generated
 */
@ProviderType
public interface Example extends EObject {
	/**
	 * Returns the value of the '<em><b>Example Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Example Id</em>' attribute.
	 * @see #setExampleId(String)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_ExampleId()
	 * @model id="true"
	 * @generated
	 */
	String getExampleId();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getExampleId <em>Example Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Example Id</em>' attribute.
	 * @see #getExampleId()
	 * @generated
	 */
	void setExampleId(String value);

	/**
	 * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mandatory</em>' attribute.
	 * @see #setMandatory(String)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_Mandatory()
	 * @model required="true"
	 * @generated
	 */
	String getMandatory();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getMandatory <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mandatory</em>' attribute.
	 * @see #getMandatory()
	 * @generated
	 */
	void setMandatory(String value);

	/**
	 * Returns the value of the '<em><b>Mandatory Multiple</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mandatory Multiple</em>' attribute list.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_MandatoryMultiple()
	 * @model required="true"
	 * @generated
	 */
	EList<String> getMandatoryMultiple();

	/**
	 * Returns the value of the '<em><b>Multiple01</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple01</em>' attribute list.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_Multiple01()
	 * @model
	 * @generated
	 */
	EList<String> getMultiple01();

	/**
	 * Returns the value of the '<em><b>Multiple02</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple02</em>' attribute list.
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_Multiple02()
	 * @model upper="4"
	 * @generated
	 */
	EList<String> getMultiple02();

	/**
	 * Returns the value of the '<em><b>Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Derived</em>' attribute.
	 * @see #setDerived(int)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_Derived()
	 * @model derived="true"
	 * @generated
	 */
	int getDerived();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getDerived <em>Derived</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Derived</em>' attribute.
	 * @see #getDerived()
	 * @generated
	 */
	void setDerived(int value);

	/**
	 * Returns the value of the '<em><b>Containment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containment</em>' containment reference.
	 * @see #setContainment(Address)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_Containment()
	 * @model containment="true"
	 * @generated
	 */
	Address getContainment();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getContainment <em>Containment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containment</em>' containment reference.
	 * @see #getContainment()
	 * @generated
	 */
	void setContainment(Address value);

	/**
	 * Returns the value of the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transient</em>' attribute.
	 * @see #setTransient(long)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_Transient()
	 * @model transient="true"
	 * @generated
	 */
	long getTransient();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getTransient <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transient</em>' attribute.
	 * @see #getTransient()
	 * @generated
	 */
	void setTransient(long value);

	/**
	 * Returns the value of the '<em><b>Big Long</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Big Long</em>' attribute.
	 * @see #setBigLong(Long)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_BigLong()
	 * @model
	 * @generated
	 */
	Long getBigLong();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigLong <em>Big Long</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Big Long</em>' attribute.
	 * @see #getBigLong()
	 * @generated
	 */
	void setBigLong(Long value);

	/**
	 * Returns the value of the '<em><b>Big Double</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Big Double</em>' attribute.
	 * @see #setBigDouble(Double)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_BigDouble()
	 * @model
	 * @generated
	 */
	Double getBigDouble();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigDouble <em>Big Double</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Big Double</em>' attribute.
	 * @see #getBigDouble()
	 * @generated
	 */
	void setBigDouble(Double value);

	/**
	 * Returns the value of the '<em><b>Big Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Big Integer</em>' attribute.
	 * @see #setBigInteger(Integer)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_BigInteger()
	 * @model
	 * @generated
	 */
	Integer getBigInteger();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigInteger <em>Big Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Big Integer</em>' attribute.
	 * @see #getBigInteger()
	 * @generated
	 */
	void setBigInteger(Integer value);

	/**
	 * Returns the value of the '<em><b>Big Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Big Float</em>' attribute.
	 * @see #setBigFloat(Float)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_BigFloat()
	 * @model
	 * @generated
	 */
	Float getBigFloat();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigFloat <em>Big Float</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Big Float</em>' attribute.
	 * @see #getBigFloat()
	 * @generated
	 */
	void setBigFloat(Float value);

	/**
	 * Returns the value of the '<em><b>Big Char</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Big Char</em>' attribute.
	 * @see #setBigChar(Character)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_BigChar()
	 * @model
	 * @generated
	 */
	Character getBigChar();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigChar <em>Big Char</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Big Char</em>' attribute.
	 * @see #getBigChar()
	 * @generated
	 */
	void setBigChar(Character value);

	/**
	 * Returns the value of the '<em><b>Big Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Big Bool</em>' attribute.
	 * @see #setBigBool(Boolean)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_BigBool()
	 * @model
	 * @generated
	 */
	Boolean getBigBool();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigBool <em>Big Bool</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Big Bool</em>' attribute.
	 * @see #getBigBool()
	 * @generated
	 */
	void setBigBool(Boolean value);

	/**
	 * Returns the value of the '<em><b>Big Short</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Big Short</em>' attribute.
	 * @see #setBigShort(Short)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_BigShort()
	 * @model
	 * @generated
	 */
	Short getBigShort();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigShort <em>Big Short</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Big Short</em>' attribute.
	 * @see #getBigShort()
	 * @generated
	 */
	void setBigShort(Short value);

	/**
	 * Returns the value of the '<em><b>Big Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Big Byte</em>' attribute.
	 * @see #setBigByte(Byte)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_BigByte()
	 * @model
	 * @generated
	 */
	Byte getBigByte();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getBigByte <em>Big Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Big Byte</em>' attribute.
	 * @see #getBigByte()
	 * @generated
	 */
	void setBigByte(Byte value);

	/**
	 * Returns the value of the '<em><b>Small Long</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Long</em>' attribute.
	 * @see #setSmallLong(long)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_SmallLong()
	 * @model
	 * @generated
	 */
	long getSmallLong();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallLong <em>Small Long</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Long</em>' attribute.
	 * @see #getSmallLong()
	 * @generated
	 */
	void setSmallLong(long value);

	/**
	 * Returns the value of the '<em><b>Small Double</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Double</em>' attribute.
	 * @see #setSmallDouble(double)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_SmallDouble()
	 * @model
	 * @generated
	 */
	double getSmallDouble();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallDouble <em>Small Double</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Double</em>' attribute.
	 * @see #getSmallDouble()
	 * @generated
	 */
	void setSmallDouble(double value);

	/**
	 * Returns the value of the '<em><b>Small Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Integer</em>' attribute.
	 * @see #setSmallInteger(int)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_SmallInteger()
	 * @model
	 * @generated
	 */
	int getSmallInteger();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallInteger <em>Small Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Integer</em>' attribute.
	 * @see #getSmallInteger()
	 * @generated
	 */
	void setSmallInteger(int value);

	/**
	 * Returns the value of the '<em><b>Small Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Float</em>' attribute.
	 * @see #setSmallFloat(float)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_SmallFloat()
	 * @model
	 * @generated
	 */
	float getSmallFloat();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallFloat <em>Small Float</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Float</em>' attribute.
	 * @see #getSmallFloat()
	 * @generated
	 */
	void setSmallFloat(float value);

	/**
	 * Returns the value of the '<em><b>Small Char</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Char</em>' attribute.
	 * @see #setSmallChar(char)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_SmallChar()
	 * @model
	 * @generated
	 */
	char getSmallChar();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallChar <em>Small Char</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Char</em>' attribute.
	 * @see #getSmallChar()
	 * @generated
	 */
	void setSmallChar(char value);

	/**
	 * Returns the value of the '<em><b>Small Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Bool</em>' attribute.
	 * @see #setSmallBool(boolean)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_SmallBool()
	 * @model
	 * @generated
	 */
	boolean isSmallBool();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#isSmallBool <em>Small Bool</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Bool</em>' attribute.
	 * @see #isSmallBool()
	 * @generated
	 */
	void setSmallBool(boolean value);

	/**
	 * Returns the value of the '<em><b>Small Short</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Short</em>' attribute.
	 * @see #setSmallShort(short)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_SmallShort()
	 * @model
	 * @generated
	 */
	short getSmallShort();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallShort <em>Small Short</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Short</em>' attribute.
	 * @see #getSmallShort()
	 * @generated
	 */
	void setSmallShort(short value);

	/**
	 * Returns the value of the '<em><b>Small Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Small Byte</em>' attribute.
	 * @see #setSmallByte(byte)
	 * @see org.gecko.emf.codec.test.model.codectest.subpackage.SubpackagePackage#getExample_SmallByte()
	 * @model
	 * @generated
	 */
	byte getSmallByte();

	/**
	 * Sets the value of the '{@link org.gecko.emf.codec.test.model.codectest.subpackage.Example#getSmallByte <em>Small Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Byte</em>' attribute.
	 * @see #getSmallByte()
	 * @generated
	 */
	void setSmallByte(byte value);

} // Example
